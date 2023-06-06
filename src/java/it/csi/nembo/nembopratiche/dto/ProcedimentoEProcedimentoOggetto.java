package it.csi.nembo.nembopratiche.dto;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;

public class ProcedimentoEProcedimentoOggetto implements ILoggable
{
  /** serialVersionUID */
  private static final long     serialVersionUID = -1624333979225343408L;
  public static final String    REQUEST_NAME     = ProcedimentoEProcedimentoOggetto.class
      .getName();
  protected Procedimento        procedimento;
  protected ProcedimentoOggetto procedimentoOggetto;

  public Procedimento getProcedimento()
  {
    return procedimento;
  }

  public void setProcedimento(Procedimento procedimento)
  {
    this.procedimento = procedimento;
  }

  public ProcedimentoOggetto getProcedimentoOggetto()
  {
    return procedimentoOggetto;
  }

  public void setProcedimentoOggetto(ProcedimentoOggetto procedimentoOggetto)
  {
    this.procedimentoOggetto = procedimentoOggetto;
  }
}
