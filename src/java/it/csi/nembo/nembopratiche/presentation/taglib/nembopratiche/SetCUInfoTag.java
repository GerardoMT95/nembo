package it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class SetCUInfoTag extends SimpleTagSupport
{
  public static final Pattern PATTERN_CU_NUMBER = Pattern
      .compile("CU-NEMBO-([0-9]+)");

  @Override
  public void doTag() throws IOException, JspException
  {
    try
    {
      final PageContext context = (PageContext) this.getJspContext();
      final HttpServletRequest request = (HttpServletRequest) context
          .getRequest();
      final String useCaseController = (String) request
          .getAttribute("useCaseController");
      request.setAttribute("cuNumber",
          NemboUtils.APPLICATION.getCUNumber(useCaseController));
      ProcedimentoOggetto po = NemboFactory.getProcedimentoOggetto(request);
      if (po != null)
      {
        final QuadroOggettoDTO quadroDTO = po.findQuadroByCU(useCaseController);
        if (quadroDTO != null)
        {
          request.setAttribute("cuCodQuadro", quadroDTO.getCodQuadro());
        }
      }
    }
    catch (Exception e)
    {
      BaseTag.logger.error("[CUNumberTag.doEndTag] Exception:", e);
    }
    super.doTag();
  }

}
