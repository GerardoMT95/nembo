<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:breadcrumbs cdu="CU-NEMBO-162" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-162" />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelControlli">
			<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger" role="alert">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}"></c:out>
						</p>
					</div>
				</div>
			</c:if>
			<c:if test="${msgInfo != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-success" role="alert">
						<p>
							<c:out value="${msgInfo}"></c:out>
						</p>
					</div>
				</div>
			</c:if>
			<div class="puls-group" style="margin-top: 2em">
				<div class="pull-right">
					<button type="button" onclick="forwardToPage('../cunembo160/index.do');" class="btn btn-primary">Avanti</button>
				</div>
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />