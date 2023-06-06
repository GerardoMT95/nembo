package it.csi.nembo.nembopratiche.util.pdf;

public interface IValuableElement
{
  public enum ValuableElementType
  {
    TEXT, RECTANGLE, CURVED_RECTANGLE;
  }

  public ValuableElementType getType();
}
