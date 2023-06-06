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

	<p:breadcrumbs cdu="CU-NEMBO-188" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-188" />


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

				<p:abilitazione-azione codiceQuadro="SOSAN" codiceAzione="MODIFICA">
					<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
						<div class="pull-left">
							<button type="button"
								onclick="forwardToPage('../cunembo189/index.do');"
								class="btn  btn-primary">modifica</button>
								<c:if test="${elenco!=null}">	
								<button type="button"
								onclick="return openPageInPopup('../cunembo190/popupindex.do','dlgElimina','Elimina sospensione anticipo','modal-lg',false)"
								class="btn  btn-primary">elimina</button>
								</c:if>
						</div>
						<br class="clear" />
					</div>
				</p:abilitazione-azione>

				<c:choose>
				<c:when test="${elenco!=null}">	
				<table id="operazioni"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
					data-toggle="table">
					<thead>
						<tr>
							<th>Operazione</th>
							<th>Descrizione</th>
							<th>Sospensione</th>
							<th>Motivo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${elenco}" var="o">
							<tr>
								<td><c:out value="${o.codiceLivello}"></c:out></td>
								<td><c:out value="${o.descrizioneLivello}"></c:out></td>
								<td><c:out value="${o.flagSospensioneStr}"></c:out></td>
								<td><c:out value="${o.motivazione}"></c:out></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<br>
				<table class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="10%">
					<col  width="90%">
				</colgroup>
				<tbody>
				<tr>
					<th>Ultima modifica</th>
					<td><c:out value="${ultimaModifica}"></c:out></td>
				</tr>
				</tbody>
			</table>
				</c:when>
				<c:otherwise>
					<div class="stdMessagePanel"><div class="alert alert-warning">Non &egrave; presente alcuna richiesta di sospensione anticipo</div></div>
				</c:otherwise>
				</c:choose>
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