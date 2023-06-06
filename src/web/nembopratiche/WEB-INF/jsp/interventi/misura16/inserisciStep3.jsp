<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
.stepwizard-step {
	width: 33%;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-266-I" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelInterventi">

			<div class="col-sm-12">
		<div class="col-sm-10">

	<c:if test="${empty isIstruttoria}">
					<div class="stepwizard col-md-offset-1">
						<div class="stepwizard-row setup-panel">
							<div class="stepwizard-step">
								<a href="#step-1" type="button" class="btn btn-default btn-circle">1</a>
								<p>Attività/Partecipante</p>
							</div>
							<c:choose>
								<c:when test="${empty isModifica}">
									<style>
										.stepwizard-step {
											width: 33%;
										}
										</style>
									<div class="stepwizard-step">
										<a href="#step-2" type="button" class="btn btn-default btn-circle">2</a>
										<p>Interventi</p>
									</div>
									<div class="stepwizard-step">
										<a href="#step-3" type="button" class="btn btn-primary btn-circle">3</a>
										<p>Importi</p>
									</div>
								</c:when>
								<c:otherwise>
									<style>
										.stepwizard-step {
											width: 50%;
										}
										</style>
									<div class="stepwizard-step">
										<a href="#step-2" type="button" class="btn btn-primary btn-circle">2</a>
										<p>Importi</p>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>
					<c:if test="${isIstruttoria}">
					<style>
										.stepwizard-step {
											width: 100%;
										}
										</style>
						<div class="stepwizard col-md-offset-1">
							<div class="stepwizard-row setup-panel">
								
								<div class="stepwizard-step">
									<a href="#step-1" type="button" class="btn btn-default btn-circle">1</a>
									<p>Importi</p>
								</div>
							</div>
						</div>
					</c:if>
				</div>
				<br> <br> 
				<div>
					<%@include file="/WEB-INF/jsp/interventi/misura16/modificaImportiInterventi.jsp"%>
				</div>
				<br> <br> 
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />