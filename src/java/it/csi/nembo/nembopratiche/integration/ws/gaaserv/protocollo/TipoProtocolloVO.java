
package it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoProtocolloVO.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoProtocolloVO">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENTRATA"/>
 *     &lt;enumeration value="USCITA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoProtocolloVO")
@XmlEnum
public enum TipoProtocolloVO {

    ENTRATA,
    USCITA;

    public String value() {
        return name();
    }

    public static TipoProtocolloVO fromValue(String v) {
        return valueOf(v);
    }

}
