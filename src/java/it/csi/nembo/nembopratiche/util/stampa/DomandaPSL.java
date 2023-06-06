package it.csi.nembo.nembopratiche.util.stampa;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class DomandaPSL extends Stampa
{
  public static final String ROOT_TAG = "Domanda";

  @Override
  public byte[] genera(long idProcedimentoOggetto, String cuName) throws Exception
  {
    ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
    XMLStreamWriter writer = getXMLStreamWriter(xmlOutputStream);
    IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    generaXML(writer, idProcedimentoOggetto, quadroEJB, cuName);
    //return callModol(xmlOutputStream.toByteArray());
    return null;
  }

  protected void generaXML(XMLStreamWriter writer, long idProcedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    ProcedimentoOggetto procedimentoOggetto = quadroEJB.getProcedimentoOggetto(idProcedimentoOggetto);
    writer.writeStartDocument("UTF-8","1.0");
    writer.writeStartElement(ROOT_TAG);
    // Scrivo i blocchi di default (simulando come se fossero dei fragment)
    MAP_FRAGMENTS.get("GLOBAL").writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    MAP_FRAGMENTS.get("HEADER").writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    generaStampaQuadri(writer, quadroEJB, procedimentoOggetto, cuName);
    writer.writeEndElement();
    writer.writeEndDocument();
  }

  protected String getCodiceModulo()
  {
    return "domandaPSL";
  }

  protected String getCodiceModello()
  {
    return "domandaPSL";
  }

  protected String getRifAdobe()
  {
    return "/psr20/rp-01/psrpratiche/templates/domandaPSL.xdp";
  }

  @Override
  public String getDefaultFileName()
  {
    return "domanda_psl.pdf";
  }
}
