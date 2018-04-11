package cl.bice.gestionactas.web;

import cl.bice.gestionactas.cliente.security.encript.TEA;
import cl.bice.gestionactas.ejb.vo.ContextVO;
import cl.bice.gestionactas.ejb.vo.FiliationVO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import java.io.Serializable;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Date: 27-11-14.
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actual
 */
public class WebUser implements Serializable {
    private UserVO usuario;
    private FiliationVO filiation;
    private int filiations;
    private TimeZone timeZone;
    private String uuid;
    transient private TEA tea;

    public WebUser() {
        uuid = UUID.randomUUID().toString().substring(0, 3);
        tea = getTea();
    }

    public WebUser(UserVO _usuario) {
        usuario = _usuario;
        uuid = UUID.randomUUID().toString().replace("-","").substring(0, 16);
        tea = getTea();
    }

    public UserVO getUsuario() {
        return usuario;
    }

    public ContextVO toContextVO(){
        return new ContextVO(usuario.getId(), null);
    }


    public String encript(Long l){
        return  getTea().encipher(l);
    }


    public String encript(Integer l){
        return  getTea().encipher(l);
    }

    public String encript(String l){
        return  getTea().encipher(l);
    }

    public Long decript(String t){
        return getTea().decipher(t);
    }

    public String decript(String t, Boolean p){
        return getTea().decipher(t, true);
    }

    private TEA getTea(){
        if (tea == null){
            tea = new TEA(uuid.getBytes());
        }
        return tea;
    }


    @Override
    public String toString() {
        return "WebUser{" +
                "usuario=" + usuario.getName() +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
