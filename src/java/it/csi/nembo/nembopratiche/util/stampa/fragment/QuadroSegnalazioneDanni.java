package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.util.List;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.danni.RigaSegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.dto.danni.SegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;

public class QuadroSegnalazioneDanni extends Fragment
{
  public static final String TAG_NAME_FRAGMENT_DICHIARAZIONI = "QuadroSegnalazioneDanni";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    
	SegnalazioneDannoDTO segnalazioneDannoDTO = quadroEJB.getSegnalazioneDanno(procedimentoOggetto.getIdProcedimentoOggetto(),null);
	
	if(segnalazioneDannoDTO==null)
		return;
	
	List<RigaSegnalazioneDannoDTO> danniColture = segnalazioneDannoDTO.getElencoDanni();
    writer.writeStartElement(TAG_NAME_FRAGMENT_DICHIARAZIONI);
    writeVisibility(writer, true);
    
    writeTag(writer, "descrizioneDanno", segnalazioneDannoDTO.getDescrizioneDanno());
    writeTag(writer, "data", segnalazioneDannoDTO.getDataDanno());
    
    if(danniColture!=null && danniColture.size()>0)
    {
	    for (RigaSegnalazioneDannoDTO row : danniColture)
	    {
	    	if(row.getImportoDannoStr()!=null && row.getImportoDannoStr().trim().length()>0)
	    	{
	    		writer.writeStartElement("Segnalazioni");
	  	      writeTag(writer, "comune", row.getDescrizioneComune());
	  	      writeTag(writer, "foglio", row.getFoglio());
	  	      writeTag(writer, "coltura", row.getDescrizioneColtura());
	  	      writeTag(writer, "superficie", row.getSuperficieGraficaStr());
	  	      writeTag(writer, "importoDanno", row.getImportoDannoStr());
	  	      writeTag(writer, "percDanno", row.getPercDannoStr());
	  	      writeTag(writer, "flagColturaAssicurata", row.getFlagColturaAssicurataDecodifica() );
	  	      writeTag(writer, "note", row.getNoteDanno());
	  	      writer.writeEndElement();
	    	}
	      
	    }
    }
    
    writer.writeEndElement();
  }


}
