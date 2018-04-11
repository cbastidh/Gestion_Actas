package cl.bice.gestionactas.ejb.svc;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.security.EstadoDesafioVO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import java.util.List;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface LoginSvc {
    /**
     * Permite autenticar el usuario en el proveedor de identidades y validar si el usuario tiene soporte
     * para SMS en entrustBice
     * @param username nombre de usuario
     * @param password contrasena del usuario generada en el form spring
     * @return si el usuario se autentico devuelve el usuario generado
     */
    UserVO login(String username, String password) throws UserBiceException;
    
    /**
     * Permite autenticar el usuario en el proveedor de identidades y validar si el usuario tiene soporte
     * para SMS en entrustBice, no genera token de autenticación
     * @param username nombre de usuario
     * @param password contrasena del usuario generada en el form spring
     * @return si el usuario se autentico devuelve el usuario generado
     */
    UserVO loginNoToken(String username, String password) throws UserBiceException;

    /**
     * Obtiene los roles de un usuario
     * @param username Nombre de usuario ldap
     * @param password password del usuario en ldap
     * @return Roles asociados al usuario
     */
    List<String> obtenerRoles(String username, String password);

    /**
     * Permite realizar la validacion realizada por el usuario (el ping ingresado) y lo que ha generado el servidor
     *
     * @param hash
     * @param keySMS
     * @return
     */
    EstadoDesafioVO validaDesafio(UserVO userVO, String respuestadesafio);

    /**
     * Permite realizar el cambio de contraseña en AD
     *
     * @param username Usuario al que se desea cambiar la contraseña
     * @param password Nueva contraseña que se requiere ingresar
     */
	String changePass(String username, String password);

	/**
     * Permite crear un desafío para el usuario, utiliza el rut del usuario
     *
     * @param userID Rut del usuario al que se creará el desafío
     * @return devuelve el token, vacio o null, dependiendo del caso.
     */
	String crearDesafio(String userID);
	
	/**
     * Valida que el número de celular ingresado corresponde al usuario
     *
     * @param user Login del usuario ingresado en pantalla
     * @param numCelula Número de celular ingresado en pantalla
     * @return devuelve true si los datos se corresponden, false si falla la validación
     */
	boolean validarCelular(String user, String numCelular);
}