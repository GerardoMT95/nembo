package it.csi.nembo.nembopratiche.util.stampa;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.stampa.fragment.AmmissioneFinanziamentoPremio_SezioniTesto;
import it.csi.nembo.nembopratiche.util.stampa.fragment.AmmissioneFinanziamento_SezioniTesto;
import it.csi.nembo.nembopratiche.util.stampa.fragment.FirmaLetteraAmmissioneFinanziamento;
import it.csi.nembo.nembopratiche.util.stampa.fragment.FirmaLetteraAmmissionePremio;
import it.csi.nembo.nembopratiche.util.stampa.fragment.FirmaListaLiquidazione;
import it.csi.nembo.nembopratiche.util.stampa.fragment.FirmaVerbaleAmmissioneFinanziamento;
import it.csi.nembo.nembopratiche.util.stampa.fragment.Fragment;
import it.csi.nembo.nembopratiche.util.stampa.fragment.Global;
import it.csi.nembo.nembopratiche.util.stampa.fragment.Header;
import it.csi.nembo.nembopratiche.util.stampa.fragment.HeaderLetteraAmmissioneFinanziamento;
import it.csi.nembo.nembopratiche.util.stampa.fragment.ListaLiquidazione;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroAccertamentoAcconto;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroAllegati;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroAttestazioneCAA;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroContiCorrenti;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroDataFineLavori;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroDatiAnticipo;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroDatiIdentificativi;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroDichiarazioni;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroEconomico;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroImpegni;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroInterventiLetteraAmmissioneFinanziamento;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroRendicontazioneSpese;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroSegnalazioneDanni;
import it.csi.nembo.nembopratiche.util.stampa.fragment.QuadroVoltura;
import it.csi.nembo.nembopratiche.util.stampa.fragment.TitoliListaLiquidazione;

