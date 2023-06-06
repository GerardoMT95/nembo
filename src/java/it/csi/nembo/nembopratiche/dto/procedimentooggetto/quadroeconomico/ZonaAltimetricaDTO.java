package it.csi.nembo.nembopratiche.dto.procedimentooggetto.quadroeconomico;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ZonaAltimetricaDTO implements ILoggable
{
	private static final long serialVersionUID = 3414230295038095623L;
	private long idUte;
	private long idZonaAltimetrica;
	private String descZonaAltimetrica;
	private BigDecimal percRipartoMontagna;
	private BigDecimal percRipartoPianura;
	private BigDecimal percContrZonaAltimetrica;
	public long getIdUte()
	{
		return idUte;
	}
	public void setIdUte(long idUte)
	{
		this.idUte = idUte;
	}
	public long getIdZonaAltimetrica()
	{
		return idZonaAltimetrica;
	}
	public void setIdZonaAltimetrica(long idZonaAltimetrica)
	{
		this.idZonaAltimetrica = idZonaAltimetrica;
	}
	public String getDescZonaAltimetrica()
	{
		return descZonaAltimetrica;
	}
	public void setDescZonaAltimetrica(String descZonaAltimetrica)
	{
		this.descZonaAltimetrica = descZonaAltimetrica;
	}
	public BigDecimal getPercRipartoMontagna()
	{
		return percRipartoMontagna;
	}
	public void setPercRipartoMontagna(BigDecimal percRipartoMontagna)
	{
		this.percRipartoMontagna = percRipartoMontagna;
	}
	public BigDecimal getPercRipartoPianura()
	{
		return percRipartoPianura;
	}
	public void setPercRipartoPianura(BigDecimal percRipartoPianura)
	{
		this.percRipartoPianura = percRipartoPianura;
	}
	public BigDecimal getPercContrZonaAltimetrica()
	{
		return percContrZonaAltimetrica;
	}
	public String getPercContrZonaAltimetricaFormatted()
	{
		if(percContrZonaAltimetrica != null)
		{
			return NemboUtils.FORMAT.formatDecimal2(percContrZonaAltimetrica) + "%";
		}
		else
		{
			return "";
		}
	}
	public void setPercContrZonaAltimetrica(BigDecimal percContrZonaAltimetrica)
	{
		this.percContrZonaAltimetrica = percContrZonaAltimetrica;
	}
}
