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

<style>
	.nopadding {
	   padding: 0 !important;
	   margin: 0 !important;
	}
</style>
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
				<div id="errorBoxDiv" class='alert alert-danger' style='display:none;'>
					<h4>Per continuare &egrave; necessario selezionare almeno una conduzione su cui segnalare i danni.</h4>
				</div>
				<c:set var ="tableName"  value ="tblConduzioni"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="${tableName}"
					class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
					data-toggle="table"
					data-pagination=true
					data-url="${dataUrl}" 
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
				>
				<thead>
					<tr>
					<th data-field="id" data-formatter="idUtilizzoDichiaratoEliminaFormatter"></th>
					<th data-field="comune" data-sortable="true">Comune</th>
					<th data-field="sezione" data-sortable="true">Sezione</th>
					<th data-field="foglio" data-sortable = "true">Foglio</th>
					<th data-field="particella" data-sortable = "true">Particella</th>
					<th data-field="subalterno" data-sortable = "true">Subalterno</th>
					<th data-field="supCatastale" data-formatter="numberFormatter4" data-sortable = "true" >Superficie catastale</th>
					<th data-field="occupazioneSuolo" data-sortable = "true">Occupazione del suolo</th>
					<th data-field="destinazione" class="alignRight" data-sortable = "true">Destinazione</th>
					<th data-field="uso">Uso</th>
					<th data-field="qualita">Qualità</th>
					<th data-field="varieta">Varietà</th>
					<th data-field="superficieUtilizzata" data-formatter="numberFormatter4">Superficie utilizzata</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
				</table>
		</m:panel>
		<input type="button" class="btn btn-default" value="indietro" onclick="window.location.href='../cunembo${cuNumber}l/index.do'"/>
		<input type="button" class="btn btn-primary pull-right" value="conferma" onclick="onConfermaConduzioni()" />
  <br class="clear"/>
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

<script src="/nembopratiche/js/dati_geografici.js"></script>



<script type="text/javascript">
    
	function visualizzaErroreNoConduzioni()
	{
	    var modifica = "${modifica}";
    	var errorNoConduzioni = "${errorNoConduzioni}";
    	if(errorNoConduzioni != null && errorNoConduzioni=='true')
    	{
    		$('#errorBoxDiv').show();
    	}
	}
    
    var modificaDanni = false;
    //funzione chiamata al click del bottone per la ricerca
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
            name : 'idUtilizzoDichiarato',
            value : row['id']
          }).appendTo($hiddens);
        });
      }
      var $form = $('#formFiltri');
      $.ajax(
      {
        type : "GET",
        url : "../cunembo${cu}/popup_ricerca_conduzioni.do",
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
    
    function idUtilizzoDichiaratoEliminaFormatter($value, row, index) 
    {
      //	return 	'<a class="ico24 ico_trash" onclick="return eliminaConduzione(\''+row['idUtilizzoDichiarato']+'\')"></a>';
      	return 	'<a class="ico24 ico_trash" onclick="return eliminaConduzione(\''+$value+'\')"></a>';
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
	           name : 'idUtilizzoDichiarato',
	           value : row['idUtilizzoDichiarato']
	         }).appendTo($hiddens);
	       });
	     }
	     $('#formFiltri').submit();
    }    
  </script>
  <c:choose>
	  <c:when test="${json != null}">
	   	  	<script type="text/javascript">
	  	  	    $(document).ready(function() {
					visualizzaErroreNoConduzioni();
					var json = ${json};
					var data = $('#tblConduzioni').bootstrapTable("append", json);
			    });
  	  	    </script>
	  </c:when>
  	  <c:otherwise>
 	  	 	<script type="text/javascript">
	  	  	    $(document).ready(function() {
					visualizzaErroreNoConduzioni();
			    });
  	  	    </script>
  	  </c:otherwise>
  </c:choose>
  
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />