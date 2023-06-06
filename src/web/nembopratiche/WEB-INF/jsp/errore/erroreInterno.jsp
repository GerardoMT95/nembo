<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
  	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">errore interno</li>
				</ul>
			</div>
		</div>
	</div>
	
	
	<div class="container-fluid" id="content" style="margin-bottom:3em">
		<h3>${titolo}</h3>
		<div class="stdMessagePanel">
			<div class="alert alert-danger">
				<p>
					<strong>Attenzione!</strong><br />
					<c:out value="${messaggio}" escapeXml="false"></c:out>
				</p>
			</div>
		</div>
		<c:if test="${esito_mail != null}">
			<div class="stdMessageLoad">			
		        <div class="alert alert-info">
		         <p><c:out value="${esito_mail}"></c:out></p> 
		        </div>
		    </div>
		</c:if>
		
	    <div class="form-group puls-group" style="margin-top:2em">
		<div class="pull-right">
			<button type="button"  onclick="history.go(-1);" class="btn btn-primary">torna alla pagina precedente</button>
		</div>
	    </div>
		
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>