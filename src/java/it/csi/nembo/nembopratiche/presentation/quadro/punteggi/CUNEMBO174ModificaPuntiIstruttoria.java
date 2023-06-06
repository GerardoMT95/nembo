package it.csi.nembo.nembopratiche.presentation.quadro.punteggi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.CriterioVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.RaggruppamentoLivelloCriterio;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-174", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("cunembo174")
public class CUNEMBO174ModificaPuntiIstruttoria extends BaseController
{

  @Autowired
  private IQuadroEJB quadroEjb;
  
  @Autowired
  private IInterventiEJB interventiEJB;  

  @RequestMapping("/index")
  public final String index(Model model, HttpServletRequest request,
      HttpSession session) throws InternalUnexpectedException
  {
    List<RaggruppamentoLivelloCriterio> listaRaggruppamento = null;
    final ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
    final QuadroOggettoDTO quadroOgg = po.findQuadroByCodiceQuadro("PUNTI");
    final Long idDatiProcedimentoPunti = this.quadroEjb
        .getIdDatiProcedimentoPunti(po.getIdProcedimentoOggetto());
    if (idDatiProcedimentoPunti != null)
    {
      listaRaggruppamento = this.quadroEjb.getCriteriPunteggio(
          quadroOgg.getIdOggetto(), idDatiProcedimentoPunti,
          getProcedimentoFromSession(session).getIdBando());
      model.addAttribute("listaRaggruppamento", listaRaggruppamento);
    }
    return "punteggi/modificaPunteggiIstruttoria";
  }

	@RequestMapping("/modifica")
	public final String modifica(Model model, HttpServletRequest request, HttpSession session)
			throws InternalUnexpectedException, ApplicationException
	{
		model.addAttribute("prefReqValues", Boolean.TRUE);
		Errors errors = new Errors();

		List<RaggruppamentoLivelloCriterio> listaRaggruppamento = null;
		final long idProcedimento = getIdProcedimento(request.getSession());
		final ProcedimentoOggetto po = getProcedimentoOggettoFromSession(session);
		final QuadroOggettoDTO quadroOgg = po.findQuadroByCodiceQuadro("PUNTI");
		final Long idDatiProcedimentoPunti = this.quadroEjb.getIdDatiProcedimentoPunti(po.getIdProcedimentoOggetto());
		Map<Long, BigDecimal> mappaValoriInput = new HashMap<Long, BigDecimal>();
		if (idDatiProcedimentoPunti != null)
		{
			listaRaggruppamento = this.quadroEjb.getCriteriPunteggio(quadroOgg.getIdOggetto(), idDatiProcedimentoPunti,
					getProcedimentoFromSession(session).getIdBando());
		}

		if (listaRaggruppamento != null)
		{
			String punteggioIstr = "";
			for (RaggruppamentoLivelloCriterio raggr : listaRaggruppamento)
			{
				for (CriterioVO criterio : raggr.getCriteri())
				{
					punteggioIstr = request
							.getParameter("puntPerIdBandoLivCrit_" + criterio.getIdBandoLivelloCriterio());
					if (!GenericValidator.isBlankOrNull(punteggioIstr))
					{
						BigDecimal bdPunteggioIstr = NemboUtils.NUMBERS.getBigDecimal(punteggioIstr);

						if (bdPunteggioIstr != null && BigDecimal.ZERO.compareTo(bdPunteggioIstr) == 0)
						{
							mappaValoriInput.put(criterio.getIdBandoLivelloCriterio(), bdPunteggioIstr);
						} else
						{
							mappaValoriInput.put(criterio.getIdBandoLivelloCriterio(),
									errors.validateMandatoryBigDecimalInRange(punteggioIstr,
											"puntPerIdBandoLivCrit_" + criterio.getIdBandoLivelloCriterio(), 2,
											criterio.getPunteggioMin(), criterio.getPunteggioMax()));
						}
					}
				}
			}
		}

		if (!errors.isEmpty())
		{
			model.addAttribute("errors", errors);
			return index(model, request, session);
		} else
		{
			final LogOperationOggettoQuadroDTO logOperationOggettoQuadro = getLogOperationOggettoQuadroDTO(
					request.getSession());
			long idProcedimentoOggetto = getIdProcedimentoOggetto(session); 
			
			
			this.quadroEjb.updateCriteriIstruttoria(quadroOgg.getIdOggetto(), logOperationOggettoQuadro,
					mappaValoriInput, idProcedimento, 
					getProcedimentoFromSession(session).getIdBando(),
					idDatiProcedimentoPunti, idProcedimentoOggetto
					);
		}
		return "redirect:../cunembo160/index.do";
	}
}
