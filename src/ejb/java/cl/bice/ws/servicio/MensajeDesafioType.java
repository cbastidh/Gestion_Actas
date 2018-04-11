
package cl.bice.ws.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MensajeDesafioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MensajeDesafioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AtributoMensaje" type="{http://servicio.ws.bice.cl/}AtributoMensajeType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MensajeDesafioType", propOrder = {
    "atributoMensaje"
})
public class MensajeDesafioType {

    @XmlElement(name = "AtributoMensaje", required = true)
    protected List<AtributoMensajeType> atributoMensaje;

    /**
     * Gets the value of the atributoMensaje property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atributoMensaje property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtributoMensaje().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link cl.bice.ws.servicio.AtributoMensajeType }
     * 
     * 
     */
    public List<AtributoMensajeType> getAtributoMensaje() {
        if (atributoMensaje == null) {
            atributoMensaje = new ArrayList<AtributoMensajeType>();
        }
        return this.atributoMensaje;
    }

}
