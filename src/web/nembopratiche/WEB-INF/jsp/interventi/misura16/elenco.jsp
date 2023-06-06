<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
.textRight {
	text-align: right;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form:form action="" modelAttribute="filtroInterventiForm" method="post">
		<input type="hidden" id="filtroInterventi" value='${filtroAziende.get("elencoInterventi")}'>
	</form:form>
	<div class="container-fluid" id="content">

		<m:panel id="panelInterventi">

			<form name="elencoForm" id="elencoForm" method="post" action="">
				<div id="filter-bar"></div>
				<table id='tblInterventi' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh show-totali" data-toggle="table"
					data-url="json/elenco.json" data-undefined-text='' data-row-style='rowStyleFormatter' data-show-columns="true" data-show-filter="true">
					<thead>
						<tr>
							<th data-totale="false" data-width="118" data-switchable="false" data-field="icone" data-formatter="iconeFormatter"><p:abilitazione-azione
									codiceQuadro="${cuCodQuadro}" codiceAzione="INSERISCI">
									<a href="../cunembo${cuNumber}i/index.do" class="ico24 ico_add_white"></a>
								</p:abilitazione-azione> <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="ELIMINA">
									<a href="../cunembo${cuNumber}m/elimina.do" onclick="return eliminaMultiplo()" class="ico24 ico_trash"></a>
								</p:abilitazione-azione> <a href="#" onClick="forwardToPage('../cunembo${cuNumber}l/elencoInterventiExcel.xls');" style="text-decoration: none;"><i
									class="ico24 ico_excel" title="Esporta dati in Excel"></i></a></th>
							<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
								<th data-switchable="false" data-field="idIntervento" data-totale="false" data-formatter="selezioneMultiplaCheckboxFormatter"><input
									type="checkbox" name="chkSelectAll" id="chkSelectAll" onclick="selectAll()" /></th>
							</p:abilitazione-azione>
							<th data-field="progressivo" data-formatter="progressivoFormatter" data-totale="false">Progr.</th>
							<th data-field="descAttivita" data-totale="false" data-sortable="true">Attivit&agrave;</th>
							<th data-field="descPartecipante" data-totale="false" data-sortable="true">Partecipante</th>
							<th data-field="descTipoClassificazione" data-visible="false" data-totale="false" data-sortable="true">Tipo classificazione</th>
							<th data-field="descTipoAggregazione" data-visible="false" data-totale="false" data-sortable="true">Tipo intervento</th>
							<th data-field="descIntervento" data-totale="false" data-sortable="true">Intervento</th>
							<th data-field="ulterioriInformazioni" data-visible="true" data-totale="false">Ulteriori informazioni</th>
							<th data-field="descComuni" data-visible="false" data-totale="false">Comuni interessati</th>
							<th data-field="misurazioni" data-formatter="misurazioniFormatter" class="cellTable" data-totale="false" data-width="10%">Dato / Valore / UM</th>
							<th data-field="importoUnitarioStr" data-visible="false" data-totale="false" class="textRight" data-width="8%">Importo unitario</th>
							<th data-field="importoInvestimento" data-formatter="numberFormatter2" data-totale="true" data-adder="importoInvestimentoAdder" data-width="8%">Importo Investimento</th>
							<c:if test="${hasflagAssociatoAltraMisura}">
								<th data-field="flagAssociatoAltraMisura" data-formatter="associatoAltraMisuraFormatter">Richiesto<br />in 4.1.2</th>
							</c:if>
							<th data-field="operazione" data-visible="false">Operazione</th>
							<th data-field="spesaAmmessa" data-visible="false" class="textRight" data-formatter="numberFormatter2" data-totale="true"  data-adder="plainNumberAdder">Spesa ammessa</th>
							<th data-field="percentualeContributoStr" data-visible="false" class="textRight">Percentuale contributo</th>
							<th data-field="contributoConcesso" data-visible="false" class="textRight" data-formatter="numberFormatter2" data-totale="true" data-adder="plainNumberAdder">Contributo concesso</th>
						</tr>
					</thead>
				</table>
				<br />
			</form>
			<c:if test="${ribassoInterventi}">
				<input type="hidden" name="totaleInterventi" id="totaleInterventi" value="${totaleInterventi}" />
				<%@include file="/WEB-INF/jsp/interventi/include/percentualeRiduzione.jsp"%>
			</c:if>
			<blockquote>
				Legenda<br /> <span class="badge" style="margin-left: 12px; margin-top: 10px; background-color: green; border: 1px solid black">&nbsp;&nbsp;</span>
				Nuovo intervento<br /> <span class="badge" style="margin-left: 12px; background-color: orange; border: 1px solid black">&nbsp;&nbsp;</span>
				Intervento modificato<br /> <span class="badge" style="margin-left: 12px; border: 1px solid black">&nbsp;&nbsp;</span> Intervento eliminato<br />
				<span class="badge" style="margin-left: 12px; background-color: white; color: black; border: 1px solid black">&nbsp;&nbsp;</span> Intervento
				consolidato<br />
			</blockquote>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="../js/dual-list-box.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('body').on("column-switch.bs.table", function(obj, field, checked) {
				$.ajax({
					type : "POST",
					url : '../session/salvaColonna.do',
					data : "key=elencoInterventi&field=" + field + "&value=" + checked,
					success : function(data) {
					}
				});
			});
			$('#filter-bar').bootstrapTableFilter({
				filters : [ {
					field : 'descAttivita',
					label : 'Attività',
					type : 'ajaxSelect',
					source : 'getElencoAttivita.do'
				}, {
					field : 'descPartecipante',
					label : 'Partecipante',
					type : 'ajaxSelect',
					source : 'getElencoPartecipanti.do'
				}, {
					field : 'descIntervento',
					label : 'Intervento',
					type : 'ajaxSelect',
					source : 'getInterventi.do'
				}, {
					field : 'descTipoAggregazione',
					label : 'Tipologia intervento',
					type : 'ajaxSelect',
					source : 'getTipologieInterventi.do'
				}

				],
				connectTo : '#tblInterventi',
				onSubmit : function() {
					var data = $('#filter-bar').bootstrapTableFilter('getData');
					var elabFilter = JSON.stringify(data);
					$.ajax({
						type : "POST",
						url : '../session/salvaFiltri.do',
						data : "key=elencoInterventi&filtro=" + elabFilter,
						success : function(data) {
						}
					});
					console.log(data);
				}
			});
			var filterJSON = $('#filtroInterventi').val();
			if (filterJSON !== undefined && filterJSON != null)
				$('#filter-bar').bootstrapTableFilter("setupFilterFromJSON", filterJSON);
		});
	</script>
	<c:if test="${hasflagAssociatoAltraMisura}">
		<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
		<script type="text/javascript">
			var toggleHandler = null;
			var toggleEnabled = false;
		</script>
		<c:choose>
			<c:when test="${isAbilita}">
				<script type="text/javascript">
					toggleEnabled = true;
					function associaAltraMisura(checkbox) {
						$this = checkbox;
						var id = $this.data('id');
						var $url = '../cunembo${cuNumber}m/';
						var checked = !$this.prop('checked');
						if (!checked) {
							$url = $url + 'non';
						}
						$url = $url + 'richiesto412_' + id + '.do'
						var retVal = false;
						$.ajax({
							url : $url,
							async : false,
							success : function(data) {
								retVal = data != null && data.indexOf("success") >= 0;
								if (!retVal) {
									if (data != null && data.indexOf("<error>") >= 0) {
										data = data.replace('<error>', '');
										data = data.replace('</error>', '');
										alert(data);
									} else {
										alert("Errore di sistema. Se l'errore persistesse si prega di contattare l'assistenza tecnica");
									}
								}
							},
							error : function() {
								retVal = false;
								alert("Errore di sistema. Se l'errore persistesse si prega di contattare l'assistenza tecnica");
							}
						});
						return retVal;
					}
					toggleHandler = associaAltraMisura;
				</script>
				<c:set var="onchange" value=' onchange="return associaAltraMisura(this)" ' />
			</c:when>
			<c:otherwise>
				<c:set var="disabeld" value='disabled="disabled"' />
			</c:otherwise>
		</c:choose>
		<script type="text/javascript">
			$('#tblInterventi').on('load-success.bs.table', function() {
				$(function() {
					$('input[data-toggle="bs-toggle"]').bootstrapToggle({
						handler : toggleHandler
					});
				});
			});
			$('#tblInterventi').on('column-switch.bs.table', function() {
				$(function() {
					$('input[data-toggle="bs-toggle"]').bootstrapToggle({
						handler : toggleHandler
					})
				});
			});
			var disabled = '';
			if (!toggleEnabled) {
				disabled = 'disabled = "disabled" ';
			}
			function associatoAltraMisuraFormatter($value, row, index) {
				if (row['idDescInterventoAssociato'] == null) {
					return '';
				}
				try {
					var checked = "";
					if ($value == 'S') {
						checked = ' checked="checked" ';
					}

					return '<div class="center"><input '+ checked+disabled
              + ' data-toggle="bs-toggle" type="checkbox" value="S" data-id="'+row['idIntervento']+'"/></div>';
				} catch (e) {

				}
			}
		</script>
	</c:if>
	<script type="text/javascript">

  $('#tblInterventi').on('load-success.bs.table', function()
  {
    var tableData = $('#tblInterventi').bootstrapTable('getData');
    var visible = false;
    if (tableData != null && tableData.length)
    {
      $.each(tableData, function(index, row)
      {
        spesaAmmessaStr =row['spesaAmmessaStr']; 
        visible |= spesaAmmessaStr!=null && spesaAmmessaStr!='' && spesaAmmessaStr!='0,00';
      });
      if (visible)
      {
        $('#tblInterventi').bootstrapTable('showColumn','spesaAmmessaStr');
      }
    }
  });
  
		function importoInvestimentoAdder(rows, field) {
			var __sum = 0;
			$(rows).each(function(index, currentRow) {
				if (currentRow['flagTipoOperazione'] != 'D') {
					var value = currentRow[field];
					if (!isNaN(value) && value.length != 0) {
						__sum += parseFloat(value);
					}
				}
			});
			return __sum;
		}

		function rowStyleFormatter(row, index) {
			if (row['flagTipoOperazione'] == 'D') {
				var retValue = {
					classes : 'danger'
				};
				return retValue;
			}
			return {};
		}

		function progressivoFormatter($value, row, index) {
			if ($value == null || $value == '') {
				$value = '&nbsp;&nbsp;';
			}
			switch (row['flagTipoOperazione']) {
			case 'I':
				$value = '<div style="text-align:center"><span class="badge" style="background-color:green;border:1px solid black">'
						+ $value + '</span>';
				break;
			case 'U':
				$value = '<span class="badge" style="background-color:orange;border:1px solid black">' + $value + '</span>';
				break;
			case 'D':
				$value = '<span class="badge" style="border:1px solid black">' + $value + '</span>';
				break;
			default:
				$value = '<span class="badge" style="background-color:white;color:black;border:1px solid black">' + $value + '</span>';
				break;
			}
			return '<div style="text-align:center">' + $value + '</div>';
		}
		function selezioneMultiplaCheckboxFormatter($value, row, index) {
			return row['flagTipoOperazione'] != 'D' ? '<input type="checkbox" name="idIntervento" value="'+$value+'" />' : '';
		}
		var ICONE = [];
		ICONE['DE'] = '<a href="../cunembo${cuNumber}v/index_:ID_INTERVENTO.do" class="ico24 ico_magnify"></a>';
		ICONE['MO'] = '<a href="../cunembo${cuNumber}m/modifica_singola_:ID_INTERVENTO.do" class="ico24 ico_modify"></a>';
		ICONE['MS'] = '<a href="../cunembo${cuNumber}ms/modifica_singola_:ID_INTERVENTO.do" class="ico24 ico_modify"></a>';
		ICONE['EL'] = '<a href="../cunembo${cuNumber}e/conferma_elimina_intervento_:ID_INTERVENTO.do" onclick="return eliminaIntervento(:ID_INTERVENTO)" class="ico24 ico_trash"></a>';
		ICONE['L1'] = '<a href=""  onclick="return localizzazione1(:ID_INTERVENTO);" class="ico24 ico_map_point"></a>';
		ICONE['L2'] = '<a href=""  onclick="return localizzazione2(:ID_INTERVENTO);" class="ico24 ico_map_point"></a>';
		ICONE['L3'] = '<a href="../cunembo${cuNumber}z/elenco_conduzioni_:ID_INTERVENTO.do" class="ico24 ico_map_point"></a>';
		ICONE['L4'] = '<a href="../cunembo${cuNumber}z/elenco_particelle_:ID_INTERVENTO.do" class="ico24 ico_map_point"></a>';
		ICONE['L5'] = '<a href="../cunembo${cuNumber}z/mappe_file_:ID_INTERVENTO.do" class="ico24 ico_clip"></a>';
		ICONE['L7'] = '<a href=""  onclick="return localizzazione7(:ID_INTERVENTO);" class="ico24 ico_map_point"></a>';
		ICONE['L8'] = '<a href="../cunembo${cuNumber}z/elenco_conduzioni_con_superficie_:ID_INTERVENTO.do" class="ico24 ico_map_point"></a>';
		function iconeFormatter($value, row, index) {
			$html = '';
			if ($value) {
				while ($value.length > 1) {
					var icona = $value.substr(0, 2);
					$value = $value.substr(2);
					$htmlIcona = ICONE[icona];
					if ($htmlIcona) {
						$html += $htmlIcona.replace(/:ID_INTERVENTO/g, row['idIntervento']);
					}
				}
			}
			return $html;
		}
		function misurazioniFormatter($value, row, index) {
			var $html = '';
			for ($i = 0; $i < $value.length; ++$i) {
				if ($i > 0) {
					$html += "<br/>"
				}
				var descMisurazione = $value[$i]['descMisurazione'];
				if (descMisurazione != null) {
					$html += descMisurazione;
				}
				var codiceUnitaMisura = $value[$i]['codiceUnitaMisura'];
				if (codiceUnitaMisura != 'NO_MISURA') {
					var valore = $value[$i]['valore'];
					$number = Number(valore);
					if (!isNaN($number)) {
						$html += "&nbsp;" + $number + "&nbsp;";
					}
					if (codiceUnitaMisura != null) {
						$html += codiceUnitaMisura;
					}
				}
			}
			return $html;
		}
		function eliminaIntervento(idIntervento) {
			return openPageInPopup('../cunembo${cuNumber}e/conferma_elimina_intervento_' + idIntervento + '.do', 'dlgEliminaIntervento',
					'Elimina intervento', 'modal-large', false);
		}
		function selectAll() {
			$("input[name='idIntervento']").prop("checked", $('#chkSelectAll').prop('checked'));
		}
		$(function() {
			$('[data-toggle="popover"]').popover()
		});
	</script>
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="LOCALIZZA">
		<script type="text/javascript">
			function localizzazione1(idIntervento) {
				return openPageInPopup('../cunembo${cuNumber}z/popup_comuni_particelle.do?idIntervento=' + idIntervento, 'dlgLocalizza',
						'Localizzazione per comune', 'modal-large', false);
			}
			function localizzazione2(idIntervento) {
				return openPageInPopup('../cunembo${cuNumber}z/popup_comuni_piemonte.do?idIntervento=' + idIntervento, 'dlgLocalizza',
						'Localizzazione per comune', 'modal-large', false);
			}
			function localizzazione7(idIntervento) {
				return openPageInPopup('../cunembo${cuNumber}z/popup_comuni_ute.do?idIntervento=' + idIntervento, 'dlgLocalizza',
						'Localizzazione per comune', 'modal-large', false);
			}
		</script>
	</p:abilitazione-azione>
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<script type="text/javascript">
			function modificaMultipla() {
				if ($("input[name='idIntervento']:checked").length > 0) {
					submitFormTo($('#elencoForm'), '../cunembo${cuNumber}m/modifica_multipla.do');
				} else {
					alert("Selezionare almeno un intervento");
				}
				return false;
			}
			function eliminaMultiplo() {
				if ($("input[name='idIntervento']:checked").length > 0) {
					openPageInPopup('../cunembo${cuNumber}e/conferma_elimina_interventi.do', 'dlgEliminaIntervento', 'Elimina intervento',
							'modal-large', false, $('#elencoForm').serialize());
				} else {
					alert("Selezionare almeno un intervento");
				}
				return false;
			}
		</script>
	</p:abilitazione-azione>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />