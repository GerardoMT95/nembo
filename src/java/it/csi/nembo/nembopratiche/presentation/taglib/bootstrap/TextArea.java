package it.csi.nembo.nembopratiche.presentation.taglib.bootstrap;

import it.csi.nembo.nembopratiche.util.NemboUtils;

public class TextArea extends SimpleBoostrapTag
{
  /** serialVersionUID */
  private static final long serialVersionUID = -4767738141456047356L;
  private Integer           cols;
  private Integer           rows;

  @Override
  protected void writeCustomTagElement(StringBuilder sb)
  {
    sb.append("<textarea");
    writeDefaultAttributes(sb);
    if (rows != null)
    {
      writeAttribute(sb, "rows", rows.toString());
    }
    if (cols != null)
    {
      writeAttribute(sb, "cols", cols.toString());
    }
    sb.append(">");
    if (this.bodyContent != null)
    {
      sb.append(
          escapeHtml(NemboUtils.STRING.nvl(this.bodyContent.getString())));
    }
    sb.append("</textarea>");
  }

  public Integer getRows()
  {
    return rows;
  }

  public void setRows(Integer rows)
  {
    this.rows = rows;
  }

  public Integer getCols()
  {
    return cols;
  }

  public void setCols(Integer cols)
  {
    this.cols = cols;
  }
}
