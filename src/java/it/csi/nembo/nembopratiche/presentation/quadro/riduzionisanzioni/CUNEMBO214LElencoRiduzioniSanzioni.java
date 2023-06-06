package it.csi.nembo.nembopratiche.presentation.quadro.riduzionisanzioni;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.RiduzioniSanzioniDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-214-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo214l")
public class CUNEMBO214LElencoRiduzioniSanzioni extends BaseController
{

  @Autowired
  IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request,
      HttpSession session) throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    QuadroOggettoDTO quadro = procedimentoOggetto
        .findQuadroByCU(NemboConstants.USECASE.RIDUZIONI_SANZIONI.DETTAGLIO);

    List<RiduzioniSanzioniDTO> riduzioni = quadroEJB.getElencoRiduzioniSanzioni(
        procedimentoOggetto.getIdProcedimentoOggetto());

    model.addAttribute("riduzioni", riduzioni);
    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEJB,
        procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));

    return "riduzionisanzioni/elencoRiduzioniSanzioni";
  }
}
