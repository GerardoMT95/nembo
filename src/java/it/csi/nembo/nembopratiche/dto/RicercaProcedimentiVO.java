package it.csi.nembo.nembopratiche.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import it.csi.nembo.nembopratiche.dto.gestioneeventi.EventiDTO;
import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.LivelloDTO;

public class RicercaProcedimentiVO implements ILoggable
{
  /** serialVersionUID */
  private static final long           serialVersionUID           = 4601339650061084885L;

  // ricerca per procedimento
  private String                      identificativo;
  private String                      cuaa;

  // ricerca per filtri
  private ArrayList<Long>			  	  vctIdEventi;
  private ArrayList<Long>              vctIdLivelli;
  private ArrayList<Long>             	  vctIdBando;
  private ArrayList<Long>	              vctIdAmministrazione;
  private ArrayList<Long>                vctIdStatoProcedimento;
  private ArrayList<String>              vctIdStatoAmmProcedimento;
  private ArrayList<String>              vctFlagEstrazione;
  private ArrayList<String>              vctFlagEstrazioneExPost;
  private ArrayList<Long>              vctNotifiche;

  private List<EventiDTO>			  eventi;
  private List<BandoDTO>              bandi;
  private List<LivelloDTO>            livelli;
  private List<AmmCompetenzaDTO>      amministrazioni;
  private List<ProcedimentoDTO>       statiProcedimento;
  private List<ProcedimentoDTO>       statiAmmProcedimento;
  private boolean                     flagShowAllAmministrazioni = false;

  // ricerca per dati anagrafici
  private String                      cuaaProcedimenti;
  private String                      piva;
  private String                      denominazione;
  private String                      provSedeLegale;
  private String                      comuneSedeLegale;

  private Date                        istanzaDataDa;
  private Date                        istanzaDataA;

  // ricerca per dati anagrafici
  private String                      tipoFiltroOggetto;

  private String                      tipoFiltroGruppo;
  // mappa del tipo <idOggetto, ArrayList<idEsito>>
  private HashMap<Long, ArrayList<Long>> mapOggetti;
  private HashMap<Long, ArrayList<Long>> mapGruppi;

  // mappa del tipo <idGruppo, ArrayList<idStati>>
  private HashMap<Long, ArrayList<Long>> mapGruppiStati;

  private Long                        idTipoDocumento;
  private Long                        idModalitaPagamento;
  private Long                        idFornitore;
  private Date                        dataDocumentoDa;
  private Date                        dataDocumentoA;


	public Long getIdTipoDocumento()
	{
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento)
	{
		this.idTipoDocumento = idTipoDocumento;
	}

	public Long getIdModalitaPagamento()
	{
		return idModalitaPagamento;
	}

	public void setIdModalitaPagamento(Long idModalitaPagamento)
	{
		this.idModalitaPagamento = idModalitaPagamento;
	}

	public Long getIdFornitore()
	{
		return idFornitore;
	}

	public void setIdFornitore(Long idFornitore)
	{
		this.idFornitore = idFornitore;
	}

	public Date getDataDocumentoDa()
	{
		return dataDocumentoDa;
	}

	public void setDataDocumentoDa(Date dataDocumentoDa)
	{
		this.dataDocumentoDa = dataDocumentoDa;
	}

	public Date getDataDocumentoA()
	{
		return dataDocumentoA;
	}

	public void setDataDocumentoA(Date dataDocumentoA)
	{
		this.dataDocumentoA = dataDocumentoA;
	}

	public String getIdentificativo()
	{
		return identificativo;
	}

	public void setIdentificativo(String identificativo)
	{
		this.identificativo = identificativo;
	}

	public String getCuaa()
	{
		return cuaa;
	}
	

	public void setCuaa(String cuaa)
	{
		this.cuaa = cuaa;
	}
	
	public ArrayList<Long> getVctIdEventi()
	{
		return vctIdEventi;
	}

	public void setVctIdEventi(ArrayList<Long> vctIdEventi)
	{
		this.vctIdEventi = vctIdEventi;
	}

	public ArrayList<Long> getVctIdLivelli()
	{
		return vctIdLivelli;
	}

	public void setVctIdLivelli(ArrayList<Long> vctIdLivelli)
	{
		this.vctIdLivelli = vctIdLivelli;
	}
	

	public ArrayList<Long> getVctIdBando()
	{
		return vctIdBando;
	}

	public void setVctIdBando(ArrayList<Long> vctIdBando)
	{
		this.vctIdBando = vctIdBando;
	}

	public ArrayList<Long> getVctIdAmministrazione()
	{
		return vctIdAmministrazione;
	}

	public void setVctIdAmministrazione(ArrayList<Long> vctIdAmministrazione)
	{
		this.vctIdAmministrazione = vctIdAmministrazione;
	}

	public ArrayList<Long> getVctIdStatoProcedimento()
	{
		return vctIdStatoProcedimento;
	}

	public void setVctIdStatoProcedimento(ArrayList<Long> vctIdStatoProcedimento)
	{
		this.vctIdStatoProcedimento = vctIdStatoProcedimento;
	}

	public String getPiva()
	{
		return piva;
	}

	public void setPiva(String piva)
	{
		this.piva = piva;
	}

