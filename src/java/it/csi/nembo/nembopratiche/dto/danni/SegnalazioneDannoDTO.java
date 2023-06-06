package it.csi.nembo.nembopratiche.dto.danni;

import java.util.Date;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class SegnalazioneDannoDTO implements ILoggable
{
	private static final long serialVersionUID = -6953036195439338566L;
	private String descrizioneDanno;
	private String dataDanno;
	private String flagValoreDefault;
	private List<RigaSegnalazioneDannoDTO> elencoDanni;
	
	
	
	public String getFlagValoreDefault() {
		return flagValoreDefault;
	}
	public void setFlagValoreDefault(String flagValoreDefault) {
		this.flagValoreDefault = flagValoreDefault;
	}
	public String getDescrizioneDanno() {
		return descrizioneDanno;
	}
	public void setDescrizioneDanno(String descrizioneDanno) {
		this.descrizioneDanno = descrizioneDanno;
	}
	
	public String getDataDanno() {
		return dataDanno;
	}
	public void setDataDanno(String dataDanno) {
		this.dataDanno = dataDanno;
	}
	public List<RigaSegnalazioneDannoDTO> getElencoDanni() {
		return elencoDanni;
	}
	public void setElencoDanni(List<RigaSegnalazioneDannoDTO> elencoDanni) {
		this.elencoDanni = elencoDanni;
	}
	
	
	
}
