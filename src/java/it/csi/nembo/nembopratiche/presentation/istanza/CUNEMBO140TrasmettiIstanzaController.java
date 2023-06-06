package it.csi.nembo.nembopratiche.presentation.istanza;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException.ExceptionType;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo140")
@NemboSecurity(value = "CU-NEMBO-140", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO_CHIUSO)
public class CUNEMBO140TrasmettiIstanzaController extends BaseController
{

	@Autowired
	private IQuadroEJB quadroEJB = null;
	
	@RequestMapping(value = "popupindex" ,method = RequestMethod.GET)
	@IsPopup
	public String popupIndex(Model model, HttpSession session) throws InternalUnexpectedException
	{
	  String msgError = null;
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(session);
	  final long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
    final UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
	  if (utenteAbilitazioni.getRuolo().isUtenteTitolareCf() || utenteAbilitazioni.getRuolo().isUtenteLegaleRappresentante())
    {
      // Se l'utente è un beneficiario in proprio (quindi con ruolo titolare CF o legale rappresentante)
      // deve avere potere di firma per poter trasmettere la domanda in quanto il sistema segna la domanda come firmata con firma semplice dall'utente connesso
      final boolean beneficiarioConPotereDiFirma = quadroEJB.isBeneficiarioAbilitatoATrasmettere(utenteAbilitazioni.getCodiceFiscale(), idProcedimentoOggetto);
      if (!beneficiarioConPotereDiFirma)
      {
          model.addAttribute("errore", "L'utente corrente non è autorizzato a firmare per conto dell'azienda, impossibile proseguire con l'operazione di trasmissione");
          return "dialog/soloErrore"; 
      }
    }
		List<StampaOggettoDTO> stampe = quadroEJB.getElencoStampeOggetto(idProcedimentoOggetto, null);
		msgError = checkProcOggetto(procedimentoOggetto, stampe);
		Vector<DecodificaDTO<String>> lStampeInAttesaFirma = null;
		Vector<DecodificaDTO<String>> lStampeInAttesaFirmaElettr = null;
		
		if(msgError == null)
		{
			if(stampe != null)
			{
				for(StampaOggettoDTO item:stampe)
				{
					if (item.getIdStatoStampa().longValue() == NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_GRAFOMETRICA)
					{
						if(lStampeInAttesaFirma == null){
							lStampeInAttesaFirma = new Vector<DecodificaDTO<String>>();
						}
						lStampeInAttesaFirma.add(new DecodificaDTO<String>(NemboConstants.STATO.STAMPA.DESCRIZIONE.IN_ATTESA_FIRMA_GRAFOMETRICA, item.getDescTipoDocumento()));
					}
					else if (item.getIdStatoStampa().longValue() == NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_ELETTRONICA_LEGGERA)
					{
						if(lStampeInAttesaFirmaElettr == null){
							lStampeInAttesaFirmaElettr = new Vector<DecodificaDTO<String>>();
						}
						lStampeInAttesaFirmaElettr.add(new DecodificaDTO<String>(NemboConstants.STATO.STAMPA.DESCRIZIONE.IN_ATTESA_FIRMA_ELETTRONICA_LEGGERA, item.getDescTipoDocumento()));
					}
					else if (item.getIdStatoStampa().longValue() == NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_SU_CARTA)
					{
						if(lStampeInAttesaFirma == null){
							lStampeInAttesaFirma = new Vector<DecodificaDTO<String>>();
						}
						lStampeInAttesaFirma.add(new DecodificaDTO<String>(NemboConstants.STATO.STAMPA.DESCRIZIONE.IN_ATTESA_FIRMA_SU_CARTA, item.getDescTipoDocumento()));
					}
				}
			}
			model.addAttribute("lStampeInAttesaFirma", lStampeInAttesaFirma);
			model.addAttribute("lStampeInAttesaFirmaElettr",lStampeInAttesaFirmaElettr);
			return "trasmissione/popupTrasmissione";
		}
		else
		{
			model.addAttribute("errore", "Impossibile procedere con la trasmissione: " + msgError);
			return "dialog/soloErrore";	
		}
	}
	
	@RequestMapping(value = "popupindex" ,method = RequestMethod.POST)
	@ResponseBody
	public String popupIndexPost(Model model, HttpSession session, HttpServletRequest request) throws InternalUnexpectedException
	{
		ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(session);
		List<StampaOggettoDTO> stampe = quadroEJB.getElencoStampeOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), null);
		String msgError = checkProcOggetto(procedimentoOggetto, stampe);
		if(msgError != null)
		{
			return "Impossibile procedere con la trasmissione: " + msgError;
		}
		
		
    	QuadroOggettoDTO quadroDatiIdentif = procedimentoOggetto.findQuadroByCU(NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO);
      msgError = quadroEJB.trasmettiIstanza(getIdProcedimento(session), procedimentoOggetto.getIdProcedimentoOggetto(),
          procedimentoOggetto.getIdBandoOggetto(),quadroDatiIdentif.getIdQuadroOggetto(), procedimentoOggetto.getIdentificativo(),
          getUtenteAbilitazioni(session));

		if(msgError == null || msgError.trim().length()<=0)
		{
		    return "redirect:../cunembo140/riepilogo.do";

		}
		else
		{
			return "Impossibile procedere con la trasmissione: " + HtmlUtils.htmlEscape(msgError);
		}
	}
	

		
	
	  @NemboSecurity(value = "CU-NEMBO-140", controllo = NemboSecurity.Controllo.NESSUNO)
	  @RequestMapping(value = "/riepilogo" ,method = RequestMethod.GET)
	  public String riepilogo(HttpSession session, Model model) throws InternalUnexpectedException
	  {
	    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
	    model.addAttribute("po", po);
	    
	    refreshTestataProcedimento(quadroEJB, session, po.getIdProcedimento());	    
	    return "trasmissione/riepilogo";
	  }
	
	private String checkProcOggetto(ProcedimentoOggetto procedimentoOggetto, List<StampaOggettoDTO> stampe) throws InternalUnexpectedException
	{
		if(procedimentoOggetto.getDataFine()==null || (procedimentoOggetto.getIdEsito().longValue() != NemboConstants.PROCEDIMENTO_OGGETTO.ESITO.POSITIVO))
		{
			return "l'oggetto selezionato non risulta chiuso con esito positivo.";
		}
		else if(procedimentoOggetto.getIdStatoOggetto().longValue() >= (new Long("10")).longValue())
		{
			return "l'oggetto selezionato non risulta essere nello stato corretto.";
		}
		
		try {
			quadroEJB.canUpdateProcedimentoOggetto(procedimentoOggetto.getIdProcedimentoOggetto(), true);
		} catch (NemboPermissionException e) {
			if(e.getType().equals(ExceptionType.PROCEDIMENTO_CHIUSO)){
				return "sono scaduti i termini di presentazione.";
			}
		} catch (InternalUnexpectedException e) { }
		
		if(stampe != null)
		{
			for(StampaOggettoDTO item:stampe)
			{
				if(item.getDataFineStampa()==null && 
						((item.getIdStatoStampa().longValue() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
							|| (item.getIdStatoStampa().longValue() == NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA)	))
				{
					return "risultano esserci stampe collegate in corso o fallite.";
				}
			}
		}
		return null;
	}

}
