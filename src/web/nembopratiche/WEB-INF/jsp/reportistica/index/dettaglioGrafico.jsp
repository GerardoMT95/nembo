<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="/nembopratiche/js/nemboReportistica.js"></script>
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
						<li><a href="indexGrafici.do">Reportistica</a> <span class="divider">/</span></li>
					<li class="active">Dettaglio Grafico</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="container-fluid" id="content" style="margin-bottom:5em">
		<div id="onePageChartContainer" align="center"></div>
		<script type="text/javascript"> createNewChart(${dettIdElencoQuery},'onePageSingleChart','onePageChartContainer',true,true);</script>
		<div class="form-group puls-group" style="margin-top:2em;margin-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-primary">Indietro</button>
			</div>
	    </div>
	</div>
	
	

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>