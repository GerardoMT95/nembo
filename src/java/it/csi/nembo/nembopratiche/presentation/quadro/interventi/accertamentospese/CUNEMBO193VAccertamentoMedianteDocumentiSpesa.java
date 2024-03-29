package it.csi.nembo.nembopratiche.presentation.quadro.interventi.accertamentospese;

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
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InterventoAccertamentoDocumentiSpesaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaAccertamentoDocumentiSpesaDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-193-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo193v")
public class CUNEMBO193VAccertamentoMedianteDocumentiSpesa
    extends CUNEMBO193AccertamentoSpeseAbstract
{

  @RequestMapping(value = "/visualizza_{idIntervento}", method = RequestMethod.GET)

  public String modificaSingola(Model model, HttpServletRequest request,
      @PathVariable("idIntervento") long idIntervento)
      throws InternalUnexpectedException
  {
    HttpSession session = request.getSession();
    List<Long> ids = new ArrayList<>();
    ids.add(idIntervento);
    Map<String, Object> common = getCommonFromSession("CU-NEMBO-193-V", session,
        true);
    Map<Long, InterventoAccertamentoDocumentiSpesaDTO> interventi = rendicontazioneEAccertamentoSpeseEJB
        .getAccertamentoDocumentiSpesaPerIntervento(
            getIdProcedimentoOggetto(session), ids);
    common.put("mapModificaRendicontazioneDocumenti", interventi);
    saveCommonInSession(common, session);
    model.addAttribute("interventi", interventi);
    model.addAttribute("action", "../" + CU_BASE_NAME + "r/modifica.do");
    addInfoRendicontazioneIVA(model,
        getIdProcedimentoOggetto(request.getSession()));
    return JSP_BASE_PATH + "visualizzazioneDocumentiMultipla";
  }

  @RequestMapping(value = "/json/accertamento_{idIntervento}", produces = "application/json")
  @ResponseBody
  @SuppressWarnings("unchecked")
  public List<RigaAccertamentoDocumentiSpesaDTO> elenco_json(Model model,
      HttpSession session,
      @PathVariable("idIntervento") long idIntervento)
      throws InternalUnexpectedException
  {
    Map<String, Object> common = getCommonFromSession("CU-NEMBO-193-V", session,
        true);
    Map<Long, InterventoAccertamentoDocumentiSpesaDTO> interventi = (Map<Long, InterventoAccertamentoDocumentiSpesaDTO>) common
        .get("mapModificaRendicontazioneDocumenti");
    if (interventi != null)
    {
      InterventoAccertamentoDocumentiSpesaDTO intervento = interventi
          .get(idIntervento);
      if (intervento != null)
      {
        return intervento.getAccertamento();
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
        .getDocumentoSpesaInRendicontazioneByIdProcedimentOggettoIstruttoria(
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
