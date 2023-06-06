<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
	
<body>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Elenco bandi </li>				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	
	<form:form action="" modelAttribute="filtroBandiForm" method="post">
	<input type="hidden" id="filtroBandi" value='${filtroAziende.get("elencoBandi")}' >
	</form:form> 
	
	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<h3>Elenco bandi disponibili</h3>
		<div id="filter-bar" style="margin-bottom: 1em"></div>
		<table id="elencoBandi"
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
				data-toggle="table" data-url="getElencoBandiJson.json"
				data-undefined-text='' 
				data-pagination = true
			  	data-page-size = 70
			  	data-show-pagination-switch="true" 
			  	data-page-list ="[10, 25, 50, 70, 100]"
			  	data-pagination-v-align="top" 
			  	data-show-columns="true" >
			<thead>
				<tr>
					<th data-width="60px"  data-field="flagGrafico" data-visible="true" data-switchable="false" data-formatter="formatterIcon"></th>
					<th data-field="denominazione" data-sortable="true">Denominazione</th>
					<th data-field="referenteBando" data-sortable="true">Referente Bando</th>
					<th data-field="annoCampagna" data-sortable="true" title="Anno campagna / Anno di riferimento">Anno di rif.</th>
					<th data-field="dataInizioStr" data-sorter="dateSorterddmmyyyyHHmmss" data-sortable="true" >Data apertura</th>
					<th data-field="dataFineStr" data-sorter="dateSorterddmmyyyyHHmmss" data-sortable="true">Data chiusura</th>
					<th data-field="descrTipoBando" data-sortable="true">Tipo bando</th>
					<th data-field="flagTitolaritaRegionale" data-sortable="true">Titolarit&agrave; regionale</th>				
					<th data-field="elencoCodiciLivelliHtml" data-sortable="true" title ="Operazione / Focus Area" data-visible="false" >Operazione / Focus Area</th>	
					<th data-field="elencoDescrizioniLivelli" data-visible="false" data-switchable="false"></th>
					<th data-field="elencoCodiciLivelli" data-visible="false" data-switchable="false"></th>	
					<th data-field="elencoCodiciLivelliMisureHtml" data-sortable="false">Operazione</th>	   	
	        		<th data-field="elencoCodiciLivelliMisure" data-visible="false" data-switchable="false" ></th>
	        		<th data-field="elencoCodiciLivelliSottoMisure" data-visible="false" data-switchable="false" ></th>
	        		<th data-field="elencoCodiciOperazione" data-visible="false" data-switchable="false" ></th>
	        		<th data-field="elencoSettoriStr" data-visible="false" data-switchable="false" ></th>
	        		<th data-field="elencoFocusAreaStr" data-visible="false" data-switchable="false" ></th>
	        		<th data-field="allegati" data-formatter="allegatiFormatter">Elenco allegati</th>
	        		<!--	<th data-width="40em;" data-field="amministrazioniCompetenzaHtml" data-visible="true" data-switchable="true" class="ammComp">Amm. competenza</th>	
					<th data-field="idsAmministrazioniCompetenza" data-visible="false" data-switchable="false"></th>	  -->
				</tr>
			</thead>
		</table>
	</div>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script>
	$( document ).ready(function() {
		$('#elencoBandi').bootstrapTable();	
	


	$('#filter-bar').bootstrapTableFilter({
        filters:[
				{				    
					field: 'annoCampagna',    
				    label: 'Anno campagna',   
				    type: 'range'
				},
				{				    
					field: 'referenteBando',    
				    label: 'Referente Bando',   
				    type: 'search'
                },
				{
				    field: 'denominazione',    
				    label: 'Denominazione',   
				    type: 'search'
				},
//                 {
//                     field: 'elencoCodiciLivelliMisure',   
//                     label: 'Misura',  
//                     type: 'ajaxSelectList',   
//                     source: 'getElencoCodiciLivelliMisureJson.do'
//                 },
//                 {
//                     field: 'elencoCodiciLivelliSottoMisure',   
//                     label: 'Sottomisura',  
//                     type: 'ajaxSelectList',   
//                     source: 'getElencoCodiciLivelliSottoMisureJson.do'
//                 },
                {
                    field: 'elencoCodiciOperazione',   
                    label: 'Operazione',  
                    type: 'ajaxSelectList',   
                    source: 'getElencoCodiciOperazioneJson.do'
                }
                ,
// 				{
// 				    field: 'elencoSettoriStr',    
// 				    label: 'Settore',   
// 				    type: 'ajaxSelectList',   
//                     source: 'getElencoSettoriJson.do'
// 				},
				
				{
				    field: 'elencoCodiciLivelliHtml',    
				    label: 'Focus Area',   
				    type: 'search'
				}
                ,
				
                {
				    field: 'descrTipoBando',    
				    label: 'Tipo bando',   
				    type: 'search'
				},
				{
				    field: 'flagTitolaritaRegionale',    
				    label: 'Titolarità regionale',   
				    type: 'select',
				    values: [
				             {id: 'Si', label: 'Si'},
				             {id: 'No', label: 'No'}
				         ],
				}/*,
				{
				    field: 'idsAmministrazioniCompetenza',    
				    label: 'Amm. Competenza',   
				    type: 'ajaxSelectList',   
                    source: 'getElencoAmministrazioniCompetenzaJson.do'
				}*/
                

        ],
        connectTo: '#elencoBandi',
        onSubmit: function() {
        	var data = $('#filter-bar').bootstrapTableFilter('getData');
            var elabFilter = JSON.stringify(data);
            $.ajax({
            	  type: "POST",
            	  url: '../session/salvaFiltri.do',
            	  data: "key=elencoBandi&filtro="+elabFilter,
            	  success: function( data ){}
            	});
            console.log(data);
        }

    
    });

	var filterJSON = $('#filtroBandi').val();
	if(filterJSON)
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	
	});

		function formatterIcon($value, row, index) {

			if(row['flagGrafico']!=null || row['flagReport']!=null || row['haveGraduatorie'])		
		      var iconDettagli = "<a href=\"gestBar_"+row['idBando']+".do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_graphic\" title=\"Dettagli bando\" style=\"font-size:1.4em;\"></i></a>";

			   return iconDettagli;
			}

		function allegatiFormatter($value, row, index)
	    {
	      var allegati = row['allegati'];
	      
	      var ret='<ul style=\"padding-left: 2em;\">';
	      var i =0;

	      if(allegati!=null)
	      {
		      for (i=0;i<allegati.length;i++){
				  var descr = allegati[i].descrizione;
		    	  ret = ret.concat('<li><a href=\"downloadAllegatiElencoBandi_'+allegati[i].idAllegatiBando+'.do\" title = \"'+descr+'\">'+allegati[i].nomeFile+'</a></li><br>');
				} 
		  }
	      ret = ret.concat('</ul>');
	      return ret;
	   
	    }
		
	</script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>	
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />