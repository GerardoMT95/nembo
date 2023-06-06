package it.csi.nembo.nembopratiche.presentation.quadro.interventi.accertamentospese.saldo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datafinelavori.DataFineLavoriDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaAccertamentoSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.TotaleContributoAccertamentoElencoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-212-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo212l")
public class CUNEMBO212LAccertamentoSpeseSaldoElenco
    extends CUNEMBO212AccertamentoSpeseSaldoAbstract
{
  @Autowired
  private IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    final List<TotaleContributoAccertamentoElencoDTO> contributi = rendicontazioneEAccertamentoSpeseEJB
        .getTotaleContributoErogabileNonErogabileESanzioniAcconto(
            getIdProcedimentoOggetto(session));
    final boolean isMisura81 = quadroEJB.isCodiceLivelloInvestimentoEsistente(
        getProcedimentoFromSession(session).getIdProcedimento(),
        NemboConstants.LIVELLO.CODICE_LIVELLO_8_1_1);
    model.addAttribute("isMisura81", isMisura81);
    model.addAttribute("contributi", contributi);

    if (isMisura81)
    {
      long idProcedimentoOggettoCorrente = getIdProcedimentoOggetto(session);
      Date dataFineLavori = null;
      List<DataFineLavoriDTO> list = quadroEJB.getElencoDateFineLavori(
          getProcedimentoFromSession(session).getIdProcedimento(),
          idProcedimentoOggettoCorrente);
      if (list != null)
      {
        for (DataFineLavoriDTO dfl : list)
        {
          long idProcedimentoOggetto = dfl.getIdProcedimentoOggetto();
          if (idProcedimentoOggettoCorrente == idProcedimentoOggetto)
          {
            dataFineLavori = dfl.getDataFineLavori();
            break;
          }
        }
      }
      model.addAttribute("warningImportaGIS",
          Boolean.valueOf(dataFineLavori == null));
    }
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
