package it.csi.nembo.nembopratiche.presentation.gestionesistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.Link;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.MessaggisticaBaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.annotation.NoLoginRequired;

@Controller
@RequestMapping(value = "/cunembo204")
@NemboSecurity(value = "CU-NEMBO-204", controllo = NemboSecurity.Controllo.DEFAULT)
@NoLoginRequired
public class CUNEMBO204GestioneSistemaController
    extends MessaggisticaBaseController
{
  @Autowired
  private IQuadroEJB quadroEJB;

  @RequestMapping(value = "/index")
  public String index(Model model, HttpSession session,
      HttpServletResponse response) throws InternalUnexpectedException
  {
    List<Link> links = new ArrayList<Link>();
    Map<String, String> mapCdU = quadroEJB.getMapHelpCdu(
        NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.MONITORAGGIO,
        NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.REFRESH_ELENCO_CDU);
    links.add(new Link("../cunembo206/index.do",
        NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.MONITORAGGIO, false,
        "Stato del sistema", mapCdU
            .get(
                NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.MONITORAGGIO)));
    links.add(new Link("refresh_elenco_cdu.do",
        NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.REFRESH_ELENCO_CDU,
        false, "Rilettura Elenco CDU", mapCdU
            .get(
                NemboConstants.USECASE.GESTIONE_SISTEMA_FUNZIONI.REFRESH_ELENCO_CDU),
        "openPageInPopup('../cunembo205/index.do','dlgRefreshCdu','Rilettura Elenco CDU', 'modal-large');return false;"));
    model.addAttribute("links", links);
    return "gestionesistema/elencoUtilita";
  }

}
