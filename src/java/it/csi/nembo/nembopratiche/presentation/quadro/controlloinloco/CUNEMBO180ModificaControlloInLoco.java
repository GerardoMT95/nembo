package it.csi.nembo.nembopratiche.presentation.quadro.controlloinloco;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import it.csi.nembo.nembopratiche.dto.ProcedimentoEstrattoVO;
import it.csi.nembo.nembopratiche.dto.Radio;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-180", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo180")
public class CUNEMBO180ModificaControlloInLoco extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public final String index(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    ProcedimentoEstrattoVO procEstratto = quadroEjb.getProcedimentoEstratto(
        procedimentoOggetto.getIdProcedimento(),
        procedimentoOggetto.getIdProcedimentoOggetto());

    model.addAttribute("procedimentoEstratto", procEstratto);

    LinkedList<LivelloDTO> livelli = quadroEjb.getLivelliControlloInLoco(
        procedimentoOggetto.getIdProcedimento(),
        procedimentoOggetto.getIdProcedimentoOggetto());
	List<DecodificaDTO<Long>> elencoTecnici = quadroEjb.getElencoTecniciDisponibiliPerAmmCompetenza(procedimentoOggetto.getIdProcedimentoOggetto(),getUtenteAbilitazioni(session).getIdProcedimento());
	List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();


    List<Radio> radio = new LinkedList<>();
    radio.add(new Radio("S", "S�"));
    radio.add(new Radio("N", "No"));

    List<LivelloDTO> livelliPossibili = quadroEjb
        .getLivelliProcOggetto(procedimentoOggetto.getIdProcedimento());
    model.addAttribute("numeroMassimoLivelli", livelliPossibili.size());

    model.addAttribute("livelliPossibili", livelliPossibili);
    model.addAttribute("radio", radio);
    model.addAttribute("funzionari", elencoTecnici);
	model.addAttribute("ufficiZona", ufficiZona);
    model.addAttribute("operazioni", livelli);

    return "controlloinloco/modificaControlloInLoco";

  }

  @SuppressWarnings("unused")
  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public final String conferma(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {

    Errors errors = new Errors();
    model.addAttribute("preferValuesProva", Boolean.TRUE);
    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);

    String numeroRighe = request.getParameter("numeroRighe");
    int numeroOperazioniInserite = Integer.parseInt(numeroRighe) + 1;
    LinkedList<LivelloDTO> livelli = new LinkedList<>();
    List<LivelloDTO> livelliPossibili = quadroEjb
        .getLivelliProcOggetto(procedimentoOggetto.getIdProcedimento());
    model.addAttribute("numeroMassimoLivelli", livelliPossibili.size());

    for (int i = 0; i < numeroOperazioniInserite - 1; i++)
    {
      LivelloDTO liv = new LivelloDTO();

      String idOperazione = request.getParameter("operazioni_" + i);

      boolean menoUno = false;
      if (idOperazione != null && idOperazione.not.equals(""))
      {
        idOperazione = idOperazione.trim();
        liv.setIdLivello(Long.parseLong(idOperazione));
      }
      if (idOperazione.compareTo("-1") == 0)
      {
        idOperazione = "";
        menoUno = true;
      }
      Long idOp = errors.validateMandatoryLong(idOperazione, "operazioni_" + i);

      if (menoUno)
        idOp = (long) -1;
      String idControlloInLoco = request.getParameter("controlloInLoco_" + i);
      liv.setFlagControllo(idControlloInLoco);

      // il flag � obbligatorio - Se vale X vuol dire che non � stato
      // selezionato -> X � il valore del radio button di default nascosto
      if (idControlloInLoco == null)
        idControlloInLoco = "X";
      if (idControlloInLoco.compareTo("X") == 0)
      {
        idControlloInLoco = "";
        errors.validateMandatoryLong(idControlloInLoco, "controlloInLoco_" + i);
      }

      Date dataInizioControllo = null, dataSoprall = null;

      String dataInizio = request.getParameter("dataInizio_" + i);
      if (dataInizio != null && dataInizio.not.equals(""))
      {
        dataInizioControllo = errors.validateDate(dataInizio, "dataInizio_" + i,
            true);
        if (dataInizioControllo != null)
          errors.validateDateInRange(dataInizioControllo, "dataInizio_" + i,
                null, Calendar.getInstance().getTime(), true, true);
        liv.setDataInizioControllo(NemboUtils.DATE.parseDate(dataInizio));
      }

      String dataSopralluogo = request.getParameter("dataSopralluogo_" + i);
      if (dataSopralluogo != null && dataSopralluogo.not.equals(""))
      {
        dataSoprall = errors.validateDate(dataSopralluogo,
            "dataSopralluogo_" + i, true);
        if (dataSoprall != null)
          if (errors.validateDateInRange(dataSoprall, "dataSopralluogo_" + i,
              dataInizioControllo, Calendar.getInstance().getTime(), true,
              true))
            liv.setDataSopralluogo(
                NemboUtils.DATE.parseDate(dataSopralluogo));
      }

      String verbale = request.getParameter("verbale_" + i);
      if (verbale != null && verbale.not.equals(""))
      {
        liv.setNumeroVerbale(verbale);
        errors.validateFieldMaxLength(verbale, "verbale_" + i, 50);
      }
      String funz = request.getParameter("funzionario_" + i);
      Long idFunzionario;
      if (funz == null || funz.equals(""))
        idFunzionario = null;
      else
        idFunzionario = Long.parseLong(funz);

      liv.setExtIdTecnico(idFunzionario);

      String motivazione = request.getParameter("motivazione_" + i);
      liv.setMotivazione(motivazione);
      errors.validateFieldLength(motivazione, "motivazione_" + i, 0, 4000);
      if (idControlloInLoco.compareTo("N") == 0)
      {
        errors.validateMandatory(motivazione, "motivazione_" + i);
      }

      for (LivelloDTO l : livelliPossibili)
        if (l.getIdLivello() == liv.getIdLivello())
        {
          liv.setDescrizione(l.getDescrizione());
          liv.setCodiceLivello(l.getCodiceLivello());
        }

      // valido ultimi flag
      String flagInadVinc = request.getParameter("inadempVincolata_" + i);
      String noteInadVinc = request.getParameter("noteInadempVincolata_" + i);
      if (flagInadVinc == null)
        flagInadVinc = "X";
      if (flagInadVinc.compareTo("X") == 0)
      {
        flagInadVinc = "";
      }
      if (flagInadVinc.compareTo("S") == 0)
      {
        errors.validateMandatory(noteInadVinc, "noteInadempVincolata_" + i);
      }
      errors.validateFieldLength(noteInadVinc, "noteInadempVincolata_" + i, 0,
          4000);

      String flagInadCondiz = request.getParameter("inadempCondizionata_" + i);
      String noteInadCondiz = request
          .getParameter("noteInadempCondizionata_" + i);
      if (flagInadCondiz == null)
        flagInadCondiz = "X";
      if (flagInadCondiz.compareTo("X") == 0)
      {
        flagInadCondiz = "";
      }
      if (flagInadCondiz.compareTo("S") == 0)
      {
        errors.validateMandatory(noteInadCondiz,
            "noteInadempCondizionata_" + i);
      }
      errors.validateFieldLength(noteInadCondiz, "noteInadempCondizionata_" + i,
          0, 4000);

      // Se il controllo in loco � SI, la dataInizioControllo tutto il resto �
      // obbligatorio
      if (idControlloInLoco.compareTo("S") == 0)
      {
        dataInizioControllo = errors.validateMandatoryDate(dataInizio,
            "dataInizio_" + i, true);
        dataSoprall = errors.validateMandatoryDate(dataSopralluogo,
            "dataSopralluogo_" + i, true);
        errors.validateMandatory(verbale, "verbale_" + i);
        errors.validateMandatoryLong(funz, "funzionario_" + i);
      }
      else
      {
        if (dataInizio != null && dataInizio.not.equals(""))
          errors.addError("dataInizio_" + i,
              "Impossibile inserire la data se il controllo in loco non � stato effettuato.");
        if (dataSopralluogo != null && dataSopralluogo.not.equals(""))
          errors.addError("dataSopralluogo_" + i,
              "Impossibile inserire la data se il controllo in loco non � stato effettuato.");
        if (verbale != null && verbale.not.equals(""))
          errors.addError("verbale_" + i,
              "Impossibile inserire il verbale se il controllo in loco non � stato effettuato.");
        if (funz != null && funz.not.equals(""))
          errors.addError("funzionario_" + i,
              "Impossibile inserire il funzionario se il controllo in loco non � stato effettuato.");

      }

      liv.setFlagInadempCondizionata(flagInadCondiz);
      liv.setFlagInadempVincolata(flagInadVinc);
      liv.setNoteInadempCondizionata(noteInadCondiz);
      liv.setNoteInadempVincolata(noteInadVinc);
      livelli.add(liv);
    }

    if (!errors.isEmpty())
    {
      ProcedimentoEstrattoVO procEstratto = quadroEjb.getProcedimentoEstratto(
          procedimentoOggetto.getIdProcedimento(),
          procedimentoOggetto.getIdProcedimentoOggetto());

      model.addAttribute("procedimentoEstratto", procEstratto);

      model.addAttribute("errors", errors);

      List<Radio> radio = new LinkedList<>();
      radio.add(new Radio("S", "S�"));
      radio.add(new Radio("N", "No"));

      model.addAttribute("numeroMassimoLivelli", livelliPossibili.size());
      model.addAttribute("livelliPossibili", livelliPossibili);
      model.addAttribute("radio", radio);
	  List<DecodificaDTO<Long>> elencoTecnici = quadroEjb.getElencoTecniciDisponibiliPerAmmCompetenza(procedimentoOggetto.getIdProcedimentoOggetto(),getUtenteAbilitazioni(session).getIdProcedimento());
	  List<DecodificaDTO<String>> ufficiZona = getListUfficiZonaFunzionari();
	  model.addAttribute("funzionari", elencoTecnici);
	  model.addAttribute("ufficiZona", ufficiZona);
      model.addAttribute("operazioni", livelli);

      return "controlloinloco/modificaControlloInLoco";

    }

    quadroEjb.eliminaControlloInLoco(
        procedimentoOggetto.getIdProcedimentoOggetto(),
        getLogOperationOggettoQuadroDTO(session));

    for (LivelloDTO l : livelli)
      quadroEjb.inserisciRecordControlloInLoco(l,
          procedimentoOggetto.getIdProcedimentoOggetto(),
          getLogOperationOggettoQuadroDTO(session));

    return "redirect:../cunembo179/index.do";
  }

  @RequestMapping(value = "popupindex", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session)
      throws InternalUnexpectedException
  {

    setModelDialogWarning(model,
        "La pratica non risulta estratta a campione per controllo il loco; se si intende proseguire la pratica verr� marchiata come estratta \"Manuale per verifica impegni OD.\"",
        "../cunembo180/popupindex.do");
    return "dialog/conferma";
  }

  @IsPopup
  @RequestMapping(value = "popupindex", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    final ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromSession(
        session);
    ProcedimentoEstrattoVO procEstratto = quadroEjb.getProcedimentoEstratto(
        procedimentoOggetto.getIdProcedimento(),
        procedimentoOggetto.getIdProcedimentoOggetto());
    quadroEjb.updateFlagEstrazione(procEstratto.getIdProcedimentoEstratto());
    return "redirect:../cunembo180/index.do";
  }
}
