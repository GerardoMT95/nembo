
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDocumentResponseVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDocumentResponseVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegato" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO" minOccurs="0"/>
 *         &lt;element name="documento" type="{http://service.gaaserv.agricoltura.aizoon.it/}documentoVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDocumentResponseVO", propOrder = {
    "allegato",
    "documento"
})
public class GetDocumentResponseVO {

    protected DocumentoVO allegato;
    protected DocumentoVO documento;

    /**
     * Gets the value of the allegato property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoVO }
     *     
     */
    public DocumentoVO getAllegato() {
        return allegato;
    }

    /**
     * Sets the value of the allegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoVO }
     *     
     */
    public void setAllegato(DocumentoVO value) {
        this.allegato = value;
    }

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoVO }
     *     
     */
    public DocumentoVO getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoVO }
     *     
     */
    public void setDocumento(DocumentoVO value) {
        this.documento = value;
    }

}
