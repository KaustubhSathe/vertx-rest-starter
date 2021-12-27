package com.vertx.starter;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
  String path();
  HttpMethod httpMethod() default HttpMethod.GET;
  String produces() default "application/json";
  String consumes

}
