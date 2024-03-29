package it.csi.nembo.nembopratiche.presentation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.csi.nembo.nembopratiche.business.IReportisticaEJB;
import it.csi.nembo.nembopratiche.dto.ElencoQueryBandoDTO;
import it.csi.nembo.nembopratiche.dto.reportistica.GraficoVO;
import it.csi.nembo.nembopratiche.dto.reportistica.ParametriQueryReportVO;
import it.csi.nembo.nembopratiche.exception.InternalServiceException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@NemboSecurity(value = "CU-NEMBO-284", controllo = NemboSecurity.Controllo.DEFAULT)
@RequestMapping(value = "/cunembo284")
public class CUNEMBO284Reportistica extends BaseController
{

  @Autowired
  private IReportisticaEJB reportEJB;

  @RequestMapping(value = "index", method = RequestMethod.GET)
  public String index(HttpSession session, Model model)
      throws InternalUnexpectedException
  {

    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    List<ElencoQueryBandoDTO> report = reportEJB
        .getElencoReport(utenteAbilitazioni.getAttori()[0].getCodice());
    model.addAttribute("report", report);

    return "reportistica/index/elencoReport";
  }

  @RequestMapping(value = "indexGrafici", method = RequestMethod.GET)
  public String indexGrafici(HttpSession session, Model model)
      throws InternalUnexpectedException
  {

    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
    List<ElencoQueryBandoDTO> grafici = reportEJB
        .getElencoGrafici(utenteAbilitazioni.getAttori()[0].getCodice());
    model.addAttribute("grafici", grafici);

    return "reportistica/index/elencoGrafici";
  }

  @RequestMapping(value = "visualizza_report_{idElencoQuery}", method = RequestMethod.GET)
  public String dettaglioReport(
      @PathVariable(value = "idElencoQuery") long idElencoQuery,
      HttpSession session, Model model) throws InternalUnexpectedException
  {
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    ParametriQueryReportVO params = new ParametriQueryReportVO();
    params
        .setCodEnteCaa(NemboUtils.PAPUASERV.extractInfoEnteBaseFromEnteLogin(
            utenteAbilitazioni.getEnteAppartenenza()).getCodiceEnte());
    GraficoVO graficoVO = reportEJB.getGrafico(idElencoQuery, params);

    model.addAttribute("tabella", graficoVO);
    session.setAttribute("reportSelzionatoSession", graficoVO);

    return "reportistica/index/dettaglioReport";
  }

  @RequestMapping(value = "visualizza_grafico_{idElencoQuery}", method = RequestMethod.GET)
  public Object dettaglioGrafico(
      @PathVariable(value = "idElencoQuery") long idElencoQuery,
      HttpSession session, Model model) throws InternalUnexpectedException
  {

    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    ParametriQueryReportVO params = new ParametriQueryReportVO();
    params
        .setCodEnteCaa(NemboUtils.PAPUASERV.extractInfoEnteBaseFromEnteLogin(
            utenteAbilitazioni.getEnteAppartenenza()).getCodiceEnte());
    GraficoVO graficoVO = reportEJB.getGrafico(idElencoQuery, params);

    return graficoVO;
  }

  @RequestMapping(value = "stampa_{idElencoQuery}", method = RequestMethod.GET)
  public String stampa(
      @PathVariable(value = "idElencoQuery") long idElencoQuery, ModelMap model,
      HttpServletRequest request, HttpSession session)
      throws InternalUnexpectedException
  {
    model.addAttribute("dettIdElencoQuery", idElencoQuery);
    return "reportistica/index/stampaGrafico";
  }

  @RequestMapping(value = "dettaglio_{idElencoQuery}", method = RequestMethod.GET)
  public String dettaglio(
      @PathVariable(value = "idElencoQuery") long idElencoQuery, ModelMap model,
      HttpServletRequest request, HttpSession session)
      throws InternalUnexpectedException
  {
    model.addAttribute("dettIdElencoQuery", idElencoQuery);
    return "reportistica/index/dettaglioGrafico";
  }

  @RequestMapping(value = "getDettaglioGrafico_{idElencoQuery}", produces = "application/json")
  @ResponseBody
  public Object getData(
      @PathVariable(value = "idElencoQuery") long idElencoQuery,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    ParametriQueryReportVO params = new ParametriQueryReportVO();
    params
        .setCodEnteCaa(NemboUtils.PAPUASERV.extractInfoEnteBaseFromEnteLogin(
            utenteAbilitazioni.getEnteAppartenenza()).getCodiceEnte());
    GraficoVO graficoVO = reportEJB.getGrafico(idElencoQuery, params);

    return graficoVO;
  }

  @RequestMapping(value = "downloadExcelReport")
  public ModelAndView downloadExcel(HttpSession session, Model model)
      throws InternalServiceException, InternalUnexpectedException
  {
    GraficoVO graficoVO = (GraficoVO) session
        .getAttribute("reportSelzionatoSession");
    if (reportEJB.hasExcelTemplateInElencoQuery(graficoVO.getIdElencoQuery()))
    {
      return new ModelAndView("redirect:downloadExcelReportTemplate.xls");
    }
    else
    {
      return new ModelAndView("excelReportView", "graficoVO", graficoVO);
    }
  }

  @RequestMapping(value = "downloadExcelReportTemplate")
  public ResponseEntity<byte[]> downloadExcelTemplate(
      HttpServletRequest request, HttpSession session, Model model)
      throws Exception
  {
    GraficoVO graficoVO = (GraficoVO) session
        .getAttribute("reportSelzionatoSession");
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type", NemboUtils.FILE.getMimeType(".xlsx"));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"downloadExcelReportTemplate.xlsx\"");
    byte[] bytes = reportEJB
        .getExcelParametroDiElencoQuery(graficoVO.getIdElencoQuery());
    ByteArrayInputStream is = new ByteArrayInputStream(bytes);
    Workbook workbook = null; //WorkbookFactory.create(is);
    is.close();
    ExcelTemplateReport.buildExcelDocument(workbook, graficoVO);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    workbook.write(baos);
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(
        baos.toByteArray(),
        httpHeaders, HttpStatus.OK);
    return responseEntity;
  }
}
