package com.bb.training.testconfig;

import com.bb.training.exception.ExceptionControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan({ "com.bb.training.exception.ExceptionControllerAdvice", "com.bb.training.testconfig.MockExceptionController" })
@Configuration
@EnableWebMvc
public class ExceptionControllerAdviceConfig
{
  @Bean
  public ExceptionControllerAdvice exceptionControllerAdvice()
  {
    ExceptionControllerAdvice advice = new ExceptionControllerAdvice();

    return advice;
  }

  @Bean
  public MockExceptionController mockExceptionController()
  {
    MockExceptionController controller = new MockExceptionController();

    return controller;
  }
}
