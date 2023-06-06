package it.csi.nembo.nembopratiche.presentation.quadro.coltureaziendali;




import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDettaglioDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-305-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo305l")
public class CUNEMBO305LVisualizzaColtureAziendali extends CUNEMBO305BaseController
{

	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  ColtureAziendaliDTO riepilogo = quadroNemboEJB.getRiepilogoColtureAziendali(idProcedimentoOggetto);
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  String ultimaModifica = getUltimaModifica(quadroNemboEJB, logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(), logOperationOggettoQuadroDTO.getIdQuadroOggetto(),logOperationOggettoQuadroDTO.getIdBandoOggetto());
		  Map<Long, StringBuilder> mapIdSuperficieColturaAnomalia = this.getMapIdSuperficieColturaAnomalia(idProcedimentoOggetto,request);

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
		  model.addAttribute("mapIdSuperficieColturaAnomalia", mapIdSuperficieColturaAnomalia);
		  return "coltureaziendali/visualizzaColtureAziendali";
	  }
	  
	  @RequestMapping(value = "/get_list_colture_aziendali.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<ColtureAziendaliDettaglioDTO> listSuperficiColtureDettaglio = quadroNemboEJB.getListColtureAziendali(idProcedimentoOggetto);
		  return listSuperficiColtureDettaglio;
	  }

	@Override
	protected long[] getArrayIdSuperficieColtura(HttpServletRequest request)
	{
		//li voglio scaricare tutti
		return null;
	}
	  
}
