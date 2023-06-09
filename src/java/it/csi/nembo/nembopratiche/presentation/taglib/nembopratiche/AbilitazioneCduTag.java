package it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import it.csi.nembo.nembopratiche.dto.ElencoCduDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

public class AbilitazioneCduTag extends BaseTag
{
  /** serialVersionUID */
  private static final long serialVersionUID = 6582461845796172740L;
  private String            codiceCdu;
  private String            extCodAttore;

  @Override
  public int doEndTag() throws JspException
  {
    Boolean abilitazione = validateCdU(extCodAttore, codiceCdu,
        (UtenteAbilitazioni) pageContext.getSession()
            .getAttribute("utenteAbilitazioni"));
    if (abilitazione != null && abilitazione && this.bodyContent != null)
    {
      try
      {
        this.pageContext.getOut().write(this.bodyContent.getString());
      }
      catch (IOException e)
      {
        throw new JspException(e);
      }
    }
    return super.doEndTag();
  }

  public boolean validateCdU(String nomeCdu) throws JspException
  {
    return validateCdU(extCodAttore, codiceCdu, (UtenteAbilitazioni) pageContext
        .getSession().getAttribute("utenteAbilitazioni"));
  }

  public static boolean validateCdU(String codAttore, String nomeCdu,
      UtenteAbilitazioni utenteAbilitazioni)
  {
    ElencoCduDTO cdu = NemboUtils.APPLICATION.getCdu(nomeCdu);
    if (cdu == null)
    {
      return false;
    }
    String tipoAzione = cdu.getTipoAzione();
    if (ElencoCduDTO.TIPO_AZIONE_READ_WRITE.equals(tipoAzione))
    {
      if (NemboUtils.PAPUASERV.isUtenteReadOnly(utenteAbilitazioni))
      {
        return false;
      }
    }
    String extCodMacroCdu = cdu.getExtCodMacroCdu();
    if (extCodMacroCdu != null)
    {
      if (!NemboUtils.PAPUASERV.isMacroCUAbilitato(utenteAbilitazioni,
          extCodMacroCdu))
      {
        return false;
      }

      if (codAttore != null)
      {
        if (!NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
            codAttore))
        {
          return false;
        }
      }
    }
    return true;
  }

  public String getCodiceCdu()
  {
    return codiceCdu;
  }

  public void setCodiceCdu(String codiceCdu)
  {
    this.codiceCdu = codiceCdu;
  }

  public String getExtCodAttore()
  {
    return extCodAttore;
  }

  public void setExtCodAttore(String extCodAttore)
  {
    this.extCodAttore = extCodAttore;
  }

}
