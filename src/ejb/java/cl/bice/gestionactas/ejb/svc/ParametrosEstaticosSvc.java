package cl.bice.gestionactas.ejb.svc;

import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoUsuarioVO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import java.util.List;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 *
 * Descripcion:
 *    Servicio que se utiliza para obtener los parametros generales en las pantallas, como por ejemplo
 *    tipo de documentos (actas), corporacion u empresas.
 */
public interface ParametrosEstaticosSvc  {
    /**
     * Obtiene la lista de tipos de documentos.
     * @return TipoDocumentosVO
     */
    public List<TipoDocumentoVO> obtenerTipoDocumentos(Long idTipoCorporacion);

    public List<TipoDocumentoVO> obtenerTipoDocumentos();


    public List<TipoDocumentoVO> obtenerTipoDocumentosVisibles(UserVO user, Long idCorporacion);

    public List<Long> obtenerAnnoPorDocumento(UserVO userVO, Long idCorporacion, Long tipoDocumento);

    /**
     * Obtiene la lista de tipos de usuarios.
     * @return TipoUsuarioVO
     */
    public List<TipoUsuarioVO> obtenerTipoUsuarios();

    /**
     * Obtiene la lista de tipos de usuarios.
     * @return TipoUsuarioVO
     */
    public List<TipoCorporacionVO> obtenerTipoCorporaciones();

    /**
     * Obtiene el tipo de corporacion a traves de su <b>nmonic</b>
     * @param nmonic String con el nmonic a ser buscado en la base de datos
     * @return devuelve el tipo de Corporacion
     */
    public TipoCorporacionVO obtenerTipoCorporacionByNmonic(String nmonic);

    /**
     * Obtiene el tipo de documento a traves de su nmonic
     * @param nmonic NMonic seleccionado por usuario en la capa de presentacion
     * @return TipoDocumentoVO que contiene obtenido de la consulta del nmonic
     */
    public TipoDocumentoVO obtenerTipoDocumentoByNmonic(String nmonic);

    public List<String> obtenerSession(UserVO usuario, Long idCorp, Long idTD, Long year);
}
