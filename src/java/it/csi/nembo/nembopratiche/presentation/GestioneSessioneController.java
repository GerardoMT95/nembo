package it.csi.nembo.nembopratiche.presentation;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.MapColonneNascosteVO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping(value = "/session")
@NemboSecurity(value = "", controllo = NemboSecurity.Controllo.NESSUNO)
public class GestioneSessioneController extends BaseController
{
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/salvaFiltri", method = RequestMethod.POST)
  @ResponseBody
  public String salvaFiltri(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    String key = request.getParameter("key");
    String filtro = request.getParameter("filtro");
    if (filtro != null)
    {
      filtro = filtro.replace("ZZZ", "&&");
    }
    if ("elencoListeLiquidazione".equals(key))
    {
      filtro = filtro.replace("\\\"", "\\\\\"");
    }
    HashMap<String, String> mapFilters = (HashMap<String, String>) session
        .getAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA);
    if (mapFilters == null)
    {
      mapFilters = new HashMap<>();
    }

    if (mapFilters.containsKey(key))
    {
      mapFilters.remove(key);
      mapFilters.put(key, filtro);
    }
    else
    {
      mapFilters.put(key, filtro);
    }
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA,
        mapFilters);
    return "OK";
  }

  @RequestMapping(value = "/salvaColonna", method = RequestMethod.POST)
  @ResponseBody
  public String salvaColonna(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    String key = request.getParameter("key");
    String field = request.getParameter("field");
    String value = request.getParameter("value");

    MapColonneNascosteVO hColumns = (MapColonneNascosteVO) session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_COLONNE_NASCOSTE);
    if (hColumns == null)
    {
      hColumns = new MapColonneNascosteVO();
    }

    if ("false".equalsIgnoreCase(value))
    {
      if (hColumns.containsKey(key))
      {
        hColumns.get(key).put(field, true);
      }
      else
      {
        HashMap<String, Boolean> vCols = new HashMap<>();
        vCols.put(field, true);
        hColumns.put(key, vCols);
      }
    }
    else
      if ("true".equalsIgnoreCase(value))
      {
        if (hColumns.containsKey(key))
        {
          hColumns.get(key).put(field, false);
        }
        else
        {
          HashMap<String, Boolean> vCols = new HashMap<>();
          vCols.put(field, false);
          hColumns.put(key, vCols);
        }
      }
    session.setAttribute(
        NemboConstants.GENERIC.SESSION_VAR_COLONNE_NASCOSTE,
        hColumns);
    return "OK";
  }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/salvaOrdinamento" ,method = RequestMethod.POST)
	@ResponseBody
	public String salvaOrdinamento(Model model, HttpSession session, HttpServletRequest request) throws InternalUnexpectedException
	{

		String key = request.getParameter("key");
		String col = request.getParameter("col");
		String order = request.getParameter("order");

		/*IMPOSTO INFO LEGATE ALLA COLONNA PER CUI SI SALVA L'ORDINAMENTO*/
		HashMap<String, String> mapFilters = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_COLONNA_ORDINAMENTO);
		if(mapFilters==null){
			mapFilters = new HashMap<>();
		}
		
		if(mapFilters.containsKey(key))
		{
			mapFilters.remove(key);
			mapFilters.put(key, col);
		}
		else
		{
			mapFilters.put(key, col);
		}
		session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_COLONNA_ORDINAMENTO, mapFilters);
		
		/*IMPOSTO INFO LEGATE AL TIPO DI ORDINAMENTO */
		HashMap<String, String> mapFiltersOrd = (HashMap<String, String>)session.getAttribute(NemboConstants.GENERIC.SESSION_VAR_TIPO_ORDINAMENTO);
		if(mapFiltersOrd==null){
			mapFiltersOrd = new HashMap<>();
		}
		
		if(mapFiltersOrd.containsKey(key))
		{
			mapFiltersOrd.remove(key);
			mapFiltersOrd.put(key, order);
		}
		else
		{
			mapFiltersOrd.put(key, order);
		}
		session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_TIPO_ORDINAMENTO, mapFiltersOrd);
		return "OK";
	}
	

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/salvaNumeroPagina", method = RequestMethod.POST)
  @ResponseBody
  public String salvaNumeroPagina(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    String key = request.getParameter("key");
    String page = request.getParameter("page");

    HashMap<String, String> mapFilters = (HashMap<String, String>) session
        .getAttribute(NemboConstants.GENERIC.SESSION_VAR_NUMERO_PAGINA);
    if (mapFilters == null)
    {
      mapFilters = new HashMap<>();
    }

    if (mapFilters.containsKey(key))
    {
      mapFilters.remove(key);
      mapFilters.put(key, page);
    }
    else
    {
      mapFilters.put(key, page);
    }
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_NUMERO_PAGINA,
        mapFilters);
    return "OK";
  }

  @RequestMapping(value = "/salvaRigheVisibili", method = RequestMethod.POST)
  @ResponseBody
  public String salvaRigheVisibili(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    String key = request.getParameter("key");
    String field = request.getParameter("field");
    String value = request.getParameter("value");

    MapColonneNascosteVO hColumns = (MapColonneNascosteVO) session
        .getAttribute(NemboConstants.GENERIC.SESSION_VAR_RIGHE_VISIBILI);
    if (hColumns == null)
    {
      hColumns = new MapColonneNascosteVO();
    }

    key = key.trim();
    field = field.trim();
    value = value.trim();

    if ("true".equalsIgnoreCase(value))
    {
      if (hColumns.containsKey(key))
      {
        hColumns.get(key).put(field, true);
      }
      else
      {
        HashMap<String, Boolean> vCols = new HashMap<>();
        vCols.put(field, true);
        hColumns.put(key, vCols);
      }
    }
    else
      if ("false".equalsIgnoreCase(value))
      {
        if (hColumns.containsKey(key))
        {
          hColumns.get(key).put(field, false);
        }
        else
        {
          HashMap<String, Boolean> vCols = new HashMap<>();
          vCols.put(field, false);
          hColumns.put(key, vCols);
        }
      }
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_RIGHE_VISIBILI,
        hColumns);
    return "OK";
  }

  @RequestMapping(value = "/loadProcedimento_{idProcedimento}", method = RequestMethod.GET)
  @ResponseBody
  public String loadProcedimento(
      @PathVariable(value = "idProcedimento") long idProcedimento, Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    // carico procedimento e lo metto in sessione
    NemboFactory.setIdProcedimentoInSession(session, idProcedimento);
    return "OK";
  }
}
