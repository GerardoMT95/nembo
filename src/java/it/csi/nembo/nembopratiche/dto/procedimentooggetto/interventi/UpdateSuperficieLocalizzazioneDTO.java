package it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class UpdateSuperficieLocalizzazioneDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = -4924122118082058465L;
  private long              idConduzioneDichiarata;
  private long              idUtilizzoDichiarato;
  private BigDecimal        superficie;

  public long getIdConduzioneDichiarata()
  {
    return idConduzioneDichiarata;
  }

  public void setIdConduzioneDichiarata(long idConduzioneDichiarata)
  {
    this.idConduzioneDichiarata = idConduzioneDichiarata;
  }

  public long getIdUtilizzoDichiarato()
  {
    return idUtilizzoDichiarato;
  }

  public void setIdUtilizzoDichiarato(long idUtilizzoDichiarato)
  {
    this.idUtilizzoDichiarato = idUtilizzoDichiarato;
  }

  public BigDecimal getSuperficie()
  {
    return superficie;
  }

  public void setSuperficie(BigDecimal superficie)
  {
    this.superficie = superficie;
  }

}
