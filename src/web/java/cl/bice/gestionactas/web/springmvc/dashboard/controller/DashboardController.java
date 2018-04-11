package cl.bice.gestionactas.web.springmvc.dashboard.controller;

import cl.bice.gestionactas.ejb.svc.MenuSvc;
import cl.bice.gestionactas.ejb.type.RoleEnum;
import cl.bice.gestionactas.ejb.vo.MenuVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Controller
public class DashboardController extends GenericController {

    @WebInjected
    MenuSvc menuSvc;

    @RequestMapping(value = "/main/dashboard")
    public ModelAndView showDashBoard(Model model, HttpSession session, @ContextoWeb WebUser webUser) {
        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("menu", createMenu());
        String uuid = UUID.randomUUID().toString();
        session.setAttribute("uuid", uuid);
        return mav;
    }

    private List<MenuVO> createMenu(){
        List<GrantedAuthority> g = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<MenuVO> menuVOs;
        GrantedAuthority s = new SimpleGrantedAuthority("ROLE_".concat(RoleEnum.ADMINISTRATOR.getNombre()));
        if (g.contains(s)) {
            menuVOs = menuSvc.getMenu(null);
        }else{
            menuVOs = menuSvc.getMenu(RoleEnum.VISUALIZADOR.getNombre());
        }
        return menuVOs;
    }
}
