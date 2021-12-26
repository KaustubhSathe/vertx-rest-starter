package com.vertx.starter;

import io.vertx.core.Handler;
import io.vertx.rxjava3.ext.web.RoutingContext;

public abstract class AbstractRoute<T> implements Handler<RoutingContext> {
  private static final Error INVALID_PARAM_ERROR = 
}
