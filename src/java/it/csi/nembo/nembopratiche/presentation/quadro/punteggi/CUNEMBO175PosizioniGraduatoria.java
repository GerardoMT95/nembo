package it.csi.nembo.nembopratiche.presentation.quadro.punteggi;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.GraduatoriaDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-175", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo175")
public class CUNEMBO175PosizioniGraduatoria extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping("/index")
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    final Procedimento procedimento = getProcedimentoFromSession(session);
    final long idProcedimento = procedimento.getIdProcedimento();

    List<GraduatoriaDTO> graduatorie = quadroEjb
        .getGraduatorieByIdProcedimento(idProcedimento);
    if (graduatorie != null && graduatorie.size() <= 0)
      graduatorie = null;
    model.addAttribute("elencoGraduatorie", graduatorie);

    return "punteggi/posizioneGraduatoria";
  }

  @RequestMapping(value = "getElencoGraduatorie", produces = "application/json")
  @ResponseBody
  public List<GraduatoriaDTO> getGraduatorieJson(Model model,
      HttpSession session) throws InternalUnexpectedException
  {
    final Procedimento procedimento = getProcedimentoFromSession(session);
    final long idProcedimento = procedimento.getIdProcedimento();

    List<GraduatoriaDTO> graduatorie = quadroEjb
        .getGraduatorieByIdProcedimento(idProcedimento);

    return graduatorie;
  }
}
