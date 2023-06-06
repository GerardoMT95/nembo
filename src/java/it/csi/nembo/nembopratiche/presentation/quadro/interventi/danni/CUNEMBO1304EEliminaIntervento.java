package it.csi.nembo.nembopratiche.presentation.quadro.interventi.danni;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Elimina;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1304-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo1304e")
public class CUNEMBO1304EEliminaIntervento extends Elimina
{
}