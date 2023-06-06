package it.csi.nembo.nembopratiche.presentation.quadro.assicurazionicolture;




import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-306-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo306i")
public class CUNEMBO306IInserisciAssicurazioniColture extends CUNEMBO306BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB;
	
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  List<DecodificaDTO<String>> province = quadroNemboEJB.getProvincie(NemboConstants.GENERIC.ID_REGIONE_BASILICATA);
		  model.addAttribute("province",province);
		  model.addAttribute("idAssicurazioniColture",null);
		  model.addAttribute("cdu","CU-NEMBO-306-I");
		  return "assicurazionicolture/inserisciAssicurazioniColture";
	  }
	  
	  @RequestMapping(value="/index", method = RequestMethod.POST)
	  public String inserisci(
			  HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  return super.inserisci(session, request, model);
	  }
	
	  public void inserisci(
			  	HttpServletRequest request,
			  	HttpSession session,
			  	Model model,
			  	long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture, LogOperationOggettoQuadroDTO logOperationOggettoQuadro)
			    throws InternalUnexpectedException
	    {
		   quadroNemboEJB.inserisciAssicurazioniColture(idProcedimentoOggetto,assicurazioniColture,logOperationOggettoQuadro);
	    }
}
