package it.csi.nembo.nembopratiche.presentation.superficicolture;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioPsrDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-299-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo299d")
public class CUNEMBO299DDettaglioSuperficiColture extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index_{idSuperficieColtura}", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model, @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  SuperficiColtureDettaglioDTO superficiColturaDettaglio = quadroNemboEJB.getSuperficiColtureDettaglio(idProcedimentoOggetto,idSuperficieColtura);
		  SuperficiColtureDettaglioPsrDTO superficiColtureDettaglioPsrDTO = quadroNemboEJB.getSuperficiColtureDettaglioPsrDTO(idProcedimentoOggetto, idSuperficieColtura);
		  
		  model.addAttribute("superficiColturaDettaglio",superficiColturaDettaglio);
		  model.addAttribute("superficiColtureDettaglioPsrDTO", superficiColtureDettaglioPsrDTO);
		  model.addAttribute("ultimaModifica",ultimaModifica);
		  return "superficicolture/dettaglioSuperficiColture";
	  }
	  
}
