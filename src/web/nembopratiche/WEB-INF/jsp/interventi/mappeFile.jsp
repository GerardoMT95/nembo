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
		<m:panel id="panelMappaFile">
		<form name="elencoForm" id="elencoForm" method="post" action="">
			<table id="tblMappeFile" summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
				data-url="json/mappe_file_${idIntervento}.json" data-undefined-text=''>
				<thead>
					<tr>
						<th data-width='50px' data-field="idFileAllegatiIntervento" data-formatter="iconeFormatter"><a href="../cunembo${cuNumber}i/index.do"
							onclick="return openPageInPopup('../cunembo${cuNumber}z/allega_file_${idIntervento}.do','dlgInserisci','Inserisci interventi','modal-large',false)"
							class="ico24 ico_add_white"></a> 
							<a href="../cunembo${cuNumber}m/elimina.do" onclick="return eliminaMultiplo()" class="ico24 ico_trash"></a></th>
						<th data-width='16px' style="width: 1em" data-field="idFileAllegatiIntervento" data-formatter="allegatiCheckboxFormatter"><input type="checkbox"
							name="chkSelectAll" id="chkSelectAll" onclick="selectAll()" /></th>
						<th data-field="nomeLogico">Nome allegato</th>
						<th data-field="nomeFisico" data-formatter="fileFormatter">File</th>
					</tr>
				</thead>
			</table>
			<br />
		</form>
		
		<input type="button" class="btn btn-primary" value="indietro" onclick="window.location.href='../cunembo${cuNumber}l/index.do'" /> <br />
		<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script type="text/javascript">
    function allegaFile(id)
    {
      $.ajax(
      {
        type : "GET",
        url : '../cunembo${cuNumber}z/allega_file' + id + '.do',
        dataType : "html",
        async : false,
        success : function(data, textStatus)
        {
          setDialogHtml(data);
        }
      });
    }

    function iconeFormatter($value, row, index)
    {
      return '<div style="text-align:center"><a href="#" onclick="return eliminaAllegato(' + ${idIntervento}
      +',' + $value + ')" class="ico24 ico_trash"></a></div>';
    }

    function fileFormatter($value, row, index)
    {
      var href = 'visualizza_allegato_' + ${idIntervento}
      +"_" + row['idFileAllegatiIntervento'] + '.do';
      return '<table cellspacing="0" cellpadding="0" style="border:none !important"><tr><td style="border:none !important;width:32px"><a href="'+href+'" class="ico32 '+row['iconClassMimeType']+'"></a></td><td style="border:none !important;vertical-align:middle"><a href="'+href+'" style="padding-left:8px">'
          + $value + '</a></td></tr></table>';
    }

    function setDialogHtml(data)
    {
      $('#dlgInserisci .modal-body').html(data);
      doErrorTooltip();
    }

    function allegatiCheckboxFormatter($value, row, index)
    {
      return '<input type="checkbox" name="idFileAllegatiIntervento" value="'+$value+'" />';
    }
    function selectAll()
    {
      $('input[name="idFileAllegatiIntervento"]').prop("checked", $('#chkSelectAll').prop('checked'));
    }
    function eliminaMultiplo()
    {
      var $selezionati=$('input[name="idFileAllegatiIntervento"]:checked');
      if ($selezionati.length==0)
      {
        alert('Selezionare almeno un allegato');
      }
      else
      {
        openPageInPopup('../cunembo${cuNumber}z/elimina_allegati_${idIntervento}.do', 'dlgEliminaAllegato', 'Eliminazione allegato',
            null, false, $selezionati.serialize());
      }
      return false;
    }
    function eliminaAllegato(id1, id2)
    {
      return openPageInPopup('../cunembo${cuNumber}z/elimina_allegato_' + id1 + '_' + id2 + '.do', 'dlgEliminaAllegato', 'Eliminazione allegato')
      $.ajax(
      {
        type : "GET",
        url : '../cunembo${cuNumber}z/elimina_allegato_' + id1 + '_' + id2 + '.do',
        dataType : "html",
        async : false,
        success : function(data, textStatus)
        {
          $('#tblMappeFile').bootstrapTable('remove',
          {
            field : 'idFileAllegatiIntervento',
            values : [ id2 ]
          });
        },
        error : function()
        {
          alert("si è verificato un errore di sistema nell'eliminazione dell'allegato, se il problema persistesse contattare l'assistenza tecnica");
        }
      });
    }
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />