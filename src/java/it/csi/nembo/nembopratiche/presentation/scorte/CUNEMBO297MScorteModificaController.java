package it.csi.nembo.nembopratiche.presentation.scorte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-297-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo297m")
public class CUNEMBO297MScorteModificaController extends BaseController
{
	private static final String FIELD_NAME_DESCRIZIONE_SCORTA = "descrizione";
	private static final String FIELD_NAME_UNITA_DI_MISURA_HIDDEN = "unitaDiMisuraHidden";
	private static final String FIELD_NAME_UNITA_DI_MISURA = "unitaDiMisura";
	private static final String FIELD_NAME_ID_SCORTA = "idScorta";
	private static final String FIELD_NAME_QUANTITA = "quantita";
	
	@Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	
	@RequestMapping(value = "/modifica_scorte_dettaglio", method = RequestMethod.POST)
	public String modificaScorte(HttpSession session, Model model, HttpServletRequest request,
			@RequestParam("idScortaMagazzino") String[] arrayIdScortaMagazzino)
			throws InternalUnexpectedException
	{

		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		long idScortaAltro = quadroNemboEJB.getIdScorteAltro();
		List<ScorteDTO> scorte = new ArrayList<>();
		Map<Long,Boolean> mappaDisabledDescrizione = new HashMap<>();
		scorte = quadroNemboEJB.getScorteByIds(NemboUtils.ARRAY.toLong(arrayIdScortaMagazzino), idProcedimentoOggetto);
		List<DecodificaDTO<Long>> elencoTipologieScorte = quadroNemboEJB.getElencoTipologieScorte();
		List<DecodificaDTO<Long>> elencoUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
		Map<Long,Long> mappaTipologiaScorteUnitaDiMisura = quadroNemboEJB.getMapTipologiaScorteUnitaDiMisura();
		for(ScorteDTO scorta : scorte)
		{
			if(scorta.getIdScorta() == idScortaAltro)
			{
				mappaDisabledDescrizione.put(scorta.getIdScortaMagazzino(),false);
			}
			else
			{
				mappaDisabledDescrizione.put(scorta.getIdScortaMagazzino(),true);
			}
		}
		model.addAttribute("elencoTipologieScorte", elencoTipologieScorte);
		model.addAttribute("elencoUnitaMisura", elencoUnitaMisura);
		model.addAttribute("idScortaAltro", idScortaAltro);
		model.addAttribute("mappaTipologiaScorteUnitaDiMisura", mappaTipologiaScorteUnitaDiMisura);
		model.addAttribute("mappaDisabledDescrizione", mappaDisabledDescrizione);
		model.addAttribute("scorte", scorte);
		return "scorte/modificaScorteMultipla";
	}
	
