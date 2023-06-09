package it.csi.nembo.nembopratiche.dto.procedimentooggetto.pianografico;

import java.math.BigDecimal;
import java.util.List;

import it.csi.nembo.nembopratiche.dto.internal.ILoggable;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class InterventoImpegnoVO implements ILoggable
{

  private static final long         serialVersionUID = -8064889027606525236L;

  private String                    descrImpegno;
  private BigDecimal                supImpegno;
  private BigDecimal                supPredisposizImpegno;
  private List<InterventoGraficoVO> interventi;

  public String getDescrImpegno()
  {
    return descrImpegno;
  }

  public void setDescrImpegno(String descrImpegno)
  {
    this.descrImpegno = descrImpegno;
  }

  public BigDecimal getSupImpegno()
  {
    return supImpegno;
  }

  public void setSupImpegno(BigDecimal supImpegno)
  {
    this.supImpegno = supImpegno;
  }

  public List<InterventoGraficoVO> getInterventi()
  {
    return interventi;
  }

  public void setInterventi(List<InterventoGraficoVO> interventi)
  {
    this.interventi = interventi;
  }

  public BigDecimal getSupPredisposizImpegno()
  {
    return supPredisposizImpegno;
  }

  public String getSupPredisposizImpegnoStr()
  {
    return NemboUtils.FORMAT.formatDecimal4(supPredisposizImpegno);
  }

  public String getSupImpegnoStr()
  {
    return NemboUtils.FORMAT.formatDecimal4(supImpegno);
  }

  public void setSupPredisposizImpegno(BigDecimal supPredisposizImpegno)
  {
    this.supPredisposizImpegno = supPredisposizImpegno;
  }
}
