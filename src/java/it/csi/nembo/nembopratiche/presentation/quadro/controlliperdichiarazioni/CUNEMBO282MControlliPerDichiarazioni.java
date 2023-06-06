package it.csi.nembo.nembopratiche.presentation.quadro.controlliperdichiarazioni;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-282-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo282m")
public class CUNEMBO282MControlliPerDichiarazioni extends BaseController
{

  public static final String ERRORE_TECNICO_NON_TROVATO = "Il tecnico indicato non è stato trovato in elenco";
  public static final String ERRORE_ESITO_NON_TROVATO   = "L'esito indicato non è tra quelli disponibili";

  public static final String BASE_JSP_URL               = "controlliperdichiarazioni/";
  @Autowired
  IQuadroEJB                 quadroEJB;

  @RequestMapping(value = "/popupEsitoTecnico", method = RequestMethod.GET)
  public String modifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromRequest(request);
    HttpSession session = request.getSession();
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    QuadroOggettoDTO quadro = po
        .findQuadroByCU(NemboConstants.USECASE.CONTROLLI_DICHIARAZIONI);
    BandoDTO bando = quadroEJB
        .getInformazioniBandoByProcedimento(po.getIdProcedimento());

    model.addAttribute("flagEstratta",
        quadroEJB.getFlagEstrattaControlliDichiarazione(po.getIdProcedimento(),
            po.getCodiceRaggruppamento(), idProcedimentoOggetto,
            bando.getCodiceTipoBando()));
    EsitoFinaleDTO esito = quadroEJB.getEsitoFinale(idProcedimentoOggetto,
        quadro.getIdQuadroOggetto());
    model.addAttribute("esito", esito);

    caricaDecodifichePerTecniciEEsiti(model, po, quadro,
        NemboConstants.ESITO.TIPO.CONTROLLI_AMMINISTRATIVI,session);

    return BASE_JSP_URL + "/popupEsitoTecnico";
  }

  @IsPopup
  @RequestMapping(value = "/popupEsitoTecnico", method = RequestMethod.POST)
  public String popupModificaEsitoTecnico(Model model,
      HttpServletRequest request) throws InternalUnexpectedException
  {

    final HttpSession session = request.getSession();
    final List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.CONTROLLI_AMMINISTRATIVI);
    model.addAttribute("esiti", elencoEsiti);
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
	List<DecodificaDTO<Long>> elencoTecnici = quadroEJB.getElencoTecniciDisponibiliPerAmmCompetenza(idProcedimentoOggetto,getUtenteAbilitazioni(session).getIdProcedimento());
	List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();
	model.addAttribute("tecnici", elencoTecnici);
	model.addAttribute("ufficiZona", ufficiZona);

    String sIdTecnico = request.getParameter("idTecnico");
    String sIdFunzionario = request.getParameter("idFunzionario");
    String sIdEsito = request.getParameter("idEsito");
    String sNote = request.getParameter("note");
    EsitoFinaleDTO esitoTecnicoDTO = new EsitoFinaleDTO();
    Errors errors = new Errors();
    Long idTecnico = errors.validateMandatoryID(sIdTecnico, "idTecnico");
    if (idTecnico != null)
    {
      DecodificaDTO<Long> tecnico = NemboUtils.LIST
          .findDecodificaById(elencoTecnici, idTecnico);
      if (tecnico == null)
      {
        // Non trovato?!?!? Qui qualcuno sta facendo il furbo!
        errors.addError("idTecnico", ERRORE_TECNICO_NON_TROVATO);
      }
      else
      {
        esitoTecnicoDTO.setIdTecnico(idTecnico);
      }
    }
    Long idFunzionario = errors.validateMandatoryID(sIdFunzionario,
        "idFunzionario");
    if (idFunzionario != null)
    {
      DecodificaDTO<Long> funzionario = NemboUtils.LIST
          .findDecodificaById(elencoTecnici, idFunzionario);
      if (funzionario == null)
      {
        // Non trovato?!?!? Qui qualcuno sta facendo il furbo!
        errors.addError("idTecnico", ERRORE_TECNICO_NON_TROVATO);
      }
      else
      {
        esitoTecnicoDTO.setIdGradoSup(idFunzionario);
      }
    }
    Long idEsito = errors.validateMandatoryID(sIdEsito, "idEsito");
    if (idEsito != null)
    {
      DecodificaDTO<Long> esito = NemboUtils.LIST
          .findDecodificaById(elencoEsiti, idEsito);
      if (esito == null)
      {
        // Non trovato?!?!? Qui qualcuno sta facendo il furbo!
        errors.addError("idEsito", ERRORE_ESITO_NON_TROVATO);
      }
      else
      {
        esitoTecnicoDTO.setIdEsito(idEsito);
        if ("N".equals(esito.getCodice()))
          errors.validateMandatoryFieldLength(sNote, 0, 4000, "note", true);
      }

    }

    errors.validateOptionalFieldLength(sNote, "note", 0, 4000, true);
    esitoTecnicoDTO.setNote(sNote);
    if (!errors.addToModelIfNotEmpty(model))
    {
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(
          session);
      quadroEJB.updateEsitoTecnico(esitoTecnicoDTO,
          logOperationOggettoQuadroDTO);
      return "dialog/success";
    }

    model.addAttribute("preferRequest", Boolean.TRUE);
    return BASE_JSP_URL + "/popupEsitoTecnico";
  }

  protected void caricaDecodifichePerTecniciEEsiti(Model model,
      ProcedimentoOggetto po, QuadroOggettoDTO quadro, String tipoEsito, HttpSession session)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    model.addAttribute("esiti", quadroEJB.getElencoEsiti(tipoEsito));
	List<DecodificaDTO<Long>> elencoTecnici = quadroEJB.getElencoTecniciDisponibiliPerAmmCompetenza(idProcedimentoOggetto,getUtenteAbilitazioni(session).getIdProcedimento());
	List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();
	model.addAttribute("tecnici", elencoTecnici);
	model.addAttribute("ufficiZona", ufficiZona);
  }

}