package it.csi.nembo.nembopratiche.dto.danni;

import java.math.BigDecimal;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.integration.QuadroNemboDAO;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class DannoDTO implements ILoggable
{
	private long idDanno;
	private String descrizione;
	private Long idUnitaMisura;
	private String nomeTabella;
	private String codice;
	public long getIdDanno()
	{
		return idDanno;
	}
	public void setIdDanno(long idDanno)
	{
		this.idDanno = idDanno;
	}
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	public Long getIdUnitaMisura()
	{
		return idUnitaMisura;
	}
	public void setIdUnitaMisura(Long idUnitaMisura)
	{
		this.idUnitaMisura = idUnitaMisura;
	}
	public String getNomeTabella()
	{
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella)
	{
		this.nomeTabella = nomeTabella;
	}
	public String getCodice()
	{
		return codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}
	
	
}
