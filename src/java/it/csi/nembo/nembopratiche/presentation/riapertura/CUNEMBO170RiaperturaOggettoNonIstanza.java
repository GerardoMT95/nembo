package it.csi.nembo.nembopratiche.presentation.riapertura;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@NemboSecurity(value = "CU-NEMBO-170", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO_CHIUSO)
@RequestMapping("/cunembo170")
public class CUNEMBO170RiaperturaOggettoNonIstanza extends BaseController
{
  public static final long ID_FONTE_CONTROLLO_SISTEMA_REGIONALE = 1;
  public static final long ID_ESITO_P                           = 1;
  @Autowired
  IQuadroEJB               quadroEJB                            = null;

  @RequestMapping(value = "/index")
  public String index() throws InternalUnexpectedException
  {
    return "riaperturaoggetto/confermaRiapertura";
  }

  @RequestMapping(value = "/attendere")
  public String attendere(Model model) throws InternalUnexpectedException
  {
    model.addAttribute("messaggio",
        "Attendere prego, il sistema sta eseguendo le verifiche necessarie alla riapertura dell'oggetto...");
    return "riaperturaoggetto/attenderePrego";
  }

  @RequestMapping(value = "/esegui_controlli")
  public String eseguiControlli(HttpSession session, Model model, @RequestParam(value="note") String note)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);

    if (po.getDataFine() == null)
    {
      return "<br /> Impossibile proseguire con l'operazione, l'oggetto non risulta chiuso";
    }
    if(note!=null && note.length()>4000){
    	return "<br />Le note non possono superare i 4000 caratteri (attualmente il campo note contiene "+note.length()+" caratteri).";
    }
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    return riapriProcedimentoOggetto(model, po, utenteAbilitazioni, session, note);
  }

  public String error(Model model, String messaggio)
  {
    model.addAttribute("messaggio", messaggio);
    return "riaperturaoggetto/errore";
  }

  private String riapriProcedimentoOggetto(Model model,
      ProcedimentoOggetto procedimentoOggetto,
      UtenteAbilitazioni utenteAbilitazioni, HttpSession session, String note)
      throws InternalUnexpectedException
  {
    String msgErrore = quadroEJB.riaperturaProcedimentoOggetto(
        procedimentoOggetto.getIdProcedimentoOggetto(),
        utenteAbilitazioni.getIdUtenteLogin().longValue(), note);
    refreshTestataProcedimento(quadroEJB, session,
        procedimentoOggetto.getIdProcedimento());
    if (msgErrore == null)
    {
      model.addAttribute("url", "../cunembo170/riepilogo.do");
      model.addAttribute("success",
          "Riapertura oggetto terminata correttamente");
      return "redirect";
    }
    else
    {
      return error(model, msgErrore);
    }
  }

  @NemboSecurity(value = "CU-NEMBO-170", controllo = NemboSecurity.Controllo.NESSUNO)
  @RequestMapping(value = "/riepilogo")
  public String riepilogo(HttpSession session, Model model)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    model.addAttribute("po", po);
    return "riaperturaoggetto/riepilogo";
  }

}