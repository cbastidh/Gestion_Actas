package cl.bice.gestionactas.web.springmvc.common;

import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public class GenericController implements Serializable{

    protected Map<String, String> mapNav = new HashMap<String, String>();

    protected ModelAndView modelAndView(String target){
        ModelAndView modelAndView = new ModelAndView(mapNav.get(target));
        return  modelAndView;
    }
}
