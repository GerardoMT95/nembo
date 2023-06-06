package it.csi.nembo.nembopratiche.presentation.estrazionicampione;

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
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IEstrazioniEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.estrazionecampione.RigaSimulazioneEstrazioneDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-218", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping("cunembo218")
public class CUNEMBO218SimulazioneEstrazioneCampione extends BaseController
{

  @Autowired
  private IQuadroEJB     quadroEjb;

  @Autowired
  private IEstrazioniEJB estrazioniEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    addCommonData(model);
    return "estrazioniacampione/simulazione";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public final String indexPost(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    Errors errors = new Errors();
    String idTipoEstrazione = request.getParameter("idTipoEstrazione");
    errors.validateMandatoryLong(idTipoEstrazione, "idTipoEstrazione");
    model.addAttribute("prfvalues", Boolean.TRUE);

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
    }
    else
    {
      model.addAttribute("visualizzaRisultati", Boolean.TRUE);
      model.addAttribute("idTipoEstrazione", idTipoEstrazione);
    }

    return index(model, session);
  }

  @RequestMapping(value = "getElencoRisultatiSimulazione_{idTipoEstrazione}", produces = "application/json")
  @ResponseBody
  public List<RigaSimulazioneEstrazioneDTO> getElencoRisultatiSimulazione(
      HttpSession session,
      @PathVariable(value = "idTipoEstrazione") long idTipoEstrazione)
      throws InternalUnexpectedException
  {
    List<RigaSimulazioneEstrazioneDTO> elenco = quadroEjb
        .getElencoRisultatiSimulazione(idTipoEstrazione);
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    return elenco;
  }

  private void addCommonData(Model model) throws InternalUnexpectedException
  {
    List<RigaFiltroDTO> elenco = estrazioniEJB
        .getElencoTipoEstrazioniCaricabili();
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    model.addAttribute("listTipoEstrazione", elenco);
  }

}
