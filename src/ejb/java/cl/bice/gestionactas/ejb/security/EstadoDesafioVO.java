package cl.bice.gestionactas.ejb.security;

import java.io.Serializable;

/**
 * Date: 19-08-16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class EstadoDesafioVO implements Serializable {
    boolean estado;
    String codigo;
    String glosa;

    public EstadoDesafioVO() {
    }

    public EstadoDesafioVO(boolean estado, String codigo, String glosa) {
        this.estado = estado;
        this.codigo = codigo;
        this.glosa = glosa;
    }


    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }


    @Override
    public String toString() {
        return "EstadoDesafioVO{" +
                "estado=" + estado +
                ", codigo='" + codigo + '\'' +
                ", glosa='" + glosa + '\'' +
                '}';
    }
}
