package it.csi.nembo.nembopratiche.presentation.quadro.interventi.danni;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DecodificaInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.quadro.interventi.base.Inserisci;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@NemboSecurity(value = "CU-NEMBO-1304-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo1304i")
public class CUNEMBO1304IInserisciInterventi extends Inserisci
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
	  long idDannoAtm = (Long) request.getAttribute("idDannoAtm");
	  return interventiEJB.getListInterventiPossibiliDannoAtmByIdProcedimentoOggetto(idProcedimentoOggetto, idDannoAtm);
  }
  

  @Override
  protected List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(
      List<Long> ids, long idBando) throws InternalUnexpectedException
  {
    return interventiEJB
        .getInfoInterventiPerInserimentoByIdDescrizioneIntervento(ids, idBando);
  }

@Override
public void aggiungiDannoAtmAlModel(Model model, HttpServletRequest request)
{
    String idDannoAtm = request.getParameter("idDannoAtm");
    model.addAttribute("idDannoAtm", idDannoAtm);
}

@RequestMapping(value = "/json/load_elenco_interventi_danno_{idDannoAtm}", produces = "application/json",  method = RequestMethod.GET)
@ResponseBody
public Map<String, Map<String, String>> loadElencoInterventiDanno(
		  Model model,
		  HttpSession session,
		  HttpServletRequest request,
		  @PathVariable("idDannoAtm") long idDannoAtm
		  ) throws InternalUnexpectedException
{
	  	request.setAttribute("idDannoAtm", idDannoAtm);
		return loadElencoInterventiGenerico(session, request);
}

@Override
protected void insertInterventi(List<RigaModificaMultiplaInterventiDTO> list, HttpServletRequest request,
		LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException, ApplicationException
{
	Long idDannoAtm = Long.parseLong(request.getParameter("idDannoAtm"));
	interventiEJB.insertInterventi(list,idDannoAtm,logOperationOggettoQuadroDTO);
}

protected void getListDanniInterventi(Model model, long idProcedimentoOggetto) throws InternalUnexpectedException
{
	  List<DecodificaDTO<Long>> listDanni = quadroNemboEJB.getListDanniDecodificaDTO(idProcedimentoOggetto);
	  model.addAttribute("listDanni",listDanni);
	  model.addAttribute("withDanni",Boolean.TRUE);
}
  

}