package com.bb.training.exception;

public enum ErrorCode
{
  SEARCH_NO_RESULTS              ("searchNoResults"),
  UNKNOWN                         ("unknown");

  private String value;

  ErrorCode (String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }
}
