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
	<p:breadcrumbs cdu="CU-NEMBO-298-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-298-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoDanni">
		<br/>

		<div id="errorBoxDiv" class='alert alert-danger col-md-12' style="display:none;">
			<h4>Non è possibile modificare pi&ugrave; danni contemporaneamente se almeno uno di quelli selezionati &egrave; di tipo Terreni Ripristinabili, Terreni Non Ripristinabili, Piantagioni Arboree o Allevamenti.</h4>
		</div>
		
		<form name="formVisualizzaDanni" id="formVisualizzaDanni" action="../cunembo298m/modifica_danni_multipla.do" method="POST">
				<c:set var ="tableName"  value ="tableElencoDanni"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh show-totali"
					data-toggle="table"
					data-url="get_elenco_danni.json"
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
					data-escape-table="true"
				>
					<thead>
						<tr>
							<th class="col-md-1" data-field="idDannoAtm" data-formatter="modificaFormatter" data-switchable="false" >
								<p:abilitazione-azione codiceQuadro="DANNI"
									codiceAzione="INSERISCI">
									<a href="../cunembo298i/index.do" title="Inserisci"><i
										class="ico24 ico_add"></i></a>
								</p:abilitazione-azione> <p:abilitazione-azione codiceQuadro="DANNI"
									codiceAzione="ELIMINA">
									<a onclick="eliminazioneMultipla()" title="Elimina selezionati"><i
										class="ico24 ico_trash"></i></a>
								</p:abilitazione-azione> <p:abilitazione-azione codiceQuadro="DANNI"
									codiceAzione="MODIFICA">
									<a class="ico24 ico_modify"
										onclick="verificaModificaMultipla()"></a>
									
								</p:abilitazione-azione>

							</th>
							<th class="checkbox-field" data-field="idDannoAtm" data-formatter="checkboxFormatter" data-switchable="false"><input type="checkbox" name="selAll" id="selAll" onclick="selectAll(this.checked);" /></th>
							<th class="col-md-1" data-field="tipoDanno" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'tipoDanno')}" data-totale="false">Tipo Danno</th>
							<th class="col-md-3" data-escape-field="false" data-field="denominazione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'denominazione')}" data-totale="false">Denominazione</th>
							<th class="col-md-2" data-field="descEntitaDanneggiata" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descEntitaDanneggiata')}" data-totale="false">Descrizione entit&agrave;<br/> danneggiata</th>
							<th class="col-md-3" data-field="descrizione" data-sortable="true" data-visible="${!colonneNascoste.visible(tableName,'descrizione')}" data-totale="false">Descrizione<br/>danno</th>
							<th class="col-md-1" data-field="quantitaUnitaMisuraFormatter" data-sortable="true" data-sorter="dataSorterNumeroUnitaMisura" data-visible="${!colonneNascoste.visible(tableName,'quantita')}" data-totale="false">Quantit&agrave;</th>
							<th class="col-md-1" data-field="importo" data-sortable="true" data-sorter="dataSorterNumeroUnitaMisura" data-visible="${!colonneNascoste.visible(tableName,'importo')}" data-formatter="numberFormatter2" data-adder="dataAdderForNumberFormatter" data-totale="true">Importo (&euro;)</th>
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
			
			var errorModificaMultipla = "${errorModificaMultipla}";
			if(errorModificaMultipla != null && errorModificaMultipla == 'true')
			{
				$('#errorBoxDiv').show();
			}
		 });
		
		function checkboxFormatter($value, row, index)
		{
			return '<input type="checkbox" name="idDannoAtm" value="' + $value + '"></input>';
		}
		
		</script>
		
		<script type="text/javascript">		
		function modificaFormatter($value, row, index)
		{
			var risultato = '';
			
			/*
				<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="DETTAGLIO">
			*/
				risultato = risultato +
					'<a href="../cunembo298d/index_' + $value + '.do" title="Dettaglio"><i class="ico24 ico_magnify"></i></a>';
			/*
				</p:abilitazione-azione>
			*/
			
			/*
				<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="ELIMINA">
			*/
				risultato = risultato +
								'<a onclick="eliminazioneSingola(' + $value + ')" title="Elimina"><i class="ico24 ico_trash"></i></a>';
			/*
				</p:abilitazione-azione>
			*/
				
			
			/*
				<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="MODIFICA">
			*/
				risultato =	risultato + 
						'<a title="Modifica" onclick="modificaDanno('+ $value +');"><i class="ico24 ico_modify"></i></a>';
			/*
				</p:abilitazione-azione>
			*/
			return risultato;
		}
		
		/*
			<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="MODIFICA">
		*/
		
		function modificaDanno(idDannoAtm)
		{
			var href = '../cunembo298m/modifica_danno_' + idDannoAtm + '.do';
		    $.ajax({
		        type: "POST",
		        url: '../cunembo298m/get_n_interventi_danno_' + idDannoAtm +'.do',
		        data: '',
		        dataType: "json",
		        success: function(data) {
		            if(parseInt(data) == 0)
		            {
		            	window.location.href = href;
		            }
		            else
		            {
		            	openPageInPopupMethod("../cunembo298m/failure_modifica_danni_interventi.do",'dlgModificaDanni','Modifica Danni','modal-large',false, $('#${formVisualizzaDanni}').serialize(),'GET');
		            }
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
	
			
			
			
			
		}	
		/*
			</p:abilitazione-azione>
		*/
		
		</script>
		
		<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="ELIMINA"> 
		<script type="text/javascript">
		function eliminazioneSingola(idDannoAtm)
		{
			openPageInPopup("../cunembo298e/conferma_elimina_danno_"+idDannoAtm+".do",'dlgEliminaDanni','Elimina danno','modal-large',false);
		}
		
		function eliminazioneMultipla()
		{
			var allUnchecked = isAllCheckboxUnchecked();
			if(allUnchecked)
			{
				showMessageBox("Eliminazione Danni", "Selezionare almeno un danno da eliminare", 'modal-large');  
			}
			else
			{
				openPageInPopup("../cunembo298e/conferma_elimina_danni.do",'dlgEliminaDanni','Elimina danni','modal-large',false, $('#formVisualizzaDanni').serialize());		
			}
		}
		</script>
		</p:abilitazione-azione>
		
		<p:abilitazione-azione codiceQuadro="DANNI" codiceAzione="MODIFICA"> 
		<script type="text/javascript">
			function verificaModificaMultipla()
			{
				var allUnchecked = isAllCheckboxUnchecked();
				if(allUnchecked)
				{
					showMessageBox("Modifica Danni", "Selezionare almeno un danno da modificare", 'modal-large');  
					return false;
				}
				else
				{
				    $.ajax({
				        type: "POST",
				        url: '../cunembo298m/get_n_interventi_danni.do',
				        data: $('#formVisualizzaDanni').serialize(),
				        dataType: "json",
				        success: function(data) {
				            if(parseInt(data) == 0)
				            {
				            	$('#formVisualizzaDanni').submit();
				            }
				            else
				            {
				            	openPageInPopupMethod("../cunembo298m/failure_modifica_danni_interventi.do",'dlgModificaDanni','Modifica Danni','modal-large',false, $('#${formVisualizzaDanni}').serialize(),'GET');
				            }
				        },
				        error: function() {
				            alert('Error occured');
				        }
				    });
					
					return true;
				}
			}
		</script>
		</p:abilitazione-azione>
		
		
		<script type="text/javascript">
			function selectAll(checked) 
			{
				$("input[name='idDannoAtm']").prop("checked",checked);
			}
	
			function isAllCheckboxUnchecked() {
				var allUnchecked = true;
				$("input[name='idDannoAtm']").each(function() 
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