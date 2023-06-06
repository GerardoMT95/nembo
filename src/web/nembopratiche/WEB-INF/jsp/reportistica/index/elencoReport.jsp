<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script src="/nembopratiche/js/nemboReportistica.js"></script>
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Reportistica</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />


	<div class="container-fluid" id="content" style="margin-bottom: 2em">

		<div class="container-fluid" style="margin-bottom: 2em;">
			<h3>REPORTISTICA</h3>
			<ul class="nav nav-tabs" style="margin-top:2em;">
				<li class="active"><a href="#">Report</a></li>
				<li><a href="indexGrafici.do">Grafici</a></li>
			</ul>
			<table id="reportTable" class="table table-hover table-striped table-bordered tableBlueTh ">
				<thead>
					<tr>
						<th></th>
						<th>Descrizione</th>
						<th>Info aggiuntive</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${report}" var="r">
						<tr>
							<td><a href="visualizza_report_${r.idElencoQuery}.do" style="text-decoration: none;"><i class="icon-list icon-large "
									title="Visualizza Report "></i></a></td>
							<td>${r.descrizione}</td>
							<td>${r.infoAggiuntive}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>


	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />