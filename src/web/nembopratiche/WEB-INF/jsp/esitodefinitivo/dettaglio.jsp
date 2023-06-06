<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-264-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-264-L" />

	<div class="container-fluid" id="content">
		<m:panel id="esito">
			<p:abilitazione-azione codiceQuadro="ESIFD" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo264m/index.do');" class="btn  btn-primary">modifica</button>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>
			<table summary="esito finale" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<tr>
						<th>Esito istruttoria confermato</th>
						<td>${esito.descrEsito}</td>
					</tr>
					<tr>
						<th>Esito provvedimento finale</th>
						<td>${esito.descrEsitoDefinitivo}</td>
					</tr>
					<tr>
						<th>Motivazioni</th>
						<td>${esito.motivazione}</td>
					</tr>
					<tr>
						<th>Funzionario istruttore</th>
						<td>${esito.descrTecnico}</td>
					</tr>
					<tr>
						<th>Funzionario di grado superiore</th>
						<td>${esito.descrGradoSup}</td>
					</tr>
				</tbody>
			</table>

			<div style="padding-top: 1em;">
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
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />