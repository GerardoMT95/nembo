package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.DatiAnticipo;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroDatiAnticipo extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "QuadroAnticipo";
  public static final String TAG_NAME_SEZIONE_TITOLO_ANTICIPO = "SezioneTitoloAnticipo";
  public static final String TAG_NAME_SEZIONE_ANTICIPO = "SezioneAnticipo";
  public static final String TAG_NAME_SEZIONE_FIDEIUSSIONE = "SezioneFideiussione";
  public static final String TAG_NAME_SEZIONE_BANCA = "SezioneBanca";
  public static final String TAG_NAME_SEZIONE_ALTRO_ISTITUTO = "SezioneAltroIstituto";
  
  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	DatiAnticipo datiAnticipo = quadroEJB.getDatiAnticipo(procedimentoOggetto.getIdProcedimentoOggetto());
    writer.writeStartElement(TAG_NAME_FRAGMENT);
    writeSezioneTitoloAnticipo(writer, datiAnticipo);
    writeDatiAnticipo(writer, datiAnticipo);
    writeDatiFideiussione(writer, datiAnticipo);
    writeDatiBanca(writer, datiAnticipo);
    writeAltroistituto(writer, datiAnticipo);
    writer.writeEndElement();
  }
  
  protected void writeSezioneTitoloAnticipo(XMLStreamWriter writer, DatiAnticipo dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_TITOLO_ANTICIPO);
    writeVisibility(writer, true);
    writeTag(writer, "TitoloQuadroAnticipo","QUADRO ANTICIPO");
    writer.writeEndElement();
  }

  protected void writeDatiAnticipo(XMLStreamWriter writer, DatiAnticipo dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_ANTICIPO);
    writeTag(writer, "TitolosezAnticipo","DATI ANTICIPO");
    //writeTag(writer, "ImportoInvestimento",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoInvestimento(), 2, false));
    //writeTag(writer, "ImportoAmmesso",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoAmmesso(), 2, false));
    //writeTag(writer, "ImportoContributo",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoContributo(), 2, false));
    writeTag(writer, "PercentualeAnticipo",NemboUtils.FORMAT.formatGenericNumber(dati.getPercentualeAnticipo(), 2, false));
    writeTag(writer, "ImportoAnticipo",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoAnticipo(), 2, false) );
    writer.writeEndElement();
  }
  
  protected void writeDatiFideiussione(XMLStreamWriter writer, DatiAnticipo dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_FIDEIUSSIONE);
    writeTag(writer, "TitoloSezFideiussione","DATI FIDEJIUSSIONE");
    writeTag(writer, "NumeroFideiussione",dati.getNumeroFideiussione());
    writeTag(writer, "ImportoFideiussione",NemboUtils.FORMAT.formatGenericNumber(dati.getImportoFideiussione(), 2, false));
    writeTag(writer, "DataStipula",NemboUtils.DATE.formatDate(dati.getDataStipula()));
    writeTag(writer, "DataScadenza",NemboUtils.DATE.formatDate(dati.getDataScadenza()));
    writer.writeEndElement();
  }
  
  protected void writeDatiBanca(XMLStreamWriter writer, DatiAnticipo dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_BANCA);
    writeTag(writer, "VisibilitaBanca", String.valueOf(dati.getExtIdSportello()!=null));
    writeTag(writer, "TitoloSezBanca","ISTITUTO BANCARIO");
    writeTag(writer, "DenominazioneBanca",dati.getDenominazioneBanca());
    writeTag(writer, "SportelloIndCapComuneProv",dati.getDescCompletaComuneSportello());
    writer.writeEndElement();
  }
  
  protected void writeAltroistituto(XMLStreamWriter writer, DatiAnticipo dati) throws XMLStreamException
  {
    writer.writeStartElement(TAG_NAME_SEZIONE_ALTRO_ISTITUTO);
    writeTag(writer, "VisibilitaIstituto", String.valueOf(dati.getExtIdSportello()==null));
    writeTag(writer, "TitoloSezAltroIstituto","ALTRO ISTITUTO");
    writeTag(writer, "AltroIstituto",dati.getAltroIstituto());
    writeTag(writer, "IndirizzoAltroIstituto",dati.getIndirizzoAltroIstituto());
    writeTag(writer, "AltroIstitutoCapComuneProv",dati.getDescCompletaComuneAltroIstituto());
    writer.writeEndElement();
  }
}
