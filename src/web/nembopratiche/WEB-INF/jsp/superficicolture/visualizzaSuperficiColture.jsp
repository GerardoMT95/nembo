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
	.btn-plv {
	    display: inline-block;
	    padding: 1px 1px;
	    padding-top: 5px;
	    padding-right: 1px;
	    padding-bottom: 1px;
	    padding-left: 1px;
	    margin-bottom: 0;
	    margin-top: 2px;
	    font-size: 10px;
	    font-weight: 100;
	    line-height: 1.42857143;
	    text-align: center;
	    white-space: nowrap;
	    vertical-align: middle;
	    -ms-touch-action: manipulation;
	    touch-action: manipulation;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    
	    color: #337ab7;
    	background-color: #ffffff;
    	border-color: #2e6da4;
    	font-weight: bold;
    	
    	width:24px;
    	height:24px;"
    	font-size:7px; 
	}
</style>
	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-299-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-299-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoDanni">
		<br/>

		<form name="formVisualizzaSuperficiColture" id="formVisualizzaSuperficiColture" action="" method="POST">
				
				<h4>Riepilogo Superfici e Colture</h4>
				<table id="tableElencoSuperificiColtureRiepilogo"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
						<tr>
							<th>Superficie catastale totale</th>
							<th>Superficie condotta totale</th>
							<th>Di cui superficie Agricola Utilizzata (SAU)</th>
							<th>Di cui superficie Agricola Non Utilizzata</th>
							<th>Di cui Altra Superficie</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${elencoSuperficiColtureRiepilogo.supCatastaleFormatted}</td>
							<td>${elencoSuperficiColtureRiepilogo.superficieUtilizzataFormatted}</td>
							<td>${elencoSuperficiColtureRiepilogo.sauSFormatted}</td>
							<td>${elencoSuperficiColtureRiepilogo.sauNFormatted}</td>
							<td>${elencoSuperficiColtureRiepilogo.sauAFormatted}</td>
						</tr>
					</tbody>
				</table>
				
				<br/>
				<h4>Elenco Superfici e Colture</h4>
				<c:set var ="tableName"  value ="tableElencoSuperificiColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
					data-toggle="table"
					data-url="get_elenco_superfici_colture_dettaglio.json"
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
							<th rowspan="2" data-field="idSuperficieColtura" data-visible="${!colonneNascoste.visible(tableName,'idSuperficieColtura')}" data-formatter="idSuperficiColturaFunzioniFormatter">
								<p:abilitazione-azione codiceQuadro="SUPCO" codiceAzione="DETTAGLIO">
									<a class="btn-plv" href="../cunembo299dplv/index.do" title="P.L.V. Vegetale">PLV</a>
								</p:abilitazione-azione>
							</th>
							<th rowspan="2" data-field="idSuperficieColtura" data-visible="${!colonneNascoste.visible(tableName,'idSuperficieColtura')}" data-formatter="idSuperficieColturaFormatter"></th>
							<th colspan="2">Ubicazione Terreno</th>
							<th rowspan="2" data-field="tipoUtilizzoDescrizione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'tipoUtilizzoDescrizione')}">Utilizzo</th>
							<th rowspan="2"  data-field="superficieUtilizzata" data-formatter="numberFormatter4" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'superficieUtilizzata')}" data-adder="dataAdderForNumberFormatter" data-totale="true">Sup.<br/>(ha)</th>
							<th rowspan="2"  data-field="produzioneHa" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'produzioneHa')}" data-adder="dataAdderForNumberFormatter" data-totale="true">Prod.<br/>(q/ha)</th>
							<th rowspan="2"  data-field="produzioneDichiarata" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'produzioneDichiarata')}" data-adder="dataAdderForNumberFormatter" data-totale="true">Produzione<br/>dichiarata</th>
							<th rowspan="2"  data-field="giornateLavorativeDich"  data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'giornateLavorativeDich')}"  data-adder="dataAdderForNumberFormatter" data-totale="true">Giornate<br/>lavorative<br/>dichiarate</th>
							<th rowspan="2" data-field="ufTotali" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'ufTotali')}"  data-adder="dataAdderForNumberFormatter" data-totale="true">U.F. (totali)</th>
							<th colspan="2">Reimpieghi</th>
							<th colspan="3">P.L.V.</th>
						</tr>
						<tr>

							<th data-field="descProvincia" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descProvincia')}">Provincia</th>
							<th data-field="descComune" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descComune')}">Comune</th>
							<th data-field="qliReimpiegati" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'ufReimpiegate')}"  data-adder="dataAdderForNumberFormatter" data-totale="true">(q)</th>
							<th data-field="ufReimpiegate" data-formatter="numberFormatter0" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'qliReimpiegati')}"  data-adder="dataAdderForNumberFormatter" data-totale="true">(U.F.)</th>
							<th data-field="plvTotQuintali" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'plvTotQuintali')}" data-adder="dataAdderForNumberFormatter" data-totale="true">Tot. (q)</th>
							<th data-field="prezzo" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'prezzo')}">Prezzo</th>
							<th data-field="plvTotDich" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'plvTotDich')}" data-adder="dataAdderForNumberFormatter" data-totale="true">Tot. dich.</th>
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
			loadBootstrapTable('${tableName}', '');
		 });
		</script>
		
		<script type="text/javascript">
			var mapIdSuperficieColturaBloccante={};
			var mapIdSuperficieColturaAnomalia={};
		</script>
		
		<c:forEach items="${mapIdSuperficieColturaBloccante}" var="elem">
			<c:forEach items="${elem.value}" var="elemI">
				<script type="text/javascript">
		 			mapIdSuperficieColturaBloccante["${elem.key}"] = {};
	 			</script>
 			</c:forEach>
		</c:forEach>
		
		<c:forEach items="${mapIdSuperficieColturaBloccante}" var="elem">
			<c:forEach items="${elem.value}" var="elemI">
				<script type="text/javascript">
		 			mapIdSuperficieColturaBloccante["${elem.key}"]["${elemI.key}"] = "${elemI.value}";
				</script>
			</c:forEach>
		</c:forEach>
		
		<c:forEach items="${mapIdSuperficieColturaAnomalia}" var="elem">
			<script type="text/javascript">
	 			mapIdSuperficieColturaAnomalia["${elem.key}"] = "${elem.value}";
			</script>
		</c:forEach>
		
		<script type="text/javascript">
		
			function idSuperficiColturaFunzioniFormatter($value, row, index)
			{
				var risultato = 
					'<p:abilitazione-azione codiceQuadro="SUPCO" codiceAzione="DETTAGLIO">' +
					'<a class="ico24 ico_magnify" href="../cunembo299d/index_' + $value +'.do" title="Dettaglio"></a>'
					;
				risultato = risultato +
					'<a class="ico24 ico_map_point" href="../cunembo299dp/index_' + $value + '.do" title="Dettaglio particellare"></a>' + 
					'</p:abilitazione-azione>';
					
				risultato = risultato +
					'<p:abilitazione-azione codiceQuadro="SUPCO" codiceAzione="MODIFICA">' +
					'<a class="ico24 ico_modify" href="../cunembo299m/index_' + $value + '.do" title="Modifica"></a>' +
					'</p:abilitazione-azione>';
				return risultato;
			}
			
			function idSuperficieColturaFormatter($value, row, index)
			{
				var commento = mapIdSuperficieColturaAnomalia[$value];
				if(commento == null)
				{
					commento ='';
				}
				if(mapIdSuperficieColturaBloccante[$value] !== undefined)
				{
					if(mapIdSuperficieColturaBloccante[$value]['S'] !== undefined && mapIdSuperficieColturaBloccante[$value]['S']!= null) //B
					{
						return '<a onclick=\'showMessageBox(\"Descrizione anomalie\", \"' + commento + '\" , \"modal-large\"); \'><i style="cursor: pointer;" class="ico24 ico_errorG"></i></a>'; 
					}
					else if (mapIdSuperficieColturaBloccante[$value]['N'] !== undefined && mapIdSuperficieColturaBloccante[$value]['N']!= null) //W
					{
						return '<a onclick=\'showMessageBox(\"Descrizione anomalie\", \"' + commento + '\" , \"modal-large\"); \'><i style="cursor: pointer;" class="ico24 ico_warning"></i></a>'; 
					}
					else
					{
						return "";
					}
				}
				else
				{
					return "";
				}
			}
			
			function rowStyleFormatter(row, index)
		    {
				if (row['recordModificato'] != null && row['recordModificato'] == 'S') {
					var retValue = {
						classes : 'red-bold'
					};
					return retValue;
				}
				return {};
		    }
			
		
		</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />