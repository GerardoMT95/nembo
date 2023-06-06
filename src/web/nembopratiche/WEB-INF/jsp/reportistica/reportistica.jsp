<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
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
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
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
					<li><a href="../elencoBandi/visualizzaBandi.do">Elenco bandi</a> <span class="divider">/</span></li>
					<li class="active">Reportistica </li>
				</ul>
			</div>
		</div>
	</div>
	
	
	<p:navTabsElencoBandiTag activeTab="<%=NavTabsElencoBandiTag.TABS.GRAFICI.toString() %>"></p:navTabsElencoBandiTag>
	<div class="container-fluid"  id="content" style="margin-bottom:2em">
		<m:panel id="panelReport" cssClass="navpanel">
	
	<div class="container-fluid" id="content" style="margin-bottom:2em;">
		<div id="chartContainer" align="center"></div>
		<c:forEach items="${elencoQueryBando}" var="a">
			<script type="text/javascript"> createNewChart(${a.id},'singleChart','chartContainer',false,false);</script>
		</c:forEach>
	</div>
		</m:panel>
	</div>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>