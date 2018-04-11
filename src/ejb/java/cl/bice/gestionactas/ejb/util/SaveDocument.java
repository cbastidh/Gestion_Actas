package cl.bice.gestionactas.ejb.util;

import cl.bice.gestionactas.ejb.vo.DocumentoVO;

/**
 * Date: 3/31/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public interface SaveDocument {
    public String guardarEnUCM(String userName, String fileName, byte[] fileStream, String description);
    public String obtenerToken(String userName, String fileName, byte[] fileStream, DocumentoVO documentoVO, String originalDocumentName,
                               String originalDocumentExtension);
    public DocumentoVO bajarDocumento(String documentId);
    public String crearDocumento(String token, String appName, DocumentoVO documentoVO);
}
