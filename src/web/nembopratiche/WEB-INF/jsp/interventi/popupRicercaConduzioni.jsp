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
	<table id="tblRicercaConduzioni" class="table table-hover tableBlueTh" data-toggle="table" data-url="json/ricerca_conduzioni${superficie}.json"
		data-undefined-text='' data-checkbox-header="true">
		<thead>
			<tr>
				<th data-field="id" data-formatter="stateFormatterConduzione"><div style="text-align: center !important">
						<input type="checkbox" onclick="$('input[name=idChiaveConduzione]').prop('checked',this.checked)" />
					</div></th>
				<th class="alignRight" data-field="particella">Part.</th>
				<th data-field="subalterno">Sub.</th>
				<th class="alignRight" data-field="supCatastale">Sup.<br />catastale
				</th>
				<th data-field="descTipoUtilizzo" title="Occupazione del suolo" data-valign="middle">Occupazione<br />del suolo
				</th>
				<th data-field="descrizioneDestinazione" data-title="Destinazione" title="Destinazione" data-valign="middle"></th>
				<th data-field="descTipoDettaglioUso" title="Uso" data-valign="middle">Uso</th>
				<th data-field="descrizioneQualitaUso" title="Qualit&agrave;" data-valign="middle">Qualit&agrave;</th>
				<th data-field="descTipoVarieta" title="Variet&agrave;" data-valign="middle">Variet&agrave;</th>
				<th class="alignRight" data-field="superficieUtilizzata">Sup.<br />utilizzata
				</th>
		</thead>
	</table>
	<br />
</div>
<br />
<br />
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla" />
<input type="button" class="btn btn-primary pull-right" value="aggiungi" onclick="onAggiungiUtilizzo()" />
<script type="text/javascript">
  function onAggiungiUtilizzo()
  {
    var data = $('#tblRicercaConduzioni').bootstrapTable("getData");
    var chk = [];
    var checkedCheckboxes = $("input[name='idChiaveConduzione']:checked");
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
      var id = row['id'];
      if (chk[id])
      {
        row['superficieImpegno'] = row['superficieUtilizzata'];
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
        $('#filtroRicercaConduzioni_comune').html(firstRow['descComune']);
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
    return [ '<div style="text-align:center !important"><input type="checkbox" title="" value="'+$value+'" name = "idChiaveConduzione"   /></div>' ];
  }
</script>
