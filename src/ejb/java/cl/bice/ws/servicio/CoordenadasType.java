
package cl.bice.ws.servicio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CoordenadasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoordenadasType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Coordenada" type="{http://servicio.ws.bice.cl/}CoordenadaType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoordenadasType", propOrder = {
    "coordenada"
})
public class CoordenadasType {

    @XmlElement(name = "Coordenada")
    protected List<CoordenadaType> coordenada;

    /**
     * Gets the value of the coordenada property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coordenada property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoordenada().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link cl.bice.ws.servicio.CoordenadaType }
     * 
     * 
     */
    public List<CoordenadaType> getCoordenada() {
        if (coordenada == null) {
            coordenada = new ArrayList<CoordenadaType>();
        }
        return this.coordenada;
    }

}
