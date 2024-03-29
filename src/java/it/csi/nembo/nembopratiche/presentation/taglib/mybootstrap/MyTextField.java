package it.csi.nembo.nembopratiche.presentation.taglib.mybootstrap;

import java.math.BigDecimal;
import java.util.Date;

import it.csi.nembo.nembopratiche.util.NemboUtils;

public class MyTextField extends MyInputGroup
{
  /** serialVersionUID */
  private static final long serialVersionUID = 5183034810591718296L;
  protected String          value;
  protected String          placeholder;
  protected Integer         maxlength;
  protected String          type;
  protected Boolean         preferRequestValues;
  protected boolean         visible          = true;

  @Override
  public void writeCustomTag(StringBuilder sb, String errorMessage,
      boolean wrappedInAGroup) throws Exception
  {

    if ("date".equalsIgnoreCase(type))
    {
      sb.append("<div class=\"input-group\">");
    }

    sb.append("<input");
    addAttribute(sb, "type", "text");
    addBaseAttributes(sb);
    if ("date".equalsIgnoreCase(type))
    {
      addAttribute(sb, "data-date-picker", true);

    }
    String textValue = value;
    if (preferRequestValues != null && preferRequestValues.booleanValue())
    {
      textValue = this.pageContext.getRequest().getParameter(name);
    }
    addAttributeIfNotNull(sb, "value", textValue);
    addAttributeIfNotNull(sb, "placeholder", escapeHtml(placeholder));
    addAttributeIfNotNull(sb, "maxlength", maxlength);
    if (!visible)
    {
      addAttribute(sb, "style", "visibility:hidden");
    }
    sb.append("/>");

    if ("date".equalsIgnoreCase(type))
    {
      this.addAddOn(true, null);
      this.addAddOn(false, " <label for=\"" + id
          + "\" class=\"input-group-addon btn\"><i class=\"icon-large icon-calendar\"></i></label></div>");
      setIsDate(true);
    }
  }

  @Override
  protected void addCssClassAttribute(StringBuilder sb, boolean errror)
  {
    String s = "form-control";
    if ("date".equalsIgnoreCase(type))
      s = s.concat(" datepicker");

    addAttribute(sb, "class", NemboUtils.STRING.concat(" ", cssClass, s));
  }

  public Object getValue()
  {
    return value;
  }

  public void setValue(Object value)
  {
    if (value == null)
    {
      this.value = null;
    }
    else
    {
      if (value instanceof String)
      {
        this.value =   value;
      }
      else
      {
        if (value instanceof java.math.BigDecimal)
        {

          final BigDecimal bigDecimal = (BigDecimal) value;
          this.value = NemboUtils.FORMAT.formatGenericNumber(bigDecimal,
              bigDecimal.scale(), true, false);
        }
        else
        {
          if (value instanceof java.util.Date)
          {

            final Date date =   value;
            this.value = NemboUtils.DATE.formatDate(date);
          }
          else
          {
            this.value = value.toString();
          }
        }
      }
    }

  }

  public String getPlaceholder()
  {
    return placeholder;
  }

  public void setPlaceholder(String placeholder)
  {
    this.placeholder = placeholder;
  }

  public Integer getMaxlength()
  {
    return maxlength;
  }

  public void setMaxlength(Integer maxlength)
  {
    this.maxlength = maxlength;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
    if ("euro".equalsIgnoreCase(type))
    {
      this.addAddOn(true, null);
      this.addAddOn(false, MyInputAddOn.getAddOnClass("&euro;"));
    }
    else
    {
      /*
       * if ("date".equalsIgnoreCase(type)) { this.addAddOn(true, null);
       * this.addAddOn(false, " <label for=\""+id+
       * "\" class=\"input-group-addon btn\"><i class=\"icon-large icon-calendar\"></i></label>"
       * ); }
       */
    }
  }

  public Boolean getPreferRequestValues()
  {
    return preferRequestValues;
  }

  public void setPreferRequestValues(Boolean preferRequestValues)
  {
    this.preferRequestValues = preferRequestValues;
  }

  public boolean isVisible()
  {
    return visible;
  }

  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }

}