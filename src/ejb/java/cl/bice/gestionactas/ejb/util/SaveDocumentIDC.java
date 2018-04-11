package cl.bice.gestionactas.ejb.util;

import cl.bice.gestionactas.cliente.security.encript.XXTEA;
import cl.bice.gestionactas.ejb.factory.EJBFactory;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * Date: 3/31/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class SaveDocumentIDC implements SaveDocument {
    static Logger logger = Logger.getLogger(SaveDocumentIDC.class.getName());
    static String IDC_PATH = EJBFactory.getProperties("ucm.idc.path");
    static String IDC_PROFILE = EJBFactory.getProperties("ucm.idc.xIdcProfile");
    static String IDC_SECURITYGROUP = EJBFactory.getProperties("ucm.idc.dSecurityGroup");
    static String IDC_DDOCACCOUNT = EJBFactory.getProperties("ucm.idc.dDocAccount");
    static String USER_UCM = new String(Base64.decodeBase64(EJBFactory.getProperties("ucm.username").getBytes()));
    static String PASS_UCM = new String(Base64.decodeBase64(EJBFactory.getProperties("ucm.password").getBytes()));
    static boolean IDC_MANDATORYFIELDS = Boolean.getBoolean(EJBFactory.getProperties("ucm.mandatory.fields"));
    static String IDC_RUTMANDATORY = EJBFactory.getProperties("ucm.mandatory.rut");
    static byte[] PASS = "g3st10n4ct4s2016".getBytes();


    @Override
    public String guardarEnUCM(String username, String fileName, byte[] fileStream, String description) {
        ServiceResponse response = null;
        String docId = null;
        IdcClientManager idcClientManager = null;
        IdcClient idcClient = null;
        IdcContext idcContext = null;
        try {
            idcClientManager = new IdcClientManager();
            idcClient = idcClientManager.createClient(IDC_PATH);
            idcContext = new IdcContext(USER_UCM, PASS_UCM);
            byte[] buffer = XXTEA.encrypt(fileStream, PASS);
            InputStream inputStream = new ByteArrayInputStream(buffer);

            DataBinder requestData = idcClient.createBinder();
            requestData.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            requestData.putLocal("dDocType", "Document");
            //Cambios 27/01/2017
            //requestData.putLocal("dDocTitle", fileName);
            requestData.putLocal("dDocTitle", "BICE ACTAS");
            requestData.putLocal("dOriginalName", fileName);
            requestData.putLocal("xIdcProfile", IDC_PROFILE);

            requestData.putLocal("dDocAuthor", username);
            requestData.putLocal("dSecurityGroup", IDC_SECURITYGROUP);
            if (IDC_MANDATORYFIELDS) {
                requestData.putLocal("xRutSolicitante", IDC_RUTMANDATORY);
                //requestData.putLocal("xIdcProfile", IDC_PROFILE);
            }
            requestData.putLocal("dDocAccount", IDC_DDOCACCOUNT);
            requestData.putLocal("dDocAccount_Options", IDC_DDOCACCOUNT);

            requestData.addFile("primaryFile", new TransferFile(inputStream, "te", buffer.length));
            logger.debug("Enviando el archivo ");
            response = idcClient.sendRequest(idcContext, requestData);
            logger.debug("Retornando el archivo ");
            DataBinder responseData = response.getResponseAsBinder();

            logger.debug("ContentID is " + responseData.getLocal("dDocName"));
            logger.debug("dID is " + responseData.getLocal("dID"));
            docId = responseData.getLocal("dID");
            logger.debug("se a agregado el archivo a UCM via RIDC");
        } catch (IdcClientException e) {
            logger.debug("Error al enviar archivo ---> " + e.getMessage());
        } finally {
            if(response != null){
                response.close();
            }
        }
        if (response == null){
            return null;
        }
        return docId;
    }

    @Override
    public String obtenerToken(String username, String fileName, byte[] fileStream, DocumentoVO documentoVO, String originalDocumentName, String originalDocumentExtension) {
        String documentId = guardarEnUCM(username, fileName, fileStream, documentoVO.getDescripcion());
        return documentId;
    }

    @Override
    public DocumentoVO bajarDocumento(String documentId) {
        ServiceResponse serviceResponse = null;
        DocumentoVO documentoVO = null;
        try {
            IdcClientManager idcClientManager;
            IdcClient idcClient;
            IdcContext idcContext;
            idcClientManager = new IdcClientManager();
            idcClient = idcClientManager.createClient(IDC_PATH);
            idcContext = new IdcContext(USER_UCM, PASS_UCM);
            DataBinder requestDataBinder = idcClient.createBinder();
            requestDataBinder.putLocal("IdcService", "GET_FILE");
            requestDataBinder.putLocal("dID", documentId);

            serviceResponse = idcClient.sendRequest(idcContext, requestDataBinder);

            int longitud = Integer.parseInt(serviceResponse.getHeader("Content-Length"));
            if (serviceResponse.getResponseType() != null){
                InputStream outInput = serviceResponse.getResponseStream();
                byte[] buffer = IOUtils.toByteArray(outInput);
                byte[] decriptedByte = XXTEA.decrypt(buffer, PASS);
                serviceResponse = idcClient.sendRequest(idcContext, requestDataBinder);
                documentoVO = new DocumentoVO(decriptedByte, "tempName");
            }
        } catch (IOException e) {
            logger.debug("error al tratar de convertir inputstream a byte " + e.getMessage());
        } catch (IdcClientException e) {
            logger.debug("Error idc " + e.getMessage());
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }
        return documentoVO;
    }

    @Override
    public String crearDocumento(String token, String appName, DocumentoVO documentoVO) {
        return null;
    }


    public static byte[] getBytesFromInputStream(InputStream is) throws IOException
    {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
        {
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);

            os.flush();

            return os.toByteArray();
        }
    }


    public byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
        }
        return buf;
    }
}
