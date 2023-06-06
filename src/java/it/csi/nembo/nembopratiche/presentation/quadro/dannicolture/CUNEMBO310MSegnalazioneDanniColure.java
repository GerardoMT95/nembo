package it.csi.nembo.nembopratiche.presentation.quadro.dannicolture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.danni.RigaSegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-310-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo310m")
public class CUNEMBO310MSegnalazioneDanniColure extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "confermaElimina_{idDettaglioSegnalDan}", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session,
      @PathVariable(value = "idDettaglioSegnalDan") long idDettaglioSegnalDan)
      throws InternalUnexpectedException
  {

    setModelDialogWarning(model,
        "Stai cercando di ripristinare i dati riferiti al danno per questa coltura, vuoi continuare?",
        "../cunembo310m/confermaElimina_" + idDettaglioSegnalDan + ".do");
    return "dialog/confermaNoNote";
  }

  @IsPopup
  @RequestMapping(value = "confermaElimina_{idDettaglioSegnalDan}", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request,
      @PathVariable(value = "idDettaglioSegnalDan") long idDettaglioSegnalDan)
      throws InternalUnexpectedException
  {
    quadroEjb.deleteSegnalazioniDanno(idDettaglioSegnalDan);
    return "redirect:../cunembo310l/index.do";
  }
  
  
  
  @RequestMapping(value = "/modifica_{idDettaglioSegnalDan}", method = RequestMethod.GET)
  public String modificaSingola(Model model, HttpServletRequest request, HttpSession session,
      @PathVariable("idDettaglioSegnalDan") @ModelAttribute("idDettaglioSegnalDan") long idDettaglioSegnalDan)
      throws InternalUnexpectedException
  {
    List<Long> list = new ArrayList<Long>();
    list.add(idDettaglioSegnalDan);
    long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
	model.addAttribute("elenco", quadroEjb.getSegnalazioneDanno(idProcedimentoOggetto,list).getElencoDanni());
    return "dannicolture/modificaMultiplaSegnalazioneDanniColture";
  }

  @RequestMapping(value = "/modifica_multipla", method = RequestMethod.POST)
  public String modificaMultipla(Model model, HttpServletRequest request,HttpSession session)
      throws InternalUnexpectedException
  {
    List<Long> list = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idDettaglioSegnalDan"));
    long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
	model.addAttribute("elenco", quadroEjb.getSegnalazioneDanno(idProcedimentoOggetto,list).getElencoDanni());
    return "dannicolture/modificaMultiplaSegnalazioneDanniColture";
  }
  
  
  @RequestMapping(value = "/modifica", method = RequestMethod.POST)
  public String modifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> list = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idDettaglioSegnalDan"));
    
    //Validazione dati
    Errors errors = new Errors();
    List<RigaSegnalazioneDannoDTO> elencoModificato = new ArrayList<RigaSegnalazioneDannoDTO>();
    for(long idDettaglioSegnalDan: list)
    {
    	RigaSegnalazioneDannoDTO riga = new RigaSegnalazioneDannoDTO();
    	riga.setIdDettaglioSegnalDan(idDettaglioSegnalDan);
	    final String parameterNameNote = "noteDanno_" + idDettaglioSegnalDan;
	    final String parameterNamePercDanno = "percDanno_" + idDettaglioSegnalDan;
	    final String parameterNameImportoDanno = "importoDanno_" + idDettaglioSegnalDan;
	    final String percDanno = request.getParameter(parameterNamePercDanno);
	    final String importoDanno = request.getParameter(parameterNameImportoDanno);
	    final String note = request.getParameter(parameterNameNote);
	    
	    
	    String nameFlagColturaAssicurata = "flagColturaAssicurata_"+ idDettaglioSegnalDan;
	        if (request.getParameter(nameFlagColturaAssicurata) != null)
	        {
	        	riga.setFlagColturaAssicurata(NemboConstants.FLAGS.SI);
	        }
	        else
	        {
	        	riga.setFlagColturaAssicurata(NemboConstants.FLAGS.NO);
	        }
	    
	    BigDecimal percBD = errors.validateMandatoryBigDecimalInRange(percDanno, parameterNamePercDanno,2,  new BigDecimal("1"), new BigDecimal("100"));  
	    BigDecimal importoBD = errors.validateMandatoryBigDecimalInRange(importoDanno.replace(",", "."), parameterNameImportoDanno, 2,new BigDecimal("0.1"), new BigDecimal("9999999999.99"));
	    if(errors.validateOptionalFieldMaxLength(note, 5000, parameterNameNote))
	    		riga.setNoteDanno(note);
	    if(percBD!=null)
	    	riga.setPercDanno(percBD);
	    if(importoBD!=null)
	    	riga.setImportoDanno(importoBD);
	    
	    elencoModificato.add(riga);
    }
    
    if (errors.addToModelIfNotEmpty(model))
    {
    	model.addAttribute("preferRequestValues", Boolean.TRUE);
    	model.addAttribute("elenco", quadroEjb.getSegnalazioneDanno(getIdProcedimentoOggetto(request.getSession()),list).getElencoDanni());
    	return "dannicolture/modificaMultiplaSegnalazioneDanniColture";
    }
    else
    {
    	//update
    	quadroEjb.updateSegnalazioniDanno(elencoModificato);
    	return "redirect:../cunembo310l/index.do";
    }
    
  }

}
