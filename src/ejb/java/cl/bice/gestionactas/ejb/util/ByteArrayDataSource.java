package cl.bice.gestionactas.ejb.util;

import java.io.*;

/**
 * Date: 3/7/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class ByteArrayDataSource implements javax.activation.DataSource {
    String name;
    private byte[] bytes;
    public void setBytes(byte[] bytes) { this.bytes = bytes; }
    public byte[] getBytes() { return bytes; }
    private String contentType;
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentType() { return contentType; }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }
    /** for completeness, here's how to implement the outputstream.
     this is unnecessary for what you're doing, you can just throw
     an UnsupportedOperationException. */
    public OutputStream getOutputStream() {
        final ByteArrayDataSource bads = this;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // return an outputstream that sets my byte array
        // when it is closed.
        return new FilterOutputStream(baos) {
            public void close() throws IOException {
                baos.close();
                bads.setBytes(baos.toByteArray());
            }
        };
    }
}
