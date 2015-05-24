package com.bb.training.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class BaseExceptionControllerAdvice
{

  /** Handle uncaught exceptions. */
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ErrorCode handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
  {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    return ErrorCode.UNKNOWN;
  }
}
