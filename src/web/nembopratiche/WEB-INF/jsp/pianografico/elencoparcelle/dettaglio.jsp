<%@page import="it.csi.nembo.nembopratiche.presentation.taglib.nembopratiche.WizardPianoGraficoTag"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-288-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-288-L" />
		
	 		
	<div class="container-fluid" id="content">
	
	<m:panel  id="elencoParticelleGrafichePanel" >
	
	<div id="filter-bar" > </div>
		<table id='tblElencoParticelle' summary="Elenco Partcelle Grafiche" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
			data-show-filter="true" 
			data-pagination="true"
			data-side-pagination = "client"
			data-pagination-v-align="top"
			data-page-size = "50" 
			data-url="elencoParticelle.json" data-undefined-text=''  data-show-columns="true">
				<thead>
					<tr>
						<th data-field="istatComune" data-visible="false" data-switchable="false"></th>
						<th data-field="idInterventoGrafico" data-visible="false" data-switchable="false"></th>
						<th data-field="idClassePremio" data-visible="false" data-switchable="false"></th>
						<th data-field="idParticelleGrafico" data-visible="false" data-switchable="false"></th>
						<th data-field="numAnomalie" data-formatter="anomaliaFormatter" data-switchable="false" class="vcenter center"><a href="#"
							onClick="forwardToPage('elencoParticelleExcel.xls');" style="text-decoration: none;"><i class="ico24 ico_excel"
							title="Esporta dati in Excel"></i></a></th>
						<th data-field="idAnomalieHtml" data-visible="false" data-switchable="false"></th>
						<th data-field="descComune" >Comune</th>
						<th data-field="sezione" >Sz</th>
						<th data-field="foglio" >Fgl</th>
						<th data-field="particella" >Part</th>
						<th data-field="subalterno" data-visible="false">Sub</th>
						<th data-field="zonaAltimetricaPart" >Zona Alt.</th>
						<th data-field="codiceUtilizzo" >Cod. Suolo</th>
						<th data-field="descrizioneUtilizzo" >Occupazione del suolo</th>
						<th data-field="codiceDestinazione"  data-visible="false">Cod. Dest</th>
						<th data-field="descrizioneDestinazione"  data-visible="false">Destinazione</th>
						<th data-field="codiceDettaglioUso"  data-visible="false">Cod Uso</th>
						<th data-field="descrizioneDettaglioUso"  data-visible="false">Uso</th>
						<th data-field="codiceQualitaUso"  data-visible="false">Cod. Qualit&agrave;</th>
						<th data-field="descrizioneQualitaUso"  data-visible="false">Qualit&agrave;</th>
						<th data-field="codiceVarieta"  data-visible="false">Cod. Var.</th>
						<th data-field="descrizioneVarieta"  data-visible="false">Variet&agrave;</th>
						<th data-field="idIsol"  data-visible="false">Id Isola</th>
						<th data-field="idParcAgri"  data-visible="false">Id Parcella</th>
						<th data-field="idAppe"  data-visible="false">Id Appezzamento</th>
						<th data-field="supePrsuLorda" data-formatter="numberFormatter4" data-visible="false">Superficie utilizzata</th>
						<th data-field="supePrsu" data-formatter="numberFormatter4">Superficie impegno</th>
						<th data-field="codiceIntervento" data-visible="false">Cod. Intervento</th>
						<th data-field="descrizioneIntervento"   >Desc. Intervento</th>
						<th data-field="codicePremio" data-visible="false">Cod. Classe premio</th>
						<th data-field="descrPremio"  >Desc. Classe premio</th>
						<th data-field="superficiePremio"  data-formatter="numberFormatter4">Superficie premio</th>
					</tr>
				</thead>
		</table>
		</m:panel>
	</div>			
	<br><br>
	
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

	<script type="text/javascript">

	$( document ).ready(function() {
		$('#tblElencoParticelle').bootstrapTable();	

		$('#filter-bar').bootstrapTableFilter({
            filters:[
					{
					    field: 'istatComune',   
					    label: 'Comune',  
					    type: 'ajaxSelect',   
                      source: 'getElencoComuni.do'
					},
					{
					    field: 'sezione',   
					    label: 'Sezione',  
					    type: 'search'   
					},
					{
					    field: 'foglio',   
					    label: 'Foglio',  
					    type: 'search'   
					},
					{
					    field: 'idAnomalieHtml',   
					    label: 'Elenco anomalie',  
					    type: 'ajaxSelect',   
                        source: 'getElencoAnomalie.do'
					},
					{
					    field: 'particella',   
					    label: 'Particella',  
					    type: 'search'					
					},
					{
					    field: 'idInterventoGrafico',   
					    label: 'Cod. Intervento',  
					    type: 'ajaxSelect',   
                      source: 'getElencoCodInterventi.do'
					},
					{
					    field: 'descrizioneIntervento',   
					    label: 'Desc. Intervento',  
					    type: 'search'				
					},
					{
					    field: 'idClassePremio',   
					    label: 'Cod. Classe premio',  
					    type: 'ajaxSelect',   
                      source: 'getElencoCodClassiPremio.do'
					},
					{
					    field: 'decrPremio',   
					    label: 'Desc. Classe premio',  
					    type: 'search'			
					},
					{
					    field: 'idParcAgri',   
					    label: 'Parcella',  
					    type: 'ajaxSelect',   
	                      source: 'getElencoParcelle.do'			
					},
					{
					    field: 'idAppe',   
					    label: 'Appezzamento',  
					    type: 'ajaxSelect',   
	                      source: 'getElencoAppezzamenti.do'			
					}
					
            ],
            connectTo: '#tblElencoParticelle',
            onSubmit: function() {
                var data = $('#filter-bar').bootstrapTableFilter('getData');
                var elabFilter = JSON.stringify(data);
                $.ajax({
                	  type: "POST",
                	  url: '../session/salvaFiltri.do',
                	  data: "key=elencoParticelle&filtro="+elabFilter,
                	  success: function( data ){}
                	});
                console.log(data);
            }
        });
		var filterJSON = $('#filtroParticelle').val();
		$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON",filterJSON);



		
	});

	function anomaliaFormatter($value, row, index) {
		if (row['numAnomalie']!=null && row['numAnomalie']>0)
			return '<div style="text-align:center;cursor:pointer"><img title="Visualizza anomalie" src="../img/../img/ico_cancel.gif" onclick="vedianomalie('+row['idParticelleGrafico']+');"></div>';
		else
			return "";
	}

	function vedianomalie(idParticelleGrafico) {
	      openPageInPopup('../cunembo288l/anomalie_'+idParticelleGrafico+'.do', 'dlgInserisci', 'Elenco Anomalie', 'modal-lg',false);
	      return false;
			}
	
	function postIdIntervento()
	{
		$('#idInterventoSelezionato').val( $('#idInterventoCombo').val() );
		$('#riepilogoPartForm').submit();
	} 
	
	function impegnoBaseFormatter($value, row, index)
    {
	  var ret="";  
      var impList = row['impegniBaseList'];
      if(impList!=null)
      {
    	  impList.forEach(function(entry) {
        	  	if(entry.checked)
    		    	ret = ret + entry.descrizioneEstesa +"<br>";
    		});
      }
      return ret;
    }
	function impegnoAggiuntivoFormatter($value, row, index)
    {
	  var ret="";  
      var impList = row['impegniAggiuntiviList'];
      if(impList!=null)
      {
    	  impList.forEach(function(entry) {
    		  	if(entry.checked)
    		    	ret = ret + entry.descrizioneEstesa +"<br>";
    		});
      }
      return ret;
    }



    
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />