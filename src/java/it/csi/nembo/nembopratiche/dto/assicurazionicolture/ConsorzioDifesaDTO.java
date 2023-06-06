package it.csi.nembo.nembopratiche.dto.assicurazionicolture;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;

public class ConsorzioDifesaDTO implements ILoggable
{
	private static final long serialVersionUID = 1L;
	private long idConsorzioDifesa;
	private String idProvincia;
	private String descrizione;
	public long getIdConsorzioDifesa()
	{
		return idConsorzioDifesa;
	}
	public void setIdConsorzioDifesa(long idConsorzioDifesa)
	{
		this.idConsorzioDifesa = idConsorzioDifesa;
	}
	public String getIdProvincia()
	{
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia)
	{
		this.idProvincia = idProvincia;
	}
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	
	
}
