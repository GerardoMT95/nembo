package it.csi.nembo.nembopratiche.presentation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/logout")
@NemboSecurity(value = "", controllo = NemboSecurity.Controllo.NESSUNO)
public class LogoutController extends BaseController
{
  @RequestMapping("conferma")
  public String conferma(Model model, HttpServletRequest request)
  {
    Cookie[] cookies = request.getCookies();
    if (cookies != null)
    {
      for (Cookie cookie : cookies)
      {
        if ("NEMBOPRATICHE_LOGIN_PORTAL".equals(cookie.getName()))
        {
          if (NemboConstants.PORTAL.PUBBLICA_AMMINISTRAZIONE
              .equals(cookie.getValue()))
          {
            model.addAttribute("shibbolethLogoutUrl",
                "/sp_liv1_WRUP/Shibboleth.sso/Logout");
          }
          else
          {
            if (NemboConstants.PORTAL.SPID.equals(cookie.getValue()))
            {
              model.addAttribute("shibbolethLogoutUrl",
                  "/ssp_liv2_spid_GASP_REGIONE/logout.do");
            }
            else
            {
              model.addAttribute("shibbolethLogoutUrl",
                  "/liv1/Shibboleth.sso/Logout");
            }
          }
          break;
        }
      }
    }
    model.addAttribute("webContext", NemboConstants.NEMBOPRATICHE.WEB_CONTEXT);
    return "logout/conferma";
  }

  @RequestMapping("esegui")
  @ResponseBody
  public String esegui(HttpSession session)
  {
    session.invalidate();
    return "<success>true</success>";
  }
}
