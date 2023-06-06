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

	<p:utente/>
	<p:breadcrumbs cdu="CU-NEMBO-166-M"/>
	<p:messaggistica/><p:testata cu="CU-NEMBO-166-M" />

	<div class="container-fluid" id="content">
		<m:panel id="esito">
			<p:abilitazione-azione codiceQuadro="ESIFN" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo166m/index.do');" class="btn  btn-primary">modifica</button>
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
							<th>Esito finale</th>
							<td>${esito.descrEsito}</td>
						</tr>
						<tr>
							<th>Motivazioni</th>
							<td>${esito.motivazione}</td>
						</tr>
						<tr>
							<th>Prescrizioni</th>
							<td>${esito.prescrizioniHtml}</td>
						</tr>
						<tr>
							<th>Funzionario istruttore</th>
							<td>${esito.descrTecnico}</td>
						</tr>
						<tr>
							<th>Funzionario di grado superiore</th>
							<td>${esito.descrGradoSup}</td>
						</tr>
						
						<c:if test="${!empty elencoAtti  && flagAmmisioneS =='S' }">
						<tr>
							<th>Tipo atto</th>
							<td>${esito.tipoAtto}</td>
						</tr>
						<c:if test="${attiVisibili =='S' }">
							<tr>
								<th>Numero</th>
								<td>${esito.numeroAtto}</td>
							</tr>
							<tr>
								<th>Data</th>
								<td>${esito.dataAttoStr}</td>
							</tr>
							</c:if>
						<tr>
						</c:if>
						
						
						<tr>
							<th>Note</th>
							<td>${esito.noteHtml}</td>
						</tr>
						
					</tbody>
				</table>
		</m:panel>

	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />