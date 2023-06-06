package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class FirmaListaLiquidazione extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "Firma";
  
  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	
  }
  
  @Override
  public void writeFragmentListaLiquidazione(XMLStreamWriter writer, RigaJSONElencoListaLiquidazioneDTO listaLiquidazione, long idListeLiquidazione, IListeLiquidazioneEJB listeLiquidazioneEJB, String cuName) throws Exception
  {
	    writer.writeStartElement(TAG_NAME_FRAGMENT);
	    writeVisibility(writer, true);
	    writeTag(writer, "TecnicoLiquidatore",NemboUtils.STRING.concat(" ",listaLiquidazione.getNomeTecnicoLiquidatore(), listaLiquidazione.getCognomeTecnicoLiquidatore()));
	    writer.writeEndElement();
  }


}
