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

import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.NotificaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo110")
@NemboSecurity(value = "CU-NEMBO-110", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO110NotificheProcedimentoController extends BaseController
{

  @Autowired
  private IRicercaEJB ricercaEJB = null;

  @RequestMapping(value = "index_{idProcedimento}", method = RequestMethod.GET)
  public String index(Model model,
      HttpSession session,
      @PathVariable("idProcedimento") long idProcedimento)
      throws InternalUnexpectedException
  {
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");

    List<NotificaDTO> notifiche = ricercaEJB.getNotifiche(idProcedimento,
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni));
    if (notifiche != null)
    {
      for (NotificaDTO n : notifiche)
      {
        List<Long> l = new LinkedList<Long>();
        l.add(new Long(n.getIdUtente()));
        List<it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin> utente = super.loadRuoloDescr(
            l);
        String u = getUtenteDescrizione(new Long(n.getIdUtente()), utente);
        n.setUtente(u);
        n.setUtenteCorrenteAbilitato(true);
        String ruoloExtIdUtenteAggiornamento = utente.get(0).getRuolo()
            .getCodice();
        utenteAbilitazioni = getUtenteAbilitazioni(session);
        String ruoloUtenteCorrente = utenteAbilitazioni.getRuolo().getCodice();
        if (ruoloExtIdUtenteAggiornamento.compareTo(ruoloUtenteCorrente) != 0)
        {
          if (ruoloUtenteCorrente
              .compareTo("SERVIZI_ASSISTENZA@AGRICOLTURA") != 0)
          {
            n.setUtenteCorrenteAbilitato(false);
          }
        }
      }
    }

    String msgErrore = (String) session.getAttribute("msgErrore");
    if (msgErrore != null && msgErrore != "")
    {
      model.addAttribute("msgErrore", msgErrore);
      session.removeAttribute("msgErrore");
    }

    model.addAttribute("notifiche", notifiche);
    model.addAttribute("idProcedimento", idProcedimento);

    return "ricercaprocedimento/notificheProcedimento";
  }

}
