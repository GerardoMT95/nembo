package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.error.Errors;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-126-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
@RequestMapping("/cunembo126e")
@IsPopup
public class CUNEMBO126EEliminaStampaOggetto extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/conferma_elimina_{idOggettoIcona}", method = RequestMethod.GET)
  public String confermaElimina(Model model, HttpSession session,
      @ModelAttribute("idOggettoIcona") @PathVariable("idOggettoIcona") long idOggettoIcona)
      throws InternalUnexpectedException
  {
    return "stampeoggetto/confermaElimina";
  }

  @RequestMapping(value = "/elimina_{idOggettoIcona}", method = RequestMethod.GET)
  public String eliminaAllegato(Model model, HttpSession session,
      @ModelAttribute("idOggettoIcona") @PathVariable("idOggettoIcona") long idOggettoIcona)
      throws InternalUnexpectedException
  {
    try
    {
      quadroEJB.deleteStampeOggettoInseriteDaUtente(
          getIdProcedimentoOggetto(session), idOggettoIcona);
    }
    catch (ApplicationException e)
    {
      Errors errors = new Errors();
      errors.addError("error", e.getMessage());
      model.addAttribute("errors", errors);
      return "stampeoggetto/confermaElimina";
    }
    return "redirect:/cunembo126l/lista.do";
  }

}
