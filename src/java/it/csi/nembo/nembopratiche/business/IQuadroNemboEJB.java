package it.csi.nembo.nembopratiche.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.DocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.dto.LogOperationOggettoQuadroDTO;
import it.csi.nembo.nembopratiche.dto.ReferenteProgettoDTO;
import it.csi.nembo.nembopratiche.dto.SezioneDocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDettaglioPlvDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.ProduzioneCategoriaAnimaleDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.DannoDTO;
import it.csi.nembo.nembopratiche.dto.danni.ParticelleDanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.UnitaMisuraDTO;
import it.csi.nembo.nembopratiche.dto.dannicolture.DanniColtureDTO;
import it.csi.nembo.nembopratiche.dto.fabbricati.FabbricatiDTO;
import it.csi.nembo.nembopratiche.dto.motoriagricoli.MotoriAgricoliDTO;
import it.csi.nembo.nembopratiche.dto.prestitiagrari.PrestitiAgrariDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioParticellareDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioPsrDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColturePlvVegetaleDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureRiepilogoDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

@Local
public interface IQuadroNemboEJB extends INemboAbstractEJB
{
	
	  public List<ScorteDTO> getListaScorteByProcedimentoOggetto(long idProcedimentoOggetto) throws InternalUnexpectedException;
	  
	  public long getIdStatoProcedimento(long idProcedimentoOggetto)  throws InternalUnexpectedException;
	  
	  public List<DecodificaDTO<Long>> getElencoTipologieScorte()  throws InternalUnexpectedException;
	  
	  public List<DecodificaDTO<Long>> getListUnitaDiMisura() throws InternalUnexpectedException;

	  public Long getUnitaMisuraByScorta(long idScorta) throws InternalUnexpectedException;
	  
	  public long getIdScorteAltro() throws InternalUnexpectedException;

	  public long inserisciScorte(long idProcedimentoOggetto, long idScorta, BigDecimal quantita, Long idUnitaMisura, String descrizione,LogOperationOggettoQuadroDTO  logOperationOggettoQuadroDTO) throws InternalUnexpectedException;
	  
	  public long modificaScorte(List<ScorteDTO> listScorte,LogOperationOggettoQuadroDTO  logOperationOggettoQuadroDTO, long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public ScorteDTO getScortaByIdScortaMagazzino(long idScortaMagazzino) throws InternalUnexpectedException;
	  
	  public long eliminaScorte(List<Long> listIdScortaMagazzino, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, long idProcedimentoOggetto) throws InternalUnexpectedException, ApplicationException;

	  public List<DanniDTO> getListaDanniByProcedimentoOggetto(long idProcedimentoOggetto, int idProcedimentoAgricolo) throws InternalUnexpectedException;
	  
	  public List<DanniDTO> getListDanniByIdsProcedimentoOggetto (long idProcedimentoOggetto, long[] arrayIdDannoAtm, int idProcedimentoAgricoltura) throws InternalUnexpectedException;
	  
	  public long eliminaDanni(List<Long> listIdDannoAtm, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, long idProcedimentoOggetto)
			throws InternalUnexpectedException, ApplicationException;

	  public List<ScorteDTO> getScorteByIds(long[] arrayIdScortaMagazzino, long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public long inserisciDanni(List<DanniDTO> listDanniDTO, long idProcedimentoOggetto, Integer idDanno, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, int idProcedimentoAgricoltura)  throws InternalUnexpectedException;
	  
	  public int inserisciDanniConduzioni(DanniDTO danno, long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)  throws InternalUnexpectedException;

	  public UnitaMisuraDTO getUnitaMisuraByIdDanno(Integer idDanno) throws InternalUnexpectedException;
	  
	  public DannoDTO getDannoByIdDanno(int idDanno) throws InternalUnexpectedException;

	  public List<DanniDTO> getDanniByIdDannoAtm(long[] arrayIdDannoAtm, long idProcedimentoOggetto, int idProcedimentoAgricoltura)  throws InternalUnexpectedException;

	  public int modificaDanni(List<DanniDTO> listDanni, long idProcedimentoOggetto,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException;
	  
	  public int modificaDanniConduzioni(DanniDTO danno, long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato,
			  LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException;

	  public Map<Long, Long> getMapTipologiaScorteUnitaDiMisura() throws InternalUnexpectedException;

	  public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException;

	  public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, long[] arrayIdMacchina, int idProcedimentoAgricoltura) throws InternalUnexpectedException;
	  
	  public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException;
	  
	  public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long[] arrayIdMacchina, long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException;

	  public List<ScorteDTO> getListaScorteNonDanneggiateByProcedimentoOggetto(long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public List<PrestitiAgrariDTO> getListPrestitiAgrari(long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public List<PrestitiAgrariDTO> getListPrestitiAgrari(long idProcedimentoOggetto, long[] arrayIdPrestitiAgrari) throws InternalUnexpectedException;
		
	  public long inserisciPrestitoAgrario(PrestitiAgrariDTO prestito,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException;

	  public int eliminaPrestitiAgrari(List<Long> listIdPrestitiAgrari, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public int modificaPrestitiAgrari(long idProcedimentoOggetto, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, List<PrestitiAgrariDTO> listPrestitiAgrari) throws InternalUnexpectedException;

	  public List<FabbricatiDTO> getListFabbricati(long idProcedimentoOggetto, int idProcedimentoAgricolo) throws InternalUnexpectedException;

	  public FabbricatiDTO getFabbricato(long idProcedimentoOggetto, long idFabbricato, int idProcedimentoAgricolo) throws InternalUnexpectedException;

	  public Long getNDanniScorte(long idProcedimentoOggetto, long[] arrayIdScortaMagazzino) throws InternalUnexpectedException;

	  public List<FabbricatiDTO> getListFabbricatiNonDanneggiati(long idProcedimentoOggetto, long[] arrayIdFabbricato, int idProcedimentoAgricolo) throws InternalUnexpectedException;
	  
	  public SuperficiColtureRiepilogoDTO getSuperficiColtureRiepilogo(long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public List<SuperficiColtureDettaglioDTO> getListSuperficiColtureDettaglio(long idProcedimentoOggetto) throws InternalUnexpectedException;
	 
	  public SuperficiColtureDettaglioDTO getSuperficiColtureDettaglio(long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException;

	  public List<ControlloColturaDTO> getListControlloColtura(long idProcedimentoOggetto, long[] arrayIdSuperficieColtura) throws InternalUnexpectedException;
	  
	  public SuperficiColtureDettaglioPsrDTO getSuperficiColtureDettaglioPsrDTO(long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException;

	  public List<SuperficiColtureDettaglioParticellareDTO> getListDettaglioParticellareSuperficiColture(long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException;

	  public void modificaSuperficieColtura(long idProcedimentoOggetto,
			SuperficiColtureDettaglioPsrDTO superficieColturaDettaglioDTO,
			List<ControlloColturaDTO> listControlloColtura, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException;

	  public List<SuperficiColturePlvVegetaleDTO> getListSuperficiColturePlvVegetale(long idProcedimentoOggetto) throws InternalUnexpectedException;

	  public List<DecodificaDTO<String>> getListProvinciaConTerreniInConduzione(long idProcedimentoOggetto, String ID_REGIONE_PIEMONTE) throws InternalUnexpectedException;

	  public List<DecodificaDTO<String>> getListComuniPerProvinciaConTerreniInConduzione(long idProcedimentoOggetto,
			String istatProvincia) throws InternalUnexpectedException;

	  public List<DecodificaDTO<String>> getListSezioniPerComuneDanniSuperficiColture(long idProcedimentoOggetto,
			String istatComune) throws InternalUnexpectedException;

	  public List<ParticelleDanniDTO> getListConduzioniDannoEscludendoGiaSelezionate(long idProcedimentoOggetto, FiltroRicercaConduzioni filtroRicercaConduzioni, boolean piantagioniArboree) throws InternalUnexpectedException;
	  
	  public List<ParticelleDanniDTO> getListConduzioniDannoGiaSelezionate(long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato, boolean piantagioniArboree) throws InternalUnexpectedException;

	  public List<ParticelleDanniDTO> getListConduzioniDanno(long idProcedimentoOggetto, long idDannoAtm) throws InternalUnexpectedException;
	  
	  public List<ParticelleDanniDTO> getListConduzioniDanni(long idProcedimentoOggetto) throws InternalUnexpectedException;
	  
	  public BigDecimal getSumSuperficiCatastaliParticelle(long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato) throws InternalUnexpectedException;

	  public List<AllevamentiDTO> getListAllevamenti(long idProcedimentoOggetto) throws InternalUnexpectedException;
	  
	  public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(long idProcedimentoOggetto) throws InternalUnexpectedException;
	  
	  public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(long idProcedimentoOggetto, long[] arrayIdAllevamento) throws InternalUnexpectedException;
	  
	  public List<AllevamentiDTO> getListAllevamentiSingoli(long idProcedimentoOggetto, long[] arrayIdAllevamento) throws InternalUnexpectedException;

	  public AllevamentiDTO getDettaglioAllevamento(long idProcedimentoOggetto, long idCategoriaAnimale,
				String istatComune) throws InternalUnexpectedException;
	  
	  public List<AllevamentiDettaglioPlvDTO> getListDettaglioAllevamenti 
		(long idProcedimentoOggetto, long idCategoriaAnimale, String istatComune) throws InternalUnexpectedException;
	  
	  public List<DecodificaDTO<Integer>> getListProduzioniVendibili(
			  long idCategoriaAnimale) 
				throws InternalUnexpectedException;
	  
	  public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniCategorieAnimali
	  	(long idProcedimentoOggetto,long idCategoriaAnimale,String istatComune) 
				throws InternalUnexpectedException;
	  
	  public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniVendibiliGiaInserite
		(long idProcedimentoOggetto, long idCategoriaAnimale,String istatComune) 
				throws InternalUnexpectedException;
	  
		public List<ProduzioneCategoriaAnimaleDTO> getListProduzioni(long idProcedimentoOggetto,long idCategoriaAnimale,String istatComune) 
				throws InternalUnexpectedException;

		public void inserisciProduzioneZootecnicaEProduzioniVendibili(
				long idProcedimentoOggetto,
				AllevamentiDTO produzioneZootecnica, 
				List<ProduzioneCategoriaAnimaleDTO> listProduzioniVendibili, 
				long idUtenteLogin,
				LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
					throws InternalUnexpectedException;

		public List<DecodificaDTO<Integer>> getListUnitaMisuraProduzioniVendibili(long idCategoriaAnimale) throws InternalUnexpectedException;
		
		public BigDecimal getPlvZootecnicaUfProdotte(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public BigDecimal getPlvZootecnicaUfNecessarie(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public BigDecimal getPlvZootecnicaUba(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public BigDecimal getPlvZootecnicaSau(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public List<AllevamentiDettaglioPlvDTO> getListPlvZootecnicaDettaglioAllevamenti(long idProcedimentoOggetto) throws InternalUnexpectedException;

		public List<AllevamentiDTO> getListAllevamentiByIdDannoAtm(long idProcedimentoOggetto,
				long[] arrayIdAllevamento) throws InternalUnexpectedException;
		
		public List<DecodificaDTO<Long>> getListDanniDecodificaDTO(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public long getNInterventiAssociatiDanni(long idProcedimentoOggetto, long[] arrayIdDannoAtm) 
				throws InternalUnexpectedException;
		
		public long getNInterventiAssociatiDanniScorte(long idProcedimentoOggetto, List<Long> listIdScortaMagazzino) 
				throws InternalUnexpectedException;

		public ColtureAziendaliDTO getRiepilogoColtureAziendali(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public ColtureAziendaliDTO getRiepilogoColtureAziendali(long idProcedimentoOggetto, long idProcedimento) throws InternalUnexpectedException;

		public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(long idProcedimentoOggetto, long[] arrayIdSuperficieColture) throws InternalUnexpectedException;

		public void updateColtureAziendali(long idProcedimentoOggetto,
				LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
				List<ColtureAziendaliDettaglioDTO> listColtureAziendaliModificate) throws InternalUnexpectedException;

		public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto)  throws InternalUnexpectedException;
		
		public AssicurazioniColtureDTO getRiepilogoAssicurazioniColture(long idProcedimentoOggetto) throws InternalUnexpectedException;
		
		public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto, long[] idAssicurazioniColture)  throws InternalUnexpectedException;

		public int eliminaAssicurazioniColture(long idProcedimentoOggetto, long[] arrayIdAssicurazioniColture) throws InternalUnexpectedException;

		public long inserisciAssicurazioniColture(long idProcedimentoOggetto,
				AssicurazioniColtureDTO assicurazioniColture, LogOperationOggettoQuadroDTO logOperationOggettoQuadro) throws InternalUnexpectedException;

		public List<DecodificaDTO<Integer>> getListConsorzi(String idProvincia) throws InternalUnexpectedException;

		public long modificaAssicurazioniColture(long idProcedimentoOggetto,
				AssicurazioniColtureDTO assicurazioniColture, LogOperationOggettoQuadroDTO logOperationOggettoQuadro)  throws InternalUnexpectedException;;
				
		public List<DanniColtureDTO> getListDanniColture(long idProcedimentoOggetto, long idProcedimento) 
				throws InternalUnexpectedException;

		public Long getNColtureDanneggiate(long idProcedimentoOggetto) throws InternalUnexpectedException;
					  			
		public void deleteRTipoDocumentiRichiesti(long idDocumentiRichiesti, long idTipoDocumentiRichiesti) throws InternalUnexpectedException;
			 		  
		public List<SezioneDocumentiRichiestiDTO> getListDocumentiRichiestiDaVisualizzare(long idProcedimentoOggetto, Boolean isVisualizzazione) throws InternalUnexpectedException;
		  
		public List<DocumentiRichiestiDTO> getDocumentiRichiesti(long idProcedimentoOggetto) throws InternalUnexpectedException;

		public int aggiornaDocumentiRichiesti(long idProcedimentoOggetto, List<String> requestList, String HValue)	throws InternalUnexpectedException;

		public List<String> getListTestoSezione() throws InternalUnexpectedException;

		public ReferenteProgettoDTO getReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto)  throws InternalUnexpectedException;

		public void insertOrUpdateReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto, String nome,
				String cognome, String codiceFiscale, String comune, String cap,
				String telefono, String cellulare, String email, LogOperationOggettoQuadroDTO logOperationOggQuadro) throws InternalUnexpectedException;

		public boolean getGestisciUnitaMisuraByIdDanno(int idDanno) throws InternalUnexpectedException;

}
