package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.ProcedimOggettoStampaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoIconaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellDocumentoVO;

@Controller
@NemboSecurity(value = "CU-NEMBO-126-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
@RequestMapping("/cunembo126i")
@IsPopup
public class CUNEMBO126IInserisciStampaOggetto extends BaseController
{
	@Autowired
	  IQuadroEJB quadroEJB = null;

	  @RequestMapping(value = "/inserisci", method = RequestMethod.GET)
	  public String inserisciGet(Model model, HttpSession session)
	      throws InternalUnexpectedException
	  {
	    loadTipologie(model, session);
	    return "stampeoggetto/allegaDocumento";
	  }

	  public void loadTipologie(Model model, HttpSession session) throws InternalUnexpectedException
	  {
	    List<StampaOggettoIconaDTO> tipologie = quadroEJB.getElencoDocumentiStampeDaAllegare(getIdProcedimentoOggetto(session), null);
	    model.addAttribute("tipologie", tipologie);
	  }

	  @RequestMapping(value = "/inserisci", method = RequestMethod.POST)
	  @ResponseBody
	  public String inserisciPost(Model model, HttpSession session,
	      @ModelAttribute("errors") Errors errors,
	      @RequestParam(value = "idTipoDocumento", required = false) String idTipoDocumento,
	      @RequestParam(value = "fileDaAllegare", required = false) MultipartFile fileAllegato) throws InternalUnexpectedException, IOException
	  {
	    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(session);
	    final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
	    Long lIdTipoDocumento = errors.validateMandatoryID(idTipoDocumento, "idTipoDocumento");
	    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);    
	    StampaOggettoIconaDTO oggettoIconaDTO = null;
	    if (lIdTipoDocumento != null)
	    {
	      List<StampaOggettoIconaDTO> tipoSelezionato = quadroEJB.getElencoDocumentiStampeDaAllegare(idProcedimentoOggetto, lIdTipoDocumento);
	      if (tipoSelezionato != null && !tipoSelezionato.isEmpty())
	      {
	        oggettoIconaDTO = tipoSelezionato.get(0);
	        
	        if (utenteAbilitazioni.getRuolo().isUtenteIntermediario() && NemboConstants.FLAGS.SI.equals(oggettoIconaDTO.getFlagFirmaGrafometrica()))
	        {
	          errors.addError("idTipoDocumento", "Errore di configurazione: Il tipo di documento selezionato è impostato per la firma grafometrica che non è al momento disponibile."
	              + " Si prega di contattare l'assistenza tecnica e segnalare l'errore");
	        }
	      }
	      else
	      {
	        errors.addError("idTipoDocumento", "Il tipo di documento selezionato al momento non è disponibile");
	      }
	    }
	   
	    if (fileAllegato == null || fileAllegato.isEmpty())
	    {
	      errors.addError("fileDaAllegare", Errors.ERRORE_CAMPO_OBBLIGATORIO);
	    }
	    boolean hasErrors = !errors.isEmpty();
	    if (!hasErrors)
	    {
	      ProcedimOggettoStampaDTO stampa = new ProcedimOggettoStampaDTO();
	      stampa.setExtIdUtenteAggiornamento(getIdUtenteLogin(session));
	      stampa.setIdBandoOggetto(procedimentoOggetto.getIdBandoOggetto());
	      stampa.setIdOggettoIcona(oggettoIconaDTO.getIdOggettoIcona());
	      stampa.setIdProcedimento(getIdProcedimento(session));
	      stampa.setContenutoFile(fileAllegato.getBytes());
	      stampa.setNomeFile(fileAllegato.getOriginalFilename());
	      stampa.setIdProcedimentoOggetto(idProcedimentoOggetto);
	      stampa.setIdStatoStampa((NemboConstants.FLAGS.SI.equals(oggettoIconaDTO.getFlagDaFirmare()))? NemboConstants.STATO.STAMPA.ID.FIRMATO_DIGITALMENTE: NemboConstants.STATO.STAMPA.ID.STAMPA_ALLEGATA);
	      String error = quadroEJB.aggiungiOggettoStampa(stampa, getUtenteAbilitazioni(session));
	      if (error!=null)
	      {
	        errors.addError("error",error);
	        hasErrors = true;
	      }
	    }
	    if (!hasErrors)
	    {
	      return "<refresh>";
	    }
	    else
	    {
	      model.addAttribute("idTipoDocumento", idTipoDocumento);
	      loadTipologie(model, session);
	      return "stampeoggetto/allegaDocumento";
	    }
	  }
}
