package it.csi.nembo.nembopratiche.util;

public abstract class CustomTagUtils
{
  public StringBuilder addAttribute(StringBuilder sb, String name, Object value)
  {
    if (value == null)
    {
      value = "";
    }
    else
    {
      if (value instanceof String)
      {
        value = ((String) value).replace("\"", "&quot;");
      }
    }
    sb.append(" ").append(name).append(" = \"").append(value).append("\"");
    return sb;
  }

  public StringBuilder addAttributeIfNotNull(StringBuilder sb, String name,
      Object value)
  {
    if (value == null)
    {
      return sb;
    }
    else
    {
      return addAttribute(sb, name, value);
    }
  }

}
