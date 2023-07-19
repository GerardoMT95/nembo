package it.csi.nembo.nembopratiche.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.nembo.nembopratiche.business.INemboAbstractEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.internal.LogVariable;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.AmmCompetenza;
import it.csi.papua.papuaserv.dto.gestioneutenti.EnteLogin;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;
import it.csi.solmr.dto.anag.sian.SianUtenteVO;

@Controller
public class BaseController
{
  protected static final Logger logger = Logger
      .getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".presentation");

  protected TestataProcedimento getTestataProcedimento(HttpSession session)
  {
    return (TestataProcedimento) session
        .getAttribute(TestataProcedimento.SESSION_NAME);
  }

  protected void refreshTestataProcedimento(IQuadroEJB quadroEJB,
      HttpSession session, long idProcedimento)
      throws InternalUnexpectedException
  {
    TestataProcedimento testataProcedimento = quadroEJB
        .getTestataProcedimento(idProcedimento);
    session.setAttribute(TestataProcedimento.SESSION_NAME, testataProcedimento);
  }

  protected UtenteAbilitazioni getUtenteAbilitazioni(HttpSession session)
  {
    return (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
  }

  protected long getIdUtenteLogin(HttpSession session)
  {
    return getUtenteAbilitazioni(session).getIdUtenteLogin();
  }

  protected long getIdProcedimentoOggetto(HttpSession session)
      throws InternalUnexpectedException
  {
    return getProcedimentoOggettoFromSession(session)
        .getIdProcedimentoOggetto();
  }

  protected long getIdProcedimento(HttpSession session)
      throws InternalUnexpectedException
  {
    return getProcedimentoFromSession(session).getIdProcedimento();
  }

  protected List<UtenteLogin> loadRuoloDescr(List<Long> lIdUtente)
      throws InternalUnexpectedException
  {
    try
    {
      int size = lIdUtente.size();
      long[] aIdUtente = new long[size];
      for (int i = 0; i < size; ++i)
      {
        aIdUtente[i] = lIdUtente.get(i);
      }
      UtenteLogin[] utenti = PapuaservProfilazioneServiceFactory
          .getRestServiceClient().findUtentiLoginByIdList(aIdUtente);
      if (utenti != null && utenti.length > 0)
      {
        return Arrays.asList(utenti);
      }
      else
      {
        // Ritorno un array vuoto in modo che se il chiamante poi non fa il
        // controllo del null ma magri esegue un for non va in eccezione
        return new ArrayList<>();
      }

    }
    catch (Exception e)
    {
      throw new InternalUnexpectedException(e, new LogVariable[]
      { new LogVariable("lIdUtente", lIdUtente) });
    }
  }

  protected Map<Long, UtenteLogin> getMapUtenti(List<Long> lIdUtente)
      throws InternalUnexpectedException
  {
    Map<Long, UtenteLogin> map = new HashMap<>();
    if (lIdUtente != null && lIdUtente.isNotEmpty())
    {
      List<UtenteLogin> list = loadRuoloDescr(lIdUtente);
      if (list != null)
      {
        for (UtenteLogin utente : list)
        {
          map.put(utente.getIdUtenteLogin(), utente);
        }
      }
    }
    return map;
  }

  protected String getUtenteDescrizione(Long idUtente, List<UtenteLogin> utenti)
  {
    if (utenti != null)
    {
      for (UtenteLogin utente : utenti)
      {
        if (utente != null && utente.getIdUtenteLogin() != null
            && utente.getIdUtenteLogin().longValue() == idUtente.longValue())
        {
          StringBuilder sb = new StringBuilder();
          sb.append(utente.getCognome()).append(" ").append(utente.getNome());
          String denominazioneEnte = utente.getEnte().getDenominazioneEnte();
          if (denominazioneEnte != null)
          {
            sb.append(" (").append(denominazioneEnte).append(")");
          }
          return sb.toString();
        }
      }
    }
    return "";
  }

  protected void setModelDialogWarning(Model model, String messaggio)
  {
    setModelDialogWarning(model, null, messaggio, null);
  }

  protected void setModelDialogWarning(Model model, String messaggio,
      String action)
  {
    setModelDialogWarning(model, null, messaggio, action);
  }

  protected void setModelDialogWarningPopup(Model model, String messaggio,
      String action, String titolo, String idPopup)
  {
    model.addAttribute("titolo", titolo);
    model.addAttribute("idPopup", idPopup);
    setModelDialogWarning(model, null, messaggio, action);
  }

  protected void setModelDialogWarning(Model model, String messaggioAttesa,
      String messaggio, String action)
  {
    model.addAttribute("chiudi", "no, chiudi");
    model.addAttribute("prosegui", "si, prosegui");
    model.addAttribute("messaggio", messaggio);
    if (messaggioAttesa != null)
    {
      model.addAttribute("messaggioAttendere", messaggioAttesa);
    }
    else
    {
      model.addAttribute("messaggioAttendere",
          "Attendere prego, operazione in corso...");
    }
    if (action != null)
    {
      model.addAttribute("action", action);
    }
  }

  public void writePlainText(HttpServletResponse response, String text)
      throws IOException
  {
    response.setContentType("text/plain");
    response.getWriter().write(text);
  }

  protected Procedimento getProcedimentoFromSession(HttpSession session)
      throws InternalUnexpectedException
  {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();
    return (Procedimento) getProcedimentoFromSession(request);
  }

  protected Procedimento getProcedimentoFromSession(HttpServletRequest request)
      throws InternalUnexpectedException
  {
    return NemboFactory.getProcedimento(request);
  }

  protected ProcedimentoOggetto getProcedimentoOggettoFromSession(
      HttpSession session) throws InternalUnexpectedException
  {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();
    return getProcedimentoOggettoFromRequest(request);
  }

  protected ProcedimentoOggetto getProcedimentoOggettoFromRequest(
      HttpServletRequest request) throws InternalUnexpectedException
  {
    return NemboFactory.getProcedimentoOggetto(request);
  }

  protected void aggiornaDatiAAEPSian(AziendaDTO azienda,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
    try
    {

      NemboUtils.PORTADELEGATA.getAnagServiceCSIInterface()
          .serviceAggiornaDatiAAEP(azienda.getCuaa());
      serviceSianAggiornaDatiTributaria(azienda,
          NemboUtils.PORTADELEGATA.getSianUtenteVO(utenteAbilitazioni));
    }
    catch (Exception e)
    {
      // Devo ignorare qualunque errore. Eventuali anomalie vengono proposte
      // dalla proceura PL seguente
    }
  }

  protected Date getDataValiditaProcOggetto(HttpSession session)
      throws InternalUnexpectedException
  {
    return getProcedimentoOggettoFromSession(session).getDataFine();
  }

  private void serviceSianAggiornaDatiTributaria(AziendaDTO azienda,
      SianUtenteVO sianUtenteVO)
  {
    try
    {
      NemboUtils.PORTADELEGATA.getAnagServiceCSIInterface()
          .serviceSianAggiornaDatiTributaria(azienda.getCuaa(), sianUtenteVO);

      if (!NemboConstants.ANAGRAFE.ID_FORMA_GIURIDICA_DITTA_INDIVIDUALE
          .equals(azienda.getIdFormaGiuridica())
          && !NemboConstants.ANAGRAFE.ID_FORMA_GIURIDICA_PERS_FISICA_NON_ESERC_ATT_IMPRESA
              .equals(azienda.getIdFormaGiuridica()))
      {
        // I dati del titolare/rappresentante legale vengono utilizzati per
        // richiamare il servizio ServiceSianAggiornaDatiTributaria
        // solo se l'azienda legata alla pratica in questione ha forma giuridica
        // diversa da 1 (DITTA INDIVIDUALE) e da 52 (PERSONA FISICA CHE NON
        // ESERCITA
        // ATTIVITA' DI IMPRESA)
        it.csi.solmr.dto.anag.PersonaFisicaVO personaFisicaVOAnag = NemboUtils.PORTADELEGATA
            .getAnagServiceCSIInterface()
            .serviceGetTitolareORappresentanteLegaleAzienda(
                azienda.getIdAzienda(), new Date(System.currentTimeMillis()));
        if (personaFisicaVOAnag != null && NemboUtils.VALIDATION
            .isNotEmpty(personaFisicaVOAnag.getCodiceFiscale()))
        {
          // Il servizio viene richiamato una seconda volta passando come
          // parametro il codice fiscale del Titolare/Rappresentante legale
          NemboUtils.PORTADELEGATA.getAnagServiceCSIInterface()
              .serviceSianAggiornaDatiTributaria(
                  personaFisicaVOAnag.getCodiceFiscale(), sianUtenteVO);
        }
      }
    }
    catch (Exception ex)
    {
      // Devo ignorare qualunque errore. Eventuali anomalie vengono proposte
      // dalla proceura PL seguente
    }
  }

  public String getDescUltimoAggiornamento(UtenteLogin utenteLogin,
      Date dataUltimoAggiornamento)
  {
    StringBuilder sb = new StringBuilder();
    boolean isDate = false;
    if (dataUltimoAggiornamento != null)
    {
      sb.append(NemboUtils.DATE.formatDateTime(dataUltimoAggiornamento));
      isDate = true;
    }
    if (utenteLogin != null)
    {
      if (isDate)
      {
        sb.append(" - ");
      }
      sb.append(utenteLogin.getCognome()).append(" ")
          .append(utenteLogin.getNome());
      final String denominazioneEnte = utenteLogin.getEnte()
          .getDenominazioneEnte();
      if (!GenericValidator.isBlankOrNull(denominazioneEnte))
      {
        sb.append(" (").append(denominazioneEnte).append(")");
      }
    }
    return sb.toString();
  }

  public String returnErrorPopup(Model model, String messaggio)
  {
    model.addAttribute("messaggio", messaggio);
    return "errorePopup";
  }

  public Map<String, Object> getCommonFromSession(String id,
      HttpSession session, boolean removeIfDifferentOwner)
  {
    @SuppressWarnings("unchecked")
    Map<String, Object> common = (Map<String, Object>) session
        .getAttribute("common");
    if (common != null)
    {
      String ownerID =   common.get("ID");
      if (ownerID == null)
      {
        common = new HashMap<>();
        common.put("ID", id);
      }
      else
      {
        if (!ownerID.equals(id))
        {
          common = new HashMap<>();
          common.put("ID", id);
          if (removeIfDifferentOwner)
          {
            session.removeAttribute("common");
          }
        }
      }
    }
    else
    {
      common = new HashMap<>();
      common.put("ID", id);
    }
    return common;
  }

  public void saveCommonInSession(Map<String, Object> common,
      HttpSession session)
  {
    session.setAttribute("common", common);
  }

  public void clearCommonInSession(HttpSession session)
  {
    session.removeAttribute("common");
  }

  protected LogOperationOggettoQuadroDTO getLogOperationOggettoQuadroDTO(
      HttpSession session) throws InternalUnexpectedException
  {
    return getLogOperationOggettoQuadroDTO(
        this.getClass().getAnnotation(NemboSecurity.class).value(), session);
  }

  protected LogOperationOggettoQuadroDTO getLogOperationOggettoQuadroDTO(
      String cuName, HttpSession session) throws InternalUnexpectedException
  {
    LogOperationOggettoQuadroDTO logOperation = new LogOperationOggettoQuadroDTO();
    logOperation.setExtIdUtenteAggiornamento(getIdUtenteLogin(session));
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    logOperation.setIdProcedimentoOggetto(po.getIdProcedimentoOggetto());
    logOperation.setIdBandoOggetto(po.getIdBandoOggetto());
    QuadroOggettoDTO quadro = po.findQuadroByCU(cuName);
    if (quadro == null)
    {
      logger.error("[" + this.getClass().getSimpleName()
          + ":getLogOperationOggettoQuadroDTO] Errore: Impossibile trovare il quadro con CU = "
          + cuName
          + ", NullPointerException in arrivo!", null);
    }
    logOperation.setIdQuadroOggetto(quadro.getIdQuadroOggetto());
    return logOperation;
  }

  protected String getUltimaModifica(INemboAbstractEJB quadroEJB,
      long idProcedimentoOggetto, long idQuadroOggetto, long idBandoOggetto)
      throws InternalUnexpectedException
  {
    LogOperationOggettoQuadroDTO logObj = quadroEJB.getIdUtenteUltimoModifica(
        idProcedimentoOggetto, idQuadroOggetto, idBandoOggetto);
    if (logObj != null)
    {
      List<Long> idUtenti = new ArrayList<>();
      idUtenti.add(logObj.getExtIdUtenteAggiornamento());
      List<UtenteLogin> utentiList = loadRuoloDescr(idUtenti);
      return NemboUtils.DATE
          .formatDateTime(logObj.getDataUltimoAggiornamento()) + " "
          + getUtenteDescrizione(logObj.getExtIdUtenteAggiornamento(),
              utentiList);
    }
    return null;
  }

  public QuadroOggettoDTO findQuadroCorrente(HttpServletRequest request)
      throws InternalUnexpectedException
  {
    return findQuadroCorrente(getProcedimentoOggettoFromRequest(request));
  }

  public QuadroOggettoDTO findQuadroCorrente(HttpSession session)
      throws InternalUnexpectedException
  {
    return findQuadroCorrente(getProcedimentoOggettoFromSession(session));
  }

  public QuadroOggettoDTO findQuadroCorrente(ProcedimentoOggetto po)
  {
    return po.findQuadroByCU(
        this.getClass().getAnnotation(NemboSecurity.class).value());
  }

  public List<Long> getListIdAmmCompetenza(HttpSession session)
  {
    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    EnteLogin[] entiAbilitati = utenteAbilitazioni.getEntiAbilitati();
    List<Long> lIdAmmCompetenza = new ArrayList<>();
    for (EnteLogin ente : entiAbilitati)
    {
      AmmCompetenza ammCompetenza = ente.getAmmCompetenza();
      if (ammCompetenza != null)
      {
        lIdAmmCompetenza.add(ammCompetenza.getIdAmmCompetenza());
      }
    }
    return lIdAmmCompetenza;
  }

  public void cleanSession(HttpSession session)
  {
    // Rimuovo dalla sessione tutti gli oggetti 'pesanti' che sono stati
    // erroneamente inseriti in sessione nei vari quadri.
    session.removeAttribute("elencoFrazionaParticelleSession");
    session.removeAttribute("pascolo");
    session.removeAttribute("sessionVarAllevamenti");
    session.removeAttribute("RegistriDiStallaVO");
    session.removeAttribute("righeDati");
    session.removeAttribute("CU171Partecipante");
    session.removeAttribute("elencoComuniGAL");
    session.removeAttribute("checkboxMisureSelezionate");
    session.removeAttribute("riepilogo");
    session.removeAttribute("premioComplessivo");
    session.removeAttribute("allevamenti");
    session.removeAttribute("sanzioniSuperfici");
    session.removeAttribute("sanzioniAllevamenti");
    session.removeAttribute("listaDaVisualizzare");
    session.removeAttribute(FiltroRicercaConduzioni.class.getName());

  }
  
  /**
   * 
   * @author nicolo.mandrile
   * @param session
   * @param tableNamesToRemove: lista dei nomi delle tabelle per le quali voglio rimuovere le informazioni
   * (filtri, colonne visibili/nascoste, ordinamento, pagina, ...) dalla sessione.
   * 
   */
  @SuppressWarnings("unchecked")
  public void cleanTableMapsInSession(HttpSession session, List<String> tableNamesToRemove){

  		HashMap<String, String> mapFilters = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER);
  		HashMap<String, String> mapFiltersColOrd = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_COLONNA_ORDINAMENTO);
  		HashMap<String, String> mapFiltersTipoOrd = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_TIPO_ORDINAMENTO);
  		HashMap<String, String> mapFiltersNumPag = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_NUMERO_PAGINA);
  		HashMap<String, String> mapFiltersPageSize = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_PAGE_SIZE);
  		HashMap<String, String> mapFiltersColonneNascoste = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_COLONNE_NASCOSTE);
  		HashMap<String, String> mapRigheVisibili = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_RIGHE_VISIBILI);

  		removeTableInfoFromMap(mapFiltersColonneNascoste, tableNamesToRemove); 
  		removeTableInfoFromMap(mapFilters, tableNamesToRemove);
  		removeTableInfoFromMap(mapFiltersColOrd, tableNamesToRemove);
  		removeTableInfoFromMap(mapFiltersTipoOrd, tableNamesToRemove);
  		removeTableInfoFromMap(mapFiltersNumPag, tableNamesToRemove);
  		removeTableInfoFromMap(mapFiltersPageSize, tableNamesToRemove);
  		removeTableInfoFromMap(mapRigheVisibili, tableNamesToRemove);
  	}
  	
	private void removeTableInfoFromMap(HashMap<String, String> mapFilters, List<String> tableNamesToRemove) {
		if(mapFilters!=null && tableNamesToRemove!=null && !tableNamesToRemove.isEmpty()){
			for(String tableName : tableNamesToRemove)
			{
				mapFilters.remove(tableName); 		
			}
		}
	}
	
	public List<DecodificaDTO<String>> getListUfficiZonaFunzionari()
	{
		List<DecodificaDTO<String>> ufficiZona = new ArrayList<>();
		final String STESSO_UFFICIO = "STESSO_UFFICIO";
		ufficiZona.add(new DecodificaDTO<String>(STESSO_UFFICIO,"Singolo ufficio zona"));
		ufficiZona.add(new DecodificaDTO<String>(null,"Tutti gli uffici zona"));
		return ufficiZona;
  	}
	
	protected int getProcedimentoAgricoloFromSession(
		      HttpSession session) throws InternalUnexpectedException
	  {
	    return getUtenteAbilitazioni(session).getIdProcedimento();
	  }


  public static Map<String,Boolean> getCodiciPunteggioPerInterventiPrevenzione()
  {
	  Map<String,Boolean> mappa = new HashMap<>();
	  mappa.put(NemboConstants.PUNTEGGI.CODICE_LUPI01, Boolean.TRUE);
	  mappa.put(NemboConstants.PUNTEGGI.CODICE_LUPI02, Boolean.TRUE);
	  mappa.put(NemboConstants.PUNTEGGI.CODICE_LUPI03, Boolean.TRUE);
	  return mappa;
  }
	
}
