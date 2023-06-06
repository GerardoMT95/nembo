package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRendicontazioneEAccertamentoSpeseEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaAccertamentoSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaProspetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.TotaleContributoAccertamentoElencoDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroAccertamentoAcconto extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "QuadroInterventiPag";
  
  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	IRendicontazioneEAccertamentoSpeseEJB rendicontazioneEAccertamentoSpeseEJB = NemboUtils.APPLICATION.getEjbAccertamenti();
	List<RigaAccertamentoSpese> elenco = rendicontazioneEAccertamentoSpeseEJB.getElencoAccertamentoSpese(procedimentoOggetto.getIdProcedimentoOggetto(), null);
	List<TotaleContributoAccertamentoElencoDTO> contributi = rendicontazioneEAccertamentoSpeseEJB.getTotaleContributoErogabileNonErogabileESanzioniAcconto(procedimentoOggetto.getIdProcedimentoOggetto());
	List<RigaProspetto> prospetto = rendicontazioneEAccertamentoSpeseEJB.getElencoProspetto(procedimentoOggetto.getIdProcedimento(), procedimentoOggetto.getIdProcedimentoOggetto());
	  
    writer.writeStartElement(TAG_NAME_FRAGMENT);
    writeVisibility(writer, true);
    
    if(elenco!=null)
    {
    	/*
    	 *  245-P (Lettera acconto) -> writeElencoContributi
    	 *  245-PN (Lettera acconto) -> writeElencoContributi , writeElencoInterventi
    	 *  
    	 *  246 (Lettera anticipo) -> writeElencoContributi
    	 *  247-P  (Lettera saldo)-> writeElencoContributi
    	 *  247-PN  (Lettera saldo)-> writeElencoContributi, writeElencoInterventi
    	 *  
    	 *  per i verbali stampo tutti e tre 
    	 *  
    	 * */
    	
    	boolean isComunicazione = cuName.indexOf("245") >= 0 || cuName.indexOf("246") >= 0 || cuName.indexOf("247") >= 0 ;
    	boolean isEsitoPosito = cuName.endsWith("-P");
    	
    	if(isComunicazione)
    	{
	    	if(isEsitoPosito)
	    	{
	    		writeElencoContributi(contributi, writer);
	    	}
	    	else
	    	{
	    		writeElencoContributi(contributi, writer);	
	    		writeElencoInterventi(elenco, writer);
	    	}
    	}
    	else
    	{
    		writeElencoInterventi(elenco, writer);
    		writeElencoContributi(contributi, writer);
    		writeProspetto(prospetto,writer);
    	}
    }
    
    writer.writeEndElement();
  }
  
  public void writeProspetto(List<RigaProspetto> prospetto, XMLStreamWriter writer) throws XMLStreamException{
	  writer.writeStartElement("ElencoProspetto");
	  writeVisibility(writer, prospetto!=null && prospetto.size()>0);
	  writer.writeStartElement("Prospetti");
	  BigDecimal contrRichiestoTot = BigDecimal.ZERO;
	  BigDecimal importoSanzioniTot = BigDecimal.ZERO;
	  BigDecimal liquidazioneTot = BigDecimal.ZERO;

	  if(prospetto!=null)
	  {
		  for(RigaProspetto riga: prospetto)
		  {
			    contrRichiestoTot = contrRichiestoTot.add(riga.getContribRichiesto());
			    importoSanzioniTot = importoSanzioniTot.add(riga.getImportoSanzioni());
			    liquidazioneTot = liquidazioneTot.add(riga.getInLiquidazione());
			    
			    writer.writeStartElement("Prospetto");
		  		writeTag(writer, "TipoIstanza",riga.getDescrizione());
		  		writeTag(writer, "DataPresentazione",NemboUtils.DATE.formatDate(riga.getDataPresentazione()));
		  		writeTag(writer, "ContributoRichiesto",NemboUtils.FORMAT.formatDecimal2(riga.getContribRichiesto()));
		  		writeTag(writer, "ImportoSanzioni",NemboUtils.FORMAT.formatDecimal2(riga.getImportoSanzioni()));
		  		writeTag(writer, "Liquidazione",NemboUtils.FORMAT.formatDecimal2(riga.getInLiquidazione()));
		  		writer.writeEndElement();  //Prospetto
		  }
	  }
	  writer.writeEndElement(); // Prospetti
	  
	  writer.writeStartElement("TotaliProspetti");
	  writer.writeStartElement("RigaTotali");
	  writeTag(writer, "ContributoRichiestoTot",NemboUtils.FORMAT.formatDecimal2(contrRichiestoTot));
	  writeTag(writer, "ImportoSanzioniTot",NemboUtils.FORMAT.formatDecimal2(importoSanzioniTot));
	  writeTag(writer, "LiquidazioneTot",NemboUtils.FORMAT.formatDecimal2(liquidazioneTot));
	  writer.writeEndElement(); // RigaTotali
	  writer.writeEndElement(); // TotaliProspetti
	  
	  writer.writeEndElement(); // ElencoProspetto
  }
  
  
  public void writeElencoContributi(List<TotaleContributoAccertamentoElencoDTO> contributi, XMLStreamWriter writer) throws XMLStreamException{
	  writer.writeStartElement("ElencoContributi");
	  writeVisibility(writer, contributi!=null && contributi.size()>0);
	  writer.writeStartElement("Contributi");
	  if(contributi!=null)
	  {
		  for(TotaleContributoAccertamentoElencoDTO riga: contributi)
		  {
			    writer.writeStartElement("Contributo");
		  		writeTag(writer, "Operazione",riga.getCodiceOperazione());
		  		writeTag(writer, "ContributoErogabile",NemboUtils.FORMAT.formatDecimal2(riga.getContributoErogabile()));
		  		writeTag(writer, "ContributoNonErogabile",NemboUtils.FORMAT.formatDecimal2(riga.getContributoNonErogabile()));
		  		writeTag(writer, "SanzioniRiduzioni",NemboUtils.FORMAT.formatDecimal2(riga.getImportoSanzioni()));
		  		writer.writeEndElement();  //Contributo
		  }
	  }
	  writer.writeEndElement(); // Contributi
	  writer.writeEndElement(); // ElencoContributi
  }
  
  
  public void writeElencoInterventi(List<RigaAccertamentoSpese> elenco, XMLStreamWriter writer) throws XMLStreamException{

	  writer.writeStartElement("ElencoInterventi");
	  writeVisibility(writer, elenco!=null && elenco.size()>0);
	  
	  writer.writeStartElement("Interventi");
  	
	  	BigDecimal spesaAmmessaTot = BigDecimal.ZERO;
	  	BigDecimal spesaRendicontataTot = BigDecimal.ZERO;
	  	BigDecimal spesaAccertataTot = BigDecimal.ZERO;
	  	BigDecimal spesaRiconosciutaTot = BigDecimal.ZERO;
	  	
	  	if(elenco!=null)
	  	{
		  	for(RigaAccertamentoSpese riga: elenco)
		  	{
		  		spesaAmmessaTot = spesaAmmessaTot.add(riga.getSpesaAmmessa());
		  		spesaRendicontataTot = spesaRendicontataTot.add(riga.getSpesaSostenutaAttuale());
		  		spesaAccertataTot = spesaAccertataTot.add(riga.getSpeseAccertateAttuali());
		  		spesaRiconosciutaTot = spesaRiconosciutaTot.add(riga.getSpesaRiconosciutaPerCalcolo());
		  		
		  		writer.writeStartElement("Intervento");
		  		writeTag(writer, "Numero",String.valueOf(riga.getProgressivo()));
		  		writeTag(writer, "DescrizioneIntervento",riga.getDescIntervento());
		  		writeTag(writer, "SpesaAmmessa",NemboUtils.FORMAT.formatDecimal2(riga.getSpesaAmmessa()));
		  		writeTag(writer, "SpesaRendicontata",NemboUtils.FORMAT.formatDecimal2(riga.getSpesaSostenutaAttuale()));
		  		writeTag(writer, "SpesaAccertata",NemboUtils.FORMAT.formatDecimal2(riga.getSpeseAccertateAttuali()));
		  		writeTag(writer, "SpesaRiconosciuta",NemboUtils.FORMAT.formatDecimal2(riga.getSpesaRiconosciutaPerCalcolo()));
		  		writer.writeEndElement();
		  	}
	  	}
	  	writer.writeEndElement();
	  	
	  	writer.writeStartElement("TotaliInterventi");
	  	writer.writeStartElement("TotaleIntervento");
	  	writeTag(writer, "SpesaAmmessaTot",NemboUtils.FORMAT.formatDecimal2(spesaAmmessaTot));
	  	writeTag(writer, "SpesaRendicontataTot",NemboUtils.FORMAT.formatDecimal2(spesaRendicontataTot));
	  	writeTag(writer, "SpesaAccertataTot",NemboUtils.FORMAT.formatDecimal2(spesaAccertataTot));
	  	writeTag(writer, "SpesaRiconosciutaTot",NemboUtils.FORMAT.formatDecimal2(spesaRiconosciutaTot));
	  	writer.writeEndElement();//TotaleIntervento
	  	writer.writeEndElement();//TotaliInterventi
	  	writer.writeEndElement(); // ElencoInterventi
  }
  

}
