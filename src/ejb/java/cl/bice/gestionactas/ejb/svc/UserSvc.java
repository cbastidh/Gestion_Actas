package cl.bice.gestionactas.ejb.svc;

import cl.bice.gestionactas.ejb.remote.bean.UserBiceException;
import cl.bice.gestionactas.ejb.vo.UserDocumentVisibilityVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;

import java.util.List;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public interface UserSvc {
    /**
     * Metodo para crear un nuevo usuario en la base de datos de actas
     * @param userVO objeto vo desde presentacion con los datos para crear
     * @return devuelve un vo con el id lleno
     */
    UserVO createUser(UserVO userVO);

    /**
     * Lista usuarios con los criterios seleccionados en la capa de presentacion
     * @param query criterios de seleccion
     * @return objeto con la paginacion y los registros
     */
    PageVO<UserVO> listarUsuariosPorCriterios(PageQueryMapVO query);

    /**
     * Lista los tipos de documentos para asignar la visibilidad de los mismos a traves de la pantalla
     * @param userId id del usuario
     * @return devuelve el listado de visibilidad de un usuario (si no viene ninguno es que no puede ver nada)
     */
    List<UserDocumentVisibilityVO> listarVisibilidadDocumentos(Long userId);

    /**
     * Modifica los atributos de visibilidad de un usuario, determina cual documento puede ver o no
     * @param id id de la visibilidad, si existiera significa que se esta eliminado o modificando.
     * @param userId id del usuario
     * @param docTypeId id del tipo de documento al que se le esa asignando la visibilidad
     * @param newStatus el estado de la visibilidad
     * @return devuelve el nuevo estado.
     */
    Boolean updateUserDocumentVisibility(Long id, Long userId, Long docTypeId, Boolean newStatus);

    /**
     * Obtiene un usuario por su nombre de usurio ldap
     * @param username Nombre del usuario
     * @return devuelve el objeto UserVO
     * @throws UserBiceException determina un error de sincronizacion entre el ldap y la base de datos de la aplicacion
     */
    UserVO obtenerUsuarioPorNombre(String username) throws UserBiceException;


    /**
     * Elimina un usuario de la base de datos de gestion de actas, con respecto al ldap no se hace ninguna operacion.
     * @param id ID del usuario a eliminar
     * @return devuelve verdado al marcar el usuario como eliminado
     * @throws UserBiceException determina un error de sincronizacion entre el ldap y la base de datos de la aplicacion
     */
    boolean deleteUser(Long id) throws UserBiceException;
}