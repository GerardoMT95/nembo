package it.csi.nembo.nembopratiche.dto.listeliquidazione;

import java.util.List;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class LivelliBandoDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long    serialVersionUID = 2925334154583939481L;
  private String               denominazioneBando;
  List<DecodificaDTO<Integer>> livelli;

  public List<DecodificaDTO<Integer>> getLivelli()
  {
    return livelli;
  }

  public void setLivelli(List<DecodificaDTO<Integer>> livelli)
  {
    this.livelli = livelli;
  }

  public String getDenominazioneBando()
  {
    return denominazioneBando;
  }

  public void setDenominazioneBando(String denominazioneBando)
  {
    this.denominazioneBando = denominazioneBando;
  }
}
