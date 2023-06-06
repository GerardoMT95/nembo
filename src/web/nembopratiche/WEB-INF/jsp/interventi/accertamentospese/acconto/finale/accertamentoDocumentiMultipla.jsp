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
	<form id="mainForm" name="mainForm" method="post" action="${action}">
    <input type="hidden" id="warningConfirmed" name="warningConfirmed" value="${warningConfirmed}" /> 
		<div class="container-fluid" id="content">
			<br />
      <m:error errorName="noRendicontazione"/>
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
					
					<input type="hidden" id="spesaAmmessa" name="spesaAmmessa" value="${intervento.spesaAmmessa}" />
					<input type="hidden" id="spesaRendicontataAttuale" name="spesaRendicontataAttuale" value="${intervento.spesaRendicontataAttuale}" />
					<table summary="info Interventi" class="table table-hover table-bordered tableBlueTh " style="margin-top:1em">
						<thead>
							<tr>
								<th style="width:80px">Intervento completato</th>
								<th style="width:240px">Importo non<br/>riconosciuto sanzionabile</th>
								<th style="width:240px">Importo non<br/>riconosciuto non sanzionabile</th>
								<th>Note</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="text-align:center"><input name="flagInterventoCompletato_${intervento.idIntervento}" data-toggle="bs-toggle" type="checkbox" value="S"
							<c:if test="${intervento.flagInterventoCompletato=='S'}"> checked="checked" </c:if> /></td>
								<td style="width:172px"><m:textfield name="importoNonRiconosciutoSanzionabile" id="importoNonRiconosciutoSanzionabile"
								disabled="${intervento.spesaRendicontataAttuale==0}" 
								value="${intervento.importoNonRiconosciutoSanzionabile}" preferRequestValues="${preferRequest && intervento.spesaRendicontataAttuale>0}" type="EURO" onkeyup="calcolaImportoNonRiconosciutoNonSanzionabileGlobaleDaSanzionabile()" onchange="calcolaImportoNonRiconosciutoNonSanzionabileGlobaleDaSanzionabile()"/></td>
								<td style="width:172px;text-align:center" id="importoNonRiconosciutoNonSanzionabile"><fmt:formatNumber value="${intervento.importoNonRiconosciutoNonSanzionabile}" pattern="#,##0.00"/></td>
								<td><m:textarea name="note_${intervento.idIntervento}" style="min-width:128px"
										id="note_${intervento.idIntervento}" preferRequestValues="${preferRequest}">${intervento.note}</m:textarea></td>
							</tr>
						</tbody>
					</table>	
					
					<div id="filter-bar_${intervento.idIntervento}" style="float: left; position: relative; top: 10px"></div>
					<table id='rendicontazioneDocumenti' summary="Elenco Interventi" class="table table-hover table-bordered tableBlueTh show-totali" data-toggle="table"
						data-url="json/accertamento_${intervento.idIntervento}.json" data-undefined-text='' data-show-columns="true">
						<thead>
							<tr>
								<th data-totale="false" data-switchable="true" data-field="ragioneSociale" data-sortable="true">Ragione<br />sociale<br />fornitore
								</th>
								<th data-totale="false" data-switchable="true" data-field="dataDocumentoSpesa" data-sortable="true" data-sorter="dateSorterddmmyyyy">Data<br />documento
								</th>
								<th data-totale="false" data-switchable="true" data-field="numeroDocumentoSpesa" data-sortable="true">Numero<br />doc.<br />spesa
								</th>
								<th data-totale="false" data-switchable="true" data-visible="false" data-sortable="true" data-field="descTipoDocumentoSpesa">Tipo<br />documento
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoSpesa" data-adder="totalFormattedAdder" data-formatter="numberFormatter2">Importo<br />documento
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoRendicontato"  data-adder="totalFormattedAdder" data-totale-formatter="numberFormatter2" data-formatter="inputFormatterRendicontato">Spesa<br />rendicontata<br />attuale
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoAccertato" data-adder="totalNumeriUserInputAdder" data-totale-formatter="numberFormatter2" data-formatter="inputFormatterAccertato">Spese<br />accertate<br />attuali
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoCalcoloContributo" data-adder="totalNumeriUserInputAdder" data-totale-formatter="numberFormatter2" data-formatter="inputFormatterCalcoloContributo">Spesa<br /> riconosciuta<br /> per il<br /> calcolo <br />del <br />contributo
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoNonRiconosciuto"  data-adder="totalNumeriUserInputAdder" data-totale-formatter="numberFormatter2" data-formatter="inputFormatterNonRiconosciuto">Importo<br />non<br />riconosciuto<br /> sanzionabile
								</th>
								<th data-totale="true" data-switchable="false" data-field="importoDisponibile"  data-adder="totalNumeriUserInputAdder" data-totale-formatter="numberFormatter2" data-formatter="inputFormatterNonRiconosciutoNonSanzionabile">Importo<br />non<br />riconosciuto<br /> non<br />sanzionabile
								</th>
								<th data-totale="false" data-switchable="true" data-field="nomeFile" data-formatter="documentoFormatter">File</th>
							</tr>
						</thead>
					</table>
					<br />
				</m:panel>
				<br />
			</c:forEach>
			<input type="hidden" name="confermaModificaRendicontazione" id="confermaModificaRendicontazione" role="s"
				class="btn btn-primary pull-right" value="conferma" /> <a role="button" class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a> <input
				type="submit" name="confermaModificaRendicontazioneBtn" role="s" class="btn btn-primary pull-right" value="conferma" /> <br style="clear: left" /> <br /> <br />
		</div>
	</form>
	<div style="display: none" id="invisibleTextfield">
		<div class=":CSS" data-original-title=":ERROR" data-toggle=":TOGGLE" style="min-width: 105px; white-space:nowrap !important">
			<m:textfield id=":ID" name=":NAME" value=":VALUE"/>
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
    function aggiornaImportoDisponibile(idDocumentoSpesaInterven)
    {
        var disp = Number($('#importoRendicontato_'+idDocumentoSpesaInterven).html().replace(',','.')) - 
        		   $('#importoCalcoloContributo_'+idDocumentoSpesaInterven).val() -
                   $('#importoNonRiconosciuto_'+idDocumentoSpesaInterven).val();
        try
  	    {
  	    	$number=Number(disp);
			$('#importoDisponibile_'+idDocumentoSpesaInterven).html($number.formatCurrency(2));
			$('#importoDisponibileh_'+idDocumentoSpesaInterven).val($number.formatCurrency(2));
			
  	    }
  	    catch(e)
  	    {
  	    }
        
	}
    function inputFormatterNonRiconosciutoNonSanzionabile($value, row, index)
    {
      var id = "importoDisponibile_" + row['idDocumentoSpesaInterven'];
      var html = inputFormatter($value, row, index, id, ' onkeyup=calcolaTotaleImportoNonRiconosciutoNonSanzionabile() onchange=calcolaTotaleImportoNonRiconosciutoNonSanzionabile()');
      if (row['errorMessageImportoDisponibile'])
          {
            html = html.replace(":CSS", 'has-error red-tooltip importo_non_riconosciuto_non_sanzionabile forza_uguale_'+index);
            html = html.replace(":ERROR", row['errorMessageImportoDisponibile']);
            html = html.replace(":TOGGLE", "error-tooltip");
          }
          else
          {
            html = html.replace(":CSS", 'importo_non_riconosciuto_non_sanzionabile forza_uguale_'+index);
          }
      return html;
    }

    function inputFormatterRendicontato($value, row, index)
    {
    	try
    	  {
    	    $number=Number($value);
    	    if (isNaN($number))
    	    {
    	    	return '<span class="pull-right primo_uguale_'+index+'" data-value="'+$value+'">'+$value+'</span>';
    	    }
    	    return '<span class="pull-right primo_uguale_'+index+'" id="importoRendicontato_'+ row['idDocumentoSpesaInterven']+'" data-value="'+$value+'">'+$number.formatCurrency(2)+'</span>';
    	  }
    	  catch(e)
    	  {
    	    
    	  }
    }

    function calcolaTotaleImportoNonRiconosciuto()
    {
      aggiornaTotale("importo_non_riconosciuto","importoNonRiconosciuto");
    }

    function calcolaTotaleImportoNonRiconosciutoNonSanzionabile()
    {
      aggiornaTotale("importo_non_riconosciuto_non_sanzionabile","importoDisponibile");
    }

    function calcolaTotaleImportoCalcoloContributo()
    {
      aggiornaTotale("importo_calcolo_contributo","importoCalcoloContributo");
      var $speseAccertateAttuali=calcolaTotale("importo_accertato","importoAccertato");
      var $spesaRendicontataAttuale=getSpesaRendicontataAttuale();
      var $spesaAmmessa=getSpesaAmmessa();
      calcolaImportoNonRiconosciutoSanzionabileGlobale($spesaAmmessa, $speseAccertateAttuali, $spesaRendicontataAttuale);
    }
    
    function calcolaTotaleSpeseAccertateAttuali()
    {
      var $speseAccertateAttuali=aggiornaTotale("importo_accertato","importoAccertato");
      var $spesaRendicontataAttuale=getSpesaRendicontataAttuale();
      var $spesaAmmessa=getSpesaAmmessa();
      calcolaImportoNonRiconosciutoSanzionabileGlobale($spesaAmmessa, $speseAccertateAttuali, $spesaRendicontataAttuale);
    }
    
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
    
    function aggiornaTotale(cssName, dataField)
    {
      var totale=calcolaTotale(cssName);
      $('#rendicontazioneDocumenti tfoot td[data-field="'+dataField+'"] span').html(totale.formatCurrency());
      return totale;
    }

    function min(v1,v2)
    {
      return (v1<v2)?v1:v2;
    }

    function getSpesaRendicontataAttuale()
    {
      return Number($('#spesaRendicontataAttuale').val());
    }
    
    function getSpesaAmmessa()
    {
      return Number($('#spesaAmmessa').val());
    }
    
    function calcolaImportoNonRiconosciutoSanzionabileGlobale($spesaAmmessa, $speseAccertateAttuali, $spesaRendicontataAttuale)
    {
      var $importoNonRiconosciutoSanzionabileGlobale="0,00";
      if (!isNaN($spesaRendicontataAttuale) && !isNaN($speseAccertateAttuali) && !isNaN($spesaAmmessa))
      {
        $importoNonRiconosciutoSanzionabileGlobale = $spesaRendicontataAttuale - $speseAccertateAttuali;
        $importoNonRiconosciutoSanzionabileGlobale = Math.round($importoNonRiconosciutoSanzionabileGlobale*100) / 100;
        $importoNonRiconosciutoSanzionabileGlobale = (""+$importoNonRiconosciutoSanzionabileGlobale).replace('.',',');
      }
      $('#importoNonRiconosciutoSanzionabile').val($importoNonRiconosciutoSanzionabileGlobale);
      return calcolaImportoNonRiconosciutoNonSanzionabileGlobale($spesaAmmessa, $spesaRendicontataAttuale, $importoNonRiconosciutoSanzionabileGlobale);
    }
    function calcolaImportoNonRiconosciutoNonSanzionabileGlobale($spesaAmmessa, $spesaRendicontataAttuale, $importoNonRiconosciutoSanzionabileGlobale)
    {
      var $speseRiconosciutaPerIlCalcolo = calcolaTotale("importo_calcolo_contributo","importoCalcoloContributo")
      $importoNonRiconosciutoSanzionabileGlobale = Number((''+$importoNonRiconosciutoSanzionabileGlobale).replace(',','.'));
      var importoNonRiconosciutoNonSanzionabile="0,00";
      if (!isNaN($spesaRendicontataAttuale) && !isNaN($speseRiconosciutaPerIlCalcolo) && !isNaN($importoNonRiconosciutoSanzionabileGlobale))
      {
        importoNonRiconosciutoNonSanzionabile = $spesaRendicontataAttuale - $speseRiconosciutaPerIlCalcolo - $importoNonRiconosciutoSanzionabileGlobale;
        importoNonRiconosciutoNonSanzionabile = Math.round(importoNonRiconosciutoNonSanzionabile*100) / 100;
        importoNonRiconosciutoNonSanzionabile = importoNonRiconosciutoNonSanzionabile.formatCurrency();
      }
      $('#importoNonRiconosciutoNonSanzionabile').html(importoNonRiconosciutoNonSanzionabile);
    }
    function calcolaImportoNonRiconosciutoNonSanzionabileGlobaleDaSanzionabile()
    {
      var $spesaRendicontataAttuale=getSpesaRendicontataAttuale();
      var $spesaAmmessa=getSpesaAmmessa();
      var $importoNonRiconosciutoSanzionabileGlobale=Number($('#importoNonRiconosciutoSanzionabile').val().replace(',','.'));
      calcolaImportoNonRiconosciutoNonSanzionabileGlobale($spesaAmmessa, $spesaRendicontataAttuale, $importoNonRiconosciutoSanzionabileGlobale);
    }

    function verificaImportiUguali($index)
    {
      var primoValore = Number($('.primo_uguale_'+$index).data('value'));
      var uguali=true;
      $('.verifica_uguale_'+$index+" input").each
      (
        function(idx, obj)
        {
          var valoreTextField=Number($(obj).val().replace(',','.'));
          if (valoreTextField!=primoValore)
          {
            uguali=false;
          }
        }
      )
      if (uguali)
      {
        $('.forza_uguale_'+$index+' input').val("0,00");
        calcolaTotaleImportoNonRiconosciutoNonSanzionabile();
        calcolaTotaleImportoNonRiconosciuto();
      }
    }
	
	function inputFormatterAccertato($value, row, index)
    {
		var id = "importoAccertato_" + row['idDocumentoSpesaInterven'];
		var html = inputFormatter($value, row, index, id, ' onchange="verificaImportiUguali('+index+');calcolaTotaleSpeseAccertateAttuali()" onkeyup="verificaImportiUguali('+index+');calcolaTotaleSpeseAccertateAttuali()"');
		if (row['errorMessageImportoAccertato'])
    {
      html = html.replace(":CSS", 'has-error red-tooltip importo_accertato verifica_uguale_'+index);
      html = html.replace(":ERROR", row['errorMessageImportoAccertato']);
      html = html.replace(":TOGGLE", "error-tooltip");
    }
    else
    {
      html = html.replace(":CSS", 'importo_accertato verifica_uguale_'+index);
    }
		return html;
	}

	function inputFormatterNonRiconosciuto($value, row, index)
    {
		var id = "importoNonRiconosciuto_" + row['idDocumentoSpesaInterven'];
		var html =  inputFormatter($value, row, index, id, ' onkeyup="calcolaTotaleImportoNonRiconosciuto();aggiornaImportoDisponibile('+row['idDocumentoSpesaInterven']+')" onchange="calcolaTotaleImportoNonRiconosciuto();aggiornaImportoDisponibile('+row['idDocumentoSpesaInterven']+');"');
		if (row['errorMessageImportoNonRiconosciuto'])
        {
          html = html.replace(":CSS", 'has-error red-tooltip importo_non_riconosciuto forza_uguale_'+index);
          html = html.replace(":ERROR", row['errorMessageImportoNonRiconosciuto']);
          html = html.replace(":TOGGLE", "error-tooltip");
        }
        else
        {
          html = html.replace(":CSS", 'importo_non_riconosciuto forza_uguale_'+index);
        }
		return html;

    }

	function inputFormatterCalcoloContributo($value, row, index)
    {
		var id = "importoCalcoloContributo_" + row['idDocumentoSpesaInterven'];
		var html = inputFormatter($value, row, index, id, ' onkeyup="verificaImportiUguali('+index+');calcolaTotaleImportoCalcoloContributo();aggiornaImportoDisponibile('+row['idDocumentoSpesaInterven']+')" onchange="verificaImportiUguali('+index+');calcolaTotaleImportoCalcoloContributo();aggiornaImportoDisponibile('+row['idDocumentoSpesaInterven']+')"');
		if (row['errorMessageImportoCalcoloContributo'])
    {
      html = html.replace(":CSS", 'has-error red-tooltip importo_calcolo_contributo verifica_uguale_'+index);
      html = html.replace(":ERROR", row['errorMessageImportoCalcoloContributo']);
      html = html.replace(":TOGGLE", "error-tooltip");
    }
    else
    {
      html = html.replace(":CSS", 'importo_calcolo_contributo verifica_uguale_'+index);
    }

		return html;

    }
	
    function inputFormatter($value, row, index, id, changeFn)
    {
      if ($value == null)
      {
        $value = "";
      }
      var html = $('#invisibleTextfield').html();

      if(changeFn)
      {
    	  html = html.replace("id=", changeFn+" id=");
      }

      if (Number(row['importoRendicontato'])==0)
      {
        html = html.replace("id=", "disabled=\"disabled\" id=");
      }
      
      html = html.replace(":ID", id);
      html = html.replace(":NAME", id);
      html = html.replace(":VALUE", $value);
      
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
        }],
        connectTo : '#rendicontazioneDocumenti'
      });
    }

    function acceptWarning()
    {
      $('#warningConfirmed').val('true');
      $('#mainForm').submit();
      return false;
    }
    
    $(document).ready(function()
    {
      if ($('#warningConfirmed').val()=='false')
      {
        showConfirmMessageBox("Conferma modifica accertamento", 'Proseguendo con l\'operazione saranno reimpostate le sanzioni automatiche, anche quelle per cui è stato effettuato lo split. Per verificarle occorre consultare il quadro "Riduzioni sanzioni".', '', '#', 'acceptWarning()');
      }     
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
              return 'Il beneficiario non ha rendicontato alcun importo sul presente intervento';
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