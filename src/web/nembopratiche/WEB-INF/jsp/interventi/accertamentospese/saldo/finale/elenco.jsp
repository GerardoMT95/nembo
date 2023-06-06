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
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="panelInterventi">
			<form name="elencoForm" id="elencoForm" method="post" action="">
				<c:if test="${msgIVA!=null}">
					<strong><c:out value="${msgIVA}" /></strong>
				</c:if>
				<c:if test="${isMisura81}">
					<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="IMPORTA_GIS">
						<c:if test="${warningImportaGIS}">
							<div class="alert alert-info">Per eseguire l'importazione delle superfici GIS &egrave; necessario prima aver indicato la data di fine lavori di
								istruttoria</div>
						</c:if>
						<c:if test="${!warningImportaGIS}">
							<div id="importaGIS" class="puls-group" style="margin-top: 1em; margin-bottom: 2em; display: none">
								<div class="pull-left">
									<a href="../cunembo260/index.do" class="btn btn-primary">importa GIS</a>
								</div>
								<br class="clear" />
							</div>
						</c:if>
					</p:abilitazione-azione>
				</c:if>

				<table id='tblInterventi' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh show-totali" data-toggle="table"
					data-url="json/elenco.json" data-undefined-text='' data-show-columns="true">
					<thead>
						<tr>
							<th data-totale="false" data-width="48" data-switchable="false" data-field="idIntervento" data-formatter="iconeFormatter"><p:abilitazione-azione
									codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
									<a href="../cunembo${cuNumber}m/modifica.do" onclick="return modificaMultipla()" class="ico24 ico_modify"></a>
								</p:abilitazione-azione></th>
							<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
								<th data-switchable="false" data-field="idIntervento" data-totale="false" data-formatter="selezioneMultiplaCheckboxFormatter"><input
									type="checkbox" name="chkSelectAll" id="chkSelectAll" onclick="selectAll()" /></th>
							</p:abilitazione-azione>
							<th data-field="progressivo" data-formatter="progressivoFormatter" data-totale="false">Progr.</th>
							<th data-field="descTipoIntervento" data-visible="false" data-totale="false">Tipo<br />intervento
							</th>
							<th data-field="descIntervento" data-totale="false">Intervento</th>
							<th data-field="ulterioriInformazioni" data-visible="false" data-totale="false">Ulteriori<br />informazioni
							</th>
							<th data-field="spesaAmmessa" data-totale="true" data-visible="true" data-formatter="numberFormatter2" data-adder="importoAdder">Spesa<br />ammessa
							</th>
							<th data-field="percentualeContributo" data-totale="false" data-visible="false" data-formatter="numberFormatter2" data-adder="importoAdder">Perc.</th>
							<th data-field="importoContributo" data-totale="true" data-visible="true" data-formatter="numberFormatter2" data-adder="importoAdder">Contributo</th>

							<th data-field="speseSostenute" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Spese gi&agrave;<br />rendicontate
							</th>
							<th data-field="speseAccertate" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="false">Spese gi&agrave;<br />riconosciute
							</th>
							<th data-field="spesaSostenutaAttuale" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Spesa<br />rendicontata<br />attuale
							</th>
							<th data-field="speseAccertateAttuali" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="true">Spese<br />
								accertate<br />attuali
							</th>
							<th data-field="spesaRiconosciutaPerCalcolo" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder">Spesa<br />riconosciuta<br />per
								il calcolo<br />del contributo
							</th>
							<th data-field="importoNonRiconosciuto" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="true">Importo<br />non<br />riconosciuto<br />sanzionabile
							</th>
							<th data-field="importoRispendibile" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="true">Importo non<br />riconosciuto<br />non
								sanzionabile
							</th>
							<th data-field="contributoCalcolato" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible="true">Contributo<br />calcolato
							</th>
							<th data-field="contributoAbbattuto" data-totale="true" data-formatter="numberFormatter2" data-adder="importoAdder" data-visible=false>Contributo<br />abbattuto
							</th>
							<th data-field="flagInterventoCompletato" data-totale="false" data-formatter="flagInterventoCompletatoFormatter">Intervento<br />completato
							</th>
							<th data-field="note" data-totale="false">Note</th>
						</tr>
					</thead>
				</table>
				<br />
				<c:forEach items="${contributi}" var="contributo" varStatus="status">
					<m:panel id="totaliContributo_${status.index}" title="${contributo.codiceOperazione}">
						<table class="pull-right">
							<tr>
								<th>Contributo erogabile</th>
								<td style="padding-left: 20px; min-width: 160px" class="numero"><fmt:formatNumber value="${contributo.contributoErogabile}" pattern="###,##0.00" />
									&euro;</td>
							</tr>
							<c:if test="${contributo.compensazioneArrotondamenti!=0}">
								<tr>
									<th>Compensazione arrotondamenti <span class="icon icon-list" data-toggle="txt-tooltip" data-placement="top"
										title='Compensazione per arrotondamenti rispetto alla colonna "Contributo calcolato" o alla colonna nascosta "Contributo abbattuto"'">
											</span></th>
									<td style="padding-left: 20px; min-width: 160px" class="numero"><fmt:formatNumber value="${contributo.compensazioneArrotondamenti}"
											pattern="###,##0.00" /> &euro;</td>
								</tr>
							</c:if>
							<tr>
								<th>Contributo non erogabile</th>
								<td style="padding-left: 20px" class="numero"><fmt:formatNumber value="${contributo.contributoNonErogabile}" pattern="###,##0.00" /> &euro;</td>
							</tr>
							<tr>
								<th>Di cui totale sanzioni/riduzioni</th>
								<td style="padding-left: 20px" class="numero"><fmt:formatNumber value="${contributo.importoSanzioni}" pattern="###,##0.00" /> &euro;</td>
							</tr>
						</table>
					</m:panel>
				</c:forEach>
				<br />
			</form>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
    function importoAdder(rows, field)
    {
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
    function flagInterventoCompletatoFormatter($value, row, index)
    {
      try
      {
        var checked = "";
        if ($value == 'S')
        {
          checked = ' checked="checked" ';
        }

        return '<div class="center"><input '+ checked+' disabled="disabled"'
          + ' data-toggle="bs-toggle" type="checkbox" value="S"/></div>';
      }
      catch (e)
      {

      }
    }
    $('#tblInterventi').on('load-success.bs.table', function()
    {
      $(function()
      {
        $('input[data-toggle="bs-toggle"]').bootstrapToggle();
      });
      $()
    });
    $('#tblInterventi').on('column-switch.bs.table', function()
    {
      $(function()
      {
        $('input[data-toggle="bs-toggle"]').bootstrapToggle()
      });
    });
    function progressivoFormatter($value, row, index)
    {
      if ($value == null || $value == '')
      {
        $value = '&nbsp;&nbsp;';
      }
      $value = '<span class="badge" style="background-color:white;color:black;border:1px solid black">' + $value + '</span>';
      return '<div style="text-align:center">' + $value + '</div>';
    }
    function iconeFormatter($value, row, index)
    {
      var html = '';

      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
       */
      if (row['usaDocumentiSpesa'] != 'S')
      {
        html = '<a href="../cunembo${cuNumber}m/modifica_singola_'+$value+'.do" class="ico24 ico_modify"></a>';
      }
      /*
      </p:abilitazione-azione>
       */
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="LOCALIZZA">
       */
      if (row['idTipoLocalizzazione'] == 8)
      {
        html += '<a href="../cunembo${255}i/modifica_'+$value+'.do" class="ico24 ico_map_point"></a>';
        $('#importaGIS').show();
      }
      /*
      </p:abilitazione-azione>
       */
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="DETTAGLIO">
       */
      if (row['idTipoLocalizzazione'] == 8)
      {
        html += '<a href="../cunembo${255}di/dettaglio_'+$value+'.do" class="ico24 ico_magnify"></a>';
      }
      /*
      </p:abilitazione-azione>
       */

      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="ACCERTA_DOC_SPESA">
       */
      if (row['usaDocumentiSpesa'] == 'S')
      {
        html += '<a href="../cunembo${cuNumber}r/accertamento_documenti_singola_'+$value+'.do" class="ico24 ico_modify"></a>';
      }
      /*
      </p:abilitazione-azione>
       */
      /*
      <p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="VISUALIZZA_DOC_SPESA">
       */
      if (row['usaDocumentiSpesa'] == 'S' && row['hasDocumentoSpesa'] == 'S')
      {
        html += '<a href="../cunembo${cuNumber}v/visualizza_'+$value+'.do" class="ico24 ico_magnify"></a>';

        //html += '&nbsp;<span id="espandi_'+$value+'"><a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-plus-sign"  href=\"#'+$value+'\" onclick=\"visualizzainterventi('+$value+');\"></a></span>';
      }
      /*
      </p:abilitazione-azione>
       */

      if (html == '')
      {
        html = '&nbsp;&nbsp;';
      }
      return '<div style="text-align:center">' + html + '</div>';
    }
    function selezioneMultiplaCheckboxFormatter($value, row, index)
    {
      return '<input type="checkbox" name="idIntervento" value="'+$value+'" />';
    }

    var tmpfoot;
    function visualizzainterventi(idIntervento)
    {
      $.ajax(
      {
        type : "GET",
        url : "../cunembo${cuNumber}v/visualizza_" + idIntervento + ".do",
        dataType : "html",
        async : false,
        success : function(data)
        {
          $('tbody input[value=' + idIntervento + ']').closest('tr').after(
              '<tr id=\"dettaglioimporti_'+idIntervento+'\"><td colspan=\"16\">' + data + '</td></tr>');
          $('#espandi_' + idIntervento).html(
              '<a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-minus-sign"  href=\"#'
                  + idIntervento + '\" onclick=\"nascondiinterventi(' + idIntervento + ');\"></a>')
          tmpfoot = $('#tblInterventi tfoot').html();
          $('#rendicontazioneDocumenti').bootstrapTable();
          $('#rendicontazioneDocumenti').on('all.bs.table', function()
          {
            $(function()
            {
              $('#tblInterventi tfoot').html(tmpfoot);
            });
          });

        }
      });
    }

    function nascondiinterventi(idIntervento)
    {
      $('#dettaglioimporti_' + idIntervento).remove();
      $('#espandi_' + idIntervento).html(
          '<a style="text-decoration: none;font-size:18px;vertical-align:middle;margin-bottom:1em;" class="glyphicon glyphicon-plus-sign"  href=\"#'
              + idIntervento + '\" onclick=\"visualizzainterventi(' + idIntervento + ');\"></a>')
    }

    function selectAll()
    {
      $("input[name='idIntervento']").prop("checked", $('#chkSelectAll').prop('checked'));
    }
    $(function()
    {
      $('[data-toggle="popover"]').popover()
    });
  </script>
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<script type="text/javascript">
      function modificaMultipla()
      {
        if ($("input[name='idIntervento']:checked").length > 0)
        {
          submitFormTo($('#elencoForm'), '../cunembo${cuNumber}m/modifica_multipla.do');
        }
        else
        {
          alert("Selezionare almeno un intervento");
        }
        return false;
      }
    </script>
	</p:abilitazione-azione>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />