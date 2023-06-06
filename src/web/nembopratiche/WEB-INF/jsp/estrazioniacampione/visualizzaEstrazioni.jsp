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
					<li class="active">Elenco estrazioni a campione</li>
				</ul>
			</div>
		</div>
	</div>
			<div class="container-fluid" style="padding-top:2em">
		
	<form:form action="" modelAttribute="filtroEstrazioniForm" method="post">
	<input type="hidden" id="filtroEstrazioni" value='${filtroAziende.get("elencoEstrazioniTable")}' >
	</form:form> 
		<div class="container-fluid">
		
		<h3>Elenco estrazioni a campione</h3>
	
		<div id="filter-bar" > </div>	 
		<table id="elencoEstrazioniTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  data-toggle="table" 
		  data-undefined-text = ''
		  data-url="getElencoEstrazioniJson.json"
	      data-pagination="true" 
		  data-show-pagination-switch="true" 
		  data-show-toggle="true"
		  data-pagination-v-align="top" 
		  data-show-filter="true"
		  >
		<thead>
        	<tr>
        		<th data-field="idTipoEstrazione" data-visible ="false"></th>
        		<th data-field="idFlagEstratta" data-visible ="false"></th>
        		<th data-field="idEstrazioneCampione" data-formatter="showDetails"></th>
				<th data-field="identificativo" data-sortable = "true">Identificativo estrazione</th>
				<th data-field="dataEstrazioneStr" data-sortable="true" data-sorter="dateSorterddmmyyyy" >Data estrazione</th>
				<th data-field="descrizioneTipologia" data-sortable = "true" >Tipologia estrazione</th>
				<th data-field="numeroEstrazione">Numero Estrazione</th>
				<th data-field="descrizione">Descrizione</th>

        	</tr>
	    </thead>
	    <tbody></tbody>
	    </table>
	    <div class="form-group puls-group" style="margin-top:2em;padding-bottom:3em">
			<div class="pull-left">
				<a href="../index.do"><button type="button"  class="btn btn-primary">Indietro</button></a>
			</div>
		</div>
		
	    </div>
	    </div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/js/nembotableformatter.js"></script>

<script type="text/javascript">
$( document ).ready(function() {
	$('#elencoEstrazioniTable').bootstrapTable();	
		
	$('#filter-bar').bootstrapTableFilter({
      filters:[
			{
			    field: 'idTipoEstrazione',    
			    label: 'Tipologia estrazione',   
			    type: 'ajaxSelect',   
			    source: 'getElencoTipologieFiltroJson.do'
			},
			{
			    field: 'idFlagEstratta',    
			    label: 'Con elementi', 
			    type: 'customWithCustomCheck'	,   
			    source: 'getElencoFlagEstrazione.do'
			}
      ],
      connectTo: '#elencoEstrazioniTable',
      onSubmit: function() {
    	  var data = $('#filter-bar').bootstrapTableFilter('getData');
            var elabFilter = JSON.stringify(data);
            $.ajax({
            	  type: "POST",
            	  url: '../session/salvaFiltri.do',
            	  data: "key=elencoEstrazioniTable&filtro="+elabFilter,
            	  success: function( data ){}
            	});
            console.log(data);
      }
  });
	  
	var filterJSON = $('#filtroEstrazioni').val();
	if(filterJSON!=null)
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	
});


function showDetails($value, row, index) {

		if(row['dataAnnullamento']!=null && row['dataAnnullamento']!==undefined && row['dataAnnullamento']!="")
			return "";
		
	   var iconDettagli = "<a href=\"dettaglio_"+row['idEstrazioneCampione']+".do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_magnify\" title=\"Dettagli estrazione\" style=\"font-size:1.4em;\"></i></a>";

	   return iconDettagli;
}

  </script>
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>	


<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>