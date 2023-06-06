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
	<p:breadcrumbs cdu="CU-NEMBO-302-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-302-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaPrestitiAgrari">
		<br/>
		<c:set var ="tableName"  value ="tableElencoPrestitiAgrari"/>
		<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
		<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
		<form id="formVisualizzaPrestitiAgrari" name="formVisualizzaPrestitiAgrari" method="POST" action="../cunembo${cuNumber}m/modifica_prestiti_agrari_multipla.do">
		<table id="${tableName}" 
			class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
			data-toggle="table"
			data-url="visualizza_prestiti_agrari.json"
			data-pagination="true" 
			data-only-info-pagination="true"
			data-show-pagination-switch="true" 
			data-show-columns="true"
			data-show-default-order-button="true"
			
			data-pagination-v-align="top"
			data-page-list="[10, 25, 50, 70, 100]"
			data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
			data-default-order-column="${defaultOrderColumn}"
			data-default-order-type="${defaultOrderType}"
			data-table-name="${tableName}"
			data-sort-name="${sessionMapNomeColonnaOrdinamento.get(tableName) != null ? 
				sessionMapNomeColonnaOrdinamento.get(tableName) : 
				defaultOrderColumn}"
			data-sort-order="${sessionMapTipoOrdinamento.get(tableName) != null ? sessionMapTipoOrdinamento.get(tableName) : defaultOrderType}"
			data-page-number="${sessionMapNumeroPagina.get(tableName) != null ? sessionMapNumeroPagina.get(tableName) : 1}"
			data-escape-table="true"
		>
			<thead>
				<tr>
					
					<th data-field="idPrestitiAgrari" data-formatter="modificaFormatter"  data-switchable="false" class="col-md-1">
						<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="INSERISCI">
								<a href="../cunembo302i/index.do" title="Inserisci"><i class="ico24 ico_add"></i></a>
						</p:abilitazione-azione>
						<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="ELIMINA"> 
								<a onclick="eliminazioneMultipla()" title="Elimina selezionati"><i class="ico24 ico_trash"></i></a> 
						</p:abilitazione-azione>
						<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="MODIFICA"> 
								<a onclick="verificaModificaMultipla('formVisualizzaPrestitiAgrari','idPrestitiAgrari','Modifica Prestiti','Selezionare almeno un prestito da modificare.','modal-large')" title="Modifica selezionati"><i class="ico24 ico_modify"></i></a> 
						</p:abilitazione-azione>
					</th>
					<th data-field="idPrestitiAgrari" data-switchable="false" data-formatter="checkboxFormatter" width="36px" class="checkbox-field">
						<input type="checkbox" name="selAll" id="selAll" onclick="selectAll('idPrestitiAgrari','selAll');"/>
					</th>
					<th data-field="finalitaPrestito" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'idDanno')}" class="col-md-4">Finalit&agrave;<br/>del prestito</th>
					<th data-field="istitutoErogante" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'tipoDanno')}" class="col-md-3">Istituto di credito<br/>erogante</th>
					<th data-field="importo" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'idScorta')}" data-formatter="numberFormatter2" data-adder="importoAdder" data-totale="true" class="col-md-2">Importo in<br/>scadenza (&euro;)</th>
					<th data-field="dataScadenzaFormatted" data-sortable="true" data-sorter="dateSorterddmmyyyy" data-visible="${!colonneNascoste.visible(tableName,'denominazione')}" class="col-md-2">Data<br/>scadenza</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		</form>
		<br/>
		Ultima modifica: ${ultimaModifica}
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
			loadBootstrapTable('${tableName}', '');
		 });

		function importoAdder(rows, field) {
			var __sum = 0;
			$(rows).each(function(index, currentRow)
			{
				var value = currentRow[field];
				if (value != null && !isNaN(value) && value.length != 0)
				{
					__sum += parseFloat(value);
				}
			});
			return __sum;
		}
		
		function modificaFormatter($value, row, index)
		{
			var risultato='';
			risultato = risultato +
						'<div class="th-innder">' +
						'<a><i class="ico24"></i></a>';
			risultato = risultato +
						'<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="ELIMINA">' +
							'<a onclick="eliminazioneSingola(' + $value + ')" title="Elimina"><i class="ico24 ico_trash"></i></a>' +
						'</p:abilitazione-azione>';
			risultato =	risultato +
						'<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="MODIFICA">' +
						'<a href="../cunembo302m/modifica_prestito_agrario_' + $value + '.do" title="Modifica"><i class="ico24 ico_modify"></i></a>' +
						'</p:abilitazione-azione>';
			return risultato;
		}
		
		function checkboxFormatter($value, row, index)
		{
			var risultato =	
						'<p:abilitazione-azione codiceQuadro="PRAGR" codiceAzione="MODIFICA">' +
						'<input type="checkbox" name="idPrestitiAgrari" value="' + $value + '"></input>';
						'</p:abilitazione-azione>';
			return risultato;	
		}
		
		function eliminazioneSingola(idPrestitiAgrari)
		{
			openPageInPopup("../cunembo302e/conferma_elimina_prestito_"+idPrestitiAgrari+".do",'dlgEliminaPrestiti','Elimina prestiti','modal-large',false);
		}
		
		function eliminazioneMultipla()
		{
			var checkboxName='idPrestitiAgrari';
			if(isAllCheckboxUnchecked(checkboxName))
			{
				showMessageBox('Elimina Prestiti', 'Selezionare almeno un prestito da eliminare.','modal-large');
			}
			else
			{
				openPageInPopup("../cunembo302e/conferma_elimina_prestiti.do",'dlgEliminaPrestiti','Elimina prestiti','modal-large',false, $('#formVisualizzaPrestitiAgrari').serialize());		
			}
		}

		function verificaModificaMultipla(formName,checkboxesName,titoloMessageBox,bodyMessageBox,messageBoxSize)
		{
			var allUnchecked = isAllCheckboxUnchecked(checkboxesName);
			if(allUnchecked)
			{
				showMessageBox(titoloMessageBox, bodyMessageBox, messageBoxSize);
				return false;
			}
			else
			{
				$('#'+formName).submit();
				return true;
			}
		}

	function selectAll(nameCheckbox,idSelectAll) 
	{
		$("input[name='" + nameCheckbox +"']").prop("checked",$('#' + idSelectAll).prop('checked'));
		return true;
	}
		
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />