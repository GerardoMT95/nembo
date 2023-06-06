package it.csi.nembo.nembopratiche.presentation.danni;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-298-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo298e")
public class CUNEMBO298EDanniEliminaController extends CUNEMBO298DanniBaseController
{

	@IsPopup
	@RequestMapping(value = "/conferma_elimina_danno_{idDannoAtm}", method = RequestMethod.GET)
	public String confermaEliminaInterventi(
			Model model,
			HttpSession session,
			HttpServletRequest request,
			@PathVariable("idDannoAtm") long idDannoAtm) throws InternalUnexpectedException
	{	
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		long[] arrayIdDannoAtm = new long[1];
		arrayIdDannoAtm[0] = idDannoAtm;
		long nInterventi = quadroNemboEJB.getNInterventiAssociatiDanni(idProcedimentoOggetto, new long[]{idDannoAtm});
		model.addAttribute("ids",arrayIdDannoAtm);
		model.addAttribute("len",arrayIdDannoAtm.length);
		model.addAttribute("nInterventi", nInterventi);
		return "danni/confermaEliminaDanni";
	}
	
	@IsPopup
	@RequestMapping(value = "/conferma_elimina_danni", method = RequestMethod.GET)
	public String confermaEliminaInterventi(
			Model model,
			HttpSession session,
			HttpServletRequest request
			) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		String[] arrayIdDannoAtmString = request.getParameterValues("idDannoAtm");
		if(arrayIdDannoAtmString == null)
		{
			arrayIdDannoAtmString = new String[0];
		}
		long[] arrayIdDannoAtm = NemboUtils.ARRAY.toLong(arrayIdDannoAtmString);
		long nInterventi = quadroNemboEJB.getNInterventiAssociatiDanni(idProcedimentoOggetto, arrayIdDannoAtm);
		model.addAttribute("ids", arrayIdDannoAtm);
		model.addAttribute("len", arrayIdDannoAtm.length);
		model.addAttribute("nInterventi",nInterventi);
		return "danni/confermaEliminaDanni";
	}
	
	@IsPopup
	@RequestMapping(value = "/elimina", method = RequestMethod.POST)
	public String eliminaIntervento(Model model, HttpServletRequest request, HttpSession session)
			throws InternalUnexpectedException, ApplicationException
	{
		List<Long> listIdDannoAtm = NemboUtils.LIST.toListOfLong(request.getParameterValues("idDannoAtm"));
		LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		long nDanniEliminati = quadroNemboEJB.eliminaDanni(listIdDannoAtm, logOperationOggettoQuadroDTO,idProcedimentoOggetto);
		if(nDanniEliminati == NemboConstants.ERRORI.ELIMINAZIONE_DANNI_CON_INTERVENTI)
		{
			model.addAttribute("azione", "eliminare");
			return "danni/failureInterventiDanni";
		}
		return "dialog/success";
	}
}
