package it.csi.nembo.nembopratiche.presentation.messaggistica;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.presentation.MessaggisticaBaseController;
import it.csi.nembo.nembopratiche.presentation.interceptor.logout.MessaggisticaManager;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping(value = "/cunembo203")
@NemboSecurity(value = "CU-NEMBO-203", controllo = NemboSecurity.Controllo.DEFAULT)
public class CUNEMBO203MessaggisticaInTestata
    extends MessaggisticaBaseController
{
  @RequestMapping(value = "/turnoff")
  @SuppressWarnings("unchecked")
  @ResponseBody
  public Map<String, Object> turnoff(HttpSession session)
  {
    final String THIS_METHOD = "[MessaggisticaController::turnoff]";
    try
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " BEGIN.");
      }
      /*
       * La mappa "messaggistica" è mantenuta aggiornata in sessione dal
       * NemboInterceptor (nello specifico dal LogoutManager richiamato dal
       * NemboInterceptor) ogni qual volta si accede a una pagina (ovviamente
       * le chiamate ai servizi vengono eseguite soltanto ogni X minuti per non
       * sovraccaricare il sistema)
       */
      Map<String, Object> mapMessaggistica = (Map<String, Object>) session
          .getAttribute("messaggistica");
      mapMessaggistica.put(MessaggisticaManager.KEY_DISABLED_UNTIL_NEXT_REFRESH,
          Boolean.TRUE);
      return mapMessaggistica;
    }
    finally
    {
      logger.debug(THIS_METHOD + " END.");
    }
  }
}
