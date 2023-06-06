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
<style>
	checkbox-field {
		max-width: 37px !important;
		width:36px !important;
		min-width: 36px !important;
		text-align:center;
		padding: 8px 0;
	}
</style>	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-297-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-297-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoScorte">
		<br/>
		<c:set var ="formName"  value ="formVisualizzaScorteMagazzino"/>
		<form name="${formName}" id="${formName}" method="POST" action="../cunembo297m/modifica_scorte.do">
				<c:set var ="tableName"  value ="tableElencoScorte"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->				
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
					data-toggle="table" 
					data-url="get_elenco_scorte.json"
					data-escape-table="true"
					>
					<thead>
			        	<tr>
							<th data-field="idScortaMagazzino" data-formatter="modificaFormatter" class="col-md-1">
							<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="INSERISCI">
									<a href="../cunembo297i/index.do" title="Inserisci"><i class="ico24 ico_add" ></i></a>						
							</p:abilitazione-azione> 
							<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="ELIMINA"> 
								<a onclick="eliminazioneMultipla()" title="Elimina selezionati"><i class="ico24 ico_trash"></i></a> 
							</p:abilitazione-azione>
							<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="MODIFICA"> 
								<a onclick="modificaMultiplaScorte();" title="Modifica selezionati"><i class="ico24 ico_modify"></i></a>
								</p:abilitazione-azione>
						</th>
							<th data-field="idScortaMagazzino"  data-formatter="checkboxFormatter" class="checkbox-field" data-class="checkbox-field"><input type="checkbox" name="selAll" id="selAll" onclick="selectAll();" /></th>
			        		<th data-field="descrizioneScorta" data-sortable="true" class="col-md-2">Tipologia scorta</th>
			        		<th data-field="quantitaUnitaMisuraFormatter" data-sortable="true" data-sorter="dataSorterNumeroUnitaMisura" class="col-md-2">Quantità</th>
			        		<th data-field="descrizione" data-sortable="true" class="col-md-7">Descrizione</th>
			        	</tr>
				    </thead>
		    		<tbody></tbody>
	    		</table>
	    		</form>
	    		<br/>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
			$( document ).ready(function() {
				loadBootstrapTable("${tableName}", "");
			 });
	</script>
		
		<script type="text/javascript">		
		function modificaFormatter($value, row, index)
		{
			var risultato='';
			risultato = risultato +
						'<div class="th-innder">' +
						'<a><i class="ico24"></i></a>';
			risultato = risultato +
						'<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="ELIMINA">' +
							'<a onclick="eliminazioneSingola(' + $value + ');" title="Elimina"><i class="ico24 ico_trash"></i></a>' +
						'</p:abilitazione-azione>';
			risultato =	risultato +
						'<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="MODIFICA">' +
						'<a class="btn-modifica-singola" title="Modifica" onclick="modificaSingolaScorta('+$value+');"><i class="ico24 ico_modify"></i></a>' +	
						'</p:abilitazione-azione>';
			return risultato;
		}
		
		function checkboxFormatter($value, row, index)
		{
			return '<input type="checkbox" name="idScortaMagazzino" value="' + $value + '"></input>';
		}
		</script>
		
		<p:abilitazione-azione codiceQuadro="SCORT" codiceAzione="ELIMINA"> 
		<script type="text/javascript">
		
		function modificaSingolaScorta(idScortaMagazzino)
		{
			openPageInPopup("../cunembo297m/conferma_modifica_scorta_"+idScortaMagazzino+".do", 'dlgModificaScorte', 'Modifica Scorta', 'modal-large', false);
		}
		
		function modificaMultiplaScorte()
		{
			if(isAllCheckboxUnchecked('idScortaMagazzino'))
			{
				showMessageBox('Modifica Scorte', 'Selezionare almeno una scorta da modificare.','modal-large');
			}
			else
			{
				openPageInPopupMethod("../cunembo297m/conferma_modifica_scorte.do",'dlgModificaScorte','Modifica scorte','modal-large',false, $('#${formName}').serialize(),'POST');		
			}
		}
		
		function eliminazioneSingola(idScortaMagazzino)
		{
			openPageInPopup("../cunembo297e/conferma_elimina_scorta_"+idScortaMagazzino+".do",'dlgEliminaScorte','Elimina scorta','modal-large',false);
		}
		
		function eliminazioneMultipla()
		{
			if(isAllCheckboxUnchecked('idScortaMagazzino'))
			{
				showMessageBox('Elimina Scorte', 'Selezionare almeno una scorta da eliminare.','modal-large');
			}
			else
			{
				openPageInPopupMethod("../cunembo297e/conferma_elimina_scorte.do",'dlgEliminaScorte','Elimina scorte','modal-large',false, $('#${formName}').serialize(),'POST');		
			}
		}
		
		function selectAll() 
		{
			//$("input[name='cBP']").prop('checked', checked);
			$("input[name='idScortaMagazzino']").prop("checked",$('#selAll').prop('checked'));
			return false;
		}
		</script>
		</p:abilitazione-azione>
		
		<script type="text/javascript">
		function isAllCheckboxUnchecked(checkboxesName) 
		{
			var allUnchecked = true;
			$("input[name="+checkboxesName+"]").each(function() 
			{
				if ($(this).prop("checked") == true) 
				{
					allUnchecked = false;
				}
			});
			return(allUnchecked);
		}
		</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />