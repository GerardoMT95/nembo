package it.csi.nembo.nembopratiche.dto.coltureaziendali;

import java.math.BigDecimal;
import java.math.MathContext;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ColtureAziendaliDTO implements ILoggable
{
	private static final long serialVersionUID = 575461918714250815L;
	private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");
	//riepilogo colture aziendali
	private BigDecimal superficieUtilizzata;
	private BigDecimal totalePlvOrdinaria;
	private BigDecimal totalePlvEffettiva;
	
	//riepilogo riferito alle assicurazioni
	private BigDecimal plvRicalcolataConAssicurazioni;
	private BigDecimal plvOrdinariaConAssicurazioni;
	private BigDecimal percentualeDannoConAssicurazioni;
	
	private BigDecimal percProdPerdutaTriennioRimborsi;
	private BandoDTO bando;

	public BigDecimal getSuperficieUtilizzata()
	{
		return superficieUtilizzata;
	}
	public void setSuperficieUtilizzata(BigDecimal superficieUtilizzata)
	{
		this.superficieUtilizzata = superficieUtilizzata;
	}
	public BigDecimal getTotalePlvOrdinaria()
	{
		return totalePlvOrdinaria;
	}
	public void setTotalePlvOrdinaria(BigDecimal totalePlvOrdinaria)
	{
		this.totalePlvOrdinaria = totalePlvOrdinaria;
	}
	public BigDecimal getTotalePlvEffettiva()
	{
		return totalePlvEffettiva;
	}
	
	public String getTotalePlvEffettivaIfNotEqualFormatted()
	{
		if(totalePlvEffettiva != null && totalePlvOrdinaria != null)
		{
			if(totalePlvEffettiva.equals(totalePlvOrdinaria))
			{
				return "-";
			}
			else
			{
				return NemboUtils.FORMAT.formatDecimal2(totalePlvEffettiva);
			}
		}
		else
		{
			if(totalePlvEffettiva != null)
			{
				return NemboUtils.FORMAT.formatDecimal2(totalePlvEffettiva);
			}
			else
			{
				return "";
			}
		}
	}
	
	public void setTotalePlvEffettiva(BigDecimal totalePlvEffettiva)
	{
		this.totalePlvEffettiva = totalePlvEffettiva;
	}
	public BigDecimal getPercentualeDanno()
	{
		BigDecimal percentualeDanno;
		if(totalePlvOrdinaria != null)
		{
			if(totalePlvOrdinaria.compareTo(BigDecimal.ZERO)==0)
			{
				if(totalePlvEffettiva.compareTo(BigDecimal.ZERO)==0)
				{
					percentualeDanno = BigDecimal.ZERO;
				}
				else
				{
					percentualeDanno = ONE_HUNDRED;
				}
			}else
			{
				percentualeDanno = BigDecimal.ONE.subtract(
							totalePlvEffettiva.divide(totalePlvOrdinaria,MathContext.DECIMAL128))
							.multiply(ONE_HUNDRED);
			}
			return percentualeDanno;
		}
		else
		{
			return null;
		}

	}
	
	//formatted
	public String getSuperficieUtilizzataFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(superficieUtilizzata);
	}
	public String getTotalePlvOrdinariaFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(totalePlvOrdinaria);
	}
	public String getTotalePlvEffettivaFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(totalePlvEffettiva);
	}
	
	//assicurazioni
	public String getPercentualeDannoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(getPercentualeDanno()) + "%";
	}
	public BigDecimal getPlvRicalcolataConAssicurazioni()
	{
		return plvRicalcolataConAssicurazioni;
	}
	public String getPlvRicalcolataConAssicurazioniFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(plvRicalcolataConAssicurazioni);
	}
	
	public void setPlvRicalcolataConAssicurazioni(BigDecimal plvRicalcolataConAssicurazioni)
	{
		this.plvRicalcolataConAssicurazioni = plvRicalcolataConAssicurazioni;
	}
	public BigDecimal getPlvOrdinariaConAssicurazioni()
	{
		return plvOrdinariaConAssicurazioni;
	}
	public String getPlvOrdinariaConAssicurazioniFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(plvOrdinariaConAssicurazioni);
	}

	public void setPlvOrdinariaConAssicurazioni(BigDecimal plvOrdinariaConAssicurazioni)
	{
		this.plvOrdinariaConAssicurazioni = plvOrdinariaConAssicurazioni;
	}
	
	public void setPercentualeDannoConAssicurazioni(BigDecimal percentualeDannoConAssicurazioni)
	{
		this.percentualeDannoConAssicurazioni = percentualeDannoConAssicurazioni;
	}
	public BigDecimal getPercentualeDannoConAssicurazioni()
	{
		return percentualeDannoConAssicurazioni;
	}
	public String getPercentualeDannoConAssicurazioniFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(percentualeDannoConAssicurazioni) + "%";
	}

//dati usati esclusivamente nel riepilogo danni colture	
	
	public BigDecimal getProduzionePerduta()
	{
		if(totalePlvOrdinaria != null && totalePlvEffettiva != null)
		{
			return totalePlvOrdinaria.subtract(totalePlvEffettiva);
		}
		else
		{
			return null;
		}
	}

	public String getProduzionePerdutaFormatted()
	{
		BigDecimal produzionePerduta = getProduzionePerduta();
		if(produzionePerduta != null)
		{
			return NemboUtils.FORMAT.formatDecimal2(produzionePerduta);
		}
		else
		{
			return "";
		}
	}
	
	public BigDecimal getPercDannoCalcolato()
	{
		BigDecimal produzionePerduta = getProduzionePerduta();
		if(totalePlvOrdinaria != null && produzionePerduta != null)
		{
			return produzionePerduta.divide(totalePlvOrdinaria,MathContext.DECIMAL64).multiply(ONE_HUNDRED);
		}
		else
		{
			return null;
		}
	}
	
	public String getPercDannoCalcolatoFormatted()
	{
		BigDecimal percDannoCalcolato = getPercDannoCalcolato();
		if(percDannoCalcolato != null)
		{
			return NemboUtils.FORMAT.formatDecimal2(percDannoCalcolato) + "%";
		}
		else
		{
			return null;
		}
	}
	
	public BigDecimal getPercProdPerdutaTriennioRimborsi()
	{
		BigDecimal produzionePerduta = getProduzionePerduta();
		if(plvOrdinariaConAssicurazioni != null && produzionePerduta != null)
		{
			return BigDecimal.ONE.subtract(
					plvOrdinariaConAssicurazioni.divide(produzionePerduta,MathContext.DECIMAL64)).multiply(ONE_HUNDRED);
		}
		else
		{
			return null;
		}
	}
	
	public String getPercProdPerdutaTriennioRimborsiFormatted()
	{
		BigDecimal percProdPerdutaTriennioRimborsi = getPercProdPerdutaTriennioRimborsi();
		if(percProdPerdutaTriennioRimborsi != null)
		{
			return NemboUtils.FORMAT.formatDecimal2(percProdPerdutaTriennioRimborsi)+"%";
		}
		else
		{
			return null;
		}
	}
	
	public void setPercProdPerdutaTriennioRimborsi(BigDecimal percProdPerdutaTriennioRimborsi)
	{
		this.percProdPerdutaTriennioRimborsi = percProdPerdutaTriennioRimborsi;
	}
	public BandoDTO getBando()
	{
		return bando;
	}
	public void setBando(BandoDTO bando)
	{
		this.bando = bando;
	}
	
	
	
}
