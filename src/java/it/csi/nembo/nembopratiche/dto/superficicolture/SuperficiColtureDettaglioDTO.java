package it.csi.nembo.nembopratiche.dto.superficicolture;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class SuperficiColtureDettaglioDTO implements ILoggable
{
	private static final long serialVersionUID = -1529306859941114441L;
	private long idSuperficieColtura;
	private String descProvincia;
	private String descComune;
	private String tipoUtilizzoDescrizione;
	private BigDecimal produzioneHa;
	private BigDecimal produzioneDichiarata;
	private BigDecimal giornateLavorativeDich;
	private BigDecimal ufTotali;
	private BigDecimal qliReimpiegati;
	private BigDecimal ufReimpiegate;
	private BigDecimal plvTotQuintali;
	private BigDecimal prezzo;
	private BigDecimal plvTotDich;
	private String recordModificato;
	
	//proprietà non visualizzate a video
	private String extIstatComune;
	private String ubicazioneTerreno;
	private String note;
	private String codTipoUtilizzo;
	private String descTipoUtilizzo;
	private String codTipoUtilizzoSecondario;
	private String descTipoUtilizzoSecondario;
	private String colturaSecondaria;
	
	private BigDecimal superficieUtilizzata;
	
	public long getIdSuperficieColtura()
	{
		return idSuperficieColtura;
	}
	public void setIdSuperficieColtura(long idSuperficieColtura)
	{
		this.idSuperficieColtura = idSuperficieColtura;
	}
	public String getExtIstatComune()
	{
		return extIstatComune;
	}
	public void setExtIstatComune(String extIstatComune)
	{
		this.extIstatComune = extIstatComune;
	}
	public String getDescProvincia()
	{
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia)
	{
		this.descProvincia = descProvincia;
	}
	public String getDescComune()
	{
		return descComune;
	}
	public void setDescComune(String descComune)
	{
		this.descComune = descComune;
	}
	public String getCodTipoUtilizzo()
	{
		return codTipoUtilizzo;
	}
	public void setCodTipoUtilizzo(String codTipoUtilizzo)
	{
		this.codTipoUtilizzo = codTipoUtilizzo;
	}
	public String getDescTipoUtilizzo()
	{
		return descTipoUtilizzo;
	}
	public void setDescTipoUtilizzo(String descTipoUtilizzo)
	{
		this.descTipoUtilizzo = descTipoUtilizzo;
	}
	public String getCodTipoUtilizzoSecondario()
	{
		return codTipoUtilizzoSecondario;
	}
	public void setCodTipoUtilizzoSecondario(String codTipoUtilizzoSecondario)
	{
		this.codTipoUtilizzoSecondario = codTipoUtilizzoSecondario;
	}
	public String getDescTipoUtilizzoSecondario()
	{
		return descTipoUtilizzoSecondario;
	}
	public void setDescTipoUtilizzoSecondario(String descTipoUtilizzoSecondario)
	{
		this.descTipoUtilizzoSecondario = descTipoUtilizzoSecondario;
	}
	public String getColturaSecondaria()
	{
		return colturaSecondaria;
	}
	public void setColturaSecondaria(String colturaSecondaria)
	{
		this.colturaSecondaria = colturaSecondaria;
	}
	public BigDecimal getSuperficieUtilizzata()
	{
		return superficieUtilizzata;
	}
	public String getSuperficieUtilizzataFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(superficieUtilizzata);
	}
	public void setSuperficieUtilizzata(BigDecimal superficieUtilizzata)
	{
		this.superficieUtilizzata = superficieUtilizzata;
	}
	public BigDecimal getProduzioneHa()
	{
		return produzioneHa;
	}
	public String getProduzioneHaFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(produzioneHa);
	}
	public void setProduzioneHa(BigDecimal produzioneHa)
	{
		this.produzioneHa = produzioneHa;
	}
	public BigDecimal getProduzioneDichiarata()
	{
		return produzioneDichiarata;
	}
	public String getProduzioneDichiarataFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(produzioneDichiarata);
	}
	public void setProduzioneDichiarata(BigDecimal produzioneDichiarata)
	{
		this.produzioneDichiarata = produzioneDichiarata;
	}
	public BigDecimal getGiornateLavorativeDich()
	{
		return giornateLavorativeDich;
	}
	public void setGiornateLavorativeDich(BigDecimal giornateLavorativeDich)
	{
		this.giornateLavorativeDich = giornateLavorativeDich;
	}
	public BigDecimal getUfTotali()
	{
		return ufTotali;
	}
	public void setUfTotali(BigDecimal ufTotali)
	{
		this.ufTotali = ufTotali;
	}
	public BigDecimal getUfReimpiegate()
	{
		return ufReimpiegate;
	}
	public void setUfReimpiegate(BigDecimal ufReimpiegate)
	{
		this.ufReimpiegate = ufReimpiegate;
	}
	public BigDecimal getQliReimpiegati()
	{
		return qliReimpiegati;
	}
	public void setQliReimpiegati(BigDecimal qliReimpiegati)
	{
		this.qliReimpiegati = qliReimpiegati;
	}
	public BigDecimal getPlvTotQuintali()
	{
		return plvTotQuintali;
	}
	public String getPlvTotQuintaliFormtted()
	{
		return NemboUtils.FORMAT.formatDecimal4(plvTotQuintali);
	}
	public void setPlvTotQuintali(BigDecimal plvTotQuintali)
	{
		this.plvTotQuintali = plvTotQuintali;
	}
	public BigDecimal getPrezzo()
	{
		return prezzo;
	}
	public String getPrezzoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(prezzo);
	}
	public void setPrezzo(BigDecimal prezzo)
	{
		this.prezzo = prezzo;
	}
	public BigDecimal getPlvTotDich()
	{
		return plvTotDich;
	}
	public void setPlvTotDich(BigDecimal plvTotDich)
	{
		this.plvTotDich = plvTotDich;
	}
	public String getTipoUtilizzoDescrizione()
	{
		return tipoUtilizzoDescrizione;
	}
	public void setTipoUtilizzoDescrizione(String tipoUtilizzoDescrizione)
	{
		this.tipoUtilizzoDescrizione = tipoUtilizzoDescrizione;
	}
	public String getUbicazioneTerreno()
	{
		return ubicazioneTerreno;
	}
	public void setUbicazioneTerreno(String ubicazioneTerreno)
	{
		this.ubicazioneTerreno = ubicazioneTerreno;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getRecordModificato()
	{
		return recordModificato;
	}
	public void setRecordModificato(String recordModificato)
	{
		this.recordModificato = recordModificato;
	}
	
}
