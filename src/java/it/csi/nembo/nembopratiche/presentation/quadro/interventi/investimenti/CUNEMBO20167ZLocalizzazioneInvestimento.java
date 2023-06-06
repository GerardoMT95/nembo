package it.csi.nembo.nembopratiche.presentation.quadro.interventi.investimenti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Localizza;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO20-167-Z", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cuNEMBO20167z")
public class CUNEMBO20167ZLocalizzazioneInvestimento extends Localizza
{
  @Override
  public String getFlagEscludiCatalogo()
  {
    return NemboConstants.FLAGS.NO;
  }

}
