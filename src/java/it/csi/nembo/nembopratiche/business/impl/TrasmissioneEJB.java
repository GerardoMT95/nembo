package it.csi.nembo.nembopratiche.business.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.business.ITrasmissioneEJB;
import it.csi.nembo.nembopratiche.dto.ProcedimentoEProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.ProcedimOggettoStampaDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.integration.TrasmissioneMassivaDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;

@Stateless()
@EJB(name = "java:app/Trasmissione", beanInterface = ITrasmissioneEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Remote(ITrasmissioneEJB.class)
public class TrasmissioneEJB implements ITrasmissioneEJB
{
  private static final String              THIS_CLASS     = TrasmissioneEJB.class
      .getSimpleName();
  protected static final Logger            logger         = Logger
      .getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".business");
  protected SessionContext                 sessionContext;

  private transient TrasmissioneMassivaDAO dao            = null;
  private transient IQuadroEJB             quadroEJB      = null;

  public static final int                  ESITO_POSITIVO = 1;

  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
    try
    {
      quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    }
    catch (Exception e)
    {

    }
    try
    {
      initializeDAO();
    }
    catch (NamingException e)
    {
    }
  }

  private IQuadroEJB getEJBQuadro() throws NamingException
  {
    if (quadroEJB == null)
    {
      quadroEJB = NemboUtils.APPLICATION.getEjbQuadro();
    }
    return quadroEJB;
  }

  private TrasmissioneMassivaDAO getDao() throws NamingException
  {
    if (dao == null)
    {
      initializeDAO();
    }
    return dao;
  }

  protected void initializeDAO() throws NamingException
  {
    dao = new TrasmissioneMassivaDAO();
    dao.initializeDAO();
  }

  public String trasmettiIstanzaElaborazioneMassiva(long idProcedimentoOggetto,
      Date dataTrasmissione, long idUtenteLogin) throws Exception
  {
    final String THIS_METHOD = "trasmettiIstanzaElaborazioneMassiva";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      UtenteAbilitazioni utenteAbilitazioni = PapuaservProfilazioneServiceFactory
          .getRestServiceClient()
          .getUtenteAbilitazioniByIdUtenteLogin(idUtenteLogin);
      ProcedimentoEProcedimentoOggetto ppo = getDao()
          .getProcedimentoEProcedimentoOggettoByIdProcedimentoOggetto(
              idProcedimentoOggetto);
      ProcedimentoOggetto po = ppo.getProcedimentoOggetto();
      po.setQuadri(
          getDao().getQuadriProcedimentoOggetto(idProcedimentoOggetto));
      String errore = "" ; //getEJBQuadro().trasmettiIstanza(po.getIdProcedimento(),
          //idProcedimentoOggetto, po.getIdBandoOggetto(),
          //po.getIdentificativo(), utenteAbilitazioni, "");
      if (errore != null)
      {
        return errore;
      }
      getDao().aggiornaDataTrasmissioneDopoTrasmissioneMassiva(
          idProcedimentoOggetto, dataTrasmissione);
      return null;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " END.");
      }
    }
  }

  public String firmaGrafometrica(long oldIdDocumentoIndex,
      long newIdDocumentoIndex, long idUtenteAggiornamento,
      Date timestamp) throws Exception
  {
    final String THIS_METHOD = "trasmettiIstanzaFirmaGrafometrica";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      /* Richiedo accesso esclusivo al PROCEDIMENTO_OGGETTO in scrittura */
      Long idProcedimentoOggettoLock = dao
          .lockProcedimentoOggettoAndProcedimentoByIdDocumentoIndexPerTrmsmissione(
              oldIdDocumentoIndex);
      if (idProcedimentoOggettoLock == null)
      {
        // Non trovo il procedimento oggetto associato al documento... ==>
        // Errore grave
        throw new ApplicationException(
            "Non è stata trovata alcuna pratica collegata a questo documento. Impossibile procedere con la trasmissione.");
      }
      final long idProcedimentoOggetto = idProcedimentoOggettoLock.longValue();
      ProcedimOggettoStampaDTO procedimOggettoStampaDTO = dao
          .findIdProcedimOggettoStampaByIdDocumentoIndex(oldIdDocumentoIndex);
      /*
       * L'oggetto è sicuramente valorizzato altrimenti se l'idDocumentoIndex
       * non fosse presente su db mi avrebbe già fermato il controllo del lock.
       * Procedo con i controlli formali
       */
      if (procedimOggettoStampaDTO.getDataFine() != null)
      {
        // Non dovrebbe mai capitare, comunque è sempre meglio eseguire i test
        // del caso
        throw new ApplicationException(
            "Il documento non risulta essere la versione finale della stampa della pratica, esiste una stampa posteriore ad esso. Impossibile procedere con la trasmissione.");
      }

      if (procedimOggettoStampaDTO
          .getIdStatoStampa() != NemboConstants.STATO.STAMPA.ID.IN_ATTESA_FIRMA_GRAFOMETRICA)
      {
        // Non dovrebbe mai capitare, comunque è sempre meglio eseguire i test
        // del caso
        throw new ApplicationException(
            "Il documento non risulta essere in attesa di firma grafometrica. Impossibile procedere con la trasmissione.");
      }
      // Controlli base superati ==> verifico la situazione del procedimento
      // oggetto
      ProcedimentoOggetto procedimentoOggetto = dao
          .findProcedimentoOggetto(idProcedimentoOggetto);
      Long idEsito = procedimentoOggetto.getIdEsito();
      /*
       * Il procedimento oggetto deve essere in attesa di trasmissione ossia con
       * idEsito == 1 (POSITIVO). Anche questo caso non dovrebbe mai capitare,
       * comunque è sempre meglio eseguire il test
       */

      if (idEsito == null || idEsito.intValue() != ESITO_POSITIVO)
      {
        throw new ApplicationException(
            "Il documento risulta essere associato ad una domanda NON in attesa di trasmissione. Impossibile procedere con la trasmissione.");
      }

      // Potrei avere più stampe da firmare, devono essere tutte firmate per
      // trasmettere
      long countStampeInCorso = dao.countStampeInCorso(idProcedimentoOggetto);

      long idProcedimOggettoStampa = procedimOggettoStampaDTO
          .getIdProcedimOggettoStampa();
      dao.closeProcedimOggettoStampa(idProcedimOggettoStampa);
      dao.cloneProcedimOggettoStampaAndUpdateExtIdDocumentoIndex(
          idProcedimOggettoStampa, newIdDocumentoIndex,
          idUtenteAggiornamento);

      if (countStampeInCorso > 0)
      {
        // Non è un errore applicativo ==> semplicemente non posso trasmettere
        return "La pratica collegata al documento ha ancora "
            + countStampeInCorso
            + " stampe in corso o fallite. Impossibile procedere con la trasmissione.";
      }
      String errore = trasmettiIstanzaElaborazioneMassiva(idProcedimentoOggetto,
          timestamp, idUtenteAggiornamento);
      if (errore != null)
      {
        sessionContext.setRollbackOnly();
      }
      return errore;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " END.");
      }
    }
  }
}
