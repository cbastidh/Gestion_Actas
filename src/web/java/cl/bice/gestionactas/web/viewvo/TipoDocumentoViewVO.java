package cl.bice.gestionactas.web.viewvo;

import cl.bice.gestionactas.web.springmvc.common.ViewVO;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class TipoDocumentoViewVO implements ViewVO {

    private String id;
    private String tipoActa;
    private String nmonic;


    public TipoDocumentoViewVO() {
    }

    public TipoDocumentoViewVO(String id, String tipoActa, String nmonic) {
        this.id = id;
        this.tipoActa = tipoActa;
        this.nmonic = nmonic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "TipoDocumentoViewVO{" +
                "id='" + id + '\'' +
                ", tipoActa='" + tipoActa + '\'' +
                ", nmonic='" + nmonic + '\'' +
                '}';
    }
}
