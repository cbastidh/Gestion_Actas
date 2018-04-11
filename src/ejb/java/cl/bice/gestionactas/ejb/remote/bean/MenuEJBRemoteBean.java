package cl.bice.gestionactas.ejb.remote.bean;

import cl.bice.gestionactas.ejb.dao.MenuDAO;
import cl.bice.gestionactas.ejb.entity.MenuBO;
import cl.bice.gestionactas.ejb.interceptor.EJBInjectorInterceptor;
import cl.bice.gestionactas.ejb.interceptor.Injected;
import cl.bice.gestionactas.ejb.interceptor.WebModuleEntityInterceptor;
import cl.bice.gestionactas.ejb.remote.MenuEJBRemote;
import cl.bice.gestionactas.ejb.vo.MenuVO;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Stateless(name = "gab_MenuEJBRemote", mappedName = "gab_MenuEJBRemote")
@Interceptors({WebModuleEntityInterceptor.class, EJBInjectorInterceptor.class})
public class MenuEJBRemoteBean implements MenuEJBRemote {

    @Injected
    MenuDAO menuDAO;

    public MenuEJBRemoteBean(){
    }


    /**
     * @see cl.bice.gestionactas.ejb.svc.MenuSvc#getMenu(java.lang.String)
     */
    @Override
    public List<MenuVO> getMenu(String rol) {
        List<MenuBO> menus = menuDAO.getMenu(rol);

        Map<Long, MenuVO> map = new HashMap<Long, MenuVO>();
        MenuVO root = new MenuVO(0, null, null, null, null, null, null, null);
        map.put(null, root);

        for (MenuBO menu: menus) {
            map.put(menu.getId(), new MenuVO(menu.getOrder(), menu.getBookmark(), menu.getOptions(), menu.getLabel(), menu.getImage(), menu.getPage(), menu.getPrivilege(), menu.getBoostrapIcon()));
        }

        for (MenuBO menu: menus) {
            MenuVO vo = map.get(menu.getId());
            MenuVO parent = map.get(menu.getParentId());
            if (parent == null && menu.getParentId() != null) {
                throw new RuntimeException("menu id: " + menu.getId() + " has missing parent menu id: " + menu.getParentId());
            }
            parent.addSubmenu(vo);
        }

        root.sort();

        return root.getSubmenus();

    }
}
