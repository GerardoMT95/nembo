
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for numeroProtocolloRequestVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="numeroProtocolloRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroProtocolloVO" type="{http://service.gaaserv.agricoltura.aizoon.it/}numeroProtocolloVO"/>
 *         &lt;element name="usernameToken" type="{http://service.gaaserv.agricoltura.aizoon.it/}usernameTokenVO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numeroProtocolloRequestVO", propOrder = {
    "numeroProtocolloVO",
    "usernameToken"
})
public class NumeroProtocolloRequestVO {

    @XmlElement(required = true)
    protected NumeroProtocolloVO numeroProtocolloVO;
    @XmlElement(required = true)
    protected UsernameTokenVO usernameToken;

    /**
     * Gets the value of the numeroProtocolloVO property.
     * 
     * @return
     *     possible object is
     *     {@link NumeroProtocolloVO }
     *     
     */
    public NumeroProtocolloVO getNumeroProtocolloVO() {
        return numeroProtocolloVO;
    }

    /**
     * Sets the value of the numeroProtocolloVO property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumeroProtocolloVO }
     *     
     */
    public void setNumeroProtocolloVO(NumeroProtocolloVO value) {
        this.numeroProtocolloVO = value;
    }

    /**
     * Gets the value of the usernameToken property.
     * 
     * @return
     *     possible object is
     *     {@link UsernameTokenVO }
     *     
     */
    public UsernameTokenVO getUsernameToken() {
        return usernameToken;
    }

    /**
     * Sets the value of the usernameToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsernameTokenVO }
     *     
     */
    public void setUsernameToken(UsernameTokenVO value) {
        this.usernameToken = value;
    }

}
