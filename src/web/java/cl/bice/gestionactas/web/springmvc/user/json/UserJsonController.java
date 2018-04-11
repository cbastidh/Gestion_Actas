package cl.bice.gestionactas.web.springmvc.user.json;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc;
import cl.bice.gestionactas.ejb.svc.UserSvc;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.ejb.vo.UserDocumentVisibilityVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.common.PageJsonViewVO;
import cl.bice.gestionactas.web.springmvc.common.PaginatorVO;
import cl.bice.gestionactas.web.springmvc.common.ResultEnum;
import cl.bice.gestionactas.web.springmvc.user.search.UserSearchCriteria;
import cl.bice.gestionactas.web.springmvc.user.viewvo.UserDocumentVisibilityJsonViewVO;
import cl.bice.gestionactas.web.springmvc.user.viewvo.UserJsonViewVO;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Controller
@RequestMapping(value = "/main/users/json")
public class UserJsonController {
    private static Logger LOGGER = Logger.getLogger(UserJsonController.class);

    @WebInjected
    UserSvc userSvc;

    @WebInjected
    ParametrosEstaticosSvc parametrosEstaticosSvc;

    @RequestMapping(value = "/updateDocumentVisibility", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEnum updateDocumentVisibility(@RequestBody UserDocumentVisibilityJsonViewVO userDocumentVisibility, @ContextoWeb WebUser webUser){
        LOGGER.debug("updateDocumentVisibility " + userDocumentVisibility.toString());
                userSvc.updateUserDocumentVisibility(userDocumentVisibility.getId(), userDocumentVisibility.getUserId(), userDocumentVisibility.getTipoDocumentoId(), userDocumentVisibility.getActive());
        return ResultEnum.SUCCESS;
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEnum addUser(@RequestBody UserJsonViewVO userJsonViewVO, @ContextoWeb WebUser webUser){
        UserVO userVO = userSvc.createUser(userJsonViewVO.toVO());
        ResultEnum r;
        switch (userVO.getStatus()){
            case EXISTS:
                r = ResultEnum.EXISTSTITLE;
                break;
            case UPDATED:
                r = ResultEnum.SUCCESS;
                break;
            default:
                r = ResultEnum.SUCCESS;
                break;
        }
        return r;
    }

    @RequestMapping(value = "/list",  produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public PageJsonViewVO<UserJsonViewVO> userList(Model model, @ContextoWeb WebUser user, @RequestBody PaginatorVO<UserSearchCriteria> paginatorVO){
        LOGGER.debug("userList" + paginatorVO.getCriteria());

        Map<String, Object> mapWhere = new HashMap<>();

        if (paginatorVO.getCriteria().getName() != null && paginatorVO.getCriteria().getName().length() > 0) {
            mapWhere.put("nombre", paginatorVO.getCriteria().getName().toLowerCase());
        }
        if (paginatorVO.getCriteria().getLoginAD() != null && paginatorVO.getCriteria().getLoginAD().length() > 0) {
            mapWhere.put("login", paginatorVO.getCriteria().getLoginAD().toLowerCase());
        }
        PageQueryMapVO queryMapVO = new PageQueryMapVO(paginatorVO.getOffset() / paginatorVO.getLimit(), paginatorVO.getLimit(), mapWhere);
        PageVO<UserVO> userVOList = userSvc.listarUsuariosPorCriterios(queryMapVO);
        List<TipoDocumentoVO> allDocumentTypes = parametrosEstaticosSvc.obtenerTipoDocumentos();

        List<UserJsonViewVO> list = new ArrayList<>();
        for (UserVO userVO : userVOList.getData()){
            List<UserDocumentVisibilityJsonViewVO> documentsVisibleByUser = new ArrayList<>();

            for(UserDocumentVisibilityVO uDocVis : userSvc.listarVisibilidadDocumentos(userVO.getId())){
                UserDocumentVisibilityJsonViewVO e = new UserDocumentVisibilityJsonViewVO(uDocVis.getId(), uDocVis.getTipoDocumentoVO().getTipoActa(),
                        uDocVis.getTipoDocumentoVO().getNmonic(), uDocVis.getTipoDocumentoVO().getId(), userVO.getId(), uDocVis.isActive());
                e.setCorporacion(uDocVis.getTipoDocumentoVO().getCorporacion());
                documentsVisibleByUser.add(e);
            }
            UserJsonViewVO userJsonViewVO = new UserJsonViewVO(userVO.getId(), userVO.getName(), userVO.getRut(), userVO.getLoginAD());
            userJsonViewVO.initializeVisibleDocuments(allDocumentTypes);
            userJsonViewVO.updateVisibleDocuments(documentsVisibleByUser);
            
            userJsonViewVO.setRut(reformatRut(userJsonViewVO.getRut()));
            
            list.add(userJsonViewVO);
        }

        PageJsonViewVO<UserJsonViewVO> pageJsonViewVO = new PageJsonViewVO<>();
        pageJsonViewVO.setTotal(userVOList.getTotal());
        pageJsonViewVO.setRows(list);
        return pageJsonViewVO;
    }

    @RequestMapping(value="/delete/{id}")
    @ResponseBody
    public ResultEnum deleteUserByLogin(@ContextoWeb WebUser user, @PathVariable String id){
        boolean p = false;
        try {
            UserVO userVO = userSvc.obtenerUsuarioPorNombre(id);
            p = userSvc.deleteUser(userVO.getId());
        } catch (UserBiceException e) {
            LOGGER.error("Error al tratar de eliminar un usuario " + e.getMessage());
            return ResultEnum.ERROR;
        }
        return (p ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    
    public String reformatRut(String rut){
    	while(rut.startsWith("0")){
    		rut = rut.substring(1);
    	}
    	String rutdv = rut.substring(rut.length()-1);
    	rut = rut.substring(0, rut.length()-1);
    	rut = rut.concat("-"+rutdv);
    	return rut;
    }

}