package it.csi.nembo.nembopratiche.presentation.quadro.quadrodinamico;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroDinamicoEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico.QuadroDinamicoDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo116d_{codiceQuadro}")
@NemboSecurity(value = "CU-NEMBO-116", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO, tipoMapping = NemboSecurity.TipoMapping.QUADRO_DINAMICO)
public class CUNEMBO116DElencoDatiQuadroDinamicoRecordSingolo
    extends BaseController
{
  @Autowired
  IQuadroDinamicoEJB quadroDinamicoEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session,
      @PathVariable("codiceQuadro") String codiceQuadro)
      throws ApplicationException, InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    model.addAttribute("codiceQuadroLC", codiceQuadro);
    codiceQuadro = codiceQuadro.toUpperCase();
    String cu = "CU-NEMBO-116-D_" + codiceQuadro;
    model.addAttribute("useCase", cu);
    QuadroDTO quadroDTO = po.findQuadroByCU(cu);
    model.addAttribute("quadro", quadroDTO);
    QuadroDinamicoDTO quadroDinamico = quadroDinamicoEJB
        .getQuadroDinamico(codiceQuadro, po.getIdProcedimentoOggetto(), 1l);
    verificaTipologiaQuadroDinamico(quadroDinamico);
    model.addAttribute("quadroDinamico", quadroDinamico);
    Errors errors = (Errors) session.getAttribute("qd_errors");
    if (errors != null)
    {
      errors.addToModelIfNotEmpty(model);
      session.removeAttribute("qd_errors");
    }
    return "quadridinamici/recordSingolo";
  }

  protected void verificaTipologiaQuadroDinamico(
      QuadroDinamicoDTO quadroDinamico) throws ApplicationException
  {
    if (NemboConstants.FLAGS.SI
        .equals(quadroDinamico.getFlagVisualizzazioneElenco()))
    {
      throw new ApplicationException(
          "Si � verificato un errore interno grave. Contattare l'assistenza tecnica comunicando il seguente messaggio: "
              +
              "Il quadro dinamico #" + quadroDinamico.getIdQuadro()
              + " � associato al caso d'uso di quadro dinamico a record singolo ma � stato configurato come quadro con elenco");
    }
  }
}