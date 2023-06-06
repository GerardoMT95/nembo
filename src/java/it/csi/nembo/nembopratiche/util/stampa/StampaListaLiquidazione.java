package it.csi.nembo.nembopratiche.util.stampa;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class StampaListaLiquidazione extends Stampa
{
  public static final String ROOT_TAG = "Domanda";

  @Override
  public byte[] genera(long idListaLiquidazione, String cuName) throws Exception
  {
	    ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
	    XMLStreamWriter writer = getXMLStreamWriter(xmlOutputStream);
	    IListeLiquidazioneEJB listaLiquidazioneEJB = NemboUtils.APPLICATION.getEjbListeLiquidazione();
	    generaXML(writer, idListaLiquidazione, listaLiquidazioneEJB, cuName);
	    //return callModol(xmlOutputStream.toByteArray());
	    return null;
  }

  protected void generaXML(XMLStreamWriter writer, long idListaLiquidazione, IListeLiquidazioneEJB listaLiquidazioneEJB, String cuName) throws Exception
  {

    writer.writeStartDocument(DEFAULT_ENCODING, "1.0");
    writer.writeStartElement(ROOT_TAG);
    writer.writeStartElement("Globale");
    // Scrivo i blocchi di default (simulando come se fossero dei fragment)
    RigaJSONElencoListaLiquidazioneDTO listaLiquidazione =  listaLiquidazioneEJB.getListaLiquidazioneById(idListaLiquidazione);
    
   


    writeFragment("TITOLI_LISTA_LIQUIDAZIONE", writer, listaLiquidazione,  listaLiquidazioneEJB, idListaLiquidazione, cuName);
    writeFragment("LISTA_LIQUIDAZIONE", writer, listaLiquidazione, listaLiquidazioneEJB, idListaLiquidazione, cuName);
    writeFragment("FIRMA_LISTA_LIQUIDAZIONE", writer, listaLiquidazione, listaLiquidazioneEJB, idListaLiquidazione, cuName);
    
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndDocument();
  }
  
  protected String getCodiceModulo()
  {
    return "ListaLiquidazione";
  }

  protected String getCodiceModello()
  {
    return "ListaLiquidazione";
  }

  protected String getRifAdobe()
  {
	    return "/psr20/rp-01/psrpratiche/templates/ListaLiquidazione.xdp";
  }

  @Override
  public String getDefaultFileName()
  {
    return "Lista liquidazione.pdf";
  }

  @Override
  public Stampa findStampaFinale(long idProcedimentoOggetto, String cuName) throws Exception
  {
	return null;
  }
}