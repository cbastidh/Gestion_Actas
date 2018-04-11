package cl.bice.gestionactas.spring.interceptor;

import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.factory.WebFactory;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    Logger logger = Logger.getLogger(ControllerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("REQUEST interceptado : " + request.getRequestURI());
        if (handler instanceof HandlerMethod){
            Object handlerMethod =  ((HandlerMethod) handler).getBean();
            for (Field field : handlerMethod.getClass().getDeclaredFields()) {
                WebInjected annotation = field.getAnnotation(WebInjected.class);
                if (annotation != null) {
                    String name = field.getName();
                    logger.info("inject field: " + name);
                    Object bean = WebFactory.getBean(name);
                    field.setAccessible(true);
                    try {
                        field.set(handlerMethod, bean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
