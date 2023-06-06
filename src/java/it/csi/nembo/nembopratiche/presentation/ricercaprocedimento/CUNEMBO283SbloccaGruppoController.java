package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.GruppoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.ProcedimentoGruppoVO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo283")
@NemboSecurity(value = "CU-NEMBO-283", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO283SbloccaGruppoController extends BaseController
{

  @Autowired
  private IRicercaEJB ricercaEJB = null;

  @RequestMapping(value = "/popupsblocca_{idGruppoOggetto}_{idProcedimento}_{codRaggruppamento}", method = RequestMethod.GET)
  @IsPopup
  public String popupSblocca(Model model,
      HttpSession session,
      @PathVariable("idGruppoOggetto") long idGruppoOggetto,
      @PathVariable("idProcedimento") long idProcedimento,
      @PathVariable("codRaggruppamento") long codRaggruppamento)
      throws InternalUnexpectedException
  {
    List<ProcedimentoGruppoVO> elencoGruppi = ricercaEJB
        .getElencoProcedimentoGruppo(idProcedimento, codRaggruppamento);
    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    boolean isBeneficiarioOCAA = NemboUtils.PAPUASERV.isAttoreAbilitato(
        utenteAbilitazioni,
        NemboConstants.PAPUA.ATTORE.BENEFICIARIO) ||
        NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
            NemboConstants.PAPUA.ATTORE.INTERMEDIARIO_CAA);
    List<GruppoOggettoDTO> listGruppiOggetto = ricercaEJB.getElencoOggetti(
        idProcedimento,
        Arrays.asList(utenteAbilitazioni.getMacroCU()), utenteAbilitazioni.getIdProcedimento());
    String title = "";
    for (GruppoOggettoDTO gruppo : listGruppiOggetto)
    {
      if (gruppo.getIdGruppoOggetto() == idGruppoOggetto)
      {
        title = "Il gruppo " + gruppo.getDescrizione();
      }
    }

    for (ProcedimentoGruppoVO item : elencoGruppi)
    {
      if (item.getDataFine() == null)
      {
        title += " è bloccato per l'operatività dal " + item.getDataInizioStr()
            + ". Se si vuole sbloccare il gruppo inserire le motivazioni e premere conferma.";
      }
    }
    model.addAttribute("title", title);
    return "ricercaprocedimento/popupSbloccoGruppo";
  }

  @RequestMapping(value = "/popupsblocca_{idGruppoOggetto}_{idProcedimento}_{codRaggruppamento}", method = RequestMethod.POST)
  public String popupSbloccaPOs(Model model,
      HttpSession session,
      @PathVariable("idGruppoOggetto") long idGruppoOggetto,
      @PathVariable("idProcedimento") long idProcedimento,
      @PathVariable("codRaggruppamento") long codRaggruppamento,
      @RequestParam(value = "note", required = false) String note)
      throws InternalUnexpectedException
  {

    model.addAttribute("preferRequest", Boolean.TRUE);

    Errors errors = new Errors();
    if (note != null)
    {
      errors.validateFieldMaxLength(note, "note", 4000);
    }

    model.addAttribute("errors", errors);

    if (!errors.isEmpty())
    {
      return popupSblocca(model, session, idGruppoOggetto, idProcedimento,
          codRaggruppamento);
    }

    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    try
    {
      ricercaEJB.sbloccaGruppoOggetto(idProcedimento, codRaggruppamento, note,
          utenteAbilitazioni.getIdUtenteLogin());
    }
    catch (ApplicationException e)
    {
      errors.addError("error", e.getMessage());
      model.addAttribute("errors", errors);
      return popupSblocca(model, session, idGruppoOggetto, idProcedimento,
          codRaggruppamento);
    }

    return "redirect:popupsuccess.do";
  }

  @RequestMapping(value = "/popupsuccess", method = RequestMethod.GET)
  @ResponseBody
  public String popupSuccess()
  {
    return "success";
  }

}
