package it.csi.nembo.nembopratiche.presentation.quadro.interventi.rendicontazionespese;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import it.csi.nembo.nembopratiche.business.IRendicontazioneEAccertamentoSpeseEJB;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.validator.Errors;

public class CUNEMBO192RendicontazioneSpeseAbstract extends BaseController
{
  @Autowired
  protected IRendicontazioneEAccertamentoSpeseEJB rendicontazioneEAccertamentoSpeseEJB;
  public static final String                      JSP_BASE_URL = "interventi/rendicontazionespese/acconto/finale/";
  public static final String                      CU_BASE_NAME = "cunembo192";

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
            "Il beneficiario NON pu� rendicontare l'IVA");
        break;
      case NemboConstants.FLAGS.SI:
        model.addAttribute("msgIVA", "Il beneficiario pu� rendicontare l'IVA");
        break;
    }
  }

  protected void verificaSuperamentoPercentuale(HttpServletRequest request,
      HttpSession session, Errors errors,
      Map<String, BigDecimal> mapTotaliContributoRichiestoPerOperazione,
      List<Long> ids)
      throws InternalUnexpectedException, ApplicationException
  {
    Map<String, BigDecimal[]> importi = rendicontazioneEAccertamentoSpeseEJB
        .getCalcoloImportiPerRendicontazioneSpese(
            getIdProcedimentoOggetto(session), null, ids);

    BigDecimal percentualeMassima = rendicontazioneEAccertamentoSpeseEJB
        .getPercentualeMassimaContributoInRendicontazioneSpese(
            getIdProcedimento(session),
            getProcedimentoOggettoFromRequest(request).getIdBandoOggetto());
    StringBuilder sb = new StringBuilder();
    for (String operazione : mapTotaliContributoRichiestoPerOperazione.keySet())
    {
      BigDecimal[] importiOperazione = importi.get(operazione);
      BigDecimal importoContributo = importiOperazione[0];
      BigDecimal contributoRichiesto = importiOperazione[1];
      BigDecimal maxImporto = importoContributo.multiply(percentualeMassima)
          .scaleByPowerOfTen(-2).setScale(2,
              RoundingMode.DOWN);
      if (maxImporto
          .subtract(contributoRichiesto, MathContext.DECIMAL128)
          .subtract(mapTotaliContributoRichiestoPerOperazione.get(operazione),
              MathContext.DECIMAL128)
          .compareTo(BigDecimal.ZERO) < 0)
      {
        if (sb.length() > 0)
        {
          sb.append("<br/>");
        }
        sb.append("Per l'operazione ").append(operazione)
            .append(" si sta sforando il limite del ")
            .append(NemboUtils.FORMAT.formatDecimal2(percentualeMassima))
            .append(
                "% del contributo ammesso in fase di istruttoria della domanda di sostegno");
      }
    }
    if (sb.length() > 0)
    {
      errors.addError("error", sb.toString());
    }
  }

}
