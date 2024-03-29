package it.csi.nembo.nembopratiche.util.stampa.placeholder;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.SegnapostoDTO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class PlaceHolderManager
{
  public static final String                                 PLACEHOLDER_NON_VALORIZZATO = "_______";
  public static final Pattern                                PLACEHOLDER_REGEXP          = Pattern.compile("(\\$\\$[a-zA-Z0-9_]+)");
  protected static final Logger                              logger                      = Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".presentation");
  protected static final IQuadroEJB                          quadroEJB                   = getEjbQuadro();

  /**
   * elabora il testo sostituendo i placeholder. Il parametro mapCache DEVE essere valorizzato e contenere al minimo il ProcedimentoOggetto
   */
  public static String process(String text, Map<String, Object> mapCache) throws Exception
  {
    if (text != null)
    {
      Matcher matcher = PLACEHOLDER_REGEXP.matcher(text);
      ProcedimentoOggetto po = (ProcedimentoOggetto) mapCache.get(ProcedimentoOggetto.REQUEST_NAME);
      final long idProcedimento = po.getIdProcedimento();
      final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
      while (matcher.find())
      {
        String placeHolder = matcher.group(1);
        String value =   mapCache.get(placeHolder);
        if (value == null)
        {
          SegnapostoDTO segnapostoDTO = NemboUtils.APPLICATION.getSegnaposto(placeHolder);
          if (segnapostoDTO != null)
          {
            value = quadroEJB.getValoreSegnaposto(segnapostoDTO, idProcedimento, idProcedimentoOggetto);
            if (value != null)
            {
              mapCache.put(placeHolder, value);
            }
          }
        }
        if (value != null)
        {
          text = text.replace(placeHolder, value);
        }
      }
    }
    return text;
  }
  

  private static IQuadroEJB getEjbQuadro()
  {
    try
    {
      return NemboUtils.APPLICATION.getEjbQuadro();
    }
    catch (NamingException e)
    {
      logger.error("[PlaceHolderManager.getEjbQuadro] exception in lookup", e);
      return null;
    }
  }
}
