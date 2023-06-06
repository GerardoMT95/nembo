package it.csi.nembo.nembopratiche.presentation.estrazionicampione;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IEstrazioniEJB;
import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;

@Controller
@NemboSecurity(value = "CU-NEMBO-220", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping("cunembo220")
public class CUNEMBO220CaricamentoEstrazioneCampione extends BaseController
{

  @Autowired
  private IEstrazioniEJB estrazioniEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    addCommonData(model);
    return "estrazioniacampione/caricamento";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public final String indexPost(Model model, HttpSession session,
      HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    Errors errors = new Errors();
    String idTipoEstrazione = request.getParameter("idTipoEstrazione");
    errors.validateMandatoryLong(idTipoEstrazione, "idTipoEstrazione");
    model.addAttribute("prfvalues", Boolean.TRUE);

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
      return index(model, session);
    }
    else
    {
      long idUtenteLogin = new Long(getIdUtenteLogin(session));
      List<Long> l = new LinkedList<Long>();
      l.add(idUtenteLogin);
      List<UtenteLogin> utenti = super.loadRuoloDescr(l);
      long idNumeroLotto = estrazioniEJB.caricamentoEstrazioni(
          Long.parseLong(idTipoEstrazione), "Estrazione effettuata da "
              + getUtenteDescrizione(idUtenteLogin, utenti));
      return "redirect:../cunembo219/index_" + idNumeroLotto + ".do";
    }
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
