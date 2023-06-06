package it.csi.nembo.nembopratiche.presentation.quadro.controlliamministrativi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.AnnoExPostsDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.ControlloAmministrativoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.EsitoControlliAmmDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.InfoExPostsDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.VisitaLuogoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.VisitaLuogoExtDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-165-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo165m")
public class CUNEMBO165MGestioneControlliAmministrativiModifica
    extends BaseController
{
  @Autowired
  IQuadroEJB                 quadroEJB;

  public static final String ERRORE_TECNICO_NON_TROVATO = "Il tecnico indicato non è stato trovato in elenco";
  public static final String ERRORE_ESITO_NON_TROVATO   = "L'esito indicato non è tra quelli disponibili";

  @RequestMapping(value = "/modifica", method = RequestMethod.POST)
  public String modifica(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> list = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idQuadroOggControlloAmm"));
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(
        request.getSession());
    final List<ControlloAmministrativoDTO> controlliAmministrativi = quadroEJB
        .getControlliAmministrativi(idProcedimentoOggetto,
            NemboConstants.QUADRO.CODICE.CONTROLLI_AMMINISTRATIVI, list);
    List<Long> listIdControlloAmministrativo = new ArrayList<Long>();
    for (ControlloAmministrativoDTO controllo : controlliAmministrativi)
    {
      listIdControlloAmministrativo
          .add(controllo.getIdControlloAmministrativo());
    }
    model.addAttribute("controlli", controlliAmministrativi);
    List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.CONTROLLI_AMMINISTRATIVI);
    model.addAttribute("esiti", elencoEsiti);
    if (validateAndInsert(controlliAmministrativi, elencoEsiti, request, model))
    {
      // Inserimento
      return "redirect:../cunembo165d/index.do";
    }
    else
    {
      model.addAttribute("preferRequest", Boolean.TRUE);
      return "controlliamministrativi/modificaMultipla";
    }
  }

  protected boolean validateAndInsert(
      List<ControlloAmministrativoDTO> controlliAmministrativi,
      List<DecodificaDTO<Long>> elencoEsiti, HttpServletRequest request,
      Model model) throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
    Errors errors = new Errors();
    List<EsitoControlliAmmDTO> esitiControlliDaAggiornare = new ArrayList<EsitoControlliAmmDTO>();
    for (ControlloAmministrativoDTO controllo : controlliAmministrativi)
    {
      EsitoControlliAmmDTO esitoControllo = new EsitoControlliAmmDTO();
      esitiControlliDaAggiornare.add(esitoControllo);
      esitoControllo.setIdProcedimentoOggetto(idProcedimentoOggetto);
      final long idQuadroOggControlloAmm = controllo
          .getIdQuadroOggControlloAmm();
      esitoControllo.setIdQuadroOggControlloAmm(idQuadroOggControlloAmm);
      final String parameterNameEsito = "idEsito_" + idQuadroOggControlloAmm;
      Long idEsito = errors.validateMandatoryID(
          request.getParameter(parameterNameEsito), parameterNameEsito);
      if (idEsito != null)
      {
        if (findEsito(elencoEsiti, idEsito) == null)
        {
          errors.put(parameterNameEsito, Errors.ERRORE_CAMPO_OBBLIGATORIO);
        }
        else
        {
          esitoControllo.setIdEsito(idEsito);
        }
      }
      final String parameterNameNote = "note_" + idQuadroOggControlloAmm;
      final String note = request.getParameter(parameterNameNote);
      esitoControllo.setNote(note);
      errors.validateOptionalFieldMaxLength(note, 4000, parameterNameNote);
    }
    if (errors.addToModelIfNotEmpty(model))
    {
      return false;
    }
    else
    {
      quadroEJB.updateEsitiControlli(esitiControlliDaAggiornare,
          getLogOperationOggettoQuadroDTO(session));
      return true;
    }
  }

  protected DecodificaDTO<Long> findEsito(List<DecodificaDTO<Long>> elencoEsiti,
      long idEsito)
  {
    for (DecodificaDTO<Long> esito : elencoEsiti)
    {
      if (esito.getId() == idEsito)
      {
        return esito;
      }
    }
    return null;
  }

  @RequestMapping(value = "/modifica_{idQuadroOggControlloAmm}", method = RequestMethod.GET)
  public String modificaSingola(Model model, HttpServletRequest request,
      @PathVariable("idQuadroOggControlloAmm") @ModelAttribute("idQuadroOggControlloAmm") long idQuadroOggControlloAmm)
      throws InternalUnexpectedException
  {
    List<Long> list = new ArrayList<Long>();
    list.add(idQuadroOggControlloAmm);
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(
        request.getSession());
    final List<ControlloAmministrativoDTO> controlliAmministrativi = quadroEJB
        .getControlliAmministrativi(idProcedimentoOggetto,
            NemboConstants.QUADRO.CODICE.CONTROLLI_AMMINISTRATIVI, list);
    List<Long> listIdControlloAmministrativo = new ArrayList<Long>();
    for (ControlloAmministrativoDTO controllo : controlliAmministrativi)
    {
      listIdControlloAmministrativo
          .add(controllo.getIdControlloAmministrativo());
    }
    model.addAttribute("controlli", controlliAmministrativi);
    List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.CONTROLLI_AMMINISTRATIVI);
    model.addAttribute("esiti", elencoEsiti);
    return "controlliamministrativi/modificaMultipla";
  }

  @RequestMapping(value = "/modifica_multipla", method = RequestMethod.POST)
  public String modificaMultipla(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> list = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idQuadroOggControlloAmm"));
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(
        request.getSession());
    final List<ControlloAmministrativoDTO> controlliAmministrativi = quadroEJB
        .getControlliAmministrativi(idProcedimentoOggetto,
            NemboConstants.QUADRO.CODICE.CONTROLLI_AMMINISTRATIVI, list);
    List<Long> listIdControlloAmministrativo = new ArrayList<Long>();
    for (ControlloAmministrativoDTO controllo : controlliAmministrativi)
    {
      listIdControlloAmministrativo
          .add(controllo.getIdControlloAmministrativo());
    }
    model.addAttribute("controlli", controlliAmministrativi);
    List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.CONTROLLI_AMMINISTRATIVI);
    model.addAttribute("esiti", elencoEsiti);
    return "controlliamministrativi/modificaMultipla";
  }

  @IsPopup
  @RequestMapping(value = "/popup_nuova_visita", method = RequestMethod.GET)
  public String popupNuovaVisita(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    QuadroOggettoDTO quadro = po.findQuadroByCU("CU-NEMBO-165-M");
    caricaDecodifichePerTecniciEEsiti(model, po, quadro,
        NemboConstants.ESITO.TIPO.VISITA_LUOGO,session);
    return "controlliamministrativi/popupNuovaVisita";
  }

  @IsPopup
  @RequestMapping(value = "/popup_inserisci_visita", method = RequestMethod.POST)
  public String popupInserisciVisita(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {

    final HttpSession session = request.getSession();
    final List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.VISITA_LUOGO);
    model.addAttribute("esiti", elencoEsiti);
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    List<DecodificaDTO<Long>> elencoTecnici = quadroEJB.getElencoTecniciDisponibiliPerAmmCompetenza(idProcedimentoOggetto,getUtenteAbilitazioni(session).getIdProcedimento());
    List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();
    model.addAttribute("tecnici", elencoTecnici);
    model.addAttribute("ufficiZona", ufficiZona);

    String sDataVisita = request.getParameter("dataVisita");
    String sIdTecnico = request.getParameter("idTecnico");
    String sIdEsito = request.getParameter("idEsito");
    String sNote = request.getParameter("note");
    VisitaLuogoDTO visitaLuogoDTO = new VisitaLuogoDTO();
    Errors errors = new Errors();
    Date dataVisita = errors.validateMandatoryDateInRange(sDataVisita,
        "dataVisita", null, new Date(), true, true);
    visitaLuogoDTO.setDataVisita(dataVisita);
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
        visitaLuogoDTO.setExtIdTecnico(idTecnico);
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
        visitaLuogoDTO.setIdEsito(idEsito);
      }
    }
    errors.validateOptionalFieldLength(sNote, "note", 0, 4000, true);
    visitaLuogoDTO.setNote(sNote);
    String nameDataVerbale = "dataVerbale";
    String sDataVerbale = request.getParameter(nameDataVerbale);
    String nameNumeroVerbale = "numeroVerbale";
    String sNumeroVerbale = request.getParameter(nameNumeroVerbale);
    Date dataVerbale = errors.validateOptionalDateInRange(sDataVerbale,
        nameDataVerbale, dataVisita, null, true, true);
    errors.validateOptionalFieldLength(sNumeroVerbale, nameNumeroVerbale, 0,
        20);
    boolean isDataVerbale = !GenericValidator.isBlankOrNull(sDataVerbale);
    boolean isNumeroVerbale = !GenericValidator.isBlankOrNull(sNumeroVerbale);
    if (isDataVerbale != isNumeroVerbale)
    {
      if (isDataVerbale)
      {
        errors.addError(nameNumeroVerbale,
            "Se è presente la data del verbale deve essere inserito anche il numero del verbale");
      }
      else
      {
        errors.addError(nameDataVerbale,
            "Se è presente il numero del verbale deve essere inserita anche la data verbale");
      }
    }
    else
    {
      if (isDataVerbale) // || isNumeroVerbale
      {
        visitaLuogoDTO.setDataVerbale(dataVerbale);
        visitaLuogoDTO.setNumeroVerbale(sNumeroVerbale);
      }
    }
    if (!errors.addToModelIfNotEmpty(model))
    {
      visitaLuogoDTO
          .setIdProcedimentoOggetto(getIdProcedimentoOggetto(session));
      final LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(
          session);
      visitaLuogoDTO.setIdQuadroOggetto(
          logOperationOggettoQuadroDTO.getIdQuadroOggetto());
      quadroEJB.insertVisitaLuogo(visitaLuogoDTO, logOperationOggettoQuadroDTO);
      return "dialog/success";
    }
    model.addAttribute("preferRequest", Boolean.TRUE);
    return "controlliamministrativi/popupNuovaVisita";
  }

  @RequestMapping(value = "/modifica_visita_luogo_{idVisitaLuogo}", method = RequestMethod.GET)
  public String modificaVisitaSingola(Model model, HttpServletRequest request,
      @PathVariable("idVisitaLuogo") long idVisitaLuogo)
      throws InternalUnexpectedException
  {
    List<Long> ids = new ArrayList<Long>();
    ids.add(idVisitaLuogo);
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    QuadroOggettoDTO quadro = findQuadroCorrente(po);
    List<VisitaLuogoExtDTO> visite = quadroEJB.getVisiteLuogo(
        po.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto(), ids);
    model.addAttribute("visite", visite);
    caricaDecodifichePerTecniciEEsiti(model, po, quadro,
        NemboConstants.ESITO.TIPO.VISITA_LUOGO,session);
    return "controlliamministrativi/modificaMultiplaVisiteLuogo";
  }

  @RequestMapping(value = "/modifica_multipla_visite_luogo", method = RequestMethod.POST)
  public String modificaMultipleVisiteLuogo(Model model,
      HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> ids = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idVisitaLuogo"));
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    QuadroOggettoDTO quadro = findQuadroCorrente(po);
    List<VisitaLuogoExtDTO> visite = quadroEJB.getVisiteLuogo(
        po.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto(), ids);
    model.addAttribute("visite", visite);
    caricaDecodifichePerTecniciEEsiti(model, po, quadro,
        NemboConstants.ESITO.TIPO.VISITA_LUOGO,session);
    return "controlliamministrativi/modificaMultiplaVisiteLuogo";
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

  @IsPopup
  @RequestMapping(value = "/aggiorna_visite_luogo", method = RequestMethod.POST)
  public String modificaVisiteLuogo(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    List<Long> ids = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idVisitaLuogo"));
    final long idProcedimentoOggetto = po.getIdProcedimentoOggetto();
    QuadroOggettoDTO quadro = findQuadroCorrente(po);
    List<VisitaLuogoExtDTO> visite = quadroEJB.getVisiteLuogo(
        idProcedimentoOggetto, quadro.getIdQuadroOggetto(), ids);
    final List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.VISITA_LUOGO);
    model.addAttribute("esiti", elencoEsiti);
    List<DecodificaDTO<Long>> elencoTecnici = quadroEJB.getElencoTecniciDisponibiliPerAmmCompetenza(idProcedimentoOggetto,getUtenteAbilitazioni(session).getIdProcedimento());
    List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();
    model.addAttribute("tecnici", elencoTecnici);
    model.addAttribute("ufficiZona", ufficiZona);
    Errors errors = new Errors();
    List<VisitaLuogoDTO> visiteDaAggiornare = new ArrayList<VisitaLuogoDTO>();
    for (VisitaLuogoExtDTO visita : visite)
    {
      final long idVisitaLuogo = visita.getIdVisitaLuogo();
      final String nameDataVisita = "dataVisita_" + idVisitaLuogo;
      String sDataVisita = request.getParameter(nameDataVisita);
      final String nameIdTecnico = "idTecnico_" + idVisitaLuogo;
      String sIdTecnico = request.getParameter(nameIdTecnico);
      final String nameIdEsito = "idEsito_" + idVisitaLuogo;
      String sIdEsito = request.getParameter(nameIdEsito);
      final String nameNote = "note_" + idVisitaLuogo;
      String sNote = request.getParameter(nameNote);
      VisitaLuogoDTO visitaLuogoDTO = new VisitaLuogoDTO();
      Date dataVisita = errors.validateMandatoryDateInRange(sDataVisita,
          nameDataVisita, null, new Date(), true, true);
      visitaLuogoDTO.setDataVisita(dataVisita);
      Long idTecnico = errors.validateMandatoryID(sIdTecnico, nameIdTecnico);
      if (idTecnico != null)
      {
        DecodificaDTO<Long> tecnico = NemboUtils.LIST
            .findDecodificaById(elencoTecnici, idTecnico);
        if (tecnico == null)
        {
          // Non trovato?!?!? Qui qualcuno sta facendo il furbo!
          errors.addError(nameIdTecnico, ERRORE_TECNICO_NON_TROVATO);
        }
        else
        {
          visitaLuogoDTO.setExtIdTecnico(idTecnico);
        }
      }
      Long idEsito = errors.validateMandatoryID(sIdEsito, nameIdEsito);
      if (idEsito != null)
      {
        DecodificaDTO<Long> esito = NemboUtils.LIST
            .findDecodificaById(elencoEsiti, idEsito);
        if (esito == null)
        {
          // Non trovato?!?!? Qui qualcuno sta facendo il furbo!
          errors.addError(nameIdEsito, ERRORE_ESITO_NON_TROVATO);
        }
        else
        {
          visitaLuogoDTO.setIdEsito(idEsito);
        }
      }

      errors.validateOptionalFieldLength(sNote, nameNote, 0, 4000, true);
      String nameDataVerbale = "dataVerbale_" + idVisitaLuogo;
      String sDataVerbale = request.getParameter(nameDataVerbale);
      String nameNumeroVerbale = "numeroVerbale_" + idVisitaLuogo;
      String sNumeroVerbale = request.getParameter(nameNumeroVerbale);
      Date dataVerbale = errors.validateOptionalDateInRange(sDataVerbale,
          nameDataVerbale, dataVisita, null, true, true);
      errors.validateOptionalFieldLength(sNumeroVerbale, nameNumeroVerbale, 0,
          20);
      boolean isDataVerbale = !GenericValidator.isBlankOrNull(sDataVerbale);
      boolean isNumeroVerbale = !GenericValidator.isBlankOrNull(sNumeroVerbale);
      if (isDataVerbale != isNumeroVerbale)
      {
        if (isDataVerbale)
        {
          errors.addError(nameNumeroVerbale,
              "Se è presente la data del verbale deve essere inserito anche il numero del verbale");
        }
        else
        {
          errors.addError(nameDataVerbale,
              "Se è presente il numero del verbale deve essere inserita anche la data verbale");
        }
      }
      else
      {
        if (isDataVerbale) // || isNumeroVerbale
        {
          visitaLuogoDTO.setDataVerbale(dataVerbale);
          visitaLuogoDTO.setNumeroVerbale(sNumeroVerbale);
        }
      }
      visitaLuogoDTO.setNote(sNote);
      visitaLuogoDTO.setIdProcedimentoOggetto(idProcedimentoOggetto);
      visitaLuogoDTO.setIdVisitaLuogo(visita.getIdVisitaLuogo());
      visiteDaAggiornare.add(visitaLuogoDTO);
    }
    if (!errors.addToModelIfNotEmpty(model))
    {
      quadroEJB.updateVisiteLuogo(visiteDaAggiornare,
          getLogOperationOggettoQuadroDTO(session));
      return "redirect:../cunembo165d/index.do";
    }
    model.addAttribute("preferRequest", Boolean.TRUE);
    model.addAttribute("visite", visite);
    return "controlliamministrativi/modificaMultiplaVisiteLuogo";
  }

  @IsPopup
  @RequestMapping(value = "/conferma_elimina_visita_luogo_{idVisitaLuogo}", method = RequestMethod.GET)
  public String confermaEliminaVisitaLuogo(Model model,
      @PathVariable("idVisitaLuogo") @ModelAttribute("idVisitaLuogo") long idVisitaLuogo)
      throws InternalUnexpectedException
  {
    List<Long> ids = new ArrayList<Long>();
    ids.add(idVisitaLuogo);
    model.addAttribute("ids", ids);
    return "controlliamministrativi/confermaEliminaVisiteLuogo";
  }

  @IsPopup
  @RequestMapping(value = "/conferma_elimina_visite_luogo", method = RequestMethod.GET)
  public String confermaEliminaVisiteLuogo(Model model,
      HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> ids = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idVisitaLuogo"));
    model.addAttribute("ids", ids);
    return "controlliamministrativi/confermaEliminaVisiteLuogo";
  }

  @IsPopup
  @RequestMapping(value = "/elimina_visite_luogo", method = RequestMethod.POST)
  public String eliminaVisiteLuogo(HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<Long> ids = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idVisitaLuogo"));
    quadroEJB.eliminaVisiteLuogo(ids,
        getLogOperationOggettoQuadroDTO(request.getSession()));
    return "dialog/success";
  }

  @IsPopup
  @RequestMapping(value = "/popup_esito_tecnico", method = RequestMethod.GET)
  public String popupEsitoTecnico(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    QuadroOggettoDTO quadro = po.findQuadroByCU("CU-NEMBO-165-M");
    caricaDecodifichePerTecniciEEsiti(model, po, quadro,
        NemboConstants.ESITO.TIPO.TECNICO_AMMINISTRATIVO,session);
    long idQuadroOggetto = quadro.getIdQuadroOggetto();
    EsitoFinaleDTO esito = quadroEJB
        .getEsitoFinale(po.getIdProcedimentoOggetto(), idQuadroOggetto);
    model.addAttribute("esito", esito);
    return "controlliamministrativi/popupEsitoTecnico";
  }

  @IsPopup
  @RequestMapping(value = "/popup_modifica_esito_tecnico", method = RequestMethod.POST)
  public String popupModificaEsitoTecnico(Model model,
      HttpServletRequest request) throws InternalUnexpectedException
  {

    final HttpSession session = request.getSession();
    final List<DecodificaDTO<Long>> elencoEsiti = quadroEJB
        .getElencoEsiti(NemboConstants.ESITO.TIPO.TECNICO_AMMINISTRATIVO);
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
    return "controlliamministrativi/popupEsitoTecnico";
  }

  @IsPopup
  @RequestMapping(value = "/popup_info_exposts", method = RequestMethod.GET)
  public String popupExposts(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);

    InfoExPostsDTO infoExPosts = quadroEJB
        .getInformazioniExposts(po.getIdProcedimentoOggetto());
    if (infoExPosts == null)
    {
      model.addAttribute("infoExPosts", new InfoExPostsDTO());
    }
    else
    {
      model.addAttribute("infoExPosts", infoExPosts);
    }

    model.addAttribute("elencorischi",
        quadroEJB.getDecodificheRischioElevato());
    model.addAttribute("elencoanni",
        quadroEJB.getDecodificheAnnoExpost(po.getIdProcedimentoOggetto()));

    return "controlliamministrativi/popupInfoExpost";
  }

  @IsPopup
  @RequestMapping(value = "/popup_info_exposts", method = RequestMethod.POST)
  public String popupExpostsPost(Model model, HttpServletRequest request)
      throws InternalUnexpectedException
  {

    final HttpSession session = request.getSession();
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);

    String sIdRischioElevato = request.getParameter("idRischioElevato");
    String[] aIdAnnoExPosts = request.getParameterValues("idAnnoExPosts");
    String sNote = request.getParameter("note");

    Errors errors = new Errors();
    if (GenericValidator.isBlankOrNull(sIdRischioElevato))
    {
      errors.validateOptionalFieldLength(sNote, "note", 0, 4000, true);
    }
    else
    {
      errors.validateMandatoryFieldLength(sNote, 0, 4000, "note", true);
    }

    if (!errors.addToModelIfNotEmpty(model))
    {
      List<AnnoExPostsDTO> anni = new ArrayList<>();

      if (aIdAnnoExPosts != null && aIdAnnoExPosts.length > 0)
      {
        for (String a : aIdAnnoExPosts)
        {
          AnnoExPostsDTO anno = new AnnoExPostsDTO();
          anno.setIdAnnoExPosts(Long.parseLong(a));
          anni.add(anno);
        }
      }

      InfoExPostsDTO info = new InfoExPostsDTO();
      if (!GenericValidator.isBlankOrNull(sIdRischioElevato))
      {
        info.setIdRischioElevato(Long.parseLong(sIdRischioElevato));
      }
      info.setNote(sNote);
      info.setAnniExPosts(anni);
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO = getLogOperationOggettoQuadroDTO(
          session);
      quadroEJB.updateInfoExPost(info, logOperationOggettoQuadroDTO);
      return "dialog/success";
    }
    model.addAttribute("preferRequest", Boolean.TRUE);
    model.addAttribute("elencorischi",
        quadroEJB.getDecodificheRischioElevato());
    model.addAttribute("elencoanni",
        quadroEJB.getDecodificheAnnoExpost(po.getIdProcedimentoOggetto()));

    return "controlliamministrativi/popupInfoExpost";
  }

}