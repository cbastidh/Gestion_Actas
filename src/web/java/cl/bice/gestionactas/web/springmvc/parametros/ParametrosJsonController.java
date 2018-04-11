package cl.bice.gestionactas.web.springmvc.parametros;

import cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import cl.bice.gestionactas.web.springmvc.common.ResultEnum;
import cl.bice.gestionactas.web.springmvc.common.WebResultVO;
import cl.bice.gestionactas.web.viewvo.TipoDocumentoViewVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2/12/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Controller
@RequestMapping(value = "/parametros/json")
public class ParametrosJsonController extends GenericController {
    @WebInjected
    ParametrosEstaticosSvc parametrosEstaticosSvc;


    @RequestMapping(value = "/tipodocumento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WebResultVO<TipoDocumentoViewVO> obtenerTipoDocumento(@ContextoWeb WebUser user){
        WebResultVO<TipoDocumentoViewVO> resultVO = new WebResultVO<>();
        List<TipoDocumentoViewVO> voList = new ArrayList<>();

        List<TipoDocumentoVO> tipoDocumentoVOs = parametrosEstaticosSvc.obtenerTipoDocumentos();
        for (TipoDocumentoVO tipoDocumentoVO : tipoDocumentoVOs) {
            TipoDocumentoViewVO documentoViewVO = new TipoDocumentoViewVO();
            documentoViewVO.setId(user.encript(tipoDocumentoVO.getId()));
            documentoViewVO.setNmonic(tipoDocumentoVO.getNmonic());
            documentoViewVO.setTipoActa(tipoDocumentoVO.getTipoActa());
            voList.add(documentoViewVO);
        }
        resultVO.setJsonCmd(ResultEnum.SUCCESS);
        resultVO.setData(voList);
        return resultVO;
    }
}
