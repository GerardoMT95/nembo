package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.util.Date;

import javax.xml.stream.XMLStreamWriter;

import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ResponsabileDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.solmr.dto.anag.services.DelegaAnagrafeVO;

public class QuadroAttestazioneCAA extends Fragment
{
	public static final String TAG_NAME_FRAGMENT_CAA = "QuadroAttestazioniCAA";

	  @Override
	  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
	  {
	    
          writer.writeStartElement(TAG_NAME_FRAGMENT_CAA);
          writeVisibility(writer, Boolean.TRUE);
          
          String nomeUtente = "XXXXXXXXXX";
          if(procedimentoOggetto.getDataFine()!=null){
        	  UtenteLogin utente = PapuaservProfilazioneServiceFactory.getRestServiceClient().findUtentiLoginByIdList(new long[]{procedimentoOggetto.getIdUtenteAggiornamento()})[0];
        	  nomeUtente = utente.getNome()+" "+utente.getCognome();
          }
          
          TestataProcedimento testata = quadroEJB.getTestataProcedimento(procedimentoOggetto.getIdProcedimento());
          DelegaAnagrafeVO delega = quadroEJB.getDettaglioDelega(testata.getIdAzienda(), procedimentoOggetto.getDataFine());
          
          //ResponsabileDTO responsabile = quadroEJB.getResponsabileCAA(Long.parseLong(delega.getIdIntermediario()));
          
          writeTag(writer, "NomeUtenteConnesso", nomeUtente);
          writeTag(writer, "DenominazioneCAA", elabCodFiscale(delega.getCodiceFiscIntermediario())+" - "+delega.getDenominazione());
          writeTag(writer, "DataLuogo", delega.getComuneUfficioZona()+", "+NemboUtils.DATE.formatDate(new Date()));
          //writeTag(writer, "FlagConflitto", String.valueOf(quadroEJB.isUtenteConflitto(responsabile.getCodiceFiscale(), testata.getCuaa()) ? Boolean.TRUE : Boolean.FALSE));
          writer.writeStartElement("SezioneResponsabile");
          //writeTag(writer, "ResponsabileCAA", responsabile.getDescrizione());
          writer.writeEndElement(); // SezioneResponsabile
          writer.writeEndElement(); // TAG_NAME_FRAGMENT_CAA
	        
	  }
	  
	  private String elabCodFiscale(String codFiscale)
	  {
		  if(codFiscale == null){
			  return null;
		  }
		  return codFiscale.substring(0,3)+"."+codFiscale.substring(3,6)+"."+codFiscale.substring(6);
	  }
  
}
