package cl.bice.gestionactas.web.springmvc.desafio;

import cl.bice.gestionactas.ejb.security.EstadoDesafioVO;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.factory.MyBatisConnectionFactory;
import cl.bice.gestionactas.web.springmvc.changepass.ValidateDAO;
import cl.bice.gestionactas.web.springmvc.common.GenericController;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ValidarToken extends GenericController{
    private static final Logger LOGGER = Logger.getLogger(ValidarToken.class);
    @WebInjected
    LoginSvc loginSvc;

    @RequestMapping(value = "/auth/validartoken", method = RequestMethod.POST)
    public String verifyCode(HttpServletRequest request,
    						 @RequestParam("textcode") String code,
    						 @RequestParam(value = "accion", required = false) String accion){
        String view = "redirect:/validateToken.jsp?error=true";
        HttpSession session = request.getSession();
        UserVO user = (UserVO)session.getAttribute("user");
        String pass = "";
        SqlSessionFactory sqlSessionFactory = null;
        SqlSession oSession = null;
        ValidateDAO oDatosIn = new ValidateDAO();
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
        if(session.getAttribute("newpassword")!=null)
        	pass = (String) session.getAttribute("newpassword");
        if(accion.equalsIgnoreCase("Cancelar")){
        	view = "redirect:/login.jsp";
    		return view;
    	}
        try {
            EstadoDesafioVO desafioVO = VerificadorDesafioSMS.verifyCode(user, code, loginSvc);
            if (desafioVO.isEstado()) {
                //grantAuthority();
                if(!pass.isEmpty() && !pass.equals(null)){
                	//String response = loginSvc.changePass(user.getName(), pass);
                	String response = loginSvc.changePass(user.getLoginAD(), pass);
                	LOGGER.info("Cambio clave: "+response);
                	if(response.contains("Error")){
                		String error = "No se pudo editar contraseña en ActiveDirectory";
                		view = "redirect:/change_pass.jsp?error=true";
                		session.setAttribute("error", error);
                		LOGGER.info("Problemas durante el cambio de contraseña: "+response);
                	}
                	else{
                		view = "redirect:/succes.jsp";
                		
	                	try {
	        	            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	        	        } catch (Exception ee) {
	        	            ee.printStackTrace();
	        	        }
	        	        try{
	        	        	//String rut = authenticate(authentication);
	        	        	//String rut = reformatRut(user.getRut());
	        	        	LOGGER.info("Rut usuario: "+user.getRut());
	        		        oSession = sqlSessionFactory.openSession();
	        		        oDatosIn.setCAMPO(user.getRut());
	        		        oSession.selectOne("ACTUALIZA_PRIMER_LOGIN", oDatosIn);
	        		        LOGGER.info("Estado: "+oDatosIn.getESTADO());
	        		        if(oDatosIn.getESTADO() == 0){
	        		        	LOGGER.info("Estado primer login actualizado correctamente");
	        		        }
	        		        else{
	        		        	LOGGER.info("Estado primer login no se pudo actualizar correctamente: "+oDatosIn.getDESCRIPCION());
	        		        }
	        		        try {
	        		            sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	        		        } catch (Exception ee) {
	        		            ee.printStackTrace();
	        		        }
	        		    }catch (Exception iE){
	        		        iE.printStackTrace();
	        		        LOGGER.error("Error durante la validación de primer ingreso:", iE);
	        		    }
	        	        finally{
	        	            try {
	        	                oSession.close();
	        	            } catch (Exception oE) {
	        	                oE.printStackTrace();
	        	                LOGGER.error("Error: "+oE);
	        	            }
	        	        }
                	}
                }
                else{
                	LOGGER.error("Error al obtener la nueva password desde la sesión");
                	session.setAttribute("error", "Error al obtener password desde sesión");
                	view = "redirect:/change_pass.jsp?error=true";
                }
            }else{
            	String identificador = loginSvc.crearDesafio(user.getRut());
	            user.setIdentificadorSMS(identificador);
                view = "redirect:/validateToken.jsp?error=true&msg="+desafioVO.getGlosa();
            }
        } catch (NumberFormatException e) {
            LOGGER.debug("error al convertir el tipo de dato a entero {}" + e.getMessage());
            view = "redirect:/validateToken.jsp?error=true";
        } catch (VerificationCodeException e) {
            LOGGER.debug("error al realizar la verificacion de codigo {}" + e.getMessage());
            view = "redirect:/validateToken.jsp?error=true";
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
