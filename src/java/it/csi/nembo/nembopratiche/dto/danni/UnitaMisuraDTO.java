package it.csi.nembo.nembopratiche.dto.danni;

import java.util.Date;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class UnitaMisuraDTO implements ILoggable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 946710029293317103L;
	private long idUnitaMisura;
	private long codice;
	private String descrizione;
	private Date dataFine;
	private Long idDanno;
	private String descDanno;
	public long getIdUnitaMisura()
	{
		return idUnitaMisura;
	}
	public void setIdUnitaMisura(long idUnitaMisura)
	{
		this.idUnitaMisura = idUnitaMisura;
	}
	public long getCodice()
	{
		return codice;
	}
	public void setCodice(long codice)
	{
		this.codice = codice;
	}
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	public Date getDataFine()
	{
		return dataFine;
	}
	public void setDataFine(Date dataFine)
	{
		this.dataFine = dataFine;
	}
	public Long getIdDanno()
	{
		return idDanno;
	}
	public void setIdDanno(Long idDanno)
	{
		this.idDanno = idDanno;
	}
	public String getDescDanno()
	{
		return descDanno;
	}
	public void setDescDanno(String descDanno)
	{
		this.descDanno = descDanno;
	}
	
}
