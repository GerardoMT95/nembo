<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

  <p:utente />
  <p:breadcrumbs cdu="CU-NEMBO-109" />
	<p:messaggistica/><p:testata cu="CU-NEMBO-109" />

	<form:form action="" modelAttribute="" method="post" class="form-inline" >
		<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelAllegati">
			<p:allegati isModifica="true" allegati="${allegati}" fileMap="${fileMap}" canUpdate="${canUpdate}" />
			<div class="puls-group" style="margin-top: 2em">
				<div class="pull-left">
					<button type="button" onclick="forwardToPage('../cunembo108/index.do');" class="btn btn-default">annulla</button>
				</div>
				<div class="pull-right">
					<button type="submit" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
				</div>
			</div>
			</m:panel>
		</div>
	</form:form>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />