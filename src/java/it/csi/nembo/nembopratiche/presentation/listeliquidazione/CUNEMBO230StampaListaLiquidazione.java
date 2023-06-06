package it.csi.nembo.nembopratiche.presentation.listeliquidazione;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IAsyncEJB;
import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.dto.StampaListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.stampeoggetto.StampaController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo230")
@NemboSecurity(value = "CU-NEMBO-230", controllo = NemboSecurity.Controllo.DEFAULT)
public class CUNEMBO230StampaListaLiquidazione extends StampaController
{

  @Autowired
  IListeLiquidazioneEJB listeLiquidazioneEJB = null;
  @Autowired
  IAsyncEJB             asyncEJB             = null;

  @RequestMapping(value = "/conferma_rigenera_{idListaLiquidazione}")
  public String confermaRigenera(HttpSession session, Model model,
      @PathVariable("idListaLiquidazione") long idListaLiquidazione)
      throws InternalUnexpectedException
  {
    model.addAttribute("idListaLiquidazione", idListaLiquidazione);
    StampaListaLiquidazioneDTO stampaListaLiq = listeLiquidazioneEJB
        .getStampaListaLiquidazione(idListaLiquidazione);
    if (stampaListaLiq.getContenutoFile() != null)
    {
      Errors errors = new Errors();
      errors.addError("error", "La stampa e' attualmente disponibile");
      errors.addToModelIfNotEmpty(model);
    }
    else
    {
      if (stampaListaLiq
          .getIdStatoStampa() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
      {
        if (NemboUtils.DATE.diffInSeconds(new Date(),
            stampaListaLiq
                .getDataUltimoAggiornamento()) < NemboConstants.TEMPO.SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA) // 10
        // minuti
        {
          Errors errors = new Errors();
          errors.addError("error",
              "La stampa richiesta e' attualmente in corso di generazione, l'operazione potrebbe richiedere alcuni minuti, si prega di attendere");
          errors.addToModelIfNotEmpty(model);
        }
        else
        {
          model.addAttribute("messaggio",
              "La stampa è in corso di generazione da più di 10 minuti, &egrave; possibile che si sia verificato un errore, si desidera rigenerarla?");
        }
      }
      else
      {
        model.addAttribute("messaggio",
            "La stampa è terminata con degli errori, &egrave; possibile che il problema fosse solo temporaneo, si desidera provare rigenerare la stampa?");
      }
    }
    return "listeliquidazione/stampa/confermaRigenera";
  }

  @RequestMapping(value = "/rigenera_{idListaLiquidazione}", method = RequestMethod.GET)
  public String rigeneraPost(HttpSession session, Model model,
      @PathVariable("idListaLiquidazione") long idListaLiquidazione)
      throws InternalUnexpectedException
  {

    Boolean daRistampare = listeLiquidazioneEJB
        .ripristinaStampaListaLiquidazione(idListaLiquidazione);

    listeLiquidazioneEJB.setStatoListaLiquidazione(idListaLiquidazione,
        NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO);
    if (Boolean.TRUE.equals(daRistampare))
    {
      // StampaListaLiquidazioneDTO stampaListaLiq =
      // listeLiquidazioneEJB.getStampaListaLiquidazione(idListaLiquidazione);
      asyncEJB.generaStampaListaLiquidazione(idListaLiquidazione);
    }
    return "redirect:/cunembo230/attendere_prego_" + idListaLiquidazione
        + ".do";
  }

  @RequestMapping(value = "/visualizza_{idListaLiquidazione}", method = RequestMethod.POST, produces = "text/html")
  @ResponseBody
  public String visualizzaPost(HttpSession session, Model model,
      @PathVariable("idListaLiquidazione") long idListaLiquidazione)
      throws InternalUnexpectedException, ApplicationException
  {
    StampaListaLiquidazioneDTO stampaListaLiq = listeLiquidazioneEJB
        .getStampaListaLiquidazione(idListaLiquidazione);
    if (stampaListaLiq != null)
    {
      if (listeLiquidazioneEJB
          .isContenutoFileListaLiquidazioneDisponibile(idListaLiquidazione))
      {
        return "<stampa>../cunembo230/visualizza_" + idListaLiquidazione
            + ".do</stampa>";
      }
      else
        if (stampaListaLiq
            .getIdStatoStampa() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
        {
          RigaJSONElencoListaLiquidazioneDTO riga = listeLiquidazioneEJB
              .getListaLiquidazioneById(idListaLiquidazione);
          if (riga.getFlagStatoLista().equals("A"))
          {
            return "<error>La lista &egrave; gi&agrave; stata approvata. Impossibile rigenerare la stampa.</error>";
          }

          if (NemboUtils.DATE.diffInSeconds(new Date(),
              stampaListaLiq
                  .getDataUltimoAggiornamento()) > NemboConstants.TEMPO.SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA) // 10
          // min
          // //
          // minuti
          {
            return "<renew>../cunembo230/conferma_rigenera_"
                + stampaListaLiq.getIdListaLiquidazione() + ".do</renew>";
          }
          else
          {
            return "<error>La stampa richiesta e' attualmente in corso di generazione, l'operazione potrebbe richiedere alcuni minuti, si prega di attendere</error>";
          }
        }
        else
        {
          return "<renew>../cunembo230/conferma_rigenera_"
              + stampaListaLiq.getIdListaLiquidazione() + ".do</renew>";
        }
    }
    else
    {
      return "<error>Errore. File non presente.</error>";
    }

  }

  @RequestMapping(value = "/visualizza_{idListaLiquidazione}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> visualizzaGet(HttpSession session, Model model,
      @PathVariable("idListaLiquidazione") long idListaLiquidazione)
      throws Exception
  {
    StampaListaLiquidazioneDTO stampaListaLiq = listeLiquidazioneEJB
        .getStampaListaLiquidazione(idListaLiquidazione);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type",
        NemboUtils.FILE.getMimeType(stampaListaLiq.getNomeFile()));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"" + stampaListaLiq.getNomeFile() + "\"");
    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
        listeLiquidazioneEJB.getContenutoFileListaLiquidazione(
            idListaLiquidazione),
        httpHeaders, HttpStatus.OK);
    return response;
  }

  @RequestMapping(value = "/attendere_prego_{idListaLiquidazione}")
  public String attenderePrego(HttpSession session, Model model,
      @PathVariable("idListaLiquidazione") long idListaLiquidazione)
      throws InternalUnexpectedException
  {
    StampaListaLiquidazioneDTO stampa = listeLiquidazioneEJB
        .getStampaListaLiquidazione(idListaLiquidazione);
    if (stampa != null)
    {
      if (listeLiquidazioneEJB
          .isContenutoFileListaLiquidazioneDisponibile(idListaLiquidazione))
      {
        model.addAttribute("pdf",
            "../cunembo230/visualizza_" + idListaLiquidazione + ".do");
        return "listeliquidazione/stampa/documentoDisponibile";
      }
      else
      {
        long idStatoStampa = stampa.getIdStatoStampa();
        if (idStatoStampa == NemboConstants.STATO.STAMPA.ID.STAMPA_FALLITA)
        {
          model.addAttribute("messaggio",
              "La generazione della stampa &egrave; fallita");
          return "listeliquidazione/stampa/errore";
        }
      }
    }
    else
    {
      model.addAttribute("messaggio",
          "La stampa richiesta non &egrave; stata trovata. E' possibile che sia stata cancellata da qualche altro utente");
      return "listeliquidazione/stampa/errore";
    }
    return "listeliquidazione/stampa/attenderePrego";
  }

}
