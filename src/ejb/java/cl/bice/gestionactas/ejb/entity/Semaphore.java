package cl.bice.gestionactas.ejb.entity;

import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.ISemaphore;
import cl.neoptera.ejb.dao.lock.Lock;

import javax.persistence.*;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Entity
@Table(name = "sema_semaphore")
@Lock(none = true)
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class Semaphore implements Identified<Long>, ISemaphore {

    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name = "sema_id")
    Long id;

    @Column(name = "nom_semaforo")
    String name;

    @Column(name = "cor_count")
    Long count;

    protected Semaphore() {
    }

    public Semaphore(String name) {
        this.name = name;
        count = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count == null ? 0 : count;
    }

    @Override
    public Long incCount() {
        return ++count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

