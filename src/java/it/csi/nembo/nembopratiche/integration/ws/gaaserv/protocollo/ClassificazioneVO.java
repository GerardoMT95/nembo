
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for classificazioneVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="classificazioneVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listLivelloClassificazioneVO" type="{http://service.gaaserv.agricoltura.aizoon.it/}livelloClassificazioneVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "classificazioneVO", propOrder = {
    "listLivelloClassificazioneVO"
})
public class ClassificazioneVO {

    @XmlElement(nillable = true)
    protected List<LivelloClassificazioneVO> listLivelloClassificazioneVO;

    /**
     * Gets the value of the listLivelloClassificazioneVO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listLivelloClassificazioneVO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListLivelloClassificazioneVO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LivelloClassificazioneVO }
     * 
     * 
     */
    public List<LivelloClassificazioneVO> getListLivelloClassificazioneVO() {
        if (listLivelloClassificazioneVO == null) {
            listLivelloClassificazioneVO = new ArrayList<LivelloClassificazioneVO>();
        }
        return this.listLivelloClassificazioneVO;
    }

}
