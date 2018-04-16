
package cl.bice.ws.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AtributoMensajeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AtributoMensajeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NombreCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ValorCampo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtributoMensajeType", propOrder = {
    "nombreCampo",
    "valorCampo"
})
public class AtributoMensajeType {

    @XmlElement(name = "NombreCampo", required = true)
    protected String nombreCampo;
    @XmlElement(name = "ValorCampo", required = true)
    protected String valorCampo;

    /**
     * Gets the value of the nombreCampo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCampo() {
        return nombreCampo;
    }

    /**
     * Sets the value of the nombreCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCampo(String value) {
        this.nombreCampo = value;
    }

    /**
     * Gets the value of the valorCampo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorCampo() {
        return valorCampo;
    }

    /**
     * Sets the value of the valorCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorCampo(String value) {
        this.valorCampo = value;
    }

}
