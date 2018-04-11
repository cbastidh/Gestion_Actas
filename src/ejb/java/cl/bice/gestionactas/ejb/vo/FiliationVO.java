package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public class FiliationVO  implements Serializable {

    Long id;
    UserVO user;
    EnterpriseVO enterprise;
    Boolean enabled;
    Date date;
    private String timeZone;

    public FiliationVO(Long id, UserVO user, EnterpriseVO enterprise, Boolean enabled, Date date, String timeZone) {
        this.id = id;
        this.user = user;
        this.enterprise = enterprise;
        this.enabled = enabled;
        this.date = date;
        this.timeZone = timeZone;
    }


    public FiliationVO(Long id,
                       Long userId, String userName, String userEmail,
                       Long enterpriseId, String enterpriseEnterpriseName,
                       Date enterpriseDateInit, Date enterpriseDateEnd,
                       Boolean enterpriseActive, String enterpriseDefaultTimeZone, boolean manageTimeZones,
                       Boolean enabled, Date date, String timeZone) {
        this.id = id;
        //this.user = new UserVO(userId, userName, userEmail);
        this.enterprise = new EnterpriseVO(enterpriseId, enterpriseEnterpriseName, enterpriseActive, enterpriseDefaultTimeZone, manageTimeZones);
        this.enabled = enabled;
        this.date = date;
        this.timeZone = timeZone;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public EnterpriseVO getEnterprise() {
        return enterprise;
    }

    public Long getId() {
        return id;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public UserVO getUser() {
        return user;
    }
}