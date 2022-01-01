package com.vertx.starter.exception;

import com.vertx.starter.io.Error;

public interface RestError {
  String getErrorCode();

  String getErrorMessage();

  int getHttpStatusCode();

  default Error getError() {
    return Error.of(this.getErrorCode(), this.getErrorMessage());
  }
}
