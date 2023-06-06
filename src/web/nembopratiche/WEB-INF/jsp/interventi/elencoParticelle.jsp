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
		<m:panel id="panelParticelle">
		<m:panel id="panelInserimento" startOpened="true" title="Ricerca">
			<%@include file="include/filtriInserimentoParticelle.jsp"%>
		</m:panel>
		<m:panel id="particelle" startOpened="true" title="Particelle localizzate">
		<m:error/>
			<table id="tblParticelle" class="table table-hover tableBlueTh" data-toggle="table" data-url="json/elenco_particelle_${idIntervento}.json" data-undefined-text='' data-checkbox-header="true">
				<thead>
					<tr>
						<th data-field="idParticellaCertificata" data-formatter="stateFormatterIconeParticella">
							</th>
						<th data-field="descComune">Comune</th>
						<th data-field="sezione">Sezione</th>
						<th class="alignRight" data-field="foglio">Foglio</th>
						<th class="alignRight" data-field="particella">Particella</th>
						<th data-field="subalterno">Subalterno</th>
						<th class="alignRight" data-field="supCatastale">Sup. catastale</th>
				</thead>
			</table>
		</m:panel>
		<input type="button" class="btn btn-default" value="indietro" onclick="window.location.href='../cunembo${cuNumber}l/index.do'"/> <input type="button" class="btn btn-primary pull-right" value="conferma"
			onclick="onConfermaParticelle()" />
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
      var tableData = $('#tblParticelle').bootstrapTable('getData');
      if (tableData != null && tableData.length)
      {
        $.each(tableData, function(index, row)
        {
          $input = $('<input>',
          {
            type : 'hidden',
            name : 'idParticellaCertificata',
            value : row['idParticellaCertificata']
          }).appendTo($hiddens);
        });
      }
      var $form = $('#formFiltri');
      $.ajax(
      {
        type : "GET",
        url : "popup_ricerca_particelle.do",
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
            openPageInPopup(null, 'dlgRicercaEInserisciParticella', 'Elenco particelle', 'modal-lg', false);
            $('#dlgRicercaEInserisciParticella .modal-body').html(data);
            $('#tblRicercaParticelle').bootstrapTable();
          }
          doErrorTooltip();
        }
      })
    }
    function eliminaParticella(id)
    {
      $('#tblParticelle').bootstrapTable("remove",{field:'idParticellaCertificata', values:[id]});
      return false;
    }
    function stateFormatterIconeParticella($value, row, index) 
    {
      return '<a href="#" onclick="return eliminaParticella('+row['idParticellaCertificata']+')" class="ico24 ico_trash"></a>';
    }

    function onConfermaParticelle()
    {
      $hiddens = $('#hiddens');
      $hiddens.empty();
      var tableData = $('#tblParticelle').bootstrapTable('getData');
      if (tableData != null && tableData.length)
      {
        $.each(tableData, function(index, row)
        {
          $input = $('<input>',
          {
            type : 'hidden',
            name : 'idParticellaCertificata',
            value : row['idParticellaCertificata']
          }).appendTo($hiddens);
        });
      }
      $('#formFiltri').submit();
    }    
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />