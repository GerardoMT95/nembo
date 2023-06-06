<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<style>
.radio {
	margin-top: 0px !important;
	padding-top: 0px !important;
}

.checkbox {
	margin-top: 0px !important;
	padding-top: 0px !important;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCase}" />
	<p:messaggistica />
	<p:testata cu="${useCase}" />
	<div class="container-fluid" id="content">
		<m:panel id="panelQuadroDinamico">
		  <m:error/>
			<form class="form-horizontal" method="post" action="">
				<c:forEach items="${quadroDinamico.recordSingolo}" var="g" varStatus="status">
					<c:choose>
						<c:when test="${g.sezioneConTitolo}">
							<m:panel id="gruppo_${status.index}" title="${g.titolo}" collapsible="true">
								<c:forEach items="${g.elementi}" var="e" varStatus="status">
									<%@include file="/WEB-INF/jsp/quadridinamici/include/editRecord.jsp"%>
								</c:forEach>
							</m:panel>
						</c:when>
						<c:otherwise>
							<c:forEach items="${g.elementi}" var="e" varStatus="status">
								<%@include file="/WEB-INF/jsp/quadridinamici/include/editRecord.jsp"%>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<a href="${annulla}" class="btn  btn-primary">annulla</a>
					</div>
					<div class="pull-right">
						<input type="submit" class="btn  btn-primary" value="conferma" />
					</div>
					<br class="clear" />
				</div>

			</form>
			<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />