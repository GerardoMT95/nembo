<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<form:form id="formPratiche">

	<div class="container-fluid" id="content">
		<div id="filter-bar" style="position: relative; top: 46px"></div>
		<div class="responsive-table">
			<table id='elencoPratiche' summary="Elenco pratiche" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
				data-url="json/pratiche_nuova_lista_${idBando}.json" data-toggle="table" data-pagination="false" data-show-pagination-switch="false"
				data-pagination-v-align="top" data-show-columns="true" data-undefined-text='' data-row-style='rowStyleFormatter' data-checkbox-header="true">
				<thead>
					<tr>
						<th class="vcenter center" data-sortable="false" data-visible="true" data-switchable="false" data-formatter="checkboxFormatter"><input
							id="slctAll" type="checkbox" onclick="$('input[name=idPOCheck]').prop('checked',this.checked)"><a href="#" title="esporta in excel"
							onclick="forwardToPage('downloadExcelPratiche.xls')" class="ico24 ico_excel"></a></th>
						<th class="vcenter center" data-sortable="false" data-class="" data-field="" data-visible="true" data-formatter="anomaliaFormatter"></th>
						<th class="vcenter center" data-sortable="true" data-class="" data-field="cuaa" data-visible="true">CUAA</th>
						<th class="vcenter center" data-sortable="true" data-class="" data-field="denominazione" data-visible="true">Denominazione</th>
						<th class="vcenter center" data-sortable="true" data-class="center" data-field="identificativo" data-visible="true">Numero <br />procedimento
						</th>
						<th class="vcenter center" data-sortable="true" data-class="" data-field="codiceLivello" data-visible="true">Operazione</th>
						<th class="vcenter center" data-sortable="true" data-class="" data-field="causalePagamento" data-visible="true">Causale<br />pagamento
						</th>
						<th class="vcenter center" data-sortable="true" data-class="" data-field="importoLiquidato" data-visible="true"
							data-formatter="numberFormatter2">Importo<br />Liquidato
						</th>
						<c:if test="${isBandoPremio}">
							<th class="vcenter center" data-sortable="true" data-class="" data-field="importoPremio" data-visible="true" data-formatter="numberFormatter2">Importo<br />Premio
							</th>
							<th class="vcenter center" data-sortable="true" data-class="" data-field="anticipoErogato" data-visible="true"
								data-formatter="numberFormatter2">Anticipo<br />Erogato
							</th>
						</c:if>
					</tr>
				</thead>
			</table>
		</div>
		<br /> <br />
		<div id="filtroJSON" style="display: none">${filtroIniziale}</div>

		<div class="col-sm-12">
				<a href="#" onclick="return false" id="chiudi" data-dismiss="modal" class="btn btn-default pull-left">chiudi</a>
			<a href="#" onclick="escludiPratiche(${idBando});return false;" class="btn btn-primary pull-right">escludi pratiche</a>
		</div>
	</div>
</form:form>

<br />

<script type="text/javascript">
	$('body .modal-body').css('max-height', '500px');
	$('body .modal-body').css('overflow-y', 'scroll');
	$('#elencoPratiche').bootstrapTable();

	function checkboxFormatter($value, row, index) {

		
		var ret =  '<div style="text-align:center"><input type="checkbox" title=""';
		if(row['checked'])
			ret+='checked';
		ret+=' value="'+row['idProcedimentoOggetto']+'" name = "idPOCheck" /></div>';

 		return ret; 
	}

	function anomaliaFormatter($value, row, index) {
		if (row['anomalia'].indexOf("S") != -1)
			return '<div style="text-align:center"><img src="../img/../img/ico_cancel.gif"></div>';
		else
			return "";
	}

	function escludiPratiche(idBando){

		var lenSelected = $("input[name='idPOCheck']:checked").length;
		if (lenSelected > 0) {
			//controllo che non ci siano pratichce non selezionate relative allo stesso id_procedimento_oggetto
			var i=0;
			for(i=0;i<lenSelected;i++){
				var selectedVal = $("input[name='idPOCheck']:checked")[i].value;
				
				var unChecked = $("input[name='idPOCheck'][value='"+selectedVal+"']:not(:checked)").length;
				if(unChecked>0)
					{
						showMessageBox("Attenzione", "Attenzione sono stati selezionati pagamenti parziali riferiti alla stessa pratica, non è possibile escludere parzialmente il pagamento di una pratica.", "modal-large")
						return;
						}
				}	
		}
		submitFormTo($('#formPratiche'), '../cunembo226/escludiPratiche_'+idBando+'.do');

		$("#chiudi").click();
		}

	$(document).ready(function() {

		$('#filter-bar').bootstrapTableFilter({
			filters : [ {
				field : 'anomalia',
				label : 'Anomalia',
				type : 'selectNoSearch',
				values : [ {
					id : 'S',
					label : '<img src="../img/../img/ico_cancel.gif">'
				}, {
					id : 'N',
					label : 'Nessuna anomalia'
				} ],
			}, {
				field : 'cuaa',
				label : 'Cuaa',
				type : 'search'
			}, {
				field : 'denominazione',
				label : 'Denominazione',
				type : 'search'
			}, {
				field : 'identificativo',
				label : 'Numero procedimento',
				type : 'search'
			}, {
				field : 'causalePagamento',
				label : 'Causale pagamento',
				type : 'search'
			} ],
			connectTo : '#elencoPratiche',
			onSubmit : function() {
				var data = $('#filter-bar').bootstrapTableFilter('getData');
				var elabFilter = JSON.stringify(data);
				$.ajax({
					type : "POST",
					url : '../session/salvaFiltri.do',
					data : "key=elencoPraticheListaLiq&filtro=" + elabFilter,
					success : function(data) {
					}
				});
			}
		});
		var filterJSON = $('#filtroJSON').html();
		if (filterJSON)
			$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON", filterJSON);

	});
</script>
<br style="clear: left" />
<br />
