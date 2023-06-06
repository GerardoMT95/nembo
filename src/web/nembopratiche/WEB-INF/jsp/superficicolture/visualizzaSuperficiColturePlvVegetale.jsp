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
	<p:breadcrumbs cdu="CU-NEMBO-299-DPLV" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-299-DPLV" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoDanni">
		<br/>

				
		<h4>P.L.V. Vegetale</h4>
		<br/>
		<c:set var ="tableName"  value ="tableSuperificiColturePlvVegetale"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<table id="${tableName}"
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
			data-toggle="table"
			data-url="get_list_superfici_colture_plv_vegetale.json"
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
		>
			<thead>
				<tr>
					<th rowspan="2" data-field="tipoUtilizzoDescrizione" data-visible="${!colonneNascoste.visible(tableName,'tipoUtilizzoDescrizione')}">Utilizzo</th>
					<th rowspan="2"  data-field="superficieUtilizzata" data-visible="${!colonneNascoste.visible(tableName,'superficieUtilizzata')}" data-formatter="numberFormatter4" data-adder="dataAdderForNumberFormatter" data-totale="true">Superficie (ha)</th>
					<th rowspan="2"  data-field="produzioneQ" data-visible="${!colonneNascoste.visible(tableName,'produzioneQ')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">Produzione (q)</th>
					<th rowspan="2"  data-field="giornateLavPerSupUtil" data-visible="${!colonneNascoste.visible(tableName,'giornateLavPerSupUtil')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">Giornate<BR/>lavorative</th>
					<th rowspan="2"  data-field="uf" data-visible="${!colonneNascoste.visible(tableName,'uf')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">U.F.</th>
					<th colspan="2">Reimpieghi</th>
					<th  rowspan="2" data-field="plv" data-visible="${!colonneNascoste.visible(tableName,'plv')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">P.L.V.</th>
				</tr>
				<tr>
					<th data-field="reimpieghiQ" data-visible="${!colonneNascoste.visible(tableName,'reimpieghiQ')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">Reimpieghi (q)</th>
					<th data-field="reimpieghiUf" data-visible="${!colonneNascoste.visible(tableName,'reimpieghiUf')}" data-formatter="numberFormatter0" data-adder="dataAdderForNumberFormatter" data-totale="true">Reimpieghi (u.f.)</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
   		<br/>
   		<a class="btn btn-default" href="../cunembo299l/index.do">Indietro</a>
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