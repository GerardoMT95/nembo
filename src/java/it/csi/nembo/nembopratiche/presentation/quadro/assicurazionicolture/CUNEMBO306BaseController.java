package it.csi.nembo.nembopratiche.presentation.quadro.assicurazionicolture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.validator.Errors;

public abstract class CUNEMBO306BaseController extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB;
	
	  public String inserisci(
			  HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  Errors errors = new Errors();
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadro = getLogOperationOggettoQuadroDTO(session);
		  
		  final String fieldNameProvincia="slcProvincia";
		  final String fieldNameConsorzi="slcConsorzi";
		  final String fieldNameEntePrivato="txtEntePrivato";
		  final String fieldNameSocioPolizza="txtSocioPolizza";
		  final String fieldNamePremioAnnuale="txtPremioAnnuale";
		  final String fieldNameValoreAssicurato="txtValoreAssicurato";
		  final String fieldNameRisarcimento="txtRisarcimento";
		  
		  String provincia = request.getParameter(fieldNameProvincia);
		  String consorziStr = request.getParameter(fieldNameConsorzi);
		  String entePrivato = request.getParameter(fieldNameEntePrivato);
		  String socioPolizza = request.getParameter(fieldNameSocioPolizza);
		  String premioAnuualeStr = request.getParameter(fieldNamePremioAnnuale);
		  String valoreAssicuratoStr = request.getParameter(fieldNameValoreAssicurato);
		  String risarcimentoStr = request.getParameter(fieldNameRisarcimento);
		  
		  
		  Long consorzi = null;
		  if(consorziStr.equals(""))
		  {
			  //ho l'ente privato e non devo avere consorzi
			  errors.validateMandatoryFieldLength(entePrivato, 1, 120, fieldNameEntePrivato, false);
			  consorzi=null;
		  }
		  else
		  {
			  //se non ho l'ente privato devo avere dei consorzi
			  List<String> listProvincePiemonte = quadroNemboEJB.getProvinceCodici(NemboConstants.GENERIC.ID_REGIONE_BASILICATA);
			  String[] arrayProvincePiemonte = listProvincePiemonte.toArray(new String[listProvincePiemonte.size()]);
			  provincia = errors.validateMandatoryValueList(provincia, fieldNameProvincia, arrayProvincePiemonte);
			  consorzi = errors.validateMandatoryLong(consorziStr,fieldNameConsorzi);
			  List<DecodificaDTO<Integer>> consorziDifesaDellaProvincia = quadroNemboEJB.getListConsorzi(provincia);
			  List<Long> listIdConsorzi = new ArrayList<Long>();
			  for(DecodificaDTO<Integer> c : consorziDifesaDellaProvincia)
			  {
				  listIdConsorzi.add(c.getId().longValue());
			  }
			  errors.validateMandatoryValueList(consorzi, fieldNameConsorzi, listIdConsorzi.toArray(new Long[listIdConsorzi.size()]));
			  if(!entePrivato.trim().equals(""))
			  {
				  errors.addError(fieldNameEntePrivato, "Il campo deve essere vuoto se è stato selezionato un consorzio");
			  }
			  entePrivato = null;
		  }
		  errors.validateMandatoryFieldLength(socioPolizza, 0, 20, fieldNameSocioPolizza, false);
		  BigDecimal maxPremioAnnuale = new BigDecimal("99999999.99");
		  BigDecimal premioAnnuale = errors.validateMandatoryBigDecimalInRange(premioAnuualeStr, fieldNamePremioAnnuale, 2, new BigDecimal("0.01"), maxPremioAnnuale);
		  
		  BigDecimal maxValoreAssicurabile = maxPremioAnnuale;
		  if(premioAnnuale != null)
		  {
			  maxValoreAssicurabile = maxPremioAnnuale.min(premioAnnuale);
		  }
		  BigDecimal minValoreAssicurato = null;
		  if(premioAnnuale == null)
		  {
			  minValoreAssicurato = new BigDecimal("0.01"); 
		  }
		  else
		  {
			  minValoreAssicurato = premioAnnuale.add(new BigDecimal("0.01"));
		  }
		  BigDecimal valoreAssicurato = errors.validateMandatoryBigDecimalInRange(valoreAssicuratoStr, fieldNameValoreAssicurato, 2, minValoreAssicurato, maxPremioAnnuale);
		  
		  BigDecimal maxValoreRisarcimento = new BigDecimal("99999999.99");
		  if(valoreAssicurato != null)
		  {
			  maxValoreRisarcimento = maxValoreRisarcimento.min(valoreAssicurato);
		  }
		  BigDecimal risarcimento = errors.validateBigDecimalInRange(risarcimentoStr, fieldNameRisarcimento, 2, BigDecimal.ZERO, maxValoreRisarcimento);
		  
		  AssicurazioniColtureDTO assicurazioniColture = new AssicurazioniColtureDTO();
		  if(errors.addToModelIfNotEmpty(model))
		  {
			  model.addAttribute("preferRequest",Boolean.TRUE);
			  if(provincia != null)
			  {
				  List<DecodificaDTO<Integer>> decodificaConsorzi = quadroNemboEJB.getListConsorzi(provincia);
				  model.addAttribute("consorzi",decodificaConsorzi);
			  }
			  return index(session, request, model);
		  }
		  else
		  {
			  assicurazioniColture.setExtIdProvincia(provincia);
			  assicurazioniColture.setIdConsorzioDifesa(consorzi);
			  assicurazioniColture.setNomeEntePrivato(entePrivato);
			  assicurazioniColture.setImportoPremio(premioAnnuale);
			  assicurazioniColture.setImportoAssicurato(valoreAssicurato);
			  assicurazioniColture.setImportoRimborso(risarcimento);
			  assicurazioniColture.setNumeroSocioPolizza(socioPolizza);
			  inserisci(request, session, model, idProcedimentoOggetto, assicurazioniColture, logOperationOggettoQuadro);
			  return "redirect:../cunembo306l/index.do";
		  }
	  }
	  
	  public abstract String index(HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException;
	  
	  public abstract void inserisci(
			  	HttpServletRequest request,
			  	HttpSession session,
			  	Model model,
			  	long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture, LogOperationOggettoQuadroDTO logOperationOggettoQuadro)
			    throws InternalUnexpectedException;
	  
	  @RequestMapping(value = "/get_list_consorzi_{idProvincia}", method = RequestMethod.GET, produces = "application/json")	  
	  @ResponseBody
	  public List<DecodificaDTO<Integer>> getListConsorzi(
			  HttpSession session, 
			  HttpServletRequest request,
			  Model model,
			  @PathVariable("idProvincia") String idProvincia) throws InternalUnexpectedException
	  {
		  List<DecodificaDTO<Integer>> listConsorzi = quadroNemboEJB.getListConsorzi(idProvincia);
		  return listConsorzi;
	  }
	  
	  @RequestMapping(value="/get_list_assicurazioni_colture", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<AssicurazioniColtureDTO> getListAssicurazioniColture(
			  HttpSession session, 
			  HttpServletRequest request,
			  Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  return quadroNemboEJB.getListAssicurazioniColture(idProcedimentoOggetto);
	  }
	
}
