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
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<style>
#panelModificaRendicontazioneDocumenti .panel-body {
	padding-top: 0px !important;
	margin-top: 0px !important
}

#rendicontazioneDocumenti {
	padding-top: 0px !important;
	margin-top: 0px !important
}
</style>
<body>
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form name="mainForm" method="post" action="${action}">
		<div class="container-fluid" id="content">
			<br />
			<m:error />
			<c:forEach items="${interventi.values()}" var="intervento">
				<input type="hidden" name="idIntervento" value="${intervento.idIntervento}" />
				<input type="hidden" id="numberOfDateFilter" value="2">
				<m:panel id="panelModificaRendicontazioneDocumenti" title="${intervento.descrizioneEstesa}">
					<c:if test="${msgIVA!=null}">
					<br/>
						<strong><c:out value="${msgIVA}" /></strong>
						<br />
					</c:if>
          <table summary="info Interventi" class="table table-hover table-bordered tableBlueTh " style="margin-top:1em">
            <thead>
              <tr>
                <th style="width:80px">Intervento completato</th>
                <th>Note</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td style="text-align:center"><input name="flagInterventoCompletato_${intervento.idIntervento}" data-toggle="bs-toggle" type="checkbox" value="S"
              <c:if test="${intervento.flagInterventoCompletato=='S'}"> checked="checked" </c:if> /></td>
                <td><m:textarea name="note_${intervento.idIntervento}" style="min-width:128px"
                    id="note_${intervento.idIntervento}" preferRequestValues="${preferRequest}">${intervento.note}</m:textarea></td>
              </tr>
            </tbody>
          </table>  
					<div id="filter-bar_${intervento.idIntervento}" style="float: left; position: relative; top: 10px"></div>
					<table id='rendicontazioneDocumenti' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh show-totali" data-toggle="table"
						data-url="json/rendicontazione_${intervento.idIntervento}.json" data-undefined-text='' data-show-columns="true">
						<thead>
							<tr>
								<th data-totale="false" data-switchable="true" data-field="ragioneSociale" data-sortable="true">Ragione<br />sociale<br />fornitore
								</th>
								<th data-totale="false" data-switchable="true" data-field="dataDocumentoSpesa" data-sortable="true" data-sorter="dateSorterddmmyyyy">Data<br />documento
								</th>
								<th data-totale="false" data-switchable="true" data-field="numeroDocumentoSpesa" data-sortable="true">Numero<br />doc.<br />spesa
								</th>
								<th data-totale="false" data-switchable="true" data-field="descTipoDocumentoSpesa" data-sortable="true">Tipo<br />documento
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoSpesa" data-adder="plainNumberAdder" data-formatter="numberFormatter2">Importo<br />documento
								</th>
								<th data-totale="true" data-switchable="false" data-field="importo" data-adder="plainNumberAdder" data-formatter="numberFormatter2">Importo<br />associato<br />documento
								</th>
                <th data-totale="true" data-switchable="false" data-field="importoRicevutePagamento" data-adder="plainNumberAdder" data-formatter="numberFormatter2" title="importo ricevute 1">Importo<br />associato<br/>ricevute
                </th>
								<th data-totale="true" data-switchable="false" data-field="importoRendicontatoPrec" data-adder="plainNumberAdder" data-formatter="numberFormatter2">Importo<br />gi&agrave;<br />rendicontato
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoRendicontatoCorrente"  data-formatter="inputFormatter" data-totale-formatter="numberFormatter2">Importo<br />in<br/>rendicontazione
								</th>
								<th data-totale="false" data-switchable="true" data-field="nomeFile" data-formatter="documentoFormatter">File</th>
							</tr>
						</thead>
					</table>
					<br />
				</m:panel>
				<br />
			</c:forEach>
			<input style="display: none;" type="submit" name="confermaModificaRendicontazione" id="confermaModificaRendicontazione" role="s"
				class="btn btn-primary pull-right" value="conferma" /> <a role="button" class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a> <input
				type="submit" name="confermaModificaRendicontazione" role="s" class="btn btn-primary pull-right" value="conferma" /> <br style="clear: left" /> <br /> <br />
		</div>
	</form>
	<div style="display: none" id="invisibleTextfield">
		<div class=":CSS" data-original-title=":ERROR" data-toggle=":TOGGLE" style="min-width: 140px">
			<m:textfield id=":ID" name=":NAME" type="EURO" value=":VALUE" onclick=":ONCLICK" onkeyup=":ONKEYUP" onchange=":ONCHANGE"/>
		</div>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
  <script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
  <script type="text/javascript">
  $('input[data-toggle="bs-toggle"]').bootstrapToggle();

  function calcolaTotale(cssName)
  {
    var totale=0;
    $('.'+cssName+' input').each(function(idx, element)
      {
        var val=Number($(element).val().replace(',','.'));
        if (!isNaN(val))
        {
          totale+=val;
        }
      });
    return totale;
  }
  
  function calcolaImportoRendicontato()
  {
    var totale=calcolaTotale('importo_rendicontato');
    $('#rendicontazioneDocumenti tfoot td[data-field="importoRendicontatoCorrente"] span').html(totale.formatCurrency());
    return totale;
  }
	
    function inputFormatter($value, row, index)
    {
      if ($value == null)
      {
        $value = "";
      }
      
      var html = $('#invisibleTextfield').html();
      var id = "importoRendicontato_" + row['idDocumentoSpesaInterven'];
      html = html.replace(":ID", id);
      html = html.replace(":NAME", id);
      html = html.replace(":VALUE", $value);
      html = html.replace(":ONKEYUP", 'calcolaImportoRendicontato()');
      html = html.replace(":ONCHANGE", 'calcolaImportoRendicontato()');
      if (row['errorMessage'])
      {
        html = html.replace(":CSS", 'has-error red-tooltip importo_rendicontato');
        html = html.replace(":ERROR", row['errorMessage']);
        html = html.replace(":TOGGLE", "error-tooltip");
      }
      else
      {
        html = html.replace(":CSS", 'importo_rendicontato');
        html = html.replace(":ERROR", '');
        html = html.replace(":TOGGLE", '');
      }
      return html;
    }

    function documentoFormatter($value, row, index)
    {
      if ($value == null)
      {
        $value = "";
      }
      var html = ' <a';
      if (row['warningDocumento'])
      {
        html+=' class="text-danger"';
      }
      html+=' href="documento_'+row['idDocumentoSpesa']+'.do">'+$value.replace(/'/g, '&apos;')+'</a>';
      return html;
    }
    
    $('#rendicontazioneDocumenti').on('load-success.bs.table', function()
        {
          doErrorTooltip();
        });

    function initFilter(id)
    {
      $('#filter-bar_'+id).bootstrapTableFilter(
      {
        filters : [
        {
          field : 'ragioneSociale',
          label : 'Ragione sociale',
          type : 'search'
        },
        {
          field : 'dataDocumentoSpesa',
          label : 'Data documento',
          type : 'date'
        },
        {
          field : 'numeroDocumentoSpesa',
          label : 'Numero doc. spesa',
          type : 'search'
        },
        {
          field : 'descTipoDocumentoSpesa',
          label : 'Tipo documento',
          type : 'search'
        } ],
        connectTo : '#rendicontazioneDocumenti'
      });
    }
    $(document).ready(function()
    {
      /*<c:forEach items="${interventi.values()}" var="intervento">*/
      initFilter('${intervento.idIntervento}');
      /*</c:forEach>*/
    });

    (function ($) {
      'use strict';

      $.fn.bootstrapTable.locales['it-IT'] = {
          formatLoadingMessage: function () {
              return 'Caricamento in corso...';
          },
          formatRecordsPerPage: function (pageNumber) {
              return pageNumber + ' records per pagina';
          },
          formatShowingRows: function (pageFrom, pageTo, totalRows) {
              return 'Pagina ' + pageFrom + ' di ' + pageTo + ' (' + totalRows + ' records)';
          },
          formatSearch: function () {
              return 'Cerca';
          },
          formatNoMatches: function () {
              return 'Non ci sono documenti di spesa associati a questo intervento. Impossibile modificare la rendicontazione.';
          },
          formatRefresh: function () {
              return 'Rinfrescare';
          },
          formatToggle: function () {
              return 'Alternare';
          },
          formatColumns: function () {
              return 'Colonne';
          }
      };

      $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['it-IT']);

  })(jQuery);
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />