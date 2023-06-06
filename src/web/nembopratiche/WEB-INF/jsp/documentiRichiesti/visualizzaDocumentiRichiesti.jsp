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
	<p:breadcrumbs cdu="CU-NEMBO-308-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-308-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaDocumentiRichiesti">
		<m:error></m:error>
		<form name="formVisualizzaDocumentiRichiesti" id="formVisualizzaDocumentiRichiesti" action="" method="POST">
				
				<h4>Elenco Documenti Richiesti</h4>
				
				<p:abilitazione-azione codiceQuadro="DOCRI" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<a href='../cunembo308m/index.do' class="btn  btn-primary">modifica</a>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>
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
				
				<c:choose>
					<c:when test="${visualizzazione && sezione.contatoreDoc==0 }">  </c:when>
					<c:otherwise>
					
						<h4>SEZIONE ${sezione.idSezione } - ${sezione.descrizione }</h4>
					
						<table id=""
							class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
							data-toggle="table"
							data-url="get_list_documenti_richiesti.json"
							data-page-list="[10, 25, 50, 70, 100]">
							
							<thead>
								<tr>					
									<p:abilitazione-azione codiceQuadro="DOCRI" codiceAzione="ELENCO">
										<th data-field="descrizione" data-sortable="true" ></th>
									</p:abilitazione-azione>
								</tr>
							</thead>						
						<tbody>
									
						<c:forEach var="doc" items="${sezione.list}">
						
						<c:if test="${doc.idDocumentiRichiesti!=null }">
							<tr >
				              <th style="white-space:pre-wrap; word-wrap:break-word">${doc.descrizione}</th>
				            </tr>
						</c:if>				
			            
		            </c:forEach>		            
		            </tbody>
							
					</table>
					</c:otherwise>
				</c:choose>
				</c:forEach>		
				
				</c:if>
			</form>
		</m:panel>
</div>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script type="text/javascript">
	doErrorTooltip();
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />