package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.GravitaNotificaVO;
import it.csi.nembo.nembopratiche.dto.NotificaDTO;
import it.csi.nembo.nembopratiche.dto.StatoNotificaVO;
import it.csi.nembo.nembopratiche.dto.VisibilitaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo111")
@NemboSecurity(value = "CU-NEMBO-111", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO111ModificaNotificaProcedimentoController
    extends BaseController
{

  private static final long APERTA     = 0;
  private static final long CHIUSA     = 1;

  @Autowired
  private IRicercaEJB       ricercaEJB = null;
  @Autowired
  private IQuadroEJB        quadroEJB  = null;

  @RequestMapping(value = "index_{idNotifica}", method = RequestMethod.GET)
  public String index(Model model, HttpSession session,
      @PathVariable("idNotifica") long idNotifica)
      throws InternalUnexpectedException
  {
    model.addAttribute("idProcedimento",
        getProcedimentoFromSession(session).getIdProcedimento());
    return defaultView(idNotifica, model, session);
  }

  @RequestMapping(value = "/index_{idNotifica}", method = RequestMethod.POST)
  public String post(Model model, @PathVariable("idNotifica") long idNotifica,
      @RequestParam(value = "descrizione", required = false) String descrizione,
      @RequestParam(value = "gravita", required = false) String gravita,
      @RequestParam(value = "stato", required = false) String stato,
      @RequestParam(value = "visibilita", required = false) String visibilita,
      HttpSession session)
      throws InternalUnexpectedException, NemboPermissionException
  {
    long idProcedimento = getProcedimentoFromSession(session)
        .getIdProcedimento();
    model.addAttribute("idProcedimento", idProcedimento);
    model.addAttribute("prfvalues", Boolean.TRUE);

    Errors errors = new Errors();

    /* Validazione input */
    descrizione = descrizione.trim();
    errors.validateFieldLength(descrizione, "descrizione", 5, 4000);
    Long idStato = errors.validateMandatoryLong(stato, "stato");
    Long idGravita = errors.validateMandatoryLong(gravita, "gravita");
    Long idVisibilita = errors.validateMandatoryLong(visibilita, "visibilita");

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
      return defaultView(idNotifica, model, session);
    }

    /*
     * Preparazione nuova notifica da passare al dao per poi fare l'inserimento
     */
    NotificaDTO notificaOld = ricercaEJB.getNotificaById(idNotifica);
    NotificaDTO notificaNew = new NotificaDTO();
    notificaNew.setNote(descrizione);
    notificaNew.setIdNotifica(notificaOld.getIdNotifica());
    notificaNew.setDataInizio(notificaOld.getDataInizio());

    if (idStato == APERTA)
      notificaNew.setStato("Aperta");
    else
      notificaNew.setStato("Chiusa");

    notificaNew.setIdGravitaNotifica(idGravita);
    notificaNew.setIdVisibilita(idVisibilita);

    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    notificaNew.setIdUtente(utenteAbilitazioni.getIdUtenteLogin());

    ricercaEJB.updateNotifica(idNotifica, notificaNew, idProcedimento);

    boolean notificheBloccanti = quadroEJB.checkNotifiche(idProcedimento,
        NemboConstants.FLAG_NOTIFICA.BLOCCANTE,
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni));
    session.setAttribute("notificheBloccanti", notificheBloccanti);

    boolean notificheWarning = quadroEJB.checkNotifiche(idProcedimento,
        NemboConstants.FLAG_NOTIFICA.WARNING,
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni));
    session.setAttribute("notificheWarning", notificheWarning);

    boolean notificheGravi = quadroEJB.checkNotifiche(idProcedimento,
        NemboConstants.FLAG_NOTIFICA.GRAVE,
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni));
    session.setAttribute("notificheGravi", notificheGravi);

    return "redirect:../cunembo110/index_"
        + getProcedimentoFromSession(session).getIdProcedimento() + ".do";
  }

  public String defaultView(long idNotifica, Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    NotificaDTO notifica = ricercaEJB.getNotificaById(idNotifica);

    List<Long> l = new LinkedList<Long>();
    l.add(new Long(notifica.getIdUtente()));
    List<it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin> utente = super.loadRuoloDescr(
        l);

    String ruoloExtIdUtenteAggiornamento = utente.get(0).getRuolo().getCodice();
    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    String ruoloUtenteCorrente = utenteAbilitazioni.getRuolo().getCodice();
    if (ruoloExtIdUtenteAggiornamento.compareTo(ruoloUtenteCorrente) != 0)
    {
      if (ruoloUtenteCorrente.compareTo("SERVIZI_ASSISTENZA@AGRICOLTURA") != 0)
      {
        session.setAttribute("msgErrore",
            "La notifica può essere modificata solo da utenti con ruolo uguale a quello dell'utente connesso");
        return "redirect:../cunembo110/index_"
            + getProcedimentoFromSession(session).getIdProcedimento() + ".do";
      }
    }

    String u = getUtenteDescrizione(new Long(notifica.getIdUtente()), utente);
    notifica.setUtente(u);

    if (notifica.getDataFine() == null)
      notifica.setStato("Aperta");
    else
      notifica.setStato("Chiusa");

    model.addAttribute("notifica", notifica);

    List<GravitaNotificaVO> elencoGravita = ricercaEJB
        .getElencoGravitaNotifica();
    model.addAttribute("elencoGravita", elencoGravita);
    model.addAttribute("idGravitaSelezionato", notifica.getIdGravitaNotifica());

    List<StatoNotificaVO> elencoStati = new LinkedList<StatoNotificaVO>();
    StatoNotificaVO s = null;
    s = new StatoNotificaVO();
    s.setId(APERTA);
    s.setDescrizione("Aperta");
    elencoStati.add(s);
    s = new StatoNotificaVO();
    s.setId(CHIUSA);
    s.setDescrizione("Chiusa");
    elencoStati.add(s);
    model.addAttribute("elencoStati", elencoStati);

    if (notifica.getStato().compareTo("Aperta") == 0)
      model.addAttribute("idStatoSelezionato", APERTA);
    else
      model.addAttribute("idStatoSelezionato", CHIUSA);

    List<VisibilitaDTO> elencoVisibilita = new LinkedList<VisibilitaDTO>();
    UtenteAbilitazioni uAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    elencoVisibilita = ricercaEJB.getElencoVisibilitaNotifiche(
        NemboUtils.PAPUASERV.getFirstCodiceAttore(uAbilitazioni));
    model.addAttribute("elencoVisibilita", elencoVisibilita);

    return "ricercaprocedimento/modificaNotificheProcedimento";
  }

}
