package it.csi.nembo.nembopratiche.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.ComuneDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.FiltroVO;
import it.csi.nembo.nembopratiche.dto.FocusAreaDTO;
import it.csi.nembo.nembopratiche.dto.GraduatoriaDTO;
import it.csi.nembo.nembopratiche.dto.GravitaNotificaVO;
import it.csi.nembo.nembopratiche.dto.GruppoOggettoDTO;
import it.csi.nembo.nembopratiche.dto.IterProcedimentoGruppoDTO;
import it.csi.nembo.nembopratiche.dto.NotificaDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoOggettoVO;
import it.csi.nembo.nembopratiche.dto.RicercaProcedimentiVO;
import it.csi.nembo.nembopratiche.dto.SettoriDiProduzioneDTO;
import it.csi.nembo.nembopratiche.dto.VisibilitaDTO;
import it.csi.nembo.nembopratiche.dto.gestioneeventi.EventiDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.FileAllegatoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.ProcedimentoGruppoVO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.papua.papuaserv.dto.gestioneutenti.MacroCU;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Local
public interface IRicercaEJB extends INemboAbstractEJB
{
  public List<LivelloDTO> getLivelliAttivi(int idProcedimentoAgricolo) throws InternalUnexpectedException;

  public List<BandoDTO> getBandiAttivi(long[] lIdLivelli, long[] lIdEventi, int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<AmmCompetenzaDTO> getAmministrazioniAttive(
      long[] lIdBando) throws InternalUnexpectedException;

  public List<ProcedimentoDTO> getStatiProcedimentiAttivi(
     long[] lIdLivelli, long[] lIdBando,
      long[] lIdAmministrazioni) throws InternalUnexpectedException;

  public List<Long> searchIdProcedimenti(RicercaProcedimentiVO vo,
      UtenteAbilitazioni utenteAbilitazioni, String orderColumn,
      String orderType) throws InternalUnexpectedException;

  public List<ProcedimentoOggettoVO> getDettaglioProcedimentiOggettiById(
      Vector<Long> vIdProcedimento) throws InternalUnexpectedException;

  public List<GruppoOggettoDTO> getOggettiProcedimentiAttivi(
      long[] lIdLivelli, long[] lIdBando,
      long[] lIdAmministrazioni, long[] lIdStati,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException;

  public List<ComuneDTO> searchComuni(String prov, String descrComune)
      throws InternalUnexpectedException;

  public List<GruppoOggettoDTO> getElencoOggetti(long idProcedimento,
      List<MacroCU> lMacroCdu, int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<ProcedimentoOggettoVO> searchProcedimenti(
      RicercaProcedimentiVO ricercaVO, UtenteAbilitazioni utenteAbilitazioni)
      throws InternalUnexpectedException;

  public List<GruppoOggettoDTO> getElencoOggettiDisponibili(long idBando,
      boolean flagIstanza, Long idGruppoOggetto)
      throws InternalUnexpectedException;

  public List<BandoDTO> getElencoBandi(String string, int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<GraduatoriaDTO> getGraduatorieBando(long idBando)
      throws InternalUnexpectedException;

  public List<FileAllegatoDTO> getAllegatiGraduatoria(long idGraduatoria)
      throws InternalUnexpectedException;

  public FileAllegatoDTO getFileAllegatoGraduatoria(long idAllegatiGraduatoria)
      throws InternalUnexpectedException;

  public List<LivelloDTO> getLivelliBando(long idBando)
      throws InternalUnexpectedException;

  public List<LivelloDTO> getElencoLivelli() throws InternalUnexpectedException;
  
  public List<LivelloDTO> getElencoLivelliByProcedimentoAgricolo(int idProcedimentoAgricolo) throws InternalUnexpectedException;


  public List<NotificaDTO> getNotifiche(long idProcedimento, String attore)
      throws InternalUnexpectedException;

  public NotificaDTO getNotificaById(long idNotifica)
      throws InternalUnexpectedException;

  public List<VisibilitaDTO> getElencoVisibilitaNotifiche(String attore)
      throws InternalUnexpectedException;

  public void updateNotifica(long idNotifica, NotificaDTO notifica,
      long idProcedimento) throws InternalUnexpectedException;

  public void insertNuovaNotifica(NotificaDTO notificaNew, long idProcedimento)
      throws InternalUnexpectedException;

  public void eliminaNotifica(long idNotifica)
      throws InternalUnexpectedException;

  public List<LivelloDTO> getMisure() throws InternalUnexpectedException;

  public List<LivelloDTO> getOperazioni(Vector<Long> idMisureSelezionate)
      throws InternalUnexpectedException;

  public List<SettoriDiProduzioneDTO> getElencoSettoriBandi()
      throws InternalUnexpectedException;

  public List<FocusAreaDTO> getElencoFocusAreaBandi(int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<FocusAreaDTO> getElencoFocusArea(long idBando)
      throws InternalUnexpectedException;

  public List<SettoriDiProduzioneDTO> getElencoSettori(long idBando)
      throws InternalUnexpectedException;

  public List<ProcedimentoOggettoVO> searchProcedimentiEstrazione(
      Long idEstrazione, UtenteAbilitazioni utenteAbilitazioni,
      HashMap<String, FiltroVO> mapFilters, String limit, String offset)
      throws InternalUnexpectedException;

  public int searchProcedimentiEstrazioneCount(Long idEstrazione,
      UtenteAbilitazioni utenteAbilitazioni,
      HashMap<String, FiltroVO> mapFilters) throws InternalUnexpectedException;

  public List<Long> elencoIdProcedimentiEstratti(
      UtenteAbilitazioni utenteAbilitazioni, long idEstrazione)
      throws InternalUnexpectedException;

  public List<Long> elencoIdProcedimentiOggettoEstratti(
      UtenteAbilitazioni utenteAbilitazioni, long idEstrazione)
      throws InternalUnexpectedException;

  public List<ProcedimentoOggettoVO> getElencoTipologieEstrazione()
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getGestoriFascicolo(long idBando,
      long idIntermediario) throws InternalUnexpectedException;

  public List<LivelloDTO> getMisureConFlagTipo(int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<Long>> getTipiMisure(int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<LivelloDTO> getOperazioniMisure(Vector<Long> vect,
      String codiceMisura) throws InternalUnexpectedException;

  public List<GravitaNotificaVO> getElencoGravitaNotifica()
      throws InternalUnexpectedException;

  public DecodificaDTO<String> findProtocolloPratica(long idProcedimentoOggetto)
      throws InternalUnexpectedException;

  public DecodificaDTO<String> findProtocolloPratica(long idProcedimentoOggetto,
      long idCategoriaDocLettereNemboPraticheSuDoquiagri)
      throws InternalUnexpectedException;

  public List<ProcedimentoDTO> getElencoProcedimentiRegistroFatture(
      long[] idsTipologiaDocumento, long[] idsModalitaPagamento,
      Long idFornitore, Date dataDa, Date dataA)
      throws InternalUnexpectedException;

  public List<GruppoOggettoDTO> getElencoOggettiChiusi(long idProcedimento,
      List<MacroCU> lMacroCdu, boolean isBeneficiarioOCAA, int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<IterProcedimentoGruppoDTO> getIterGruppoOggetto(
      long idProcedimento, long codRaggruppamento)
      throws InternalUnexpectedException;

  //TODO: FIXME: rimuovere, probabilmente in disuso
  public List<GruppoOggettoDTO> getStatiAmmProcedimentiAttivi(
      Vector<Long> lIdLivelli, Vector<Long> lIdBando,
      Vector<Long> lIdAmministrazioni, Vector<Long> lIdStatiProc)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getElencoDescrizioniGruppi()
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getElencoDescrizioneStatiOggetti()
      throws InternalUnexpectedException;

  public List<ProcedimentoGruppoVO> getElencoProcedimentoGruppo(
      long idProcedimento, long codiceRaggruppamento)
      throws InternalUnexpectedException;

  public void sbloccaGruppoOggetto(long idProcedimento, long codRaggruppamento,
      String note, Long idUtenteLogin)
      throws InternalUnexpectedException, ApplicationException;

  List<Long> elencoIdProcedimentiEstrattiExPost(
      UtenteAbilitazioni utenteAbilitazioni, long idEstrazione)
      throws InternalUnexpectedException;

  List<Long> elencoIdProcedimentiOggettoEstrattiExPost(
      UtenteAbilitazioni utenteAbilitazioni, long idEstrazione)
      throws InternalUnexpectedException;
	
	public List<EventiDTO> getEventiAttivi(long[] idLivelli, int idProcedimentoAgricolo)
		throws InternalUnexpectedException;
	
	public DecodificaDTO<String> findProtocolloPraticaByIdPOAndCodice(long idProcedimentoOggetto, long idTipoDoc)
			throws InternalUnexpectedException;
}
