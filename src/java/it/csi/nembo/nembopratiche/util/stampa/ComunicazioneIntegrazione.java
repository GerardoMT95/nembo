package it.csi.nembo.nembopratiche.util.stampa;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.validator.GenericValidator;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;

public class ComunicazioneIntegrazione extends Stampa
{
  public static final String ROOT_TAG = "Comunicazione";

  @Override
  public byte[] genera(long idProcedimentoOggetto, String cuName) throws Exception
  {
    ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
    XMLStreamWriter writer = getXMLStreamWriter(xmlOutputStream);
    IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    generaXML(writer, idProcedimentoOggetto, quadroEJB, cuName);
    return callJasperPratica(xmlOutputStream.toByteArray(),quadroEJB,idProcedimentoOggetto);
  }

  private static byte[] callJasperPratica(byte[] xmlData,IQuadroEJB quadroEJB,long idProcedimentoOggetto) throws Exception{
	  JasperReport jasperReport = caricaTemplate(NemboConstants.TEMPLATE_JASPER.COMUNICAZIONE_INTEGRAZIONE);
		
		jasperReport.setProperty(JRTextField.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
	    HashMap<String, Object> params = new HashMap<>();
	    
	    //XML PARAMS
	    params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
	    params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
	    params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
	    params.put(JRParameter.REPORT_LOCALE, Locale.US);
	    
	    params.put("logo", caricaLogo("HEAD"));
	    params.put("footer", caricaLogo("FOOT"));

	    JRXmlDataSource source = new JRXmlDataSource(new ByteArrayInputStream(xmlData),"/Comunicazione");
	    source.setDatePattern("yyyy-MM-dd'T'HH:mm:ssXXX");
	    return JasperRunManager.runReportToPdf(jasperReport,params, source);
} 
  
  private static BufferedImage caricaLogo(String codice) throws Exception{
	  IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
	  byte[] imgbyte = quadroEJB.caricaTemplate(codice);
	  InputStream logo = new ByteArrayInputStream(imgbyte);
      return  ImageIO.read(logo);
  }	
  
  private static JasperReport caricaTemplate(String codice) throws Exception{
	  IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
	  byte[] template = quadroEJB.caricaTemplate(codice);
	  if (null == template ) {
			throw new JRException("Errore durante il recupero dei template di stampa. Record con codice="+codice+" non trovato");
		}
	 return (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(template));
  }	
  

  
  
  protected void generaXML(XMLStreamWriter writer, long idProcedimentoOggetto, IQuadroEJB quadroEJB, String cuName) throws Exception
  {
    ProcedimentoOggetto procedimentoOggetto = quadroEJB.getProcedimentoOggetto(idProcedimentoOggetto);
    
    writer.writeStartDocument("UTF-8","1.0");
    writer.writeStartElement("Comunicazione");
    
    //destinatario
    DatiIdentificativi dati = quadroEJB.getDatiIdentificativiProcedimentoOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), procedimentoOggetto
            .findQuadroByCU(NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO_ISTRUTTORIA).getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    writer.writeStartElement("Destinatario");
    writeTag(writer, "Denominazione", dati.getAzienda().getDenominazione());
    writeTag(writer, "IndirizzoCompleto", dati.getAzienda().getIndirizzoSedeLegale());
    writeTag(writer, "Pec", NemboUtils.STRING.nvl(dati.getAzienda().getPec()));
    
    //DecodificaDTO<String> datiCaa = quadroEJB.findDatiCaa(idProcedimentoOggetto);
    
    writeTag(writer, "CaaNome", " "); //datiCaa.getDescrizione());
    writeTag(writer, "CaaPec"," "); // datiCaa.getCodDescrizione());
    
    writer.writeEndElement();

    //oggetto
    TestataProcedimento testata = quadroEJB.getTestataProcedimento(procedimentoOggetto.getIdProcedimento());
    writer.writeStartElement("Oggetto");
    writeTag(writer, "DenominazioneBando", testata.getDenominazioneBando());
    writeTag(writer, "DenominazioneOggetto", procedimentoOggetto.getDescrizione());
    writeTag(writer, "Cuaa", testata.getCuaa());
    writeTag(writer, "MisuraDenominazione", testata.getMisure().get(0));
    writeTag(writer, "AnnoCampagna", testata.getAnnoCampagna()+"");
    writeTag(writer, "RifDomanda", dati.getDatiProcedimento().getIdentificativo());
    writer.writeEndElement();
    
    writeTag(writer, "Note", dati.getDatiProcedimento().getNote());
    
    //Gestione firma
    String[] PARAM_NAMES = { NemboConstants.PARAMETRO.FIRMA_COM_INTEGRAZIONE, 
    		NemboConstants.PARAMETRO.FIRMA_COM_INTEG_SX, 
    		NemboConstants.PARAMETRO.HEAD_SX_COM_INTEGRAZ, 
    		NemboConstants.PARAMETRO.HEAD_DX_COM_INTEGRAZ,
    		NemboConstants.PARAMETRO.INTRO_COM_INTEGRAZ,
    		NemboConstants.PARAMETRO.FINE_COM_INTEGRAZ};
    Map<String, String> mapParametri = quadroEJB.getParametri(PARAM_NAMES);
	Map<String, String> mapParametriBandoOggetto = quadroEJB.getParametriBandoOggetto(procedimentoOggetto.getIdBandoOggetto(), PARAM_NAMES);
    
	String firmaHtml = findParametro(NemboConstants.PARAMETRO.FIRMA_COM_INTEGRAZIONE, mapParametri, mapParametriBandoOggetto);
	String firmaSxHtml = findParametro(NemboConstants.PARAMETRO.FIRMA_COM_INTEG_SX, mapParametri, mapParametriBandoOggetto);
	String headSx = findParametro(NemboConstants.PARAMETRO.HEAD_SX_COM_INTEGRAZ, mapParametri, mapParametriBandoOggetto);
	String headDx = findParametro(NemboConstants.PARAMETRO.HEAD_DX_COM_INTEGRAZ, mapParametri, mapParametriBandoOggetto);
	String intro = findParametro(NemboConstants.PARAMETRO.INTRO_COM_INTEGRAZ, mapParametri, mapParametriBandoOggetto);
	String fine = findParametro(NemboConstants.PARAMETRO.FINE_COM_INTEGRAZ, mapParametri, mapParametriBandoOggetto);
    
    writeTag(writer, "Firma", firmaHtml);
    writeTag(writer, "FirmaSx", firmaSxHtml);
    writeTag(writer, "HeadSx", headSx);
    writeTag(writer, "HeadDx", headDx);
    writeTag(writer, "Intro", intro);
    writeTag(writer, "Fine", fine);
    
    writer.writeEndElement();
    writer.writeEndDocument();
  }
  
  private String findParametro(String parametro,Map<String, String> mapParametri,Map<String, String> mapParametriBandoOggetto){
	  String retVal=null;
	  if(mapParametriBandoOggetto.get(parametro)!=null){
		  retVal = mapParametriBandoOggetto.get(parametro);
	  }else if(mapParametri.get(parametro)!=null){
		  retVal = mapParametri.get(parametro);
	  }
	  
	  return retVal;
  }

  protected String getCodiceModulo()
  {
    return "comunicazione";
  }

  protected String getCodiceModello()
  {
    return "comunicazione";
  }

  protected String getRifAdobe()
  {
    return "";
  }

  @Override
  public String getDefaultFileName()
  {
    return "comunicazione.pdf";
  }
  
  
  protected void writeTag(XMLStreamWriter writer, String name, String value) throws XMLStreamException
  {
    writeTag(writer, name, value, false);
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
