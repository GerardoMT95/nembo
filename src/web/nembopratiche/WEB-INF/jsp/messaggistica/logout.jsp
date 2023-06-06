<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<c:if test="${!isPopup}">
		<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	</c:if>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<div class="container-fluid" id="content">
		<br />
		<div class="alert alert-danger">
			Ci scusiamo ma la sessione di lavoro &egrave; stata terminata automaticamente dal sistema con la seguente motivazione:<br />
			<h3 class="alert-danger">${logoutErrorMessage}</h3>
		</div>
		<a class="btn btn-primary" href="/" role="button">chiudi</a>
	</div>
	<script type="text/javascript">
    var noLogout = true;
  </script>
	<br class="left" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<c:if test="${!isPopup}">
		<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
	</c:if>
	<c:if test="${isPopup}">
		</html>
	</c:if>