package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class ContextVO implements Serializable {

    Long userId;
    String timeZone;

    public ContextVO(Long userId, String timeZone) {
        this.userId = userId;
        this.timeZone = timeZone;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTimeZone() {
        return timeZone;
    }
}