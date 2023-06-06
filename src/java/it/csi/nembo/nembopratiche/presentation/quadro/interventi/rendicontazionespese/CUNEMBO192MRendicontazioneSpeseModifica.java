package it.csi.nembo.nembopratiche.presentation.quadro.interventi.rendicontazionespese;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-192-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo192m")
public class CUNEMBO192MRendicontazioneSpeseModifica
    extends CUNEMBO192RendicontazioneSpeseAbstract
{

  @RequestMapping(value = "/modifica", method = RequestMethod.POST)
  public String controllerModifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    String[] idIntervento = request.getParameterValues("id");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idIntervento);

    HttpSession session = request.getSession();
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
    List<RigaRendicontazioneSpese> list = rendicontazioneEAccertamentoSpeseEJB
        .getElencoRendicontazioneSpese(idProcedimentoOggetto, ids);
    if (request.getParameter("confermaModificaInterventi") != null)
    {
      if (validateAndUpdate(model, request, list))
      {
        return "redirect:../" + CU_BASE_NAME + "l/index.do";
      }
      else
      {
        model.addAttribute("preferRequest", Boolean.TRUE);
      }
    }
    model.addAttribute("interventi", list);
    model.addAttribute("action", "../" + CU_BASE_NAME + "m/modifica.do");
    checkForMultiMisura(model, list);
    return JSP_BASE_URL + "modificaMultipla";
  }

  protected void checkForMultiMisura(Model model,
      List<RigaRendicontazioneSpese> list)
  {
    Map<String, String> mapMisure = new HashMap<>();
    if (list != null)
    {
      for (RigaRendicontazioneSpese riga : list)
      {
        final String codiceLivello = riga.getCodiceLivello();
        mapMisure.put(codiceLivello, codiceLivello);
      }
    }
    model.addAttribute("multiMisura",
        Boolean.valueOf(mapMisure.values().size() > 1));
  }

  private boolean validateAndUpdate(Model model, HttpServletRequest request,
      List<RigaRendicontazioneSpese> list)
      throws InternalUnexpectedException, ApplicationException
  {
    HttpSession session = request.getSession();
    Errors errors = new Errors();
    Map<String, BigDecimal> mapTotaliContributoRichiestoPerOperazione = new HashMap<>();
    for (RigaRendicontazioneSpese riga : list)
    {
      final long idIntervento = riga.getIdIntervento();
      final String nameNote = "note_" + idIntervento;
      String note = request.getParameter(nameNote);
      if (errors.validateFieldMaxLength(note, nameNote, 4000))
      {
        riga.setNote(note);
      }
      String nameFlagInterventoCompletato = "flagInterventoCompletato_"
          + idIntervento;
      if (request.getParameter(nameFlagInterventoCompletato) != null)
      {
        riga.setFlagInterventoCompletato(NemboConstants.FLAGS.SI);
      }
      else
      {
        riga.setFlagInterventoCompletato(NemboConstants.FLAGS.NO);
      }
      final String codiceLivello = riga.getCodiceLivello();
      BigDecimal totaleContributoRichiesto = NemboUtils.NUMBERS
          .nvl(mapTotaliContributoRichiestoPerOperazione.get(codiceLivello));
      if (!NemboConstants.FLAGS.SI.equals(riga.getUsaDocumentiSpesa()))
      {
        String nameSpesaSostenutaAttuale = "importoSpesa_" + idIntervento;
        BigDecimal spesaSostenutaAttuale = errors
            .validateMandatoryBigDecimalInRange(
                request.getParameter(nameSpesaSostenutaAttuale),
                nameSpesaSostenutaAttuale,
                2, BigDecimal.ZERO, NemboConstants.MAX.IMPORTO_INTERVENTO);
        riga.setImportoSpesa(spesaSostenutaAttuale);
        if (spesaSostenutaAttuale != null)
        {
          BigDecimal contributoRichiesto = spesaSostenutaAttuale.multiply(
              riga.getPercentualeContributo(),
              MathContext.DECIMAL128);
          contributoRichiesto = contributoRichiesto.scaleByPowerOfTen(-2);
          contributoRichiesto = contributoRichiesto.setScale(2,
              RoundingMode.HALF_UP);
          totaleContributoRichiesto = totaleContributoRichiesto
              .add(contributoRichiesto);
          riga.setContributoRichiesto(contributoRichiesto);
        }
      }
      else
      {
        totaleContributoRichiesto = totaleContributoRichiesto
            .add(riga.getContributoRichiesto());
      }
      mapTotaliContributoRichiestoPerOperazione.put(codiceLivello,
          totaleContributoRichiesto);
    }
    if (errors.isEmpty())
    {
      List<Long> ids = new ArrayList<Long>();
      for (RigaRendicontazioneSpese riga : list)
      {
        ids.add(riga.getIdIntervento());
      }
      verificaSuperamentoPercentuale(request, session, errors,
          mapTotaliContributoRichiestoPerOperazione, ids);
    }
    if (!errors.addToModelIfNotEmpty(model))
    {
      rendicontazioneEAccertamentoSpeseEJB.updateRendicontazioneSpese(list,
          getLogOperationOggettoQuadroDTO(session));
      return true;
    }
    else
    {
      return false;
    }
  }

  @RequestMapping(value = "/modifica_multipla", method = RequestMethod.POST)
  public String modificaMultipla(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    String[] idIntervento = request.getParameterValues("idIntervento");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idIntervento);

    HttpSession session = request.getSession();
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
    List<RigaRendicontazioneSpese> list = rendicontazioneEAccertamentoSpeseEJB
        .getElencoRendicontazioneSpese(idProcedimentoOggetto, ids);
    model.addAttribute("interventi", list);
    model.addAttribute("action", "../" + CU_BASE_NAME + "m/modifica.do");
    checkForMultiMisura(model, list);
    return JSP_BASE_URL + "modificaMultipla";
  }

  @RequestMapping(value = "/modifica_singola_{idIntervento}", method = RequestMethod.GET)
  public String modificaSingola(Model model, HttpServletRequest request,
      @PathVariable("idIntervento") long idIntervento)
      throws InternalUnexpectedException
  {
    HttpSession session = request.getSession();
    List<Long> ids = new ArrayList<Long>();
    ids.add(idIntervento);
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
    List<RigaRendicontazioneSpese> list = rendicontazioneEAccertamentoSpeseEJB
        .getElencoRendicontazioneSpese(idProcedimentoOggetto, ids);
    model.addAttribute("interventi", list);
    model.addAttribute("action", "../" + CU_BASE_NAME + "m/modifica.do");
    checkForMultiMisura(model, list);
    return JSP_BASE_URL + "modificaMultipla";
  }
}
