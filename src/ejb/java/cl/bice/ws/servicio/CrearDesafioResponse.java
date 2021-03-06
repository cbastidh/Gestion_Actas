
package cl.bice.ws.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CrearDesafioResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CrearDesafioResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Estado" type="{http://servicio.ws.bice.cl/}EstadoType"/>
 *         &lt;element name="Desafios" type="{http://servicio.ws.bice.cl/}DesafiosType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrearDesafioResponse", propOrder = {
    "estado",
    "desafios"
})
public class CrearDesafioResponse {

    @XmlElement(name = "Estado", required = true)
    protected EstadoType estado;
    @XmlElement(name = "Desafios")
    protected DesafiosType desafios;

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link cl.bice.ws.servicio.EstadoType }
     *     
     */
    public EstadoType getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link cl.bice.ws.servicio.EstadoType }
     *     
     */
    public void setEstado(EstadoType value) {
        this.estado = value;
    }

    /**
     * Gets the value of the desafios property.
     * 
     * @return
     *     possible object is
     *     {@link cl.bice.ws.servicio.DesafiosType }
     *     
     */
    public DesafiosType getDesafios() {
        return desafios;
    }

    /**
     * Sets the value of the desafios property.
     * 
     * @param value
     *     allowed object is
     *     {@link cl.bice.ws.servicio.DesafiosType }
     *     
     */
    public void setDesafios(DesafiosType value) {
        this.desafios = value;
    }

}
