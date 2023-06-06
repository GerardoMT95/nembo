package it.csi.nembo.nembopratiche.presentation.quadro.documentiRichiesti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.SezioneDocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;


@Controller
@RequestMapping("/cunembo308m")
@NemboSecurity(value = "CU-NEMBO-308-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO) 
//TODO: FIXME! Operazione non permessa: il procedimento selezionato è chiuso
public class CUNEMBO308MModificaDocumentiRichiestiController extends BaseController {

	 public static final String CU_NAME = "CU-NEMBO-308-M";
	  @Autowired
	  IQuadroNemboEJB                 quadroNemboEJB;

	  @RequestMapping("/index")
	  public String index(HttpSession session, HttpServletRequest request, Model model)
	      throws InternalUnexpectedException
	  { 
		 
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<SezioneDocumentiRichiestiDTO> sezioniDaVisualizzare = quadroNemboEJB.getListDocumentiRichiestiDaVisualizzare(idProcedimentoOggetto, false);	  
		  if(sezioniDaVisualizzare!=null) model.addAttribute("numeroDoc", sezioniDaVisualizzare.size());
		  else model.addAttribute("numeroDoc", 0);
		  model.addAttribute("sezioniDaVisualizzare", sezioniDaVisualizzare);
		  model.addAttribute("visualizzazione", Boolean.FALSE);
	    return "documentiRichiesti/modificaDocumentiRichiesti";
	  }
	 
	  @RequestMapping(value = "/confermaModifica", method = RequestMethod.POST)
	  public String confermaModifica(HttpSession session, HttpServletRequest request, Model model) throws InternalUnexpectedException
	  {
		  Errors error = new Errors();
		  if(request.getParameter("check_H_1")!=null){
			  error.validateMandatory(request.getParameter("textFieldH"), "textFieldH");
			  
		  }
		  if(error.addToModelIfNotEmpty(model)){
			  model.addAttribute("preferRequest", Boolean.TRUE);
			  return index(session, request, model);
		  }				  
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<String> listaCheckBox = new ArrayList<>();
		  if(request.getParameterValues("check")!=null){
			  listaCheckBox = Arrays.asList(request.getParameterValues("check"));
		  }		  
		  String HValue = "";
		  if(request.getParameter("check_H_1")!=null)
			  HValue = request.getParameter("textFieldH");	
		  
		  quadroNemboEJB.aggiornaDocumentiRichiesti(idProcedimentoOggetto, listaCheckBox, HValue);
		  
		  return  "redirect:../cunembo308l/index.do";
	  }
}
