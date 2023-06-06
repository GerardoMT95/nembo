<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-263-I" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-I" />
	
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDocumenti">
		
		<div class="col-sm-10" >
		
		<div class="stepwizard col-md-offset-1">
		    <div class="stepwizard-row setup-panel">
		      <div class="stepwizard-step">
		        <a href="#step-1" type="button" class="btn btn-default btn-circle" disabled="disabled">1</a>
		        <p>Dati Documento</p>
		      </div>
		      <div class="stepwizard-step">
		        <a href="#step-2" type="button" class="btn btn-primary btn-circle" >2</a>
		        <p>Scelta fornitore</p>
		      </div>
		    </div>
		  </div>
			  <div>
				<m:error />
				
				<form:form action="confermaInserisciDocumento.do" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">
				<div class="container-fluid" id="content">
					<br />
					<div class="alert alert-warning" role="alert">Stai cercando di inserire un documento spesa già presente su un altro procedimento, vuoi inserirlo nuovamente?</div>
					<div class="col-sm-6">
				        <button type="button" onclick="forwardToPage('../cunembo263l/index.do');"  class="btn btn-default">indietro</button>
					</div>
					<div class="col-sm-6">
						<button type="submit" class="btn btn-primary pull-right" >conferma</button>
					</div>
					<br class="clear" />
				</div>
				</form:form>
				
			</div>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
