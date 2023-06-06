package it.csi.nembo.nembopratiche.presentation.quadro.integrazionealpremio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.IntegrazioneAlPremioDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-259-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO259ModificaIntegrazioneAlPremioController
    extends BaseController
{

  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/cunembo259m/index", method = RequestMethod.POST)
  public String modifica(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    String[] idLivello = request.getParameterValues("idLivello");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idLivello);
    List<IntegrazioneAlPremioDTO> integrazione = quadroEJB
        .getIntegrazioneAlPremio(procedimentoOggetto.getIdProcedimento(),
            procedimentoOggetto.getIdProcedimentoOggetto(), ids);
    model.addAttribute("integrazione", integrazione);

    return "integrazionealpremio/modifica";
  }

  @RequestMapping(value = "/cunembo259m/index_{idLivelloSel}", method = RequestMethod.POST)
  public String modifica(Model model, HttpSession session,
      @PathVariable("idLivelloSel") Long idLivelloSel,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    List<Long> ids = new ArrayList<>();
    ids.add(idLivelloSel);
    List<IntegrazioneAlPremioDTO> integrazione = quadroEJB
        .getIntegrazioneAlPremio(procedimentoOggetto.getIdProcedimento(),
            procedimentoOggetto.getIdProcedimentoOggetto(), ids);
    model.addAttribute("integrazione", integrazione);

    return "integrazionealpremio/modifica";
  }

  @RequestMapping(value = "/cunembo259m/modifica", method = RequestMethod.POST)
  public String modificaPost(Model model, HttpSession session,
      HttpServletRequest request)
      throws InternalUnexpectedException, NemboPermissionException
  {
    Errors errors = new Errors();

    String[] idLivelloV = request.getParameterValues("idLivello");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idLivelloV);
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    List<IntegrazioneAlPremioDTO> integrazione = quadroEJB
        .getIntegrazioneAlPremio(procedimentoOggetto.getIdProcedimento(),
            procedimentoOggetto.getIdProcedimentoOggetto(), ids);

    validate(integrazione, ids, errors, request);

    if (!errors.isEmpty())
    {
      model.addAttribute("preferRequest", true);
      model.addAttribute("errors", errors);

      model.addAttribute("integrazione", integrazione);
      return "integrazionealpremio/modifica";
    }

    quadroEJB.updateIntegrazionePremio(procedimentoOggetto, integrazione,
        getLogOperationOggettoQuadroDTO(session));

    return "redirect:/cunembo259v/index.do";
  }

  private void validate(List<IntegrazioneAlPremioDTO> integrazione,
      List<Long> ids, Errors errors, HttpServletRequest request)
  {
    for (IntegrazioneAlPremioDTO i : integrazione)
    {
      Long idLivello = i.getIdLivello();
      String contributoIntegrazioneLivello = request
          .getParameter("contributoIntegrazione_" + idLivello);
      BigDecimal contributo = errors.validateBigDecimal(
          contributoIntegrazioneLivello, "contributoIntegrazione_" + idLivello,
          2);
      if (contributo != null && i.getEconomie() != null)
        if (contributo.compareTo(i.getEconomie()) > 0)
          errors.addError("contributoIntegrazione_" + idLivello,
              "Il valore del contributo di integrazione non può superare il valore delle economie residue.");
      if (contributo != null)
        if (contributo.compareTo(BigDecimal.ZERO) <= 0)
          errors.addError("contributoIntegrazione_" + idLivello,
              "Il valore del contributo di integrazione deve essere strettamente maggiore di zero.");

      if (contributo != null)
        i.setContributoIntegrazione(contributo);

      // validate contributoRiduzioneSanzione
      String contributoRiduzioneSanzioneStr = request
          .getParameter("contributoRiduzioniSanzioni_" + idLivello);
      if (contributoRiduzioneSanzioneStr != null
          && contributoRiduzioneSanzioneStr.compareTo("") != 0)
      {
        BigDecimal contributoRidSanzioneInserito = errors.validateBigDecimal(
            contributoRiduzioneSanzioneStr,
            "contributoRiduzioniSanzioni_" + idLivello, 2);

        if (contributoRidSanzioneInserito != null
            && i.getMaxRiduzioniSanzioni() != null)
        {
          BigDecimal valToCompare = BigDecimal.ZERO;
          if (contributo.compareTo(i.getMaxRiduzioniSanzioni()) <= 0)
            valToCompare = contributo;
          else
            valToCompare = i.getMaxRiduzioniSanzioni();
          if (contributoRidSanzioneInserito.compareTo(valToCompare) > 0)
          {
            errors.addError("contributoRiduzioniSanzioni_" + idLivello,
                "Il valore delle riduzioni/sanzioni non può superare: "
                    + valToCompare);
          }
        }

        if (contributoRidSanzioneInserito != null)
          if (contributoRidSanzioneInserito.compareTo(BigDecimal.ZERO) < 0)
            errors.addError("contributoRiduzioniSanzioni_" + idLivello,
                "Il valore delle riduzioni/sanzioni non può essere negativo.");

        if (contributoRidSanzioneInserito != null)
          i.setContributoRiduzioniSanzioni(contributoRidSanzioneInserito);
      }
      else
        i.setContributoRiduzioniSanzioni(null);

    }

  }

  @RequestMapping(value = "/cunembo259m/importiNonValorizzati", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    model.addAttribute("messaggio",
        "Alcuni campi della colonna \"Di cui da sanzioni / riduzioni\" non sono valorizzati. Procedere comunque?");
    return "integrazionealpremio/conferma";
  }

}