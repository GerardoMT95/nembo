
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for insertProtocolloRequestVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="insertProtocolloRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegato" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="annotazioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataRicezione" type="{http://service.gaaserv.agricoltura.aizoon.it/}dataVO" minOccurs="0"/>
 *         &lt;element name="destinatari" type="{http://service.gaaserv.agricoltura.aizoon.it/}destinatarioVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documentoDaProtocollare" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO"/>
 *         &lt;element name="mittente" type="{http://service.gaaserv.agricoltura.aizoon.it/}mittenteVO" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="privacy" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tipoDocumento" type="{http://service.gaaserv.agricoltura.aizoon.it/}tipoDocumentoVO"/>
 *         &lt;element name="tipoProtocollo" type="{http://service.gaaserv.agricoltura.aizoon.it/}tipoProtocolloVO"/>
 *         &lt;element name="ufficioProtocollazione" type="{http://service.gaaserv.agricoltura.aizoon.it/}ufficioProtocollazioneVO"/>
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
@XmlType(name = "insertProtocolloRequestVO", propOrder = {
    "allegato",
    "annotazioni",
    "dataRicezione",
    "destinatari",
    "documentoDaProtocollare",
    "mittente",
    "oggetto",
    "privacy",
    "tipoDocumento",
    "tipoProtocollo",
    "ufficioProtocollazione",
    "usernameToken"
})
public class InsertProtocolloRequestVO {

    @XmlElement(nillable = true)
    protected List<DocumentoVO> allegato;
    protected String annotazioni;
    protected DataVO dataRicezione;
    @XmlElement(nillable = true)
    protected List<DestinatarioVO> destinatari;
    @XmlElement(required = true)
    protected DocumentoVO documentoDaProtocollare;
    protected MittenteVO mittente;
    @XmlElement(required = true)
    protected String oggetto;
    protected boolean privacy;
    @XmlElement(required = true)
    protected TipoDocumentoVO tipoDocumento;
    @XmlElement(required = true)
    protected TipoProtocolloVO tipoProtocollo;
    @XmlElement(required = true)
    protected UfficioProtocollazioneVO ufficioProtocollazione;
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
     * Gets the value of the annotazioni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotazioni() {
        return annotazioni;
    }

    /**
     * Sets the value of the annotazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotazioni(String value) {
        this.annotazioni = value;
    }

    /**
     * Gets the value of the dataRicezione property.
     * 
     * @return
     *     possible object is
     *     {@link DataVO }
     *     
     */
    public DataVO getDataRicezione() {
        return dataRicezione;
    }

    /**
     * Sets the value of the dataRicezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataVO }
     *     
     */
    public void setDataRicezione(DataVO value) {
        this.dataRicezione = value;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DestinatarioVO }
     * 
     * 
     */
    public List<DestinatarioVO> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<DestinatarioVO>();
        }
        return this.destinatari;
    }

    /**
     * Gets the value of the documentoDaProtocollare property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoVO }
     *     
     */
    public DocumentoVO getDocumentoDaProtocollare() {
        return documentoDaProtocollare;
    }

    /**
     * Sets the value of the documentoDaProtocollare property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoVO }
     *     
     */
    public void setDocumentoDaProtocollare(DocumentoVO value) {
        this.documentoDaProtocollare = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link MittenteVO }
     *     
     */
    public MittenteVO getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link MittenteVO }
     *     
     */
    public void setMittente(MittenteVO value) {
        this.mittente = value;
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
     * Gets the value of the privacy property.
     * 
     */
    public boolean isPrivacy() {
        return privacy;
    }

    /**
     * Sets the value of the privacy property.
     * 
     */
    public void setPrivacy(boolean value) {
        this.privacy = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoVO }
     *     
     */
    public TipoDocumentoVO getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoVO }
     *     
     */
    public void setTipoDocumento(TipoDocumentoVO value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the tipoProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoProtocolloVO }
     *     
     */
    public TipoProtocolloVO getTipoProtocollo() {
        return tipoProtocollo;
    }

    /**
     * Sets the value of the tipoProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoProtocolloVO }
     *     
     */
    public void setTipoProtocollo(TipoProtocolloVO value) {
        this.tipoProtocollo = value;
    }

    /**
     * Gets the value of the ufficioProtocollazione property.
     * 
     * @return
     *     possible object is
     *     {@link UfficioProtocollazioneVO }
     *     
     */
    public UfficioProtocollazioneVO getUfficioProtocollazione() {
        return ufficioProtocollazione;
    }

    /**
     * Sets the value of the ufficioProtocollazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link UfficioProtocollazioneVO }
     *     
     */
    public void setUfficioProtocollazione(UfficioProtocollazioneVO value) {
        this.ufficioProtocollazione = value;
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
