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
				  <li><a href="elencobando.do">Elenco bandi</a> <span class="divider">/</span></li>
				  <li><a href="dettaglioBando.do">Dettaglio bando</a> <span class="divider">/</span></li>
				  <li class="active">Elenco aziende</li>
				</ul>
            </div>
        </div>           
    </div>
    <p:messaggistica/>
	<form:form action="" modelAttribute="filtroAziendeForm" method="post">
	<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoAziende")}' >
	</form:form>   
	<div class="container-fluid" id="content" style="margin-bottom:3em;position:relative">
		<h3><c:out value="${denominazionebando}"></c:out></h3>
		<h3>Elenco aziende</h3>
		<div id="filter-bar" style="position:absolute;top:80px"> </div>
		<table id="elencoAziende" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
			  data-toggle="table" data-url="getElencoAziende.json"
			  data-pagination="true" 
			  data-show-pagination-switch="true" 
			  data-show-toggle="true"
			  data-pagination-v-align="top" 
			  data-show-columns="true"
			  data-show-filter="true"
			  data-undefined-text = ''
			  data-page-number="${sessionMapNumeroPagina.get('elencoAziende') != null ? sessionMapNumeroPagina.get('elencoAziende') : '1'}">
			<thead>
	        	<tr>
					<th data-width="80" data-field="azione" data-switchable="false"></th>
					<th data-field="procesistente" data-switchable="false" data-visible="false"></th>
	        		<th data-field="cuaa" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','cuaa')}">CUAA</th>
	        		<th data-field="partitaIva" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','partitaIva')}">Partita IVA</th>		
	        		<th data-field="denominazioneIntestazione" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','denominazioneIntestazione')}">Denominazione</th>
	        		<th data-field="descrComune" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','descrComune')}">Comune</th>		
	        		<th data-field="sedeLegale" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','sedeLegale')}">Sede legale</th>		
	        		<th data-field="identificativo" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','identificativo')}">Identificativo</th>
	        		<th data-field="descrStatoOggetto" data-sortable="true" data-visible="${!colonneNascoste.visible('elencoAziende','descrStatoOggetto')}">Stato procedimento</th>
	        		<th data-field="richiedenteDescr" data-sortable="true" data-visible="false">Richiedente</th>	
	        	</tr>
		    </thead>
		</table>	
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<script type="text/javascript">

		function creaProcedimento(idAzienda)
		{
		    return openPageInPopup('confermaNuovoProcedimento_'+idAzienda+'.do','dlgProcedimento','Conferma Nuovo Procedimento', 'modal-large');
		}
		
		function confermaNuovoProcedimentoAdUnoEsistente(idAzienda)
		{
		    return openPageInPopup('confermaNuovoProcedimentoAdUnoEsistente_'+idAzienda+'.do','dlgProcedimento','Conferma Nuovo Procedimento', 'modal-large');
		}

    	$( document ).ready(function() {
    		
    		$('body').on( "column-switch.bs.table", function(obj, field) {
    			  var value = $('input[data-field="'+field+'"]').prop("checked");
    			  $.ajax({
                	  type: "POST",
                	  url: '../session/salvaColonna.do',
                	  data: "key=elencoAziende&field="+field+"&value="+value,
                	  success: function( data ){}
                	});
    			});
    		
    		/*Mantengo in sessione il numero di pagina dell'elenco*/
    		$('body').on("page-change.bs.table", function(obj, size) {
    			$.ajax({
                	  type: "POST",
                	  url: '../session/salvaNumeroPagina.do',
                	  data: "key=elencoAziende&page="+size,
                	  success: function( data ){}
                	});
    		});
    		
    		$('#elencoAziende').bootstrapTable(
   			    {
    			   queryParams: function(params) 
    			     {
   			            var values = $('#filter-bar').bootstrapTableFilter('getData');
   			            if ( !jQuery.isEmptyObject(values)) {
   			            	var elabFilter = JSON.stringify(values);
   	    			        return elabFilter;
   			            }
   			            return params;
   			        }
   			    }
   			);
    			
    		$('#filter-bar').bootstrapTableFilter({
                filters:[
					{
					    field: 'procesistente',    
					    label: 'Procedimento',   
					    type: 'select',
					    values: [
					             {id: 'S', label: 'Esistente'},
					             {id: 'N', label: 'Da creare'}
					         ],
					},
					{
					    field: 'cuaa',   
					    label: 'Cuaa',  
					    type: 'search',
					},
                    {
                        field: 'denominazioneIntestazione',   
                        label: 'Denominazione',  
                        type: 'search'   
                    },
                    {
                        field: 'descrComune',   
                        label: 'Comune',  
                        type: 'search'   
                    },
                    {
                        field: 'descrStatoOggetto',   
                        label: 'Stato procedimento',  
                        type: 'ajaxSelect',   
                        source: 'getElencoStatiProcedimenti.do'
                    }
                ],
                connectTo: '#elencoAziende',
                onSubmit: function() {
                    var data = $('#filter-bar').bootstrapTableFilter('getData');
                    var elabFilter = JSON.stringify(data);
                    $.ajax({
                    	  type: "POST",
                    	  url: '../session/salvaFiltri.do',
                    	  data: "key=elencoAziende&filtro="+elabFilter,
                    	  success: function( data ){}
                    	});
                    console.log(data);
                }
            });
    		var filterJSON = $('#filtroAziende').val();
    		if(filterJSON){
    			$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
    		}
    	});
	</script>

<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
