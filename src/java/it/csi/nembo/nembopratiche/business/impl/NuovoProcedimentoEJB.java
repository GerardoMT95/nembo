package it.csi.nembo.nembopratiche.business.impl;

import java.util.List;
import java.util.Vector;

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

import it.csi.nembo.nembopratiche.business.INuovoProcedimentoEJB;
import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.FileAllegatoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.OggettoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.NuovoProcedimentoDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Stateless()
@EJB(name = "java:app/NuovoProcedimento", beanInterface = INuovoProcedimentoEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class NuovoProcedimentoEJB extends
    NemboAbstractEJB<NuovoProcedimentoDAO> implements INuovoProcedimentoEJB
{
  private final String   THIS_CLASS = NuovoProcedimentoEJB.class
      .getCanonicalName();
  private SessionContext sessionContext;

  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public BandoDTO getDettaglioBandoByIdBando(long idBando)
      throws InternalUnexpectedException
  {
    return dao.getDettaglioBandoByIdBando(idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<BandoDTO> getElencoBandiAttivi(int idProcedimentoAgricolo)
      throws InternalUnexpectedException
  {
    List<BandoDTO> bandi = dao.getElencoBandiAttivi(idProcedimentoAgricolo);
    if(bandi != null)
    {
    	for (BandoDTO b : bandi)
    	{
    		b.setAmministrazioniCompetenza(
    				dao.getAmmCompetenzaAssociate(b.getIdBando()));
    	}
    }
    return bandi;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<OggettoDTO> getOggettiByIdBando(long idBando)
      throws InternalUnexpectedException
  {
    return dao.getOggettiByIdBando(idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<Long> getAziendeByCUAA(String cuaa,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
    return dao.getAziendeByCUAA(cuaa, utenteAbilitazioni);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<Long> getAziendeByIdBando(long idBando,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
    return dao.getAziendeByIdBando(idBando, utenteAbilitazioni);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<AziendaDTO> getDettaglioAziendeById(Vector<Long> vIdAzienda,
      long idBando) throws InternalUnexpectedException
  {
    return dao.getDettaglioAziendeById(vIdAzienda, idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<AziendaDTO> getDettaglioAziendeByIdBando(long idBando,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
    return dao.getDettaglioAziendeByIdBando(idBando, utenteAbilitazioni);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public MainControlloDTO callMainControlliGravi(long idBandoOggetto,
      Long idProcedimento, long idAzienda, long idUtenteLogin,
      Long codRaggruppamento) throws InternalUnexpectedException
  {
    return dao.callMainControlliGravi(idBandoOggetto, idProcedimento, idAzienda,
        idUtenteLogin, codRaggruppamento);
  }

  @Override
  public MainControlloDTO callMainCreazione(long idBando,
      long idLegameGruppoOggetto, Long idProcedimento, long idAzienda,
      long idUtenteLogin,
      String codAttore, Long codRaggruppamento, boolean forzaAlfanumerico, String note)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::callMainCreazione]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    MainControlloDTO result = null;
    try
    {
      result = dao.callMainCreazione(idBando, idLegameGruppoOggetto,
          idProcedimento, idAzienda, idUtenteLogin, codAttore,
          codRaggruppamento, forzaAlfanumerico);
    	
      if(idProcedimento!=null && note!=null) 
    	  dao.updateNoteIterCorrenteByIdprocedimento(idProcedimento, note);
      if (NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE != result
          .getRisultato())
      {
        sessionContext.setRollbackOnly();
      }
      return result;
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
  public void updateTecnicoAndUfficioZonaIfModified(long idProcedimento,
      long extIdUfficioZona, long extIdTecnico,
      long idUtenteLogin) throws InternalUnexpectedException
  {
    final String THIS_METHOD = "updateTecnicoAndUfficioZonaIfModified";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.updateTecnicoAndUfficioZonaIfModified(idProcedimento,
          extIdUfficioZona, extIdTecnico, idUtenteLogin);
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public FileAllegatoDTO getFileAllegato(long idAllegatiBando)
      throws InternalUnexpectedException
  {
    return dao.getFileAllegato(idAllegatiBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<FileAllegatoDTO> getElencoAllegati(long idBando)
      throws InternalUnexpectedException
  {
    return dao.getElencoAllegati(idBando);
  }

  @Override
  public List<AmmCompetenzaDTO> getAmmCompetenzaAssociate(long idBando)
      throws InternalUnexpectedException
  {
    return dao.getAmmCompetenzaAssociate(idBando);
  }

  @Override
  public List<Procedimento> searchProcedimentiBandoEsistente(long idBando,
      long idAzienda) throws InternalUnexpectedException
  {
    return dao.searchProcedimentiBandoEsistente(idBando, idAzienda);
  }

  @Override
  public void inserisciProcDomandaPrec(int idProcedimentoVecchio, int idAzienda,
      long idBandoVecchio) throws InternalUnexpectedException
  {
    dao.inserisciProcDomandaPrec(idProcedimentoVecchio, idAzienda,
        idBandoVecchio);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean callIsValidazioneGrafica(long idAzienda, String annoCampagna)
      throws InternalUnexpectedException
  {
    return dao.callIsValidazioneGrafica(idAzienda, annoCampagna);
  }

}
