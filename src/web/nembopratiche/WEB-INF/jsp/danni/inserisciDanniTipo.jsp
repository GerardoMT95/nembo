<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
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
	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-298-I" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-298-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaTipologieDanni">
		<br/>
		<form name="formVisualizzaDanni" id="formVisualizzaDanni" method="POST" action="../cunembo298i/inserisci_danno_dettaglio.do" onsubmit="onProsegui();">
			<div class="col-md-12">
			<m:select id="idDanno" list="${listTipologieDanni}" name="idDanno" label="Tipo Danno" onchange="gestisciTipoDanno()" preferRequestValues="${preferRequest}"></m:select>
			</div>
			<div class="col-md-12">
				<br/>
				<div id="errorBoxDiv" class='alert alert-danger col-md-12' style="display:none;">
				<h4>Selezionare almeno un elemento tra quelli in elenco per continuare</h4>
				</div>
			    <div id="dual-list-box1" class="form-group row dualListMultipli">
					<select style="display: none" 
							id="scorteDualList" 
							name="scorteDualList" 
							multiple="multiple" 
							data-title="Scorte" 
							data-sortText = "true" 
							data-horizontal = false   
							data-source="../cunembo297l/get_elenco_scorte_non_danneggiate.json"      
							data-value="idScortaMagazzino" 
							data-text="descrizioneScortaDanno"
							data-toggle="true" 
							class="dualListMultipli">
					</select> 
				</div>
				<div id="dual-list-box2" class="form-group row dualListMultipli">
					<select style="display: none" 
							id="macchineDualList" 
							name="macchineDualList" 
							multiple="multiple" 
							data-title="Macchine" 
							data-sortText = "true" 
							data-horizontal = false   
							data-source="../cunembo301/get_elenco_motori_agricoli_non_danneggiati.json"      
							data-value="idMacchina" 
							data-text="descMacchinaDanno"
							data-toggle="true" 
							class="dualListMultipli">
					</select> 
				</div>
				
				<div id="dual-list-box3" class="form-group row dualListMultipli">
					<select style="display: none" 
							id="fabbricatiDualList" 
							name="fabbricatiDualList" 
							multiple="multiple" 
							data-title="Fabbricati" 
							data-sortText = "true" 
							data-horizontal = false   
							data-source="../cunembo1303l/get_elenco_fabbricati_non_danneggiati.json"      
							data-value="idFabbricato" 
							data-text="descrizioneFabbricatoDanno"
							data-toggle="true" 
							class="dualListMultipli">
					</select> 
				</div>
				
				<div id="dual-list-box4" class="form-group row dualListMultipli">
					<select style="display: none" 
							id="allevamentiDualList" 
							name="allevamentiDualList" 
							multiple="multiple" 
							data-title="Allevamenti" 
							data-sortText = "true" 
							data-horizontal = "false"   
							data-source="../cunembo300l/get_list_allevamenti_singoli_non_danneggiati.json"      
							data-value="idAllevamento" 
							data-text="descrizioneAllevamento"
							data-toggle="true" 
							class="dualListMultipli">
					</select> 
				</div>
			<a class="btn btn-default"  href="../cunembo298l/index.do">Indietro</a>
   			<input type="submit" class="btn btn-primary pull-right"  value="Avanti"/>
			</div>
   		<br/>
   		</form>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script src="../js/dual-list-box.js"></script>
<script src="/nembopratiche/js/nembotableformatter.js"></script>
<script type="text/javascript">
    function gestisciTipoDanno()
    {
    	debugger;
    	var tipoDanno = $('#idDanno').val();
    	$('.dualListMultipli').hide();
    	$('#errorBoxDiv').hide();
    	switch(tipoDanno)
    	{
    		case '1': //allevamenti
    			$('#dual-list-box4').show();
    			$('#allevamentiDualList').show();
    			$('#allevamentiDualList').DualListBox();
    			$('#dual-list-box-Allevamenti').show();
   				break;      		
    		case '2': //fabbricati
    			$('#dual-list-box3').show();
    			$('#fabbricatiDualList').DualListBox();
    			$('#dual-list-box-Fabbricati').show();    		
    		break;
    		
    		case '4': //scorte
    		case '10':
    			$('#dual-list-box1').show();
    			$('#scorteDualList').DualListBox();
    			$('#dual-list-box-Scorte').show();
    		break;
    		case '3': //macchine agricole
    		case '9':
    			$('#dual-list-box2').show();
    			$('#macchineDualList').DualListBox();
    			$('#dual-list-box-Macchine').show();
			break;    			
    		case '5': //piantagioni arboree
    		case '6': //terreni ripristinabili
    		case '7': //terreni non ripristinabili
    		case '11': //altre piantagioni
    			//non visualizzare nulla, seplicemente avanti
    			
    		break;
    		case '8':
    		
    		break;
    		default: //apertura della pagina di inserimento danni
    		break;
    	}
    }
    
    function onProsegui()
    {
    	
    	var tipoDanno = $('#idDanno').val();
 //TODO:   	
    	switch(tipoDanno)
    	{
    		case '1':
    			return inserimentoDanniComplessi(1);
    			
    		case '2':
    			return inserimentoDanniComplessi(2);
    		
    		case '4': //scorte
    		case '10':
    			return inserimentoDanniComplessi(4);
    		break;
    		case '3':
    		case '9':
    			return inserimentoDanniComplessi(3);
    		break;
    		case '5': //piantagioni arboree
    		case '6': //terreni ripristinabili
    		case '7': //terreni non ripristinabili
    		case '11': //altre piantagioni
				
    		break;
    		
    		default: //apertura della pagina di inserimento danni
    		break;
    	}
    }
	
	function inserimentoDanniComplessi(idDanno)
	{
    	var length=1;
    	switch(idDanno)
    	{
	    	
	    	case 1:
	   			var formData = {idAllevamento : new Array(), prosegui:'true' };
		   		$('#dual-list-box-Allevamenti #selectedListHidden option').each(function(index) {
		      		formData.idAllevamenti[index] = $(this).val();
		    	});
	   			length = formData.idAllevamento.length; 		
	    	
	    	case 2:
	    		var formData = {idFabbricato : new Array(), prosegui:'true' };
		   		$('#dual-list-box-Fabbricati #selectedListHidden option').each(function(index) {
		      		formData.idFabbricati[index] = $(this).val();
		    	});
	   			length = formData.idFabbricato.length;
	    	break;
	    	
	    	case 4:
	    	case 10:
		    	var formData = {idScortaMagazzino : new Array(), prosegui:'true' };
		   		$('#dual-list-box-Scorte #selectedListHidden option').each(function(index) {
		      		formData.idScortaMagazzino[index] = $(this).val();
		    	});
				length = formData.idScortaMagazzino.length;
	   		break;
	   		case 3:
	   		case 9:
	   			var formData = {idMacchina : new Array(), prosegui:'true' };
		   		$('#dual-list-box-Macchine #selectedListHidden option').each(function(index) {
		      		formData.idMacchina[index] = $(this).val();
		    	});
	   			length = formData.idMacchina.length;
	   		break;
	   		case 8:
				return true;	   		
	   		break;
    	
    	}
    	if(length == 0)
    	{
    		event.preventDefault();
    		$('#errorBoxDiv').show();
    		return false;
    	}
    	else
    	{
    		$('#errorBoxDiv').hide();
    		return true;
   		}
	}
</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />