package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.TipoDocumentoBO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.neoptera.ejb.dao.GenericDAO;
import cl.neoptera.ejb.dao.GenericQuery;
import cl.neoptera.ejb.dao.GenericWhere;

import java.util.List;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface TipoDocumentoDAO extends GenericDAO<Long, TipoDocumentoBO> {


    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoDocumentoVO(d.id, d.tipoActa, d.nmonic, d.tipoCorporacion.tipoCorporacion) " +
            "from TipoDocumentoBO d order by d.tipoActa")
    public List<TipoDocumentoVO> obtenerTipoDocumento(@GenericWhere(element = "d.estado") Boolean estado);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoDocumentoVO(d.id, d.tipoActa, d.nmonic) " +
            "from TipoDocumentoBO d where d.tipoCorporacion.id = :id order by d.tipoActa")
    public List<TipoDocumentoVO> obtenerTipoDocumento(@GenericWhere(element = "d.estado") Boolean estado,
                                                      @GenericWhere(element = ":id")Long id);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoDocumentoVO(d.id, d.tipoActa, d.nmonic) " +
            "from TipoDocumentoBO d")
    public TipoDocumentoVO obtenerTipoDocumentoByNmonic(@GenericWhere(element = "d.estado") Boolean estado,
                                                        @GenericWhere(element = "d.nmonic") String nmonic);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.TipoDocumentoVO(td.id, td.tipoActa, td.nmonic) " +
            "from TipoDocumentoBO td, UserDocumentVisibilityBO vis where td.id = vis.tipoDocumentoBO.id" +
            " and vis.userBO.id = :userId and td.tipoCorporacion.id = :idCorporacion order by td.tipoActa")
    		//" and vis.userBO.id = :userId and td.tipoCorporacion.id = :idCorporacion")

	public List<TipoDocumentoVO> obtenerTipoDocumentosVisibles(@GenericWhere(element = ":userId") Long id,
                                                                @GenericWhere(element = ":idCorporacion") Long idCorporacion);

}
