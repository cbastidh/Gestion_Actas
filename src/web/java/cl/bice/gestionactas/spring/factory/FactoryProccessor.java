package cl.bice.gestionactas.spring.factory;

import javax.servlet.http.HttpSession;

/**
 * Date: 27-11-14.
 * Time: 17:23
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: ncloud
 */
public interface FactoryProccessor<T> {
    public T process(HttpSession session);
}
