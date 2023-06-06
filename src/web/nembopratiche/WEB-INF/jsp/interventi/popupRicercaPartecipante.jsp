<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
  <div class="row">
	<div class="col-md-4">
		<label>Cuaa</label>
		<m:textfield id="partCuaa" name="partCuaa"/>
	</div>
	<div class="col-md-5">
		<label>Denominazione</label>
		<m:textfield id="partDenominazione" name="partDenominazione"/>
	</div>
	<div class="col-md-3">
		<button type="button" name="btncercacomune" id="btncercacomune" style="margin-top:1.8em" onclick="ricercaComuni()"
						class="btn btn-primary">cerca</button>
	</div>
  </div>
  <br>
  <div class="row">
  <div class="col-md-12">
	<label>Elenco partecipanti</label>
	</div>
	</div>
	<div class="row">
	<div class="col-md-12" >
	  <m:list name="partecipanti" id="partecipanti" list=""></m:list>
	</div>
	</div>
	<br /> <br />
</div>
<br style="clear: left" />
<m:include-scripts/>
<script type="text/javascript">

	$( document ).ready(function() {
		popolaListPartecipanti('','');
	});

  function ricercaComuni()
  {
    var $listComuni=$('#partecipanti');
    $val = $('#partCuaa').val();
    if ($val=='')
    {
    	$val = $('#partDenominazione').val();
    }

    if ($val=='')
    {
      $listComuni.empty();
      return;
    }

    popolaListPartecipanti($('#partCuaa').val(), $('#partDenominazione').val());
    }

  function popolaListPartecipanti(cuaa, denominazione){
	  
	  var page="../cunembo171l/getElencoPartecipantiFiltrati.json";
	  var $listComuni=$('#partecipanti');
	  $listComuni.empty();
	    $.ajax({
	        type: "POST",
	        url: page,
	        data: "cuaa="+cuaa+"&denominazione="+denominazione,
	        async:false,
	    }).success(function(data) {
	        	$listComuni.empty();

	        	if(data!=null && data.indexOf('errore')<0)
	        	{
		            $(data).each(function(index, partecipante)
		            {
		              myList_addItem($listComuni,partecipante.cuaa + " - "+partecipante.denominazione, partecipante.idPartecipante, "return selezionaPartecipante(this,'"+partecipante.cuaa+"')");
		            });
	        	}
	        }).error(function(){alert('Errore nel caricamento dei partecipanti ')});
  }
</script>
