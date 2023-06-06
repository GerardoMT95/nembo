package it.csi.nembo.nembopratiche.presentation.quadro.fabbricati;

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
import it.csi.nembo.nembopratiche.dto.fabbricati.FabbricatiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1303-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo1303l")
public class CUNEMBO1303LVisualizzaFabbricatiController extends BaseController
{
	@Autowired
	private IQuadroNemboEJB quadroNemboEJB = null;

	private String cuNumber = "1303";

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	{
		model.addAttribute("cuNumber", cuNumber);
		return "fabbricati/visualizzaFabbricati";
	}

	@RequestMapping(value = "/visualizza_fabbricati", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<FabbricatiDTO> visualizzaFabbricati(HttpSession session, Model model, HttpServletRequest request)
			throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		return quadroNemboEJB.getListFabbricati(idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
	}

	@RequestMapping(value = "/get_elenco_fabbricati_non_danneggiati", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<FabbricatiDTO> getElencoFabbricatiNonDanneggiati(HttpSession session, Model model,
			HttpServletRequest request) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		return quadroNemboEJB.getListFabbricatiNonDanneggiati(idProcedimentoOggetto,null, getUtenteAbilitazioni(session).getIdProcedimento());
	}

}
