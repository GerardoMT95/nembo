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

				

				<div class="stdMessagePanel" id="msgErrorecnt" style="display: none">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
						<div id="msgErrore"></div>
						</p>
					</div>
				</div>
				
				<c:if test="${listaTabelle== null}">
					<div class="stdMessagePanel"><div class="alert alert-info"><p>Nessun intervento presente</p></div></div>
				</c:if>
				
				<form id="inserisciForm" method="post" action="confermaimporti.do" class="form-horizontal" style="margin-top: 2em">
					<c:if test="${listaTabelle!= null}">
					<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="${idDocumentoSpesa}">

              <c:set var="totImportoDocumento" value="0" />
              <c:set var="totImportoDocumentoLordo" value="0" />            
              <c:set var="totImportoDaRendicontare" value="0" />
              <c:set var="totImportoRendicontato" value="0" />
              <c:set var="totImportoRicevuta" value="0" />
              <c:set var="totImportoAssociato" value="0" />
              <c:set var="totImportoAssociato" value="0" />

            <table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
              <thead>
              	<tr><th></th><th colspan="5" style="text-align: center">Documento di spesa</th><th colspan="5" style="text-align: center">Ricevuta di pagamento</th></tr>
                <tr>
                  <th>Progr.</th>
                  <th>Importo lordo
                  </th>
                  <th>Importo netto
                  </th>
                  <th>Importo associato <br/>per la rendicontazione
                  </th>
                  <th>Importo <br/>rendicontato
                  </th>
                  <th>File</th>
                  <th>Estremi pagamento</th>
                  <th>Data pagamento</th>
                  <th>Modalità pagamento</th>
                  <th>Importo pagamento</th>
                  <th>Importo associato <br/>per la rendicontazione</th>
                  <!-- th>Documento</th-->
                </tr>
              </thead>
              <tbody>
              <c:set var="totImportoDocumentoPerIntervento" value="0" />
              <c:set var="totImportoLordoDocumentoPerIntervento" value="0" />
              <c:set var="totImportoDaRendicontarePerIntervento" value="0" />
              <c:set var="totImportoRendicontatoPerIntervento" value="0" />
              <c:set var="totImportoRicevutaPerIntervento" value="0" />
              <c:set var="totImportoAssociatoPerIntervento" value="0" />
              <c:set var="totImportoAssociatoPerIntervento" value="0" />
              <c:set var="lastProgressivo" value="-1000000000" />
					<c:forEach items="${listaTabelle}" var="listaInterventi" varStatus="vs"> 
								<c:forEach items="${listaInterventi}" var="dettaglioIntervento" varStatus="vs2">
                <c:set var="progressivoCorrente" value="${dettaglioIntervento.intervento.progressivo}" />
                <c:if test="${progressivoCorrente!=lastProgressivo}" >
              <c:set var="lastProgressivo" value="${progressivoCorrente}" />
              <c:set var="totImportoDocumentoPerIntervento" value="0" />
             <c:set var="totImportoLordoDocumentoPerIntervento" value="0" />
              <c:set var="totImportoDaRendicontarePerIntervento" value="0" />
              <c:set var="totImportoRendicontatoPerIntervento" value="0" />
              <c:set var="totImportoRicevutaPerIntervento" value="0" />
              <c:set var="totImportoAssociatoPerIntervento" value="0" />
              <c:set var="totImportoAssociatoPerIntervento" value="0" />
                </c:if>
									<!--  CALCOLO ROWSPAN -->
									<c:set var="rowspanProgressivo" value="1"></c:set>
									<c:forEach items="${dettaglioIntervento.intervento.elencoDocumenti}" var="documento">
											<!-- <c:set var="rowspanProgressivo" value="${rowspanProgressivo+1 }"></c:set>-->
										<c:forEach items="${documento.elencoRicevutePagamento}" var="ricevuta" varStatus="i">
											<c:set var="rowspanProgressivo" value="${rowspanProgressivo+1 }"></c:set>
										</c:forEach>
									</c:forEach>
									<c:choose>
										<c:when test="${fn:length(dettaglioIntervento.intervento.elencoDocumenti)>0 }">
											<tr>
												<td class="cella_progressivo" data-progressivo="${progressivoCorrente}" rowspan="${rowspanProgressivo+1}"
													style="vertical-align: middle; text-align: center; background-color: lightblue;"><div style="text-align: center">
														<span class="badge" style="background-color: white; color: black; border: 1px solid black; font-weight: bold;">${dettaglioIntervento.intervento.progressivo}</span>
													</div> <input type="hidden" name="id" value="${dettaglioIntervento.intervento.idIntervento}" /></td>
												<c:if test="${vs2.index == 0 }"><td colspan="10" style="vertical-align: middle; background-color: lightblue; font-weight: bold;">${dettaglioIntervento.intervento.descIntervento} <c:if test="${dettaglioIntervento.intervento.ulterioriInformazioni != null}">- ${dettaglioIntervento.intervento.ulterioriInformazioni}</c:if></td></c:if>
											</tr>
											<c:set var="totImportoRicevutaPerDoc" value="0"/>
											<c:set var="totImportoDaAssociarePerDoc" value="0"/>
	
											<c:forEach items="${dettaglioIntervento.intervento.elencoDocumenti}" var="documento">
												<c:set var="rowspan" value="${fn:length(documento.elencoRicevutePagamento)}"></c:set> 
												<tr>
													<td colspan="10" style="vertical-align: middle; background-color: lightgray; font-weight: bold;">${documento.ragioneSociale}-
														${documento.dataDocumentoSpesaStr} - ${documento.numeroDocumentoSpesa} - ${documento.descrTipoDocumento}</td>
												</tr>
												<tr>
													<td rowspan="${rowspan}" class="numero" style="vertical-align: middle">
													<fmt:formatNumber value="${documento.importoLordo}" pattern="#,##0.00"/> &euro;
													<c:set var="totImportoDocumentoPerIntervento" value="${totImportoDocumentoPerIntervento+documento.importoSpesa}" />
													<c:set var="totImportoDocumento" value="${totImportoDocumento+documento.importoSpesa}" />
													</td>
													
													<td rowspan="${rowspan}" class="numero" style="vertical-align: middle">
													<fmt:formatNumber value="${documento.importoSpesa}" pattern="#,##0.00"/> &euro;
													<c:set var="totImportoLordoDocumentoPerIntervento" value="${totImportoLordoDocumentoPerIntervento+documento.importoLordo}" />
													<c:set var="totImportoLordoDocumento" value="${totImportoLordoDocumento+documento.importoLordo}" />
											  </td>
													
													<td rowspan="${rowspan}" class="numero" style="vertical-align: middle"><fmt:formatNumber value="${documento.importoRendicontato}" pattern="#,##0.00"/><c:if test="${documento.importoRendicontato!=null}"> &euro;</c:if>
              <c:set var="totImportoDaRendicontarePerIntervento" value="${totImportoDaRendicontarePerIntervento+documento.importoRendicontato}" />
              <c:set var="totImportoDaRendicontare" value="${totImportoDaRendicontare+documento.importoRendicontato}" />
													</td>
                        	<td rowspan="${rowspan}" class="numero" style="vertical-align: middle"><fmt:formatNumber value="${documento.importoDaRendicontare}" pattern="#,##0.00"/><c:if test="${documento.importoDaRendicontare!=null}"> &euro;</c:if>
              <c:set var="totImportoRendicontatoPerIntervento" value="${totImportoRendicontatoPerIntervento+documento.importoDaRendicontare}" />
              <c:set var="totImportoRendicontato" value="${totImportoRendicontato+documento.importoDaRendicontare}" />
                        	</td>
													<td rowspan="${rowspan}" style="vertical-align: middle; text-align: left">
													<!-- <a href="../cunembo263m/download_${documento.idDocumentoSpesaFile}.do" title = '${documento.nomeFileFisicoDocumentoSpe}'>${documento.nomeFileLogicoDocumentoSpe}</a>
													-->
													<ul>
													<c:forEach items="${documento.allegati}" var="allegato">
													<li class="myLi"><span class="mySpan"><a href="../cunembo263m/download_${allegato.idDocumentoSpesaFile}.do" <c:if test="${allegato.poApprovatoNegativo}">style='color:red;'</c:if>  title = '${allegato.nomeFileFisicoDocumentoSpe}'>${allegato.nomeFileLogicoDocumentoSpe}</a></span></li>
													</c:forEach>
													</ul>
													</td>
													<c:forEach items="${documento.elencoRicevutePagamento}" var="ricevuta" varStatus="i">
														<c:if test="${i.index>0}"><tr></c:if>
															<td>${ricevuta.numero}</td>
															<td>${ricevuta.dataPagamentoStr}</td>
															<td>${ricevuta.descrModalitaPagamento}</td>
															<td class="numero">${ricevuta.importoPagamentoStr} &euro;</td>
															<td class="numero"><fmt:formatNumber value="${ricevuta.importoDaAssociare}" pattern="#,##0.00"/> &euro;</td>
															<c:set var="totImportoRicevutaPerDoc" value="${totImportoRicevutaPerDoc+ricevuta.importoPagamento}"/>
                              								<c:set var="totImportoDaAssociarePerDoc" value="${totImportoDaAssociarePerDoc+ricevuta.importoDaAssociare}"/>
														</tr>
													</c:forEach>
	
											</c:forEach>
											<tr style="font-weight:bold">
  											<th colspan="8" class="right">SUBTOTALE:</th>
                        <td class="numero">
                          <fmt:formatNumber value="${totImportoRicevutaPerDoc}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                          <fmt:formatNumber value="${totImportoDaAssociarePerDoc}" pattern="#,##0.00"/> &euro;
                        </td>
              <c:set var="totImportoRicevutaPerIntervento" value="${totImportoRicevutaPerIntervento+totImportoRicevutaPerDoc}" />
              <c:set var="totImportoAssociatoPerIntervento" value="${totImportoAssociatoPerIntervento+totImportoDaAssociarePerDoc}" />
              <c:set var="totImportoRicevuta" value="${totImportoRicevuta+totImportoRicevutaPerDoc}" />
              <c:set var="totImportoAssociato" value="${totImportoAssociato+totImportoDaAssociarePerDoc}" />
                      </tr>
                      
										</c:when>
										<c:otherwise>
											<tr>
												<td rowspan="2" style="vertical-align: middle; text-align: center"><div style="text-align: center">
														<span class="badge" style="background-color: white; color: black; border: 1px solid black">${dettaglioIntervento.intervento.progressivo}</span>
													</div> <input type="hidden" name="id" value="${dettaglioIntervento.intervento.idIntervento}" /></td>
												<td colspan="11" style="vertical-align: middle">${dettaglioIntervento.intervento.descIntervento} <c:if test="${dettaglioIntervento.intervento.ulterioriInformazioni != null}">- ${dettaglioIntervento.intervento.ulterioriInformazioni}</c:if></td>
											</tr>
											<tr>
												<td colspan="11">Non sono stati caricati e/o associati all'intervento i relativi documenti di spesa.
												<td>
											</tr>
	
										</c:otherwise>
									</c:choose>
									
                      <tr class="totale_intervento " data-progressivo="${progressivoCorrente}"  style="font-weight:bold;display:none">
                        <th style="vertical-align: middle;" class="right">TOTALE:</th>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoLordoDocumentoPerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoDocumentoPerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoDaRendicontarePerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoRendicontatoPerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td colspan="4"></td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoRicevutaPerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoAssociatoPerIntervento}" pattern="#,##0.00"/> &euro;
                        </td>
                      </tr>
								</c:forEach>
					</c:forEach>
					<tr style="vertical-align: middle;"><td colspan="11"></td></tr>
					<tr style="font-weight:bold">
					<th class="numero" style="vertical-align: middle;">TOTALE:</th>
                        <td class="numero"><fmt:formatNumber value="${totImportoLordoDocContatiSoloUnaVolta}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero"><fmt:formatNumber value="${totImportoNettoDocContatiSoloUnaVolta}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoDaRendicontare}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoRendicontato}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td colspan="4"></td>
                        <td class="numero"><fmt:formatNumber value="${totImportoRicevuteContateSoloUnaVolta}" pattern="#,##0.00"/> &euro;
                        </td>
                        <td class="numero">
                        <fmt:formatNumber value="${totImportoAssociato}" pattern="#,##0.00"/> &euro;
                        </td>
					</tr>
              </tbody>
            </table>
					</c:if>
					<div class="puls-group" style="margin-top: 2em">
						<div class="pull-left">
							<button type="button" onclick="forwardToPage('../cunembo263l/index.do');" class="btn btn-default">indietro</button>
						</div>
					</div>
					<br /> <br /> <br />
				</form>
				
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">

	
	  $(document).ready(function()
	  	{
			$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Dettaglio interventi</li>');
		  	
	     $('.totale_intervento').each
	     (
	         function(index, obj)
           {
	           obj=$(obj);
	           var progressivo = $(obj).data('progressivo');
	           $('.totale_intervento[data-progressivo='+progressivo+']').last().show();

	           var list=$('.cella_progressivo[data-progressivo='+progressivo+']');
	           var rowSpan=0;
	           list.each(function(idx, cell)
	  	         {
	  	          cell=$(cell);
	  	          var currentRowSpan = $(cell).attr("rowspan");
	  	          rowSpan+=parseInt(currentRowSpan);
  	           });
	           var first=list.first();
	           first.attr("rowspan",rowSpan);
	           first.removeClass("cella_progressivo");
	           $('.cella_progressivo[data-progressivo='+progressivo+']').remove();
           }
       )
  	  });
		function controllaImporti() {
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
			field.removeAttr('title');
		}

		function setError(key, message) {
			var field = $('#' + key).parent();
			field.addClass('has-error red-tooltip');
			field.attr("data-toggle", "error-tooltip");
			field.attr("title", message);
			field.tooltip('enable');
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />