package com.vertx.starter.healthcheck;

import com.vertx.starter.exception.RestException;
import com.vertx.starter.io.Error;

public class HealthCheckException extends RestException {
  public HealthCheckException(String responseMessage){
    super(responseMessage, Error.of("HEALTHCHECK_FAILED", "healthcheck failed"), 503);
  }

  @Override
  public String toString(){
    return this.getMessage();
  }
}
