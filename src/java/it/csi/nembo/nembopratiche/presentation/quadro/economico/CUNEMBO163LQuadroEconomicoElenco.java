package it.csi.nembo.nembopratiche.presentation.quadro.economico;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RigaJSONInterventoQuadroEconomicoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.ZonaAltimetricaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.AbilitazioneAzioneTag;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-163-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo163l")
public class CUNEMBO163LQuadroEconomicoElenco extends BaseController
{
  @Autowired
  IInterventiEJB interventiEJB;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
	  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
	  ZonaAltimetricaDTO zonaAltimetrica = interventiEJB.getZonaAltimetricaProcedimento(idProcedimentoOggetto, getUtenteAbilitazioni(session).getIdProcedimento());
	  model.addAttribute("zonaAltimetrica",zonaAltimetrica);
	  return "quadroeconomico/elenco";
  }

  @RequestMapping(value = "/json/elenco", produces = "application/json")
  @ResponseBody
  public List<RigaJSONInterventoQuadroEconomicoDTO> elenco_json(Model model,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    HttpSession session = request.getSession();
    List<RigaJSONInterventoQuadroEconomicoDTO> interventi = interventiEJB
        .getElencoInterventiQuadroEconomico(getIdProcedimentoOggetto(session),
            getDataValiditaProcOggetto(session));
    boolean isModifica = AbilitazioneAzioneTag.validate(
        NemboConstants.QUADRO.CODICE.QUADRO_ECONOMICO,
        NemboConstants.AZIONE.CODICE.MODIFICA,
        getProcedimentoOggettoFromSession(session),
        getUtenteAbilitazioni(session), request);
    StringBuilder sb = new StringBuilder();
    for (RigaJSONInterventoQuadroEconomicoDTO intervento : interventi)
    {
      sb.setLength(0);
      if (!NemboConstants.INTERVENTI.TIPO_OPERAZIONE_ELIMINAZIONE
          .equals(intervento.getFlagTipoOperazione()))
      {
        // Per semplificare al js il parsing uso SEMPRE 2 caratteri per indicare
        // l'icona a causa della localizzazione in cui devo specificare anche il
        // tipo di
        // localizzazione
        if (isModifica)
        {
          sb.append("MO");
        }
      }
      intervento.setIcone(sb.toString());
    }
    return interventi;
  }
}