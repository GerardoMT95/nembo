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
	<p:breadcrumbs cdu="CU-NEMBO-302-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-302-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaPrestitiAgrari">
		<br/>
		<c:set var ="tableName"  value ="tableModificaPrestitiAgrari"/>
		<form id="formVisualizzaPrestitiAgrari" name="formVisualizzaPrestitiAgrari" method="POST" action="modifica_prestiti_agrari_conferma.do">
			<c:set var ="tableName"  value ="tableElencoPrestitiAgrari"/>
			<table id="${tableName}" 
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
			>
			<thead>
					<tr>
						<th>Finalità del prestito</th>
						<th>Istituto di credito erogante</th>
						<th>Importo in scadenza (&euro;)</th>
						<th>Data scadenza</th>
					</tr>
			</thead>
			<tbody>
				<c:forEach items="${listPrestiti}" var="prestito">
					<tr>
						<td>
							<input type="hidden" name=idPrestitiAgrari value="${prestito.idPrestitiAgrari}" />
							<m:textfield name="finalitaPrestito_${prestito.idPrestitiAgrari}" id="finalitaPrestito_${prestito.idPrestitiAgrari}" value="${prestito.finalitaPrestito}" preferRequestValues="${preferRequest}"></m:textfield>
						</td>					
						<td><m:textfield name="istitutoErogante_${prestito.idPrestitiAgrari}" id="istitutoErogante_${prestito.idPrestitiAgrari}" value="${prestito.istitutoErogante}" preferRequestValues="${preferRequest}"></m:textfield></td>					
						<td><m:textfield name="importo_${prestito.idPrestitiAgrari}" id="importo_${prestito.idPrestitiAgrari}" value="${prestito.importo}" preferRequestValues="${preferRequest}"></m:textfield></td>					
						<td><m:textfield type="DATE" name="dataScadenza_${prestito.idPrestitiAgrari}" id="dataScadenza_${prestito.idPrestitiAgrari}" value="${prestito.dataScadenzaFormatted}" preferRequestValues="${preferRequest}"></m:textfield></td>					
					</tr>
				</c:forEach>
			</tbody>	
			</table>
			<input class="btn btn-primary pull-right" type="submit" value="Conferma" />
			<a class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a>
		</form>
		<br/>
   		<br/>
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
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />