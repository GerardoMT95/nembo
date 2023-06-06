package it.csi.nembo.nembopratiche.presentation.operazioniprocedimento;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.ProspettoEconomicoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-254-L", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping(value = "/cunembo254l")
public class CUNEMBO254ProspettoEconomico extends BaseController
{
  @Autowired
  private IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index")
  public String index(Model model, HttpSession session)
      throws InternalUnexpectedException, ApplicationException
  {
    Procedimento procedimento = getProcedimentoFromSession(session);
    BandoDTO bando = quadroEJB.getInformazioniBando(procedimento.getIdBando());

    if (bando.getCodiceTipoBando().compareTo("I") != 0
        && bando.getCodiceTipoBando().compareTo("G") != 0)
    {
      // bando a premio - non abilitato
      model.addAttribute("errore",
          "Funzionalità disponibile solo per bandi di misure ad investimento.");
    }
    else
    {
      List<ProspettoEconomicoDTO> prospetto = quadroEJB
          .getProspettoEconomico(procedimento.getIdProcedimento());
      if (prospetto == null || prospetto.isEmpty())
        model.addAttribute("pagamentiNonPresenti",
            "Non sono presenti pagamenti approvati dall'ente istruttore in associazione al procedimento selezionato.");
      else
        model.addAttribute("prospetto", prospetto);
    }

    return "operazioniprocedimento/prospettoEconomico";
  }

}
