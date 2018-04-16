package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.UserDocumentVisibilityBO;
import cl.neoptera.ejb.dao.GenericDAO;
import cl.neoptera.ejb.dao.GenericQuery;
import cl.neoptera.ejb.dao.GenericWhere;

import java.util.List;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public interface UserDocumentVisibilityDAO extends GenericDAO<Long, UserDocumentVisibilityBO> {

    @GenericQuery(query = "select vd from UserDocumentVisibilityBO vd")
    List<UserDocumentVisibilityBO> listarVisibilidadDocumentos(@GenericWhere(element = "vd.estado") Boolean estado,
                                                               @GenericWhere(element = "vd.userBO.id") Long userId);
}
