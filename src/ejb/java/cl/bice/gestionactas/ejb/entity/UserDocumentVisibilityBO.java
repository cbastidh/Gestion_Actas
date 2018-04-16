package cl.bice.gestionactas.ejb.entity;

import cl.bice.gestionactas.ejb.vo.UserDocumentVisibilityVO;
import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Entity
@Table(name = "udv_userdocumentvisibility")
@Lock(id="id", semaphore = Semaphore.class, precedence = 10)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class UserDocumentVisibilityBO  implements Identified<Long> {
    @Id
    @GeneratedValue(generator = "IdGen", strategy = GenerationType.SEQUENCE)
    @Column(name = "udv_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "udv_tipodocumentobo_id")
    private TipoDocumentoBO tipoDocumentoBO;

    @Column(name = "flg_estado")
    private Boolean estado = true;

    @ManyToOne()
    @JoinColumn(name = "udv_userbo_id")
    private UserBO userBO;

    @Column(name = "flg_isactive")
    private boolean isActive = false;

    public UserDocumentVisibilityBO() {
    }

    public UserDocumentVisibilityBO(UserBO userBO, TipoDocumentoBO tipoDocumentoBO, boolean isActive) {
        this.tipoDocumentoBO = tipoDocumentoBO;
        this.isActive = isActive;
        this.userBO = userBO;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UserBO getUserBO() {
        return userBO;
    }

    public void setUserBO(UserBO userBO) {
        this.userBO = userBO;
    }

    public TipoDocumentoBO getTipoDocumentoBO() {
        return tipoDocumentoBO;
    }

    public void setTipoDocumentoBO(TipoDocumentoBO tipoDocumentoBO) {
        this.tipoDocumentoBO = tipoDocumentoBO;
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
    public UserDocumentVisibilityVO toVO(){
        return new UserDocumentVisibilityVO(this.getId(), this.getUserBO().toVO(), this.getTipoDocumentoBO().toVO(), this.isActive());
    }

    @Override
    public String toString() {
        return "UserDocumentVisibilityBO{" +
                "isActive=" + isActive +
                ", id=" + id +
                ", userBO=" + userBO +
                ", estado=" + estado +
                ", tipoDocumentoBO=" + tipoDocumentoBO +
                '}';
    }
}
