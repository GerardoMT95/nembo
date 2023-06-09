package it.csi.nembo.nembopratiche.presentation.quadro.datafinelavori;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datafinelavori.DataFineLavoriDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-231-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo231m")
public class CUNEMBO231MDataFineLavori extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    List<DataFineLavoriDTO> elenco = quadroEjb.getElencoDateFineLavori(
        getProcedimentoFromSession(session).getIdProcedimento(),
        getIdProcedimentoOggetto(session));
    if (elenco == null)
    {
      elenco = new ArrayList<>();
    }
    model.addAttribute("elenco", elenco);

    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    String codice = quadroEjb.getCodiceOggetto(po.getIdProcedimentoOggetto());
    model.addAttribute("isProcedimentoOggettoIstanza",
        NemboConstants.FLAGS.SI.equals(po.getFlagIstanza())
            && codice.compareTo("DSAL") != 0 && codice.compareTo("DCESA") != 0);
    if (codice.compareTo("DSAL") == 0 || codice.compareTo("ISSAL") == 0
        || codice.compareTo("DANT") == 0 || codice.compareTo("ISANT") == 0
        || codice.compareTo("DACC") == 0 || codice.compareTo("ISACC") == 0
        || codice.compareTo("DCESA") == 0 || codice.compareTo("DCEAC") == 0
        || codice.compareTo("DCEAN") == 0)
      model.addAttribute("isDomandaOIstruttoriaPagamento", true);

    return "datafinelavori/modifica";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public final String indexPost(Model model, HttpSession session,
      @RequestParam(value = "idDataFine", required = true) long idDataFine,
      @RequestParam(value = "data_fine_lavori", required = false) String dataFineLavori,
      @RequestParam(value = "data_termine", required = false) String dataTermine,
      @RequestParam(value = "data_proroga", required = false) String dataProroga,
      @RequestParam(value = "note", required = false) String note)
      throws InternalUnexpectedException
  {
    DataFineLavoriDTO dto = new DataFineLavoriDTO();
    model.addAttribute("preferRequestValues", Boolean.TRUE);

    Errors error = validateForm(dataFineLavori, dataTermine, dataProroga, note,
        session, dto);

    if (error.isEmpty())
    {
      dto.setNote(note);
      quadroEjb.aggiornaDataFineLavori(idDataFine, dto,
          getLogOperationOggettoQuadroDTO(session));
      return "redirect:../cunembo231l/index.do";
    }
    model.addAttribute("errors", error);
    return index(model, session);
  }

  private Errors validateForm(String dataFineLavori, String dataTermine,
      String dataProroga, String note, HttpSession session,
      DataFineLavoriDTO dto) throws InternalUnexpectedException
  {
    Errors error = new Errors();

    error.validateFieldMaxLength(note, "note", 4000);

    Date dataFineLavoriDate = null;
    Date dataProrogaDate = null;
    Date dataTermineDate = null;

    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    String codice = quadroEjb.getCodiceOggetto(po.getIdProcedimentoOggetto());
    if (NemboConstants.FLAGS.SI.equals(po.getFlagIstanza())
        && codice.compareTo("DSAL") != 0 && codice.compareTo("DCESA") != 0)
    {
      dataProrogaDate = error.validateMandatoryDateInRange(dataProroga,
          "data_proroga",
          quadroEjb.getMinDataProroga(
              getProcedimentoFromSession(session).getIdProcedimento()),
          null, true, true);
    }
    else
    {
      dataFineLavoriDate = error.validateMandatoryDate(dataFineLavori,
          "data_fine_lavori", true);
      dataTermineDate = error.validateOptionalDate(dataTermine, "data_termine",
          true);
    }

    if (dataFineLavoriDate != null)
      dto.setDataFineLavori(dataFineLavoriDate);
    if (dataProrogaDate != null)
    {
      error.validateMandatory(note, "note");
      dto.setDataProroga(dataProrogaDate);
    }
    if (dataTermineDate != null)
      dto.setDataTermineRendicontazione(dataTermineDate);

    return error;
  }

}
