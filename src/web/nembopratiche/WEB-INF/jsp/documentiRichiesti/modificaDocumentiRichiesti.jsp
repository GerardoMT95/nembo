<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-308-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-308-M" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaDocumentiRichiesti">
		<m:error></m:error>
		<form name="formVisualizzaDocumentiRichiesti" id="formVisualizzaDocumentiRichiesti" action="../cunembo308m/confermaModifica.do" method="POST">
				
			<h4>Elenco Documenti Richiesti</h4>
				
			<c:if test="${numeroDoc == 0}">
				<div class="container-fluid" id="content">
					<div class="alertalert alert-success" role="alert">
					  	<p style="margin-left:1em;margin-right:2em;margin-top:1em;margin-bottom:1em;height:40px;">
							Non ci sono documenti richiesti da visualizzare.
						</p>
					</div>
				</div>
			</c:if>
			<c:if test="${numeroDoc != 0}">	
			<c:forEach var="sezione" items="${sezioniDaVisualizzare}">		
				
				<h4>SEZIONE ${sezione.idSezione } - ${sezione.descrizione }</h4>
			
				<table 	id="visualizzaModificaDocumenti"
							class=" table table-hover table-striped table-bordered tableBlueTh show-totali"
							data-page-list="[10, 25, 50, 70, 100]">
												
					<thead>
						<tr>					
							<th   data-field="sezione" data-sortable="true" ></th>
							<th class='col-sm-12' data-field="descrizione" data-sortable="true" ></th>
						</tr>
					</thead>
					
					<tbody>					
		              	<c:forEach var="doc" items="${sezione.list}">
		              		<c:choose>
		             			<c:when test="${sezione.idSezione.equals(\"H\")}">
				              		<tr>
					              		<td><m:checkBox checked="${doc.descrizione!=null}" controlSize="3" name="check_${sezione.idSezione}_1" value="check_${sezione.idSezione}_1" preferRequestValues="${preferRequest }"></m:checkBox></td>
					              		<td><m:textarea name='textFieldH' id='textFieldH' preferRequestValues="${preferRequest }" >${doc.descrizione}</m:textarea></td>
			              		   </tr>
				              	</c:when>
				              	<c:otherwise>
				              		<tr >
					              		<td><m:checkBox checked="${doc.idDocumentiRichiesti!=null&&doc.idDocumentiRichiesti!=0}" controlSize="3" name="check" value="${doc.idTipoDocRichiesti }" preferRequestValues="${preferRequest }"></m:checkBox></td>
					              		<th>${doc.descrizione}</th>
				              		</tr>
			              		 </c:otherwise>
		              		  </c:choose>
		              	</c:forEach>				            
					</tbody>
						
				</table>			
			</c:forEach>				
			</c:if>
				
			<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
				
					<input type="submit" href='../cunembo308m/confermaModifica.do' class="btn  btn-primary pull-right" value="conferma"></input>
					<a href='../cunembo308l/index.do' class="btn  btn-default" >annulla</a>
				
				<br class="clear" />
			</div>
		</form>			
		</m:panel>
	</div>
	
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script type="text/javascript">
	doErrorTooltip();
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />