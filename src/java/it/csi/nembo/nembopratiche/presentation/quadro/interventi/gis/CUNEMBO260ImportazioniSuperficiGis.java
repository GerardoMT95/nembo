package it.csi.nembo.nembopratiche.presentation.quadro.interventi.gis;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRendicontazioneEAccertamentoSpeseEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datafinelavori.DataFineLavoriDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-260", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo260")
public class CUNEMBO260ImportazioniSuperficiGis extends BaseController
{
  private static final String                   JSP_BASE_PATH = "interventi/accertamentospese/saldo/gis/";

  @Autowired
  private IRendicontazioneEAccertamentoSpeseEJB rendicontazioneEAccertamentoSpeseEJB;
  @Autowired
  private IQuadroEJB                            quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model)
  {
    return JSP_BASE_PATH + "importaGis";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public String importaSuperficiGIS(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    try
    {
      final LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = this
          .getLogOperationOggettoQuadroDTO(session);
      long idProcedimentoOggettoCorrente = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      Date dataFineLavori = null;
      List<DataFineLavoriDTO> list = quadroEjb.getElencoDateFineLavori(
          getProcedimentoFromSession(session).getIdProcedimento(),
          getIdProcedimentoOggetto(session));
      if (list != null)
      {
        for (DataFineLavoriDTO dfl : list)
        {
          long idProcedimentoOggetto = dfl.getIdProcedimentoOggetto();
          if (idProcedimentoOggettoCorrente == idProcedimentoOggetto)
          {
            dataFineLavori = dfl.getDataFineLavori();
            break;
          }
        }
      }
      if (dataFineLavori == null)
      {
        throw new ApplicationException(
            "Impossibile effettuare l'importazione in quanto non è stata indicata una data di fine lavori ");
      }

      int anno = NemboUtils.DATE.getYearFromDate(dataFineLavori);
      Date dataLimite = NemboUtils.DATE.parseDate("11/11/" + anno);
      if (dataFineLavori.compareTo(dataLimite) > 0)
      {
        anno++;
      }
      rendicontazioneEAccertamentoSpeseEJB
          .importaSuperficiGIS(logOperationOggettoQuadroDTO, anno);
    }
    catch (ApplicationException e)
    {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return JSP_BASE_PATH + "risultatoImportazione";
  }
}