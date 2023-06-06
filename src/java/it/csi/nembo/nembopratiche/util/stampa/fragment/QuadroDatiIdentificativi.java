package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiAziendaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiRappresentanteLegaleDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiSoggettoFirmatarioDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroDatiIdentificativi extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "QuadroDatiIdentificativi";
  public static final String TAG_NAME_SEZIONE_ANAGRAFICA = "SezioneAnagrafica";
  public static final String TAG_NAME_SEZIONE_TITOLARE = "SezioneTitolare";
  public static final String TAG_NAME_SEZIONE_FIRMATARIO = "SezioneFirmatario";
  
  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    DatiIdentificativi dati = quadroEJB.getDatiIdentificativiProcedimentoOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), procedimentoOggetto
        .findQuadroByCU(NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO).getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    writer.writeStartElement(TAG_NAME_FRAGMENT);
    writeSezioneAnagrafica(writer, procedimentoOggetto, dati, quadroEJB); 
    writeSezioneTitolare(writer, procedimentoOggetto, dati); 
    writeSezioneFirmatario(writer, procedimentoOggetto, dati);
    writer.writeEndElement();
  }

  protected void writeSezioneAnagrafica(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, DatiIdentificativi dati, IQuadroEJB quadroEJB) throws XMLStreamException, InternalUnexpectedException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_ANAGRAFICA);
    writeVisibility(writer, true);
    DatiAziendaDTO azienda = dati.getAzienda();
    writeTag(writer, "Cuaa", azienda.getCuaa());
    writeTag(writer, "PartitaIva", azienda.getPartitaIva());
    writeTag(writer, "IntestazionePartitaIva", azienda.getItestazionePartitaIva());
    writeTag(writer, "Denominazione", azienda.getDenominazione());
    writeTag(writer, "NumeroIscrizioneCCIAA", dati.getRifDomandaSianCollegata());
    writeTag(writer, "FormaGiuridica", azienda.getFormaGiuridica());
    writeTag(writer, "IndirizzoSedeLeg", azienda.getIndirizzoSedeLegale());
    writeTag(writer, "ComuneSedeLeg", azienda.getIndirizzoSedeLegale().substring(azienda.getIndirizzoSedeLegale().lastIndexOf("-")+2));
    writeTag(writer, "DataOdierna",NemboUtils.DATE.formatDate(NemboUtils.DATE.getCurrentDateNoTime()));
//    List<SedeOperativaDTO> elencoSedi = quadroEJB.getElencoSedeOperativa(procedimentoOggetto.getIdProcedimentoOggetto());
//    String indirizzi="";
//    if(elencoSedi!=null)
//    {
//    	boolean first = true;
//    	for(SedeOperativaDTO sede: elencoSedi)
//    	{
//    		if(first)
//    			indirizzi +=  sede.getIndirizzo().toUpperCase()+" - "+ sede.getDescrizioneComune().toUpperCase();
//    		else
//    			indirizzi +=  " \n" + sede.getIndirizzo().toUpperCase()+" - "+ sede.getDescrizioneComune().toUpperCase();
//    		
//    		first = false;
//    	}
//    }
//    writeTag(writer, "IndirizziSediOperative", indirizzi);

    writeTag(writer, "Pec", azienda.getPec());
    writeTag(writer, "email", azienda.getEmail());
    writeTag(writer, "Tel", azienda.getTelefono());
    writeTag(writer, "Fax", azienda.getFax());
    writeTag(writer, "Ateco", azienda.getAttivitaAteco());
    writeTag(writer, "Ote", azienda.getAttivitaOte());
    writeTag(writer, "Registro", azienda.getCciaaNumeroRegistroImprese());
    writeTag(writer, "Anno", azienda.getCciaaAnnoIscrizione());
    writer.writeEndElement();
  }

  protected void writeSezioneTitolare(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, DatiIdentificativi dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_TITOLARE);
    DatiRappresentanteLegaleDTO rappLegale = dati.getRappLegale();
    writeVisibility(writer, true);
    writeTag(writer, "CognomeTitolare", rappLegale.getCognome());
    writeTag(writer, "NomeTitolare", rappLegale.getNome());
    writeTag(writer, "CodiceFiscaleTitolare", rappLegale.getCodiceFiscale());
    writeTag(writer, "Indirizzo", rappLegale.getIndirizzo());
    writeTag(writer, "Comune", rappLegale.getComune());
    writeTag(writer, "SessoTitolare", rappLegale.getSesso());
    writeTag(writer, "MailTitolare", rappLegale.getMail());
    writeTag(writer, "TelefonoTitolare", rappLegale.getTelefono());
    writeTag(writer, "IndirizzoResidenzaTitolare", rappLegale.getIndirizzoResidenza());
    writeTag(writer, "IndirizzoTitolare", rappLegale.getIndirizzoResidenza());
    writeTag(writer, "DataNascitaTitolare", NemboUtils.DATE.formatDate(rappLegale.getDataNascita()));
    writeTag(writer, "LuogoNascitaTitolare", rappLegale.getLuogoNascita());
    writer.writeEndElement();
  }
  
  protected void writeSezioneFirmatario(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, DatiIdentificativi dati) throws XMLStreamException
  {
    DatiSoggettoFirmatarioDTO item = dati.getSoggFirmatario();
    if(item != null) 
    {
        writer.writeStartElement(TAG_NAME_SEZIONE_FIRMATARIO);
	    writeVisibility(writer, true);
	    writeTag(writer, "RuoloFirmatario", item.getDescrizioneRuolo());
	    writeTag(writer, "CodiceFiscaleFirmatario", item.getCodiceFiscale());
	    writeTag(writer, "CognomeFirmatario", item.getCognome());
	    writeTag(writer, "NomeFirmatario", item.getNome());
	    writeTag(writer, "SessoFirmatario", item.getSesso());
	    writeTag(writer, "MailFirmatario", item.getMail());
	    writeTag(writer, "TelefonoFirmatario", item.getTelefono());
	    writeTag(writer, "IndirizzoResidenzaFirmatario", item.getIndirizzoResidenza());
	    writeTag(writer, "DataNascitaFirmatario", NemboUtils.DATE.formatDate(item.getNascitaData()));
	    writeTag(writer, "LuogoNascitaFirmatario", item.getComuneCitta());
	    writer.writeEndElement();
    }
  }
}
