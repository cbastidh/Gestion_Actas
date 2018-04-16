
package cl.bice.ws.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DesafiosType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DesafiosType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Desafio" type="{http://servicio.ws.bice.cl/}DesafioType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DesafiosType", propOrder = {
    "desafio"
})
public class DesafiosType {

    @XmlElement(name = "Desafio")
    protected List<DesafioType> desafio;

    /**
     * Gets the value of the desafio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the desafio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDesafio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link cl.bice.ws.servicio.DesafioType }
     * 
     * 
     */
    public List<DesafioType> getDesafio() {
        if (desafio == null) {
            desafio = new ArrayList<DesafioType>();
        }
        return this.desafio;
    }

}
