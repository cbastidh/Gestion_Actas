package cl.bice.gestionactas.spring;

import cl.bice.gestionactas.spring.interceptor.UserWebArgumentResolver;
import org.apache.log4j.Logger;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Date: 27-11-14.
 * Time: 14:28
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: Gestion Actas
 */
//@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    Logger logger = Logger.getLogger(WebConfig.class);


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        logger.debug("Resolver Size  " + argumentResolvers.size());
        UserWebArgumentResolver resolver = new UserWebArgumentResolver();
        argumentResolvers.add(resolver);
    }
}
