
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for protocolloResponseVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolloResponseVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegati" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="annotazioni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="annullato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="areaOrganizzativaOmogenea" type="{http://service.gaaserv.agricoltura.aizoon.it/}areaOrganizzativaOmogeneaVO" minOccurs="0"/>
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataRicezione" type="{http://service.gaaserv.agricoltura.aizoon.it/}dataVO" minOccurs="0"/>
 *         &lt;element name="destinatari" type="{http://service.gaaserv.agricoltura.aizoon.it/}destinatarioVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fascicoli" type="{http://service.gaaserv.agricoltura.aizoon.it/}fascicoloVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mittente" type="{http://service.gaaserv.agricoltura.aizoon.it/}mittenteVO" minOccurs="0"/>
 *         &lt;element name="numeroProtocollo" type="{http://service.gaaserv.agricoltura.aizoon.it/}numeroProtocolloVO" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oraProtocollazione" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *         &lt;element name="privacy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="protocolliCollegati" type="{http://service.gaaserv.agricoltura.aizoon.it/}numeroProtocolloVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="refAllegati" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoRefVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="refDocumento" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoRefVO" minOccurs="0"/>
 *         &lt;element name="tipoDocumento" type="{http://service.gaaserv.agricoltura.aizoon.it/}tipoDocumentoVO" minOccurs="0"/>
 *         &lt;element name="tipoProtocollo" type="{http://service.gaaserv.agricoltura.aizoon.it/}tipoProtocolloVO" minOccurs="0"/>
 *         &lt;element name="ufficioProtocollazione" type="{http://service.gaaserv.agricoltura.aizoon.it/}ufficioProtocollazioneVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocolloResponseVO", propOrder = {
    "allegati",
    "annotazioni",
    "annullato",
    "areaOrganizzativaOmogenea",
    "dataProtocollo",
    "dataRicezione",
    "destinatari",
    "fascicoli",
    "mittente",
    "numeroProtocollo",
    "oggetto",
    "oraProtocollazione",
    "privacy",
    "protocolliCollegati",
    "refAllegati",
    "refDocumento",
    "tipoDocumento",
    "tipoProtocollo",
    "ufficioProtocollazione"
})
public class ProtocolloResponseVO {

    @XmlElement(nillable = true)
    protected List<DocumentoVO> allegati;
    protected String annotazioni;
    protected Boolean annullato;
    protected AreaOrganizzativaOmogeneaVO areaOrganizzativaOmogenea;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocollo;
    protected DataVO dataRicezione;
    @XmlElement(nillable = true)
    protected List<DestinatarioVO> destinatari;
    @XmlElement(nillable = true)
    protected List<FascicoloVO> fascicoli;
    protected MittenteVO mittente;
    protected NumeroProtocolloVO numeroProtocollo;
    protected String oggetto;
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar oraProtocollazione;
    protected Boolean privacy;
    @XmlElement(nillable = true)
    protected List<NumeroProtocolloVO> protocolliCollegati;
    @XmlElement(nillable = true)
    protected List<DocumentoRefVO> refAllegati;
    protected DocumentoRefVO refDocumento;
    protected TipoDocumentoVO tipoDocumento;
    protected TipoProtocolloVO tipoProtocollo;
    protected UfficioProtocollazioneVO ufficioProtocollazione;

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoVO }
     * 
     * 
     */
    public List<DocumentoVO> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<DocumentoVO>();
        }
        return this.allegati;
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
     * Gets the value of the annullato property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnnullato() {
        return annullato;
    }

    /**
     * Sets the value of the annullato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnnullato(Boolean value) {
        this.annullato = value;
    }

    /**
     * Gets the value of the areaOrganizzativaOmogenea property.
     * 
     * @return
     *     possible object is
     *     {@link AreaOrganizzativaOmogeneaVO }
     *     
     */
    public AreaOrganizzativaOmogeneaVO getAreaOrganizzativaOmogenea() {
        return areaOrganizzativaOmogenea;
    }

    /**
     * Sets the value of the areaOrganizzativaOmogenea property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaOrganizzativaOmogeneaVO }
     *     
     */
    public void setAreaOrganizzativaOmogenea(AreaOrganizzativaOmogeneaVO value) {
        this.areaOrganizzativaOmogenea = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocollo(XMLGregorianCalendar value) {
        this.dataProtocollo = value;
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
     * Gets the value of the fascicoli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fascicoli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFascicoli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FascicoloVO }
     * 
     * 
     */
    public List<FascicoloVO> getFascicoli() {
        if (fascicoli == null) {
            fascicoli = new ArrayList<FascicoloVO>();
        }
        return this.fascicoli;
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
     * Gets the value of the oraProtocollazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOraProtocollazione() {
        return oraProtocollazione;
    }

    /**
     * Sets the value of the oraProtocollazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOraProtocollazione(XMLGregorianCalendar value) {
        this.oraProtocollazione = value;
    }

    /**
     * Gets the value of the privacy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrivacy() {
        return privacy;
    }

    /**
     * Sets the value of the privacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrivacy(Boolean value) {
        this.privacy = value;
    }

    /**
     * Gets the value of the protocolliCollegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolliCollegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolliCollegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NumeroProtocolloVO }
     * 
     * 
     */
    public List<NumeroProtocolloVO> getProtocolliCollegati() {
        if (protocolliCollegati == null) {
            protocolliCollegati = new ArrayList<NumeroProtocolloVO>();
        }
        return this.protocolliCollegati;
    }

    /**
     * Gets the value of the refAllegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the refAllegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRefAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoRefVO }
     * 
     * 
     */
    public List<DocumentoRefVO> getRefAllegati() {
        if (refAllegati == null) {
            refAllegati = new ArrayList<DocumentoRefVO>();
        }
        return this.refAllegati;
    }

    /**
     * Gets the value of the refDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoRefVO }
     *     
     */
    public DocumentoRefVO getRefDocumento() {
        return refDocumento;
    }

    /**
     * Sets the value of the refDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoRefVO }
     *     
     */
    public void setRefDocumento(DocumentoRefVO value) {
        this.refDocumento = value;
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

}
