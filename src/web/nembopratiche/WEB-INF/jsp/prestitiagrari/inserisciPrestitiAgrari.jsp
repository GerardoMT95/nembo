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
	<p:breadcrumbs cdu="CU-NEMBO-302-I" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-302-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelInserisciPrestitiAgrari">
		<br/>
		<form method="POST" action="../cunembo302i/inserisci_prestito_conferma.do">
		<table class="table table-hover table-striped table-bordered tableBlueTh">
			<thead>
			<tr>
				<th>Finalità del Prestito</th>
				<th>Istituto di credito erogante</th>
				<th>Importo in scadenza (&euro;)</th>
				<th>Data scadenza</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td><m:textfield id="finalitaPrestito" name="finalitaPrestito" preferRequestValues="${preferRequest}"></m:textfield></td>
				<td><m:textfield id="istitutoErogante" name="istitutoErogante" preferRequestValues="${preferRequest}"></m:textfield></td>
				<td><m:textfield id="importo" name="importo" preferRequestValues="${preferRequest}"></m:textfield></td>
				<td><m:textfield type="DATE" id="dataScadenza" name="dataScadenza" preferRequestValues="${preferRequest}"></m:textfield></td>
			</tr>
			</tbody>
		</table>
		<input class="btn btn-primary pull-right" type="submit" value="Inserisci" />
		<a class="btn btn-default" href="../cunembo302l/index.do">Indietro</a>
		
		</form>
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

		function importoAdder(rows, field) {
			var __sum = 0;
			$(rows).each(function(index, currentRow)
			{
				var value = currentRow[field];
				if (value != null && !isNaN(value) && value.length != 0)
				{
					__sum += parseFloat(value);
				}
			});
			return __sum;
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />