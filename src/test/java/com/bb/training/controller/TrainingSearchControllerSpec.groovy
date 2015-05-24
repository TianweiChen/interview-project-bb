package com.bb.training.controller

import com.bb.training.config.MainConfig
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.view.InternalResourceViewResolver
import spock.lang.Specification
import com.bb.training.domain.Node

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * mocMvc test for the controller
 */
@ContextConfiguration(classes = MainConfig.class)
@WebAppConfiguration
class TrainingSearchControllerSpec extends Specification
{
  @Autowired
  TrainingSearchController trainingSearchController

  @Autowired
  Gson gson;

  private MockMvc mockMvc;

   def setup() {
     InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
             viewResolver.setPrefix("/WEB-INF/jsp/view/");
             viewResolver.setSuffix(".jsp");

     mockMvc = MockMvcBuilders.standaloneSetup(trainingSearchController)
                                      .setViewResolvers(viewResolver)
                                      .build();
   }

   def "training search controller test"() {
     when:
     MvcResult result = mockMvc.perform(get("/30daymain").accept(MediaType.APPLICATION_JSON)).andReturn()

     String json = result.getResponse().getContentAsString();
     Node resultRoot = gson.fromJson(json, Node.class);

     then:
     assert resultRoot.getChildren().size() == 6
     checkResultStructure("30daymain", resultRoot)
   }

  /**
   * Assert the result structure
   * a) the path of the selected id;
   * b) the structure of other paths.
   */
  private void checkResultStructure(String id, Node resultRoot) {
    List<Node> path = findPath(id, resultRoot)

    StringBuilder sb = new StringBuilder()
    String suffix;
    for(int i=0;i<path.size();i++) {
      suffix = ""
      Node node = path.get(i)
      if(i != path.size() -1) {
        suffix = ":"
      }
      sb.append(node.getId() + suffix)
    }

    assert sb.toString().equals("root:Training_Main:find-a-plan:30day:30daymain")

    for(Node child : resultRoot.getChildren()) {
      if(!child.equals(path.get(1))) {
        assert child.getChildren().size() == 0
      }
    }
  }

  private List<Node> findPath(String id, Node resultRoot) {

    Node found = findNode(id, resultRoot)

    List<Node> path = new ArrayList<>()
    Node child = found
    path.add(0, child)
    while(child.getParent() != null) {
      path.add(0, child.getParent())
      child = child.getParent()
    }
    return path
  }

  private Node findNode(String id, Node parent) {
    if(id.equals(parent.getId())) {
      return parent
    }

    for(Node child: parent.getChildren()) {
      child.setParent(parent)
      if(id.equals(child.getId())) {
        return child
      }
      return findNode(id, child)
    }

    return null
  }
}
