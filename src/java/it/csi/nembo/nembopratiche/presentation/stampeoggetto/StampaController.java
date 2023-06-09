package it.csi.nembo.nembopratiche.presentation.stampeoggetto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.stampa.Stampa;

@Controller
@NemboSecurity(value = "CU-NEMBO-128", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
public abstract class StampaController extends BaseController
{
  protected ResponseEntity<byte[]> stampaByCUName(long idProcedimentoOggetto,
      String cuName) throws Exception
  {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type", "application/pdf");
    Stampa stampa = NemboUtils.STAMPA.getStampaFromCdU(cuName);
    byte[] contenuto = stampa.findStampaFinale(idProcedimentoOggetto, cuName)
        .genera(idProcedimentoOggetto, cuName);
   // httpHeaders.add("Content-Disposition",
    //    "attachment; filename=\"" + stampa.getDefaultFileName(idProcedimentoOggetto) + "\"");

    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contenuto,
        httpHeaders, HttpStatus.OK);
    return response;
  }
}
