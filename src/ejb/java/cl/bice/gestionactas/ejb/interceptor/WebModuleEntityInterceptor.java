package cl.bice.gestionactas.ejb.interceptor;

import cl.neoptera.ejb.dao.jpa.ServiceContext;
import cl.neoptera.ejb.interceptor.AbtractEntityInterceptor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class WebModuleEntityInterceptor extends AbtractEntityInterceptor {

    @PersistenceContext(unitName = "gaPU")
    EntityManager em;

    @Override
    protected EntityManager getEM() {
        return em;
    }

    @Override
    protected ServiceContext newServiceContext() {
        return new ServiceContext();
    }

}
