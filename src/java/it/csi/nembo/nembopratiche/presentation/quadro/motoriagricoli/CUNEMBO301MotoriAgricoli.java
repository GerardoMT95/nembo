package it.csi.nembo.nembopratiche.presentation.quadro.motoriagricoli;

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
import it.csi.nembo.nembopratiche.dto.motoriagricoli.MotoriAgricoliDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-301", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo301")
public class CUNEMBO301MotoriAgricoli extends BaseController
{

	@Autowired
	private IQuadroNemboEJB quadroNemboEJB = null;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	{
		return "motoriagricoli/visualizzaMotoriAgricoli";
	}

	@RequestMapping(value = "/visualizza_motori_agricoli", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<MotoriAgricoliDTO> visualizzaMotoriAgricoli(HttpSession session, Model model,
			HttpServletRequest request) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		return quadroNemboEJB.getListMotoriAgricoli(idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
	}

	@RequestMapping(value = "/get_elenco_motori_agricoli_non_danneggiati", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<MotoriAgricoliDTO> getMotoriAgricoliNonDanneggiati(HttpSession session, Model model,
			HttpServletRequest request) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		return quadroNemboEJB.getListMotoriAgricoliNonDanneggiati(idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
	}
}
