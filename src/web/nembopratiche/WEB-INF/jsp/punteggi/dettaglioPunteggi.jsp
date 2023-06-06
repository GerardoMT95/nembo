<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-160" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-160" />
	<form:form action="" modelAttribute="" method="post" class="form-inline">
		<div class="container-fluid" id="content">
			<m:panel id="panelPunteggi">
				<div class="stdMessagePanel stdMessageLoad" style="display: none">
					<div class="alert alert-info">
						<p>Attenzione: Calcolo punteggio in corso; l'operazione potrebbe richiedere alcuni secondi...</p>
					</div>
					<span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
				</div>
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
				<c:choose>
					<c:when test="${listaRaggruppamento != null}">
						<br />
						<div class="form-group puls-group" style="margin-left:-1em">
							<div class="col-sm-12">
								<p:abilitazione-azione codiceQuadro="PUNTI" codiceAzione="CALCOLA">
									<button type="button" onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();forwardToPage('../cunembo162/index.do');" class="btn btn-primary ">Calcola</button>
								</p:abilitazione-azione>
		
								<c:if test="${isOggettoIstanza}">
									<p:abilitazione-azione codiceQuadro="PUNTI" codiceAzione="MODIFICA" visible="${calcoloPuntiEffettuato}">
										<button type="button" onclick="$('.stdMessageError').hide();forwardToPage('../cunembo161/index.do');" class="btn btn-primary pull-right" style="margin-left:3em">Modifica</button>
									</p:abilitazione-azione>
								</c:if>
								
								<c:if test="${!isOggettoIstanza}">
									<p:abilitazione-azione codiceQuadro="PUNTI" codiceAzione="MODIFICA_ISTRUTTORIA">
										<button type="button" onclick="$('.stdMessageError').hide();forwardToPage('../cunembo174/index.do');" class="btn btn-primary pull-right" style="margin-left:3em">Modifica</button>
									</p:abilitazione-azione>
								</c:if>
							</div>
						</div>
						<br style="clear: left" />
						<br />
						
						<c:forEach items="${listaRaggruppamento}" var="elemento" varStatus="status">
							
							<m:panel id="panel_${status.index}" title="${elemento.descrizioneLivelloCompletaHtml}">
								<table data-toggle="table"
									   data-show-columns="true"
									   class="table table-hover table-bordered tableBlueTh bTable" id="dett_${status.index}" >
									<thead>
										<tr>
											<th data-field="principio" data-visible="false">Principio</th>
											<th data-field="codice">Codice</th>
											<th data-field="criterio">Criterio</th>
											<th data-field="flagElaborazioneDecod" data-visible="false">Tipo elaborazione</th>
											<th data-field="specifiche" data-visible="false">Specifiche</th>
											<th data-field="puntBase" data-visible="false">Punteggio base</th>
											<th data-field="puntCalcolato" data-switchable="false">Punteggio calcolato</th>
											<c:if test="${!isOggettoIstanza}">
												<th data-field="puntIstruttoria" data-switchable="false">Punteggio istruttoria</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${elemento.criteri}" var="criterio">
											<tr>  
												<td><c:out value="${criterio.descPrincipioSelezioneHtml}" escapeXml="false"></c:out></td>
												<td><c:out value="${criterio.codiceHtml}" escapeXml="false"></c:out></td>
												<td><c:out value="${criterio.criterioSelezioneHtml}" escapeXml="false"></c:out></td>
												<td><c:out value="${criterio.flagElaborazioneDecod}" escapeXml="false"></c:out></td>
												<td><c:out value="${criterio.specificheHtml}" escapeXml="false"></c:out></td>
												<td><c:out value="${criterio.punteggio}"></c:out></td>
												<td><fmt:formatNumber pattern="###0.##" value="${criterio.punteggioCalcolato}"></fmt:formatNumber></td>
												<c:if test="${!isOggettoIstanza}">
													<c:choose>
													<c:when test="${criterio.punteggioIstruttoria!=null}">
														<td class="red-bold"><fmt:formatNumber pattern="####.##" value="${criterio.punteggioIstruttoria}"></fmt:formatNumber></td>
													</c:when>
													<c:otherwise>
														<td ><fmt:formatNumber pattern="####.##" value="${criterio.punteggioCalcolato}"></fmt:formatNumber></td>
													</c:otherwise>
													</c:choose>
													
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr> 
											<td colspan="2" id="tdLabelTotale_${status.index}" align="right"><b>Totale punti</b></td>
											<td><b><fmt:formatNumber pattern="###0.##" value="${elemento.totalePunteggioCalcolato}"></fmt:formatNumber></b></td>
											<c:if test="${!isOggettoIstanza}">
												<td><b><fmt:formatNumber pattern="####.##" value="${elemento.totalePunteggioIstruttoria}"></fmt:formatNumber></b></td>
											</c:if>
										</tr>
									</tfoot>
								</table>
							</m:panel>
						</c:forEach>
					</c:when>
					<c:otherwise>	
						<p:abilitazione-azione codiceQuadro="PUNTI" codiceAzione="CALCOLA">
							<button type="button" onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();forwardToPage('../cunembo162/index.do');" class="btn btn-primary">Calcola</button>
						</p:abilitazione-azione>
						<br style="clear: left" />	
						<div class="alert alert-danger">Calcolo punteggio non effettuato, premere su calcola punti</div>
					</c:otherwise>
				</c:choose>
				<c:if test="${!isOggettoIstanza}">
				<div style="margin-bottom: 2em">
				<i>N.b.: I punteggi evidenziati in rosso sono stati modificati dall'istruttore</i>
				</div>
				</c:if>
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
			</m:panel>
		</div>
	</form:form>



	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
	$( document ).ready(function() {
		/*<c:if test="${!isOggettoIstanza}">*/
		var reduceColSpanBy = 1;
		/*</c:if>*/
		/*<c:if test="${isOggettoIstanza}">*/
		var reduceColSpanBy = 0;
		/*</c:if>*/
		$('.bTable').each(function(){ 
			$(this).on('column-switch.bs.table', function (field, checked) {
				var colspan = $("#"+$(this).attr('id')+" tbody tr:first td").length - 1;
				$("#"+$(this).attr('id')+" tfoot td:first").attr("colspan",colspan-reduceColSpanBy);
				console.log(colspan);
			})
		});
		    
	});
	
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />