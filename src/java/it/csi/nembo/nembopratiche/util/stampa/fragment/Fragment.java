package it.csi.nembo.nembopratiche.util.stampa.fragment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.validator.GenericValidator;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;

public abstract class Fragment
{
  public abstract void writeFragment(XMLStreamWriter writer, ProcedimentoOggetto procedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception;
 
  public void writeFragmentListaLiquidazione(XMLStreamWriter writer, RigaJSONElencoListaLiquidazioneDTO listaLiquidazione, long idListeLiquidazione, IListeLiquidazioneEJB listeLiquidazioneEJB, String cuName) throws Exception {
  }

  protected void writeTag(XMLStreamWriter writer, String name, String value) throws XMLStreamException
  {
    writeTag(writer, name, value, false);
  }

  protected void writeVisibility(XMLStreamWriter writer, boolean visibility) throws XMLStreamException
  {
    writeTag(writer, "Visibility", String.valueOf(visibility));
  }

  protected void writeTag(XMLStreamWriter writer, String name, String value, boolean blankAsNull) throws XMLStreamException
  {
    if (value == null)
    {
      if (blankAsNull)
      {
        value = "";
      }
      else
      {
        return;
      }
    }
    writer.writeStartElement(name);
    try
    {
      writer.writeCharacters(value);
    }
    catch (Exception e)
    {
      throw new XMLStreamException(e);
    }
    writer.writeEndElement();
  }

  protected void writeTagIfNotEmpty(XMLStreamWriter writer, String name, String value) throws XMLStreamException
  {
    if (GenericValidator.isBlankOrNull(value))
    {
      return;
    }
    writer.writeStartElement(name);
    try
    {
      writer.writeCharacters(value);
    }
    catch (Exception e)
    {
      throw new XMLStreamException(e);
    }
    writer.writeEndElement();
  }

  protected void writeCDataTag(XMLStreamWriter writer, String name, String value) throws XMLStreamException
  {
    writeCDataTag(writer, name, value, false);
  }

  protected void writeCDataTag(XMLStreamWriter writer, String name, String value, boolean blankAsNull) throws XMLStreamException
  {
    if (value == null)
    {
      if (blankAsNull)
      {
        value = "";
      }
      else
      {
        return;
      }
    }
    writer.writeStartElement(name);
    writer.writeCData(value);
    writer.writeEndElement();
  }

}
