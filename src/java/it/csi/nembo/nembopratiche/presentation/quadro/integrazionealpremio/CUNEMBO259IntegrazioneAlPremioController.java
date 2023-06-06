package it.csi.nembo.nembopratiche.presentation.quadro.integrazionealpremio;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.IntegrazioneAlPremioDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-259-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO259IntegrazioneAlPremioController extends BaseController
{

  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/cunembo259v/index", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    List<IntegrazioneAlPremioDTO> integrazione = quadroEJB
        .getIntegrazioneAlPremio(procedimentoOggetto.getIdProcedimento(),
            procedimentoOggetto.getIdProcedimentoOggetto(), null);

    model.addAttribute("integrazione", integrazione);

    QuadroOggettoDTO quadro = getProcedimentoOggettoFromSession(session)
        .findQuadroByCU("CU-NEMBO-259-M");
    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB,
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(),
            procedimentoOggetto.getIdBandoOggetto()));
    return "integrazionealpremio/dettaglio";
  }

}