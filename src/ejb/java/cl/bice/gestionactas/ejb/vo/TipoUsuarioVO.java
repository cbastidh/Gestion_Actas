package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;


/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class TipoUsuarioVO implements Serializable {
    private Long id;
    private String tipoUsuario;
    private String nmonic;

    public TipoUsuarioVO(Long id, String tipoUsuario, String nmonic) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.nmonic = nmonic;
    }

    public TipoUsuarioVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNmonic() {
        return nmonic;
    }

    public void setNmonic(String nmonic) {
        this.nmonic = nmonic;
    }
}
