
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addAllegatiRequestVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addAllegatiRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegato" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO" maxOccurs="unbounded"/>
 *         &lt;element name="numeroProtocollo" type="{http://service.gaaserv.agricoltura.aizoon.it/}numeroProtocolloVO" minOccurs="0"/>
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
@XmlType(name = "addAllegatiRequestVO", propOrder = {
    "allegato",
    "numeroProtocollo",
    "usernameToken"
})
public class AddAllegatiRequestVO {

    @XmlElement(required = true)
    protected List<DocumentoVO> allegato;
    protected NumeroProtocolloVO numeroProtocollo;
    @XmlElement(required = true)
    protected UsernameTokenVO usernameToken;

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoVO }
     * 
     * 
     */
    public List<DocumentoVO> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<DocumentoVO>();
        }
        return this.allegato;
    }

    /**
     * Gets the value of the numeroProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link NumeroProtocolloVO }
     *     
     */
    public NumeroProtocolloVO getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets the value of the numeroProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumeroProtocolloVO }
     *     
     */
    public void setNumeroProtocollo(NumeroProtocolloVO value) {
        this.numeroProtocollo = value;
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
