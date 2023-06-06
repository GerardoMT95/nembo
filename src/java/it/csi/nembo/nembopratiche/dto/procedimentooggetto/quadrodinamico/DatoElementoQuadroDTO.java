package it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class DatoElementoQuadroDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = -2697954292839006325L;
  protected long            idElementoQuadro;
  protected String          valoreElemento;

  public String getValoreElemento()
  {
    return valoreElemento;
  }

  public void setValoreElemento(String valoreElemento)
  {
    this.valoreElemento = valoreElemento;
  }

  public long getIdElementoQuadro()
  {
    return idElementoQuadro;
  }

  public void setIdElementoQuadro(long idElementoQuadro)
  {
    this.idElementoQuadro = idElementoQuadro;
  }
}
