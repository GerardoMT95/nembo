package it.csi.nembo.nembopratiche.business.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.multipart.MultipartFile;

import it.csi.nembo.nembopratiche.business.IInterventiEJB;
import it.csi.nembo.nembopratiche.business.IQuadroEJB;
import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaAmmCompentenza;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaSoggFirmatario;
import it.csi.nembo.nembopratiche.dto.ElencoCduDTO;
import it.csi.nembo.nembopratiche.dto.ExcelRicevutePagInterventoDTO;
import it.csi.nembo.nembopratiche.dto.ExcelRigaDocumentoSpesaDTO;
import it.csi.nembo.nembopratiche.dto.FornitoreDTO;
import it.csi.nembo.nembopratiche.dto.GraduatoriaDTO;
import it.csi.nembo.nembopratiche.dto.ImportoInterventoVO;
import it.csi.nembo.nembopratiche.dto.ImportoLiquidatoDTO;
import it.csi.nembo.nembopratiche.dto.IntegrazioneAlPremioDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoEProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.ProcedimentoEstrattoVO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.ProspettoEconomicoDTO;
import it.csi.nembo.nembopratiche.dto.RegistroAntimafiaDTO;
import it.csi.nembo.nembopratiche.dto.RiduzioniSanzioniDTO;
import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.dto.RipartizioneImportoDTO;
import it.csi.nembo.nembopratiche.dto.VolturaDTO;
import it.csi.nembo.nembopratiche.dto.danni.RigaSegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.dto.danni.SegnalazioneDannoDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.EstrazioneACampioneDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.NumeroLottoDTO;
import it.csi.nembo.nembopratiche.dto.internal.LogParameter;
import it.csi.nembo.nembopratiche.dto.internal.LogVariable;
import it.csi.nembo.nembopratiche.dto.login.ProcedimentoAgricoloDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.FileAllegatoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.dto.permission.UpdatePermissionProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.plsql.EstrazioneCampioneDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainPunteggioDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.DocumentoSpesaVO;
import it.csi.nembo.nembopratiche.dto.procedimento.OrganismoDelegatoDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimento.RicevutaPagamentoVO;
import it.csi.nembo.nembopratiche.dto.procedimento.TestataProcedimento;
import it.csi.nembo.nembopratiche.dto.procedimento.TipoDocumentoSpesaVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.OggettoIconaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.QuadroOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.ResponsabileDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.allegati.ContenutoFileAllegatiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.allegati.FileAllegatiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.allegati.SelezioneInfoInserimentoAllegatiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.DatiAnticipo;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.DatiAnticipoModificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.SospensioneAnticipoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.anticipo.SportelloBancaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.conticorrenti.ContoCorrenteDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.conticorrenti.ContoCorrenteEstesoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlli.ControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlli.FonteControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlli.GiustificazioneAnomaliaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.AnnoExPostsDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.ControlloAmministrativoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.EsitoControlliAmmDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.InfoExPostsDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.VisitaLuogoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliamministrativi.VisitaLuogoExtDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliinlocomisureinvestimento.ControlliInLocoInvestDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.controlliinlocomisureinvestimento.DatiSpecificiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datafinelavori.DataFineLavoriDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativi;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.DatiIdentificativiModificaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.datiidentificativi.SettoriDiProduzioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.DettaglioInfoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.GruppoInfoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.dichiarazioni.ValoriInseritiDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.esitofinale.EsitoFinaleDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.estrazionecampione.RigaSimulazioneEstrazioneDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DecodificaInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.DettaglioInterventoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneDocumentiSpesaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaRendicontazioneSpese;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.partecipanti.PartecipanteDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.CriterioVO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.punteggi.RaggruppamentoLivelloCriterio;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.ProcedimOggettoStampaDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.SegnapostoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoIconaDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException;
import it.csi.nembo.nembopratiche.exception.NemboPermissionException.ExceptionType;
import it.csi.nembo.nembopratiche.integration.ImportoAnticipoLivelloDTO;
import it.csi.nembo.nembopratiche.integration.PermissionDAO;
import it.csi.nembo.nembopratiche.integration.QuadroNewDAO;
import it.csi.nembo.nembopratiche.integration.RendicontazioneEAccertamentoSpeseDAO;
import it.csi.nembo.nembopratiche.integration.RigaAnticipoLivello;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ComuneVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.DocumentoRefVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.DocumentoVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.IndirizzoVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.InsertProtocolloRequestVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ListaDestinatariPecCCVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ListaDocumentiVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.MittenteVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.NotificaByPecRequestVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.ProtocolloResponseVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.TipoDocumentoVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.TipoProtocolloVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.UfficioProtocollazioneVO;
import it.csi.nembo.nembopratiche.integration.ws.gaaserv.protocollo.UsernameTokenVO;
import it.csi.nembo.nembopratiche.util.DumpUtils;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;
import it.csi.nembo.nembopratiche.util.WsUtils;
import it.csi.nembo.nembopratiche.util.stampa.placeholder.PlaceHolderManager;
import it.csi.papua.papuaserv.dto.gestioneutenti.UtenteLogin;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;
import it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory;
import it.csi.smrcomms.siapcommws.dto.smrcomm.SiapCommWsDatiInvioMailPecVO;
import it.csi.smrcomms.smrcomm.dto.agriwell.AgriWellEsitoVO;
import it.csi.solmr.dto.anag.services.DelegaAnagrafeVO;

@Stateless()
@EJB(name = "java:app/Quadro", beanInterface = IQuadroEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class QuadroEJB extends NemboAbstractEJB<QuadroNewDAO>
    implements IQuadroEJB
{
  private static final String                  THIS_CLASS = QuadroEJB.class
      .getSimpleName();
  @Autowired
  private PermissionDAO                        permissionDAO;
  @Autowired
  private RendicontazioneEAccertamentoSpeseDAO rendicontazioneEAccertamentoSpeseDAO;
  private SessionContext                       sessionContext;

  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
  }
  
  
  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DatiIdentificativi getDatiIdentificativiProcedimentoOggetto(
      long idProcedimentoOggetto, long idQuadroOggetto, Date dataValidita)
      throws InternalUnexpectedException
  {
    return dao.getDatiIdentificativiProcedimentoOggetto(idProcedimentoOggetto,
        idQuadroOggetto, dataValidita, dao.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto));
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DatiIdentificativiModificaDTO getDatiIdentificativiModificaProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getDatiIdentificativiModificaProcedimentoOggetto(
        idProcedimentoOggetto);
  }

  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<QuadroOggettoDTO> getQuadriProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getQuadriProcedimentoOggetto(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaAmmCompentenza> getListAmmCompetenzaAbilitateBando(
      long idBando) throws InternalUnexpectedException
  {
    return dao.getListAmmCompetenzaAbilitateBando(idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaSoggFirmatario> getListSoggettiFirmatari(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getListSoggettiFirmatari(idProcedimentoOggetto);
  }

  @Override
  public void updateDatiProcedimentoOggetto(long idProcedimentoOggetto,
      long idQuadroOggetto, long idBandoOggetto, Integer idAmmCompetenza,
      String note, long extIdUtenteAggiornamento, String idContitolare,
      Long idSettore, String cup, String avvioIstruttoria, boolean hasVercod,
      Long vercod, Date dataVisuraCamerale)
      throws InternalUnexpectedException, NemboPermissionException,
      ApplicationException
  {
    Long idProcedimento = dao
        .lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
    permissionDAO.canUpdateProcedimentoOggetto(idProcedimentoOggetto, true);
    /*
     * @TODO Fondere i 3 metodi updateNoteProcedimentoOggetto(),
     * updateContitolareProcedimentoOggetto() e updateIdSettoreDiProduzione() in
     * un unico metodo dato che tutti e 3 aggiornano dati su
     * NEMBO_T_PROCEDIMENTO_OGGETTO
     */
    dao.updateNoteProcedimentoOggetto(idProcedimentoOggetto, note,
        extIdUtenteAggiornamento);
    dao.updateContitolareProcedimentoOggetto(idProcedimentoOggetto,
        idContitolare, extIdUtenteAggiornamento);

    if (idSettore != null)
    {
      dao.updateIdSettoreDiProduzione(idProcedimentoOggetto, idSettore);
    }

    if (idAmmCompetenza != null)
    {
      // Verifico che il PROCEDIMENTO (NB: non procedimento_oggetto) sia in
      // stato IN_BOZZA
      Procedimento p = dao.getProcedimento(idProcedimento);
      if (p.getIdStatoOggetto() != NemboConstants.STATO.ITER.ID.IN_BOZZA)
      {
        // Se non lo è ==> Eccezione, non posso modificare l'amministrazione di
        // competenza. (Se la jsp ha permesso di inserire il dato è probabile
        // che qualche
        // utente non abbia cambiato lo stato del procedimento)
        throw new NemboPermissionException(
            ExceptionType.PROCEDIMENTO_OGGETTO_CHIUSO);
      }
      // Tento di modificare i dati a prescindere, se non li trovassi allora
      // farei l'inserimento (inutile leggere e decidere dato che nel caso
      // peggiore farei 3
      // accessi al db e 2 in media)
      // lo faccio solo se l'amm competenza è stato modificato!
      Long oldIdAmmCompetenza = dao
          .getIdAmmCompetenzaProcedimento(p.getIdProcedimento());
      if (oldIdAmmCompetenza != null
          && oldIdAmmCompetenza.longValue() != idAmmCompetenza.intValue())
      {
        boolean updateOK = dao.updateAmmCompetenzaByIdProcedimentoOggetto(
            idProcedimentoOggetto, idAmmCompetenza, extIdUtenteAggiornamento);
        if (!updateOK)
        {
          // Non è avvenuto nessun update su db ==> il record non esiste ==> lo
          // inserisco
          dao.insertAmmCompetenzaProcedimento(idProcedimento, idAmmCompetenza,
              extIdUtenteAggiornamento);
        }
      }
      else
        if (oldIdAmmCompetenza == null)
          dao.insertAmmCompetenzaProcedimento(idProcedimento, idAmmCompetenza,
              extIdUtenteAggiornamento);

    }

    if (dao.updateProcedimOggettoQuadro(idProcedimentoOggetto, idQuadroOggetto,
        idBandoOggetto, extIdUtenteAggiornamento) == 0)
    {
      dao.insertProcedimOggettoQuadro(idProcedimentoOggetto, idQuadroOggetto,
          idBandoOggetto, extIdUtenteAggiornamento);
    }

    if (cup != null)
    {
      dao.updateCodiceCup(idProcedimento, cup);
    }

    if (hasVercod)
    {
      dao.updateVercodProcedimento(vercod, dataVisuraCamerale, idProcedimento);
    }
    if (avvioIstruttoria != null && "S".equals(avvioIstruttoria))
    {
      // Gestione stati
      MainControlloDTO esito = dao.callMainAvvioIstruttoria(
          idProcedimentoOggetto, extIdUtenteAggiornamento);
      if (esito.getRisultato() != 0)
      {
        sessionContext.setRollbackOnly();
        throw new ApplicationException(esito.getMessaggio());
      }

    }

  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public UpdatePermissionProcedimentoOggetto canUpdateProcedimentoOggetto(
      long idProcedimentoOggetto, boolean throwException)
      throws NemboPermissionException,
      InternalUnexpectedException
  {
    return permissionDAO.canUpdateProcedimentoOggetto(idProcedimentoOggetto,
        throwException);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean hasDelega(long idIntermediario, long extIdAzienda,
      Date dataRiferimento)
      throws InternalUnexpectedException
  {
    return permissionDAO.hasDelega(idIntermediario, extIdAzienda,
        dataRiferimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean hasDelegaForProcedimento(long idIntermediario,
      long idProcedimento, Date dataRiferimento)
      throws InternalUnexpectedException
  {
    return permissionDAO.hasDelegaForProcedimento(idIntermediario,
        idProcedimento, dataRiferimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Long getAziendaAgricolaProcedimento(long idProcedimento, Date date)
      throws InternalUnexpectedException
  {
    return dao.getAziendaAgricolaProcedimento(idProcedimento, date);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public AziendaDTO getDatiAziendaAgricolaProcedimento(long idProcedimento,
      Date date) throws InternalUnexpectedException
  {
    return dao.getDatiAziendaAgricolaProcedimento(idProcedimento, date);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ProcedimentoOggetto getProcedimentoOggetto(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    ProcedimentoOggetto procOggetto = dao
        .getProcedimentoOggetto(idProcedimentoOggetto);
    procOggetto
        .setQuadri(dao.getQuadriProcedimentoOggetto(idProcedimentoOggetto));
    return procOggetto;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ProcedimentoOggetto getProcedimentoOggettoByIdProcedimento(
      long idProcedimento) throws InternalUnexpectedException
  {
    ProcedimentoOggetto procOggetto = dao
        .getProcedimentoOggettoByIdProcedimento(idProcedimento);
    if (procOggetto != null)
    {
      procOggetto.setQuadri(dao.getQuadriProcedimentoOggetto(
          procOggetto.getIdProcedimentoOggetto()));
    }
    return procOggetto;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public TestataProcedimento getTestataProcedimento(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getTestataProcedimento(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<GruppoInfoDTO> getDichiarazioniOggetto(long idProcedimentoOggetto,
      long idQuadroOggetto, long idBandoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getDichiarazioniOggetto(idProcedimentoOggetto, idQuadroOggetto,
        idBandoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ProcedimentoDTO getIterProcedimento(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getIterProcedimento(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<AmmCompetenzaDTO> getAmmCompetenzaList(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getAmmCompetenzaList(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<AziendaDTO> getAziendeProcedimentoList(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getAziendeProcedimentoList(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ContoCorrenteEstesoDTO getContoCorrente(long idProcedimentoOggeto,
      Date dataRiferimento) throws InternalUnexpectedException
  {
    return dao.getContoCorrente(idProcedimentoOggeto, dataRiferimento);
  }

  @Override
  public void updateDichiarazioniOAllegatiOggetto(long idProcedimentoOggetto,
      long idQuadroOggetto, long idBandoOggetto,
      List<GruppoInfoDTO> dichiarazioni, long extIdUtenteAggiornamento)
      throws InternalUnexpectedException, NemboPermissionException,
      ApplicationException
  {
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    permissionDAO.canUpdateProcedimentoOggetto(idProcedimentoOggetto, true);

    // cancello e inserisco ex-novo NEMBO_T_VALORI_INSERITI
    dao.deleteValoriInseriti(idProcedimentoOggetto, idQuadroOggetto,
        idBandoOggetto);
    // cancello NEMBO_T_SELEZIONE_INFO

    for (GruppoInfoDTO info : dichiarazioni)
    {
      for (DettaglioInfoDTO dettaglio : info.getDettaglioInfo())
      {
        // lavoro solamente sulle dichiarazioni selezionate a video
        if (dettaglio.isChecked())
        {
          if ("S".equals(dettaglio.getFlagObbligatorio())
              && (dettaglio.getValoriInseriti() == null
                  || dettaglio.getValoriInseriti().size() <= 0))
          {
            // è inutile salvare nel database le informazioni obbligatorie che
            // non prevedono parametri in quanto ritrovabili agevolmente
            // attraverso le informazioni relative al bando ed all’oggetto
            // corrente
            continue;
          }

          // inserisco ex-novo NEMBO_T_SELEZIONE_INFO
          SelezioneInfoInserimentoAllegatiDTO selezioneInfo = dao
              .getIdSelezioneInfo(idProcedimentoOggetto,
                  dettaglio.getIdDettaglioInfo());
          Long idSelezioneInfo = selezioneInfo == null ? null
              : selezioneInfo.getIdSelezioneInfo();
          if (idSelezioneInfo == null || idSelezioneInfo == 0)
          {
            idSelezioneInfo = dao.insertSelezioneInfo(idProcedimentoOggetto,
                dettaglio.getIdDettaglioInfo());
          }

          for (ValoriInseritiDTO valoreDto : dettaglio.getValoriInseriti())
          {
            dao.insertValoriInseriti(idSelezioneInfo, valoreDto.getValore(),
                valoreDto.getPosizione());
          }
        }
        else
        {
          Long idSelezioneInfo = dettaglio.getIdSelezioneInfo();
          if (idSelezioneInfo != null && idSelezioneInfo != 0)
          {
            List<FileAllegatiDTO> files = dao.getFileAllegatiByIdSelezioneInfo(
                idProcedimentoOggetto, idSelezioneInfo);
            if (files != null)
            {
              for (FileAllegatiDTO file : files)
              {
                this.deleteFileAllegati(idProcedimentoOggetto, file);
              }
            }
            dao.deleteSelezioneInfoIfUnused(idSelezioneInfo);
          }
        }
      }
    }

    logOperationOggettoQuadro(idProcedimentoOggetto, idQuadroOggetto,
        idBandoOggetto, extIdUtenteAggiornamento);
  }

  @Override
  public void updateContoCorrenteOggetto(long idProcedimentoOggetto,
      long idQuadroOggetto, long idBandoOggetto, long extIdUtenteAggiornamento,
      long idContoCorrente)
      throws InternalUnexpectedException, NemboPermissionException
  {
    dao.lockProcedimentoByIdProcedimentoOggetto(idProcedimentoOggetto);
    permissionDAO.canUpdateProcedimentoOggetto(idProcedimentoOggetto, true);

    dao.deleteWProcedimentoOggetto(idProcedimentoOggetto);
    dao.insertWProcedimentoOggetto(idProcedimentoOggetto, idContoCorrente);

    logOperationOggettoQuadro(idProcedimentoOggetto, idQuadroOggetto,
        idBandoOggetto, extIdUtenteAggiornamento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ContoCorrenteDTO> getContiCorrentiValidi(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getContiCorrentiValidi(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<FonteControlloDTO> getControlliList(long idProcedimentoOggeto,
      Date dataRiferimento, boolean onlyFailed)
      throws InternalUnexpectedException
  {
    return dao.getControlliList(idProcedimentoOggeto, dataRiferimento,
        onlyFailed, false);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<FonteControlloDTO> getControlliList(long idProcedimentoOggeto,
      Date dataRiferimento, boolean onlyFailed, boolean acceptWarning)
      throws InternalUnexpectedException
  {
    return dao.getControlliList(idProcedimentoOggeto, dataRiferimento,
        onlyFailed, acceptWarning);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public MainControlloDTO callMainControlli(long idBandoOggetto,
      long idProcedimentoOggetto, long idAzienda, long idUtenteLogin)
      throws InternalUnexpectedException
  {
    return dao.callMainControlli(idBandoOggetto, idProcedimentoOggetto,
        idAzienda, idUtenteLogin);
  }

  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<Long, List<FileAllegatiDTO>> getMapFileAllegati(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getMapFileAllegati(idProcedimentoOggetto);
  }

  public void insertFileAllegati(long idProcedimentoOggetto,
      FileAllegatiDTO fileAllegatiDTO, byte[] fileContent)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    final long idDettaglioInfo = fileAllegatiDTO.getIdDettaglioInfo();
    SelezioneInfoInserimentoAllegatiDTO sia = dao
        .getIdSelezioneInfo(idProcedimentoOggetto, idDettaglioInfo);
    Long idSelezione = sia.getIdSelezioneInfo();
    String flagObbligatorio = sia.getFlagObbligatorio();
    if (DettaglioInfoDTO.FLAG_ALLEGATO_OBBLIGATORIO.equals(flagObbligatorio)
        && idSelezione == null)
    {
      idSelezione = dao.insertSelezioneInfo(idProcedimentoOggetto,
          idDettaglioInfo);
    }
    else
    {
      if (idSelezione == null)
      {
        throw new InternalUnexpectedException(
            "Tentativo di inserire un allegato NON OBBLIGATORIO di cui non è già presente l'ID_SELEZIONE_INFO.",
            new LogParameter[]
            { new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto),
                new LogParameter("fileAllegatiDTO", fileAllegatiDTO)
            }, new LogVariable[]
            { new LogVariable("selezioneInfoInserimentoAllegatiDTO", sia),
                new LogVariable("fileAllegatiDTO", fileAllegatiDTO)
            });
      }
    }
    fileAllegatiDTO.setIdSelezioneInfo(idSelezione);
    dao.insertFileAllegati(fileAllegatiDTO, fileContent);
  }

  @Override
  public void deleteFileAllegati(long idProcedimentoOggetto,
      long idFileAllegati)
      throws InternalUnexpectedException, ApplicationException
  {
    FileAllegatiDTO fileAllegatiDTO = dao
        .getFileAllegatiById(idProcedimentoOggetto, idFileAllegati);
    deleteFileAllegati(idProcedimentoOggetto, fileAllegatiDTO);
  }

  public void deleteFileAllegati(long idProcedimentoOggetto,
      FileAllegatiDTO fileAllegatiDTO)
      throws InternalUnexpectedException, ApplicationException
  {
    if (fileAllegatiDTO != null)
    {
      // Se lo trovo procedo all'eliminazione
      if (fileAllegatiDTO.getIdProcedimentoOggetto() == idProcedimentoOggetto) // Verifica
                                                                               // di
                                                                               // sicurezza
      {
        Long extIdDocumentoIndex = fileAllegatiDTO.getExtIdDocumentoIndex();
        if (extIdDocumentoIndex != null)
        {
          AgriWellEsitoVO esito = null;
          try
          {
            esito = NemboUtils.PORTADELEGATA.getAgriwellCSIInterface()
                .agriwellServiceCancellaDoquiAgri(extIdDocumentoIndex);
          }
          catch (Exception e)
          {
            throw new InternalUnexpectedException(
                "Errore nel richiamo del servizio agriwellServiceCancellaDoquiAgri con extIdDocumentoIndex = "
                    + extIdDocumentoIndex,
                e);
          }
          Integer codice = esito == null ? null : esito.getEsito();
          if (codice == null
              || codice != NemboConstants.SERVICE.AGRIWELL.RETURN_CODE.SUCCESS)
          {
            throw new ApplicationException(
                "Errore nel richiamo del servizio agriwellServiceCancellaDoquiAgri con extIdDocumentoIndex = "
                    + extIdDocumentoIndex
                    + ": impossibile eliminare il documento su index");
          }
        }
        dao.deleteFileAllegati(fileAllegatiDTO.getIdFileAllegati());
        dao.deleteSelezioneInfoIfUnusedFileAllegati(
            fileAllegatiDTO.getIdSelezioneInfo());
      }
    }
    else
    {
      // Se no, non c'è nulla da fare, è già stato eliminato da qualcun altro
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ContenutoFileAllegatiDTO getFileFisicoAllegato(
      long idProcedimentoOggetto, long idFileAllegati)
      throws InternalUnexpectedException
  {
    ContenutoFileAllegatiDTO cfa = ContenutoFileAllegatiDTO
        .from(dao.getFileAllegatiById(idProcedimentoOggetto, idFileAllegati));
    if (cfa != null)
    {
      Long extIdDocumentoIndex = cfa.getExtIdDocumentoIndex();

      if (extIdDocumentoIndex != null)
      {
        AgriWellEsitoVO esito;
        try
        {
          esito = NemboUtils.PORTADELEGATA.getAgriwellCSIInterface()
              .agriwellServiceLeggiDoquiAgri(extIdDocumentoIndex);
        }
        catch (Exception e)
        {
          throw new InternalUnexpectedException(
              "Errore nel richiamo del servizio agriwellServiceLeggiDoquiAgri con extIdDocumentoIndex = "
                  + extIdDocumentoIndex,
              e);
        }
        Integer codiceEsito = esito == null ? null : esito.getEsito();
        if (codiceEsito != null
            && codiceEsito == NemboConstants.SERVICE.AGRIWELL.RETURN_CODE.SUCCESS)
        {
          cfa.setContenuto(esito.getContenutoFile());
        }
      }
      else
      {
        cfa.setContenuto(
            dao.getFileFisicoAllegato(idProcedimentoOggetto, idFileAllegati));
      }
    }
    return cfa;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ElencoCduDTO> getElencoCdu()
      throws InternalUnexpectedException
  {
    return dao.getElencoCdu();
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Long getIdAmmCompetenzaProcedimento(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getIdAmmCompetenzaProcedimento(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<StampaOggettoDTO> getElencoStampeOggetto(
      long idProcedimentoOggetto, Long idOggettoIcona)
      throws InternalUnexpectedException
  {
    return dao.getElencoStampeOggetto(idProcedimentoOggetto, idOggettoIcona);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<StampaOggettoIconaDTO> getElencoDocumentiStampeDaAllegare(
      long idProcedimentoOggetto, Long extIdTipoDocumento)
      throws InternalUnexpectedException
  {
    return dao.getElencoDocumentiStampeDaAllegare(idProcedimentoOggetto,
        extIdTipoDocumento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getIdentificativo(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getIdentificativo(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ProcedimOggettoStampaDTO getProcedimOggettoStampaByIdOggetoIcona(
      long idProcedimentoOggetto, long idOggettoIcona)
      throws InternalUnexpectedException
  {
    return dao.getProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto,
        idOggettoIcona);
  }

  public int deleteStampeOggettoInseriteDaUtente(long idProcedimentoOggetto,
      long idOggettoIcona)
      throws ApplicationException, InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::deleteStampeOggettoInseriteDaUtente]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      ProcedimentoOggetto procedimentoOggetto = dao
          .getProcedimentoOggetto(idProcedimentoOggetto);
      if (procedimentoOggetto != null)
      {
        if (procedimentoOggetto.getDataFine() == null)
        {
          throw new ApplicationException(
              "L'oggetto non è chiuso, impossibile eliminare la stampa");
        }
      }
      ProcedimOggettoStampaDTO stampa = dao
          .getProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto,
              idOggettoIcona);
      return deleteStampeOggetto(stampa, true);
    }
    catch (ApplicationException e)
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

  protected int deleteStampeOggetto(ProcedimOggettoStampaDTO stampa,
      boolean soloInseriteDaUtente)
      throws ApplicationException, InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::deleteStampeOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      if (stampa != null)
      {
        Long idDocumentoIndex = stampa.getExtIdDocumentoIndex();
        // La delete elimina il record con idProcedimOggettoStampa uguale a
        // quello passato per 'idProcedimentoOggetto
        // corrente e, nel caso che il parametro soloInseriteDaUtente sia true
        // verifica che sia legato ad un record di NEMBO_r_oggetto_icona il
        // cui cdu è NEMBO-126-I.
        int numDelete = dao.deleteStampeOggetto(
            stampa.getIdProcedimentoOggetto(),
            stampa.getIdProcedimOggettoStampa(), soloInseriteDaUtente);
        if ((idDocumentoIndex != null) && (numDelete > 0))
        {
          // Se ho eliminato qualcosa è segno che gli id passati sono corretti
          // ==> elimino anche il file su index.
          AgriWellEsitoVO esito = null;
          try
          {
            esito = NemboUtils.PORTADELEGATA.getAgriwellCSIInterface()
                .agriwellServiceCancellaDoquiAgri(idDocumentoIndex);
          }
          catch (Exception e)
          {
            logger.error(THIS_METHOD
                + " Errore nell'accesso a AGRIWELL, rilevata eccezione "
                + e.getMessage(), e);
            throw new ApplicationException(
                "Si è verificato un errore nell'accesso al servizio di cancellazione AGRIWELL, se il problema persistesse si prega di contattare l'assistenza tecnica");
          }
          Integer codice = esito == null ? null : esito.getEsito();
          if (codice == null
              || codice != NemboConstants.SERVICE.AGRIWELL.RETURN_CODE.SUCCESS)
          {
            String messaggio = "null (esito==null)";
            if (esito != null)
            {
              messaggio = esito.getMessaggio();
            }
            logger.error(THIS_METHOD
                + " Errore nell'accesso a AGRIWELL, codice di errore = "
                + codice + ", messaggio = " + messaggio);
            throw new ApplicationException(
                "Si è verificato un errore nell'accesso al servizio di cancellazione AGRIWELL, se il problema persistesse si prega di contattare l'assistenza tecnica");
          }
        }
      }
      return 0;
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
  public Boolean ripristinaStampaOggetto(long idProcedimentoOggetto,
      long idOggettoIcona, long idUtenteLogin)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::ripristinaStampaOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.lockProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto,
          idOggettoIcona);
      ProcedimOggettoStampaDTO stampa = dao
          .getProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto,
              idOggettoIcona);
      if (stampa != null)
      {
        Long idDocumentoIndex = stampa.getExtIdDocumentoIndex();
        if (idDocumentoIndex == null)
        {
          // Procedo solo non c'è il documento index
          if (stampa
              .getIdStatoStampa() == NemboConstants.STATO.STAMPA.ID.GENERAZIONE_STAMPA_IN_CORSO)
          {
            // Se lo stato è generazione in corso, per aggiornare il record deve
            // essere passato un certo tempo (10 minuti di default)
            // Non è ancora passato il lasso di tempo per permettere la
            // rigenerazione == > non faccio nulla, una stampa è già in corso
            // Questo caso, se non ci sono errori è dovuto al fatto che più
            // utenti abbiano richiesto la rigenerazione in contemporanea,
            // partono entrambi da una situazione valida per richiedere la
            // rigenerazione ma il secondo che arriva si trova il record
            // già aggiornato, quindi non fa nulla.
            if (NemboUtils.DATE.diffInSeconds(new Date(), stampa
                .getDataInizio()) > NemboConstants.TEMPO.SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA) // 10
                                                                                                    // minuti
            {
              dao.ripristinaProcedimOggettoStampaByIdOggetoIcona(
                  idProcedimentoOggetto, idOggettoIcona, idUtenteLogin);
              return true;
            }
            else
            {
              return false;
            }
          }
          else
          {
            // Non mi metto a controllare lo stato, TANTO NON ESSENDOCI
            // EXT_ID_DOCUMENTO_INDEX non può essere andato bene... ==> RIGENERO
            dao.ripristinaProcedimOggettoStampaByIdOggetoIcona(
                idProcedimentoOggetto, idOggettoIcona, idUtenteLogin);
            return true;
          }
        }
        else
        {
          return null;
        }
      }
      return false;
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
  public Map<String, OggettoIconaDTO> getIconeTestata(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::getIconeTestata]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getIconeTestata(idProcedimentoOggetto);
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
  public DecodificaDTO<Integer> chiudiOggetto(long idProcedimentoOggetto,
      Long idEsito, String note, UtenteAbilitazioni utenteAbilitazioni)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::chiudiOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    DecodificaDTO<Integer> result = null;
    try
    {
      final long idUtenteLogin = utenteAbilitazioni.getIdUtenteLogin();
      result = dao.chiudiOggetto(idProcedimentoOggetto, idEsito, note,
          idUtenteLogin);
      if (NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE != result.getId())
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
  public List<DecodificaInterventoDTO> getListInterventiByIdProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::getListInterventiByIdProcedimentoOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao
          .getListInterventiByIdProcedimentoOggetto(idProcedimentoOggetto);
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
  public boolean canDeleteProcedimentoOggetto(long idProcedimentoOggetto,
      long idBandoOggetto, boolean checkIstanza)
      throws InternalUnexpectedException
  {
    return dao.canDeleteProcedimentoOggetto(idProcedimentoOggetto,
        idBandoOggetto, checkIstanza);
  }

  @Override
  public MainControlloDTO callMainEliminazione(long idProcedimentoOggetto,
      long idUtenteLogin) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::callMainEliminazione]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    MainControlloDTO result = null;
    try
    {
      long[] aIdUtente = new long[1];
      aIdUtente[0] = idUtenteLogin;
      UtenteLogin[] utenti;
      StringBuilder sb = null;
      try
      {
        utenti = PapuaservProfilazioneServiceFactory.getRestServiceClient()
            .findUtentiLoginByIdList(aIdUtente);
        ProcedimentoEProcedimentoOggetto po = dao
            .getProcedimentoEProcedimentoOggettoByIdProcedimentoOggetto(
                idProcedimentoOggetto);
        TestataProcedimento testataProcedimento = dao
            .getTestataProcedimento(po.getProcedimento().getIdProcedimento());
        sb = new StringBuilder("Per l'azienda ")
            .append(testataProcedimento.getCuaa())
            .append(" è stato cancellato il procedimento oggetto con id: ")
            .append(idProcedimentoOggetto)
            .append(" e descrizione: ")
            .append(po.getProcedimentoOggetto().getDescrizione())
            .append(" dall'utente ")
            .append(idUtenteLogin)
            .append(" (")
            .append(utenti[0].getCognome() + " " + utenti[0].getNome())
            .append(")  - identificativo procedimento: "
                + po.getProcedimento().getIdentificativo());

      }
      catch (Exception e)
      {
        sessionContext.setRollbackOnly();
      }

      result = dao.callMainEliminazione(idProcedimentoOggetto, idUtenteLogin);
      if (NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE != result
          .getRisultato())
      {
        sessionContext.setRollbackOnly();
        return result;
      }

      dao.insertLog(sb.toString());

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
  public MainControlloDTO callMainRiapertura(long idProcedimentoOggetto,
      String note, Long idUtenteLogin) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::chiudiOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    MainControlloDTO result = null;
    try
    {
      result = dao.callMainRiapertura(idProcedimentoOggetto, note,
          idUtenteLogin);
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
  public String aggiungiOggettoStampa(ProcedimOggettoStampaDTO stampa,  UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::aggiungiOggettoStampa]";
    final long idOggettoIcona = stampa.getIdOggettoIcona();
    dao.lockOggettoIcona(idOggettoIcona);
    if (!dao.canInsertProcedimentoOggettoStampa(idOggettoIcona, stampa.getIdProcedimentoOggetto()))
    {
      return "E' già presente un documento allegato per la tipologia prescelta! Se lo si desidera cambiare si prega di eliminare prima quello presente";
    }
    try
    {
    	stampa.setExtIdDocumentoIndex(null);
        if(stampa.getIdStatoStampa()!=NemboConstants.STATO.STAMPA.ID.FIRMATO_DIGITALMENTE)
        {
      	  stampa.setIdStatoStampa(NemboConstants.STATO.STAMPA.ID.STAMPA_ALLEGATA);
        }
        dao.insertProcedimOggettoStampa(stampa);
        return null;
    }
    catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]{new LogParameter("stampa", stampa)}, null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      return "Si è verificato un problema nella registrazione del documento. Se il problema persistesse si prega di contattare l'assistenza tecnica";
    }
  }

  @Override
  public String riaperturaProcedimentoOggetto(long idProcedimentoOggetto,
      long idUtente, String note) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::riaperturaProcedimentoOggetto]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      MainControlloDTO result = callMainRiapertura(idProcedimentoOggetto, note,
          idUtente);
      switch (result.getRisultato())
      {
        case NemboConstants.SQL.RESULT_CODE.NESSUN_ERRORE:
          return completeRiaperturaProcedimentoOggetto(idProcedimentoOggetto,
              idUtente);
        case NemboConstants.SQL.RESULT_CODE.ERRORE_CRITICO:
          return "<br />Si è verificato un errore di sistema. Contattare l'assistenza comunicando il seguente messaggio: "
              + result.getMessaggio();
        case NemboConstants.SQL.RESULT_CODE.ERRORE_GRAVE:
          return "<br />E' stato riscontrato il seguente errore: "
              + result.getMessaggio();
        default:
          return "<br />Si è verificato un problema grave nella riapertura dell'oggetto, se il problema persistesse contattare l'assistenza tecnica.";
      }
    }
    catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]
      { new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto) },
          null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      return "Si è verificato un problema nella riapertura dell'oggetto. Se il problema persistesse si prega di contattare l'assistenza tecnica";
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }

  private String completeRiaperturaProcedimentoOggetto(
      long idProcedimentoOggetto, long idUtente)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS
        + "::completeRiaperturaProcedimentoOggetto]";
    AgriWellEsitoVO esitoVO = null;
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.lockProcedimentoOggetto(idProcedimentoOggetto);

      Map<Long, List<FileAllegatiDTO>> listaFile = getMapFileAllegati(
          idProcedimentoOggetto);

      StringBuilder errorMessage = new StringBuilder();
      if (listaFile != null)
      {
        for (Map.Entry<Long, List<FileAllegatiDTO>> item : listaFile.entrySet())
        {
          if (item.getValue() != null)
          {
            for (FileAllegatiDTO file : item.getValue())
            {
              final Long extIdDocumentoIndex = file.getExtIdDocumentoIndex();
              if (extIdDocumentoIndex != null)
              {
                esitoVO = NemboUtils.PORTADELEGATA.getAgriwellCSIInterface()
                    .agriwellServiceLeggiDoquiAgri(extIdDocumentoIndex);
                if (esitoVO.getEsito() != null && esitoVO
                    .getEsito() == NemboConstants.SERVICE.AGRIWELL.RETURN_CODE.SUCCESS)
                {
                  dao.ripristinaDocumentoIndexOnFileAllegati(
                      file.getIdFileAllegati(), esitoVO.getContenutoFile());
                  esitoVO = NemboUtils.PORTADELEGATA
                      .getAgriwellCSIInterface()
                      .agriwellServiceCancellaDoquiAgri(extIdDocumentoIndex);
                  if (esitoVO.getEsito() != null && esitoVO
                      .getEsito() == NemboConstants.SERVICE.AGRIWELL.RETURN_CODE.SUCCESS)
                  {
                  }
                  else
                  {
                    logger.error(THIS_METHOD
                        + " Errore nella cancellazione del docuemnto su index, con id_procedimento_oggetto = "
                        + idProcedimentoOggetto
                        + " e idDocumentoIndex = " + extIdDocumentoIndex
                        + "\nMessaggio di errore: " + esitoVO.getMessaggio());
                    if (errorMessage.length() > 0)
                    {
                      errorMessage.append("<br />");
                    }
                    errorMessage.append(
                        "Non &egrave; stato possibile cancellare il documento \""
                            + file.getNomeLogico()
                            + "\" dal sistema AGRIWELL.");
                  }
                }
                else
                {
                  logger.error(THIS_METHOD
                      + " Errore nel reperire su index il documento con id_procedimento_oggetto = "
                      + idProcedimentoOggetto
                      + " e idDocumentoIndex = " + extIdDocumentoIndex
                      + "\nMessaggio di errore: " + esitoVO.getMessaggio());
                  if (errorMessage.length() > 0)
                  {
                    errorMessage.append("<br />");
                  }
                  errorMessage.append(
                      "Si &egrave; verificato un problema nel recupero del documento \""
                          + file.getNomeLogico() + "\" dal sistema AGRIWELL");
                }
              }
            }
          }
        }
      }
      if (errorMessage.length() > 0)
      {
        errorMessage.insert(0,
            "Sono stati rilevati i seguenti errori gravi:<br/>");
        errorMessage.append(
            "<br/>Sebbene la pratica sia stata riaperta &egrave; importante contattare IMMEDIATAMENTE l'assistenza tecnica e comunicare i problemi sopra indicati");
      }
      //
      List<ProcedimOggettoStampaDTO> stampe = dao
          .getProcedimOggettiStampa(idProcedimentoOggetto);
      if (stampe != null)
      {
        for (ProcedimOggettoStampaDTO stampaDTO : stampe)
        {
          try
          {
            deleteStampeOggetto(stampaDTO, false);
          }
          catch (ApplicationException e)
          {
            if (errorMessage.length() > 0)
            {
              errorMessage.append("<br />");
            }
            errorMessage
                .append("Non &egrave; stato possibile cancellare il documento #"
                    + stampaDTO.getExtIdDocumentoIndex()
                    + " dal sistema AGRIWELL.");
          }
        }
      }

      return errorMessage.length() > 0 ? errorMessage.toString() : null;
    }
    catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]
      { new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto) },
          null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      return "Si è verificato un problema nella riapertura dell'oggetto. Si prega di contattare l'assistenza tecnica";
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
  public DelegaAnagrafeVO getDettaglioDelega(long idAzienda,
      Date dataRiferimento) throws InternalUnexpectedException
  {
    return dao.getDettaglioDelega(idAzienda, dataRiferimento);
  }

  @Override
  public String trasmettiIstanza(long idProcedimento, long idProcedimentoOggetto, long idBandoOggetto,long idQuadroOggettoDatiIdentificativi, String identificativoProcOggetto, UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException
  {
	String THIS_METHOD = "[" + THIS_CLASS + "::trasmettiIstanza]";
    if (logger.isDebugEnabled())
	{
	  logger.debug(THIS_METHOD + " BEGIN.");
	}
	try
	{
	    if (utenteAbilitazioni.getRuolo().isUtenteTitolareCf() || utenteAbilitazioni.getRuolo().isUtenteLegaleRappresentante())
	    {
	      // Se l'utente è un beneficiario in proprio (quindi con ruolo titolare CF o legale rappresentante)
	      // deve avere potere di firma per poter trasmettere la domanda in quanto il sistema segna la domanda come firmata con firma semplice dall'utente connesso 
	      
	    	if (!dao.isBeneficiarioAbilitatoATrasmettere(utenteAbilitazioni.getCodiceFiscale(), idProcedimentoOggetto))
	      {
	        // Nel caso non ce l'abbia ==> errore (comunque non dovrebbe mai accadere in quanto a monte l'utente non dovrebbe vedere l'icona di trasmissione)
	        return "L'utente corrente non è autorizzato a firmare per conto dell'azienda, impossibile proseguire con l'operazione di trasmissione";
	      }
	    }
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		ProcedimentoOggetto po = dao.getProcedimentoOggetto(idProcedimentoOggetto);
		if (po.getIdStatoOggetto().longValue()>= NemboConstants.STATO.OGGETTO.ID.TRASMESSO)
		{
		  return "L'oggetto non si trova nello stato corretto per la trasmissione o è già stato trasmesso. Impossibile proseguire con l'operazione";
		}
		Date dataFine = dao.updateDataFineIterProcedimentoOggetto(idProcedimentoOggetto);
		dao.insertIterProcedimOggetto(idProcedimentoOggetto, NemboConstants.STATO.OGGETTO.ID.TRASMESSO, dataFine, null, null, utenteAbilitazioni.getIdUtenteLogin());
		dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.TRASMESSO, utenteAbilitazioni.getIdUtenteLogin());
		
		Procedimento proc = dao.getProcedimento(idProcedimento);
		String codiceOggetto = dao.getCodiceOggettoDelProcedimentoOggetto(idProcedimentoOggetto);
		if (NemboConstants.OGGETTO.CODICE.COMUNICAZIONE_RINUNCIA.equals(codiceOggetto))
		{
		  final Date dataLimiteRinunciaTotale = dao.getDataLimiteRinunciaTotale(idProcedimentoOggetto);
		  if (dataLimiteRinunciaTotale!=null && !new Date().after(dataLimiteRinunciaTotale))
		  {
		    //dao.updateProcedimento(idProcedimento, NemboConstants.STATO.OGGETTO.ID.RINUNCIA_BENEFICIARIO, utenteAbilitazioni.getIdUtenteLogin());
		  }
		}
		else
		{
	      if(proc.getIdStatoOggetto() < NemboConstants.STATO.ITER.ID.TRASMESSO)
	      {
	        dao.updateProcedimento(idProcedimento, NemboConstants.STATO.OGGETTO.ID.TRASMESSO, utenteAbilitazioni.getIdUtenteLogin());
	      }
		}
		if (NemboConstants.PROCEDIMENTO_OGGETTO.CRITERIO.SOVRASCRIVI_IDENTIFICATIVO.equals(dao.findCriterioNumIdentificativo(idProcedimentoOggetto)))
		{
		  dao.updateIdentificativoProcedimento(idProcedimento, identificativoProcOggetto);
		}
		
		List<StampaOggettoDTO> stampe = dao.getElencoStampeOggetto(idProcedimentoOggetto, null);
		boolean trovataStampa = false;
		if(stampe != null)
		{
		    for(StampaOggettoDTO item:stampe)
			{
				//Protocollo documento firmato e allegati
		    	if(item.getCodiceCdu().equals(NemboConstants.USECASE.STAMPE_OGGETTO.INSERISCI_STAMPA_OGGETTO) || "S".equals(item.getFlagDaProtocollare()))
		    	{
		    		trovataStampa = true;
		    		DatiIdentificativi dati = getDatiIdentificativiProcedimentoOggetto(idProcedimentoOggetto, idQuadroOggettoDatiIdentificativi, null);
		    		byte[] contenutoStampaFirmata = getStampaProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, item.getIdOggettoIcona());
		    		return protocollaStampaAllegati("Trasmissione domanda",utenteAbilitazioni, item.getIdProcedimOggettoStampa(),contenutoStampaFirmata, idProcedimentoOggetto, dati, item.getNomeFile(),idBandoOggetto);
		    	}
				
			}
		}
		if(!trovataStampa && dao.isObbligatorioInserireStampaFirmata(po.getIdOggettoProcOgg())  )
		{
			   sessionContext.setRollbackOnly();
		      return"Per procedere è necessario inserire la stampa firmata.";
		}
		
		 
		return null;
	}
	catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]{new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto)}, null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      return "Si è verificato un problema nella trasmissione dell'istanza. Se il problema persistesse si prega di contattare l'assistenza tecnica";
    }
	finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }  
  }
  
  
  private String protocollaStampaAllegati(String oggettoProtocollo,UtenteAbilitazioni utente, long idProcedimOggettoStampa,byte[] contenutoStampaFirmata, 
		  long idProcedimentoOggetto,DatiIdentificativi dati, String nomeFile, long idBandoOggetto) throws java.lang.Exception {
		logger.debug( "BEGIN protocollaStampaAllegati");
		
		try {
			  // Invio al protocollo
			  logger.debug( "--- INIZIO GESTIONE INSERTPROTOCOLLO");
			  logger.debug( "-- ** Preparo i campi di input per il servizio insertProtocollo **");
			  try{
				  InsertProtocolloRequestVO protReq = new InsertProtocolloRequestVO();	
				  
				  ComuneVO comune = new ComuneVO();
			      comune.setCodiceISTAT(dati.getRappLegale().getIstatComune());
			  	  comune.setCap(new java.math.BigInteger(dati.getRappLegale().getCap()));
			  	  comune.setNome(dati.getRappLegale().getCognome()+" "+dati.getRappLegale().getNome());
			  	  comune.setProvincia(dati.getRappLegale().getIndirizzoResidenza().substring(
			  			dati.getRappLegale().getIndirizzoResidenza().lastIndexOf("(")+1, 
			  			dati.getRappLegale().getIndirizzoResidenza().lastIndexOf(")")));	
			      
			  	  IndirizzoVO indir = new IndirizzoVO();
				  indir.setComune(comune);
			  	  indir.setNumeroCivico("1");  //default perche non abbiamo il dato singolo disponibile
			  	  indir.setVia(dati.getRappLegale().getIndirizzo());
			  	  
			  	  MittenteVO mittente = new MittenteVO();
				  mittente.setDenominazione(dati.getAzienda().getDenominazione());
				  mittente.setIndirizzo(indir);
			  	  protReq.setMittente(mittente);
				  
				  
				  UsernameTokenVO userNameVO = new UsernameTokenVO();
			      userNameVO.setUserAccountProtocollo(WsUtils.USER_ACCOUNT_PROT);
			      userNameVO.setPasswordAccountProtocollo(WsUtils.PWD_ACCOUNT_PROT);
			      protReq.setUsernameToken(userNameVO);	  
				  
			      protReq.setPrivacy(false);		      		      
			      
			      TipoDocumentoVO tipoDoc = new TipoDocumentoVO();
			      tipoDoc.setCodice(NemboConstants.PROTOCOLLO.CODICE_STAMPE_ISTANZE);
			      tipoDoc.setNome(NemboConstants.PROTOCOLLO.NOME_STAMPE_ISTANZE);
			      protReq.setTipoDocumento(tipoDoc);
			      		      
			      TipoProtocolloVO tipoProt = TipoProtocolloVO.ENTRATA;
			      protReq.setTipoProtocollo(tipoProt);
			      
			      UfficioProtocollazioneVO uffProt = new UfficioProtocollazioneVO();
			      uffProt.setCodice(NemboConstants.PROTOCOLLO.CODICE_UFFICIO_PROTOCOLLAZIONE_PROT);
			      uffProt.setNome(NemboConstants.PROTOCOLLO.NOME_UFFICIO_PROTOCOLLAZIONE_PROT);
			      protReq.setUfficioProtocollazione(uffProt);
			      
				  
			      protReq.setOggetto(oggettoProtocollo+" NEMBO - CUAA: "+dati.getAzienda().getCuaa());	  
				  			  
				 // Setto il PDF generato 
			     if(nomeFile==null){
			    	 nomeFile = "pratica.pdf";
			     }
			     String mimeType = NemboUtils.FILE.getMimeType(nomeFile);
				 DocumentoVO docPdfCorsoGenerato = new DocumentoVO();
				 docPdfCorsoGenerato.setBinario(contenutoStampaFirmata);
				 docPdfCorsoGenerato.setDescrizione("Documento da protocollare");
				 docPdfCorsoGenerato.setMimetype(mimeType);
				 docPdfCorsoGenerato.setNome(nomeFile);
				 protReq.setDocumentoDaProtocollare(docPdfCorsoGenerato);			
				 			
				
				 // Setto Allegati del corso
				 ListaDocumentiVO documentiPecVO = new ListaDocumentiVO();
				 documentiPecVO.getAllegato().add(docPdfCorsoGenerato);
				 
				 
				 ProtocolloResponseVO protRespnse = NemboUtils.WS.getServiceProtocolloInformaticoserv().insertProtocollo(protReq);
				 if(protRespnse != null){
					 // Loggo l'oggetto di output
					 logger.debug( "ProtocolloResponseVO ="+protRespnse.getNumeroProtocollo().getAnno()+" "+protRespnse.getNumeroProtocollo().getNumero());
					 XMLGregorianCalendar dataProtocollo = protRespnse.getDataProtocollo();
					 
					 if (protRespnse.getNumeroProtocollo() == null) {
						 throw new ApplicationException("Numero di protocollo non valorizzato da servizio insertProtocollo");
					 }					 
					 
					 int numeroProtocollo = protRespnse.getNumeroProtocollo().getNumero();
					 if(protRespnse.getRefAllegati()!=null && protRespnse.getRefAllegati().size()>0)
					 {
						 for(DocumentoRefVO doc:protRespnse.getRefAllegati()){
								if(doc.getNome().equalsIgnoreCase(nomeFile)){								 
										dao.aggiornaDatiProtocollazionePratica( idProcedimOggettoStampa,
										 NemboUtils.DATE.fromXMLGregorianCalendar(dataProtocollo),
										 numeroProtocollo+"",
										 doc.getRef()
										 );
							 }
						 }
					 }
					 
					 
					 //Mando una pec al beneficiario
					 
					 ProcedimentoOggetto po = dao.getProcedimentoOggetto(idProcedimentoOggetto);
					 String[] PARAM_NAMES =
					      {
					          NemboConstants.PARAMETRO.MITTENTE_RICEVUTA,
					          NemboConstants.PARAMETRO.MITTENTE_DENOM,
					          NemboConstants.PARAMETRO.MITTENTE_PSW,
					          
					          //nel caso istanza
					          NemboConstants.PARAMETRO.TESTO_RICEVUTA,
					          NemboConstants.PARAMETRO.INVIO_PEC_ISTANZE,
					          NemboConstants.PARAMETRO.OGGETTO_RICEVUTA,
					          
					          //nel caso istruttoria
					          NemboConstants.PARAMETRO.INVIO_PEC_NO_ISTANZE,
					          NemboConstants.PARAMETRO.TESTO_NO_ISTANZA,
					          NemboConstants.PARAMETRO.OGGETTO_NO_ISTANZA
					      };
					 
					 DecodificaDTO<String> datiInvioPec = dao.findDatiInvioPec(idBandoOggetto);
					 Map<String, String> mapParametri = getParametri(PARAM_NAMES);
					 Map<String, String> mapParametriBandoOggetto = getParametriBandoOggetto(idBandoOggetto, PARAM_NAMES);
					 String flagInvioAbilitatoIstanze = findParametro(NemboConstants.PARAMETRO.INVIO_PEC_ISTANZE, mapParametri, mapParametriBandoOggetto);
					 String flagInvioAbilitatoNonIstanze = findParametro(NemboConstants.PARAMETRO.INVIO_PEC_NO_ISTANZE, mapParametri, mapParametriBandoOggetto); 
					 
					 
					 NotificaByPecRequestVO pecRequestVO = new NotificaByPecRequestVO();
		    		 pecRequestVO.setDestinatarioPecA(dati.getAzienda().getPec());
		    		 pecRequestVO.setListaDocumentiAllegati(documentiPecVO);
		    		 pecRequestVO.setMittenteDenominazione(findParametro(NemboConstants.PARAMETRO.MITTENTE_DENOM, mapParametri, mapParametriBandoOggetto));
		    		 pecRequestVO.setMittentePassword(findParametro(NemboConstants.PARAMETRO.MITTENTE_PSW, mapParametri, mapParametriBandoOggetto));
		    		 pecRequestVO.setMittentePec(findParametro(NemboConstants.PARAMETRO.MITTENTE_RICEVUTA, mapParametri, mapParametriBandoOggetto));
		    		 pecRequestVO.setListaDestinatariPecCC(new ListaDestinatariPecCCVO());
		    		 String testo = "";
		    		 String oggetto = "";    
		    		 
		    		 if(datiInvioPec!=null && datiInvioPec.getDescrizione()!=null && datiInvioPec.getDescrizione().trim().length()>0)
					 { 
		    		 	Map<String, Object> mapCache = new HashMap<>(); 
		    			mapCache.put(ProcedimentoOggetto.REQUEST_NAME, getProcedimentoOggetto(idProcedimentoOggetto));
		    			testo = PlaceHolderManager.process(datiInvioPec.getCodDescrizione(), mapCache);
		    			oggetto = PlaceHolderManager.process(datiInvioPec.getDescrizione(), mapCache);
		    			
		    			if(testo.indexOf("$$") >= 0)
		    			{    
		    				Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)"); 
		    				Matcher m = pattern.matcher(testo);
		    				testo = m.replaceAll("<<$1>>");
		    			}
		    			if(oggetto.indexOf("$$") >= 0)
		    			{
		    				Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)"); 
		    				Matcher m = pattern.matcher(oggetto);
		    				oggetto = m.replaceAll("<<$1>>");
		    			}
					 }
		    		 else
					 {
		    			Map<String, Object> mapCache = new HashMap<>(); 
		    			mapCache.put(ProcedimentoOggetto.REQUEST_NAME, getProcedimentoOggetto(idProcedimentoOggetto));
		    			
		    			if("S".equals(flagInvioAbilitatoIstanze) && "S".equals(po.getFlagIstanza()))
				    	{
			    			testo = PlaceHolderManager.process(findParametro(NemboConstants.PARAMETRO.TESTO_RICEVUTA, mapParametri, mapParametriBandoOggetto) , mapCache);
			    			oggetto = PlaceHolderManager.process(findParametro(NemboConstants.PARAMETRO.OGGETTO_RICEVUTA, mapParametri, mapParametriBandoOggetto), mapCache);
				    	}
		    			else if("S".equals(flagInvioAbilitatoNonIstanze) && !"S".equals(po.getFlagIstanza()))
		    			{
			    			testo = PlaceHolderManager.process(findParametro(NemboConstants.PARAMETRO.TESTO_NO_ISTANZA, mapParametri, mapParametriBandoOggetto) , mapCache);
			    			oggetto = PlaceHolderManager.process(findParametro(NemboConstants.PARAMETRO.OGGETTO_NO_ISTANZA, mapParametri, mapParametriBandoOggetto), mapCache);
		    			}
		    			
		    			if(testo.indexOf("$$") >= 0)
		    			{
		    				Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)"); 
		    				Matcher m = pattern.matcher(testo);
		    				testo = m.replaceAll("<<$1>>");
		    			}
		    			if(oggetto.indexOf("$$") >= 0)
		    			{
		    				Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)"); 
		    				Matcher m = pattern.matcher(oggetto);
		    				oggetto = m.replaceAll("<<$1>>");
		    			}
					 }
		    		 
		    		 
		    		    
		    		 
					 if("S".equals(po.getFlagIstanza()))
					 {
						 //nel caso istanza
						 if("S".equals(flagInvioAbilitatoIstanze))
				    	 {
							 pecRequestVO.setOggetto(oggetto);
				    		 pecRequestVO.setTesto(testo);
				    		 StringWriter sw = new StringWriter();
				    			JAXB.marshal(pecRequestVO, sw);
				    			String xmlString = sw.toString();
				    			System.out.println(xmlString);
							 NemboUtils.WS.getServiceProtocolloInformaticoserv().notificaByPec(pecRequestVO);
				    	 }
					 }
					 else
					 {
						 //nel caso di istruttoria
						 if("S".equals(flagInvioAbilitatoNonIstanze))
				    	 {
							 pecRequestVO.setOggetto(oggetto);
				    		 pecRequestVO.setTesto(testo);
				    		 StringWriter sw = new StringWriter();
			    			JAXB.marshal(pecRequestVO, sw);
			    			String xmlString = sw.toString();
			    			System.out.println(xmlString);
							 NemboUtils.WS.getServiceProtocolloInformaticoserv().notificaByPec(pecRequestVO);
				    	 }
					 }					 
				 }
				 else{
					 logger.error( " si è verificato un problema durante il richiamo al servizio di protocollazione. idProcedimOggettoStampa="+idProcedimOggettoStampa);
					 sessionContext.setRollbackOnly();
					 return "si è verificato un problema durante il richiamo al servizio di protocollazione.";
				 }
				 
				 
				 
				 /*
					 * Nel caso di oggetti per la trasmissione di comunicazioni dai beneficiari verso Regione (es. Trasmissione integrazioni),
					 *  prevedere una notifica automatica via mail ai funzionari regionali, ad un indirizzo email configurabile a livello di bando/oggetto.  
					 * */
					
					String[] PARAM_NAMES =
					      {
					          NemboConstants.PARAMETRO.EMAIL_COM_INTEGRAZ // destinatario mail notifica
					      };
					 
					 Map<String, String> mapParametriBandoOggetto = getParametriBandoOggetto(idBandoOggetto, PARAM_NAMES);
					 String destinatarioMailNotifica = findParametro(NemboConstants.PARAMETRO.EMAIL_COM_INTEGRAZ, null, mapParametriBandoOggetto);
					if(destinatarioMailNotifica!=null)
					{
						String denominazioneBando = getInformazioniBandoByIdProcOgg(idProcedimentoOggetto).getDenominazione();
						NemboUtils.MAIL.postMail("noreply@regione.basilicata.it", new String[] {destinatarioMailNotifica}, null,
								"SIA-RB : Notifica comunicazione del beneficiario "+dati.getAzienda().getCuaa(), 
								
								"SIA-RB \n" + 
								"Il beneficiario "+dati.getAzienda().getDenominazione()+" CUAA: "+dati.getAzienda().getCuaa()+" ha trasmesso la comunicazione prot. n. "+protRespnse.getNumeroProtocollo().getAnno()+" "+protRespnse.getNumeroProtocollo().getNumero()+" del "+NemboUtils.DATE.formatDate(NemboUtils.DATE.fromXMLGregorianCalendar(protRespnse.getDataProtocollo()))+" relativamente ai procedimenti del bando "+denominazioneBando, 
								
								null);
					}
				 
				 
			  }
			  catch(java.lang.Exception ex){
				  sessionContext.setRollbackOnly();
				  ex.printStackTrace();
				 logger.error( " Eccezione con la chiamata al servizio insertProtocollo() in protocollaStampaAllegati ="+ex.getMessage());
				 return " si è verificato un problema durante il richiamo al servizio di protocollazione.";
			  }
			
		} 
		catch (java.lang.Exception ex) {
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			logger.error("-- Exception in generaPdfNuovoCorso ="+ ex.getMessage());		
			 return " si è verificato un problema durante il richiamo al servizio di protocollazione.";
		} 
		finally {
			logger.debug( "END generaPdfNuovoCorso");
		}
		return null;
	}
  
  private String findParametro(String parametro,Map<String, String> mapParametri,Map<String, String> mapParametriBandoOggetto){
	  String retVal=null;
	  if(mapParametriBandoOggetto!=null && mapParametriBandoOggetto.get(parametro)!=null){
		  retVal = mapParametriBandoOggetto.get(parametro);
	  }else if(mapParametri!=null && mapParametri.get(parametro)!=null){
		  retVal = mapParametri.get(parametro);
	  }
	  
	  return retVal;
  }
  
  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public byte[] getStampaProcedimOggettoStampaByIdOggetoIcona(long idProcedimentoOggetto, long idOggettoIcona) throws InternalUnexpectedException
  {
    return dao.getStampaProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, idOggettoIcona);
  }
  

  private SiapCommWsDatiInvioMailPecVO getDatiInvioMailPecVO(
      DecodificaDTO<String> datiInvioPec, List<String> destinatari,
      long idTipoDocumentoPec, long idProcedimentoOggetto, boolean isIstanza,
      boolean isIstanzaPagamento) throws InternalUnexpectedException
  {

    Map<String, String> mapParametri = getParametri(new String[]
    { NemboConstants.PARAMETRO.DOQUIAGRI_FLAG_PEC,
        NemboConstants.PARAMETRO.MITTENTE_RICEVUTA,
        NemboConstants.PARAMETRO.INVIO_PEC_NO_ISTANZE });

    if (isIstanza)
    {
      // es: Preparo l'oggetto per mandare la ricevuta al beneficiario di
      // avvenuta trasmissione della domanda
      if ("S".equals(
          mapParametri.get(NemboConstants.PARAMETRO.DOQUIAGRI_FLAG_PEC)))
      {
        SiapCommWsDatiInvioMailPecVO datiInvioMailPecVO = new SiapCommWsDatiInvioMailPecVO();
        datiInvioMailPecVO.setFlagAllegaDocumento("S");
        datiInvioMailPecVO.setMittente(
            mapParametri.get(NemboConstants.PARAMETRO.MITTENTE_RICEVUTA));

        String oggetto = datiInvioPec.getDescrizione();
        String testo = datiInvioPec.getCodDescrizione();

        Map<String, Object> mapCache = new HashMap<>();
        try
        {
          mapCache.put(ProcedimentoOggetto.REQUEST_NAME,
              getProcedimentoOggetto(idProcedimentoOggetto));
          testo = PlaceHolderManager.process(testo, mapCache);
          oggetto = PlaceHolderManager.process(oggetto, mapCache);

          if (testo.indexOf("$$") >= 0)
          {
            Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)");
            Matcher m = pattern.matcher(testo);
            testo = m.replaceAll("<<$1>>");
          }
          if (oggetto.indexOf("$$") >= 0)
          {
            Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)");
            Matcher m = pattern.matcher(oggetto);
            oggetto = m.replaceAll("<<$1>>");
          }

          datiInvioMailPecVO.setIdTipoDocumentoPec(
              NemboConstants.PEC.TIPO_DOCUMENTO.PEC_GENERICA);
          datiInvioMailPecVO.setOggetto(oggetto);
          datiInvioMailPecVO.setTesto(testo);
          datiInvioMailPecVO.setArrDestinatariA(destinatari);
          return datiInvioMailPecVO;

        }
        catch (Exception e)
        {
          throw new InternalUnexpectedException(
              "Si è verificato un errore durante la composizione della PEC. Si prega di contattate l'assistenza tecnica.",
              e);
        }
      }
    }
    else
    {
      // es: Preparo l'oggetto per mandare la conferma al beneficiario di
      // avvenuta approvazione della domanda
      if ("S".equals(
          mapParametri.get(NemboConstants.PARAMETRO.INVIO_PEC_NO_ISTANZE)))
      {
        SiapCommWsDatiInvioMailPecVO datiInvioMailPecVO = new SiapCommWsDatiInvioMailPecVO();
        datiInvioMailPecVO.setFlagAllegaDocumento("S");
        datiInvioMailPecVO.setMittente(
            mapParametri.get(NemboConstants.PARAMETRO.MITTENTE_RICEVUTA));

        String oggetto = datiInvioPec.getDescrizione();
        String testo = datiInvioPec.getCodDescrizione();

        Map<String, Object> mapCache = new HashMap<>();
        try
        {
          mapCache.put(ProcedimentoOggetto.REQUEST_NAME,
              getProcedimentoOggetto(idProcedimentoOggetto));
          testo = PlaceHolderManager.process(testo, mapCache);
          oggetto = PlaceHolderManager.process(oggetto, mapCache);

          if (testo.indexOf("$$") >= 0)
          {
            Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)");
            Matcher m = pattern.matcher(testo);
            testo = m.replaceAll("<<$1>>");
          }
          if (oggetto.indexOf("$$") >= 0)
          {
            Pattern pattern = Pattern.compile("\\$\\$([a-zA-z0-9_]+)");
            Matcher m = pattern.matcher(oggetto);
            oggetto = m.replaceAll("<<$1>>");
          }

          datiInvioMailPecVO.setIdTipoDocumentoPec(
              NemboConstants.PEC.TIPO_DOCUMENTO.PEC_GENERICA);
          datiInvioMailPecVO.setOggetto(oggetto);
          datiInvioMailPecVO.setTesto(testo);
          datiInvioMailPecVO.setArrDestinatariA(destinatari);
          return datiInvioMailPecVO;
        }
        catch (Exception e)
        {
          throw new InternalUnexpectedException(
              "Si è verificato un errore durante la composizione della PEC. Si prega di contattate l'assistenza tecnica.",
              e);
        }

      }
    }
    return null;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Long getIdDatiProcedimento(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getIdDatiProcedimento(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public FonteControlloDTO getControlliListByIdFonteControllo(
      long idProcedimentoOggeto, long idFonteControllo, Date dataRiferimento,
      boolean onlyFailed) throws InternalUnexpectedException
  {
    return dao.getControlliListByIdFonteControllo(idProcedimentoOggeto,
        idFonteControllo, dataRiferimento, onlyFailed);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RaggruppamentoLivelloCriterio> getCriteriPunteggio(long idOggetto,
      long idDatiProcedimentoPunti, long idBando)
      throws InternalUnexpectedException
  {
    boolean isOggettoIstanza = dao.isOggettoIstanza(idOggetto);
    return dao.getCriteriPunteggio(idDatiProcedimentoPunti, idBando,
        isOggettoIstanza);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isOggettoIstanza(long idOggetto)
      throws InternalUnexpectedException
  {
    return dao.isOggettoIstanza(idOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isCalcoloPuntiEseguito(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.isCalcoloPuntiEseguito(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RaggruppamentoLivelloCriterio> getCriteriPunteggioManuali(
      long idProcedimento, long idBando) throws InternalUnexpectedException
  {
    return dao.getCriteriPunteggioManuali(idProcedimento, idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Long getIdDatiProcedimentoPunti(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    Long retValue = dao
        .getIdDatiProcedimentoPuntiByIdProcOgg(idProcedimentoOggetto);
    if (retValue == null || retValue.longValue() == 0)
    {
      retValue = dao
          .getIdDatiProcedimentoPuntiByIdProcedimento(idProcedimentoOggetto);
    }
    return retValue;
  }

  @Override
  public MainPunteggioDTO callMainCalcoloPunteggi(
      LogOperationOggettoQuadroDTO logOperationDTO, long idProcedimento,
      long idProcedimentoOggetto, long idAzienda, long idBando, Long idLivello)
      throws InternalUnexpectedException
  {
    logOperationOggettoQuadro(logOperationDTO);
    return dao.callMainCalcoloPunteggi(idProcedimento, idProcedimentoOggetto,
        idAzienda, idBando, idLivello);
  }

  @Override
  public void updateCriteriManuali(LogOperationOggettoQuadroDTO logOperation,
      Map<Long, BigDecimal> listaIdBandoLivelloCriterio, long idProcedimento,
      long idBando, long idProcedimentoOggetto) throws InternalUnexpectedException, ApplicationException
  {
    logOperationOggettoQuadro(logOperation);
    for (RaggruppamentoLivelloCriterio rlc : dao
        .getCriteriPunteggioManuali(idProcedimento, idBando))
    {
      for (CriterioVO criterio : rlc.getCriteri())
      {
        if (listaIdBandoLivelloCriterio
            .containsKey(criterio.getIdBandoLivelloCriterio()))
        {
          // aggiorno il punteggio
          dao.updateCriterioManuale(criterio.getIdBandoLivelloCriterio(),
              idProcedimento, false, listaIdBandoLivelloCriterio
                  .get(criterio.getIdBandoLivelloCriterio()));
        }
        else
        {
          // azzero il punteggio
          dao.updateCriterioManuale(criterio.getIdBandoLivelloCriterio(),
              idProcedimento, true, null);
        }
      }
    }
    
    
    //gestione dell'intervento per punteggi
    IInterventiEJB interventiEjb = null;
    try
    {
    	interventiEjb = NemboUtils.APPLICATION.getEjbInterventi();
    }
    catch(NamingException ne)
    {
    	//Logger.logMsg(Logger.ERROR, "Errore verificatosi durante l'esecuzione della lookup di interventiEJB\n" + ne.getMessage());
    	throw new InternalUnexpectedException(ne);
    }
    List<RaggruppamentoLivelloCriterio> listaRaggruppamento = getCriteriPunteggioManuali(
            idProcedimento, idBando); 
    interventiEjb.insertInterventoPrevenzioneIstruttoria(idProcedimentoOggetto, listaRaggruppamento, logOperation);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void updateCriteriIstruttoria(long idOggetto,
      LogOperationOggettoQuadroDTO logOperation,
      Map<Long, BigDecimal> listaIdBandoLivelloCriterio, long idProcedimento,
      //parametri per aggiorare l'intervento
      long idBando, Long idDatiProcedimentoPunti, long idProcedimentoOggetto) throws InternalUnexpectedException, ApplicationException
  {
    long idDatiProcPunti = dao
        .getIdDatiProcedimentoPunti(logOperation.getIdProcedimentoOggetto());
    dao.deletePunteggioIstruttoria(idDatiProcPunti);
    Iterator<Map.Entry<Long, BigDecimal>> it = listaIdBandoLivelloCriterio
        .entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry pair = (Map.Entry) it.next();
      if (pair.getValue() != null)
      {
        dao.insertPunteggioIstruttoria((Long) pair.getKey(), idDatiProcPunti,
            (BigDecimal) pair.getValue());
      }
    }
    logOperationOggettoQuadro(logOperation);
    IInterventiEJB interventiEjb = null;
    try
    {
    	interventiEjb = NemboUtils.APPLICATION.getEjbInterventi();
    }
    catch(NamingException ne)
    {
    	//Logger.logMsg(Logger.ERROR, "Errore verificatosi durante l'esecuzione della lookup di interventiEJB\n" + ne.getMessage());
    	throw new InternalUnexpectedException(ne);
    }
    
	// elaboro il punteggio per l'intervento
	List<RaggruppamentoLivelloCriterio> listaRaggruppamento = getCriteriPunteggio(idOggetto, idDatiProcedimentoPunti,idBando);
	interventiEjb.insertInterventoPrevenzioneIstruttoria(idProcedimentoOggetto, listaRaggruppamento, logOperation);
    
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ControlloAmministrativoDTO> getControlliAmministrativi(
      long idProcedimentoOggetto, String codiceQuadro, List<Long> ids)
      throws InternalUnexpectedException
  {
    return dao.getControlliAmministrativi(idProcedimentoOggetto, codiceQuadro,
        ids);
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public String getFlagEstratta(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getFlagEstratta(idProcedimentoOggetto);
  }

  @Override
  public void updateEsitiControlli(
      List<EsitoControlliAmmDTO> esitiControlliDaAggiornare,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
        .getIdProcedimentoOggetto();
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    dao.deleteEsitiControlliAmministrativi(idProcedimentoOggetto,
        esitiControlliDaAggiornare);
    dao.insertEsitiControlliAmministrativi(esitiControlliDaAggiornare);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)

  public List<DecodificaDTO<Long>> getElencoTecniciDisponibili(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getElencoTecniciDisponibili(idProcedimentoOggetto,
        dao.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto));
  }
  
  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getElencoTecniciDisponibili(long idProcedimentoOggetto, Boolean singoloUfficioZona, Long istruttoreGradoSup) throws InternalUnexpectedException
  {
	  return dao.getElencoTecniciDisponibili(idProcedimentoOggetto, dao.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto), singoloUfficioZona, istruttoreGradoSup);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getElencoTecniciDisponibili(long idProcedimentoOggetto, Boolean singoloUfficioZona) throws InternalUnexpectedException
  {
	  return dao.getElencoTecniciDisponibili(idProcedimentoOggetto, dao.getIdProcedimentoAgricoloByIdProcedimentoOggetto(idProcedimentoOggetto), singoloUfficioZona);
  }
  
  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getElencoTecniciDisponibiliPerAmmCompetenza(long idProcedimentoOggetto, long idProcedimentoAgricolo)  throws InternalUnexpectedException
  {
	  return dao.getElencoTecniciDisponibiliPerAmmCompetenza(idProcedimentoOggetto, idProcedimentoAgricolo);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public EsitoFinaleDTO getEsitoFinale(long idProcedimentoOggetto,
      long idQuadroOggetto) throws InternalUnexpectedException
  {
    return dao.getEsitoFinale(idProcedimentoOggetto, idQuadroOggetto);
  }

  @Override
  public void updateEsitoFinale(long idProcedimentoOggetto,
      long idQuadroOggetto, long idBandoOggetto, EsitoFinaleDTO esito,
      long idUtenteLogin) throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateEsitoFinale]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.lockProcedimentoOggetto(idProcedimentoOggetto);

      EsitoFinaleDTO esitoTmp = dao.getEsitoFinale(idProcedimentoOggetto,
          idQuadroOggetto);
      if (esitoTmp != null)
      {
        dao.delete("NEMBO_T_ESITO_TECNICO_ISTRUT", "ID_ESITO_TECNICO",
            esitoTmp.getIdEsitoTecnico());
        dao.delete("NEMBO_T_ESITO_TECNICO", "ID_ESITO_TECNICO",
            esitoTmp.getIdEsitoTecnico());
      }

      long idEsitoTecnico = dao.insertTEsitoTecnico(idProcedimentoOggetto,
          idQuadroOggetto,
          esito.getIdTecnico(),
          esito.getIdGradoSup(),
          esito.getNote(),
          esito.getIdEsito(),
          esito.getIdTipoAtto(),
          esito.getNumeroAtto(),
          esito.getDataAtto());

      dao.insertTEsitoTecnicoIstrut(idEsitoTecnico, esito.getPrescrizioni(),
          esito.getMotivazione());
      logOperationOggettoQuadro(idProcedimentoOggetto, idQuadroOggetto,
          idBandoOggetto, idUtenteLogin);
    }
    catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]
      { new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto) },
          null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      throw new InternalUnexpectedException(
          "Si è verificato un errore durante l'aggiornamento dei dati.", e);
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
  public void updateEsitoTecnico(EsitoFinaleDTO esito,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    String THIS_METHOD = "[" + THIS_CLASS + "::updateEsitoTecnico]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
    try
    {
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      final long idQuadroOggetto = logOperationOggettoQuadroDTO
          .getIdQuadroOggetto();
      dao.lockProcedimentoOggetto(idProcedimentoOggetto);
      EsitoFinaleDTO esitoTmp = dao.getEsitoFinale(idProcedimentoOggetto,
          idQuadroOggetto);
      if (esitoTmp != null)
      {
        dao.delete("NEMBO_T_ESITO_TECNICO", "ID_ESITO_TECNICO",
            esitoTmp.getIdEsitoTecnico());
      }

      dao.insertTEsitoTecnico(idProcedimentoOggetto, idQuadroOggetto,
          esito.getIdTecnico(), esito.getIdGradoSup(), esito.getNote(),
          esito.getIdEsito(), esito.getIdTipoAtto(), esito.getNumeroAtto(),
          esito.getDataAtto());

      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      DumpUtils.logGenericException(logger, null, e, new LogParameter[]
      { new LogParameter("esito", esito),
          new LogParameter("logOperationOggettoQuadroDTO",
              logOperationOggettoQuadroDTO), },
          null, THIS_METHOD);
      sessionContext.setRollbackOnly();
      throw new InternalUnexpectedException(
          "Si è verificato un errore durante l'aggiornamento dei dati.", e);
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
  public List<DecodificaDTO<Long>> getElencoEsiti(String tipoEsito)
      throws InternalUnexpectedException
  {
    return dao.getElencoEsiti(tipoEsito);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<VisitaLuogoExtDTO> getVisiteLuogo(long idProcedimentoOggetto,
      long idQuadroOggetto, List<Long> ids) throws InternalUnexpectedException
  {
    return dao.getVisiteLuogo(idProcedimentoOggetto, idQuadroOggetto, ids);
  }

  @Override
  public void insertVisitaLuogo(VisitaLuogoDTO visitaLuogoDTO,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoOggetto(
        logOperationOggettoQuadroDTO.getIdProcedimentoOggetto());
    dao.insertVisitaLuogo(visitaLuogoDTO);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public void eliminaVisiteLuogo(List<Long> ids,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
        .getIdProcedimentoOggetto();
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    dao.deleteVisitaLuogo(ids, idProcedimentoOggetto);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public void updateVisiteLuogo(List<VisitaLuogoDTO> visiteDaAggiornare,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoOggetto(
        logOperationOggettoQuadroDTO.getIdProcedimentoOggetto());
    dao.updateVisiteLuogoBatch(visiteDaAggiornare);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public DatiAnticipo getDatiAnticipo(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getDatiAnticipo";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      DatiAnticipo datiAnticipo = dao.getDatiAnticipo(idProcedimentoOggetto);
      if (datiAnticipo != null)
      {
        datiAnticipo.setRipartizioneAnticipo(
            dao.getListAnticipoLivello(idProcedimentoOggetto));
      }
      return datiAnticipo;
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
  public List<DecodificaDTO<Integer>> getDecodificheBanche()
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getDecodificheBanche";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getDecodificheBanche();
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
  public List<DecodificaDTO<Integer>> getDecodificheSportelli(int idBanca)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getDecodificheSportelli";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getDecodificheSportelli(idBanca);
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
  public SportelloBancaDTO getSportelloBancaById(long idSportello)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getSportelloBancaById";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getSportelloBancaById(idSportello);
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
  public void updateAnticipo(DatiAnticipoModificaDTO updateDTO,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "updateAnticipo";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      dao.updateAnticipo(idProcedimentoOggetto, updateDTO);

      List<ImportoAnticipoLivelloDTO> ripartizione = dao
          .getRipartizioneAnticipoSuLivelli(idProcedimentoOggetto);
      if (ripartizione != null && !ripartizione.isEmpty())
      {
        int len = ripartizione.size();
        if (len > 1)
        {
          BigDecimal parziale = BigDecimal.ZERO;
          for (int i = 0; i < len - 1; ++i)
          {
            parziale = NemboUtils.NUMBERS.add(parziale,
                ripartizione.get(i).getImportoAnticipo());
          }
          ripartizione.get(len - 1).setImportoAnticipo(updateDTO
              .getImportoAnticipo().subtract(parziale, MathContext.DECIMAL128));
        }
        dao.updateAnticipoLivello(ripartizione);
      }
      this.logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
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
  public boolean isBeneficiarioAbilitatoATrasmettere(String codiceFiscale,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    final String THIS_METHOD = "isBeneficiarioAbilitatoATrasmettere";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.isBeneficiarioAbilitatoATrasmettere(codiceFiscale,
          idProcedimentoOggetto);
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
  public PartecipanteDTO findPartecipanteInAnagrafe(String cuaa)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "findPartecipanteInAnagrafe";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.findPartecipanteInAnagrafe(cuaa);
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
  public List<LivelloDTO> getLivelliProcOggetto(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getLivelliProcOggetto(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String findIstatComune(String comune)
      throws InternalUnexpectedException
  {
    return dao.findIstatComune(comune);
  }

  @Override
  public List<SettoriDiProduzioneDTO> getSettoriDiProduzioneProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getSettoriDiProduzioneProcedimentoOggetto(idProcedimentoOggetto);
  }

  @Override
  public List<SettoriDiProduzioneDTO> getSettoriDiProduzioneInLivelliBandi(
      long idProcedimentoOggetto, long idBando)
      throws InternalUnexpectedException
  {
    return dao.getSettoriDiProduzioneInLivelliBandi(idProcedimentoOggetto,
        idBando);

  }

  @Override
  public List<GraduatoriaDTO> getGraduatorieByIdProcedimento(
      long idProcedimento) throws InternalUnexpectedException
  {
    return dao.getGraduatorieByIdProcedimento(idProcedimento);

  }

  @Override
  public Long getIdEsitoEquivalente(Long idEsito, String tipoEsito)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getIdEsitoEquivalente";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getIdEsitoEquivalente(idEsito, tipoEsito);
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
  public List<SospensioneAnticipoDTO> getElencoSospensioniAnticipo(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getElencoSospensioniAnticipo(idProcedimentoOggetto);
  }

  @Override
  public List<SospensioneAnticipoDTO> getElencoSospensioniAnticipoDisponibili(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getElencoSospensioniAnticipoDisponibili(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public void updateSospensioneAnticipo(long idProcedimentoOggetto,
      List<SospensioneAnticipoDTO> elenco,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "updateSospensioneAnticipo";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      dao.delete("NEMBO_T_SOSPENSIONE_ANTICIPO", "ID_PROCEDIMENTO_OGGETTO",
          idProcedimentoOggetto);
      if (elenco != null)
      {
        for (SospensioneAnticipoDTO item : elenco)
        {
          dao.insertSospensioneAnticipo(idProcedimentoOggetto,
              item.getIdLivello(),
              item.getFlagSospensione(),
              item.getMotivazione());
        }
      }
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
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
  public ProcedimentoEstrattoVO getProcedimentoEstratto(long idProcedimento,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getProcedimentoEstratto(idProcedimento, idProcedimentoOggetto);
  }

  @Override
  public LinkedList<LivelloDTO> getLivelliControlloInLoco(long idProcedimento,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getLivelliControlloInLoco(idProcedimento, idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public void eliminaControlloInLoco(long idProcedimentoOggetto,
      LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    dao.eliminaControlloInLoco(idProcedimentoOggetto);
    logOperationOggettoQuadro(logOperationDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public void inserisciRecordControlloInLoco(LivelloDTO livello,
      long idProcedimentoOggetto, LogOperationOggettoQuadroDTO logOperationDTO)
      throws InternalUnexpectedException
  {
    dao.inserisciRecordControlloInLoco(livello, idProcedimentoOggetto);
    logOperationOggettoQuadro(logOperationDTO);
  }

  @Override
  public void updateFlagEstrazione(long idProcedimentoEstratto)
      throws InternalUnexpectedException
  {
    dao.updateFlagEstrazione(idProcedimentoEstratto);
  }

  @Override
  public void eliminaSospensioneAnticipo(long idProcedimentoOggetto,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.eliminaSospensioneAnticipo(idProcedimentoOggetto);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public boolean checkExtension(String extension)
      throws InternalUnexpectedException
  {
    return dao.checkExtension(extension);
  }

  @Override
  public boolean checkNotifiche(long idProcedimento, Long idGravita,
      String attore) throws InternalUnexpectedException
  {
    return dao.checkNotifiche(idProcedimento, idGravita, attore);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
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
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Map<String, List<String>> getTestiStampeIstruttoria(String codiceCdu,
      long idBandoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getTestiStampeIstruttoria(codiceCdu, idBandoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ImportoLiquidatoDTO> getElencoImportiLiquidazione(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    List<ImportoLiquidatoDTO> l = dao
        .getElencoImportiLiquidazione(idProcedimentoOggetto);

    for (ImportoLiquidatoDTO i : l)
      i.setAmmCompetenza(
          dao.getAmmCompetenzaListaLiquidazione(i.getIdListaLiquidazione()));

    return l;

  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RipartizioneImportoDTO> getRipartizioneImporto(
      long idListaLiquidazImpLiq) throws InternalUnexpectedException
  {
    return dao.getRipartizioneImporto(idListaLiquidazImpLiq);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ImportoLiquidatoDTO getElencoImportiLiquidazioneByIdImportoLiquidato(
      long idImportoLiquidato) throws InternalUnexpectedException
  {
    return dao
        .getElencoImportiLiquidazioneByIdImportoLiquidato(idImportoLiquidato);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RiduzioniSanzioniDTO> getElencoRiduzioniSanzioni(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getElencoRiduzioniSanzioni(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getSanzioniInvestimento(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getSanzioniInvestimento(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<String>> getTipologieSanzioni(
      long idProcedimentoOggetto, boolean soloAutomatiche)
      throws InternalUnexpectedException
  {
    return dao.getTipologieSanzioni(idProcedimentoOggetto, soloAutomatiche);

  }

  @Override
  public void inserisciSanzioneRiduzione(Long idTipologia, Long idOperazione,
      Long idDescrizione, String note, BigDecimal importo,
      Long idProcedimentoOggetto, String tipoPagamentoSigop,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.inserisciSanzioneRiduzione(idTipologia, idOperazione, idDescrizione,
        note, importo, idProcedimentoOggetto);
    dao.updateRProcedimentoOggettoSanzione(idProcedimentoOggetto);
    aggiornaAggregatiContributiLivelloPerAccertamentoSpese(
        idProcedimentoOggetto, tipoPagamentoSigop);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public void eliminaRiduzioneSanzione(List<Long> ids,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
      Long idProcedimentoOggetto, String tipoPagamentoSigop)
      throws InternalUnexpectedException
  {

    try
    {
      for (Long id : ids)
        dao.eliminaRiduzioneSanzione(id);

      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
      dao.updateRProcedimentoOggettoSanzione(idProcedimentoOggetto);

      aggiornaAggregatiContributiLivelloPerAccertamentoSpese(
          idProcedimentoOggetto, tipoPagamentoSigop);

    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  public void modificaRiduzioneSanzione(List<RiduzioniSanzioniDTO> riduzioni,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
      Long idProcedimentoOggetto, String tipoPagamentoSigop)
      throws InternalUnexpectedException
  {
    try
    {
      for (RiduzioniSanzioniDTO r : riduzioni)
      {
        if (!r.isSplitted())
        {
          dao.eliminaRiduzioneSanzione(r.getIdProcOggSanzione());
          dao.inserisciSanzioneRiduzione(r.getIdTipologia(),
              r.getIdOperazione(), r.getIdDescrizione(), r.getNote(),
              r.getImporto(), idProcedimentoOggetto);
        }
        else
        {
          dao.eliminaRiduzioneSanzione(r.getIdProcOggSanzione());
          dao.eliminaRiduzioneSanzione(
              r.getIdProcOggSanzioneSecondRecordAfterSplit());
          dao.inserisciSanzioneRiduzione(r.getIdTipologia(),
              r.getIdOperazione(), r.getIdDescrizione(), r.getNote(),
              r.getImportoFirstRecord(), idProcedimentoOggetto);
          dao.inserisciSanzioneRiduzione(r.getIdTipologia(),
              r.getIdOperazione(), r.getIdDescrizioneSecondRecordAfterSplit(),
              r.getNoteB(), r.getImportoSecondRecordAfterSplit(),
              idProcedimentoOggetto);
        }
      }

      dao.updateRProcedimentoOggettoSanzione(idProcedimentoOggetto);
      aggiornaAggregatiContributiLivelloPerAccertamentoSpese(
          idProcedimentoOggetto, tipoPagamentoSigop);
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DatiSpecificiDTO getDatiSpecifici(long idProcedimentoOggetto,
      long idProcedimento) throws InternalUnexpectedException
  {
    if (NemboConstants.TIPO_BANDO.PREMIO
        .equals(dao.getInformazioniBandoByIdProcedimento(idProcedimento)
            .getCodiceTipoBando()))
    {
      return dao.getDatiSpecificiPremio(idProcedimentoOggetto);
    }
    else
    {
      return dao.getDatiSpecifici(idProcedimentoOggetto);
    }
  }

  @Override
  public void updateOrInsertControlliInLocoInvest(
      ControlliInLocoInvestDTO controlliInLocoInvestDTO,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoOggetto(
        controlliInLocoInvestDTO.getIdProcedimentoOggetto());

    if (NemboConstants.FLAGS.NO
        .equals(controlliInLocoInvestDTO.getFlagControllo()))
    {
      // Rimuovo i dati presenti sui controlli in loco

      List<ControlloAmministrativoDTO> controlliAmministrativi = getControlliAmministrativi(
          controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          NemboConstants.QUADRO.CODICE.CONTROLLI_IN_LOCO_MISURE_INVESTIMENTO,
          null);

      if (controlliAmministrativi != null)
      {
        List<EsitoControlliAmmDTO> esitiControlliDaAggiornare = null;
        EsitoControlliAmmDTO esito = null;
        for (ControlloAmministrativoDTO contr : controlliAmministrativi)
        {
          if (esitiControlliDaAggiornare == null)
          {
            esitiControlliDaAggiornare = new ArrayList<>();
          }
          esito = new EsitoControlliAmmDTO();
          esito.setIdQuadroOggControlloAmm(contr.getIdQuadroOggControlloAmm());
          esitiControlliDaAggiornare.add(esito);
        }

        if (esitiControlliDaAggiornare != null)
        {
          dao.deleteEsitiControlliAmministrativi(
              controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
              esitiControlliDaAggiornare);
        }
      }

      dao.deleteEsitoTecnico(
          controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          controlliInLocoInvestDTO.getIdQuadroOggettoControlliLoco());
      dao.deleteVisitaLuogo(controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          controlliInLocoInvestDTO.getIdQuadroOggettoControlliLoco());
    }

    int numrecord = dao.updateControlliInLocoInvest(controlliInLocoInvestDTO);
    if (numrecord == 0)
    {
      dao.insertControlliInLocoInvest(controlliInLocoInvestDTO);
    }
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public EstrazioneCampioneDTO callEseguiSimulazioneEstrazCampione(
      long idTipoEstrazione) throws InternalUnexpectedException
  {
    return dao.callEseguiSimulazioneEstrazCampione(idTipoEstrazione);
  }

  @Override
  public List<EstrazioneACampioneDTO> getElencoEstrazioniACampione(
      boolean isUtenteGal) throws InternalUnexpectedException
  {
    List<EstrazioneACampioneDTO> lista = dao
        .getElencoEstrazioniACampione(isUtenteGal);

    for (EstrazioneACampioneDTO e : lista)
    {
      boolean hasEstrazione = false;
      boolean hasEstrazioneControlloInLoco = false;

      hasEstrazione = dao.checkEstrazione(e.getIdEstrazioneCampione(), "N"); // Esiste
                                                                             // almeno
                                                                             // un
                                                                             // procedimento
                                                                             // con
                                                                             // flag
                                                                             // !=
                                                                             // N
      hasEstrazioneControlloInLoco = dao
          .checkEstrazione(e.getIdEstrazioneCampione(), "D"); // Esiste almeno
                                                              // un procedimento
                                                              // con flag != N e
                                                              // = a D

      if (hasEstrazione)
      {
        if (hasEstrazioneControlloInLoco)
          e.setFlagEstratta("D");
        else
          e.setFlagEstratta("S");
      }
      else
        e.setFlagEstratta("N");
    }

    return lista;
  }

  @Override
  public List<NumeroLottoDTO> getElencoNumeroLotti()
      throws InternalUnexpectedException
  {
    return dao.getElencoNumeroLotti();
  }

  @Override
  public List<RigaFiltroDTO> getFiltroElencoStatoEstrazioniCampione()
      throws InternalUnexpectedException
  {
    return dao.getFiltroElencoStatoEstrazioniCampione();
  }

  @Override
  public List<RigaFiltroDTO> getFiltroElencoTipoEstrazioniCampione()
      throws InternalUnexpectedException
  {
    return dao.getFiltroElencoTipoEstrazioniCampione();
  }

  @Override
  public List<SegnapostoDTO> getSegnapostiStampe()
      throws InternalUnexpectedException
  {
    return dao.getSegnapostiStampe();
  }

  @Override
  public String getValoreSegnaposto(SegnapostoDTO segnapostoDTO,
      long idProcedimento, long idProcedimentoOggetto)
      throws InternalUnexpectedException, ApplicationException
  {
    final String THIS_METHOD = "getValoreSegnaposto";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getValoreSegnaposto(segnapostoDTO, idProcedimento,
          idProcedimentoOggetto);
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
  public BigDecimal calcolaImportoMaxRiduzioneSanzione(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.calcolaImportoMaxRiduzioneSanzione(idProcedimentoOggetto);
  }

  @Override
  public List<RigaSimulazioneEstrazioneDTO> getElencoRisultatiSimulazione(
      long idTipoEstrazione) throws InternalUnexpectedException
  {
    List<RigaSimulazioneEstrazioneDTO> elenco = new ArrayList<>();
    EstrazioneCampioneDTO estrazione = dao
        .callEseguiSimulazioneEstrazCampione(idTipoEstrazione);

    if (estrazione != null && estrazione.getIdProcedimentoOggetto() != null
        && estrazione.getIdProcedimentoOggetto().length > 0)
    {
      elenco = dao
          .getElencoRisultatiSimulazione(estrazione.getIdProcedimentoOggetto());
    }

    return elenco;
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public BigDecimal calcolaImportoParzialeRiduzioneSanzione(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.calcolaImportoParzialeRiduzioneSanzione(idProcedimentoOggetto);
  }

  @Override
	public String approvaIstanza(long idProcedimento, long idProcedimentoOggetto, long idBandoOggetto, String identificativoProcOggetto, UtenteAbilitazioni utenteAbilitazioni,long idQuadroOggettoDatiIdentificativi) throws InternalUnexpectedException
	{
		String THIS_METHOD = "[" + THIS_CLASS + "::approvaIstanza]";
	    if (logger.isDebugEnabled())
		{
		  logger.debug(THIS_METHOD + " BEGIN.");
		}
		
	    try
		{
			dao.lockBandoByIdProcedimento(idProcedimento);
			ProcedimentoOggetto po = dao.getProcedimentoOggetto(idProcedimentoOggetto);
			if (po.getIdStatoOggetto().longValue()< NemboConstants.STATO.OGGETTO.ID.TRASMESSO)
			{
			  return "L'oggetto non si trova nello stato corretto per l'approvazione. Impossibile proseguire con l'operazione";
			}  
			Date dataFine = dao.updateDataFineIterProcedimentoOggetto(idProcedimentoOggetto);
			if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.COMPLETATO == po.getIdEsito())
				dao.insertIterProcedimOggetto(idProcedimentoOggetto, NemboConstants.STATO.OGGETTO.ID.RICHIESTA_TRASMESSA, dataFine, null, null, utenteAbilitazioni.getIdUtenteLogin());
			else
				dao.insertIterProcedimOggetto(idProcedimentoOggetto, NemboConstants.STATO.OGGETTO.ID.ISTRUTTORIA_APPROVATA, dataFine, null, null, utenteAbilitazioni.getIdUtenteLogin());
			
			//Verifico se trovo nell'iter del procediemnto un oggetto uguale a quello mio attuale (Gestione della doppia istruttoria con stesso id)
			ProcedimentoDTO procIter = dao.getIterProcedimento(idProcedimento);
			int trovato = 0;
			for(ProcedimentoOggettoDTO procOggDTO: procIter.getProcedimentoOggetto())
			{
				if(NemboConstants.OGGETTO.CODICE.ISTRUTTORIA_AMMISSIONE_FINANZIAMENTO.equals(procOggDTO.getCodOggetto()))
				{
					trovato++;
				}
				if (procOggDTO.getIdProcedimentoOggetto() == idProcedimentoOggetto)
				{
				  break;
				}
			}
			
			if(trovato >1)
			{
				//Ho già un oggetto simile nel procedimento -> gestisco esito P e N
				if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.POSITIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_POSITIVO, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.AMMESSO_FINANZIAMENTO, utenteAbilitazioni.getIdUtenteLogin());
				}
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.NEGATIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_NEGATIVO, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.NON_AMMESSO_FINANZIAMENTO, utenteAbilitazioni.getIdUtenteLogin());
				}
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.PARZIALMENTE_POSITIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_PARZIALE, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.IN_ATTESA_CONTRODEDUZIONE, utenteAbilitazioni.getIdUtenteLogin());
				}
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.COMPLETATO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_POSITIVO, utenteAbilitazioni.getIdUtenteLogin());
					//dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.AMMESSO_FINANZIAMENTO, utenteAbilitazioni.getIdUtenteLogin());
				}
				else
				{
				  logger.error(THIS_METHOD + " Errore nel gestire il cambiamento di stato per il procedimento con id_procedimento_oggetto = " + idProcedimentoOggetto);
                sessionContext.setRollbackOnly();
                return "Si è verificato un problema durante la storicizzazione del procedimento. Se il problema persistesse si prega di contattare l'assistenza tecnica";
				}
			}
			else
			{
				//NON ho già un oggetto simile nel procedimento -> gestisco esito P ,N, PN
				if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.POSITIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_POSITIVO, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.AMMESSO_FINANZIAMENTO, utenteAbilitazioni.getIdUtenteLogin());
				} 
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.COMPLETATO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_POSITIVO, utenteAbilitazioni.getIdUtenteLogin());
					//dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.AMMESSO_FINANZIAMENTO, utenteAbilitazioni.getIdUtenteLogin());
				}
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.NEGATIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_NEGATIVO, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.IN_ATTESA_CONTRODEDUZIONE, utenteAbilitazioni.getIdUtenteLogin());
				}
				else if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.PARZIALMENTE_POSITIVO == po.getIdEsito())
				{
					dao.updateProcedimentoOggetto(idProcedimentoOggetto,  NemboConstants.STATO.ESITO.ID.APPROVATA_PARZIALE, utenteAbilitazioni.getIdUtenteLogin());
					dao.updateProcedimento(idProcedimento, NemboConstants.STATO.PROCEDIMENTO.ID.IN_ATTESA_CONTRODEDUZIONE, utenteAbilitazioni.getIdUtenteLogin());
				}
				else
				{
				  logger.error(THIS_METHOD + " Errore nel gestire il cambiamento di stato per il procedimento con id_procedimento_oggetto = " + idProcedimentoOggetto);
                sessionContext.setRollbackOnly();
                return "Si è verificato un problema durante la storicizzazione del procedimento. Se il problema persistesse si prega di contattare l'assistenza tecnica";
				}
			}
			
			
			//Verifico per la Voltura se ci sono rek su PSR_W_PROCEDIMENTO_OGG_AZIENDA e riporto su PSR_T_PROCEDIMENTO_AZIENDA 
			if(NemboConstants.STATO.ESITO.ID.TIPO_ESITO_O.POSITIVO == po.getIdEsito() && NemboConstants.OGGETTO.CODICE.ISTRUTTORIA_VOLTURA.equals(po.getCodOggetto()) && dao.existWProcedimentoOggAzienda(idProcedimentoOggetto))
			{
				MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
				mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
				DecodificaDTO<Long> wProcedimOggAzienda = ((List<DecodificaDTO<Long>>)dao.queryForDecodificaLong("SELECT EXT_ID_AZIENDA AS ID, EXT_ID_AZIENDA AS CODICE, NOTE AS DESCRIZIONE FROM PSR_W_PROCEDIMENTO_OGG_AZIENDA WHERE ID_PROCEDIMENTO_OGGETTO=:ID_PROCEDIMENTO_OGGETTO", mapParameterSource)).get(0);
				inserisciProcedimentoAzienda(idProcedimento, wProcedimOggAzienda.getId(), wProcedimOggAzienda.getDescrizione(), utenteAbilitazioni.getIdUtenteLogin());
			}
			
			
			/*FASE DI PROTOCOLLAZIONE STAMPA E INVIO PEC*/
			
			List<StampaOggettoDTO> stampe = dao.getElencoStampeOggetto(idProcedimentoOggetto, null);
			if(stampe != null)
			{
				
			    for(StampaOggettoDTO item:stampe)
				{
					//Protocollo documento firmato e allegati
			    	if("S".equals(item.getFlagDaProtocollare()))
			    	{
			    		DatiIdentificativi dati = getDatiIdentificativiProcedimentoOggetto(idProcedimentoOggetto, idQuadroOggettoDatiIdentificativi, null);
			    		byte[] contenutoStampaFirmata = getStampaProcedimOggettoStampaByIdOggetoIcona(idProcedimentoOggetto, item.getIdOggettoIcona());
			    		
			    		if(contenutoStampaFirmata!=null)
			    		{
				    		String oggetto ="";
				    		String nomeFile = item.getNomeFile()!=null ? item.getNomeFile() : "stampa_istruttoria.pdf";
				    		
				    		if(item.getCodiceCdu().equals(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_STAMPA_COMUNICAZIONE_INTEGRAZIONE))
				    		{
				    			oggetto = "Richiesta integrazione";
					    		nomeFile = "Richiesta_integrazione.pdf";
				    		}
				    		
				    		return protocollaStampaAllegati(oggetto,utenteAbilitazioni, item.getIdProcedimOggettoStampa(),contenutoStampaFirmata, idProcedimentoOggetto, dati, nomeFile,idBandoOggetto);
			    		}
			    	}
					
				}
			}
			
			
			
			return null;
		}
		catch (Exception e)
	    {
			DumpUtils.logGenericException(logger, null, e, new LogParameter[]{new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto)}, null, THIS_METHOD);
	    	sessionContext.setRollbackOnly();
	    	return "Si è verificato un problema nell'approvazione. Se il problema persistesse si prega di contattare l'assistenza tecnica";
	    }
		finally
	  {
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " END.");
	    }
	  }  
	}

  private void inserisciProcedimentoAzienda(long idProcedimento, long idAzienda,
      String note, long idUtente) throws InternalUnexpectedException
  {
    dao.chiudiIterAzienda(idProcedimento, note);
    dao.insertIterAzienda(idProcedimento, idAzienda, idUtente);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<StampaOggettoIconaDTO> getElencoDocumentiStampeDaVerificare(
      long idProcedimentoOggetto, Long extIdTipoDocumento, Long idCategoriaDoc)
      throws InternalUnexpectedException
  {
    return dao.getElencoDocumentiStampeDaVerificare(idProcedimentoOggetto,
        extIdTipoDocumento, idCategoriaDoc);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DataFineLavoriDTO> getElencoDateFineLavori(long idProcedimento,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getElencoDateFineLavori(idProcedimento, idProcedimentoOggetto);
  }

  @Override
  public void aggiornaDataFineLavori(long idDataFineLavori,
      DataFineLavoriDTO dataFineLavoriDTO,
      LogOperationOggettoQuadroDTO logOperation)
      throws InternalUnexpectedException
  {
    dao.aggiornaDataFineLavori(idDataFineLavori, dataFineLavoriDTO);
    logOperationOggettoQuadro(logOperation);
  }

  @Override
  public void inserisciDataFineLavori(DataFineLavoriDTO dataFineLavoriDTO,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.inserisciDataFineLavori(dataFineLavoriDTO);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public void deleteDataFineLavori(long idFineLavori,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.deleteDataFineLavori(idFineLavori);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DataFineLavoriDTO getLastDataFineLavori(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getLastDataFineLavori(idProcedimento);
  }

  @Override
  public List<LivelloDTO> getLivelliImpegnoProcOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getLivelliImpegnoProcOggetto(idProcedimentoOggetto);
  }

  @Override
  public Date getMinDataProroga(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getMinDataProroga(idProcedimento);
  }

  @Override
  public GiustificazioneAnomaliaDTO getGiustificazioneAnomalia(long idControllo,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getGiustificazioneAnomalia(idControllo, idProcedimentoOggetto);
  }

  @Override
  public ControlloDTO getControllo(long idControllo, long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getControllo(idControllo, idProcedimentoOggetto);
  }

  @Override
  public List<DecodificaDTO<Long>> getTipiRisoluzioneControlli(long idControllo)
      throws InternalUnexpectedException
  {
    return dao.getTipiRisoluzioneControlli(idControllo);
  }

  @Override
  public String getFlagObbligatorieta(Long idTipoRisoluzione, String val)
      throws InternalUnexpectedException
  {
    return dao.getFlagObbligatorieta(idTipoRisoluzione, val);
  }

  @Override
  public void inserisciGiustificazioneAnomaliaControllo(
      Long idSoluzioneAnomalia, long idControllo, long idProcedimentoOggetto,
      Long idTipoRisoluzione, String note,
      MultipartFile fileAllegato, String nomeFisico, String nomeLogico,
      Long idUtenteLogin,
      String prov, Date dataProtocollo, Date dataDocumento,
      String numProtocollo) throws InternalUnexpectedException, IOException
  {

    byte[] b = null;
    if (fileAllegato != null)
      b = fileAllegato.getBytes();
    dao.inserisciGiustificazioneAnomaliaControllo(idControllo,
        idProcedimentoOggetto, idTipoRisoluzione, note, b, nomeLogico,
        nomeFisico, idUtenteLogin, prov, dataProtocollo, dataDocumento,
        numProtocollo);
  }

  @Override
  public void eliminaGiustificazione(long idSoluzioneAnomalia)
      throws InternalUnexpectedException
  {
    dao.delete("NEMBO_T_SOLUZIONE_ANOMALIA", "ID_SOLUZIONE_ANOMALIA",
        idSoluzioneAnomalia);
  }

  @Override
  public FileAllegatoDTO getAllegatoGiustificazione(long idSoluzioneAnomalia)
      throws InternalUnexpectedException
  {
    return dao.getAllegatoGiustificazione(idSoluzioneAnomalia);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public EsitoFinaleDTO getEsitoFinale(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getEsitoFinale(idProcedimentoOggetto);
  }

  @Override
  public void aggiornaEsitoFinale(long idProcedimentoOggetto, long idEsito,
      long extIdFunzionarioIstruttore,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.aggiornaEsitoFinale(idProcedimentoOggetto, idEsito,
        extIdFunzionarioIstruttore);
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getValoreParametroFunzSupEsitoVerbale()
      throws InternalUnexpectedException
  {
    return dao.getValoreParametroFunzSupEsitoVerbale();
  }

  @Override
  public boolean findOggettoByIdProcedimento(long idProcedimento,
      String codiceOggetto) throws InternalUnexpectedException
  {
    return dao.findOggettoByIdProcedimento(idProcedimento, codiceOggetto);
  }

  protected void aggiornaAggregatiContributiLivelloPerAccertamentoSpese(
      long idProcedimentoOggetto, String tipoPagamentoSigop)
      throws InternalUnexpectedException
  {
    boolean isAcconto = true;
    if (tipoPagamentoSigop != null
        && tipoPagamentoSigop.compareTo("SALDO") == 0)
      isAcconto = false;

    RendicontazioneEAccertamentoSpeseEJB
        .aggiornaAggregatiContributiLivelloPerAccertamentoSpese(
            idProcedimentoOggetto,
            rendicontazioneEAccertamentoSpeseDAO,
            isAcconto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getTipoPagamentoSigopOggetto(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getTipoPagamentoSigopOggetto(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getValoreParametroDichiarazioneByCodice(
      long idProcedimentoOggetto, String codiceInfo)
      throws InternalUnexpectedException
  {
    return dao.getValoreParametroDichiarazioneByCodice(idProcedimentoOggetto,
        codiceInfo);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Long getLastIstanzaWithPremio(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getLastIstanzaWithPremio(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getResponsabileProcedimento(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getResponsabileProcedimento(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isTipoGiustificazioneAntimafia(long idTipoSoluzione)
      throws InternalUnexpectedException
  {
    return dao.isTipoGiustificazioneAntimafia(idTipoSoluzione);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isIstruttoriaInCorsoMisuraPremio(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.isIstruttoriaInCorsoMisuraPremio(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getInfoAziendaByCuaa(String cuaa)
      throws InternalUnexpectedException
  {
    return dao.getInfoAziendaByCuaa(cuaa);
  }

  @Override
  public void updateVoltura(VolturaDTO voltura, long idProcedimentoOggetto,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    try
    {
      dao.delete("NEMBO_W_PROCEDIMENTO_OGG_AZIEN", "ID_PROCEDIMENTO_OGGETTO",
          idProcedimentoOggetto);
      dao.inserisciVoltura(voltura.getIdAzienda(), idProcedimentoOggetto,
          voltura.getNote());
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      context.setRollbackOnly();
      throw e;
    }
  }

  @Override
  public VolturaDTO getVoltura(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getVoltura(idProcedimentoOggetto);
  }

  @Override
  public List<AziendaDTO> getInfoAziendaByDenominazione(
      String denominazioneAzienda) throws InternalUnexpectedException
  {
    return dao.getInfoAziendaByDenominazione(denominazioneAzienda);
  }

  @Override
  public List<AziendaDTO> getAziendeByCuaa(String cuaa)
      throws InternalUnexpectedException
  {
    return dao.getAziendeByCuaa(cuaa);
  }

  @Override
  public AziendaDTO getAziendaById(long idAzienda)
      throws InternalUnexpectedException
  {
    return dao.getAziendaById(idAzienda);
  }

  @Override
  public boolean isQuadroAttestazioneVisible(long idOggetto)
      throws InternalUnexpectedException
  {
    return dao.isQuadroAttestazioneVisible(idOggetto);
  }

  @Override
  public boolean isUtenteConflitto(String codiceFiscale, String cuaaAzienda)
      throws InternalUnexpectedException
  {
    return dao.isUtenteConflitto(codiceFiscale, cuaaAzienda);
  }

  @Override
  public ResponsabileDTO getResponsabileCAA(long idIntermediarioCAA, int idProcedimentoAgricolo)
      throws InternalUnexpectedException
  {
    return dao.getResponsabileCAA(idIntermediarioCAA, idProcedimentoAgricolo);
  }

  @Override
  public OrganismoDelegatoDTO getOrganismoDelegato(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getOrganismoDelegato(idProcedimento);
  }

  @Override
  public List<DecodificaDTO<String>> getElencoUfficiZona(long idAmmCompetenza)
      throws InternalUnexpectedException
  {
    return dao.getElencoUfficiZona(idAmmCompetenza);
  }

  @Override
  public void updateOrganismoDelegato(Vector<Long> idProcedimentiSelezionati,
      long idAmministrazione, Long idUfficiozona, long idUtenteLogin,
      String note, Long idTecnico) throws InternalUnexpectedException
  {
    try
    {

      boolean stessaAmm = dao
          .checkSeIProcSelezHannoLaStessaAmm(idProcedimentiSelezionati);
      boolean stessiStati = dao.checkStatiOggetti(idProcedimentiSelezionati);
      boolean stessoBando = dao.checkSeIProcSelezAppartengonoAlloStessoBando(
          idProcedimentiSelezionati);
      if (stessaAmm && stessiStati && stessoBando)
        for (Long idProcedimento : idProcedimentiSelezionati)
        {
          dao.chiudiAmministrazioneProcedimento(idProcedimento);
          dao.insertOrganismoDelegato(idProcedimento, idAmministrazione,
              idUfficiozona, idUtenteLogin, note, idTecnico);
        }
    }
    catch (Exception e)
    {
      context.setRollbackOnly();
      throw e;
    }
  }

  @Override
  public List<RigaAnticipoLivello> getListAnticipoLivello(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    final String THIS_METHOD = "getListAnticipoLivello";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      return dao.getListAnticipoLivello(idProcedimentoOggetto);
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
  public void unsplitRiduzioneSanzione(long idProcOggSanzione,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
      RiduzioniSanzioniDTO riduzioneA)
      throws InternalUnexpectedException
  {
    try
    {
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      String flagControlloInLoco = dao.getFlagControllo(idProcedimentoOggetto);

      // cancello il record corretto in base al flag
      dao.deleteRiduzioneUnsplit(flagControlloInLoco, idProcedimentoOggetto,
          riduzioneA.getIdOperazione());
      // leggo quello che rimane
      RiduzioniSanzioniDTO r = dao.getRiduzioneSanzione(idProcOggSanzione,
          idProcedimentoOggetto);
      if (r != null && r.getIdProcOggSanzione() != null
          && r.getIdProcOggSanzione() == idProcOggSanzione)
        dao.delete("NEMBO_T_PROC_OGGETTO_SANZIONE", "ID_PROC_OGGETTO_SANZIONE",
            idProcOggSanzione);
      else
        dao.delete("NEMBO_T_PROC_OGGETTO_SANZIONE", "ID_PROC_OGGETTO_SANZIONE",
            riduzioneA.getIdProcOggSanzioneSecondRecordAfterSplit());

      // lo reinserisco con l'importo aggiornato
      if (r != null && r.getIdProcOggSanzione() != null)
        dao.inserisciSanzioneRiduzione(r.getIdTipologia(), r.getIdOperazione(),
            r.getIdDescrizione(), r.getNote(), riduzioneA.getImporto(),
            idProcedimentoOggetto);
      else
        dao.inserisciSanzioneRiduzione(riduzioneA.getIdTipologia(),
            riduzioneA.getIdOperazione(),
            riduzioneA.getIdDescrizioneSecondRecordAfterSplit(),
            riduzioneA.getNoteB(), riduzioneA.getImporto(),
            idProcedimentoOggetto);
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      context.setRollbackOnly();
      throw e;
    }
  }

  @Override
  public void splitRiduzioneSanzione(RiduzioniSanzioniDTO riduzione,
      long idProcOggSanzione,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
      BigDecimal importoA,
      BigDecimal importoB, String noteA, String noteB, String codice)
      throws InternalUnexpectedException
  {
    try
    {
      final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
          .getIdProcedimentoOggetto();
      dao.delete("NEMBO_T_PROC_OGGETTO_SANZIONE", "ID_PROC_OGGETTO_SANZIONE",
          idProcOggSanzione);
      Long idDescrizione2 = dao.getIdDescrizioneSanzione(codice);
      dao.inserisciSanzioneRiduzione(riduzione.getIdTipologia(),
          riduzione.getIdOperazione(), riduzione.getIdDescrizione(), noteA,
          importoA, idProcedimentoOggetto);
      dao.inserisciSanzioneRiduzione(riduzione.getIdTipologia(),
          riduzione.getIdOperazione(), idDescrizione2, noteB, importoB,
          idProcedimentoOggetto);
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      context.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getDescrizioneSanzioneSplit(String codice)
      throws InternalUnexpectedException
  {
    return dao.getDescrizioneSanzioneSplit(codice);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public RiduzioniSanzioniDTO getRiduzioneSanzione(long idProcOggSanzione,
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getRiduzioneSanzione(idProcOggSanzione, idProcedimentoOggetto);
  }

  @Override
  public boolean isCodiceLivelloInvestimentoEsistente(long idProcedimento,
      String codiceLivello) throws InternalUnexpectedException
  {
    return dao.isCodiceLivelloInvestimentoEsistente(idProcedimento,
        codiceLivello);
  }

  @Override
  public Long getAziendaRichiestaVoltura(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getAziendaRichiestaVoltura(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ProspettoEconomicoDTO> getProspettoEconomico(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getProspettoEconomico(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getCodiceOggetto(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getCodiceOggettoDelProcedimentoOggetto(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<IntegrazioneAlPremioDTO> getIntegrazioneAlPremio(
      long idProcedimento, long idProcedimentoOggetto, List<Long> ids)
      throws InternalUnexpectedException
  {
    return dao.getIntegrazioneAlPremio(idProcedimento, idProcedimentoOggetto,
        ids);
  }

  @Override
  public void updateIntegrazionePremio(ProcedimentoOggetto procedimentoOggetto,
      List<IntegrazioneAlPremioDTO> integrazione,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {

    try
    {
      dao.lockProcedimentoOggetto(
          procedimentoOggetto.getIdProcedimentoOggetto());

      for (IntegrazioneAlPremioDTO i : integrazione)
      {
        if (!dao.existRecordInNEMBO_R_PROC_OGG_LIV(
            procedimentoOggetto.getIdProcedimentoOggetto(), i.getIdLivello()))
          dao.updateIntegrazioneAlPremio(i.getIdLivello(),
              procedimentoOggetto.getIdProcedimentoOggetto(),
              i.getContributoIntegrazione());
        else
          dao.insertIntegrazioneAlPremio(i.getIdLivello(),
              procedimentoOggetto.getIdProcedimentoOggetto(),
              i.getContributoIntegrazione());

        logOperationOggettoQuadro(
            logOperationOggettoQuadroDTO.getIdProcedimentoOggetto(),
            logOperationOggettoQuadroDTO.getIdQuadroOggetto(),
            logOperationOggettoQuadroDTO.getIdBandoOggetto(),
            logOperationOggettoQuadroDTO.getExtIdUtenteAggiornamento());
        dao.updateRProcedimentoOggettoSanzione(
            procedimentoOggetto.getIdProcedimentoOggetto(), i.getIdLivello(),
            i.getContributoRiduzioniSanzioni());
      }
    }
    catch (Exception e)
    {
      context.setRollbackOnly();
      throw new InternalUnexpectedException(e.getCause());
    }
  }

  @Override
  public boolean isAmmissioneFuoriLinea(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.isAmmissioneFuoriLinea(idProcedimentoOggetto);
  }

  @Override
  public String getProcedimentoOggEstrattoCampioneDescr(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getProcedimentoOggEstrattoCampioneDescr(idProcedimentoOggetto);
  }

  @Override
  public String getProcedimentoEstrattoCampioneExPostDescr(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getProcedimentoEstrattoCampioneExPostDescr(idProcedimento);
  }

  @Override
  public String getProcedimentoEstrattoCampioneDescr(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getProcedimentoEstrattoCampioneDescr(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public ProcedimentoDTO getIterProcedimento(long idProcedimento,
      long codiceRaggruppamento) throws InternalUnexpectedException
  {
    return dao.getIterProcedimento(idProcedimento, codiceRaggruppamento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String checkFlagAltreInfoAtto(long idTipoAtto)
      throws InternalUnexpectedException
  {
    return dao.checkFlagAltreInfoAtto(idTipoAtto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getElencoAtti()
      throws InternalUnexpectedException
  {
    return dao.getElencoAtti();
  }

  @Override
  public boolean isEsisteImpBaseByCodiceLivello(long idProcedimentoOggetto,
      String codiceLivello) throws InternalUnexpectedException
  {
    return dao.isEsisteImpBaseByCodiceLivello(idProcedimentoOggetto,
        codiceLivello);
  }

  @Override
  public boolean isDataAmmissioneDaGestire(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.isDataAmmissioneDaGestire(idProcedimento);
  }

  @Override
  public void updateDataAmmissione(long idProcedimento, Date dataAmmissione)
      throws InternalUnexpectedException
  {
    dao.updateDataAmmissione(idProcedimento, dataAmmissione);
  }

  @Override
  public Date findDataProtocolloLettera(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.findDataProtocolloLettera(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getTecniciByUfficioDiZona(Long idUfficioZona, int idProcedimentoAgricolo)
      throws InternalUnexpectedException
  {
    return dao.getTecniciByUfficioDiZona(idUfficioZona, idProcedimentoAgricolo);
  }

  @Override
  public List<DecodificaDTO<Long>> geElencoTipiDocumenti()
      throws InternalUnexpectedException
  {
    return dao.geElencoTipiDocumenti();
  }

  @Override
  public TipoDocumentoSpesaVO getDettagioTipoDocumento(
      long idTipoDocumentoSpesa) throws InternalUnexpectedException
  {
    return dao.getDettagioTipoDocumento(idTipoDocumentoSpesa);
  }

  @Override
  public List<DecodificaDTO<Long>> geElencoFornitori()
      throws InternalUnexpectedException
  {
    return dao.geElencoFornitori();
  }

  @Override
  public List<DecodificaDTO<Long>> geElencoModalitaPagamento()
      throws InternalUnexpectedException
  {
    return dao.geElencoModalitaPagamento();
  }

  @Override
  public void inserisciInterventoSpesa(long idDocumentoSpesa,
      List<ImportoInterventoVO> importi, long idUtente)
      throws InternalUnexpectedException
  {

    for (ImportoInterventoVO importo : importi)
    {
      if (dao.docSpesaHasIntervento(idDocumentoSpesa,
          importo.getIdIntervento()))
        dao.updateRDocSpesaIntervento(idDocumentoSpesa, importo);
      else
      {
        // dao.delete("NEMBO_R_DOCUMENTO_SPESA_INTERV", "ID_DOCUMENTO_SPESA",
        // idDocumentoSpesa);
        dao.inserisciInterventoSpesa(idDocumentoSpesa, importo, idUtente);
      }
    }
    // dao.updateRDocSpesaIntervento(idDocumentoSpesa);
  }

  @Override
  public List<DecodificaDTO<Long>> geElencoFornitori(String piva)
      throws InternalUnexpectedException
  {
    return dao.geElencoFornitori(piva);
  }

  @Override
  public long inserisciFornitore(FornitoreDTO fornitore,
      boolean updateDocumento, Long idDettDocuemntoSpesa)
      throws InternalUnexpectedException
  {
    if (updateDocumento && idDettDocuemntoSpesa != null
        && idDettDocuemntoSpesa.longValue() > 0)
    {
      long idFornitore = dao.inserisciFornitore(fornitore);
      dao.aggiornaFornitore(idDettDocuemntoSpesa, idFornitore);
      return idFornitore;
    }
    return dao.inserisciFornitore(fornitore);
  }

  @Override
  public FornitoreDTO getDettaglioFornitore(long idFornitore)
      throws InternalUnexpectedException
  {
    return dao.getDettaglioFornitore(idFornitore);
  }

  @Override
  public void eliminaDocumentoSpesaById(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {

    // controllo se non è collegato a proc_ogg posso eliminare gli allegati e il
    // doc
    if (canDeleteDocSpesa(idDocumentoSpesa))
      eliminaAllegatiDocumentoSpesaById(idDocumentoSpesa);

    // elimino ricevute
    long[] idsDocumentoSpesa = new long[1];
    idsDocumentoSpesa[0] = idDocumentoSpesa;
    List<RicevutaPagamentoVO> ricevute = getElencoRicevutePagamento(
        idsDocumentoSpesa);
    if (ricevute != null)
      for (RicevutaPagamentoVO r : ricevute)
        if (canDeleteRicevuta(r.getIdDettRicevutaPagamento()))
        {
          dao.delete("NEMBO_R_DOC_SPESA_INT_RICEV_PA", "ID_RICEVUTA_PAGAMENTO",
              r.getIdRicevutaPagamento());
          eliminaRicevutaSpesaById(r.getIdDettRicevutaPagamento());
        }

    dao.delete("NEMBO_R_DOCUMENTO_SPESA_INTERV", "ID_DOCUMENTO_SPESA",
        idDocumentoSpesa);
    dao.delete("NEMBO_T_DETT_DOCUMENTO_SPESA", "ID_DOCUMENTO_SPESA",
        idDocumentoSpesa);
    dao.delete("NEMBO_T_DOCUMENTO_SPESA", "ID_DOCUMENTO_SPESA",
        idDocumentoSpesa);
  }

  @Override
  public DocumentoSpesaVO getDettagioDocumentoSpesa(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {
    DocumentoSpesaVO doc = dao.getDettagioDocumentoSpesa(idDocumentoSpesa);
    List<DocumentoSpesaVO> allegati = dao
        .getElencoAllegatiDocSpesa(idDocumentoSpesa, null);
    doc.setAllegati(allegati);
    return doc;
  }

  @Override
  public void aggiornaDocumentoSpesa(long idDettDocumentoSpesa,
      DocumentoSpesaVO documentoVO) throws InternalUnexpectedException
  {
    dao.aggiornaDocumentoSpesa(idDettDocumentoSpesa, documentoVO);
  }

  @Override
  public void aggiornaInterventoSpesa(long idProcedimentoOggetto,
      long idDocumentoSpesa, List<ImportoInterventoVO> importi)
      throws InternalUnexpectedException
  {
    dao.aggiornaInterventoSpesa(idProcedimentoOggetto, idDocumentoSpesa,
        importi);
  }

  @Override
  public BigDecimal getImportoIntervento(long idDocumentoSpesa,
      long idIntervento) throws InternalUnexpectedException
  {
    return dao.getImportoIntervento(idDocumentoSpesa, idIntervento);
  }

  @Override
  public FileAllegatoDTO getFileAllegatoDocSpesa(long idDocumentoSpesaFile)
      throws InternalUnexpectedException
  {
    return dao.getFileAllegatoDocSpesa(idDocumentoSpesaFile);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Boolean checkSeIProcSelezHannoLaStessaAmm(
      Vector<Long> idProcSelezionati) throws InternalUnexpectedException
  {
    return dao.checkSeIProcSelezHannoLaStessaAmm(idProcSelezionati);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean checkStatiOggetti(Vector<Long> vect)
      throws InternalUnexpectedException
  {
    return dao.checkStatiOggetti(vect);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean checkSeIProcSelezAppartengonoAlloStessoBando(
      Vector<Long> idProcSelezionati) throws InternalUnexpectedException
  {
    return dao.checkSeIProcSelezAppartengonoAlloStessoBando(idProcSelezionati);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public EsitoFinaleDTO getEsitoDefinitivo(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getEsitoDefinitivo(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean checkIfMotivazioniObbligatorieEsito(Long idEsito)
      throws InternalUnexpectedException
  {
    return dao.checkIfMotivazioniObbligatorieEsito(idEsito);
  }

  @Override
  public void updateEsitoDefinitivo(long idProcedimentoOggetto,
      EsitoFinaleDTO esito,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
      long idUtenteLogin) throws InternalUnexpectedException
  {

    try
    {
      dao.delete("NEMBO_T_ESITO_FINALE", "ID_ESITO_FINALE",
          esito.getIdEsitoFinale());
      dao.insertEsitoDefinitivo(idProcedimentoOggetto, esito);
      logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getCuaaByIdProcedimentoOggetto(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getCuaaByIdProcedimentoOggetto(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ImportoLiquidatoDTO> getListeDegliImportiLiquidazione(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getListeDegliImportiLiquidazione(idProcedimentoOggetto);
  }

  @Override
  public void updateGiustificazioneAnomaliaControllo(Long idSoluzioneAnomalia,
      long idControllo, long idProcedimentoOggetto, Long idTipoRisoluzione,
      String note, MultipartFile fileAllegato, String nomeFile,
      String nomeAllegato, Long idUtenteLogin, String prov, Date dataProtocollo,
      Date dataDocumento, String numProtocollo, boolean maintainOldFile)
      throws InternalUnexpectedException, IOException
  {

    FileAllegatoDTO file = dao.getAllegatoGiustificazione(idSoluzioneAnomalia);

    dao.delete("NEMBO_T_SOLUZIONE_ANOMALIA", "ID_SOLUZIONE_ANOMALIA",
        idSoluzioneAnomalia);

    if (maintainOldFile)
      dao.inserisciGiustificazioneAnomaliaControllo(idControllo,
          idProcedimentoOggetto, idTipoRisoluzione, note,
          file.getFileAllegato(), nomeAllegato, nomeFile, idUtenteLogin, prov,
          dataProtocollo, dataDocumento, numProtocollo);
    else
      if (fileAllegato != null)
        dao.inserisciGiustificazioneAnomaliaControllo(idControllo,
            idProcedimentoOggetto, idTipoRisoluzione, note,
            fileAllegato.getBytes(), nomeAllegato, nomeFile, idUtenteLogin,
            prov, dataProtocollo, dataDocumento, numProtocollo);
      else
        dao.inserisciGiustificazioneAnomaliaControllo(idControllo,
            idProcedimentoOggetto, idTipoRisoluzione, note, null, nomeAllegato,
            nomeFile, idUtenteLogin, prov, dataProtocollo, dataDocumento,
            numProtocollo);

  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean checkIfProcHaveSameUfficioZona(
      Vector<Long> idProcedimentiSelezionati) throws InternalUnexpectedException
  {
    return dao.checkIfProcHaveSameUfficioZona(idProcedimentiSelezionati);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean checkIfProcHaveSameTecnico(
      Vector<Long> idProcedimentiSelezionati) throws InternalUnexpectedException
  {
    return dao.checkIfProcHaveSameTecnico(idProcedimentiSelezionati);
  }

  @Override
  public void aggiornaFlagRendizontazione(String flagRendicontazione,
      long idProcedimento) throws InternalUnexpectedException
  {
    dao.aggiornaFlagRendizontazione(flagRendicontazione, idProcedimento);
  }

  @Override
  public List<Long> searchDocumentoSpesaByKey(DocumentoSpesaVO documentoVO)
      throws InternalUnexpectedException
  {
    TipoDocumentoSpesaVO documentoSpesaVO = dao
        .getDettagioTipoDocumento(documentoVO.getIdTipoDocumentoSpesa());
    return dao.searchDocumentoSpesaByKey(documentoSpesaVO, documentoVO);
  }

  @Override
  public long inserisciDocumentoSpesa(long idProcedimento,
      String firstCodiceAttore, DocumentoSpesaVO documentoVO)
      throws InternalUnexpectedException
  {
    try
    {
      long idDocumentoSpesa = dao.inserisciTDocumentoSpesa(idProcedimento);
      documentoVO.setIdDocumentoSpesa(idDocumentoSpesa);
      dao.inserisciDettDocumentoSpesa(documentoVO, firstCodiceAttore);
      return idDocumentoSpesa;
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public FornitoreDTO getDettaglioFornitoreByPiva(String piva)
      throws InternalUnexpectedException
  {
    return dao.getDettaglioFornitoreByPiva(piva);
  }

  public long storicizzaFornitore(long idFornitore, FornitoreDTO fornitoreNew)
      throws InternalUnexpectedException
  {
    dao.storicizzaFornitore(idFornitore);
    return dao.inserisciFornitore(fornitoreNew);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public BandoDTO getInformazioniBandoByProcedimento(long idProcedimento)
      throws InternalUnexpectedException
  {
    Long idBando = dao.getProcedimento(idProcedimento).getIdBando();
    return dao.getInformazioniBando(idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getCodiceAmmCompetenza(long extIdAmmComp)
      throws InternalUnexpectedException
  {
    return dao.getCodiceAmmCompetenza(extIdAmmComp);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DocumentoSpesaVO> getElencoDocumentiSpesa(long idProcedimento,
      long[] idsDocSpesa, long idIntervento) throws InternalUnexpectedException
  {
    return dao.getElencoDocumentiSpesa(idProcedimento, idsDocSpesa,
        idIntervento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> geElencoFornitoriProcedimento(
      long idProcedimento) throws InternalUnexpectedException
  {
    return dao.getElencoFronitoriProcedimento(idProcedimento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> geElencoFornitoriRicercaFatture(String CUAA,
      String denominazione) throws InternalUnexpectedException
  {
    return dao.geElencoFornitoriRicercaFatture(CUAA, denominazione);
  }

  @Override
  public List<RigaRendicontazioneDocumentiSpesaDTO> getListInterventiByIdDocumentoSpesa(
      long idDocumentoSpesa) throws InternalUnexpectedException
  {
    return dao.getListInterventiByIdDocumentoSpesa(idDocumentoSpesa);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public Boolean isComunicazionePagamento(long idOggetto, String cuName)
      throws InternalUnexpectedException
  {
    return dao.isComunicazionePagamento(idOggetto, cuName);
  }

  @Override
  public boolean findFornitore(FornitoreDTO fornitore)
      throws InternalUnexpectedException
  {
    return dao.findFornitore(fornitore);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public String getStatoOggetto(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getStatoOggetto(idProcedimento);
  }

  @Override
  public BigDecimal findImportoRendicontaTO(long idDocumentoSpesa,
      long idIntervento) throws InternalUnexpectedException
  {
    return dao.findImportoRendicontaTO(idDocumentoSpesa, idIntervento);
  }

  @Override
  public long updateOrInsertDatiDocumentoSpesa(long idProcedimento,
      String codAttore, DocumentoSpesaVO documentoVO)
      throws InternalUnexpectedException
  {
    try
    {
      if (documentoVO.getIdDocumentoSpesa() <= 0)
      {
        long idDocSpesa = inserisciDocumentoSpesa(idProcedimento, codAttore,
            documentoVO);
        documentoVO.setIdDocumentoSpesa(idDocSpesa);
        dao.insertTDocSpesaFile(documentoVO, codAttore);
        return idDocSpesa;
      }
      else
      {

        dao.updateTDocSpesaFile(documentoVO, codAttore);
        aggiornaDocumentoSpesa(documentoVO.getIdDettDocumentoSpesa(),
            documentoVO);
        return documentoVO.getIdDocumentoSpesa();
      }
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<String> getMacroCDUList(long idLegameGruppoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getMacroCDUList(idLegameGruppoOggetto);
  }

  @Override
  public int insertLog(String descrizione) throws InternalUnexpectedException
  {
    return dao.insertLog(descrizione);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isPrimoOggettoDelGruppo(long idProcedimento,
      long codiceRaggruppamento) throws InternalUnexpectedException
  {
    return dao.isPrimoOggettoDelGruppo(idProcedimento, codiceRaggruppamento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean hasTecnico(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.hasTecnico(idProcedimento);
  }

  @Override
  public void storicizzaProcedimentoOggetto(
      ProcedimentoOggetto procedimentoOggetto, Long extIdUtenteAggiornamento)
      throws InternalUnexpectedException
  {
    Long extIdAmm = dao.getIdAmmCompetenzaProcedimento(
        procedimentoOggetto.getIdProcedimento());
    Long extIdUffZona = dao
        .getIdUfficioZona(procedimentoOggetto.getIdProcedimento());
    try
    {
      dao.chiudiAmministrazioneProcedimento(
          procedimentoOggetto.getIdProcedimento());
      dao.insertNuovaAmmCompetenzaProcedimento(
          procedimentoOggetto.getIdProcedimento(), extIdAmm, extIdUffZona,
          extIdUtenteAggiornamento);
    }
    catch (Exception e)
    {
      sessionContext.setRollbackOnly();
      throw e;
    }
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isSoggettoAdApprovazione(Long idOggetto)
      throws InternalUnexpectedException
  {
    return dao.isSoggettoAdApprovazione(idOggetto);
  }

  @Override
  public void deletedocSpesaIntervento(long[] idDocumentoSpesa,
      long idIntervento) throws InternalUnexpectedException
  {
    dao.deletedocSpesaIntervento(idDocumentoSpesa, idIntervento);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean bandoHasLivWithVERCOD(long idBando)
  {
    return dao.bandoHasLivWithVERCOD(idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public boolean isEsitoApprovato(long idEsito)
      throws InternalUnexpectedException
  {
    return dao.isEsitoApprovato(idEsito);
  }

  @Override
  public String getFlagEstrattaControlliDichiarazione(long idProcedimento,
      long codiceRaggruppamento, long idProcedimentoOggetto,
      String codiceTipoBando) throws InternalUnexpectedException
  {
    return dao.getFlagEstrattaControlliDichiarazione(idProcedimento,
        codiceRaggruppamento, idProcedimentoOggetto, codiceTipoBando);
  }

  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<RicevutaPagamentoVO> getElencoRicevutePagamento(
      long[] idsDocumentoSpesa) throws InternalUnexpectedException
  {
    return dao.getElencoRicevutePagamento(idsDocumentoSpesa);
  }

  public void updateOrInsertRicevutaPagamento(long[] idsDocuemntiSpesa,
      RicevutaPagamentoVO ricevuta, String codiceAttore)
      throws InternalUnexpectedException
  {
    final String THIS_METHOD = "updateOrInsertRicevutaPagamento";
    if (logger.isDebugEnabled())
    {
      logger.debug("[" + THIS_CLASS + "." + THIS_METHOD + " BEGIN.");
    }
    try
    {
      if (ricevuta.getIdDettRicevutaPagamento() != null)
      {
        {
          dao.updateRicevutaPagamento(ricevuta, codiceAttore);
        }
      }
      else
      {
        for (long idDocSpesa : idsDocuemntiSpesa)
        {
          long idRicevutaPagamento = dao.insertRicevutaPagamento(idDocSpesa);
          dao.insertDettaglioRicevutaPagamento(idRicevutaPagamento, ricevuta,
              codiceAttore);
        }
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

  /*
   * @Override public FileAllegatoDTO getFileAllegatoRicevutaPagamento(long
   * idDettRicevutaPagamento) throws InternalUnexpectedException{ return
   * dao.getFileAllegatoRicevutaPagamento(idDettRicevutaPagamento); }
   */

  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public RicevutaPagamentoVO getRicevutaPagamento(long idDettRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.getRicevutaPagamento(idDettRicevutaPagamento);
  }

  @Override
  public void eliminaRicevutaSpesaById(long idDettRicevutaPagamento)
      throws InternalUnexpectedException
  {

    Long idRicevutaPagamento = dao
        .getIdRicevutaPagamento(idDettRicevutaPagamento);

    /*
     * List<Long> idsInterventi =
     * dao.getListInterventiByIdRicevutaPagamento(idRicevutaPagamento); Long
     * idDocumentoSpesa =
     * dao.getIdDocumentoSpesaByIdRicevutaPagamento(idRicevutaPagamento);
     * for(Long idIntervento : idsInterventi) {
     * dao.deleteRDocSpesaIntRic(idIntervento, idDocumentoSpesa,
     * idRicevutaPagamento); }
     */
    dao.delete("NEMBO_R_DOC_SPESA_INT_RICEV_PA", "ID_RICEVUTA_PAGAMENTO",
        idRicevutaPagamento);

    dao.delete("NEMBO_T_DETT_RICEVUTA_PAGAMENT", "ID_DETT_RICEVUTA_PAGAMENTO",
        idDettRicevutaPagamento);
    dao.delete("NEMBO_T_RICEVUTA_PAGAMENTO", "ID_RICEVUTA_PAGAMENTO",
        idRicevutaPagamento);

  }

  @Override
  public boolean controlloSommaRicevute(DocumentoSpesaVO documento,
      RicevutaPagamentoVO ricevuta, String flagRendicontazioneConIva)
      throws InternalUnexpectedException
  {
    return dao.controlloSommaRicevute(documento, ricevuta,
        flagRendicontazioneConIva);
  }

  @Override
  public boolean canDeleteRicevuta(long idDettRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.canDeleteRicevuta(idDettRicevutaPagamento);
  }

  @Override
  public void inserisciImportiInterventoDocRicevuta(
      List<DettaglioInterventoDTO> listaInterventi,
      long extIdUtenteAggiornamento) throws InternalUnexpectedException
  {
    for (DettaglioInterventoDTO intervento : listaInterventi)
      for (DocumentoSpesaVO doc : intervento.getIntervento()
          .getElencoDocumenti())
        for (RicevutaPagamentoVO r : doc.getElencoRicevutePagamento())
        {
          dao.deleteRDocSpesaIntRic(
              intervento.getIntervento().getIdIntervento(),
              doc.getIdDocumentoSpesa(), r.getIdRicevutaPagamento());
          // se un intervento è stato rimosso e quindi non più associato al
          // documento??
          // cancello tutti i record su NEMBO_R_DOC_SPESA_INT_RICEV_PA che non
          // hanno corrispondenza su NEMBO_R_DOCUMENTO_SPESA_INTERV
          dao.deleteImportiVecchiInterventi();
          // se l'importo è zero lo inserisco cmq, se null NO
          if (r.getImportoDaAssociare() != null)
            dao.insertRDocSpesaIntRic(
                intervento.getIntervento().getIdIntervento(),
                doc.getIdDocumentoSpesa(), r.getIdRicevutaPagamento(),
                r.getImportoDaAssociare(), extIdUtenteAggiornamento);
        }
  }

  @Override
  public BigDecimal getImportoDocSpesaIntRic(long idIntervento,
      long idDocumentoSpesa, long idRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.getImportoDocSpesaIntRic(idIntervento, idDocumentoSpesa,
        idRicevutaPagamento);
  }

  @Override
  public BigDecimal getImportoGiaAssociato(long idIntervento,
      long idDocumentoSpesa, long idRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.getImportoGiaAssociato(idIntervento, idDocumentoSpesa,
        idRicevutaPagamento);
  }

  @Override
  public Boolean canModifyRendicontazioneIva(long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.canModifyRendicontazioneIva(idProcedimento);
  }

  @Override
  public List<ExcelRicevutePagInterventoDTO> getElencoExcelRicevutePagamentoIntervento(
      long idProcedimento) throws InternalUnexpectedException
  {
    return dao.getElencoExcelRicevutePagamentoIntervento(idProcedimento);
  }

  @Override
  public List<ExcelRigaDocumentoSpesaDTO> getElencoExcelRicevutePagamento(
      long idProcedimento) throws InternalUnexpectedException
  {
    return dao.getElencoExcelRicevutePagamento(idProcedimento);
  }

  @Override
  public List<RegistroAntimafiaDTO> getDatiGeneraliRegistroAntimafia(
      String cuaa, String denominazione) throws InternalUnexpectedException
  {
    return dao.getRegistroAntimafia(cuaa, denominazione);
  }

  @Override
  public List<RegistroAntimafiaDTO> getDatiTitolareRegistroAntimafia(
      String cuaa, String denominazione) throws InternalUnexpectedException
  {
    return dao.getDatiTitolareRegistroAntimafia(cuaa, denominazione);
  }

  @Override
  public List<RegistroAntimafiaDTO> getDatiCertificatoRegistroAntimafia(
      String cuaa, String denominazione) throws InternalUnexpectedException
  {
    return dao.getRegistroAntimafia(cuaa, denominazione);
  }

  @Override
  public BigDecimal getImportoDocSpesaIntRicPag(long idRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.getImportoDocSpesaIntRicPag(idRicevutaPagamento);
  }

  @Override
  public boolean canDeleteDocSpesa(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {
    return dao.canDeleteDocSpesa(idDocumentoSpesa);
  }

  @Override
  public void eliminaAllegatiDocumentoSpesaById(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {
    dao.delete("NEMBO_T_DOCUMENTO_SPESA_FILE", "ID_DOCUMENTO_SPESA",
        idDocumentoSpesa);
  }

  @Override
  public List<DocumentoSpesaVO> getElencoDocumentiSpesa(long idProcedimento,
      ArrayList<String> idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato,
      String flagRendicontazioneConIva) throws InternalUnexpectedException
  {

    List<DocumentoSpesaVO> documenti = dao.getElencoDocumentiSpesaNew(
        idProcedimento,
        idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato);
    if (documenti != null)
    {
      for (DocumentoSpesaVO doc : documenti)
      {
        doc.setImportoLordoPagamento(
            dao.getImportoPagamentoLordo(doc.getIdDocumentoSpesa()));
        doc.setImportoAssociatoRic(
            dao.getImportoAssociatoRic(doc.getIdDocumentoSpesa()));
        List<DocumentoSpesaVO> allegati = dao.getElencoAllegatiDocSpesa(
            doc.getIdDocumentoSpesa(),
            idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato);
        doc.setAllegati(allegati);

        BigDecimal impRendNoFilter = dao
            .getImportoRendicontatoDoc(doc.getIdDocumentoSpesa());
        if (impRendNoFilter == null)
          impRendNoFilter = BigDecimal.ZERO;

        List<ProcedimentoOggettoDTO> procOggDoc = dao.getElencoProcOggDoc(
            doc.getIdDocumentoSpesa(),
            idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato);
        doc.setOggettiDoc(procOggDoc);

        BigDecimal importoRendicontatoConFiltro = BigDecimal.ZERO;
        if (procOggDoc != null)
          for (ProcedimentoOggettoDTO p : procOggDoc)
          {
            if (p.getImportoRendicontato() != null)
              if (p.getNumIstrNeg() == null || p.getNumIstrNeg() < 2)// se non
                                                                     // ho due
                                                                     // istruttorie
                                                                     // APP-N,
                                                                     // considero
                                                                     // l'importo
                importoRendicontatoConFiltro = importoRendicontatoConFiltro
                    .add(p.getImportoRendicontato());
          }
        doc.setImportoRendicontato(importoRendicontatoConFiltro);

        // Per l'importo da rendicontare, ignoro il filtro, faccio sempre il
        // calcolo con l'importo rendicontato globale a livello di documento.
        if (doc.getImportoAssociatoRic() != null && impRendNoFilter != null)
          doc.setImportoDaRendicontare(
              doc.getImportoAssociatoRic().subtract(impRendNoFilter));

        /*
         * Mostro l'icona dell'euro solo se il doc non è stato già completamente
         * rendicontato
         */
        if (flagRendicontazioneConIva != null)
        {
          BigDecimal importo = BigDecimal.ZERO;
          if ("S".equals(flagRendicontazioneConIva))
          {
            importo = doc.getImportoLordo();
          }
          else
            if ("N".equals(flagRendicontazioneConIva))
            {
              importo = doc.getImportoSpesa();
            }

          BigDecimal sommatoriaRic = dao
              .getSommaImportoRicevuteRendicontate(doc.getIdDocumentoSpesa());

          if (sommatoriaRic != null && importo.compareTo(sommatoriaRic) == 0)
          {
            doc.setFlagShowIconEuro("N");
          }
          else
            doc.setFlagShowIconEuro("S");
        }
        else
          doc.setFlagShowIconEuro("S");

      }

      List<DocumentoSpesaVO> docs = new ArrayList<>();
      for (DocumentoSpesaVO doc : documenti)
      {
        TipoDocumentoSpesaVO tipoDoc = dao
            .getDettagioTipoDocumento(doc.getIdTipoDocumentoSpesa());

        if (!"N".equals(tipoDoc.getFlagFileDocumentoSpesa()))
        {
          if (doc.getAllegati() != null && !doc.getAllegati().isEmpty())
            docs.add(doc);
        }
        else
          docs.add(doc);
      }
      return docs;
    }
    return null;
  }

  @Override
  public void deleteRDocSpesaIntRicPag(long[] idsDocSpesa, long idInt)
      throws InternalUnexpectedException
  {
    dao.deleteRDocSpesaIntRicPag(idsDocSpesa, idInt);
  }

  @Override
  public String showIconDocGiaRendicontato(DocumentoSpesaVO doc,
      String flagRendicontazioneConIva) throws InternalUnexpectedException
  {
    if (doc.getOggettiDoc() == null || doc.getOggettiDoc().isEmpty())
      return "N";
    /*
     * esiste un record in NEMBO_T_DOCUMENTO_SPESA_FILE con ID_DOCUMENTO_SPESA
     * del documento di spesa selezionato presente in
     * NEMBO_R_DOC_SPESA_PROC_OGG, ma lo stato del procedimento oggetto
     * collegato non valido (non è between 10 and 90)
     */
    List<DocumentoSpesaVO> allegs = dao
        .getElencoAllegatiDocSpesaWithPONotIn1090(doc.getIdDocumentoSpesa());
    if (allegs != null && !allegs.isEmpty())
      return "N";

    /*
     * sommatoria di NEMBO_R_DOC_SPESA_INT_RICEV_PA.IMPORTO con
     * ID_DOCUMENTO_SPESA = NEMBO_T_DETT_DOCUMENTO_SPESA.IMPORTO_SPESA se
     * NEMBO_T_PROCEDIMENTO.FLAG_RENDICONTAZIONE_CON_IVA = ‘N’ oppure sommatoria
     * di NEMBO_R_DOC_SPESA_INT_RICEV_PA.IMPORTO con ID_DOCUMENTO_SPESA =
     * NEMBO_T_DETT_DOCUMENTO_SPESA.IMPORTO_SPESA +
     * NEMBO_T_DETT_DOCUMENTO_SPESA.IMPORTO_IVA_SPESA se
     * NEMBO_T_PROCEDIMENTO.FLAG_RENDICONTAZIONE_CON_IVA = ‘S’) e non esiste un
     * record in NEMBO_T_DOCUMENTO_SPESA_FILE con ID_DOCUMENTO_SPESA del
     * documento di spesa selezionato non ancora presente in
     * NEMBO_R_DOC_SPESA_PROC_OGG.
     */
    BigDecimal importo = BigDecimal.ZERO;
    if ("S".equals(flagRendicontazioneConIva))
    {
      importo = doc.getImportoLordo();
    }
    else
      if ("N".equals(flagRendicontazioneConIva))
      {
        importo = doc.getImportoSpesa();
      }

    BigDecimal sommatoriaRic = dao
        .getSommaImportoRicevute(doc.getIdDocumentoSpesa());

    if (importo.compareTo(sommatoriaRic) == 0)
    {
      /*
       * e non esiste un record in NEMBO_T_DOCUMENTO_SPESA_FILE con
       * ID_DOCUMENTO_SPESA del documento di spesa selezionato non ancora
       * presente in NEMBO_R_DOC_SPESA_PROC_OGG.
       */
      if (dao.checkIfRecordFileNotInRDocSpesaPO(doc.getIdDocumentoSpesa()))
        return "N";

    }

    return "S";
  }

  @Override
  public DocumentoSpesaVO getDettagioDocumentoSpesaGiaRendicontato(
      long idDocumentoSpesa) throws InternalUnexpectedException
  {
    DocumentoSpesaVO doc = dao.getDettagioDocumentoSpesa(idDocumentoSpesa);
    if (doc != null)
      doc.setAllegati(
          dao.getElencoAllegatiDocSpesaGiaRendicontato(idDocumentoSpesa, null));
    return doc;
  }

  @Override
  public void insertOrUpdateFileAllegatoDocSpesaGiaRendicontato(
      DocumentoSpesaVO documentoVO, String codAttore, Long idDocumentoSpesaFile)
      throws InternalUnexpectedException
  {
    if (idDocumentoSpesaFile == null || idDocumentoSpesaFile == 0)
      dao.insertTDocSpesaFile(documentoVO, codAttore);
    else
    {
      documentoVO.setIdDocumentoSpesaFile(idDocumentoSpesaFile);
      dao.updateTDocSpesaFile(documentoVO, codAttore);
    }
  }

  @Override
  public List<DocumentoSpesaVO> getElencoAllegatiDocSpesa(long idDocumentoSpesa,
      ArrayList<String> idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato)
      throws InternalUnexpectedException
  {
    return dao.getElencoAllegatiDocSpesa(idDocumentoSpesa,
        idsDegliOggettiPerCuiVisualizzareAllegatiESommareImportoRendicontato);
  }

  @Override
  public boolean canDeleteRicevutaDocGiaRendicontato(
      long idDettRicevutaPagamento) throws InternalUnexpectedException
  {
    return dao.canDeleteRicevutaDocGiaRendicontato(idDettRicevutaPagamento);
  }

  @Override
  public BigDecimal getImportoAssociatoDoc(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {
    return dao.getImportoAssociatoDoc(idDocumentoSpesa);
  }

  @Override
  public boolean ricEsisteInDocSpesIntRicPag(long idRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.ricEsisteInDocSpesIntRicPag(idRicevutaPagamento);
  }

  @Override
  public BigDecimal getImportoPagamentoLordo(long idDocumentoSpesa)
      throws InternalUnexpectedException
  {
    return dao.getImportoPagamentoLordo(idDocumentoSpesa);
  }

  @Override
  public boolean canDeleteAndDeleteFile(long idDocumentoSpesaFile)
      throws InternalUnexpectedException
  {

    if (dao.canDeleteFile(idDocumentoSpesaFile) // se non ci sia già un record
                                                // collegato nella tabella
                                                // NEMBO_R_DOC_SPESA_PROC_OGG
        &&
        dao.nonEsistonoRicNotInRDocSpesaPO(idDocumentoSpesaFile))// e non
                                                                 // esistono
                                                                 // ricevute di
                                                                 // pagamento in
                                                                 // NEMBO_T_RICEVUTA_PAGAMENTO
                                                                 // legate al
                                                                 // documento di
                                                                 // spesa
                                                                 // selezionato
                                                                 // che non sono
                                                                 // presenti in
                                                                 // NEMBO_R_DOC_SPESA_PROC_OGG)
    {
      dao.delete("NEMBO_T_DOCUMENTO_SPESA_FILE", "ID_DOCUMENTO_SPESA_FILE",
          idDocumentoSpesaFile);
      return true;
    }
    return false;
  }

  @Override
  public boolean docHasInterventi(long idDocSpesa)
      throws InternalUnexpectedException
  {
    return dao.docHasInterventi(idDocSpesa);
  }

  @Override
  public boolean ricEsisteInDocSpesRicPag(long idRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.ricEsisteInDocSpesRicPag(idRicevutaPagamento);
  }

  @Override
  public Long getIdRicevutaPagamento(long idDettRicevutaPagamento)
      throws InternalUnexpectedException
  {
    return dao.getIdRicevutaPagamento(idDettRicevutaPagamento);
  }

  @Override
  public List<DocumentoSpesaVO> getElencoAllegatiIdIntervento(
      long idDocumentoSpesa, long idIntervento)
      throws InternalUnexpectedException
  {
    return dao.getElencoAllegatiIdIntervento(idDocumentoSpesa, idIntervento);
  }

  @Override
  public boolean canDeleteAllegato(long idDocumentoSpesaFile)
      throws InternalUnexpectedException
  {
    if (dao.canDeleteFile(idDocumentoSpesaFile) // se non ci sia già un record
                                                // collegato nella tabella
                                                // NEMBO_R_DOC_SPESA_PROC_OGG
        &&
        dao.nonEsistonoRicNotInRDocSpesaPO(idDocumentoSpesaFile))// e non
                                                                 // esistono
                                                                 // ricevute di
                                                                 // pagamento in
                                                                 // NEMBO_T_RICEVUTA_PAGAMENTO
                                                                 // legate al
                                                                 // documento di
                                                                 // spesa
                                                                 // selezionato
                                                                 // che non sono
                                                                 // presenti in
                                                                 // NEMBO_R_DOC_SPESA_PROC_OGG)
    {
      return true;
    }
    return false;
  }

  @Override
  public List<RegistroAntimafiaDTO> getDatiAziendaCertificatoRegistroAntimafia(
      String cuaa, String denominazione) throws InternalUnexpectedException
  {
    return dao.getDatiAziendaRegistroAntimafia(cuaa, denominazione);
  }

  @Override
  public List<RicevutaPagamentoVO> getElencoRicevutePagamentoDomande(
      long idDocumentoSpesa,
      ArrayList<Long> idsDomande) throws InternalUnexpectedException
  {
    return dao.getElencoRicevutePagamentoDomande(idDocumentoSpesa, idsDomande);
  }

  @Override
  public List<ExcelRicevutePagInterventoDTO> getElencoExcelRicevutePagamentoInterventoPerDomanda(
      long idProcedimento, long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getElencoExcelRicevutePagamentoInterventoPerDomanda(
        idProcedimento, idProcedimentoOggetto);
  }

  @Override
  public boolean canUseDocumentiSpesa(long idProcedimento, long idBando)
      throws InternalUnexpectedException
  {
    long cntRecordRendicontazione = dao
        .contaRecordRendicontazione(idProcedimento, idBando);
    long cntRecordDoc = dao.contaRecordDocSpesa(idProcedimento);

    if (cntRecordRendicontazione != 0 && cntRecordDoc == 0)
      return false;
    else
      return true;
  }

  @Override
  public long contaInterventiRendicontazConFlagS(long idProcedimento,
      long idBando) throws InternalUnexpectedException
  {
    return dao.contaInterventiRendicontazConFlagS(idProcedimento, idBando);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public DatiSpecificiDTO getDatiSpecificiExPost(long idProcedimentoOggetto,
      long idProcedimento)
      throws InternalUnexpectedException
  {
    return dao.getDatiSpecificiExPost(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<ControlloAmministrativoDTO> getControlliAmministrativiExPost(
      long idProcedimentoOggetto, String codiceQuadro, List<Long> ids)
      throws InternalUnexpectedException
  {
    return dao.getControlliAmministrativiExPost(idProcedimentoOggetto,
        codiceQuadro, ids);
  }

  @Override
  public void updateOrInsertControlliInLocoExPost(
      ControlliInLocoInvestDTO controlliInLocoInvestDTO,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    dao.lockProcedimentoOggetto(
        controlliInLocoInvestDTO.getIdProcedimentoOggetto());

    if (NemboConstants.FLAGS.NO
        .equals(controlliInLocoInvestDTO.getFlagControllo()))
    {
      // Rimuovo i dati presenti sui controlli in loco

      List<ControlloAmministrativoDTO> controlliAmministrativi = getControlliAmministrativi(
          controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          NemboConstants.QUADRO.CODICE.CONTROLLI_IN_LOCO_MISURE_INVESTIMENTO,
          null);

      if (controlliAmministrativi != null)
      {
        List<EsitoControlliAmmDTO> esitiControlliDaAggiornare = null;
        EsitoControlliAmmDTO esito = null;
        for (ControlloAmministrativoDTO contr : controlliAmministrativi)
        {
          if (esitiControlliDaAggiornare == null)
          {
            esitiControlliDaAggiornare = new ArrayList<>();
          }
          esito = new EsitoControlliAmmDTO();
          esito.setIdQuadroOggControlloAmm(contr.getIdQuadroOggControlloAmm());
          esitiControlliDaAggiornare.add(esito);
        }

        if (esitiControlliDaAggiornare != null)
        {
          dao.deleteEsitiControlliAmministrativi(
              controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
              esitiControlliDaAggiornare);
        }
      }

      dao.deleteEsitoTecnico(
          controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          controlliInLocoInvestDTO.getIdQuadroOggettoControlliLoco());
      dao.deleteVisitaLuogo(controlliInLocoInvestDTO.getIdProcedimentoOggetto(),
          controlliInLocoInvestDTO.getIdQuadroOggettoControlliLoco());
    }

    int numrecord = dao.updateControlliInLocoExPost(controlliInLocoInvestDTO);
    if (numrecord == 0)
    {
      dao.insertControlliInLocoExPost(controlliInLocoInvestDTO);
    }
    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public boolean procedimentoHasOggettoInStato(long idProcedimento,
      String codiceOggetto, long idStatoOggetto)
      throws InternalUnexpectedException
  {
    return dao.procedimentoHasOggettoInStato(idProcedimento, codiceOggetto,
        idStatoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public InfoExPostsDTO getInformazioniExposts(long idProcedimentoOggetto)
      throws InternalUnexpectedException
  {
    return dao.getInformazioniExposts(idProcedimentoOggetto);
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<DecodificaDTO<Long>> getDecodificheRischioElevato()
      throws InternalUnexpectedException
  {
    return dao.getDecodificheRischioElevato();
  }

  @Override
  @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
  public List<AnnoExPostsDTO> getDecodificheAnnoExpost(
      long idProcedimentoOggetto) throws InternalUnexpectedException
  {
    return dao.getDecodificheAnnoExpost(idProcedimentoOggetto);
  }

  @Override
  public void updateInfoExPost(InfoExPostsDTO info,
      LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
      throws InternalUnexpectedException
  {
    final long idProcedimentoOggetto = logOperationOggettoQuadroDTO
        .getIdProcedimentoOggetto();
    dao.lockProcedimentoOggetto(idProcedimentoOggetto);
    InfoExPostsDTO oldInfo = dao.getInformazioniExposts(idProcedimentoOggetto);
    if (oldInfo != null)
    {
      dao.delete("NEMBO_R_ANNI_EXPOST", "ID_INFO_EXPOST",
          oldInfo.getIdInfoExPosts());
      dao.delete("NEMBO_T_INFO_EXPOST", "ID_INFO_EXPOST",
          oldInfo.getIdInfoExPosts());
    }

    long idDatiProcedimento = dao.getIdDatiProcedimento(idProcedimentoOggetto);
    long idInfoExPost = dao.inserInfoExPost(idDatiProcedimento, info);
    if (info.getAnniExPosts() != null)
    {
      for (AnnoExPostsDTO anno : info.getAnniExPosts())
      {
        dao.inserRAnniExPost(idInfoExPost, anno.getIdAnnoExPosts());
      }
    }

    logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  }

  @Override
  public List<ProcedimentoAgricoloDTO> getProcedimentiAgricoli()
      throws InternalUnexpectedException
  {
    return dao.getProcedimentiAgricoli();
  }
  
  @Override
  public boolean verificaEsistenzaProcedimentoAgricolo(int idProcedimentoAgricolo)
      throws InternalUnexpectedException
  {
    return dao.verificaEsistenzaProcedimentoAgricolo(idProcedimentoAgricolo);
  }


	@Override
	public ContenutoFileAllegatiDTO getImmagineDaVisualizzare(long idProcedimentoAgricolo)
			throws InternalUnexpectedException {
		return dao.getImmagineDaVisualizzare(idProcedimentoAgricolo);
	}
	
	private String getGruppoIdentificativo(long idProcedimento, long idProcedimentoOggetto) throws InternalUnexpectedException{
		String gruppoIdentificativo = dao.getProcedimento(idProcedimento).getIdentificativo();
		if(gruppoIdentificativo==null){
			gruppoIdentificativo=dao.getProcedimentoOggetto(idProcedimentoOggetto).getIdentificativo();
			if(gruppoIdentificativo!=null)
				gruppoIdentificativo = gruppoIdentificativo.substring(0, 11);
		}
		return gruppoIdentificativo;
	}
	
	@Override
	public byte[] caricaTemplate(String codice) throws InternalUnexpectedException
	{
		return dao.caricaTemplate(codice);
	}
	
	
	public Map<String, String> getParametriBandoOggetto(long idBandoOggetto, String[] paramNames) throws InternalUnexpectedException{
		return dao.getParametriBandoOggetto(idBandoOggetto, paramNames);
	}
	
	@Override
	public DecodificaDTO<String> findDatiCaa(long idProcedimentoOggetto) throws InternalUnexpectedException{
		return dao.findDatiCaa(idProcedimentoOggetto);
	}
	
	@Override
	public SegnalazioneDannoDTO getSegnalazioneDanno(long idProcedimentoOggetto, List<Long> ids)throws InternalUnexpectedException{
		SegnalazioneDannoDTO dannoDTO = dao.getDatiDanno(idProcedimentoOggetto);
		dannoDTO.setElencoDanni(dao.getSegnalazioneDanno(idProcedimentoOggetto,ids));
		return dannoDTO;
	}

	@Override
	public void deleteSegnalazioniDanno(long idDettaglioSegnalDanno)throws InternalUnexpectedException{
		dao.deleteSegnalazioniDanno(idDettaglioSegnalDanno);
	}
	
	@Override
	public void updateSegnalazioniDanno(List<RigaSegnalazioneDannoDTO> elenco)throws InternalUnexpectedException
	{
		if(elenco!=null)
		{
			for(RigaSegnalazioneDannoDTO row: elenco)
				dao.updateSegnalazioneDanno(row);
		}
	}
	
	@Override
	public void updateDatiGeneraliDanno(long idProcedimentoOggetto, String descrizione, String dataDate)throws InternalUnexpectedException
	{
		dao.updateDatiGeneraliDanno(idProcedimentoOggetto,descrizione,dataDate);
	}
	
}
