package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;

public class Global extends Fragment
{
  public static final String TAG_NAME_GLOBAL = "Global";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    writer.writeStartElement(TAG_NAME_GLOBAL);
    writeTag(writer, "Bozza", String.valueOf(procedimentoOggetto.getDataFine() == null));
    writer.writeEndElement();
  }
}
