package com.vertx.starter;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
  String path();
  String httpMethod() default "GET";
  String produces() default "application/json";
  String consumes() default  "application/json";
  String[] requiredHeaders() default {};
  String[] requiredQueryParams() default {};
  String[] requiredBodyParams() default {};
  long timeout() default 20_000L;
}
