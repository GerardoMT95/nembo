package it.csi.nembo.nembopratiche.presentation.quadro.coltureaziendali;




import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioParticellareDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-305-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo305d")
public class CUNEMBO305DDettaglioColtureAziendali extends CUNEMBO305BaseController
{

	  @RequestMapping(value = "/index_{idSuperficieColtura}", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model,
			  HttpServletRequest request,
			  @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  
		  long[] arrayIdSuperficieColtura = new long[] {idSuperficieColtura};
		  ColtureAziendaliDettaglioDTO dettaglio = quadroNemboEJB.getListColtureAziendali(idProcedimentoOggetto,arrayIdSuperficieColtura).get(0);
		  
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  List<ControlloColturaDTO> listControlloColtura = quadroNemboEJB.getListControlloColtura(idProcedimentoOggetto, new long[] {idSuperficieColtura});
		  
		  model.addAttribute("dettaglio", dettaglio);
		  model.addAttribute("ultimaModifica", ultimaModifica);
		  model.addAttribute("idSuperficieColtura", idSuperficieColtura);
		  model.addAttribute("listControlloColtura", listControlloColtura);
		  return "coltureaziendali/dettaglioColtureAziendali";
	  }
	  
	  @RequestMapping(value = "/get_list_colture_aziendali_{idSuperficieColtura}.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(HttpSession session, Model model,
			  @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<ColtureAziendaliDettaglioDTO> listSuperficiColtureDettaglio = quadroNemboEJB.getListColtureAziendali(idProcedimentoOggetto);
		  return listSuperficiColtureDettaglio;
	  }
	  
	  @RequestMapping(value = "/get_list_dettaglio_particellare_superfici_colture_{idSuperficieColtura}.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<SuperficiColtureDettaglioParticellareDTO> getListDettaglioParticellareSuperficiColture(HttpSession session, Model model,
			  @PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  return quadroNemboEJB.getListDettaglioParticellareSuperficiColture(idProcedimentoOggetto, idSuperficieColtura);
	  }
	  
	@Override
	protected long[] getArrayIdSuperficieColtura(HttpServletRequest request)
	{
		long idSuperficieColtura = Long.parseLong(request.getParameter("idSuperficieColtura"));
		return new long[]{idSuperficieColtura};
	}
	  
}
