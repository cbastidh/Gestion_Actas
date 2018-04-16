package cl.bice.gestionactas.web.springmvc.changepass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.svc.UserSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.factory.MyBatisConnectionFactory;
import cl.bice.gestionactas.web.springmvc.common.GenericController;

@Controller
public class ChangePassController extends GenericController{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ChangePassController.class);
    @WebInjected
    LoginSvc loginSvc;
    
    @WebInjected
    UserSvc userSvc;

    @RequestMapping(value = "/auth/changepass", method = RequestMethod.POST)
    public String verifyCode(HttpServletRequest request,
    						 @RequestParam(value = "accion", required = false) String accion,
    						 @RequestParam(value = "newpassword", required = false) String pass,
    						 @RequestParam(value = "repassword", required = false) String repass){
    	HttpSession session = request.getSession();
    	UserVO userVO = new UserVO();
    	if(session.getAttribute("user")!=null)
    		userVO = (UserVO) session.getAttribute("user");
    	else{
    		try {
    			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    			userVO = userSvc.obtenerUsuarioPorNombre(auth.getName());
    			session.setAttribute("user", userVO);
    		} catch (UserBiceException e) {
    			e.printStackTrace();
    			LOGGER.info("Error de usuario", e);
                session.setAttribute("error", "Error de usuario "+e);
    		}
    	}
    		
    	String view = "redirect:/change_pass.jsp?error=true";

    	if(accion.equalsIgnoreCase("Cancelar")){
    		view = "redirect:/login.jsp";
    		return view;
    	}

    	if(pass == null || repass == null){
    		LOGGER.error("Los campos están vacíos");
    		session.setAttribute("error", "Campo contraseña y/o reingreso de contraseña están vacíos");
    		view = "redirect:/change_pass.jsp?error=true";
    		return view;
    	}
    	if(!pass.equals(repass)){
    		LOGGER.error("Las contraseñas no coinciden");
    		session.setAttribute("error", "Contraseñas no coinciden");
    		view = "redirect:/change_pass.jsp?error=true";
    		return view;
    	}

        SqlSessionFactory sqlSessionFactory = null;
        SqlSession oSession = null;
        ValidateDAO oDatosIn = new ValidateDAO();
        try {
            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        try{
	        oSession = sqlSessionFactory.openSession();
	        oDatosIn.setCAMPO(pass);
	        oSession.selectOne("VALIDA_PASSWORD_PRE_AD", oDatosIn);
	        LOGGER.info("Estado: "+oDatosIn.getESTADO());
	        LOGGER.info("Descripcion: "+oDatosIn.getDESCRIPCION());
	        if(oDatosIn.getESTADO() == 0){
	        	session.setAttribute("newpassword", pass);
	        	view = "redirect:/validateToken.jsp";
	        	String identificador = loginSvc.crearDesafio(userVO.getRut());
	            userVO.setIdentificadorSMS(identificador);
	        }else{
	            LOGGER.error("Error al validar la constraseña: "+oDatosIn.getDESCRIPCION());
	            session.setAttribute("error", oDatosIn.getDESCRIPCION());
	            view = "redirect:/change_pass.jsp?error=true";
	        }
	        try {
	            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	        } catch (Exception ee) {
	            ee.printStackTrace();
	        }
	    }catch (Exception iE){
	        iE.printStackTrace();
	        session.setAttribute("error", "");
	        LOGGER.error("Error durante el ingreso:", iE);
	    }
        finally{
            try {
                oSession.close();
            } catch (Exception oE) {
                oE.printStackTrace();
                LOGGER.error("Error: "+oE);
            }
        }
        return view;
    }

}

