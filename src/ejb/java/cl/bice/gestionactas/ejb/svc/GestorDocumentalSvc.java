package cl.bice.gestionactas.ejb.svc;

import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;

import java.util.List;

/**
 * Date: 2/10/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface GestorDocumentalSvc {
    /**
     * Inserta el documento y obtiene el token del archvio insertado
     * @param fileName nombre del archivo
     * @param fileStream stream del archivo
     * @param originalDocumentName
     * @param originalDocumentExtension
     * @return token del archivo
     */
    String obtenerToken(String userName, String fileName, byte[] fileStream, DocumentoVO documentoVO, String originalDocumentName, String originalDocumentExtension);


    /**
     * Relaciona el token con la aplicacion
     * @param token Token generado al momento de hacer el
     * @param appName
     * @param documentoVO
     */
    String crearDocumento(String token, String appName, DocumentoVO documentoVO);

    /**
     * Se utiliza para bajar el documento desde el UCM
     * @param documentId ID del documento
     * @return devuelve un documentContent
     */
    DocumentoVO bajarDocumento(String documentId);

    /**
     * Se utiliza para obtener documentos desde BDD Soporte
     * @param documentId id del documento en UCM
     * @return devuelve un lista de documentos
     */
    DocumentoVO obtenerDocumentoByDocumentId(Long documentId);


    /**
     * Obtiene documentos utilizando criterios de búsqueda
     * @param query Parametros para la busqueda
     * @return lista de documentos que cumplen con el criterio
     */
    PageVO<DocumentoVO> obtenerDocumentos(PageQueryMapVO query);

    /**
     * Se utiliza para obtener documentos desde BDD Soporte
     * @param user usuario utilizado para filtrar documentos
     * @return devuelve un lista de documentos
     */
    public List<DocumentoVO> obtenerDocumentosRecientes(UserVO user);

    /**
     * Determina la existencia del documento en base a los parametros
     * @param documentoVO parametros para buscar si el doc existe.
     * @param parentDocument
     * @return
     */
    int existDocument(DocumentoVO documentoVO, DocumentoVO parentDocument);

    /**
     * Crea nueva vesion del documento ya existente
     * @param fileName nombre archivo nuevo
     * @param fileStream byteStream con archivo
     * @param documentoVO Value Object con los datos a actualizar
     * @param originalDocumentName nombre base del archivo nuevo
     * @param originalDocumentExtension extensión del archivo nuevo
     * @return nuevo Token.
     */
    String actualizarDocumento(String userName, String fileName, byte[] fileStream, DocumentoVO documentoVO,
                               String originalDocumentName, String originalDocumentExtension);

    /**
     * Obtiene el documento por el id ingresado por el dato de presentacion
     * @param id Identificador del documento
     * @return devuelve un objeto de presentacion con el documento solicitado
     */
    public DocumentoVO obtenerDocumentoById(Long id);

    /**
     * Obtiene el listo de versiones (documentos) de acuerdo al documento padre y al usuario (se determina si tiene o no
     * permisos para poder ver ese documento
     * @param user Usuario que esta visualizando el documento
     * @param document documento original
     * @return Listado de documentos basados en el original.
     */
    List<DocumentoVO> obtenerListadoVersiones(UserVO user, DocumentoVO document);
    
    /**
     * Elimina documento logicamente cambiando el estado en la base de datos
     * @param document documento original
     */
	String eliminarDocumento(DocumentoVO documentoVO);
}
