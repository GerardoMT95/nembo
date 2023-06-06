package it.csi.nembo.nembopratiche.presentation.estrazionicampione;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.NumeroLottoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-217", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping("cunembo217")
public class CUNEMBO217GestioneEstrazioneCampione extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    return "estrazioniacampione/elencoEstrazioniCampione";
  }

  @RequestMapping(value = "getElencoEstrazioniCampioneJson", produces = "application/json")
  @ResponseBody
  public List<NumeroLottoDTO> getElencoProcedimenti(HttpSession session)
      throws InternalUnexpectedException
  {
    List<NumeroLottoDTO> elenco = quadroEjb.getElencoNumeroLotti();
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    return elenco;
  }

  @RequestMapping(value = "getElencoTipologieFiltroEstrCampioneJson", produces = "application/json")
  @ResponseBody
  public List<RigaFiltroDTO> getElencoTipologieFiltroEstrCampioneJson(
      Model model) throws InternalUnexpectedException
  {
    List<RigaFiltroDTO> elenco = quadroEjb
        .getFiltroElencoTipoEstrazioniCampione();
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    return elenco;
  }

  @RequestMapping(value = "getElencoStatoEstrCampione", produces = "application/json")
  @ResponseBody
  public List<RigaFiltroDTO> getElencoStatoEstrCampione(Model model)
      throws InternalUnexpectedException
  {
    List<RigaFiltroDTO> elenco = quadroEjb
        .getFiltroElencoStatoEstrazioniCampione();
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    return elenco;
  }

}
