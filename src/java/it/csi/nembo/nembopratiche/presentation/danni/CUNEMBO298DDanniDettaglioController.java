package it.csi.nembo.nembopratiche.presentation.danni;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.ParticelleDanniDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.QuadroNemboDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-298-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo298d")
public class CUNEMBO298DDanniDettaglioController extends CUNEMBO298DanniBaseController
{
	  @RequestMapping(value = "/index_{idDannoAtm}", method = RequestMethod.GET)
	  public String index(
			  HttpServletRequest request, 
			  HttpSession session,
			  Model model,
			  @PathVariable("idDannoAtm") long idDannoAtm) throws InternalUnexpectedException
	  {
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		long[] arrayIdDannoAtm = new long[]{idDannoAtm};
		List<DanniDTO> danni = quadroNemboEJB.getDanniByIdDannoAtm(arrayIdDannoAtm, idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
		DanniDTO danno = danni.get(0);
		List<Long> listIdDannoAtm = new ArrayList<Long>();
		List<Integer> listIdDannoConduzioni = QuadroNemboDAO
				.getListDanniEquivalenti(NemboConstants.DANNI.TERRENI_RIPRISTINABILI);
		listIdDannoAtm.add(danno.getIdDannoAtm());
		if (listIdDannoConduzioni.contains(danno.getIdDanno()))
		{
			model.addAttribute("isConduzioni", Boolean.TRUE);
		} 
		model.addAttribute("danno", danno);
		return "danni/dettaglioDanni";
	  }
	  
	  @RequestMapping(value = "/get_list_conduzioni_danno_{idDannoAtm}", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<ParticelleDanniDTO> getListConduzioniDanno(
			  HttpSession session, 
			  Model model,
			  @PathVariable("idDannoAtm") long idDannoAtm)  throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  List<ParticelleDanniDTO> listConduzioniDanno = quadroNemboEJB.getListConduzioniDanno(idProcedimentoOggetto, idDannoAtm);
		  return listConduzioniDanno;
	  }
}
