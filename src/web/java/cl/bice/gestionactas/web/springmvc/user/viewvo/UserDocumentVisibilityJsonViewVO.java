package cl.bice.gestionactas.web.springmvc.user.viewvo;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class UserDocumentVisibilityJsonViewVO {

    String tipoDocumentoLabel;
    String tipoDocumentoNmonic;
    String corporacion;

    Long id;
    Long tipoDocumentoId;
    Long userId;

    Boolean isActive;

    public UserDocumentVisibilityJsonViewVO(){}

    public UserDocumentVisibilityJsonViewVO(Long id, String tipoDocumentoLabel, String tipoDocumentoNmonic,
                                            Long tipoDocumentoId, Long userId, Boolean isActive) {
        this.id = id;
        this.tipoDocumentoLabel = tipoDocumentoLabel;
        this.tipoDocumentoNmonic = tipoDocumentoNmonic;
        this.tipoDocumentoId = tipoDocumentoId;
        this.userId = userId;
        this.isActive = isActive;
    }

    public String getTipoDocumentoLabel() {
        return tipoDocumentoLabel;
    }

    public void setTipoDocumentoLabel(String tipoDocumentoLabel) {
        this.tipoDocumentoLabel = tipoDocumentoLabel;
    }

    public String getTipoDocumentoNmonic() {
        return tipoDocumentoNmonic;
    }

    public void setTipoDocumentoNmonic(String tipoDocumentoNmonic) {
        this.tipoDocumentoNmonic = tipoDocumentoNmonic;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorporacion() {
        return corporacion;
    }

    public void setCorporacion(String corporacion) {
        this.corporacion = corporacion;
    }

    @Override
    public String toString() {
        return "UserDocumentVisibilityJsonViewVO{" +
                "tipoDocumentoLabel='" + tipoDocumentoLabel + '\'' +
                ", tipoDocumentoNmonic='" + tipoDocumentoNmonic + '\'' +
                ", id=" + id +
                ", tipoDocumentoId=" + tipoDocumentoId +
                ", userId=" + userId +
                ", isActive=" + isActive +
                '}';
    }
}
