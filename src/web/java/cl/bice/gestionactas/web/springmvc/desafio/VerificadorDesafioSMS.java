package cl.bice.gestionactas.web.springmvc.desafio;

import cl.bice.gestionactas.ejb.security.EstadoDesafioVO;
import cl.bice.gestionactas.ejb.svc.LoginSvc;
import cl.bice.gestionactas.ejb.vo.UserVO;

/**
 * Date: 2/8/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class VerificadorDesafioSMS {
    public static EstadoDesafioVO verifyCode(UserVO userVO, String code, LoginSvc loginSvc) throws VerificationCodeException{
        return loginSvc.validaDesafio(userVO, code);
    }
}
