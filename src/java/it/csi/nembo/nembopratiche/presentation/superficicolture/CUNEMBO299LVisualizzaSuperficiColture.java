package it.csi.nembo.nembopratiche.presentation.superficicolture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureRiepilogoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-299-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo299l")
public class CUNEMBO299LVisualizzaSuperficiColture extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  List<String> tableNamesToRemove = new ArrayList<>();
		  tableNamesToRemove.add("tableSuperificiColturePlvVegetale");
		  tableNamesToRemove.add("tableDettaglioParticellareSuperificiColture");
		  cleanTableMapsInSession(session, tableNamesToRemove);
		  
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  SuperficiColtureRiepilogoDTO elencoSuperficiColtureRiepilogo = quadroNemboEJB.getSuperficiColtureRiepilogo(idProcedimentoOggetto);

		  Map<Long,StringBuilder> mapIdSuperficieColturaAnomalia = new HashMap<Long,StringBuilder>();
		  Map<Long,Map<String,Boolean>> mapIdSuperficieColturaBloccante = new HashMap<Long,Map<String,Boolean>>();
		  List<ControlloColturaDTO> elencoControlloColtura = quadroNemboEJB.getListControlloColtura(idProcedimentoOggetto,null);
		  for(ControlloColturaDTO cc : elencoControlloColtura)
		  {
			  if(mapIdSuperficieColturaAnomalia.containsKey(cc.getIdSuperficieColtura()))
			  {
				  mapIdSuperficieColturaAnomalia
				  		.get(cc.getIdSuperficieColtura())
				  		.append("; ")
				  		.append(cc.getDescrizioneAnomalia());
				  
				  if(cc.getBloccante()!= null && !cc.getBloccante().equals(""))
				  {
					  mapIdSuperficieColturaBloccante.get(cc.getIdSuperficieColtura()).put(cc.getBloccante(),Boolean.TRUE);
				  }
			  }
			  else
			  {
				  StringBuilder sb = new StringBuilder(cc.getDescrizioneAnomalia());
				  mapIdSuperficieColturaAnomalia.put(cc.getIdSuperficieColtura(), sb);
				  Map<String,Boolean> map = new HashMap<String,Boolean>();
				  if(cc.getBloccante() != null && !cc.getBloccante().equals(""))
				  {
					  map.put(cc.getBloccante(),Boolean.TRUE);
				  }
				  mapIdSuperficieColturaBloccante.put(cc.getIdSuperficieColtura(), map);
			  }
		  }
		  model.addAttribute("elencoSuperficiColtureRiepilogo",elencoSuperficiColtureRiepilogo);
		  model.addAttribute("mapIdSuperficieColturaAnomalia",mapIdSuperficieColturaAnomalia);
		  model.addAttribute("mapIdSuperficieColturaBloccante",mapIdSuperficieColturaBloccante);
		  return "superficicolture/visualizzaSuperficiColture";
	  }
	  
	  @RequestMapping(value = "/get_elenco_superfici_colture_dettaglio.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<SuperficiColtureDettaglioDTO> getElencoSuperficiColtureDettaglio(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<SuperficiColtureDettaglioDTO> listSuperficiColtureDettaglio = quadroNemboEJB.getListSuperficiColtureDettaglio(idProcedimentoOggetto);
		  if(listSuperficiColtureDettaglio == null)
		  {
			  listSuperficiColtureDettaglio = new ArrayList<SuperficiColtureDettaglioDTO>();
		  }
		  return listSuperficiColtureDettaglio;
	  }
}
