package it.csi.nembo.nembopratiche.dto.gestioneeventi;

import java.util.Date;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class EventiDTO implements ILoggable {
	
	private static final long serialVersionUID = 1749358568724269201L;
	private long idEventoCalamitoso;
	private String descEvento;
	private Date dataEvento;
	private long idCategoriaEvento;
	private String descCategoriaEvento;

	
	public long getIdEventoCalamitoso() {
		return idEventoCalamitoso;
	}
	public void setIdEventoCalamitoso(long idEventoCalamitoso) {
		this.idEventoCalamitoso = idEventoCalamitoso;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	
	public int getAnnoEvento(){
		return NemboUtils.DATE.getYearFromDate(dataEvento);
	}
	public long getIdCategoriaEvento() {
		return idCategoriaEvento;
	}
	public void setIdCategoriaEvento(long idCategoriaEvento) {
		this.idCategoriaEvento = idCategoriaEvento;
	}
	public String getDescEvento() {
		return descEvento;
	}
	public void setDescEvento(String descEvento) {
		this.descEvento = descEvento;
	}
	public String getDescCategoriaEvento() {
		return descCategoriaEvento;
	}
	public void setDescCategoriaEvento(String descCategoriaEvento) {
		this.descCategoriaEvento = descCategoriaEvento;
	}
	
	public String getDataEventoStr() {
		return NemboUtils.DATE.formatDate(dataEvento);
	}

	public String getDescrizione()
	{
		return (getDataEventoStr() + " - " + getDescEvento()); 
	}
	public boolean isDefaultChecked()
	{ 
		return true; 
	}
	
}
