<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
  <c:if test="${!isPopup}">
	 <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	</c:if>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/header.html" />
  <input type="hidden" name="hiddenSessioneScaduta" />
	<div class="container-fluid" id="content">
		<h2>
			Ci scusiamo ma la sessione di lavoro è scaduta o non si è ancora autenticati<br /> Si prega di effettuare (nuovamente) l'autenticazione
		</h2>
		<br /> <a class="btn btn-primary" href="/${requestScope.webContext}/login/sisp/seleziona_ruolo.do" role="button">Autenticazione utenti privati</a><br /> <br /> <a
			class="btn btn-primary" href="/${requestScope.webContext}/login/wrup/seleziona_ruolo.do" role="button">Autenticazione utenti Pubblica Amministrazione</a>
	</div>
	<br class="left" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<c:if test="${!isPopup}">
  	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
	</c:if>
	 <c:if test="${isPopup}">
	 </html>
</c:if>