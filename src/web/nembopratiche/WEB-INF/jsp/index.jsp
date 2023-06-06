<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<%
		session.removeAttribute("idBandoSelezionato");
	%>
	<%
		session.removeAttribute("RicercaProcedimentiVO");
	%>
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li class="active">Home</li>
				</ul>
			</div>
		</div>
	</div>

	<p:messaggistica />
	<div class="container-fluid mainmenu" id="content" >
		<table style="z-index: 10;min-width:100%">
			<c:forEach items="${links}" var="link">
				<tr style="background-color: rgba(255, 255, 255, 0.8)">
					<td style="width:100%"><p:link href="../${link.link}" useCase="${link.useCase}"
							readWrite="${link.readWrite}" title="${link.title}"
							forceVisualization = "${link.forceVisualization}"
							description="${link.description}" onClick="${link.onclick}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script>
		$( document ).ready(function() {
			$(".toggle_handle").click(function() {
			  var id = $(this).attr('id');
			  var secId = id.split('toggle_handle_')[1];
			  $('.toggle_target_'+secId).toggle();
			});
		});
	</script> 
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />