package com.vertx.starter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaustubh.vertx.commons.entity.VertxEntity;
import com.kaustubh.vertx.commons.guice.GuiceContext;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.*;

@Slf4j
public class RestUtil {
  private static final List<Class> collectionClasses = Arrays.asList(
    List.class,
    Set.class,
    ArrayList.class,
    HashSet.class,
    LinkedHashSet.class,
    LinkedList.class
  );

  private static Reflections ref;

  public static List<Class<?>> annotatedClasses(String packageName, Class<? extends Annotation> annotation) {
    List<Class<?>> annotatedClasses = new ArrayList<>();
    try {
      setRef(packageName);
      annotatedClasses = new ArrayList<>(ref.getTypesAnnotatedWith(annotation));
    } catch (Exception e) {
      log.error("Failed to get classes with annotation {}", annotation, e);
    }
    return annotatedClasses;
  }

  private static synchronized void setRef(String packageName) {
    if (ref == null) {
      ref = new Reflections(packageName);
    }
  }

  public static List<AbstractRoute> abstractRouteList(String packageName) {
    List<AbstractRoute> routes = new ArrayList<>();
    List<Class<?>> classes = RestUtil.annotatedClasses(packageName, Route.class);
    if (classes != null && !classes.isEmpty()) {
      classes.forEach(clazz -> {
        try {
          Route routeAnnotation = clazz.getAnnotation(Route.class);
          if(routeAnnotation != null){
            AbstractRoute route = (AbstractRoute) GuiceContext.getInstance(clazz);
            route.setPath(routeAnnotation.path());
            route.setHttpMethod(HttpMethod.valueOf(routeAnnotation.httpMethod().toString()));
            route.setProduces(routeAnnotation.produces());
            route.setConsumes(routeAnnotation.consumes());
            route.setRequiredHeaders(Arrays.asList(routeAnnotation.requiredHeaders()));
            route.setRequiredBodyParams(Arrays.asList(routeAnnotation.requiredBodyParams()));
            route.setRequiredQueryParams(Arrays.asList(routeAnnotation.requiredQueryParams()));
            route.setTimeout(routeAnnotation.timeout());
            routes.add(route);
          }
        } catch (Exception e) {
          log.error("Failed to initialize route", e);
        }
      });
    }
    return routes;
  }


  public static String getString(Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = GuiceContext.getInstance(ObjectMapper.class);
    String str;
    if(object instanceof String){
      str = (String)object;
    }else if(object instanceof JsonObject){
      str = String.valueOf(object);
    }else if (collectionClasses.contains(object.getClass())) {
      str = new JsonArray(new ArrayList((Collection) object)).toString();
    } else if (object instanceof VertxEntity) {
      str = ((VertxEntity) object).toJson().toString();
    } else {
      str = objectMapper.writeValueAsString(object);
    }
    return str;
  }


}
