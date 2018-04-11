package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.TipoCorporacionBO;
import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
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
public interface TipoCorporacionDAO extends GenericDAO<Long, TipoCorporacionBO>  {
    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoCorporacionVO(d.id, d.tipoCorporacion, d.nmonic)" +
            " from TipoCorporacionBO d order by d.tipoCorporacion")
    public List<TipoCorporacionVO> obtenerTipoCorporacion(@GenericWhere(element = "d.estado") Boolean estado);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoCorporacionVO(d.id, d.tipoCorporacion, d.nmonic)" +
            " from TipoCorporacionBO d ")
    public TipoCorporacionVO obtenerTipoCorporacionByNmonic(@GenericWhere(element = "d.estado") Boolean estado,
                                                            @GenericWhere(element = "d.nmonic") String nmonic);
}
