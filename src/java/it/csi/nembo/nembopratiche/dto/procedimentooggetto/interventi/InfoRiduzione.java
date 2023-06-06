package it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class InfoRiduzione implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 177132428417097260L;
  protected BigDecimal      percentuale;
  protected BigDecimal      totaleImporto;
  protected BigDecimal      totaleRichiesto;

  public BigDecimal getPercentuale()
  {
    return percentuale;
  }

  public void setPercentuale(BigDecimal percentuale)
  {
    this.percentuale = percentuale;
  }

  public BigDecimal getTotaleImporto()
  {
    return totaleImporto;
  }

  public void setTotaleImporto(BigDecimal totaleImporto)
  {
    this.totaleImporto = totaleImporto;
  }

  public BigDecimal getTotaleRichiesto()
  {
    return totaleRichiesto;
  }

  public void setTotaleRichiesto(BigDecimal totaleRichiesto)
  {
    this.totaleRichiesto = totaleRichiesto;
  }

  public String getTotaleRichiestoEuro()
  {
    return NemboUtils.FORMAT.formatDecimal2(getTotaleRichiesto())
        + "  &euro;";
  }
}
