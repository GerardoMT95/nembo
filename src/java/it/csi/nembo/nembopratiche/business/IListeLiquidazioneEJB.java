package it.csi.nembo.nembopratiche.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.springframework.web.multipart.MultipartFile;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.ImportiRipartitiListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.StampaListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.DatiCreazioneListaDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.DatiListaDaCreareDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.LivelliBandoDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RiepilogoImportiApprovazioneDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RiepilogoPraticheApprovazioneDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONBandiNuovaListaDTO;
import it.csi.nembo.nembopratiche.dto.listeliquidazione.RigaJSONElencoListaLiquidazioneDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.papua.papuaserv.dto.gestioneutenti.ws.UtenteAbilitazioni;

@Local
public interface IListeLiquidazioneEJB extends INemboAbstractEJB
{
  List<RigaJSONElencoListaLiquidazioneDTO> getListeLiquidazione()
      throws InternalUnexpectedException;

  List<Map<String, Object>> getAmministrazioniCompetenzaListe()
      throws InternalUnexpectedException;

  List<RigaJSONBandiNuovaListaDTO> getBandiProntiPerListeLiquidazione(
      List<Long> lIdAmmCompetenza) throws InternalUnexpectedException;

  LivelliBandoDTO getLivelliBando(long idBando)
      throws InternalUnexpectedException;

  List<DecodificaDTO<Long>> findAmministrazioniInProcedimentiBando(long idBando,
      List<Long> lIdAmmCompetenza) throws InternalUnexpectedException;

  void deleteListaLiquidazione(Long idListaLiquidazione)
      throws InternalUnexpectedException;

  StampaListaLiquidazioneDTO getStampaListaLiquidazione(
      long idListaLiquidazione) throws InternalUnexpectedException;

  String getStatoListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException;

  Boolean ripristinaStampaListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException;

  void setStatoListaLiquidazione(long idListaLiquidazione, int stato)
      throws InternalUnexpectedException;

  byte[] getContenutoFileListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException;

  RigaJSONElencoListaLiquidazioneDTO getListaLiquidazioneById(
      long idListaLiquidazione) throws InternalUnexpectedException;

  List<ImportiRipartitiListaLiquidazioneDTO> getImportiRipartitiListaLiquidazione(
      long idListaLiquidazione) throws InternalUnexpectedException;

  List<DecodificaDTO<Long>> getTecniciLiquidatori(long idAmmCompetenza, int idProcedimentoAgricoltura)
      throws InternalUnexpectedException;

  DatiListaDaCreareDTO getDatiListaDaCreare(long idBando, long idAmmCompetenza,
      long idTipoImporto, List<Long> idsPODaEscludere)
      throws InternalUnexpectedException;

  DatiCreazioneListaDTO creaListaLiquidazione(long idBando,
      long idAmmCompetenza, int idTipoImporto, Long idTecnicoLiquidatore,
      DatiListaDaCreareDTO datiListaDaCreareDTO, long idUtenteAggiornamento,
      List<Long> idsPODaEscludere)
      throws InternalUnexpectedException, ApplicationException;

  List<RiepilogoImportiApprovazioneDTO> getRiepilogoImportiApprovazione(
      long idListaLiquidazione)
      throws InternalUnexpectedException;

  void aggiornaStatoLista(long idListaLiquidazione, String flagStatoLista,
      long idUtenteAggiornamento)
      throws InternalUnexpectedException, ApplicationException;

  ApplicationException approvaLista(long idListaLiquidazione,
      MultipartFile stampaFirmata, UtenteAbilitazioni utenteAbilitazioni)
      throws InternalUnexpectedException;

  public List<RiepilogoPraticheApprovazioneDTO> getRiepilogoPraticheInNuovaLista(
      long idBando, long idAmmCompetenza,
      int idTipoImporto)
      throws InternalUnexpectedException;

  String getTitoloListaLiquidazioneByIdBando(long idBando)
      throws InternalUnexpectedException;

  boolean isListaLiquidazioneCorrotta(long idListaLiquidazione)
      throws InternalUnexpectedException;

  boolean isContenutoFileListaLiquidazioneDisponibile(long idListaLiquidazione)
      throws InternalUnexpectedException;

  List<RiepilogoPraticheApprovazioneDTO> getRiepilogoPraticheInNuovaListaWithAnomalia(
      long idBando, long idAmmCompetenza, int idTipoImporto,
      long idTecnicoLiquidatore) throws InternalUnexpectedException;

  long getIdBandoByIdListaLiquidazione(long idListaLiquidazione)
      throws InternalUnexpectedException;

  List<RiepilogoPraticheApprovazioneDTO> getRiepilogoPraticheListaLiquidazione(
      long idListaLiquidazione, boolean isPremio)
      throws InternalUnexpectedException;

  List<LivelloDTO> getElencoLivelli() throws InternalUnexpectedException;

}
