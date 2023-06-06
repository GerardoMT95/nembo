<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-301" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-301" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaMotoriAgricoli">
		<br/>
		<c:set var ="tableName"  value ="tableElencoMotoriAgricoli"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<table id="${tableName}"
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
			data-toggle="table"
			data-url="visualizza_motori_agricoli.json"
			data-pagination=true 
			data-only-info-pagination=true
			data-show-pagination-switch="true" 
			data-pagination-v-align="top"
			data-show-columns="true" 
			data-page-list="[10, 25, 50, 70, 100]"
			data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
			data-show-default-order-button="true"
			data-default-order-column="${defaultOrderColumn}"
			data-default-order-type="${defaultOrderType}"
			data-table-name="${tableName}"
			data-sort-name="${sessionMapNomeColonnaOrdinamento.get(tableName) != null ? 
				sessionMapNomeColonnaOrdinamento.get(tableName) : 
				defaultOrderColumn}"
			data-sort-order="${sessionMapTipoOrdinamento.get(tableName) != null ? sessionMapTipoOrdinamento.get(tableName) : defaultOrderType}"
			data-page-number="${sessionMapNumeroPagina.get(tableName) != null ? sessionMapNumeroPagina.get(tableName) : 1}"
			data-escape-table="true"
		>
			<thead>
				<tr>

					<th data-field="descTipoGenereMacchina" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'idDanno')}">Genere macchina</th>
					<th data-field="descTipoCategoria" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'tipoDanno')}">Categoria</th>
					<th data-field="descTipoMarca" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'idScorta')}">Marca</th>
					<th data-field="tipoMacchina" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'denominazione')}">Tipo</th>
					<th data-field="potenzaKw" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descrizione')}">Potenza (kW)</th>
					<th data-field="dataCaricoFormatted" data-sortable="true" data-sorter="dateSorterddmmyyyy" data-visible="${!colonneNascoste.visible(tableName,'quantita')}">Data carico</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	    		<br/>
	    		<a class="btn btn-default"  href="../cunembo298l/index.do">Indietro</a>
		</m:panel>
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
		$( document ).ready(function() {
			loadBootstrapTable('${tableName}', '');
		 });
		
		</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />