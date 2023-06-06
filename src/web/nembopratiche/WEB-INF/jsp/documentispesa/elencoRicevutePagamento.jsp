<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-263-M" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-M" />


	<div class="container-fluid" id="content">
		<m:panel id="panelDocumenti">
			<div class="col-sm-12">

					<div class="stepwizard">
						<div class="stepwizard-row setup-panel">
							<div class="stepwizard-step">
								<a href="#step-1" type="button" class="btn btn-default btn-circle">1</a>
								<p>Dati Documento</p>
							</div>
							<div class="stepwizard-step">
								<a href="#step-2" type="button" class="btn btn-default btn-circle">2</a>
								<p>Scelta fornitore</p>
							</div>
							<div class="stepwizard-step">
								<a href="#step-4" type="button" class="btn btn-primary btn-circle">3</a>
								<p>Ricevute Pagamento</p>
							</div>
						</div>
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
				<form name="elencoForm" id="elencoForm" method="post" action="" style="padding-top:2em;"> 
					<m:panel collapsible="false" title="Documento" id="panelRiepilogoDoc" >
						<p>${docSpesa.ragioneSociale } - ${docSpesa.dataDocumentoSpesaStr } - ${docSpesa.numeroDocumentoSpesa } -${docSpesa.descrTipoDocumento }</p>
						<div>
					<table  id="riepilogoImporti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh">
					<thead><tr><th>Importo Netto</th><th>Importo Iva</th><th>Importo Lordo</th><th>Importo Pagamento Lordo</th></tr></thead>
					<tbody><tr><td>${docSpesa.importoSpesaStr}</td><td>${docSpesa.importoIvaStr}</td><td>${docSpesa.importoLordoStr}</td><td>${docSpesa.importoLordoPagamentoStr}</td></tr></tbody>
					</table> 
					<input type="hidden" id="importoLordo" value="${docSpesa.importoLordo}">
					<input type="hidden" id="importoLordoPagamento" value="${docSpesa.importoLordoPagamento}">
					
					</div>
					</m:panel>
				
					<div id="filter-bar" style="position: relative; top: 46px"></div>
					<table id="elencoDocumenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali" data-toggle="table"
						data-url="json/getElencoRicevutePagamento.json" data-show-columns="true" data-show-filter="true" data-undefined-text=''
						data-select-item-name="idRicevutaPagamento" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top"
						data-id-field="idRicevutaPagamento">
						<thead>
							<tr>
								<th class="center" data-field="idDocumentoSpesa" data-width="130" data-switchable="false" data-formatter="iconeFormatter"><p:abilitazione-cdu
										codiceCdu="CU-NEMBO-263-I">
										<a onclick="checkAddRicevuta();" href="#" style="text-decoration: none;"><i class="ico24 ico_add"
											title="Aggiungi ricevuta pagamento"></i></a>
									</p:abilitazione-cdu></th>
								<th data-field="numero" data-sortable="true" data-totale="false">Estremi pagamento</th>
								<th data-field="dataPagamentoStr" data-sortable="true" data-totale="false">Data pagamento</th>
								<th data-field="descrModalitaPagamento" data-sortable="true" data-totale="false">Modalit&agrave; pagamento</th>
								<th data-field="importoPagamento" data-sortable="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-totale="true">Importo
									pagamento lordo</th>
								<!-- th data-field="nomeLogicoFile" data-sortable="true" data-totale="false" data-formatter="allegatiFormatter">Documento</th> -->
								<th data-field="note" data-sortable="true" data-totale="false" data-visible="false">Note</th>
							</tr>
						</thead>
					</table>
				</form>



				<div class="col-sm-12" style="margin-bottom: 2em; margin-top: 2em">
					<div class="puls-group">
						<div class="pull-left">
							<c:if test="${docSpesa.idFornitore !=null }">
									<a type="button" href="../cunembo263m/inseriscifornitore.do" class="btn btn-default">indietro</a>
							</c:if>
							<c:if test="${docSpesa.idFornitore ==null }">
									<a type="button" href="../cunembo263m/getDatiInserimento.do" class="btn btn-default">indietro</a>
							</c:if>
						</div>
					</div>
					<div class="puls-group">
						<div class="pull-right">
							<a type="button" href="../cunembo263l/index.do" class="btn btn-primary">torna a elenco documenti spesa</a>
						</div>
					</div>
				</div>
			</div>
		</m:panel>
	</div>

	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">

	$( document ).ready(function() {

		$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Elenco ricevute</li>');

	});
	
		function iconeFormatter($value, row, index) {
			var html = '';

			//vedi dettaglio interventi spesa
			//html += '&nbsp;<span id="espandi_'+row.idDocumentoSpesa+'"><a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-plus-sign"  href=\"#'+row.idDocumentoSpesa+'\" onclick=\"visualizzainterventi('+row.idDocumentoSpesa+');\"></a></span>';

			/*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-E">*/
			html += '<a href="#" onclick="elimina(\'' + row.idDettRicevutaPagamento + '\')"><i class="ico24 ico_trash"></i></a>';
			/*</p:abilitazione-cdu>*/

			/*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
			html += '<a href=\"../cunembo263m/modificaRicevuta_'+row.idDettRicevutaPagamento+'.do\"><i class="ico24 ico_modify"></i></a>';
			/*</p:abilitazione-cdu>*/

			return html;
		}

		function elimina(idDettRicevutaPagamento) {


			 $.ajax(
			          {
			            type : "GET",
			            url : '../cunembo263e/canDeleteRicevuta_'+idDettRicevutaPagamento+'.do',
			            dataType : "html",
			            async : false,
			            success : function(html)
			            {
			              var COMMENT = '<success>';
			              if (html != null && html.indexOf(COMMENT) >= 0)
			              {
			      			return openPageInPopup('../cunembo263e/confermaEliminaRicevuta_' + idDettRicevutaPagamento + '.do', 'dlgEliminaDocumento',
			      					'Elimina', '', false);
			              }
			              else
			              {
			                doErrorTooltip();
			                writeModalBodyError(COMMENT);
			              }
			            },
			            error : function(jqXHR, html, errorThrown)
			            {
			              writeModalBodyError("Errore.");
			            }
			          });

		}

		function allegatiFormatter($value, row, index) {
			var ret = row.nomeLogicoFile
					+ ' <a href=\"../cunembo263m/downloadRicevuta_'+row.idDettRicevutaPagamento+'.do\" class=\"ico24 '+row.iconaFile+'\" title = '+row.nomeFisicoFile+'></a>';
			return ret;

		}

		function checkAddRicevuta() {

			var importoLordo = $("#importoLordo").val();
			var importoLordoPagamento = $("#importoLordoPagamento").val();
			if(importoLordo==importoLordoPagamento)
			{
				showMessageBox("Attenzione", "Le ricevute di pagamento presenti in elenco coprono già interamente l'importo lordo del documento di spesa. Non è più consentito inserire nuove ricevute di pagamento." , "modal-large")
				return false;
			}
			else 
				forwardToPage("../cunembo263i/inserisciricevuta.do");
		}
	</script>

	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />