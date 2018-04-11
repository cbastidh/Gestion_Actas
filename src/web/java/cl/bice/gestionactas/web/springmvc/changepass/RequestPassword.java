package cl.bice.gestionactas.web.springmvc.changepass;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.svc.UserSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import cl.bice.gestionactas.web.springmvc.desafio.VerificadorSMSController;


@Controller
public class RequestPassword extends GenericController{
	
	@WebInjected
    UserSvc userSvc;

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(VerificadorSMSController.class);
    @WebInjected
    LoginSvc loginSvc;

    @RequestMapping(value = "/auth/passrequest", method = RequestMethod.POST)
    public String verifyCode(HttpServletRequest request,
    						 @RequestParam(value = "accion", required = false) String accion,
    						 @RequestParam(value = "user", required = false) String user,
    						 @RequestParam(value = "num", required = false) String num){
    	String view = "redirect:/passRequest.jsp?error=true";
    	num = reformatNum(num);
 
    	if(accion.equalsIgnoreCase("Cancelar")){
    		view = "redirect:/login.jsp";
    		return view;
    	}

    	HttpSession session = request.getSession();

    	if(user == null || num == null || user.equalsIgnoreCase("") || num.equalsIgnoreCase("")){
    		LOGGER.info("Campo usuario y/o numero movil estan vacios");
    		session.setAttribute("error", "Campo usuario y/o numero movil estan vacios");
    		view = "redirect:/passRequest.jsp?error=true";
    		return view;
    	}
    	UserVO userVO = new UserVO();
		try {
			userVO = userSvc.obtenerUsuarioPorNombre(user);
		} catch (UserBiceException e) {
			e.printStackTrace();
			LOGGER.info("Error de usuario", e);
            session.setAttribute("error", "Error de usuario "+e);
            view = "redirect:/passRequest.jsp?error=true";
            return view;
		}
		
		


		boolean validNum = loginSvc.validarCelular(userVO.getRut(), num);
		if (validNum){
			session.setAttribute("user", userVO);
			session.setAttribute("num", num.substring(num.length()-4));
			view = "redirect:/change_pass.jsp";
		}
		else{
			session.setAttribute("error", "Numero de celular no corresponde al usuario ");
			view = "redirect:/passRequest.jsp?error=true";
		}
    	
        return view;
    }
    
    public String reformatNum(String numero){
		String formattedNum = "";
		numero = numero.replaceAll("[^\\d]+", "");
		if(numero.length()==8){
			formattedNum = "569"+numero;
			return formattedNum;
		}
		if(numero.startsWith("9") && numero.length()==9){
			formattedNum = "56"+numero;
			return formattedNum;
		}
		return numero;
	}
}
