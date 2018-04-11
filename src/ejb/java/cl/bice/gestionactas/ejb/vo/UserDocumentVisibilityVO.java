package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class UserDocumentVisibilityVO implements Serializable {
    private Long id;

    private TipoDocumentoVO tipoDocumentoVO;

    private Boolean estado = true;

    private UserVO userVO;

    public UserDocumentVisibilityVO(Long id, UserVO userVO, TipoDocumentoVO tDocVO, Boolean isActive) {
        this.id = id;
        this.tipoDocumentoVO = tDocVO;
        this.userVO = userVO;
        this.isActive = isActive;
    }

    private boolean isActive = false;

    public UserDocumentVisibilityVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumentoVO getTipoDocumentoVO() {
        return tipoDocumentoVO;
    }

    public void setTipoDocumentoVO(TipoDocumentoVO tipoDocumentoVO) {
        this.tipoDocumentoVO = tipoDocumentoVO;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserDocumentVisibilityVO{" +
                "id=" + id +
                ", tipoDocumentoVO=" + tipoDocumentoVO +
                ", estado=" + estado +
                ", userVO=" + userVO +
                ", isActive=" + isActive +
                '}';
    }
}
