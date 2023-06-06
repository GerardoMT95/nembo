package it.csi.nembo.nembopratiche.presentation.quadro.interventi.danni;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Modifica;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1304-M", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo1304m")
public class CUNEMBO1304MModificaInterventi extends Modifica
{
  @Override
  protected List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(
      List<Long> ids, final long idProcedimentoOggetto, final long idBando)
      throws InternalUnexpectedException
  {
    return interventiEJB
        .getInfoInterventiPerModifica(idProcedimentoOggetto, ids, idBando);
  }

}