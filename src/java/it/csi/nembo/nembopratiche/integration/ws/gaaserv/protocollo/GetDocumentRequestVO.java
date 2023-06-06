
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDocumentRequestVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDocumentRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usernameTokenVO" type="{http://service.gaaserv.agricoltura.aizoon.it/}usernameTokenVO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDocumentRequestVO", propOrder = {
    "documentID",
    "usernameTokenVO"
})
public class GetDocumentRequestVO {

    @XmlElement(required = true)
    protected String documentID;
    @XmlElement(required = true)
    protected UsernameTokenVO usernameTokenVO;

    /**
     * Gets the value of the documentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentID() {
        return documentID;
    }

    /**
     * Sets the value of the documentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentID(String value) {
        this.documentID = value;
    }

    /**
     * Gets the value of the usernameTokenVO property.
     * 
     * @return
     *     possible object is
     *     {@link UsernameTokenVO }
     *     
     */
    public UsernameTokenVO getUsernameTokenVO() {
        return usernameTokenVO;
    }

    /**
     * Sets the value of the usernameTokenVO property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsernameTokenVO }
     *     
     */
    public void setUsernameTokenVO(UsernameTokenVO value) {
        this.usernameTokenVO = value;
    }

}
