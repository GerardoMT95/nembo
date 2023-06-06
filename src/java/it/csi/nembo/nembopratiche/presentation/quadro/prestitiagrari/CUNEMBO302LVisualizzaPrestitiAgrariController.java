package it.csi.nembo.nembopratiche.presentation.quadro.prestitiagrari;

import java.util.ArrayList;
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
import it.csi.nembo.nembopratiche.dto.prestitiagrari.PrestitiAgrariDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-302-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo302l")
public class CUNEMBO302LVisualizzaPrestitiAgrariController extends CUNEMBO302BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	  {
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  model.addAttribute("ultimaModifica",ultimaModifica);
		  model.addAttribute("cuNumber", cuNumber);
		  return "prestitiagrari/visualizzaPrestitiAgrari";
	  }
	  
	  @RequestMapping(value = "/visualizza_prestiti_agrari", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<PrestitiAgrariDTO> visualizzaPrestitiAgrari(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<PrestitiAgrariDTO> listPrestitiAgrari = quadroNemboEJB.getListPrestitiAgrari(idProcedimentoOggetto);
		  if(listPrestitiAgrari == null)
		  {
			  listPrestitiAgrari = new ArrayList<PrestitiAgrariDTO>();
		  }
		  return listPrestitiAgrari;
	  }
	
}
