package cl.bice.gestionactas.ejb.viewvo;

import cl.bice.gestionactas.ejb.type.RoleEnum;
import cl.bice.gestionactas.ejb.vo.FiliationVO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import java.util.Set;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class UserViewVO extends UserVO {

    Set<RoleEnum> roles;
    boolean filiationEnabled;
    String filiationTimeZone;
    private Long enterpriseId;

    public UserViewVO(Long id, String name, String email,
                      Boolean filiationEnabled, String filiationTimeZone,
                      Set<RoleEnum> roles) {
        //super(id, name, email);
        this.roles = roles;
        this.filiationEnabled = filiationEnabled;
        this.filiationTimeZone = filiationTimeZone;
    }

    public UserViewVO(UserVO user,
                      Boolean filiationEnabled, String filiationTimeZone,
                      Set<RoleEnum> roles) {
        this.roles = roles;
        this.filiationEnabled = filiationEnabled;
        this.filiationTimeZone = filiationTimeZone;
    }

    public UserViewVO(FiliationVO filiation, Set<RoleEnum> roles) {
        this.roles = roles;
        this.filiationEnabled = filiation.getEnabled();
        this.filiationTimeZone = filiation.getTimeZone();
    }

    public boolean hasRole(RoleEnum role) {
        return roles.contains(role);
    }

    public boolean isFiliationEnabled() {
        return filiationEnabled;
    }

    public String getFiliationTimeZone() {
        return filiationTimeZone;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }
}