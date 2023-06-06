package it.csi.nembo.nembopratiche.business.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import it.csi.nembo.nembopratiche.business.IEstrazioniEJB;
import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.DettaglioImportoDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.FlagEstrazioneDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.ImportiAttualiPrecedentiDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.ImportiTotaliDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.NumeroLottoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.estrazionecampione.RigaSimulazioneEstrazioneDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.EstrazioniDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

@Stateless()
@EJB(name = "java:app/Estrazioni", beanInterface = IEstrazioniEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EstrazioniEJB extends NemboAbstractEJB<EstrazioniDAO>
    implements IEstrazioniEJB
{
  protected static final String THIS_CLASS = EstrazioniEJB.class
      .getSimpleName();
  protected SessionContext      sessionContext;

  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ImportiTotaliDTO getImportiTotali(long idNumeroLotto)
      throws InternalUnexpectedException
  {
    ImportiTotaliDTO imp = dao.getImportiTotali(idNumeroLotto);
    imp.setElencoDettagliImportiMisure(
        dao.getElencoDettagliImportiMisure(idNumeroLotto));

    return imp;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ImportiAttualiPrecedentiDTO getImportiAttualiPrecedenti(
      long idNumeroLotto) throws InternalUnexpectedException
  {
    ImportiAttualiPrecedentiDTO dto = new ImportiAttualiPrecedentiDTO();
    dto.setElencoImporti(dao.getImportiAttualiPrecedenti(idNumeroLotto));
    dto.setElencoImportiMisure(
        dao.getImportiAttualiPrecedentiMisure(idNumeroLotto));
    if (dto.getElencoImporti() != null)
    {
      BigDecimal imp = new BigDecimal("0");
      BigDecimal impCasuali = new BigDecimal("0");
      BigDecimal rischio = new BigDecimal("0");

      for (DettaglioImportoDTO importo : dto.getElencoImporti())
      {
        imp = imp.add(NemboUtils.NUMBERS.nvl(importo.getImportoRichiesto()));
        impCasuali = impCasuali.add(NemboUtils.NUMBERS
            .nvl(importo.getImportoRichiestoParteCasual()));
        rischio = rischio
            .add(NemboUtils.NUMBERS.nvl(importo.getAnalisiRischio()));
      }

      dto.setTotaleImportiEstratti(imp);
      dto.setTotaleImportiEstrattiCasuale(impCasuali);
      dto.setTotaleAnalisiDelRischio(rischio);
    }

    return dto;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaSimulazioneEstrazioneDTO> getElencoRisultati(
      long idNumeroLotto) throws InternalUnexpectedException
  {
    return dao.getElencoRisultati(idNumeroLotto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public MainControlloDTO callRegistraDP(long idNumeroLotto,
      long idTipoEstrazione) throws InternalUnexpectedException
  {
    MainControlloDTO risultato = dao.callRegistraDP(idNumeroLotto,
        idTipoEstrazione);

    if (risultato != null && risultato
        .getRisultato() == NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE)
    {
      dao.updateStatoEstrazione(idNumeroLotto,
          NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.STATO_ESTRAZIONE.REGISTRATA);
    }

    return risultato;
  }

  @Override
  public void eliminaEstrazioni(long idNumeroLotto)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::eliminaEstrazioni]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.deleteTAnalisiRischioImp(idNumeroLotto);
      dao.delete("NEMBO_T_ANALISI_RISCHIO", "ID_NUMERO_LOTTO", idNumeroLotto);
      dao.delete("NEMBO_T_IMPORTI_ESTRAZ_DP", "ID_NUMERO_LOTTO", idNumeroLotto);
      dao.delete("NEMBO_D_NUMERO_LOTTO", "ID_NUMERO_LOTTO", idNumeroLotto);
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Boolean isEstrazioneAnnullabile(long idNumeroLotto,
      long idTipoEstrazione) throws InternalUnexpectedException
  {
    return dao.isEstrazioneAnnullabile(idNumeroLotto, idTipoEstrazione);
  }

  @Override
  public void annullaEstrazioni(long idNumeroLotto, String motivo)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::annullaEstrazioni]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.updateTEstrazioneCampione(idNumeroLotto, motivo);
      dao.updateTAnalisiRischio(idNumeroLotto, "S");
      dao.updateStatoEstrazione(idNumeroLotto,
          NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.STATO_ESTRAZIONE.ANNULLATA);
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaFiltroDTO> getElencoTipoEstrazioniCaricabili()
      throws InternalUnexpectedException
  {
    return dao.getElencoTipoEstrazioniCaricabili();
  }

  @Override
  public long caricamentoEstrazioni(long idTipoEstrazione,
      String utenteDescrizione)
      throws InternalUnexpectedException, ApplicationException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::annullaEstrazioni]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      long idNumeroLotto = 0;
      if (dao.esisteEstrazioneCaricata(idTipoEstrazione))
      {
        throw new ApplicationException(
            "Sono presenti estrazioni a campione dello stesso tipo estrazione non ancora registrate a sistema. Impossibile procedere",
            null);
      }

      idNumeroLotto = dao.insertNumeroLotto(idTipoEstrazione,
          utenteDescrizione);
      MainControlloDTO risultato = dao.callCaricaDP(idNumeroLotto,
          idTipoEstrazione);

      if (risultato != null && risultato
          .getRisultato() == NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE)
      {
        return idNumeroLotto;
      }
      else
      {
        sessionContext.setRollbackOnly();
        throw new ApplicationException(
            "Errore durante l'elaborazione: " + risultato.getMessaggio(), null);
      }

    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public MainControlloDTO callEstraiDP(long idNumeroLotto,
      long idTipoEstrazione, String chkRegistra)
      throws InternalUnexpectedException
  {
    MainControlloDTO risultato = dao.callEstraiDP(idNumeroLotto,
        idTipoEstrazione, chkRegistra);

    if (risultato != null && risultato
        .getRisultato() == NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE)
    {
      dao.updateStatoEstrazione(idNumeroLotto,
          NemboConstants.FLAGS.ESTRAZIONE_CAMPIONE.STATO_ESTRAZIONE.ESTRATTA);
    }
    return risultato;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public NumeroLottoDTO getNumeroLottoDto(long idNumeroLotto)
      throws InternalUnexpectedException
  {
    return dao.getNumeroLottoDto(idNumeroLotto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<FlagEstrazioneDTO> getElencoFlagEstrazioni()
      throws InternalUnexpectedException
  {
    return dao.getElencoFlagEstrazioni();
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<FlagEstrazioneDTO> getElencoFlagEstrazioniExPost()
      throws InternalUnexpectedException
  {
    return dao.getElencoFlagEstrazioniExPost();
  }

}
