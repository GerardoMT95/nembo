package it.csi.nembo.nembopratiche.presentation.quadro.coltureaziendali;




import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDettaglioDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-305-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo305m")
public class CUNEMBO305MModificaColtureAziendali extends BaseController
{
	@Autowired
	protected IQuadroNemboEJB quadroNemboEJB = null;
	private String paginaModifica = "coltureaziendali/modificaColtureAziendali";

	private final String fieldNameProduzioneHaOrd 				= "txtProduzioneHaOrd_";
	private final String fieldNamePrezzoOrd 					= "txtPrezzoOrd_";
	private final String fieldNameProduzioneTotaleDannoEff 		= "txtProduzioneTotaleDannoEff_";
	private final String fieldNamePrezzoDanneggiatoEff 			= "txtPrezzoDanneggiatoEff_";
	private final BigDecimal zero = new BigDecimal("0.00");
	private final BigDecimal _0_01 = new BigDecimal("0.01");
	private final BigDecimal _1 = new BigDecimal("1.00");
	private final BigDecimal _100 = new BigDecimal("100.00");
	private final String errInseritiONull = "I campi Tot. Q.li e Prezzo al q.le devono essere entrambi inseriti o entrambi vuoti";
	
	@RequestMapping(value = "/index_{idSuperficieColtura}", method = RequestMethod.GET)
	public String index(HttpSession session, Model model, HttpServletRequest request,
			@PathVariable("idSuperficieColtura") long idSuperficieColtura) throws InternalUnexpectedException
	{
		long[] arrayIdSuperficieColtura = new long[] { idSuperficieColtura };
		return modificaColtureAziendaliGenerico(session, model, arrayIdSuperficieColtura);
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	{
		long[] arrayIdSuperficieColtura = NemboUtils.ARRAY.toLong(request.getParameterValues("idSuperficieColtura"));
		return modificaColtureAziendaliGenerico(session, model, arrayIdSuperficieColtura);
	}
	
	@RequestMapping(value = "/modifica", method = RequestMethod.POST)
	public String modifica(HttpSession session, Model model, HttpServletRequest request) throws InternalUnexpectedException
	{
		Errors errors = new Errors();
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		long[] arrayIdSuperficieColtura = NemboUtils.ARRAY.toLong(request.getParameterValues("idSuperficieColtura"));
		String paginaDaCaricare=null;
		
		Map<Long,ColtureAziendaliDettaglioDTO> mapColtureAziendali = new HashMap<Long,ColtureAziendaliDettaglioDTO>();
		List<ColtureAziendaliDettaglioDTO> listColtureAziendaliModificate = new ArrayList<ColtureAziendaliDettaglioDTO>();
		List<ColtureAziendaliDettaglioDTO> listColtureAziendali = quadroNemboEJB.getListColtureAziendali(idProcedimentoOggetto, arrayIdSuperficieColtura);
		for(ColtureAziendaliDettaglioDTO coltura : listColtureAziendali)
		{
			mapColtureAziendali.put(coltura.getIdSuperficieColtura(), coltura);
		}
		
		for(long idSuperficieColtura : arrayIdSuperficieColtura)
		{
			String fieldNameProduzioneHaOrd = this.fieldNameProduzioneHaOrd + idSuperficieColtura; 				
			String fieldNamePrezzoOrd = this.fieldNamePrezzoOrd + idSuperficieColtura;					
			String fieldNameProduzioneTotaleDannoEff = this.fieldNameProduzioneTotaleDannoEff + idSuperficieColtura; 		
			String fieldNamePrezzoDanneggiatoEff = this.fieldNamePrezzoDanneggiatoEff + idSuperficieColtura;
			
			String fieldProduzioneHaOrd = request.getParameter(fieldNameProduzioneHaOrd);
			String fieldPrezzoOrd = request.getParameter(fieldNamePrezzoOrd);
			String fieldProduzioneTotaleDannoEff = request.getParameter(fieldNameProduzioneTotaleDannoEff);
			String fieldPrezzoDanneggiatoEff = request.getParameter(fieldNamePrezzoDanneggiatoEff);
			
			BigDecimal _999999_99 = new BigDecimal("999999.99");
			
			BigDecimal produzioneHaOrd = errors.validateMandatoryBigDecimalInRange(fieldProduzioneHaOrd, fieldNameProduzioneHaOrd, 2, zero,_999999_99);
			BigDecimal prezzoOrd = 
					errors.validateMandatoryBigDecimalInRange(fieldPrezzoOrd, fieldNamePrezzoOrd, 2, zero,_999999_99);
			errors.validateMandatory(fieldPrezzoOrd, fieldNamePrezzoOrd);
			BigDecimal produzioneTotaleDannoEff = null;
			BigDecimal  prezzoDanneggiatoEff = null;
			if(fieldProduzioneTotaleDannoEff == null || fieldProduzioneTotaleDannoEff.equals(""))
			{
				if(fieldPrezzoDanneggiatoEff != null && !fieldPrezzoDanneggiatoEff.equals(""))
				{
					errors.addError(fieldNamePrezzoDanneggiatoEff, errInseritiONull);
				}
			}else if(fieldPrezzoDanneggiatoEff == null || fieldPrezzoDanneggiatoEff.equals(""))
			{
				if(fieldProduzioneTotaleDannoEff != null && !fieldProduzioneTotaleDannoEff.equals(""))
				{
					errors.addError(fieldNameProduzioneTotaleDannoEff, errInseritiONull);
				}
			}else
			{
				ColtureAziendaliDettaglioDTO coltura = mapColtureAziendali.get(idSuperficieColtura);
				produzioneHaOrd = errors.validateMandatoryBigDecimalInRange(fieldProduzioneHaOrd, fieldNameProduzioneHaOrd, 2, _0_01,_999999_99);
				prezzoOrd = errors.validateMandatoryBigDecimalInRange(fieldPrezzoOrd, fieldNamePrezzoOrd, 2, _0_01,_999999_99);
				BigDecimal maxProduzioneTotaleDannoEff = null;
				if(produzioneHaOrd != null)
				{
					maxProduzioneTotaleDannoEff = coltura.getSuperficieUtilizzata().multiply(produzioneHaOrd).setScale(2, RoundingMode.DOWN);
				}else
				{
					maxProduzioneTotaleDannoEff = _999999_99;
				}
				
				produzioneTotaleDannoEff = 
						errors.validateMandatoryBigDecimalInRange(
								fieldProduzioneTotaleDannoEff, 
								fieldNameProduzioneTotaleDannoEff, 2, zero, _999999_99.min(maxProduzioneTotaleDannoEff));
				
				prezzoDanneggiatoEff = 
						errors.validateMandatoryBigDecimalInRange(fieldPrezzoDanneggiatoEff,fieldNamePrezzoDanneggiatoEff, 2, zero,_999999_99);

			}
			if(errors.isEmpty())
			{
				ColtureAziendaliDettaglioDTO colturaModificata = mapColtureAziendali.get(idSuperficieColtura);
				colturaModificata.setProduzioneHa(produzioneHaOrd);
				colturaModificata.setPrezzo(prezzoOrd);
				colturaModificata.setProduzioneTotaleDanno(produzioneTotaleDannoEff);
				colturaModificata.setPrezzoDanneggiato(prezzoDanneggiatoEff);
				if(produzioneTotaleDannoEff != null)
				{
					BigDecimal numeratore = produzioneTotaleDannoEff.multiply(prezzoDanneggiatoEff);
					BigDecimal denominatore = colturaModificata.getSuperficieUtilizzata().multiply(colturaModificata.getProduzioneHa())
						.multiply(colturaModificata.getPrezzo()).setScale(2,RoundingMode.HALF_UP);
					BigDecimal percentualeDanno = null;
					try
					{
						percentualeDanno = _1.subtract(
								numeratore.divide(
										denominatore, MathContext.DECIMAL128) //NB: da usare quando faccio divide
								).multiply(_100).setScale(2,RoundingMode.HALF_UP);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						percentualeDanno = BigDecimal.ZERO;
					}
					colturaModificata.setPercentualeDanno(percentualeDanno);
					
					if(percentualeDanno.compareTo(new BigDecimal("999.99")) > 0)
					{
						errors.addError("errorPercentualeMax", "La percentuale di danno calcolata su alcune colture supera il valore massimo di 999.99%. Modificare i dati inseriti e riprovare");
					}
					else if(percentualeDanno.compareTo(new BigDecimal("-999.99")) < 0)
					{
						errors.addError("errorPercentualeMax", "La percentuale di danno calcolata su alcune colture è inferiore al valore minimo di -999.99%. Modificare i dati inseriti e riprovare");
					}
				}
				else
				{
					colturaModificata.setPercentualeDanno(null);
				}
				listColtureAziendaliModificate.add(colturaModificata);
			}
		}
		if(errors.addToModelIfNotEmpty(model))
		{
			paginaDaCaricare = modificaColtureAziendaliGenerico(session,model,arrayIdSuperficieColtura);
			model.addAttribute("preferRequest", Boolean.TRUE);
		}
		else
		{
			paginaDaCaricare = "redirect:../cunembo305l/index.do";
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
			quadroNemboEJB.updateColtureAziendali(idProcedimentoOggetto,logOperationOggettoQuadroDTO,listColtureAziendaliModificate);
		}
		return paginaDaCaricare;
	}

	private String modificaColtureAziendaliGenerico(HttpSession session, Model model, long[] arrayIdSuperficieColtura)
			throws InternalUnexpectedException
	{
		long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		List<ColtureAziendaliDettaglioDTO> listColtureAziendali = quadroNemboEJB
				.getListColtureAziendali(idProcedimentoOggetto, arrayIdSuperficieColtura);
		model.addAttribute("listColtureAziendali", listColtureAziendali);
		return paginaModifica;
	}
	
	
	  
}
