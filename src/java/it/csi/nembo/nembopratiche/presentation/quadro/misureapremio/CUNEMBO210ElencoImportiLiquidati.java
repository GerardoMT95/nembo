package it.csi.nembo.nembopratiche.presentation.quadro.misureapremio;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.ImportoLiquidatoDTO;
import it.csi.nembo.nembopratiche.dto.RipartizioneImportoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalServiceException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellEsitoVO;
import it.csi.smrcomms.smrcomm.exception.SmrcommInternalException;

@Controller
@RequestMapping("/cunembo210")
@NemboSecurity(value = "CU-NEMBO-210", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO210ElencoImportiLiquidati extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    ProcedimentoOggetto p = getProcedimentoOggettoFromSession(session);
    List<ImportoLiquidatoDTO> importi = quadroEJB
        .getElencoImportiLiquidazione(p.getIdProcedimentoOggetto());
    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);

    List<ImportoLiquidatoDTO> listeDelPO = quadroEJB
        .getListeDegliImportiLiquidazione(p.getIdProcedimentoOggetto());
    if (listeDelPO != null)
      model.addAttribute("listSize", listeDelPO.size());

    model.addAttribute("listeDelPO", listeDelPO);

    if ((utenteAbilitazioni.getRuolo().isUtentePA()
        || utenteAbilitazioni.getRuolo().isUtenteServiziAgri())
        && importi != null && listeDelPO != null)
      model.addAttribute("showPrintButton", true);

    /*
     * for(ImportoLiquidatoDTO i : importi) { if(i.getFlagStatoLista()!=null)
     * if(i.getFlagStatoLista().compareTo("A")==0 ||
     * i.getFlagStatoLista().compareTo("T")==0 ) {
     * if(i.getExtIdDocumentoIndex()!=null && i.getExtIdDocumentoIndex()!=0)
     * model.addAttribute("idDocIndex", i.getExtIdDocumentoIndex());
     * model.addAttribute("idListaStampa", i.getIdListaLiquidazione());
     * model.addAttribute("flagStatoLista", i.getFlagStatoLista()); } }
     */

    return "importiliquidati/importiLiquidati";
  }

  @RequestMapping(value = "getImportiLiquidati", produces = "application/json")
  @ResponseBody
  public List<ImportoLiquidatoDTO> getImportiLiquidatiJson(Model model,
      HttpSession session) throws InternalUnexpectedException
  {
    ProcedimentoOggetto p = getProcedimentoOggettoFromSession(session);
    List<ImportoLiquidatoDTO> importi = quadroEJB
        .getElencoImportiLiquidazione(p.getIdProcedimentoOggetto());
    return importi;
  }

  @RequestMapping(value = "/ripartizioneImportiPopup_{idListaLiquidazImpLiq}", method = RequestMethod.GET)
  @IsPopup
  public String index(
      @PathVariable(value = "idListaLiquidazImpLiq") long idListaLiquidazImpLiq,
      Model model, HttpSession session, HttpServletResponse response)
      throws InternalUnexpectedException
  {
    List<RipartizioneImportoDTO> importi = quadroEJB
        .getRipartizioneImporto(idListaLiquidazImpLiq);
    model.addAttribute("importi", importi);

    return "importiliquidati/ripartizionePopup";
  }

  @RequestMapping(value = "/esitoLiquidazionePopup_{idImportoLiquidato}", method = RequestMethod.GET)
  @IsPopup
  public String esito(
      @PathVariable(value = "idImportoLiquidato") long idImportoLiquidato,
      Model model, HttpSession session, HttpServletResponse response)
      throws InternalUnexpectedException
  {
    ImportoLiquidatoDTO i = quadroEJB
        .getElencoImportiLiquidazioneByIdImportoLiquidato(idImportoLiquidato);
    model.addAttribute("importoEsito", i);
    return "importiliquidati/esitoLiquidazionePopup";
  }

  @RequestMapping(value = "/getDoc_{extIdDocIndex}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> getDoc(
      @PathVariable(value = "extIdDocIndex") long extIdDocIndex, Model model,
      HttpSession session,
      HttpServletResponse response)
      throws InternalUnexpectedException, SmrcommInternalException,
      InternalServiceException, UnrecoverableException
  {
    AgriWellEsitoVO ee = NemboUtils.PORTADELEGATA.getAgriwellCSIInterface()
        .agriwellServiceLeggiDoquiAgri(extIdDocIndex);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type", NemboUtils.FILE.getMimeType(ee.getNomeFile()));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"" + ee.getNomeFile() + "\"");
    ResponseEntity<byte[]> res = new ResponseEntity<byte[]>(
        ee.getContenutoFile(), httpHeaders, HttpStatus.OK);
    return res;
  }
}