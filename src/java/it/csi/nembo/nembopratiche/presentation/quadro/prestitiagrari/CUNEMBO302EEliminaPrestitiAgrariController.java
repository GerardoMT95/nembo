package it.csi.nembo.nembopratiche.presentation.quadro.prestitiagrari;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-302-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo302e")
public class CUNEMBO302EEliminaPrestitiAgrariController extends CUNEMBO302BaseController
{
	@Autowired
	private IQuadroNemboEJB quadroNemboEJB = null;
	private final String dlgName = "dlgEliminaScorte";

	@IsPopup
	@RequestMapping(value = "/conferma_elimina_prestito_{idPrestitiAgrari}", method = RequestMethod.GET)
	public String confermaEliminaPrestitoAgrario(
			Model model, 
			HttpServletRequest request,
			@PathVariable("idPrestitiAgrari") long idPrestitiAgrari) throws InternalUnexpectedException
	{	
		long[] arrayIdPrestitiAgrari = new long[1];
		arrayIdPrestitiAgrari[0] = idPrestitiAgrari;
		model.addAttribute("ids",arrayIdPrestitiAgrari);
		model.addAttribute("len",arrayIdPrestitiAgrari.length);
		model.addAttribute("dlgName",dlgName);
		return "prestitiagrari/confermaEliminaPrestiti";
	}
	
	@IsPopup
	@RequestMapping(value = "/conferma_elimina_prestiti", method = RequestMethod.GET)
	public String confermaEliminaPrestitiAgrari(
			Model model,
			HttpSession session,
			HttpServletRequest request
			) throws InternalUnexpectedException
	{
		String[] arrayIdPrestitiAgrari = request.getParameterValues("idPrestitiAgrari");
		if(arrayIdPrestitiAgrari == null)
		{
			arrayIdPrestitiAgrari = new String[0];
		}
		model.addAttribute("ids", arrayIdPrestitiAgrari);
		model.addAttribute("len", arrayIdPrestitiAgrari.length);
		model.addAttribute("dlgName",dlgName);
		return "prestitiagrari/confermaEliminaPrestiti";
	}
	
	@IsPopup
	@RequestMapping(value = "/elimina", method = RequestMethod.POST)
	public String eliminaPrestitiAgrari(Model model, HttpServletRequest request, HttpSession session)
			throws InternalUnexpectedException
	{
		List<Long> listIdPrestitiAgrari = NemboUtils.LIST.toListOfLong(request.getParameterValues("idPrestitiAgrari"));
		LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		quadroNemboEJB.eliminaPrestitiAgrari(listIdPrestitiAgrari, logOperationOggettoQuadroDTO,idProcedimentoOggetto);
		return "dialog/success";
	}
}