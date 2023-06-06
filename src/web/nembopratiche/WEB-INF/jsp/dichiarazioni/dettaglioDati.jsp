<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
<p:utente/>
		<p:breadcrumbs cdu="CU-NEMBO-106-D"/>
		<p:messaggistica/><p:testata cu="CU-NEMBO-106-D" />
		
		<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelDich">
			<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}"></c:out>
						</p>
					</div>
				</div>
			</c:if>
			
			<p:abilitazione-azione codiceQuadro="DICH" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top:1em;margin-bottom: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo107d/index.do');" class="btn  btn-primary">modifica</button>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>
	
			<p:dichiarazioni isModifica="false" />
			
			<table class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="10%">
					<col  width="90%">
				</colgroup>
				<tbody>
				<tr>
					<th>Ultima modifica</th>
					<td><c:out value="${ultimaModifica}"></c:out></td>
				</tr>
				</tbody>
			</table>
			</m:panel>
		</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
