
package cl.bice.ws.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CoordenadaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoordenadaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fila" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Columna" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoordenadaType", propOrder = {
    "fila",
    "columna"
})
public class CoordenadaType {

    @XmlElement(name = "Fila")
    protected int fila;
    @XmlElement(name = "Columna", required = true)
    protected String columna;

    /**
     * Gets the value of the fila property.
     * 
     */
    public int getFila() {
        return fila;
    }

    /**
     * Sets the value of the fila property.
     * 
     */
    public void setFila(int value) {
        this.fila = value;
    }

    /**
     * Gets the value of the columna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumna() {
        return columna;
    }

    /**
     * Sets the value of the columna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumna(String value) {
        this.columna = value;
    }

}
