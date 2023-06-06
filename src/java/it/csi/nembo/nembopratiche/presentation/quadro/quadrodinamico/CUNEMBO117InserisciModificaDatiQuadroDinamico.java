package it.csi.nembo.nembopratiche.presentation.quadro.quadrodinamico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.csi.nembo.nembopratiche.business.IQuadroDinamicoEJB;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico.ElementoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadrodinamico.QuadroDinamicoDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.presentation.quadro.quadrodinamico.validator.MultipleValuesValidator;
import it.csi.nembo.nembopratiche.presentation.quadro.quadrodinamico.validator.SingleValueValidator;
import it.csi.nembo.nembopratiche.util.annotation.NemboSecurity;
import it.csi.nembo.nembopratiche.util.validator.Errors;

@Controller
@RequestMapping("/cunembo117_{codiceQuadro}")
@NemboSecurity(value = "CU-NEMBO-117", controllo = NemboSecurity.Controllo.PROCEDIMENTO_OGGETTO, tipoMapping = NemboSecurity.TipoMapping.QUADRO_DINAMICO)
public class CUNEMBO117InserisciModificaDatiQuadroDinamico
    extends BaseController
{
  @Autowired
  IQuadroDinamicoEJB quadroDinamicoEJB;

  @RequestMapping(value = "/inseriscimodifica_{numProgressivoRecord}", method = RequestMethod.GET)
  public String inserisciModifica(Model model, HttpServletRequest request,
      @PathVariable("codiceQuadro") String codiceQuadro,
      @PathVariable("numProgressivoRecord") Long numProgressivoRecord)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromRequest(request);
    model.addAttribute("annulla",
        "../procedimento/quadro_" + codiceQuadro + ".do");
    model.addAttribute("codiceQuadroLC", codiceQuadro);
    codiceQuadro = codiceQuadro.toUpperCase();
    String cu = "CU-NEMBO-117_" + codiceQuadro;
    model.addAttribute("useCase", cu);
    QuadroDTO quadroDTO = po.findQuadroByCU(cu);
    model.addAttribute("quadro", quadroDTO);
    QuadroDinamicoDTO quadroDinamico = quadroDinamicoEJB.getQuadroDinamico(
        codiceQuadro, po.getIdProcedimentoOggetto(),
        numProgressivoRecord);
    model.addAttribute("quadroDinamico", quadroDinamico);
    return "quadridinamici/inseriscimodifica";
  }

  @RequestMapping(value = "/inserisci", method = RequestMethod.GET)
  public String inserisci(Model model, HttpServletRequest request,
      @PathVariable("codiceQuadro") String codiceQuadro)
      throws InternalUnexpectedException, ApplicationException
  {
    // Uso Long.MIN_VALUE per indicare alla business di non caricare i dati da
    // db (non posso usare null in quanto null
    // ==> tutti i record)
    return inserisciModifica(model, request, codiceQuadro, Long.MIN_VALUE);
  }

  @RequestMapping(value = "/inserisci", method = RequestMethod.POST)
  public String inserisciPost(Model model, HttpServletRequest request,
      @PathVariable("codiceQuadro") String codiceQuadro)
      throws InternalUnexpectedException, ApplicationException
  {
    // Uso Long.MIN_VALUE per indicare alla business di non caricare i dati da
    // db (non posso usare null in quanto null
    // ==> tutti i record)
    return inserisciModificaPost(model, request, codiceQuadro, Long.MIN_VALUE);
  }

  @RequestMapping(value = "/inseriscimodifica_{numProgressivoRecord}", method = RequestMethod.POST)
  public String inserisciModificaPost(Model model, HttpServletRequest request,
      @PathVariable("codiceQuadro") String codiceQuadro,
      @PathVariable("numProgressivoRecord") Long numProgressivoRecord)
      throws InternalUnexpectedException, ApplicationException
  {
    ProcedimentoOggetto po = getProcedimentoOggettoFromSession(
        request.getSession());
    model.addAttribute("codiceQuadroLC", codiceQuadro);
    codiceQuadro = codiceQuadro.toUpperCase();
    QuadroDinamicoDTO quadroDinamico = quadroDinamicoEJB
        .getStrutturaQuadroDinamico(codiceQuadro,
            po.getIdProcedimentoOggetto());
    if (validaQuadroDinamicoEAggiornaDati(quadroDinamico, request, model,
        codiceQuadro, numProgressivoRecord))
    {
      return "redirect:../procedimento/quadro_" + codiceQuadro + ".do";
    }
    else
    {
      String cu = "CU-NEMBO-116_" + codiceQuadro;
      model.addAttribute("useCase", cu);
      QuadroDTO quadroDTO = po.findQuadroByCU(cu);
      model.addAttribute("quadro", quadroDTO);
      model.addAttribute("quadroDinamico", quadroDinamico);
      model.addAttribute("preferRequest", Boolean.TRUE);
      model.addAttribute("annulla",
          "../procedimento/quadro_" + codiceQuadro + ".do");
      return "quadridinamici/inseriscimodifica";
    }
  }

  private boolean validaQuadroDinamicoEAggiornaDati(
      QuadroDinamicoDTO quadroDinamico, HttpServletRequest request,
      Model model, String codQuadro,
      Long numProgressivoRecord)
      throws InternalUnexpectedException, ApplicationException
  {
    if (numProgressivoRecord != null
        && numProgressivoRecord.longValue() == Long.MIN_VALUE)
    {
      /*
       * Long.MIN_VALUE è utilizzato in fase di caricamento dati per indicare
       * alla business di non caricare i dati da db (non posso usare null in
       * quanto null ==> tutti i record). In questo caso invece sto inserento
       * fisicamente i dati su db e non conosco il numProgressivoRecord, quindi
       * numProgressivoRecord ==> null
       */
      numProgressivoRecord = null;
    }

    List<ElementoQuadroDTO> elementi = quadroDinamico.getElementiQuadro();
    Errors errors = new Errors();
    Map<Long, String[]> mapValues = new HashMap<Long, String[]>();
    for (ElementoQuadroDTO elemento : elementi)
    {
      if (!elemento.isProtetto())
      {
        if (!elemento.isTipoHTM() && !elemento.isTipoTIT()
            && !elemento.isTipoTXT())
        {
          /*
           * Valido solo gli elementi che possono veneri inseriti in input
           * dall'utente, gli altri di sola visualizzazione (tipo i titoli)
           * vengono ignorati
           */
          if (elemento.isTipoLST() || elemento.isTipoCBT())
          {
            String[] valori = validaParametroConSelezioneMultipla(elemento,
                request, errors);
            if (valori != null)
            {
              addToMap(mapValues, elemento.getIdElementoQuadro(), valori);
            }
          }
          else
          {
            String valore = validaParametroConValoreSingolo(elemento, request,
                errors);
            if (valore != null)
            {
              addToMap(mapValues, elemento.getIdElementoQuadro(), valore);
            }
          }
        }
      }
    }
    boolean noError = errors.isEmpty();
    if (noError)
    {
      ProcedimentoOggetto po = getProcedimentoOggettoFromRequest(request);
      QuadroOggettoDTO quadroDTO = po.findQuadroByCodiceQuadro(codQuadro);
      if (quadroDTO == null)
      {
        logger.error(
            "[CUNEMBO117InserisciModificaDatiQuadroDinamico:validaQuadroDinamicoEAggiornaDati] quadroDTO == null ==> NullPointerException in arrivo!");
      }
      String errorMessage = quadroDinamicoEJB
          .aggiornaInserisciRecordQuadroDinamico(quadroDTO.getIdQuadro(),
              mapValues,
              getLogOperationOggettoQuadroDTO(
                  "CU-NEMBO-117_" + quadroDTO.getCodQuadro(),
                  request.getSession()),
              numProgressivoRecord);
      if (errorMessage != null)
      {
        errors.addError("error", errorMessage);
        noError = false;
        errors.addToModel(model);
      }
    }
    else
    {
      errors.addToModel(model);
    }
    return noError;
  }

  private void addToMap(Map<Long, String[]> mapValues, Long id,
      String... values)
  {
    mapValues.put(id, values);
  }

  private String validaParametroConValoreSingolo(ElementoQuadroDTO elemento,
      HttpServletRequest request, Errors errors)
      throws ApplicationException,
      InternalUnexpectedException
  {
    final String nomeParametro = "elemento_" + elemento.getIdElementoQuadro();
    String valore = request.getParameter(nomeParametro);
    SingleValueValidator validator = SingleValueValidator.MAP_VALIDATORS
        .get(elemento.getCodiceTipoDato());
    if (validator != null)
    {
      return validator.validate(elemento, valore, nomeParametro, errors,
          quadroDinamicoEJB);
    }
    else
    {
      return null;
    }
  }

  private String[] validaParametroConSelezioneMultipla(
      ElementoQuadroDTO elemento, HttpServletRequest request,
      Errors errors)
  {
    final String nomeParametro = "elemento_" + elemento.getIdElementoQuadro();
    String[] valore = request.getParameterValues(nomeParametro);
    MultipleValuesValidator validator = MultipleValuesValidator.MAP_VALIDATORS
        .get(elemento.getCodiceTipoDato());
    if (validator != null)
    {
      return validator.validate(elemento, valore, nomeParametro, errors);
    }
    else
    {
      return null;
    }
  }
}