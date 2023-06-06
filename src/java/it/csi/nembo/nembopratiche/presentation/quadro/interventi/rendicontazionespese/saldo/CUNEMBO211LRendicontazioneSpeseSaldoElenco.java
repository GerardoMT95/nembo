package it.csi.nembo.nembopratiche.presentation.quadro.interventi.rendicontazionespese.saldo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.PotenzialeErogabileESanzioniRendicontazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-211-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo211l")
public class CUNEMBO211LRendicontazioneSpeseSaldoElenco
    extends CUNEMBO211RendicontazioneSpeseSaldoAbstract
{
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    PotenzialeErogabileESanzioniRendicontazioneDTO totali = rendicontazioneEAccertamentoSpeseEJB
        .getPotenzialeErogabileETotaleSanzioniRendicontazione(
            getIdProcedimentoOggetto(session), true);
    model.addAttribute("potenzialeErogabile", totali.getPotenzialeErogabile());
    model.addAttribute("totaliSanzioni", totali.getTotaleSanzioni());
    String flagRendicontazioneIVA = rendicontazioneEAccertamentoSpeseEJB
        .getInfoSePossibileRendicontazioneConIVAByIdProcedimentoOggetto(
            getIdProcedimentoOggetto(session));
    switch (flagRendicontazioneIVA)
    {
      case NemboConstants.FLAGS.NO:
        model.addAttribute("msgIVA",
            "Il beneficiario NON può rendicontare l'IVA");
        break;
      case NemboConstants.FLAGS.SI:
        model.addAttribute("msgIVA", "Il beneficiario può rendicontare l'IVA");
        break;
    }
    List<RigaRendicontazioneSpese> elenco = rendicontazioneEAccertamentoSpeseEJB
        .getElencoRendicontazioneSpese(getIdProcedimentoOggetto(session), null);
    int countModifica = 0;
    if (elenco != null)
    {
      for (RigaRendicontazioneSpese riga : elenco)
      {
        if (!NemboConstants.FLAGS.SI.equals(riga.getUsaDocumentiSpesa()))
        {
          countModifica++;
        }
      }
    }
    model.addAttribute("modificaMultipla", Boolean.valueOf(countModifica > 1));
    return JSP_BASE_URL + "elenco";
  }

  @RequestMapping(value = "/json/elenco", produces = "application/json")
  @ResponseBody
  public List<RigaRendicontazioneSpese> elenco_json(Model model,
      HttpServletRequest request)
      throws InternalUnexpectedException
  {
    HttpSession session = request.getSession();
    List<RigaRendicontazioneSpese> elenco = rendicontazioneEAccertamentoSpeseEJB
        .getElencoRendicontazioneSpese(getIdProcedimentoOggetto(session), null);
    return elenco;
  }
}
