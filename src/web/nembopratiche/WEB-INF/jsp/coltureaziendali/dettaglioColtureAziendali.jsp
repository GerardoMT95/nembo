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

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-305-D" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-305-D" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		<br/>
		<div style="overflow-x:scroll;">
		
				<h4>Identificativo elemento</h4>
				<table id="tableRiepilogoColtureAziendali"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th>Ubicazione terreno</th>
							<td>${dettaglio.ubicazioneTerreno}</td>
						</tr>
						<tr>
							<th>Utilizzo</th>
							<td>${dettaglio.tipoUtilizzoDescrizione}</td>
						</tr>
						<tr>
							<th>Superficie (ha)</th>
							<td>${dettaglio.superficieUtilizzata}</td>
						</tr>
						<tr>
							<th>Ultima Modifica</th>
							<td>${ultimaModifica}</td>
						</tr>
					</tbody>
				</table>
				<br/>
				<h4>Dettaglio PSR</h4>
				<table id="tableDettaglioColtureAziendali"
					class="table table-hover table-striped table-bordered tableBlueTh"
					data-toggle="table"
				>
					<thead>
						<tr>
							<th colspan="4">P.L.V. ordinaria annuale<br/>(media del triennio precedente)</th>
							<th colspan="3">P.L.V. effettiva (conseguita nell'annata<br/>agraria dell'evento)</th>
							<th rowspan="2" class="col-md-1" data-formatter="numberFormatter2">Danno<br/>(%)</th>
						</tr>
						<tr>
							<th class="col-md-1" data-formatter="numberFormatter2">Q.li / ha</th>
							<th class="col-md-1" data-formatter="numberFormatter2">Tot. Q.li</th>
							<th class="col-md-1" data-formatter="numberFormatter2">Prezzo<br/>al q.le</th>
							<th class="col-md-1" data-formatter="numberFormatter2">Totale<br/>Euro</th>

							<th class="col-md-1" data-formatter="numberFormatter2">Tot. Q.li</th>
							<th class="col-md-1" data-formatter="numberFormatter2">Prezzo<br/>al q.le</th>
							<th class="col-md-1" data-formatter="numberFormatter2">Totale<br/>Euro</th>
						
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${dettaglio.produzioneHa}</td>
							<td>${dettaglio.produzioneDichiarata}</td>
							<td>${dettaglio.prezzo}</td>
							<td>${dettaglio.totaleEuroPlvOrd}</td>
							
							<td>${dettaglio.produzioneTotaleDanno}</td>
							<td>${dettaglio.prezzoDanneggiato}</td>
							<td>${dettaglio.totaleEuroPlvEff}</td>
							
							<td>${dettaglio.percentualeDanno}</td>
						</tr>
					</tbody>
				</table>
				
				<c:if test="${listControlloColtura.size() > 0 }">
					<br/>
					<table id="tableControlloColtureAziendali"
						class="table table-hover table-striped table-bordered tableBlueTh"
						data-toggle="table"
					>
						<thead>
							<tr>
								<th>Descrizione Anomalia</th>
								<th>Bloccante</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listControlloColtura}" var="controlloColtura">
								<tr>
									<td>${controlloColtura.descrizioneAnomalia}</td>
									<td>${controlloColtura.bloccante}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			<br/>
		</div>
   		<br/>
   		
		<h4>Particellare</h4>
		<c:set var ="tableName"  value ="tableDettaglioParticellareColtureAziendali"/>
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
   		<a class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a>
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
var mapIdSuperficieColturaAnomalia={};
</script>	
	
<c:forEach items="${mapIdSuperficieColturaAnomalia}" var="elem">
	<script type="text/javascript">
			mapIdSuperficieColturaAnomalia["${elem.key}"] = "${elem.value}";
	</script>
</c:forEach>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />