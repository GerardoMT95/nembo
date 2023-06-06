<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-309-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-309-L" />
	<p:set-cu-info />
	
	<div class="container-fluid" id="content">
		
		<b:panel>
		<br/>
			<div class="pull-left">
	        <button type="button" onclick="forwardToPage('../cunembo309m/index.do');"  class="btn btn-primary">modifica</button>
	      </div>
		<br/><br/><br/>
			<c:if test="${referenteAssegnato != 'true'}">Nessun referente progetto presente per il procedimento selezionato.</c:if>
			<div class="col-sm-15">			
				<c:if test="${referenteAssegnato == 'true'}"> 	
				<b:panel title="Dettagli referente progetto" id="datiReferente">
					<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
						<colgroup>
							<col width="20%" />
							<col width="80%" />
						</colgroup>
						<tbody>
							<tr class="toggle_target_altri">
								<th>Cognome</th>
								<td>${referente.cognome}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Nome</th>
								<td>${referente.nome}</td>
							<tr class="toggle_target_altri">
								<th>CodiceFiscale</th>
								<td>${referente.codiceFiscale}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Comune</th>
								<td>${referente.descrizioneComune}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Provincia</th>
								<td>${referente.descrizioneProvincia}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>CAP</th>
								<td>${referente.cap}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Telefono</th>
								<td>${referente.telefono}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Cellulare</th>
								<td>${referente.cellulare}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Email</th>
								<td>${referente.email}</td>
							</tr>
						</tbody>
					</table>
				</b:panel>	
				</c:if>
			</div>
			
			<div class="col-sm-12" >
   			     
				<c:if test="${referenteAssegnato == 'true'}"> 					
						<table
							class="myovertable table table-hover table-condensed table-bordered">
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>
							<tbody>
								<tr>
									<th>Ultima modifica</th>
									<td><c:out value="${ultimaModifica}"></c:out></td>
								</tr>
							</tbody>
						</table>
				</c:if>
			</div>
		</b:panel>
	</div>  

				
						
							
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script type="text/javascript">
	doErrorTooltip();
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />