package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import it.csi.nembo.nembopratiche.business.IAsyncEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.ProcedimOggettoStampaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoIconaDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellEsitoVO;

@Controller
@NemboSecurity(value = "CU-NEMBO-126-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo126l")
public class CUNEMBO126LListaStampeOggetto extends BaseController
{
	@Autowired
	  IQuadroEJB quadroEJB = null;
	  @Autowired
	  IAsyncEJB  asyncEJB  = null;

	  @RequestMapping(value = "/conferma_rigenera_{idOggettoIcona}")
	  public String confermaRigenera(HttpSession session, Model model, @PathVariable("idOggettoIcona") long idOggettoIcona) throws InternalUnexpectedException
	  {
	    ProcedimOggettoStampaDTO procedimOggettoStampaDTO = quadroEJB.getProcedimOggettoStampaByIdOggetoIcona(getIdProcedimentoOggetto(session), idOggettoIcona);
	    if (procedimOggettoStampaDTO.getExtIdDocumentoIndex() != null)
	    {
	      Errors errors = new Errors();
	      errors.addError("error", "La stampa e' attualmente disponibile");
	      errors.addToModelIfNotEmpty(model);
	    }
	    else
	    {
	      if (procedimOggettoStampaDTO.getIdStatoStampa() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
	      {
	        if (NemboUtils.DATE.diffInSeconds(new Date(), procedimOggettoStampaDTO.getDataInizio()) < NemboConstants.TEMPO.SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA) // 10
	                                                                                                                                                           // minuti
	        {
	          Errors errors = new Errors();
	          errors.addError("error",
	              "La stampa richiesta e' attualmente in corso di generazione, l'operazione potrebbe richiedere alcuni minuti, si prega di attendere");
	          errors.addToModelIfNotEmpty(model);
	        }
	        else
	        {
	          model.addAttribute("messaggio",
	              "La stampa è in corso di generazione da più di 10 minuti, &egrave; possibile che si sia verificato un errore, si desidera rigenerarla?");
	        }
	      }
	      else
	      {
	        model.addAttribute("messaggio",
	            "La stampa è terminata con degli errori, &egrave; possibile che il problema fosse solo temporaneo, si desidera provare rigenerare la stampa?");
	      }
	    }
	    return "stampeoggetto/confermaRigenera";
	  }

	  @RequestMapping(value = "/rigenera_{idOggettoIcona}", method = RequestMethod.GET)
	  public String rigeneraPost(HttpSession session, Model model, @PathVariable("idOggettoIcona") long idOggettoIcona) throws InternalUnexpectedException, ApplicationException
	  {
	    /*Boolean daRistampare = quadroEJB.ripristinaStampaOggetto(getIdProcedimentoOggetto(session), idOggettoIcona, getIdUtenteLogin(session));
	    if (Boolean.TRUE.equals(daRistampare))
	    {
	      asyncEJB.generaStampa(getIdProcedimentoOggetto(session), getIdProcedimento(session), idOggettoIcona);
	    }
	    return "redirect:/cunembo126l/attendere_prego_" + idOggettoIcona + ".do";*/
		  return visualizzaPost(session, model, idOggettoIcona);
	  }

	  @RequestMapping(value = "/attendere_prego_{idOggettoIcona}")
	  public String attenderePrego(HttpSession session, Model model, @PathVariable("idOggettoIcona") long idOggettoIcona) throws InternalUnexpectedException
	  {
	    ProcedimOggettoStampaDTO stampa = quadroEJB.getProcedimOggettoStampaByIdOggetoIcona(getIdProcedimentoOggetto(session), idOggettoIcona);
	    if (stampa != null)
	    {
	      if (stampa.getExtIdDocumentoIndex() != null)
	      {
	        // Ho trovato il documento, non mi interessa lo stato, DEVE ESSERE uno di quelli OK
	        model.addAttribute("pdf", "../cunembo126l/visualizza_" + idOggettoIcona + ".do");
	        return "stampeoggetto/documentoDisponibile";
	      }
	      else
	      {
	        long idStatoStampa = stampa.getIdStatoStampa();
	        if (idStatoStampa == NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA)
	        {
	          model.addAttribute("messaggio", "La generazione della stampa &egrave; fallita");
	          return "stampeoggetto/errore";
	        }
	      }
	    }
	    else
	    {
	      model.addAttribute("messaggio", "La stampa richiesta non &egrave; stata trovata. E' possibile che sia stata cancellata da qualche altro utente");
	      return "stampeoggetto/errore";
	    }
	    return "stampeoggetto/attenderePrego";
	  }

	  @RequestMapping("/lista")
	  public String lista(HttpSession session, Model model) throws InternalUnexpectedException,
	      ApplicationException
	  {
	    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(session);
	    final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
	    List<StampaOggettoDTO> stampe = quadroEJB.getElencoStampeOggetto(idProcedimentoOggetto, null);
	    if (stampe != null && !stampe.isEmpty())
	    {
	      List<Long> ids = new ArrayList<Long>();
	      List<StampaOggettoDTO> stampeNew = new ArrayList<>();
	      for (StampaOggettoDTO stampa : stampe)
	      {
	    	 /*
	    	  if(procedimentoOggetto.getIdStatoOggetto().longValue() != NemboConstants.STATO.OGGETTO.ID.APPROVATO 
	    			 || (procedimentoOggetto.getIdStatoOggetto().longValue() == NemboConstants.STATO.OGGETTO.ID.APPROVATO  && stampa.getCodiceCdu().equals(NemboConstants.USECASE.STAMPE_OGGETTO.INSERISCI_STAMPA_OGGETTO)))
	    	 {
	    		stampeNew.add(stampa); 
		        Long extIdUtenteAggiornamento = stampa.getExtIdUtenteAggiornamento();
		        if (extIdUtenteAggiornamento != null)
		        {
		          ids.add(extIdUtenteAggiornamento);
		        }
	    	 }
	    	 */
	  		stampeNew.add(stampa); 
	        Long extIdUtenteAggiornamento = stampa.getExtIdUtenteAggiornamento();
	        if (extIdUtenteAggiornamento != null)
	        {
	          ids.add(extIdUtenteAggiornamento);
	        }

	      }
	      stampe = stampeNew;
	      Map<Long, UtenteLogin> utenti = getMapUtenti(ids);
	      for (StampaOggettoDTO stampa : stampe)
	      {
	        Long idUtenteAggiornamento = stampa.getExtIdUtenteAggiornamento();
	        if (idUtenteAggiornamento != null)
	        {
	          stampa.setDescUltimoAggiornamento(getDescUltimoAggiornamento(utenti.get(idUtenteAggiornamento), stampa.getDataUltimoAggiornamento()));
	        }
	      }
	    }
	    boolean oggettoChiuso = procedimentoOggetto.getDataFine() != null;
	    boolean stampaCancellabile = procedimentoOggetto.getDataFine() != null && (procedimentoOggetto.getIdStatoOggetto() != NemboConstants.STATO.OGGETTO.ID.ISTRUTTORIA_APPROVATA)
	    		&& (procedimentoOggetto.getIdStatoOggetto() != NemboConstants.STATO.OGGETTO.ID.TRASMESSO);
	    model.addAttribute("oggettoChiuso", Boolean.valueOf(oggettoChiuso));
	    model.addAttribute("stampaCancellabile", Boolean.valueOf(stampaCancellabile));
	    boolean stampeUtenteVisibili = oggettoChiuso;
	    model.addAttribute("stampeUtenteVisibili", Boolean.valueOf(stampeUtenteVisibili));
	    if (stampeUtenteVisibili)
	    {
	      List<StampaOggettoIconaDTO> list = quadroEJB.getElencoDocumentiStampeDaAllegare(idProcedimentoOggetto, null);
	      stampeUtenteVisibili = list != null && !list.isEmpty();
	    }
	    model.addAttribute("stampeUtenteVisibili", Boolean.valueOf(stampeUtenteVisibili));
	    model.addAttribute("stampe", stampe);
	    return "stampeoggetto/lista";
	  }

	  @RequestMapping(value = "/visualizza_{idOggettoIcona}", method = RequestMethod.POST, produces = "text/html")
	  @ResponseBody
	  public String visualizzaPost(HttpSession session, Model model, @PathVariable("idOggettoIcona") long idOggettoIcona)
	      throws InternalUnexpectedException, ApplicationException
	  {
	    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
	    long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
	    if (po.getDataFine() == null)
	    {
	      List<StampaOggettoDTO> stampe = quadroEJB.getElencoStampeOggetto(idProcedimentoOggetto, idOggettoIcona);
	      if (stampe != null && !stampe.isEmpty())
	      {
	        StampaOggettoDTO stampa = stampe.get(0);
	        if (!NemboConstants.FLAGS.SI.equals(stampa.getFlagStampaOggettoAperto()))
	        {
	          return "<error>Impossibile stampare questo documento fino a quando non si esegue la chiusura dell'oggetto corrente</error>";
	        }
	        else
	        {
	          return getTagUrlStampa(stampa.getCodiceCdu());
	        }
	      }
	      else
	      {
	        return "<error>Stampa non trovata. E' possibile che sia stata cancellata da un altro utente</error>";
	      }
	    }
	    else
	    {
	      ProcedimOggettoStampaDTO procedimOggettoStampaDTO = quadroEJB.getProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona);
	      if (procedimOggettoStampaDTO != null)
	      {
	        if (procedimOggettoStampaDTO.getFlagStampaPresente().equals("S")) // Esiste un record su db
	        {
	          return "<stampa>../cunembo126l/visualizza_" + procedimOggettoStampaDTO.getIdOggettoIcona() + ".do</stampa>";
	        }
	        else
	        {
	          if (procedimOggettoStampaDTO.getIdStatoStampa() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
	          {
	            long diffInSeconds = NemboUtils.DATE.diffInSeconds(new Date(), procedimOggettoStampaDTO.getDataInizio());
				if (diffInSeconds < NemboConstants.TEMPO.SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA) // 10
	            {
	              return "<timer>../cunembo126l/conferma_rigenera_" + procedimOggettoStampaDTO.getIdOggettoIcona() + ".do</timer>";
	            }
	            else
	            {
	            	return "<renew>../cunembo126l/conferma_rigenera_" + procedimOggettoStampaDTO.getIdOggettoIcona() + ".do</renew>";
	            }
	          }
	          else
	          {
	            return "<renew>../cunembo126l/conferma_rigenera_" + procedimOggettoStampaDTO.getIdOggettoIcona() + ".do</renew>";
	          }
	        }
	      }
	      else
	      {
	        return "<error>Errore di configurazione, per questa stampa non e' mai stata richiesta la generazione e non puo' essere prodotta a oggetto chiuso</error>";
	      }
	    }

	  }

	  @RequestMapping(value = "/visualizza_{idOggettoIcona}", method = RequestMethod.GET)
	  public ResponseEntity<byte[]> visualizzaGet(HttpSession session, Model model, @PathVariable("idOggettoIcona") long idOggettoIcona) throws Exception
	  {
	    long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		byte[] stampa = quadroEJB.getStampaProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona);
	    ProcedimOggettoStampaDTO procedimOggettoStampaDTO = quadroEJB.getProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona);

	    String nomeFile = "pratica.pdf"; // default
	    if(procedimOggettoStampaDTO.getNomeFile()!=null && procedimOggettoStampaDTO.getNomeFile().trim().length()>0){
	    	nomeFile = procedimOggettoStampaDTO.getNomeFile();
	    }
	    
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add("Content-type", NemboUtils.FILE.getMimeType(nomeFile));
	    httpHeaders.add("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(stampa, httpHeaders, HttpStatus.OK);
	    return response;
	  }

	  protected String getTagUrlStampa(String codiceCdu)
	  {
	    codiceCdu = codiceCdu.replace("-", "").toLowerCase();
	    return "<stampa>../" + codiceCdu + "/stampa.do</stampa>";
	  }
}
