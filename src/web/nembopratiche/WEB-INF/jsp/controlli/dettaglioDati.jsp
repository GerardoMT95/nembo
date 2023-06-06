<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
	<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-114" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-114" />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelControlli">
			<div class="stdMessagePanel stdMessageLoad" style="display: none">
				<div class="alert alert-info">
					<p>Attenzione: Il sistema sta effettuando il controllo sui dati
						inseriti nella pratica in esame; l'operazione potrebbe richiedere
						alcuni secondi...</p>
				</div>
				<span class="please_wait" style="vertical-align: middle"></span>
				Attendere prego ...
			</div>
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

			<p:abilitazione-azione codiceQuadro="CONTR" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<button type="button"
							onclick="this.disabled=true;$('.stdMessageError').hide();$('.stdMessageLoad').show();forwardToPage('../cunembo115/index.do');"
							class="btn  btn-primary">esegui controlli</button>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>

    <div id="filter-bar" style="position: absolute;height:3em;"> </div>
    	<form:form action="" modelAttribute="filtroAziendeForm" method="post">
	<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoControlli")}' >
	</form:form> 
    <br><br>
	<table summary="Bando" id="elencoControlli" 
				class="table table-hover table-striped table-bordered " 
				        data-show-filter="true">


				<c:forEach var="c" items="${fonteControlloDTOList}">
					<c:choose>
						<c:when
							test="${oggettoAperto && esistonoAnomalieBloccanti && esistonoAnomalieGiustificabili}">
							<c:set var="colSpan" value="${6}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="colSpan" value="${5}"></c:set>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="${colSpan}" class="caption"><c:out
								value="${c.descrizione }"></c:out></th>
					</tr>

					<tr>
						<td colspan="${colSpan}"><c:choose>
								<c:when
									test="${c.controlli[0].idEsecuzioneControllo == null || c.controlli[0].idEsecuzioneControllo == 0}">
									<div class="alert alert-warning" role="alert">
										I controlli sulla fonte
										<c:out value="${c.descrizione }"></c:out>
										non sono mai stati eseguiti
									</div>
								</c:when>
								<c:when test="${c.controlli[0].flagElaborazioneInCorso == 'S'}">
									<div class="alert alert-danger" role="alert">ATTENZIONE,
										L'ULTIMA ESECUZIONE E' TERMINATA IN MODO ANOMALO. SI PREGA DI
										RIPETERE L'ELABORAZIONE DEI CONTROLLI</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-success" role="alert">
										I controlli sulla fonte
										<c:out value="${c.descrizione }"></c:out>
										sono stati eseguiti il
										<fmt:formatDate value="${c.controlli[0].dataEsecuzione}"
											pattern="dd/MM/yyyy HH:mm:ss" />
										(
										<c:out value="${c.controlli[0].utenteEsecuzione }"></c:out>
										)
									</div>
								</c:otherwise>
							</c:choose></td>
					</tr>

					<tr>
						<!-- se il controllo fallisce poi metto questo -->
						<c:if
							test="${oggettoAperto && esistonoAnomalieBloccanti && esistonoAnomalieGiustificabili }">
							<th>Risolvi Anomalia</th>
						</c:if>
						<th>Codice</th>
						<th>Descrizione</th>
						<th>Esito</th>
						<th>Risolto</th>
						<th>Descrizione anomalia</th>
					</tr>
					<c:forEach var="d" items="${c.controlli}">
						<tr>
							<c:if
								test="${oggettoAperto && esistonoAnomalieBloccanti && esistonoAnomalieGiustificabili}">

								<td><c:if
										test="${d.flagAnomaliaGiustificabile == 'S' &&  d.idAnomalieControllo!= null && d.idAnomalieControllo!=0 && d.gravita== 'B'}">
										
										<c:choose>
										<c:when test="${d.idSoluzioneAnomalia != null && d.idSoluzioneAnomalia != 0}">
											<m:icon icon="modify" href="#" 
											onclick="return modificaGiustificazione(${d.idControllo},${d.idSoluzioneAnomalia})"/>
											<m:icon icon="trash" href="#" onclick="return eliminaGiustificazione(${d.idSoluzioneAnomalia})" />
										</c:when>
										<c:otherwise>
										<m:icon icon="modify" href="#" 
											onclick="return modificaGiustificazione(${d.idControllo},${d.idSoluzioneAnomalia})"/>
										</c:otherwise>
										</c:choose>
									</c:if></td>
							</c:if>


							<td class=""><c:out value="${d.codice}" /></td>
							<td class=""><c:out value="${d.descrizione}" /> <c:if
									test="${d.descrizioneEstesa != null}">
									<span class="icon icon-list" data-toggle="txt-tooltip"
										data-placement="top"
										title="<c:out value="${d.descrizioneEstesa}"  />"></span>
								</c:if></td>

							<td><c:choose>
									<c:when
										test="${d.idAnomalieControllo == null || d.idAnomalieControllo == 0}">
										<img src="../img/ico_ok.gif">
									</c:when>
									<c:when test="${d.gravita == 'B' || d.gravita == 'G'}">
										<img src="../img/ico_cancel.gif">
									</c:when>
									<c:when test="${d.gravita == 'W'}">
										<img src="../img/ico_alert.gif">
									</c:when>
								</c:choose></td>
							<td><c:if
									test="${d.idSoluzioneAnomalia != null && d.idSoluzioneAnomalia != 0}">
									<a title="visualizza giustificazione" href="javascript:void(0);" onclick="showGiustificazione(${d.idControllo});"><img
										src="../img/ico_ok.gif"></a>
								</c:if></td>
							<td><c:out value="${d.descrizioneAnomalia}" /> <c:if
									test="${d.ulterioriInformazioniAnomalia != null}">
									<span class="icon icon-list" data-toggle="txt-tooltip"
										data-placement="top"
										title="${d.ulterioriInformazioniAnomalia}"></span>
								</c:if></td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
					
		</m:panel>
	</div>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	
	//GESTIONE FILTRO 
	$( document ).ready(function() {
				
	$('#filter-bar').bootstrapTableFilter({
	    filters:[
	         	{
					field: 'descrizione',   
                    label: 'Descrizione',  
                    type: 'search'   
				},
				{
					field: 'codice',   
                    label: 'Codice',  
                    type: 'search'   
				},
				{
				    field: 'esito',    
				    label: 'Esito',   
				    type: 'selectNoSearch',
				    values: [
				             {id: 'W', label: '<img src="../img/ico_alert.gif">'},
				             {id: 'B', label: '<img src="../img/ico_cancel.gif">'},
				             {id: 'N', label: '<img src="../img/ico_ok.gif">'}
				         ]
				},
				{
				    field: 'risolto',    
				    label: 'Risolto',   
				    type: 'selectNoSearch',
				    values: [
				             {id: 'S', label: 'Sì'},
				             {id: 'N', label: 'No'}
				         ]
				}
				],
		        connectTo: "",
		        onSubmit: function() {
		        	var data = $('#filter-bar').bootstrapTableFilter('getData');
		            var elabFilter = JSON.stringify(data);
					//reset variabili per ricostruire correttamente la tabella
				
		            $.ajax({
		            	  type: "POST",
		            	  async: false,
		            	  url: '../session/salvaFiltri.do',
		            	  data: "key=elencoControlli&filtro="+elabFilter,
		            	  success: function( data ){
		            	  }
		            	});
		            console.log(data);
		        },
				onRefreshSuccess: function(){
						location.reload();
					},
				onSubmitClick: function(){
			
						location.reload();
					}
						    
		    });
	var filterJSON = $('#filtroAziende').val();
	if(filterJSON){
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	}

	});
	
	function eliminaGiustificazione(idSoluzioneAnomalia){
		return openPageInPopup('../cunembo234/conferma_elimina_' + idSoluzioneAnomalia + '.do', 'dlgModifica', 'Elimina giustificazione', 'modal-lg', false, null);
	}
	
	function modificaGiustificazione(idControllo,idSoluzioneAnomalia){
			return openPageInPopup('../cunembo234/modificaGiustificazione_' + idControllo + '_'+idSoluzioneAnomalia+'.do', 'dlgModifica', 'Modifica giustificazione', 'modal-lg', false, null);
	}
		
	function showGiustificazione(idControllo){
		return openPageInPopup('../cunembo234/showGiustificazione_' + idControllo + '.do', 'dlgModifica', 'Dettaglio giustificazione', 'modal-lg', false, null);
		
	}
	
 </script>
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />