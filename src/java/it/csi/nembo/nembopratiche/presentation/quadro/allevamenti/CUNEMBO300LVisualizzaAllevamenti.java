package it.csi.nembo.nembopratiche.presentation.quadro.allevamenti;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-300-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo300l")
public class CUNEMBO300LVisualizzaAllevamenti extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  List<String> tableNamesToRemove = new ArrayList<String>();
		  tableNamesToRemove.add("tableListPlvZootecnicaDettaglio");
		  tableNamesToRemove.add("tableDettaglioAllevamentiPlv");
		  cleanTableMapsInSession(session, tableNamesToRemove);
		  return "allevamenti/visualizzaAllevamenti";
	  }
	  
	  @RequestMapping(value = "/get_list_allevamenti.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<AllevamentiDTO> getListAllevamenti(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<AllevamentiDTO> lista =  quadroNemboEJB.getListAllevamenti(idProcedimentoOggetto);
		  return lista;
	  }
	  
	  @RequestMapping(value = "/get_list_allevamenti_singoli_non_danneggiati.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<AllevamentiDTO> lista =  quadroNemboEJB.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto);
		  return lista;
	  }
}
