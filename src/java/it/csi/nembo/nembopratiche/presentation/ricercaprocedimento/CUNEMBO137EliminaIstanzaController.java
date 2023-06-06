package it.csi.nembo.nembopratiche.presentation.ricercaprocedimento;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.IRicercaEJB;
import it.csi.nembo.nembopratiche.dto.GruppoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Controller
@RequestMapping("/cunembo137")
@NemboSecurity(value = "CU-NEMBO-137", controllo = NemboSecurity.Controllo.PROCEDIMENTO)
public class CUNEMBO137EliminaIstanzaController extends BaseController
{

  @Autowired
  private IQuadroEJB  quadroEJB  = null;
  @Autowired
  private IRicercaEJB ricercaEJB = null;

  @RequestMapping(value = "popupindex_{paginaPadre}_{idProcedimento}_{idProcedimentoOggetto}_{idBandoOggetto}", method = RequestMethod.GET)
  @IsPopup
  public String popupIndex(Model model, HttpSession session,
      @PathVariable("paginaPadre") String paginaPadre,
      @PathVariable("idProcedimento") @NumberFormat(style = NumberFormat.Style.NUMBER) long idProcedimento,
      @PathVariable("idProcedimentoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idProcedimentoOggetto,
      @PathVariable("idBandoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idBandoOggetto)
      throws InternalUnexpectedException
  {
    setModelDialogWarning(model,
        "Stai cercando di eliminare l'Istanza selezionata, vuoi continuare ?",
        "../cunembo137/popupindex_" + paginaPadre + "_" + idProcedimento + "_"
            + idProcedimentoOggetto + "_" + idBandoOggetto + ".do");
    return "dialog/conferma";
  }

  @RequestMapping(value = "popupindex_{paginaPadre}_{idProcedimento}_{idProcedimentoOggetto}_{idBandoOggetto}", method = RequestMethod.POST)
  public String popupIndexPost(Model model, HttpSession session,
      HttpServletRequest request,
      @PathVariable("paginaPadre") String paginaPadre,
      @PathVariable("idProcedimento") @NumberFormat(style = NumberFormat.Style.NUMBER) long idProcedimento,
      @PathVariable("idProcedimentoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idProcedimentoOggetto,
      @PathVariable("idBandoOggetto") @NumberFormat(style = NumberFormat.Style.NUMBER) long idBandoOggetto)
      throws InternalUnexpectedException
  {

    UtenteAbilitazioni utenteAbilitazioni = (UtenteAbilitazioni) session
        .getAttribute("utenteAbilitazioni");
    MainControlloDTO controlliGravi = quadroEJB.callMainEliminazione(
        idProcedimentoOggetto, utenteAbilitazioni.getIdUtenteLogin());

    if (controlliGravi
        .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO)
    {
      model.addAttribute("messaggio",
          NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_CRITICO.replace("&egrave;",
              "è") + " " + controlliGravi.getMessaggio());
      model.addAttribute("titolo", "Errore");
      return "errore/erroreInterno";
    }
    else
      if (controlliGravi
          .getRisultato() == NemboConstants.SQL.RESULT_CODE.ERRORE_GRAVE)
      {
        model.addAttribute("messaggio",
            NemboConstants.SQL.MESSAGE.PLSQL_ERRORE_GRAVE.replace("&egrave;",
                "è") + " " + controlliGravi.getMessaggio());
        model.addAttribute("titolo", "Errore");
        return "errore/erroreInterno";
      }
      else
      {
        if (paginaPadre.equals("E"))
        {
          // se arrivo dalla pagina di elenco oggetti ricarico la pagina se
          // esiste ancora il procedimento, altrimenti torno ai risultati di
          // ricerca
          List<GruppoOggettoDTO> listGruppiOggetto = ricercaEJB
              .getElencoOggetti(idProcedimento,
                  Arrays.asList(utenteAbilitazioni.getMacroCU()),
                  utenteAbilitazioni.getIdProcedimento());
          if (listGruppiOggetto != null && listGruppiOggetto.size() > 0)
          {
            return "redirect:../cunembo129/index_" + idProcedimento + ".do";
          }
          else
          {
            return "redirect:../home/index.do";
          }
        }
        else
        {
          // Se arrivo dalla pagina di elenco procedimenti ricarico la pagina
          return "redirect:../nuovoprocedimento/ricercaBandoMultipla.do";
        }
      }
  }

}
