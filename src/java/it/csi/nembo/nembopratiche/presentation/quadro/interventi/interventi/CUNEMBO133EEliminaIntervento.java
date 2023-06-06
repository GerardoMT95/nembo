package it.csi.nembo.nembopratiche.presentation.quadro.interventi.interventi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Elimina;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-133-E", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo133e")
public class CUNEMBO133EEliminaIntervento extends Elimina
{
}