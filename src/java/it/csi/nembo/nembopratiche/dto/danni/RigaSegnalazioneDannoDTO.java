package it.csi.nembo.nembopratiche.dto.danni;

import java.math.BigDecimal;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class RigaSegnalazioneDannoDTO implements ILoggable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6953036195439338566L;
	private long idSegnalazioneDanno;
	private long idDettaglioSegnalDan; 
	private String foglio;
	private String descrizioneColtura;
	private BigDecimal superficieGrafica;
	private BigDecimal importoDanno;
	private BigDecimal percDanno;
	private String noteDanno;
	private String descrizioneComune;
	private String flagColturaAssicurata;
	
	
	
	
	public String getFlagColturaAssicurata() {
		return flagColturaAssicurata;
	}
	
	public String getFlagColturaAssicurataDecodifica() {
		return "S".equalsIgnoreCase(flagColturaAssicurata) ? "Si" : "No" ;
	}
	
	public void setFlagColturaAssicurata(String flagColturaAssicurata) {
		this.flagColturaAssicurata = flagColturaAssicurata;
	}
	public long getIdDettaglioSegnalDan() {
		return idDettaglioSegnalDan;
	}
	public void setIdDettaglioSegnalDan(long idDettaglioSegnalDan) {
		this.idDettaglioSegnalDan = idDettaglioSegnalDan;
	}
	public String getImportoDannoStr() {
		return NemboUtils.FORMAT.formatCurrency(importoDanno);
	}
	public BigDecimal getImportoDanno() {
		return importoDanno;
	}
	public void setImportoDanno(BigDecimal importoDanno) {
		this.importoDanno = importoDanno;
	}
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	public long getIdSegnalazioneDanno() {
		return idSegnalazioneDanno;
	}
	public void setIdSegnalazioneDanno(long idSegnalazioneDanno) {
		this.idSegnalazioneDanno = idSegnalazioneDanno;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getDescrizioneColtura() {
		return descrizioneColtura;
	}
	public void setDescrizioneColtura(String descrizioneColtura) {
		this.descrizioneColtura = descrizioneColtura;
	}
	public BigDecimal getSuperficieGrafica() {
		return superficieGrafica;
	}
	public String getSuperficieGraficaStr() {
		return NemboUtils.FORMAT.formatDecimal4(superficieGrafica);
	}
	public void setSuperficieGrafica(BigDecimal superficieGrafica) {
		this.superficieGrafica = superficieGrafica;
	}
	public BigDecimal getPercDanno() {
		return percDanno;
	}
	public String getPercDannoStr() {
		return NemboUtils.FORMAT.formatCurrency(percDanno);
	}

	public void setPercDanno(BigDecimal percDanno) {
		this.percDanno = percDanno;
	}
	public String getNoteDanno() {
		return noteDanno;
	}
	public void setNoteDanno(String noteDanno) {
		this.noteDanno = noteDanno;
	}
	
	public String getNoteDannoHtml() {
		return NemboUtils.STRING.safeHTMLText(noteDanno);
	}
	
}
