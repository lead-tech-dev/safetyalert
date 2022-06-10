package com.safetynet.alert.web.exception;

import java.util.Date;

/**
 * ErrorMessage. class that structure
 * the response exception message.
 */
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;

  /**
   * ErrorMessage class Constructor.
   *
   * @param statusCode a statusCode
   * @param timestamp a timestamp
   * @param message a message
   * @param description a description
   */
  public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }
}