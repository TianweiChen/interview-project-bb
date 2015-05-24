package com.bb.training.search

import com.bb.training.context.Context
import com.bb.training.domain.Node
import com.bb.training.exception.DataException
import spock.lang.Specification

/**
 * Tests cover 1 or multiple treasures, and at different levels
 */
class SearchServiceImplSpec extends Specification
{
  Map<Integer, PosCounter> counterMap = new HashMap<>()

  def "search test with 1 treasure"()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    treasures.add(new Position(2, 1))
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    resultNode.getChildren().size() == 3

    treasureNodeCount(resultNode) == treasures.size()

    allTreasuresExistInResultAndAssertPosition(treasures, resultNode) == true

    maxDepthOfPathNoTreasureIsOne(resultNode) == true
  }

  def "search test with 2 treasures"()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    treasures.add(new Position(2, 1))
    treasures.add(new Position(3, 6))
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    resultNode.getChildren().size() == 3

    treasureNodeCount(resultNode) == treasures.size()

    allTreasuresExistInResultAndAssertPosition(treasures, resultNode) == true

    maxDepthOfPathNoTreasureIsOne(resultNode) == true
  }

  def "search test with 3 treasures"()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    treasures.add(new Position(1, 1))
    treasures.add(new Position(2, 4))
    treasures.add(new Position(3, 12))
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    resultNode.getChildren().size() == 3

    treasureNodeCount(resultNode) == treasures.size()

    allTreasuresExistInResultAndAssertPosition(treasures, resultNode) == true

    maxDepthOfPathNoTreasureIsOne(resultNode) == true
  }

  def "search test with no treasure "()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    DataException ex = thrown()
  }

  def "search test with 1 treasure at root"()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    treasures.add(new Position(0, 0))
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    DataException ex = thrown()
  }

  def "search test with 1 treasure at 1st level"()
  {
    given:
    counterMap.clear()

    SearchService searchService = new SearchServiceImpl()
    Context mockContext = Mock(Context)
    searchService.context = mockContext
    List<Position> treasures = new ArrayList<>()
    treasures.add(new Position(1, 1))
    mockContext.getData() >> createData(treasures, 4)

    when:
    Node resultNode = searchService.search("treasure")

    then:
    resultNode.getChildren().size() == 3

    treasureNodeCount(resultNode) == treasures.size()

    allTreasuresExistInResultAndAssertPosition(treasures, resultNode) == true

    maxDepthOfPathNoTreasureIsOne(resultNode) == true
  }

  /**
   * Assert result structure for the first level nodes other than the treasure path
   */
  private boolean maxDepthOfPathNoTreasureIsOne(Node resultNode) {
    List<Node> treasureNodes = new ArrayList<>()
    findTreasureNodes(resultNode, treasureNodes)

    Map<Node, Integer> depthMapOfTreasurePath = new HashMap<>()
    for(Node treasureNode: treasureNodes) {
      List<Node> path = findPath(treasureNode)

      if(path.size() > 1)
      {
        if(depthMapOfTreasurePath.get(path.get(1)) == null || (depthMapOfTreasurePath.get(path.get(1)) != null && depthMapOfTreasurePath.get(path.get(1)).intValue() < path.size()))
        {
          depthMapOfTreasurePath.put(path.get(1), path.size())
        }
      }
    }

    for(Node child: resultNode.getChildren()) {
      if(depthMapOfTreasurePath.get(child) == null) {
        assert findPath(child).size() == 1
      }
    }
    return true
  }

  private List<Node> findPath(Node treasureNode)
  {
    List<Node> path = new ArrayList<>()

    Node child = treasureNode
    while (child.getParent() != null)
    {
      path.add(0, child.getParent())
      child = child.getParent()
    }

    return path
  }

  private void findTreasureNodes(Node resultNode, List<Node> treasureNodes) {
    if("treasure".equals(resultNode.getId())) {
      treasureNodes.add(resultNode)
    }

    for(Node resultChild : resultNode.getChildren()) {
      resultChild.setParent(resultNode)
      findTreasureNodes(resultChild, treasureNodes)
    }
  }

  private int treasureNodeCount(Node resultNode) {
    List<Node> treasureNodes = new ArrayList<>()
    findTreasureNodes(resultNode, treasureNodes)
    return treasureNodes.size()
  }

  /**
   * Assert result structure for the treasures
   */
  private boolean allTreasuresExistInResultAndAssertPosition(List<Position> treasures, Node resultNode) {
    List<Node> treasureNodes = new ArrayList<>()
    findTreasureNodes(resultNode, treasureNodes)

    for(Position position: treasures) {
      boolean found = false
      for(Node foundTreasure: treasureNodes) {
        if(foundTreasure.getName().contains(position.y.toString() + position.x.toString())) {
          assert findPath(foundTreasure).size() == position.y
          found = true;
          break;
        }
      }

      if(!found) {
        return false;
      }
    }

    return true;
  }

  /**
   * create mock data for the tests
   */
  private Node createData(List<Position> treasures, int maxLevel)
  {
    Node root = createNode(0, 0, "")
    Node parent = root

    Integer level = 1
    PosCounter posCounter;
    if(counterMap.get(level) == null)
    {
      counterMap.put(level, new PosCounter(level))
    }
    posCounter = counterMap.get(level)

    addNode(parent, level, posCounter, treasures, maxLevel)

    return root
  }

  private addNode(Node parent, Integer level, PosCounter posCounter, List<Position> treasures, int maxLevel)
  {
    if (level < maxLevel)
    {
      for (int pos =0; pos < 3; pos++)
      {
        Node child = createChild(level, posCounter, treasures)
        parent.addChild(child)
        posCounter.increment()
      }

      level = ++level
      if(counterMap.get(level) == null)
      {
        counterMap.put(level, new PosCounter(level))
      }
      posCounter = counterMap.get(level)

      for (Node child : parent.getChildren())
      {
        addNode(child, level, posCounter, treasures, maxLevel)
      }
    }
  }

  private Node createChild(Integer level, PosCounter posCounter, List<Position> treasures)
  {
    if (needATreasure(treasures, level, posCounter.getCounter()))
    {

      return createNode(level, posCounter.getCounter(), "treasure")
    }
    else
    {
      return createNode(level, posCounter.getCounter(), "")
    }

  }

  private Boolean needATreasure(List<Position> treasures, Integer level, int pos)
  {
    for (Position position : treasures)
    {
      if (position.getY() == level.intValue() && position.getX() == pos)
      {
        return true;
      }
    }

    return false;
  }

  private Node createNode(Integer level, int pos, String treasure)
  {
    Node aNode = new Node()
    if (treasure.length() != 0)
    {
      aNode.setId(treasure)
    }
    else
    {
      aNode.setId("id" + level + pos)
    }

    aNode.setName("name" + level + pos)
    aNode.setUrl("http://" + level + pos)

    return aNode
  }
}

class Position
{
  private int y;
  private int x;

  Position(int y, int x)
  {
    this.y = y
    this.x = x
  }

  int getY()
  {
    return y
  }

  void setY(int y)
  {
    this.y = y
  }

  int getX()
  {
    return x
  }

  void setX(int x)
  {
    this.x = x
  }
}

class PosCounter {
private Integer level;
  private int counter;

  PosCounter(int level)
  {
    this.level = level
  }

  Integer getLevel()
  {
    return level
  }

  void setLevel(Integer level)
  {
    this.level = level
  }

  int getCounter()
  {
    return counter
  }

  void setCounter(int counter)
  {
    this.counter = counter
  }

  void increment() {
    counter = ++counter
  }
}
