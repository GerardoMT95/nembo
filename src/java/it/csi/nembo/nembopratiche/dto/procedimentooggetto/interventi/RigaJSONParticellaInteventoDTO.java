package it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class RigaJSONParticellaInteventoDTO
    extends AbstractRigaJSONCatastoInteventoDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 5067674552034245663L;
  private long              idParticellaCertificata;

  public long getIdParticellaCertificata()
  {
    return idParticellaCertificata;
  }

  public void setIdParticellaCertificata(long idParticellaCertificata)
  {
    this.idParticellaCertificata = idParticellaCertificata;
  }
}
