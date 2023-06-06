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
				<h4>Dettaglio Allevamento</h4>
				<table id="tblDettaglioAllevamento"
					class=" table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead></thead>
					<tbody>
						<tr>
							<th>Ubicazione allevamento</th>
							<td>${allevamento.ubicazioneAllevamento}</td>
						</tr>
						<tr>
							<th>Cod. Azienda Zootecnica</th>
							<td>${allevamento.codiceAziendaZootecnica}</td>
						</tr>
						<tr>
							<th>Specie</th>
							<td>${allevamento.descrizioneSpecieAnimale}</td>
						</tr>
						<tr>
							<th>Categoria</th>
							<td>${allevamento.descrizioneCategoriaAnimale}</td>
						</tr>
						<tr>
							<th>Quantit&agrave;</th>
							<td>${allevamento.quantitaUnitaMisura}</td>
						</tr>
						<tr>
							<th>Peso medio per unit&agrave;</th>
							<td>${allevamento.pesoVivoMedioFormatted}</td>
						</tr>
						<tr>
							<th>Giornate lavorative medie per unit&agrave;</th>
							<td>${allevamento.giornateLavorativeMedieFormatted}</td>
						</tr>
						<tr>
							<th>Giornate lavorative totali</th>
							<td>${allevamento.giornateLavorativeFormatted}</td>
						</tr>
						<tr>
							<th>Unit&agrave; foraggere</th>
							<td>${allevamento.unitaForaggereFormatted}</td>
						</tr>
						<tr>
							<th>Note</th>
							<td>${allevamento.note}</td>
						</tr>
						<tr>
							<th>Ultima modifica</th>
							<td>${ultimaModifica}</td>
						</tr>
				</tbody>
				</table>
		<br/>
		<h4>P.L.V. Zootecnica</h4>		
		<c:set var ="tableName"  value ="tableDettaglioAllevamentiPlv"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<table id="${tableName}" 
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
			data-toggle="table"
			data-url="../cunembo300d/get_list_dettaglio_allevamenti_${idCategoriaAnimale}_${istatComune}.json"
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
					<th data-field="descProduzione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descProduzione')}">Tipo produzione</th>
					<th data-field="numeroCapi" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'numeroCapi')}">N° capi</th>
					<th data-field="quantitaProdottaFormatted" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'quantitaProdottaFormatted')}" data-formatter="numberFormatter2" data-adder="importoAdder" data-totale="true">Quantita prodotta annua per unità</th>
					<th data-field="prodLordaFormatted" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prodLordaFormatted')}">Prod. lorda</th>
					<th data-field="quantitaReimpiegataFormatted" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'prodLordaFormatted')}">Reimpieghi</th>
					<th data-field="prodNettaFormatted" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prodNettaFormatted')}">Prod. netta</th>
					<th data-field="prezzoFormatted" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prezzoFormatted')}">Prezzo unitario</th>
					<th data-field="importoTotaleFormatted" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'importoTotale')}">Importo totale</th>
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