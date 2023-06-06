<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-174" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-174" />
	<form:form action="../cunembo174/modifica.do" modelAttribute="listaRaggruppamento" method="post" class="form-inline">
		<div class="container-fluid" id="content">
			<m:panel id="panelPunteggi">
			<m:error></m:error>
				<c:if test="${msgErrore != null}">
					<div class="stdMessagePanel">
						<div class="alert alert-danger">
							<p>
								<strong>Attenzione!</strong><br />
								<c:out value="${msgErrore}"></c:out>
							</p>
						</div>
					</div>
				</c:if>
				<c:if test="${listaRaggruppamento != null}">
					<c:forEach items="${listaRaggruppamento}" var="elemento" varStatus="status">
						<m:panel id="panel_${status.index}" title="${elemento.descrizioneLivello } ${elemento.raggruppamento}">
							<table  data-toggle="table"
									data-show-columns="true"
									class="table table-hover table-bordered tableBlueTh">
								<thead>
									<tr>
										<th data-field="principio" data-visible="false">Principio</th>
										<th data-field="codice">Codice</th>
										<th data-field="criterio">Criterio</th>
										<th data-field="specifiche" data-visible="false">Specifiche</th>
										<th data-field="puntBase" data-visible="false">Punteggio base</th>
										<th data-field="puntCalcolato" data-switchable="false">Punteggio calcolato</th>
										<th data-field="puntIstruttoria" data-switchable="false">Punteggio istruttoria</th>
									</tr>
								</thead> 
								<tbody>
									<c:forEach items="${elemento.criteri}" var="criterio">
										<tr>
											<td><c:out value="${criterio.descPrincipioSelezioneHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.codiceHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.criterioSelezioneHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.specificheHtml}" escapeXml="false"> </c:out></td>
											<td><c:out value="${criterio.punteggio}"></c:out></td>
											<td><c:out value="${criterio.punteggioCalcolato}"></c:out></td>
											<td>
												<m:textfield maxlength="7" id="puntPerIdBandoLivCrit_${criterio.idBandoLivelloCriterio}" name="puntPerIdBandoLivCrit_${criterio.idBandoLivelloCriterio}" preferRequestValues="${prefReqValues}" value="${criterio.punteggioIstruttoria}"></m:textfield> 
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</m:panel>
					</c:forEach>
				</c:if>
				<div class="puls-group" style="margin-top: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo160/index.do');" class="btn btn-default">annulla</button>
					</div>
					<div class="pull-right">
						<button type="submit" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
					</div>
				</div>
			</m:panel>
		</div>
	</form:form>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />