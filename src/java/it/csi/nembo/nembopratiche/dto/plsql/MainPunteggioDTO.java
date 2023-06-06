package it.csi.nembo.nembopratiche.dto.plsql;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class MainPunteggioDTO implements ILoggable
{
  private static final long serialVersionUID = 4174010140204588163L;

  private int               risultato;
  private String            messaggio;

  /**
   * @return the risultato
   */
  public final int getRisultato()
  {
    return risultato;
  }

  /**
   * @param risultato
   *          the risultato to set
   */
  public final void setRisultato(int risultato)
  {
    this.risultato = risultato;
  }

  /**
   * @return the messaggio
   */
  public final String getMessaggio()
  {
    return messaggio;
  }

  /**
   * @param messaggio
   *          the messaggio to set
   */
  public final void setMessaggio(String messaggio)
  {
    this.messaggio = messaggio;
  }

  /**
   * @return the serialversionuid
   */
  public static final long getSerialversionuid()
  {
    return serialVersionUID;
  }

}
