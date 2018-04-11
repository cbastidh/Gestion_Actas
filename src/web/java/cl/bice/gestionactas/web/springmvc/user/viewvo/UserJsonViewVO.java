package cl.bice.gestionactas.web.springmvc.user.viewvo;

import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.springmvc.common.ViewVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class UserJsonViewVO implements ViewVO {
    Long id;
    String name;
    String loginAD;
    String rut;
    Map<Long, UserDocumentVisibilityJsonViewVO> documentsVisibilityList;

    public UserJsonViewVO() {
    }

    public UserJsonViewVO(Long id, String name, String rut, String loginAD) {
        this.id = id;
        this.name = name;
        this.loginAD = loginAD;
        this.rut = rut;
    }

    public UserJsonViewVO(Long id, String name, String rut, String loginAD, Map<Long, UserDocumentVisibilityJsonViewVO> uDocVisJsonVVOList) {
        this.id = id;
        this.name = name;
        this.loginAD = loginAD;
        this.documentsVisibilityList = uDocVisJsonVVOList;
        this.rut = rut;
    }

    public Map<Long, UserDocumentVisibilityJsonViewVO> getUserDocumentVisibility() {
        return documentsVisibilityList;
    }

    public void setUserDocumentVisibility(Map<Long, UserDocumentVisibilityJsonViewVO> userDocumentVisibilityJsonVVO) {
        this.documentsVisibilityList = userDocumentVisibilityJsonVVO;
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

    public UserVO toVO(){
        Long newID = this.getId() != null && this.getId() > 0 ? this.getId() : null;
        return  new UserVO(newID, this.getName(), this.getRut(), this.getLoginAD());
    }

    public void initializeVisibleDocuments(List<TipoDocumentoVO> allDocumentTypes) {
        this.documentsVisibilityList = new HashMap<>();
        for(TipoDocumentoVO tdVO : allDocumentTypes){
            UserDocumentVisibilityJsonViewVO u = new UserDocumentVisibilityJsonViewVO(null, tdVO.getTipoActa(), tdVO.getNmonic(),
                    tdVO.getId(), this.getId(), false);
            u.setCorporacion(tdVO.getCorporacion());
            this.documentsVisibilityList.put(tdVO.getId(), u);
        }

    }

    public void updateVisibleDocuments(List<UserDocumentVisibilityJsonViewVO> uDocVisJsonVVOList) {
        for(UserDocumentVisibilityJsonViewVO targetDocumentVisibility : uDocVisJsonVVOList){
            Long targetDocTypeId = targetDocumentVisibility.getTipoDocumentoId();
            if(this.documentsVisibilityList.containsKey(targetDocTypeId)){
                this.documentsVisibilityList.get(targetDocTypeId).setId(targetDocumentVisibility.getId());
                this.documentsVisibilityList.get(targetDocTypeId).setActive(true);
            }
        }
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public String toString() {
        return "UserJsonViewVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginAD='" + loginAD + '\'' +
                ", rut='" + rut + '\'' +
                ", documentsVisibilityList=" + documentsVisibilityList +
                '}';
    }
}