	@RequestMapping(value = "/modifica_scorte_conferma", method = RequestMethod.POST)
	public String modificaScorteConferma(HttpSession session, HttpServletRequest request, Model model)
			throws InternalUnexpectedException, ApplicationException
	{
		boolean isErrato=false;
		long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		String[] arrayIdScortaMagazzino = request.getParameterValues("idScortaMagazzino");
		List<String> listaTipologiaScorte = new ArrayList<>();
		List<DecodificaDTO<Long>> elencoTipologieScorte = quadroNemboEJB.getElencoTipologieScorte();
		List<DecodificaDTO<Long>> elencoUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
		Map<Long,Long> mappaTipologiaScorteUnitaDiMisura = quadroNemboEJB.getMapTipologiaScorteUnitaDiMisura();
		List<ScorteDTO> listScorte = new ArrayList<>();
		Map<Long,Boolean> mappaDisabledDescrizione = new HashMap<>();
		
		
		List<String> listIdUnitaMisura = new ArrayList<>();
		for (DecodificaDTO<Long> dt : elencoUnitaMisura)
		{
			listIdUnitaMisura.add(Long.toString(dt.getId()));
		}
		String[] arrayIdUnitaMisura = listIdUnitaMisura.toArray(new String[listIdUnitaMisura.size()]);
		for(DecodificaDTO<Long> elem : elencoTipologieScorte)
		{
	    	  listaTipologiaScorte.add(elem.getId().toString());
	    }
		long idScortaAltro = quadroNemboEJB.getIdScorteAltro();
	      
		Errors errors = new Errors();
		for(String idScortaMagazzino : arrayIdScortaMagazzino)
		{
			String fieldNameIdScorta = FIELD_NAME_ID_SCORTA + "_" + idScortaMagazzino;
			String fieldNameDescrizione = FIELD_NAME_DESCRIZIONE_SCORTA + "_" + idScortaMagazzino;
			String fieldNameQuantita = FIELD_NAME_QUANTITA + "_" + idScortaMagazzino;
			String fieldNameUnitaDiMisura = FIELD_NAME_UNITA_DI_MISURA + "_" + idScortaMagazzino;
			String fieldNameUnitaDiMisuraHidden = FIELD_NAME_UNITA_DI_MISURA_HIDDEN + "_" + idScortaMagazzino;
			
			String fieldIdScorta = request.getParameter(fieldNameIdScorta);
			String fieldDescrizione = request.getParameter(fieldNameDescrizione);
			String fieldQuantita = request.getParameter(fieldNameQuantita);
			String fieldUnitaDiMisuraHidden = request.getParameter(fieldNameUnitaDiMisuraHidden);
			String descrizione;
			Long idUnitaDiMisura=null;
			errors.validateMandatoryValueList(fieldIdScorta, fieldNameIdScorta, listaTipologiaScorte.toArray(new String[listaTipologiaScorte.size()]));
			BigDecimal bdQuantita = errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita, 2, new BigDecimal("0.01"), new BigDecimal("99999.99"));
			errors.validateMandatoryValueList(fieldUnitaDiMisuraHidden, fieldNameUnitaDiMisuraHidden, arrayIdUnitaMisura);
			
			if (Long.toString(idScortaAltro).equals(fieldIdScorta))
			{
				errors.validateMandatoryFieldLength(fieldDescrizione, 1, 4000, fieldNameDescrizione, true);
				errors.validateMandatoryValueList(fieldUnitaDiMisuraHidden, fieldNameUnitaDiMisura, arrayIdUnitaMisura);
				descrizione = fieldDescrizione;
				if(fieldUnitaDiMisuraHidden != null && !fieldUnitaDiMisuraHidden.equals(""))
				{
					idUnitaDiMisura = new Long(fieldUnitaDiMisuraHidden);
				}
				mappaDisabledDescrizione.put(new Long(idScortaMagazzino), false);
			} 
			else
			{
				fieldDescrizione = null;
				descrizione = null;
				idUnitaDiMisura = null;
				mappaDisabledDescrizione.put(new Long(idScortaMagazzino), true);
				
			}
			if (errors.size() != 0)
			{
				isErrato = true;
			}
			if(!isErrato)
			{
				ScorteDTO scorta = new ScorteDTO();
				scorta.setIdScortaMagazzino(NemboUtils.NUMBERS.getNumericValue(idScortaMagazzino));
			    scorta.setIdScorta(NemboUtils.NUMBERS.getNumericValue(fieldIdScorta));
			    scorta.setQuantita(bdQuantita);
			    scorta.setIdUnitaMisura(idUnitaDiMisura);
			    scorta.setDescrizione(descrizione);
			    listScorte.add(scorta);
			}
		}
		
