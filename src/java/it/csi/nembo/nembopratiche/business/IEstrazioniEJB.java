package it.csi.nembo.nembopratiche.business;

import java.util.List;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.RigaFiltroDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.FlagEstrazioneDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.ImportiAttualiPrecedentiDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.ImportiTotaliDTO;
import it.csi.nembo.nembopratiche.dto.estrazionecampione.NumeroLottoDTO;
import it.csi.nembo.nembopratiche.dto.plsql.MainControlloDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.estrazionecampione.RigaSimulazioneEstrazioneDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

@Local
public interface IEstrazioniEJB extends INemboAbstractEJB
{
  public ImportiTotaliDTO getImportiTotali(long idNumeroLotto)
      throws InternalUnexpectedException;

  public ImportiAttualiPrecedentiDTO getImportiAttualiPrecedenti(
      long idNumeroLotto) throws InternalUnexpectedException;

  public List<RigaSimulazioneEstrazioneDTO> getElencoRisultati(
      long idNumeroLotto) throws InternalUnexpectedException;

  public MainControlloDTO callRegistraDP(long idNumeroLotto,
      long idTipoEstrazione) throws InternalUnexpectedException;

  public void eliminaEstrazioni(long idNumeroLotto)
      throws InternalUnexpectedException;

  public Boolean isEstrazioneAnnullabile(long idNumeroLotto,
      long idTipoEstrazione) throws InternalUnexpectedException;

  public void annullaEstrazioni(long idNumeroLotto, String motivo)
      throws InternalUnexpectedException;

  public List<RigaFiltroDTO> getElencoTipoEstrazioniCaricabili()
      throws InternalUnexpectedException;

  public long caricamentoEstrazioni(long idTipoEstrazione,
      String utenteDescrizione)
      throws InternalUnexpectedException, ApplicationException;

  public MainControlloDTO callEstraiDP(long idNumeroLotto,
      long idTipoEstrazione, String chkRegistra)
      throws InternalUnexpectedException;

  public NumeroLottoDTO getNumeroLottoDto(long idNumeroLotto)
      throws InternalUnexpectedException;

  public List<FlagEstrazioneDTO> getElencoFlagEstrazioni()
      throws InternalUnexpectedException;

  public List<FlagEstrazioneDTO> getElencoFlagEstrazioniExPost()
      throws InternalUnexpectedException;
}
