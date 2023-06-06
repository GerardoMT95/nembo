package it.csi.nembo.nembopratiche.presentation.quadro.voltura;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.VolturaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-248-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo248l")
public class CUNEMBO248Voltura extends BaseController
{
  @Autowired
  protected IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {

    QuadroOggettoDTO quadro = getProcedimentoOggettoFromSession(
        request.getSession()).findQuadroByCU("CU-NEMBO-248-L");
    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        request.getSession());

    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB,
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(),
            procedimentoOggetto.getIdBandoOggetto()));

    VolturaDTO voltura = quadroEJB
        .getVoltura(procedimentoOggetto.getIdProcedimentoOggetto());
    model.addAttribute("voltura", voltura);

    return "voltura/dettaglio";
  }

}