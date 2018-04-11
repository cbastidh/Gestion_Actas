package cl.bice.gestionactas.ejb.remote.bean;

import cl.bice.gestionactas.ejb.dao.DocumentoDAO;
import cl.bice.gestionactas.ejb.dao.TipoCorporacionDAO;
import cl.bice.gestionactas.ejb.dao.TipoDocumentoDAO;
import cl.bice.gestionactas.ejb.dao.TipoUsuarioDAO;
import cl.bice.gestionactas.ejb.interceptor.EJBInjectorInterceptor;
import cl.bice.gestionactas.ejb.interceptor.Injected;
import cl.bice.gestionactas.ejb.interceptor.WebModuleEntityInterceptor;
import cl.bice.gestionactas.ejb.remote.ParametrosEstaticosEJBRemote;
import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoUsuarioVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.ejb.dao.jpa.ServiceContext;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Stateless(name = "gab_ParametrosEstaticosEJBRemote", mappedName = "gab_ParametrosEstaticosEJBRemote")
@Interceptors({WebModuleEntityInterceptor.class, EJBInjectorInterceptor.class})
public class ParametrosEstaticosEJBRemoteBean implements ParametrosEstaticosEJBRemote {
    @Injected
    TipoDocumentoDAO tipoDocumentoDAO;

    @Injected
    TipoUsuarioDAO tipoUsuarioDAO;

    @Injected
    TipoCorporacionDAO tipoCorporacionDAO;

    @Injected
    DocumentoDAO documentoDAO;

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoDocumentosVisibles(cl.bice.gestionactas.ejb.vo.UserVO, Long idCorporacion)
     */
    @Override
    public List<TipoDocumentoVO> obtenerTipoDocumentosVisibles(UserVO user, Long idCorporacion){
        return tipoDocumentoDAO.obtenerTipoDocumentosVisibles(user.getId(), idCorporacion);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoDocumentos
     */
    @Override
    public List<TipoDocumentoVO> obtenerTipoDocumentos(Long idTipoCorporacion) {
        return tipoDocumentoDAO.obtenerTipoDocumento(true,idTipoCorporacion);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoDocumentos
     */
    @Override
    public List<TipoDocumentoVO> obtenerTipoDocumentos() {
        return tipoDocumentoDAO.obtenerTipoDocumento(true);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoUsuarios
     */
    @Override
    public List<TipoUsuarioVO> obtenerTipoUsuarios(){
        return tipoUsuarioDAO.obtenerTipoUsuario(true);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoCorporaciones
     */
    @Override
    public List<TipoCorporacionVO> obtenerTipoCorporaciones(){
        return tipoCorporacionDAO.obtenerTipoCorporacion(true);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoCorporacionByNmonic(java.lang.String)
     */
    @Override
    public TipoCorporacionVO obtenerTipoCorporacionByNmonic(String nmonic){
        return tipoCorporacionDAO.obtenerTipoCorporacionByNmonic(true, nmonic);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc#obtenerTipoDocumentoByNmonic(java.lang.String)
     */
    public TipoDocumentoVO obtenerTipoDocumentoByNmonic(String nmonic){
        return tipoDocumentoDAO.obtenerTipoDocumentoByNmonic(true, nmonic);
    }

    @Override
    public List<Long> obtenerAnnoPorDocumento(UserVO userVO, Long idCorporacion, Long tipoDocumento) {
        EntityManager entityManager = ServiceContext.current().getEM();
        Query query = entityManager.createNativeQuery("select distinct extract(year from fec_fechacreacion) year from doc_documento " +
                "where doc_tipocorporacion_id=:t and flg_tipodocumento_id=:d and flg_estado=1 order by year desc");
        query.setParameter("t", idCorporacion);
        query.setParameter("d", tipoDocumento);
        List<Long> longs = query.getResultList();
        return longs;
    }

    @Override
    public List<String> obtenerSession(UserVO usuario, Long idCorp, Long idTD, Long year) {
        EntityManager entityManager = ServiceContext.current().getEM();
        Query query = entityManager.createNativeQuery("SELECT NUM_SESION || ' - ' || TO_CHAR(TRUNC(FEC_FECHACREACION), 'dd MON') NUMERO_SESION " +
        " FROM DOC_DOCUMENTO D " +
        " WHERE EXTRACT(YEAR FROM D.FEC_FECHACREACION) = :y " +
        " AND D.DOC_ID IN (SELECT MAX(DOC_ID) "+
        " FROM DOC_DOCUMENTO DOC, UDV_USERDOCUMENTVISIBILITY U "+
        " WHERE DOC.DOC_PARENTDOCUMENT_ID IN(SELECT DOC_PARENTDOCUMENT_ID "+
        " FROM DOC_DOCUMENTO "+
        " WHERE DOC_ID IN (SELECT DOC_PARENTDOCUMENT_ID "+
        " FROM DOC_DOCUMENTO "+
        " GROUP BY DOC_PARENTDOCUMENT_ID) "+
        " AND FLG_ESTADO = 1) "+
        " AND U.UDV_TIPODOCUMENTOBO_ID = DOC.FLG_TIPODOCUMENTO_ID "+
        " AND DOC.DOC_TIPOCORPORACION_ID = :t "+
        " AND DOC.FLG_TIPODOCUMENTO_ID = :d "+
        " GROUP BY DOC.DOC_PARENTDOCUMENT_ID) "+
        " AND D.FLG_ESTADO = 1 "+
        " GROUP BY TRUNC(D.FEC_FECHACREACION), D.NUM_SESION "+
        " ORDER BY TRUNC(D.FEC_FECHACREACION) DESC, D.NUM_SESION DESC ");

        //Query query = entityManager.createNativeQuery("select num_sesion from (select distinct num_sesion || ' - ' || to_char(fec_fechacreacion, 'dd MON') as num_sesion from doc_documento " +
        //       "where doc_tipocorporacion_id=:t and flg_tipodocumento_id=:d and extract(year from fec_fechacreacion) = :y ) order by num_sesion ");
        query.setParameter("t", idCorp);
        query.setParameter("d", idTD);
        query.setParameter("y", year);
        List<String> string = query.getResultList();
        return string;
    }

}
