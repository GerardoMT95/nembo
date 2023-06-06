package it.csi.nembo.nembopratiche.presentation.quadro.anticipo;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.DatiAnticipo;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.RigaAnticipoLivello;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-169-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo169v")
public class CUNEMBO169VVisualizzaDatiAnticipo extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String visualizzaModifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(
        request.getSession());
    final DatiAnticipo datiAnticipo = quadroEJB
        .getDatiAnticipo(idProcedimentoOggetto);
    final List<RigaAnticipoLivello> ripartizioneAnticipo = datiAnticipo
        .getRipartizioneAnticipo();
    model.addAttribute("datiAnticipo", datiAnticipo);
    model.addAttribute("ripartizioneAnticipo", ripartizioneAnticipo);
    BigDecimal totImportoAmmesso = BigDecimal.ZERO;
    BigDecimal totImportoInvestimento = BigDecimal.ZERO;
    BigDecimal totImportoContributo = BigDecimal.ZERO;
    BigDecimal totImportoAnticipo = BigDecimal.ZERO;
    if (ripartizioneAnticipo != null)
    {
      for (RigaAnticipoLivello riga : ripartizioneAnticipo)
      {
        totImportoAmmesso = NemboUtils.NUMBERS.add(totImportoAmmesso,
            riga.getImportoAmmesso());
        totImportoInvestimento = NemboUtils.NUMBERS
            .add(totImportoInvestimento, riga.getImportoInvestimento());
        totImportoContributo = NemboUtils.NUMBERS.add(totImportoContributo,
            riga.getImportoContributo());
        totImportoAnticipo = NemboUtils.NUMBERS.add(totImportoAnticipo,
            riga.getImportoAnticipo());
      }
    }
    model.addAttribute("totImportoAmmesso", totImportoAmmesso);
    model.addAttribute("totImportoInvestimento", totImportoInvestimento);
    model.addAttribute("totImportoContributo", totImportoContributo);
    model.addAttribute("totImportoAnticipo", totImportoAnticipo);
    return "anticipo/visualizzaDati";
  }
}