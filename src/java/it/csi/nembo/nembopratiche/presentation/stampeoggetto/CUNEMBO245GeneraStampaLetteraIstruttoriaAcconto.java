package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-245", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO245GeneraStampaLetteraIstruttoriaAcconto
    extends StampaController
{
  @RequestMapping(value = "/cunembo245/stampa")
  public ResponseEntity<byte[]> stampa(HttpSession session) throws Exception
  {
    return stampaByCUName(getIdProcedimentoOggetto(session),
        CUNEMBO245GeneraStampaLetteraIstruttoriaAcconto.class
            .getAnnotation(NemboSecurity.class).value());
  }
}
