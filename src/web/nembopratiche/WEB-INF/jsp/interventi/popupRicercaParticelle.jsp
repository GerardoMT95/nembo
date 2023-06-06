<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<div>
<m:error />
<div class="col-md-12">
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla" />
<input type="button" class="btn btn-primary pull-right" value="aggiungi" onclick="onAggiungiParticelle()" />
</div>
<br/><br />
<div class="col-md-12">
	<table id="tblRicercaParticelle" class="table table-hover tableBlueTh" data-toggle="table" data-url="json/ricerca_particelle.json" data-undefined-text = ''
	 data-checkbox-header="true">
		<thead>
			<tr>
				<th data-field="idParticellaCertificata" data-formatter="stateFormatterConduzione"><div style="text-align:center !important"><input type="checkbox" onclick="$('input[name=idParticellaCertificata]').prop('checked',this.checked)"/></div></th>
				<th data-field="descComune">Comune</th>
				<th data-field="sezione">Sezione</th>
				<th class="alignRight" data-field="foglio">Foglio</th>
				<th class="alignRight" data-field="particella">Particella</th>
				<th data-field="subalterno">Subalterno</th>
				<th class="alignRight" data-field="supCatastale">Sup. catastale</th>
		</thead>
	</table>
	<br />
	</div>
<br />
<br />
<div class="col-md-12">
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla" />
<input type="button" class="btn btn-primary pull-right" value="aggiungi" onclick="onAggiungiParticelle()" />
</div>
</div>
<br style="clear:left" />
<script type="text/javascript">
function onAggiungiParticelle()
{
  var data = $('#tblRicercaParticelle').bootstrapTable("getData");
  var chk=[];
  var checkedCheckboxes=$("input[name='idParticellaCertificata']:checked");
  if (checkedCheckboxes.length==0)
  {
    alert('Selezionare almeno un elemento');
    return false;
  }
  checkedCheckboxes.each(function(index, value)
  {
    var val=$(value).val();
    chk[val]=val;
  });
  var json=Array();
  $.each(data, function(index, row)
  {
    var id=row['idParticellaCertificata'];
    if (chk[id])
    {
      json.push(row);
    }
  });
  closeModal();
  var data = $('#tblParticelle').bootstrapTable("append", json);
}
function stateFormatterConduzione($value, row, index) {
  return [
            '<div style="text-align:center !important"><input type="checkbox" title="" value="'+$value+'" name = "idParticellaCertificata"   /></div>'
        ];
  }
</script>
