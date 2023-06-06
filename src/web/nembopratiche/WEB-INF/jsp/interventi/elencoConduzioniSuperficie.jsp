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
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelElenco">
			<m:panel id="panelInserimento" startOpened="true" title="Ricerca">
				<%@include file="include/filtriInserimentoConduzione.jsp"%>
			</m:panel>
			<m:panel id="particelle" startOpened="true" title="Destinazioni produttive localizzate">
				<m:error />
				<table id="tblConduzioni" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " data-toggle="table" data-url="${dataUrlTable}"
					data-undefined-text=''>
					<thead>
						<tr>
							<th data-field="id" data-formatter="stateFormatterIconeConduzione"></th>
							<th data-field="descComune" data-title="Comune" data-valign="middle"></th>
							<th data-field="sezione" data-title="Sez." data-valign="middle"></th>
							<th data-field="foglio" data-title="Foglio" data-valign="middle"></th>
							<th data-field="particella" data-title="Part." data-valign="middle"></th>
							<th data-field="subalterno" data-title="Sub." data-valign="middle"></th>
							<th data-field="supCatastale" data-title="Sup.<br/>Catastale<br/>(ha)" data-formatter="numberFormatter4" data-valign="middle"></th>
							<th data-field="descTipoUtilizzo" data-title="Occupazione<br/>del suolo" title="Occupazione del suolo" data-valign="middle"></th>
							<th data-field="descrizioneDestinazione" data-title="Destinazione" title="Destinazione" data-valign="middle"></th>
							<th data-field="descTipoDettaglioUso" data-title="Uso" title="Uso" data-valign="middle"></th>
							<th data-field="descrizioneQualitaUso" data-title="Qualit&agrave;" title="Qualit&agrave;" data-valign="middle"></th>
							<th data-field="descTipoVarieta" data-title="Variet&agrave;" title="Variet&agrave;" data-valign="middle"></th>
							<th data-field="superficieUtilizzata" data-title="Sup.<br/>Utilizzata<br/>(ha)" data-formatter="numberFormatter4" data-valign="middle"></th>
							<th data-field="superficieImpegno" data-title="Superficie ammessa (ha)" data-formatter="stateFormatterSupInteressata"></th>
						</tr>
					</thead>
				</table>

			</m:panel>
			<input type="button" class="btn btn-default" value="indietro" onclick="window.location.href='../cunembo${cuNumber}l/index.do'" />
			<input type="button" class="btn btn-primary pull-right" value="conferma" onclick="onConfermaConduzioni()" />
			<br class="clear" />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/dati_geografici.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
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
        url : "popup_ricerca_conduzioni_con_superficie.do",
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
            $('#formFiltri [data-toggle="error-tooltip"]').tooltip("disable");
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
      $('#tblConduzioni').bootstrapTable("remove",
      {
        field : 'id',
        values : [ id ]
      });
      return false;
    }
    function stateFormatterIconeConduzione($value, row, index)
    {
      return '<a href="" onclick="return eliminaConduzione(\'' + row['id'] + '\')" class="ico24 ico_trash"></a>';
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
          $input = $('<input>',
          {
            type : 'hidden',
            name : 'superficieImpegno_'+row['id'],
            value : $('#superficieImpegno_'+row['id']).val()
          }).appendTo($hiddens);
        });
      }
      $('#formFiltri').submit();
    }

    function stateFormatterSupInteressata($value, row, index)
    {
      if ($value==null)
      {
        $value='';
      }
      if (row['error'] != null)
      {
        var _error=row['error'];
        if (_error!=null)
        {
          _error=_error.replace(/"/g,'&quot;');
        }
        return '<div class="has-error red-tooltip" data-original-title="'+_error+'" data-toggle="error-tooltip" >'
            + '<input type="text" name="superficieImpegno" id="superficieImpegno_'+row['id']+'" class="form-control" value="'+$value+'">'
            + '</div>';
      }
      else
      {
        return'<input type="text" name="superficieImpegno" id="superficieImpegno_'+row['id']+'" class="form-control" value="'+$value+'">';
      }
    }
  </script>
	<c:if test="${json!=null}">
		<script type="text/javascript">
      var json=${json};
      $(document).ready
      (
        function()
        {
          var data = $('#tblConduzioni').bootstrapTable("append", json);
          doErrorTooltip();
        }
      )
    </script>
	</c:if>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />