package cl.bice.gestionactas.ejb.entity;

import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Entity
@Table(name ="actas_doc")
@Lock(id = "id", semaphore = Semaphore.class, precedence = 3)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class ActasBO implements Identified<Long> {
    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name = "actas_id")
    private Long id;

    @Column(name = "nom_titulo")
    String titulo;

    @Column(name = "nom_autor")
    String autor;


    @Column(name = "cod_corporacion")
    String corporacion;

    @ManyToOne
    TipoDocumentoBO tipoDocumento;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fec_acta")
    Date date;

    public ActasBO() {
    }

    public ActasBO(String titulo, String autor, String corporacion, TipoDocumentoBO tipoDocumento, Date date) {
        this.titulo = titulo;
        this.autor = autor;
        this.corporacion = corporacion;
        this.tipoDocumento = tipoDocumento;
        this.date = date;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCorporacion() {
        return corporacion;
    }

    public void setCorporacion(String corporacion) {
        this.corporacion = corporacion;
    }

    public TipoDocumentoBO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoBO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
