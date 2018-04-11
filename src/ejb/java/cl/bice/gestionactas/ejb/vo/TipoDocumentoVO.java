package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class TipoDocumentoVO implements Serializable {
    private Long id;
    private String tipoActa;
    private String nmonic;
    private String ciphed;
    private String corporacion;

    public TipoDocumentoVO(Long id, String tipoActa, String nmonic) {
        this.id = id;
        this.tipoActa = tipoActa;
        this.nmonic = nmonic;
    }

    public TipoDocumentoVO(Long id, String tipoActa, String nmonic, String corporacion) {
        this.id = id;
        this.tipoActa = tipoActa;
        this.nmonic = nmonic;
        this.ciphed = ciphed;
        this.corporacion = corporacion;
    }

    public TipoDocumentoVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoActa() {
        return tipoActa;
    }

    public void setTipoActa(String tipoActa) {
        this.tipoActa = tipoActa;
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

    public String getCorporacion() {
        return corporacion;
    }

    public void setCorporacion(String corporacion) {
        this.corporacion = corporacion;
    }
}
