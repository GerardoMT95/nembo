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
	<p:breadcrumbs cdu="CU-NEMBO-300-I" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-300-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelInserisciProduzioni">
		<br/>
				<h4>P.L.V. Zootecnica</h4>
				<table id="tblDettaglioAllevamento"
					class=" table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead></thead>
					<tbody>
						<tr>
							<th>U.F. prodotte</th>
							<td>${ufProdotte}</td>
						</tr>
						<tr>
							<th>U.F. necessarie</th>
							<td>${ufNecessarie}</td>
						</tr>
						<tr>
							<th>Autosufficienza foraggera</th>
							<td>${autosufficenzaForaggera}</td>
						</tr>
						<tr>
							<th>Rapporto UBA/SAU</th>
							<td>${rapportoUbaSau}</td>
						</tr>
					</tbody>
				</table>
		<br/>
		<h4>P.L.V. Zootecnica</h4>		
		<c:set var ="tableName"  value ="tableListPlvZootecnicaDettaglio"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<table id="${tableName}" 
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
			data-toggle="table"
			data-url="../cunembo300plv/get_list_plv_zootecnica_dettaglio_allevamenti.json"
			data-pagination="true" 
			data-only-info-pagination="true"
			data-show-pagination-switch="true" 
			data-show-columns="true"
			data-show-default-order-button="true"
			
			data-pagination-v-align="top"
			data-page-list="[10, 25, 50, 70, 100]"
			data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
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
					<th data-field="codiceAziendaZootecnica" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'codiceAziendaZootecnica')}">Cod. Azienda<br>Zootecnica</th>
					<th data-field="descrizioneSpecieAnimale" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descrizioneSpecieAnimale')}">Specie</th>
					<th data-field="descrizioneCategoriaAnimale" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'numeroCapi')}">Categoria</th>
					<th data-field="descProduzione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descProduzione')}">Tipo<br/>produzione</th>
					<th data-field="numeroCapi" data-class="text-right" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'numeroCapi')}">N° Capi</th>
					<th data-field="quantitaProdottaFormatted" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'quantitaProdotta')}" text-align="right">Qt&agrave; prod<br/>annua per unit&agrave;</th>
					<th data-field="prodLordaFormatted" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prodLorda')}" >Prod.<br/>lorda</th>
					<th data-field="quantitaReimpiegataFormatted" data-class="text-right" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'quantitaReimpiegata')}">Reimpieghi</th>
					<th data-field="prodNettaFormatted" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prodNettaFormatted')}">Prod.<br/>netta</th>
					<th data-field="descUnitaMisura" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'descUnitaMisura')}">Unit&agrave; di<br/>misura</th>
					<th data-field="prezzoFormatted" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prezzoFormatted')}">Prezzo<br/>unitario</th>
					<th data-field="plv" data-class="text-right" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'plv')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true" >P.L.V.</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<br/>
		<a class="btn btn-default" href="../cunembo300l/index.do">Indietro</a>
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
		<script type="text/javascript">
		
		</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />