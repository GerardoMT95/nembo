package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.solmr.dto.anag.services.DelegaAnagrafeVO;

public class Header extends Fragment
{
  public static final String TAG_NAME_HEADER      = "Header";
  public static final String TAG_NAME_CASTELLETTO = "Castelletto";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	TestataProcedimento testata = quadroEJB.getTestataProcedimento(procedimentoOggetto.getIdProcedimento());
	  
    writer.writeStartElement(TAG_NAME_HEADER);
    
    
    writeTag(writer, "Titolo1", "REGIONE PIEMONTE");
    writeTag(writer, "Titolo2", testata.getDescAmmCompetenza());
    writeTag(writer, "Titolo3", "");
    writeCastelletto(writer, procedimentoOggetto,testata.getIdAzienda());
    writeTag(writer, "Bando", testata.getAnnoCampagna() + " - " +testata.getDenominazioneBando());
    
    String codici = "";
    for(int i=0; i<testata.getCodMisure().size(); i++)
    {
    	if(i==0){
    		codici = testata.getCodMisure().get(i); 
    	}else{
    		codici = codici + " - " + testata.getCodMisure().get(i);
    	}
    }
    
    writeTag(writer, "Operazioni", codici);
    writeTag(writer, "Oggetto", procedimentoOggetto.getDescrizione());
    writeTag(writer, "Specificita", procedimentoOggetto.getSpecificita());
    writer.writeEndElement();
  }

  protected void writeCastelletto(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, long idAzienda) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_CASTELLETTO);
    String codFiscaleTramite = "";
    String descrizioneTramite = "";
    String intestazioneBoxRichiedente = "";
    try
    {	
	    if(NemboConstants.COD_ATTORE.INTERMEDIARIO.equals(procedimentoOggetto.getCodiceAttore()))
	    {  
	    	DelegaAnagrafeVO delega = NemboUtils.APPLICATION.getEjbQuadro().getDettaglioDelega(idAzienda, procedimentoOggetto.getDataFine());
	    	codFiscaleTramite = elabCodFiscale(delega.getCodiceFiscIntermediario());
	    	descrizioneTramite = delega.getDenominazione();
	    	intestazioneBoxRichiedente = "DOMANDA PRESENTATA PER TRAMITE DI";
	    }
	    else
	    {
	    	intestazioneBoxRichiedente = "DOMANDA PRESENTATA IN PROPRIO";
	    }
    }
    catch(Exception e){
    }
    
    
    writeTag(writer, "IntestazioneBoxRichiedente", intestazioneBoxRichiedente);
    writeTag(writer, "CodiceFiscaleTramite", codFiscaleTramite);
    writeTag(writer, "DescrizioneTramite", descrizioneTramite);
    
    writeTag(writer, "NumeroDomanda", procedimentoOggetto.getIdentificativo());
    writer.writeEndElement();
  }
  
  private String elabCodFiscale(String codFiscale)
  {
	  if(codFiscale == null){
		  return null;
	  }
	  return codFiscale.substring(0,3)+"."+codFiscale.substring(3,6)+"."+codFiscale.substring(6);
  }
}
