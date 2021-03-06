
package cl.bice.ws.servicio;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AutentificarWS", targetNamespace = "http://servicio.ws.bice.cl/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AutentificarWS {


    /**
     * 
     * @param tipoCliente
     * @param mensajeDesafio
     * @param tipoDesafio
     * @param mensajeUsuario
     * @param respuestaDesafio
     * @param usuarioId
     * @param canal
     * @param ipCliente
     * @return
     *     returns cl.bice.ws.servicio.EstadoType
     */
    @WebMethod(operationName = "Autentificar")
    @WebResult(name = "Estado", targetNamespace = "")
    @RequestWrapper(localName = "Autentificar", targetNamespace = "http://servicio.ws.bice.cl/", className = "cl.bice.ws.servicio.Autentificar")
    @ResponseWrapper(localName = "AutentificarResponse", targetNamespace = "http://servicio.ws.bice.cl/", className = "cl.bice.ws.servicio.AutentificarResponse")
    public EstadoType autentificar(
            @WebParam(name = "UsuarioId", targetNamespace = "")
            String usuarioId,
            @WebParam(name = "TipoCliente", targetNamespace = "")
            String tipoCliente,
            @WebParam(name = "IPCliente", targetNamespace = "")
            String ipCliente,
            @WebParam(name = "TipoDesafio", targetNamespace = "")
            String tipoDesafio,
            @WebParam(name = "RespuestaDesafio", targetNamespace = "")
            String respuestaDesafio,
            @WebParam(name = "Canal", targetNamespace = "")
            String canal,
            @WebParam(name = "MensajeUsuario", targetNamespace = "")
            String mensajeUsuario,
            @WebParam(name = "MensajeDesafio", targetNamespace = "")
            MensajeDesafioType mensajeDesafio);

}
