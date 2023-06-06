package it.csi.nembo.nembopratiche.presentation.quadro.interventi.investimenti;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Modifica;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO20-167-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cuNEMBO20167m")
public class CUNEMBO20167MModificaInvestimenti extends Modifica
{
  @Override
  protected List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(List<Long> ids, final long idProcedimentoOggetto, final long idBando)
      throws InternalUnexpectedException
  {
    return interventiEJB.getInfoInvestimentiPerModifica(idProcedimentoOggetto, ids);
  }

  @RequestMapping(value = "/richiesto412_{idIntervento}")
  public String richiesto412(Model model, HttpServletRequest request, @PathVariable("idIntervento") long idIntervento) throws InternalUnexpectedException
  {
    interventiEJB.modificaFlagAssociatoAltraMisura(idIntervento, NemboConstants.FLAGS.SI, getLogOperationOggettoQuadroDTO(request.getSession()));
    return "dialog/success";
  }

  @RequestMapping(value = "/nonrichiesto412_{idIntervento}")
  public String nonrichiesto412(Model model, HttpServletRequest request, @PathVariable("idIntervento") long idIntervento) throws InternalUnexpectedException
  {
    interventiEJB.modificaFlagAssociatoAltraMisura(idIntervento, NemboConstants.FLAGS.NO, getLogOperationOggettoQuadroDTO(request.getSession()));
    return "dialog/success";
  }
}