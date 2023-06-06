package it.csi.nembo.nembopratiche.presentation.quadro.interventi.accertamentospese;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaAccertamentoSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.TotaleContributoAccertamentoElencoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-193-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo193l")
public class CUNEMBO193LAccertamentoSpeseElenco
    extends CUNEMBO193AccertamentoSpeseAbstract
{

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final List<TotaleContributoAccertamentoElencoDTO> contributi = rendicontazioneEAccertamentoSpeseEJB
        .getTotaleContributoErogabileNonErogabileESanzioniAcconto(
            getIdProcedimentoOggetto(request.getSession()));
    model.addAttribute("contributi", contributi);
    addInfoRendicontazioneIVA(model,
        getIdProcedimentoOggetto(request.getSession()));
    return JSP_BASE_PATH + "elenco";
  }

  @RequestMapping(value = "/json/elenco", produces = "application/json")
  @ResponseBody
  public List<RigaAccertamentoSpese> elenco_json(Model model,
      HttpServletRequest request)
      throws InternalUnexpectedException
  {
    HttpSession session = request.getSession();
    List<RigaAccertamentoSpese> elenco = rendicontazioneEAccertamentoSpeseEJB
        .getElencoAccertamentoSpese(getIdProcedimentoOggetto(session), null);
    return elenco;
  }
}
