package it.csi.nembo.nembopratiche.presentation.quadro.interventi.accertamentospese;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import it.csi.nembo.nembopratiche.business.IRendicontazioneEAccertamentoSpeseEJB;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;

public class CUNEMBO193AccertamentoSpeseAbstract extends BaseController
{
  @Autowired
  protected IRendicontazioneEAccertamentoSpeseEJB rendicontazioneEAccertamentoSpeseEJB;
  public static final String                      JSP_BASE_PATH = "interventi/accertamentospese/acconto/finale/";
  public static final String                      CU_BASE_NAME  = "cunembo193";

  public void addInfoRendicontazioneIVA(Model model, long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    String flagRendicontazioneIVA = rendicontazioneEAccertamentoSpeseEJB
        .getInfoSePossibileRendicontazioneConIVAByIdProcedimentoOggetto(
            idProcedimentoOggetto);
    switch (flagRendicontazioneIVA)
    {
      case NemboConstants.FLAGS.NO:
        model.addAttribute("msgIVA",
            "Il beneficiario NON può rendicontare l'IVA");
        break;
      case NemboConstants.FLAGS.SI:
        model.addAttribute("msgIVA", "Il beneficiario può rendicontare l'IVA");
        break;
    }
  }
}
