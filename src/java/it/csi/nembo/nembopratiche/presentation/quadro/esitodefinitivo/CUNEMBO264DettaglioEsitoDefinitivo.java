package it.csi.nembo.nembopratiche.presentation.quadro.esitodefinitivo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo264l")
@NemboSecurity(value = "CU-NEMBO-264-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO264DettaglioEsitoDefinitivo extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String get(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);

    EsitoFinaleDTO esito = quadroEJB
        .getEsitoDefinitivo(po.getIdProcedimentoOggetto());
    model.addAttribute("esito", esito);

    QuadroOggettoDTO quadro = po.findQuadroByCU("CU-NEMBO-264-M");
    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB, po.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), po.getIdBandoOggetto()));
    return "esitodefinitivo/dettaglio";
  }

}