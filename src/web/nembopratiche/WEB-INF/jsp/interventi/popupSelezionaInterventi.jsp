<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<m:error/>
<form name="formPopupInterventi" id="formPopupInterventi" action="../cunembo${cuNumber}i/inserisci.do" method="post">
<c:choose>
<c:when test="${withDanni != null && withDanni == true}">
	<h4>Tipo danno</h4>
	<m:select id="slcListDanni" list="${listDanni}" name="slcListDanni" onchange="loadInterventiDanni();"></m:select>
	<input type="hidden" name="withDanni" id="withDanni" value="true" />
	<input type="hidden" name="idDannoAtm" id="idDannoAtm" />
	<br/> 
	<div id="dual-div">
	<div id="dual-list-box1" class="form-group row">
	<select style="display: none" id="interventiDualList" multiple="multiple" 
		data-title="Interventi" 
		data-value="idDescrizioneIntervento" 
		data-source=":URL"
		data-text="descrizioneIntervento" 
		data-addcombo="true" 
		data-labelcombo="Tipo intervento" 
		data-labelfilter="Filtro" 
		data-horizontal="false" 
		data-toggle="true">
	</select>
</div>
</div>
</c:when>
<c:otherwise>
	<div id="dual-div">
		<div id="dual-list-box1" class="form-group row">
		<select style="display: none" id="interventiDualList" multiple="multiple" 
			data-title="Interventi" 
			data-source="../cunembo${cuNumber}i/json/load_elenco_interventi.json"
			data-value="idDescrizioneIntervento" 
			data-text="descrizioneIntervento" 
			data-addcombo="true" 
			data-labelcombo="Tipo intervento" 
			data-labelfilter="Filtro" 
			data-horizontal="false" 
			data-toggle="true">
		</select>
	</div>
</div>
</c:otherwise>
</c:choose>

</form>
<input type="button" class="btn btn-default" data-dismiss="modal" value="annulla"/>
<input type="button" class="btn btn-primary pull-right" value="prosegui" onclick="onProsegui()" />
<script type="text/javascript">
  var dualDiv = document.getElementById('dual-div').innerHTML;
  var tmpHtmlDualList = dualDiv.replace(":URL","");
  $('#dual-div').html(tmpHtmlDualList);	
  var dualList = $('#interventiDualList').DualListBox();
  function onProsegui()
  {
    var idDannoAtmValue = null;
    if($('#idDannoAtm').length == 0)
    {
	    idDannoAtmValue = null;
    }
    else
    {
    	idDannoAtmValue = $('#idDannoAtm').val();
    }
    var formData = {idDescrizioneIntervento : new Array(), prosegui:'true', idDannoAtm : idDannoAtmValue};
    $('#dual-list-box-Interventi #selectedListHidden option').each(function(index) {
      formData.idDescrizioneIntervento[index] = $(this).val();
    });
    $.ajax({
      type: "POST",
      url: '../cunembo${cuNumber}i/popup_seleziona_interventi.do',
      data: $.param(formData, true),
      dataType: "text",
      async:false,
      success: function(data) 
      {
        $('#'+_lastModalID+" .modal-body").html(data);
      }
  });
  }
  /**/
</script>

<c:if test="${withDanni != null && withDanni == true}">
	<script type="text/javascript">
			var cuNumber = "${cuNumber}";
	  		//Descrizione danni come NClob, non ordinabile con order by
			$(function() {
			 	  // choose target dropdown
				  var select = $('#slcListDanni');
				  select.html(select.find('option').sort(function(x, y) {
				    return $(x).text().toLowerCase() > $(y).text().toLowerCase() ? 1 : -1;
				  }));
				  $('#slcListDanni').get(0).selectedIndex = 0;
			});
		
		function loadInterventiDanni()
		{
			var dannoSelezionato = $('#slcListDanni').val();
			var url=""
			if(dannoSelezionato!=null && dannoSelezionato != '')
			{
				url = "../cunembo"+cuNumber+"i/json/load_elenco_interventi_danno_" + dannoSelezionato + ".json"; 
 				$('#idDannoAtm').val(dannoSelezionato);
			}
			var htmlDualList = dualDiv.replace(":URL",url);
			$('#dual-div').html(htmlDualList);	
 			$('#interventiDualList').DualListBox();
		}
	</script> 
 </c:if>
