
package cl.bice.ws.servicio;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AutentificarWSService", targetNamespace = "http://servicio.ws.bice.cl/", wsdlLocation = "http://soades.bice.local:7001/Internet/SFA/AutentificarWS?wsdl")
public class AutentificarWSService
    extends Service
{

    private final static URL AUTENTIFICARWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUTENTIFICARWSSERVICE_EXCEPTION;
    private final static QName AUTENTIFICARWSSERVICE_QNAME = new QName("http://servicio.ws.bice.cl/", "AutentificarWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://soades.bice.local:7001/Internet/SFA/AutentificarWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTENTIFICARWSSERVICE_WSDL_LOCATION = url;
        AUTENTIFICARWSSERVICE_EXCEPTION = e;
    }

    public AutentificarWSService(String url) {
        super(getURLFromSource(url), AUTENTIFICARWSSERVICE_QNAME);
    }

    private static URL getURLFromSource(String url){
        URL u = null;
        WebServiceException e = null;
        try{
            u = new URL(url);
        }
        catch(MalformedURLException ex){
            e = new WebServiceException(ex);
        }
        return u;
    }
	
    public AutentificarWSService() {
        super(__getWsdlLocation(), AUTENTIFICARWSSERVICE_QNAME);
    }

    public AutentificarWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTENTIFICARWSSERVICE_QNAME, features);
    }

    public AutentificarWSService(URL wsdlLocation) {
        super(wsdlLocation, AUTENTIFICARWSSERVICE_QNAME);
    }

    public AutentificarWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTENTIFICARWSSERVICE_QNAME, features);
    }

    public AutentificarWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AutentificarWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AutentificarWS
     */
    @WebEndpoint(name = "AutentificarWSPort")
    public AutentificarWS getAutentificarWSPort() {
        return super.getPort(new QName("http://servicio.ws.bice.cl/", "AutentificarWSPort"), AutentificarWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AutentificarWS
     */
    @WebEndpoint(name = "AutentificarWSPort")
    public AutentificarWS getAutentificarWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://servicio.ws.bice.cl/", "AutentificarWSPort"), AutentificarWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTENTIFICARWSSERVICE_EXCEPTION!= null) {
            throw AUTENTIFICARWSSERVICE_EXCEPTION;
        }
        return AUTENTIFICARWSSERVICE_WSDL_LOCATION;
    }

}
