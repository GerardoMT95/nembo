
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usernameTokenVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usernameTokenVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="passwordAccountProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userAccountProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usernameTokenVO", propOrder = {
    "passwordAccountProtocollo",
    "userAccountProtocollo"
})
public class UsernameTokenVO {

    @XmlElement(required = true)
    protected String passwordAccountProtocollo;
    @XmlElement(required = true)
    protected String userAccountProtocollo;

    /**
     * Gets the value of the passwordAccountProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasswordAccountProtocollo() {
        return passwordAccountProtocollo;
    }

    /**
     * Sets the value of the passwordAccountProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasswordAccountProtocollo(String value) {
        this.passwordAccountProtocollo = value;
    }

    /**
     * Gets the value of the userAccountProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAccountProtocollo() {
        return userAccountProtocollo;
    }

    /**
     * Sets the value of the userAccountProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAccountProtocollo(String value) {
        this.userAccountProtocollo = value;
    }

}
