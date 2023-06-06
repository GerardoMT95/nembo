package it.csi.nembo.nembopratiche.presentation.quadro.dannicolture;




import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.danni.RigaSegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-310-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo310l")
public class CUNEMBO310SegnalazioneDanniColture extends BaseController
{
	  @Autowired
	  IQuadroEJB quadroEJB;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  model.addAttribute("dannoDTO", quadroEJB.getSegnalazioneDanno(getIdProcedimentoOggetto(session),null));  
		  return "dannicolture/visualizzaSegnalazioneDanniColture";
	  }
	  
	  
	  @RequestMapping(value = "/get_list_danni_colture.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<RigaSegnalazioneDannoDTO> getListDanniColture(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  List<RigaSegnalazioneDannoDTO> danniColture = quadroEJB.getSegnalazioneDanno(idProcedimentoOggetto,null).getElencoDanni();
		  return danniColture;
	  }
	 
	  
	  @RequestMapping(value = "/indexDatiGenerali", method = RequestMethod.GET)
	  public String modificaSingola(Model model, HttpServletRequest request, HttpSession session)
	      throws InternalUnexpectedException
	  {
		  model.addAttribute("dannoDTO", quadroEJB.getSegnalazioneDanno(getIdProcedimentoOggetto(session),null));  
		   return "dannicolture/modificaDatiGenerali";
	  }
	  
	  @RequestMapping(value = "/indexDatiGenerali", method = RequestMethod.POST)
	  public String modificaSingolaPost(Model model, HttpServletRequest request, HttpSession session)
	      throws InternalUnexpectedException
	  {
		  Errors errors = new Errors();
		  final String parameterNameDescrizione = "descrizioneDanno";
		  final String parameterNamePercData = "dataDanno";
		  final String descrizione = request.getParameter(parameterNameDescrizione);
		  final String data = request.getParameter(parameterNamePercData);
		  
		  
		  errors.validateMandatoryFieldMaxLength(descrizione, parameterNameDescrizione, 4000);
		  errors.validateMandatoryFieldMaxLength(data, parameterNamePercData, 4000);
			  
		  if (errors.addToModelIfNotEmpty(model)){
			model.addAttribute("preferRequestValues", Boolean.TRUE);
	    	model.addAttribute("dannoDTO", quadroEJB.getSegnalazioneDanno(getIdProcedimentoOggetto(request.getSession()),null));
	    	return "dannicolture/modificaDatiGenerali";
		  }else
		  {
			  quadroEJB.updateDatiGeneraliDanno(getIdProcedimentoOggetto(request.getSession()),descrizione,data);
		  }
		  
		  return "redirect:../cunembo310l/index.do";
	  }
}
