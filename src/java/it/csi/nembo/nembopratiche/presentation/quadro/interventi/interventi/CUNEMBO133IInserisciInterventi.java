package it.csi.nembo.nembopratiche.presentation.quadro.interventi.interventi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DecodificaInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Inserisci;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-133-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo133i")
public class CUNEMBO133IInserisciInterventi extends Inserisci
{
  @Override
  public String getFlagEscludiCatalogo()
  {
    return NemboConstants.FLAGS.NO;
  }

  @Override
  protected List<DecodificaInterventoDTO> getListInterventiForInsert(
      long idProcedimentoOggetto, HttpServletRequest request) throws InternalUnexpectedException
  {
    return interventiEJB.getListInterventiPossibiliByIdProcedimentoOggetto(
        idProcedimentoOggetto);
  }

  @Override
  protected List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(
      List<Long> ids, long idBando) throws InternalUnexpectedException
  {
    return interventiEJB
        .getInfoInterventiPerInserimentoByIdDescrizioneIntervento(ids, idBando);
  }

@Override
public void aggiungiDannoAtmAlModel(Model model, HttpServletRequest request) {}

@Override
protected void insertInterventi(List<RigaModificaMultiplaInterventiDTO> list, HttpServletRequest request,
		LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException, ApplicationException
{
	interventiEJB.insertInterventi(list,null,logOperationOggettoQuadroDTO);
}

protected void getListDanniInterventi(Model model, long idProcedimentoOggetto) throws InternalUnexpectedException
{
	//non si gestiscono danni
}

}