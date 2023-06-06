package it.csi.nembo.nembopratiche.presentation.danni;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-298-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo298l")
public class CUNEMBO298LDanniVisualizzaController extends CUNEMBO298DanniBaseController
{
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpServletRequest request, HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  session.removeAttribute(FiltroRicercaConduzioni.class.getName());
		  
		  List<String> tableNamesToRemove = new ArrayList<>();
		  tableNamesToRemove.add("tblRicercaConduzioni");
		  tableNamesToRemove.add("tblRicercaConduzioniRiepilogo");
		  tableNamesToRemove.add("tblConduzioni");
		  cleanTableMapsInSession(session, tableNamesToRemove);
		  
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      model.addAttribute("idProcedimentoOggetto",idProcedimentoOggetto);
	      if(request.getParameter("errorModificaMultipla") != null)
	      {
	    	  model.addAttribute("errorModificaMultipla", Boolean.TRUE);
	      }
		  return "danni/visualizzaDanni";
	  }
	  
	  @RequestMapping(value = "/get_elenco_danni", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<DanniDTO> getListaDanniByProcedimentoOggetto(
			  HttpSession session, 
			  Model model)  throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getListaDanniByProcedimentoOggetto(idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
	  }
}