public abstract class Stampa
{
  public static final Map<String, Fragment> MAP_FRAGMENTS = new HashMap<String, Fragment>();
  static
  {
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.DATI_IDENTIFICATIVI, new QuadroDatiIdentificativi());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.DICHIARAZIONI, new QuadroDichiarazioni());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.SEGNALAZIONE_DANNI, new QuadroSegnalazioneDanni());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.IMPEGNI, new QuadroImpegni());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.ALLEGATI, new QuadroAllegati());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.CONTI_CORRENTI, new QuadroContiCorrenti());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.DATA_FINE_LAVORI, new QuadroDataFineLavori());
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.VOLTURA, new QuadroVoltura());

    
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.DATI_ANTICIPO, new QuadroDatiAnticipo());
    final QuadroRendicontazioneSpese quadroRendicontazioneSpese = new QuadroRendicontazioneSpese();
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.RENDICONTAZIONE_SPESE, quadroRendicontazioneSpese);
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.RENDICONTAZIONE_SALDO, quadroRendicontazioneSpese);
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.QUADRO_ECONOMICO, new QuadroEconomico());
    
    MAP_FRAGMENTS.put(NemboConstants.QUADRO.CODICE.ACCERTAMENTO_ACCONTO, new QuadroAccertamentoAcconto());

    //Liste liquidazione
    MAP_FRAGMENTS.put("FIRMA_LISTA_LIQUIDAZIONE", new FirmaListaLiquidazione());
    MAP_FRAGMENTS.put("TITOLI_LISTA_LIQUIDAZIONE", new TitoliListaLiquidazione());
    MAP_FRAGMENTS.put("LISTA_LIQUIDAZIONE", new ListaLiquidazione());

    MAP_FRAGMENTS.put("QUADRO_ATTESTAZIONE_CAA", new QuadroAttestazioneCAA());
    
    MAP_FRAGMENTS.put("HEADER", new Header());
    MAP_FRAGMENTS.put("GLOBAL", new Global());
    MAP_FRAGMENTS.put("AMMISSIONE_FINANZIAMENTO_SEZIONI_TESTO", new AmmissioneFinanziamento_SezioniTesto());
    MAP_FRAGMENTS.put("AMMISSIONE_FINANZIAMENTO_INTERVENTI", new QuadroInterventiLetteraAmmissioneFinanziamento());
    MAP_FRAGMENTS.put("HEADER_AMMISSIONE_FINANZIAMENTO", new HeaderLetteraAmmissioneFinanziamento());
    MAP_FRAGMENTS.put("FIRMA_AMMISSIONE_FINANZIAMENTO", new FirmaLetteraAmmissioneFinanziamento());
    MAP_FRAGMENTS.put("FIRMA_VERBALE_AMMISSIONE_FINANZIAMENTO", new FirmaVerbaleAmmissioneFinanziamento());
    
    //Misure a premio
    MAP_FRAGMENTS.put("MISURE_PREMIO_SEZIONI_TESTO", new AmmissioneFinanziamentoPremio_SezioniTesto());
    MAP_FRAGMENTS.put("FIRMA_AMMISSIONE_FINANZIAMENTO_PREMIO", new FirmaLetteraAmmissionePremio());
  }

  public static final String DEFAULT_ENCODING = "UTF-8";

  public abstract byte[] genera(long id, String cuName) throws Exception;

  protected XMLStreamWriter getXMLStreamWriter(ByteArrayOutputStream xmlOutputStream) throws XMLStreamException
  {
    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    return factory.createXMLStreamWriter(xmlOutputStream, DEFAULT_ENCODING);
  }

  protected void generaStampaQuadri(XMLStreamWriter writer, IQuadroEJB quadroEJB, ProcedimentoOggetto procedimentoOggetto, String cuName) throws Exception
  {
    List<QuadroOggettoDTO> quadri = procedimentoOggetto.getQuadri();
    if (quadri != null && !quadri.isEmpty())
    {
      for (QuadroOggettoDTO quadro : quadri)
      {
        writeFragment(quadro.getCodQuadro(), writer, quadroEJB, procedimentoOggetto, cuName);
      }
    }
  }

  public void writeFragment(String codFragment, XMLStreamWriter writer, IQuadroEJB quadroEJB, ProcedimentoOggetto procedimentoOggetto, String cuName)
      throws Exception
  {
    Fragment f = MAP_FRAGMENTS.get(codFragment);
    if (f != null)
    {
      f.writeFragment(writer, procedimentoOggetto, quadroEJB, cuName);
    }
  }
  
   public void writeFragment(String codFragment, XMLStreamWriter writer, RigaJSONElencoListaLiquidazioneDTO listaLiquidazione, IListeLiquidazioneEJB listeLiquidazioneEJB, long idListeLiquidazione, String cuName)
	      throws Exception
	  {
	    Fragment f = MAP_FRAGMENTS.get(codFragment);
	    if (f != null)
	    {
	      f.writeFragmentListaLiquidazione(writer, listaLiquidazione, idListeLiquidazione, listeLiquidazioneEJB, cuName);
	    }
	  }

  protected String getDescrizioneApplicazione()
  {
    return "Gestione Pratiche PSR";
  }

  protected String getCodiceApplicazione()
  {
    return "PSRPRATICHE";
  }

  protected abstract String getCodiceModulo();

  protected abstract String getCodiceModello();

  protected abstract String getRifAdobe();

  public abstract String getDefaultFileName();

  /**
   * Implementazione di default, ritorna l'oggetto corrente come stampa finale. E' da riscrivere nelle classi figlie per gestire le stampe "finte" che servono a
   * smistare su altre stampe, come ad esempio la lettera di ammissione al finanziamento che deve smistare sulla lettera di ammissione (positiva) e quella di
   * rifiuto (negativa).
   */
  public Stampa findStampaFinale(long idProcedimentoOggetto, String cuName) throws Exception
  {
    return this;
  }
}
