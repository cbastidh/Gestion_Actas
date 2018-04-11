package cl.bice.gestionactas.test;

import cl.bice.gestionactas.web.springmvc.acts.viewvo.MultipleDocViewVO;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 5/9/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class ValidarDatos {

    @Test
    public void largoRut(){
        String rut = "12015416k";
        String temp = "0000000000".substring(rut.length()) + rut;
        System.out.println(temp);
    }

    @Test
    public void convertJson(){
        MultipleDocViewVO multipleDocViewVO = new MultipleDocViewVO();
        List<String> l = new ArrayList<>();
        l.add("t");
        l.add("p");
        multipleDocViewVO.setDocuments(l);
        Gson gson = new Gson();
        String p = gson.toJson(multipleDocViewVO);
        System.out.println(p);
    }
}
