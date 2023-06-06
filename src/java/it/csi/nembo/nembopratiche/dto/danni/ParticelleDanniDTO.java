package it.csi.nembo.nembopratiche.dto.danni;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ParticelleDanniDTO implements ILoggable
{
	private static final long serialVersionUID = 8632415387388345485L;
	private long idUtilizzoDichiarato;
	private String istatComune;
	private String sezione;
	private String descSezione;
	private String descComune;
	private String comune;
	private long foglio;
	private long particella;
	private String subalterno;
	private BigDecimal supCatastale;
	private String occupazioneSuolo;
	private String destinazione;
	private String uso;
	private String qualita;
	private String varieta;
	private String flagArboreo; 
	private BigDecimal superficieUtilizzata;
	private String descProvincia;
	private String descTipoUtilizzo;
	
	private Long idDannoAtm;
	private Long progressivo;
	
	public String getId()
	{
		return Long.toString(idUtilizzoDichiarato);
	}
	
	public long getIdUtilizzoDichiarato()
	{
		return idUtilizzoDichiarato;
	}

	public void setIdUtilizzoDichiarato(long idUtilizzoDichiarato)
	{
		this.idUtilizzoDichiarato = idUtilizzoDichiarato;
	}
	
	public String getFlagArboreo()
	{
		return flagArboreo;
	}

	public void setFlagArboreo(String flagArboreo)
	{
		this.flagArboreo = flagArboreo;
	}

	public String getIstatComune()
	{
		return istatComune;
	}
	public void setIstatComune(String istatComune)
	{
		this.istatComune = istatComune;
	}
	public String getComune()
	{
		return comune;
	}
	public void setComune(String comune)
	{
		this.comune = comune;
	}
	
	public String getDescComune()
	{
		return descComune;
	}

	public void setDescComune(String descComune)
	{
		this.descComune = descComune;
	}

	public String getSezione()
	{
		return sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}
	
	public String getDescSezione()
	{
		return descSezione;
	}
	public void setDescSezione(String descSezione)
	{
		this.descSezione = descSezione;
	}
	public long getFoglio()
	{
		return foglio;
	}
	public void setFoglio(long foglio)
	{
		this.foglio = foglio;
	}
	public long getParticella()
	{
		return particella;
	}
	public void setParticella(long particella)
	{
		this.particella = particella;
	}
	public String getSubalterno()
	{
		return subalterno;
	}
	public void setSubalterno(String subalterno)
	{
		this.subalterno = subalterno;
	}
	public BigDecimal getSupCatastale()
	{
		return supCatastale;
	}
	public void setSupCatastale(BigDecimal supCatastale)
	{
		this.supCatastale = supCatastale;
	}
	public String getOccupazioneSuolo()
	{
		return occupazioneSuolo;
	}
	public void setOccupazioneSuolo(String occupazioneSuolo)
	{
		this.occupazioneSuolo = occupazioneSuolo;
	}
	public String getDestinazione()
	{
		return destinazione;
	}
	public void setDestinazione(String destinazione)
	{
		this.destinazione = destinazione;
	}
	public String getUso()
	{
		return uso;
	}
	public void setUso(String uso)
	{
		this.uso = uso;
	}
	public String getQualita()
	{
		return qualita;
	}
	public void setQualita(String qualita)
	{
		this.qualita = qualita;
	}
	public String getVarieta()
	{
		return varieta;
	}
	public void setVarieta(String varieta)
	{
		this.varieta = varieta;
	}
	public BigDecimal getSuperficieUtilizzata()
	{
		return superficieUtilizzata;
	}
	public void setSuperficieUtilizzata(BigDecimal superficieUtilizzata)
	{
		this.superficieUtilizzata = superficieUtilizzata;
	}

	public String getDescProvincia()
	{
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia)
	{
		this.descProvincia = descProvincia;
	}

	public String getDescTipoUtilizzo()
	{
		return descTipoUtilizzo;
	}

	public void setDescTipoUtilizzo(String descTipoUtilizzo)
	{
		this.descTipoUtilizzo = descTipoUtilizzo;
	}

	public Long getIdDannoAtm()
	{
		return idDannoAtm;
	}

	public void setIdDannoAtm(Long idDannoAtm)
	{
		this.idDannoAtm = idDannoAtm;
	}

	public Long getProgressivo()
	{
		return progressivo;
	}

	public void setProgressivo(Long progressivo)
	{
		this.progressivo = progressivo;
	}

	public String getSupCatastaleFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(supCatastale);
	}

	public String getSuperficieUtilizzataFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(superficieUtilizzata);
	}
	
	
	
}
