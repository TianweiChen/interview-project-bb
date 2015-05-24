package com.bb.training.util;

import com.bb.training.domain.Node;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

public class PrintUtil
{
  private PrintUtil()
  {
  }

  /**
   * Print out json
   */
  public static void printNode(Node root)
    {
      Jackson2ObjectMapperFactoryBean bean = new org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean();
      bean.setFeaturesToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      bean.afterPropertiesSet();
      ObjectMapper objectMapper = bean.getObject();

      try
      {
        String json = objectMapper.writeValueAsString(root);

        System.out.println(json);
      }
      catch (Exception e)
      {
        // do nothing
      }
    }

}
