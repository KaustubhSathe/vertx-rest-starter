package com.vertx.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertx.starter.exception.RestException;
import com.vertx.starter.io.Error;
import guice.GuiceContext;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava3.core.http.HttpServerResponse;
import io.vertx.rxjava3.ext.web.RoutingContext;

import java.util.List;

public abstract class AbstractRoute<T> implements Handler<RoutingContext> {
  private static final Error INVALID_PARAM_ERROR = Error.of("MG1000", "Missing Parameters");
  private static final RestException INVALID_REST_EXCEPTION = new RestException("Invalid Or Missing Request Params.", INVALID_PARAM_ERROR);

  protected ObjectMapper objectMapper;
  private String path;
  private HttpMethod httpMethod;
  private String produces;
  private String consumes;
  private List<String> requiredHeaders;
  private List<String> requiredQueryParams;
  private List<String> requiredBodyParams;
  private long timeout;

  public AbstractRoute(){
    this.objectMapper = GuiceContext.getInstance(ObjectMapper.class);
  }

  private void prepareResponse(RoutingContext context, T response, long startTime){
    if(!context.response().ended()){
      try{
        setResponseHeader(context.response(), response)
          .end(RestUtil.getString(response));

      }catch (){

      }
    }
  }


  protected HttpServerResponse setResponseHeader(HttpServerResponse httpServerResponse, T response){
    httpServerResponse.putHeader("content-type", produces);
    return httpServerResponse;
  }


}
