package it.csi.nembo.nembopratiche.dto.allevamenti;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class AllevamentiDettaglioPlvDTO implements ILoggable
{
	private static final long serialVersionUID = 3147906851702453326L;
	private long idProduzione;
	private String descProduzione;
	private long numeroCapi;
	private String unitaMisuraSpecie;
	private BigDecimal quantitaProdotta;
	private String descUnitaMisura;
	private BigDecimal quantitaReimpiegata;
	private BigDecimal prodLorda;
	private BigDecimal prodNetta;
	private BigDecimal prezzo;
	private BigDecimal importoTotale;
	
	//PLZ Zootecnica dettaglio
	private String codiceAziendaZootecnica;
	private String descrizioneSpecieAnimale;
	private String descrizioneCategoriaAnimale;
	private BigDecimal plv;
	
	
	public long getIdProduzione()
	{
		return idProduzione;
	}
	public void setIdProduzione(long idProduzione)
	{
		this.idProduzione = idProduzione;
	}
	public String getDescProduzione()
	{
		return descProduzione;
	}
	public void setDescProduzione(String descProduzione)
	{
		this.descProduzione = descProduzione;
	}
	public long getNumeroCapi()
	{
		return numeroCapi;
	}
	public String getNumeroCapiFormatted()
	{
		return (Long.toString(numeroCapi) + " " + unitaMisuraSpecie);
	}
	
	public String getNumeroCapiFormattedPerStampa()
	{
		return (Long.toString(numeroCapi));
	}
	
	public void setNumeroCapi(long numeroCapi)
	{
		this.numeroCapi = numeroCapi;
	}
	public String getUnitaMisuraSpecie()
	{
		return unitaMisuraSpecie;
	}
	public void setUnitaMisuraSpecie(String unitaMisuraSpecie)
	{
		this.unitaMisuraSpecie = unitaMisuraSpecie;
	}
	public BigDecimal getQuantitaProdotta()
	{
		return quantitaProdotta;
	}
	public String getQuantitaProdottaFormatted()
	{
		return NemboUtils.FORMAT.formatGenericNumber(quantitaProdotta, 1, true);
	}	
	public void setQuantitaProdotta(BigDecimal quantitaProdotta)
	{
		this.quantitaProdotta = quantitaProdotta;
	}
	public String getDescUnitaMisura()
	{
		return descUnitaMisura;
	}
	public void setDescUnitaMisura(String descUnitaMisura)
	{
		this.descUnitaMisura = descUnitaMisura;
	}
	public BigDecimal getQuantitaReimpiegata()
	{
		return quantitaReimpiegata;
	}
	public String getQuantitaReimpiegataFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(quantitaReimpiegata);
	}
	public void setQuantitaReimpiegata(BigDecimal quantitaReimpiegata)
	{
		this.quantitaReimpiegata = quantitaReimpiegata;
	}
	public BigDecimal getProdLorda()
	{
		return prodLorda;
	}
	public String getProdLordaFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(prodLorda);
	}
	public void setProdLorda(BigDecimal prodLorda)
	{
		this.prodLorda = prodLorda;
	}
	public BigDecimal getProdNetta()
	{
		return prodNetta;
	}
	public String getProdNettaFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(prodNetta);
	}
	
	public void setProdNetta(BigDecimal prodNetta)
	{
		this.prodNetta = prodNetta;
	}
	public BigDecimal getPrezzo()
	{
		return prezzo;
	}
	public String getPrezzoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(prezzo) + " &euro;/" + descUnitaMisura;
	}
	
	public String getPrezzoFormattedPerStampe()
	{
		return NemboUtils.FORMAT.formatDecimal2(prezzo) + " €/" + descUnitaMisura;
	}
	
	public void setPrezzo(BigDecimal prezzo)
	{
		this.prezzo = prezzo;
	}
	public BigDecimal getImportoTotale()
	{
		return importoTotale;
	}
	public String getImportoTotaleFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(importoTotale) + " &euro;";
	}	
	
	public String getImportoTotaleFormattedPerStampe()
	{
		return NemboUtils.FORMAT.formatDecimal2(importoTotale) + " €";
	}	
	
	public void setImportoTotale(BigDecimal importoTotale)
	{
		this.importoTotale = importoTotale;
	}
	public String getCodiceAziendaZootecnica()
	{
		return codiceAziendaZootecnica;
	}
	public void setCodiceAziendaZootecnica(String codiceAziendaZootecnica)
	{
		this.codiceAziendaZootecnica = codiceAziendaZootecnica;
	}
	public String getDescrizioneSpecieAnimale()
	{
		return descrizioneSpecieAnimale;
	}
	public void setDescrizioneSpecieAnimale(String descrizioneSpecieAnimale)
	{
		this.descrizioneSpecieAnimale = descrizioneSpecieAnimale;
	}
	public String getDescrizioneCategoriaAnimale()
	{
		return descrizioneCategoriaAnimale;
	}
	public void setDescrizioneCategoriaAnimale(String descrizioneCategoriaAnimale)
	{
		this.descrizioneCategoriaAnimale = descrizioneCategoriaAnimale;
	}
	public BigDecimal getPlv()
	{
		return plv;
	}
	public String getPlvFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(plv);
	}
	public void setPlv(BigDecimal plv)
	{
		this.plv = plv;
	}
	
	
}
