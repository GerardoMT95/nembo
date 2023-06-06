package it.csi.nembo.nembopratiche.presentation.login;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.iride2.policy.entity.Identita;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.MapColonneNascosteVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.allegati.ContenutoFileAllegatiDTO;
import it.csi.nembo.nembopratiche.exception.InternalException;
import it.csi.nembo.nembopratiche.exception.InternalServiceException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NoLoginRequired;
import it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;

@Controller
@RequestMapping(value = "/login")
@NoLoginRequired
public class CtrlLogin
{
  @Autowired
  IQuadroEJB quadroEJB;

  @RequestMapping(value = "wrup/seleziona_ruolo", method = RequestMethod.GET)
  public String selezionaRuoloWRUP(HttpServletResponse response,
      HttpSession session) throws InternalException
  {
    addPortalCookie(response,
        NemboConstants.PORTAL.PUBBLICA_AMMINISTRAZIONE);
    session.setAttribute(NemboConstants.PORTAL.NEMBOPRATICHE_LOGIN_PORTAL,
        NemboConstants.PORTAL.PUBBLICA_AMMINISTRAZIONE);
    Identita identita = (Identita) session.getAttribute("identita");
    /*
     * Imposto il 6 bit del livello di autenticazione a 0, come convenzione per
     * papua se questo bit è a 1 significa che l'utente ha fatto login tramite
     * SPID
     */
    identita.setLivelloAutenticazione(
        identita.getLivelloAutenticazione()
            & (255 - NemboConstants.SPID.BIT_SPID));
    return "redirect:../seleziona_procedimento_agricolo.do";
  }

  @RequestMapping(value = "sisp/seleziona_ruolo", method = RequestMethod.GET)
  public String selezionaRuoloSISP(HttpServletResponse response,
      HttpSession session) throws InternalException
  {
    addPortalCookie(response, NemboConstants.PORTAL.PRIVATI_SISPIE);
    session.setAttribute(NemboConstants.PORTAL.NEMBOPRATICHE_LOGIN_PORTAL,
        NemboConstants.PORTAL.PRIVATI_SISPIE);
    Identita identita = (Identita) session.getAttribute("identita");
    /*
     * Imposto il 6 bit del livello di autenticazione a 0, come convenzione per
     * papua se questo bit è a 1 significa che l'utente ha fatto login tramite
     * SPID
     */
    identita.setLivelloAutenticazione(
        identita.getLivelloAutenticazione()
            & (255 - NemboConstants.SPID.BIT_SPID));
    return "redirect:../seleziona_procedimento_agricolo.do";
  }

  @RequestMapping(value = "spid/seleziona_ruolo", method = RequestMethod.GET)
  public String selezionaRuoloSPID(HttpServletResponse response,
      HttpSession session) throws InternalException
  {
    addPortalCookie(response, NemboConstants.PORTAL.SPID);
    session.setAttribute(NemboConstants.PORTAL.NEMBOPRATICHE_LOGIN_PORTAL,
        NemboConstants.PORTAL.SPID);
    Identita identita = (Identita) session.getAttribute("identita");
    /*
     * Imposto il 6 bit del livello di autenticazione a 1, come convenzione per
     * papua se questo bit è a 1 significa che l'utente ha fatto login tramite
     * SPID (Non crea problemi in quanto il livello di autenticazione di
     * shibboleth/iride2 usa solo i primi 5 bit.
     */
    identita.setLivelloAutenticazione(
        identita.getLivelloAutenticazione() | NemboConstants.SPID.BIT_SPID);
    return "redirect:../seleziona_procedimento_agricolo.do";
  }

  @RequestMapping(value = "/seleziona_procedimento_agricolo", method = RequestMethod.GET)
  public String selezionaProcedimentoAgricolo(Model model) throws InternalUnexpectedException
  {
    model.addAttribute("procedimentiAgricoli", quadroEJB.getProcedimentiAgricoli());
    return "login/selezionaProcedimentoAgricolo";
  }
  public void addPortalCookie(HttpServletResponse response, String portal)
  {
    Cookie cookie = new Cookie(NemboConstants.PORTAL.NEMBOPRATICHE_LOGIN_PORTAL,
        portal);
    cookie.setPath("/" + NemboConstants.NEMBOPRATICHE.WEB_CONTEXT);
    cookie.setMaxAge(8 * 60 * 60);
    response.addCookie(cookie);
  }

  @RequestMapping(value = "seleziona_ruolo_{idProcedimentoAgricolo}", method = RequestMethod.GET)
  public String selezionaRuoloGet(
      @ModelAttribute("loginModel") ModelLogin loginModel, ModelMap model,
      HttpSession session, @ModelAttribute("idProcedimentoAgricolo") @PathVariable("idProcedimentoAgricolo") int idProcedimentoAgricolo) throws InternalException, InternalUnexpectedException
  {
    if (!quadroEJB.verificaEsistenzaProcedimentoAgricolo(idProcedimentoAgricolo))
    {
      return "redirect:../seleziona_procedimento_agricolo.do";
    }
    model.addAttribute("ruoli", loadRuoli(session,idProcedimentoAgricolo));
	session.setAttribute("headerProcedimento","header_"+idProcedimentoAgricolo+".html");
    return "login/selezionaRuolo";
  }

