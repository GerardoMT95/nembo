package it.csi.nembo.nembopratiche.presentation.scorte;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-297-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo297i")
public class CUNEMBO297IScorteInserisciController extends BaseController
{
	private static final String FIELD_NAME_DESCRIZIONE_SCORTA = "descrizione";
	private static final String FIELD_NAME_UNITA_DI_MISURA_HIDDEN = "unitaDiMisuraHidden";
	private static final String FIELD_NAME_UNITA_DI_MISURA = "unitaDiMisura";
	private static final String FIELD_NAME_ID_SCORTA = "idScorta";
	private static final String FIELD_NAME_QUANTITA = "quantita";
	
	@Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;

	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
	      long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      long idTipologiaAltro = quadroNemboEJB.getIdScorteAltro();
	      List<DecodificaDTO<Long>> elencoTipologieScorte = quadroNemboEJB.getElencoTipologieScorte();
	      List<DecodificaDTO<Long>> elencoUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
	      model.addAttribute("elencoTipologieScorte",elencoTipologieScorte);  
	      model.addAttribute("elencoUnitaMisura",elencoUnitaMisura);
	      model.addAttribute("idTipologiaAltro", idTipologiaAltro);
	      return "scorte/inserisciScorte";  
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
	  
	  @RequestMapping(value = "/inserisci_scorte", method = RequestMethod.POST)
	  public String inserisciScorte(
			  HttpSession session, 
			  Model model,
			  HttpServletRequest request,
			  @RequestParam(value = FIELD_NAME_QUANTITA, required = false) 	         		String fieldQuantita,
			  @RequestParam(value = FIELD_NAME_ID_SCORTA, required = false) 	     		String fieldTipologia,			  
			  @RequestParam(value = FIELD_NAME_UNITA_DI_MISURA, required = false) 			String fieldUnitaDiMisura,
			  @RequestParam(value = FIELD_NAME_UNITA_DI_MISURA_HIDDEN, required = false) 	String fieldUnitaDiMisuraHidden,
			  @RequestParam(value = FIELD_NAME_DESCRIZIONE_SCORTA, required = false) String fieldDescrizioneScorta
			  ) throws InternalUnexpectedException
	  {
	      long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      Errors errors = new Errors();
	      List<DecodificaDTO<Long>> listaTipologiaScorteDecodificaDTO = quadroNemboEJB.getElencoTipologieScorte();
	      List<String> listaTipologiaScorte = new ArrayList<String>();
	      for(DecodificaDTO<Long> elem : listaTipologiaScorteDecodificaDTO){
	    	  listaTipologiaScorte.add(elem.getId().toString());
	      }
	      List<DecodificaDTO<Long>> elencoTipologieScorte = quadroNemboEJB.getElencoTipologieScorte();
	      List<DecodificaDTO<Long>> elencoUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
	      long idTipologiaAltro = quadroNemboEJB.getIdScorteAltro();
	      
	      List<String> listIdUnitaMisura = new ArrayList<String>();
	      for(DecodificaDTO<Long> dt : elencoUnitaMisura)
	      {
	    	  listIdUnitaMisura.add(Long.toString(dt.getId()));
	      }
	     
	      errors.validateMandatoryValueList(fieldTipologia, FIELD_NAME_ID_SCORTA, listaTipologiaScorte.toArray(new String[listaTipologiaScorte.size()]));
	      errors.validateMandatoryBigDecimalInRange(fieldQuantita, FIELD_NAME_QUANTITA, 2, new BigDecimal("0.01"), new BigDecimal("99999.99"));
	      String[] arrayIdUnitaMisura = listIdUnitaMisura.toArray(new String[listIdUnitaMisura.size()]);
	      errors.validateMandatoryValueList(fieldUnitaDiMisuraHidden, FIELD_NAME_UNITA_DI_MISURA_HIDDEN, arrayIdUnitaMisura);
	      
	      String descrizioneScorta=null;
	      Long idUnitaDiMisura = null;
	      boolean isAltro = Long.toString(idTipologiaAltro).equals(fieldTipologia);
	      if(isAltro) //sto gestendo il caso di Altro come Tipologia di scorta
	      {
	    	  errors.validateMandatoryFieldLength(fieldDescrizioneScorta, 1, 4000, FIELD_NAME_DESCRIZIONE_SCORTA, true);
		      errors.validateMandatoryValueList(fieldUnitaDiMisura, FIELD_NAME_UNITA_DI_MISURA, arrayIdUnitaMisura);
	      }
	      else
	      {
	    	  descrizioneScorta=null;
	    	  fieldDescrizioneScorta=null;
	    	  fieldUnitaDiMisura=null;
	    	  fieldUnitaDiMisuraHidden=null;
	    	  idUnitaDiMisura=null;
	      }
	      if(errors.addToModelIfNotEmpty(model))
	      {
	    	  model.addAttribute("preferRequest", Boolean.TRUE);  
		      model.addAttribute("elencoTipologieScorte",elencoTipologieScorte);  
		      model.addAttribute("elencoUnitaMisura",elencoUnitaMisura);
		      model.addAttribute("idTipologiaAltro", idTipologiaAltro);
		      return "scorte/inserisciScorte";  
	      }
	      descrizioneScorta = fieldDescrizioneScorta;
	      if(isAltro)
	      {
	    	  idUnitaDiMisura = new Long(fieldUnitaDiMisuraHidden);
	      }
	      quadroNemboEJB.inserisciScorte(idProcedimentoOggetto,
	    		  					Long.parseLong(fieldTipologia),
	    		  					new BigDecimal(fieldQuantita.replace(',', '.')),
	    		  					idUnitaDiMisura,
	    		  					descrizioneScorta,
	    		  					getLogOperationOggettoQuadroDTO(session));
	      
	      return "redirect:../cunembo297l/index.do";
	  }
}
