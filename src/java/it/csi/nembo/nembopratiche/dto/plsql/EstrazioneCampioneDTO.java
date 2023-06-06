package it.csi.nembo.nembopratiche.dto.plsql;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class EstrazioneCampioneDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 4601339650061084885L;

  private Long              idProcedimentoOggetto[];

  public Long[] getIdProcedimentoOggetto()
  {
    return idProcedimentoOggetto;
  }

  public void setIdProcedimentoOggetto(Long[] idProcedimentoOggetto)
  {
    this.idProcedimentoOggetto = idProcedimentoOggetto;
  }

}
