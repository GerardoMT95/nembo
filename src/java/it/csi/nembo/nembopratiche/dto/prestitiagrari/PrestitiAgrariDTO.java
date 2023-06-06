package it.csi.nembo.nembopratiche.dto.prestitiagrari;

import java.math.BigDecimal;
import java.util.Date;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class PrestitiAgrariDTO implements ILoggable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333968660942102426L;
	private long idPrestitiAgrari;
	private Date dataScadenza;
	private String finalitaPrestito;
	private BigDecimal importo;
	private String istitutoErogante;
	private long progressivo;
	private long idProcedimentoOggetto;
	
	public long getIdPrestitiAgrari()
	{
		return idPrestitiAgrari;
	}
	public void setIdPrestitiAgrari(long idPrestitiAgrari)
	{
		this.idPrestitiAgrari = idPrestitiAgrari;
	}
	public Date getDataScadenza()
	{
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza)
	{
		this.dataScadenza = dataScadenza;
	}
	public String getDataScadenzaFormatted()
	{
		return NemboUtils.DATE.formatDate(this.dataScadenza);
	}
	public String getFinalitaPrestito()
	{
		return finalitaPrestito;
	}
	public void setFinalitaPrestito(String finalitaPrestito)
	{
		this.finalitaPrestito = finalitaPrestito;
	}
	public BigDecimal getImporto()
	{
		return importo;
	}
	public void setImporto(BigDecimal importo)
	{
		this.importo = importo;
	}
	public String getIstitutoErogante()
	{
		return istitutoErogante;
	}
	public void setIstitutoErogante(String istitutoErogante)
	{
		this.istitutoErogante = istitutoErogante;
	}
	public long getProgressivo()
	{
		return progressivo;
	}
	public void setProgressivo(long progressivo)
	{
		this.progressivo = progressivo;
	}
	public long getIdProcedimentoOggetto()
	{
		return idProcedimentoOggetto;
	}
	public void setIdProcedimentoOggetto(long idProcedimentoOggetto)
	{
		this.idProcedimentoOggetto = idProcedimentoOggetto;
	}
	
	
	
}
