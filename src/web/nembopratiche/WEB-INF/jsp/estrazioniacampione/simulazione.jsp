<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo217/index.do">Gestione Estrazioni</a> <span class="divider">/</span></li>
					<li class="active">Simulazione</li>
				</ul>
			</div>
		</div>
	</div>
	   
	   <div class="container-fluid" style="padding-top:2em">
	
		<m:panel id="panelRicercaSimulazione" title="seleziona tipo estrazione per simulazione">
			<form:form action="" modelAttribute="" method="post"
					class="form-horizontal" style="margin-top:2em">
				<m:select label="Tipo estrazione *" name="idTipoEstrazione"
						list="${listTipoEstrazione}" id="idTipoEstrazione"
						valueProperty="id"
						textProperty="label"
						preferRequestValues="${prfvalues}"/>	
						
				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button"
							onclick="history.go(-1);"
							class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma"
							class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>			
			</form:form>
		</m:panel>
	
		<c:if test="${visualizzaRisultati != null}">
		<m:panel id="panelrisSimulazione" title="risultati">
			<div id="filter-bar" > </div>	 
			<table id="elencoEstrazioniTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
			  data-toggle="table" 
			  data-undefined-text = ''
			  data-url="getElencoRisultatiSimulazione_${idTipoEstrazione}.json"
		      data-pagination="true" 
			  data-show-pagination-switch="true" 
			  data-show-toggle="true"
			  data-pagination-v-align="top" 
			  data-show-columns="true"
			  data-show-filter="true"
			  >
			<thead>
	        	<tr>
	        		<th data-field="idProcedimentoOggetto" data-visible ="false"></th>
					<th data-field="descrEnteDelegato" data-sortable = "true">Ente Delegato</th>
					<th data-field="listLivelliHtml" data-sortable="true" >Operazione</th>
					<th data-field="identificativo" data-sortable = "true" >Num. domanda</th>
					<th data-field="descrTipoDomanda" data-sortable = "true" >Tipo domanda</th>
					<th data-field="descrStato" data-sortable = "true" >Stato</th>
					<th data-field="cuaAzie" data-sortable = "true" >CUAA</th>
					<th data-field="denominazioneAzie" data-sortable = "true" >Denominazione</th>
	        	</tr>
		    </thead>
		    <tbody></tbody>
		    </table>
		   </m:panel> 
	    </c:if>
		
	    </div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>	
<script type="text/javascript">
$( document ).ready(function() {
	$('#estrazioniTable').bootstrapTable();	
		
	$('#filter-bar').bootstrapTableFilter({
      filters:[
			{
			    field: 'idTipoEstrazione',    
			    label: 'Tipo estrazione',   
			    type: 'ajaxSelectList',   
			    source: 'getElencoTipologieFiltroEstrCampioneJson.do'
			},
			{
			    field: 'idStatoEstrazione',    
			    label: 'Stato estrazione', 
			    type: 'ajaxSelectList',   
			    source: 'getElencoStatoEstrCampione.do'
			}
      ],
      connectTo: '#estrazioniTable',
      onSubmit: function() {
    	  var data = $('#filter-bar').bootstrapTableFilter('getData');
          var elabFilter = JSON.stringify(data);
      }
  });
	var filterJSON = $('#filtroEstrazioni').val();
	$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	
});


function showDetails($value, row, index) {
	if(row['flagEstratta']!="N")
	  var iconDettagli = "<a href=\"dettaglio_"+row['idEstrazioneCampione']+".do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_magnify\" title=\"Dettagli estrazione\" style=\"font-size:1.4em;\"></i></a>";

	   return iconDettagli;
}

  </script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>