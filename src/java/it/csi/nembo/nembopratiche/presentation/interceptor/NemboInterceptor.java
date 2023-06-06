package it.csi.nembo.nembopratiche.presentation.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.presentation.interceptor.login.LoginManager;
import it.csi.nembo.nembopratiche.presentation.interceptor.logout.MessaggisticaManager;
import it.csi.nembo.nembopratiche.presentation.interceptor.security.NemboSecurityManager;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;

public class NemboInterceptor implements HandlerInterceptor
{
  public List<BaseManager>      managers = new ArrayList<BaseManager>();
  @Autowired
  IQuadroEJB                    quadroEJB;
  protected static final Logger logger   = Logger
      .getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".presentation");

  public NemboInterceptor()
  {
  }

  @PostConstruct
  public void createManagerList()
  {
    managers.add(new LoginManager());
    managers.add(new MessaggisticaManager()); // Deve essere DOPO il
                                              // LoginManager
    managers.add(new NemboSecurityManager(quadroEJB));
  }

  @Override
  public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex)
      throws Exception
  {
  }

  @Override
  public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception
  {
  }

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception
  {
    final String THIS_METHOD = "preHandle";
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
                                                                                // 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
    request.setAttribute("isPopup",
        BaseManager.findHandlerAnnotation(IsPopup.class, handler) != null);
    if (logger.isDebugEnabled())
    {
      logger.debug("[NemboInterceptor:: " + THIS_METHOD + "] URI = "
          + request.getRequestURI());
    }
    try
    {
      for (BaseManager manager : managers)
      {
        BaseManager.Return managerReturn = manager.validate(request, response,
            handler);
        switch (managerReturn)
        {
          case SKIP_ALL_MANAGER_AND_CONTROLLER: // Sospendo tutta
                                                // l'elaborazione, il manager ha
                                                // già fornito una pagina di
                                                // risposta (es forward,
                                                // redirect...)
            return false;
          case SKIP_MANAGERS_AND_GO_TO_CONTROLLER: // Sospende tutta
                                                   // l'elaborazione, il manager
                                                   // ha rilevato una pagina
                                                   // particolare e non devono
                                                   // essere fatti
            // altri controlli (es una pagina con annotation NoLoginRequired)
            return true;
          case CONTINUE_TO_NEXT_MANAGER:
            // Non c'è nulla da fare in questo caso, tutto è andato bene, si
            // passa al prossimo manager
            break;
        }
      }
    }
    catch (Exception e)
    {
      BaseManager.errorPage("Errore interno",
          "Si è verificato un errore di sistema", request, response);
      return false;
    }
    return true;
  }

}