  @RequestMapping(value = "seleziona_ruolo_{idProcedimentoAgricolo}", method = RequestMethod.POST)
  public String selezionaRuoloPost(ModelMap model, HttpSession session,
      @ModelAttribute("loginModel") @Valid ModelLogin loginModel,
      BindingResult bindingResult,@ModelAttribute("idProcedimentoAgricolo") @PathVariable("idProcedimentoAgricolo") int idProcedimentoAgricolo)
      throws InternalException, InternalUnexpectedException
  {
    if (bindingResult.hasErrors())
    {
      return selezionaRuoloGet(loginModel, model, session, idProcedimentoAgricolo);
    }
    if (!quadroEJB.verificaEsistenzaProcedimentoAgricolo(idProcedimentoAgricolo))
    {
      return "redirect:../seleziona_procedimento_agricolo.do";
    }   
    return login(session, loginModel.getRuolo(), idProcedimentoAgricolo);
  }

  public static String login(HttpSession session, String ruolo, int idProcedimentoAgricolo)
      throws InternalServiceException
  {
    Identita identita = (Identita) session.getAttribute("identita");
    try
    {
      session.setAttribute("codiceRuoloLogin", ruolo);
      UtenteAbilitazioni utenteAbilitazioni = PapuaservProfilazioneServiceFactory
          .getRestServiceClient().login(
              identita.getCodFiscale(), identita.getCognome(),
              identita.getNome(),
              identita.getLivelloAutenticazione(),
              idProcedimentoAgricolo, ruolo);
      session.setAttribute("utenteAbilitazioni", utenteAbilitazioni);
      session.setAttribute(
          NemboConstants.GENERIC.SESSION_VAR_COLONNE_NASCOSTE,
          new MapColonneNascosteVO());
      session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA,
          new HashMap<String, String>());

      Map<String, String> mapParametri = NemboUtils.APPLICATION
          .getEjbQuadro().getParametri(new String[]
      { NemboConstants.PARAMETRO.DOQUIAGRI_FLAG_PROT,
          NemboConstants.PARAMETRO.HELP_DEFAULT_OPEN });
      session.setAttribute(NemboConstants.PARAMETRO.DOQUIAGRI_FLAG_PROT,
          mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_FLAG_PROT));
      session.setAttribute(NemboConstants.PARAMETRO.HELP_DEFAULT_OPEN,
          mapParametri.get(NemboConstants.PARAMETRO.HELP_DEFAULT_OPEN));
    }
    catch (Exception e)
    {
      throw new InternalServiceException(
          "Si è verificato un errore interno durante l'accesso ai servizi di autenticazione di PAPUA",
          InternalException.GENERIC_ERROR, e);
    }
    return "redirect:../cunembo201/index.do";
  }

  private Ruolo[] loadRuoli(HttpSession session, int idProcedimentoAgricolo) throws InternalException
  {
    Identita identita = (Identita) session.getAttribute("identita");
    try
    {
      
      ResourceBundle config = ResourceBundle.getBundle("config");
      
      PapuaservProfilazioneServiceFactory.setLoggerName("nembopratiche");
      PapuaservProfilazioneServiceFactory.getRestServiceClient().setLogger(Logger.getLogger("nembopratiche"));
      Ruolo[] ruoli = PapuaservProfilazioneServiceFactory.getRestServiceClient()
          .findRuoliForPersonaInApplicazione(
              identita.getCodFiscale(),
              identita.getLivelloAutenticazione(),
              idProcedimentoAgricolo);
      if (ruoli != null)
      {
        for (Ruolo ruolo : ruoli)
        {
          ruolo.setDescrizione(" " + ruolo.getDescrizione());
        }
      }
      return ruoli;
    }
    catch (Exception e)
    {
      throw new InternalServiceException(
          "Si è verificato un errore interno durante l'accesso ai servizi di autenticazione di PAPUA",
          InternalException.GENERIC_ERROR, e);
    }
  }
  
  @RequestMapping(value = "visualizza_immagine_{idProcedimentoAgricolo}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> visualizzaImmagine(HttpSession session,
      @PathVariable("idProcedimentoAgricolo") long idProcedimentoAgricolo)
      throws InternalUnexpectedException
  {
    ContenutoFileAllegatiDTO contenuto = quadroEJB.getImmagineDaVisualizzare(idProcedimentoAgricolo);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type",
        NemboUtils.FILE.getMimeType(".jpg"));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"\"");

    if(contenuto.getContenuto()!=null){
    	 ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
    		        contenuto.getContenuto(), httpHeaders, HttpStatus.OK);
    		    return response;
    }
   return null;
  }

}
