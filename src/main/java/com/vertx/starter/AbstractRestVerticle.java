package com.vertx.starter;

import com.vertx.starter.config.HttpConfig;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.http.HttpServer;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.ext.web.handler.BodyHandler;
import io.vertx.rxjava3.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.rxjava3.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractRestVerticle extends AbstractVerticle {
  private final String packageName;
  private HttpServer httpServer;
  private HttpConfig httpConfig;

  private final List<String> abstractRouterEndPoints = new ArrayList<>();

  public AbstractRestVerticle(String packageName){
    this.packageName = packageName;
  }

  @Override
  public Completable rxStart() {

  }

  private Single<HttpServer> startHttpServer() {
    HttpServerOptions options = new HttpServerOptions()
      .setHost(httpConfig.getHost())
      .setPort(httpConfig.getPort())
      .setIdleTimeout(httpConfig.getIdleTimeOut())
      .setUseAlpn(httpConfig.isUseAlpn());

    Router router = getRouter();

  }

  protected Router getRouter(){
    Router router = Router.router(this.vertx);
    router.route().handler(BodyHandler.create());
    router.route().handler(ResponseContentTypeHandler.create());
    router.route().handler(StaticHandler.create());
    router.get("/liveness").handler(ctx -> ctx.response().end("Success"));
    abstractRouterEndPoints.add("/liveness");
    var routes = 
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
