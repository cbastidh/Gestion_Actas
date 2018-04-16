
package cl.bice.ws.servicio;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.bice.ws.servicio package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListarDesafiosResponse_QNAME = new QName("http://servicio.ws.bice.cl/", "ListarDesafiosResponse");
    private final static QName _ListarDesafios_QNAME = new QName("http://servicio.ws.bice.cl/", "ListarDesafios");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.bice.ws.servicio
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.ListarDesafios }
     * 
     */
    public ListarDesafios createListarDesafios() {
        return new ListarDesafios();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.ListarDesafiosResponse }
     * 
     */
    public ListarDesafiosResponse createListarDesafiosResponse() {
        return new ListarDesafiosResponse();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.DesafiosType }
     * 
     */
    public DesafiosType createDesafiosType() {
        return new DesafiosType();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.DesafioType }
     * 
     */
    public DesafioType createDesafioType() {
        return new DesafioType();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.CoordenadaType }
     * 
     */
    public CoordenadaType createCoordenadaType() {
        return new CoordenadaType();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.EstadoType }
     * 
     */
    public EstadoType createEstadoType() {
        return new EstadoType();
    }

    /**
     * Create an instance of {@link cl.bice.ws.servicio.CoordenadasType }
     * 
     */
    public CoordenadasType createCoordenadasType() {
        return new CoordenadasType();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link cl.bice.ws.servicio.ListarDesafiosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws.bice.cl/", name = "ListarDesafiosResponse")
    public JAXBElement<ListarDesafiosResponse> createListarDesafiosResponse(ListarDesafiosResponse value) {
        return new JAXBElement<ListarDesafiosResponse>(_ListarDesafiosResponse_QNAME, ListarDesafiosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link cl.bice.ws.servicio.ListarDesafios }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws.bice.cl/", name = "ListarDesafios")
    public JAXBElement<ListarDesafios> createListarDesafios(ListarDesafios value) {
        return new JAXBElement<ListarDesafios>(_ListarDesafios_QNAME, ListarDesafios.class, null, value);
    }

}
