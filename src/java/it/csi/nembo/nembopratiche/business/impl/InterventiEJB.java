package it.csi.nembo.nembopratiche.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaDualLIstDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.AccertamentoSpeseDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DatiLocalizzazioneParticellarePerStampa;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DecodificaInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DettaglioInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FileAllegatoInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaParticelle;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.InfoRiduzione;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.MisurazioneDescrizioneInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.MisurazioneInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaAccertamentoSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaElencoInterventi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaJSONAllegatiInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaJSONConduzioneInteventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaJSONParticellaInteventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaModificaMultiplaInterventiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.SuperficieConduzione;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.CriterioVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.RaggruppamentoLivelloCriterio;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RangePercentuale;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RigaJSONInterventoQuadroEconomicoByLivelloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.RigaJSONInterventoQuadroEconomicoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico.ZonaAltimetricaDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.InterventiDAO;
import it.csi.nembo.nembopratiche.presentation.BaseController;
import it.csi.nembo.nembopratiche.util.NemboConstants;

@Stateless()
@EJB(name = "java:app/Interventi", beanInterface = IInterventiEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class InterventiEJB extends NemboAbstractEJB<InterventiDAO>
    implements IInterventiEJB
{
  private static final String THIS_CLASS = InterventiEJB.class.getSimpleName();
  @SuppressWarnings("unused")
  private SessionContext      sessionContext;

  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaInterventoDTO> getListInterventiPossibiliByIdProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getListInterventiByIdProcedimentoOggetto]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getListInterventiPossibiliByIdProcedimentoOggetto(
          idProcedimentoOggetto);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }
  
  @Override
  public List<DecodificaInterventoDTO> getListInterventiPossibiliDannoAtmByIdProcedimentoOggetto(
  		long idProcedimentoOggetto, long idDannoAtm) throws InternalUnexpectedException
  {
  	List<DecodificaInterventoDTO> lista = dao.getListInterventiPossibiliPerDannoAtmByIdProcedimentoOggetto(idProcedimentoOggetto, idDannoAtm);
  	if(lista == null)
  	{
  		lista = new ArrayList<>();
  	}
  	return lista;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerModifica(
      final long idProcedimentoOggetto, List<Long> idIntervento, long idBando)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getInfoInterventiPerModifica]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getInfoInterventiPerModifica(idProcedimentoOggetto,
          idIntervento, idBando);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaModificaMultiplaInterventiDTO> getInfoInvestimentiPerModifica(
      final long idProcedimentoOggetto, List<Long> idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getInfoInvestimentiPerModifica]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getInfoInvestimentiPerModifica(idProcedimentoOggetto,
          idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONInterventoQuadroEconomicoDTO> getInterventiQuadroEconomicoPerModifica(
      final long idProcedimentoOggetto, List<Long> idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getInfoInterventiPerModifica]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getInterventiQuadroEconomicoPerModifica(idProcedimentoOggetto,
          idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaModificaMultiplaInterventiDTO> getInfoInterventiPerInserimentoByIdDescrizioneIntervento(
      List<Long> listIdDescrizioneIntervento, long idBando)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getInfoInterventiPerInserimentoByIdDescrizioneIntervento]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getInfoInterventiPerInserimentoByIdDescrizioneIntervento(
          listIdDescrizioneIntervento, idBando);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaModificaMultiplaInterventiDTO> getInfoInvestimentiPerInserimentoByIdDescrizioneIntervento(
      List<Long> listIdDescrizioneIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getInfoInvestimentiPerInserimentoByIdDescrizioneIntervento]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getInfoInvestimentiPerInserimentoByIdDescrizioneIntervento(
          listIdDescrizioneIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void insertInterventi(
      List<RigaModificaMultiplaInterventiDTO> listInterventi,
      Long idDannoAtm,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException, ApplicationException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::insertInterventi]";
    
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      // Do per scontato che la lista abbia almeno un elemento, altrimenti il
      // controller avrebbe bloccato l'utente.
      long idProcedimentoOggetto = listInterventi.get(0).getIdProcedimentoOggetto();
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      if(idDannoAtm != null)
      {
    	  ArrayList<Long> arrayIdDescrizioneIntervento = new ArrayList<>();
    	  for (RigaModificaMultiplaInterventiDTO intervento : listInterventi)
    	  {
    		  arrayIdDescrizioneIntervento.add(intervento.getIdDescrizioneIntervento());
    	  }
    	  Boolean isCategorieInterventiNonInserite = dao.isCategoriaInverventoNonInseritePerDannoAtm(idProcedimentoOggetto,idDannoAtm,arrayIdDescrizioneIntervento);
      	  if(!isCategorieInterventiNonInserite)
      	  {
      		  throw new ApplicationException("Si sta cercando di inserire degli interventi che sono gi� stati inseriti precedentemente per lo stesso danno.",10001);
      	  }
    	  
      }
      for (RigaModificaMultiplaInterventiDTO intervento : listInterventi)
      {
        modificaintervento(intervento);
      }
      logOperationOggettoQuadro(logOperationDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  void modificaintervento(RigaModificaMultiplaInterventiDTO intervento){
    
        long idIntervento = dao.insertIntervento(intervento);
        intervento.setIdIntervento(idIntervento);
        long idDettIntervProcOgg = dao
            .insertInterventoNonDefinitivo(intervento);
        for (MisurazioneInterventoDTO misurazione : intervento
            .getMisurazioneIntervento())
        {
          dao.insertMisurazioneInterventoNonDefinitivo(idDettIntervProcOgg,
              misurazione.getIdMisurazioneIntervento(),
              misurazione.getValore());
        }
        Long idAttivita = intervento.getIdAttivita();
        if (idAttivita != null)
        {
          Long idPartecipante = intervento.getIdPartecipante();
          if (idPartecipante != null)
          {
            dao.insertLegameInterventoPartecipantiAttivita(idIntervento,
                intervento.getIdProcedimentoOggetto(), idAttivita,
                idPartecipante);
          }
        }
        if(idDannoAtm != null)
        {
        	DanniDTO danno = dao.getDannoByIdDannoAtm(idProcedimentoOggetto,idDannoAtm);
        	dao.inserisciDannoAtmIntervento(idProcedimentoOggetto,idIntervento,danno);
        }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaElencoInterventi> getElencoInterventiProcedimentoOggetto(
      long idProcedimentoOggetto,
      String flagEscludiCatalogo, Date dataValidita)
      throws InternalUnexpectedException
  {
    return getElencoInterventiProcedimentoOggetto(idProcedimentoOggetto,
        flagEscludiCatalogo, false, dataValidita);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaElencoInterventi> getElencoInterventiProcedimentoOggetto(
      long idProcedimentoOggetto, String flagEscludiCatalogo,
      boolean partecipanti, Date dataValidita)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getElencoInterventiProcedimentoOggetto]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      List<RigaElencoInterventi> interventi = dao
          .getElencoInterventiProcedimentoOggetto(idProcedimentoOggetto, null,
              flagEscludiCatalogo, dataValidita);
      if (interventi != null)
      {
        Map<Long, StringBuilder> map = dao.getMapLocalizzazioneComuniInterventi(
            idProcedimentoOggetto, flagEscludiCatalogo);
        for (RigaElencoInterventi intervento : interventi)
        {
          intervento.setDescComuni(map.get(intervento.getIdIntervento()));

        }
      }
      return interventi;
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONInterventoQuadroEconomicoDTO> getElencoInterventiQuadroEconomico(
      long idProcedimentoOggetto, Date dataValidita)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getElencoInterventiQuadroEconomico]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoInterventiQuadroEconomico(idProcedimentoOggetto,
          dataValidita);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DettaglioInterventoDTO getDettaglioIntervento(
      long idProcedimentoOggetto, long idIntervento, String flagEscludiCatalogo,
      Date dataValidita)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getDettaglioIntervento]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Nota: passo
      List<RigaElencoInterventi> interventi = dao
          .getElencoInterventiProcedimentoOggetto(idProcedimentoOggetto,
              idIntervento, flagEscludiCatalogo, dataValidita);
      if (interventi != null && interventi.size() == 1)
      {
        DettaglioInterventoDTO dettaglio = null;
        RigaElencoInterventi intervento = interventi.get(0);
        if (intervento != null)
        {
          dettaglio = new DettaglioInterventoDTO();
          dettaglio.setIntervento(intervento);
          int idTipoLocalizzazione = intervento.getIdTipoLocalizzazione();
          if (idTipoLocalizzazione == 1 || idTipoLocalizzazione == 2
              || idTipoLocalizzazione == 7 || idTipoLocalizzazione == 9)
          {
            dettaglio.setComuni(dao.geLocalizzazioneComuniIntervento(
                idProcedimentoOggetto, idIntervento));
          }
        }
        return dettaglio;
      }
      else
      {
        return null;
      }
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<String, Map<String, String>> getMapComuniPiemontesiNonInInterventoForJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getMapComuniPiemontesiNonInInterventoForJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getMapComuniPiemontesiNonInInterventoForJSON(
          idProcedimentoOggetto, idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<String, Map<String, String>> getMapComuniParticelleNonInInterventoForJSON(
      long idProcedimentoOggetto, long idIntervento, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getMapComuniParticelleNonInInterventoForJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getMapComuniParticelleNonInInterventoForJSON(
          idProcedimentoOggetto, idIntervento, idProcedimentoAgricoltura);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<String, Map<String, String>> getMapComuniUteNonInInterventoForJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getMapComuniUteNonInInterventoForJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getMapComuniUteNonInInterventoForJSON(idProcedimentoOggetto,
          idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void insertLocalizzazioneComuni(long idProcedimentoOggetto,
      long idIntervento, String[] ids,
      LogOperationOggettoQuadroDTO logOperationDTO,
      String flagCanale, String flagOpereDiPresa, String flagCondotta)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::insertLocalizzazioneComuni]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      // Cerco se c'� l'intervento sulla tabella temporanea
      // NEMBO_W_DETT_INTERV_PROC_OGG
      Long idDettIntervProcOgg = dao
          .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
      if (idDettIntervProcOgg == null) // Non esiste su
                                       // NEMBO_W_DETT_INTERV_PROC_OGG ==>
                                       // ribalto il record da
                                       // NEMBO_T_DETTAGLIO_INTERVENTO
      {
        Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
            idProcedimentoOggetto, idIntervento);
        idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
            idDettaglioIntervento, idProcedimentoOggetto,
            NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
        dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
            idDettIntervProcOgg);
      }
      else
      {
        dao.delete("NEMBO_W_LOCAL_INTERV_PROC_OGG", "ID_DETT_INTERV_PROC_OGG",
            idDettIntervProcOgg);
      }
      //se i 3 flag sono != null (sono nel caso idTipoLocalizzazione=9)
      if(idDettIntervProcOgg!=null && flagCanale!=null && flagOpereDiPresa!=null && flagCondotta!=null){
    	  dao.updateWDettIntervProcOgg(idDettIntervProcOgg, flagCanale, flagOpereDiPresa, flagCondotta);
      }
      // Inserisco la localizzazione per questo ID_DETT_INTERV_PROC_OGG
      dao.insertLocalizzazioneComuni(idDettIntervProcOgg, ids);
      logOperationOggettoQuadro(logOperationDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<String, Map<String, String>> getMapComuniPiemontesiInInterventoForJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getMapComuniPiemontesiInInterventoForJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getMapComuniPiemontesiInInterventoForJSON(
          idProcedimentoOggetto, idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void eliminaIntervento(long idProcedimentoOggetto, List<Long> ids,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::eliminaInterventoTemporaneo]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      for (Long idIntervento : ids)
      {
        Long idDettIntervProcOgg = dao
            .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
        Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
            idProcedimentoOggetto, idIntervento);
        if (idDettIntervProcOgg != null)
        {
          // Se l'intervento si trova sul temporaneo
          // NEMBO_W_DETT_INTERV_PROC_OGG devo controllare se esiste anche sul
          // consolidato NEMBO_T_DETTAGLIO_INTERVENTO
          if (idDettaglioIntervento != null)
          {
            // Se il record esiste su NEMBO_T_DETTAGLIO_INTERVENTO allora
            // riporto la situazione sulla working allo stato originale
            // Prima elimino i record collegati a NEMBO_W_DETT_INTERV_PROC_OGG
            dao.delete("NEMBO_R_DETT_INTE_PROC_OGG_MIS",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            dao.delete("NEMBO_W_LOCAL_INTERV_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            dao.delete("NEMBO_W_FILE_ALL_INTE_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            // Poi elimino su NEMBO_W_DETT_INTERV_PROC_OGG
            dao.delete("NEMBO_W_DETT_INTERV_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            // E poi li reinserisco con i dati del consolidato
            // (NEMBO_T_DETTAGLIO_INTERVENTO) su NEMBO_W_DETT_INTERV_PROC_OGG
            // modificando il flag_tipo_operazione in "D" (Eliminato)
            idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
                idDettaglioIntervento, idProcedimentoOggetto,
                NemboConstants.INTERVENTI.TIPO_OPERAZIONE_ELIMINAZIONE);
            // Copio i dati delle tabelle figlie
            dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
                idDettIntervProcOgg);
            dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
                idDettIntervProcOgg, null);
            dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
                idDettIntervProcOgg);
          }
          else
          {
            // Se il record NON esiste su NEMBO_T_DETTAGLIO_INTERVENTO allora
            // semplicemente elimino il record e tutti i record figli in quanto
            // non mi serve tenere
            // traccia dell'eliminazione
        	if(dao.isInterventoAssociatoADanni(idProcedimentoOggetto, idIntervento))
        	{
        		dao.delete("NEMBO_R_DANNO_ATM_INTERVENTO", "ID_INTERVENTO", idIntervento, "ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto);
        	}
        	dao.delete("NEMBO_R_ATTIV_PARTECIP_INTERV", "ID_INTERVENTO",idIntervento);
            dao.delete("NEMBO_R_DETT_INTE_PROC_OGG_MIS",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            dao.delete("NEMBO_W_LOCAL_INTERV_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            dao.delete("NEMBO_W_FILE_ALL_INTE_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            dao.delete("NEMBO_W_DETT_INTERV_PROC_OGG",
                "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
            // Elimino il record padre dell'intervento SE E SOLO SE NON ESISTE
            // un altro record per quell'id_intervento su
            // NEMBO_W_DETT_INTERV_PROC_OGG
            // Su NEMBO_T_DETTAGLIO_INTERVENTO non pu� esserci in questo ramo
            // della if
            dao.eliminazioneCondizionaleIntervento(idIntervento);
          }
        }
        else
        {
          // Se l'intervento NON si trova sul temporaneo
          // NEMBO_W_DETT_INTERV_PROC_OGG allora NON PUO' CHE ESSERE SUL
          // CONSOLIDATO NEMBO_T_DETTAGLIO_INTERVENTO
          // Quindi inserisco con i dati del consolidato
          // (NEMBO_T_DETTAGLIO_INTERVENTO) su NEMBO_W_DETT_INTERV_PROC_OGG
          // modificando il flag_tipo_operazione in "D"
          // (Eliminato)
          idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
              idDettaglioIntervento, idProcedimentoOggetto,
              NemboConstants.INTERVENTI.TIPO_OPERAZIONE_ELIMINAZIONE);
          // Copio i dati delle tabelle figlie
          dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg, null);
          dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
        }
      }
      logOperationOggettoQuadro(logOperationDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void updateInterventi(
      List<RigaModificaMultiplaInterventiDTO> listInterventi,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateInterventi]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      // Do per scontato che la lista abbia almeno un elemento, altrimenti il
      // controller avrebbe bloccato l'utente.
      final long idProcedimentoOggetto = listInterventi.get(0)
          .getIdProcedimentoOggetto();
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      for (RigaModificaMultiplaInterventiDTO intervento : listInterventi)
      {
        long idIntervento = intervento.getIdIntervento();
        Long idDettIntervProcOgg = dao
            .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
        if (idDettIntervProcOgg == null)
        {
          // Peccato, non � sulla temporanea, devo duplicarlo dal consolidato
          // sulla temporanea e modificarlo.
          Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
              idProcedimentoOggetto, idIntervento);
          // Duplico la NEMBO_W_DETT_INTERV_PROC_OGG con i dati inseriti
          // dall'utente e quelli del consolidato per i campi che non sono
          // oggetto di modifica a video
          idDettIntervProcOgg = dao
              .copiaDettaglioInterventoSuTemporaneoConModifica(
                  idDettaglioIntervento, intervento);
          // e la NEMBO_W_LOCAL_INTERV_PROC_OGG con le localizzazioni
          dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg, null);
          // Inutile copiare le misurazioni (altrimenti dovrei ricancellarle),
          // tanto le inserisco di nuovo tutte
        }
        else
        {
          // L'intervento esiste sulla temporanea
          // Aggiorno i dati
          dao.updateDettaglioInterventoTemporaneo(idDettIntervProcOgg,
              intervento);
          // E cancello le misurazioni per poterle inserire da zero
          dao.delete("NEMBO_R_DETT_INTE_PROC_OGG_MIS",
              "ID_DETT_INTERV_PROC_OGG", idDettIntervProcOgg);
        }
        // Mancano solo le misurazioni (che sono gi� state eliminate dal db se
        // presenti) ==> le inserisco
        for (MisurazioneInterventoDTO misurazione : intervento
            .getMisurazioneIntervento())
        {
          dao.insertMisurazioneInterventoNonDefinitivo(idDettIntervProcOgg,
              misurazione.getIdMisurazioneIntervento(),
              misurazione.getValore());
        }
        Long idAttivita = intervento.getIdAttivita();
        if (idAttivita != null)
        {
          Long idPartecipante = intervento.getIdPartecipante();
          if (idPartecipante != null)
          {
            dao.delete("NEMBO_R_ATTIV_PARTECIP_INTERV", "ID_INTERVENTO",
                idIntervento);
            dao.insertLegameInterventoPartecipantiAttivita(idIntervento,
                intervento.getIdProcedimentoOggetto(), idAttivita,
                idPartecipante);
          }
        }
      }
      logOperationOggettoQuadro(logOperationDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONConduzioneInteventoDTO> getElencoConduzioniJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getElencoConduzioniJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoConduzioniJSON(idProcedimentoOggetto, idIntervento,
          RigaJSONConduzioneInteventoDTO.class);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONConduzioneInteventoDTO> getElencoConduzioniJSON(
      long idProcedimentoOggetto, String[] idChiaveConduzione, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getElencoConduzioniJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoConduzioniJSON(idProcedimentoOggetto,
          idChiaveConduzione, idProcedimentoAgricoltura);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONParticellaInteventoDTO> getElencoParticelleJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getElencoParticelleJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoParticelleJSON(idProcedimentoOggetto, idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  public void privateInsertLocalizzazioneConduzioni(long idProcedimentoOggetto,
      long idIntervento, Object localizzazioni,
      LogOperationOggettoQuadroDTO logOperationDTO, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
    // Cerco se c'� l'intervento sulla tabella temporanea
    // NEMBO_W_DETT_INTERV_PROC_OGG
    Long idDettIntervProcOgg = dao
        .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
    if (idDettIntervProcOgg == null) // Non esiste su
                                     // NEMBO_W_DETT_INTERV_PROC_OGG ==> ribalto
                                     // il record da
                                     // NEMBO_T_DETTAGLIO_INTERVENTO
    {
      Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
          idProcedimentoOggetto, idIntervento);
      idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
          idDettaglioIntervento, idProcedimentoOggetto,
          NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
      dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg);
    }
    else
    {
      dao.delete("NEMBO_W_LOCAL_INTERV_PROC_OGG", "ID_DETT_INTERV_PROC_OGG",
          idDettIntervProcOgg);
    }
    // Inserisco la localizzazione per questo ID_DETT_INTERV_PROC_OGG
    if (localizzazioni instanceof String[])
    {
      String[] idChiaveConduzione = (String[]) localizzazioni;
      dao.insertLocalizzazioneConduzioni(idProcedimentoOggetto,
          idDettIntervProcOgg, idChiaveConduzione, idProcedimentoAgricoltura);
    }
    else
    {
      if (localizzazioni instanceof List)
      {
        @SuppressWarnings("unchecked")
        List<SuperficieConduzione> superfici = (List<SuperficieConduzione>) localizzazioni;
        dao.insertLocalizzazioneConduzioniBatch(idDettIntervProcOgg, superfici);
      }
    }
    logOperationOggettoQuadro(logOperationDTO);
  }

  @Override
  public void insertLocalizzazioneConduzioni(long idProcedimentoOggetto,
      long idIntervento, String[] idChiaveConduzione,
      LogOperationOggettoQuadroDTO logOperationDTO, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException
  {
    privateInsertLocalizzazioneConduzioni(idProcedimentoOggetto, idIntervento,
        idChiaveConduzione, logOperationDTO, idProcedimentoAgricoltura);
  }

  @Override
  public void insertLocalizzazioneConduzioni(long idProcedimentoOggetto,
      long idIntervento, List<SuperficieConduzione> superfici,
      LogOperationOggettoQuadroDTO logOperationDTO, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException
  {
    privateInsertLocalizzazioneConduzioni(idProcedimentoOggetto, idIntervento,
        superfici, logOperationDTO, idProcedimentoAgricoltura);
  }

  @Override
  public void insertLocalizzazioneParticelle(long idProcedimentoOggetto,
      long idIntervento, List<Long> idParticellaCertificata,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
    // Cerco se c'� l'intervento sulla tabella temporanea
    // NEMBO_W_DETT_INTERV_PROC_OGG
    Long idDettIntervProcOgg = dao
        .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
    if (idDettIntervProcOgg == null) // Non esiste su
                                     // NEMBO_W_DETT_INTERV_PROC_OGG ==> ribalto
                                     // il record da
                                     // NEMBO_T_DETTAGLIO_INTERVENTO
    {
      Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
          idProcedimentoOggetto, idIntervento);
      idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
          idDettaglioIntervento, idProcedimentoOggetto,
          NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
      dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg);
    }
    else
    {
      dao.delete("NEMBO_W_LOCAL_INTERV_PROC_OGG", "ID_DETT_INTERV_PROC_OGG",
          idDettIntervProcOgg);
    }
    if (idDettIntervProcOgg != null)
    {
      // Inserisco la localizzazione per questo ID_DETT_INTERV_PROC_OGG
      dao.insertLocalizzazioneParticelle(idDettIntervProcOgg,
          idParticellaCertificata);
    }
    logOperationOggettoQuadro(logOperationDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONParticellaInteventoDTO> ricercaParticelle(
      FiltroRicercaParticelle filtro) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::ricercaParticelle]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.ricercaParticelle(filtro);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RigaJSONAllegatiInterventoDTO> getAllegatiJSON(
      long idProcedimentoOggetto, long idIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getAllegatiJSON]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getAllegatiJSON(idProcedimentoOggetto, idIntervento);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void insertLocalizzazioneMappeFile(long idProcedimentoOggetto,
      Long idIntervento, FileAllegatoInterventoDTO file,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
    // Cerco se c'� l'intervento sulla tabella temporanea
    // NEMBO_W_DETT_INTERV_PROC_OGG
    Long idDettIntervProcOgg = dao
        .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
    if (idDettIntervProcOgg == null) // Non esiste su
                                     // NEMBO_W_DETT_INTERV_PROC_OGG ==> ribalto
                                     // il record da
                                     // NEMBO_T_DETTAGLIO_INTERVENTO
    {
      Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
          idProcedimentoOggetto, idIntervento);
      idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
          idDettaglioIntervento, idProcedimentoOggetto,
          NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
      dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg);
    }
    if (idDettIntervProcOgg != null)
    {
      long idFileAllegatiIntervento = dao.insertFileAllegatoIntervento(file);
      dao.insertLocalizzazioneMappeFile(idDettIntervProcOgg,
          idFileAllegatiIntervento);
    }
    logOperationOggettoQuadro(logOperationDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public FileAllegatoInterventoDTO getFileFisicoAllegato(
      long idProcedimentoOggetto, long idIntervento,
      long idFileAllegatiIntervento)
      throws InternalUnexpectedException
  {
    return dao.getFileFisicoAllegato(idProcedimentoOggetto, idIntervento,
        idFileAllegatiIntervento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public void eliminaAllegati(long idProcedimentoOggetto, long idIntervento,
      List<Long> idFileAllegatiIntervento,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    for (Long id : idFileAllegatiIntervento)
    {
      eliminaAllegato(idProcedimentoOggetto, idIntervento, id);
    }
    logOperationOggettoQuadro(logOperationDTO);
  }

  protected void eliminaAllegato(long idProcedimentoOggetto, long idIntervento,
      long idFileAllegatiIntervento)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::eliminaAllegato]";
    if (dao.isAllegatoAppartenenteProcedimentoOggettoEIntervento(
        idFileAllegatiIntervento, idProcedimentoOggetto, idIntervento))
    {
      // Come al solito... prima controllo che sia presente sulla working...
      Long idDettIntervProcOgg = dao
          .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
      if (idDettIntervProcOgg == null) // Non esiste su
                                       // NEMBO_W_DETT_INTERV_PROC_OGG ==>
                                       // ribalto il record da
                                       // NEMBO_T_DETTAGLIO_INTERVENTO
      {
        // Se non c'� allora � sul consolidato, quindi devo ribaltare dal
        // consolidato alla working
        Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
            idProcedimentoOggetto, idIntervento);
        idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
            idDettaglioIntervento, idProcedimentoOggetto,
            NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
        dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
            idDettIntervProcOgg);
        // Copio tutti gli allegati ad esclusione di quello con
        // idFileAllegatiIntervento (il metodo � gi� predisposto per escludere
        // un allegato, se presente,
        // in fase di copia)
        dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
            idDettIntervProcOgg, idFileAllegatiIntervento);
      }
      else
      {
        // E' gi� sulla working... elimino semplicemente la relazione con
        // l'intervento
        dao.delete("NEMBO_W_FILE_ALL_INTE_PROC_OGG",
            "ID_FILE_ALLEGATI_INTERVENTO", idFileAllegatiIntervento);
      }
      // Elimino fisicamente l'allegato se non � pi� referenziato
      dao.deleteFileAllegatiInterventoSeNonReferenziato(
          idFileAllegatiIntervento);
    }
    else
    {
      // L'allegato non � parte dell'intervento del procedimento oggetto in
      // questione ==> Bug o tentativo di forzare il sistema ==>
      // Non eseguo delete ma registro la notizIa
      logger.warn(THIS_METHOD
          + " Attenzione: Richiesta di eliminazione dell'allegato con idFileAllegatiIntervento #"
          + idFileAllegatiIntervento
          + " che per� non appartiene all'intervento #" + idIntervento
          + " e al procedimento oggetto #" + idProcedimentoOggetto);
    }
  }

  @Override
  public List<DatiLocalizzazioneParticellarePerStampa> getLocalizzazioneParticellePerStampa(
      long idProcedimentoOggetto, String flagEscludiCatalogo)
      throws InternalUnexpectedException
  {
    return dao.getLocalizzazioneParticellePerStampa(idProcedimentoOggetto,
        flagEscludiCatalogo);
  }

  @Override
  public boolean isBandoConPercentualeRiduzione(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.isBandoConPercentualeRiduzione(idProcedimentoOggetto);
  }

  @Override
  public void updatePercentualeRibassoInterventi(long idProcedimentoOggetto,
      BigDecimal percentuale) throws InternalUnexpectedException
  {
    dao.updatePercentualeRibassoInterventi(idProcedimentoOggetto, percentuale);
  }

  @Override
  public InfoRiduzione getInfoRiduzione(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getInfoRiduzione(idProcedimentoOggetto);
  }

  @Override
  public void updateInterventiQuadroEconomico(
      List<RigaJSONInterventoQuadroEconomicoDTO> list,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateInterventi]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      // Do per scontato che la lista abbia almeno un elemento, altrimenti il
      // controller avrebbe bloccato l'utente.
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      for (RigaJSONInterventoQuadroEconomicoDTO intervento : list)
      {
        long idIntervento = intervento.getIdIntervento();
        Long idDettIntervProcOgg = dao
            .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
        if (idDettIntervProcOgg == null)
        {
          // Peccato, non � sulla temporanea, devo duplicarlo dal consolidato
          // sulla temporanea e modificarlo.
          Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
              idProcedimentoOggetto, idIntervento);
          // Duplico la NEMBO_W_DETT_INTERV_PROC_OGG con i dati inseriti
          // dall'utente e quelli del consolidato per i campi che non sono
          // oggetto di modifica a video
          idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
              idDettaglioIntervento, idProcedimentoOggetto,
              NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA_QUADRO_ECONOMICO);
          dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          // e la NEMBO_W_LOCAL_INTERV_PROC_OGG con le localizzazioni
          dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg, null);
        }
        dao.updateInterventoDatiQuadroEconomico(idDettIntervProcOgg,
            intervento.getImportoAmmesso(),
            intervento.getPercentualeContributo(),
            intervento.getImportoContributo());
      }
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public void modificaPercentualeInterventoQuadroEconomico(long[] idsIntervento,
      Map<Long, BigDecimal> mapInterventiPercentuali,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateInterventi]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      // Do per scontato che la lista abbia almeno un elemento, altrimenti il
      // controller avrebbe bloccato l'utente.
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      BigDecimal percNettaRiduzione = BigDecimal.ZERO;
      if (dao.isBandoConPercentualeRiduzione(idProcedimentoOggetto))
      {
        InfoRiduzione infoRiduzione = dao
            .getInfoRiduzione(idProcedimentoOggetto);
        if (infoRiduzione != null)
        {
          percNettaRiduzione = infoRiduzione.getPercentuale();
        }
      }
      percNettaRiduzione = percNettaRiduzione.scaleByPowerOfTen(-2);
      for (long idIntervento : idsIntervento)
      {
        Long idDettIntervProcOgg = dao
            .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
        if (idDettIntervProcOgg == null)
        {
          // Peccato, non � sulla temporanea, devo duplicarlo dal consolidato
          // sulla temporanea e modificarlo.
          Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
              idProcedimentoOggetto, idIntervento);
          // Duplico la NEMBO_W_DETT_INTERV_PROC_OGG con i dati inseriti
          // dall'utente e quelli del consolidato per i campi che non sono
          // oggetto di modifica a video
          idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
              idDettaglioIntervento, idProcedimentoOggetto,
              NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA_QUADRO_ECONOMICO);
          dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          // e la NEMBO_W_LOCAL_INTERV_PROC_OGG con le localizzazioni
          dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg);
          dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
              idDettIntervProcOgg, null);
          // Inutile copiare le misurazioni (altrimenti dovrei ricancellarle),
          // tanto le inserisco di nuovo tutte
        }
        dao.updatePercentualeInterventoQuadroEconomico(idDettIntervProcOgg,
            mapInterventiPercentuali.get(idIntervento), percNettaRiduzione);
      }
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  @Override
  public List<RangePercentuale> getRangePercentuali(long[] idIntervento)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getRangePercentuali";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getRangePercentuali(idIntervento);
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
  public List<RigaRendicontazioneSpese> getElencoRendicontazioneSpese(
      long idProcedimentoOggetto, List<Long> ids)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getElencoRendicontazioneSpese";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoRendicontazioneSpese(idProcedimentoOggetto, ids);
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
  public List<RigaAccertamentoSpese> getElencoAccertamentoSpese(
      long idProcedimentoOggetto, List<Long> ids)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getElencoAccertamentoSpese";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getElencoAccertamentoSpese(idProcedimentoOggetto, ids);
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
  public void updateRendicontazioneSpese(List<RigaRendicontazioneSpese> list,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
        .getIdProcedimentoOggetto();
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    for (RigaRendicontazioneSpese riga : list)
    {
      // Provo ad eseguire l'aggiornamento del record
      int numRecord = dao.updateRendicontazioneSpese(idProcedimentoOggetto,
          riga);
      if (numRecord == 0)
      {
        // Se non ho aggiornato nessun record vuol dire che il record non c'�
        // ==> lo inserisco
        dao.insertRendicontazioneSpese(idProcedimentoOggetto, riga);
      }
    }
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public void updateAccertamentoSpese(List<AccertamentoSpeseDTO> list,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
        .getIdProcedimentoOggetto();
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    for (AccertamentoSpeseDTO riga : list)
    {
      // Provo ad eseguire l'aggiornamento del record
      int numRecord = dao.updateAccertamentoSpese(idProcedimentoOggetto, riga);
      if (numRecord == 0)
      {
        // Se non ho aggiornato nessun record vuol dire che il record non c'�
        // ==> lo inserisco
        dao.insertAccertamentoSpese(idProcedimentoOggetto, riga);
      }
    }
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  protected long forzaInterventoSuTabelleDiWorking(long idIntervento,
      long idProcedimentoOggetto,
      String flagTipoOperazione)
      throws InternalUnexpectedException
  {
    Long idDettIntervProcOgg = dao
        .findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
    if (idDettIntervProcOgg == null)
    {
      // Peccato, non � sulla temporanea, devo duplicarlo dal consolidato sulla
      // temporanea.
      Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(
          idProcedimentoOggetto, idIntervento);
      // Duplico la NEMBO_W_DETT_INTERV_PROC_OGG con i dati inseriti dall'utente
      // e quelli del consolidato per i campi che
      // non sono oggetto di modifica a video
      idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(
          idDettaglioIntervento, idProcedimentoOggetto,
          flagTipoOperazione);
      dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg);
      // e la NEMBO_W_LOCAL_INTERV_PROC_OGG con le localizzazioni
      dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg);
      dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento,
          idDettIntervProcOgg, null);
    }
    return idDettIntervProcOgg;
  }

  @Override
  public void updateMisurazioniInterventoSaldo(
      List<RigaModificaMultiplaInterventiDTO> list,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "updateMisurazioniInterventoSaldo";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      dao.lockProcedimentoOggetto(idProcedimentoOggetto);
      for (RigaModificaMultiplaInterventiDTO intervento : list)
      {
        long idDettIntervProcOgg = forzaInterventoSuTabelleDiWorking(
            intervento.getIdIntervento(),
            idProcedimentoOggetto,
            NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
        dao.updateMisurazioniInterventoSaldo(idDettIntervProcOgg,
            intervento.getMisurazioneIntervento());
      }
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
  public List<DecodificaDTO<Long>> findAttivitaDisponibiliPerProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    final String THIS_METHOD = "findAttivitaDisponibiliPerProcedimentoOggetto";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao
          .findAttivitaDisponibiliPerProcedimentoOggetto(idProcedimentoOggetto);
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
  public List<DecodificaDTO<Long>> findPartecipantiAttivitaInProcedimentoOggetto(
      long idProcedimentoOggetto, long idAttivita)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "findPartecipantiAttivitaInProcedimentoOggetto";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.findPartecipantiAttivitaInProcedimentoOggetto(
          idProcedimentoOggetto, idAttivita);
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
  public List<DecodificaDualLIstDTO<Long>> getElencoInterventiPerDocSpesa(
      long idProcedimento, ArrayList<Long> idsDocSpesa, boolean disponibili)
      throws InternalUnexpectedException
  {
    return dao.getElencoInterventiPerDocSpesa(idProcedimento, idsDocSpesa,
        disponibili);
  }

  @Override
  public List<RigaJSONInterventoQuadroEconomicoByLivelloDTO> getElencoInterventiByLivelliQuadroEconomico(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao
        .getElencoInterventiByLivelliQuadroEconomico(idProcedimentoOggetto);
  }

  @Override
  public RigaElencoInterventi getDettaglioInterventoById(long idProcedimento,
      long idIntervento) throws InternalUnexpectedException
  {
    return dao.getDettaglioInterventoById(idProcedimento, idIntervento);
  }

  @Override
  public BigDecimal getSommamportiDocumentoSpesaIntervento(
      long idDocumentoSpesa) throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getSommamportiDocumentoSpesaIntervento";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getSommamportiDocumentoSpesaIntervento(idDocumentoSpesa);
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
  public List<DecodificaInterventoDTO> getListInvestimentiPossibili(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getListInterventiPossibili]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getListInvestimentiPossibili(idProcedimentoOggetto);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }
  
  @Override
  public void modificaFlagAssociatoAltraMisura(long idIntervento, String flagAssociatoAltraMisura, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateInterventi]";
    final boolean debugLevel = logger.isDebugEnabled();
    if (debugLevel)
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      // Accesso esclusivo agli interventi di questo procedimento
      // Do per scontato che la lista abbia almeno un elemento, altrimenti il controller avrebbe bloccato l'utente.
      long idProcedimentoOggetto = logOperationOggettoQuadroDTO.getIdProcedimentoOggetto();
      dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
      Long idDettIntervProcOgg = dao.findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
      if (idDettIntervProcOgg == null)
      {
        // Peccato, non � sulla temporanea, devo duplicarlo dal consolidato sulla temporanea e modificarlo.
        Long idDettaglioIntervento = dao.findIdDettaglioMisurazioneIntervento(idProcedimentoOggetto, idIntervento);
        // Duplico la NEMBO_W_DETT_INTERV_PROC_OGG con i dati inseriti dall'utente e quelli del consolidato per i campi che non sono oggetto di modifica a video
        idDettIntervProcOgg = dao.copiaDettaglioInterventoSuTemporaneo(idDettaglioIntervento, idProcedimentoOggetto,
            NemboConstants.INTERVENTI.TIPO_OPERAZIONE_MODIFICA);
        dao.copiaMisurazioneInterventoSuTemporaneo(idDettaglioIntervento, idDettIntervProcOgg);
        // e la NEMBO_W_LOCAL_INTERV_PROC_OGG con le localizzazioni
        dao.copiaLocalizzazioneInterventoSuTemporaneo(idDettaglioIntervento, idDettIntervProcOgg);
        dao.copiaAllegatiInterventoSuTemporaneo(idDettaglioIntervento, idDettIntervProcOgg, null);
        // Inutile copiare le misurazioni (altrimenti dovrei ricancellarle), tanto le inserisco di nuovo tutte
      }
      dao.updateFlagAssociatoAltraMisura(idDettIntervProcOgg, flagAssociatoAltraMisura);
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    finally
    {
      if (debugLevel)
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

@Override
public ZonaAltimetricaDTO getZonaAltimetricaProcedimento(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
{
	return dao.getZonaAltimetricaProcedimento(idProcedimentoOggetto, idProcedimentoAgricoltura);
}

	@Override
	public List<String> getFlagCanaleOpereCondotta(long idProcedimentoOggetto, long idIntervento) throws InternalUnexpectedException {
		String THIS_METHOD = "[" + THIS_CLASS + "::getFlagCanaleOpereCondotta]";
		final boolean debugLevel = logger.isDebugEnabled();
		//controllo se su NEMBO_W_DETT_INTERV_PROC_OGG esiste il record -> leggo i flAG
		try{
			// Accesso esclusivo agli interventi di questo intervento
		    dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
		    Long idDettIntervProcOgg = dao.findIdWDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
		    if (idDettIntervProcOgg != null){
		    	List<String> flags = dao.getFlagCanaleOpereCondotta(idDettIntervProcOgg);
		    	if(flags!=null && flags.size()==3){
		    		return flags;
		    	}else{
		    		return null;
		    	}
		    }else{
		    	Long idDettInterv = dao.findIdTDettIntervProcOgg(idProcedimentoOggetto, idIntervento);
		    	if (idDettInterv != null){
			    	List<String> flags = dao.getTFlagCanaleOpereCondotta(idDettInterv);
			    	if(flags!=null && flags.size()==3){
			    		return flags;
			    	}else{
			    		return null;
			    	}
		    }		
		}
		}
		finally
	    {
	      if (debugLevel)
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
		return null;
	}

  
  public int insertInterventoPrevenzioneIstruttoria(long idProcedimentoOggetto,
			List<RaggruppamentoLivelloCriterio> listaRaggruppamento, 
			LogOperationOggettoQuadroDTO logOperationOggettoQuadro) throws InternalUnexpectedException, ApplicationException
{
	  BigDecimal centocinquanta = new BigDecimal("150.00");
	  Map<Long,Boolean> mappaIdBandoLivelloCriterio = new HashMap<>(); 
	  
	  List<RigaElencoInterventi> listaInterventiPrevenzioneConsolidati = dao.getListInterventiPrevenzioneConsolidati(idProcedimentoOggetto);
	  List<RigaElencoInterventi> listaInterventiPrevenzioneTemporanei = dao.getListInterventiPrevenzioneTemporanei(idProcedimentoOggetto);
	 
	  boolean inserisciInterventoPrevenzione = false;
	  
	  //nuovo calcolo punteggio
		BigDecimal punteggio  = BigDecimal.ZERO;
		for(RaggruppamentoLivelloCriterio r : listaRaggruppamento)
		{
      punteggio = calcolaPunteggio(punteggio, r);
		}
		
	  //creazione degli oggetti di tipo intervento da inserire
	  MisurazioneDescrizioneInterventoDTO datiIntervento = dao.getIdMisurazioneInterventoPrevenzione();
	  //creo la lista degli interventi da modificare (un solo intervento, quello di prevenzione)
	  RigaModificaMultiplaInterventiDTO intervento = new RigaModificaMultiplaInterventiDTO();
	  intervento.setIdProcedimentoOggetto(idProcedimentoOggetto);
	  intervento.setIdDescrizioneIntervento(datiIntervento.getIdDescrizioneIntervento());
	  intervento.setImportoUnitario(centocinquanta);
	  intervento.setImporto(centocinquanta.multiply(punteggio));

	  //imposto i dati della misurazione dell'intervento (punteggio e unit� di misura associata al codice intervento PREV)
	  List<MisurazioneInterventoDTO> misurazioniIntervento = new ArrayList<>();
	  MisurazioneInterventoDTO misurazioneIntervento = new MisurazioneInterventoDTO();
	  misurazioneIntervento.setIdMisurazioneIntervento(datiIntervento.getIdMisurazioneIntervento());
	  misurazioneIntervento.setValore(punteggio);
	  misurazioniIntervento.add(misurazioneIntervento);
	  intervento.setMisurazioneIntervento(misurazioniIntervento);
		
	  //non vi sono interventi consolidati, allora creo degli interventi temporanei
	  if(listaInterventiPrevenzioneConsolidati == null || listaInterventiPrevenzioneConsolidati.isEmpty())
	  {
		  if(listaInterventiPrevenzioneTemporanei != null && !listaInterventiPrevenzioneTemporanei.isEmpty())
		  {
			  List<Long> ids = new ArrayList<>();
			  for(RigaElencoInterventi interventoTemporaneo : listaInterventiPrevenzioneTemporanei)
			  {
				  ids.add(interventoTemporaneo.getIdIntervento());
			  }
			  eliminaIntervento(idProcedimentoOggetto, ids, logOperationOggettoQuadro);
		  }
		  if(inserisciInterventoPrevenzione)
		  {
			  List<RigaModificaMultiplaInterventiDTO> listInterventi = new ArrayList<>();
			  listInterventi.add(intervento);
			  insertInterventi(listInterventi , null, logOperationOggettoQuadro);
		  }
	  }
	  else
	  {
		  //vi � gi� un intervento consolidato di prevenzione PREV
		  //aggiorno l'intervento temporaneo se gi� esiste

		  RigaElencoInterventi interventoConsolidato = listaInterventiPrevenzioneConsolidati.get(0);
		  long idIntervento = interventoConsolidato.getIdIntervento();
		  intervento.setIdIntervento(idIntervento);
		  
		  //aggiornare o eliminare l'intervento in base al punteggio
      aggiornaEliminaIntervento(inserisciInterventoPrevenzione, intervento);
		  if(inserisciInterventoPrevenzione)
		  {
			  List<RigaModificaMultiplaInterventiDTO> listInterventi = new ArrayList<>();
			  listInterventi.add(intervento);
			  updateInterventi(listInterventi, logOperationOggettoQuadro);
			  dao.updateFlagTipoOperazioneInterventoW(intervento.getIdIntervento(),NemboConstants.INTERVENTI.TIPO_OPERAZIONE_INSERIMENTO);
		  }
		  else
		  {
			  List<Long> ids = new ArrayList<>();
			  ids.add(idIntervento);
			  eliminaIntervento(idProcedimentoOggetto, ids , logOperationOggettoQuadro);
		  }
	  }
	  return 0;
}

BigDecimal calcolaPunteggio(BigDecimal punteggio, RaggruppamentoLivelloCriterio r){

			for(CriterioVO criterio : r.getCriteri())
			{
				mappaIdBandoLivelloCriterio.put(criterio.getIdBandoLivelloCriterio(), Boolean.TRUE);
				BigDecimal punteggioTmp = BigDecimal.ZERO;
				if(criterio.getPunteggioIstruttoria() == null)
				{
		    		if(criterio.getPunteggioCalcolato() != null)
		    		{
		    			punteggioTmp=criterio.getPunteggioCalcolato();
		    		}
		    	}else
		    	{
		    		punteggioTmp = criterio.getPunteggioIstruttoria();
		    	}
				punteggio = punteggio.add(punteggioTmp);
				
				if(mappaCodiciPunteggioPerInterventiPrevenzione.containsKey((criterio.getCodice())) && punteggioTmp.compareTo(BigDecimal.ZERO) > 0)
				{
					inserisciInterventoPrevenzione = true;
				}
			}
return punteggio;
}


	@Override
	public String getCodiceIdentificativoIntervento(Long idIntervento) throws InternalUnexpectedException {
		return dao.getCodiceIdentificativoIntervento(idIntervento);
	}
	
}
