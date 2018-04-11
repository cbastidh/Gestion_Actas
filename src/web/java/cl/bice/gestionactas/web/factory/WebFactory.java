package cl.bice.gestionactas.web.factory;

import cl.neoptera.core.factory.AbstractFactory;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class WebFactory extends AbstractFactory {

    private static final String BASE = "gestionactas-web-context";

    static WebFactory s_instance = new WebFactory(BASE + ".xml", BASE);

    protected WebFactory(String configFileName, String propertiesFileName) {
        super(configFileName, propertiesFileName);
    }

    public static <T> T getBean(Class<T> bean) {
        return s_instance.doGetBean(bean);
    }

    public static <T> T getBean(String bean) {
        return (T) s_instance.doGetBean(bean);
    }
}
