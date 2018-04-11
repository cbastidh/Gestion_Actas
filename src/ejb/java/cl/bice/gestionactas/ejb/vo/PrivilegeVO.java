package cl.bice.gestionactas.ejb.vo;

import cl.bice.gestionactas.ejb.type.RoleEnum;

import java.io.Serializable;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class PrivilegeVO implements Serializable {

    Long id;

    String privilege;

    RoleEnum role;

    public PrivilegeVO(Long id, String privilege, RoleEnum role) {
        this.id = id;
        this.privilege = privilege;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public RoleEnum getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "PrivilegeVO{" +
                "id=" + id +
                ", privilege='" + privilege + '\'' +
                ", role=" + role +
                '}';
    }
}
