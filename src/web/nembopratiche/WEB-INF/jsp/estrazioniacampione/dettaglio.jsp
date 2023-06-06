<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
	<p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="../cunembo217/index.do">Gestione estrazioni</a> <span class="divider">/</span></li>
				  <li class="active">Dettaglio estrazione</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>
<div class="container-fluid" id="content" style="margin-bottom:3em">
<div style="padding-bottom:1em">
	<m:button id="scaricaExcel" btnType="button"  cssClass="btn btn-primary">Scarica in Excel</m:button>
	<c:if test="${btnRegistraVisibile}">
	<m:button id="btnEstrai" btnType="button"  cssClass="btn btn-primary">Estrazione</m:button>
		<m:button id="btnRegistra" btnType="button"  cssClass="btn btn-primary">Registra</m:button>
		<m:button id="btnElimina" btnType="button"  cssClass="btn btn-primary">Elimina</m:button>
	</c:if>
	<c:if test="${btnAnnullaVisibile}">
		<m:button id="btnAnnulla" btnType="button"  cssClass="btn btn-primary">Annullamento</m:button>
	</c:if>
</div>
<m:panel id="pTotali" >
 	<table id="tableTotali" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
 	<tbody>
 		<c:if test="${totali.elencoDettagliImporti != null}">
 			<c:forEach items="${totali.elencoDettagliImporti}" var="a">
 				<tr class="hRows collapse">
 					<td><c:out value="${a.codiceLivello}"></c:out></td>
 					<td><c:out value="${a.decodificaImporto}"></c:out></td>
 					<td align="right"><c:out value="${a.importoStr}"></c:out></td>
 					<td colspan="2"></td>
 				</tr>
 			</c:forEach>
 		</c:if>
 		
 		<tr>
			<th colspan="2">
				<a href="#" data-toggle="collapse" data-target=".hRows" class="toggle_handle espandi no-decoration" title="visualizza dettaglio">
				  <span style="text-decoration: none; font-size:18px" id="icona_espandi" class="icon icon-expand icona_espandi"></span>
				  <span class="hidden">visualizza dettaglio importi</span>
				 </a> &nbsp;
			Importo totale richiesto complessivo</th>
			<td align="right"><c:out value="${totali.impTotaleComplessivoStr}"></c:out></td>
			<td colspan="2"></td>
		</tr>
		<c:if test="${totali.elencoDettagliImportiMisure != null}">
 			<c:forEach items="${totali.elencoDettagliImportiMisure}" var="a">
 				<tr class="hRows2 collapse">
 					<td><c:out value="${a.codiceLivello}"></c:out></td>
 					<td><c:out value="${a.decodificaImporto}"></c:out></td>
 					<td align="right"><c:out value="${a.importoStr}"></c:out></td>
 					<td colspan="2"></td>
 				</tr>
 			</c:forEach>
 		</c:if>
		<tr>
			<th colspan="2">
				<a href="#" data-toggle="collapse" data-target=".hRows2" class="toggle_handle espandi no-decoration" title="visualizza dettaglio">
				  <span style="text-decoration: none; font-size:18px" id="icona_espandi2" class="icon icon-expand icona_espandi2"></span>
				  <span class="hidden">visualizza dettaglio importi per misura</span>
				 </a> &nbsp;
			Importo totale richiesto complessivo per misura</th>
			<td align="right"><c:out value="${totali.impTotaleComplessivoStr}"></c:out></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<th colspan="2">Importo totale richiesto estraz. attuale</th>
			<td align="right"><c:out value="${totali.impTotaleAttualeStr}"></c:out></td>
			<th>Data estraz. attuale</th>
			<td align="right"><c:out value="${totali.dataEstrazioneAttualeStr}"></c:out></td>
		</tr>
 	</tbody>
 	</table>
</m:panel>


<c:if test="${importiPA != null}">
	<m:panel id="pImportiAttuali">
	 	<table id="tableTotali" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
		 	<tbody>
				<c:forEach items="${importiPA.elencoImporti}" var="a">
					<tr>
						<td>
							<a href="#impo_${a.codiceLivello}" id="impo_${a.codiceLivello}" data-toggle="collapse" data-target=".hRowsPerc" class="toggle_handle espandi no-decoration" title="visualizza dettaglio">
							  <span style="text-decoration: none; font-size:18px"  class=" icona_espandi_perc icon icon-expand"></span>
							  <span class="hidden">visualizza dettaglio percentuali</span>
							 </a> &nbsp;
							 <c:out value="${a.codiceLivello}"></c:out>
						 </td>
						<c:choose>
							<c:when test="${a.tipoImporto == 'A'}">
								<td>Imp. estrazione attuale</td>
								<td align="right"><c:out value="${a.importoRichiestoStr}"></c:out> </td>
								<td>Imp. estrazione attuale - casuale</td>
								<td align="right"><c:out value="${a.importoRichiestoParteCasualStr}"></c:out> </td>
								<td>Imp. estrazione attuale -  analisi del rischio</td>
								<td align="right"><c:out value="${a.analisiRischioStr}"></c:out> </td>
							</c:when>		
							<c:when test="${a.tipoImporto == 'P'}"> 
								<td>Imp. estrazioni precedenti</td>
								<td align="right"><c:out value="${a.importoRichiestoStr}"></c:out> </td>
								<td>Imp. estrazioni precedenti - casuale</td>
								<td align="right"><c:out value="${a.importoRichiestoParteCasualStr}"></c:out> </td>
								<td>Imp. estrazioni precedenti - analisi del rischio</td>
								<td align="right"><c:out value="${a.analisiRischioStr}"></c:out> </td>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</tr>
					<tr class="collapse">
						<td></td>
						<td></td>
						<td align="right"><c:out value="${a.percImportoRichiestoStr}"></c:out> </td>
						<td></td>
						<td align="right"><c:out value="${a.percTotaleImportiEstrattiCasualeStr}"></c:out> </td>
						<td></td>
						<td align="right"><c:out value="${a.percTotaleAnalisiDelRischioStr}"></c:out> </td>
					</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				<tr>
					<th>TOTALI</th>
					<th>Importo totale estratto</th>
					<td align="right"><c:out value="${importiPA.totaleImportiEstrattiStr}"></c:out> </td>
					<th>Importo totale estratto - casuale</th>
					<td align="right"><c:out value="${importiPA.totaleImportiEstrattiCasualeStr}"></c:out> </td>
					<th>Importo totale estratto - analisi del rischio</th>
					<td align="right"><c:out value="${importiPA.totaleAnalisiDelRischioStr}"></c:out> </td>
				</tr>
				<tr>
					<th>% Estrazione</th>
					<th>% Totale</th>
					<td align="right"><c:out value="${importiPA.percTotaleImportiEstrattiStr}"></c:out> </td>
					<th>% Casuale</th>
					<td align="right"><c:out value="${importiPA.percTotaleImportiEstrattiCasualeStr}"></c:out> </td>
					<th>% Analisi del rischio</th>
					<td align="right"><c:out value="${importiPA.percTotaleAnalisiDelRischioStr}"></c:out> </td>
				</tr>
			</tfoot>
		</table>
		
		
		<m:panel id="tableTotaliMisurePanel" collapsible="true" startOpened="false" title="Importi estrazioni per misura">
		<table id="tableTotaliMisure" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
		 	<tbody>
				<c:forEach items="${importiPA.elencoImportiMisure}" var="a">
					<tr>
						<td>
							<a href="#mis_${a.codiceLivello}" id="mis_${a.codiceLivello}" data-toggle="collapse" data-target=".hRowsPerc" class="toggle_handle espandi no-decoration" title="visualizza dettaglio">
							  <span style="text-decoration: none; font-size:18px"  class=" icona_espandi_perc icon icon-expand"></span>
							  <span class="hidden">visualizza dettaglio percentuali</span>
							 </a> &nbsp;
							 <c:out value="${a.codiceLivello}"></c:out>
						 </td>
								<td>Imp. estrazioni</td>
								<td align="right"><c:out value="${a.importoRichiestoStr}"></c:out> </td>
								<td>Imp. estrazioni - casuale</td>
								<td align="right"><c:out value="${a.importoRichiestoParteCasualStr}"></c:out> </td>
								<td>Imp. estrazioni - analisi del rischio</td>
								<td align="right"><c:out value="${a.analisiRischioStr}"></c:out> </td>
					</tr>
					<tr class="collapse">
						<td></td>
						<td></td>
						<td align="right"><c:out value="${a.percImportoRichiestoStr}"></c:out> </td>
						<td></td>
						<td align="right"><c:out value="${a.percTotaleImportiEstrattiCasualeStr}"></c:out> </td>
						<td></td>
						<td align="right"><c:out value="${a.percTotaleAnalisiDelRischioStr}"></c:out> </td>
					</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				<tr>
					<th>TOTALI</th>
					<th>Importo totale estratto</th>
					<td align="right"><c:out value="${importiPA.totaleImportiEstrattiStr}"></c:out> </td>
					<th>Importo totale estratto - casuale</th>
					<td align="right"><c:out value="${importiPA.totaleImportiEstrattiCasualeStr}"></c:out> </td>
					<th>Importo totale estratto - analisi del rischio</th>
					<td align="right"><c:out value="${importiPA.totaleAnalisiDelRischioStr}"></c:out> </td>
				</tr>
				<tr>
					<th>% Estrazioni</th>
					<th>% Totale</th>
					<td align="right"><c:out value="${importiPA.percTotaleImportiEstrattiStr}"></c:out> </td>
					<th>% Casuale</th>
					<td align="right"><c:out value="${importiPA.percTotaleImportiEstrattiCasualeStr}"></c:out> </td>
					<th>% Analisi del rischio</th>
					<td align="right"><c:out value="${importiPA.percTotaleAnalisiDelRischioStr}"></c:out> </td>
				</tr>
			</tfoot>
		</table>
		</m:panel>
	</m:panel>
</c:if>

