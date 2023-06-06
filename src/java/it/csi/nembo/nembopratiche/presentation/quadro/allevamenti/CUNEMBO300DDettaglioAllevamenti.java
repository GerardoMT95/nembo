package it.csi.nembo.nembopratiche.presentation.quadro.allevamenti;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDettaglioPlvDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;

@Controller
@NemboSecurity(value = "CU-NEMBO-300-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo300d")
public class CUNEMBO300DDettaglioAllevamenti extends BaseController
{
	@Autowired
	private IQuadroNemboEJB quadroNemboEJB = null;

	@RequestMapping(value = "/index_{idCategoriaAnimale}_{istatComune}", method = RequestMethod.GET)
	public String index(HttpSession session, Model model, 
			@PathVariable("idCategoriaAnimale") long idCategoriaAnimale,
			@PathVariable("istatComune") String istatComune) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		AllevamentiDTO allevamento = quadroNemboEJB.getDettaglioAllevamento(idProcedimentoOggetto, idCategoriaAnimale,istatComune);
		String ultimaModificaAllevamento="";
		if(allevamento.getDataUltimoAggiornamento() != null)
		{
			ultimaModificaAllevamento = allevamento.getDataUltimoAggiornamento()+ " " + getUtenteDescrizione(allevamento.getExtIdUtenteAggiornamento());
		}
		model.addAttribute("allevamento", allevamento);
		model.addAttribute("ultimaModifica", ultimaModificaAllevamento);
		model.addAttribute("idCategoriaAnimale", idCategoriaAnimale);
		model.addAttribute("istatComune", istatComune);
		return "allevamenti/dettaglioAllevamenti";
	}

	@RequestMapping(value = "/get_list_dettaglio_allevamenti_{idCategoriaAnimale}_{istatComune}.json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<AllevamentiDettaglioPlvDTO> getListDettaglioAllevamenti(HttpSession session, Model model,
			@PathVariable("idCategoriaAnimale") long idCategoriaAnimale,
			@PathVariable("istatComune") String istatComune)
			throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		return quadroNemboEJB.getListDettaglioAllevamenti(idProcedimentoOggetto, idCategoriaAnimale, istatComune);
	}
	
	private String getUtenteDescrizione(long idUtente) throws InternalUnexpectedException
	{
		try{
			long[] aIdUtente = new long[]{idUtente};
			UtenteLogin[] utenti = PapuaservProfilazioneServiceFactory
					.getRestServiceClient().findUtentiLoginByIdList(aIdUtente);
			return utenti[0].getDenominazione();
		}
		catch(Exception e)
		{
			throw new InternalUnexpectedException(e);
		}
	}
  }
