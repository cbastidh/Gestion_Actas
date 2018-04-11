package cl.bice.gestionactas.web.springmvc.desafio;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.security.EstadoDesafioVO;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.common.GenericController;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Date: 2/8/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Controller
public class VerificadorSMSController extends GenericController{
    private static final Logger LOGGER = Logger.getLogger(VerificadorSMSController.class);
    @WebInjected
    LoginSvc loginSvc;

    @RequestMapping(value = "/auth/securitytoken", method = RequestMethod.POST)
    public String verifyCode(HttpServletRequest request,
    						 @RequestParam("textcode") String code, //@ContextoWeb WebUser user,
    						 @RequestParam(value = "accion", required = false) String accion){
    	String view = "redirect:/securitytoken.jsp?error=true";
    	UserVO userVO = new UserVO();
    	HttpSession session = request.getSession();
    	if(code != null && !code.isEmpty()){
        	try{
        		int codigo = Integer.parseInt(code);
        	} catch (NumberFormatException e) {
        		e.printStackTrace();
        		String error = "Debe ingresar sólo números";
        		view = "redirect:/change_pass.jsp?error=true";
        		session.setAttribute("error", error);
        		return view;
        	}
        }
    	try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			userVO = loginSvc.loginNoToken(auth.getName(),auth.getCredentials().toString());
		} catch (UserBiceException e) {
			e.printStackTrace();
			LOGGER.info("Error de usuario", e);
		}

        try {
        	EstadoDesafioVO desafioVO = VerificadorDesafioSMS.verifyCode(userVO, code, loginSvc);
            //EstadoDesafioVO desafioVO = VerificadorDesafioSMS.verifyCode(user.getUsuario(), code, loginSvc);
            if (desafioVO.isEstado()) {
                grantAuthority();
                view = "redirect:/web/main/dashboard";
            }else{
                view = "redirect:/securitytoken.jsp?error=true&msg="+desafioVO.getGlosa();
            }
        } catch (NumberFormatException e) {
        	e.printStackTrace();
            LOGGER.debug("error al convertir el tipo de dato a entero {}" + e.getMessage());
            view = "redirect:/securitytoken.jsp?error=true";
        } catch (VerificationCodeException e) {
        	e.printStackTrace();
            LOGGER.debug("error al realizar la verificacion de codigo {}" + e.getMessage());
            view = "redirect:/securitytoken.jsp?error=true";
        }
        return view;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String showSecurityTokenForm() {
        return "security/securitytoken";
    }

    private void grantAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = loginSvc.obtenerRoles(auth.getPrincipal().toString(), auth.getCredentials().toString());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(String rol : roles){
            authorities.add(new SimpleGrantedAuthority(rol));
        }
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private static class EstadoJSON implements Serializable{
        String glosa;
        Boolean estado;

        public EstadoJSON(String glosa, Boolean estado) {
            this.glosa = glosa;
            this.estado = estado;
        }

        public String getGlosa() {
            return glosa;
        }

        public void setGlosa(String glosa) {
            this.glosa = glosa;
        }

        public Boolean getEstado() {
            return estado;
        }

        public void setEstado(Boolean estado) {
            this.estado = estado;
        }
    }

}
