<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<form:form action="" modelAttribute="" method="post" class="form-inline" style="margin-top:2em">
		
		<p:breadcrumbs cdu="${useCaseController}"/>
		<p:messaggistica/>
		<p:testata cu="${useCaseController}" />

		<div class="container-fluid" id="content" style="margin-bottom: 3em; margin-top: 2em">
		<c:if test="${msgInfoConferma != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-warning" style="min-height:4em">
						<p>
							<c:out value="${msgInfoConferma}"></c:out>
							<button type="button"
											onclick="forwardToPage('../cunembo164/chiudi_oggetto.do');"
											class="btn  btn-primary pull-right">Chiudi Oggetto</button>
						</p>
					</div>
				</div>
			</c:if>

			<table summary="Bando" class="table table-hover table-striped table-bordered tableBlueTh">
				<colgroup>
					<col width="5%" />
					<col width="45%" />
					<col width="5%" />
					<col width="45%" />
				</colgroup>
				
				<thead>
				<tr>
					<th colspan="4" ><c:out value="${fonte.descrizione }"></c:out></th>
				</tr>
				
				<tr>
					<th>Codice</th>
					<th>Descrizione</th>
					<th>Esito</th>
					<th>Descrizione anomalia</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="d" items="${fonte.controlli}">
					<tr>
						<td class=""><c:out value="${d.codice}" /></td>
						<td class=""><c:out value="${d.descrizione}" /> <c:if test="${d.descrizioneEstesa != null}">
								<span class="icon icon-list" data-toggle="txt-tooltip" data-placement="top" title="<c:out value="${d.descrizioneEstesa}"  />"></span>
							</c:if></td>

						<td><img src="../img/ico_cancel.gif"></td>

						<td><c:out value="${d.descrizioneAnomalia}" /> <c:if test="${d.ulterioriInformazioniAnomalia != null}">
								<span class="icon icon-list" data-toggle="txt-tooltip" data-placement="top" title="${d.ulterioriInformazioniAnomalia}"></span>
							</c:if></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</form:form>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />