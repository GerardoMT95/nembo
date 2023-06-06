<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
	<div class="row" id="firstRow">
	<span style="font-size:12px;font-style:italic;padding-left:30px;padding-bottom:8px">Selezionare il fornitore filtrando epr Partita IVA o Ragione Sociale</span>
	</div>
	<div class="col-md-8 form-horizontal">
		<label>Partita IVA</label>
		<m:textfield id="filtroPIVA" name="filtroPIVA" />
	</div>
	<!-- /btn-group -->
</div>
<div class="col-md-4; padding-top:1em"><br/>
	<button type="button" name="conferma" id="conferma" onclick="cercafornitori()" class="btn btn-primary">cerca</button>
</div>

<div class="col-md-12" style="margin-left: 15em;margin-top: 2em;">
	<div id="loadingdiv" style="display: none">
		<div id="floatBarsG_1" class="floatBarsG"></div>
		<div id="floatBarsG_2" class="floatBarsG"></div> 
		<div id="floatBarsG_3" class="floatBarsG"></div>
		<div id="floatBarsG_4" class="floatBarsG"></div>
		<div id="floatBarsG_5" class="floatBarsG"></div>
		<div id="floatBarsG_6" class="floatBarsG"></div>
		<div id="floatBarsG_7" class="floatBarsG"></div>
		<div id="floatBarsG_8" class="floatBarsG"></div>
	</div>
</div>

<div class="col-md-12"  id="elencofornitorilabel">
	<label>Elenco fornitori</label>   
</div>
<div class="col-md-12" id="elencofornitori">
	<m:list name="fornitori" id="fornitori" list="${listaFornitori}" multipleSelection="true" ></m:list>
</div>
<br />
<br />
<br style="clear: left" />
<m:include-scripts/>
<script type="text/javascript">
	function cercafornitori()
	{
			$('.stdMessagePanel').remove();
		var $val=$('#filtroPIVA').val();
	    var $listSportelli=$('#fornitori');
	    if ($val=='')
	    {
	      $listSportelli.empty();
	      return;
	    }

		$('#elencofornitori').hide();
		$('#elencofornitorilabel').hide();
		$('#loadingdiv').show();

		var page="ricerca_fornitori.json";
	    $.ajax({
	      url: page,
	      type: "POST",
	      data: "piva="+$val,
	      async:true
	      }).success(function(data) 
	        {
	    	  $('#elencofornitori').show();
	    	  $('#elencofornitorilabel').show();
	  		  $('#loadingdiv').hide();
	          $listSportelli.empty();

	          if($(data).length <=0)
		      {
	        	  $('#firstRow').before(
	        	  "<div class=\"stdMessagePanel\">"+	
		            " <div class=\"alert alert-danger\">"+
		               "  <p>Non è stato trovato nessun fornitore!</p>"+
		             " </div>"+
		         " </div>");
			  }
	          
	          $(data).each(function(index, sportello)
	          {
	            myList_addItem($listSportelli,sportello.codice+" - "+ sportello.descrizione, sportello.id, "return selezionaFornitore(this,'"+sportello.id+"')");
	          });
	      }).error(function(){alert('Errore nel caricamento dei fornitori')});
		
	}


</script>

