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

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-305-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-305-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		<br/>
		<div style="overflow-x:scroll;">
		
				<h4>DICHIARAZIONE P.L.V. ORDINARIA ED EFFETTIVA</h4>
				<table id="tableRiepilogoColtureAziendali"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th class="col-sm-6">Totale Superficie (ha)</th>
							<td class="col-sm-6">${riepilogo.superficieUtilizzataFormatted}</td>
						</tr>
						<tr>
							<th>Totale P.L.V. ordinaria<br/>(media del triennio precedente)</th>
							<td>${riepilogo.totalePlvOrdinariaFormatted}</td>
						</tr>
						<tr>
							<th>Totale P.L.V. effettiva<br/>(conseguita nell'annata agraria dell'evento)</th>
							<td>${riepilogo.totalePlvEffettivaFormatted}</td>
						</tr>
						<tr>
							<th>% Danno</th>
							<td>${riepilogo.percentualeDannoFormatted}</td>
						</tr>
						<tr>
							<th>Ultima Modifica</th>
							<td>${ultimaModifica}</td>
						</tr>
					</tbody>
				</table>
				
					<h4>RICALCOLI P.L.V. ORDINARIA ED EFFETTIVA CON ASSICURAZIONI</h4>
					<table id="tableRiepilogoColtureAziendaliAssicurazioni"
						class="table table-hover table-striped table-bordered tableBlueTh"
					>
						<thead>
						</thead>
						<tbody>
							<tr>
								<th class="col-sm-6">Importo del Premio pagato all'assicurazione</th>
								<td class="col-sm-6">${assicurazioniColture.importoPremioFormatted}</td>
							</tr>
							<tr>
								<th>Importo del risarcimento ricevuto dall'assicurazione</th>
								<td>${assicurazioniColture.importoRimborsoFormatted}</td>
							</tr>
							<tr>
								<th>P.L.V. ricalcolata tenendo conto dei rimborsi assicurativi</th>
								<td>${riepilogo.plvRicalcolataConAssicurazioniFormatted}</td>
							</tr>
							<tr>
								<th>Produzione Perduta ricalcolata tenendo conto dei rimborsi assicurativi</th>
								<td>${riepilogo.plvOrdinariaConAssicurazioniFormatted}</td>
							</tr>
							<tr>
								<th>% Danno calcolato tenendo conto dei rimborsi assicurativi</th>
								<td>${riepilogo.percentualeDannoConAssicurazioniFormatted}</td>
								
							</tr>
							<tr>
								<th>Ultima Modifica</th>
								<td>${ultimaModifica}</td>
							</tr>
						</tbody>
					</table>
				
				<form name="formVisualizzaColtureAziendali" id="formVisualizzaColtureAziendali" action="../cunembo${cuNumber}m/index.do" method="POST">
				<c:set var ="tableName"  value ="tableElencoColtureAziendali"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
					data-toggle="table"
					data-url="get_list_colture_aziendali.json"
					data-pagination=true 
					data-only-info-pagination=true
					data-show-pagination-switch="true" 
					data-pagination-v-align="top"
					data-show-columns="true" 
					data-page-list="[10, 25, 50, 70, 100]"
					data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
					data-show-default-order-button="true"
					data-default-order-column="${defaultOrderColumn}"
					data-default-order-type="${defaultOrderType}"
					data-table-name="${tableName}"
					data-sort-name="${sessionMapNomeColonnaOrdinamento.get(tableName) != null ? 
						sessionMapNomeColonnaOrdinamento.get(tableName) : 
						defaultOrderColumn}"
					data-sort-order="${sessionMapTipoOrdinamento.get(tableName) != null ? sessionMapTipoOrdinamento.get(tableName) : defaultOrderType}"
					data-page-number="${sessionMapNumeroPagina.get(tableName) != null ? sessionMapNumeroPagina.get(tableName) : 1}"
					data-row-style='rowStyleFormatter'
					data-escape-table="true"
				>
					<thead>
						<tr>
							<th rowspan="2" data-field="idSuperficieColtura" data-formatter="modificaFormatter" data-switchable="true">
								<p:abilitazione-azione codiceQuadro="COLAZ" codiceAzione="MODIFICA">
									<a class="ico24 ico_modify" title="Modifica" onclick="$('#formVisualizzaColtureAziendali').submit();"></a>
								</p:abilitazione-azione>
							</th>
							<th rowspan="2" class="checkbox-field" data-field="idSuperficieColtura" data-formatter="checkboxFormatter" data-switchable="false"><input type="checkbox" name="selAll" id="selAll" onclick="selectAllCheck('idSuperficieColtura',this.checked);" /></th>
							<th rowspan="2" class="checkbox-field" data-field="idSuperficieColtura" data-formatter="bloccanteFormatter"></th>
							<th rowspan="2" class="col-md-1" data-field="ubicazioneTerreno" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'ubicazioneTerreno')}" >Ubicazione<br/>Terreno</th>
							<th rowspan="2" class="col-md-1" data-field="tipoUtilizzoDescrizione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'tipoUtilizzoDescrizione')}">Utilizzo</th>
							<th rowspan="2" class="col-md-1" data-field="superficieUtilizzata" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'superficieUtilizzata')}" data-formatter="numberFormatter4">Sup.<br/>(ha)</th>

							<th colspan="4">P.L.V. ordinaria annuale<br/>(media del triennio precedente)</th>
							<th colspan="3">P.L.V. effettiva (conseguita nell'annata<br/>agraria dell'evento)</th>
							<th rowspan="2" class="col-md-1" data-field="percentualeDanno" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'percentualeDanno')}" data-formatter="numberFormatter2AcceptNull">Danno<br/>(%)</th>
							
						</tr>
						<tr>
							<th class="col-md-1" data-field="produzioneHa" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'produzioneHa')}" data-formatter="numberFormatter2">Q.li / ha</th>
							<th class="col-md-1" data-field="produzioneDichiarata" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'produzioneDichiarata')}" data-formatter="numberFormatter2">Tot. Q.li</th>
							<th class="col-md-1" data-field="prezzo" data-sortable="true"  data-visible="${!colonneNascoste.visible(tableName,'prezzo')}" data-formatter="numberFormatter2">Prezzo<br/>al q.le</th>
							<th class="col-md-1" data-field="totaleEuroPlvOrd" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'totaleEuroPlvOrd')}" data-formatter="numberFormatter2">Totale<br/>Euro</th>

							<th class="col-md-1" data-field="produzioneTotaleDanno" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'produzioneTotaleDanno')}" data-formatter="numberFormatter2AcceptNull">Tot. Q.li</th>
							<th class="col-md-1" data-field="prezzoDanneggiato" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'prezzoDanneggiato')}" data-formatter="numberFormatter2AcceptNull">Prezzo<br/>al q.le</th>
							<th class="col-md-1" data-field="totaleEuroPlvEff" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'totaleEuroPlvEff')}" data-formatter="numberFormatter2AcceptNull">Totale<br/>Euro</th>
						
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</form>
			<br/>
		</div>
   		<br/>
   		<a class="btn btn-default" href="../cunembo299l/index.do">Indietro</a>
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
var mapIdSuperficieColturaAnomalia={};
</script>	
	
