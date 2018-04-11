package cl.bice.gestionactas.web.security.authenticate;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.factory.WebFactory;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2/8/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	System.out.println("Ingreso autenticación");
        LoginSvc loginSvc = WebFactory.getBean(LoginSvc.class);
        UserVO userVO = null;
        try {
            logger.debug("usuario --> " + authentication.getName());
            userVO = loginSvc.loginNoToken(authentication.getName(), authentication.getCredentials().toString());
            if (userVO == null){
                throw new UsernameNotFoundException("error");
            }
        } catch (UserBiceException e) {
        	e.printStackTrace();
            logger.debug("no se ha podido autenticar el usuario "+ e.getMessage());
        }

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (userVO != null) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}