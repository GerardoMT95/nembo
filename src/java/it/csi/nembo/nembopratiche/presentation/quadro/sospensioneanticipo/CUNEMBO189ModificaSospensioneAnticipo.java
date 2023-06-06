package it.csi.nembo.nembopratiche.presentation.quadro.sospensioneanticipo;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.Radio;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.SospensioneAnticipoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-189", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo189")
public class CUNEMBO189ModificaSospensioneAnticipo extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    List<Radio> r = new LinkedList<Radio>();
    r.add(new Radio("S", "Sì"));
    r.add(new Radio("N", "No"));
    model.addAttribute("radio", r);
    List<SospensioneAnticipoDTO> elenco = quadroEjb
        .getElencoSospensioniAnticipoDisponibili(
            getIdProcedimentoOggetto(session));
    model.addAttribute("elenco", elenco);
    return "sospensioneanticipo/modificaSospensioneAnticipo";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public final String conferma(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    model.addAttribute("prfvalues", Boolean.TRUE);
    Errors errors = new Errors();

    String flagSospensione = "";
    String motivazione = "";

    List<SospensioneAnticipoDTO> elenco = quadroEjb
        .getElencoSospensioniAnticipoDisponibili(
            getIdProcedimentoOggetto(session));
    for (int i = 0; i < elenco.size(); i++)
    {
      flagSospensione = request.getParameter("sospensione_" + i);
      motivazione = request.getParameter("motivazione_" + i);
      errors.validateMandatory(flagSospensione, "sospensione_" + i);

      if ("S".equals(flagSospensione))
      {
        errors.validateMandatoryFieldMaxLength(motivazione, "motivazione_" + i,
            1000);
      }
      else
      {
        errors.validateFieldMaxLength(motivazione, "motivazione_" + i, 1000);
      }

      if (errors.isEmpty())
      {
        elenco.get(i).setFlagSospensione(flagSospensione);
        elenco.get(i).setMotivazione(motivazione);
      }
    }

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
      return index(model, session);
    }
    else
    {
      quadroEjb.updateSospensioneAnticipo(getIdProcedimentoOggetto(session),
          elenco, getLogOperationOggettoQuadroDTO(session));
      return "redirect:../cunembo188/index.do";
    }
  }

}
