package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.NotificaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo113")
@NemboSecurity(value = "CU-NEMBO-113", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO113EliminaNotificaController extends BaseController
{

  @Autowired
  private IRicercaEJB ricercaEJB = null;

  @RequestMapping(value = "popupindex_{idNotifica}", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session,
      @PathVariable("idNotifica") long idNotifica)
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
        model.addAttribute("url", "../cunembo110/index_"
            + getProcedimentoFromSession(session).getIdProcedimento() + ".do");
        return "redirect";
      }
    }

    setModelDialogWarning(model,
        "Stai cercando di eliminare la notifica selezionata, vuoi continuare?",
        "../cunembo113/popupindex_" + idNotifica + ".do");
    return "dialog/conferma";
  }

  @IsPopup
  @RequestMapping(value = "popupindex_{idNotifica}", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request, @PathVariable("idNotifica") long idNotifica)
      throws InternalUnexpectedException
  {

    ricercaEJB.eliminaNotifica(idNotifica);
    return "redirect:../cunembo110/index_"
        + getProcedimentoFromSession(session).getIdProcedimento() + ".do";
  }

  @RequestMapping(value = "canDelete_{idNotifica}", method = RequestMethod.GET)
  public String canDelete(Model model, HttpSession session,
      HttpServletRequest request, @PathVariable("idNotifica") long idNotifica)
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

    return "errorePopup";
  }

}
