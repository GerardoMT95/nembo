package it.csi.nembo.nembopratiche.presentation.quadro.sospensioneanticipo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.SospensioneAnticipoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-188", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo188")
public class CUNEMBO188DettaglioSospensioneAnticipo extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(
        NemboConstants.USECASE.SOSPENSIONE_ANTICIPO.DETTAGLIO);
    List<SospensioneAnticipoDTO> elenco = quadroEjb
        .getElencoSospensioniAnticipo(getIdProcedimentoOggetto(session));
    model.addAttribute("elenco", elenco);
    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEjb,
        procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));
    return "sospensioneanticipo/dettaglioSospensioneAnticipo";
  }

}
