package it.csi.nembo.nembopratiche.dto;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class EsitoDTO implements ILoggable
{

  /**
   * 
   */
  private static final long serialVersionUID = 6035364196001956453L;

  private long              idEsito;
  private String            descrizione;


  public long getIdEsito()
  {
    return idEsito;
  }

  public void setIdEsito(long idEsito)
  {
    this.idEsito = idEsito;
  }

  public String getDescrizione()
  {
    return descrizione;
  }

  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }

}
