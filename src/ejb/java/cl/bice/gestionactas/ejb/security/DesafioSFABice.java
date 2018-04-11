package cl.bice.gestionactas.ejb.security;

import cl.bice.gestionactas.ejb.factory.EJBFactory;
import cl.bice.ws.servicio.*;
import org.apache.log4j.Logger;

import javax.xml.ws.Holder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 5/5/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class DesafioSFABice implements Serializable {
    private static Integer TIPOCLIENTE = 5;
    private static Logger logger = Logger.getLogger(DesafioSFABice.class.getName());

    public static boolean isEntrustComite(String username){
        logger.info("Validando el Desafio");
        String newAddress = EJBFactory.getProperties("ws.listardesafio.url");
        if (newAddress.isEmpty()){
            logger.error("no se a agregado la direccion del ws listar desafio");
            throw new NullPointerException("No se agrego el valor ws.listardesafio.url en el archivo 'gestionactas-ejb-context.properties'");
        }
        newAddress = newAddress.concat("?wsdl");
        ListarDesafiosWSService listarDesafiosWS = new ListarDesafiosWSService(newAddress);
        ListarDesafiosWS listarDesafiosWS1 = listarDesafiosWS.getListarDesafiosWSPort();

        Holder<EstadoType> holderEstadoType = new Holder<EstadoType>();
        Holder<DesafiosType> holderDesafiosType = new Holder<DesafiosType>();
        listarDesafiosWS1.listarDesafios(username, TIPOCLIENTE.toString(), holderEstadoType, holderDesafiosType);
        logger.info("Obtener el holder desafio ");
        if (holderDesafiosType != null) {
            logger.info("Estado Desafio  " + holderEstadoType.value.getCodigo());
            logger.info("Glosa desafio  " + holderEstadoType.value.getGlosa());
        }else{
            logger.info("Error en el entrust");
        }
        if (holderEstadoType.value.getCodigo().equals("0")){
            List<DesafioType> desafioTypes = holderDesafiosType.value.getDesafio();
            if (desafioTypes.isEmpty()){
                return false;
            }
            logger.info("Validar el tipo de desafio");
            for (DesafioType desafioType : desafioTypes){
                if(desafioType.getTipoDesafio().equalsIgnoreCase("sms")){
                    return true;
                }
            }
        }
        return false;
    }


    public static String tokenDesafio(String usuarioId){
        String token = null;
        String newAddress = EJBFactory.getProperties("ws.desafio.url");
        if (newAddress.isEmpty()){
            logger.error("no se a agregado la direccion del ws crear desafio");
            throw new NullPointerException("No se agrego el valor ws.desafio.url en el archivo 'gestionactas-ejb-context.properties'");
        }
        newAddress = newAddress.concat("?wsdl");

        CrearDesafioWSService crearDesafioWSService = new CrearDesafioWSService(newAddress);
        CrearDesafioWS crearDesafioWS = crearDesafioWSService.getCrearDesafioWSPort();

        MensajeDesafioType mensajeDesafioType = new MensajeDesafioType();
        AtributoMensajeType atributoMensajeType = new AtributoMensajeType();
        mensajeDesafioType.getAtributoMensaje().add(atributoMensajeType);
        Holder<EstadoType> estadoTypeHolder = new Holder<>();
        Holder<DesafiosType> desafiosTypeHolder = new Holder<>();

        crearDesafioWS.crearDesafio(usuarioId, "", TIPOCLIENTE.toString(),
                "0", TipoDesafioEnum.SMS.name(), "",
                mensajeDesafioType, estadoTypeHolder, desafiosTypeHolder);
        if (estadoTypeHolder.value != null && estadoTypeHolder.value.getCodigo().equals("0")){
            DesafiosType desafiosTypes = desafiosTypeHolder.value;
            if (desafiosTypes.getDesafio().isEmpty()){
                return null;
            }else {
                for(DesafioType desafioType : desafiosTypes.getDesafio()){
                    if (desafioType.getTipoDesafio().equalsIgnoreCase("sms")){
                        token = desafioType.getIdentificador();
                        break;
                    }
                }
            }
        }
        return token;
    }

    public static EstadoDesafioVO validarDesafio(String usuarioId, String respuestaDesafio){
        String newAddress = EJBFactory.getProperties("ws.autenticar.url");
        String mensajesDesafio = EJBFactory.getProperties("entrust.mensajes"); //Problemas con el desafío
        String[] listMensajes = mensajesDesafio.split("~");
        Map<String, String> mapMensajes = new HashMap<>();
        if (listMensajes != null){
            for(String s : listMensajes){
                String[] m = s.split("-");
                if (m != null && m.length == 2){
                    mapMensajes.put(m[0], m[1]);
                }
            }
        }
        if (newAddress.isEmpty()){
            logger.error("no se a agregado la direccion del ws autenticar desafio");
            throw new NullPointerException("No se agrego el valor ws.autenticar.url en el archivo 'gestionactas-ejb-context.properties'");
        }
        newAddress = newAddress.concat("?wsdl");
        AutentificarWSService autentificarWSService = new AutentificarWSService(newAddress);
        AutentificarWS autentificarWS = autentificarWSService.getAutentificarWSPort();
        //Cambio dinamico de URL

        String canal = "bancapersonas";
        String mensajeUsuario = "";
        MensajeDesafioType mensajeDesafioType = new MensajeDesafioType();
        mensajeDesafioType.getAtributoMensaje().add(new AtributoMensajeType());
        logger.debug("Validar desafio para el usuario --> " + "0" + usuarioId);
        EstadoType estadoType = autentificarWS.autentificar(usuarioId, TIPOCLIENTE.toString(), "0",
                TipoDesafioEnum.SMS.name(), respuestaDesafio, canal,
                null, null);
        logger.debug("codigo de validacion ->" + estadoType.getCodigo());
        logger.debug("glosa validacion     -> " + estadoType.getGlosa());
        EstadoDesafioVO estadoDesafioVO = new EstadoDesafioVO();
        if (estadoType != null){
            if (estadoType.getCodigo().equalsIgnoreCase("0")){
                estadoDesafioVO.setEstado(true);
                estadoDesafioVO.setCodigo(estadoType.getCodigo());
                estadoDesafioVO.setGlosa(estadoType.getGlosa());
            }else{
                estadoDesafioVO.setEstado(false);
                estadoDesafioVO.setCodigo(estadoType.getCodigo());
                String g = mapMensajes.get(estadoType.getCodigo().toLowerCase());
                if (g != null){
                    estadoDesafioVO.setGlosa(g);
                }else {
                    estadoDesafioVO.setGlosa("Problemas con el desafio");
                }
            }
        }else {
            estadoDesafioVO.setEstado(false);
            estadoDesafioVO.setGlosa("Error en lectura de estado");
            estadoDesafioVO.setCodigo("-1");
        }

        return  estadoDesafioVO;
    }
    
    public static boolean listarIdentificador(String userRut, String num){
        logger.info("Validando el Desafio");
        String newAddress = EJBFactory.getProperties("ws.listardesafio.url");
        if (newAddress.isEmpty()){
            logger.error("no se a agregado la direccion del ws listar desafio");
            throw new NullPointerException("No se agrego el valor ws.listardesafio.url en el archivo 'gestionactas-ejb-context.properties'");
        }
        newAddress = newAddress.concat("?wsdl");
        ListarDesafiosWSService listarDesafiosWS = new ListarDesafiosWSService(newAddress);
        ListarDesafiosWS listarDesafiosWS1 = listarDesafiosWS.getListarDesafiosWSPort();

        Holder<EstadoType> holderEstadoType = new Holder<EstadoType>();
        Holder<DesafiosType> holderDesafiosType = new Holder<DesafiosType>();
        listarDesafiosWS1.listarDesafios(userRut, TIPOCLIENTE.toString(), holderEstadoType, holderDesafiosType);
        logger.info("Obtener el holder desafio ");
        if (holderDesafiosType != null) {
            logger.info("Estado Desafio  " + holderEstadoType.value.getCodigo());
            logger.info("Glosa desafio  " + holderEstadoType.value.getGlosa());
        }else{
            logger.info("Error en el entrust");
        }
        if (holderEstadoType.value.getCodigo().equals("0")){
            List<DesafioType> desafioTypes = holderDesafiosType.value.getDesafio();
            if (desafioTypes.isEmpty()){
                return false;
            }
            logger.info("Validar el tipo de desafio");
            for (DesafioType desafioType : desafioTypes){
                if(desafioType.getIdentificador().equalsIgnoreCase(num)){
                    return true;
                }
            }
        }
        return false;
    }
}
