package cl.bice.gestionactas.ejb.security;

import cl.bice.gestionactas.ejb.dao.UserDAO;
import cl.bice.gestionactas.ejb.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/24/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class DummySecurityProvider extends SecurityProvider {

    public DummySecurityProvider(Boolean entrustActive) {
        super(entrustActive);
    }

    public DummySecurityProvider() {
    }

    @Override
    public UserVO validateUser(String user, String pass, UserDAO userDAO) {
        Map<String, String> usuarios = new HashMap<>();
        usuarios.put("admin", "system");
        usuarios.put("view", "system");
        usuarios.put("csepulveday_qa", "system");
        usuarios.put("fleon_qa", "system");
        UserVO userVO = null;
        boolean p = this.isEntrustActive(user);
        if (usuarios.containsKey(user) & pass.equals("system") ){
            switch (user){
                case "admin":
                    userVO = new UserVO(0l, "Usuario Desa", "0120154168", "admin");
                    break;
                case "view":
                    //userVO = new UserVO(0l, "Usuario Desa", "0120154168", "view");
                    userVO = new UserVO(0l, "View", "0120154168", "view");
                    break;
                case "csepulveday_qa":
                    userVO = new UserVO(0l, "Charles Sepulveda", "0120154168", "csepulveday_qa");
                    break;
                case "fleon_qa":
                    userVO = new UserVO(0l, "Felipe Leon", "0139230566", "fleon_qa");
                    break;
            }
        }else {
            return null;
        }

        return  userVO;

    }

    @Override
    public List<String> retreiveRoles(String username, String password) {
        List<String> roles = new ArrayList<>();
        if (username.equals("admin") || username.equals("csepulveday_qa")) {
            roles.add("ROLE_ADMINISTRATOR");
            roles.add("ROLE_VIEW");
        }else {
            roles.add("ROLE_VIEW");
        }
        return  roles;
    }

	@Override
	public String changePass(String username, String password) {
		return "";
	}
}
