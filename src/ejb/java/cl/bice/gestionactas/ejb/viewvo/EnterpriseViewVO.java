package cl.bice.gestionactas.ejb.viewvo;

import cl.bice.gestionactas.ejb.vo.EnterpriseVO;

/**
 * Date: 2/7/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class EnterpriseViewVO extends EnterpriseVO {

    public EnterpriseViewVO(EnterpriseVO enterprise) {
        super(enterprise.getId(), enterprise.getName(), enterprise.getActive(), enterprise.getDefaultTimeZone(), enterprise.isManageTimeZones());
    }

    public EnterpriseViewVO(Long id, String name, Boolean active, String defaultTimeZone, boolean manageTimeZones) {
        super(id, name,  active, defaultTimeZone, manageTimeZones);
    }

    public EnterpriseViewVO() {
        super(null, "", true, null, false);
    }

}
