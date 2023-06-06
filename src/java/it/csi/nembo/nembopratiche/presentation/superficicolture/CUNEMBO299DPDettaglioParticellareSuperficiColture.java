package it.csi.nembo.nembopratiche.presentation.superficicolture;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioParticellareDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-299-DP", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo299dp")
public class CUNEMBO299DPDettaglioParticellareSuperficiColture extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index_{idSuperficieColtura}", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model, @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  SuperficiColtureDettaglioDTO superficiColturaDettaglio = quadroNemboEJB.getSuperficiColtureDettaglio(idProcedimentoOggetto,idSuperficieColtura);
		  model.addAttribute("superficiColturaDettaglio",superficiColturaDettaglio);
		  model.addAttribute("idSuperficieColtura", idSuperficieColtura);
		  return "superficicolture/dettaglioParticellareSuperficiColture";
	  }
	  
	  
	  @RequestMapping(value = "/get_list_dettaglio_particellare_superfici_colture_{idSuperficieColtura}.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<SuperficiColtureDettaglioParticellareDTO> getListDettaglioParticellareSuperficiColture(HttpSession session, Model model,
			  @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getListDettaglioParticellareSuperficiColture(idProcedimentoOggetto, idSuperficieColtura);
	  }
}
