package it.csi.nembo.nembopratiche.presentation.stampeoggetto;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-310", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUPSR20310StampaComunicazioneIntegrazione extends StampaController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/cunembo310/stampa")
  public ResponseEntity<byte[]> stampa(HttpSession session) throws Exception
  {
    return stampaByCUName(getIdProcedimentoOggetto(session), CUPSR20310StampaComunicazioneIntegrazione.class.getAnnotation(NemboSecurity.class).value());
  }
}
