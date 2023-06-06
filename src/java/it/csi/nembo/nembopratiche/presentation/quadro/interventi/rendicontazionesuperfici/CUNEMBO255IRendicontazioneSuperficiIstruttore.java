package it.csi.nembo.nembopratiche.presentation.quadro.interventi.rendicontazionesuperfici;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaJSONRendicontazioneSuperficiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.UpdateSuperficieLocalizzazioneDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@NemboSecurity(value = "CU-NEMBO-255-I", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO)
@RequestMapping(value = "/cunembo255i")
public class CUNEMBO255IRendicontazioneSuperficiIstruttore
    extends AbstractRendicontazioneSuperfici
{

  @Override
  protected boolean requireIstanza()
  {
    return false;
  }

  @Override
  protected String getBasePath()
  {
    return "rendicontazionesuperifici/istruttore/";
  }

  @Override
  public boolean validateAndUpdate(Model model, long idIntervento,
      List<RigaJSONRendicontazioneSuperficiDTO> elencoParticelle,
      HttpServletRequest request)
      throws InternalUnexpectedException, ApplicationException
  {
    Errors errors = new Errors();
    List<UpdateSuperficieLocalizzazioneDTO> list = new ArrayList<>();
    for (RigaJSONRendicontazioneSuperficiDTO particella : elencoParticelle)
    {
      final long idConduzioneDichiarata = particella
          .getIdConduzioneDichiarata();
      final long idUtilizzoDichiarato = particella.getIdUtilizzoDichiarato();
      final String key = "superficieIstruttoria_" + idConduzioneDichiarata + "_"
          + idUtilizzoDichiarato;
      String superficieIstruttoria = request.getParameter(key);

      BigDecimal bdSuperficieEffettiva = NemboUtils.NUMBERS
          .getBigDecimal(particella.getSuperficieEffettiva().replace(".", "")
              .replace(",", "."));
      BigDecimal bdSuperficieIstruttoria = null;
      bdSuperficieIstruttoria = errors.validateMandatoryBigDecimalInRange(
          superficieIstruttoria, key, 4,
          BigDecimal.ZERO, NemboUtils.NUMBERS.min(
              particella.getSuperficieAccertataGis(), bdSuperficieEffettiva));
      UpdateSuperficieLocalizzazioneDTO superficie = new UpdateSuperficieLocalizzazioneDTO();
      superficie.setIdConduzioneDichiarata(idConduzioneDichiarata);
      superficie.setIdUtilizzoDichiarato(idUtilizzoDichiarato);
      superficie.setSuperficie(bdSuperficieIstruttoria);
      list.add(superficie);
    }
    if (errors.addToModelIfNotEmpty(model))
    {
      return false;
    }
    else
    {
      rendicontazioneEAccertamentoSpeseEJB
          .updateSuperficieIstruttoriaLocalizzazioneIntervento(idIntervento,
              getLogOperationOggettoQuadroDTO(request.getSession()), list);
      return true;
    }
  }

  @Override
  public String getParentUrl()
  {
    return "/cunembo212l/index.do";
  }

  @Override
  protected void verificaAmmissibilitaDatiSuDB(
      List<RigaJSONRendicontazioneSuperficiDTO> elenco)
      throws InternalUnexpectedException, ApplicationException
  {
    for (RigaJSONRendicontazioneSuperficiDTO riga : elenco)
    {
      if (riga.getSuperficieAccertataGis() == null)
      {
        throw new ApplicationException(
            "Errore: prima di proseguire è necessario eseguire l'importazione delle superfici GIS");
      }
      if (riga.getSuperficieEffettiva() == null)
      {
        throw new ApplicationException(
            "Errore grave: per almeno una localizzazione non risulta presente la superficie effettiva");
      }
    }
    super.verificaAmmissibilitaDatiSuDB(elenco);
  }

  @Override
  protected void calcolaTotali(Model model,
      List<RigaJSONRendicontazioneSuperficiDTO> elenco)
  {
    CUNEMBO255DIDettaglioRendicontazioneSuperficiIstruttore.calcolaTotali(model,
        elenco);
  }

  @Override
  protected void onFirstPageLoad(
      List<RigaJSONRendicontazioneSuperficiDTO> elenco)
  {
    if (elenco != null)
    {
      for (RigaJSONRendicontazioneSuperficiDTO rigaJSONRendicontazioneSuperficiDTO : elenco)
      {
        if (rigaJSONRendicontazioneSuperficiDTO
            .getSuperficieIstruttoria() == null)
        {
          BigDecimal bdSuperficieEffettiva = NemboUtils.NUMBERS
              .getBigDecimal(
                  rigaJSONRendicontazioneSuperficiDTO.getSuperficieEffettiva()
                      .replace(".", "").replace(",", "."));
          BigDecimal min = NemboUtils.NUMBERS.min(bdSuperficieEffettiva,
              rigaJSONRendicontazioneSuperficiDTO.getSuperficieAccertataGis());
          rigaJSONRendicontazioneSuperficiDTO.setSuperficieIstruttoria(
              NemboUtils.FORMAT.formatDecimal4(min).replace(".", ""));
        }
      }
    }
  }
}