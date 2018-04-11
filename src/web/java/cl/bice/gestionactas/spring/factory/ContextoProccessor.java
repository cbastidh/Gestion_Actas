package cl.bice.gestionactas.spring.factory;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.UserSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.factory.WebFactory;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;

/**
 * Date: 27-11-14.
 * Neoptera SPA - Chile - Copyright 2013
 * Project: Gestion Actas
 */
public class ContextoProccessor implements FactoryProccessor<WebUser> {
    private static Logger LOGGER = Logger.getLogger(ContextoProccessor.class);

    @Override
    public WebUser process(HttpSession session) {
        WebUser webUser;
        UserSvc userSvc = WebFactory.getBean(UserSvc.class);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserVO userVO = null;
        try {
            userVO = userSvc.obtenerUsuarioPorNombre(auth.getName().toString());
        } catch (UserBiceException e) {
            LOGGER.error("Error de usuario", e);
        }
        webUser = new WebUser(userVO);
        return webUser;
    }
}
