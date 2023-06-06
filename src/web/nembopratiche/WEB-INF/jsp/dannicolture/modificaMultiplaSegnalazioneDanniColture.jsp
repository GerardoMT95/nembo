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
	<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<style>
	checkbox-field {
		max-width: 37px !important;
		width:36px !important;
		min-width: 36px !important;
		text-align:center;
		padding: 8px 0;
	}
</style>	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-310-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-310-M" />
	
	<form:form action="modifica.do" modelAttribute="" method="post" class="form-horizontal">
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		
		
		
		<br/>
				<c:set var ="tableName"  value ="tableRiepilogoDanniColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value ="asc"/><!-- asc o desc -->
				<table id="tableRiepilogoDanniColture"
					class="table-bootstrap table table-hover table-striped table-bordered tableBlueTh"
					data-table-name="${tableName}"
					data-escape-table="true"
				>
					<thead>
						<tr>
							<th data-field="descrizioneComune" data-sortable="true" data-switchable="true">Comune</th>
							<th data-field="foglio" data-sortable="true" data-switchable="true">Foglio n.</th>
							<th data-field="descrizioneColtura" data-sortable="true" data-switchable="true">Coltura/struttura</th>
							<th data-field="superficieGraficaStr" data-sortable="true" data-switchable="true">Ha</th>
							<th data-field="percDannoStr" data-sortable="true" data-switchable="true">% danno</th>
							<th data-field="importoDannoStr" data-sortable="true" data-switchable="true">Danno stimato - &euro;</th>
							<th data-field="flagColturaAssicurata" data-sortable="true" data-switchable="true">Coltura assicurata</th>
							<th data-field="noteDanno" data-sortable="true" data-switchable="true">Note</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${elenco}" var="row">
							<tr>
								<td><input type="hidden" name="idDettaglioSegnalDan" value="${row.idDettaglioSegnalDan}"> ${row.descrizioneComune}</td>
								<td>${row.foglio}</td>
								<td>${row.descrizioneColtura}</td>
								<td>${row.superficieGraficaStr}</td>
								<td><m:textfield preferRequestValues="${preferRequestValues}" value="${row.percDanno}" id="percDanno_${row.idDettaglioSegnalDan}" name="percDanno_${row.idDettaglioSegnalDan}"></m:textfield> </td>
								<td><m:textfield preferRequestValues="${preferRequestValues}" value="${row.importoDanno}" id="importoDanno_${row.idDettaglioSegnalDan}" name="importoDanno_${row.idDettaglioSegnalDan}"></m:textfield> </td>
								
								<td><div class="center">
										<input name="flagColturaAssicurata_${row.idDettaglioSegnalDan}" data-toggle="bs-toggle" type="checkbox" value="S"
											<c:if test="${row.flagColturaAssicurata=='S'}"> checked="checked" </c:if> />
									</div></td>
								
								<td><m:textarea preferRequestValues="${preferRequestValues}"  id="noteDanno_${row.idDettaglioSegnalDan}" name="noteDanno_${row.idDettaglioSegnalDan}">${row.noteDanno}</m:textarea> </td>
							</tr>						
						</c:forEach>
					</tbody>
			</table>
			
			
			<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button" onclick="forwardToPage('../cunembo310l/index.do');" class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>
			
		</m:panel>
	</div>
	
</form:form>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>

<script type="text/javascript">
$('input[data-toggle="bs-toggle"]').bootstrapToggle();
	
	

</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />