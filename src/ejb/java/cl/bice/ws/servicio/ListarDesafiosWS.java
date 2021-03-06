
package cl.bice.ws.servicio;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ListarDesafiosWS", targetNamespace = "http://servicio.ws.bice.cl/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ListarDesafiosWS {


    /**
     * 
     * @param tipoCliente
     * @param estado
     * @param desafios
     * @param usuarioId
     */
    @WebMethod(operationName = "ListarDesafios")
    @RequestWrapper(localName = "ListarDesafios", targetNamespace = "http://servicio.ws.bice.cl/", className = "cl.bice.ws.servicio.ListarDesafios")
    @ResponseWrapper(localName = "ListarDesafiosResponse", targetNamespace = "http://servicio.ws.bice.cl/", className = "cl.bice.ws.servicio.ListarDesafiosResponse")
    public void listarDesafios(
            @WebParam(name = "UsuarioId", targetNamespace = "")
            String usuarioId,
            @WebParam(name = "TipoCliente", targetNamespace = "")
            String tipoCliente,
            @WebParam(name = "Estado", targetNamespace = "", mode = WebParam.Mode.OUT)
            Holder<EstadoType> estado,
            @WebParam(name = "Desafios", targetNamespace = "", mode = WebParam.Mode.OUT)
            Holder<DesafiosType> desafios);

}
