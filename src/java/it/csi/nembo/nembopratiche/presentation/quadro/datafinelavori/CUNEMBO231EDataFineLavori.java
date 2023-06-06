package it.csi.nembo.nembopratiche.presentation.quadro.datafinelavori;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-231-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo231e")
public class CUNEMBO231EDataFineLavori extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "popupindex_{idFineLavori}", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session,
      @PathVariable(value = "idFineLavori") long idFineLavori)
      throws InternalUnexpectedException
  {

    setModelDialogWarning(model,
        "Stai cercando di eliminare il record, vuoi continuare?",
        "../cunembo231e/popupindex_" + idFineLavori + ".do");
    return "dialog/conferma";
  }

  @IsPopup
  @RequestMapping(value = "popupindex_{idFineLavori}", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request,
      @PathVariable(value = "idFineLavori") long idFineLavori)
      throws InternalUnexpectedException
  {
    quadroEjb.deleteDataFineLavori(idFineLavori,
        getLogOperationOggettoQuadroDTO(session));
    return "redirect:../cunembo231l/index.do";
  }

}
