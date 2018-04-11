package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class EnterpriseVO implements Serializable {
    private Long id;
    private String name;
    private Boolean active;
    private String defaultTimeZone;
    private boolean manageTimeZones;

    public EnterpriseVO() {
    }

    public EnterpriseVO(Long id, String name, Boolean active, String defaultTimeZone, boolean manageTimeZones) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.defaultTimeZone = defaultTimeZone;
        this.manageTimeZones = manageTimeZones;
    }

    public Boolean getActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDefaultTimeZone() {
        return defaultTimeZone;
    }

    public boolean isManageTimeZones() {
        return manageTimeZones;
    }

    public void setManageTimeZones(boolean manageTimeZones) {
        this.manageTimeZones = manageTimeZones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultTimeZone(String defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

