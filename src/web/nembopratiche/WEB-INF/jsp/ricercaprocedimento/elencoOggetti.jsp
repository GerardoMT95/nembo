<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>



	<p:utente />

	
			<div class="container-fluid">
				<div class="row">
					<div class="moduletable">
						<ul class="breadcrumb">
							<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
							<li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li>
							<li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li>
							<li class="active">Dettaglio oggetto</li>
						</ul>
					</div>
				</div>
			</div>
	
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-129" />

	<div class="container-fluid" id="content" style="margin-bottom:3em">
		
		<table class="table table-hover table-striped table-bordered tableBlueTh">
			<thead>
				<tr>
					<th>
						<c:if test="${procedimentoValido}">
							<p:abilitazione-cdu codiceCdu="CU-NEMBO-135">
								<a href="crea_nuova_istanza.do" 
									onclick="return openPageInPopup('../cunembo135/popupindex_0_0.do','dlgNuovo','Crea Nuova Istanza','modal-lg',false)" 
								    style="text-decoration: none;"><i class="ico24 ico_add" title="Crea nuova Istanza per il procedimento"></i></a>
							</p:abilitazione-cdu>
							<p:abilitazione-cdu codiceCdu="CU-NEMBO-136">
								<a href="crea_nuovo_oggetto.do" 
									onclick="return openPageInPopup('../cunembo136/popupindex_0_0.do','dlgNuovo','Crea Nuovo Oggetto','modal-lg',false)" 
								    style="text-decoration: none;"><i class="ico24 ico_add" title="Crea Nuovo Oggetto per il procedimento"></i></a>
							</p:abilitazione-cdu>
						</c:if>
					</th>
					<th>Oggetto/Istanza</th>
					<th>Numero procedimento</th>
					<th>Data inizio</th>
					<th>Data fine</th>
					<th data-switchable="false">Data protocollo trasmissione/approvazione</th>
					<th data-switchable="false">Numero protocollo</th>
					<th>Data trasmissione</th>
					<th>Esito</th>
					<th>Stato</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${elenco}" var="a">
					<tr>
						<th>
							<c:if test="${procedimentoValido}">
								<p:abilitazione-cdu codiceCdu="CU-NEMBO-135" >
									<a href="crea_nuova_istanza.do" 
										onclick="return openPageInPopup('../cunembo135/popupindex_${a.idGruppoOggetto}_${a.codRaggruppamento}.do','dlgNuovo','Crea Nuova Istanza','modal-lg',false)" 
									    style="text-decoration: none;"><i class="ico24 ico_add" title="Crea nuova Istanza per la <c:out value="${a.descrizione}"></c:out>"></i></a>
								</p:abilitazione-cdu>
								<p:abilitazione-cdu codiceCdu="CU-NEMBO-136" >
									<a href="crea_nuovo_oggetto.do" 
										onclick="return openPageInPopup('../cunembo136/popupindex_${a.idGruppoOggetto}_${a.codRaggruppamento}.do','dlgNuovo','Crea Nuovo Oggetto','modal-lg',false)" 
									    style="text-decoration: none;"><i class="ico24 ico_add" title="Crea Nuovo Oggetto per la <c:out value="${a.descrizione}"></c:out>"></i></a>
								</p:abilitazione-cdu>
							</c:if>
						</th>
						<td colspan="9" class="noPadding">
							<h4><c:out value="${a.descrizione}"></c:out></h4>
						</td>
					</tr>
					<c:forEach items="${a.oggetti}" var="b">
						<tr>
							<td scope="row">
								<a href="../procedimento/switch_${a.idProcedimento}_${b.idProcedimentoOggetto}.do" style="text-decoration: none;"><i class="icon-list icon-large" title="vai al dettaglio"></i></a>
								
								<c:choose>
									<c:when test="${b.flagIstanza == 'S' && b.dataFine == null}">
										<p:abilitazione-cdu codiceCdu="CU-NEMBO-137" extCodAttore="${b.extCodAttore}">
											<a href="elimina_istanza.do" 
												onclick="return openPageInPopup('../cunembo137/popupindex_E_${a.idProcedimento}_${b.idProcedimentoOggetto}_${b.idBandoOggetto}.do','dlgElimina','Elimina Istanza','modal-lg',false)" 
											    style="text-decoration: none;"><i class="ico24 ico_trash" title="Elimina Istanza"></i></a>
										</p:abilitazione-cdu>
									</c:when>
									<c:when test="${b.flagIstanza == 'N' && b.dataFine == null}">
										<p:abilitazione-cdu codiceCdu="CU-NEMBO-138" extCodAttore="${b.extCodAttore}">
											<a href="elimina_oggetto.do" 
												onclick="return openPageInPopup('../cunembo138/popupindex_E_${a.idProcedimento}_${b.idProcedimentoOggetto}_${b.idBandoOggetto}.do','dlgElimina','Elimina Oggetto','modal-lg',false)" 
											    style="text-decoration: none;"><i class="ico24 ico_trash" title="Elimina Oggetto"></i></a>
										</p:abilitazione-cdu>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>								
								
							</td>
							<td><c:out value="${b.descrizione}"></c:out></td>
							<td><c:out value="${b.codiceDomanda}"></c:out></td>
							<td><fmt:formatDate value="${b.dataInizio}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td><fmt:formatDate value="${b.dataFine}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td><c:out value="${b.dataTrasmissioneStr}"></c:out></td>
							<td><c:out value="${b.numeroProtocollo}"></c:out></td>
							<td><fmt:formatDate value="${b.dataTrasmissione}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td><c:out value="${b.lastEsitoDescr}"></c:out></td>
							<td><c:out value="${b.stato}"></c:out></td>
						</tr>
					</c:forEach>
				</c:forEach>			
			</tbody>
		</table>
	</div>


	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	
	 <script>
		$( document ).ready(function() {
			$(".toggle_handle").click(function() {
			  var id = $(this).attr('id');
			  var secId = id.split('toggle_handle_')[1];
			  $('.toggle_target_'+secId).toggle();
			  if($('.toggle_target_'+secId).is(":visible"))
				  $('#icona_espandi_'+secId).removeClass('icon-plus-sign').addClass('icon-minus-sign');
			  else
				  $('#icona_espandi_'+secId).removeClass('icon-minus-sign').addClass('icon-plus-sign');
			});
		});
	</script> 
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />