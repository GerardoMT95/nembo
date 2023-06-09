package it.csi.nembo.nembopratiche.dto.estrazionecampione;

import java.math.BigDecimal;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ImportiAttualiPrecedentiDTO implements ILoggable
{

  private static final long         serialVersionUID = 5918223009553272325L;

  private List<DettaglioImportoDTO> elencoImporti;
  private List<DettaglioImportoDTO> elencoImportiMisure;
  private BigDecimal                totaleImportiEstratti;
  private BigDecimal                totaleImportiEstrattiCasuale;
  private BigDecimal                totaleAnalisiDelRischio;

  private BigDecimal                percTotaleImportiEstratti;
  private BigDecimal                percTotaleImportiEstrattiCasuale;
  private BigDecimal                percTotaleAnalisiDelRischio;

  public List<DettaglioImportoDTO> getElencoImportiMisure()
  {
    return elencoImportiMisure;
  }

  public void setElencoImportiMisure(
      List<DettaglioImportoDTO> elencoImportiMisure)
  {
    this.elencoImportiMisure = elencoImportiMisure;
  }

  public List<DettaglioImportoDTO> getElencoImporti()
  {
    return elencoImporti;
  }

  public void setElencoImporti(List<DettaglioImportoDTO> elencoImporti)
  {
    this.elencoImporti = elencoImporti;
  }

  public BigDecimal getTotaleImportiEstratti()
  {
    return totaleImportiEstratti;
  }

  public void setTotaleImportiEstratti(BigDecimal totaleImportiEstratti)
  {
    this.totaleImportiEstratti = totaleImportiEstratti;
  }

  public BigDecimal getTotaleImportiEstrattiCasuale()
  {
    return totaleImportiEstrattiCasuale;
  }

  public void setTotaleImportiEstrattiCasuale(
      BigDecimal totaleImportiEstrattiCasuale)
  {
    this.totaleImportiEstrattiCasuale = totaleImportiEstrattiCasuale;
  }

  public BigDecimal getTotaleAnalisiDelRischio()
  {
    return totaleAnalisiDelRischio;
  }

  public void setTotaleAnalisiDelRischio(BigDecimal totaleAnalisiDelRischio)
  {
    this.totaleAnalisiDelRischio = totaleAnalisiDelRischio;
  }

  public String getTotaleImportiEstrattiStr()
  {
    if (totaleImportiEstratti == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT.formatCurrency(totaleImportiEstratti);
  }

  public String getTotaleImportiEstrattiCasualeStr()
  {
    if (totaleImportiEstrattiCasuale == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT.formatCurrency(totaleImportiEstrattiCasuale);
  }

  public String getTotaleAnalisiDelRischioStr()
  {
    if (totaleAnalisiDelRischio == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT.formatCurrency(totaleAnalisiDelRischio);
  }

  public BigDecimal getPercTotaleImportiEstratti()
  {
    return percTotaleImportiEstratti;
  }

  public void setPercTotaleImportiEstratti(BigDecimal percTotaleImportiEstratti)
  {
    this.percTotaleImportiEstratti = percTotaleImportiEstratti;
  }

  public BigDecimal getPercTotaleImportiEstrattiCasuale()
  {
    return percTotaleImportiEstrattiCasuale;
  }

  public void setPercTotaleImportiEstrattiCasuale(
      BigDecimal percTotaleImportiEstrattiCasuale)
  {
    this.percTotaleImportiEstrattiCasuale = percTotaleImportiEstrattiCasuale;
  }

  public BigDecimal getPercTotaleAnalisiDelRischio()
  {
    return percTotaleAnalisiDelRischio;
  }

  public void setPercTotaleAnalisiDelRischio(
      BigDecimal percTotaleAnalisiDelRischio)
  {
    this.percTotaleAnalisiDelRischio = percTotaleAnalisiDelRischio;
  }

  public void calcPercentualiTotali(ImportiTotaliDTO totali)
  {
    percTotaleImportiEstratti = (NemboUtils.NUMBERS
        .nvl(totaleImportiEstratti).multiply(new BigDecimal(100)))
            .divide(
                NemboUtils.NUMBERS.initNumberNvlZero(
                    totali.getImpTotaleComplessivo(), BigDecimal.ONE),
                2, BigDecimal.ROUND_HALF_UP);
    percTotaleImportiEstrattiCasuale = (NemboUtils.NUMBERS
        .nvl(totaleImportiEstrattiCasuale).multiply(new BigDecimal(100)))
            .divide(NemboUtils.NUMBERS
                .initNumberNvlZero(totaleImportiEstratti, BigDecimal.ONE), 2,
                BigDecimal.ROUND_HALF_UP);
    if (percTotaleImportiEstrattiCasuale.compareTo(BigDecimal.ZERO) == 0)
    {
      percTotaleAnalisiDelRischio = BigDecimal.ZERO;
    }
    else
    {
      percTotaleAnalisiDelRischio = (new BigDecimal("100")).subtract(
          NemboUtils.NUMBERS.nvl(percTotaleImportiEstrattiCasuale));
    }

    if (elencoImporti != null)
    {
      for (DettaglioImportoDTO imp : elencoImporti)
      {
        imp.setImportoTotaleRichiestoComplessivo(
            totali.getImpTotaleComplessivo());
      }
    }

    if (elencoImportiMisure != null)
    {
      for (DettaglioImportoDTO imp : elencoImportiMisure)
      {
        imp.setImportoTotaleRichiestoComplessivo(
            totali.getImpTotaleComplessivo());
      }
    }

  }

  public String getPercTotaleAnalisiDelRischioStr()
  {
    if (percTotaleAnalisiDelRischio == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT.formatCurrency(percTotaleAnalisiDelRischio);
  }

  public String getPercTotaleImportiEstrattiCasualeStr()
  {
    if (percTotaleImportiEstrattiCasuale == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT
        .formatCurrency(percTotaleImportiEstrattiCasuale);
  }

  public String getPercTotaleImportiEstrattiStr()
  {
    if (percTotaleImportiEstratti == null)
    {
      return NemboUtils.FORMAT.formatCurrency(BigDecimal.ZERO);
    }
    return NemboUtils.FORMAT.formatCurrency(percTotaleImportiEstratti);
  }

}
