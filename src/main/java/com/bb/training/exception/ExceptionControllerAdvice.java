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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionControllerAdvice
{
  /** Handle DataException. */
  @ExceptionHandler(DataException.class)
  @ResponseBody
  public ErrorCode exception(DataException ex, HttpServletRequest request, HttpServletResponse response)
  {
    ErrorCode errorCode = ex.getErrorCode();


    if(errorCode == ErrorCode.SEARCH_NO_RESULTS)
    {
      response.setStatus(HttpStatus.NOT_FOUND.value());
    } else {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    return errorCode;
  }
}
