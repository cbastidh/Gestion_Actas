package cl.bice.gestionactas.ejb.remote.bean;

import cl.bice.gestionactas.ejb.dao.TipoDocumentoDAO;
import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.dao.UserDocumentVisibilityDAO;
import cl.bice.gestionactas.ejb.entity.TipoDocumentoBO;
import cl.bice.gestionactas.ejb.entity.UserBO;
import cl.bice.gestionactas.ejb.entity.UserDocumentVisibilityBO;
import cl.bice.gestionactas.ejb.interceptor.EJBInjectorInterceptor;
import cl.bice.gestionactas.ejb.interceptor.Injected;
import cl.bice.gestionactas.ejb.interceptor.WebModuleEntityInterceptor;
import cl.bice.gestionactas.ejb.remote.UserEJBRemote;
import cl.bice.gestionactas.ejb.vo.UserDocumentVisibilityVO;
import cl.bice.gestionactas.ejb.vo.UserStatusEnum;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;
import cl.neoptera.ejb.dao.jpa.ServiceContext;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Stateless(name = "gab_UserEJBRemote", mappedName = "gab_UserEJBRemote")
@Interceptors({WebModuleEntityInterceptor.class, EJBInjectorInterceptor.class})
public class UserEJBRemoteBean implements UserEJBRemote {
    @Injected
    UserDAO userDAO;

    @Injected
    TipoDocumentoDAO tipoDocumentoDAO;

    @Injected
    UserDocumentVisibilityDAO userDocumentVisibilityDAO;

    /**
     * @see cl.bice.gestionactas.ejb.svc.UserSvc#obtenerUsuarioPorNombre(java.lang.String)
     */
    @Override
    public UserVO obtenerUsuarioPorNombre(String username) throws UserBiceException {
        UserBO userBO = userDAO.obtenerUsuario(username);
        if (userBO == null){
            throw new UserBiceException("usuario no encontrado en la base de datos de la aplicacion, pero si en ldap, debe sincronizarlos");
        }
        return userBO.toVO();
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.UserSvc#createUser(cl.bice.gestionactas.ejb.vo.UserVO)
     */
    @Override
    public UserVO createUser(UserVO userVO){
        UserBO userBO = new UserBO(userVO);
        UserBO u = userDAO.obtenerUsuario(userVO.getLoginAD().toLowerCase());
        if (u != null){
            userVO.setStatus(UserStatusEnum.EXISTS);
        }else {
            if(userBO.getId() == null){
                userBO.setEstado(true);
                userDAO.lockNew();
                userDAO.insert(userBO);
                userVO.setStatus(UserStatusEnum.ADDED);
            } else {
                userBO.setEstado(true);
                userDAO.lock(userBO.getId());
                userDAO.update(userBO);
                userVO.setStatus(UserStatusEnum.UPDATED);
            }
        }
        return userVO;
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.UserSvc#updateUserDocumentVisibility(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Boolean)
     */
    @Override
    public Boolean updateUserDocumentVisibility(Long permId, Long userId, Long docTypeId, Boolean newStatus){
        if(permId  != null && !newStatus){
            userDocumentVisibilityDAO.lock(permId);
            userDocumentVisibilityDAO.delete(permId);
        } else if(permId == null && newStatus){
            UserBO targetUser = userDAO.get(userId);
            TipoDocumentoBO targetDocumentType = tipoDocumentoDAO.get(docTypeId);
            UserDocumentVisibilityBO newBO = new UserDocumentVisibilityBO(targetUser, targetDocumentType, true);
            userDocumentVisibilityDAO.lockNew();
            userDocumentVisibilityDAO.insert(newBO);
        }
        return true;
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.UserSvc#listarUsuariosPorCriterios(cl.neoptera.core.page.PageQueryMapVO)
     */
    @Override
    public PageVO<UserVO> listarUsuariosPorCriterios(PageQueryMapVO query){
        Map<String, Object> mapWhere =query.getQuery();
        if(mapWhere == null){
            throw new NullPointerException("Debe venir el query aunque sea sin parametros");
        }
        String name = (String) mapWhere.get("nombre");
        String loginAD = (String) mapWhere.get("login");
        String sql = "select count(userbo0_.usr_id) as col_0_0_ from usr_usuario userbo0_ where usr_estado = 1 ";
        if (name != null){
            sql = sql + "and lower(userbo0_.nom_nombre) like '%"+name.toLowerCase()+"%'";
        }
        if(loginAD != null){
            sql = sql + "and lower(userbo0_.nom_loginad) like '%"+loginAD.toLowerCase()+"%'";
        }

        Query qCount  = ServiceContext.current().getEM().createNativeQuery(sql);
        List<Object> objectList =  qCount.getResultList();

        Integer count = ((BigDecimal) objectList.get(0)).intValue();
        List<UserVO> voList = new ArrayList<>();
        if (count > 0){
            if (name != null && loginAD != null){
                voList = userDAO.listarUsuariosPorCriterios("%"+name+"%", loginAD, "u.rut", query.getFirst(), query.getSize());
            }else if(name != null && loginAD == null){
                voList = userDAO.listarUsuariosPorNombre("%"+name+"%", "u.rut", query.getFirst(), query.getSize());
            }else if(loginAD != null && name == null){
                voList = userDAO.listarUsuariosPorLogin("%"+loginAD+"%", "u.rut", query.getFirst(), query.getSize());
            }else{
                voList = userDAO.listarUsuariosPorSinParametro("u.rut", query.getFirst(), query.getSize());
            }
        }
        PageVO<UserVO> pageVO = new PageVO<>(count, query.getSize());
        pageVO.getData().addAll(voList);
        return pageVO;
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.UserSvc#listarVisibilidadDocumentos(java.lang.Long)
     */
    public List<UserDocumentVisibilityVO> listarVisibilidadDocumentos(Long userId){
        List<UserDocumentVisibilityVO> userDocVisVOs = new ArrayList<>();
        for(UserDocumentVisibilityBO uDocBO : userDocumentVisibilityDAO.listarVisibilidadDocumentos(true, userId)){
            userDocVisVOs.add(new UserDocumentVisibilityVO(uDocBO.getId(), uDocBO.getUserBO().toVO(), uDocBO.getTipoDocumentoBO().toVO(), uDocBO.isActive()));
        }
        return userDocVisVOs;
    }

    @Override
    public boolean deleteUser(Long userId) throws UserBiceException {
        try {
            userDAO.lock(userId);
            UserBO userBO = userDAO.get(userId);
            userBO.setEstado(false);
            userDAO.update(userBO);
        }catch (NullPointerException n){
            throw new UserBiceException("El usuario "+userId+" no existe");
        }catch (Exception e){
            throw new UserBiceException("Error al eliminar el usuario "+userId + " --- " + e.getMessage());
        }
        return true;
    }
}
