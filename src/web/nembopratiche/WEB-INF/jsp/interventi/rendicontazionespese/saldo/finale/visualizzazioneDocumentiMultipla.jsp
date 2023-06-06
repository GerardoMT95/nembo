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
					<div id="filter-bar" style="float: left; position: relative; top: 10px"></div>
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
								<th data-totale="true" data-switchable="false" data-field="importoSpesa" data-formatter="numberFormatter2" data-adder="plainNumberAdder">Importo<br />documento
								</th>
								<th data-totale="true" data-switchable="false" data-field="importo" data-formatter="numberFormatter2" data-adder="plainNumberAdder">Importo<br />da<br />rendicontare
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoRendicontatoPrec" data-formatter="numberFormatter2" data-adder="plainNumberAdder">Importo<br />gi&agrave;<br />rendicontato
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoRendicontatoCorrente" data-formatter="numberFormatter2">Importo<br />in<br/>rendicontazione
								</th>
								<th data-totale="false" data-switchable="true" data-field="nomeFile" data-formatter="documentoFormatter">File</th>
							</tr>
						</thead>
					</table>
					<br />
				</m:panel>
				<br />
			</c:forEach>
      <a role="button" class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a> <br /> <br />
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">

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
    function initFilter()
    {
      $('#filter-bar').bootstrapTableFilter(
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
      initFilter();
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