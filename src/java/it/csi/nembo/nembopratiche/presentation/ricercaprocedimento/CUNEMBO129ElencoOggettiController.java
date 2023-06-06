package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.GruppoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo129")
@NemboSecurity(value = "CU-NEMBO-129", controllo = NemboSecurity.Controllo.DEFAULT)
public class CUNEMBO129ElencoOggettiController extends BaseController
{

	@Autowired
	private IRicercaEJB  ricercaEJB = null;
	@Autowired
	private IQuadroEJB  quadroEJB = null;
	
	@RequestMapping(value = "index_{idProcedimento}" ,method = RequestMethod.GET) 
	public String index(Model model, 
				  		HttpSession session,
				  		@PathVariable("idProcedimento") long idProcedimento) throws InternalUnexpectedException
	{ 
		List<GruppoOggettoDTO> listGruppiOggetto = ricercaEJB.getElencoOggetti(idProcedimento, 
				Arrays.asList(getUtenteAbilitazioni(session).getMacroCU()),
				getUtenteAbilitazioni(session).getIdProcedimento());
		refreshTestataProcedimento(quadroEJB, session, idProcedimento);
		
		ProcedimentoOggetto po= quadroEJB.getProcedimentoOggettoByIdProcedimento(idProcedimento);
		NemboFactory.setIdProcedimentoOggettoInSession(session, po.getIdProcedimentoOggetto(), po.getIdProcedimento());
		
		boolean notificheBloccanti = quadroEJB.checkNotifiche(idProcedimento, NemboConstants.FLAG_NOTIFICA.BLOCCANTE,NemboUtils.PAPUASERV.getFirstCodiceAttore(getUtenteAbilitazioni(session)));
		session.setAttribute("notificheBloccanti",notificheBloccanti);
		
		boolean notificheWarning = quadroEJB.checkNotifiche(idProcedimento, NemboConstants.FLAG_NOTIFICA.WARNING,NemboUtils.PAPUASERV.getFirstCodiceAttore(getUtenteAbilitazioni(session)));
		session.setAttribute("notificheWarning",notificheWarning);
		
		
		long idStatoOggetto = getProcedimentoFromSession(session).getIdStatoOggetto();
		if(idStatoOggetto < 10 || idStatoOggetto > 90)
		{
			model.addAttribute("procedimentoValido", Boolean.FALSE);
		}
		else
		{
			model.addAttribute("procedimentoValido", Boolean.TRUE);
		}
		
		model.addAttribute("elenco", listGruppiOggetto);
		session.setAttribute("comeFromRicerca", "TRUE");
		return "ricercaprocedimento/elencoOggetti";
	}

}
