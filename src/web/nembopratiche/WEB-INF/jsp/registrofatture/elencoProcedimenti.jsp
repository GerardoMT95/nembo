<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
span.tab-space {
	padding-left: 0.4em;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="index.do">Ricerca documenti</a><span class="divider">/</span></li>
					<li class="active">Elenco procedimenti</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<form:form action="" modelAttribute="filtroAziendeForm" method="post">
		<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoProcedimentiDoc")}'>
	</form:form>
	<div class="container-fluid" id="content" style="margin-bottom: 3em; position: relative">
		<h3>Registro fatture</h3>
		<m:panel title="Elenco procedimenti" id="panelDocumenti" collapsible="false">

			<div id="filter-bar" style="position: relative; top: 46px"></div>
			<table id="elencoProcedimentiDoc" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " data-toggle="table"
				data-url="getElencoProcedimenti.json" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top"
				data-show-columns="true" data-undefined-text='' data-show-toggle="true" data-page-list="[10, 25, 50, 100, 250, 500]"
				data-page-number="${sessionMapNumeroPagina.get('elencoProcedimentiDoc') != null ? sessionMapNumeroPagina.get('elencoProcedimentiDoc') : '1'}"
				data-detail-formatter="detailFormatter" data-detail-view="true">
				<thead>
					<tr>
						<th data-field="idProcedimento" data-visible="true" data-switchable="false" data-formatter="linkFormatter"></th>
						<th data-field="identificativo" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','identificativo')}">Identificativo</th>
						<th data-field="denominazioneBando" data-sortable="true"
							data-visible="${colonneNascoste.hide('elencoProcedimentiDoc','denominazioneBando', true)}">Bando</th>
						<th data-field="annoCampagna" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','annoCampagna')}">Anno<br />campagna
						</th>
						<th data-field="livelliHtml" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','elencoLivelli')}">Operazione</th>
						<th data-field="ammCompetenza" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','ammCompetenza')}">Organismo<br />Delegato
						</th>
						<th data-field="cuaaBeneficiario" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','cuaaBeneficiario')}">CUAA</th>
						<th data-field="denominazioneBeneficiario" data-sortable="true"
							data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','denominazioneBeneficiario')}">Denominazione</th>
						<th data-field="denominazioneDelega" data-visible="${!colonneNascoste.visible('elencoProcedimentiDoc','denominzioneDelega')}">CAA
						</th>
					</tr>
				</thead>
			</table>
		</m:panel>
		<div class="col-sm-12 ">
			<div class="puls-group" style="margin-top: 2em">
				<div class="pull-left">
					<button type="button" onclick="forwardToPage('index.do');" class="btn btn-default">indietro</button>
				</div>
			</div>
		</div>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
		showPleaseWait('elencoProcedimentiDoc');

		function linkFormatter(index, row) {
			var idProcedimento = row['idProcedimento'];
			return "<a href=\"../cunembo129/indexFromRegistroFatture_"+idProcedimento+".do\" title=\"Visualizza oggetti\" class=\"glyphicon glyphicon-list\"></a>";
		}

		function detailFormatter(index, row) {

			var html = [];

			html
					.push('<div style="padding-top:1em;padding-bottom:1em;padding-left:1em;padding-right:1em;"><table id="ordinamenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh">');
			html.push('<thead>');
			html.push('<tr>');
			html.push('<th><span class="tab-space">Tipo di domanda</span></th>');
			html.push('<th><span class="tab-space">Progressivo</span></th>');
			html.push('<th><span class="tab-space">Importo rendicontato</span></th>');
			html.push('<th><span class="tab-space">Fornitore</span></th>');
			html.push('<th><span class="tab-space">Data documento</span></th>');
			html.push('<th><span class="tab-space">Numero documento</span></th>');
			html.push('<th><span class="tab-space">Tipo documento</span></th>');
			html.push('<th><span class="tab-space">Modalita di pagamento</span></th>');
			html.push('<th><span class="tab-space">Data pagamento</span></th>');
			html.push('<th><span class="tab-space">Importo documento</span></th>');
			html.push('</tr>');
			html.push('</thead>');
			html.push('<tbody>');
			for (i = 0; i < row['documentiSpesa'].length; i++) {
				var pratica = row['documentiSpesa'][i];
				html.push('<tr>');
				if (pratica.tipoDomanda != null && pratica.tipoDomanda != "")
					html.push('<td>' + pratica.tipoDomanda + '</td>');
				else
					html.push('<td></td>');
				if (pratica.progressivo != null && pratica.progressivo != "")
					html.push('<td class=\"center vcenter\"><span class=\"badge\" style=\"background-color: white; color: black; border: 1px solid black\">' + pratica.progressivo + '</span></td>');
				else
					html.push('<td></td>');
				if (pratica.importoRendicontato != null && pratica.importoRendicontato != "")
					html.push('<td>' + pratica.importoRendicontatoStr + '</td>');
				else
					html.push('<td></td>');
				
				if (pratica.codiceFornitore != null && pratica.codiceFornitore != "")
					html.push('<td>' + pratica.codiceFornitore + ' - ' + pratica.ragioneSociale + '</td>');
				else
					html.push('<td></td>');
				if (pratica.dataDocumentoSpesa != null && pratica.dataDocumentoSpesa != "")
					html.push('<td>' + pratica.dataDocumentoSpesaStr + '</td>');
				else
					html.push('<td></td>');
				if (pratica.numeroDocumentoSpesa != null && pratica.numeroDocumentoSpesa != "")
					html.push('<td>' + pratica.numeroDocumentoSpesa + '</td>');
				else
					html.push('<td></td>');
				if (pratica.descrTipoDocumento != null && pratica.descrTipoDocumento != "")
					html.push('<td>' + pratica.descrTipoDocumento + '</td>');
				else
					html.push('<td></td>');
				if (pratica.descrModPagamento != null && +pratica.descrModPagamento != "")
					html.push('<td>' + pratica.descrModPagamento + '</td>');
				else
					html.push('<td></td>');
				if (pratica.dataPagamento != null && +pratica.dataPagamento != "")
					html.push('<td>' + pratica.dataPagamentoStr + '</td>');
				else
					html.push('<td></td>');
				if (pratica.importoSpesa != null && pratica.importoSpesa != "")
					html.push('<td>' + pratica.importoSpesaStr + '</td>');
				else
					html.push('<td></td>');

				html.push('</tr>');
			}
			html.push('</tbody>');
			html.push('</table></div>');

			return html.join('');
		}

		$(document).ready(function() {

			$('body').on("column-switch.bs.table", function(obj, field) {
				var value = $('input[data-field="' + field + '"]').prop("checked");
				$.ajax({
					type : "POST",
					url : '../session/salvaColonna.do',
					data : "key=elencoProcedimentiDoc&field=" + field + "&value=" + value,
					success : function(data) {
					}
				});
			});

			/*Mantengo in sessione il numero di pagina dell'elenco*/
			$('body').on("page-change.bs.table", function(obj, size) {
				$.ajax({
					type : "POST",
					url : '../session/salvaNumeroPagina.do',
					data : "key=elencoProcedimentiDoc&page=" + size,
					success : function(data) {
					}
				});
			});

			$('#filter-bar').bootstrapTableFilter({
				filters : [ {
					field : 'identificativo',
					label : 'Identificativo',
					type : 'search'
				}, {
					field : 'annoCampagnaStr',
					label : 'Anno campagna',
					type : 'search'
				}, {
					field : 'denominazioneBando',
					label : 'Bando',
					type : 'search'
				}, {
					field : 'livelliForFilter',
					label : 'Operazione',
					type : 'ajaxSelectList',
					source : 'getElencoCodiciOperazioneJson.do'
				}, {
					field : 'ammCompetenza',
					label : 'Amm. Comp.',
					type : 'search'
				}, {
					field : 'cuaaBeneficiario',
					label : 'Cuaa',
					type : 'search'
				}, {
					field : 'denominazioneBeneficiario',
					label : 'Denominazione',
					type : 'search'
				} ],
				connectTo : '#elencoProcedimentiDoc',
				onSubmit : function() {
					var data = $('#filter-bar').bootstrapTableFilter('getData');

					var elabFilter = JSON.stringify(data);
					$.ajax({
						type : "POST",
						url : '../session/salvaFiltri.do',
						data : "key=elencoProcedimentiDoc&filtro=" + elabFilter,
						success : function(data) {
						}
					});
					console.log(data);
				}
			});

			var filterJSON = $('#filtroAziende').val();
			if (filterJSON)
				$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON", filterJSON);
		});
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />