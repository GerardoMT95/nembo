package it.csi.nembo.nembopratiche.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.Link;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboFactory;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "N/A", controllo = NemboSecurity.Controllo.NESSUNO)
public class IndexController extends BaseController
{
  @Autowired
  IQuadroEJB quadroEJB;

  @RequestMapping("/index")
  public String redirectIndex(ModelMap model, HttpSession session)
      throws InternalUnexpectedException
  {
    clearCommonInSession(session);
    NemboFactory.removeIdProcedimentoInSession(session);
    if (getUtenteAbilitazioni(session).getRuolo().isUtenteMonitoraggio())
    {
      return "redirect:cunembo206/index.do";
    }
    return "redirect:home/index.do";
  }

  @RequestMapping(value = "/home/index", method = RequestMethod.GET)
  public String getIndex(ModelMap model, HttpSession session)
      throws InternalUnexpectedException
  {
    clearCommonInSession(session);
    // Pulisco la sessione dall'id_procedimento e id_procedimento_oggetto
    NemboFactory.removeIdProcedimentoInSession(session);
    
    
    NemboUtils.APPLICATION.reloadCDU();  
    
    if (getUtenteAbilitazioni(session).getRuolo().isUtenteMonitoraggio())
    {
      return "redirect:cunembo206/index.do";
    }
    List<Link> links = new ArrayList<Link>();
    Map<String, String> mapCdU = quadroEJB.getMapHelpCdu(
        NemboConstants.USECASE.NUOVO_PROCEDIMENTO,
        NemboConstants.USECASE.RICERCA_PROCEDIMENTO,
        NemboConstants.USECASE.MESSAGGISTICA_ELENCO,
        NemboConstants.USECASE.GESTIONE_SISTEMA,
        NemboConstants.USECASE.ELENCO_BANDI,
        NemboConstants.USECASE.VISUALIZZA_ESTRAZIONI,
        NemboConstants.USECASE.GESTIONE_ESTRAZIONI,
        NemboConstants.USECASE.REGISTRO_FATTURE);

    links.add(new Link("nuovoprocedimento/elencobando.do",
        NemboConstants.USECASE.NUOVO_PROCEDIMENTO, true,
        "Nuovo procedimento", mapCdU
            .get(NemboConstants.USECASE.NUOVO_PROCEDIMENTO)));
    links.add(new Link("ricercaprocedimento/index.do",
        NemboConstants.USECASE.RICERCA_PROCEDIMENTO, false,
        "Ricerca procedimenti", mapCdU
            .get(NemboConstants.USECASE.RICERCA_PROCEDIMENTO)));

    // links.add(new Link("cunembo287/index.do",
    // NemboConstants.USECASE.REGISTRO_ANTIMAFIA, false,
    // "Certificato Antimafia", mapCdU
    // .get(NemboConstants.USECASE.REGISTRO_ANTIMAFIA)));

    links.add(new Link("cunembo227/index.do",
        NemboConstants.USECASE.LISTE_LIQUIDAZIONE.ELENCO, false,
        "Liste di liquidazione", mapCdU
            .get(NemboConstants.USECASE.LISTE_LIQUIDAZIONE.ELENCO)));

    links.add(new Link("elencoBandi/visualizzaBandi.do",
        NemboConstants.USECASE.ELENCO_BANDI, false, "Elenco bandi", mapCdU
            .get(NemboConstants.USECASE.ELENCO_BANDI)));

    links.add(new Link("cunembo202/index.do",
        NemboConstants.USECASE.MESSAGGISTICA_ELENCO, false,
        "Messaggi utente", mapCdU
            .get(NemboConstants.USECASE.MESSAGGISTICA_ELENCO)));
    links.add(new Link("cunembo204/index.do",
        NemboConstants.USECASE.GESTIONE_SISTEMA, false, "Gestione sistema",
        mapCdU
            .get(NemboConstants.USECASE.GESTIONE_SISTEMA)));

    links.add(new Link("cunembo215/index.do",
        NemboConstants.USECASE.VISUALIZZA_ESTRAZIONI, false,
        "Visualizza estrazioni a campione", mapCdU
            .get(NemboConstants.USECASE.VISUALIZZA_ESTRAZIONI)));

    links.add(new Link("cunembo217/index.do",
        NemboConstants.USECASE.GESTIONE_ESTRAZIONI, false,
        "Gestione estrazioni a campione", mapCdU
            .get(NemboConstants.USECASE.GESTIONE_ESTRAZIONI)));

    links.add(new Link("cunembo273/index.do",
        NemboConstants.USECASE.REGISTRO_FATTURE, false, "Registro fatture",
        mapCdU
            .get(NemboConstants.USECASE.REGISTRO_FATTURE)));

    links.add(new Link("cunembo284/index.do",
        NemboConstants.USECASE.REPORTISTICA, false, "Reportistica", mapCdU
            .get(NemboConstants.USECASE.REPORTISTICA)));

    model.addAttribute("links", links);

    final String ID_ELENCO_BANDI = "elencoBandi";
    final String ID_ELENCO_REPORT = "dettEstrazioneTable";
    final String ID_ELENCO_GRADUATORIE = "graduatorieTable";
    final String ID_ELENCO_ESTRAZIONI = "elencoEstrazioniTable";
    final String ID_ELENCO_CONTROLLI = "elencoControlli";
    final String ID_LISTE_LIQUIDAZIONE = "listeLiquidazione";
    final String ID_ELENCO_AMMINISTRAZIONI = "elencoAmministrazioni";
    final String ID_ELENCO_PROCEDIMENTI = "elencoProcedimenti";
    final String ID_ELENCO_DOCUMENTI = "elencoDocumenti";

    @SuppressWarnings("unchecked")
    HashMap<String, String> mapFilters = (HashMap<String, String>) session
        .getAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA);
    mapFilters.remove(ID_ELENCO_BANDI);
    mapFilters.remove(ID_ELENCO_REPORT);
    mapFilters.remove(ID_ELENCO_GRADUATORIE);
    mapFilters.remove(ID_ELENCO_ESTRAZIONI);
    mapFilters.remove(ID_ELENCO_CONTROLLI);
    mapFilters.remove(ID_LISTE_LIQUIDAZIONE);
    mapFilters.remove(ID_ELENCO_AMMINISTRAZIONI);
    mapFilters.remove(ID_ELENCO_PROCEDIMENTI);
    mapFilters.remove(ID_ELENCO_DOCUMENTI);

