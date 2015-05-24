package com.bb.training.exception

import com.bb.training.testconfig.ExceptionControllerAdviceConfig
import com.bb.training.testconfig.MockExceptionController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.method.HandlerMethod
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.util.NestedServletException
import spock.lang.Specification

import java.lang.reflect.Method

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

/**
 * mocMVC to test DataException e.g. no results found
 */
@ContextConfiguration(classes = ExceptionControllerAdviceConfig.class)
@WebAppConfiguration
class ExceptionControllerAdviceSpec extends Specification
{
  @Autowired
  MockExceptionController mockExceptionController

  @Autowired
  private ExceptionControllerAdvice exceptionControllerAdvice;

  private MockMvc mockMvc;

      def setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
                viewResolver.setPrefix("/WEB-INF/jsp/view/");
                viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(mockExceptionController)
                                         .setViewResolvers(viewResolver)
        .setHandlerExceptionResolvers(createExceptionResolver())
                                         .build();

      }

  private ExceptionHandlerExceptionResolver createExceptionResolver() {
      ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
          protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
              Method method = new ExceptionHandlerMethodResolver(ExceptionControllerAdvice.class).resolveMethod(exception);
              return new ServletInvocableHandlerMethod(exceptionControllerAdvice, method);
          }
      };
      exceptionResolver.afterPropertiesSet();
      return exceptionResolver;
  }


  def "Data Exception Test"()
  {
    when:
    def result = mockMvc.perform(get("/dataException").accept(MediaType.APPLICATION_JSON))

    then:
    NestedServletException ex = thrown()
    assert ex.getCause() instanceof DataException
    ((DataException)ex.getCause()).errorCode == ErrorCode.SEARCH_NO_RESULTS
  }
}
