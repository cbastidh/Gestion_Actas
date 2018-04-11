package cl.bice.gestionactas.ejb.vo;

import cl.bice.gestionactas.ejb.type.RoleEnum;
import cl.neoptera.core.vo.Identified;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class RoleVO implements Identified<Long> {

    Long id;
    RoleEnum name;
    Long filiationId;
    Boolean enabled;

    public RoleVO(Long id, RoleEnum name, Long filiationId, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.filiationId = filiationId;
        this.enabled = enabled;
    }

    public RoleVO(Long id, RoleEnum name, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Long getFiliationId() {
        return filiationId;
    }

    public Long getId() {
        return id;
    }

    public RoleEnum getName() {
        return name;
    }
}
