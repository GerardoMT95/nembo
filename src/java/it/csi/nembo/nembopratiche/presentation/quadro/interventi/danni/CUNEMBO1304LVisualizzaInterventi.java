package it.csi.nembo.nembopratiche.presentation.quadro.interventi.danni;

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
@NemboSecurity(value = "CU-NEMBO-1304-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo1304l")
public class CUNEMBO1304LVisualizzaInterventi extends Elenco
{

  @Override
  public String getCodiceQuadro()
  {
    return NemboConstants.QUADRO.CODICE.INTERVENTI;
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
    List<RigaElencoInterventi> elenco = elenco_json(model, request);
    request.setAttribute("withDanni",true);
    return new ModelAndView("excelElencoInterventiView", "elenco", elenco);
  }

@Override
protected void isInterventoWithDanni(Model model)
{
	model.addAttribute("withDanni", Boolean.TRUE);
}
  
  
}