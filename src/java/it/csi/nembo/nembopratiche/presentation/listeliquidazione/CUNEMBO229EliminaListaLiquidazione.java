package it.csi.nembo.nembopratiche.presentation.listeliquidazione;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IListeLiquidazioneEJB;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.IsPopup;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;

@Controller
@RequestMapping("/cunembo229")
@NemboSecurity(value = "CU-NEMBO-229", controllo = NemboSecurity.Controllo.DEFAULT)
public class CUNEMBO229EliminaListaLiquidazione extends BaseController
{

  @Autowired
  IListeLiquidazioneEJB listeLiquidazioneEJB = null;

  @IsPopup
  @RequestMapping(value = "/confermaElimina_{idListaLiquidazione}", method = RequestMethod.GET)
  public String confermaElimina(
      @PathVariable("idListaLiquidazione") Long idListaLiquidazione,
      HttpServletRequest request, Model model)
      throws InternalUnexpectedException
  {
    model.addAttribute("idListaLiquidazione", idListaLiquidazione);
    return "listeliquidazione/confermaElimina";
  }

  @RequestMapping(value = "/elimina_{idListaLiquidazione}", method = RequestMethod.GET)
  public String elimina(
      @PathVariable("idListaLiquidazione") Long idListaLiquidazione,
      HttpServletRequest request, Model model)
      throws InternalUnexpectedException
  {
    RigaJSONElencoListaLiquidazioneDTO lista = listeLiquidazioneEJB
        .getListaLiquidazioneById(idListaLiquidazione);

    boolean canUpdate = NemboUtils.PAPUASERV.hasAmministrazioneCompetenza(
        getUtenteAbilitazioni(request.getSession()),
        lista.getExtIdAmmCompetenza());

    if (canUpdate && !lista.getFlagStatoLista().equals("A"))
    {
      listeLiquidazioneEJB.deleteListaLiquidazione(idListaLiquidazione);
      return "dialog/success";
    }
    model.addAttribute("error",
        "L'utente corrente non è abilitato a questa operazione.");
    return "dialog/error";
  }

}
