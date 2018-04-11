package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.TipoUsuarioBO;
import cl.bice.gestionactas.ejb.vo.TipoUsuarioVO;
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
public interface TipoUsuarioDAO extends GenericDAO<Long, TipoUsuarioBO>  {
    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoUsuarioVO(d.id, d.tipoUsuario, d.nmonic) from TipoUsuarioBO d order by d.tipoUsuario")
    public List<TipoUsuarioVO> obtenerTipoUsuario(@GenericWhere(element = "d.estado") Boolean estado);
}
