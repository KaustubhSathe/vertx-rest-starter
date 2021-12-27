package com.vertx.starter;

import io.vertx.rxjava3.ext.web.Route;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RestUtil {
  private static Reflections ref;

  public static List<Class<?>> annotatedClasses(String packageName, Class<? extends Annotation> annotation){
    List<Class<?>> annotatedClasses = new ArrayList<>();
    try{
      setRef(packageName);
      annotatedClasses = new ArrayList<>(ref.getTypesAnnotatedWith(annotation));
    }catch (Exception e){
      log.error("Failed to get classes with annotation {}", annotation, e);
    }
    return annotatedClasses;
  }

  private static synchronized void setRef(String packageName){
    if(ref == null){
      ref = new Reflections();
    }
  }

  public static List<AbstractRoute> abstractRouteList(String packageName){
    List<AbstractRoute> routes = new ArrayList<>();
    List<Class<?>> classes = RestUtil.annotatedClasses(packageName, Route.class);

  }


}
