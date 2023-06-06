<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
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
					<li><a href="../elencoBandi/visualizzaBandi.do">Elenco bandi</a> <span class="divider">/</span></li>
					<li class="active">Elenco report</li>
				</ul>
			</div>
		</div>
	</div>
	
	<p:navTabsElencoBandiTag activeTab="<%=NavTabsElencoBandiTag.TABS.REPORT.toString() %>"></p:navTabsElencoBandiTag>
	<div class="container-fluid"  id="content" style="margin-bottom:2em">
		<m:panel id="panelReport" cssClass="navpanel">
	
	
	<form:form action="" modelAttribute="filtroReportForm" method="post">
	<input type="hidden" id="filtroReport" value='${filtroAziende.get("dettEstrazioneTable")}' >
	</form:form> 
		
	
	<div class="container-fluid"  id="content" style="margin-bottom:2em">
			
		<h3>Elenco report disponibili</h3>
		<div id="filter-bar" style="position:absolute;top:340px"> </div>	
		<table id="dettEstrazioneTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  data-toggle="table" 
		  data-undefined-text = ''
		  data-url="getElencoEstrazioni.json"
	      data-pagination="true" 
		  data-show-pagination-switch="true" 
		  data-show-toggle="true"
		  data-pagination-v-align="top" 
		  data-show-columns="true"
		  data-show-filter="true"
		  >
		<thead>
        	<tr>
				<th data-switchable="false" data-field="id" data-formatter="idFormatter"></th>
				<th data-field="codice">Descrizione</th>
				<th data-field="descrizione">Info aggiuntive</th>
        	</tr>
	    </thead>
	    <tbody></tbody>
	    </table>
		
		<div class="form-group puls-group" style="margin-top:2em;margin-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-primary">Indietro</button>
			</div>
	    </div>
	</div>
</m:panel>
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
		
		$('#dettEstrazioneTable').bootstrapTable(
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
				    field: 'codice',    
				    label: 'Descrizione',   
				    type: 'search'
				},
				{
				    field: 'descrizione',    
				    label: 'Info aggiuntive',   
				    type: 'search'
				}

	      ],
	      connectTo: '#dettEstrazioneTable',
	      onSubmit: function() {
	        	var data = $('#filter-bar').bootstrapTableFilter('getData');
	            var elabFilter = JSON.stringify(data);
	            $.ajax({
	            	  type: "POST",
	            	  url: '../session/salvaFiltri.do',
	            	  data: "key=dettEstrazioneTable&filtro="+elabFilter,
	            	  success: function( data ){}
	            	});
	            console.log(data);
	      }
	  });

		var filterJSON = $('#filtroReport').val();
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
		
	});

    function idFormatter($value, row, index)
    {
      var href = 'visualizza_report_' + row['id'] + '.do';
      return '<a href=\"'+href+'\" style=\"text-decoration: none;\"><i class=\"icon-list icon-large\" title=\"Visualizza Report\"></i></a>';
    }
  </script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>