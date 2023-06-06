package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class TitoliListaLiquidazione extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "Titoli";
  

  
    @Override
	public void writeFragmentListaLiquidazione(XMLStreamWriter writer, RigaJSONElencoListaLiquidazioneDTO listaLiquidazione, long idListaLiquidazione, IListeLiquidazioneEJB listeLiquidazioneEJB,
			String cuName) throws Exception {
    	
    	String bozza = "";
    	if(listaLiquidazione.getFlagStatoLista().equals("B"))
    		bozza = "true";
    		else     			
    			bozza = "false";

    	writer.writeStartElement("PerBozza");
    	writeTag(writer, "Bozza",bozza);
    	writer.writeEndElement();
    	
    	
	  	writer.writeStartElement(TAG_NAME_FRAGMENT);
	    writeVisibility(writer, true);

	    writeTag(writer, "OD",NemboUtils.STRING.concat(" - ",listaLiquidazione.getOrganismoDelegato(), listaLiquidazione.getDescOrganismoDelegato()));
	    writeTag(writer, "DescBando",listaLiquidazione.getDenominazioneBando());
	    writeTag(writer, "ElencoLiq","\n"+"Elenco di liquidazione N. "+ listaLiquidazione.getNumeroLista() + " del " + listaLiquidazione.getDataCreazioneDate() + " - " + listaLiquidazione.getDescTipoImporto());
	    String livello = listeLiquidazioneEJB.getTitoloListaLiquidazioneByIdBando(listaLiquidazione.getIdBando());
	    writeTag(writer,"Livello", livello);
	    writer.writeEndElement();
	}



@Override
public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception {
	// TODO Auto-generated method stub
	
}
  


}
