package com.vertx.starter;

import com.kaustubh.vertx.commons.utils.ConfigUtils;
import com.vertx.starter.config.HttpConfig;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.Context;
import io.vertx.rxjava3.core.RxHelper;
import io.vertx.rxjava3.core.http.HttpServer;
import io.vertx.rxjava3.core.http.HttpServerRequest;
import io.vertx.rxjava3.ext.web.Router;
import io.vertx.rxjava3.ext.web.handler.BodyHandler;
import io.vertx.rxjava3.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.rxjava3.ext.web.handler.StaticHandler;
import io.vertx.rxjava3.ext.web.handler.TimeoutHandler;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractRestVerticle extends AbstractVerticle {
  private final String packageName;
  private final String mountPath;
  private HttpServer httpServer;
  private HttpConfig httpConfig;

  private final List<String> abstractRouteEndPoints = new ArrayList<>();

  public AbstractRestVerticle(String packageName){
    this.packageName = packageName;
    this.mountPath = "";
  }
  public AbstractRestVerticle(String packageName, String mountPath){
    this.packageName = packageName;
    if(!Strings.isNullOrEmpty(mountPath)){
      this.mountPath = "/" + mountPath;
    }else{
      this.mountPath = "";
    }
  }

  @Override
  public Completable rxStart() {
    init();
    return startHttpServer().doOnSuccess(server -> {
      this.httpServer = server;
    }).ignoreElement();
  }

  protected void init(){
    httpConfig = ConfigUtils.fromConfigFile("config/http-server/http-server-%s.conf", HttpConfig.class);
  }

  private Single<HttpServer> startHttpServer() {
    HttpServerOptions options = new HttpServerOptions()
      .setHost(httpConfig.getHost())
      .setPort(httpConfig.getPort())
      .setIdleTimeout(httpConfig.getIdleTimeOut())
      .setUseAlpn(httpConfig.isUseAlpn());

    Router router = getRouter();
    //add swagger regex
    abstractRouteEndPoints.add("/swagger(.*)");
    val server = vertx.createHttpServer(options);
    val handleRequests = server.requestStream()
      .toFlowable()
      .map(HttpServerRequest::pause)
      .onBackpressureDrop(req -> {
        log.error("Dropping request with status 503");
        req.response().setStatusCode(503).end();
      })
      .observeOn(RxHelper.scheduler(new Context(this.context)))
      .doOnNext(req -> router.handle(req))
      .map(HttpServerRequest::resume)
      .doOnError(error -> log.error("Uncaught ERROR while handling request", error))
      .ignoreElements();

    return server
      .rxListen()
      .doOnSuccess(res -> log.info("Started http server at " + options.getPort() + " package : " + packageName))
      .doOnError(error -> log.error("Failed to start http server at port : " + options.getPort() + " with error " + error.getMessage()))
      .doOnSubscribe(disposable -> handleRequests.subscribe());
  }

  protected Router getRouter(){
    Router router = Router.router(this.vertx);
    router.route().handler(BodyHandler.create());
    router.route().handler(ResponseContentTypeHandler.create());
    router.route().handler(StaticHandler.create());
    router.get("/liveness").handler(ctx -> ctx.response().end("Success"));
    abstractRouteEndPoints.add("/liveness");
    var routes = RestUtil.abstractRouteList(this.packageName);
    log.info("AbstractRoutes : " + routes.size());
    routes.forEach(route -> {
      router
        .routeWithRegex(route.getHttpMethod(), "(?i)" + this.mountPath + route.getPath())
        .consumes(route.getConsumes())
        .produces(route.getProduces())
        .handler(TimeoutHandler.create(route.getTimeout(), 594))
        .handler(route);
      abstractRouteEndPoints.add("(?i)" + this.mountPath + route.getPath());
    });

    return router;
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
