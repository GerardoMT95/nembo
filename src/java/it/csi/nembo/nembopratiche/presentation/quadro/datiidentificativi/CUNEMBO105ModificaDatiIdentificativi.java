package it.csi.nembo.nembopratiche.presentation.quadro.datiidentificativi;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaAmmCompentenza;
import it.csi.nembo.nembopratiche.dto.DecodificaSoggFirmatario;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativiModificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.SettoriDiProduzioneDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo105")
@NemboSecurity(value = "CU-NEMBO-105", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO105ModificaDatiIdentificativi extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String get(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    DatiIdentificativiModificaDTO modifica = quadroEJB
        .getDatiIdentificativiModificaProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    QuadroOggettoDTO quadro = procedimentoOggetto
        .findQuadroByCU(NemboConstants.USECASE.DATI_IDENTIFICATIVI.MODIFICA);
    DatiIdentificativi dati = quadroEJB
        .getDatiIdentificativiProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    model.addAttribute("azienda", dati.getAzienda());
    model.addAttribute("rappLegale", dati.getRappLegale());
    model.addAttribute("soggFirmatario", dati.getSoggFirmatario());
    model.addAttribute("datiProcedimento", dati.getDatiProcedimento());
    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEJB,
        procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));
    if (modifica.getSettore() != null)
    {
      String descrizioneSettore = modifica.getSettore().getDescrizione();
      model.addAttribute("descrizioneSettore", descrizioneSettore);
      model.addAttribute("settoreSelezionato", modifica.getSettore());
      model.addAttribute("idSettoreSelezionato",
          modifica.getSettore().getIdSettore());
    }

    // questi sono quelli da visualizzare nella combo
    List<SettoriDiProduzioneDTO> settoriDiProduzione = quadroEJB
        .getSettoriDiProduzioneProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    model.addAttribute("settoriDiProduzione", settoriDiProduzione);

    // questa lista, invece, se è vuota non visualizzo la combo - altrimenti si
    long idBando = getProcedimentoFromSession(session).getIdBando();
    List<SettoriDiProduzioneDTO> settoriDiProduzioneInLivelliBandi = quadroEJB
        .getSettoriDiProduzioneInLivelliBandi(
            procedimentoOggetto.getIdProcedimentoOggetto(), idBando);
    model.addAttribute("settoriDiProduzioneInLivelliBandi",
        settoriDiProduzioneInLivelliBandi);

    return defaultView(procedimentoOggetto.getIdProcedimentoOggetto(), model,
        session, modifica.getIdAmmCompetenza(), modifica.getIdContitolare(),
        modifica.getNote());
  }

  public String defaultView(long idProcedimentoOggetto, Model model,
      HttpSession session, Long idAmmCompetenza, Long idContitolare,
      String note) throws InternalUnexpectedException
  {
    Procedimento procedimento = getProcedimentoFromSession(session);
    List<DecodificaAmmCompentenza> listAmmCompetenza = quadroEJB
        .getListAmmCompetenzaAbilitateBando(procedimento.getIdBando());
    List<DecodificaSoggFirmatario> listSoggFirmatari = quadroEJB
        .getListSoggettiFirmatari(idProcedimentoOggetto);
    model.addAttribute("idAmmCompetenza", idAmmCompetenza);
    model.addAttribute("idContitolare", idContitolare);
    model.addAttribute("note", note);
    Procedimento p = quadroEJB.getProcedimento(
        getProcedimentoFromSession(session).getIdProcedimento());
    boolean modAmmCompetenza = p
        .getIdStatoOggetto() == NemboConstants.STATO.ITER.ID.IN_BOZZA;
    model.addAttribute("modAmmCompetenza", modAmmCompetenza);
    model.addAttribute("listAmmCompetenza", listAmmCompetenza);

    if (listSoggFirmatari != null && !listSoggFirmatari.isEmpty())
    {
      DecodificaSoggFirmatario titolareRappLegale = listSoggFirmatari.get(0);
      model.addAttribute("infoTitolareRL", titolareRappLegale.getDescrizione());
      listSoggFirmatari.remove(0);
    }
    model.addAttribute("listSoggFirmatari", listSoggFirmatari);

    return "datiidentificativi/modificaDati";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public String post(Model model,
      @RequestParam(value = "idAmmCompetenza", required = false) String idAmmCompetenza,
      @RequestParam(value = "idContitolare", required = false) String idContitolare,
      @RequestParam(value = "note", required = false) String note,
      @RequestParam(value = "idSettore", required = false) String idSettore,
      HttpSession session)
      throws InternalUnexpectedException, NemboPermissionException,
      ApplicationException
  {
    model.addAttribute("prfvalues", Boolean.TRUE); // ricarica i valori in
                                                   // pagina (quelli
                                                   // precedentemente
                                                   // selezionati)
    Errors errors = new Errors();
    Long lIdAmmCompetenza = null;
    Procedimento procedimento = getProcedimentoFromSession(session);
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    if (procedimento
        .getIdStatoOggetto() == NemboConstants.STATO.ITER.ID.IN_BOZZA)
    {
      lIdAmmCompetenza = errors.validateMandatoryLong(idAmmCompetenza,
          "idAmmCompetenza");
    }

    note = note.trim();
    errors.validateFieldMaxLength(note, "note", 4000);
    // questa lista, invece, se è vuota non visualizzo la combo - altrimenti si
    long idBando = getProcedimentoFromSession(session).getIdBando();
    Long idSettoreLong = null;
    List<SettoriDiProduzioneDTO> settoriDiProduzioneInLivelliBandi = quadroEJB
        .getSettoriDiProduzioneInLivelliBandi(
            procedimentoOggetto.getIdProcedimentoOggetto(), idBando);
    if (settoriDiProduzioneInLivelliBandi != null
        && !settoriDiProduzioneInLivelliBandi.isEmpty())
    {
      idSettoreLong = errors.validateMandatoryLong(idSettore, "idSettore");
    }
    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
    }
    else
    {
      QuadroOggettoDTO q = procedimentoOggetto.findQuadroByCU(
          NemboConstants.USECASE.DATI_IDENTIFICATIVI.MODIFICA);
      quadroEJB.updateDatiProcedimentoOggetto(
          procedimentoOggetto.getIdProcedimentoOggetto(),
          q.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto(),
          lIdAmmCompetenza == null ? null : lIdAmmCompetenza.intValue(), note,
          getIdUtenteLogin(session), idContitolare, idSettoreLong, null, null,
          false, null, null);
      return "redirect:../cunembo103/index.do";
    }

    DatiIdentificativiModificaDTO modifica = quadroEJB
        .getDatiIdentificativiModificaProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(
        NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO);
    DatiIdentificativi dati = quadroEJB
        .getDatiIdentificativiProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    model.addAttribute("azienda", dati.getAzienda());
    model.addAttribute("rappLegale", dati.getRappLegale());
    model.addAttribute("soggFirmatario", dati.getSoggFirmatario());
    model.addAttribute("datiProcedimento", dati.getDatiProcedimento());
    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEJB,
        procedimentoOggetto.getIdProcedimentoOggetto(),
        quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));

    if (modifica.getSettore() != null)
    {
      String descrizioneSettore = modifica.getSettore().getDescrizione();
      model.addAttribute("descrizioneSettore", descrizioneSettore);
      model.addAttribute("settoreSelezionato", modifica.getSettore());
      model.addAttribute("idSettoreSelezionato",
          modifica.getSettore().getIdSettore());
    }
    // questi sono quelli da visualizzare nella combo
    List<SettoriDiProduzioneDTO> settoriDiProduzione = quadroEJB
        .getSettoriDiProduzioneProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    model.addAttribute("settoriDiProduzione", settoriDiProduzione);

    model.addAttribute("settoriDiProduzioneInLivelliBandi",
        settoriDiProduzioneInLivelliBandi);
    model.addAttribute("idAmmCompetenza", idAmmCompetenza);
    model.addAttribute("note", note);
    Procedimento p = quadroEJB
        .getProcedimento(procedimentoOggetto.getIdProcedimento());
    boolean modAmmCompetenza = p
        .getIdStatoOggetto() == NemboConstants.STATO.ITER.ID.IN_BOZZA;
    model.addAttribute("modAmmCompetenza", modAmmCompetenza);

    Long lIdContitolare = null;
    if (!GenericValidator.isBlankOrNull(idContitolare))
    {
      lIdContitolare = Long.parseLong(idContitolare);
    }

    return defaultView(procedimentoOggetto.getIdProcedimentoOggetto(), model,
        session, lIdAmmCompetenza, lIdContitolare, note);
  }
}