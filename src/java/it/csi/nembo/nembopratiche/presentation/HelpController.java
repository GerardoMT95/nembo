package it.csi.nembo.nembopratiche.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.INuovoProcedimentoEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping(value = "/help")
@NemboSecurity(value = "", controllo = NemboSecurity.Controllo.NESSUNO)
public class HelpController extends BaseController
{
  @Autowired
  private INuovoProcedimentoEJB nuovoProcedimento = null;

  @RequestMapping(value = "gethelp_{codcdu}", method = RequestMethod.POST)
  @ResponseBody
  public String gethelp(@PathVariable("codcdu") String codcdu, Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_HELP_IS_ACTIVE,
        NemboConstants.FLAGS.SI);
    ProcedimentoOggetto procedimentoOggetto = NemboFactory
        .getProcedimentoOggetto(request);
    if (procedimentoOggetto == null)
    {
      return nuovoProcedimento.getHelpCdu(codcdu, null);
    }

    QuadroOggettoDTO qo = procedimentoOggetto.findQuadroByCU(codcdu);
    if (qo == null)
    {
      return nuovoProcedimento.getHelpCdu(codcdu, null);
    }

    return nuovoProcedimento.getHelpCdu(codcdu, qo.getIdQuadroOggetto());
  }

  @RequestMapping(value = "cleanhelp_{codcdu}", method = RequestMethod.POST)
  @ResponseBody
  public String cleanhelp(@PathVariable("codcdu") String codcdu, Model model,
      HttpSession session) throws InternalUnexpectedException
  {
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_HELP_IS_ACTIVE,
        NemboConstants.FLAGS.NO);
    return "OK";
  }
}
