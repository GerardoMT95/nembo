package it.csi.nembo.nembopratiche.dto.coltureaziendali;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ColtureAziendaliDettaglioDTO extends SuperficiColtureDettaglioDTO implements ILoggable
{
	private static final long serialVersionUID = -1132197678230336741L;
	
	private String bloccante;
	private String siglaProvincia;
	private BigDecimal produzioneTotaleDanno;
	private BigDecimal prezzoDanneggiato;
	private BigDecimal totaleEuroPlvOrd;
	private BigDecimal totaleEuroPlvEff;
	private BigDecimal percentualeDanno;
	private String recordModificato;
	
	public String getBloccante()
	{
		return bloccante;
	}
	public void setBloccante(String bloccante)
	{
		this.bloccante = bloccante;
	}
	public String getSiglaProvincia()
	{
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia)
	{
		this.siglaProvincia = siglaProvincia;
	}
	public BigDecimal getProduzioneTotaleDanno()
	{
		return produzioneTotaleDanno;
	}
	public String getProduzioneTotaleDannoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(produzioneTotaleDanno);
	}
	public void setProduzioneTotaleDanno(BigDecimal produzioneTotaleDanno)
	{
		this.produzioneTotaleDanno = produzioneTotaleDanno;
	}
	public BigDecimal getPrezzoDanneggiato()
	{
		return prezzoDanneggiato;
	}
	
	public String getPrezzoDanneggiatoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(prezzoDanneggiato);
	}
	
	public BigDecimal getTotaleEuroPlvOrd()
	{
		return totaleEuroPlvOrd;
	}
	public String getTotaleEuroPlvOrdFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(totaleEuroPlvOrd);
	}
	
	public void setTotaleEuroPlvOrd(BigDecimal totaleEuroPlvOrd)
	{
		this.totaleEuroPlvOrd = totaleEuroPlvOrd;
	}
	public void setPrezzoDanneggiato(BigDecimal prezzoDanneggiato)
	{
		this.prezzoDanneggiato = prezzoDanneggiato;
	}
	
	public BigDecimal getTotaleEuroPlvEff()
	{
		return totaleEuroPlvEff;
	}
	
	public String getTotaleEuroPlvEffFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(totaleEuroPlvEff);
	}
	public void setTotaleEuroPlvEff(BigDecimal totaleEuroPlvEff)
	{
		this.totaleEuroPlvEff = totaleEuroPlvEff;
	}
	public BigDecimal getPercentualeDanno()
	{
		return percentualeDanno;
	}
	public String getPercentualeDannoFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal2(percentualeDanno);
	}
	
	public void setPercentualeDanno(BigDecimal percentualeDanno)
	{
		this.percentualeDanno = percentualeDanno;
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


