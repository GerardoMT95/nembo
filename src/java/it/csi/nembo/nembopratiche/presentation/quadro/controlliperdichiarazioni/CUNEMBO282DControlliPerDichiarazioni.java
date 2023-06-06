package it.csi.nembo.nembopratiche.presentation.quadro.controlliperdichiarazioni;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliinlocomisureinvestimento.DatiSpecificiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-282-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo282d")
public class CUNEMBO282DControlliPerDichiarazioni extends BaseController
{
  public static final String BASE_JSP_URL = "controlliperdichiarazioni/";

  @Autowired
  IQuadroEJB                 quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromRequest(request);
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    QuadroOggettoDTO quadro = po
        .findQuadroByCU(NemboConstants.USECASE.CONTROLLI_DICHIARAZIONI);
    BandoDTO bando = quadroEJB
        .getInformazioniBandoByProcedimento(po.getIdProcedimento());
    model.addAttribute("flagEstratta",
        quadroEJB.getFlagEstrattaControlliDichiarazione(po.getIdProcedimento(),
            po.getCodiceRaggruppamento(), idProcedimentoOggetto,
            bando.getCodiceTipoBando()));
    EsitoFinaleDTO esito = quadroEJB.getEsitoFinale(idProcedimentoOggetto,
        quadro.getIdQuadroOggetto());
    DatiSpecificiDTO datiSpecifici = quadroEJB
        .getDatiSpecifici(idProcedimentoOggetto, po.getIdProcedimento());
    model.addAttribute("datiSpecifici", datiSpecifici);
    model.addAttribute("modalitaSelezione",
        getHtmlDecodificaModalitaSelezione(datiSpecifici.getFlagEstratta()));
    model.addAttribute("esito", esito);
    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB, idProcedimentoOggetto,
            quadro.getIdQuadroOggetto(), po.getIdBandoOggetto()));

    return BASE_JSP_URL + "dettaglio";
  }

  public static String getHtmlDecodificaModalitaSelezione(String flagEstratta)
  {
    switch (flagEstratta)
    {
      case NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.CASUALE:
        return NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.DECODIFICA.CASUALE;
      case NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.RISCHIO:
        return NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.DECODIFICA.RISCHIO;
      case NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.MANUALE:
        return NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.DECODIFICA.MANUALE;
      case NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.NON_ESTRATTA:
      case NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.DICHIARAZIONI_SOSTITUTIVE:
        return "";
      default:
        return NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.MODALITA_SELEZIONE.DECODIFICA.SCONOSCIUTA;
    }
  }

}