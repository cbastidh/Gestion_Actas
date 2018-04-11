package cl.bice.gestionactas.web.springmvc.user.search;

import cl.bice.gestionactas.web.springmvc.common.SearchCriteria;

import java.io.Serializable;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class UserSearchCriteria implements SearchCriteria, Serializable {
    private String name;
    private String loginAD;

    public UserSearchCriteria(){}

    public UserSearchCriteria(String name, String loginAD) {
        this.name = name;
        this.loginAD = loginAD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginAD() {
        return loginAD;
    }

    public void setLoginAD(String loginAD) {
        this.loginAD = loginAD;
    }

    @Override
    public String toString() {
        return "UserSearchCriteria{" +
                "name='" + name + '\'' +
                ", loginAD='" + loginAD + '\'' +
                '}';
    }
}
