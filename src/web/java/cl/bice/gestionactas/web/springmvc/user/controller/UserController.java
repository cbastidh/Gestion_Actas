package cl.bice.gestionactas.web.springmvc.user.controller;

import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
@Controller
@RequestMapping(value = "/main/users")
public class UserController extends GenericController {

    public UserController(){
        mapNav.put("list", "user/list");
        mapNav.put("new", "user/new");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showPage(@ContextoWeb WebUser user){
        return modelAndView("list");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showNewPage(@ContextoWeb WebUser user){
        ModelAndView mav = new ModelAndView(mapNav.get("new"));
        mav.addObject("userId", user.encript(0l));
        return mav;
    }
}
