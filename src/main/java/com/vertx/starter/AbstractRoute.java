package com.vertx.starter;

import com.vertx.starter.io.Error;
import io.vertx.core.Handler;
import io.vertx.rxjava3.ext.web.RoutingContext;

public abstract class AbstractRoute<T> implements Handler<RoutingContext> {
  private static final Error INVALID_PARAM_ERROR = Error.of("MG1000", "Missing Parameters");


}
