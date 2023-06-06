package it.csi.nembo.nembopratiche.dto.superficicolture;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class SuperficiColtureRiepilogoDTO implements ILoggable
{
	private static final long serialVersionUID = -1529306859941114441L;
	private BigDecimal supCatastale;
	private BigDecimal superficieUtilizzata;
	private BigDecimal sauS,sauN,sauA;
	
	public BigDecimal getSupCatastale()
	{
		return supCatastale;
	}
	public String getSupCatastaleFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(supCatastale);
	}
	public void setSupCatastale(BigDecimal supCatastale)
	{
		this.supCatastale = supCatastale;
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
	public BigDecimal getSauS()
	{
		return sauS;
	}
	public String getSauSFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(sauS);
	}
	public void setSauS(BigDecimal sauS)
	{
		this.sauS = sauS;
	}
	public BigDecimal getSauN()
	{
		return sauN;
	}
	public String getSauNFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(sauN);
	}
	public void setSauN(BigDecimal sauN)
	{
		this.sauN = sauN;
	}
	public BigDecimal getSauA()
	{
		return sauA;
	}
	public String getSauAFormatted()
	{
		return NemboUtils.FORMAT.formatDecimal4(sauA);
	}
	public void setSauA(BigDecimal sauA)
	{
		this.sauA = sauA;
	}
}
