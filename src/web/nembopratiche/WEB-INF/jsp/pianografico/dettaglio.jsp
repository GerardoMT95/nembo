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
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-285-V" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-285-V" />
		
	 		
	<div class="container-fluid" id="content">
		<m:panel id="panelPianoGraficoHome">
			<p:wizardPianoGraficoTag activeStep="${currentStepWizard}"></p:wizardPianoGraficoTag>


			<c:if test="${codicePianoGrafico == 'R' }">
				<div class="col-sm-12" style="margin-top:3em;margin-bottom: 2em;">
					<div class="alert alert-warning">
						<label>Attenzione! Domanda grafica in fase di creazione.</label>
					</div>
				</div>
			</c:if>

			<!-- sezione azioni - in base a esito mostro le possibili azioni -->
			<div style="margin-top:10em;margin-bottom: 2em;">
				<div class="puls-group" style="margin-top:2em">
					<c:if test="${btnCreazione}">
			        	<button type="button" onclick="forwardToPage('../cunembo285n/index.do');"  class="btn btn-primary">Crea parcelle grafiche</button>
			      	</c:if> 
					<c:if test="${btnConsultaModifica}">
			        	<button type="button" onclick="consultaPianoGrafico();"  class="btn btn-primary">Consulta / Modifica parcelle grafiche</button>
			      	</c:if> 
					<c:if test="${btnSalvaImporta}">
			        	<button type="button" onclick="forwardToPage('../cunembo285a/index.do');"  class="btn btn-primary">Salva / Importa parcelle grafiche</button>
			      	</c:if> 
					<c:if test="${btnElimina}">
			        	<button type="button" onclick="forwardToPage('../cunembo285e/index.do');"  class="btn btn-primary">Elimina parcelle grafiche</button>
			      	</c:if> 
					<c:if test="${btnConsulta}">
			        	<button type="button" onclick="consultaPianoGrafico();"  class="btn btn-primary">Consulta parcelle grafiche</button>
			      	</c:if> 
					<c:if test="${btnSblocca}">
			        	<button type="button" onclick="forwardToPage('../cunembo285s/index.do');"  class="btn btn-primary">Riapri modifica domanda grafica</button>
			      	</c:if> 
			    </div>
			    <div style="clear: both;"></div>
			</div>

			

			<div style="margin-top:3em">
				<m:panel id="panelRiepilogoImpegni" title="Riepilogo Impegni" collapsible="true">
					<table id="tblMappeFile" summary="Riepilogo Impegni" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
						data-url="../cunembo285v/elencoImpegni.json" 
						data-undefined-text=''>
						<thead>
							<tr>
								<th data-field="codLivello">Operazione</th>
								<th data-field="impegnoBase" data-formatter="impegnoBaseFormatter">Impegni di base</th>
								<th data-field="impegnoAggiuntivo" data-formatter="impegnoAggiuntivoFormatter">Impegni aggiuntivi</th>
							</tr>
						</thead>
					</table>
				</m:panel>
			</div>
			
			<c:if test="${codicePianoGrafico == 'I' }">
				<div style="margin-top:3em">
					<m:panel id="panelRiepilogoParticelle" title="Riepilogo interventi con superficie impegno" collapsible="true">
							<table id='tblElencoParticelle' summary="Elenco Partcelle Grafiche" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
								 data-undefined-text=''  data-show-columns="true">
									<thead>
										<tr>
											<th data-field="codiceIntervento" >Cod. Intervento</th>
											<th data-field="descrizioneIntervento" >Desc. Intervento</th>
											<th data-field="supePrsu" >Superficie impegno</th>
											<th data-field="supePrsuTotale" >Sup. Predisposizione impegno particella</th>
										</tr>
									</thead>
									
									<tbody>
									 <c:forEach items="${interventiImpegno}" var="i">
									 	<c:forEach items="${i.interventi}" var="f">
									 		<tr>
									 			<td><c:out value="${f.codiceIntervento}"></c:out></td>
									 			<td><c:out value="${f.descrizioneIntervento}"></c:out></td>
									 			<td><c:out value="${f.supInterventoStr}"></c:out></td>
									 			<td></td>
									 		</tr>
									 	</c:forEach>
									 	<tr>
									 		<td></td>
									 		<td><b>Totale (<c:out value="${i.descrImpegno}"></c:out>)</b></td>
									 		<td><b><c:out value="${i.supImpegnoStr}"></c:out></b></td>
									 		<td><b><c:out value="${i.supPredisposizImpegnoStr}"></c:out></b></td>
									 	</tr>
									 </c:forEach>
									
									</tbody>
									
							</table>
					</m:panel>
				</div>
			</c:if>
		</m:panel>
					<div class="container-fluid">
				<table
					class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="10%">
						<col width="90%">
					</colgroup>
					<tbody>
						<tr>
							<th>Ultima modifica</th>
							<td><c:out value="${ultimaModifica}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>
	</div>			
	
	
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">

	$( document ).ready(function() {
		$('#tblElencoParticelle').bootstrapTable();	
	});
	
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

	
	 function consultaPianoGrafico()
	 {
	 	$.ajax({
            type: "GET",
            url: "getConsultaPianoGraficoUrl.do",
            async:false,
            success: function(data) {
            	 var w=900; 
	       		 var h=700;
	       		 window.open(data,"piano_grafico",'width='+w+',height='+h+',location=no,directories=no,menubar=no,resizable=yes,titlebar=no,toolbar=no,scrollbar=yes');
	       		 return false;
            }
        }); 
	 }
	
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />