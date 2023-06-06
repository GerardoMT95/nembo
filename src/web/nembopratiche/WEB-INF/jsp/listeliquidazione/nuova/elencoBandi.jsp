<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo227/index.do">Liste di liquidazione</a><span class="divider">/</span></li>
					<li class="active">Nuova lista di liquidazione</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	
	<form:form action="" modelAttribute="filtroAziendeForm" method="post">
	<input type="hidden" id="filtroAziende" value='${filtroAziende.get("listeLiquidazione")}' >	
	</form:form> 
	
	<div class="container-fluid" id="content">
		<div id="filter-bar" style="position: relative; top: 46px"></div>
		<table id='listeLiquidazione' summary="Liste di liquidazione" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
			data-url="json/elenco.json" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top" data-show-columns="true"
			data-undefined-text='' data-row-style='rowStyleFormatter'>
			<thead>
				<tr>
					<th class="vcenter center" data-field="idBando" data-formatter="iconeFormatter" data-switchable="false"></th>
					<th class="vcenter center" data-class="" data-field="denominazioneBando" data-visible="true">Denominazione</th>
					<th class="vcenter center" data-class="" data-field="referenteBando" data-visible="true">Referente bando</th>
					<th class="vcenter center" data-class="center" data-field="annoCampagnaStr" data-visible="true">Anno di rif.</th>
					<th class="vcenter center" data-class="" data-field="dataInizioBando" data-visible="true">Data apertura</th>
					<th class="vcenter center" data-class="" data-field="descTipoBando" data-visible="true">Tipo bando</th>
					<th class="vcenter center" data-class="" data-field="codiciLivelloHtml" data-visible="true">Operazione</th>	
					<th class="vcenter center" data-class="" data-field="codiciLivelloForFilter" data-visible="false" data-switchable="false">Operazione</th>					
									
				</tr>
			</thead>
		</table>
		<br />
	</div>
	<br />
	<div class="col-sm-12">
		<a href="../cunembo227/index.do" class="btn  btn-default pull-left">indietro</a>
	</div>
  <br />
  <br />
  <br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
    function iconeFormatter($value, row, index)
    {
      var html = '<a href="elenco_operazioni_bando_'+$value+'.do"><i class="ico24 ico_add"></i></a>';
      return html;
    }

	$( document ).ready(function() {
		
		$('body').on( "column-switch.bs.table", function(obj, field) {
			  var value = $('input[data-field="'+field+'"]').prop("checked");
			  $.ajax({
          	  type: "POST",
          	  url: '../session/salvaColonna.do',
          	  data: "key=listeLiquidazione&field="+field+"&value="+value,
          	  success: function( data ){}
          	});
			});
		
		/*Mantengo in sessione il numero di pagina dell'elenco*/
		$('body').on("page-change.bs.table", function(obj, size) {
			$.ajax({
            	  type: "POST",
            	  url: '../session/salvaNumeroPagina.do',
            	  data: "key=listeLiquidazione&page="+size,
            	  success: function( data ){}
            	});
		});

		$('#listeLiquidazione').bootstrapTable();	

			
		$('#filter-bar').bootstrapTableFilter({
            filters:[
				{
				    field: 'denominazioneBando',   
				    label: 'Denominazione',  
				    type: 'search'   
				},
				{
				    field: 'annoCampagnaStr',   
				    label: 'Anno di riferimento',  
				    type: 'search'   
				},				
				{
				    field: 'descTipoBando',   
				    label: 'Tipo bando',  
				    type: 'selectNoSearch',
				    values: [
				             {id: 'Premio', label: 'Premio'},
				             {id: 'Investimento', label: 'Investimento'}
				         ],
				},
                {
                    field: 'codiciLivelloForFilter',   
                    label: 'Operazione',  
                    type: 'ajaxSelectList',   
                    source: '../elencoBandi/getElencoCodiciOperazioneJson.do'
                }
            ],
            connectTo: '#listeLiquidazione',
            onSubmit: function() {
            	var data = $('#filter-bar').bootstrapTableFilter('getData');
       
                var elabFilter = JSON.stringify(data);
                $.ajax({
                	  type: "POST",
                	  url: '../session/salvaFiltri.do',
                	  data: "key=listeLiquidazione&filtro="+elabFilter,
                	  success: function( data ){}
                	});
             
                console.log(data);
            }
        });
		
		var filterJSON = $('#filtroAziende').val();
		if(filterJSON)
			$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
	});
	
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />