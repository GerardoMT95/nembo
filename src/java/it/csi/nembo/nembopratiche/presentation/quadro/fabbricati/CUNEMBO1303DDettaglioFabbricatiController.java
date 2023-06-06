package it.csi.nembo.nembopratiche.presentation.quadro.fabbricati;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.fabbricati.FabbricatiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1303-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo1303d")
public class CUNEMBO1303DDettaglioFabbricatiController extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  private String cuNumber = "1303";
	  
	  @RequestMapping(value = "/index_{idFabbricato}", method = RequestMethod.GET)
	  public String index(
			  		HttpSession session, 
			  		Model model, 
			  		@PathVariable(value = "idFabbricato") long idFabbricato) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  FabbricatiDTO fabbricato = quadroNemboEJB.getFabbricato(idProcedimentoOggetto,idFabbricato, getUtenteAbilitazioni(session).getIdProcedimento());
		  model.addAttribute("fabbricato", fabbricato);
		  model.addAttribute("cuNumber", cuNumber);
		  return "fabbricati/dettagliFabbricato";
	  }
	  

}
