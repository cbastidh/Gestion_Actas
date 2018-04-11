package cl.bice.gestionactas.spring.interceptor;

import cl.bice.gestionactas.spring.factory.AnnotationProccessor;
import cl.bice.gestionactas.spring.factory.FactoryProccessor;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.factory.WebFactory;
import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 27-11-14.
 * Time: 13:19
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: Gestion de Actas
 */
public class UserWebArgumentResolver implements HandlerMethodArgumentResolver {
    Logger logger = Logger.getLogger(UserWebArgumentResolver.class);
    Map<String, Integer> maps = new HashMap<String, Integer>();
    Annotation obj;

    public UserWebArgumentResolver(){
        maps.put("ContextoWeb", 0);
        maps.put("WebInjectorSvc", 1);
        maps.put("SearchWeb", 2);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        for (Annotation annotation : annotations){
            boolean b = maps.containsKey(annotation.annotationType().getSimpleName());
            if (b){
                obj = annotation;
                return  true;
            }
        }
        return  false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,  WebDataBinderFactory binderFactory) throws Exception {
        if (this.supportsParameter(methodParameter)) {
            logger.debug("Method intercepted " + methodParameter.getMethod().getName());
            logger.debug("Parameter intercepted " + methodParameter.getParameterName());
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            HttpSession session = request.getSession(false);
            switch (maps.get(obj.annotationType().getSimpleName())){
                case 0:
                    WebUser webUser = (WebUser) session.getAttribute("webuser");
                    if (webUser != null){
                        return webUser;
                    }
                    FactoryProccessor<WebUser> factoryProcessor =  AnnotationProccessor.processor(ContextoWeb.class);
                    webUser = factoryProcessor.process(session);
                    session.setAttribute("webuser", webUser);
                    return webUser;
                case 1:
                    Class clazz = ((WebInjected) obj).clazz();
                    Object o = WebFactory.getBean(clazz);
                    return o;
                case 2:
                    return null;
                default:
                    return WebArgumentResolver.UNRESOLVED;
            }

        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
    }

}
