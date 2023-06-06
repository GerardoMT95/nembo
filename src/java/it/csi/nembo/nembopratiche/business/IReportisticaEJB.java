package it.csi.nembo.nembopratiche.business;

import java.util.List;

import javax.ejb.Local;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.ElencoQueryBandoDTO;
import it.csi.nembo.nembopratiche.dto.nuovoprocedimento.BandoDTO;
import it.csi.nembo.nembopratiche.dto.reportistica.GraficoVO;
import it.csi.nembo.nembopratiche.dto.reportistica.ParametriQueryReportVO;
import it.csi.nembo.nembopratiche.dto.reportistica.ReportVO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;

@Local
public interface IReportisticaEJB extends INemboAbstractEJB
{

  public BandoDTO getInformazioniBando(long idBando)
      throws InternalUnexpectedException;

  public GraficoVO getGrafico(long idElencoQuery, ParametriQueryReportVO params)
      throws InternalUnexpectedException;

  public List<DecodificaDTO<String>> elencoQueryBando(long attribute,
      Boolean true1, String string) throws InternalUnexpectedException;

  public boolean graficiTabellariPresenti(long idBando, String string)
      throws InternalUnexpectedException;

  public ReportVO getReportBando(ParametriQueryReportVO parametri)
      throws InternalUnexpectedException;

  public String getQueryParametroPagamenti() throws InternalUnexpectedException;

  boolean hasExcelTemplate(long idElencoQuery)
      throws InternalUnexpectedException;

  byte[] getExcelParametroDiElencoQuery(long idElencoQuery)
      throws InternalUnexpectedException;

  boolean hasExcelTemplateInElencoQuery(long idElencoQuery)
      throws InternalUnexpectedException;

  public List<ElencoQueryBandoDTO> getElencoReport(String extCodAttore)
      throws InternalUnexpectedException;

  public List<ElencoQueryBandoDTO> getElencoGrafici(String extCodAttore)
      throws InternalUnexpectedException;

}
