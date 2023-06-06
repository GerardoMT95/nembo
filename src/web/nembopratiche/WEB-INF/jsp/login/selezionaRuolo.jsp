<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<div class="container-fluid" id="content" style="margin-bottom:3em">
		<h3>Selezionare il ruolo con cui si desidera accedere</h3>
	
	<form:form action="" commandName="loginModel" modelAttribute="loginModel" method="post">
		<form:errors path="ruolo" cssClass="stdMessagePanel alert alert-danger" element="div" htmlEscape="false" />
		<div style="margin-top:2em">
		<c:forEach var="ruolo" items="${ruoli}">
				<div class="row">
				<div class="col-lg-6">
				    <div class="input-group">
				      <span class="input-group-addon">
				        <input type="radio" name="ruolo" id="optradio_${ruolo.codice}"  value="${ruolo.codice}"/>
				      </span>
				      <input type="text" style="background-color:#FFF;" class="form-control" value="${ruolo.descrizione}" readonly="readonly">
				    </div><!-- /input-group -->
				  </div><!-- /.col-lg-6 -->
				  </div>
		</c:forEach>
		</div>
		<div class="form-group puls-group" style="margin-top:2em">
	      <div class="pull-right">
	        <button type="submit" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
	      </div>
	    </div>
	</form:form>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
