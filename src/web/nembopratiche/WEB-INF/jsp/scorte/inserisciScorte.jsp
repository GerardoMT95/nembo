<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
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
	<p:breadcrumbs cdu="CU-NEMBO-297-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-297-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoScorte">
		<form action="../cunembo297i/inserisci_scorte.do" method="post" onsubmit="unitaDiMisuraToHidden()"> 
			<br/>
			<div class="col-md-8">
				<m:select id="idScorta" list="${elencoTipologieScorte}" name="idScorta" label="Tipologia" onchange="aggiornaUnitaMisura()" preferRequestValues="${preferRequest}"></m:select>
			</div>
			<div class="col-md-8" id="divDescrizione" style="display:none;"> 
				<m:textfield id="descrizione" name="descrizione" label="Descrizione" preferRequestValues="${preferRequest}" ></m:textfield>
			</div>
			<div class="col-md-8">
				<m:textfield id="quantita" name="quantita" label="Quantità" preferRequestValues="${preferRequest}"></m:textfield>
			</div>
			<div class="col-md-4">
				
				<div class="col-md-12">
				<m:select id="unitaDiMisura" list="${elencoUnitaMisura}" name="unitaDiMisura" label="Unità di Misura" disabled="true" preferRequestValues="${preferRequest}"></m:select>
				<m:textfield name="unitaDiMisuraHidden" id="unitaDiMisuraHidden"  preferRequestValues="${preferRequest}" style="display:none;"></m:textfield> 
				</div>
			</div>
			
				<div class="col-md-8">
					<a class="btn btn-default" href="../cunembo297l/index.do">Indietro</a>
				</div>
				
				
				<div class="col-md-4">
					<div class="col-md-12">
							<div class="col-sm-9"></div>
							<div class="col-sm-3">
								<input class="btn btn-primary pull-right" type="submit" value="Inserisci"></input>
							
							</div>
					</div>
				</div>
			
		</form>	
		</m:panel>
	</div>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	
	<script>
//TODO: FIXME:	eventuale futura modifica al DB	 
		 var idScortaAltro="${idTipologiaAltro}";
		 $(document).ready( function(){
		 	var idScortaVal = $('#idScorta').val();
		 	if(idScortaVal != '' && idScortaVal == idScortaAltro && "${preferRequest}"=='true')
		 	{
		 		$('#unitaDiMisura').prop('disabled', false);
				$('#divDescrizione').show();		
		 	}
		 	else
		 	{
			 	var um = $('#unitaDiMisuraHidden').val();
			 	$('#unitaDiMisura').val(um);
			 	var um2 = $('#unitaDiMisura').val();
		 	}
		 	
		 });
		 
		 function aggiornaUnitaMisura()
		 {
		    var idScortaVal = $('#idScorta').val();
		    if(idScortaVal != null && idScortaVal != '' && idScortaVal!=""){
			    var idUnitaMisuraVal = 0;
	   		 	$.ajax(
			         {
			           type : "GET",
			           url : '../cunembo297i/get_unita_misura_by_scorta_' + idScortaVal + '.do',
			           dataType : "json",
			           async : false,
			           success : function(data)
			           {
			         		if(data != null)
			         		{
				         		idUnitaMisuraVal = data;
				         		$('#unitaDiMisura').val(idUnitaMisuraVal);
				         		$('#unitaDiMisura').prop('disabled', true);
				         		$('#divDescrizione').hide();
			         		}
			         		else
			         		{
			         			$('#unitaDiMisura').val('');
			         			$('#unitaDiMisura').prop('disabled', false);
			         			$('#divDescrizione').show();
			         		}
			         		
							//TODO: FIXME: da modificare con eventuale modifica al DB
			         		if(idScortaVal != '' && idScortaVal == idScortaAltro){
			         			$('#unitaDiMisura').val('');
			         			$('#unitaDiMisura').prop('disabled', false);
			         			$('#divDescrizione').show();		
			         		}
	         		
			           }
			         });
		    }
		    else
		    {
		    	$('#unitaDiMisura').val('');
		    	$('#unitaDiMisura').prop('disabled', true);
		    	$('#divDescrizione').hide();
		    }
		 } 
		 
		 function unitaDiMisuraToHidden()
		 {
		 	var um = $('#unitaDiMisura').val();
		 	$('#unitaDiMisuraHidden').val(um);
		 	var um2 = $('#unitaDiMisuraHidden').val();
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