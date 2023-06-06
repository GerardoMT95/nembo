package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroRendicontazioneSpese extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "QuadroSpese";
  public static final String TAG_NAME_TABELLA_SPESE = "TabellaSpese";
  public static final String TAG_NAME_RIGA_SPESE = "RigaSpese";
  public static final String TAG_NAME_RIGA_TOTALI = "TotaliSpese";
  
  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	List<RigaRendicontazioneSpese> elenco = quadroEJB.getElencoRendicontazioneSpese(procedimentoOggetto.getIdProcedimentoOggetto(), null);
    writer.writeStartElement(TAG_NAME_FRAGMENT);
    writeVisibility(writer, true);
    writeTag(writer, "TitoloSezioneSpese","Quadro - Rendicontazione Spese");
    writer.writeStartElement(TAG_NAME_TABELLA_SPESE);
    BigDecimal totImportoAmmesso = new BigDecimal(0);
    BigDecimal totImportoContributo = new BigDecimal(0);
    BigDecimal totImportoSpesa = new BigDecimal(0);
    BigDecimal totContributoRichiesto = new BigDecimal(0);
    if(elenco!=null)
    {
    	for(RigaRendicontazioneSpese riga: elenco)
    	{
    		writeRiga(writer, riga);
    		totImportoAmmesso = totImportoAmmesso.add(riga.getSpesaAmmessa());
    		totImportoContributo = totImportoContributo.add(riga.getImportoContributo());
    		totImportoSpesa = totImportoSpesa.add(riga.getImportoSpesa());
    		totContributoRichiesto = totContributoRichiesto.add(riga.getContributoRichiesto());
    	}    	
    }
    writer.writeEndElement();
    //RIGA TOTALI
    writer.writeStartElement(TAG_NAME_RIGA_TOTALI);
    writeTag(writer, "TotImportoAmmesso",NemboUtils.FORMAT.formatGenericNumber(totImportoAmmesso, 2, false));
    writeTag(writer, "TotImportoContributo",NemboUtils.FORMAT.formatGenericNumber(totImportoContributo, 2, false));
    writeTag(writer, "TotImportoSpesa",NemboUtils.FORMAT.formatGenericNumber(totImportoSpesa, 2, false));
    writeTag(writer, "TotContributoRichiesto",NemboUtils.FORMAT.formatGenericNumber(totContributoRichiesto, 2, false));
    writer.writeEndElement();
    writer.writeEndElement();
  }
  
  protected void writeRiga(XMLStreamWriter writer, RigaRendicontazioneSpese dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_RIGA_SPESE);
    writeTag(writer, "Progressivo",String.valueOf(dati.getProgressivo()));
    writeTag(writer, "DescrizioneIntervento",dati.getDescIntervento());
    writeTag(writer, "ImportoAmmesso",NemboUtils.FORMAT.formatGenericNumber(dati.getSpesaAmmessa(), 2, false));
    writeTag(writer, "ImportoContributo",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoContributo(), 2, false));
    writeTag(writer, "ImportoSpesa",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoSpesa(), 2, false));
    writeTag(writer, "ContributoRichiesto",NemboUtils.FORMAT.formatGenericNumber(dati.getContributoRichiesto(), 2, false));
    writer.writeEndElement();
  }

}
