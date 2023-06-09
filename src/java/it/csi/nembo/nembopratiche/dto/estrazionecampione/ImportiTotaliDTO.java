package it.csi.nembo.nembopratiche.dto.estrazionecampione;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class ImportiTotaliDTO implements ILoggable
{

  private static final long         serialVersionUID = 5918223009553272325L;

  private long                      idNumeroLotto;
  private BigDecimal                impTotaleComplessivo;
  private BigDecimal                impTotaleAttuale;
  private Date                      dataEstrazioneAttuale;
  private List<DettaglioImportoDTO> elencoDettagliImporti;
  private List<DettaglioImportoDTO> elencoDettagliImportiMisure;

  public List<DettaglioImportoDTO> getElencoDettagliImportiMisure()
  {
    return elencoDettagliImportiMisure;
  }

  public void setElencoDettagliImportiMisure(
      List<DettaglioImportoDTO> elencoDettagliImportiMisure)
  {
    this.elencoDettagliImportiMisure = elencoDettagliImportiMisure;
  }

  public long getIdNumeroLotto()
  {
    return idNumeroLotto;
  }

  public void setIdNumeroLotto(long idNumeroLotto)
  {
    this.idNumeroLotto = idNumeroLotto;
  }

  public BigDecimal getImpTotaleComplessivo()
  {
    return impTotaleComplessivo;
  }

  public String getImpTotaleComplessivoStr()
  {
    return (impTotaleComplessivo == null) ? ""
        : NemboUtils.FORMAT.formatCurrency(impTotaleComplessivo);
  }

  public void setImpTotaleComplessivo(BigDecimal impTotaleComplessivo)
  {
    this.impTotaleComplessivo = impTotaleComplessivo;
  }

  public BigDecimal getImpTotaleAttuale()
  {
    return impTotaleAttuale;
  }

  public String getImpTotaleAttualeStr()
  {
    return (impTotaleAttuale == null) ? ""
        : NemboUtils.FORMAT.formatCurrency(impTotaleAttuale);
  }

  public void setImpTotaleAttuale(BigDecimal impTotaleAttuale)
  {
    this.impTotaleAttuale = impTotaleAttuale;
  }

  public Date getDataEstrazioneAttuale()
  {
    return dataEstrazioneAttuale;
  }

  public String getDataEstrazioneAttualeStr()
  {
    return (dataEstrazioneAttuale != null)
        ? NemboUtils.DATE.formatDate(dataEstrazioneAttuale) : "";
  }

  public void setDataEstrazioneAttuale(Date dataEstrazioneAttuale)
  {
    this.dataEstrazioneAttuale = dataEstrazioneAttuale;
  }

  public List<DettaglioImportoDTO> getElencoDettagliImporti()
  {
    return elencoDettagliImporti;
  }

  public void setElencoDettagliImporti(
      List<DettaglioImportoDTO> elencoDettagliImporti)
  {
    this.elencoDettagliImporti = elencoDettagliImporti;
  }

}
