package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Date: 2/10/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class DocumentoVO implements Serializable {
    byte[] content;
    String name;
    private Long id;
    private Boolean estado;
    private TipoCorporacionVO tipoCorporacion;
    private TipoDocumentoVO tipoDocumento;
    private String titulo;
    private String descripcion;
    private Date fechaCarga;
    private Date fechaCreacion;
    private String tokenDocumento;
    private String originalDocumentBaseName;
    private String originalDocumentExtension;
    private DocumentoVO parentDocument;
    private Integer version;
    private Long numSesion;

    public String getOriginalDocumentBaseName() {
        return originalDocumentBaseName;
    }

    public void setOriginalDocumentBaseName(String originalDocumentBaseName) {
        this.originalDocumentBaseName = originalDocumentBaseName;
    }

    public String getOriginalDocumentExtension() {
        return originalDocumentExtension;
    }

    public void setOriginalDocumentExtension(String originalDocumentExtension) {
        this.originalDocumentExtension = originalDocumentExtension;
    }

    public DocumentoVO() {
    }

    public DocumentoVO(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }

    public DocumentoVO(Long id, Boolean estado, TipoCorporacionVO tipoCorporacion,
                       TipoDocumentoVO tipoDocumento,
                       String titulo, String descripcion, Date fechaCarga,
                       Date fechaCreacion, String tokenDocumento,
                       String originalDocumentBaseName, String originalDocumentExtension, Integer version,
                       DocumentoVO parentDocument, Long numSesion) {
        this.id = id;
        this.estado = estado;
        this.tipoCorporacion = tipoCorporacion;
        this.tipoDocumento = tipoDocumento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCarga = fechaCarga;
        this.fechaCreacion = fechaCreacion;
        this.tokenDocumento = tokenDocumento;
        this.originalDocumentBaseName = originalDocumentBaseName;
        this.originalDocumentExtension = originalDocumentExtension;
        this.version = version;
        this.parentDocument = parentDocument != null ? parentDocument : this;
        this.numSesion = numSesion;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenDocumento() {
        if (tokenDocumento == null) {
            tokenDocumento = "-1";
        }
        return tokenDocumento;
    }

    public void setTokenDocumento(String tokenDocumento) {
        this.tokenDocumento = tokenDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoCorporacionVO getTipoCorporacion() {
        return tipoCorporacion;
    }

    public void setTipoCorporacion(TipoCorporacionVO tipoCorporacion) {
        this.tipoCorporacion = tipoCorporacion;
    }

    public TipoDocumentoVO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoVO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getOriginalFileName(){
        return this.originalDocumentBaseName + "." + this.originalDocumentExtension;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public DocumentoVO getParentDocument() {
        return parentDocument;
    }

    public void setParentDocument(DocumentoVO parentDocument) {
        this.parentDocument = parentDocument;
    }

    public Long getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(Long numSesion) {
        this.numSesion = numSesion;
    }

    @Override
    public String toString() {
        return "DocumentoVO{" +
                "content=" + Arrays.toString(content) +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", estado=" + estado +
                ", tipoCorporacion=" + tipoCorporacion +
                ", tipoDocumento=" + tipoDocumento +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", fechaCreacion=" + fechaCreacion +
                ", tokenDocumento='" + tokenDocumento + '\'' +
                ", originalDocumentBaseName='" + originalDocumentBaseName + '\'' +
                ", originalDocumentExtension='" + originalDocumentExtension + '\'' +
                ", parentDocument=" + parentDocument +
                ", version=" + version +
                ", numSesion=" + numSesion +
                '}';
    }
}
