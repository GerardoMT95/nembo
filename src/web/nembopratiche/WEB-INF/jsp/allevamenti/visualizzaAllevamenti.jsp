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
	<p:breadcrumbs cdu="CU-NEMBO-300-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-300-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoAllevamenti">
		<br/>

		<form name="formVisualizzaElencoAllevamenti" id="formVisualizzaElencoAllevamenti" action="" method="POST">
				
				<h4>Elenco Allevamenti</h4>
				<c:set var ="tableName"  value ="tableElencoAllevamenti"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
					data-toggle="table"
					data-url="get_list_allevamenti.json"
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
							<th data-field="chiaveAllevamento" data-visible="${!colonneNascoste.visible(tableName,'chiaveAllevamento')}" data-formatter="chiaveAllevamentoFormatter" data-switchable="false">
								<p:abilitazione-azione codiceQuadro="SUPCO" codiceAzione="DETTAGLIO">
									<a class="btn-plv" href="../cunembo300plv/index.do" title="P.L.V. Zootecnica">PLV</a>
								</p:abilitazione-azione>
							</th>
							<th data-field="ubicazioneAllevamento" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'ubicazioneAllevamento')}">Ubicazione allevamento</th>
							<th data-field="descrizioneSpecieAnimale" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descrizioneSpecieAnimale')}">Specie</th>
							<th data-field="descrizioneCategoriaAnimale" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descrizioneCategoriaAnimale')}">Categoria</th>
							<th data-field="quantita" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'quantita')}" data-totale="true" data-adder="dataAdderForNumberFormatter" data-totale="true">Quantità</th>
							<th data-field="coefficienteUba" data-formatter="numberFormatter4" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'coefficienteUba')}" data-totale="true" data-adder="dataAdderForNumberFormatter">U.B.A.</th>
							<th data-field="unitaForaggere" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'consumoAnnuoUf')}" data-totale="true" data-adder="dataAdderForNumberFormatter">U.F.</th>
							<th data-field="giornateLavorative" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'giornateLavorativeMedie')}" data-totale="true" data-adder="dataAdderForNumberFormatter">Giornate lavorative</th>
							<th data-field="plv" data-formatter="numberFormatter2" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'plv')}" data-totale="true" data-adder="dataAdderForNumberFormatter">P.L.V.</th>
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
		 
		 function chiaveAllevamentoFormatter($value, row, index)
		 {
		 	var valore='';
		 	/*
		 	<p:abilitazione-azione codiceQuadro="ALLEV" codiceAzione="DETTAGLIO">
		 	*/
		 	valore = valore +
		 		'<a class="ico24 ico_magnify" href="../cunembo300d/index_' + $value + '.do" title="Dettaglio"></a>';
	 		/*
	 		</p:abilitazione-azione>
	 		*/
	 		
	 		/*
		 	<p:abilitazione-azione codiceQuadro="ALLEV" codiceAzione="INSERISCI">
		 	*/
		 	valore = valore +
		 		'<a class="icon-list icon-large" href="../cunembo300i/index_' + $value + '.do" title="Inserisci produzioni"></a>';
	 		/*
	 		</p:abilitazione-azione>
	 		*/
		 	return valore;
		 }
		 
		 function rowStyleFormatter(row, index) {
			if (row['dataUltimoAggiornamento'] != null && row['dataUltimoAggiornamento'] != '') {
				var retValue = {
					classes : 'red-bold'
				};
				return retValue;
			}
			return {};
		}
		$("form")
		</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />