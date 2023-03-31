package com.aistgroup.cinemarestfullapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class IncorrectTimeRangeException extends RuntimeException {

  public IncorrectTimeRangeException(String message) {
    super(message);
  }
}
