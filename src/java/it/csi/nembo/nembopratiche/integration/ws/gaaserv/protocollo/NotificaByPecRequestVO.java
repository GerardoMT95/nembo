
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notificaByPecRequestVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificaByPecRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatarioPecA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaDestinatariPecCC" type="{http://service.gaaserv.agricoltura.aizoon.it/}listaDestinatariPecCCVO" minOccurs="0"/>
 *         &lt;element name="listaDocumentiAllegati" type="{http://service.gaaserv.agricoltura.aizoon.it/}listaDocumentiVO" minOccurs="0"/>
 *         &lt;element name="mittenteDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mittentePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mittentePec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificaByPecRequestVO", propOrder = {
    "destinatarioPecA",
    "listaDestinatariPecCC",
    "listaDocumentiAllegati",
    "mittenteDenominazione",
    "mittentePassword",
    "mittentePec",
    "oggetto",
    "testo"
})
public class NotificaByPecRequestVO {

    @XmlElement(required = true)
    protected String destinatarioPecA;
    protected ListaDestinatariPecCCVO listaDestinatariPecCC;
    protected ListaDocumentiVO listaDocumentiAllegati;
    @XmlElement(required = true)
    protected String mittenteDenominazione;
    @XmlElement(required = true)
    protected String mittentePassword;
    @XmlElement(required = true)
    protected String mittentePec;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String testo;

    /**
     * Gets the value of the destinatarioPecA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinatarioPecA() {
        return destinatarioPecA;
    }

    /**
     * Sets the value of the destinatarioPecA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinatarioPecA(String value) {
        this.destinatarioPecA = value;
    }

    /**
     * Gets the value of the listaDestinatariPecCC property.
     * 
     * @return
     *     possible object is
     *     {@link ListaDestinatariPecCCVO }
     *     
     */
    public ListaDestinatariPecCCVO getListaDestinatariPecCC() {
        return listaDestinatariPecCC;
    }

    /**
     * Sets the value of the listaDestinatariPecCC property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDestinatariPecCCVO }
     *     
     */
    public void setListaDestinatariPecCC(ListaDestinatariPecCCVO value) {
        this.listaDestinatariPecCC = value;
    }

    /**
     * Gets the value of the listaDocumentiAllegati property.
     * 
     * @return
     *     possible object is
     *     {@link ListaDocumentiVO }
     *     
     */
    public ListaDocumentiVO getListaDocumentiAllegati() {
        return listaDocumentiAllegati;
    }

    /**
     * Sets the value of the listaDocumentiAllegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDocumentiVO }
     *     
     */
    public void setListaDocumentiAllegati(ListaDocumentiVO value) {
        this.listaDocumentiAllegati = value;
    }

    /**
     * Gets the value of the mittenteDenominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittenteDenominazione() {
        return mittenteDenominazione;
    }

    /**
     * Sets the value of the mittenteDenominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittenteDenominazione(String value) {
        this.mittenteDenominazione = value;
    }

    /**
     * Gets the value of the mittentePassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittentePassword() {
        return mittentePassword;
    }

    /**
     * Sets the value of the mittentePassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittentePassword(String value) {
        this.mittentePassword = value;
    }

    /**
     * Gets the value of the mittentePec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittentePec() {
        return mittentePec;
    }

    /**
     * Sets the value of the mittentePec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittentePec(String value) {
        this.mittentePec = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the testo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Sets the value of the testo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }

}
