package com.vertx.starter;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.MultiMap;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Value
@AllArgsConstructor
@Slf4j
public class Request {
  RoutingContext routingContext;
  MultiMap headers;
  Map<String,String> pathParams;
  MultiMap queryParams;
  JsonObject body;

  public String getHeader(String name){
    return headers != null ? headers.get(name) : null;
  }

  public String getPathParam(String name){
    return this.pathParams != null ? pathParams.get(name) : null;
  }

  public String getQueryParam(String name) {
    return queryParams != null ? queryParams.get(name) : null;
  }

  public Object getBodyParam(String name) {
    return body != null ? body.getValue(name) : null;
  }

  public Vertx vertx() {
    return this.getRoutingContext().vertx();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Request{");
    sb.append("body=").append(body != null ? body.toString() : "no body");
    sb.append(", headers=").append(headers != null ? headers.toString().replace("\n", ", ") : "no headers");
    sb.append(", pathParams=").append(pathParams != null ? pathParams.toString() : "no path params");
    sb.append(", queryParams=").append(queryParams != null ? queryParams.toString().replace("\n", ", ") : "no query params");
    sb.append('}');
    return sb.toString();
  }
}
