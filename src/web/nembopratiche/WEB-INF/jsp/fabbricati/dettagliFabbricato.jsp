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
	<p:breadcrumbs cdu="CU-NEMBO-1303-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-1303-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDettaglioFabbricati">
		<br/>
		<c:set var ="tableName"  value ="tableDettaglioFabbricati"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<form id="formVisualizzaFabbricati" name="formDettaglioFabbricati" method="POST" action="">
		<table id="${tableName}" 
			class=" table table-hover table-striped table-bordered tableBlueTh">
			<thead></thead>
			<colgroup>
				<col width="25%" />
				<col width="75%" />
			</colgroup>
			<tbody>
					<tr>
						<th>Ubicazione fabbricato (UTE)</th>
						<td>${fabbricato.ute}</td>
					</tr>
					<tr>
						<th>Tipo fabbricato</th>
						<td>${fabbricato.tipoFabbricato}</td>
					</tr>
					<tr>
						<th>Forma</th>
						<td>${fabbricato.tipologia}</td>
					</tr>
					<tr>
						<th>Denominazione</th>
						<td>${fabbricato.denominazione}</td>
					</tr>
					<tr>
						<th>Larghezza (m)</th>
						<td>${fabbricato.larghezzaFormatted}</td>
					</tr>
					<tr>
						<th>Lunghezza (m)</th>
						<td>${fabbricato.lunghezzaFormatted}</td>
					</tr>
					<tr>
						<th>Altezza (m)</th>
						<td>${fabbricato.altezzaFormatted}</td>
					</tr>
					<tr>
						<th>Superficie (m<sup>2</sup>)</th>
						<td>${fabbricato.superficieFormatted}</td>
					</tr>
					<tr>
						<th>Dimensione</th>
						<td>${fabbricato.dimensioneFormatted}</td>
					</tr>			
					<tr>
						<th>Anno di costruzione o di ritrattamento</th>
						<td>${fabbricato.annoCostruzione}</td>
					</tr>
					<tr>
						<th>Coordinate U.T.M. (X,Y)</th>
						<td>${fabbricato.coordinateUtm}</td>
					</tr>			
					<tr>
						<th>Note</th>
						<td>${fabbricato.note}</td>
					</tr>	
					<tr>
						<th>Data inizio validità</th>
						<td>${fabbricato.dataInizioValFabbrFormatted}</td>
					</tr>
					<tr>
						<th>Data fine validità</th>
						<td>${fabbricato.dataFineValFabbrFormatted}</td>
					</tr>																												
			</tbody>
			
		</table>
		</form>
		<a class="btn btn-default" href="../cunembo1303l/index.do" >Indietro</a>
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
	<p:abilitazione-azione codiceQuadro="FABBR" codiceAzione="DETTAGLIO">
	<script type="text/javascript">
	function dettaglioFormatter($value, row, index)
	{
		return '<a class="icon-list icon-large" href="../cunembo1303d/visualizza_dettaglio_' + $value +'.do"></a>';
	}
	</script>
								
	</p:abilitazione-azione>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />