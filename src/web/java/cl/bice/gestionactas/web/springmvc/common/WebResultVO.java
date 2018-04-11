package cl.bice.gestionactas.web.springmvc.common;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class WebResultVO<T> implements Serializable {
    public List<T> data;
    public Boolean isExist;
    public String result;
    public ResultEnum jsonCmd;

    public WebResultVO() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResultEnum getJsonCmd() {
        return jsonCmd;
    }

    public void setJsonCmd(ResultEnum jsonCmd) {
        this.jsonCmd = jsonCmd;
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}