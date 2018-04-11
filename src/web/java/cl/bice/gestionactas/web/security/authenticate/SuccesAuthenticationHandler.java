package cl.bice.gestionactas.web.security.authenticate;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.svc.UserSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.factory.MyBatisConnectionFactory;
import cl.bice.gestionactas.web.factory.WebFactory;
import cl.bice.gestionactas.web.springmvc.changepass.ChangePassController;
import cl.bice.gestionactas.web.springmvc.changepass.ValidateDAO;

public class SuccesAuthenticationHandler implements AuthenticationSuccessHandler{
	private static final Logger logger = Logger.getLogger(SuccesAuthenticationHandler.class);

	    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, 
	      HttpServletResponse response, Authentication authentication) throws IOException {
	        handle(request, response, authentication);
	        //clearAuthenticationAttributes(request);
	    }

	    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
	        String targetUrl = determineTargetUrl(authentication);
	 
	        if (response.isCommitted()) {
	            logger.debug("Respuesta ya enviada. No se puede redirigir a " + targetUrl);
	            return;
	        }

	        redirectStrategy.sendRedirect(request, response, targetUrl);
	    }
	 
	    protected String determineTargetUrl(Authentication authentication) {
	    	LoginSvc loginSvc = WebFactory.getBean(LoginSvc.class);

	    	String view = "/login.jsp?error=true";
	    	SqlSessionFactory sqlSessionFactory = null;
	        SqlSession oSession = null;
	        ValidateDAO oDatosIn = new ValidateDAO();
	        UserVO userVO = new UserVO();
	        UserSvc userSvc = WebFactory.getBean(UserSvc.class);
	        try {
	            userVO = userSvc.obtenerUsuarioPorNombre(authentication.getName());
	        } catch (UserBiceException e) {
	        	e.printStackTrace();
	        	logger.error("Error de usuario", e);
	        	return view;
	        }

	        try {
	            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	        } catch (Exception ee) {
	            ee.printStackTrace();
	        }
	        try{
	        	//String rut = reformatRut(userVO.getRut());
		        oSession = sqlSessionFactory.openSession();
		        oDatosIn.setCAMPO(userVO.getRut());
		        oSession.selectOne("ES_PRIMER_LOGIN", oDatosIn);
		        logger.info("Estado: " + oDatosIn.getESTADO());
		        logger.info("¿Es primer login?: " + oDatosIn.getDESCRIPCION());
		        if(oDatosIn.getESTADO() == 0){
		        	view = "/change_pass.jsp";
		        }else{
		        	if(oDatosIn.getESTADO() == 1){
		        		//view = "/securitytoken.jsp";
		        		try {
		                    logger.debug("usuario --> " + authentication.getName());
		                    userVO = loginSvc.login(authentication.getName(), authentication.getCredentials().toString());
		                    if (userVO == null){
		                        throw new UsernameNotFoundException("error");
		                    }
		                    else
		                    	view = "/securitytoken.jsp";
		                } catch (UserBiceException e) {
		                	e.printStackTrace();
		                    logger.info("no se ha podido autenticar el usuario "+ e.getMessage());
		                }
		        	}
		        	else{
		        		System.out.println("No se pudo redirigir, problemas al verificar primer ingreso: "+oDatosIn.getDESCRIPCION());
		        		logger.error("No se pudo redirigir, problemas al verificar primer ingreso: "+oDatosIn.getDESCRIPCION());
			            view = "/login.jsp?error=true";
		        	}
		        }
		    }catch (Exception iE){
		        iE.printStackTrace();
		        logger.error("Error durante la validación de primer ingreso:", iE);
		    }
	        finally{
	            try {
	                oSession.close();
	            } catch (Exception oE) {
	                oE.printStackTrace();
	                logger.error("Error: "+oE);
	            }
	        }
	        return view;
	    }

	    /*public String reformatRut(String rut){
	    	while(rut.startsWith("0")){
	    		rut = rut.substring(1);
	    	}
	    	String rutdv = rut.substring(rut.length()-1);
	    	rut = rut.substring(0, rut.length()-1);
	    	rut = rut.concat("-"+rutdv);
	    	return rut;
	    }*/
	 
	    protected void clearAuthenticationAttributes(HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        if (session == null) {
	            return;
	        }
	        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	    }
	 
	    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
	        this.redirectStrategy = redirectStrategy;
	    }
	    protected RedirectStrategy getRedirectStrategy() {
	        return redirectStrategy;
	    }

}
