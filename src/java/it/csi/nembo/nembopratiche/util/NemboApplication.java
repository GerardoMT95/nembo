package it.csi.nembo.nembopratiche.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.business.IQuadroDinamicoEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.business.IRendicontazioneEAccertamentoSpeseEJB;
import it.csi.nembo.nembopratiche.dto.ElencoCduDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.SegnapostoDTO;

public final class NemboApplication
{
  private Map<String, ElencoCduDTO>  MAP_ELENCO_CDU        = null;
  private List<SegnapostoDTO>        SEGNAPOSTI_STAMPE     = null;
  private Map<String, SegnapostoDTO> MAP_SEGNAPOSTI_STAMPE = null;
  public static final Pattern        PATTERN_CU_NUMBER     = Pattern
      .compile("CU-NEMBO-([0-9]+)");

  NemboApplication()
  {
    loadSyncSegnapostiStampe();
  }

  public void reloadCDU()
  {
	  if (MAP_ELENCO_CDU == null || MAP_ELENCO_CDU.isEmpty())
	    {
		    Map<String, ElencoCduDTO> map = loadCDU();
		    if (map != null && !map.isEmpty())
		    {
		      MAP_ELENCO_CDU = map;
		    }
	    }
  }

  protected synchronized Map<String, ElencoCduDTO> loadSyncCDU()
  {
    if (MAP_ELENCO_CDU == null || MAP_ELENCO_CDU.isEmpty())
    {
      return loadCDU();
    }
    return MAP_ELENCO_CDU;
  }

  protected Map<String, ElencoCduDTO> loadCDU()
  {
    Map<String, ElencoCduDTO> map = new HashMap<String, ElencoCduDTO>();
    try
    {
      InitialContext ic = new InitialContext();
      IQuadroEJB quadro = (IQuadroEJB) ic.lookup("java:app/Quadro");
      List<ElencoCduDTO> list = quadro.getElencoCdu();
      for (ElencoCduDTO cdu : list)
      {
        map.put(cdu.getCodiceCdu(), cdu);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".util")
          .error("Errore nelle crazione della lista di cdu", e);
    }
    return map;
  }

  public synchronized Map<String, SegnapostoDTO> loadSyncSegnapostiStampe()
  {
    Map<String, SegnapostoDTO> map = new HashMap<String, SegnapostoDTO>();
    try
    {
      InitialContext ic = new InitialContext();
      IQuadroEJB quadro = (IQuadroEJB) ic.lookup("java:app/Quadro");
      List<SegnapostoDTO> list = quadro.getSegnapostiStampe();
      for (SegnapostoDTO segnapostoDTO : list)
      {
        map.put(segnapostoDTO.getNome(), segnapostoDTO);
      }
      SEGNAPOSTI_STAMPE = list;
      MAP_SEGNAPOSTI_STAMPE = map;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".util").error(
          "Errore nelle crazione della lista e mappa di segnaposto per le stampe",
          e);
    }
    return map;
  }

  public IQuadroEJB getEjbQuadro() throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IQuadroEJB) ic.lookup("java:app/Quadro");
  }

  public IQuadroNemboEJB getEjbQuadroNembo() throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IQuadroNemboEJB) ic.lookup("java:app/QuadroNembo");
  }

  public IListeLiquidazioneEJB getEjbListeLiquidazione() throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IListeLiquidazioneEJB) ic.lookup("java:app/ListeLiquidazione");
  }

  public IQuadroDinamicoEJB getEJBQuadroDinamico() throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IQuadroDinamicoEJB) ic.lookup("java:app/QuadroDinamico");
  }

  public IInterventiEJB getEjbInterventi() throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IInterventiEJB) ic.lookup("java:app/Interventi");
  }

  public IRendicontazioneEAccertamentoSpeseEJB getEjbAccertamenti()
      throws NamingException
  {
    InitialContext ic = new InitialContext();
    return (IRendicontazioneEAccertamentoSpeseEJB) ic
        .lookup("java:app/RendicontazioneEAccertamentoSpese");
  }

  public ElencoCduDTO getCdu(String cdu)
  {
    if (MAP_ELENCO_CDU == null || MAP_ELENCO_CDU.isEmpty())
    {
      Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME)
          .error(
              "[NemboApplication.getCdu] Trovata Map MAP_ELENCO_CDU vuota o NULL!!! Caricamento dati in corso...");
      loadSyncCDU();
    }
    return MAP_ELENCO_CDU.get(cdu);
  }

  public SegnapostoDTO getSegnaposto(String nomeSegnaposto)
  {
    if (MAP_SEGNAPOSTI_STAMPE == null || MAP_SEGNAPOSTI_STAMPE.isEmpty())
    {
      Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME)
          .error(
              "[NemboApplication.getCdu] Trovata Map MAP_SEGNAPOSTI_STAMPE vuota o NULL!!! Caricamento dati in corso...");
      loadSyncSegnapostiStampe();
    }
    return MAP_SEGNAPOSTI_STAMPE.get(nomeSegnaposto);
  }

  public String getCUNumber(HttpServletRequest request)
  {
    String useCaseController = (String) request
        .getAttribute("useCaseController");
    return getCUNumber(useCaseController);
  }

  public String getCUNumber(String useCaseController)
  {
    try
    {
      Matcher matcher = PATTERN_CU_NUMBER.matcher(useCaseController);
      if (matcher.find())
      {
        return matcher.group(1);
      }
    }
    catch (Exception e)
    {
      // Log dell'eccezione
      Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".util")
          .warn("[NemboApplication.getCUNumber] exception:", e);
      // e lascio che il metodo ritorni null;
    }
    return null;
  }

  public List<SegnapostoDTO> getSEGNAPOSTI_STAMPE()
  {
    return SEGNAPOSTI_STAMPE;
  }

  public Map<String, SegnapostoDTO> getMAP_SEGNAPOSTI_STAMPE()
  {
    return MAP_SEGNAPOSTI_STAMPE;
  }
  
  public static String getNomeApplicativoByIdProcedimentoAgricolo(int idProcedimentoAgricolo)
  {
	  String nomeApplicativo="";
	  switch(idProcedimentoAgricolo)
	  {
	  case NemboConstants.TIPO_PROCEDIMENTO_AGRICOLO.AVVERSITA_ATMOSFERICHE:
		  nomeApplicativo="Avversità atmosferiche";
		  break;
	  case NemboConstants.TIPO_PROCEDIMENTO_AGRICOLO.AIUTI_DI_STATO:
		  nomeApplicativo="Aiuti di Stato";
		  break;
	  case NemboConstants.TIPO_PROCEDIMENTO_AGRICOLO.CONSORZI_IRRIGUI:
		  nomeApplicativo="Consorzi Irrigui";
		  break;
	  }
	  return nomeApplicativo;
  }

}