		if(isErrato)
		{
			errors.addToModelIfNotEmpty(model);
			List<ScorteDTO> scorte = quadroNemboEJB.getScorteByIds(NemboUtils.ARRAY.toLong(arrayIdScortaMagazzino), idProcedimentoOggetto);
			for(ScorteDTO scorta : scorte)
			{
				String idScorta = request.getParameter("idScorta_" + scorta.getIdScortaMagazzino());
				if(NemboUtils.NUMBERS.isNumericValue(idScorta) && NemboUtils.NUMBERS.checkLong(idScorta) != idScortaAltro)
				{
					if(mappaTipologiaScorteUnitaDiMisura.containsKey(new Long(idScorta)))
					{
						Long idUnitaMisura = mappaTipologiaScorteUnitaDiMisura.get(Long.parseLong(idScorta));
						scorta.setIdUnitaMisura(idUnitaMisura);
					}
				}
				else
				{
					scorta.setIdUnitaMisura(null);
				}
			}
			model.addAttribute("preferRequest", Boolean.TRUE);
			model.addAttribute("elencoTipologieScorte", elencoTipologieScorte);
			model.addAttribute("elencoUnitaMisura", elencoUnitaMisura);
			model.addAttribute("idScortaAltro", idScortaAltro);
			model.addAttribute("mappaTipologiaScorteUnitaDiMisura", mappaTipologiaScorteUnitaDiMisura);
			model.addAttribute("mappaDisabledDescrizione",mappaDisabledDescrizione);
			model.addAttribute("scorte",scorte);
			return "scorte/modificaScorteMultipla";
		}
		else
		{
			long nScorteModificate = 
					quadroNemboEJB.modificaScorte(listScorte, getLogOperationOggettoQuadroDTO(session), idProcedimentoOggetto);
			if(nScorteModificate == NemboConstants.ERRORI.ELIMINAZIONE_SCORTE_CON_DANNI_CON_INTERVENTI)
			{
				throw new ApplicationException("Impossibile eliminare le scorte desiderate perch� esistono degli interventi associati ai danni delle scorte",10003);
			}
		}
		return "redirect:../cunembo297l/index.do";
	}
	
	  @RequestMapping(value = "/get_unita_misura_by_scorta_{idScorta}", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public Long getUnitaMisuraByScorta(
			  HttpSession session, 
			  Model model,
			  @PathVariable(FIELD_NAME_ID_SCORTA) long idScorta)  throws InternalUnexpectedException
	  {
		  return quadroNemboEJB.getUnitaMisuraByScorta(idScorta);
	  }
	  
	@IsPopup
	@RequestMapping(value = "/conferma_modifica_scorta_{idScortaMagazzino}", method = RequestMethod.GET)
	public String confermaModificaScorta(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable("idScortaMagazzino") long idScortaMagazzino) throws InternalUnexpectedException
	{
		long [] arrayIdScortaMagazzino = new long[]{idScortaMagazzino};
		return confermaModificaGenerico(model, session, arrayIdScortaMagazzino);
	}
	
	@IsPopup
	@RequestMapping(value = "/conferma_modifica_scorte", method = RequestMethod.POST)
	public String confermaModificaScorte(
			Model model,
			HttpSession session,
			HttpServletRequest request
			) throws InternalUnexpectedException
	{
		String[] arrayIdScortaMagazzino = request.getParameterValues("idScortaMagazzino");
		return confermaModificaGenerico(model, session, NemboUtils.ARRAY.toLong(arrayIdScortaMagazzino));
	}

	private String confermaModificaGenerico(Model model, HttpSession session, long[] arrayIdScortaMagazzino)
			throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		if(arrayIdScortaMagazzino == null)
		{
			arrayIdScortaMagazzino = new long[0];
		}
		Long nDanniScorte = quadroNemboEJB.getNDanniScorte(idProcedimentoOggetto, arrayIdScortaMagazzino);
		List<Long> listIdScortaMagazzino = new ArrayList<>();
		for(long l : arrayIdScortaMagazzino)
		{
			listIdScortaMagazzino.add(l);
		}
		long n = quadroNemboEJB.getNInterventiAssociatiDanniScorte(idProcedimentoOggetto, listIdScortaMagazzino);
		if(n > 0L)
		{
			model.addAttribute("azione", "modificare");
			return "scorte/failureScorteDanniInterventi";
		}
		model.addAttribute("ids", arrayIdScortaMagazzino);
		model.addAttribute("len", arrayIdScortaMagazzino.length);
		model.addAttribute("nDanniScorte",nDanniScorte);
		return "scorte/confermaModificaScorte";
	}
	  
}
