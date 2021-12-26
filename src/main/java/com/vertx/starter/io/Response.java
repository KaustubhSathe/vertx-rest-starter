package com.vertx.starter.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
  private T data;
  private Error error;

  @JsonIgnore
  private int httpStatusCode = 200;

  @JsonCreator()
  public Response(@JsonProperty("data") T data,@JsonProperty("error") Error error){
    this.data = data;
    this.error = error;
  }


}
