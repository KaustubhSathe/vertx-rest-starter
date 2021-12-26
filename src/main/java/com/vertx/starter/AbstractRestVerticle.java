package com.vertx.starter;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.http.HttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRestVerticle extends AbstractVerticle {
  private final String packageName;
  private HttpServer httpServer;
  private HttpConfig

  public AbstractRestVerticle(String packageName){
    this.packageName = packageName;
  }

  @Override
  public Completable rxStart() {

  }

  private Single<HttpServer> startHttpServer() {
    HttpServerOptions options = new HttpServerOptions()
      .setHost(htt)
  }

  @Override
  public Completable rxStop() {
    return super.rxStop();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
