package cl.bice.gestionactas.test;

import cl.bice.gestionactas.cliente.security.encript.XXTEA;
import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.crypto.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Date: 4/20/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */

public class SendFileTest  {

    private IdcClient client;
    private IdcContext connectionContext;
    static byte[] PASS = "g3st10n4ct4s2016".getBytes();

    @Test
    public void download () throws IdcClientException, IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        createConnection("http://10.1.1.233:16200/cs/idcplg", "weblogic1", "weblogic1");
        downloadFile("4616");
    }

    private void downloadFile(String s) throws IdcClientException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        DataBinder requestData = client.createBinder();
        requestData.putLocal("IdcService", "GET_FILE");
        requestData.putLocal("dID", s);
//        requestData.putLocal("dSecurityGroup", "Public");
        ServiceResponse response = client.sendRequest(connectionContext, requestData);

        int longitud = Integer.parseInt(response.getHeader("Content-Length"));
        if (response.getResponseType() != null) {
            InputStream inputStream = response.getResponseStream();
            byte[] buffer = IOUtils.toByteArray(inputStream);
            byte[] decriptedByte = XXTEA.decrypt(buffer, PASS);
//            byte[] decriptedByte = decript(buffer);
            File f = new File("/home/mwlopez/Desktop/testUCM/Test41.png");
            FileUtils.writeByteArrayToFile(f, decriptedByte);
            if (response != null){
                response.close();
            }
        }
    }


    @Test
    public void upload() throws IdcClientException, IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        createConnection("http://10.1.1.233:16200/cs/idcplg", "weblogic1", "weblogic1");
        uploadFile("TestScreen","Public","/home/mwlopez/Desktop/testUCM/testimage.png");
    }


    public void createConnection(final String connectURL, String username,
                                 String password) throws IdcClientException {
        System.out.println("Connecting to content server at " + connectURL
                + " using username " + username + " and password");
        try {
            IdcClientManager idcClientManager = new IdcClientManager();
            this.client = idcClientManager.createClient(connectURL);
        } catch (IdcClientException e) {
            System.out.println("Error occurred while establishing client");
            throw e;
        }
        this.connectionContext = new IdcContext(username, password);
        System.out.println("Succesfully connected RIDC client to " + connectURL);
    }

    public ServiceResponse uploadFile(String docTitle, String securityGroup,
                                     String filePath) throws IdcClientException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File " + filePath + " does not exist");
            return null;
        }
        DataBinder requestData = client.createBinder();

        byte[] p = FileUtils.readFileToByteArray(file);

        // 1) Empty data
//        System.out.println(RIDCUtils.dataBinderToString(requestData));
//        byte[] encriptedByte = encript(p);
//        InputStream inputStream = new ByteArrayInputStream(encriptedByte);

        byte[] buffer = XXTEA.encrypt(p, PASS);
        InputStream inputStream = new ByteArrayInputStream(buffer);

        requestData.putLocal("IdcService", "CHECKIN_UNIVERSAL");
        requestData.putLocal("dDocType", "Document");
        requestData.putLocal("dDocTitle", docTitle);
        requestData.putLocal("dDocAuthor", connectionContext.getUser());
        requestData.putLocal("dSecurityGroup", securityGroup);
        requestData.addFile("primaryFile", new TransferFile(inputStream, "2te", buffer.length));

        // 2) Service request data
//        System.out.println(RIDCUtils.dataBinderToString(requestData));

        ServiceResponse response = client.sendRequest(connectionContext, requestData);
        DataBinder responseData = response.getResponseAsBinder();

        // 3) Service request response data
//        System.out.println(RIDCUtils.dataBinderToString(responseData));
        System.out.println(responseData.getLocal("dID"));
        return response;
    }


    public byte[] encript(byte[] inputBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key symKey = KeyGenerator.getInstance("DESede").generateKey();
        Cipher c = Cipher.getInstance("DESede");
        c.init(Cipher.ENCRYPT_MODE, symKey);
        return c.doFinal(inputBytes);
    }

    public byte[] decript(byte[] inputBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key symKey = KeyGenerator.getInstance("DESede").generateKey();
        Cipher c = Cipher.getInstance("DESede");
        c.init(Cipher.DECRYPT_MODE, symKey);
        return c.doFinal(inputBytes);
    }
}
