<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.NavTabsElencoBandiTag"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="visualizzaBandi.do">Elenco bandi</a> <span class="divider">/</span></li>
					<li class="active">Elenco graduatorie</li>
				</ul>
			</div>
		</div>
	</div>
		
	<form:form action="" modelAttribute="filtroGraduatorieForm" method="post">
	<input type="hidden" id="filtroGraduatorie" value='${filtroAziende.get("graduatorieTable")}' >
	</form:form> 
	
	<p:navTabsElencoBandiTag activeTab="<%=NavTabsElencoBandiTag.TABS.GRADUATORIE.toString() %>"></p:navTabsElencoBandiTag>
	<div class="container-fluid"  id="content" style="margin-bottom:2em;">
		<m:panel id="panelReport" cssClass="navpanel" >
		<h3>Elenco graduatorie</h3>
	
	<div class="container-fluid" style="padding-top:2em">
		<div id="filter-bar" style="position:absolute;top:316px"> </div>	 
		<table id="graduatorieTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  data-toggle="table" 
		  data-undefined-text = ''
		  data-url="getElencoGraduatorie.json"
	      data-pagination="true" 
		  data-show-pagination-switch="true" 
		  data-show-toggle="true"
		  data-pagination-v-align="top" 
		  data-show-columns="true"
		  data-show-filter="true"
		  >
		<thead>
        	<tr>
				<th data-field="descrizione">Descrizione</th>
				<th data-field="dataApprovazioneStr">Data approvazione</th>
				<th data-field="elencoAllegati" data-formatter="allegatiFormatter">Allegati</th>	
        	</tr>
	    </thead>
	    <tbody></tbody>
	    </table>
		</div>
		<div class="form-group puls-group" style="margin-top:2em;padding-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-primary">Indietro</button>
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
				    field: 'descrizione',    
				    label: 'Descrizione',   
				    type: 'search'
				}
	      ],
	      connectTo: '#graduatorieTable',
	      onSubmit: function() {
	    	  var data = $('#filter-bar').bootstrapTableFilter('getData');
	            var elabFilter = JSON.stringify(data);
	            $.ajax({
	            	  type: "POST",
	            	  url: '../session/salvaFiltri.do',
	            	  data: "key=graduatorieTable&filtro="+elabFilter,
	            	  success: function( data ){}
	            	});
	            console.log(data);
	      }
	  });
		var filterJSON = $('#filtroGraduatorie').val();
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);
		
	});

	 function allegatiFormatter($value, row, index)
	    {
	      var allegati = row['elencoAllegati'];
	      
	      var ret='<ul style=\"padding-left: 2em;\">';
	      var i =0;

	      if(allegati!=null)
	      {
		      for (i=0;i<allegati.length;i++)
		      	ret = ret.concat('<li><a href=\"downloadgraduatoria_'+allegati[i].idAllegatiBando+'.do\" title = '+allegati[i].nomeFile+'>'+allegati[i].descrizione+'</a></li><br>');
	      }
	      ret = ret.concat('</ul>');
	      return ret;
	   
	    }

  </script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>