<m:panel id="pRisultati">
		<table id="tableInfoTotali" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh ">
			<tbody>	
				<tr>
					<th>Tipo Estrazione</th>
					<td><c:out value="${tipoEstrazione}"></c:out></td>
					<th>Numero Estrazione <a style="padding-left: 1em;" class="icon icon-list" rel="tooltip" data-placement="top"  title="Il numero è univoco nell'ambito del tipo di estrazione."></a></th>
					<td><c:out value="${numeroEstrazione}"></c:out></td>
					<th>Stato Estrazione</th>
					<td><c:out value="${statoEstrazione}"></c:out></td>
				</tr>
			</tbody>		
		</table>

	<div id="filter-bar" > </div>	 
	<table id="elencoEstrazioniTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
	  data-toggle="table" 
	  data-undefined-text = ''
	  data-url="getElencoRisultatiEstrazione_${idNumeroLotto}.json"
      data-pagination="true" 
	  data-show-pagination-switch="true" 
	  data-show-toggle="true"
	  data-pagination-v-align="top" 
	  data-show-columns="true"
	  data-side-pagination = "client" 
	  data-show-filter="true"
	  >
	<thead>
       	<tr>
       	    <th data-field="idTipoEstrazione"  data-visible ="false" data-switchable="false"></th>
       	    <th data-field="idFlagEstratta"  data-visible ="false" data-switchable="false"></th>
       	    <th data-field="idStatoEstrazione"  data-visible ="false" data-switchable="false"></th>
			<th data-field="descrEnteDelegato" data-sortable = "true">Ente Delegato</th>
			<th data-field="listLivelliHtml" data-sortable="true" >Misura</th>
			<th data-field="identificativo" data-sortable = "true" >Num. domanda</th>
			<th data-field="descrTipoDomanda" data-sortable = "true" >Tipo domanda</th>
			<th data-field="descrStato" data-sortable = "true" >Stato</th>
			<th data-field="cuaAzie" data-sortable = "true" >CUAA</th>
			<th data-field="denominazioneAzie" data-sortable = "true" >Denominazione</th>
			<th data-field="importoRichiesto" data-formatter="numberFormatter2" data-sortable = "true" >Imp. richiesto</th>
			<th data-field="punteggio" data-formatter="numberFormatter" data-sortable = "true" >Punti</th>
			<th data-field="classe" data-sortable = "true" >Classe</th>
			<th data-field="flagEstratta" data-sortable = "true" >Estr.</th>
       	</tr>
    </thead>
    <tbody></tbody>
    </table>
</m:panel>

<div class="form-group puls-group" style="margin-top:2em;padding-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-default">Indietro</button>
			</div>
	    </div>


</div>	

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/js/nembotableformatter.js"></script>
<script>
		$( document ).ready(function() {

			 $(function () {
			        $("[rel='tooltip']").tooltip();
			    });
			    
			$('#elencoEstrazioniTable').bootstrapTable();	
			
			$('#scaricaExcel').click(function(){
				forwardToPage('dettaglioEstrazioneExcel_${idNumeroLotto}.xls');
			});

			$('#btnRegistra').click(function(){
				forwardToPage('../cunembo223/registra_${idNumeroLotto}_${idStatoEstrazione}_${idTipoEstrazione}.do');
			});

			$('#btnEstrai').click(function(){
				forwardToPage('../cunembo221/index_${idNumeroLotto}_${idStatoEstrazione}_${idTipoEstrazione}.do');
			});

			$('#btnElimina').click(function(){
				forwardToPage('../cunembo222/index_${idNumeroLotto}_${idStatoEstrazione}.do');
			});

			$('#btnAnnulla').click(function(){
				forwardToPage('../cunembo224/index_${idNumeroLotto}_${idStatoEstrazione}_${idTipoEstrazione}.do');
			});
			
			$(".toggle_handle").click(function() {
			  if(!$('.hRows').is(":visible"))
				  $('#icona_espandi').removeClass('icon-expand').addClass('icon-collapse');
			  else
				  $('#icona_espandi').removeClass('icon-collapse').addClass('icon-expand');

			  if(!$('.hRows2').is(":visible"))
				  $('#icona_espandi2').removeClass('icon-expand').addClass('icon-collapse');
			  else
				  $('#icona_espandi2').removeClass('icon-collapse').addClass('icon-expand');
			});

			$(".icona_espandi_perc").click(function() {
				
				  if(!$(this).closest('tr').next('tr').is(":visible"))
					  $(this).removeClass('icon-expand').addClass('icon-collapse');
				  else
					  $(this).removeClass('icon-collapse').addClass('icon-expand');

				  $(this).closest('tr').next('tr').toggle();
				});


			$('#filter-bar').bootstrapTableFilter({
			      filters:[
						{
						    field: 'idFlagEstratta',    
						    label: 'Elenco domande',   
						    type: 'customWithCustomCheck'	,   
						    source: 'getElencoFlagEstrazione.do'
						}
			      ],
			      connectTo: '#elencoEstrazioniTable',
			      onSubmit: function() {
			    	  var data = $('#filter-bar').bootstrapTableFilter('getData');
			          var elabFilter = JSON.stringify(data);
			      }
			  });
		});



		
	</script> 
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
