<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
				  <li><a href="index.do">Elenco estrazioni</a> <span class="divider">/</span></li>
				  <li class="active">Dettaglio estrazione</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>

	<div class="container-fluid" id="testata_di_pagina">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div id="header_info">
						Estrazione <c:if test="${!empty numeroEstrazione }">n° ${numeroEstrazione}</c:if>  del ${dataEstrazioneStr} - ${tipologiaEstrazione} - ${descrizioneEstrazione} <br>
					</div>
				</div>
			</div>
	</div>
<form:form action="" modelAttribute="filtroProcedimentiEstrazioneForm" method="post">
	<input type="hidden" id="filtroProcedimentiEstrazione" value='${filtroAziende.get("elencoProcedimenti")}' >
	</form:form>
	<div class="container-fluid" id="content" style="margin-bottom:3em;position:relative">
		<h3>Elenco procedimenti estrazione</h3>
		<div id="filter-bar" style="position:absolute;top:54px"> </div>

		<table id="elencoProcedimenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
			  data-toggle="table" data-url="getElencoProcedimenti_${idEstrazione}.json"
			  data-pagination="true" 
			  data-show-pagination-switch="true" 
			  data-pagination-v-align="top" 
			  data-page-size = "25"  
			  data-page-list="[10, 25, 50, 100]"		  			  
			  data-show-columns="true"
			  data-side-pagination = "server" 
			  data-undefined-text = ''
			  data-show-toggle="true"
			  data-page-number="${sessionMapNumeroPagina.get('elencoProcedimenti') != null ? sessionMapNumeroPagina.get('elencoProcedimenti') : '1'}">
			<thead>
	        	<tr>
					<th data-field="" data-switchable="false">
						<a href="#" onClick="forwardToPage('../cunembo215/elencoProcedimentiEstrazioneExcel_${idEstrazione}.xls');" style="text-decoration: none;"><i class="ico24 ico_excel" title="Esporta dati in Excel"></i></a>
					</th>
	        		<th data-field="identificativo" data-sortable="false"  data-visible="${!colonneNascoste.visible('elencoProcedimenti','identificativo')}">Identificativo<br/>procedimento</th>
	        		<th data-field="descrAmmCompetenza" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrAmmCompetenza')}">Amm.<br/>Comp.</th>		
	        		<th data-field="annoCampagna"  data-visible="${!colonneNascoste.visible('elencoProcedimenti','annoCampagna')}">Anno<br/>campagna</th>
	        		<th data-field="elencoCodiciLivelliHtml"  data-visible="${!colonneNascoste.visible('elencoProcedimenti','elencoLivelli')}">Operazione</th>
       				<th data-field="denominazioneBando" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','denominazioneBando')}">Bando</th>
	        		<th data-field="descrizione" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','descrizione')}">Stato<br/>procedimento</th>		
	        		<c:if test="${PO == 0}">
		        		<th data-field="identificativoOggetto" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','elencoLivelli')}">Identificativo oggetto</th>
		        		<th data-field="tipoOggetto" data-sortable="false"  data-visible="${!colonneNascoste.visible('elencoProcedimenti','elencoLivelli')}">Tipo oggetto</th>
		        		<th data-field="statoOggetto"  data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','elencoLivelli')}">Stato oggetto</th>        		
	        		</c:if>       
	        		<th data-field="flagEstrattaStr"  data-visible="${!colonneNascoste.visible('elencoProcedimenti','flagEstrattaStr')}">Tipologia estrazione</th>        		 		
	        		<th data-field="cuaa" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','cuaa')}">CUAA</th>
	        		<th data-field="denominazioneAzienda" data-sortable="false" data-visible="${!colonneNascoste.visible('elencoProcedimenti','denominazioneAzienda')}">Denominazione</th>
	        		<th data-field="descrComune" data-sortable="false" data-visible="${!colonneNascoste.hide('elencoProcedimenti','descrComune',true)}">Comune</th>
	        		<th data-field="descrProvincia" data-sortable="false" data-visible="${!colonneNascoste.hide('elencoProcedimenti','descrProvincia',true)}">Provincia</th>
	        		<th data-field="indirizzoSedeLegale" data-sortable="false" data-visible="${!colonneNascoste.hide('elencoProcedimenti','indirizzoSedeLegale',true)}">Sede<br/>Legale</th>
	        		<th data-field="denominzioneDelega" data-sortable="false" data-visible="${!colonneNascoste.hide('elencoProcedimenti','denominzioneDelega',true)}">Gestore<br/>fascicolo</th>	
	        	</tr>
		    </thead>
		</table>
		
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/js/nembotableformatter.js"></script>

<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/js/nembotableformatter.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script type="text/javascript">
    	$( document ).ready(function() {
    		var filterJSON = $('#filtroProcedimentiEstrazione').val();
    		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
    		
    		$('body').on( "column-switch.bs.table", function(obj, field) {
  			  var value = $('input[data-field="'+field+'"]').prop("checked");
  			  $.ajax({
              	  type: "POST",
              	  url: '../session/salvaColonna.do',
              	  data: "key=elencoProcedimenti&field="+field+"&value="+value,
              	  success: function( data ){}
              	});
  			});
    		
    		/*Mantengo in sessione il numero di pagina dell'elenco*/
    		/*$('body').on("page-change.bs.table", function(obj, size) {
    			$.ajax({
                	  type: "POST",
                	  url: '../session/salvaNumeroPagina.do',
                	  data: "key=elencoProcedimenti&page="+size,
                	  success: function( data ){}
                	});
    		});*/

   		
    			
    		$('#filter-bar').bootstrapTableFilter({
                filters:[
					/*{
					    field: 'annoCampagna',   
					    label: 'Anno campagna',  
					    type: 'search'   
					},*/
					/*{
					    field: 'denominazioneBando',   
					    label: 'Bando',  
					    type: 'search'   
					},
					{
					    field: 'descrComune',   
					    label: 'Comune sede legale',  
					    type: 'search'   
					},
					{
					    field: 'cuaa',   
					    label: 'Cuaa',  
					    type: 'search'   
					},
					{
                        field: 'dataUltimoAggiornamento',   
                        label: 'Data ultimo aggiornamento',  
                        type: 'range'   
                    },
                    {
                        field: 'denominazioneAzienda',   
                        label: 'Denominazione Azienda',  
                        type: 'search'   
                    },
                    {
                        field: 'descrizione',   
                        label: 'Stato procedimento',  
                        type: 'ajaxSelect',   
                        source: 'getElencoStatiProcedimenti.do'
                    },
					{
					    field: 'descrProvincia',   
					    label: 'Provincia',  
					    type: 'search'   
					}*/
					    {
					    	field: 'flagEstrattaStr',    
							label: 'Tipologia estrazione', 
	                        type: 'ajaxSelect',   
	                        source: 'getElencoTipologieEstrazione.do'
	                    }
                ],
                connectTo: '#elencoProcedimenti',
                onSubmit: function() {
                	var data = $('#filter-bar').bootstrapTableFilter('getData');
           
                    var elabFilter = JSON.stringify(data);
                    $.ajax({
                    	  type: "POST",
                    	  url: '../session/salvaFiltri.do',
                    	  data: "key=elencoProcedimenti&filtro="+elabFilter,
                    	  success: function( data ){}
                    	});
                 
                    console.log(data);
                }
            });
    		
    		
    	});
    	
    	
	</script>


<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
