package com.bb.training.exception;

public class DataException extends Exception
{
  private String message;
  private ErrorCode errorCode;

  public DataException(String message, ErrorCode errorCode)
  {
    this.message = message;
    this.errorCode = errorCode;

  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public ErrorCode getErrorCode()
  {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode)
  {
    this.errorCode = errorCode;
  }
}
