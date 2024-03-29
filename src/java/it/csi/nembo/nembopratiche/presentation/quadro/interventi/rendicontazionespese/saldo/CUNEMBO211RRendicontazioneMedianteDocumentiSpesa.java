package it.csi.nembo.nembopratiche.presentation.quadro.interventi.rendicontazionespese.saldo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DocumentoAllegatoDownloadDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InterventoRendicontazioneDocumentiSpesaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneDocumentiSpesaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-211-R", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo211r")
public class CUNEMBO211RRendicontazioneMedianteDocumentiSpesa
    extends CUNEMBO211RendicontazioneSpeseSaldoAbstract
{

  @RequestMapping(value = "/modifica", method = RequestMethod.POST)
  public String controllerModifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    if (request.getParameter("confermaModificaRendicontazione") != null)
    {
      if (validateAndUpdate(model, request))
      {
        return "redirect:../" + CU_BASE_NAME + "l/index.do";
      }
      else
      {
        model.addAttribute("preferRequest", Boolean.TRUE);
      }
    }
    model.addAttribute("action", "../" + CU_BASE_NAME + "r/modifica.do");
    addInfoRendicontazioneIVA(model,
        getIdProcedimentoOggetto(request.getSession()));
    return JSP_BASE_URL + "rendicontazioneDocumentiMultipla";
  }

  private boolean validateAndUpdate(Model model, HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    HttpSession session = request.getSession();
    Errors errors = new Errors();

    Map<String, Object> common = getCommonFromSession("CU-NEMBO-211-R", session,
        true);
    String[] idIntervento = request.getParameterValues("idIntervento");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idIntervento);
    Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi = getRendicontazioneDocumentiSpesaPerIntervento(
        getIdProcedimentoOggetto(session), ids);
    common.put("mapModificaRendicontazioneDocumenti", interventi);

    for (InterventoRendicontazioneDocumentiSpesaDTO intervento : interventi
        .values())
    {
      String nameFlagInterventoCompletato = "flagInterventoCompletato_"
          + intervento.getIdIntervento();
      if (request.getParameter(nameFlagInterventoCompletato) != null)
      {
        intervento.setFlagInterventoCompletato(NemboConstants.FLAGS.SI);
      }
      else
      {
        intervento.setFlagInterventoCompletato(NemboConstants.FLAGS.NO);
      }

      final String nameNote = "note_" + intervento.getIdIntervento();
      String note = request.getParameter(nameNote);
      if (errors.validateOptionalFieldMaxLength(note, 4000, nameNote))
      {
        intervento.setNote(note);
      }
      for (RigaRendicontazioneDocumentiSpesaDTO riga : intervento
          .getRendicontazione())
      {
        final long idDocumentoSpesaInterven = riga
            .getIdDocumentoSpesaInterven();
        final String nameImportoRendicontato = "importoRendicontato_"
            + idDocumentoSpesaInterven;

        /*
         * Il massimo importo rendicontabile � dato da: min (importo del
         * documento di spesa ripartito sull'intervento, importo delle ricevute
         * di pagamento) meno le rendicontazioni precedenti. Se non ci sono
         * ricevute di pagamento il calcolo allora si fa solo sull'importo
         * deldocumento di spesa ripartito sull'intervento. E' un workaround per
         * permettere il corretto funzionamento delle domande i cui documenti di
         * spesa sono stati registrati prima dell'introduzione del caso d'uso
         * delle ricevute di pagamento
         */
        BigDecimal maxImportoRendicontabile = null;
        BigDecimal importoRicevutePagamento = riga
            .getImportoRicevutePagamento();
        if (importoRicevutePagamento != null)
        {
          maxImportoRendicontabile = NemboUtils.NUMBERS
              .min(riga.getImporto(), importoRicevutePagamento);
        }
        else
        {
          maxImportoRendicontabile = riga.getImporto();
        }
        maxImportoRendicontabile = maxImportoRendicontabile
            .subtract(riga.getImportoRendicontatoPrec());

        String importoRendicontato = request
            .getParameter(nameImportoRendicontato);
        BigDecimal bdImportoRendicontato = errors
            .validateMandatoryBigDecimalInRange(importoRendicontato,
                nameImportoRendicontato, 2, BigDecimal.ZERO,
                maxImportoRendicontabile);
        riga.setImportoRendicontato(bdImportoRendicontato);
        riga.setImportoRendicontatoString(importoRendicontato);
        riga.setErrorMessage(errors.get(nameImportoRendicontato));
      }
    }
    if (!errors.addToModelIfNotEmpty(model))
    {
      try
      {
        rendicontazioneEAccertamentoSpeseEJB
            .updateRendicontazioneSpeseDocumenti(interventi,
                getLogOperationOggettoQuadroDTO(session));
        clearCommonInSession(session);
        return true;
      }
      catch (ApplicationException e)
      {
        errors.addError("error", e.getMessage());
        errors.addToModel(model);
      }
    }
    model.addAttribute("interventi", interventi);
    saveCommonInSession(common, session);
    return false;

  }

  @RequestMapping(value = "/rendicontazione_documenti", method = RequestMethod.POST)
  public String modificaMultipla(Model model, HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    String[] idIntervento = request.getParameterValues("idIntervento");
    List<Long> ids = NemboUtils.LIST.toListOfLong(idIntervento);

    HttpSession session = request.getSession();
    Map<String, Object> common = getCommonFromSession("CU-NEMBO-211-R", session,
        true);
    Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi = getRendicontazioneDocumentiSpesaPerIntervento(
        getIdProcedimentoOggetto(session), ids);
    common.put("mapModificaRendicontazioneDocumenti", interventi);
    saveCommonInSession(common, session);
    model.addAttribute("interventi", interventi);
    model.addAttribute("action", "../" +
        CU_BASE_NAME + "r/modifica.do");
    addInfoRendicontazioneIVA(model,
        getIdProcedimentoOggetto(request.getSession()));
    return JSP_BASE_URL + "rendicontazioneDocumentiMultipla";
  }

  @RequestMapping(value = "/rendicontazione_documenti_singola_{idIntervento}", method = RequestMethod.GET)
  public String modificaSingola(Model model, HttpServletRequest request,
      @PathVariable("idIntervento") long idIntervento)
      throws InternalUnexpectedException, ApplicationException
  {
    HttpSession session = request.getSession();
    List<Long> ids = new ArrayList<>();
    ids.add(idIntervento);
    Map<String, Object> common = getCommonFromSession("CU-NEMBO-211-R", session,
        true);
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
    Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi = getRendicontazioneDocumentiSpesaPerIntervento(
        idProcedimentoOggetto, ids);
    preloadImportoRendicontatoIfNull(interventi);
    common.put("mapModificaRendicontazioneDocumenti", interventi);
    saveCommonInSession(common, session);
    model.addAttribute("interventi", interventi);
    model.addAttribute("action", "../" + CU_BASE_NAME + "r/modifica.do");
    addInfoRendicontazioneIVA(model,
        getIdProcedimentoOggetto(request.getSession()));
    return JSP_BASE_URL + "rendicontazioneDocumentiMultipla";
  }

  protected Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> getRendicontazioneDocumentiSpesaPerIntervento(
      final long idProcedimentoOggetto, final List<Long> ids)
      throws InternalUnexpectedException, ApplicationException
  {
    Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi = rendicontazioneEAccertamentoSpeseEJB
        .getRendicontazioneDocumentiSpesaPerIntervento(idProcedimentoOggetto,
            ids);
    if (ids.size() == 1)
    {
      if (interventi == null || interventi.size() == 0)
      {
        // Caso di modifica singola in cui non ci sono documenti di spesa
        // associati a questo intervento ==> carico solo
        // i dati della rendicontazione
        List<RigaRendicontazioneSpese> elenco = rendicontazioneEAccertamentoSpeseEJB
            .getElencoRendicontazioneSpese(idProcedimentoOggetto, ids);
        if (elenco != null && elenco.size() == 1)
        {
          final Long idIntervento = ids.get(0);
          RigaRendicontazioneSpese rendicontazione = elenco.get(0);
          InterventoRendicontazioneDocumentiSpesaDTO intervento = new InterventoRendicontazioneDocumentiSpesaDTO();
          intervento.setDescIntervento(rendicontazione.getDescIntervento());
          intervento.setFlagInterventoCompletato(
              rendicontazione.getFlagInterventoCompletato());
          intervento.setIdIntervento(idIntervento);
          intervento.setNote(rendicontazione.getNote());
          intervento.setPercentualeContributo(
              rendicontazione.getPercentualeContributo());
          intervento.setProgressivo(rendicontazione.getProgressivo());
          intervento.setRendicontazione(new ArrayList<>());
          interventi.put(idIntervento, intervento);
        }
        else
        {
          throw new ApplicationException(
              "Errore interno grave: sono stati trovati pi� dati di rendicontazione a fronte di un solo intervento");
        }
      }
    }
    return interventi;
  }

  private void preloadImportoRendicontatoIfNull(
      Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi)
      throws InternalUnexpectedException
  {
    for (InterventoRendicontazioneDocumentiSpesaDTO intervento : interventi
        .values())
    {
      for (RigaRendicontazioneDocumentiSpesaDTO riga : intervento
          .getRendicontazione())
      {
        BigDecimal importoRendicontato = riga.getImportoRendicontato();
        if (importoRendicontato != null
            && BigDecimal.ZERO.compareTo(importoRendicontato) != 0)
        {
          // Se trovo anche un solo importo != 0 allora vuol dire che questi
          // sono dati inseriti dall'utente ==> non
          // precarico nulla
          return;
        }
      }
    }
    // se tutti gli importi sono a 0 allora vuol dire che non ho mai modificato
    // questi dati (o quanto meno � stato
    // concordato con Arpea che sia accettabile questa assunzione) e quindi
    // precarico i valori
    for (InterventoRendicontazioneDocumentiSpesaDTO intervento : interventi
        .values())
    {
      for (RigaRendicontazioneDocumentiSpesaDTO riga : intervento
          .getRendicontazione())
      {
        BigDecimal maxImportoRendicontabile = NemboUtils.NUMBERS
            .nvl(riga.getImportoRicevutePagamento())
            .subtract(riga.getImportoRendicontatoPrec());
        riga.setImportoRendicontato(maxImportoRendicontabile);
      }
    }
  }

  @RequestMapping(value = "/json/rendicontazione_{idIntervento}", produces = "application/json")
  @ResponseBody
  @SuppressWarnings("unchecked")
  public List<RigaRendicontazioneDocumentiSpesaDTO> elenco_json(Model model,
      HttpSession session,
      @PathVariable("idIntervento") long idIntervento)
      throws InternalUnexpectedException
  {
    Map<String, Object> common = getCommonFromSession("CU-NEMBO-211-R", session,
        true);
    Map<Long, InterventoRendicontazioneDocumentiSpesaDTO> interventi = (Map<Long, InterventoRendicontazioneDocumentiSpesaDTO>) common
        .get("mapModificaRendicontazioneDocumenti");
    if (interventi != null)
    {
      InterventoRendicontazioneDocumentiSpesaDTO intervento = interventi
          .get(idIntervento);
      if (intervento != null)
      {
        return intervento.getRendicontazione();
      }
    }
    return null;
  }

  @RequestMapping(value = "/documento_{idDocumentoSpesa}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> downloadDocumento(Model model,
      HttpSession session,
      @PathVariable("idDocumentoSpesa") long idDocumentoSpesa)
      throws InternalUnexpectedException, ApplicationException
  {
    DocumentoAllegatoDownloadDTO documento = rendicontazioneEAccertamentoSpeseEJB
        .getDocumentoSpesaCorrenteByIdProcedimentoOggetto(
            getIdProcedimentoOggetto(session),
            idDocumentoSpesa);
    HttpHeaders httpHeaders = new HttpHeaders();
    final String nomeFile = documento.getFileName();
    httpHeaders.add("Content-type", NemboUtils.FILE.getMimeType(nomeFile));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"" + nomeFile + "\"");
    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
        documento.getBytes(), httpHeaders, HttpStatus.OK);
    return response;
  }

}
