package it.csi.nembo.nembopratiche.presentation.quadro.datiidentificativi;

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
@RequestMapping("/cunembo173")
@NemboSecurity(value = "CU-NEMBO-173", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public class CUNEMBO173ModificaDatiIdentificativiIstruttoria
    extends BaseController
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

    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(
        NemboConstants.USECASE.DATI_IDENTIFICATIVI.MODIFICA_ISTRUTTORIA);
    DatiIdentificativi dati = quadroEJB
        .getDatiIdentificativiProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    model.addAttribute("azienda", dati.getAzienda());
    model.addAttribute("rappLegale", dati.getRappLegale());
    model.addAttribute("soggFirmatario", dati.getSoggFirmatario());
    model.addAttribute("datiProcedimento", dati.getDatiProcedimento());
    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB,
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(),
            procedimentoOggetto.getIdBandoOggetto()));

    model.addAttribute("idContitolare", modifica.getIdContitolare());

    String note = modifica.getNote();
    model.addAttribute("note", note);

    String cup = dati.getDatiProcedimento().getCodiceCup();
    model.addAttribute("cup", cup);

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

    long idBando = getProcedimentoFromSession(session).getIdBando();
    // se questa lista se è vuota non visualizzo la combo
    List<SettoriDiProduzioneDTO> settoriDiProduzioneInLivelliBandi = quadroEJB
        .getSettoriDiProduzioneInLivelliBandi(
            procedimentoOggetto.getIdProcedimentoOggetto(), idBando);
    model.addAttribute("settoriDiProduzioneInLivelliBandi",
        settoriDiProduzioneInLivelliBandi);

    if (quadroEJB.isIstruttoriaInCorsoMisuraPremio(
        procedimentoOggetto.getIdProcedimento()))
    {
      model.addAttribute("visualizzaAvvioIstruttoria", Boolean.TRUE);
    }

    if (quadroEJB
        .bandoHasLivWithVERCOD(getProcedimentoFromSession(session).getIdBando())
        && "ISAMM".equals(procedimentoOggetto.getCodOggetto()))
      model.addAttribute("showVercod", true);

    return "datiidentificativi/modificaDatiIstruttoria";
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public String post(Model model,
      @RequestParam(value = "idAmmCompetenza", required = false) String idAmmCompetenza,
      @RequestParam(value = "idContitolare", required = false) String idContitolare,
      @RequestParam(value = "note", required = false) String note,
      @RequestParam(value = "idSettore", required = false) String idSettore,
      @RequestParam(value = "cup", required = false) String cup,
      @RequestParam(value = "chkAvvioIstruttoria", required = false) String chkAvvioIstruttoria,
      @RequestParam(value = "vercod", required = false) String vercodStr,
      @RequestParam(value = "dataVisuraCamerale", required = false) String dataVisuraCameraleStr,
      HttpSession session)
      throws InternalUnexpectedException, NemboPermissionException,
      ApplicationException
  {

    model.addAttribute("prfvalues", Boolean.TRUE);

    Errors errors = new Errors();
    Long lIdAmmCompetenza = null;
    ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);

    note = note.trim();
    cup = cup.trim();

    errors.validateFieldMaxLength(note, "note", 4000);
    if (cup.length() > 0)
      errors.validateFieldLength(cup, "cup", 15, 15);

    long idBando = getProcedimentoFromSession(session).getIdBando();
    Long idSettoreLong = null;

    // se questa lista è vuota non visualizzo la combo - altrimenti si
    List<SettoriDiProduzioneDTO> settoriDiProduzioneInLivelliBandi = quadroEJB
        .getSettoriDiProduzioneInLivelliBandi(
            procedimentoOggetto.getIdProcedimentoOggetto(), idBando);
    // questi sono quelli da visualizzare nella combo
    List<SettoriDiProduzioneDTO> settoriDiProduzione = quadroEJB
        .getSettoriDiProduzioneProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    if (settoriDiProduzioneInLivelliBandi != null
        && !settoriDiProduzioneInLivelliBandi.isEmpty()
        && settoriDiProduzione != null
        && !settoriDiProduzione.isEmpty())
    {
      idSettoreLong = errors.validateMandatoryLong(idSettore, "idSettore");
    }

    // valido VERCOD e data
    boolean hasVercod = quadroEJB
        .bandoHasLivWithVERCOD(getProcedimentoFromSession(session).getIdBando())
        && "ISAMM".equals(procedimentoOggetto.getCodOggetto());
    Long vercod = null;
    Date dataVisuraCamerale = null;
    if (hasVercod)
    {
      vercod = errors.validateOptionalLong(vercodStr, "vercod");
      dataVisuraCamerale = errors.validateOptionalDate(dataVisuraCameraleStr,
          "dataVisuraCamerale", true);
    }

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
      if (quadroEJB.bandoHasLivWithVERCOD(
          getProcedimentoFromSession(session).getIdBando())
          && "ISAMM".equals(procedimentoOggetto.getCodOggetto()))
        model.addAttribute("showVercod", true);
    }
    else
    {

      QuadroOggettoDTO q = procedimentoOggetto.findQuadroByCU(
          NemboConstants.USECASE.DATI_IDENTIFICATIVI.MODIFICA_ISTRUTTORIA);
      quadroEJB.updateDatiProcedimentoOggetto(
          procedimentoOggetto.getIdProcedimentoOggetto(),
          q.getIdQuadroOggetto(),
          procedimentoOggetto.getIdBandoOggetto(),
          lIdAmmCompetenza == null ? null : lIdAmmCompetenza.intValue(), note,
          getIdUtenteLogin(session), idContitolare, idSettoreLong, cup,
          chkAvvioIstruttoria, hasVercod, vercod, dataVisuraCamerale);
      return "redirect:../cunembo172/index.do";
    }

    DatiIdentificativiModificaDTO modifica = quadroEJB
        .getDatiIdentificativiModificaProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto());

    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU(
        NemboConstants.USECASE.DATI_IDENTIFICATIVI.DETTAGLIO_ISTRUTTORIA);
    DatiIdentificativi dati = quadroEJB
        .getDatiIdentificativiProcedimentoOggetto(
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(), procedimentoOggetto.getDataFine());
    model.addAttribute("azienda", dati.getAzienda());
    model.addAttribute("rappLegale", dati.getRappLegale());
    model.addAttribute("soggFirmatario", dati.getSoggFirmatario());
    model.addAttribute("datiProcedimento", dati.getDatiProcedimento());
    model.addAttribute("ultimaModifica",
        getUltimaModifica(quadroEJB,
            procedimentoOggetto.getIdProcedimentoOggetto(),
            quadro.getIdQuadroOggetto(),
            procedimentoOggetto.getIdBandoOggetto()));
    model.addAttribute("idContitolare", modifica.getIdContitolare());
    model.addAttribute("note", note);
    model.addAttribute("cup", cup);

    if (modifica.getSettore() != null)
    {
      String descrizioneSettore = modifica.getSettore().getDescrizione();
      model.addAttribute("descrizioneSettore", descrizioneSettore);
      model.addAttribute("settoreSelezionato", modifica.getSettore());
      model.addAttribute("idSettoreSelezionato",
          modifica.getSettore().getIdSettore());
    }

    model.addAttribute("settoriDiProduzione", settoriDiProduzione);
    model.addAttribute("settoriDiProduzioneInLivelliBandi",
        settoriDiProduzioneInLivelliBandi);
    if (quadroEJB.isIstruttoriaInCorsoMisuraPremio(
        procedimentoOggetto.getIdProcedimento()))
    {
      model.addAttribute("visualizzaAvvioIstruttoria", Boolean.TRUE);
    }
    return "datiidentificativi/modificaDatiIstruttoria";
  }
}