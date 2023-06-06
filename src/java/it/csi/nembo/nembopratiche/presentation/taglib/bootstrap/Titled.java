package it.csi.nembo.nembopratiche.presentation.taglib.bootstrap;

import it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.BaseTag;

/**
 * 
 * @author Stefano Einaudi (70399)
 *
 *         Classe che implementa un tag generico con Titolo (Utilizzata come
 *         base per permettere al tag Title di essere utilizzato in più di un
 *         tag, basta che quest'ultimo derivi da questa classe
 */
public abstract class Titled extends BaseTag
{
  /** serialVersionUID */
  private static final long serialVersionUID = -4119385161046230600L;
  protected String          title;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }
}
