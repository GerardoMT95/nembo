<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<script type="text/javascript">
	function gestisciCampoInput(checkbox, idBando) {
		var input = $('#'+"inputPerIdBando_"+idBando);
		if($(checkbox).attr('checked')) {
			$(input).prop('disabled', false);
		} else {
			$(input).prop('disabled', true);
		}
	}
</script>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-161" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-161" />
	<form:form action="../cunembo161/modifica.do" modelAttribute="listaRaggruppamento" method="post" class="form-inline">
		<div class="container-fluid" id="content">
			<m:panel id="panelPunteggi">
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
							<m:error></m:error>
							<table  data-toggle="table"
									data-show-columns="true"
									class="table table-hover table-bordered tableBlueTh">
								<thead>
									<tr>
										<th data-switchable="false"></th>
										<th data-field="principio" data-visible="false">Principio</th>
										<th data-field="codice">Codice</th>
										<th data-field="criterio">Criterio</th>
										<th data-field="specifiche" data-visible="false">Specifiche</th>
										<th data-field="puntBase" data-visible="false">Punteggio base</th>
										<th data-field="puntCalcolato" data-switchable="false">Punteggio calcolato</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${elemento.criteri}" var="criterio">
										<tr>
											<td>
												<c:if test="${criterio.punteggioCalcolato > 0 }">
													<m:checkBox checked="true" preferRequestValues="${prefReqValues}" id="${criterio.idBandoLivelloCriterio}" name="idBandoLivelloCriterio" value="${criterio.idBandoLivelloCriterio}"   onClick="gestisciCampoInput(this,'${criterio.idBandoLivelloCriterio}')"></m:checkBox>
												</c:if> 
												<c:if test="${criterio.punteggioCalcolato == 0 or criterio.punteggioCalcolato == null}">
													<m:checkBox  preferRequestValues="${prefReqValues}" id="${criterio.idBandoLivelloCriterio}" name="idBandoLivelloCriterio" value="${criterio.idBandoLivelloCriterio}" onClick="gestisciCampoInput(this,'${criterio.idBandoLivelloCriterio}')"></m:checkBox>
												</c:if>
											</td>
											<td><c:out value="${criterio.descPrincipioSelezioneHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.codiceHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.criterioSelezioneHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.specificheHtml}" escapeXml="false"></c:out></td>
											<td><c:out value="${criterio.punteggio}"></c:out></td>
											<td>
												
												<c:choose>
													<c:when test="${criterio.punteggioMin == criterio.punteggioMax}">
														<fmt:formatNumber pattern="###0.##" value="${criterio.punteggioCalcolato}"/>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${(criterio.punteggioCalcolato > 0 ) || (param[\"puntPerIdBandoLivCrit_\".concat(criterio.idBandoLivelloCriterio).concat(\"_\").concat(criterio.punteggioMin).concat(\"_\").concat(criterio.punteggioMax).concat(\"_\").concat(criterio.codice)] != null)}">
																<m:textfield   preferRequestValues="${prefReqValues}" maxlength="7" id="inputPerIdBando_${criterio.idBandoLivelloCriterio}" name="puntPerIdBandoLivCrit_${criterio.idBandoLivelloCriterio}_${criterio.punteggioMin}_${criterio.punteggioMax}_${criterio.codice}" value="${criterio.punteggioCalcolato}"></m:textfield>
															</c:when>
															<c:otherwise>
																<m:textfield disabled="true"  preferRequestValues="${prefReqValues}" maxlength="7" id="inputPerIdBando_${criterio.idBandoLivelloCriterio}" name="puntPerIdBandoLivCrit_${criterio.idBandoLivelloCriterio}_${criterio.punteggioMin}_${criterio.punteggioMax}_${criterio.codice}" value="${criterio.punteggioCalcolato}"></m:textfield>
															</c:otherwise>
														</c:choose>														
													</c:otherwise>
												</c:choose>
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
						<button type="submit" name="conferma" id="conferma" class="btn btn-primary" >conferma</button>
					</div>
				</div>
			</m:panel>
		</div>
	</form:form>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />