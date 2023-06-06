<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<style type="text/css">
span.tab-space {
	padding-left: 0.4em;
}
</style>
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-179" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-179" />


	<div class="container-fluid" id="content">
		<m:panel id="panelPosizioni">
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

			<div class="container-fluid" id="content" style="margin-bottom: 3em">

				<p:abilitazione-azione codiceQuadro="CNTLO" codiceAzione="MODIFICA">
					<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
						<div class="pull-left">

							<c:if test="${procedimentoEstratto !=null}">
								<c:choose>
									<c:when test="${procedimentoEstratto.flagEstrazione =='N'}">
										<button type="button"
											onclick="return openPageInPopup('../cunembo180/popupindex.do','dlgModifica','Modifica controllo in loco','modal-lg',false)"
											class="btn  btn-primary">modifica</button>
									</c:when>
									<c:otherwise>
										<button type="button"
											onclick="forwardToPage('../cunembo180/index.do');"
											class="btn  btn-primary">modifica</button>
									</c:otherwise>
								</c:choose>
							</c:if>

						</div>
						<br class="clear" />
					</div>
				</p:abilitazione-azione>

				<c:choose>
					<c:when test="${procedimentoEstratto !=null}">
						<table summary="dettaglio" style="margin-top: 4px"
							class="myovertable table table-hover table-condensed table-bordered">
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>
							<tbody>
								<tr>
									<th>Pratica estratta a campione</th>
									<c:choose>
										<c:when test="${procedimentoEstratto.flagEstrazione =='N'}">
											<td>No</td>
										</c:when>
										<c:otherwise>
											<td>Si</td>
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
									<th>Data estrazione</th>
									<td>${procedimentoEstratto.dataEstrazioneStr}</td>
								</tr>
								<c:if test="${procedimentoEstratto.flagEstrazione !='N'}">
									<tr>
										<th>Modalità di selezione</th>
										<td>${procedimentoEstratto.statoEstrazione}</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<div class="stdMessagePanel">
							<div class="alert alert-warning">Pratica mai sottoposta a
								estrazione a campione</div>
						</div>
					</c:otherwise>
				</c:choose>

				<c:if test="${operazioni !=null}">

					<table id="operazioni"
						class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
						data-toggle="table">
						<thead>
							<tr>
								<th>Operazione</th>
								<th>Esecuzione controllo in loco</th>
								<th>Data inizio controllo in loco</th>
								<th>Data sopralluogo</th>
								<th>Verbale N</th>
								<th>Funzionario controllore</th>
								<th>Inadempienza tecnica NON<BR> vincolata a controllo di
									<BR>ammissibilità superfici</th>
								<th>Inadempienza tecnica<BR> condizionata all'esito di<BR>
									ammissibilità superfici</th>
								<th>Motivazione</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${operazioni}" var="o">
								<tr>
									<td><c:out value="${o.codiceDescrizione}"></c:out></td>
									<td><c:out value="${o.flagControlloStr}"></c:out></td>
									<td><c:out value="${o.dataInizioControlloStr}"></c:out></td>
									<td><c:out value="${o.dataSopralluogoStr}"></c:out></td>
									<td><c:out value="${o.numeroVerbale}"></c:out></td>
									<td><c:out value="${o.decodificaTecnico}"></c:out></td>
									
									<c:choose>
									<c:when  test="${o.codiceLivello!='10.1.8'}">
									<td>
									<c:choose>
											<c:when test="${o.flagInadempVincolata=='N'}">
									No
									</c:when>
											<c:otherwise><c:if test="${o.flagInadempVincolata=='S'}">Sì</c:if></c:otherwise>
										</c:choose>
										<c:if test="${o.noteInadempVincolata!=null}"> - </c:if>
									 <c:out value="${o.noteInadempVincolata}"></c:out></td>
									<td>
									<c:choose>
											<c:when test="${o.flagInadempCondizionata=='N'}">
									No
									</c:when>
											<c:otherwise><c:if test="${o.flagInadempCondizionata=='S'}">Sì</c:if></c:otherwise>
										</c:choose>
										<c:if test="${o.noteInadempCondizionata!=null}"> - </c:if>
										 <c:out value="${o.noteInadempCondizionata}"></c:out></td>
										</c:when>
										<c:otherwise>
										<td></td><td></td>
										</c:otherwise>
										</c:choose>
								<td><c:out value="${o.motivazione}"></c:out></td>								
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</c:if>
			</div>
			<div>
				<table
					class="myovertable table table-hover table-condensed table-bordered">
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

	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">

	</script>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />