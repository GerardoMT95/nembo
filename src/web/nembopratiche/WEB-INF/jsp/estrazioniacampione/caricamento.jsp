<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo217/index.do">Gestione Estrazioni</a> <span class="divider">/</span></li>
					<li class="active">Caricamento</li>
				</ul>
			</div>
		</div>
	</div>
	   
	   <div class="container-fluid" style="padding-top:2em" >
	
			<form:form action="" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">
				<m:select label="Tipo estrazione *" name="idTipoEstrazione"
						list="${listTipoEstrazione}" id="idTipoEstrazione"
						valueProperty="id"
						textProperty="label"
						preferRequestValues="${prfvalues}"/>	
						
				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button"
							onclick="history.go(-1);"
							class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma"
							class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>			
			</form:form>
	
	    </div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>