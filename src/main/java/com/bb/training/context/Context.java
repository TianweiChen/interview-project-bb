package com.bb.training.context;

import com.bb.training.domain.Node;
import com.bb.training.util.PrintUtil;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Hold the navigation data.
 */
public class Context
{
  @Autowired
  Gson gson;

  private Node data;

  public Context()
  {
  }

  @PostConstruct
  public void init()  throws Throwable {
    data = loadData();
  }

  public Node getData()
  {
    return data;
  }

  public Node loadData() throws Throwable
  {
    InputStream inputStream = Context.class.getResourceAsStream("/navigation.json");

    try
    {
      String navigationJson = IOUtils.toString(inputStream);

      Node rootScope = gson.fromJson(navigationJson, Node.class);

      PrintUtil.printNode(rootScope);

      return rootScope;

    } catch (IOException ex) {
      throw ex.getCause();
    }
    finally
    {
      IOUtils.closeQuietly(inputStream);
    }
  }
}
