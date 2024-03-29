package it.csi.nembo.nembopratiche.presentation.quadro.conticorrenti;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.conticorrenti.ContoCorrenteDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-120", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class ContiCorrentiController extends BaseController
{

  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/cunembo119/index", method = RequestMethod.GET)
  @NemboSecurity(value = "CU-NEMBO-119", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
  public String dettaglio(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    ContoCorrenteDTO contoCorrente = quadroEJB.getContoCorrente(
        procedimentoOggetto.getIdProcedimentoOggetto(),
        ((procedimentoOggetto.getDataFine() != null)
            ? procedimentoOggetto.getDataFine() : new Date()));

    if (contoCorrente != null)
    {
      model.addAttribute("contoCorrente", contoCorrente);
    }
    else
    {
      model.addAttribute("msgErrore", "Nessun Conto Corrente associato! ");
    }

    return "contiCorrenti/dettaglioDati";
  }

  @RequestMapping(value = "/cunembo120/index", method = RequestMethod.GET)
  public String modifica(Model model, HttpSession session)
      throws InternalUnexpectedException, ApplicationException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    /*
     * Verificare che sul procedimento corrente NON ci sia alcun oggetto con
     * codice DIRPA in stato trasmesso (stato oggetto = 10 su
     * NEMBO_T_ITER_PROCEDIMENTO_OGGE con data_fine null).
     */
    if (quadroEJB.procedimentoHasOggettoInStato(
        procedimentoOggetto.getIdProcedimento(), "DIRPA",
        NemboConstants.STATO.ITER.ID.TRASMESSO))
    {
      throw new ApplicationException(
          "Impossibile modificare il conto corrente in quanto e' stata trasmessa una Disposizione Irrevocabile di Pagamento.");
    }

    List<ContoCorrenteDTO> conticorrenti = quadroEJB
        .getContiCorrentiValidi(getIdProcedimento(session));
    ContoCorrenteDTO contoCorrenteSel = quadroEJB.getContoCorrente(
        procedimentoOggetto.getIdProcedimentoOggetto(),
        ((procedimentoOggetto.getDataFine() != null)
            ? procedimentoOggetto.getDataFine() : new Date()));

    if (conticorrenti == null)
    {
      model.addAttribute("msgErrore",
          "Non � presente nessun Conto Corrente associabile ");
    }

    model.addAttribute("conticorrenti", conticorrenti);
    model.addAttribute("contoCorrenteSel", contoCorrenteSel);
    return "contiCorrenti/modificaDati";
  }

  @RequestMapping(value = "/cunembo120/index", method = RequestMethod.POST)
  public String modificaPost(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException,
      NemboPermissionException, ApplicationException
  {
    String idContoCorrenteSel =   request
        .getParameter("contoSelezionato");

    if (GenericValidator.isBlankOrNull(idContoCorrenteSel))
    {
      model.addAttribute("msgErrore",
          "E' necessario selezionare almeno un Conto Corrente! ");
      return modifica(model, session);
    }
    else
    {
      ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
          session);
      QuadroOggettoDTO quadro = procedimentoOggetto
          .findQuadroByCU("CU-NEMBO-120");
      quadroEJB.updateContoCorrenteOggetto(getIdProcedimentoOggetto(session),
          quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto(),
          getIdUtenteLogin(session), Long.parseLong(idContoCorrenteSel));
    }
    return "redirect:/cunembo119/index.do";
  }

}