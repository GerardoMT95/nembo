package it.csi.nembo.nembopratiche.presentation.quadro.punteggi;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainPunteggioDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-162", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo162")
public class CUNEMBO162CalcolaPunti extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEJB;

  @RequestMapping("/index")
  public final String calcola(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    final Procedimento procedimento = getProcedimentoFromSession(session);
    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    final TestataProcedimento testataProcedimento = (TestataProcedimento) session
        .getAttribute(TestataProcedimento.SESSION_NAME);
    final Long idBando = procedimento.getIdBando();
    final LogOperationOggettoQuadroDTO logOperationOggettoQuadro = getLogOperationOggettoQuadroDTO(
        session);
    final List<LivelloDTO> livelli = this.quadroEJB
        .getLivelliProcOggetto(procedimento.getIdProcedimento());
    Long idLivelloRef = null;
    if (livelli != null && livelli.size() == 1)
    {
      idLivelloRef = livelli.get(0).getIdLivello();
    }

    final MainPunteggioDTO punteggiDTO = this.quadroEJB.callMainCalcoloPunteggi(
        logOperationOggettoQuadro, procedimento.getIdProcedimento(),
        procedimentoOggetto.getIdProcedimentoOggetto(),
        testataProcedimento.getIdAzienda(), idBando, idLivelloRef);

    final int risultato = punteggiDTO.getRisultato();
    final String messaggio = punteggiDTO.getMessaggio();
    final String avanti = ". Premere \"Avanti\" per visualizzare il risultato";
    if (risultato == NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO)
    {
      model.addAttribute("msgErrore",
          "Si è verificato un errore di sistema. Contattare l'assistenza comunicando il seguente messaggio: "
              + messaggio + avanti);
    }
    else
      if (risultato == NemboConstants.SQL.RESULT_CODE.ERRORE_GRAVE)
      {
        model.addAttribute("msgErrore",
            "Si è verificato il seguente errore bloccante: " + messaggio
                + avanti);
      }
      else
        if (risultato == NemboConstants.SQL.RESULT_CODE.ERRORE_NON_BLOCCANTE)
        {
          model.addAttribute("msgErrore",
              "Elaborazione terminata correttamente con la seguente segnalazione: "
                  + messaggio + avanti);
        }
        else
        {
          model.addAttribute("msgInfo",
              "Elaborazione terminata correttamente; premere \"Avanti\" per visualizzare il risultato");
        }

    return "punteggi/esitoCalcoloPunteggi";
  }

}
