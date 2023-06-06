<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
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
  
  max-width: 30em;
}

.myLi{
    max-width: 30em;
    min-width: 6em;
}
</style>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-263-L" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-L" />
	
	
	<div class="container-fluid" id="content">
		<c:if test="${updateSuccess != null}">
		       	<div class="stdMessagePanel">	
		             <div class="alert alert-success">
		                 <p>Aggiornamento eseguito con successo!</p>
		              </div>
		          </div>
		   	</c:if>		
		
			<m:panel id="flagRendicontazionePanel" title="tipo rendicontazione">
				<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-F">
					<m:icon icon="modify" href="" onclick="return modificaFlagRendicontazione()" />
				</p:abilitazione-cdu>
				
				<table summary="Rendicontazione con IVA" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<tbody>
						<tr >
						  <th class="col-sm-3 ">Il beneficiario, secondo quanto predisposto dal Bando, può richiedere l'IVA a sostegno?</th>
							<td>
								<c:if test="${flagRendicontazioneConIvaDescr != 'X'}">
									${flagRendicontazioneConIvaDescr}
								</c:if>
							</td>
						</tr>
						
					</tbody>
				</table>
			</m:panel>
	
		<m:panel id="panelDocumenti" title="Documenti spesa">
		
			<c:if test="${hasHelp!=null}">
				<a href="#" onclick="toggleHelp('CU-NEMBO-263-L');return false;" style="background-color:#FFFFFF;border:0px" class="toggle_help" title="Visualizza Help"><span style="text-decoration: none; font-size: 18px; background-color: yellow;" id="icona_help" class="icon icon-info"></span></a>
				<div id="help_container" class="container-fluid" style="margin-top: 1em;"><blockquote id="help_text"></blockquote></div>
			</c:if>
			
			<c:if test="${flagRendicontazioneConIva == 'X'}">
				<div class="stdMessagePanel">	
		            <div class="alert alert-warning">
		                <p>Per gestire i documenti spesa è necessario specificare se il beneficiario può o meno rendicontare l'IVA.</p>
		             </div>
		         </div>
			</c:if>
			
			
			<c:if test="${flagRendicontazioneConIva != 'X'}">
				<form name="elencoForm" id="elencoForm" method="post" action="">
				<div id="filter-bar" style="position: relative; top: 46px"></div>
    			<form:form action="" modelAttribute="filtroAziendeForm" method="post">
				<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoDocumenti")}' >
				</form:form> 
				<table id="elencoDocumenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
					  data-toggle="table" 
					  data-url="getElencoDocumenti.json"
					  data-show-columns="true"
					  data-show-filter="true"
					  data-sort-name="${sessionMapNomeColonnaOrdinamento.get('keyCol')}"
       				  data-sort-order="${sessionMapNomeColonnaOrdinamento.get('keyOrder')}"
					  data-undefined-text = ''
					  data-select-item-name="idDocumentoSpesa"
					  data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top"
					  data-id-field="idDocumentoSpesa"
					  data-page-number="${sessionMapNumeroPagina.get('elencoDocumenti') != null ? sessionMapNumeroPagina.get('elencoDocumenti') : '1'}">
					<thead>

			        	<tr>
			        		<c:if test="${empty isFromRegistroFatture}">
				        		<th rowspan="2" class="center" data-field="idDocumentoSpesa" data-width="130" data-switchable="false"  data-formatter="iconeFormatter">
									<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-I">
										<a href="../cunembo263i/index.do"  style="text-decoration: none;"><i class="ico24 ico_add" title="Aggiungi documento"></i></a>
									</p:abilitazione-cdu>
									<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">
										<a href="#"  style="text-decoration: none;vertical-align:middle;margin-bottom:1em;color:white;font-size:18px;" class="glyphicon glyphicon-euro" onclick="aggiungiinterventifn()" title="Aggiungi interventi"></a>
									</p:abilitazione-cdu>
									<a href="#"  style="text-decoration: none;vertical-align:middle;margin-bottom:1em;color:white;font-size:18px;" class="ico24 ico_magnify" onclick="visualizzaDettagliRicevute()" title="Dettaglio interventi"></a>
									<a href="#" onClick="forwardToPage('elencoDocumentiExcel.xls');" style="text-decoration: none;"><i class="ico24 ico_excel" title="Esporta dati in Excel"></i> </a>									
								
								</th>
								<th  rowspan="2" data-checkbox="true" data-switchable="false"  data-sortable="false"></th>
							</c:if>
							<th colspan="2" class="center colConColspanFornitore" >Dati fornitore</th>
							<th colspan="10" class="center colConColspan" >Dati documento di spesa</th>
							<th colspan="2" class="center colConColspanRic" >Dati ricevute pagamento</th>
			        		<th rowspan="2" data-field="note"  data-visible="${!colonneNascoste.hide('elencoDocumenti','note',true)}">Note</th>
			        		<th rowspan="2" data-visible="false" data-switchable="false" class="center colConColspanFornitore" data-field="codOggettiDoc"></th>
			        		
			        	</tr>
			        <tr>
			        		<th data-field="codiceFornitore" data-switchable="true" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoDocumenti','codiceFornitore')}">Codice</th>
			        		<th data-field="ragioneSociale" data-switchable="true" data-sortable="true" data-width="280" data-visible="${!colonneNascoste.visible('elencoDocumenti','ragioneSociale')}">Denominazione</th>
			        		<th data-field="dataDocumentoSpesaStr" data-switchable="true" data-sortable="true" data-width="100" data-sorter="dateSorterddmmyyyy" data-visible="${!colonneNascoste.visible('elencoDocumenti','dataDocumentoSpesaStr')}">Data</th>
			        		<th data-field="numeroDocumentoSpesa" data-switchable="true" data-sortable="true" data-width="100" data-visible="${!colonneNascoste.visible('elencoDocumenti','numeroDocumentoSpesa')}">Numero</th>
			        		<th data-field="descrTipoDocumento" data-switchable="true" data-sortable="true" data-width="100" data-visible="${!colonneNascoste.visible('elencoDocumenti','descrTipoDocumento')}">Tipo</th>		        		
			        		<th data-field="importoSpesa" data-switchable="true" data-sortable="true" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoSpesa')}">Importo<br>netto</th>
			        		<th data-field="importoIva" data-switchable="true" data-sortable="true" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoIva')}">Importo<br>iva</th>
			        		<th data-field="importoLordo" data-switchable="true" data-sortable="true" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoLordo')}">Importo<br>lordo</th>
			        		<th data-field="importoAssociato" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.hide('elencoDocumenti','importoAssociato',true)}">Importo associato<br>per la rendicontazione</th>
							<th data-field="importoRendicontato" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoRendicontato')}">Importo<br>rendicontato</th>
							<!--  th data-field="importoAncoraDaRendicontare" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoAncoraDaRendicontare')}">Importo da<br>rendicontare</th>
							-->
							<th data-field="importoDaRendicontare" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.visible('elencoDocumenti','importoDaRendicontare')}">Importo da<br>rendicontare</th>
				        	<th data-field="nomeFileLogicoDocumentoSpe" data-switchable="true" data-sortable="false" data-formatter="allegatiFormatter" data-visible="${!colonneNascoste.visible('elencoDocumenti','nomeFileLogicoDocumentoSpe')}">File</th>
				        	<th data-field="importoLordoPagamento" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.hide('elencoDocumenti','importoLordoPagamento',true)}">Importo<br>pagamento</th>
				        	<th data-field="importoAssociatoRic" data-switchable="true" data-sortable="true" data-width="100" data-totale="true" data-formatter="numberFormatter2" data-adder="totalFormattedAdder" data-visible="${!colonneNascoste.hide('elencoDocumenti','importoAssociatoRic',true)}">Importo associato<br>per la rendicontazione</th>
			        		
					</tr>
				    </thead>
				</table>
				</form>
				<form name="aggiungiinterventi" id="aggiungiinterventi" method="post" action="../cunembo263m/modificainterventi.do">
				</form>
				<form name="dettaglioricevute" id="dettaglioricevute" method="post" action="../cunembo263l/dettagliointerventi.do">
				</form>
			</c:if>
			<c:if test="${isFromRegistroFatture }">
			<div class="col-sm-12" style="margin-bottom: 2em;margin-top: 2em">
						<div class="puls-group" >
							<div class="pull-left">
								<button type="button" onclick="goBack();" class="btn btn-default">indietro</button>
							</div>
						</div>
						<div class="puls-group" >
							<div class="pull-right">
								<a type="button" href="../cunembo129/index_${idProcedimento}.do" class="btn btn-default">elenco oggetti</a>
							</div>
						</div>
					</div>
			</c:if>
		</m:panel>
	</div>
	<input type="hidden" id="numberOfDateFilter" value="2">  
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script type="text/javascript">
	
	function goBack(){
	
		forwardToPage('../cunembo270/back.do');
		
	}
	
	$( document ).ready(function() {

		toggleHelp('CU-NEMBO-263-L');

		
		/*<c:choose><c:when test="${empty isFromRegistroFatture}">*/
		$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li class="active"><span class="divider">/</span>Documenti spesa</li>');
		/*</c:when><c:otherwise>*/
		$(".breadcrumb").html('<li><a href="../index.do">Home</a></li><li><span class="divider">/</span><a href="cunembo273/index.do">Registro fatture</a></li><li class="active"><span class="divider">/</span>Documenti spesa</li>');
		/*</c:otherwise></c:choose>*/

		$('#elencoDocumenti').bootstrapTable();

		/*Mantengo in sessione il numero di pagina dell'elenco*/
		$('body').on("page-change.bs.table", function(obj, size) {
			$.ajax({
            	  type: "POST",
            	  url: '../session/salvaNumeroPagina.do',
            	  data: "key=elencoDocumenti&page="+size,
            	  success: function( data ){}
            	});
		});

		/*Mantengo in sessione il numero di pagina dell'elenco*/
		$('body').on("sort.bs.table", function(obj, col, order) {

			$.ajax({
            	  type: "POST",
            	  url: '../session/salvaOrdinamento.do',
            	  data: "col="+col+"&order="+order,
            	  success: function( data ){}
            	});
		});
		
  		$('body').on( "column-switch.bs.table", function(obj, field, checked) {

  	  		calculateColspan();
  	  		
			  $.ajax({
            	  type: "POST",
            	  url: '../session/salvaColonna.do',
            	  data: "key=elencoDocumenti&field="+field+"&value="+checked,
            	  success: function( data ){}
            	});
			});
		
		$('#filter-bar').bootstrapTableFilter({
            filters:[
				{
				    field: 'codiceFornitore',   
				    label: 'Codice Fornitore',  
				    type: 'search'   
				},
				{
				    field: 'ragioneSociale',   
				    label: 'Fornitore',  
				    type: 'search'   
				},
				{
				    field: 'dataDocumentoSpesaStr',   
				    label: 'Data documento',  
				    type: 'date'   
				},
				{
				    field: 'numeroDocumentoSpesa',   
				    label: 'Numero documento',  
				    type: 'search'   
				},
				{
				    field: 'idTipoDocumentoSpesa',   
				    label: 'Tipo documento',  
				    type: 'ajaxSelect',
                    source: 'getTipiDocumento.do'		     
				},
				{
				    field: 'codOggettiDoc',   
				    label: 'Domanda di pagamento',  
				    type: 'ajaxSelectList',   
				    source: 'getDomandePagamento.do'   
				},
				{
				    field: 'note',   
				    label: 'Note',  
				    type: 'search'   
				}
            ],
            connectTo: '#elencoDocumenti',
            onSubmit: function() {
            	var data = $('#filter-bar').bootstrapTableFilter('getData');
            	 var elabFilter = JSON.stringify(data);
				
		            $.ajax({
		            	  type: "POST",
		            	  async: false,
		            	  url: '../session/salvaFiltri.do',
		            	  data: "key=elencoDocumenti&filtro="+elabFilter,
		            	  success: function( data ){
		            	  }
		            	});
                console.log(data);
            	$('#elencoDocumenti').bootstrapTable('refresh');
            }
        });
		var filterJSON = $('#filtroAziende').val();
		if(filterJSON){
			$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);}

		//add default ordination button
		$("button[name='paginationSwitch']").after('<button class="btn btn-default" id="defaultOrderButton" type="button" onClick="ripristinaOrdinamentoDefault();return false;"  title="Ripristino ordinamento iniziale" style="text-decoration: none;vertical-align:center;margin-bottom:2em;color:black;"> <span class="glyphicon glyphicon-sort-by-attributes"></span> </a>');

		toggleHelp('CU-NEMBO-263-L');
		
		calculateColspan();
		$("button[name='paginationSwitch']").click();

		
		
	});

	function calculateColspan(){	

			$(".colConColspan").show();
  			$(".colConColspanFornitore").show();
			
			var colspan = 0;
	  		if($("input[data-field=dataDocumentoSpesaStr]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=numeroDocumentoSpesa]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=descrTipoDocumento]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoSpesa]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoIva]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoLordo]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoAssociato]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoRendicontato]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=importoDaRendicontare]")[0].checked==true)
		  		colspan++;
	  		if($("input[data-field=nomeFileLogicoDocumentoSpe]")[0].checked==true)
		  		colspan++;
	  		
	  		
	  		$(".colConColspan").attr('colspan',colspan);
	  		if(colspan==0)
	  			$(".colConColspan").hide();

	  		var colspan2=0;
	  		if($("input[data-field=codiceFornitore]")[0].checked==true)
	  			colspan2++;
	  		if($("input[data-field=ragioneSociale]")[0].checked==true)
	  			colspan2++;
	  		$(".colConColspanFornitore").attr('colspan',colspan2);
	  		if(colspan2==0)
	  			$(".colConColspanFornitore").hide();


	  		var colspan3=0;
	  		if($("input[data-field=importoLordoPagamento]")[0].checked==true)
	  			colspan3++;
	  		if($("input[data-field=importoAssociatoRic]")[0].checked==true)
	  			colspan3++;
	  		$(".colConColspanRic").attr('colspan',colspan3);
	  		if(colspan3==0)
	  			$(".colConColspanRic").hide();
  			
		}

	
	function modificaFlagRendicontazione()
	  {
	    return openPageInPopup('../cunembo263l/popup_flag_rendicontazione.do', 'dlgModificaFlag', 'Modifica Tipo Rendicontazione', 'modal-lg',
	        false);
	  }

	
	function allegatiFormatter($value, row, index)
    {
      var allegati = row['allegati'];
      //var ret=row.nomeFileLogicoDocumentoSpe+' <a href=\"../cunembo263m/download_'+row.idDettDocumentoSpesa+'.do\" class=\"ico24 '+row.iconaFile+'\" title = '+row.nomeFileFisicoDocumentoSpe+'></a>';
      var ret= '<ul style=\"padding-left: 2em;\">';
	  var i =0;

	  if(allegati!=null)
      for(i=0;i<allegati.length;i++)
          {
			var all = allegati[i];
			if(all.poApprovatoNegativo)
				ret = ret.concat('<li class=\"myLi\"><span class=\"mySpan\"><a style=\"color:red;\" href=\"../cunembo263m/download_'+all.idDocumentoSpesaFile+'.do\" title = \"'+all.nomeFileFisicoDocumentoSpe+'\">'+all.nomeFileLogicoDocumentoSpe+'</a></span></li><br>');
			else
				ret = ret.concat('<li class=\"myLi\"><span class=\"mySpan\"><a href=\"../cunembo263m/download_'+all.idDocumentoSpesaFile+'.do\" title = \"'+all.nomeFileFisicoDocumentoSpe+'\">'+all.nomeFileLogicoDocumentoSpe+'</a></span></li><br>');
          }

	  ret = ret.concat('</ul>');
      
      return ret;
    }
	
	function iconeFormatter($value, row, index)
    {
        var html = '';

		//vedi dettaglio interventi spesa

        html += '<a href="#" style="cursor: pointer" onclick=\"visualizzaDettaglioRicevuta('+row.idDocumentoSpesa+')\" title=\" Dettaglio interventi\" > <i class="ico24 ico_magnify"></i></a>';
        
        if(row.flagEliminabile == "S"){

        /*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-E">*/
        html += '<a href="#" onclick="elimina('+$value+',\''+row.flagEliminabile+'\')"><i class="ico24 ico_trash"></i></a>';
        /*</p:abilitazione-cdu>*/

        /*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
        html += '<a href=\"../cunembo263m/getDatiInserimento_'+row.idDocumentoSpesa+'.do\"><i class="ico24 ico_modify"></i></a>';
        /*</p:abilitazione-cdu>*/

        /*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
		html += '&nbsp;<br/> <a style="text-decoration: none;font-size:14px;vertical-align:middle;margin-bottom:1em;" title ="Ricevute pagamento" class="ico24 ico_document"  href=\"../cunembo263m/elencoricevute_'+row.idDocumentoSpesa+'.do\"></a>';
	    /*</p:abilitazione-cdu>*/


        
        }
        else{

        	 if(row.flagShowIconDocGiaRendicontato == "S"){
	        	/*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
	    		html += '&nbsp;<br/> <a style="text-decoration: none;font-size:14px;vertical-align:middle;margin-bottom:1em;" title ="Ricevute pagamento" class="ico24 ico_document"  href=\"../cunembo263m/elencoricevuteDocRendicontato_'+row.idDocumentoSpesa+'.do\"></a>';
	    	    /*</p:abilitazione-cdu>*/
        	 }
            }


        
	if (row.flagShowIconEuro == "S") {
			if (row.flagHasInterventiAssociati == "S") {
				/*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
				html += '&nbsp;<a style="text-decoration: none;font-size:14px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-euro"  href=\"../cunembo263m/modificainterventi_'+row.idDocumentoSpesa+'.do\"></a>';
				/*</p:abilitazione-cdu>*/

			} else {
				/*<p:abilitazione-cdu codiceCdu="CU-NEMBO-263-M">*/
				html += '&nbsp;<a style="text-decoration: none;font-size:14px;vertical-align:middle;margin-bottom:1em;color:red;" class="glyphicon glyphicon-euro"  href=\"../cunembo263m/modificainterventi_'+row.idDocumentoSpesa+'.do\"></a>';
				/*</p:abilitazione-cdu>*/
			}
		}

		return html;
	}

	function visualizzainterventi(idDocumentoSpesa) {
		$.ajax({
			type : "GET",
			url : "getInterventi_" + idDocumentoSpesa + ".do",
			dataType : "html",
			async : false,
			success : function(data) {
				$('input[value=' + idDocumentoSpesa + ']').closest('tr').after(
						'<tr id=\"dettaglioimporti_'+idDocumentoSpesa+'\"><td colspan=\"13\">' + data + '</td></tr>');
				$('#espandi_' + idDocumentoSpesa).html(
						'<a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-minus-sign"  href=\"#'
								+ idDocumentoSpesa + '\" onclick=\"nascondiinterventi(' + idDocumentoSpesa + ');\"></a>')

			}
		});
	}

	function nascondiinterventi(idDocumentoSpesa) {
		$('#dettaglioimporti_' + idDocumentoSpesa).remove();
		$('#espandi_' + idDocumentoSpesa).html(
				'<a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-plus-sign"  href=\"#'
						+ idDocumentoSpesa + '\" onclick=\"visualizzainterventi(' + idDocumentoSpesa + ');\"></a>')
	}

	function elimina(idDocumentoSpesa, flagEliminabile) {
		if (flagEliminabile == "N")
			showMessageBox("Attenzione!", "Il documento spesa non può essere eliminato.", 'modal-large');
		else{

			  $.ajax(
			          {
			            type : "GET",
			            url : '../cunembo263e/canDeleteDoc_'+idDocumentoSpesa+'.do',
			            dataType : "html",
			            async : false,
			            success : function(html)
			            {
			              var COMMENT = '<success>';
			              if (html != null && html.indexOf(COMMENT) >= 0)
			              {
			      			
			  				return openPageInPopup('../cunembo263e/confermaElimina_' + idDocumentoSpesa + '.do', 'dlgEliminaDocumento', 'Elimina', '',
			  					false);
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
			
	}

	function aggiungiinterventifn() {
		if ($('input[name="idDocumentoSpesa"]:checked').length <= 0) {
			showMessageBox("Attenzione!", "Non hai selezionato nessun documento spesa", 'modal-large');
		} else {
			$('input[name="idDocumentoSpesa"]:checked').each(
					function() {
						$('#aggiungiinterventi')
								.append(
										'<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="' + $(this).val()
												+ '"></input>');
					});

			$('#aggiungiinterventi').submit();
		}
	}

	function visualizzaDettagliRicevute() {
		if ($('input[name="idDocumentoSpesa"]:checked').length <= 0) {
			showMessageBox("Attenzione!", "Non hai selezionato nessun documento spesa", 'modal-large');
		} else {
			$('input[name="idDocumentoSpesa"]:checked').each(
					function() {
						$('#dettaglioricevute')
								.append(
										'<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="' + $(this).val()
												+ '"></input>');
					});

			$('#dettaglioricevute').submit();
		}
	}

	function visualizzaDettaglioRicevuta(id) {

		$('#dettaglioricevute').append('<input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa" value="'+id+'"></input>');
		$('#dettaglioricevute').submit();
	}

	function ripristinaOrdinamentoDefault() {

		$
				.ajax({
					type : "POST",
					url : '../session/salvaOrdinamento.do',
					data : "col=idDocumentoSpesa&order=asc",
					success : function(data) {

						$('.asc').removeClass("asc");
						$('.desc').removeClass("desc");
						$('#elencoDocumenti').bootstrapTable('refreshOptions', {
							sortName : 'idDocumentoSpesa',
							sortOrder : 'asc',
						});
						$("#defaultOrderButton").remove();
						//add default ordination button
						$("button[name='paginationSwitch']")
								.after(
										'<button id="defaultOrderButton" title="Ripristino ordinamento iniziale" class="btn btn-default" type="button" onClick="ripristinaOrdinamentoDefault();return false;" style="text-decoration: none;vertical-align:center;margin-bottom:2em;color:black;"> <span class="glyphicon glyphicon-sort-by-attributes" ></span> </a>');
						calculateColspan();

					}
				});

	}
</script>

<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
