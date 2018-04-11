package cl.bice.gestionactas.web.viewvo;

import cl.bice.gestionactas.web.springmvc.common.ViewVO;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class RolEnumViewVO implements ViewVO {
    String name;
    String roleId;

    public RolEnumViewVO(String name, String roleId) {
        this.name = name;
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
