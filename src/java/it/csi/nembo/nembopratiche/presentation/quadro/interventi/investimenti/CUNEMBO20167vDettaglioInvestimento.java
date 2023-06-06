package it.csi.nembo.nembopratiche.presentation.quadro.interventi.investimenti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Dettaglio;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO20-167-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cuNEMBO20167v")
public class CUNEMBO20167vDettaglioInvestimento extends Dettaglio
{
  @Override
  public String getFlagEscludiCatalogo()
  {
    return NemboConstants.FLAGS.SI;
  }


}
