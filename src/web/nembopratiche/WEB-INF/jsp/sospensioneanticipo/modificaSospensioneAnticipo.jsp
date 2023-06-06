<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

	<p:breadcrumbs cdu="CU-NEMBO-189" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-189" />


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

				<form:form action="" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">


					<table id="operazioniTable"
						class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
						<thead>
							<tr>
								<th>Operazione</th>
								<th>Descrizione</th>
								<th>Sospensione</th>
								<th>Motivo</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${elenco}" var="o" varStatus="idx">
								<tr>

									<td><c:out value="${o.codiceLivello}"></c:out></td>
									<td><c:out value="${o.descrizioneLivello}"></c:out></td>
									<td><m:radio-list id="sospensione_${idx.index}"
											name="sospensione_${idx.index}" list="${radio}"
											selectedValue="${o.flagSospensione}" onTable="true" inline="false"
											controlSize="10" valueProperty="value" textProperty="label"
											preferRequestValues="${prfvalues}" /></td>
									<td><m:textarea id="motivazione_${idx.index}"
											placeholder="Inserire la motivazione"
											name="motivazione_${idx.index}"
											preferRequestValues="${prfvalues}">${o.motivazione}</m:textarea></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
						<div class="col-sm-12" style="padding-bottom: 2em;">
							<button type="button"
								onclick="forwardToPage('../cunembo188/index.do');"
								class="btn btn-default">indietro</button>
							<button type="submit" name="conferma" id="conferma"
								class="btn btn-primary pull-right">conferma</button>
						</div>
				</form:form>

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