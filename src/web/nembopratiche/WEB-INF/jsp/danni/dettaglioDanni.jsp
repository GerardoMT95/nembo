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
	<p:breadcrumbs cdu="CU-NEMBO-298-D" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-298-D" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoDanni">
		<br/>

		<div id="errorBoxDiv" class='alert alert-danger col-md-12' style="display:none;">
			<h4>Non è possibile modificare pi&ugrave; danni contemporaneamente se almeno uno di quelli selezionati &egrave; di tipo Terreni Ripristinabili, Terreni Non Ripristinabili, Piantagioni Arboree o Allevamenti.</h4>
		</div>
		<h4>Dettaglio Danno</h4>
		<table
			class="table table-hover table-striped table-bordered tableBlueTh "
		>
			<thead>
			</thead>
			<tbody>
				<tr>
					<th>Danno relativo a:</th>
					<td>${danno.tipoDanno}</td>
				</tr>
				<tr>
					<th>Num. Progressio danno</th>
					<td>${danno.progressivo}</td>
				</tr>
				<tr>
					<th>Denominazione</th>
					<td>${danno.denominazione}</td>
				</tr>
				<tr>
					<th>Descrizione entit&agrave; danneggiata</th>
					<td>${danno.descEntitaDanneggiata}</td>
				</tr>
				<tr>
					<th>Descrizione del danno</th>
					<td>${danno.descrizione}</td>
				</tr>
				<tr>
					<th>Quantit&agrave;</th>
					<td>${danno.quantitaUnitaMisuraFormatter}</td>
				</tr>
				<tr>
					<th>Importo</th>
					<td>${danno.importoFormatter}</td>
				</tr>
			</tbody>
		</table>
		<br/>
		<c:if test="${isConduzioni!= null && isConduzioni==true}">
			<h4>Elenco Particelle Danneggiate</h4>
			<c:set var ="tableName"  value ="tblElencoConduzioniDettaglio"/>
						<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
					data-toggle="table"
					data-url="get_list_conduzioni_danno_${danno.idDannoAtm}.json"
					data-pagination="true" 
					data-only-info-pagination="true"
					data-show-pagination-switch="true" 
					data-pagination-v-align="top"
					data-show-columns="true" 
					data-page-list="[10, 25, 50, 70, 100]"
			>
				<thead>
					<tr>
					<th data-field="descProvincia" data-sortable="true">Prov</th>
					<th data-field="descComune" data-sortable = "true">Comune</th>
					<th data-field="sezione" data-sortable = "true">Sez.</th>
					<th data-field="foglio" data-sortable = "true">Foglio</th>
					<th data-field="particella" data-sortable = "true">Part.</th>
					<th data-field="subalterno" data-sortable = "true">Sub.</th>
					<th data-field="supCatastale" data-sortable = "true" data-formatter="numberFormatter4" >Sup. catastale<br/>(ha)</th>
					<th data-field="superficieUtilizzata" data-formatter="numberFormatter4">Superficie<br/>(ha)</th>
					<th data-field="descTipoUtilizzo" data-sortable = "true">Utilizzo</th>
				</tr>
				</thead>
			</table>

		</c:if>
   		<br/>
   		<a class="btn btn-default" href="../cunembo298l/index.do">Indietro</a>
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

</script>
		
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />