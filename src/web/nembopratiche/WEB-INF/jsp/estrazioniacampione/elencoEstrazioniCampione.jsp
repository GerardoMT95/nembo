<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>.tooltip-inner {
    max-width: 350px;
    /* If max-width does not work, try using width instead */
    width: 350px; 
}</style>
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Gestione Estrazioni</li>
				</ul>
			</div>
		</div>
	</div>
		<div class="container-fluid" style="padding-top:1em">
		
		<div class="puls-group" >
		<p:abilitazione-cdu codiceCdu="CU-NEMBO-218" >
				<div class="pull-left">
					<button type="button" onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();forwardToPage('../cunembo218/index.do');" class="btn  btn-primary">Simulazione</button>
				</div>
		</p:abilitazione-cdu>
	
		<p:abilitazione-cdu codiceCdu="CU-NEMBO-220" >
				<div class="pull-left" style="margin-left:120px">
					<button type="button" onclick="$('.stdMessageError').hide();$('.stdMessageLoad').show();forwardToPage('../cunembo220/index.do');" class="btn  btn-primary">Caricamento</button>
				</div>
				<br class="clear" />
		</p:abilitazione-cdu>
		</div>
		
	<form:form action="" modelAttribute="filtroEstrazioniForm" method="post">
	<input type="hidden" id="filtroEstrazioni" value='${filtroAziende.get("estrazioniTable")}' >
	</form:form> 
	
		<div style="margin-top:3em">
		<div id="filter-bar" > </div>	 
		<table id="estrazioniTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  data-toggle="table" 
		  data-undefined-text = ''
		  data-url="getElencoEstrazioniCampioneJson.json"
	      data-pagination="true" 
		  data-show-pagination-switch="true" 
		  data-show-toggle="true"
		  data-pagination-v-align="top" 
		  data-show-columns="true"
		  data-show-filter="true"
		  >
		<thead>
        	<tr>
        		<th data-field="idNumeroLotto" data-formatter="showDetails" data-switchable="false"></th>
        		<th data-field="idTipoEstrazione" data-visible ="false" data-switchable="false"></th>
        		<th data-field="idStatoEstrazione" data-visible ="false" data-switchable="false"></th>
				<th data-field="descrTipoEstrazione" data-sortable = "true">Tipo estrazione</th>
				<th data-field="numeroEstrazione" data-sortable = "true" >Numero estrazione<a style="padding-left: 1em;color:white;" class="icon icon-list" rel="tooltip" data-placement="top"  title="Il numero è univoco nell'ambito del tipo di estrazione."></a></th>				
				<th data-field="dataElaborazioneStr" data-sortable="true" data-sorter="dateSorterddmmyyyy" >Data</th>
				<th data-field="descrizione" data-sortable = "true" >Note</th>
				<th data-field="descrStatoEstrazione" data-sortable = "true" >Stato estrazione</th>				
        	</tr>
	    </thead>
	    <tbody></tbody>
	    </table>
	    </div>
		<div class="form-group puls-group" style="margin-top:2em;padding-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-default">Indietro</button>
			</div>
	    </div>
	    </div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>	

<script type="text/javascript">
$( document ).ready(function() {

	  $(function () {
	        $("[rel='tooltip']").tooltip();
	    });
	
	$('#estrazioniTable').bootstrapTable();	
		
	$('#filter-bar').bootstrapTableFilter({
      filters:[
			{
			    field: 'idTipoEstrazione',    
			    label: 'Tipo estrazione',   
			    type: 'ajaxSelect',   
			    source: 'getElencoTipologieFiltroEstrCampioneJson.do'
			},
			{
			    field: 'idStatoEstrazione',    
			    label: 'Stato estrazione', 
			    type: 'ajaxSelect',   
			    source: 'getElencoStatoEstrCampione.do'
			}
      ],
      connectTo: '#estrazioniTable',
      onSubmit: function() {
    	  var data = $('#filter-bar').bootstrapTableFilter('getData');
          var elabFilter = JSON.stringify(data);
          $.ajax({
        	  type: "POST",
        	  url: '../session/salvaFiltri.do',
        	  data: "key=estrazioniTable&filtro="+elabFilter,
        	  success: function( data ){}
        	});
        console.log(data);
      }
  });
	var filterJSON = $('#filtroEstrazioni').val();
	if(filterJSON!==undefined && filterJSON!=null)
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	
});


function showDetails($value, row, index) {
	  var iconDettagli = "<a href=\"../cunembo219/index_"+row['idNumeroLotto']+".do\" style=\"text-decoration: none;\"><i class=\"icon-list icon-large\" title=\"Dettaglio estrazione\" style=\"font-size:1.4em;\"></i></a>";

	   return iconDettagli;
}

  </script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>