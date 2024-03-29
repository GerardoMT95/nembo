package it.csi.nembo.nembopratiche.presentation.quadro.prestitiagrari;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.prestitiagrari.PrestitiAgrariDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-302-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo302m")
public class CUNEMBO302MModificaPrestitiAgrariController extends CUNEMBO302BaseController
{

	@Autowired
	private IQuadroNemboEJB quadroNemboEJB = null;
	
	@RequestMapping(value = "/modifica_prestiti_agrari_multipla", method = RequestMethod.POST)
	public String modificaPrestitiAgrariMultipla(HttpServletRequest request, HttpSession session, Model model, @RequestParam("idPrestitiAgrari") String[] arrayIdPrestitiAgrari)
			throws InternalUnexpectedException
	{

		return modifica(session, model,arrayIdPrestitiAgrari);
	}
	
	@RequestMapping(value = "/modifica_prestito_agrario_{idPrestitiAgrari}", method = RequestMethod.GET)
	public String modificaPrestitoAgrario(HttpServletRequest request, HttpSession session, Model model,
			@PathVariable("idPrestitiAgrari") long idPrestitiAgrari)
			throws InternalUnexpectedException
	{
			String[] arrayIdPrestitiAgrari = new String[]{Long.toString(idPrestitiAgrari)};
			return modifica(session, model,arrayIdPrestitiAgrari);
	}
	
	private String modifica(HttpSession session, Model model, String[] arrayIdPrestitiAgrari) throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		List<PrestitiAgrariDTO> listPrestiti = quadroNemboEJB.getListPrestitiAgrari(idProcedimentoOggetto, NemboUtils.ARRAY.toLong(arrayIdPrestitiAgrari));
		model.addAttribute("cuNumber", cuNumber);
		model.addAttribute("listPrestiti", listPrestiti);
		return "prestitiagrari/modificaPrestitiAgrari";
	}
	
	@RequestMapping(value = "/modifica_prestiti_agrari_conferma", method = RequestMethod.POST)
	  public String modificaPrestitiAgrariConferma(
			  HttpServletRequest request,
			  HttpSession session,
			  Model model
			  ) throws InternalUnexpectedException
	  {
		  String[] arrayIdPrestitiAgrari = request.getParameterValues(fieldNameIdPrestitiAgrari);
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  Errors errors = new Errors();
		  List<PrestitiAgrariDTO> listPrestitiAgrari = new ArrayList<>();
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dateMin=null,dateMax=null;
			try
			{
				dateMin = sdf.parse("01/01/1900");
				dateMax = sdf.parse("01/01/2200");
			} catch (ParseException e)
			{
				//e.printStackTrace();
			}
		  for(String idPrestitiAgrari : arrayIdPrestitiAgrari)
		  {
			  String idConcat = "_" + idPrestitiAgrari;
			  String fieldNameFinalitaPrestitoId = fieldNameFinalitaPrestito + idConcat;
			  String fieldNameIstitutoEroganteId = fieldNameIstitutoErogante + idConcat;
			  String fieldNameImportoId = fieldNameImporto + idConcat;
			  String fieldNameDataScadenzaId = fieldNameDataScadenza + idConcat;
			  
			  String fieldFinalitaPrestito = request.getParameter(fieldNameFinalitaPrestitoId);
			  String fieldIstitutoErogante = request.getParameter(fieldNameIstitutoEroganteId);
			  String fieldImporto = request.getParameter(fieldNameImportoId);
			  String fieldDataScadenza = request.getParameter(fieldNameDataScadenzaId);
			  
			  errors.validateMandatoryFieldLength(fieldFinalitaPrestito, 1, 100, fieldNameFinalitaPrestitoId, true);
			  errors.validateMandatoryFieldLength(fieldIstitutoErogante, 1, 100, fieldNameIstitutoEroganteId, false);
		      errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImportoId, 2, new BigDecimal("0.01"), new BigDecimal("99999999.99"));
		      errors.validateMandatoryDate(fieldDataScadenza, fieldNameDataScadenzaId,true);
		      errors.validateMandatoryDateInRange(fieldDataScadenza, fieldNameDataScadenzaId, dateMin, dateMax, true, true);
		  }
		  if(errors.addToModelIfNotEmpty(model))
		  {
				model.addAttribute("preferRequest", Boolean.TRUE);
				return modifica(session, model,arrayIdPrestitiAgrari);
		  }
		  for(String idPrestitiAgrari : arrayIdPrestitiAgrari)
		  {
			  String idConcat = "_" + idPrestitiAgrari;
			  String fieldNameFinalitaPrestitoId = fieldNameFinalitaPrestito + idConcat;
			  String fieldNameIstitutoEroganteId = fieldNameIstitutoErogante + idConcat;
			  String fieldNameImportoId = fieldNameImporto + idConcat;
			  String fieldNameDataScadenzaId = fieldNameDataScadenza + idConcat;
			  
			  String fieldFinalitaPrestito = request.getParameter(fieldNameFinalitaPrestitoId);
			  String fieldIstitutoErogante = request.getParameter(fieldNameIstitutoEroganteId);
			  String fieldImporto = request.getParameter(fieldNameImportoId);
			  String fieldDataScadenza = request.getParameter(fieldNameDataScadenzaId);

			  BigDecimal importo = errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImportoId, 2, new BigDecimal("0.01"), new BigDecimal("99999999.99"));
		      Date dataScadenza = errors.validateMandatoryDateInRange(fieldDataScadenza, fieldNameDataScadenzaId, dateMin, dateMax, true, true);
		      
		      PrestitiAgrariDTO prestito = new PrestitiAgrariDTO();
		      prestito.setIdPrestitiAgrari(Long.parseLong(idPrestitiAgrari));
		      prestito.setFinalitaPrestito(fieldFinalitaPrestito);
		      prestito.setIstitutoErogante(fieldIstitutoErogante);
		      prestito.setImporto(importo);
		      prestito.setDataScadenza(dataScadenza);
		      listPrestitiAgrari.add(prestito);
		  }
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  quadroNemboEJB.modificaPrestitiAgrari(idProcedimentoOggetto,logOperationOggettoQuadroDTO, listPrestitiAgrari);
		  return "redirect:../cunembo302l/index.do";
	  }
}
