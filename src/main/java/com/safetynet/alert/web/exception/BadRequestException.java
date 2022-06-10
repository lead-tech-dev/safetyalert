package com.safetynet.alert.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException. class that intercept
 * given bad request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

}
