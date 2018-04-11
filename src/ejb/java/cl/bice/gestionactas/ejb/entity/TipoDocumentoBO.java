package cl.bice.gestionactas.ejb.entity;

import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Entity
@Table(name ="tipdoc_tipodocumento")
@Lock(none = true)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class TipoDocumentoBO implements Identified<Long> {
    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name = "tipdoc_id")
    private Long id;

    @Column(name = "nom_tipoacta")
    private String tipoActa;

    @Column(name = "cod_nmonic")
    private String nmonic;

    @Column(name = "flg_estado")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_id")
    TipoCorporacionBO tipoCorporacion;

    public TipoDocumentoBO() {
    }

    public TipoDocumentoBO(String tipoActa, String nmonic, Boolean estado) {
        this.tipoActa = tipoActa;
        this.nmonic = nmonic;
        this.estado = estado;
    }

    @Override
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoCorporacionBO getTipoCorporacion() {
        return tipoCorporacion;
    }

    public void setTipoCorporacion(TipoCorporacionBO tipoCorporacion) {
        this.tipoCorporacion = tipoCorporacion;
    }

    @Override
    public String toString() {
        return "TipoDocumentoBO{" +
                "id=" + id +
                ", tipoActa='" + tipoActa + '\'' +
                ", nmonic='" + nmonic + '\'' +
                ", estado=" + estado +
                '}';
    }

    public TipoDocumentoVO toVO() {
        return new TipoDocumentoVO(this.id, this.tipoActa, this.nmonic, this.getTipoCorporacion().getTipoCorporacion());
    }
}
