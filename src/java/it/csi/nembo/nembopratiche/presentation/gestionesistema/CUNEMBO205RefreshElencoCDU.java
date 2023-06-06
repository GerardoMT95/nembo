package it.csi.nembo.nembopratiche.presentation.gestionesistema;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-205", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping(value = "/cunembo205")
public class CUNEMBO205RefreshElencoCDU extends BaseController
{
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  @IsPopup
  public String index(Model model, HttpSession session,
      HttpServletResponse response)
  {
    setModelDialogWarning(model,
        "Stai cercando di aggiornare l'elenco CDU, vuoi continuare ?",
        "../cunembo205/confermaaggiorna.do");
    return "dialog/conferma";
  }

  @RequestMapping(value = "confermaaggiorna", method = RequestMethod.POST)
  public String confermaaggiorna(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    try
    {
      // aggiorno l'elenco in APPLICATION su ciascun server
      ResourceBundle res = ResourceBundle.getBundle("config");
      URL url = null;
      HttpURLConnection http = null;

      // nembo.gestione.servers -> contiene elenco server del cluster separati
      // da virgola
      String servers = res.getString("nembo.gestione.servers");
      StringTokenizer token = new StringTokenizer(servers, ", ");
      while (token.hasMoreElements())
      {
        url = new URL(token.nextElement() + "/nembopratiche/reloaddatisistema.jsp");
        http = (HttpURLConnection) url.openConnection();
        int statusCode = http.getResponseCode();
        if (statusCode != 200)
        {
          throw new InternalUnexpectedException("Errore durante la chiamata a "
              + url.toString() + " - Status-Code:" + statusCode, null);
        }
      }
    }
    catch (MalformedURLException e)
    {
      throw new InternalUnexpectedException("Errore", e);
    }
    catch (IOException r)
    {
      throw new InternalUnexpectedException("Errore", r);
    }
    return "gestionesistema/riepilogo";
  }
}