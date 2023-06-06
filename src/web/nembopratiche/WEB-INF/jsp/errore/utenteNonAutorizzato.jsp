<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container-fluid" id="content" style="margin-bottom:1.5em">
		<div class="alert alert-danger alert-error">
			<label>${errore}</label>
		</div>
    <div class="form-group puls-group" style="margin-top: 1.5em">
      <div class="col-sm-12">
        <button type="button" data-dismiss="modal" name="indietro" id="conferma" class="btn btn-primary pull-right" onclick="history.go(-1);">indietro</button>
      </div>
    </div>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />