package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2/3/16
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public class UserVO  implements Serializable {

    Long id;
    String name;
    String loginAD;
    String rut;
    List<String> roles;
    String identificadorSMS;
    private UserStatusEnum status;

    public UserVO() {
    }

    public UserVO(Long id, String name, String rut, String loginAD) {
        this.id = id;
        this.name = name;
        this.loginAD = loginAD;
        this.rut = rut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRut() {
        String temp = rut.replace("-","");
        return "0000000000".substring(temp.length()) + temp;
    }

    public String getIdentificadorSMS() {
        return identificadorSMS;
    }

    public void setIdentificadorSMS(String identificadorSMS) {
        this.identificadorSMS = identificadorSMS;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginAD='" + loginAD + '\'' +
                ", rut='" + rut + '\'' +
                '}';
    }


    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    public UserStatusEnum getStatus() {
        return status;
    }
}
