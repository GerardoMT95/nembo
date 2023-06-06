
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indirizzoVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="indirizzoVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comune" type="{http://service.gaaserv.agricoltura.aizoon.it/}comuneVO"/>
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="via" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzoVO", propOrder = {
    "comune",
    "numeroCivico",
    "via"
})
public class IndirizzoVO {

    @XmlElement(required = true)
    protected ComuneVO comune;
    @XmlElement(required = true)
    protected String numeroCivico;
    @XmlElement(required = true)
    protected String via;

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneVO }
     *     
     */
    public ComuneVO getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneVO }
     *     
     */
    public void setComune(ComuneVO value) {
        this.comune = value;
    }

    /**
     * Gets the value of the numeroCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Sets the value of the numeroCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Gets the value of the via property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVia() {
        return via;
    }

    /**
     * Sets the value of the via property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVia(String value) {
        this.via = value;
    }

}
