package it.csi.nembo.nembopratiche.presentation.quadro.dannicolture;




import java.math.BigDecimal;
import java.math.MathContext;
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
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDTO;
import it.csi.nembo.nembopratiche.dto.dannicolture.DanniColtureDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-307-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo307l")
public class CUNEMBO307LVisualizzaDanniColture extends BaseController
{
	  @Autowired
	  IQuadroNemboEJB quadroNemboEJB;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  long idProcedimento = getIdProcedimento(session);
		  
		  
		  List<SuperficiColtureDettaglioDTO> superfici = quadroNemboEJB.getListSuperficiColtureDettaglio(idProcedimentoOggetto);
		  Long nColtureDanneggiate = quadroNemboEJB.getNColtureDanneggiate(idProcedimentoOggetto);
		  model.addAttribute("nColtureDanneggiate", nColtureDanneggiate);
		  if(superfici == null || superfici.size() == 0)
		  {
			  model.addAttribute("hasDanni",Boolean.FALSE);
		  }
		  else
		  {
			  model.addAttribute("hasDanni",Boolean.TRUE);
		  }
		  
		  AssicurazioniColtureDTO riepilogoAssicurazioni = quadroNemboEJB.getRiepilogoAssicurazioniColture(idProcedimentoOggetto);
		  model.addAttribute("riepilogoAssicurazioni", riepilogoAssicurazioni);
		  
		  ColtureAziendaliDTO riepilogo = quadroNemboEJB.getRiepilogoColtureAziendali(idProcedimentoOggetto,idProcedimento);
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  
		  //assicurazioni
		  List<AssicurazioniColtureDTO> listAssicurazioniColture = quadroNemboEJB.getListAssicurazioniColture(idProcedimentoOggetto);
		  AssicurazioniColtureDTO assicurazioniColture = new AssicurazioniColtureDTO();
		  if(listAssicurazioniColture != null && listAssicurazioniColture.size()>0)
		  {
			  BigDecimal sommaImportoPremio=BigDecimal.ZERO,sommaImportoAssicurato=BigDecimal.ZERO,sommaImportoRimborso=BigDecimal.ZERO;
			  for(AssicurazioniColtureDTO assic : listAssicurazioniColture)
			  {
				  sommaImportoPremio = sommaImportoPremio.add(assic.getImportoPremio());
				  sommaImportoAssicurato = sommaImportoAssicurato.add(assic.getImportoAssicurato());
				  sommaImportoRimborso = sommaImportoRimborso.add(assic.getImportoRimborso());
			  }
			  assicurazioniColture.setImportoPremio(sommaImportoPremio);
			  assicurazioniColture.setImportoAssicurato(sommaImportoAssicurato);
			  assicurazioniColture.setImportoRimborso(sommaImportoRimborso);
		  }
		  else
		  {
			  assicurazioniColture.setImportoPremio(BigDecimal.ZERO);
			  assicurazioniColture.setImportoAssicurato(BigDecimal.ZERO);
			  assicurazioniColture.setImportoRimborso(BigDecimal.ZERO);
		  }
		  
		  if(riepilogo.getTotalePlvEffettiva() != null)
		  {
			  riepilogo.setPlvRicalcolataConAssicurazioni(
					  riepilogo.getTotalePlvEffettiva().subtract(assicurazioniColture.getImportoPremio()).add(assicurazioniColture.getImportoRimborso())
					  );
		  }
		  else
		  {
			  riepilogo.setPlvRicalcolataConAssicurazioni(null);

		  }
		  if(riepilogo.getTotalePlvOrdinaria() != null)
		  {
			  riepilogo.setPlvOrdinariaConAssicurazioni(
					  riepilogo.getTotalePlvOrdinaria().subtract(
							  NemboUtils.NUMBERS.nvl(riepilogo.getPlvRicalcolataConAssicurazioni()))
					  );
		  }
		  else
		  {
			  riepilogo.setPlvOrdinariaConAssicurazioni(null);
		  }
		  
		  if(riepilogo.getTotalePlvOrdinaria() == null || riepilogo.getTotalePlvOrdinaria().equals(BigDecimal.ZERO))
		  {
			  riepilogo.setPercentualeDannoConAssicurazioni(BigDecimal.ZERO);
		  }
		  else
		  {
			  riepilogo.setPercentualeDannoConAssicurazioni(
					  riepilogo.getPlvOrdinariaConAssicurazioni().divide(riepilogo.getTotalePlvOrdinaria(), MathContext.DECIMAL64).multiply(new BigDecimal("100.00"))
					  );
		  }
		  
		  model.addAttribute("assicurazioniColture", assicurazioniColture);
		  model.addAttribute("riepilogo", riepilogo);
		  model.addAttribute("ultimaModifica", ultimaModifica);
		  return "dannicolture/visualizzaDanniColture";
	  }
	  
	  @RequestMapping(value = "/get_list_danni_colture.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<DanniColtureDTO> getListDanniColture(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  List<DanniColtureDTO> danniColture = quadroNemboEJB.getListDanniColture(idProcedimentoOggetto,getIdProcedimento(session));;
		  return danniColture;
	  }
}
