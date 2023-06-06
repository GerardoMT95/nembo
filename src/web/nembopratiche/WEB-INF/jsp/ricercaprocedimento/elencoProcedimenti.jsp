<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="index.do">Ricerca procedimento</a> <span class="divider">/</span></li>
					<li class="active">Elenco procedimenti</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<form:form action="" modelAttribute="filtroAziendeForm" method="post">
		<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoProcedimenti")}'>


	</form:form>
	<div class="container-fluid" id="content" style="margin-bottom: 3em; position: relative">
		<h3 style="margin-bottom: 1em">Elenco procedimenti</h3>
		
		
		<!-- VISUALIZZO FILTRI IMPOSTATI inizio-->
		<m:panel id="riepilogofiltri" title="Riepilogo Filtri" startOpened="false" >
			<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<c:if test="${RicercaProcedimentiVO.identificativo != null}">
						<tr>
							<th>Identificativo</th>
							<td><c:out value="${RicercaProcedimentiVO.identificativo}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.cuaa != null}">
						<tr>
							<th>CUAA</th>
							<td><c:out value="${RicercaProcedimentiVO.cuaa}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${fn:length(livelli)>0 }">
						<tr>
							<th>Misura/Sottomisura/Operazione</th>
							<td>
								<c:forEach items="${livelli}" var="i"><c:out value="${i.descrEstesa}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(bandi)>0 }">
						<tr>
							<th>Bando</th>
							<td>
								<c:forEach items="${bandi}" var="i"><c:out value="${i.descrizione}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(amministrazioni)>0 }">
						<tr>
							<th>Amministrazione (Organismo Delegato)</th>
							<td>
								<c:forEach items="${amministrazioni}" var="i"><c:out value="${i.descrizione}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(statiProcedimento)>0 }">
						<tr>
							<th>Stato del procedimento</th>
							<td>
								<c:forEach items="${statiProcedimento}" var="i"><c:out value="${i.descrStatoOggetto}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(selectGuppiRicerca)>0 }">
						<tr>
							<th>Gruppi/Oggetti procedimento</th>
							<td>
							<span > <c:if test="${RicercaProcedimentiVO.tipoFiltroOggetto == 'OR' }"> <b>Procedimenti che contengono almeno un gruppo/oggetto tra quelli selezionati:</b><br> </c:if>
								<c:if test="${RicercaProcedimentiVO.tipoFiltroOggetto == 'AND' }"> <b>Procedimenti che contengono tutti i gruppi/oggetti selezionati:</b><br> </c:if>
							</span>
							
								<c:forEach items="${selectGuppiRicerca}" var="i"><c:out value="${i.descrizione}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(flagFiltrati)>0 }">
						<tr>
							<th>Estrazione Campione</th>
							<td>
								<c:forEach items="${flagFiltrati}" var="i"><c:out value="${i.descrizione}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.istanzaDataDa != null || RicercaProcedimentiVO.istanzaDataA!=null}">
						<tr>
							<th>Ultima istanza trasmessa</th>
							<td>
								<b>Da</b> <fmt:formatDate value="${RicercaProcedimentiVO.istanzaDataDa}" pattern="dd/MM7YYYY"/> <b>a</b> <fmt:formatDate pattern="dd/MM7YYYY" value="${RicercaProcedimentiVO.istanzaDataA}"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${fn:length(notificheProcedimento)>0 }">
						<tr>
							<th>Notifiche del procedimento</th>
							<td>
								<c:forEach items="${notificheProcedimento}" var="i"><c:out value="${i.gravita}"></c:out> <br></c:forEach>
							</td>
						</tr>
					</c:if>
					
					
					<c:if test="${RicercaProcedimentiVO.cuaaProcedimenti != null && RicercaProcedimentiVO.cuaaProcedimenti != ''}">
						<tr>
							<th>CUAA (Codice fiscale)</th>
							<td><c:out value="${RicercaProcedimentiVO.cuaaProcedimenti}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.piva != null && RicercaProcedimentiVO.piva != ''}">
						<tr>
							<th>Partita IVA</th>
							<td><c:out value="${RicercaProcedimentiVO.piva}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.denominazione != null && RicercaProcedimentiVO.denominazione != ''}">
						<tr>
							<th>Denominazione</th>
							<td><c:out value="${RicercaProcedimentiVO.denominazione}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.comuneSedeLegale != null && RicercaProcedimentiVO.comuneSedeLegale != ''}">
						<tr>
							<th>Comune sede legale</th>
							<td><c:out value="${RicercaProcedimentiVO.comuneSedeLegale}"></c:out></td>
						</tr>
					</c:if>
					<c:if test="${RicercaProcedimentiVO.provSedeLegale != null && RicercaProcedimentiVO.provSedeLegale != ''}">
						<tr>
							<th>Provincia sede legale</th>
							<td><c:out value="${RicercaProcedimentiVO.provSedeLegale}"></c:out></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</m:panel>
		
		<!-- VISUALIZZO FILTRI IMPOSTATI fine-->
		<p:ablitazione-macrocdu codiceMacroCdu="GESTIONE_ISTRUTTORIE">
			<div class="pull-right dropdown" style="padding-left: 4px;">
				<button class="btn btn-default dropdown-toggle" id="btnOperazioniMassive" style="color: black;" type="button" data-toggle="dropdown">
					Operazioni massive <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="#" onclick="trasferisciPratiche();">Trasferimento/Assegnazione pratica</a></li>
				</ul>
			</div>
		</p:ablitazione-macrocdu>
		<div id="filter-bar"></div>
		<table id="elencoProcedimenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " data-toggle="table"
			data-url="getElencoProcedimenti.json" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top"
			data-show-columns="true" data-undefined-text='' data-show-toggle="true" data-page-list="[10, 25, 50, 100, 250, 500]"
			data-page-number="${sessionMapNumeroPagina.get('elencoProcedimenti') != null ? sessionMapNumeroPagina.get('elencoProcedimenti') : '1'}"
			data-escape-table="true"
			>
			
			<thead>
				<tr>
					<th data-field="azione" data-escape-field="false" data-switchable="false" data-width="64" style="vertical-align: middle"><a href="#"
						onClick="forwardToPage('elencoProcedimentiExcel.xls');" style="text-decoration: none;"><i class="ico24 ico_excel"
							title="Esporta dati in Excel"></i> </a><span style="padding-left: 0.4em;"></span><input type="checkbox" id="selAll" onclick="selectAll();">

						<c:if test="${showpagamenti}">
							<a href="#" onClick="forwardToPage('elencoPagamentiExcel.xls');" style="text-decoration: none;"><i class="ico24 ico_excel_pg"
								title="Esporta dati pagamento in Excel"></i></a>
						</c:if></th>
					<th data-field="identificativo" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','identificativo')}">Identificativo</th>
					<th data-field="descrAmmCompetenza" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrAmmCompetenza')}">Amm.<br />Comp.
					</th>
					<th data-field="descrAmmCompetenzaInfo" data-sortable="true" data-visible="${!colonneNascoste.hide('elencoProcedimenti','descrAmmCompetenzaInfo', true)}" data-switchable="true">Dettaglio<br />Amministrazione
					</th>
					<th data-field="descrUfficioZona" data-sortable="true" data-visible="${!colonneNascoste.hide('elencoProcedimenti','descrUfficioZona', true)}" data-switchable="true">Ufficio di <br />zona
					</th>
					<th data-field="annoCampagna" data-visible="${!colonneNascoste.visible('elencoProcedimenti','annoCampagna')}">Anno<br />campagna
					</th>
					<th data-field="elencoLivelli" data-visible="${!colonneNascoste.visible('elencoProcedimenti','elencoLivelli')}">Operazione</th>

					<th data-field="elencoCodiciLivelliMisure" data-visible="false" data-switchable="false"></th>
					<th data-field="elencoCodiciLivelliSottoMisure" data-visible="false" data-switchable="false"></th>
					<th data-field="elencoCodiciOperazione" data-visible="false" data-switchable="false"></th>

					<th data-field="denominazioneBando" data-sortable="true" data-visible="${!colonneNascoste.hide('elencoProcedimenti','denominazioneBando', true)}">Bando</th>
					<th data-field="descrizione" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrizione')}">Stato <br />procedimento
					</th>
					<th data-field="dataUltimoAggiornamento" data-sortable="true" data-sorter="dateSorterddmmyyyy"
						data-visible="${!colonneNascoste.visible('elencoProcedimenti','dataUltimoAggiornamento')}">Data ultimo<br />aggiornamento
					</th>
					<th data-field="cuaa" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','cuaa')}">CUAA</th>
					<th data-field="denominazioneIntestazione" data-sortable="true"
						data-visible="${!colonneNascoste.visible('elencoProcedimenti','denominazioneIntestazione')}">Denominazione</th>
					<th data-field="descrComune" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrComune')}">Comune</th>
					<th data-field="descrProvincia" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrProvincia')}">Provincia</th>
					<th data-field="indirizzoSedeLegale" data-visible="${!colonneNascoste.hide('elencoProcedimenti','indirizzoSedeLegale', true)}">Sede<br />Legale
					</th>
					<th data-field="denominzioneDelega" data-visible="${!colonneNascoste.hide('elencoProcedimenti','denominzioneDelega', true)}">Gestore<br />fascicolo
					</th>
					<th data-field="procedimImportoInvestimento" data-formatter="numberFormatter2AcceptNull"
						data-visible="${!colonneNascoste.hide('elencoProcedimenti','procedimImportoInvestimento', true)}">Importo<br />Investimento
					</th>
					<th data-field="procedimSpesaAmmessa" data-formatter="numberFormatter2AcceptNull"
						data-visible="${!colonneNascoste.hide('elencoProcedimenti','procedimSpesaAmmessa', true)}">Importo<br />Ammesso
					</th>
					<th data-field="procedimContributoConcesso" data-formatter="numberFormatter2AcceptNull"
						data-visible="${!colonneNascoste.hide('elencoProcedimenti','procedimContributoConcesso', true)}">Importo<br />Contributo
					</th>
					<th data-field="importoLiquidato" data-formatter="numberFormatter2AcceptNull"
						data-visible="${!colonneNascoste.hide('elencoProcedimenti','importoLiquidato', true)}">Importo<br />Pagato
					</th>
					<th data-field="responsabileProcedimento" data-visible="${!colonneNascoste.hide('elencoProcedimenti','responsabileProcedimento', true)}">Responsabile<br />Procedimento
					</th>
					<th data-field="tecnicoIstruttore" data-visible="${!colonneNascoste.hide('elencoProcedimenti','tecnicoIstruttore', true)}">Tecnico<br />Istruttore
					</th>

					<th data-field="descrUltimaIstanza" data-visible="${!colonneNascoste.hide('elencoProcedimenti','descrUltimaIstanza', true)}">Ultima istanza<br />trasmessa
					</th>
					<th data-field="dataUltimaIstanzaStr" data-sortable="true" data-sorter="dateSorterddmmyyyy" data-visible="${!colonneNascoste.hide('elencoProcedimenti','dataUltimaIstanzaStr', true)}">Data trasmissione</th>
					<th data-field="notificheHtml" data-esceape-field="false" data-sortable="false" data-visible="${!colonneNascoste.hide('elencoProcedimenti','notificheHtml', true)}" data-switchable="true">Notifiche</th>
					<th data-field="notificheForFilter" data-esceape-field="false" data-sortable="false" data-switchable="false" data-visible="false"></th>
				</tr>
			</thead>
		</table>

	</div>
	<input type="hidden" id="numberOfDateFilter" value="2">

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
		showPleaseWait('elencoProcedimenti');

		function selectAll() {
			$(".cBP").prop('checked', $("#selAll").is(':checked'));
		}

		function trasferisciPratiche() {

			if ($(".cBP:checked").length == 0) {
				
				showMessageBox("Attenzione", "Selezionare almeno un procedimento.", "modal-large");
				return false;
			}
	
		    //openPageInPopup('../cunembo251/attendere.do', 'dlgChiusura', 'Attendere prego...', 'modal-large', true);	
		
			$.ajax({
				type : "POST",
				url : '../cunembo251/checkInfoPerTrasferimentoMassivo.do',
				data : $(".cBP:checked").serialize(),
				async : true,
				success : function(dataR) {
					if (dataR != "SUCCESS") {
						//$("#dlgChiusura").modal('hide');
						showMessageBox("Attenzione", dataR, "modal-large");
						return false;
					} else {
						//$("#dlgChiusura").modal('hide');
						openPageInPopup('../cunembo251/trasferimentoMassivo.do', 'dlgTrasferimento',
								'Trasferimento procedimenti selezionati ad altro Organismo Delegato/Ufficio di zona/Tecnico Istruttore',
								'modal-large', false, $(".cBP:checked").serialize());
					}
				}
			});
			
		}

		$(document).ready(function() {

			if ($("#btnOperazioniMassive").length == 0) {
				$("#selAll").hide();
			}

			$('body').on("column-switch.bs.table", function(obj, field) {
				var value = $('input[data-field="' + field + '"]').prop("checked");
				$.ajax({
					type : "POST",
					url : '../session/salvaColonna.do',
					data : "key=elencoProcedimenti&field=" + field + "&value=" + value,
					success : function(data) {
					}
				});
			});

			/*Mantengo in sessione il numero di pagina dell'elenco*/
			$('body').on("page-change.bs.table", function(obj, size) {
				$.ajax({
					type : "POST",
					url : '../session/salvaNumeroPagina.do',
					data : "key=elencoProcedimenti&page=" + size,
					success : function(data) {
					}
				});
			});

			$('#elencoProcedimenti').bootstrapTable({
				onAll : function() {
					if ($("#btnOperazioniMassive").length == 0) {
						$(".cBP").hide();
					}

				}
			});

			$('#filter-bar').bootstrapTableFilter({
				filters : [ {
					field : 'descrAmmCompetenza',
					label : 'Amm. Comp.',
					type : 'search'
				}, {
					field : 'annoCampagna',
					label : 'Anno campagna',
					type : 'search'
				}, {
					field : 'denominazioneBando',
					label : 'Bando',
					type : 'search'
				}, {
					field : 'descrComune',
					label : 'Comune sede legale',
					type : 'search'
				}, {
					field : 'cuaa',
					label : 'Cuaa',
					type : 'search'
				}, {
					field : 'dataUltimoAggiornamento',
					label : 'Data ultimo aggiornamento',
					type : 'range'
				}, {
					field : 'denominazioneIntestazione',
					label : 'Denominazione Azienda',
					type : 'search'
				}, {
					field : 'elencoCodiciLivelliMisure',
					label : 'Misura',
					type : 'ajaxSelectList',
					source : 'getElencoCodiciLivelliMisureJson.do'
				}, {
					field : 'elencoCodiciLivelliSottoMisure',
					label : 'Sottomisura',
					type : 'ajaxSelectList',
					source : 'getElencoCodiciLivelliSottoMisureJson.do'
				}, {
					field : 'elencoCodiciOperazione',
					label : 'Operazione',
					type : 'ajaxSelectList',
					source : 'getElencoCodiciOperazioneJson.do'
				}, {
					field : 'descrizione',
					label : 'Stato procedimento',
					type : 'ajaxSelect',
					source : 'getElencoStatiProcedimenti.do'
				}, {
					field : 'descrProvincia',
					label : 'Provincia',
					type : 'search'
				}, {
					field : 'responsabileProcedimento',
					label : 'Responsabile del procedimento',
					type : 'search'
				}, {
					field : 'tecnicoIstruttore',
					label : 'Tecnico Istruttore',
					type : 'search'
				}, {
					field : 'notificheForFilter',
					label : 'Notifiche',
					type : 'ajaxSelectList',
                    source: 'getNotificheFilter.do'
				}, {
					field : 'descrUltimaIstanza',
					label : 'Ultima istanza trasmessa',
					type : 'search'
				}, {
					field : 'dataUltimaIstanzaStr',
					label : 'Data trasmissione',
					type : 'date'
				}],
				connectTo : '#elencoProcedimenti',
				onSubmit : function() {
					var data = $('#filter-bar').bootstrapTableFilter('getData');

					var elabFilter = JSON.stringify(data);
					$.ajax({
						type : "POST",
						url : '../session/salvaFiltri.do',
						data : "key=elencoProcedimenti&filtro=" + elabFilter,
						success : function(data) {
						}
					});
				}
			});

			var filterJSON = $('#filtroAziende').val();
			if (filterJSON)
				$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON", filterJSON);
		});
	</script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />