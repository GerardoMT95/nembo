package it.csi.nembo.nembopratiche.presentation.quadro.controlloinlocomisureinvestimento;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.ControlloAmministrativoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.VisitaLuogoExtDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliinlocomisureinvestimento.DatiSpecificiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-213-D", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo213d")
public class CUNEMBO213DControlloinlocoMisureInvestimentoDettaglio
    extends BaseController
{
  public static final String BASE_JSP_URL = "controlliinlocomisureinvestimento/";

  @Autowired
  IQuadroEJB                 quadroEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromRequest(request);
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    List<ControlloAmministrativoDTO> controlliAmministrativi = quadroEJB
        .getControlliAmministrativi(idProcedimentoOggetto,
            NemboConstants.QUADRO.CODICE.CONTROLLI_IN_LOCO_MISURE_INVESTIMENTO,
            null);
    model.addAttribute("controlli", controlliAmministrativi);
    DatiSpecificiDTO datiSpecifici = quadroEJB
        .getDatiSpecifici(idProcedimentoOggetto, po.getIdProcedimento());
    model.addAttribute("datiSpecifici", datiSpecifici);
    model.addAttribute("modalitaSelezione",
        getHtmlDecodificaModalitaSelezione(datiSpecifici.getFlagEstratta()));

    QuadroOggettoDTO quadro = findQuadroCorrente(po);
    final long idQuadroOggetto = quadro.getIdQuadroOggetto();
    List<VisitaLuogoExtDTO> visite = quadroEJB
        .getVisiteLuogo(idProcedimentoOggetto, idQuadroOggetto, null);
    model.addAttribute("visite", visite);
    EsitoFinaleDTO esito = quadroEJB.getEsitoFinale(idProcedimentoOggetto,
        idQuadroOggetto);
    model.addAttribute("esito", esito);

    if (datiSpecifici != null && (datiSpecifici.getFlagControllo() == null
        || NemboConstants.FLAGS.NO.equals(datiSpecifici.getFlagControllo())))
    {
      model.addAttribute("disabilitaControlloLoco", Boolean.TRUE);
    }
    else
    {
      model.addAttribute("disabilitaControlloLoco", Boolean.FALSE);
    }

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