<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
.mySpan {
  -ms-word-break: break-all;
  word-break: break-all;
	word-wrap: break-word;

  /* Non standard for webkit */
  word-break: break-word;

  -webkit-hyphens: auto;
  -moz-hyphens: auto;
  hyphens: auto;

}
.myLi{
    max-width: 30em;
    min-width: 8em;    
}
</style>
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
							<p>Scelta Interventi</p>
						</div>
						<div class="stepwizard-step">
							<a href="#step-2" type="button" class="btn btn-primary btn-circle">2</a>
							<p>Inserimento Importi</p>
						</div>
					</div>
				</div>

				<div class="stdMessagePanel" id="msgErrorecnt" style="display: none">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
						<div id="msgErrore"></div>
						</p>
					</div>
				</div>
				<form id="inserisciForm" method="post" action="confermaimporti.do" class="form-horizontal" style="margin-top: 2em">

					<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="${idDocumentoSpesa}">


					<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
						<thead>
						    <tr><th></th><th colspan="4" style="text-align: center">Documento di spesa</th><th colspan="5" style="text-align: center">Ricevuta di pagamento</th></tr>
							<tr>
								<th>Progr.</th>
								<th>Importo lordo</th>
								<th>Importo netto</th>
								<th>Importo da associare<br />per la rendicontazione
								</th>
								<th>File</th>
								<th>Estremi pagamento</th>
								<th>Data pagamento</th>
								<th>Modalita' pagamento</th>
								<th>Importo pagamento</th>
								<th>Importo da associare<br />per la rendicontazione</th>
								<!-- th>Documento</th-->
							</tr>
						</thead>
						<tbody>
							<c:set var="complessivoDocSpesaInt" value="0"></c:set>
							<c:set var="complessivoDocSpesaRendInt" value="0"></c:set>
							<c:set var="complessivoImpRicInt" value="0"></c:set>
							<c:set var="complessivoImpRicDaAssInt" value="0"></c:set>
							<c:forEach items="${listaInterventi}" var="dettaglioIntervento" >
								<c:set var="totDocSpesaInt" value="0"></c:set>
								<c:set var="totDocSpesaIntLordo" value="0"></c:set>
								<c:set var="totDocSpesaRendInt" value="0"></c:set>
								<c:set var="totImpRicInt" value="0"></c:set>
								<c:set var="totImpRicDaAssInt" value="0"></c:set>

								<!--  CALCOLO ROWSPAN = n doc + n ricevute -->
								<c:set var="rowspanProgressivo" value="0"></c:set>
								<c:forEach items="${dettaglioIntervento.intervento.elencoDocumenti}" var="documento">
									<!-- <c:set var="rowspanProgressivo" value="${rowspanProgressivo+1 }"></c:set>-->
									<c:forEach items="${documento.elencoRicevutePagamento}" var="ricevuta" varStatus="i">
										<c:set var="rowspanProgressivo" value="${rowspanProgressivo+1 }"></c:set>
									</c:forEach>
								</c:forEach>

								<c:choose>
									<c:when test="${fn:length(dettaglioIntervento.intervento.elencoDocumenti)>0 }">
										<tr>
											<c:set var="nDocInt" value="${fn:length(dettaglioIntervento.intervento.elencoDocumenti)}"></c:set>
											<!--  ROWSPAN = calcolato prima + righe per i totali (1 per doc))-->
											<td rowspan="${rowspanProgressivo+1+nDocInt}" style="vertical-align: middle; text-align: center; background-color: lightblue;"><div
													style="text-align: center">
													<span class="badge" style="background-color: white; color: black; border: 1px solid black; font-weight: bold;">${dettaglioIntervento.intervento.progressivo}</span>
												</div> <input type="hidden" name="id" value="${dettaglioIntervento.intervento.idIntervento}" /></td>
											<c:if test="true">
												<td colspan="9" style="vertical-align: middle; background-color: lightblue; font-weight: bold;">${dettaglioIntervento.intervento.descIntervento} <c:if test="${dettaglioIntervento.intervento.ulterioriInformazioni != null}">- ${dettaglioIntervento.intervento.ulterioriInformazioni}</c:if></td>
											</c:if>
										</tr>

										<c:forEach items="${dettaglioIntervento.intervento.elencoDocumenti}" var="documento">

											<!-- SOMMO IMPORTI DOCUMENTI -->
											<c:set var="totDocSpesaIntLordo" value="${totDocSpesaIntLordo+documento.importoLordo}"></c:set>
											<c:set var="totDocSpesaInt" value="${totDocSpesaInt+documento.importoSpesa}"></c:set>
											<c:if test='${documento.importoRendicontato!=null && documento.importoRendicontato!=""}'>
												<c:set var="totDocSpesaRendInt" value="${totDocSpesaRendInt+documento.importoRendicontato}"></c:set>
											</c:if>
											<c:set var="rowspan" value="${fn:length(documento.elencoRicevutePagamento)}"></c:set>
											<tr>
												<td colspan="9" style="vertical-align: middle; background-color: lightgray; font-weight: bold;">${documento.ragioneSociale}-
													${documento.dataDocumentoSpesaStr} - ${documento.numeroDocumentoSpesa} - ${documento.descrTipoDocumento}</td>
											</tr>
											<tr>
												<c:if test="${fn:length(documento.elencoRicevutePagamento)>0 }">

													<td rowspan="${rowspan}" style="vertical-align: middle; text-align: center">${documento.importoLordoStr}</td>
													<td rowspan="${rowspan}" style="vertical-align: middle; text-align: center">${documento.importoSpesaStr}</td>
													
													<td rowspan="${rowspan}" style="vertical-align: middle; text-align: center"><m:textfield type="euro"
															id="importo_${dettaglioIntervento.intervento.idIntervento}_${documento.idDocumentoSpesa}"
															name="importo_${dettaglioIntervento.intervento.idIntervento}_${documento.idDocumentoSpesa}"
															preferRequestValues="${preferRequestValues}" value="${documento.importoRendicontato}"></m:textfield></td>

													<td rowspan="${rowspan}" style="vertical-align: middle; text-align: left">
														<ul>
															<c:forEach items="${documento.allegati}" var="allegato">
																<li class="myLi"><span class="mySpan"><a href="../cunembo263m/download_${allegato.idDocumentoSpesaFile}.do" title='${allegato.nomeFileFisicoDocumentoSpe}'>${allegato.nomeFileLogicoDocumentoSpe}</a></span></li>
															</c:forEach>
														</ul>
													</td>

													<c:set var="totImpRicevutePerDoc" value="0" />
													<c:set var="totImpRicevuteDaAssPerDoc" value="0" />

													<c:forEach items="${documento.elencoRicevutePagamento}" var="ricevuta" varStatus="i">
														<c:if test="${i.index>0}">
															<tr>
														</c:if>
														<td>${ricevuta.numero}</td>
														<td>${ricevuta.dataPagamentoStr}</td>
														<td>${ricevuta.descrModalitaPagamento}</td>
														<td>&euro;&nbsp;${ricevuta.importoPagamentoStr}</td>
	
														<!--  SOMMA IMP RICEVUTE PER DOC -->
														<c:set var="totImpRicevutePerDoc" value="${totImpRicevutePerDoc+ricevuta.importoPagamento}" />
														<c:if test='${ricevuta.importoDaAssociare!=null && ricevuta.importoDaAssociare!=""}'>
															<c:set var="totImpRicevuteDaAssPerDoc" value="${totImpRicevuteDaAssPerDoc+ricevuta.importoDaAssociare}" />
														</c:if>
														<td><m:textfield type="euro"
																id="importoRicevuta_${dettaglioIntervento.intervento.idIntervento}_${documento.idDocumentoSpesa}_${ricevuta.idDettRicevutaPagamento}"
																name="importoRicevuta_${dettaglioIntervento.intervento.idIntervento}_${documento.idDocumentoSpesa}_${ricevuta.idDettRicevutaPagamento}"
																preferRequestValues="${preferRequestValues}" value="${ricevuta.importoDaAssociare}"></m:textfield></td>
														
											</tr>
										</c:forEach>
										<tr>
											<!-- RIGA TOTALI PER RICEVUTE -->
											<td colspan="7" style="text-align: right;"><strong>SUBTOTALE:</strong></td>
											<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totImpRicevutePerDoc}" pattern="###,##0.00" /></strong></td>
											<td class="totaleCalcolato" style="text-align: right;" id="totImpRicevute_${dettaglioIntervento.intervento.idIntervento}_${documento.idDocumentoSpesa}"><strong>&euro;&nbsp;<fmt:formatNumber
														value="${totImpRicevuteDaAssPerDoc}" pattern="###,##0.00" /></strong></td>

											<!-- SOMMO TOTALI IMPORTI RICEVUTE -->
											<c:set var="totImpRicInt" value="${totImpRicInt+totImpRicevutePerDoc}"></c:set>
											<c:set var="totImpRicDaAssInt" value="${totImpRicDaAssInt+totImpRicevuteDaAssPerDoc}"></c:set>
										</tr>
										</c:if>
							</c:forEach>
							<tr>
								<!-- RIGA TOTALI PER INTERVENTI -->
								<td colspan="1" style="text-align: right;"><strong>TOTALE:</strong>
								<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totDocSpesaIntLordo}" pattern="###,##0.00" /></strong></td>
								<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totDocSpesaInt}" pattern="###,##0.00" /></strong></td>
								<td class="totaleCalcolato" style="text-align: right;" id="totaleImpDaRendicontare_${dettaglioIntervento.intervento.idIntervento}"><strong>&euro;&nbsp;<fmt:formatNumber
											value="${totDocSpesaRendInt}" pattern="###,##0.00" /></strong></td>
								<td colspan="4"></td>
								<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totImpRicInt}" pattern="###,##0.00" /></strong></td>
								<td class="totaleCalcolato" style="text-align: right;" id="totaleImpDaAssociare_${dettaglioIntervento.intervento.idIntervento}"><strong>&euro;&nbsp;<fmt:formatNumber
											value="${totImpRicDaAssInt}" pattern="###,##0.00" /></strong></td>

								<!-- CALCOLO TOTALI COMPLESSIVI FINALI -->
								<c:set var="complessivoDocSpesaInt" value="${complessivoDocSpesaInt + totDocSpesaInt}"></c:set>
								<c:set var="complessivoDocSpesaIntLordo" value="${complessivoDocSpesaIntLordo + totDocSpesaIntLordo}"></c:set>
								
								<c:set var="complessivoDocSpesaRendInt" value="${complessivoDocSpesaRendInt + totDocSpesaRendInt}"></c:set>
								<c:set var="complessivoImpRicInt" value="${complessivoImpRicInt+ totImpRicInt}"></c:set>
								<c:set var="complessivoImpRicDaAssInt" value="${complessivoImpRicDaAssInt+ totImpRicDaAssInt}"></c:set>

							</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td rowspan="2" style="vertical-align: middle; text-align: center"><div style="text-align: center">
											<span class="badge" style="background-color: white; color: black; border: 1px solid black">${dettaglioIntervento.intervento.progressivo}</span>
										</div> <input type="hidden" name="id" value="${dettaglioIntervento.intervento.idIntervento}" /></td>
									<td colspan="9" style="vertical-align: middle">${dettaglioIntervento.intervento.descIntervento}</td>
								</tr>
								<tr>
									<td colspan="9">Non sono stati caricati e/o associati all'intervento i relativi documenti di spesa. <td></tr>

			
														</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
								<td colspan="8"></td>
							</tr>
						<tr>
						<!-- RIGA TOTALI COMPLESSIVI -->
						<td style="text-align: right;"><strong>TOTALE:</strong></td>
						<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totImportoLordoDocContatiSoloUnaVolta}" pattern="###,##0.00" /></strong></td>
						<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totImportoNettoDocContatiSoloUnaVolta}" pattern="###,##0.00" /></strong></td>
						<td class="totaleCalcolato" style="text-align: right;" id="complessivoDocSpesaRendInt"><strong>&euro;&nbsp;<fmt:formatNumber value="${complessivoDocSpesaRendInt}"
											pattern="###,##0.00" /></strong></td>
								
						<td colspan="4"></td>
						<td class="totaleCalcolato" style="text-align: right;"><strong>&euro;&nbsp;<fmt:formatNumber value="${totImportoRicevuteContateSoloUnaVolta}" pattern="###,##0.00" /></strong></td>
						<td class="totaleCalcolato" style="text-align: right;" id="complessivoImpRicDaAssInt"><strong>&euro;&nbsp;<fmt:formatNumber value="${complessivoImpRicDaAssInt}"
											pattern="###,##0.00" /></strong></td>
						</tr>
						</tfoot>
					</table>


					<div class="puls-group" style="margin-top: 2em">
						<div class="pull-left">
							<button type="button" onclick="forwardToPage('../cunembo263m/modificainterventi.do');" class="btn btn-default">indietro</button>
						</div>
						<div class="pull-right">
							<button type="button" name="conferma" id="conferma" onclick="controllaImporti()" class="btn btn-primary">conferma</button>
						</div>
					</div>
					<br /> <br /> <br />
				</form>
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
	$( document ).ready(function() {

		$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Modifica interventi</li>');

	});


		$("input[id^='importo_']").keyup(function() {

			this.value = this.value.replace('.',',');
			var val = this.value;
			var id = this.id;
			var idSpl = id.split('_');
			var idIntervento = idSpl[1];
			var idDocumento = idSpl[2];

			//RICALCOLO IL TOTALE DELL'IMP DA RENDICONTARE PER INTERVENTO
			var totImpRend = 0;
			$("input[id^='importo_" + idIntervento + "_']").each(function(index) {
				if (this.value !== undefined && this.value != null && this.value != "")
					totImpRend += parseFloat(this.value.replace(/\./g, '').replace(',', '.'));
			});
			$("#totaleImpDaRendicontare_" + idIntervento).html("<strong>&euro;&nbsp;" + totImpRend.toLocaleString([ 'ban', 'id' ], {
				minimumFractionDigits : 2
			}) + "</strong>");

			//RICALCOLO IL TOTALE DEI TOTALI
			var complImpRend = 0;
			$("input[id^='importo_']").each(function(index) {
				if (this.value !== undefined && this.value != null && this.value != "")
					complImpRend += parseFloat(this.value.replace(/\./g, '').replace(',', '.'));
			});
			$("#complessivoDocSpesaRendInt").html("<strong>&euro;&nbsp;" + complImpRend.toLocaleString([ 'ban', 'id' ], {
				minimumFractionDigits : 2
			}) + "</strong>");

		});

		$("input[id^='importoRicevuta_']").keyup(function() {

			this.value = this.value.replace('.',',');
			
			var val = this.value;
			var id = this.id;
			var idSpl = id.split('_');
			var idIntervento = idSpl[1];
			var idDocumento = idSpl[2];
			var idRicevuta = idSpl[3];
			
			//RICALCOLO IL TOTALE DELL'IMP DA ASSOCIARE PER DOC SPESA
			var totImpRic = 0;
			$("input[id^='importoRicevuta_" + idIntervento + "_" + idDocumento + "_']").each(function(index) {
				if (this.value !== undefined && this.value != null && this.value != "")
					totImpRic += parseFloat(this.value.replace(/\./g, '').replace(',', '.'));
			});
			
			$("#totImpRicevute_" + idIntervento + "_" + idDocumento).html("<strong>&euro;&nbsp;" + totImpRic.toLocaleString([ 'ban', 'id' ], {
				minimumFractionDigits : 2
			}) + "</strong>");


			//RICALCOLO IL TOTALE DEI TOTALI PER INTERVENTO
			var totTotImpRic = 0;
			$("input[id^='importoRicevuta_"+idIntervento+"_']").each(function(index) {
				if (this.value !== undefined && this.value != null && this.value != "")
					totTotImpRic += parseFloat(this.value.replace(/\./g, '').replace(',', '.'));
			});
			$("#totaleImpDaAssociare_"+idIntervento).html("<strong>&euro;&nbsp;" + totTotImpRic.toLocaleString([ 'ban', 'id' ], {
				minimumFractionDigits : 2
			}) + "</strong>");

			//RICALCOLO IL TOTALE DEI TOTALI
			var complexImpRic = 0;
			$("input[id^='importoRicevuta_']").each(function(index) {
				if (this.value !== undefined && this.value != null && this.value != "")
					complexImpRic += parseFloat(this.value.replace(/\./g, '').replace(',', '.'));
			});
			$("#complessivoImpRicDaAssInt").html("<strong>&euro;&nbsp;	" + complexImpRic.toLocaleString([ 'ban', 'id' ], {
				minimumFractionDigits : 2
			}) + "</strong>");

		});

		function controllaImporti() {
			clearErrors();
			var ser = $("#inserisciForm").serialize();
			$.ajax({
				type : 'POST',
				url : 'controllaimporti.do',
				data : ser,
				success : function(data, textStatus) {
					if (data == 'OK') {
						$('#inserisciForm').submit();
					} else if (data == 'WARN') {
						openPageInPopup('confermaimportipopup.do', 'dlgInserisci', 'Conferma importi', 'modal-lg', false);
						return false;
					} else {
						clearErrors();
						$('#msgErrorecnt').show();
						$('#msgErrore').html(data);
					}
				},
				async : false
			});
		}

		function clearErrors() {
			var field = $('[data-toggle="error-tooltip"]');
			field.tooltip('disable');
			field.removeClass('has-error red-tooltip');
			field.removeAttr('data-toggle');
			field.removeAttr('data-original-title');
			field.removeAttr('title');
		}

		function setError(key, message) {
			var field = $('#' + key).parent();
			field.addClass('has-error red-tooltip');
			field.attr("data-toggle", "error-tooltip");
			field.attr("title", message);
			field.attr('data-original-title', message);
			field.tooltip('enable');
		}
	</script> <r:include
											resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />