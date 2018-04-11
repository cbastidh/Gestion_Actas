package cl.bice.gestionactas.web.springmvc.acts.viewvo;

import cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Date: 2/10/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class ActaViewVO implements Serializable {
    String parentDocumentId;
    String documentId;
    String titulo;
    String corporacion;
    String tipoDocumento;
    String periodo;
    String tokenDocumento;
    Integer version;
    String descripcion;
    String numSesion;

    Date fechaCarga;
    String fechaCreacion;

    public ActaViewVO() {
    }

    public ActaViewVO(String titulo, String corporacion, String tipoDocumento, String descripcion, String periodo,Integer version, String numSesion) {
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.corporacion = corporacion;
        this.tipoDocumento = tipoDocumento;
        this.periodo = periodo;
        this.version = version;
        this.numSesion = numSesion;
    }


    public ActaViewVO(DocumentoVO documentoVO){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (documentoVO.getFechaCreacion() != null) {
            this.fechaCreacion = df.format(documentoVO.getFechaCreacion());
        }else{
            this.fechaCreacion = df.format(Calendar.getInstance().getTime());
        }
        this.corporacion = documentoVO.getTipoCorporacion().getTipoCorporacion();
        this.fechaCarga = documentoVO.getFechaCarga();
        this.tipoDocumento = documentoVO.getTipoDocumento().getTipoActa();
        this.titulo = documentoVO.getTitulo();
        this.tokenDocumento = documentoVO.getTokenDocumento();
        this.descripcion = documentoVO.getDescripcion();
        this.version = documentoVO.getVersion();
        this.numSesion = String.valueOf(documentoVO.getNumSesion());
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorporacion() {
        return corporacion;
    }

    public void setCorporacion(String corporacion) {
        this.corporacion = corporacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTokenDocumento() {
        return tokenDocumento;
    }

    public void setTokenDocumento(String tokenDocumento) {
        this.tokenDocumento = tokenDocumento;
    }

    public String getParentDocumentId() {
        return parentDocumentId;
    }

    public void setParentDocumentId(String parentDocumentId) {
        this.parentDocumentId = parentDocumentId;
    }

    public String getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(String numSesion) {
        this.numSesion = numSesion;
    }

    public DocumentoVO toVO(Boolean isFindTipo, ParametrosEstaticosSvc parametrosEstaticosSvc){
        DocumentoVO newVO = new DocumentoVO();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        newVO.setDescripcion(this.getDescripcion());
        newVO.setTitulo(this.getTitulo());
        newVO.setFechaCarga(new Date());
        newVO.setVersion(this.getVersion());
        newVO.setNumSesion(Long.decode(this.getNumSesion()));

        if (isFindTipo) {
            TipoCorporacionVO tipoCorporacionVO = parametrosEstaticosSvc.obtenerTipoCorporacionByNmonic(this.getCorporacion());
            TipoDocumentoVO tipoDocumentoVO = parametrosEstaticosSvc.obtenerTipoDocumentoByNmonic(this.getTipoDocumento());
            newVO.setTipoDocumento(tipoDocumentoVO);
            newVO.setTipoCorporacion(tipoCorporacionVO);
        }

        try {
            newVO.setFechaCreacion(df.parse(this.getFechaCreacion()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newVO;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "ActaViewVO{" +
                "parentDocumentId='" + parentDocumentId + '\'' +
                ", titulo='" + titulo + '\'' +
                ", corporacion='" + corporacion + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", periodo='" + periodo + '\'' +
                ", tokenDocumento='" + tokenDocumento + '\'' +
                ", version=" + version +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", fechaCreacion=" + fechaCreacion +
                ", sesion=" + numSesion +
                '}';
    }
}
