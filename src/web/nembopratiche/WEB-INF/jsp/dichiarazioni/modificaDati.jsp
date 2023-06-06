<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  <p:utente/>
	<p:breadcrumbs cdu="CU-NEMBO-107-D"/>
	<p:messaggistica/><p:testata cu="CU-NEMBO-107-D" />
	<form:form action="" modelAttribute="" method="post" class="form-inline" >
		<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelDich">
		<b:error />
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
		
			<p:dichiarazioni isModifica="true" />
		
			<div class="puls-group" style="margin-top:2em">
			 <div class="pull-left">
		        <button type="button" onclick="forwardToPage('../cunembo106d/index.do');"  class="btn btn-default">annulla</button>
		      </div>
		      <div class="pull-right">  
		        <button type="submit"name="conferma" id="conferma" class="btn btn-primary">conferma</button>
		      </div>
		    </div>
		</m:panel>
		</div>
	</form:form> 

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
