package it.csi.nembo.nembopratiche.presentation.danni;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDTO;
import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.DannoDTO;
import it.csi.nembo.nembopratiche.dto.danni.ParticelleDanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.UnitaMisuraDTO;
import it.csi.nembo.nembopratiche.dto.fabbricati.FabbricatiDTO;
import it.csi.nembo.nembopratiche.dto.motoriagricoli.MotoriAgricoliDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.QuadroNemboDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-298-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo298i")
public class CUNEMBO298IDanniInserisciController extends CUNEMBO298DanniBaseController
{
	  private static final String LIST_UNITA_MISURA = "listUnitaMisura";
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  ObjectWriter jacksonWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());
	  
	  final static String fieldNameDescrizione = "descrizione";
	  final static String fieldNameUnitaMisura = "unitaMisura";
	  final static String fieldNameQuantita = "quantita";
	  final static String fieldNameImporto = "importo";
	  final static String fieldNameIdDanno = "idDanno";
	  final static String fieldNameIdScortaMagazzino = "idScortaMagazzino";
	  final static String cuId = "CU-NEMBO-298-I";
	  final static String commonFieldIdDanno ="idDanno";
	  final static String inserisciDannoConduzioniDettaglio = "inserisci_danno_conduzioni_dettaglio.do";

	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
	      long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      session.removeAttribute(FiltroRicercaConduzioni.class.getName());
	      model.addAttribute("idProcedimentoOggetto",idProcedimentoOggetto);
	      List<DecodificaDTO<Integer>> listTipologieDanni = quadroNemboEJB.getTabellaDecodifica("NEMBO_D_DANNO",true);
	      model.addAttribute("listTipologieDanni",listTipologieDanni);
	      return "danni/inserisciDanniTipo";  
	  }	  
	  
	  @RequestMapping(value = "inserisci_danno_dettaglio", method = RequestMethod.POST)
	  public String inserisciDannoDettaglio(
			  HttpSession session, 
			  Model model,
			  HttpServletRequest request
			  ) throws InternalUnexpectedException,JsonGenerationException,JsonMappingException, IOException
	  {
		  Errors errors = new Errors();
		  boolean errorNothingSelected = false;
		  String paginaDaCaricare="";
		  
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
	      String fieldIdDanno = request.getParameter(fieldNameIdDanno);
	      errors.validateMandatoryLong(fieldIdDanno, fieldNameIdDanno);
	      boolean piantagioniArboree = false;
	      if(errors.isEmpty())
	      {
	    	  Integer idDanno = Integer.parseInt(fieldIdDanno);
	    	  
	    	  Map<String, Object> common = getCommonFromSession(cuId, session, true);
	    	  common.put(commonFieldIdDanno, idDanno);
	    	  saveCommonInSession(common, session);
	    	  UnitaMisuraDTO unitaMisura = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
	    	  model.addAttribute(fieldNameIdDanno,idDanno);
	    	  switch(idDanno)
		      {
		      case NemboConstants.DANNI.SCORTA:
		      case NemboConstants.DANNI.SCORTE_MORTE:
		    	  String[] arrayIdScortaMagazzino = request.getParameterValues("scorteDualList");
		    	  if(arrayIdScortaMagazzino == null || arrayIdScortaMagazzino.length == 0)
		    	  {
		    		  errorNothingSelected = true;
		    		  model.addAttribute("errorNothingSelected", errorNothingSelected);
		    	  }
		    	  else
		    	  {
		    		  List<ScorteDTO> listScorte = quadroNemboEJB.getScorteByIds(NemboUtils.ARRAY.toLong(arrayIdScortaMagazzino),idProcedimentoOggetto);
		    		  model.addAttribute("listScorte",listScorte);
		    		  paginaDaCaricare="danni/inserisciDanniDettaglio";
		    	  }
		    	  break;
		      case NemboConstants.DANNI.MACCHINA_AGRICOLA:
		      case NemboConstants.DANNI.ATTREZZATURA:
		    	  String[] arrayIdMacchine = request.getParameterValues("macchineDualList");
		    	  UnitaMisuraDTO danno = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
		    	  if(arrayIdMacchine == null || arrayIdMacchine.length == 0)
		    	  {
		    		  errorNothingSelected=true;
		    		  model.addAttribute("errorNothingSelected", errorNothingSelected);
		    		  paginaDaCaricare="danni/inserisciDanniTipo";
		    	  }
		    	  else
		    	  {
		    		  List<MotoriAgricoliDTO> listMacchine = quadroNemboEJB.getListMotoriAgricoliNonDanneggiati(NemboUtils.ARRAY.toLong(arrayIdMacchine),idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
		    		  model.addAttribute("listMacchine",listMacchine);
		    		  model.addAttribute("danno", danno);
		    		  model.addAttribute("listMacchine", listMacchine);
		    		  paginaDaCaricare="danni/inserisciDanniDettaglio";
		    	  }
		    	  break;
		    	  
		      case NemboConstants.DANNI.FABBRICATO:
		    	  long[] arrayIdFabbricato = NemboUtils.ARRAY.toLong(request.getParameterValues("fabbricatiDualList"));
		    	  
		    	  UnitaMisuraDTO dannoFabbricato = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
		    	  if(arrayIdFabbricato == null || arrayIdFabbricato.length == 0)
		    	  {
		    		  errorNothingSelected=true;
		    		  model.addAttribute("errorNothingSelected", errorNothingSelected);
		    		  paginaDaCaricare="danni/inserisciDanniTipo";
		    	  }
		    	  else
		    	  {
		    		  List<FabbricatiDTO> listFabbricati = quadroNemboEJB.getListFabbricatiNonDanneggiati(idProcedimentoOggetto,arrayIdFabbricato, getUtenteAbilitazioni(session).getIdProcedimento());
		    		  model.addAttribute("listFabbricati",listFabbricati);
		    		  model.addAttribute("danno", dannoFabbricato);
		    		  paginaDaCaricare="danni/inserisciDanniDettaglio";
		    	  }
		    	  break;
		      case NemboConstants.DANNI.PIANTAGIONI_ARBOREE:
		    	  piantagioniArboree = true;
		      case NemboConstants.DANNI.TERRENI_RIPRISTINABILI:
		      case NemboConstants.DANNI.TERRENI_NON_RIPRISTINABILI:
		      case NemboConstants.DANNI.ALTRE_PIANTAGIONI:
		    	  	String indietro = request.getParameter("indietro");
		    	  	if(indietro != null && indietro.equals("true"))
		    	  	{
		    	  		long[] arrayIdUtilizzoDichiarato = NemboUtils.ARRAY.toLong(request.getParameterValues("idUtilizzoDichiarato"));
		    	  		List<ParticelleDanniDTO> listConduzioni =  quadroNemboEJB.getListConduzioniDannoGiaSelezionate(idProcedimentoOggetto, arrayIdUtilizzoDichiarato, piantagioniArboree);
		    		    StringWriter sw = new StringWriter();
		    		    jacksonWriter.writeValue(sw, listConduzioni);
		    		    model.addAttribute("json", sw.toString());
		    	  	}
		    	    model.addAttribute("provincie",quadroNemboEJB.getListProvinciaConTerreniInConduzione(idProcedimentoOggetto, NemboConstants.GENERIC.ID_REGIONE_BASILICATA));
	        		model.addAttribute("comuneDisabled", Boolean.TRUE);
	        		model.addAttribute("sezioneDisabled", Boolean.TRUE);
	        		model.addAttribute("idDanno",fieldIdDanno);
	        		model.addAttribute("cu", "298i");
					model.addAttribute("urlDannoDettaglio",inserisciDannoConduzioniDettaglio);
	        		paginaDaCaricare = "danni/elencoConduzioni";
		    	  
		      break;
		      case NemboConstants.DANNI.ALLEVAMENTO:
		    	  long[] arrayIdAllevamento = NemboUtils.ARRAY.toLong(request.getParameterValues("allevamentiDualList"));
		    	  UnitaMisuraDTO dannoAllevamenti = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
		    	  if(arrayIdAllevamento == null || arrayIdAllevamento.length == 0)
		    	  {
		    		  errorNothingSelected=true;
		    		  model.addAttribute("errorNothingSelected", errorNothingSelected);
		    		  paginaDaCaricare="danni/inserisciDanniTipo";
		    	  }
		    	  else
		    	  {
		    		  List<AllevamentiDTO> listAllevamenti = quadroNemboEJB.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto, arrayIdAllevamento);
		    		  model.addAttribute("listAllevamenti",listAllevamenti);
		    		  model.addAttribute("danno", dannoAllevamenti);
		    		  paginaDaCaricare="danni/inserisciDanniDettaglio";
		    	  }
		    	  
	    	  break;
		      case NemboConstants.DANNI.ALTRO:
		      default:
		    	  paginaDaCaricare="danni/inserisciDanniDettaglio";
	    		  if(unitaMisura != null)
	    		  {
	    			  model.addAttribute("descUnitaMisura",unitaMisura.getDescrizione());
	    		  }
	    		  else
	    		  {
	    			  //danni ad altro o danni che comunque non riportano l'unita di misura nella nembo_d_danno
	    			  List<DecodificaDTO<Long>> listUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
	    			  model.addAttribute(LIST_UNITA_MISURA, listUnitaMisura);
	    		  }
		    	  
	    		  paginaDaCaricare="danni/inserisciDanniDettaglio";
		    	  break;
		      }
	      }
	      if(errors.addToModelIfNotEmpty(model) || errorNothingSelected)
	      {
	    	  model.addAttribute("preferRequest", Boolean.TRUE);
	    	  return this.index(session, model);
	      }
	      else
	      {
		      List<DecodificaDTO<Long>> listUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
		      model.addAttribute(LIST_UNITA_MISURA,listUnitaMisura);
	    	  return paginaDaCaricare;
	      }
	  }
	  
	  @RequestMapping(value = "inserisci_danni_conferma", method = RequestMethod.POST)
	  public String inserisciDanniConferma(
			  HttpSession session, 
			  Model model,
			  HttpServletRequest request
			  ) throws InternalUnexpectedException, JsonGenerationException, JsonMappingException, IOException
	  {
		  Errors errors = new Errors();
		  Map<String, Object> common = getCommonFromSession(cuId, session, true);
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  final Integer idDanno = (Integer)common.get(commonFieldIdDanno);
		  List<DanniDTO> listDanniDTO = new ArrayList<DanniDTO>();
		  List<ScorteDTO> listScorte = null;
		  final String paginaDettaglio = "danni/inserisciDanniDettaglio";
		  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(session);
		  Long idUnitaMisura = null;
		  String fieldDescrizione;
		  String fieldQuantita;
		  String fieldImporto;
		  String fieldUnitaMisura;
		  
		  switch(idDanno)
		  {
		  	  case NemboConstants.DANNI.SCORTA: //caso scorte
		  	  case NemboConstants.DANNI.SCORTE_MORTE: //caso scorte
				  long arrayIdScortaMagazzino[] = NemboUtils.ARRAY.toLong(request.getParameterValues("hiddenIdScortaMagazzino"));
				  for(long idScortaMagazzino : arrayIdScortaMagazzino)
				  {
					  validaCampiInseritiDanno(request, errors, idScortaMagazzino, idDanno);
				  }
				  if(errors.addToModelIfNotEmpty(model))
				  {
						listScorte = quadroNemboEJB.getScorteByIds(arrayIdScortaMagazzino, idProcedimentoOggetto);
						model.addAttribute("listScorte", listScorte);
						return ritornaPaginaInserimento(model, idDanno, paginaDettaglio);
				  }
				  else
				  {
					  for(long idScortaMagazzino : arrayIdScortaMagazzino)
					  {
						  inserisciDanniInLista(request, idProcedimentoOggetto, idDanno, idScortaMagazzino,listDanniDTO);
					  }
					  quadroNemboEJB.inserisciDanni(
							  				listDanniDTO,
							  				idProcedimentoOggetto,
							  				idDanno,
							  				logOperationOggettoQuadroDTO, getUtenteAbilitazioni(session).getIdProcedimento());
					  return "redirect:../cunembo298l/index.do";
				  }
			  case NemboConstants.DANNI.MACCHINA_AGRICOLA: //caso Motori Agricoli - Macchine
			  case NemboConstants.DANNI.ATTREZZATURA:
				  long [] arrayIdMacchina = NemboUtils.ARRAY.toLong(request.getParameterValues("hiddenIdMacchina"));
				  for(long idMacchina : arrayIdMacchina)
				  {
					  validaCampiInseritiDanno(request, errors, idMacchina, idDanno);
				  }
				  if(errors.addToModelIfNotEmpty(model))
				  {
						List<MotoriAgricoliDTO> listMacchine = quadroNemboEJB.getListMotoriAgricoliNonDanneggiati(arrayIdMacchina, idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
						UnitaMisuraDTO danno = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
						model.addAttribute("listMacchine", listMacchine);
						model.addAttribute("idDanno", idDanno);
						model.addAttribute("danno",danno);
						return ritornaPaginaInserimento(model, idDanno, paginaDettaglio);
				  }
				  else
				  {
					  for(long idMacchina : arrayIdMacchina)
					  {
						  inserisciDanniInLista(request, idProcedimentoOggetto, idDanno, idMacchina, listDanniDTO);
					  }
					  quadroNemboEJB.inserisciDanni(
				  				listDanniDTO,
				  				idProcedimentoOggetto,
				  				idDanno,
				  				logOperationOggettoQuadroDTO, getUtenteAbilitazioni(session).getIdProcedimento());
					  return "redirect:../cunembo298l/index.do";
				  }
			  case NemboConstants.DANNI.FABBRICATO:
				  long[] arrayIdFabbricato = NemboUtils.ARRAY.toLong(request.getParameterValues("hiddenIdFabbricato"));
				  
				  for(long idFabbricato : arrayIdFabbricato)
				  {
					  validaCampiInseritiDanno(request, errors, idFabbricato, idDanno);
				  }
				  if(errors.addToModelIfNotEmpty(model))
				  {
						List<FabbricatiDTO> listFabbricati= quadroNemboEJB.getListFabbricatiNonDanneggiati(idProcedimentoOggetto,arrayIdFabbricato, getUtenteAbilitazioni(session).getIdProcedimento());
						UnitaMisuraDTO danno = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
						model.addAttribute("listFabbricati", listFabbricati);
						model.addAttribute("idDanno", idDanno);
						model.addAttribute("danno",danno);
						return ritornaPaginaInserimento(model, idDanno, paginaDettaglio);
				  }
				  else
				  {
					  for(long idFabbricato : arrayIdFabbricato)
					  {
						  inserisciDanniInLista(request, idProcedimentoOggetto, idDanno, idFabbricato, listDanniDTO);
					  }
					  quadroNemboEJB.inserisciDanni(
				  				listDanniDTO,
				  				idProcedimentoOggetto,
				  				idDanno,
				  				logOperationOggettoQuadroDTO, getUtenteAbilitazioni(session).getIdProcedimento());
					  return "redirect:../cunembo298l/index.do";
				  }	
			  case NemboConstants.DANNI.TERRENI_RIPRISTINABILI:
			  case NemboConstants.DANNI.TERRENI_NON_RIPRISTINABILI:
			  case NemboConstants.DANNI.PIANTAGIONI_ARBOREE:
			  case NemboConstants.DANNI.ALTRE_PIANTAGIONI:
				  String fieldNameIdUtilizzoDichiarato = "idUtilizzoDichiarato";
				  long[] arrayIdUtilizzoDichiarato = NemboUtils.ARRAY.toLong(request.getParameterValues(fieldNameIdUtilizzoDichiarato));  
				  fieldDescrizione = request.getParameter(fieldNameDescrizione);
				  fieldQuantita   = request.getParameter(fieldNameQuantita);
				  fieldImporto = request.getParameter(fieldNameImporto);
				  BigDecimal sumSuperficiCatastali = 
						  quadroNemboEJB.getSumSuperficiCatastaliParticelle(idProcedimentoOggetto,arrayIdUtilizzoDichiarato);			  
				  BigDecimal quantita = errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita, 4, 
						  new BigDecimal("0.0001"),
						  new BigDecimal("99999.9999").min(sumSuperficiCatastali));
				  
				  errors.validateMandatoryFieldLength(fieldDescrizione, 1, 4000, fieldNameDescrizione);
				  BigDecimal importo = errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto, 2, new BigDecimal("0.01"), new BigDecimal("9999999.99"));
				  if(errors.addToModelIfNotEmpty(model)){
					  model.addAttribute("preferRequest", Boolean.TRUE);
					  return inserisciDannoConduzioniDettaglio(session, model, request);
				  }
				  else
				  {
					  DanniDTO danno = new DanniDTO();
					  danno.setIdProcedimentoOggetto(idProcedimentoOggetto);
					  danno.setDescrizione(fieldDescrizione);
					  danno.setIdDanno(idDanno);
					  danno.setImporto(importo);
					  danno.setQuantita(quantita);
					  danno.setIdUnitaMisura(quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno).getIdUnitaMisura());
					  
					  quadroNemboEJB.inserisciDanniConduzioni(danno, idProcedimentoOggetto, arrayIdUtilizzoDichiarato, logOperationOggettoQuadroDTO);
				  }
				  break;
			  case NemboConstants.DANNI.ALLEVAMENTO:
				  long[] arrayIdAllevamento = NemboUtils.ARRAY.toLong(request.getParameterValues("hiddenIdAllevamento"));
				  List<AllevamentiDTO> listaAllevamenti = quadroNemboEJB.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto, arrayIdAllevamento);
				  Map<Long,Long> mappaIdAllevamentoQuantita = new HashMap<Long,Long>();
				  listDanniDTO = new ArrayList<DanniDTO>();
				  for(AllevamentiDTO allevamento : listaAllevamenti)
				  {
					  mappaIdAllevamentoQuantita.put(allevamento.getIdAllevamento(), allevamento.getQuantita());
					  
				  }
				  for(long idAllevamento : arrayIdAllevamento)
				  {
					  if(mappaIdAllevamentoQuantita.containsKey(idAllevamento))
					  {
						  fieldDescrizione = request.getParameter(fieldNameDescrizione + "_" + idAllevamento);
						  fieldQuantita   = request.getParameter(fieldNameQuantita + "_" + idAllevamento);
						  fieldImporto = request.getParameter(fieldNameImporto + "_" + idAllevamento);
						  
						  errors.validateMandatory(fieldDescrizione, fieldNameDescrizione + "_" + idAllevamento);
						  importo = errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto + "_" + idAllevamento , 2, new BigDecimal("0.01"), new BigDecimal("9999999.99"));
						  
						  Long maxNumeroCapi = mappaIdAllevamentoQuantita.get(idAllevamento);
						  quantita = errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita + "_" + idAllevamento, 0, new BigDecimal("1"), new BigDecimal(maxNumeroCapi));
						  
						  if(errors.isEmpty())
						  {
							  DanniDTO danno = new DanniDTO();
							  danno.setDescrizione(fieldDescrizione);
							  danno.setQuantita(quantita);
							  danno.setImporto(importo);
							  danno.setExtIdEntitaDanneggiata(idAllevamento);
							  danno.setIdDanno(idDanno);
							  danno.setIdProcedimentoOggetto(idProcedimentoOggetto);
							  listDanniDTO.add(danno);
						  }
					  }
				  }
				  if(errors.addToModelIfNotEmpty(model))
				  {
		    		  List<AllevamentiDTO> listAllevamenti = quadroNemboEJB.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto, arrayIdAllevamento);
		    		  UnitaMisuraDTO dannoAllevamenti = quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno);
		    		  model.addAttribute("listAllevamenti",listAllevamenti);
		    		  model.addAttribute("danno", dannoAllevamenti);
		    		  model.addAttribute("idDanno", idDanno);
		    		  model.addAttribute("preferRequest", Boolean.TRUE);
		    		  return "danni/inserisciDanniDettaglio";
					 
				  }
				  else
				  {
					  quadroNemboEJB.inserisciDanni(listDanniDTO, idProcedimentoOggetto, idDanno, logOperationOggettoQuadroDTO, getUtenteAbilitazioni(session).getIdProcedimento());
				  }
				  break;
			 
			  //i danni che non sono contemplati in questa lista devvono essere trattati in modo generico  
			  case NemboConstants.DANNI.ALTRO:
			  default:
				  fieldDescrizione = request.getParameter(fieldNameDescrizione);
				  fieldQuantita = request.getParameter(fieldNameQuantita);
				  fieldImporto = request.getParameter(fieldNameImporto);
				  fieldUnitaMisura = request.getParameter(fieldNameUnitaMisura);
				  errors.validateMandatory(fieldDescrizione, fieldNameDescrizione);
				  
				  quantita = errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita, 4, new BigDecimal("0.01"),new BigDecimal("99999.9999"));
				  importo = errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto, 2, new BigDecimal("0.01"), new BigDecimal("9999999.9999"));
				  boolean gestisciUnitaMisura = quadroNemboEJB.getGestisciUnitaMisuraByIdDanno(idDanno);
				  if(gestisciUnitaMisura)
				  {
					  fieldUnitaMisura = request.getParameter(fieldNameUnitaMisura);
					  idUnitaMisura = errors.validateMandatoryLong(fieldUnitaMisura, fieldNameUnitaMisura);
				  }
				  
				  if(errors.addToModelIfNotEmpty(model))
				  {
		    		  model.addAttribute("idDanno", idDanno);
		    		  model.addAttribute("preferRequest", Boolean.TRUE);
		    		  return ritornaPaginaInserimento(model, idDanno, paginaDettaglio);
				  }
				  else
				  {
					  listDanniDTO = new ArrayList<DanniDTO>();
					  DanniDTO danno = new DanniDTO();
					  danno.setDescrizione(fieldDescrizione);
					  danno.setQuantita(quantita);
					  danno.setImporto(importo);
					  danno.setIdProcedimentoOggetto(idProcedimentoOggetto);
					  danno.setIdDanno(idDanno);
					  danno.setIdUnitaMisura(idUnitaMisura);
					  listDanniDTO.add(danno);
					  quadroNemboEJB.inserisciDanni(listDanniDTO, idProcedimentoOggetto, idDanno, logOperationOggettoQuadroDTO, getUtenteAbilitazioni(session).getIdProcedimento());
				  }
				  break;
		  }
		  return "redirect:../cunembo298l/index.do";
	  }

	private void inserisciDanniInLista(HttpServletRequest request, long idProcedimentoOggetto, final Integer idDanno, long idElemento,List<DanniDTO> listDanniDTO)
	{
		  String fieldDescrizione = request.getParameter(fieldNameDescrizione + "_" + idElemento);
		  String fieldQuantita   = request.getParameter(fieldNameQuantita + "_" + idElemento);
		  String fieldImporto = request.getParameter(fieldNameImporto + "_" + idElemento);
		  
		  DanniDTO tmpDanniDTO = new DanniDTO();
		  switch(idDanno)
		  {
			  case NemboConstants.DANNI.SCORTA:
			  case NemboConstants.DANNI.SCORTE_MORTE:
				  tmpDanniDTO.setDescrizione(fieldDescrizione);
				  tmpDanniDTO.setQuantita(new BigDecimal(fieldQuantita.replace(',', '.')));
				  tmpDanniDTO.setImporto(new BigDecimal(fieldImporto.replace(',', '.')));
				  tmpDanniDTO.setIdDanno(idDanno);
				  tmpDanniDTO.setIdProcedimentoOggetto(idProcedimentoOggetto);
 				  tmpDanniDTO.setExtIdEntitaDanneggiata(idElemento);
				  break;
				  
			  case NemboConstants.DANNI.MACCHINA_AGRICOLA:
			  case NemboConstants.DANNI.ATTREZZATURA:
				  tmpDanniDTO.setDescrizione(fieldDescrizione);
				  tmpDanniDTO.setQuantita(new BigDecimal("1.0"));
				  tmpDanniDTO.setImporto(new BigDecimal(fieldImporto.replace(',', '.')));
				  tmpDanniDTO.setIdDanno(idDanno);
				  tmpDanniDTO.setIdProcedimentoOggetto(idProcedimentoOggetto);
				  tmpDanniDTO.setExtIdEntitaDanneggiata(idElemento);
				  break;
				  
			  case NemboConstants.DANNI.FABBRICATO:
				  
				  tmpDanniDTO.setDescrizione(fieldDescrizione);
				  tmpDanniDTO.setQuantita(new BigDecimal(fieldQuantita.replace(',', '.')));
				  tmpDanniDTO.setImporto(new BigDecimal(fieldImporto.replace(',', '.')));
				  tmpDanniDTO.setIdDanno(idDanno);
				  tmpDanniDTO.setIdProcedimentoOggetto(idProcedimentoOggetto);
				  tmpDanniDTO.setExtIdEntitaDanneggiata(idElemento);
				  break;
			  default:
				  //la gestione dei danni a superfici, essendo diversa, è eseguita nel metodo chiamante
				  break;
		  }
		  listDanniDTO.add(tmpDanniDTO);
	}

	private void validaCampiInseritiDanno(HttpServletRequest request, Errors errors, long idElemento, Integer idDanno) throws InternalUnexpectedException
	{
		  String fieldDescrizione = request.getParameter(fieldNameDescrizione + "_" + idElemento);
		  String fieldUnitaMisura = request.getParameter(fieldNameUnitaMisura + "_" + idElemento);
		  String fieldQuantita   = request.getParameter(fieldNameQuantita + "_" + idElemento);
		  String fieldImporto = request.getParameter(fieldNameImporto + "_" + idElemento);
		  
		  //TODO: completare con tutti i casi che sono interessati a valutare l'unità di misura
		  switch(idDanno)
		  {
			  case NemboConstants.DANNI.SCORTA:
			  case NemboConstants.DANNI.SCORTE_MORTE:
				  errors.validateMandatory(fieldDescrizione, fieldNameDescrizione + "_" + idElemento);
				  BigDecimal maxQuantita = quadroNemboEJB.getScortaByIdScortaMagazzino(new Long(idElemento)).getQuantita();
				  errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita + "_" + idElemento, 2, new BigDecimal("0.01"),new BigDecimal("99999.99"));
				  errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita + "_" + idElemento, 2, new BigDecimal("0.01"),maxQuantita);
				  errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto + "_" + idElemento , 2, new BigDecimal("0.01"), new BigDecimal("9999999.99"));
				  
				  break;
			  case NemboConstants.DANNI.FABBRICATO:
				  errors.validateMandatoryBigDecimalInRange(fieldQuantita, fieldNameQuantita + "_" + idElemento, 2, new BigDecimal("0.01"), new BigDecimal("99999.99"));
				  errors.validateMandatory(fieldDescrizione, fieldNameDescrizione + "_" + idElemento);
				  errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto + "_" + idElemento , 2, new BigDecimal("0.01"), new BigDecimal("9999999.99"));
				  break;
				  
			  case NemboConstants.DANNI.MACCHINA_AGRICOLA:
			  case NemboConstants.DANNI.ATTREZZATURA:
				  errors.validateMandatory(fieldDescrizione, fieldNameDescrizione + "_" + idElemento);
				  errors.validateMandatoryBigDecimalInRange(fieldImporto, fieldNameImporto + "_" + idElemento , 2, new BigDecimal("0.01"), new BigDecimal("9999999.99"));
				  break;

			  case NemboConstants.DANNI.ALLEVAMENTO:
				  //non validato qua
				  break;
		
		  }
	}

	private String ritornaPaginaInserimento(Model model, final Integer idDanno, String paginaDettaglio) throws InternalUnexpectedException
	{
		List<DecodificaDTO<Long>> listUnitaMisura;
		listUnitaMisura = quadroNemboEJB.getListUnitaDiMisura();
		model.addAttribute(LIST_UNITA_MISURA, listUnitaMisura);
		model.addAttribute(fieldNameIdDanno, idDanno);
		model.addAttribute("preferRequest", Boolean.TRUE);
		return paginaDettaglio;
	}

	  @RequestMapping(value = "/inserisci_danno_conduzioni_dettaglio", method = RequestMethod.POST)
	  public String inserisciDannoConduzioniDettaglio(HttpSession session, Model model, HttpServletRequest request)
	      throws InternalUnexpectedException, JsonGenerationException, JsonMappingException, IOException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  long[] arrayIdUtilizzoDichiarato = NemboUtils.ARRAY.toLong(request.getParameterValues("idUtilizzoDichiarato"));
		  String fieldNameIdDanno = "idDanno";
		  String fieldIdDanno = request.getParameter(fieldNameIdDanno);
		  boolean piantagioniArboree = false;
		  int idDanno = Integer.parseInt(fieldIdDanno);
		  String paginaDaCaricare = null; 
		  List<Integer> listDanniEquivalentiConduzioni = QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.TERRENI_RIPRISTINABILI);
		  if(listDanniEquivalentiConduzioni.contains(idDanno))
		  {
			  if(idDanno == NemboConstants.DANNI.PIANTAGIONI_ARBOREE)
			  {
				  piantagioniArboree = true;
			  }
			  if(arrayIdUtilizzoDichiarato == null || arrayIdUtilizzoDichiarato.length == 0)
			  {
				  paginaDaCaricare = inserisciDannoDettaglio(session, model, request);
				  model.addAttribute("errorNoConduzioni", Boolean.TRUE);
				  model.addAttribute("dataUrl", "");
				  
			  }
			  else
			  {
				  List<ParticelleDanniDTO> listConduzioni = quadroNemboEJB.getListConduzioniDannoGiaSelezionate(idProcedimentoOggetto, arrayIdUtilizzoDichiarato, piantagioniArboree);
				  model.addAttribute("descUnitaMisura", quadroNemboEJB.getUnitaMisuraByIdDanno(idDanno).getDescrizione());
				  model.addAttribute(fieldNameIdDanno, fieldIdDanno);
				  model.addAttribute("listConduzioni", listConduzioni);
				  paginaDaCaricare = "danni/inserisciDanniDettaglio";
			  }
		  }
		  else
		  {
			  paginaDaCaricare = index(session,model);
		  }
		  return paginaDaCaricare;
	  }
}
