package com.bb.training.search;

import com.bb.training.context.Context;
import com.bb.training.domain.Node;
import com.bb.training.exception.DataException;
import com.bb.training.exception.ErrorCode;
import com.bb.training.util.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Search node(s) based on the ID. Since there may be multiple nodes with the same ID, we traverse the whole tree and add all found nodes on
 * a result tree.
 */
@Component
public class SearchServiceImpl implements SearchService
{
  @Autowired
  Context context;

  /**
   * Search nodes based on id.
   *
   * @return a rootNode with first layer nodes from data root; and full path include found nodes.
   * @throws DataException if no node has been found.
   */
  @Override
  public Node search(String id) throws DataException
  {
    Node root = context.getData();

    PrintUtil.printNode(root);

    Node resultRoot = cloneNode(root);

    for (Node child : root.getChildren())
    {
      child.setParent(root);
      if (id.equals(child.getId()))
      {
        addFoundNode(child, resultRoot);
      }

      findNode(child, id, resultRoot);
    }

    if (resultRoot.getChildren().size() == 0)
    {
      throw new DataException("No results found.", ErrorCode.SEARCH_NO_RESULTS);
    }
    else
    {
      addFirstLevelNodesToResultRoot(root, resultRoot);
    }

    PrintUtil.printNode(resultRoot);

    return resultRoot;
  }

  private void addFirstLevelNodesToResultRoot(Node root, Node resultRoot)
  {
    for (Node child : root.getChildren())
    {
      if (!resultRoot.getChildren().contains(child))
      {
        resultRoot.addChild(cloneNode(child));
      }
    }
  }

  private void findNode(Node parent, String id, Node resultRoot)
  {
    for (Node child : parent.getChildren())
    {
      child.setParent(parent);
      if (id.equals(child.getId()))
      {
        addFoundNode(child, resultRoot);
      }

      findNode(child, id, resultRoot);
    }

  }

  /**
   * Add found node to the result root
   */
  private void addFoundNode(Node found, Node resultRoot)
  {
    Node parentNode = resultRoot;

    List<Node> path = new ArrayList();
    path.add(found);

    Node childTmp = found;
    while(childTmp.getParent() != null) {
      path.add(0, childTmp.getParent());
      childTmp = childTmp.getParent();
    }

    for (int i = 1; i < path.size(); i++)
    {
      Node child = path.get(i);

      Node existingChild = parentNode.findChild(child);
      Node resultChild;
      if(existingChild == null) {
        resultChild = cloneNode(child);
        parentNode.addChild(resultChild);
      } else {
        resultChild = existingChild;
      }

      parentNode = resultChild;
    }
  }

  private Node cloneNode(Node node)
  {
    Node aNode = new Node();
    aNode.setId(node.getId());
    aNode.setName(node.getName());
    aNode.setUrl(node.getUrl());

    return aNode;
  }
}
