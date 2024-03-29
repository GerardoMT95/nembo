package it.csi.nembo.nembopratiche.dto.reportistica;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ReportVO implements Serializable
{
  private static final long  serialVersionUID = 9104726695231817976L;

  private List<ColReportVO>  colsDefinitions;
  private List<RowsReportVO> rowValues;

  public HashMap<String, Object> getJSON()
  {
    HashMap<String, Object> json = new HashMap<>();
    json.put("cols", colsDefinitions);
    json.put("rows", rowValues);
    return json;
  }

  public HashMap<String, Object> createExampleJson()
  {
    ColReportVO colReportVO = null;
    CellReportVO cellReportVO = null;
    List<CellReportVO> listRow = null;
    RowsReportVO rowsReportVO = null;

    colsDefinitions = new ArrayList<>();
    rowValues = new ArrayList<>();

    // Colums definitions
    colReportVO = new ColReportVO();
    colReportVO.setId("A");
    colReportVO.setLabel("Task");
    colReportVO.setType(ColReportVO.TYPE_STRING);
    colsDefinitions.add(colReportVO);

    colReportVO = new ColReportVO();
    colReportVO.setId("B");
    colReportVO.setLabel("Hours per Day");
    colReportVO.setType(ColReportVO.TYPE_NUMBER);
    colsDefinitions.add(colReportVO);

    // Rows definitions
    rowsReportVO = new RowsReportVO();
    listRow = new ArrayList<>();
    cellReportVO = new CellReportVO();
    cellReportVO.setV("Work");
    listRow.add(cellReportVO);
    cellReportVO = new CellReportVO();
    cellReportVO.setV(11L);
    listRow.add(cellReportVO);
    rowsReportVO.addRowReport(listRow);
    rowValues.add(rowsReportVO);

    rowsReportVO = new RowsReportVO();
    listRow = new ArrayList<>();
    cellReportVO = new CellReportVO();
    cellReportVO.setV("Eat");
    listRow.add(cellReportVO);
    cellReportVO = new CellReportVO();
    cellReportVO.setV(2L);
    listRow.add(cellReportVO);
    rowsReportVO.addRowReport(listRow);
    rowValues.add(rowsReportVO);

    rowsReportVO = new RowsReportVO();
    listRow = new ArrayList<>();
    cellReportVO = new CellReportVO();
    cellReportVO.setV("Commute");
    listRow.add(cellReportVO);
    cellReportVO = new CellReportVO();
    cellReportVO.setV(2L);
    listRow.add(cellReportVO);
    rowsReportVO.addRowReport(listRow);
    rowValues.add(rowsReportVO);

    rowsReportVO = new RowsReportVO();
    listRow = new ArrayList<>();
    cellReportVO = new CellReportVO();
    cellReportVO.setV("In bozza");
    listRow.add(cellReportVO);
    cellReportVO = new CellReportVO();
    cellReportVO.setV(7L);
    listRow.add(cellReportVO);
    rowsReportVO.addRowReport(listRow);
    rowValues.add(rowsReportVO);

    return getJSON();
  }

  public List<ColReportVO> getColsDefinitions()
  {
    return colsDefinitions;
  }

  public void setColsDefinitions(List<ColReportVO> colsDefinitions)
  {
    this.colsDefinitions = colsDefinitions;
  }

  public List<RowsReportVO> getRowValues()
  {
    return rowValues;
  }

  public void setRowValues(List<RowsReportVO> rowValues)
  {
    this.rowValues = rowValues;
  }

}
