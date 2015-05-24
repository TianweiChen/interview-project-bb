package com.bb.training.config;

import com.bb.training.context.Context;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

/** Configure the Spring MVC application. */
@ComponentScan(basePackages = {"com.bb.training.context", "com.bb.training.controller", "com.bb.training.domain", "com.bb.training.search"})
@Configuration
public class MainConfig
{
  @Bean
  Context context() throws Throwable
  {
    return new Context();
  }

  @Bean
  ObjectMapper objectMapper()
  {
    Jackson2ObjectMapperFactoryBean bean = new org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean();

    bean.setFeaturesToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    bean.afterPropertiesSet();
    return bean.getObject();
  }

  @Bean
  Gson gson()
  {
    return new GsonBuilder().serializeNulls().create();
  }
}
