package it.csi.nembo.nembopratiche.presentation.quadro.controlli;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.INuovoProcedimentoEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo115")
@NemboSecurity(value = "CU-NEMBO-115", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO115EseguiControlli extends BaseController
{
  @Autowired
  private IQuadroEJB            quadroEJB         = null;
  @Autowired
  private INuovoProcedimentoEJB nuovoProcedimento = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String dettaglio(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    Procedimento procedimento = getProcedimentoFromSession(session);
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    TestataProcedimento testataProcedimento = (TestataProcedimento) session
        .getAttribute(TestataProcedimento.SESSION_NAME);

    Vector<Long> vId = new Vector<Long>();
    vId.add(testataProcedimento.getIdAzienda());
    List<AziendaDTO> vAziende = nuovoProcedimento.getDettaglioAziendeById(vId,
        procedimento.getIdBando());
    AziendaDTO azienda = vAziende.get(0);
    aggiornaDatiAAEPSian(azienda, utenteAbilitazioni);

    Long idAzienda = quadroEJB.getAziendaRichiestaVoltura(
        procedimentoOggetto.getIdProcedimentoOggetto());
    if (idAzienda == null || idAzienda.longValue() == 0)
    {
      idAzienda = testataProcedimento.getIdAzienda();
    }

    MainControlloDTO mainControlloDTO = quadroEJB.callMainControlli(
        procedimentoOggetto.getIdBandoOggetto(),
        procedimentoOggetto.getIdProcedimentoOggetto(),
        idAzienda.longValue(),
        utenteAbilitazioni.getIdUtenteLogin());

    if (mainControlloDTO
        .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO)
    {
      model.addAttribute("msgErrore",
          "Si è verificato un errore di sistema. Contattare l'assistenza comunicando il seguente messaggio: "
              + mainControlloDTO.getMessaggio());
      return "controlli/esito";
    }

    return "redirect:../cunembo114/index.do";
  }
}