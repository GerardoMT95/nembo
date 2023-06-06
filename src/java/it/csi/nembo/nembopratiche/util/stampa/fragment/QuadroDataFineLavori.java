package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datafinelavori.DataFineLavoriDTO;

public class QuadroDataFineLavori extends Fragment
{
  public static final String TAG_NAME_FRAGMENT = "QuadroDateFineLavori";

  @Override
  public void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
	DataFineLavoriDTO fineLavori = quadroEJB.getLastDataFineLavori(procedimentoOggetto.getIdProcedimento());  
    writer.writeStartElement(TAG_NAME_FRAGMENT);
    writeVisibility(writer, true);
    if (fineLavori != null)
    {
      writer.writeStartElement("SezDateFineLavori");
      writeTag(writer, "DataProroga", fineLavori.getDataProrogaStr());
      writeTag(writer, "NoteDateFineLavori", fineLavori.getNote());
      writer.writeEndElement(); // SezDateFineLavori
    }
    writer.writeEndElement(); // TAG_NAME_FRAGMENT
  }
}
