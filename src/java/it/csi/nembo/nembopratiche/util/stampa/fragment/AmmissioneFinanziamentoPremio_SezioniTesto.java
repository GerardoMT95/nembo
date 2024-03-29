package it.csi.nembo.nembopratiche.util.stampa.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.stampa.placeholder.PlaceHolderManager;

public class AmmissioneFinanziamentoPremio_SezioniTesto extends Fragment
{
  public static final String TAG_NAME_HEADER_TESTO_CONFIGURATO = "SezioneTestoConfigurato";
  public static final String TAG_NAME_CASTELLETTO           = "Castelletto";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    Map<String, List<String>> mapTesti = quadroEJB.getTestiStampeIstruttoria(cuName, procedimentoOggetto.getIdBandoOggetto());
    Map<String, Object> cache = new HashMap<>();
    cache.put(ProcedimentoOggetto.REQUEST_NAME, procedimentoOggetto);
    if (mapTesti!=null && !mapTesti.isEmpty())
    {
      for(String tipoCollocazioneTesto:mapTesti.keySet())
      {
        writeSezioneTestoConfigurato(writer, tipoCollocazioneTesto, mapTesti.get(tipoCollocazioneTesto), procedimentoOggetto, quadroEJB, cache);
      }
    }
  }

  private void writeSezioneTestoConfigurato(XMLStreamWriter writer, String tipoCollocazioneTesto, List<String> list, ProcedimentoOggetto procedimentoOggetto,
      IQuadroEJB quadroEJB,
      Map<String, Object> cache)
      throws Exception
  {
    if (list == null || list.isEmpty())
    {
      return;
    }
    writer.writeStartElement(TAG_NAME_HEADER_TESTO_CONFIGURATO+tipoCollocazioneTesto);
    for (String testo : list)
    {
      testo = PlaceHolderManager.process(testo, cache);
      writeTestoConfigurato(writer, tipoCollocazioneTesto, testo);
    }
    writer.writeEndElement();

  }

  private void writeTestoConfigurato(XMLStreamWriter writer,String tipoCollocazioneTesto, String testo) throws XMLStreamException
  {
    writer.writeStartElement("RigaTestoConfigurato"+tipoCollocazioneTesto);
    writeTag(writer, "TestoConfigurato"+tipoCollocazioneTesto, testo);
    writer.writeEndElement();
  }
}