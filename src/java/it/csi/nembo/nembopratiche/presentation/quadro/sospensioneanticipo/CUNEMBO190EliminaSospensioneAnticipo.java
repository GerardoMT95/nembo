package it.csi.nembo.nembopratiche.presentation.quadro.sospensioneanticipo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-190", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo190")
public class CUNEMBO190EliminaSospensioneAnticipo extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "popupindex", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    setModelDialogWarning(model,
        "Stai cercando di eliminare la sospensione dell'anticipo, vuoi continuare?",
        "../cunembo190/popupindex.do");
    return "dialog/conferma";
  }

  @IsPopup
  @RequestMapping(value = "popupindex", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);

    quadroEjb.eliminaSospensioneAnticipo(
        procedimentoOggetto.getIdProcedimentoOggetto(),
        getLogOperationOggettoQuadroDTO(session));
    return "redirect:../cunembo188/index.do";
  }

}
