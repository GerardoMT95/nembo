package it.csi.nembo.nembopratiche.presentation.quadro.interventi.investimenti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Elimina;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO20-167-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cuNEMBO20167e")
public class CUNEMBO20167EEliminaInvestimento extends Elimina
{
}