package it.csi.nembo.nembopratiche.presentation.quadro.quadrodinamico;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroDinamicoEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico.QuadroDinamicoDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo118_{codiceQuadro}")
@NemboSecurity(value = "CU-NEMBO-118", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO, tipoMapping = NemboSecurity.TipoMapping.QUADRO_DINAMICO)
public class CUNEMBO118EliminaRecordQuadroDinamico extends BaseController
{
  @Autowired
  IQuadroDinamicoEJB quadroDinamicoEJB;

  @IsPopup
  @RequestMapping(value = "/index_{numProgressivoRecord}", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session,
      @PathVariable("codiceQuadro") String codiceQuadro,
      @ModelAttribute("numProgressivoRecord") @PathVariable("numProgressivoRecord") int numProgressivoRecord)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    model.addAttribute("codiceQuadroLC", codiceQuadro);
    codiceQuadro = codiceQuadro.toUpperCase();
    String cu = "CU-NEMBO-118_" + codiceQuadro;
    model.addAttribute("useCase", cu);
    QuadroDTO quadroDTO = po.findQuadroByCU(cu);
    model.addAttribute("quadro", quadroDTO);
    QuadroDinamicoDTO quadroDinamico = quadroDinamicoEJB.getQuadroDinamico(
        codiceQuadro, po.getIdProcedimentoOggetto(),
        (long) numProgressivoRecord);
    model.addAttribute("quadroDinamico", quadroDinamico);
    return "quadridinamici/confermaElimina";
  }

  @RequestMapping(value = "/elimina_{numProgressivoRecord}", method = RequestMethod.POST)
  public String modifica(Model model, HttpServletRequest request,
      @PathVariable("codiceQuadro") String codiceQuadro,
      @PathVariable("numProgressivoRecord") int numProgressivoRecord)
      throws InternalUnexpectedException, ApplicationException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(
        request.getSession());
    model.addAttribute("codiceQuadroLC", codiceQuadro);
    String codiceQuadroLC = codiceQuadro;
    codiceQuadro = codiceQuadro.toUpperCase();
    String cu = "CU-NEMBO-118_" + codiceQuadro;
    model.addAttribute("useCase", cu);
    QuadroDTO quadroDTO = po.findQuadroByCU(cu);
    model.addAttribute("quadro", quadroDTO);
    LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(
        "CU-NEMBO-118_" + quadroDTO.getCodQuadro(),
        request.getSession());
    String errorMessage = quadroDinamicoEJB.eliminaRecordQuadroDinamico(
        quadroDTO.getIdQuadro(), numProgressivoRecord,
        logOperationOggettoQuadroDTO);
    if (errorMessage != null)
    {
      Errors errors = new Errors();
      errors.addError("error", errorMessage);
      request.getSession().setAttribute("qd_errors", errors);
    }
    return "redirect:../procedimento/quadro_" + codiceQuadroLC + ".do";
  }
}