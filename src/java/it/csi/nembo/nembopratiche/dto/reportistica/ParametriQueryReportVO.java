package it.csi.nembo.nembopratiche.dto.reportistica;

import java.util.List;

public class ParametriQueryReportVO
{

  private long       idBando;
  private String     codEnteCaa;
  private String     istruzioneSQL;
  private long       idTipoVisualizzazione;
  private List<Long> lIdsProcedimenti;

  public long getIdBando()
  {
    return idBando;
  }

  public void setIdBando(Long idBando)
  {
    this.idBando = idBando;
  }

  public String getCodEnteCaa()
  {
    return codEnteCaa;
  }

  public void setCodEnteCaa(String codEnteCaa)
  {
    this.codEnteCaa = codEnteCaa;
  }

  public String getIstruzioneSQL()
  {
    return istruzioneSQL;
  }

  public void setIstruzioneSQL(String istruzioneSQL)
  {
    this.istruzioneSQL = istruzioneSQL;
  }

  public long getIdTipoVisualizzazione()
  {
    return idTipoVisualizzazione;
  }

  public void setIdTipoVisualizzazione(long idTipoVisualizzazione)
  {
    this.idTipoVisualizzazione = idTipoVisualizzazione;
  }

  public List<Long> getlIdsProcedimenti()
  {
    return lIdsProcedimenti;
  }

  public void setlIdsProcedimenti(List<Long> lIdsProcedimenti)
  {
    this.lIdsProcedimenti = lIdsProcedimenti;
  }

}
