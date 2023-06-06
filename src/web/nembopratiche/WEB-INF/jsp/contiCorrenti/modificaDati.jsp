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
	
		
		<p:breadcrumbs cdu="CU-NEMBO-120"/>
		<p:messaggistica/><p:testata cu="CU-NEMBO-120" />
		
		<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelCC">
		<form:form action="" modelAttribute="" method="post" class="form-inline" style="margin-top: 2px">
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
			
			<c:if test="${conticorrenti == null}">
			<div class="puls-group" style="margin-top:2em">
				 <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../cunembo119/index.do');"  class="btn btn-default">annulla</button>
			      </div>
			    </div>
			</c:if>
			
			<c:if test="${conticorrenti != null}">
				<table summary="Bando" class="table table-hover table-striped table-bordered tableBlueTh">
					<colgroup>
						<col width="2%" />
			           	<col width="18%" />
			           	<col width="20%" />
			           	<col width="20%" />
			           	<col width="20%" />
			           	<col width="20%" />
		            </colgroup>
					<thead>
					  <tr>
						<th scope="col" class=""></th>
						<th scope="col" class="">Banca</th>
						<th scope="col" class="">Sportello</th>
						<th scope="col" class="">Indirizzo</th>
						<th scope="col" class="">Intestazione</th>
						<th scope="col" class="">IBAN</th>
					  </tr>
					</thead>
					<tbody>
					  <c:forEach items="${conticorrenti}" var="cc" >
						  <tr>	
						  	<c:choose>
						  		<c:when test="${contoCorrenteSel != null && contoCorrenteSel.idContoCorrente == cc.idContoCorrente}">
						  			<td class=""><input type="radio" id="${cc.idContoCorrente}" value="${cc.idContoCorrente}" name="contoSelezionato" checked="checked" ></td>
						  		</c:when>
						  		<c:otherwise>
						  			<td class=""><input type="radio" id="${cc.idContoCorrente}" value="${cc.idContoCorrente}" name="contoSelezionato"  ></td>
						  		</c:otherwise>
						  	</c:choose>
						  	
							<td class=""><c:out value="${cc.denominazioneBanca}"/></td>
							<td class=""><c:out value="${cc.denominazioneSportello}"/></td>
							<td class=""><c:out value="${cc.indirizzoSportello}"/></td>
							<td class=""><c:out value="${cc.intestazione}"/></td>
							<td class=""><c:out value="${cc.iban}"/></td>
						  </tr>
					  </c:forEach>
					</tbody>
				</table>
				<div class="puls-group" style="margin-top:2em">
				 <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../cunembo119/index.do');"  class="btn btn-default">annulla</button>
			      </div>
			      <div class="pull-right">  
			        <button type="submit"name="conferma" id="conferma" class="btn btn-primary">conferma</button>
			      </div>
			    </div>
		    </c:if>
		</form:form>
		</m:panel>
		</div>
	

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
 
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
