<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info />
<m:error />
<div class="col-md-12">
	<m:panel id="filtroRicercaConduzioni_panel">
		<div class="col-md-1">
			<label class="control-label">Comune:</label>
		</div>
		<div class="col-md-6" id="filtroRicercaConduzioni_comune"></div>
		<div class="col-md-1">
			<label class="control-label">Sezione:</label>
		</div>
		<div class="col-md-2" id="filtroRicercaConduzioni_sezione"></div>
		<div class="col-md-1">
			<label class="control-label">Foglio:</label>
		</div>
		<div class="col-md-1" id="filtroRicercaConduzioni_foglio"></div>
	</m:panel>
	
	<c:set var ="tableName"  value ="tblRicercaConduzioni"/>
	<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
	<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
	<table id="${tableName}"
		class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
		data-toggle="table"
		data-url="elenco_particelle_danni_${piantagioniArboree}.json"
		data-pagination="false" 
		data-only-info-pagination="false"
		data-show-pagination-switch="false" 
		data-pagination-v-align="top"
		data-table-name="${tableName}"
	>
		<thead>
			<tr>
			<th data-field="idUtilizzoDichiarato" data-formatter="idUtilizzoDichiaratoFormatter">
				<input type="checkbox" onclick="$('input[name=chkIdUtilizzoDichiarato]').prop('checked',this.checked)" />
			</th>
			<th data-field="particella" data-sortable="true">Part.<br/></th>
			<th data-field="subalterno" data-sortable="true">Sub.<br/></th>
			<th data-field="supCatastale" data-sortable="true">Sup.<br/>catastale</th>
			<th data-field="occupazioneSuolo" data-sortable="true">Occup.<br/>del<br/>suolo</th>
			<th data-field="destinazione" class="alignRight" data-sortable = "true">Dest.</th>
			<th data-field="uso" data-sortable="true">Uso</th>
			<th data-field="qualita" data-sortable="true">Qualit&agrave;</th>
			<th data-field="varieta" data-sortable="true">Variet&agrave;</th>
			<th data-field="superficieUtilizzata" data-sortable="true">Sup.<br/>util.</th>
		</tr>
		</thead>
		<tbody></tbody>
	</table>
	<br />
</div>
<br />
<br />
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla" />
<input type="button" class="btn btn-primary pull-right" value="aggiungi" onclick="onAggiungiUtilizzo()" />
<script type="text/javascript">
  
  function idUtilizzoDichiaratoFormatter($value, row, index)
  {
    return [ '<div style="text-align:center !important"><input type="checkbox" title="" value="'+$value+'" name = "chkIdUtilizzoDichiarato"/></div>' ];
  }
  
  function onAggiungiUtilizzo()
  {
    var data = $('#tblRicercaConduzioni').bootstrapTable("getData");
    var chk = [];
    var checkedCheckboxes = $("input[name='chkIdUtilizzoDichiarato']:checked");
    if (checkedCheckboxes.length == 0)
    {
      alert('Selezionare almeno un elemento');
      return false;
    }
    checkedCheckboxes.each(function(index, value)
    {
      var val = $(value).val();
      chk[val] = val;
    });
    var json = Array();
    $.each(data, function(index, row)
    {
      var id = row['idUtilizzoDichiarato'];
      if (chk[id])
      {
        json.push(row);
      }
    })
    closeModal();
    var data = $('#tblConduzioni').bootstrapTable("append", json);
  }
  $('#tblRicercaConduzioni').off('load-success.bs.table');
  $('#tblRicercaConduzioni').on('load-success.bs.table', function()
  {
    $(function()
    {
      var $tblRicercaConduzioni = $('#tblRicercaConduzioni');
      var tableData = $tblRicercaConduzioni.bootstrapTable('getData');
      if (tableData.length)
      {
        var firstRow = tableData[0];
        $('#filtroRicercaConduzioni_comune').html(firstRow['comune']);
        var sezione = firstRow['sezione'];
        if (sezione==null)
        {
          sezione = 'Non presente';
        }
        $('#filtroRicercaConduzioni_sezione').html(sezione);
        $('#filtroRicercaConduzioni_foglio').html(firstRow['foglio']);
        $('#filtriRicercaConduzioni').show();
      }

    });
  });
  function stateFormatterConduzione($value, row, index)
  {
    return [ '<div style="text-align:center !important"><input type="checkbox" title="" value="'+$value+'" name = "idUtilizzoDichiarato"   /></div>' ];
  }
</script>
