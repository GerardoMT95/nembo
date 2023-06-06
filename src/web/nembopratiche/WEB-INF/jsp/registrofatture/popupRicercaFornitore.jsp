<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<div>
	<m:error />
	<div class="row" id="firstRow">
		<span style="font-size: 12px; font-style: italic; padding-left: 30px; padding-bottom: 15 px">Selezionare il fornitore filtrando per CUAA o
			per Denominazione</span>
	</div>
	<div class="col-md-8 form-horizontal">
		<label>CUAA</label>
		<m:textfield id="cuaa" name="filtroCUAA" />
		<label>Denominazione</label>
		<m:textfield id="denominazione" name="filtroDENOMINAZIONE" />
				<br /> <input type="button" name="conferma" id="conferma" onclick="cercafornitori()" class="btn btn-primary" value="cerca">	
	</div>

	<div class="col-md-12" style="margin-left: 15em;">
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

	<div id="fornitori" style="display: none;padding-top:15em;">
		<div class="col-md-12" id="elencofornitorilabel">
			<label>Elenco fornitori</label>
		</div>
		<div class="col-md-12" id="elencofornitori">
			<m:list name="fornitori" id="fornitori" list="${listaFornitori}" multipleSelection="false"></m:list>
		</div>
	</div>
	<br /> <br />
</div>

<br style="clear: left" />
<m:include-scripts />
<script type="text/javascript">

	function selezionaFornitore($this,id, codice, indirizzo){
		
		$("#idFornitore").val(id);
		var res = codice;
		if(indirizzo!==undefined && indirizzo!=null && indirizzo!="" && indirizzo!="null")
			res += " - " +indirizzo;
		$("#fornitore").val(res);
		$("#dlgInserisci").modal('hide');
		}
		
	function cercafornitori() {
		$('.stdMessagePanel').remove();
		var $cuaa = $('#cuaa').val();
		var $den = $('#denominazione').val();

		var $listSportelli = $('#fornitori');
		if ($cuaa == '' && $den == '') {
			$listSportelli.empty();
			return;
		}

		if ($cuaa == '' && $den == '') {
			$listSportelli.empty();
			return;
		}	
		$('#fornitori').hide();
		$('#loadingdiv').show();

		var page = "ricerca_fornitori.json";
		$.ajax({
			url : page,
			type : "POST",
			data : "den=" + $den + "&cuaa=" + $cuaa,
			async : true
		}).success(
				function(data) {
					$('#fornitori').show();
					$('#loadingdiv').hide();
					$listSportelli.empty();

					if ($(data).length <= 0) {
						$('#firstRow').before(
								"<div class=\"stdMessagePanel\">" + " <div class=\"alert alert-danger\">"
										+ "  <p>La ricerca non ha prodotto alcun risultato</p>" + " </div>" + " </div>");
					}

					$(data).each(
							function(index, sportello) {

								if(sportello.descrizione == null)
									sportello.descrizione = "";
								var text = sportello.codice;
								if(sportello.descrizione!==undefined && sportello.descrizione!=null && sportello.descrizione!="")
									text += " - " +sportello.descrizione;
								myList_addItem($listSportelli, text, ' ',
										"return selezionaFornitore(this,'" + sportello.id + "', '" + sportello.codice.replace(/'/g, "\\'") + "', '" + sportello.descrizione.replace(/'/g, "\\'") + "')");
							});
				}).error(function() {
					showMessageBox("Errore", 'Errore nel caricamento dei fornitori', "modal-large");
		});

	}
</script>

