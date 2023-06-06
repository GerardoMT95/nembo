package it.csi.nembo.nembopratiche.presentation.quadro.interventi.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DettaglioInterventoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;

public abstract class Dettaglio extends LocalizzazioneJSON
{
  @RequestMapping(value = "/index_{idIntervento}", method = RequestMethod.GET)
  public String elenco(Model model,
      @ModelAttribute("idIntervento") @PathVariable("idIntervento") Long idIntervento,
      HttpSession session)
      throws InternalUnexpectedException
  {
    DettaglioInterventoDTO dettaglio = interventiEJB.getDettaglioIntervento(
        getIdProcedimentoOggetto(session), idIntervento,
        getFlagEscludiCatalogo(), getDataValiditaProcOggetto(session));
    model.addAttribute("intervento", dettaglio.getIntervento());
    model.addAttribute("comuni", dettaglio.getComuni());
    List<String> valoriFlag = interventiEJB.getFlagCanaleOpereCondotta(getIdProcedimentoOggetto(session), dettaglio.getIntervento().getIdIntervento());
    List<String> listaDanni = new ArrayList<>();
    if(valoriFlag!=null){
    	if(NemboConstants.FLAGS.SI.equals(valoriFlag.get(0))){
    		listaDanni.add("Canale");
    	}
    	if(NemboConstants.FLAGS.SI.equals(valoriFlag.get(1))){
    		listaDanni.add("Opera di presa");
    	}
    	if(NemboConstants.FLAGS.SI.equals(valoriFlag.get(2))){
    		listaDanni.add("Condotta");
    	}
    }
    model.addAttribute("danni", listaDanni);
    preloadData(model, getIdProcedimentoOggetto(session), dettaglio);
    return "interventi/dettaglio";
  }

  public abstract String getFlagEscludiCatalogo();

  protected void preloadData(Model model, long idProcedimentoOggetto,
      DettaglioInterventoDTO dettaglio) throws InternalUnexpectedException
  {
  }
  
}
