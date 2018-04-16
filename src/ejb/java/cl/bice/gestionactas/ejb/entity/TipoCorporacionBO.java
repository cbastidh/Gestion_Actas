package cl.bice.gestionactas.ejb.entity;

import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;
import java.util.List;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Entity
@Table(name ="tip_tipocorporacion")
@Lock(none = true)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class TipoCorporacionBO implements Identified<Long>{
    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name = "tip_id")
    private Long id;

    @Column(name = "nom_tipocorporacion")
    private String tipoCorporacion;

    @Column(name = "cod_nmonic")
    private String nmonic;

    @Column(name = "flg_estado")
    private Boolean estado;

    @OneToMany(mappedBy = "tipoCorporacion")
    List<TipoDocumentoBO> tipoDocumento;

    public TipoCorporacionBO() {
    }

    public TipoCorporacionBO(String tipoCorporacion, String nmonic, Boolean estado) {
        this.tipoCorporacion = tipoCorporacion;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<TipoDocumentoBO> getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(List<TipoDocumentoBO> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoCorporacionVO toVO(){
        return new TipoCorporacionVO(this.id, this.tipoCorporacion, this.nmonic);
    }

    @Override
    public String toString() {
        return "TipoCorporacionBO{" +
                "id=" + id +
                ", tipoCorporacion='" + tipoCorporacion + '\'' +
                ", nmonic='" + nmonic + '\'' +
                ", estado=" + estado +
                '}';
    }
}