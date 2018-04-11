package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.MenuBO;
import cl.neoptera.ejb.dao.GenericDAO;
import cl.neoptera.ejb.dao.GenericOp;
import cl.neoptera.ejb.dao.GenericQuery;
import cl.neoptera.ejb.dao.GenericWhere;

import java.util.List;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface MenuDAO extends GenericDAO<Long, MenuBO> {
    @GenericQuery(query = "select m from MenuBO m where m.status = 1")
    List<MenuBO> getMenu(@GenericWhere(element = "m.privilege", optional = true, op = GenericOp.EQ) String rol);
}

