package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.UserBO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.ejb.dao.*;

import java.util.List;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public interface UserDAO extends GenericDAO<Long, UserBO> {

    @GenericQuery(query = "select count(f.id) from UserBO f where f.enabled = :status and u.estado = 1")
    Integer countUserByStatus(@GenericWhere(element = ":status") Boolean status);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.UserVO(u.id, u.name, u.rut, u.email) from UserBO u where u.estado = 1")
    @GenericSort(element = "u.name")
    List<UserVO> userByStatus(@GenericWhere(element = "u.enabled") Boolean status,
                                   @GenericWhere(element = "*first") int first,
                                   @GenericWhere(element = "*max") int max);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.UserVO(u.id, u.name, u.rut, u.loginAD) from UserBO u where lower(u.name) like :name or lower(u.loginAD) like :login and u.estado = 1 order by :field")
    List<UserVO> listarUsuariosPorCriterios(@GenericWhere(element = ":name", optional = true) String name,
                                            @GenericWhere(element = ":login", optional = true) String loginAD,
                                            @GenericWhere(element = ":field", optional = true) String field,
                                            @GenericWhere(element = "*first") int first,
                                            @GenericWhere(element = "*max") int max);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.UserVO(u.id, u.name, u.rut, u.loginAD) from UserBO u where lower(u.name) like :name and u.estado = 1 order by :field")
    List<UserVO> listarUsuariosPorNombre(@GenericWhere(element = ":name", optional = true) String name,
                                            @GenericWhere(element = ":field", optional = true) String field,
                                            @GenericWhere(element = "*first") int first,
                                            @GenericWhere(element = "*max") int max);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.UserVO(u.id, u.name, u.rut, u.loginAD) from UserBO u where lower(u.loginAD) like :login and u.estado = 1 order by :field")
    List<UserVO> listarUsuariosPorLogin(@GenericWhere(element = ":login", optional = true) String loginAD,
                                            @GenericWhere(element = ":field", optional = true) String field,
                                            @GenericWhere(element = "*first") int first,
                                            @GenericWhere(element = "*max") int max);

    @GenericQuery(query = "select new cl.bice.gestionactas.ejb.vo.UserVO(u.id, u.name, u.rut, u.loginAD) from UserBO u where u.estado = 1 order by :field")
    List<UserVO> listarUsuariosPorSinParametro(@GenericWhere(element = ":field", optional = true) String field,
                                            @GenericWhere(element = "*first") int first,
                                            @GenericWhere(element = "*max") int max);

    @GenericQuery(query = "select count(u.id) from UserBO u where lower(u.name) like :name or lower(u.loginAD) like :login and u.estado = 1")
    Integer countListarUsuariosPorCriterios(@GenericWhere(element = ":name", optional = true) String name,
                                            @GenericWhere(element = ":login", optional = true) String loginAD);

    @GenericQuery(query = "select u from UserBO u where lower(u.loginAD) = :login and u.estado = 1")
    UserBO obtenerUsuario(@GenericWhere(element = ":login", optional = false) String username);
}
