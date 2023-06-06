package it.csi.nembo.nembopratiche.presentation.quadro.allegati;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.permission.UpdatePermissionProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.allegati.FileAllegatiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.GruppoInfoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo108")
@NemboSecurity(value = "CU-NEMBO-108", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO108AllegatiOggetto extends BaseController
{
  public static final String CU_NAME = "CU-NEMBO-108";
  @Autowired
  IQuadroEJB                 quadroEJB;

  @RequestMapping("/index")
  public String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(CU_NAME);
    long idProcedimentoOggetto = procedimentoOggetto.getIdProcedimentoOggetto();
    List<GruppoInfoDTO> allegati = quadroEJB.getDichiarazioniOggetto(
        idProcedimentoOggetto, quadro.getIdQuadroOggetto(),
        procedimentoOggetto.getIdBandoOggetto());
    Map<Long, List<FileAllegatiDTO>> fileMap = quadroEJB
        .getMapFileAllegati(idProcedimentoOggetto);

    model.addAttribute("allegati", allegati);
    model.addAttribute("fileMap", fileMap);
    model.addAttribute("canUpdate", canUpdate(idProcedimentoOggetto, session));
    return "allegati/allegatiOggetto";
  }

  public boolean canUpdate(long idProcedimentoOggetto, HttpSession session)
      throws InternalUnexpectedException
  {
    UpdatePermissionProcedimentoOggetto permission = null;
    try
    {
      permission = quadroEJB.canUpdateProcedimentoOggetto(idProcedimentoOggetto,
          true);
    }
    catch (NemboPermissionException e)
    {
      return false;
    }
    String extCodAttore = permission.getExtCodAttore();
    if (extCodAttore != null)
    {
      UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);
      return NemboUtils.PAPUASERV.isAttoreAbilitato(utenteAbilitazioni,
          extCodAttore);
    }
    return true;

  }
}
