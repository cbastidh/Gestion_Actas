package cl.bice.gestionactas.spring.factory;

import javax.servlet.http.HttpSession;

/**
 * Date: 01-12-14.
 * Time: 14:56
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: ncloud
 */
public class ServiceProccessor implements FactoryProccessor<Object> {
    public ServiceProccessor() {
    }

    @Override
    public Object process(HttpSession session) {
        return null;
    }
}
