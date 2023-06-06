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
	<p:set-cu-info />
	<p:breadcrumbs cdu="CU-NEMBO-306-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-306-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoAllevamenti">
		<br/>

		<form name="formVisualizzaAssicurazioniColture" id="formVisualizzaAssicurazioniColture" action="" method="POST">
				
				<h4>Assicurazioni Colture</h4>
				<c:set var ="tableName"  value ="tableVisualizzaAssicurazioniColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<c:set var ="checkboxName"  value ="chkIdAssicurazioniColture"/>
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
					data-toggle="table"
					data-url="get_list_assicurazioni_colture.json"
					data-pagination="true" 
					data-only-info-pagination="true"
					data-show-pagination-switch="true" 
					data-pagination-v-align="top"
					data-show-columns="true" 
					data-page-list="[10, 25, 50, 70, 100]"
					data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
					data-show-default-order-button="true"
					data-default-order-column="${defaultOrderColumn}"
					data-default-order-type="${defaultOrderType}"
					data-table-name="${tableName}"
					data-escape-table="true"
				>
					<thead>
						<tr>
							<th data-field="idAssicurazioniColture" data-formatter="idAssicurazioniColtureFormatter" data-switchable="false">
								<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="INSERISCI">
									<a class="ico24 ico_add" href="../cunembo${cuNumber}i/index.do"></a>
								</p:abilitazione-azione>
								<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
									<a class="ico24 ico_trash" onclick="eliminazioneMultipla()"></a>
								</p:abilitazione-azione>
							</th>
							<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
								<th class="checkbox-field" data-field="idAssicurazioniColture" data-formatter="checkboxFormatter" data-switchable="false">
								<input type="checkbox" name="selAll" id="selAll" onclick="selectAllCheck('${checkboxName}',this.checked);" />
							</th>
							</p:abilitazione-azione>
							<th data-field="nomeConsorzioEntePrivato" data-sortable="true" data-switchable="false">Nome Consorzio<br/>o Ente</th>
							<th data-field="numeroSocioPolizza" data-sortable="true"  data-switchable="false">Num. socio<br/>polizza</th>
							<th data-field="importoPremio" data-formatter="numberFormatter2" data-sortable="true" data-switchable="false" data-totale="true" data-adder="dataAdderForNumberFormatter">Importo premio<br/>annuale (&euro;)</th>
							<th data-field="importoAssicurato" data-formatter="numberFormatter2" data-sortable="true" data-switchable="false" data-totale="true" data-adder="dataAdderForNumberFormatter">Valore<br/>assicurato(&euro;)</th>
							<th data-field="importoRimborso" data-formatter="numberFormatter2" data-sortable="true"  data-switchable="false" data-totale="true" data-adder="dataAdderForNumberFormatter">Importo<br/>Risarcimento (&euro;)</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				Ultima modifica: ${ultimaModifica}
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
		
		
		<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
		</p:abilitazione-azione>
		
		<script type="text/javascript">
			function idAssicurazioniColtureFormatter($value, row, index){
				var risultato='';
				/*
					<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="MODIFICA">
				*/
					risultato+='<a class="ico24 ico_modify" href="../cunembo${cuNumber}m/index_' + $value +'.do"></a>';
				/*
					</p:abilitazione-azione>
				*/

				/*
					<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
				*/
					var modifica = 'openPageInPopup(\'../cunembo${cuNumber}e/index_' + $value +'.do\',\'dlgEliminaAssicurazioni\',\'Elimina Assicurazioni\',\'modal-large\',false, null);';
					risultato+='<a class="ico24 ico_trash" onclick="' + modifica +'"></a>'; 
				/*
					</p:abilitazione-azione>
				*/
										
				
				return risultato;
			}
			
		function eliminazioneMultipla()
		{
			openPageInPopup("../cunembo${cuNumber}e/index.do",'dlgEliminaAssicurazioni','Elimina Assicurazioni','modal-large',false, $('#formVisualizzaAssicurazioniColture').serialize());		
		}
		
		function checkboxFormatter($value, row, index)
		{
			return '<input type="checkbox" name="${checkboxName}" value="' + $value + '"></input>';
		}

		</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />