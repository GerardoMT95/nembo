<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
  <p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo217/index.do">Gestione estrazioni</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo219/index_${idNumeroLotto}.do">Dettaglio estrazione</a> <span class="divider">/</span></li>
				  <li class="active">Elimina</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>
	<form:form action="" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">
	<div class="container-fluid" id="content">
		<br />
		<div class="alert alert-warning" role="alert">Proseguendo il sistema effettuer&agrave; l'eliminazione dei dati dell'estrazione a campione. Continuare?</div>
		<div class="col-sm-6">
			<button type="button" class="btn btn-default" onclick="history.go(-1)">annulla</button>
		</div>
		<div class="col-sm-6">
			<button type="submit" class="btn btn-primary pull-right" >conferma</button>
		</div>
		<br class="clear" />
	</div>
	</form:form>
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />