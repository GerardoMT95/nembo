package it.csi.nembo.nembopratiche.business;

import java.util.List;
import java.util.Vector;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.AmmCompetenzaDTO;
import it.csi.nembo.nembopratiche.dto.AziendaDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.FileAllegatoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.OggettoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Local
public interface INuovoProcedimentoEJB extends INemboAbstractEJB
{
  public BandoDTO getDettaglioBandoByIdBando(long idBando)
      throws InternalUnexpectedException;

  public List<BandoDTO> getElencoBandiAttivi(int idProcedimentoAgricolo)
      throws InternalUnexpectedException;

  public List<OggettoDTO> getOggettiByIdBando(long idBando)
      throws InternalUnexpectedException;

  public List<Long> getAziendeByCUAA(String cuaa,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException;

  public List<Long> getAziendeByIdBando(long idBando,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException;

  public List<AziendaDTO> getDettaglioAziendeById(Vector<Long> vIdAzienda,
      long idBando) throws InternalUnexpectedException;

  public List<AziendaDTO> getDettaglioAziendeByIdBando(long idBando,
      UtenteAbilitazioni utenteAbilitazioni) throws InternalUnexpectedException;

  public MainControlloDTO callMainControlliGravi(long idBandoOggetto,
      Long idProcedimento, long idAzienda, long idUtenteLogin,
      Long codRaggruppamento) throws InternalUnexpectedException;

  public MainControlloDTO callMainCreazione(long idBando,
      long idLegameGruppoOggetto, Long idProcedimento, long idAzienda,
      long idUtenteLogin, String codAttore, Long codRaggruppamento,
      boolean forzaAlfanumerico, String note) throws InternalUnexpectedException;

  public FileAllegatoDTO getFileAllegato(long idAllegatiBando)
      throws InternalUnexpectedException;

  public List<FileAllegatoDTO> getElencoAllegati(long idBando)
      throws InternalUnexpectedException;

  public List<AmmCompetenzaDTO> getAmmCompetenzaAssociate(long idBando)
      throws InternalUnexpectedException;

  void updateTecnicoAndUfficioZonaIfModified(long idProcedimento,
      long extIdUfficioZona, long extIdTecnico,
      long idUtenteLogin) throws InternalUnexpectedException;

  public List<Procedimento> searchProcedimentiBandoEsistente(long idBando,
      long idAzienda) throws InternalUnexpectedException;

  public void inserisciProcDomandaPrec(int idProcedimentoVecchio, int idAzienda,
      long idBandoVecchio) throws InternalUnexpectedException;

  public boolean callIsValidazioneGrafica(long idAzienda, String annoCampagna)
      throws InternalUnexpectedException;
}
