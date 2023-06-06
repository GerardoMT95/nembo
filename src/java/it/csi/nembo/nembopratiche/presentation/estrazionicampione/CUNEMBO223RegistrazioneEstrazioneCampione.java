package it.csi.nembo.nembopratiche.presentation.estrazionicampione;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IEstrazioniEJB;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-223", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping("cunembo223")
public class CUNEMBO223RegistrazioneEstrazioneCampione extends BaseController
{

  @Autowired
  private IEstrazioniEJB estrazioniEjb;

  @RequestMapping(value = "/registra_{idNumeroLotto}_{idStatoEstrazione}_{idTipoEstrazione}")
  public String index(Model model, HttpSession session,
      @PathVariable(value = "idNumeroLotto") long idNumeroLotto,
      @PathVariable(value = "idTipoEstrazione") long idTipoEstrazione,
      @PathVariable(value = "idStatoEstrazione") long idStatoEstrazione)
      throws InternalUnexpectedException
  {
    if (idStatoEstrazione != NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.STATO_ESTRAZIONE.CARICATA
        &&
        idStatoEstrazione != NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.STATO_ESTRAZIONE.ESTRATTA)
    {
      model.addAttribute("messaggio",
          "Operazione non consentita: l'estrazione a campione deve trovarsi nello stato CARICATA o ESTRATTA");
      return "errore/messaggio";
    }

    model.addAttribute("idNumeroLotto", idNumeroLotto);
    model.addAttribute("idStatoEstrazione", idStatoEstrazione);
    model.addAttribute("idTipoEstrazione", idTipoEstrazione);
    return "estrazioniacampione/confermaEstrazione";
  }

  @RequestMapping(value = "/attendere")
  public String attendere(Model model) throws InternalUnexpectedException
  {
    model.addAttribute("messaggio",
        "Attendere prego, il sistema sta effettuando l'operazione di registrazione dei dati dell'estrazione a campione; l'operazione potrebbe richiedere alcuni secondi...");
    return "estrazioniacampione/attenderePrego";
  }

  @RequestMapping(value = "/esegui_registra_{idNumeroLotto}_{idTipoEstrazione}")
  @ResponseBody
  public String esegui_registra(Model model,
      @PathVariable(value = "idNumeroLotto") long idNumeroLotto,
      @PathVariable(value = "idTipoEstrazione") long idTipoEstrazione)
      throws InternalUnexpectedException
  {
    MainControlloDTO result = estrazioniEjb.callRegistraDP(idNumeroLotto,
        idTipoEstrazione);
    switch (result.getRisultato())
    {
      case NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE:
        return "attendere prego...";
      default:
        return "<br /><div class=\"alert alert-danger\">Errore durante l'elaborazione: "
            + result.getMessaggio() + "</div>";
    }
  }

}
