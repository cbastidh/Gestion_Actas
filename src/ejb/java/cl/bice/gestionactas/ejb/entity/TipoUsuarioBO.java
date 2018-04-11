package cl.bice.gestionactas.ejb.entity;

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
@Table(name ="tip_tipousuario")
@Lock(none = true)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class TipoUsuarioBO  implements Identified<Long>  {
    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name = "tip_id")
    private Long id;

    @Column(name = "nom_tipousuario")
    private String tipoUsuario;

    @Column(name = "cod_nmonic")
    private String nmonic;

    @Column(name = "flg_estado")
    private Boolean estado;

    public TipoUsuarioBO() {
    }

    public TipoUsuarioBO(String tipoUsuario, String nmonic, Boolean estado) {
        this.tipoUsuario = tipoUsuario;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TipoUsuarioBO{" +
                "id=" + id +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", nmonic='" + nmonic + '\'' +
                ", estado=" + estado +
                '}';
    }
}
