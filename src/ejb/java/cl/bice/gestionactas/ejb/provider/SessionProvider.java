package cl.bice.gestionactas.ejb.provider;

import cl.bice.gestionactas.ejb.factory.EJBFactory;
import cl.neoptera.core.factory.AbstractFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class SessionProvider {
    private static ThreadLocal<SessionProvider> provider = new ThreadLocal<SessionProvider>();

    /**
     * campo para indicar otro factory a usar (se usa para tests)
     */
    static AbstractFactory __factory;

    private AbstractFactory factory;

    private Map<Class, Object> beans = new HashMap<Class, Object>();

    public static SessionProvider instance() {

        SessionProvider sessionProvider = provider.get();
        if (sessionProvider == null) {
            sessionProvider = new SessionProvider();
            provider.set(sessionProvider);
        }
        return sessionProvider;
    }

    public SessionProvider() {
        factory = __factory;
        if (factory == null) {
            factory = EJBFactory.instance();
        }
    }

    public <T> T getBean(Class<T> clazz) {

        T t = (T) beans.get(clazz);
        if (t == null) {
            t = factory.doGetBean(clazz);
            beans.put(clazz, t);
        }
        return t;
    }
}