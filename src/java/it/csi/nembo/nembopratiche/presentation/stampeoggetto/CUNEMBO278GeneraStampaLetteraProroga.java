package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-278", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO278GeneraStampaLetteraProroga extends StampaController
{
  @RequestMapping(value = "/cunembo278/stampa")
  public ResponseEntity<byte[]> stampa(HttpSession session) throws Exception
  {
    return stampaByCUName(getIdProcedimentoOggetto(session),
        CUNEMBO278GeneraStampaLetteraProroga.class
            .getAnnotation(NemboSecurity.class).value());
  }
}
