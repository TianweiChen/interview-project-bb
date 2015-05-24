package com.bb.training;

import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Component
/**
 * Creates and registers Spring MVC DispatcherServlet upon container startup.
 */
class ServletTrainingServiceInitializer implements WebApplicationInitializer
{
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException
  {
    WebApplicationContext springContext = getContext();

        // spring mvc dispatcher servlet
    ServletRegistration.Dynamic registration = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(springContext));

    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }

  private AnnotationConfigWebApplicationContext getContext()
  {
    String configPackage = getConfigPackage();

    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

    context.setConfigLocation(configPackage);

    return context;
  }

  protected String getConfigPackage()
  {
    return "com.bb.training.config";
  }
}
