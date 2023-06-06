package it.csi.nembo.nembopratiche.presentation.quadro.interventi.danni;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Dettaglio;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1304-V", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo1304v")
public class CUNEMBO1304VDettaglioIntervento extends Dettaglio
{
	@Override
	public String getFlagEscludiCatalogo()
	{
		return NemboConstants.FLAGS.NO;
	}
}
