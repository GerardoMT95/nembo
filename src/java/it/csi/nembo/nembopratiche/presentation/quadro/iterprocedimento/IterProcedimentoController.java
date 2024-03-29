package it.csi.nembo.nembopratiche.presentation.quadro.iterprocedimento;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.IterProcedimentoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@NemboSecurity(value = "CU-NEMBO-104", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class IterProcedimentoController extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB = null;

  @RequestMapping(value = "/cunembo104/index", method = RequestMethod.POST)
  @IsPopup
  @ResponseBody
  public String elencoIterHtml(Model model, HttpSession session)
      throws InternalUnexpectedException
  {
    String codDomanda = "";
    long idProcedimento = getIdProcedimento(session);
    List<AziendaDTO> aziende = quadroEJB
        .getAziendeProcedimentoList(idProcedimento);
    List<AmmCompetenzaDTO> ammCompetenzaDTO = quadroEJB
        .getAmmCompetenzaList(idProcedimento);
    ProcedimentoDTO procedimentoDTO = quadroEJB
        .getIterProcedimento(idProcedimento);

    List<Long> idUtenti = new ArrayList<>();
    if (aziende != null)
    {
      for (AziendaDTO azienda : aziende)
      {
        idUtenti.add(azienda.getExtIdUtenteAggiornamento());
      }
    }

    if (ammCompetenzaDTO != null)
    {
      for (AmmCompetenzaDTO amm : ammCompetenzaDTO)
      {
        idUtenti.add(amm.getExtIdUtenteAggiornamento());
      }
    }

    for (ProcedimentoOggettoDTO procOggetto : procedimentoDTO
        .getProcedimentoOggetto())
    {
      for (IterProcedimentoOggettoDTO iter : procOggetto
          .getIterProcedimentoggetto())
      {
        idUtenti.add(iter.getExtIdUtenteAggiornamento());
      }
    }

    List<UtenteLogin> utentiList = loadRuoloDescr(idUtenti);

    StringBuffer sb = new StringBuffer("<div class=\"panel-body\">");
    sb.append("<h4> Aziende collegate al procedimento</h4>");

    if (aziende != null)
    {
      for (AziendaDTO azienda : aziende)
      {
        sb.append("<span><b>CUAA:</b> " + azienda.getCuaa() + "</span><br/>"
            + "<span><b>Partita IVA:</b> " + azienda.getPartitaIva()
            + "</span><br/>"
            + "<span><b>Denominazione:</b> " + azienda.getDenominazione()
            + "</span><br/>"
            + "<span><b>Forma giuridica:</b> "
            + NemboUtils.STRING.nvl(azienda.getFormaGiuridica())
            + "</span><br/>"
            + "<span><b>Sede legale:</b> "
            + NemboUtils.STRING.nvl(azienda.getSedeLegale()) + "</span><br/>"
            + "<span><b>Azienda cessata in data:</b> "
            + NemboUtils.DATE.formatDate(azienda.getDataCessazione())
            + "</span><br/>"
            + "<span><b>Dal:</b> "
            + NemboUtils.DATE.formatDateTime(azienda.getDataInizio())
            + "</span><br/>"
            + "<span><b>Utente aggiornamento:</b> "
            + getUtenteDescrizione(azienda.getExtIdUtenteAggiornamento(),
                utentiList)
            + "</span><br/>"
            + "<span><b>Note:</b> "
            + StringEscapeUtils
                .escapeHtml4(NemboUtils.STRING.nvl(azienda.getNote()))
            + "</span><br/><br/>");
      }
    }

    sb.append("<h4>Gestore del fascicolo</h4>");
    if (procedimentoDTO.getDenominazioneDelega() != null)
    {
      sb.append("<div style=\"margin-bottom:2em;\"><span>"
          + NemboUtils.STRING.nvl(procedimentoDTO.getDenominazioneDelega())
          + "</span><br/></div>");
    }

    sb.append(
        "<h4>Iter delle Amministrazioni (Organismi Delegati) competenti</h4>");
    if (ammCompetenzaDTO != null)
    {
      sb.append(
          "<div class=\"container-fluid\"><table class=\"bootstrap-table table table-hover table-striped table-bordered tableBlueTh \"><thead><tr><th>Amministrazione (Organismo Delegato)</th> <th>Dettaglio Amministrazione</th> <th>Ufficio di zona</th> <th>Funzionario istruttore</th> <th>Dal</th>  <th>Utente aggiornamento</th> </tr>  </thead><tbody>");

      for (AmmCompetenzaDTO amm : ammCompetenzaDTO)
      {
        sb.append(
            "<tr> <td> " + StringEscapeUtils.escapeHtml4(amm.getDescrizione()));
        if (amm.getNote() != null)
          sb.append(
              "<a style=\"margin-left:0.5em;\"  href=\"javascript:void(0);\" data-toggle=\"tooltip\" title=\""
                  + StringEscapeUtils.escapeHtml4(amm.getNote())
                  + "\"><span class=\"icon icon-list\"></span></a>");
        sb.append("</td><td> "
            + ((!GenericValidator.isBlankOrNull(amm.getDescrAggiuntiva()))
                ? StringEscapeUtils.escapeHtml4(amm.getDescrAggiuntiva()) : "")
            + "</td>" + "<td> "
            + StringEscapeUtils.escapeHtml4(amm.getDescrizioneUfficioZona())
            + "</td>" + "<td> "
            + StringEscapeUtils.escapeHtml4(amm.getDescrizioneTecnico())
            + "</td>" + "<td> "
            + NemboUtils.DATE.formatDateTime(amm.getDataInizio()) + "</td>"
            + "<td> "
            + getUtenteDescrizione(amm.getExtIdUtenteAggiornamento(),
                utentiList)
            + "</td>"
        // +"<td>"+StringEscapeUtils.escapeHtml4(NemboUtils.STRING.nvl(amm.getNote()))+"</td>
        // </tr>"
        );
      }
      sb.append("</tbody></table></div>");

    }

    sb.append("<h4> Elenco procedimenti/oggetti e relativo ITER</h4>");
    sb.append("<span><b>Stato del procedimento:</b> "
        + procedimentoDTO.getDescrStatoOggetto() + "</span><br/><br/>");
    sb.append("<span><b>Identificativo:</b> "
        + procedimentoDTO.getIdentificativo() + "</span><br/><br/>");
    UtenteAbilitazioni utenteAbilitazioni = getUtenteAbilitazioni(session);

    for (ProcedimentoOggettoDTO procOggetto : procedimentoDTO
        .getProcedimentoOggetto())
    {
      codDomanda = "";
      if (procOggetto.getIdentificativo() != null
          && procOggetto.getIdentificativo().trim().length() > 0)
        codDomanda = "Codice Domanda: " + procOggetto.getIdentificativo();

      boolean canSeeIter = true;
      if (utenteAbilitazioni != null
          && (utenteAbilitazioni.getRuolo().isUtenteIntermediario()
              || isBeneficiario(utenteAbilitazioni)))
        if (procOggetto.getIterProcedimentoggetto() != null
            && procOggetto.getIterProcedimentoggetto()
                .get(procOggetto.getIterProcedimentoggetto().size() - 1)
                .getIdStatoOggetto() != 22
            && procOggetto.getIterProcedimentoggetto()
                .get(procOggetto.getIterProcedimentoggetto().size() - 1)
                .getIdStatoOggetto() != 30)
          if ("N".equals(procOggetto.getFlagIstanza()))
          {
            if (quadroEJB.isSoggettoAdApprovazione(procOggetto.getIdOggetto()))
            {
              canSeeIter = false;
            }
          }

      if (canSeeIter)
      {
        sb.append(
            "<fieldset><legend style=\"font-size:17px\">"
                + StringEscapeUtils
                    .escapeHtml4(procOggetto.getDescrGruppoOggetto())
                + " - "
                + StringEscapeUtils.escapeHtml4(procOggetto.getDescrOggetto())
                + " - " + codDomanda + "</legend>");

        sb.append(
            "<div class=\"container-fluid\"><table class=\"bootstrap-table table table-hover table-striped table-bordered tableBlueTh \"><thead><tr><th>Stato</th> <th>Dal</th>  <th>Utente aggiornamento</th> <th>Note</th></tr>  </thead><tbody>");
        for (IterProcedimentoOggettoDTO iter : procOggetto
            .getIterProcedimentoggetto())
        {
          sb.append("<tr> <td> " + iter.getDescrizione() + "</td>"
              + "<td> "
              + NemboUtils.DATE.formatDateTime(iter.getDataInizio())
              + "</td>"
              + "<td> " + getUtenteDescrizione(
                  iter.getExtIdUtenteAggiornamento(), utentiList)
              + "</td>"
              + "<td>" + StringEscapeUtils.escapeHtml4(
                  NemboUtils.STRING.nvl(iter.getNote()))
              + "</td> </tr>");
        }

        sb.append("</tbody></table></div>");
        sb.append("</fieldset>");
      }

    }

    sb.append("</div>");

    return sb.toString();
  }

  private boolean isBeneficiario(UtenteAbilitazioni utenteAbilitazioni)
  {
    return utenteAbilitazioni.getRuolo().isUtenteAziendaAgricola()
        || utenteAbilitazioni.getRuolo().isUtenteLegaleRappresentante()
        || utenteAbilitazioni.getRuolo().isUtenteTitolareCf();
  }

}