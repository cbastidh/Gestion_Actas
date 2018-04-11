package cl.bice.gestionactas.web.springmvc.acts.viewvo;

import cl.bice.gestionactas.web.springmvc.common.SearchCriteria;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date: 2/18/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class ActasSearchCriteria  implements SearchCriteria, Serializable {
    String titulo;
    String autor;
    String tipoAutor;
    String corporacion;
    String tipoDocumento;
    String periodo;
    String contenido;
    String descripcion;
    String anoInicial;
    String mesInicial;
    String anoFinal;
    String mesFinal;
    String anno;
    String sesion;


    public ActasSearchCriteria() {
    }

    public ActasSearchCriteria(String titulo, String autor, String corporacion, String tipoDocumento, String periodo, String contenido, String descripcion, String sesion) {
        this.titulo = titulo;
        this.autor = autor;
        this.corporacion = corporacion;
        this.tipoDocumento = tipoDocumento;
        this.periodo = periodo;
        this.contenido = contenido;
        this.descripcion = descripcion;
        this.sesion = sesion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(String tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public String getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(String anoInicial) {
        this.anoInicial = anoInicial;
    }

    public String getMesInicial() {
        return mesInicial;
    }

    public void setMesInicial(String mesInicial) {
        this.mesInicial = mesInicial;
    }

    public String getAnoFinal() {
        return anoFinal;
    }

    public void setAnoFinal(String anoFinal) {
        this.anoFinal = anoFinal;
    }

    public String getMesFinal() {
        return mesFinal;
    }

    public void setMesFinal(String mesFinal) {
        this.mesFinal = mesFinal;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public Date getPeriodoInicio(){
        if( (this.mesInicial != null && this.mesInicial.length() > 0) &&
                (this.anoInicial != null && this.anoInicial.length() > 0)){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return format.parse("01/" + this.mesInicial + "/" + this.anoInicial);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Date getPeriodoFin(){
        if( (this.mesFinal != null && this.mesFinal.length() > 0) &&
                (this.anoFinal != null && this.anoFinal.length() > 0)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(new Integer(this.anoFinal), new Integer(this.mesFinal) - 1, 1);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
            return calendar.getTime();
        }
        return null;

    }

    @Override
    public String toString() {
        return "ActasSearchCriteria{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", tipoAutor='" + tipoAutor + '\'' +
                ", corporacion='" + corporacion + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", periodo='" + periodo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", anoInicial='" + anoInicial + '\'' +
                ", mesInicial='" + mesInicial + '\'' +
                ", anoFinal='" + anoFinal + '\'' +
                ", mesFinal='" + mesFinal + '\'' +
                '}';
    }
}
