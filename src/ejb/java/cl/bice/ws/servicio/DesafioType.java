
package cl.bice.ws.servicio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DesafioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DesafioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoDesafio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Identificador" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Grupo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AliasUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoQA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OnLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Coordenadas" type="{http://servicio.ws.bice.cl/}CoordenadasType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DesafioType", propOrder = {
    "tipoDesafio",
    "identificador",
    "grupo",
    "aliasUsuario",
    "codigoQA",
    "onLine",
    "coordenadas"
})
public class DesafioType {

    @XmlElement(name = "TipoDesafio", required = true)
    protected String tipoDesafio;
    @XmlElement(name = "Identificador", required = true)
    protected String identificador;
    @XmlElement(name = "Grupo")
    protected String grupo;
    @XmlElement(name = "AliasUsuario")
    protected String aliasUsuario;
    @XmlElement(name = "CodigoQA")
    protected String codigoQA;
    @XmlElement(name = "OnLine")
    protected String onLine;
    @XmlElement(name = "Coordenadas")
    protected CoordenadasType coordenadas;

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
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificador(String value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the grupo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * Sets the value of the grupo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrupo(String value) {
        this.grupo = value;
    }

    /**
     * Gets the value of the aliasUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasUsuario() {
        return aliasUsuario;
    }

    /**
     * Sets the value of the aliasUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasUsuario(String value) {
        this.aliasUsuario = value;
    }

    /**
     * Gets the value of the codigoQA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoQA() {
        return codigoQA;
    }

    /**
     * Sets the value of the codigoQA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoQA(String value) {
        this.codigoQA = value;
    }

    /**
     * Gets the value of the onLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnLine() {
        return onLine;
    }

    /**
     * Sets the value of the onLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnLine(String value) {
        this.onLine = value;
    }

    /**
     * Gets the value of the coordenadas property.
     * 
     * @return
     *     possible object is
     *     {@link cl.bice.ws.servicio.CoordenadasType }
     *     
     */
    public CoordenadasType getCoordenadas() {
        return coordenadas;
    }

    /**
     * Sets the value of the coordenadas property.
     * 
     * @param value
     *     allowed object is
     *     {@link cl.bice.ws.servicio.CoordenadasType }
     *     
     */
    public void setCoordenadas(CoordenadasType value) {
        this.coordenadas = value;
    }

}
