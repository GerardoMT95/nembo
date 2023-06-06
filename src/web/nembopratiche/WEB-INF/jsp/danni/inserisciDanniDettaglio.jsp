<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-298-I" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-298-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaTipologieDanni">
		<br/>
		<form name="formInserisciDanniDettaglio" id="formInserisciDanniDettaglio" action="../cunembo298i/inserisci_danni_conferma.do" method="POST">
			<input type="hidden" name="idDanno" value="${idDanno}" />
			<table id="inserisciDanniTable" class="table table-hover table-striped table-bordered tableBlueTh"
				  data-undefined-text = '-'
			      data-pagination="false" 
				  >
					<thead>
						<tr>
							<c:choose>
								<c:when test='${idDanno == 4 || idDanno == 10}'>
									<th>Descrizione Scorta</th>
									<th>Descrizione</th>
								</c:when>
								<c:when test='${idDanno == 3 || idDanno == 9}'>
									<th>Denominazione</th>
									<th>Descrizione entità danneggiata</th>
								</c:when>
								<c:when test='${idDanno == 2}'>
									<th>Tipo Fabbricato</th>
									<th>Denominazione</th>
								</c:when>
								<c:when test="${idDanno == 1}">
									<th>Descrizione<br/>Allevamento</th>
									<th>Denominazione<br/>allevamento</th>
								</c:when>
							</c:choose>
							<th>Descrizione<br/>Danno</th>
							<th>Quantit&agrave;<br/>Danno</th>
							<th>Unità di<br/>Misura</th>
							<th>Importo (&euro;)</th>
						</tr>
					</thead>
					<tbody>

						<c:choose>
							<c:when test='${idDanno == 4 || idDanno == 10}'>
								<c:choose>
									<c:when test="${listScorte != null && listScorte.size()!= 0}">
										<c:forEach items="${listScorte}" var="scorta">
											<tr>

												<td><c:out value="${scorta.descrizioneScorta}"></c:out>
												<input type="hidden"
													name="hiddenIdScortaMagazzino"
													value="${scorta.idScortaMagazzino}" /> 
												<input
													type="hidden"
													name="idScortaMagazzino_${scorta.idScortaMagazzino}"
													value="${scorta.idScortaMagazzino}" />
												</td>
												<td><c:out value="${scorta.descrizione}"></c:out></td>
												<td><m:textfield
														id="descrizione_${scorta.idScortaMagazzino}"
														name="descrizione_${scorta.idScortaMagazzino}"
														preferRequestValues="${preferRequest}"></m:textfield></td>
												<td><m:textfield
														id="quantita_${scorta.idScortaMagazzino}"
														name="quantita_${scorta.idScortaMagazzino}"
														preferRequestValues="${preferRequest}"></m:textfield></td>
												<td><c:out value="${scorta.descUnitaMisura}"></c:out></td>
												<td><m:textfield
														id="importo_${scorta.idScortaMagazzino}"
														name="importo_${scorta.idScortaMagazzino}"
														preferRequestValues="${preferRequest}"></m:textfield></td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</c:when>
							<c:when test="${idDanno == 3 || idDanno == 9}">
								<c:forEach items="${listMacchine}" var="macchina">
									<tr>
										<td><c:out value="${macchina.dannoDenominazione}"></c:out>
											<input type="hidden" name="hiddenIdMacchina" value="${macchina.idMacchina}"/>
											<input type="hidden" name="idMacchina_${macchina.idMacchina}" value="${macchina.idMacchina}" />
										</td>
										<td><c:out value="${macchina.descTipoGenereMacchina}"></c:out></td>
										<td><m:textfield id="descrizione_${macchina.idMacchina}" name="descrizione_${macchina.idMacchina}" preferRequestValues="${preferRequest}"></m:textfield></td>
										<td>1</td>
										<td><c:out value="${danno.descrizione}"></c:out></td>
										<td><m:textfield id="importo_${macchina.idMacchina}" name="importo_${macchina.idMacchina}" preferRequestValues="${preferRequest}"></m:textfield></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:when test="${idDanno == 2}">
								<c:forEach items="${listFabbricati}" var="fabbricato">
									<tr>
										<td><c:out value="${fabbricato.descrizioneFabbricatoDanno}"></c:out>
											<input type="hidden" name="hiddenIdFabbricato" value="${fabbricato.idFabbricato}"/>
											<input type="hidden" name="idFabbricato_${fabbricato.idFabbricato}" value="${fabbricato.idFabbricato}" />
										</td>
										<td><c:out value="${fabbricato.tipologia}"></c:out></td>
										<td><m:textfield id="descrizione_${fabbricato.idFabbricato}" name="descrizione_${fabbricato.idFabbricato}" preferRequestValues="${preferRequest}"></m:textfield></td>
										<td><m:textfield id="quantita_${fabbricato.idFabbricato}" name="quantita_${fabbricato.idFabbricato}" preferRequestValues="${preferRequest}"></m:textfield></td>
										<td><c:out value="${danno.descrizione}"></c:out></td>
										<td><m:textfield id="importo_${fabbricato.idFabbricato}" name="importo_${fabbricato.idFabbricato}" preferRequestValues="${preferRequest}"></m:textfield></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:when test="${idDanno == 5 || idDanno == 6 || idDanno == 7 || idDanno == 11 }">
								<tr>
									<td><m:textfield id="descrizione" name="descrizione" preferRequestValues="${preferRequest}"></m:textfield></td>
									<td><m:textfield id="quantita" name="quantita" preferRequestValues="${preferRequest}"></m:textfield></td>
									<td><m:textfield id="unitaDiMisura" name="descUnitaMisura" value="${descUnitaMisura}" disabled="true"></m:textfield>
									<td><m:textfield id="importo" name="importo" preferRequestValues="${preferRequest}"></m:textfield></td>
								</tr>
							</c:when>
							<c:when test="${idDanno == 1}">
								<c:forEach items="${listAllevamenti}" var="allevamento">
									<tr>
										<td><c:out value="${allevamento.descrizioneAllevamentoDanno}" />
											<input type="hidden" name="hiddenIdAllevamento" value="${allevamento.idAllevamento}"/>
											<input type="hidden" name="idAllevamento_${allevamento.idAllevamento}" value="${allevamento.idAllevamento}" />
										</td>
										<td>${allevamento.denominazioneAllevamentoDanno}</td>
										<td><m:textfield id="descrizione_${allevamento.idAllevamento}" name="descrizione_${allevamento.idAllevamento}" preferRequestValues="${preferRequest}"></m:textfield></td>
										<td><m:textfield id="quantita_${allevamento.idAllevamento}" name="quantita_${allevamento.idAllevamento}" preferRequestValues="${preferRequest}"></m:textfield></td>
										<td><c:out value="${danno.descrizione}"></c:out></td>
										<td><m:textfield id="importo_${allevamento.idAllevamento}" name="importo_${allevamento.idAllevamento}" preferRequestValues="${preferRequest}"></m:textfield></td>
									</tr>
								</c:forEach>
							</c:when>

							<c:otherwise>
								<c:choose>
									<c:when test="${descUnitaMisura == null}">
											<tr>
												<td><m:textfield id="descrizione" name="descrizione" preferRequestValues="${preferRequest}"></m:textfield></td>
												<td><m:textfield id="quantita" name="quantita" preferRequestValues="${preferRequest}"></m:textfield></td>
												<td>
													<m:select id="unitaMisura" list="${listUnitaMisura}" name="unitaMisura" preferRequestValues="${preferRequest}"></m:select>
												</td>
												<td><m:textfield id="importo" name="importo" preferRequestValues="${preferRequest}"></m:textfield></td>
											</tr>
									</c:when>
									<c:otherwise>
											<tr>
												<td><m:textfield id="descrizione" name="descrizione" preferRequestValues="${preferRequest}"></m:textfield></td>
												<td><m:textfield id="quantita" name="quantita" preferRequestValues="${preferRequest}"></m:textfield></td>
												<td>${descUnitaMisura}</td>
												<td><m:textfield id="importo" name="importo" preferRequestValues="${preferRequest}"></m:textfield></td>
											</tr>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:choose>
					<c:when test="${idDanno == 5 || idDanno == 6 || idDanno == 7 || idDanno == 11  }">
							<h4>Riepilogo danni</h4>
							<c:set var ="tableName"  value ="tblRicercaConduzioniRiepilogo"/>
							<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
							<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
							<table id="${tableName}"
									class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
									data-toggle="table"
									data-pagination=true 
									data-only-info-pagination=true
									data-show-pagination-switch="true" 
									data-pagination-v-align="top"
									data-show-columns="true" 
									data-page-list="[10, 25, 50, 70, 100]"
									data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
									data-show-default-order-button="true"
									data-default-order-column="${defaultOrderColumn}"
									data-default-order-type="${defaultOrderType}"
									data-table-name="${tableName}"
									data-sort-name="${sessionMapNomeColonnaOrdinamento.get(tableName) != null ? 
										sessionMapNomeColonnaOrdinamento.get(tableName) : 
										defaultOrderColumn}"
									data-sort-order="${sessionMapTipoOrdinamento.get(tableName) != null ? sessionMapTipoOrdinamento.get(tableName) : defaultOrderType}"
									data-page-number="${sessionMapNumeroPagina.get(tableName) != null ? sessionMapNumeroPagina.get(tableName) : 1}"
									data-escape-table="true"
							>
								<thead>
									<tr>
									<th data-field="comune" data-sortable = "true" data-escape-field="false">Comune</th>
									<th data-field="sezione" data-sortable = "true">Sezione</th>
									<th data-field="foglio" data-sortable = "true">Foglio</th>
									<th data-field="particella" data-sortable = "true">Particella</th>
									<th data-field="subalterno" data-sortable = "true">Subalterno</th>
									<th data-field="supCatastale" data-sortable = "true" data-formatter="numberFormatter4" >Superficie catastale</th>
									<th data-field="occupazioneSuolo" data-sortable = "true">Occupazione del suolo</th>
									<th data-field="destinazione" class="alignRight" data-sortable = "true">Destinazione</th>
									<th data-field="uso">Uso</th>
									<th data-field="qualita">Qualità</th>
									<th data-field="varieta">Varietà</th>
									<th data-field="superficieUtilizzata" data-formatter="numberFormatter4">Superficie utilizzata</th>
								</tr>
								</thead>
								<tbody>
									<c:forEach items="${listConduzioni}" var="conduzione">
										<tr>	
											<td>
												${conduzione.comune}
												<input type="hidden" name="idUtilizzoDichiarato" value="${conduzione.idUtilizzoDichiarato}"></input>
											</td>
											<td>${conduzione.sezione}</td>
											<td>${conduzione.foglio}</td>
											<td>${conduzione.particella}</td>
											<td>${conduzione.subalterno}</td>
											<td>${conduzione.supCatastale}</td>
											<td>${conduzione.occupazioneSuolo}</td>
											<td>${conduzione.destinazione}</td>
											<td>${conduzione.uso}</td>
											<td>${conduzione.qualita}</td>
											<td>${conduzione.varieta}</td>
											<td>${conduzione.superficieUtilizzata}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<br/>							
							<input type="hidden" name="indietro" value="true" />
							<a id="btnIndietro" class="btn btn-default">Indietro</a>
					</c:when>

					<c:otherwise>
						<br/>							
						<a class="btn btn-default" href="../cunembo298i/index.do">Indietro</a>
					</c:otherwise>				
				</c:choose>				
				
				
		<a class="pull-right"><input type="submit" class="btn btn-primary" value="Inserisci"></input></a>
	    </form>
		</m:panel>
   		<br/>
   		<br/>
   		<br/>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script src="/nembopratiche/js/nembotableformatter.js"></script>
<script type="text/javascript">
	
	function popupSelezione()
	{
		var checkedRadio = $('input[name=idDanno]:checked', '#formVisualizzaDanni').val();
		//TODO: gestire tutti i tipi possibili di danno
		switch(checkedRadio) {
			case '4': //scorte
			case '10':
				openPageInPopup('../cunembo297l/visualizza_scorte_popup.do','dlgVisualizzaScortePopup','Seleziona Scorte','modal-large');
			break;
		}
	}
	
	function postQueryParams(params) {
        params.idUtilizzoDichiarato = "${arrayIdUtilizzoDichiarato}"; // add param1
        return params; // body data
    }
</script>
<c:choose>
	<c:when test="${idDanno == 5 || idDanno == 6 || idDanno == 7 || idDanno == 11 }">
		<script type="text/javascript">
		      $('#btnIndietro').click(function () {
		          $('#formInserisciDanniDettaglio').attr("action", "../cunembo298i/inserisci_danno_dettaglio.do");
		          $('#formInserisciDanniDettaglio').attr("method", "POST");
		          $('#formInserisciDanniDettaglio').submit();
		      });
		</script>
	</c:when>
</c:choose>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />