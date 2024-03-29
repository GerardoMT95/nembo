package it.csi.nembo.nembopratiche.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import it.csi.nembo.nembopratiche.business.INuovoProcedimentoEJB;
import it.csi.nembo.nembopratiche.business.IReportisticaEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.FocusAreaDTO;
import it.csi.nembo.nembopratiche.dto.GraduatoriaDTO;
import it.csi.nembo.nembopratiche.dto.SettoriDiProduzioneDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.FileAllegatoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/elencoBandi")
@NemboSecurity(value = "000", controllo = NemboSecurity.Controllo.NESSUNO)
public class ElencoBandiController
{

  @Autowired
  private IRicercaEJB           ricercaEJB           = null;
  @Autowired
  private IReportisticaEJB      reportisticaEJB      = null;
  @Autowired
  private INuovoProcedimentoEJB nuovoProcedimentoEJB = null;

  @RequestMapping(value = "visualizzaBandi", method = RequestMethod.GET)
  public String visualizzaBandi(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    final String ID_ELENCO_REPORT = "dettEstrazioneTable";
    final String ID_ELENCO_GRADUATORIE = "graduatorieTable";
    @SuppressWarnings("unchecked")
    HashMap<String, String> mapFilters = (HashMap<String, String>) session
        .getAttribute(NemboConstants.GENERIC.SESSION_VAR_FILTER_AZIENDA);
    mapFilters.remove(ID_ELENCO_REPORT);
    mapFilters.remove(ID_ELENCO_GRADUATORIE);

    return "elencoBandi/visualizzaBandi";
  }

  @RequestMapping(value = "getElencoBandiJson", produces = "application/json")
  @ResponseBody
  public List<BandoDTO> getElencoBandiJson(Model model, HttpSession session,
      HttpServletRequest request) throws InternalUnexpectedException
  {
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    int idProcedimentoAgricolo = ((UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni")).getIdProcedimento();
    List<BandoDTO> elencoBandi = ricercaEJB.getElencoBandi(
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni), idProcedimentoAgricolo);
  
    for (BandoDTO b : elencoBandi)
    {
      long idBando = b.getIdBando();

      List<GraduatoriaDTO> graduatorie = ricercaEJB
          .getGraduatorieBando(idBando);
      if (graduatorie != null && graduatorie.isNotEmpty())
      {
        b.setHaveGraduatorie(true);
      }

      b.setLivelli(ricercaEJB.getLivelliBando(idBando));
      b.setElencoFocusArea(ricercaEJB.getElencoFocusArea(idBando));
      b.setElencoSettori(ricercaEJB.getElencoSettori(idBando));
      b.setAllegati(nuovoProcedimentoEJB.getElencoAllegati(idBando));
      // b.setAmministrazioniCompetenza(nuovoProcedimentoEJB.getAmmCompetenzaAssociate(b.getIdBando()));
    }
    return elencoBandi;
  }

  @RequestMapping(value = "getElencoCodiciOperazioneJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoCodiciOperazioneJson(Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
	UtenteAbilitazioni utenteAbilitazioni =  (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
	List<LivelloDTO> livelli = ricercaEJB.getElencoLivelliByProcedimentoAgricolo(utenteAbilitazioni.getIdProcedimento());
    List<LivelloDTO> liv = new LinkedList<LivelloDTO>();
    boolean aggiungi = true;

    for (LivelloDTO item : livelli)
    {
      for (LivelloDTO d : liv)
      {
        if (d.getCodiceLivello().compareTo(item.getCodiceLivello()) == 0)
        {
          aggiungi = false;
        }
      }

      if (aggiungi)
        liv.add(item);
      aggiungi = true;
    }

    Map<String, Object> stato;
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
    ;
    for (LivelloDTO item : liv)
    {
      stato = new HashMap<>();
      stato.put("id", item.getCodiceLivello());
      stato.put("label", item.getCodiceLivello());
      ret.add(stato);
    }

    return ret;
  }

  @RequestMapping(value = "getElencoCodiciLivelliJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoCodiciLivelliJson(Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<LivelloDTO> livelli = ricercaEJB.getElencoLivelli();
    List<LivelloDTO> liv = new LinkedList<>();
    boolean aggiungi = true;

    for (LivelloDTO item : livelli)
    {
      for (LivelloDTO d : liv)
      {
        if (d.getCodice().compareTo(item.getCodice()) == 0)
        {
          aggiungi = false;
        }
      }

      if (aggiungi)
        liv.add(item);
      aggiungi = true;
    }

    Map<String, Object> stato;
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (LivelloDTO item : liv)
    {
      stato = new HashMap<>();
      stato.put("id", item.getCodice());// ci andrebbe l'id, ma ci sono pi�
                                        // codici uguali ma con id diverso,
                                        // quindi il confronto non lo faccio per
                                        // id ma lo faccio direttamente per
                                        // codice
      stato.put("label", item.getCodice());
      ret.add(stato);
    }

    return ret;
  }

  @RequestMapping(value = "getElencoCodiciLivelliMisureJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoCodiciLivelliMisureJson(Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
	UtenteAbilitazioni utenteAbilitazioni =  (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
    List<LivelloDTO> livelli = ricercaEJB.getElencoLivelliByProcedimentoAgricolo(utenteAbilitazioni.getIdProcedimento());
    Map<String, Object> stato;
    List<String> valList = new ArrayList<>();
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (LivelloDTO item : livelli)
    {
      if (!valList.contains(item.getCodiceMisura()))
      {
        stato = new HashMap<>();
        stato.put("id", item.getCodiceMisura());
        stato.put("label", item.getCodiceMisura());
        ret.add(stato);
        valList.add(item.getCodiceMisura());
      }
    }

    return ret;
  }

  @RequestMapping(value = "getElencoCodiciLivelliSottoMisureJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoCodiciLivelliSottoMisureJson(
      Model model, HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
	UtenteAbilitazioni utenteAbilitazioni =  (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
	List<LivelloDTO> livelli = ricercaEJB.getElencoLivelliByProcedimentoAgricolo(utenteAbilitazioni.getIdProcedimento());
	Map<String, Object> stato;
    List<String> valList = new ArrayList<>();
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (LivelloDTO item : livelli)
    {
      if (!valList.contains(item.getCodiceSottoMisura()))
      {
        stato = new HashMap<>();
        stato.put("id", item.getCodiceSottoMisura());
        stato.put("label", item.getCodiceSottoMisura());
        ret.add(stato);
        valList.add(item.getCodiceSottoMisura());
      }
    }

    return ret;
  }

  @RequestMapping(value = "getElencoSettoriJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoSettoriJson(Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    List<SettoriDiProduzioneDTO> livelli = ricercaEJB.getElencoSettoriBandi();
    Map<String, Object> stato;
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (SettoriDiProduzioneDTO item : livelli)
    {
      stato = new HashMap<>();
      stato.put("id", item.getDescrizione());
      stato.put("label", item.getDescrizione());
      ret.add(stato);
    }

    return ret;
  }

  
  @RequestMapping(value = "getElencoTipiBandoJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoTipiBandoJson(Model model,
      HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
	UtenteAbilitazioni utenteAbilitazioni =  (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
    List<BandoDTO> tipiBando = ricercaEJB.getElencoBandi(NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni), utenteAbilitazioni.getIdProcedimento());
    Map<String, Object> stato;
    List<String> valList = new ArrayList<>();
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    
    for (BandoDTO item : tipiBando)
    {
      if (!valList.contains(item.getDescrTipoBando()))
      {
        stato = new HashMap<>();
        stato.put("id", item.getDescrTipoBando());
        stato.put("label", item.getDescrTipoBando());
        ret.add(stato);
        valList.add(item.getDescrTipoBando());
      }
    }

    return ret;
  }

  @RequestMapping(value = "gestBar_{idBando}")
  public String index(@PathVariable(value = "idBando") long idBando,
      Model model, HttpSession session) throws InternalUnexpectedException
  {
    BandoDTO bando = ricercaEJB.getInformazioniBando(idBando);
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    List<DecodificaDTO<String>> elencoQueryBando = reportisticaEJB
        .elencoQueryBando(idBando, Boolean.FALSE,
            NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni));
    if (elencoQueryBando != null && elencoQueryBando.isNotEmpty())
    {
      bando.setHaveChart(true);
      // session.setAttribute("elencoQueryBando_"+idBando, elencoQueryBando);
    }
    if (reportisticaEJB.graficiTabellariPresenti(idBando,
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni)))
    {
      bando.setHaveReport(true);
    }

    List<GraduatoriaDTO> graduatorie = ricercaEJB.getGraduatorieBando(idBando);
    if (graduatorie != null && graduatorie.isNotEmpty())
    {
      bando.setHaveGraduatorie(true);
    }

    session.setAttribute("idBando", bando);

    if (bando.isHaveReport())
      return "redirect:../reportistica/elencoreport_" + idBando + ".do";
    if (bando.isHaveChart())
      return "redirect:../reportistica/mainreport_" + idBando + ".do";

    return "redirect:elencograduatorie_" + idBando + ".do";

  }

  @RequestMapping(value = "downloadAllegatiElencoBandi_{idAllegatiBando}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> download(
      @PathVariable("idAllegatiBando") long idAllegatiBando,
      HttpSession session) throws IOException, InternalUnexpectedException
  {
    FileAllegatoDTO allegato = nuovoProcedimentoEJB
        .getFileAllegato(idAllegatiBando);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type",
        NemboUtils.FILE.getMimeType(allegato.getNomeFile()));
    httpHeaders.add("Content-Disposition",
        "attachment; filename=\"" + allegato.getNomeFile() + "\"");
    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
        allegato.getFileAllegato(), httpHeaders, HttpStatus.OK);
    return response;
  }

  @RequestMapping(value = "getElencoAmministrazioniCompetenzaJson", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoAmministrazioniCompetenzaJson(
      Model model, HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    int idProcedimentoAgricolo = utenteAbilitazioni.getIdProcedimento();
    List<BandoDTO> elencoBandi = ricercaEJB.getElencoBandi(
        NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni), idProcedimentoAgricolo);

    Map<String, Object> stato;
    List<String> valList = new ArrayList<>();
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (BandoDTO item : elencoBandi)
    {
      item.setAmministrazioniCompetenza(
          nuovoProcedimentoEJB.getAmmCompetenzaAssociate(item.getIdBando()));

      for (AmmCompetenzaDTO a : item.getAmministrazioniCompetenza())
      {
        if (!valList.contains(a.getDescrizione()))
        {
          stato = new HashMap<>();
          stato.put("id", a.getIdAmmCompetenza());
          stato.put("label", a.getDescrizione());
          ret.add(stato);
          valList.add(a.getDescrizione());
        }
      }
    }

    return ret;
  }
  
  @RequestMapping(value = "getElencoReferentiBando", produces = "application/json")
  @ResponseBody
  public List<Map<String, Object>> getElencoReferentiBando(
      Model model, HttpSession session, HttpServletRequest request)
      throws InternalUnexpectedException
  {
	UtenteAbilitazioni utenteAbilitazioni =  (UtenteAbilitazioni) session.getAttribute("utenteAbilitazioni");
	List<BandoDTO> elencoBandi = ricercaEJB.getElencoBandi(NemboUtils.PAPUASERV.getFirstCodiceAttore(utenteAbilitazioni), utenteAbilitazioni.getIdProcedimento());
	Map<String, Object> stato;
    List<String> valList = new ArrayList<>();
    // al bootstrap-table-filter devo passare una map di questo tipo
    List<Map<String, Object>> ret = new LinkedList<>();
    ;
    for (BandoDTO bando : elencoBandi)
    {
      if (!valList.contains(bando.getReferenteBando()))
      {
        stato = new HashMap<>();
        stato.put("id", bando.getReferenteBando());
        stato.put("label", bando.getReferenteBando());
        ret.add(stato);
        valList.add(bando.getReferenteBando());
      }
    }

    return ret;
  }
}
