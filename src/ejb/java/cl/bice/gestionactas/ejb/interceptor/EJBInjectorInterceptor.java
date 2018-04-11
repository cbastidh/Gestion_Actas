package cl.bice.gestionactas.ejb.interceptor;

import cl.bice.gestionactas.ejb.factory.EJBFactory;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Field;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class EJBInjectorInterceptor {
    private static Logger logger = Logger.getLogger(EJBInjectorInterceptor.class);

    @PostConstruct
    public void inject(InvocationContext ctx) {
        Object target = ctx.getTarget();
        logger.info("inject target 1: " + target);
        logger.info("inject target 2: " + target.getClass().getSuperclass().getName());
        for (Field field : target.getClass().getSuperclass().getDeclaredFields()) {
            Injected annotation = field.getAnnotation(Injected.class);
            if (annotation != null) {
                String name = field.getName();
                logger.info("inject field: " + name);
                Object bean = EJBFactory.getBean(name);
                field.setAccessible(true);
                try {
                    field.set(target, bean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
