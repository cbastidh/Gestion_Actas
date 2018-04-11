package cl.bice.gestionactas.web.springmvc.common;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public enum  ResultEnum {
    SUCCESS("success"),
    EXISTSTITLE("existstitle"),
    ERROR("error");

    String result;

    ResultEnum(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
