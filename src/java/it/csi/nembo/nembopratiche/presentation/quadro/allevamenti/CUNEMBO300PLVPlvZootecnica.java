package it.csi.nembo.nembopratiche.presentation.quadro.allevamenti;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDettaglioPlvDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-300-PLV", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping("/cunembo300plv")
public class CUNEMBO300PLVPlvZootecnica extends BaseController
{
	  @Autowired
	  private IQuadroNemboEJB quadroNemboEJB = null;
	  
	  @RequestMapping(value = "/index", method = RequestMethod.GET)
	  public String index(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getIdProcedimentoOggetto(session);
		  BigDecimal ufProdotte = quadroNemboEJB.getPlvZootecnicaUfProdotte(idProcedimentoOggetto);
		  BigDecimal ufNecessarie = quadroNemboEJB.getPlvZootecnicaUfNecessarie(idProcedimentoOggetto);
		  BigDecimal autosufficenzaForaggera;
		  BigDecimal zero = new BigDecimal("0.0");
		  if(ufNecessarie.compareTo(zero) == 0 && ufProdotte.compareTo(zero) == 0)
		  {
			  autosufficenzaForaggera = zero;
		  }
		  else if(ufNecessarie.compareTo(zero) == 0 && ufProdotte.compareTo(zero) > 0)
		  {
			  autosufficenzaForaggera = new BigDecimal("100.0");
		  }
		  else
		  {
			  autosufficenzaForaggera = ufProdotte.divide(ufNecessarie).multiply(new BigDecimal("100.0"));
		  }
		  BigDecimal rapportoUbaSau = getRapportoUbaSau(idProcedimentoOggetto);
		  
		  model.addAttribute("ufProdotte", NemboUtils.FORMAT.formatDecimal2(ufProdotte));
		  model.addAttribute("ufNecessarie", NemboUtils.FORMAT.formatDecimal2(ufNecessarie));
		  model.addAttribute("autosufficenzaForaggera", NemboUtils.FORMAT.formatDecimal2(autosufficenzaForaggera));
		  model.addAttribute("rapportoUbaSau", NemboUtils.FORMAT.formatDecimal4(rapportoUbaSau));
		  return "allevamenti/visualizzaPlvZootecnica";
	  }

	private BigDecimal getRapportoUbaSau(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		  BigDecimal uba = quadroNemboEJB.getPlvZootecnicaUba(idProcedimentoOggetto);
		  BigDecimal sau = quadroNemboEJB.getPlvZootecnicaSau(idProcedimentoOggetto);
		  BigDecimal rapportoUbaSau = BigDecimal.ZERO;
		  if(uba.compareTo(BigDecimal.ZERO) == 0
				  || sau.compareTo(BigDecimal.ZERO)== 0 )
		  {
			  rapportoUbaSau = BigDecimal.ZERO;
		  }
		  else
		  {
			  rapportoUbaSau = uba.divide(sau,MathContext.DECIMAL128).setScale(4,RoundingMode.HALF_UP);
		  }
		  return rapportoUbaSau;
	}
	  
	  @RequestMapping(value = "/get_list_plv_zootecnica_dettaglio_allevamenti.json", method = RequestMethod.GET, produces = "application/json")
	  @ResponseBody
	  public List<AllevamentiDettaglioPlvDTO> getListPlvZootecnicaDettaglioAllevamenti(HttpSession session, Model model) throws InternalUnexpectedException
	  {
		  long idProcedimentoOggetto = getProcedimentoOggettoFromSession(session).getIdProcedimentoOggetto();
		  List<AllevamentiDettaglioPlvDTO> lista =  quadroNemboEJB.getListPlvZootecnicaDettaglioAllevamenti(idProcedimentoOggetto);
		  return lista;
	  }
}