<c:forEach items="${mapIdSuperficieColturaAnomalia}" var="elem">
	<script type="text/javascript">
			mapIdSuperficieColturaAnomalia["${elem.key}"] = "${elem.value}";
	</script>
</c:forEach>

<script type="text/javascript">
	
	$(document).ready(function() {
		$('form').on('submit',function(e){
			if(isAllCheckboxUnchecked('idSuperficieColtura'))
			{
				showMessageBox('Modifica Scorte', 'Selezionare almeno una coltura aziendale da modificare.','modal-large');
				e.preventDefault();
			}
		});
	});
	
	function bloccanteFormatter($value, row, index)
	{
		var commento = mapIdSuperficieColturaAnomalia[$value];
		if(row['bloccante'] == 'S')
		{
			return '<a onclick=\'showMessageBox(\"Descrizione anomalie\", \"' + commento + '\" , \"modal-large\"); \'><i style="cursor: pointer;" class="ico24 ico_errorG"></i></a>';
		}
		else if(row['bloccante'] == 'N')
		{
			return '<a onclick=\'showMessageBox(\"Descrizione anomalie\", \"' + commento + '\" , \"modal-large\"); \'><i style="cursor: pointer;" class="ico24 ico_warning"></i></a>';  
		}
		else
		{
			return '';
		}
	}
	
	function checkboxFormatter($value, row, index)
	{
		return '<input type="checkbox" name="idSuperficieColtura" value="' + $value + '"></input>';
	}
	
	function modificaFormatter($value, row, index)
	{
		var risultato = '';
		
		/*
		<p:abilitazione-azione codiceQuadro="COLAZ" codiceAzione="DETTAGLIO">
		*/			
		risultato = risultato +
		'<a class="ico24 ico_magnify" href="../cunembo${cuNumber}d/index_' + $value +'.do" title="Dettaglio"></a>'	
		/*
		</p:abilitazione-azione>
		*/	

		/*
		<p:abilitazione-azione codiceQuadro="COLAZ" codiceAzione="MODIFICA">
		*/
		risultato = risultato +
		'<a class="ico24 ico_modify" href="../cunembo${cuNumber}m/index_' + $value + '.do" title="Modifica"></a>';
		/*
		</p:abilitazione-azione>
		*/
		return risultato;
	}
	
	
	function rowStyleFormatter(row, index) {
		if(row['recordModificato'] != null && row['recordModificato'] === 'S') {
			var retValue = {
				classes : 'red-bold'
			};
			return retValue;
		}
		return {};
	}
	
	

</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />