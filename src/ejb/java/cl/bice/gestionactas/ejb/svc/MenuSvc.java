package cl.bice.gestionactas.ejb.svc;

import cl.bice.gestionactas.ejb.vo.MenuVO;

import java.util.List;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface MenuSvc {
    /**
     * Obtiene listado de opciones del menu
     * @param rol Rol del usuario
     * @return Listado de menu
     */
    List<MenuVO> getMenu(String rol);
}
