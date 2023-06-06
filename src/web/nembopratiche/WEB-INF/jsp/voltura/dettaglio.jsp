<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-248-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-248-L" />
	<div class="container-fluid" id="content">
		<m:panel id="panelFiliere">
			<m:error />

			<c:choose>
				<c:when test="${!empty voltura}">
				<div class="puls-group" style="margin-top: 2em">
					<div class="pull-left">
					<p:abilitazione-azione codiceQuadro="VOLT" codiceAzione="MODIFICA">	<a type="button" href="../cunembo248m/index.do" class="btn btn-primary">modifica</a>
					</p:abilitazione-azione></div>
					<br>
					<br>
				</div>
					<b:panel title="Dati voltura" id="voltura" collapsible="false">
						<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
							<tbody>
								<tr class="toggle_target_altri">
									<th>Denominazione Azienda</th>
									<td><c:out value="${voltura.denominazioneAzienda}"></c:out></td>
								</tr>
								<tr class="toggle_target_altri">
									<th>CUAA</th>
									<td><c:out value="${voltura.cuaa}"></c:out></td>
								</tr>
								<tr class="toggle_target_altri">
									<th>Sede legale</th>
									<td>${voltura.sedeLegale}</td>
								</tr>
								<tr class="toggle_target_altri">
									<th>Legale rappresentante</th>
									<td>${voltura.rappresentanteLegale}</td>
								</tr>
								<tr class="toggle_target_altri">
									<th>Motivazione</th>
									<td>${voltura.note}</td>
								</tr>
							</tbody>
						</table>
					</b:panel>
				</c:when>
				<c:otherwise>
					<div><a type="button" href="../cunembo248m/index.do" class="btn btn-primary" title="inserisci voltura">Inserisci voltura</a></div>
				</c:otherwise>
			</c:choose>
		
		
			<div style="padding-top:1em;">
				<table class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="10%">
						<col width="90%">
					</colgroup>
					<tbody>
						<tr>
							<th>Ultima modifica</th>
							<td><c:out value="${ultimaModifica}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>

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