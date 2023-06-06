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
<body>
  <p:set-cu-info/>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica/><p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelElenco">
		<m:panel id="panelInserimento" startOpened="true" title="Ricerca">
			<%@include file="include/filtriInserimentoConduzione.jsp"%>
		</m:panel>
		<m:panel id="particelle" startOpened="true" title="Destinazioni produttive localizzate">
		<m:error/>
			<table id="tblConduzioni" class="table table-hover tableBlueTh" data-toggle="table" data-url="json/elenco_conduzioni_${idIntervento}.json" data-undefined-text='' data-checkbox-header="true">
				<thead>
					<tr>
						<th data-field="id" data-formatter="stateFormatterIconeConduzione"></th>
						<th data-field="descComune">Comune</th>
						<th data-field="sezione">Sezione</th>
						<th class="alignRight" data-field="foglio">Foglio</th>
						<th class="alignRight" data-field="particella">Particella</th>
						<th data-field="subalterno">Subalterno</th>
						<th class="alignRight" data-field="supCatastale">Sup. catastale</th>
						<th data-field="descTipoUtilizzo">Destinazione produttiva</th>
						<th class="alignRight" data-field="superficieUtilizzata">Sup. utilizzata</th>
				</thead>
			</table>
		</m:panel>
		<input type="button" class="btn btn-default" value="indietro" onclick="window.location.href='../cunembo${cuNumber}l/index.do'"/> <input type="button" class="btn btn-primary pull-right" value="conferma"
			onclick="onConfermaConduzioni()" />
  <br class="clear"/>
  </m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/dati_geografici.js"></script>
	<script type="text/javascript">
    function openInsertPopup()
    {
      $hiddens = $('#hiddens');
      $hiddens.empty();
      var tableData = $('#tblConduzioni').bootstrapTable('getData');
      if (tableData != null && tableData.length)
      {
        $.each(tableData, function(index, row)
        {
          $input = $('<input>',
          {
            type : 'hidden',
            name : 'idChiaveConduzione',
            value : row['id']
          }).appendTo($hiddens);
        });
      }
      var $form = $('#formFiltri');
      $.ajax(
      {
        type : "GET",
        url : "popup_ricerca_conduzioni.do",
        dataType : "html",
        data : $form.serialize(),
        async : false,
        success : function(data)
        {
          if (data.indexOf('formFiltri') >= 0)
          {
            $('#formFiltri').html(data);
          }
          else
          {
            $('#formFiltri .has-error').removeClass('has-error');
            $('#formFiltri [data-toggle="error-tooltip"]').tooltip( "disable" );
            openPageInPopup(null, 'dlgRicercaEInserisciConduzione', 'Elenco particelle condotte', 'modal-lg', false);
            $('#dlgRicercaEInserisciConduzione .modal-body').html(data);
            $('#tblRicercaConduzioni').bootstrapTable();
          }
          doErrorTooltip();
        }
      })
    }
    function eliminaConduzione(id)
    {
      $('#tblConduzioni').bootstrapTable("remove",{field:'id', values:[id]});
      return false;
    }
    function stateFormatterIconeConduzione($value, row, index) 
    {
      return '<a href="" onclick="return eliminaConduzione(\''+row['id']+'\')" class="ico24 ico_trash"></a>';
    }

    function onConfermaConduzioni()
    {
      $hiddens = $('#hiddens');
      $hiddens.empty();
      var tableData = $('#tblConduzioni').bootstrapTable('getData');
      if (tableData != null && tableData.length)
      {
        $.each(tableData, function(index, row)
        {
          $input = $('<input>',
          {
            type : 'hidden',
            name : 'idChiaveConduzione',
            value : row['id']
          }).appendTo($hiddens);
        });
      }
      $('#formFiltri').submit();
    }    
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />