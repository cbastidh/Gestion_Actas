package cl.bice.gestionactas.ejb.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class EventAdviceAfter implements AfterReturningAdvice {

    private static Logger logger = LoggerFactory.getLogger(EventAdviceAfter.class);

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        String className = target.getClass().getName();
        String methodName = method.getName();
        try{
            for (Object obj : args){
                logger.debug("Objecto", obj);
            }
        }catch (Exception e){
            logger.error("se produjo una excepcion ---> " + e.getMessage());
        }
        logger.debug("Returning from method "+methodName+" of class "+className);
    }

}
