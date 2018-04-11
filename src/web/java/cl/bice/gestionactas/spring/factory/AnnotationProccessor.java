package cl.bice.gestionactas.spring.factory;


import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 27-11-14.
 * Time: 17:21
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: Gestion Actas
 */
public class AnnotationProccessor<T> {

    public static <T> FactoryProccessor processor(Class<T> clazz){
        Map<Class, FactoryProccessor> factoryProcessorMap = new HashMap<Class, FactoryProccessor>();
        factoryProcessorMap.put(ContextoWeb.class, new ContextoProccessor());
        factoryProcessorMap.put(WebInjected.class, new ServiceProccessor());
        FactoryProccessor factoryProcessor = factoryProcessorMap.get(clazz);
        return factoryProcessor;
    }
}
