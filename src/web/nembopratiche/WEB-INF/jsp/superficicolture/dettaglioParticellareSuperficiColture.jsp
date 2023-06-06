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
	<p:breadcrumbs cdu="CU-NEMBO-299-D" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-299-D" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDettaglioSuperficiColture">
		<br/>

		<form name="formVisualizzaSuperficiColture" id="formVisualizzaSuperficiColture" action="" method="POST">
				
				<h4>Identificativo elemento</h4>
				<table id="tableSuperificiColtureDettaglio"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th>Ubicazione terreno</th>
							<td>${superficiColturaDettaglio.ubicazioneTerreno}</td>
						</tr>
						<tr>
							<th>Utilizzo</th>
							<td>${superficiColturaDettaglio.tipoUtilizzoDescrizione}</td>
						</tr>
					</tbody>
				</table>
				
				<br/>
				
				<h4>Particellare</h4>
				<c:set var ="tableName"  value ="tableDettaglioParticellareSuperificiColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
					data-toggle="table"
					data-url="get_list_dettaglio_particellare_superfici_colture_${idSuperficieColtura}.json"
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
							<th data-field="sezione" 				data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'sezione')}">Sez</th>
							<th data-field="foglio" 				data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'foglio')}">Fo</th>
							<th data-field="particella" 			data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'particella')}">Part</th>
							<th data-field="subalterno" 			data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'subalterno')}">Sub</th>
							<th data-field="supCatastale" 			data-sortable="true" data-formatter="numberFormatter4" data-visible="${!colonneNascoste.visible(tableName,'supCatastale')}">Sup. catastale (ha)</th>
							<th data-field="superficieUtilizzata" 	data-sortable="true" data-formatter="numberFormatter4" data-visible="${!colonneNascoste.visible(tableName,'superficieUtilizzata')}">Superficie (ha)</th>
							<th data-field="descTitoloPossesso" 	data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descTitoloPossesso')}">Titolo possesso</th>
							<th data-field="descZonaAltimetrica" 	data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descZonaAltimetrica')}">Zona altimetrica</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				<br/>
				
				<br/>
		
			</form>
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
	function biolFormatter($value, row, index)
	{
		return 'N.D.';
	}
</script>
	
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />