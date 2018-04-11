package cl.bice.gestionactas.ejb.factory;

import cl.neoptera.core.factory.AbstractFactory;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class EJBFactory extends AbstractFactory {

    private static final String BASE = "gestionactas-ejb-context";

    static EJBFactory s_instance = new EJBFactory(BASE + ".xml", BASE);

    protected EJBFactory(String configFileName, String propertiesFileName) {
        super(configFileName, propertiesFileName);
    }

    public static EJBFactory instance() {
        return s_instance;
    }

    public static <T> T getBean(Class<T> bean) {
        return s_instance.doGetBean(bean);
    }

    public static <T> T getBean(String bean) {
        return (T) s_instance.doGetBean(bean);
    }

    public static String getProperties(String propertyName){
        PropertiesUtil propertiesUtil = (PropertiesUtil) s_instance.doGetBean(PropertiesUtil.class);
        return propertiesUtil.getProperty(propertyName);
    }
}
