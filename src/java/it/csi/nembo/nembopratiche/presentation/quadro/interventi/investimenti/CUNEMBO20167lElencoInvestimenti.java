package it.csi.nembo.nembopratiche.presentation.quadro.interventi.investimenti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Elenco;
import it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.AbilitazioneAzioneTag;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO20-167-L", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cuNEMBO20167l")
public class CUNEMBO20167lElencoInvestimenti extends Elenco
{
	  @Autowired
	  protected IQuadroEJB quadroEJB;
	
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String elenco(Model model, HttpServletRequest request) throws InternalUnexpectedException
  {
	ProcedimentoOggetto procedimentoOggetto = getProcedimentoOggettoFromRequest(request);
    QuadroOggettoDTO quadro = procedimentoOggetto.findQuadroByCU("CU-NEMBO20-167-L");
    model.addAttribute("ultimaModifica", getUltimaModifica(quadroEJB, procedimentoOggetto.getIdProcedimentoOggetto(), quadro.getIdQuadroOggetto(), procedimentoOggetto.getIdBandoOggetto()));

	return super.elenco(model, request);
  }

  @Override
  public String getCodiceQuadro()
  {
    return NemboConstants.QUADRO.CODICE.INVESTIMENTI;
  }

  @Override
  public String getFlagEscludiCatalogo()
  {
    return NemboConstants.FLAGS.SI;
  }

  @Override
  protected boolean isBandoConPercentualeRiduzione(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return false;
  }

  @Override
  protected void addExtraAttributeToModel(Model model, HttpServletRequest request) throws InternalUnexpectedException
  {
    final HttpSession session = request.getSession();
    boolean isAbilita = AbilitazioneAzioneTag.validate(NemboConstants.QUADRO.CODICE.INVESTIMENTI, NemboConstants.AZIONE.CODICE.ABILITA,
        getProcedimentoOggettoFromSession(session), getUtenteAbilitazioni(session), request);    
    model.addAttribute("isAbilita",Boolean.valueOf(isAbilita));
    model.addAttribute("hasflagAssociatoAltraMisura", Boolean.TRUE);
  }

	@Override
	protected void isInterventoWithDanni(Model model)
	{
		model.addAttribute("withDanni", Boolean.FALSE);
	}
}