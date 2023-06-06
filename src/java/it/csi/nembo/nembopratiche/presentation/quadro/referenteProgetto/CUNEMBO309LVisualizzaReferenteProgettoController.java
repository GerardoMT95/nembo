package it.csi.nembo.nembopratiche.presentation.quadro.referenteProgetto;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.ReferenteProgettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo309l")
@NemboSecurity(value = "CU-NEMBO-309-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO309LVisualizzaReferenteProgettoController extends BaseController{

	 public static final String CU_NAME = "CU-NEMBO-309-L";

	  @Autowired
	  IQuadroNemboEJB                 quadroNemboEJB;

	  @RequestMapping("/index")
	  public String index(Model model, HttpSession session)
	      throws InternalUnexpectedException
	  { 
		//recupero se esiste il referente del progetto
		ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(session);
		long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
		long idBandoOggetto = procedimentoOggetto.getIdBandoOggetto();
		QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO-309-L");
		ReferenteProgettoDTO referenteProgetto = quadroNemboEJB.getReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto);
		if(referenteProgetto==null){
			model.addAttribute("referenteAssegnato", Boolean.FALSE);
		}else{
			model.addAttribute("referenteAssegnato", Boolean.TRUE);
		}
		model.addAttribute("ultimaModifica",
		          getUltimaModifica(quadroNemboEJB,
		              idProcedimentoOggetto,
		              quadro.getIdQuadroOggetto(),
		              idBandoOggetto));
		model.addAttribute("referente", referenteProgetto);
	    return "referenteProgetto/visualizzaReferenteProgetto";
	  }
	  
	
	  
}
