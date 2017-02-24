package com.zy17.guess.famous.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 2017/2/17 zy17
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String tokenExpireException(NullPointerException ex) {
    return ClassUtils.getShortName(ex.getClass()) + ex.getMessage();
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleIOException(Exception ex) {
    return ClassUtils.getShortName(ex.getClass()) + ex.getMessage();
  }
}
