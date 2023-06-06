<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<style>
.textRight {
	text-align: right;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="tabPanel">
			<form name="elencoForm" id="elencoForm" method="post" action="">
				<h3>Importazione superfici GIS - Terminata</h3>
				<c:if test="${errorMessage==null}">
				<div class="alert alert-success">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>&nbsp;Elaborazione terminata con successo, sono state importate le superfici gis per tutte le particelle
				</div>
				</c:if>
        <c:if test="${errorMessage!=null}">
				<div class="alert alert-danger">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>&nbsp;Elaborazione terminata con ERRORI: <c:out value="${errorMessage}" />
				</div>
        </c:if>
				<div class="col-md-12">
          <a class="btn btn-default" href="../cunembo212l/index.do" >chiudi</a>
				</div>
			</form>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />