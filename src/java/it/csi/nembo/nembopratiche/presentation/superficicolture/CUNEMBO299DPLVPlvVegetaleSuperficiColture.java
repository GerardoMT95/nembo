package it.csi.nembo.nembopratiche.presentation.superficicolture;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColturePlvVegetaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-299-DPLV", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo299dplv")
public class CUNEMBO299DPLVPlvVegetaleSuperficiColture extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  return "superficicolture/visualizzaSuperficiColturePlvVegetale";
	  }
	  
	  
	  @RequestMapping(value = "/get_list_superfici_colture_plv_vegetale.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<SuperficiColturePlvVegetaleDTO> getListSuperficiColturePlvVegetale(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getListSuperficiColturePlvVegetale(idProcedimentoOggetto);
	  }
}
