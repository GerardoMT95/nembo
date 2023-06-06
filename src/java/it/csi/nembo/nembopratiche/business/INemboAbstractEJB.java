package it.csi.nembo.nembopratiche.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.csi.nembo.nembopratiche.dto.ComuneDTO;
import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.ProcedimentoEProcedimentoOggetto;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.procedimento.Procedimento;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.RigaJSONConduzioneInteventoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

public interface INemboAbstractEJB
{
  public List<DecodificaDTO<Integer>> getTabellaDecodifica(String nomeTabella,
      boolean orderByDesc) throws InternalUnexpectedException;

  public Map<String, String> getParametri(String[] paramNames)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getProvincie(String idRegione)
      throws InternalUnexpectedException;
  
  public List<String> getProvinceCodici(final String idRegione)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getProvincie(String idRegione,
      boolean visualizzaStatiEsteri) throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getDecodificheComuni(String idRegione,
      String istatProvincia, String flagEstinto)
      throws InternalUnexpectedException;

  public List<ComuneDTO> getDecodificheComuniWidthProv(String idRegione,
      String istatProvincia, String flagEstinto)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getSezioniPerComune(String istatComune)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getProvincieConTerreniInConduzione(
      long idProcedimentoOggetto, String istatRegione, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getComuniPerProvinciaConTerreniInConduzione(
      long idProcedimentoOggetto, String istatProvincia, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> getSezioniPerComuneConTerreniInConduzione(
      long idProcedimentoOggetto, String istatProvincia, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException;

  public List<RigaJSONConduzioneInteventoDTO> ricercaConduzioni(
      FiltroRicercaConduzioni filtro, int idProcedimentoAgricoltura) throws InternalUnexpectedException;

  public String getParametroComune(String idParametro)
      throws InternalUnexpectedException;

  public Map<String, String> getParametriComune(String... idParametro)
      throws InternalUnexpectedException;

  public LogOperationOggettoQuadroDTO getIdUtenteUltimoModifica(
      long idProcediemntoOggetto, long idQuadroOggetto, long idBandoOggetto)
      throws InternalUnexpectedException;

  public String getHelpCdu(String codcdu, Long idQuadroOggetto)
      throws InternalUnexpectedException;

  public Map<String, String> getMapHelpCdu(String... codcdu)
      throws InternalUnexpectedException;

  public List<String[]> getStatoDatabase() throws InternalUnexpectedException;

  public Date getSysDate() throws InternalUnexpectedException;

  public BandoDTO getInformazioniBando(long idBando)
      throws InternalUnexpectedException;

  List<ComuneDTO> getComuni(String idRegione, String istatProvincia,
      String flagEstinto, String flagEstero) throws InternalUnexpectedException;

  ComuneDTO getComune(String istatComune) throws InternalUnexpectedException;

  ProcedimentoEProcedimentoOggetto getProcedimentoEProcedimentoOggettoByIdProcedimentoOggetto(
      long idProcedimentoOggetto, boolean ricaricaQuadri)
      throws InternalUnexpectedException;

  public Procedimento getProcedimento(long idProcedimento)
      throws InternalUnexpectedException;

  public List<ComuneDTO> getDecodificheComuniWidthProvByComune(
      String denominazioneComune, String flagEstinto)
      throws InternalUnexpectedException;

  public int getIdProcedimentoAgricoloByIdProcedimentoOggetto(
      long idProcedimentoOggetto) throws InternalUnexpectedException;
  
  public BandoDTO getInformazioniBandoByIdProcOgg(long idProcOggetto)throws InternalUnexpectedException;

}
