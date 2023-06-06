package it.csi.nembo.nembopratiche.presentation.quadro.assicurazionicolture;




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
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-306-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo306l")
public class CUNEMBO306LVisualizzaAssicurazioniColture extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB;
	
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  model.addAttribute("ultimaModifica", ultimaModifica);
		  return "assicurazionicolture/visualizzaAssicurazioniColture";
	  }
	  
	  @RequestMapping(value="/get_list_assicurazioni_colture", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<AssicurazioniColtureDTO> getListAssicurazioniColture(
			  HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  return quadroNemboEJB.getListAssicurazioniColture(idProcedimentoOggetto);
	  }
	  
	
}
