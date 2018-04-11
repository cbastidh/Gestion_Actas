
package cl.bice.ws.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Autentificar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Autentificar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UsuarioId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TipoCliente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IPCliente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TipoDesafio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespuestaDesafio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Canal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MensajeUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MensajeDesafio" type="{http://servicio.ws.bice.cl/}MensajeDesafioType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Autentificar", propOrder = {
    "usuarioId",
    "tipoCliente",
    "ipCliente",
    "tipoDesafio",
    "respuestaDesafio",
    "canal",
    "mensajeUsuario",
    "mensajeDesafio"
})
public class Autentificar {

    @XmlElement(name = "UsuarioId", required = true)
    protected String usuarioId;
    @XmlElement(name = "TipoCliente", required = true)
    protected String tipoCliente;
    @XmlElement(name = "IPCliente", required = true)
    protected String ipCliente;
    @XmlElement(name = "TipoDesafio", required = true)
    protected String tipoDesafio;
    @XmlElement(name = "RespuestaDesafio", required = true)
    protected String respuestaDesafio;
    @XmlElement(name = "Canal", required = true)
    protected String canal;
    @XmlElement(name = "MensajeUsuario", required = true)
    protected String mensajeUsuario;
    @XmlElement(name = "MensajeDesafio", required = true)
    protected MensajeDesafioType mensajeDesafio;

    /**
     * Gets the value of the usuarioId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioId() {
        return usuarioId;
    }

    /**
     * Sets the value of the usuarioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioId(String value) {
        this.usuarioId = value;
    }

    /**
     * Gets the value of the tipoCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Sets the value of the tipoCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCliente(String value) {
        this.tipoCliente = value;
    }

    /**
     * Gets the value of the ipCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPCliente() {
        return ipCliente;
    }

    /**
     * Sets the value of the ipCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPCliente(String value) {
        this.ipCliente = value;
    }

    /**
     * Gets the value of the tipoDesafio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDesafio() {
        return tipoDesafio;
    }

    /**
     * Sets the value of the tipoDesafio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDesafio(String value) {
        this.tipoDesafio = value;
    }

    /**
     * Gets the value of the respuestaDesafio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespuestaDesafio() {
        return respuestaDesafio;
    }

    /**
     * Sets the value of the respuestaDesafio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespuestaDesafio(String value) {
        this.respuestaDesafio = value;
    }

    /**
     * Gets the value of the canal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanal() {
        return canal;
    }

    /**
     * Sets the value of the canal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanal(String value) {
        this.canal = value;
    }

    /**
     * Gets the value of the mensajeUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     * Sets the value of the mensajeUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeUsuario(String value) {
        this.mensajeUsuario = value;
    }

    /**
     * Gets the value of the mensajeDesafio property.
     * 
     * @return
     *     possible object is
     *     {@link cl.bice.ws.servicio.MensajeDesafioType }
     *     
     */
    public MensajeDesafioType getMensajeDesafio() {
        return mensajeDesafio;
    }

    /**
     * Sets the value of the mensajeDesafio property.
     * 
     * @param value
     *     allowed object is
     *     {@link cl.bice.ws.servicio.MensajeDesafioType }
     *     
     */
    public void setMensajeDesafio(MensajeDesafioType value) {
        this.mensajeDesafio = value;
    }

}
