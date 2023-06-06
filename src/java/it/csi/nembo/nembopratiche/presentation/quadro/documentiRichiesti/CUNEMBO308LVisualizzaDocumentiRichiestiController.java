package it.csi.nembo.nembopratiche.presentation.quadro.documentiRichiesti;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.dto.SezioneDocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo308l")
@NemboSecurity(value = "CU-NEMBO-308-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO308LVisualizzaDocumentiRichiestiController extends BaseController{

	 public static final String CU_NAME = "CU-NEMBO-308-L";
	  @Autowired
	  IQuadroNemboEJB                 quadroNemboEJB;
	  

	  @RequestMapping("/index")
	  public String index(Model model, HttpSession session)
	      throws InternalUnexpectedException
	  { 
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<SezioneDocumentiRichiestiDTO> sezioniDaVisualizzare = quadroNemboEJB.getListDocumentiRichiestiDaVisualizzare(idProcedimentoOggetto, true);
		  if(sezioniDaVisualizzare==null) model.addAttribute("numeroDoc", 0);
		  else model.addAttribute("numeroDoc", sezioniDaVisualizzare.size());
		  model.addAttribute("sezioniDaVisualizzare", sezioniDaVisualizzare);
		  model.addAttribute("visualizzazione", Boolean.TRUE);
	    return "documentiRichiesti/visualizzaDocumentiRichiesti";
	  }
	  
	  

	public List<DocumentiRichiestiDTO> getListDocumentiRichiesti(long idProcedimentoOggetto) throws InternalUnexpectedException
	  {
		  List<DocumentiRichiestiDTO> documentiRichiesti = quadroNemboEJB.getDocumentiRichiesti(idProcedimentoOggetto);
		  return documentiRichiesti;
	  }
	
}
