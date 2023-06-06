package it.csi.nembo.nembopratiche.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
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
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.prestitiagrari.PrestitiAgrariDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDecodificaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioParticellareDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioPsrDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColturePlvVegetaleDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureRiepilogoDTO;
import it.csi.nembo.nembopratiche.exception.ApplicationException;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.integration.QuadroNemboDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;

@Stateless()
@EJB(name = "java:app/QuadroNembo", beanInterface = IQuadroNemboEJB.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class QuadroNemboEJB extends NemboAbstractEJB<QuadroNemboDAO> implements IQuadroNemboEJB
{
  @SuppressWarnings("unused")
  private SessionContext                       sessionContext;
  
  @Resource
  private void setSessionContext(SessionContext sessionContext)
  {
    this.sessionContext = sessionContext;
  }
  
  @Override
  public List<ScorteDTO> getListaScorteByProcedimentoOggetto(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
  	List<ScorteDTO> listaScorte = null;
  	listaScorte = dao.getListScorteByProcedimentoOggetto(idProcedimentoOggetto);
  	if(listaScorte == null){
  		listaScorte = new ArrayList<ScorteDTO>();
  	}
  	return listaScorte;
  }
  
	@Override
	public List<ScorteDTO> getListaScorteNonDanneggiateByProcedimentoOggetto(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
	  	List<ScorteDTO> listaScorte = null;
	  	listaScorte = dao.getListScorteNonDanneggiateByProcedimentoOggetto(idProcedimentoOggetto);
	  	if(listaScorte == null){
	  		listaScorte = new ArrayList<ScorteDTO>();
	  	}
	  	return listaScorte;
	}

  @Override
  public long getIdStatoProcedimento(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
  	long idStatoProcedimento = dao.getIdStatoProcedimento(idProcedimentoOggetto);
  	return idStatoProcedimento;
  }

  @Override
  public List<DecodificaDTO<Long>> getElencoTipologieScorte() throws InternalUnexpectedException
  {
  	return dao.getElencoTipologieScorte();
  }

  @Override
  public List<DecodificaDTO<Long>> getListUnitaDiMisura() throws InternalUnexpectedException
  {
  	return dao.getListUnitaDiMisura();
  }
  

  @Override
  public Long getUnitaMisuraByScorta(long idScorta) throws InternalUnexpectedException
  {
  	return dao.getUnitaMisuraByScorta(idScorta);
  }

  @Override
  public long getIdScorteAltro() throws InternalUnexpectedException
  {
  	return dao.getIdScorteAltro();
  }

  @Override
  public long inserisciScorte(long idProcedimentoOggetto, long idScorta, BigDecimal quantita, Long idUnitaMisura, String descrizione,LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException
  {
  	long idScortaMagazzino = dao.inserisciScorte(idProcedimentoOggetto,idScorta,quantita,idUnitaMisura,descrizione);
  	logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
  	return idScortaMagazzino;
  }

	@Override
	public long modificaScorte(List<ScorteDTO> listScorte, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
			long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		long returnValue;
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		List<Long> arrayIdScortaMagazzino = new ArrayList<Long>();
		for(int i=0 ; i< listScorte.size(); i++)
		{
			ScorteDTO scorta = listScorte.get(i);
			arrayIdScortaMagazzino.add(scorta.getIdScortaMagazzino());
		}
		long nInterventi = dao.getNInterventiAssociatiDanniScorte(idProcedimentoOggetto, arrayIdScortaMagazzino);
		if(nInterventi > 0L)
		{
			returnValue = NemboConstants.ERRORI.ELIMINAZIONE_SCORTE_CON_DANNI_CON_INTERVENTI;
		}
		else
		{
			dao.eliminaDanniAssociatiAlleScorteMagazzinoModificateORimosse(arrayIdScortaMagazzino, idProcedimentoOggetto);
			for(ScorteDTO scorta : listScorte)
			{
				dao.modificaScorta(scorta, idProcedimentoOggetto);
			}
			logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
			returnValue =  listScorte.size();
		}
		return returnValue;
	}

  @Override
  public ScorteDTO getScortaByIdScortaMagazzino(long idScortaMagazzino) throws InternalUnexpectedException
  {
  	return dao.getScortaByIdScortaMagazzino(idScortaMagazzino);
  }
  

	@Override
	public long eliminaScorte(List<Long> listIdScortaMagazzino,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, long idProcedimentoOggetto)
			throws InternalUnexpectedException, ApplicationException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		long nInterventi = dao.getNInterventiAssociatiDanniScorte(idProcedimentoOggetto, listIdScortaMagazzino);
		if(nInterventi == 0L)
		{
			long nScorteMagazzinoRimosse = dao.eliminaScorteMagazzino(listIdScortaMagazzino, idProcedimentoOggetto);
			dao.eliminaDanniAssociatiAlleScorteMagazzinoModificateORimosse(listIdScortaMagazzino,idProcedimentoOggetto);
			logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
			return nScorteMagazzinoRimosse;
		}
		else
		{
			return NemboConstants.ERRORI.ELIMINAZIONE_SCORTE_CON_DANNI_CON_INTERVENTI;
		}
	}

	@Override
	public List<DanniDTO> getListaDanniByProcedimentoOggetto(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{	
		List<DanniDTO> listaDanniByProcedimentoOggetto = dao.getListDanniByProcedimentoOggettoAndArrayIdDannoAtm(null, idProcedimentoOggetto, idProcedimentoAgricoltura);
		if(listaDanniByProcedimentoOggetto == null)
		{
			listaDanniByProcedimentoOggetto = new ArrayList<DanniDTO>();
		}
		return listaDanniByProcedimentoOggetto;
	}
	
	@Override
	public long eliminaDanni(List<Long> listIdDannoAtm, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
			long idProcedimentoOggetto) throws InternalUnexpectedException, ApplicationException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		long[] arrayIdDannoAtm = new long[listIdDannoAtm.size()];
		for(int i=0; i < listIdDannoAtm.size(); i++)
		{
			arrayIdDannoAtm[i] = listIdDannoAtm.get(i);
		}
		long nInterventiDanni = dao.getNInterventiAssociatiDanni(idProcedimentoOggetto,arrayIdDannoAtm);
		if(nInterventiDanni > 0L)
		{
			return NemboConstants.ERRORI.ELIMINAZIONE_DANNI_CON_INTERVENTI;
		}
		List<DanniDTO> danniConduzioni = dao.getListDanniConduzioni(idProcedimentoOggetto,arrayIdDannoAtm);
		List<Long> listIdDannoAtmConduzioni = new ArrayList<Long>();
		if(danniConduzioni != null)
		{
			for(DanniDTO d : danniConduzioni)
			{
				listIdDannoAtmConduzioni.add(d.getIdDannoAtm());
			}
			dao.eliminaDanniConduzioniFromTParticellaDanneggiata(idProcedimentoOggetto, listIdDannoAtmConduzioni);
		}
		long nDanniRimossi = dao.eliminaDanni(listIdDannoAtm,idProcedimentoOggetto);
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return nDanniRimossi;
	}
	
	
	@Override
	public List<ScorteDTO> getScorteByIds(long[] arrayIdScortaMagazzino, long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		List<ScorteDTO> lista = null;
		lista = dao.getListScorteByIds(arrayIdScortaMagazzino,idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<ScorteDTO>();
		}
		return lista;
	}
	
	
	@Override
	public long inserisciDanni(List<DanniDTO> listDanniDTO, long idProcedimentoOggetto, Integer idDanno, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		long nDanni = 0L;
		String nomeTabella = dao.getNomeTabellaByIdDanno(idDanno);
		if(nomeTabella != null)
		{
			nDanni = dao.getNumDanniGiaEsistenti(listDanniDTO,idProcedimentoOggetto,idDanno);
		}
		boolean isUtenteAutorizzatoInserimentoDanniIsProprietarioDegliElementi = dao.isUtenteAutorizzatoInserimentoDanni(listDanniDTO,idProcedimentoOggetto,idDanno,idProcedimentoAgricoltura);
		if(nDanni == 0 && isUtenteAutorizzatoInserimentoDanniIsProprietarioDegliElementi)
		{
			long n;
			for(DanniDTO danno : listDanniDTO)
			{
				n=dao.inserisciDanno(danno, idDanno);
			}
			logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
			return listDanniDTO.size();
		}
		else{
			return 0;
		}
	}
	
	
	@Override
	public UnitaMisuraDTO getUnitaMisuraByIdDanno(Integer idDanno) throws InternalUnexpectedException
	{
		return dao.getUnitaMisuraByIdDanno(idDanno);
	}

	@Override
	public DannoDTO getDannoByIdDanno(int idDanno) throws InternalUnexpectedException
	{
		return dao.getDannoByIdDanno(idDanno);
	}
	
	public boolean getGestisciUnitaMisuraByIdDanno(int idDanno) throws InternalUnexpectedException
	{
		boolean isGestisciUnitaMisura = false;
		DannoDTO dDanno = dao.getDannoByIdDanno(idDanno);
		if(dDanno.getIdUnitaMisura() == null && dDanno.getNomeTabella() == null)
		{
			isGestisciUnitaMisura = true;
		}
		else
		{
			isGestisciUnitaMisura = false;
		}
		return isGestisciUnitaMisura;
	}

	@Override
	public List<DanniDTO> getDanniByIdDannoAtm(long[] arrayIdDannoAtm, long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		List<DanniDTO> listDanniByIdsDannoAtm = dao.getListDanniByProcedimentoOggettoAndArrayIdDannoAtm(arrayIdDannoAtm, idProcedimentoOggetto,  idProcedimentoAgricoltura);
		if(listDanniByIdsDannoAtm==null)
		{
			listDanniByIdsDannoAtm = new ArrayList<DanniDTO>();
		}
		return listDanniByIdsDannoAtm;
	}
	
	
	
	@Override
	public int modificaDanniConduzioni(DanniDTO danno, long idProcedimentoOggetto,
			long[] arrayIdUtilizzoDichiarato, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
			throws InternalUnexpectedException
	{
		List<Long> listaIdDannoAtm = new ArrayList<Long>();
		listaIdDannoAtm.add(danno.getIdDannoAtm());
		dao.eliminaDanniConduzioniFromTParticellaDanneggiata(idProcedimentoOggetto,listaIdDannoAtm);
		for(long idUtilizzoDichiarato : arrayIdUtilizzoDichiarato)
		{
			dao.inserisciConduzioneDanneggiata(idProcedimentoOggetto, danno, idUtilizzoDichiarato);
		}
		dao.modificaDanno(danno, idProcedimentoOggetto);
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return 1;
	}

	@Override
	public int modificaDanni(List<DanniDTO> listDanni, long idProcedimentoOggetto,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException
	{
		for (DanniDTO danno : listDanni)
		{
			dao.modificaDanno(danno, idProcedimentoOggetto);
		}
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return listDanni.size();
	}

	@Override
	public Map<Long, Long> getMapTipologiaScorteUnitaDiMisura() throws InternalUnexpectedException
	{
		
		 List<ScorteDecodificaDTO> listDecodificaScorte = dao.getListDecodicaScorta();
		 Map<Long,Long> mappa = new HashMap<Long,Long>();
		 for(ScorteDecodificaDTO sdd : listDecodificaScorte)
		 {
			 mappa.put(sdd.getIdScorta(),sdd.getIdUnitaMisura());
		 }
		 return mappa;
	}


	@Override
	public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		List<MotoriAgricoliDTO> listMotoriAgricoli = dao.getListMotoriAgricoli(idProcedimentoOggetto, idProcedimentoAgricoltura);
		if(listMotoriAgricoli == null)
		{
			listMotoriAgricoli=new ArrayList<MotoriAgricoliDTO>();
		}
		return listMotoriAgricoli;
	}
	
	@Override
	public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, long[] arrayIdMacchina, int idProcedimentoAgricoltura)
			throws InternalUnexpectedException
	{
		List<MotoriAgricoliDTO> listMotoriAgricoli = dao.getListMotoriAgricoli(idProcedimentoOggetto,arrayIdMacchina, idProcedimentoAgricoltura);
		if(listMotoriAgricoli == null)
		{
			listMotoriAgricoli=new ArrayList<MotoriAgricoliDTO>();
		}
		return listMotoriAgricoli;
	}


	@Override
	public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		List<MotoriAgricoliDTO> listMotoriAgricoli = dao.getListMotoriAgricoliNonDanneggiati(idProcedimentoOggetto, idProcedimentoAgricoltura);
		if(listMotoriAgricoli == null)
		{
			listMotoriAgricoli=new ArrayList<MotoriAgricoliDTO>();
		}
		return listMotoriAgricoli;
	}


	@Override
	public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long[] arrayIdMacchina, long idProcedimentoOggetto, int idProcedimentoAgricoltura)
			throws InternalUnexpectedException
	{
		return dao.getListMotoriAgricoliNonDanneggiati(arrayIdMacchina, idProcedimentoOggetto, idProcedimentoAgricoltura);
	}

	@Override
	public List<DanniDTO> getListDanniByIdsProcedimentoOggetto(long idProcedimentoOggetto, long[] arrayIdDannoAtm, int idProcedimentoAgricoltura)
			throws InternalUnexpectedException
	{
		List<DanniDTO> listDanni = dao.getListDanniByProcedimentoOggettoAndArrayIdDannoAtm(arrayIdDannoAtm, idProcedimentoOggetto, idProcedimentoAgricoltura);
		if(listDanni==null)
		{
			listDanni=new ArrayList<DanniDTO>();	
		}
		return listDanni;
	}

	@Override
	public List<PrestitiAgrariDTO> getListPrestitiAgrari(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return getListPrestitiAgrari(idProcedimentoOggetto,null);
	}
	
	@Override
	public List<PrestitiAgrariDTO> getListPrestitiAgrari(long idProcedimentoOggetto, long[] arrayIdPrestitiAgrari) throws InternalUnexpectedException
	{
		List<PrestitiAgrariDTO> listPrestitiAgrari = dao.getListPrestitiAgrari(idProcedimentoOggetto,arrayIdPrestitiAgrari);
		if(listPrestitiAgrari == null)
		{
			listPrestitiAgrari = new ArrayList<PrestitiAgrariDTO>();
		}
		return listPrestitiAgrari;
	}

	@Override
	public long inserisciPrestitoAgrario(PrestitiAgrariDTO prestito,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException
	{
		long idPrestitiAgrari = dao.inserisciPrestitoAgrario(prestito);
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return idPrestitiAgrari;
	}

	@Override
	public int eliminaPrestitiAgrari(List<Long> listIdPrestitiAgrari,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		int nPrestitiEliminati = dao.eliminaPrestitiAgrari(listIdPrestitiAgrari,idProcedimentoOggetto);
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return nPrestitiEliminati;
	}

	@Override
	public int modificaPrestitiAgrari(long idProcedimentoOggetto, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO, List<PrestitiAgrariDTO> listPrestitiAgrari)
			throws InternalUnexpectedException
	{
		int nModificati=0;
		for(PrestitiAgrariDTO prestito : listPrestitiAgrari)
		{
			nModificati += dao.modificaPrestitiAgrari(idProcedimentoOggetto,prestito);
		}
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		return nModificati;
	}

	@Override
	public List<FabbricatiDTO> getListFabbricati(long idProcedimentoOggetto, int idProcedimentoAgricolo) throws InternalUnexpectedException
	{
		List<FabbricatiDTO> lista = dao.getListFabbricati(idProcedimentoOggetto, idProcedimentoAgricolo );
		if(lista == null)
		{
			lista = new ArrayList<FabbricatiDTO>();
		}
		return lista;
	}

	@Override
	public FabbricatiDTO getFabbricato(long idProcedimentoOggetto, long idFabbricato, int idProcedimentoAgricolo) throws InternalUnexpectedException
	{
		return dao.getFabbricato(idProcedimentoOggetto, idFabbricato, idProcedimentoAgricolo);
	}

	@Override
	public Long getNDanniScorte(long idProcedimentoOggetto, long[] arrayIdScortaMagazzino)
			throws InternalUnexpectedException
	{
		Long nDanniScorte = dao.getNDanniScorte(idProcedimentoOggetto,arrayIdScortaMagazzino);
		return nDanniScorte;
	}

	@Override
	public List<FabbricatiDTO> getListFabbricatiNonDanneggiati(long idProcedimentoOggetto, long[] arrayIdFabbricato, int idProcedimentoAgricolo) throws InternalUnexpectedException
	{
		List<FabbricatiDTO> listFabbricatiNonDanneggiati = null;
		listFabbricatiNonDanneggiati = dao.getListFabbricatiNonDanneggiati(idProcedimentoOggetto, arrayIdFabbricato, idProcedimentoAgricolo);
		if(listFabbricatiNonDanneggiati == null)
		{
			listFabbricatiNonDanneggiati = new ArrayList<FabbricatiDTO>();
		}
		return listFabbricatiNonDanneggiati;
	}

	@Override
	public SuperficiColtureRiepilogoDTO getSuperficiColtureRiepilogo(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		SuperficiColtureRiepilogoDTO superficiColtureRiepilogoDTO = dao.getSuperficiColtureRiepilogo(idProcedimentoOggetto);
		return superficiColtureRiepilogoDTO;
	}

	@Override
	public List<SuperficiColtureDettaglioDTO> getListSuperficiColtureDettaglio(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<SuperficiColtureDettaglioDTO> listSuperficiColtureDettaglio = dao.getListSuperficiColtureDettaglio(idProcedimentoOggetto);
		if(listSuperficiColtureDettaglio == null)
		{
			listSuperficiColtureDettaglio = new ArrayList<SuperficiColtureDettaglioDTO>();
		}
		return listSuperficiColtureDettaglio;
	}
	
	@Override
	public SuperficiColtureDettaglioDTO getSuperficiColtureDettaglio(long idProcedimentoOggetto, long idSuperficieColtura)
			throws InternalUnexpectedException
	{
		return dao.getSuperficiColtureDettaglio(idProcedimentoOggetto,idSuperficieColtura);
	}

	@Override
	public List<ControlloColturaDTO> getListControlloColtura(long idProcedimentoOggetto, long[] arrayIdSuperficieColtura)
			throws InternalUnexpectedException
	{
		List<ControlloColturaDTO> listControlloColtura = dao.getListControlloColtura(idProcedimentoOggetto, arrayIdSuperficieColtura);
		if(listControlloColtura == null)
		{
			listControlloColtura = new ArrayList<ControlloColturaDTO>();
		}
		return listControlloColtura;
	}

	@Override
	public SuperficiColtureDettaglioPsrDTO getSuperficiColtureDettaglioPsrDTO(long idProcedimentoOggetto,
			long idSuperficieColtura) throws InternalUnexpectedException
	{
		return dao.getSuperficiColtureDettaglioPsrDTO(idProcedimentoOggetto, idSuperficieColtura);
	}

	@Override
	public List<SuperficiColtureDettaglioParticellareDTO> getListDettaglioParticellareSuperficiColture(
			long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException
	{
		List<SuperficiColtureDettaglioParticellareDTO> list = dao.getListDettaglioParticellareSuperficiColture(idProcedimentoOggetto,idSuperficieColtura);
		if(list == null)
		{
			list = new ArrayList<SuperficiColtureDettaglioParticellareDTO>();
		}
		return list;
	}

	@Override
	public void modificaSuperficieColtura(long idProcedimentoOggetto,
			SuperficiColtureDettaglioPsrDTO superficieColturaDettaglioDTO,
			List<ControlloColturaDTO> listControlloColtura, LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		List<Long> listIdSuperficieColtura = new ArrayList<Long>();
		for(ControlloColturaDTO controlloColtura : listControlloColtura)
		{	
			listIdSuperficieColtura.add(controlloColtura.getIdSuperficieColtura());
		}
		dao.eliminaControlloColtura(idProcedimentoOggetto,superficieColturaDettaglioDTO.getIdSuperficieColtura());
		dao.updateSuperficieColtura(idProcedimentoOggetto,superficieColturaDettaglioDTO);
		for(ControlloColturaDTO controlloColtura : listControlloColtura)
		{
			dao.inserisciControlloColtura(idProcedimentoOggetto,superficieColturaDettaglioDTO.getIdSuperficieColtura(),controlloColtura);
		}
		logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
	}

	@Override
	public List<SuperficiColturePlvVegetaleDTO> getListSuperficiColturePlvVegetale(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<SuperficiColturePlvVegetaleDTO> lista = dao.getListSuperficiColturePlvVegetale(idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<SuperficiColturePlvVegetaleDTO>();
		}
		return lista;
	}

	@Override
	public List<DecodificaDTO<String>> getListProvinciaConTerreniInConduzione(long idProcedimentoOggetto,
			String ID_REGIONE_PIEMONTE) throws InternalUnexpectedException
	{
		List<DecodificaDTO<String>> lista = dao.getListProvinciaConTerreniInConduzione(idProcedimentoOggetto,ID_REGIONE_PIEMONTE);
		if(lista == null)
		{
			lista = new ArrayList<DecodificaDTO<String>>();
		}
		return lista;				
	}
	
	@Override
	public List<DecodificaDTO<String>> getListComuniPerProvinciaConTerreniInConduzione(long idProcedimentoOggetto,
			String istatProvincia) throws InternalUnexpectedException
	{
		List<DecodificaDTO<String>> lista = dao.getListComuniPerProvinciaConTerreniInConduzioneDanniSuperficiColture(idProcedimentoOggetto,istatProvincia);
		if(lista == null)
		{
			lista = new ArrayList<DecodificaDTO<String>>();
		}
		return lista;
	}

	@Override
	public List<DecodificaDTO<String>> getListSezioniPerComuneDanniSuperficiColture(long idProcedimentoOggetto,
			String istatComune) throws InternalUnexpectedException
	{
		List<DecodificaDTO<String>> lista = dao.getListSezioniPerComuneConTerreniInConduzioneDanniSuperficiColture(
				idProcedimentoOggetto,istatComune);
		if(lista == null)
		{
			lista = new ArrayList<DecodificaDTO<String>>();
		}
		return lista;
	}

	@Override
	public List<ParticelleDanniDTO> getListConduzioniDannoEscludendoGiaSelezionate(
			long idProcedimentoOggetto, 
			FiltroRicercaConduzioni filtroRicercaConduzioni,
			boolean piantagioniArboree)
			throws InternalUnexpectedException
	{
		List<ParticelleDanniDTO> lista = dao.getListConduzioniDannoEscludendoGiaSelezionate(
				idProcedimentoOggetto, 
				filtroRicercaConduzioni, 
				piantagioniArboree);
		if(lista == null)
		{
			lista = new ArrayList<ParticelleDanniDTO>();
		}
		lista.size();
		return lista;
	}

	@Override
	public List<ParticelleDanniDTO> getListConduzioniDannoGiaSelezionate(
			long idProcedimentoOggetto,
			long[] arrayIdUtilizzoDichiarato,
			boolean piantagioniArboree
			) throws InternalUnexpectedException
	{
		if(arrayIdUtilizzoDichiarato == null)
		{
			return new ArrayList<ParticelleDanniDTO>();
		}
		List<ParticelleDanniDTO> lista = 
				dao.getListConduzioniDannoSelezionate(
						idProcedimentoOggetto, 
						arrayIdUtilizzoDichiarato, 
						piantagioniArboree);
		if(lista == null)
		{
			lista = new ArrayList<ParticelleDanniDTO>();
		}
		lista.size();
		return lista;
	}
	
	@Override
	public List<ParticelleDanniDTO> getListConduzioniDanno(long idProcedimentoOggetto, long idDannoAtm)
			throws InternalUnexpectedException
	{
		List<ParticelleDanniDTO> lista = dao.getListConduzioniDanno(idProcedimentoOggetto,idDannoAtm);
		if(lista == null)
		{
			lista = new ArrayList<ParticelleDanniDTO>();
		}
		return lista;
	}
	
	@Override
	public List<ParticelleDanniDTO> getListConduzioniDanni(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<ParticelleDanniDTO> lista = dao.getListConduzioniDanno(idProcedimentoOggetto,null);
		if(lista == null)
		{
			lista = new ArrayList<ParticelleDanniDTO>();
		}
		return lista;
	}

	@Override
	public BigDecimal getSumSuperficiCatastaliParticelle(long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato)
			throws InternalUnexpectedException
	{
		if(arrayIdUtilizzoDichiarato == null || arrayIdUtilizzoDichiarato.length==0)
		{
			return new BigDecimal("0.0");
		}
		return dao.getSumSuperficiCatastaliParticelle(idProcedimentoOggetto,arrayIdUtilizzoDichiarato);
	}

	@Override
	public int inserisciDanniConduzioni(
			DanniDTO danno, 
			long idProcedimentoOggetto,
			long[] arrayIdUtilizzoDichiarato,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO) throws InternalUnexpectedException
	{
		
		boolean piantagioniArboree = (danno.getIdDanno() == NemboConstants.DANNI.PIANTAGIONI_ARBOREE);
		List<Integer> listDanniEquivalentiConduzioni = QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.PIANTAGIONI_ARBOREE);
		
		if(listDanniEquivalentiConduzioni.contains(danno.getIdDanno()))
		{
			List<ParticelleDanniDTO> listConduzioni = dao.getListConduzioniDannoSelezionate(idProcedimentoOggetto, arrayIdUtilizzoDichiarato, piantagioniArboree);
			if(listConduzioni != null && listConduzioni.size() > 0)
			{
				long[] arrayIdUtilizzoDichiaratoDaInserire;
				if(listConduzioni.size() == arrayIdUtilizzoDichiarato.length)
				{
					arrayIdUtilizzoDichiaratoDaInserire = arrayIdUtilizzoDichiarato;
				}
				else
				{
					arrayIdUtilizzoDichiaratoDaInserire = new long[listConduzioni.size()];
					int i = 0;
					for(ParticelleDanniDTO conduzione : listConduzioni)
					{
						arrayIdUtilizzoDichiaratoDaInserire[i++] = conduzione.getIdUtilizzoDichiarato();
					}
				}
				long idDannoAtm = dao.inserisciDanno(danno, danno.getIdDanno());
				danno.setIdDannoAtm(idDannoAtm);
				for(long idUtilizzoDichiarato : arrayIdUtilizzoDichiaratoDaInserire)
				{
					dao.inserisciConduzioneDanneggiata(idProcedimentoOggetto,danno,idUtilizzoDichiarato);
				}
				logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
			}
		}
		return 0;
	}

	@Override
	public List<AllevamentiDTO> getListAllevamenti(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListAllevamenti(idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDTO>();
		}
		return lista;
	}

	@Override
	public AllevamentiDTO getDettaglioAllevamento(long idProcedimentoOggetto, long idCategoriaAnimale,
			String istatComune) throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListRiepilogoAllevamenti(idProcedimentoOggetto, idCategoriaAnimale, istatComune, true);
		if(lista != null)
		{
			return lista.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<AllevamentiDettaglioPlvDTO> getListDettaglioAllevamenti(long idProcedimentoOggetto,
			long idCategoriaAnimale, String istatComune) throws InternalUnexpectedException
	{
		List<AllevamentiDettaglioPlvDTO> lista = dao.getListDettaglioAllevamenti(idProcedimentoOggetto, idCategoriaAnimale, istatComune);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDettaglioPlvDTO>();
		}
		return lista;
	}
	
	@Override
	public List<DecodificaDTO<Integer>> getListProduzioniVendibili(long idCategoriaAnimale) throws InternalUnexpectedException
	{
		return dao.getListProduzioniVendibili(idCategoriaAnimale);
	}
	
	@Override
	public List<DecodificaDTO<Integer>> getListUnitaMisuraProduzioniVendibili(long idCategoriaAnimale)
			throws InternalUnexpectedException
	{
		return dao.getListUnitaMisuraProduzioniVendibili(idCategoriaAnimale);
	}

	@Override
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniCategorieAnimali(long idProcedimentoOggetto,
			long idCategoriaAnimale, String istatComune) throws InternalUnexpectedException
	{
		List<ProduzioneCategoriaAnimaleDTO> lista = dao.getListProduzioniCategorieAnimali(idProcedimentoOggetto, idCategoriaAnimale, istatComune);
		if(lista == null)
		{
			lista = new ArrayList<ProduzioneCategoriaAnimaleDTO>();
		}
		return lista;
	}

	@Override
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniVendibiliGiaInserite(long idProcedimentoOggetto,
			long idCategoriaAnimale, String istatComune) throws InternalUnexpectedException
	{
		List<ProduzioneCategoriaAnimaleDTO> lista = dao.getListProduzioniVendibiliGiaInserite(idProcedimentoOggetto, idCategoriaAnimale, istatComune);
		if(lista == null)
		{
			lista = new ArrayList<ProduzioneCategoriaAnimaleDTO>();
		}
		return lista;
	}

	@Override
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioni(long idProcedimentoOggetto, long idCategoriaAnimale,
			String istatComune) throws InternalUnexpectedException
	{
		List<ProduzioneCategoriaAnimaleDTO> lista = dao.getListProduzioni(idProcedimentoOggetto, idCategoriaAnimale, istatComune);
		if(lista == null)
		{
			lista = new ArrayList<ProduzioneCategoriaAnimaleDTO>();
		}
		return lista;
	}

	@Override
	public void inserisciProduzioneZootecnicaEProduzioniVendibili(
			long idProcedimentoOggetto,
			AllevamentiDTO produzioneZootecnica, 
			List<ProduzioneCategoriaAnimaleDTO> listProduzioniVendibili,
			long idUtenteLogin,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO)
			throws InternalUnexpectedException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		List<AllevamentiDTO> listAllevamenti = dao.getListRiepilogoAllevamenti(idProcedimentoOggetto, produzioneZootecnica.getIdCategoriaAnimale(),produzioneZootecnica.getIstatComune(),true);
		if(listAllevamenti != null && listAllevamenti.size() > 0)
		{
			AllevamentiDTO allevamento = listAllevamenti.get(0);
			long idProduzioneZootecnica;
			if(allevamento.getIdProduzioneZootecnica() == null)
			{
				idProduzioneZootecnica = dao.inserisciProduzioneZootecnica(idProcedimentoOggetto,produzioneZootecnica,idUtenteLogin);
			}
			else
			{
				idProduzioneZootecnica = allevamento.getIdProduzioneZootecnica();
				produzioneZootecnica.setIdProduzioneZootecnica(idProduzioneZootecnica);
				dao.modificaProduzioneZootecnica(idProcedimentoOggetto,produzioneZootecnica,idUtenteLogin);
				dao.eliminaProduzioniVendibili(idProduzioneZootecnica);
			}
			if(listProduzioniVendibili != null && listProduzioniVendibili.size()>0)
			{
				for(ProduzioneCategoriaAnimaleDTO produzione : listProduzioniVendibili)
				{
					dao.inserisciProduzioneVendibile(idProcedimentoOggetto,produzione,idProduzioneZootecnica);
				}
			}
			logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		}
	}

	@Override
	public BigDecimal getPlvZootecnicaUfProdotte(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return dao.getPlvZootecnicaUfProdotte(idProcedimentoOggetto);
	}

	@Override
	public BigDecimal getPlvZootecnicaUfNecessarie(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return dao.getPlvZootecnicaUfNecessarie(idProcedimentoOggetto);
	}

	
	@Override
	public BigDecimal getPlvZootecnicaUba(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return dao.getPlvZootecnicaUba(idProcedimentoOggetto);
	}
	
	@Override
	public BigDecimal getPlvZootecnicaSau(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return dao.getPlvZootecnicaSau(idProcedimentoOggetto);
	}

	@Override
	public List<AllevamentiDettaglioPlvDTO> getListPlvZootecnicaDettaglioAllevamenti(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<AllevamentiDettaglioPlvDTO> lista = dao.getListPlvZootecnicaDettaglioAllevamenti(idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDettaglioPlvDTO>();
		}
		return lista;
	}

	@Override
	public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto,null);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDTO>();
		}
		return lista;		
	}
	

	@Override
	public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(long idProcedimentoOggetto,
			long[] arrayIdAllevamento) throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListAllevamentiSingoliNonDanneggiati(idProcedimentoOggetto, arrayIdAllevamento);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDTO>();
		}
		return lista;
	}

	@Override
	public List<AllevamentiDTO> getListAllevamentiSingoli(long idProcedimentoOggetto, long[] arrayIdAllevamento)
			throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListAllevamentiSingoli(idProcedimentoOggetto, arrayIdAllevamento,false);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDTO>();
		}
		return lista;		
	}

	@Override
	public List<AllevamentiDTO> getListAllevamentiByIdDannoAtm(long idProcedimentoOggetto,
			long[] arrayIdDannoAtm) throws InternalUnexpectedException
	{
		List<AllevamentiDTO> lista = dao.getListAllevamentiByIdDannoAtm(idProcedimentoOggetto, arrayIdDannoAtm);
		if(lista == null)
		{
			lista = new ArrayList<AllevamentiDTO>();
		}
		return lista;			
		
	}

	@Override
	public List<DecodificaDTO<Long>> getListDanniDecodificaDTO(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<DecodificaDTO<Long>> lista = dao.getListDanniDecodificaDTO(idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<DecodificaDTO<Long>>();
		}
		return lista;
	}

	@Override
	public long getNInterventiAssociatiDanni(long idProcedimentoOggetto, long[] arrayIdDannoAtm)
			throws InternalUnexpectedException
	{
		return dao.getNInterventiAssociatiDanni(idProcedimentoOggetto, arrayIdDannoAtm);
	}

	@Override
	public long getNInterventiAssociatiDanniScorte(long idProcedimentoOggetto, List<Long> listIdScortaMagazzino)
			throws InternalUnexpectedException
	{
		return dao.getNInterventiAssociatiDanniScorte(idProcedimentoOggetto, listIdScortaMagazzino);
	}

	@Override
	public ColtureAziendaliDTO getRiepilogoColtureAziendali(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		ColtureAziendaliDTO riepilogo = dao.getRiepilogoColtureAziendali(idProcedimentoOggetto);
		return riepilogo;
	}
	
	@Override
	public ColtureAziendaliDTO getRiepilogoColtureAziendali(long idProcedimentoOggetto, long idProcedimento)
			throws InternalUnexpectedException
	{
		ColtureAziendaliDTO riepilogo = dao.getRiepilogoColtureAziendali(idProcedimentoOggetto);
		BandoDTO bando = dao.getInformazioniBandoByIdProcedimento(idProcedimento);
		riepilogo.setBando(bando);
		return riepilogo;
	}

	@Override
	public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<ColtureAziendaliDettaglioDTO> lista = dao.getListColtureAziendali(idProcedimentoOggetto, null);
		if(lista == null)
		{
			lista = new ArrayList<ColtureAziendaliDettaglioDTO>();
		}
		return lista;
	}

	@Override
	public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(long idProcedimentoOggetto,
			long[] arrayIdSuperficieColtura) throws InternalUnexpectedException
	{
		List<ColtureAziendaliDettaglioDTO> lista = dao.getListColtureAziendali(idProcedimentoOggetto,arrayIdSuperficieColtura);
		if(lista == null)
		{
			lista = new ArrayList<ColtureAziendaliDettaglioDTO>();
		}
		return lista;
	}

	@Override
	public void updateColtureAziendali(long idProcedimentoOggetto,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadroDTO,
			List<ColtureAziendaliDettaglioDTO> listColtureAziendaliModificate) throws InternalUnexpectedException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		int nUpdated = 0;
		for(ColtureAziendaliDettaglioDTO coltura : listColtureAziendaliModificate)
		{
			if(dao.updateColturaAziendale(idProcedimentoOggetto,coltura) == 1){
				nUpdated ++;
				dao.eliminaControlloColtura(idProcedimentoOggetto, coltura.getIdSuperficieColtura());
				SuperficiColtureDettaglioPsrDTO controllo = dao.getSuperficiColtureDettaglioPsrDTO(idProcedimentoOggetto, coltura.getIdSuperficieColtura());
				
				
				if(coltura.getProduzioneHa().compareTo(controllo.getProduzioneHaMin()) < 0 || 
						coltura.getProduzioneHa().compareTo(controllo.getProduzioneHaMax()) > 0 )
				{
					ControlloColturaDTO controlloColtura = new ControlloColturaDTO();
					controlloColtura.setIdSuperficieColtura(coltura.getIdSuperficieColtura());
					controlloColtura.setDescrizioneAnomalia("La prodizione q/ha deve essere un valore compreso tra " + controllo.getProduzioneHaMin() + " e " + controllo.getProduzioneHaMax());
					controlloColtura.setBloccante("N");
					dao.inserisciControlloColtura(idProcedimentoOggetto,coltura.getIdSuperficieColtura(),controlloColtura);
				}
				if(coltura.getPrezzo().compareTo(controllo.getPrezzoMin()) < 0 || 
						coltura.getPrezzo().compareTo(controllo.getPrezzoMax()) > 0)
				{
					ControlloColturaDTO controlloColtura = new ControlloColturaDTO();
					controlloColtura.setIdSuperficieColtura(coltura.getIdSuperficieColtura());
					controlloColtura.setDescrizioneAnomalia("Il prezzo/q deve essere un valore compreso tra " + controllo.getPrezzoMin() + " e " + controllo.getPrezzoMax());
					controlloColtura.setBloccante("N");
					dao.inserisciControlloColtura(idProcedimentoOggetto,coltura.getIdSuperficieColtura(),controlloColtura);
				}
			}
		}
		if(nUpdated>0)
		{
			logOperationOggettoQuadro(logOperationOggettoQuadroDTO);
		}
	}

	@Override
	public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		List<AssicurazioniColtureDTO> lista = dao.getListAssicurazioniColture(idProcedimentoOggetto);
		if(lista == null)
		{
			lista = new ArrayList<AssicurazioniColtureDTO>();
		}
		return lista;
	}
	

	
	@Override
	public AssicurazioniColtureDTO getRiepilogoAssicurazioniColture(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		AssicurazioniColtureDTO riepilogo = dao.getRiepilogoAssicurazioniColture(idProcedimentoOggetto);
		if(riepilogo == null)
		{
			riepilogo = new AssicurazioniColtureDTO();
		}
		return riepilogo;
	}
	
	
	@Override
	public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto,
			long[] idAssicurazioniColture) throws InternalUnexpectedException
	{
		List<AssicurazioniColtureDTO> lista = dao.getListAssicurazioniColture(idProcedimentoOggetto,idAssicurazioniColture);
		if(lista == null)
		{
			lista = new ArrayList<AssicurazioniColtureDTO>();
		}
		return lista;
	}

	@Override
	public int eliminaAssicurazioniColture(long idProcedimentoOggetto, long[] arrayIdAssicurazioniColture)
			throws InternalUnexpectedException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		return dao.eliminaAssicurazioniColture(idProcedimentoOggetto,arrayIdAssicurazioniColture);
	}

	@Override
	public long inserisciAssicurazioniColture(long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadro) throws InternalUnexpectedException
	{
		dao.lockProcedimentoOggetto(idProcedimentoOggetto);
		dao.inserisciAssicurazioniColture(idProcedimentoOggetto, assicurazioniColture);
		logOperationOggettoQuadro(logOperationOggettoQuadro);
		return 1;
	}

	@Override	
	public List<DecodificaDTO<Integer>> getListConsorzi(String idProvincia)
			throws InternalUnexpectedException
	{
		List<DecodificaDTO<Integer>> lista = dao.getListConsorzi(idProvincia);
		if(lista == null)
		{
			lista = new ArrayList<DecodificaDTO<Integer>>();
		}
		return lista;
	}

	@Override
	public long modificaAssicurazioniColture(long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture,
			LogOperationOggettoQuadroDTO logOperationOggettoQuadro) throws InternalUnexpectedException
	{
		return dao.modificaAssicurazioniColture(idProcedimentoOggetto,assicurazioniColture);
	}


	@Override
	public List<DanniColtureDTO> getListDanniColture(long idProcedimentoOggetto, long idProcedimento) 
			throws InternalUnexpectedException
	{
		List<DanniColtureDTO> lista = dao.getListDanniColture(idProcedimentoOggetto);
		BandoDTO bando = dao.getInformazioniBandoByIdProcedimento(idProcedimento);
		if(lista == null)
		{
			lista = new ArrayList<DanniColtureDTO>();
		}
		for(DanniColtureDTO danno : lista)
		{
			danno.setBando(bando);
		}
		return lista;
	}

	@Override
	public Long getNColtureDanneggiate(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return dao.getNColtureDanneggiate(idProcedimentoOggetto);
	}


	  
	  @Override
	  public void deleteRTipoDocumentiRichiesti(long idDocumentiRichiesti, long idTipoDocumentiRichiesti) throws InternalUnexpectedException{
		  dao.deleteRTipoDocRichiesti(idDocumentiRichiesti);
	  }
	  
	  		 
	  @Override
	  public List<SezioneDocumentiRichiestiDTO> getListDocumentiRichiestiDaVisualizzare(long idProcedimentoOggetto, Boolean isVisualizzazione) throws InternalUnexpectedException{
		  return dao.getListDocumentiRichiestiDaVisualizzare(idProcedimentoOggetto, isVisualizzazione);
	  }
	  
	  @Override
	  public List<DocumentiRichiestiDTO> getDocumentiRichiesti(long idProcedimentoOggetto) throws InternalUnexpectedException{
		  return dao.getDocumentiRichiesti(idProcedimentoOggetto);
	  }
	  

		
		@Override
		public int aggiornaDocumentiRichiesti(long idProcedimentoOggetto, List<String> requestList, String HValue) throws InternalUnexpectedException{
			//aggiorno altro doc su NEMBO_T_DOCUMENTI_RICHIESTI:
			//se il record esiste -> update
			//se il record non esiste ancora -> insert
			dao.lockProcedimentoOggetto(idProcedimentoOggetto);
			dao.updateAltroDocRichiesto(idProcedimentoOggetto,HValue);
			 
			//cancello ogni doc legato all' idDocRichiesti su NEMBO_R_TIPO_DOCUMENTI_RICHIESTI
			dao.deleteRTipoDocRichiesti(idProcedimentoOggetto);
			
			//per ogni checkbox controllo:
			//se è checkata faccio insert su NEMBO_R_TIPO_DOCUMENTI_RICHIESTI
			List<Long> listaTipoDoc = new ArrayList<>();
			for(String checkVal : requestList){
				listaTipoDoc.add(Long.parseLong(checkVal));
			}
			if(listaTipoDoc.size()>0)
				dao.insertRTipoDocRichiesti(idProcedimentoOggetto, listaTipoDoc);
				
			return 0;			
		}
		
		@Override
		public List<String> getListTestoSezione() throws InternalUnexpectedException{
			return dao.getListTestoSezioni();
		}

		@Override
		public ReferenteProgettoDTO getReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto)
				throws InternalUnexpectedException {
			
			return dao.getReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto);
		}

		@Override
		public void insertOrUpdateReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto, String nome,
				String cognome, String codiceFiscale, String comune, String cap, String telefono,
				String cellulare, String email, LogOperationOggettoQuadroDTO logOperationOggettoQuadro) throws InternalUnexpectedException {
			ReferenteProgettoDTO referenteVecchio = dao.getReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto);
			if(referenteVecchio==null){
				//insert
				dao.insertReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto, nome,
						cognome, codiceFiscale, comune, cap, telefono,
						cellulare, email);
			}else{
				//update
				dao.updateReferenteProgettoByIdProcedimentoOggetto(idProcedimentoOggetto, nome,
						cognome, codiceFiscale, comune, cap, telefono,
						cellulare, email);
			}
			logOperationOggettoQuadro(logOperationOggettoQuadro);
		}

}
