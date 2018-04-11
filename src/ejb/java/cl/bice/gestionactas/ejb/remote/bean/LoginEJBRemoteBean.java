package cl.bice.gestionactas.ejb.remote.bean;

import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.interceptor.EJBInjectorInterceptor;
import cl.bice.gestionactas.ejb.interceptor.Injected;
import cl.bice.gestionactas.ejb.interceptor.WebModuleEntityInterceptor;
import cl.bice.gestionactas.ejb.remote.LoginEJBRemote;
import cl.bice.gestionactas.ejb.security.EstadoDesafioVO;
import cl.bice.gestionactas.ejb.security.SecurityProvider;
import cl.bice.gestionactas.ejb.vo.UserVO;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Date: 2/5/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Stateless(name = "gab_LoginEJBRemote", mappedName = "gab_LoginEJBRemote")
@Interceptors({WebModuleEntityInterceptor.class, EJBInjectorInterceptor.class})
public class LoginEJBRemoteBean implements LoginEJBRemote {
	@Injected
    SecurityProvider securityProvider;

    @Injected
    UserDAO userDAO;

    public LoginEJBRemoteBean() {
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#login(java.lang.String, java.lang.String)
     */
    @Override
    public UserVO login(String username, String password) throws UserBiceException{
        UserVO userVO = securityProvider.validateUser(username, password, userDAO);
        if (userVO == null){
            return null;
        }
        String identificador = securityProvider.identificadorDesafio(userVO.getRut());
        userVO.setIdentificadorSMS(identificador);
        return userVO;
    }
    
    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#loginNoToken(java.lang.String, java.lang.String)
     */
    @Override
    public UserVO loginNoToken(String username, String password) throws UserBiceException{
        UserVO userVO = securityProvider.validateUser(username, password, userDAO);
        if (userVO == null){
            return null;
        }
        return userVO;
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#obtenerRoles(java.lang.String, java.lang.String)
     */
    @Override
    public List<String> obtenerRoles(String username, String password) {
        return securityProvider.retreiveRoles(username, password);
    }

    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#validaDesafio(cl.bice.gestionactas.ejb.vo.UserVO, java.lang.String)
     */
    @Override
    public EstadoDesafioVO validaDesafio(UserVO userVO, String respuestadesafio) {
        return securityProvider.validaDesafio(userVO.getRut(), respuestadesafio);
    }
    
    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#changePass(java.lang.String, java.lang.String)
     */
    @Override
    public String changePass(String username, String password){
    	return securityProvider.changePass(username, password);
    }
    
    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#crearDesafio(java.lang.String)
     */
    @Override
    public String crearDesafio(String userID){
    	return securityProvider.identificadorDesafio(userID);
    }
    
    /**
     * @see cl.bice.gestionactas.ejb.svc.LoginSvc#validarCelular(java.lang.String, java.lang.String)
     */
    @Override
    public boolean validarCelular(String user, String numCelular){
    	return securityProvider.validaNumeroCelular(user, numCelular);
    }

}
