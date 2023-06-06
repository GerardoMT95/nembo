package it.csi.nembo.nembopratiche.presentation.quadro.esito;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo166v")
@NemboSecurity(value = "CU-NEMBO-166-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO166DettaglioEsitoFinale extends BaseController
{
  @Autowired
  IQuadroEJB  quadroEJB  = null;

  @Autowired
  IRicercaEJB ricercaEJB = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String get(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    QuadroOggettoDTO quadro = po.findQuadroByCU("CU-NEMBO-166-V");

    EsitoFinaleDTO esito = quadroEJB.getEsitoFinale(
        po.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto());

    List<DecodificaDTO<Long>> elencoAtti = quadroEJB.getElencoAtti();
    model.addAttribute("elencoAtti", elencoAtti);

    if ("S".equals(po.getFlagAmmissione())
        && "N".equals(esito.getFlagAltreInfoAtto()))
    {
      DecodificaDTO<String> prot = ricercaEJB.findProtocolloPratica(
          po.getIdProcedimentoOggetto(),
          NemboConstants.NEMBO.ID_CATEGORIA_DOC_LETTERE_NEMBO_SU_DOQUIAGRI);
      esito.setNumeroAtto(prot.getCodice());
      esito.setDataAtto(NemboUtils.DATE.parseDate(prot.getDescrizione()));
    }

    model.addAttribute("esito", esito);
    model.addAttribute("flagAmmisioneS", po.getFlagAmmissione());
    model.addAttribute("attiVisibili", po.getFlagAmmissione());
    return "esitofinale/dettaglioDati";
  }

}