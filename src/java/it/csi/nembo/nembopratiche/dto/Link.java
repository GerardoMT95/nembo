package it.csi.nembo.nembopratiche.dto;

public class Link
{
  protected String  link;
  protected String  useCase;
  protected boolean readWrite;
  protected boolean forceVisualization;
  protected String  title;
  protected String  description;
  private String    onclick;

  public Link(String link,
      String useCase,
      boolean readWrite,
      String title,
      String description)
  {
    this.link = link;
    this.useCase = useCase;
    this.readWrite = readWrite;
    this.title = title;
    this.description = description;
  }

  public Link(String link,
      String useCase,
      String title,
      String description)
  {
    this.link = link;
    this.useCase = useCase;
    this.forceVisualization = true;
    this.title = title;
    this.description = description;
  }

  public Link(String link,
      String useCase,
      boolean readWrite,
      String title,
      String description, String onclick)
  {
    this.link = link;
    this.useCase = useCase;
    this.readWrite = readWrite;
    this.title = title;
    this.description = description;
    this.onclick = onclick;
  }

  public String getLink()
  {
    return link;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public String getUseCase()
  {
    return useCase;
  }

  public void setUseCase(String useCase)
  {
    this.useCase = useCase;
  }

  public boolean isReadWrite()
  {
    return readWrite;
  }

  public void setReadWrite(boolean readWrite)
  {
    this.readWrite = readWrite;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getOnclick()
  {
    return onclick;
  }

  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }

  public boolean isForceVisualization()
  {
    return forceVisualization;
  }

  public void setForceVisualization(boolean forceVisualization)
  {
    this.forceVisualization = forceVisualization;
  }

}
