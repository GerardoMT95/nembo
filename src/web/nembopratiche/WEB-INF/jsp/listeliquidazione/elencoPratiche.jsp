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
		<div id="filter-bar2" style="position: relative; top: 46px"></div>
		<div class="responsive-table">
			<table id='elencoPratiche' summary="Elenco pratiche" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
				data-url="json/pratiche_nuova_lista_${idListaLiquidazione}.json" data-toggle="table" data-pagination="true"
				data-show-pagination-switch="true"
				data-pagination-v-align="top" data-show-columns="true" data-undefined-text='' data-row-style='rowStyleFormatter' data-checkbox-header="true">
				<thead>
					<tr>
						<th data-sortable="false" data-switchable="false"><a href="#" title="esporta in excel"
							onclick="forwardToPage('downloadExcelPratiche_${idListaLiquidazione}.xls')" class="ico24 ico_excel"></a>  </th>
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

	<div id="filtroJSON2" style="display:none">${filtroIniziale2}</div>

		<div class="col-sm-12">
				<a href="#" onclick="return false" id="chiudi" data-dismiss="modal" class="btn btn-default pull-left">chiudi</a>
		</div>
	</div>
</form:form>

<br />

<script type="text/javascript">

$("#popup_elenco_pratiche_nuova_lista").on('hidden.bs.modal', function () {
    $('#filter-bar').html('');
    $('#filter-bar').bootstrapTableFilter('resetView');
});


	$('body .modal-body').css('max-height', '500px');
	$('body .modal-body').css('overflow-y', 'scroll');
	$('#elencoPratiche').bootstrapTable();

	$(document).ready(function() {

		$('#filter-bar2').bootstrapTableFilter({
			filters : [  {
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
				var data2 = $('#filter-bar2').bootstrapTableFilter('getData');
				var elabFilter2 = JSON.stringify(data2);
				$.ajax({
					type : "POST",
					url : '../session/salvaFiltri.do',
					data : "key=elencoPraticheListaLiq&filtro=" + elabFilter2,
					success : function(data) {
					}
				});
			}
		});
		var filterJSON2 = $('#filtroJSON2').html();
		if (filterJSON2)
			$('#filter-bar2').bootstrapTableFilter("setupFilterFromJSON", filterJSON2);

	});
</script>
<br style="clear: left" />
<br />
