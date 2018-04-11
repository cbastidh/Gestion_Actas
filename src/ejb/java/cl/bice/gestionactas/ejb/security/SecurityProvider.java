package cl.bice.gestionactas.ejb.security;

import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Date: 2/24/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public abstract class SecurityProvider {
    public static final Logger LOGGER = Logger.getLogger(SecurityProvider.class);
    Boolean entrustActive = false;
    String ldapURL = "";
    String ldapURLCambioPass = "";
    String ldapUserDN = "";
    String ldapPass = "";
    String ldapBaseDN = "";
    String ldapBase = "";
    

	public SecurityProvider(Boolean entrustActive){
        this.entrustActive = entrustActive;
    }

    public SecurityProvider(){
        entrustActive = false;
    }

    /**
     * Valida la existencia de un usuario en base a los datos de usuario y password, proporcionados por la capa de presentacion
     * @param user Nombre de usuario que se ingreso en la pantalla de login
     * @param pass Password que el usuario ingreso
     * @return si el usuario existe devuelve el objeto UserVO lleno, sino un null
     */
    public abstract UserVO validateUser(String user, String pass, UserDAO userDAO);

    /**
     * Una vez validado el usuario y validado el sms se obtiene los roles
     * @param username nombre de usuario
     * @param password password del usuario
     * @return lista de roles
     */
    public abstract List<String> retreiveRoles(String username, String password);


    /**
     * Determina si esta activo el sistema de desafio y en caso de ser afirmativo valida si el usuario esta en Entrust Comite
     * @param username Nombre de usuario (el que el usuario ingreso al momento de logearse
     * @return si el sistema entrust no esta activo devuelve true, en caso contrario se hace la validacion que aporta la clase DesafioSFABice
     */
    protected boolean isEntrustActive(String username){
        return (entrustActive ? DesafioSFABice.isEntrustComite(username) : true);
    }

    /**
     * Genera un token de desafio par el usuario, para luego ser validado con la segunda clave.
     * @param userid Id del usuario, esto debe venir del ldap, ya que es su RUT
     * @return devuelve el token, vacio o null, dependiendo del caso.
     */
    public  String identificadorDesafio(String userid){
        if (!entrustActive){
            return "";
        }else{
            return DesafioSFABice.tokenDesafio(userid);
        }
    }

    public EstadoDesafioVO validaDesafio(String userid, String respuestaDesafio){
        if (!entrustActive){
            EstadoDesafioVO estadoDesafioVO = new EstadoDesafioVO(respuestaDesafio.equalsIgnoreCase("1234"), "0", "Error al tratar de verificar el codigo");
            return estadoDesafioVO;
        }else{
            return DesafioSFABice.validarDesafio(userid, respuestaDesafio);
        }
    }
    
    public abstract String changePass(String username, String password);

    public boolean validaNumeroCelular(String user, String numCelular) {
    	return DesafioSFABice.listarIdentificador(user, numCelular);
    }

    public String getLdapURL() {
        return ldapURL;
    }

    public void setLdapURL(String ldapURL) {
        this.ldapURL = ldapURL;
    }
    
    public String getLdapURLCambioPass() {
        return ldapURLCambioPass;
    }

    public void setLdapURLCambioPass(String ldapURLCambioPass) {
        this.ldapURLCambioPass = ldapURLCambioPass;
    }


    public String getLdapUserDN() {
        return ldapUserDN;
    }

    public void setLdapUserDN(String ldapUserDN) {
        this.ldapUserDN = ldapUserDN;
    }

    public String getLdapPass() {
        byte[] decode = Base64.decodeBase64(ldapPass.getBytes());
        String d = new String(decode);
        return d;
    }

    public void setLdapPass(String ldapPass) {
        this.ldapPass = ldapPass;
    }

    public String getLdapBaseDN() {
        return ldapBaseDN;
    }

    public void setLdapBaseDN(String ldapBaseDN) {
        this.ldapBaseDN = ldapBaseDN;
    }
    
    public String getLdapBase() {
		return ldapBase;
	}

	public void setLdapBase(String ldapBase) {
		this.ldapBase = ldapBase;
	}

}
