package it.csi.nembo.nembopratiche.presentation.scorte;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-297-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo297l")
public class CUNEMBO297LScorteController extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;

	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
	      long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      long idStatoProcedimento = quadroNemboEJB.getIdStatoProcedimento(idProcedimentoOggetto);
	      model.addAttribute("idProcedimentoOggetto",idProcedimentoOggetto);
	      model.addAttribute("idStatoProcedimento", idStatoProcedimento);
	      clearCommonInSession(session);
		  return "scorte/visualizzaScorte";
	  }
	  
	  @RequestMapping(value = "/get_elenco_scorte", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<ScorteDTO> getListaScorteByProcedimentoOggetto(
			  HttpSession session, 
			  Model model)  throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  return quadroNemboEJB.getListaScorteByProcedimentoOggetto(idProcedimentoOggetto);
	  }
	  
	  @RequestMapping(value = "/get_elenco_scorte_non_danneggiate", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<ScorteDTO> getListaScorteNonDanneggiateByProcedimentoOggetto(
			  HttpSession session, 
			  Model model
			  )  throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getListaScorteNonDanneggiateByProcedimentoOggetto(idProcedimentoOggetto);
	  }
	  
	  @RequestMapping(value = "/visualizza_scorte_popup", method = RequestMethod.GET)
	  public String visualizzaScortePopup(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  this.index(session, model);
		  return "scorte/visualizzaScortePopup";
	  }
	  
	 
	  @RequestMapping(value = "/get_n_danni_scorte", method = RequestMethod.POST, produces = "text/plain")
	  @ResponseBody
	  public String getNDanniScorte(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	  {
		  String[] arrayIdScortaMagazzinoString = request.getParameterValues("idScortaMagazzino");
		  long[] arrayIdScortaMagazzino = NemboUtils.ARRAY.toLong(arrayIdScortaMagazzinoString);
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getNDanniScorte(idProcedimentoOggetto, arrayIdScortaMagazzino).toString();
	  }
	  
}
