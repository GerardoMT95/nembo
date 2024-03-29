package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.INuovoProcedimentoEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.GruppoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.ControlloDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo135")
@NemboSecurity(value = "CU-NEMBO-135", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO135NuovaIstanzaController extends BaseController
{

  @Autowired
  private IRicercaEJB           ricercaEJB           = null;

  @Autowired
  private IQuadroEJB            quadroEJB            = null;

  @Autowired
  private INuovoProcedimentoEJB nuovoProcedimentoEJB = null;

  @RequestMapping(value = "popupindex_{idGruppoOggetto}_{codRaggruppamento}", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpServletRequest request,
      @PathVariable("idGruppoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idGruppoOggetto,
      @PathVariable("codRaggruppamento") @NumberFormat(style = NumberFormat.Style.NUMBER) long codRaggruppamento)
      throws InternalUnexpectedException
  {
    Procedimento procedimento = NemboFactory.getProcedimento(request);
    List<GruppoOggettoDTO> elenco = ricercaEJB.getElencoOggettiDisponibili(
        procedimento.getIdBando(), true,
        ((idGruppoOggetto > 0) ? idGruppoOggetto : null));

    if (procedimento.getIdStatoOggetto() < 10
        || procedimento.getIdStatoOggetto() > 90)
    {
      model.addAttribute("noConfirm", Boolean.TRUE);
      model.addAttribute("msgErrore",
          "Il procedimento selezionato si trova in uno stato per cui non � possibile creare alcuna Istanza.");

    }
    else
      if (elenco != null && elenco.isNotEmpty())
      {
        model.addAttribute("elenco", elenco);
      }
      else
      {
        model.addAttribute("noConfirm", Boolean.TRUE);
        model.addAttribute("msgErrore",
            "Non � possibile creare alcuna Istanza sul procedimento selezionato.");
      }

    model.addAttribute("descrizioneTipo", "Istanza");
    return "ricercaprocedimento/popupNuovoOggetto";
  }

  @RequestMapping(value = "popupindex_{idGruppoOggetto}_{codRaggruppamento}", method = RequestMethod.POST)
  @ResponseBody
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request,
      @PathVariable("idGruppoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idGruppoOggetto,
      @PathVariable("codRaggruppamento") @NumberFormat(style = NumberFormat.Style.NUMBER) long codRaggruppamento,
      @RequestParam(value="note") String note)
      throws InternalUnexpectedException
  {
	 if(note!=null && note.length()>4000){
    	return "Le note non possono superare i 4000 caratteri (attualmente il campo note contiene "+note.length()+" caratteri).";
    }
    String idBandoOggettoStr = request.getParameter("oggettoSelezionato");
    
    if (GenericValidator.isBlankOrNull(idBandoOggettoStr))
    {
      return "Non hai selezionato nessuna istanza!";
    }
    else
    {
      UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
          .getAttribute("utenteAbilitazioni");
      String idLegameGruppoOggettoStr = request
          .getParameter("idLegameGruppoOggetto_" + idBandoOggettoStr);
      long idLegameGruppoOggetto = Long.parseLong(idLegameGruppoOggettoStr);
      long idBandoOggetto = Long.parseLong(idBandoOggettoStr);

      List<String> elencoCDU = quadroEJB.getMacroCDUList(idLegameGruppoOggetto);
      boolean abilitato = false;
      if (elencoCDU != null)
      {
        for (String cdu : elencoCDU)
        {
          if (NemboUtils.PAPUASERV.isMacroCUAbilitato(utenteAbilitazioni,
              cdu))
          {
            abilitato = true;
            break;
          }
        }
      }

      if (!abilitato)
      {
        return " <div class=\"stdMessagePanel\"> 																			\n"
            + " 	<div class=\"alert alert-danger\">																		\n"
            + "  	<p><strong>Attenzione!</strong><br/>																\n"
            + "   	Non &egrave; possibile creare l'oggetto selezionato in base al tipo di utente connesso!</p>				\n"
            + " 	</div> 																									\n"
            + " </div> 																									\n";
      }

      // in questo caso effettuo tutte le chiamate per creare il procedimento
      Procedimento procedimento = NemboFactory.getProcedimento(request);
      AziendaDTO aziendaDTO = quadroEJB
          .getDatiAziendaAgricolaProcedimento(getIdProcedimento(session), null);

      aggiornaDatiAAEPSian(aziendaDTO, utenteAbilitazioni);

      // chiamo callMainControlliGravi
      MainControlloDTO controlliGravi = nuovoProcedimentoEJB
          .callMainControlliGravi(idBandoOggetto,
              procedimento.getIdProcedimento(), aziendaDTO.getIdAzienda(),
              utenteAbilitazioni.getIdUtenteLogin(), codRaggruppamento);

      if (controlliGravi
          .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO)
      {
        return NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_CRITICO + " "
            + NemboUtils.STRING.safeHTMLText(controlliGravi.getMessaggio());
      }
      else
        if (controlliGravi
            .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_GRAVE)
        {
          return createHTMLControlli(controlliGravi);
        }

      // chiamo il pl che crea il procedimento
      controlliGravi = nuovoProcedimentoEJB.callMainCreazione(
          procedimento.getIdBando(),
          idLegameGruppoOggetto,
          procedimento.getIdProcedimento(),
          aziendaDTO.getIdAzienda(),
          utenteAbilitazioni.getIdUtenteLogin(),
          NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni),
          (codRaggruppamento > 0) ? codRaggruppamento : null, Boolean.FALSE, note);
      if (controlliGravi
          .getRisultato() == NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE)
      {
        return "redirect:../procedimento/visualizza_procedimento_"
            + procedimento.getIdProcedimento() + ".do";
      }
      else
        if (controlliGravi
            .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO)
        {
          return NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_CRITICO + " "
              + NemboUtils.STRING
                  .safeHTMLText(controlliGravi.getMessaggio());
        }
        else
          if (controlliGravi
              .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_GRAVE)
          {
            return NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_GRAVE + " "
                + NemboUtils.STRING
                    .safeHTMLText(controlliGravi.getMessaggio());
          }
          else
          {
            return NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_CRITICO + " "
                + NemboUtils.STRING
                    .safeHTMLText(controlliGravi.getMessaggio());
          }
    }
  }

  private String createHTMLControlli(MainControlloDTO controlliGravi)
  {
    StringBuilder sb = new StringBuilder(""
        + " <div class=\"stdMessagePanel\"> 																			\n"
        + " 	<div class=\"alert alert-danger\">																		\n"
        + "  	<p><strong>Attenzione!</strong><br/>																\n"
        + "   	Operazione non consentita a causa del rilevamento delle seguenti anomalie:</p>						\n"
        + " 	</div> 																									\n"
        + " </div> 																									\n"
        + " <table summary=\"controlliGravi\" class=\"table table-hover table-striped table-bordered tableBlueTh\"> 	\n"
        + " <thead> 																									\n"
        + "   <tr>  																									\n"
        + " 	<th>Codice</th> 																						\n"
        + " 	<th>Descrizione</th> 																					\n"
        + " 	<th>Messaggio</th> 																						\n"
        + "   </tr> 																									\n"
        + " </thead> 																								\n"
        + " <tbody> 																									\n");

    for (ControlloDTO controllo : controlliGravi.getControlli())
    {
      sb.append(" "
          + " <tr> \n"
          + " 	<td scope=\"col\" >" + controllo.getCodice() + "</td> \n"
          + " 	<td scope=\"col\" >"
          + NemboUtils.STRING.safeHTMLText(controllo.getDescrizione())
          + "</td> \n"
          + " 	<td scope=\"col\" >"
          + NemboUtils.STRING.safeHTMLText(controllo.getMessaggioErrore())
          + "</td> \n"
          + " </tr> \n");

    }
    sb.append("</tbody> </table>");
    return sb.toString();
  }

}
