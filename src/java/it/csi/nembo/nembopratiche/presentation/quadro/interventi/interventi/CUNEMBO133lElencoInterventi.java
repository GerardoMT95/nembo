package it.csi.nembo.nembopratiche.presentation.quadro.interventi.interventi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaElencoInterventi;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Elenco;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-133-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo133l")
public class CUNEMBO133lElencoInterventi extends Elenco
{

  @Override
  public String getCodiceQuadro()
  {
    return NemboConstants.QUADRO.CODICE.INTERVENTI_INFRASTRUTTURE;
  }

  @Override
  public String getFlagEscludiCatalogo()
  {
    return NemboConstants.FLAGS.NO;
  }

  @Override
  protected boolean isBandoConPercentualeRiduzione(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return interventiEJB.isBandoConPercentualeRiduzione(idProcedimentoOggetto);
  }

  @Override
  protected void addExtraAttributeToModel(Model model,
      HttpServletRequest request)
  {
    // Nessun attributo da aggiungere rispetto alla gestione standard
  }

  @RequestMapping(value = "elencoInterventiExcel")
  public ModelAndView downloadExcel(Model model, HttpServletRequest request,
      HttpSession session) throws InternalUnexpectedException
  {
	List<String> valoriFlag = new ArrayList<>();
    List<RigaElencoInterventi> elenco = elenco_json(model, request);
    if(elenco!=null){
    	 for(RigaElencoInterventi riga : elenco){
	    	valoriFlag = interventiEJB.getFlagCanaleOpereCondotta(getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto(),
	    			riga.getIdIntervento());
	    	if(valoriFlag!=null && valoriFlag.size()>0){
	    		if("S".equals(valoriFlag.get(0))){riga.setFlagCanale("SÌ");} else{riga.setFlagCanale("NO");}
	    		if("S".equals(valoriFlag.get(1))){riga.setFlagOpereDiPresa("SÌ");} else{riga.setFlagOpereDiPresa("NO");}
	    		if("S".equals(valoriFlag.get(2))){riga.setFlagCondotta("SÌ");} else{riga.setFlagCondotta("NO");}
	    	}
	    	
	    }
    }   
    
    return new ModelAndView("excelElencoInterventiView", "elenco", elenco);
  }

	@Override
	protected void isInterventoWithDanni(Model model)
	{
		model.addAttribute("withDanni", Boolean.FALSE);
	}
}