package com.bb.training.testconfig;

import com.bb.training.exception.DataException;
import com.bb.training.exception.ErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MockExceptionController
{
  @RequestMapping(value = "/dataException",
                  method = RequestMethod.GET)
  public void getUserException() throws DataException
  {
    throw new DataException("no results found", ErrorCode.SEARCH_NO_RESULTS);
  }
}
