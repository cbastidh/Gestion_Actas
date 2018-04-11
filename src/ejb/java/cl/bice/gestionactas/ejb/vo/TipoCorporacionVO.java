package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;


/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class TipoCorporacionVO implements Serializable {
    private Long id;
    private String tipoCorporacion;
    private String nmonic;
    private String ciphed;

    public TipoCorporacionVO(Long id, String tipoCorporacion, String nmonic) {
        this.id = id;
        this.tipoCorporacion = tipoCorporacion;
        this.nmonic = nmonic;
    }

    public TipoCorporacionVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCorporacion() {
        return tipoCorporacion;
    }

    public void setTipoCorporacion(String tipoCorporacion) {
        this.tipoCorporacion = tipoCorporacion;
    }

    public String getNmonic() {
        return nmonic;
    }

    public void setNmonic(String nmonic) {
        this.nmonic = nmonic;
    }

    public String getCiphed() {
        return ciphed;
    }

    public void setCiphed(String ciphed) {
        this.ciphed = ciphed;
    }
}
