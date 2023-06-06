package it.csi.nembo.nembopratiche.presentation.estrazionicampione;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IEstrazioniEJB;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-222", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping("cunembo222")
public class CUNEMBO222EliminaEstrazioneCampione extends BaseController
{

  @Autowired
  private IEstrazioniEJB estrazioniEjb;

  @RequestMapping(value = "/index_{idNumeroLotto}_{idStatoEstrazione}")
  public String index(Model model, HttpSession session,
      @PathVariable(value = "idNumeroLotto") long idNumeroLotto,
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
    return "estrazioniacampione/confermaElimina";
  }

  @RequestMapping(value = "/index_{idNumeroLotto}_{idStatoEstrazione}", method = RequestMethod.POST)
  public String indexPost(Model model, HttpSession session,
      @PathVariable(value = "idNumeroLotto") long idNumeroLotto,
      @PathVariable(value = "idStatoEstrazione") long idStatoEstrazione)
      throws InternalUnexpectedException
  {
    estrazioniEjb.eliminaEstrazioni(idNumeroLotto);
    return "redirect:../cunembo217/index.do";
  }

}
