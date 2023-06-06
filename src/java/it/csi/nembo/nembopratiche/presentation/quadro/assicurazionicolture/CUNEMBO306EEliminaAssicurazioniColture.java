package it.csi.nembo.nembopratiche.presentation.quadro.assicurazionicolture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-306-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo306e")
public class CUNEMBO306EEliminaAssicurazioniColture extends BaseController
{
	  private static final String FIELD_NAME_ID_NAME = "fieldNameIdName";
	  final String idsName = "chkIdAssicurazioniColture";
	  final String fieldNameIdName = "idsName";
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB;
	
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  @IsPopup
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long[] arrayIdAssicurazioniColture = NemboUtils.ARRAY.toLong(request.getParameterValues(idsName));
		  if(arrayIdAssicurazioniColture!=null)
		  {
			  model.addAttribute("len", arrayIdAssicurazioniColture.length);
			  model.addAttribute("ids",arrayIdAssicurazioniColture);
		  }else
		  {
			  model.addAttribute("len", 0);
		  }
		  model.addAttribute(fieldNameIdName,idsName);
		  model.addAttribute(FIELD_NAME_ID_NAME, fieldNameIdName);
		  return "assicurazionicolture/popupEliminaAssicurazioniColture";
	  }
	  
	  @RequestMapping(value = "/index_{chkIdAssicurazioniColture}", method = RequestMethod.GET)
	  @IsPopup
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model,
			  @PathVariable("chkIdAssicurazioniColture") long idAssicurazioniColture) throws InternalUnexpectedException
	  {
		  long[] arrayIdAssicurazioniColture = new long[]{idAssicurazioniColture};
		  model.addAttribute("ids", arrayIdAssicurazioniColture);
		  model.addAttribute("len", arrayIdAssicurazioniColture.length);
		  model.addAttribute(fieldNameIdName,idsName);
		  model.addAttribute(FIELD_NAME_ID_NAME, fieldNameIdName);
		  return "assicurazionicolture/popupEliminaAssicurazioniColture";
	  }
	  
	  @RequestMapping(value = "/elimina", method = RequestMethod.POST)
	  public String elimina(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  String fieldName = request.getParameter(FIELD_NAME_ID_NAME);
		  long[] arrayIdAssicurazioniColture = NemboUtils.ARRAY.toLong(request.getParameterValues(fieldName));
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  quadroNemboEJB.eliminaAssicurazioniColture(idProcedimentoOggetto, arrayIdAssicurazioniColture);
		  return "dialog/success";
	  }
	
}
