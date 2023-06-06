<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
.ammCompClass{min-width: 20em;}
</style>
<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  <p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li class="active">Elenco bandi</li>
				</ul>
            </div>
        </div>           
    </div>
<p:messaggistica/>
	<div class="container-fluid" id="content" style="margin-bottom:3em">
		<h3>Nuova domanda</h3>
      <form:form action="" modelAttribute="filtroAziendeForm" method="post">
				<input type="hidden" id="filtroAziende" value='${filtroAziende.get("elencoAmministrazioni")}' >
			</form:form> 
  			
  	<c:if test="${msgError != null}">
  			<div class="stdMessagePanel"> 
					<div class="alert alert-info">
						<p>
							
							<c:out value="${msgError}"></c:out>
						</p>
					</div>
				</div>
				
				<div class="puls-group" style="margin-top:2em">
				 <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../index.do');"  class="btn btn-default">indietro</button>
			      </div>
			    </div>
  	</c:if>
  			
  	<c:if test="${bandi != null}">		
	<form:form action="" modelAttribute="sceltaBando" method="post">       
         <div class="pageContainer">   
          <c:if test="${msgErrore != null}">
          	<div class="stdMessagePanel">	
                      <div class="alert alert-danger">
                          <p><strong>Attenzione!</strong><br/>
                         <c:out value="${msgErrore}"></c:out></p>
                       </div>
                   </div>
           </c:if>
            <div id="filter-bar" style="position: absolute;height:3em;"> </div>
    		<br><br>
           
           
            <table id="elencoBandi" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh" data-toggle="table"
				data-url="json/getElencoBandi.json" data-show-columns="true" data-show-filter="true" data-undefined-text=''
				data-select-item-name="idBando" data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top"
				data-id-field="idBando">
				<thead>
					<tr>
						<th class="center" data-field="idBando" data-width="130" data-switchable="false" data-formatter="iconeFormatter"></th>
						<th data-field="denominazione" data-sortable="true">Bando</th>
						<th data-field="referenteBando" data-sortable="true" data-formatter="mailFormatter">Referente</th>
						<th data-field="annoCampagna" data-sortable="true">Anno campagna</th>
						<th data-field="dataInizioStr" data-sortable="true">Data apertura</th>
						<th data-field="dataFineStr" data-sortable="true">Data scadenza</th>
						<th data-sortable="true" data-formatter="livelliFormatter">Operazioni</th>
						<th data-field="nomeFile" data-sortable="true" data-formatter="allegatiFormatter">Elenco allegati</th>
						
						<th data-field="elencoCodiciLivelliMisure" data-visible="false" data-switchable="false" ></th>
	        		    <th data-field="elencoCodiciLivelliSottoMisure" data-visible="false" data-switchable="false" ></th>
	        		    <th data-field="elencoCodiciOperazione" data-visible="false" data-switchable="false" ></th>
						<th data-field="elencoAmministrazioni" data-visible="false" data-switchable="false" ></th>
						
						<th data-sortable="true" data-formatter="amministrazioniFormatter">Amm. competenza</th>
					</tr>
				</thead>
			 </table>
            
           
			</div>


			<div class="form-group puls-group" style="margin-top:2em">
			      <div class="pull-left">
			        <button type="button" onclick="forwardToPage('../index.do');"  class="btn btn-default">indietro</button>
			      </div>
			    </div>
			
			<br />
	
		</form:form>
		</c:if>	
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script>
		function iconeFormatter($value, row, index) {
			var html = '';
			html += '<a href=\"../nuovoprocedimento/prosegui_'+row.idBando+'.do\"><i class="icon-list icon-large"></i></a>';
			return html;
		}
		
		function allegatiFormatter($value, row, index) {
			var ret="";
			if(row.allegati!=null)
			{
				for (i = 0; i < row.allegati.length; i++) { 
				    var alleg = row.allegati[i];
				    ret = ret + "<li>"+ ' <a href=\"download_'+alleg.idAllegatiBando+'.do\"  title =\"'+alleg.descrizione+'\">'+alleg.nomeFile+'</a>'+"</li>";
				}
			}
			return ret;

		}

		function mailFormatter($value, row, index) {
			var ret=' <a href=\"mailto:'+row.emailReferenteBando+'.do\"  title =\"'+row.emailReferenteBando+'\">'+row.referenteBando+'</a>';
			return ret;
		}

		function amministrazioniFormatter($value, row, index) {
			var ret="";
			if(row.amministrazioniCompetenza!=null)
			{
				for (i = 0; i < row.amministrazioniCompetenza.length; i++) { 
				    var amm = row.amministrazioniCompetenza[i];
				    ret = ret + "<li>"+amm.descrizione+"</li>";
				}
			}
			return ret;

		}

		function livelliFormatter($value, row, index) {
			var ret="";
			if(row.livelli!=null)
			{
				for (i = 0; i < row.livelli.length; i++) { 
				    var op = row.livelli[i];
				    ret = ret + op.codice+"-"+op.descrizione+"<br>";
				}
			}
			return ret;

		}
		

		$( document ).ready(function() {
			
			$('#filter-bar').bootstrapTableFilter({
			    filters:[
						 {
							field : 'denominazione',
							label : 'Bando',
							type : 'search'
						},  
						{
							field: 'elencoAmministrazioni',   
						    label: 'Amm. Competenza',   
						    type: 'ajaxSelectList',   
		                    source: 'getElencoAmministrazioniCompetenzaJson.do'
						},
						{
		                    field: 'elencoCodiciLivelliMisure',   
		                    label: 'Misura',  
		                    type: 'ajaxSelectList',   
		                    source: 'getElencoCodiciLivelliMisureJson.do'
		                },
		                {
		                    field: 'elencoCodiciLivelliSottoMisure',   
		                    label: 'Sottomisura',  
		                    type: 'ajaxSelectList',   
		                    source: 'getElencoCodiciLivelliSottoMisureJson.do'
		                },
		                {
		                    field: 'elencoCodiciOperazione',   
		                    label: 'Operazione',  
		                    type: 'ajaxSelectList',   
		                    source: 'getElencoCodiciOperazioneJson.do'
		                }
						],
				        connectTo: "#elencoBandi",
				        onSubmit: function() {
				        	var data = $('#filter-bar').bootstrapTableFilter('getData');
				            var elabFilter = JSON.stringify(data);
							//reset variabili per ricostruire correttamente la tabella
						
				            $.ajax({
				            	  type: "POST",
				            	  async: false,
				            	  url: '../session/salvaFiltri.do',
				            	  data: "key=elencoBandi&filtro="+elabFilter,
				            	  success: function( data ){
				            	  }
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
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
