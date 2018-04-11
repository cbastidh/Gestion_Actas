package cl.bice.gestionactas.ejb.remote;

import cl.bice.gestionactas.ejb.svc.UserSvc;

import javax.ejb.Remote;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Remote
public interface UserEJBRemote extends UserSvc {
}
