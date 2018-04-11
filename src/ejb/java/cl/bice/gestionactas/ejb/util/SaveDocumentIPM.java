package cl.bice.gestionactas.ejb.util;

import cl.bice.gestionactas.cliente.security.encript.TEA;
import cl.bice.gestionactas.ejb.factory.EJBFactory;
import cl.bice.gestionactas.ejb.type.UCMFieldEnum;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import oracle.imaging.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.xml.ws.BindingProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Date: 3/31/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class SaveDocumentIPM implements SaveDocument {
    transient static Logger logger = Logger.getLogger(SaveDocumentIPM.class);

    private static String UCMUSER = new String(Base64.decodeBase64(EJBFactory.getProperties("ucm.username").getBytes()));
    private static String UCMPASS = new String(Base64.decodeBase64(EJBFactory.getProperties("ucm.password").getBytes()));
    private static String UCMURL = EJBFactory.getProperties("ucm.url");
    static byte[] PASS = "g3st10n4ct4s2016".getBytes();

    /**
     * Permite guardar el archivo en el UCM
     * @param fileName Nombre del archivo original
     * @param fileStream bytes del archivo
     * @param description Descripcion generada por el usuario en la capa de presentacion
     * @return Token generado en UCM y que es almacenado en la base de datos de la aplicacion
     */
    public String guardarEnUCM(String userName, String fileName, byte[] fileStream, String description){
        logger.debug("Llamando al obtener token desde el ejb");
        String token = null;
        String documentId = null;
        try {
            logger.debug("Seteando la politicas");
            ServicesFactory servicesFactory = getUCMServiceFactory();
            logger.debug("agregando la politica 3");
            oracle.imaging.DocumentContentService documentContentService = servicesFactory.getDocumentContentService();
            oracle.imaging.DocumentService docService = servicesFactory.getDocumentService();
            logger.debug("Conectado");
            byte[] b = new byte[fileStream.length];
            logger.debug("fileStream " + fileStream.length);

            TEA tea = new TEA(PASS);
            byte[] encriptedByte = tea.encrypt(fileStream);

            InputStream is = new ByteArrayInputStream(encriptedByte);
            is.read(b);
            DataHandler dataHandler = new DataHandler(new InputStreamDataSource(is));

            logger.debug("Inicio ---> nuevo metodo");
            ByteArrayDataSource ds = new ByteArrayDataSource();
            ds.setContentType("*/*");
            ds.setBytes(fileStream);
            ds.setName(fileName);
            DataHandler dh = new DataHandler(ds);

            logger.debug("preparando archivo para subir");
            Map requestContext = ((BindingProvider)documentContentService).getRequestContext();
            requestContext.put("javax.xml.ws.security.auth.username", UCMUSER);
            requestContext.put("javax.xml.ws.security.auth.password", UCMPASS);
            token = documentContentService.uploadDocument(dh, fileName);

            List<Document.FieldValue> fieldValues = new ArrayList<Document.FieldValue>();
            fieldValues.add(new Document.FieldValue(UCMFieldEnum.NAME.getUCMFieldName(), "BICE"+token));

            fieldValues.add(new Document.FieldValue(UCMFieldEnum.DESCRIPTION.getUCMFieldName(), description));
            List<NameId> nameIds = docService.listTargetApplications(Document.Ability.CREATEDOCUMENT);
            NameId appNameId = null;
            for (NameId nameId : nameIds) {
                if (nameId.getName().equalsIgnoreCase("BICE")) {
                    appNameId = nameId;
                    break;
                }
            }
            documentId = docService.createDocument(token, appNameId, fieldValues, true);
        } catch (ImagingException e) {
            logger.error("ImagingException", e);
            token = null;
        } catch (IOException e) {
            logger.error("IOException", e);
            token = null;
        }
        logger.debug("Archivo con token --->  " + token);
        logger.debug("Archivo con documentId --->  " + documentId);
        return documentId;
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerToken(java.lang.String, java.lang.String,byte[] ,cl.bice.gestionactas.ejb.vo.DocumentoVO, java.lang.String,
     * java.lang.String)
     */
    @Override
    public String obtenerToken(String userName, String fileName, byte[] fileStream, DocumentoVO documentoVO, String originalDocumentName,
                               String originalDocumentExtension) {
        String documentId = guardarEnUCM(userName, fileName, fileStream, documentoVO.getDescripcion());
        return documentId;
    }

    /**
     * Trunca la descripcion de un documento, ya que en UCM solo soporta 200 caracteres como maximo
     * @param documentoVO Documento con los datos a ser truncados
     * @return String truncado con la descripcion
     */
    private String truncateDescription(DocumentoVO documentoVO) {
        String description = documentoVO.getDescripcion();
        if (description == null){
            return "";
        }
        description = (description.length() > 200 ? description.substring(0, 199) : description);
        return description;
    }

    /**
     * Obtiene el factory para la conexion con UCM
     * @return ServiceFactory del ucm
     * @throws ImagingException si no puede logear devuele una excepcion que se debe manejar en el objeto superior
     */
    private ServicesFactory getUCMServiceFactory() throws ImagingException {
        UserToken credentials = new BasicUserToken(UCMUSER, UCMPASS);
        return ServicesFactory.login(credentials, Locale.US, UCMURL+"/imaging/ws");
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#crearDocumento(java.lang.String,java.lang.String,cl.bice.gestionactas.ejb.vo.DocumentoVO)
     */
    @Override
    public String crearDocumento(String token, String appName, DocumentoVO documentoVO) {
        String documentId = null;
        try {
            ServicesFactory servicesFactory = getUCMServiceFactory();
            oracle.imaging.DocumentService docService = servicesFactory.getDocumentService();
            List<Document.FieldValue> fieldValues = new ArrayList<Document.FieldValue>();
            List<NameId> nameIds = docService.listTargetApplications(Document.Ability.CREATEDOCUMENT);
            NameId appNameId = null;
            for (NameId nameId : nameIds) {
                if (nameId.getName().equalsIgnoreCase(appName)) {
                    appNameId = nameId;
                    break;
                }
            }
            if (appNameId == null) {
                throw new Exception("no se encontro la aplicacion BICECorp");
            }
            logger.debug("token recibido --> " + token);
            logger.debug("App recibida   --> " + appNameId.getName());

            String description = truncateDescription(documentoVO);
            fieldValues.add(new Document.FieldValue(UCMFieldEnum.NAME.getUCMFieldName(), "BICE"+token));
            fieldValues.add(new Document.FieldValue(UCMFieldEnum.DESCRIPTION.getUCMFieldName(), description));
            documentId = docService.createDocument(token, appNameId, fieldValues, true);
        }catch (ImagingException e) {
            logger.error("ImagingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return documentId;
    }


    /**
     * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#bajarDocumento(java.lang.String)
     */
    @Override
    public DocumentoVO bajarDocumento(String documentId) {
        DocumentoVO documentoUCM = null;
        try {
            ServicesFactory servicesFactory = getUCMServiceFactory();
            logger.debug("Conectando al ws");
            DocumentContentService documentContentService = servicesFactory.getDocumentContentService();
            logger.debug("Obteniendo el factory");
            Rendition rendition = documentContentService.retrieveRendition(documentId, true, true, RenderOptions.RenditionOutput.ORIGINALORTIFF, null);
            if (rendition == null){
                logger.error("No se ha encontrado el documento solicitado");
            }else{
                TEA tea = new TEA(PASS);
                byte[] decriptedByte = tea.decrypt(getContentAsByteArray(rendition.getContent()));
                documentoUCM = new DocumentoVO(decriptedByte, rendition.getOriginalFilename());
            }
        }catch (ImagingException e) {
            logger.error("ImagingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return documentoUCM;
    }

    /**
     * Convierte de un DataHandler (que es manejado por UCM) a un conjunto de byte
     * @param handler Handler que viene desde UCM
     * @return arreglo de bytes con el archivo obtenido en UCM
     * @throws IOException Se debe manejar a nivel superior.
     */
    public static byte[] getContentAsByteArray(DataHandler handler) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        handler.writeTo(bos);
        bos.flush();
        bos.close();
        bytes = bos.toByteArray();
        return bytes;
    }

}
