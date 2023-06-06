
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fascicoloVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fascicoloVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classificazioneVO" type="{http://service.gaaserv.agricoltura.aizoon.it/}classificazioneVO" minOccurs="0"/>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fascicoloVO", propOrder = {
    "classificazioneVO",
    "codice",
    "nome"
})
public class FascicoloVO {

    protected ClassificazioneVO classificazioneVO;
    protected String codice;
    protected String nome;

    /**
     * Gets the value of the classificazioneVO property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificazioneVO }
     *     
     */
    public ClassificazioneVO getClassificazioneVO() {
        return classificazioneVO;
    }

    /**
     * Sets the value of the classificazioneVO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificazioneVO }
     *     
     */
    public void setClassificazioneVO(ClassificazioneVO value) {
        this.classificazioneVO = value;
    }

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

}
