<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
<p:utente/>

	    <p:breadcrumbs cdu="CU-NEMBO-119" />
	 	<p:messaggistica/><p:testata cu="CU-NEMBO-119" />
		
		<div class="container-fluid" id="content" >
		<m:panel id="panelCC">
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
			
			<p:abilitazione-azione codiceQuadro="CC" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top:1em;margin-bottom: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo120/index.do');" class="btn  btn-primary">modifica</button>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>
	
			<c:if test="${contoCorrente != null}">
				<table summary="Bando" class="table table-hover table-striped table-bordered tableBlueTh">
					<colgroup>
			           	<col width="20%" />
			           	<col width="20%" />
			           	<col width="20%" />
			           	<col width="20%" />
			           	<col width="20%" />
		            </colgroup>
					<thead>
					  <tr>
						<th scope="col" class="">Banca</th>
						<th scope="col" class="">Sportello</th>
						<th scope="col" class="">Indirizzo</th>
						<th scope="col" class="">Intestazione</th>
						<th scope="col" class="">IBAN</th>
					  </tr>
					</thead>
					<tbody>
					  <tr>	
						<td class=""><c:out value="${contoCorrente.denominazioneBanca}"/></td>
						<td class=""><c:out value="${contoCorrente.denominazioneSportello}"/></td>
						<td class=""><c:out value="${contoCorrente.indirizzoSportello}"/></td>
						<td class=""><c:out value="${contoCorrente.intestazione}"/></td>
						<td class=""><c:out value="${contoCorrente.iban}"/></td>
					  </tr>
					</tbody>
				</table>
			</c:if>
			</m:panel>
		</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