	public String getDenominazione()
	{
		return denominazione;
	}
	
	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}

	public String getProvSedeLegale()
	{
		return provSedeLegale;
	}
	
	public void setProvSedeLegale(String provSedeLegale)
	{
		this.provSedeLegale = provSedeLegale;
	}

	public String getComuneSedeLegale()
	{
		return comuneSedeLegale;
	}

	public void setComuneSedeLegale(String comuneSedeLegale)
	{
		this.comuneSedeLegale = comuneSedeLegale;
	}

	public List<EventiDTO> getEventi()
	{
		return eventi;
	}

	public void setEventi(List<EventiDTO> eventi)
	{
		this.eventi = eventi;
	}

	public List<BandoDTO> getBandi()
	{
		return bandi;
	}

	public void setBandi(List<BandoDTO> bandi)
	{
		this.bandi = bandi;
	}

	public List<LivelloDTO> getLivelli()
	{
		return livelli;
	}

	public void setLivelli(List<LivelloDTO> livelli)
	{
		this.livelli = livelli;
	}

	public List<AmmCompetenzaDTO> getAmministrazioni()
	{
		return amministrazioni;
	}

	public void setAmministrazioni(List<AmmCompetenzaDTO> amministrazioni)
	{
		this.amministrazioni = amministrazioni;
	}

	public List<ProcedimentoDTO> getStatiProcediemnto()
	{
		return statiProcedimento;
	}

	public void setStatiProcedimento(List<ProcedimentoDTO> statiProcedimento)
	{
		this.statiProcedimento = statiProcedimento;
	}

	public String getCuaaProcedimenti()
	{
		return cuaaProcedimenti;
	}

	public void setCuaaProcedimenti(String cuaaProcedimenti)
	{
		this.cuaaProcedimenti = cuaaProcedimenti;
	}

	public HashMap<Long, ArrayList<Long>> getMapOggetti()
	{
		return mapOggetti;
	}

	public void setMapOggetti(HashMap<Long, ArrayList<Long>> mapOggetti)
	{
		this.mapOggetti = mapOggetti;
	}

	public HashMap<Long, ArrayList<Long>> getMapGruppi()
	{
		return mapGruppi;
	}

	public void setMapGruppi(HashMap<Long, ArrayList<Long>> mapGruppi)
	{
		this.mapGruppi = mapGruppi;
	}

	public String getTipoFiltroOggetto()
	{
		return tipoFiltroOggetto;
	}

	public void setTipoFiltroOggetto(String tipoFiltroOggetto)
	{
		this.tipoFiltroOggetto = tipoFiltroOggetto;
	}

	public boolean isFlagShowAllAmministrazioni()
	{
		return flagShowAllAmministrazioni;
	}

	public void setFlagShowAllAmministrazioni(boolean flagShowAllAmministrazioni)
	{
		this.flagShowAllAmministrazioni = flagShowAllAmministrazioni;
	}

	public ArrayList<String> getVctFlagEstrazione()
	{
		return vctFlagEstrazione;
	}

	public void setVctFlagEstrazione(ArrayList<String> vctFlagEstrazione)
	{
		this.vctFlagEstrazione = vctFlagEstrazione;
	}

	public VecArrayListtor<String> getVctFlagEstrazioneExPost()
	{
		return vctFlagEstrazioneExPost;
	}

	public void setVctFlagEstrazioneExPost(ArrayList<String> vctFlagEstrazioneExPost)
	{
		this.vctFlagEstrazioneExPost = vctFlagEstrazioneExPost;
	}

	public Date getIstanzaDataDa()
	{
		return istanzaDataDa;
	}

	public void setIstanzaDataDa(Date istanzaDataDa)
	{
		this.istanzaDataDa = istanzaDataDa;
	}

	public Date getIstanzaDataA()
	{
		return istanzaDataA;
	}

	public void setIstanzaDataA(Date istanzaDataA)
	{
		this.istanzaDataA = istanzaDataA;
	}

	public ArrayList<Long> getVctNotifiche()
	{
		return vctNotifiche;
	}

	public void setVctNotifiche(ArrayList<Long> vctNotifiche)
	{
		this.vctNotifiche = vctNotifiche;
	}

	public ArrayList<String> getVctIdStatoAmmProcedimento()
	{
		return vctIdStatoAmmProcedimento;
	}

	public void setVctIdStatoAmmProcedimento(ArrayList<String> vctIdStatoAmmProcedimento)
	{
		this.vctIdStatoAmmProcedimento = vctIdStatoAmmProcedimento;
	}

	public List<ProcedimentoDTO> getStatiAmmProcediemnto()
	{
		return statiAmmProcedimento;
	}

	public void setStatiAmmProcedimento(List<ProcedimentoDTO> statiAmmProcedimento)
	{
		this.statiAmmProcedimento = statiAmmProcedimento;
	}

	public HashMap<Long, ArrayList<Long>> getMapGruppiStati()
	{
		return mapGruppiStati;
	}

	public void setMapGruppiStati(HashMap<Long, ArrayList<Long>> mapGruppiStati)
	{
		this.mapGruppiStati = mapGruppiStati;
	}

	public String getTipoFiltroGruppo()
	{
		return tipoFiltroGruppo;
	}

	public void setTipoFiltroGruppo(String tipoFiltroGruppo)
	{
		this.tipoFiltroGruppo = tipoFiltroGruppo;
	}

}
