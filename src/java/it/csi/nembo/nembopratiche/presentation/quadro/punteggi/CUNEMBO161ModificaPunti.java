package it.csi.nembo.nembopratiche.presentation.quadro.punteggi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.CriterioVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.RaggruppamentoLivelloCriterio;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-161", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo161")
public class CUNEMBO161ModificaPunti extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;
  @Autowired
  protected IInterventiEJB       interventiEJB;

  @RequestMapping("/index")
  public final String index(Model model, HttpServletRequest request,
      HttpSession session) throws InternalUnexpectedException
  {
    final long idProcedimento = getIdProcedimento(session);
    List<RaggruppamentoLivelloCriterio> listaRaggruppamento = null;
    listaRaggruppamento = this.quadroEjb.getCriteriPunteggioManuali(
        idProcedimento, getProcedimentoFromSession(session).getIdBando());
    if (listaRaggruppamento != null && listaRaggruppamento.size() > 0)
    {
      model.addAttribute("listaRaggruppamento", listaRaggruppamento);
    }
    return "punteggi/modificaPunteggi";
  }

  @RequestMapping("/modifica")
  public final String modifica(Model model, HttpServletRequest request,
      HttpSession session) throws InternalUnexpectedException, ApplicationException
  {
    model.addAttribute("prefReqValues", Boolean.TRUE);
    Errors errors = new Errors();

    final long idProcedimento = getIdProcedimento(request.getSession());
    final long idProcedimentoOggetto = getIdProcedimentoOggetto(request.getSession());
    final List<Long> listaIdBando = NemboUtils.LIST
        .toListOfLong(request.getParameterValues("idBandoLivelloCriterio"));
    final Map<String, String[]> mappa = request.getParameterMap();
    Map<Long, BigDecimal> mappaValoriInput = new HashMap<Long, BigDecimal>();

    // precompilo la mia mappa inserendo NULL come punteggio da aggiornare per
    // ogni idBandoLivello che trovo nella request
    for (Long idBando : listaIdBando)
    {
      mappaValoriInput.put(idBando, null);
    }

    boolean inserisciInterventoPrevenzione = false;
    Map<String,Boolean> mappaCodiciPunteggioPerInterventiPrevenzione = getCodiciPunteggioPerInterventiPrevenzione();
    
    // prendo dalla request tutte le chiavi "puntPerIdBandoLivCrit_..ecc" per
    // associare il valore inserito dall'utente al suo relativo idBando
    for (String chiaveParameterMap : mappa.keySet())
    {
      final String[] infoCriterio = chiaveParameterMap.split("_"); 	// [0]
															    	// stringa
															    	// usata
															    	// come
															    	// chiave,
															    	// [1]
															    	// idBando,
															    	// [2]
															    	// punteggioMin,
															    	// [3]
															    	// punteggioMax,
															    	// [4]
															    	// codice
															    	// criterio
      
      //prendo i raggruppamenti per il bando
      List<RaggruppamentoLivelloCriterio> listaRaggruppamento = null;
      listaRaggruppamento = this.quadroEjb.getCriteriPunteggioManuali(
          idProcedimento, getProcedimentoFromSession(session).getIdBando());
      
      //verifico se almeno uno dei criteri relativi alla prevenzione sia stato selezionato
      if (listaRaggruppamento != null && listaRaggruppamento.size() > 0)
      {
    	  for(int i = 0 ; i<listaRaggruppamento.size() ; i++)
    	  {
    		  RaggruppamentoLivelloCriterio rlc = listaRaggruppamento.get(i);
    		  for(CriterioVO criterio : rlc.getCriteri())
    		  {
    			  if(mappaCodiciPunteggioPerInterventiPrevenzione.containsKey(criterio.getCodice()))
    			  {
    				  for(int j=0; j<listaIdBando.size() && !inserisciInterventoPrevenzione; j++)
    				  {
    					  long idBandoLivelloCriterioSelezionatoDaUtente = listaIdBando.get(j);
    					  if(criterio.getIdBandoLivelloCriterio() == idBandoLivelloCriterioSelezionatoDaUtente)
    					  {
    						  inserisciInterventoPrevenzione = true;
    					  }
    				  }
    			  }
    		  }
    	  }
      }

      if (chiaveParameterMap.contains("puntPerIdBandoLivCrit"))
      {
        if (mappaValoriInput.containsKey(Long.parseLong(infoCriterio[1])))
        {
          BigDecimal punteggioInserito = errors
              .validateMandatoryBigDecimalInRange(
                  mappa.get(chiaveParameterMap)[0].replace(",", "."),
                  chiaveParameterMap, 2, new BigDecimal(infoCriterio[2]),
                  new BigDecimal(infoCriterio[3]));
          mappaValoriInput.put(Long.parseLong(infoCriterio[1]),
              punteggioInserito);
        }
      }
    }

    if (!errors.isEmpty())
    {
      model.addAttribute("errors", errors);
      return index(model, request, session);
    }
    else
    {
      final LogOperationOggettoQuadroDTO logOperationOggettoQuadro = getLogOperationOggettoQuadroDTO(
          request.getSession());
      this.quadroEjb.updateCriteriManuali(logOperationOggettoQuadro,
          mappaValoriInput, idProcedimento,
          getProcedimentoFromSession(session).getIdBando(), getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto());
    }
    return "redirect:../cunembo160/index.do";
  }

  

  protected List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(
	      List<Long> ids, long idBando) throws InternalUnexpectedException
  {
    return interventiEJB
        .getInfoInterventiPerInserimentoByIdDescrizioneIntervento(ids, idBando);
  }
  
  
}