    mapFilters.remove("elencoListeLiquidazione");
    session.setAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA,
        mapFilters);

    /*
     * Rimuovi dalla sessione le info delle bootstrap-table elencate.
     */
    List<String> tableNamesToRemove = new ArrayList<>();
    tableNamesToRemove.add("elencoBandi");
    tableNamesToRemove.add("dettEstrazioneTable");
    tableNamesToRemove.add("graduatorieTable");
    tableNamesToRemove.add("elencoEstrazioniTable");
    tableNamesToRemove.add("elencoControlli");
    tableNamesToRemove.add("listeLiquidazione");
    tableNamesToRemove.add("elencoAmministrazioni");
    tableNamesToRemove.add("elencoDocumenti");
    tableNamesToRemove.add("elencoInterventi");
    tableNamesToRemove.add("tableIacsDettaglioSemplice");
    tableNamesToRemove.add("tableIacsDettaglioMisura");
    tableNamesToRemove.add("tableIacsDettaglioOperazione");

    // NEMBO tables
    tableNamesToRemove.add("tableElencoMotoriAgricoli"); // motori agricoli
    tableNamesToRemove.add("tableElencoSuperificiColture"); // superfici colture
    tableNamesToRemove.add("tableSuperificiColturePlvVegetale");
    tableNamesToRemove.add("tableDettaglioParticellareSuperificiColture");
    tableNamesToRemove.add("tableElencoAllevamenti"); // allevamenti
    tableNamesToRemove.add("tableListPlvZootecnicaDettaglio");
    tableNamesToRemove.add("tableDettaglioAllevamentiPlv");
    tableNamesToRemove.add("tableElencoFabbricati"); // fabbricati
    tableNamesToRemove.add("tableDettaglioFabbricati");
    tableNamesToRemove.add("tableElencoPrestitiAgrari"); // prestiti agrari
    tableNamesToRemove.add("tableElencoDanni"); // danni
    tableNamesToRemove.add("tblRicercaConduzioni");
    tableNamesToRemove.add("tblRicercaConduzioniRiepilogo");
    tableNamesToRemove.add("tblConduzioni");
    tableNamesToRemove.add("tableElencoScorte"); // scorte

    cleanTableMapsInSession(session, tableNamesToRemove);

    if (session.getAttribute("checkboxMisureSelezionate") != null)
      session.removeAttribute("checkboxMisureSelezionate");
    if (session.getAttribute("all_gruppiProcedimento") != null)
      session.removeAttribute("all_gruppiProcedimento");
    if (session.getAttribute("selectGuppiRicerca") != null)
      session.removeAttribute("selectGuppiRicerca");
    if (session.getAttribute("all_bandi") != null)
      session.removeAttribute("all_bandi");
    if (session.getAttribute("all_eventi") != null)
      session.removeAttribute("all_eventi");
    if (session.getAttribute(FiltroRicercaConduzioni.class.getName()) != null)
      session.removeAttribute(FiltroRicercaConduzioni.class.getName());

    return "index";
  }
}
