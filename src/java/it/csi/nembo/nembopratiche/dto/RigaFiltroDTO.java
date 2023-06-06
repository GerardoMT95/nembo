package it.csi.nembo.nembopratiche.dto;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class RigaFiltroDTO implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 4601339650061084885L;

  private String            id;
  private String            label;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getLabel()
  {
    return label;
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

}
