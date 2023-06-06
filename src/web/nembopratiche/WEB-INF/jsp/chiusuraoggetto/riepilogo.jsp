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
		<p:messaggistica/><p:testata cu="${useCaseController}" />

		<div class="container-fluid" id="content" style="margin-bottom: 3em; margin-top: 2em">
		  <div class="alert alert-success" role="alert">
      L'oggetto ${po.descrizione} &egrave; stato correttamente chiuso e si trova nello stato ${po.descStato}
      <c:if test="${po.identificativo!=null}">
      <br />All'oggetto &egrave; stato assegnato il numero ${po.identificativo}
      </c:if>
      </div>
		</div>
	</form:form>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />