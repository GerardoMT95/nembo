<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error/>
<div>
<form name="formPopupInterventi" id="formPopupInterventi" action="../cunembo${cuNumber}i/inserisci.do" method="post">
<div id="dual-list-box1" class="form-group row">
	<select style="display: none" id="interventiDualList" multiple="multiple" 
	data-title="Interventi" data-source="../cunembo${cuNumber}i/json/load_elenco_interventi.json"
		data-value="idDescrizioneIntervento" data-text="descrizioneIntervento" data-addcombo="true" data-labelcombo="Tipo intervento" 
		data-labelfilter="Filtro" data-horizontal="false" data-toggle="true"></select>
</div>
</form>
<br>
<input type="button" class="btn btn-default" data-dismiss="modal" value="indietro" onclick="forwardToPage('../cunembo266i/index.do');"/>
<input type="button" class="btn btn-primary pull-right" value="avanti" onclick="onProsegui()" />
</div>
<script type="text/javascript">
  function onProsegui()
  {
    var formData = {idDescrizioneIntervento : new Array(), prosegui:'true' };
    $('#dual-list-box-Interventi #selectedListHidden option').each(function(index) {
      formData.idDescrizioneIntervento[index] = $(this).val();
    });
    $.ajax({
        type: "POST",
        url: '../cunembo${cuNumber}i/selezionaInterventi.do',
        data: $.param(formData, true),
        dataType: "text",
        async:false,
        success: function(data) 
        {
          if(data.indexOf("error")!=-1)
              $("#divErrore").show();
          else
             {
        	  $(location).attr('href', '../cunembo${cuNumber}i/goToStep3.do')
              }
        }
    });
  }
  /**/
</script>
