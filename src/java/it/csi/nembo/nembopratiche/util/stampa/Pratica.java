package it.csi.nembo.nembopratiche.util.stampa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;

import javax.naming.NamingException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
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

public class Pratica extends Stampa
{
  public static final String ROOT_TAG = "Domanda";

  @Override
  public byte[] genera(long idProcedimentoOggetto, String cuName) throws Exception
  {
    ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
    XMLStreamWriter writer = getXMLStreamWriter(xmlOutputStream);
    IQuadroEJB quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    generaXML(writer, idProcedimentoOggetto, quadroEJB, cuName);
    return callJasperPratica(xmlOutputStream.toByteArray());
  }

  private static byte[] callJasperPratica(byte[] xmlData) throws Exception{
		JasperReport jasperReport = caricaTemplate(NemboConstants.TEMPLATE_JASPER.PRATICA.CODICE);
		
		jasperReport.setProperty(JRTextField.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
	    HashMap<String, Object> params = new HashMap<>();
	    
	    //XML PARAMS
	    params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
	    params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
	    params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
	    params.put(JRParameter.REPORT_LOCALE, Locale.US);
	    
	    //SUBREPORTS
	       params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.DATI_IDENTIFICATIVI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.DATI_IDENTIFICATIVI));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.AVVIO_CORSI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.AVVIO_CORSI));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.VOLTURA, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.VOLTURA));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.CONTO_CORRENTE, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.CONTO_CORRENTE));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.ANTICIPO, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.ANTICIPO));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.INTERVENTI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.INTERVENTI));
//	    params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.ECONOMICO, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.ECONOMICO));
	     params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.DICHIARAZIONI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.DICHIARAZIONI));
	       params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.SEGNALAZIONE_DANNI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.SEGNALAZIONE_DANNI));
	   // params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.IMPEGNI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.IMPEGNI));
	      params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.ALLEGATI, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.ALLEGATI));
	      params.put(NemboConstants.TEMPLATE_JASPER.QUADRO.NOME.FIRMA, caricaTemplate(NemboConstants.TEMPLATE_JASPER.QUADRO.CODICE.FIRMA));
	    
	    JRXmlDataSource source = new JRXmlDataSource(new ByteArrayInputStream(xmlData),"/Domanda");
	    source.setDatePattern("yyyy-MM-dd'T'HH:mm:ssXXX");    
	    return JasperRunManager.runReportToPdf(jasperReport,params, source);
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
    writer.writeStartDocument(DEFAULT_ENCODING,"1.0");
    writer.writeStartElement(ROOT_TAG);
    // Scrivo i blocchi di default (simulando come se fossero dei fragment)
    MAP_FRAGMENTS.get("GLOBAL").writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    MAP_FRAGMENTS.get("HEADER").writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    generaStampaQuadri(writer, quadroEJB, procedimentoOggetto, cuName);
    
    /*
    if(quadroEJB.isQuadroAttestazioneVisible(procedimentoOggetto.getIdOggetto())){
    	if(PapuaservProfilazioneServiceFactory.getRestServiceClient().getUtenteAbilitazioniByIdUtenteLogin(procedimentoOggetto.getIdUtenteAggiornamento()).getRuolo().isUtenteIntermediario()){
    		MAP_FRAGMENTS.get("QUADRO_ATTESTAZIONE_CAA").writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    	}
    }*/
    writer.writeEndElement();
    writer.writeEndDocument();
  }
  
  protected String getCodiceModulo()
  {
    return "pratica";
  }

  protected String getCodiceModello()
  {
    return "pratica";
  }

  protected String getRifAdobe()
  {
    return "";
  }

  @Override
  public String getDefaultFileName()
  {
    return "pratica.pdf";
  }
}
