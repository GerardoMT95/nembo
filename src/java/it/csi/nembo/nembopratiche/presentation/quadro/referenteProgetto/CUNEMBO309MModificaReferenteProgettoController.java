package it.csi.nembo.nembopratiche.presentation.quadro.referenteProgetto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.ReferenteProgettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo309m")
@NemboSecurity(value = "CU-NEMBO-309-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO309MModificaReferenteProgettoController extends BaseController{

	public static final String CU_NAME = "CU-NEMBO-309-M";
	
	  @Autowired
	  IQuadroNemboEJB                 quadroNemboEJB;
	  @Autowired
	  IQuadroNemboEJB                 quadroEJB;
	  @Autowired
	  IInterventiEJB interventiEJB;

	  @RequestMapping("/index")
	  public String index(Model model, HttpSession session)
	      throws InternalUnexpectedException
	  { 
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		ReferenteProgettoDTO referenteProgetto = quadroNemboEJB.getReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto);
		model.addAttribute("referente",referenteProgetto);
		model.addAttribute("prefReqValues",Boolean.FALSE);
		model.addAttribute("extIstatComune",referenteProgetto.getExtIstatComune());
		return "referenteProgetto/modificaReferenteProgetto";
	  }
	  
	  @IsPopup
	  @RequestMapping(value = "/popup_ricerca_comune", method = RequestMethod.GET)
	  public String popupRicercaComuniAltroistituto(Model model, HttpServletRequest request) throws InternalUnexpectedException
	  {
	    model.addAttribute("province", quadroEJB.getProvincie(null));
	    return "referenteProgetto/popupRicercaComune";
	  }
	  
	  @RequestMapping("/confermaModifica")
	  public String confermaModifica(Model model, HttpSession session, HttpServletRequest request)
	      throws InternalUnexpectedException
	  { 
		//controllo campi obbligatori
		Errors errors = new Errors();
		
		String cognome = NemboUtils.STRING.trim(request.getParameter("cognome"));
		String nome = NemboUtils.STRING.trim(request.getParameter("nome"));
		String codiceFiscale = NemboUtils.STRING.trim(request.getParameter("codiceFiscale"));
		String extIstatComune = NemboUtils.STRING.trim(request.getParameter("extIstatComune"));
		String cap = NemboUtils.STRING.trim(request.getParameter("cap"));
		String telefono = NemboUtils.STRING.trim(request.getParameter("telefono"));
		String cellulare = NemboUtils.STRING.trim(request.getParameter("cellulare"));
		String email = NemboUtils.STRING.trim(request.getParameter("email"));


	   errors.validateMandatory(cognome, "cognome");
	   errors.validateMandatory(nome, "nome");
	   errors.controlloCf(codiceFiscale.toUpperCase(), "codiceFiscale");
	   errors.validateMandatory(extIstatComune, "comune");
	   errors.validateMandatoryFieldLength(cap, 5, 5, "cap");
	   
	    		
	    if (!errors.isEmpty())
	    {
	      model.addAttribute("extIstatComune",extIstatComune);
		  model.addAttribute("errors", errors);
	      model.addAttribute("prefReqValues", Boolean.TRUE);
	      return "referenteProgetto/modificaReferenteProgetto";
	    }else{
	    	//SALVO SU DB E RICARICO
	    	long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	    	quadroNemboEJB.insertOrUpdateReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto,
	    			nome, cognome, codiceFiscale, extIstatComune, cap, telefono, cellulare, email, getLogOperationOggettoQuadroDTO(session));
	    }
	    return "redirect:../cunembo309l/index.do";
	  }
	  
	  
}
