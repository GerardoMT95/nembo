package it.csi.nembo.nembopratiche.presentation.quadro.datiidentificativi;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo103")
@NemboSecurity(value = "CU-NEMBO-103", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO103DatiIdentificativi extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(
        NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO);
    DatiIdentificativi dati = quadroEJB
        .getDatiIdentificativiProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    model.addAttribute("azienda", dati.getAzienda());
    model.addAttribute("rappLegale", dati.getRappLegale());
    model.addAttribute("soggFirmatario", dati.getSoggFirmatario());
    model.addAttribute("datiProcedimento", dati.getDatiProcedimento());
    if (dati.getSettore() != null)
      model.addAttribute("descrizioneSettore",
          dati.getSettore().getDescrizione());

    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEJB,
        procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));
    return "datiidentificativi/dettaglioDati";
  }
}