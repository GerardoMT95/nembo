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
<link rel="stylesheet" href="/nembopratiche/bootstrap-toggle/css/bootstrap-toggle.min.css">
<body>
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form name="mainForm" id="mainForm" method="post" action="${action}">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
			  <m:error/>
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<th>Progr.</th>
							<th>Spesa<br/>ammessa</th>
              <th>Contributo</th>
							<th>Spesa<br/>rendicontata<br/>attuale</th>
							<th>Spese<br/>accertate<br/>attuali</th>
							<th>Spesa riconosciuta<br/>per il calcolo<br/>del contributo</th>
							<th>Importo non riconosciuto sanzionabile</th>
							<th>Importo non riconosciuto non sanzionabile</th>
							<th>Contributo calcolato</th>
							<th>Intervento<br />completato
							</th>
							<th>Note</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interventi}" var="intervento">
						  <tr>
                <td style="vertical-align: middle; text-align: center" rowspan="2"><div style="text-align: center">
                    <span class="badge" style="background-color: white; color: black; border: 1px solid black">${intervento.progressivo}</span>
                  </div> <input type="hidden" name="id" value="${intervento.idIntervento}" /></td>
                <td colspan="9" style="vertical-align: middle"><strong>${intervento.descIntervento}</strong></td>
                </tr>
							<tr>
								<td style="vertical-align: middle; text-align: center" id="spesaAmmessa_${intervento.idIntervento}" data-value="${intervento.spesaAmmessa}"><fmt:formatNumber value="${intervento.spesaAmmessa}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.importoContributo}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center" id="spesa_rendicontata_attuale_${intervento.idIntervento}" data-value="${intervento.spesaSostenutaAttuale}"><fmt:formatNumber value="${intervento.spesaSostenutaAttuale}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest && intervento.spesaSostenutaAttualeValida}"
										value="${intervento.speseAccertateAttuali}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida|| intervento.usaDocumentiSpesa=='S'}" maxlength="13" style="min-width:90px"
										id="speseAccertateAttuali_${intervento.idIntervento}" name="speseAccertateAttuali_${intervento.idIntervento}" 
										onchange="calcolaImportoNonRiconosciutoSanzionabile(${intervento.idIntervento});calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
										onkeyup="calcolaImportoNonRiconosciutoSanzionabile(${intervento.idIntervento});calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
										/></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest && intervento.spesaSostenutaAttualeValida}"
										value="${intervento.spesaRiconosciutaPerCalcolo}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida || intervento.usaDocumentiSpesa=='S'}" maxlength="13"
										style="min-width:90px" id="spesaRiconosciutaPerCalcolo_${intervento.idIntervento}" name="spesaRiconosciutaPerCalcolo_${intervento.idIntervento}" 
										onchange="calcolaContributo(${intervento.idIntervento},'${intervento.percentualeContributo}');calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
                    onkeyup="calcolaContributo(${intervento.idIntervento},'${intervento.percentualeContributo}');calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
                    /></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest && intervento.spesaSostenutaAttualeValida}"
										value="${intervento.importoNonRiconosciuto}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida || intervento.usaDocumentiSpesa=='S'}" maxlength="13" style="min-width:90px"
										id="importoNonRiconosciutoSanzionabile_${intervento.idIntervento}" name="importoNonRiconosciutoSanzionabile_${intervento.idIntervento}"
                    onchange="calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
                    onkeyup="calcolaImportoNonRiconosciutoRispendibile(${intervento.idIntervento})"
                    /></td>
                <td style="vertical-align: middle; text-align: center" id="importoNonRiconosciutoRispendibile_${intervento.idIntervento}"><fmt:formatNumber
                    value="${intervento.importoRispendibile}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center" id="contributoCalcolato_${intervento.idIntervento}"><fmt:formatNumber
										value="${intervento.contributoCalcolato}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><div class="center">
										<input name="flagInterventoCompletato_${intervento.idIntervento}" data-toggle="bs-toggle" type="checkbox" value="S"
											<c:if test="${intervento.flagInterventoCompletato=='S'}"> checked="checked" </c:if> />
									</div></td>
								<td style="vertical-align: middle; text-align: center"><m:textarea name="note_${intervento.idIntervento}" style="min-width:128px"
										id="note_${intervento.idIntervento}" preferRequestValues="${preferRequest}">${intervento.note}</m:textarea></td>
						</c:forEach>
					</tbody>
				</table>
				<a role="button" class="btn btn-primary" href="../cunembo${cuNumber}l/index.do">Indietro</a>
        		<input type="button" onclick="openPageInPopup('../cunembo${cuNumber}m/confermaModifica.do', 'dlgChiusura', 'Conferma modifica', 'modal-large', true); " role="s" class="btn btn-primary pull-right" value="conferma" />
				<input style="display:none;" type="submit" name="confermaModificaInterventi" id="confermaModificaInterventi"  role="s" class="btn btn-primary pull-right" value="conferma" />
				
				<br class="clear" />
				<br />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
    $('input[data-toggle="bs-toggle"]').bootstrapToggle();
    function calcolaImportoNonRiconosciutoSanzionabile(id)
    {
      var $spesaRendicontataAttuale = Number($('#spesa_rendicontata_attuale_'+id).data('value'));
      var $speseAccertateAttuali = Number($('#speseAccertateAttuali_'+id).val().replace(',', '.'));
      var $spesaAmmessa = Number($('#spesaAmmessa_'+id).data('value'));
      var importoNonRiconosciutoSanzionabile="0,00";
      if (!isNaN($spesaRendicontataAttuale) && !isNaN($speseAccertateAttuali) && !isNaN($spesaAmmessa))
      {
        importoNonRiconosciutoSanzionabile = $spesaRendicontataAttuale - $speseAccertateAttuali;
        importoNonRiconosciutoSanzionabile = Math.round(importoNonRiconosciutoSanzionabile*100) / 100;
        importoNonRiconosciutoSanzionabile = (""+importoNonRiconosciutoSanzionabile).replace('.',',');
      }
      $('#importoNonRiconosciutoSanzionabile_'+id).val(importoNonRiconosciutoSanzionabile);
      return calcolaImportoNonRiconosciutoRispendibile(id);
    }
    
    function calcolaImportoNonRiconosciutoRispendibile(id)
    {
      var $spesaRendicontataAttuale = Number($('#spesa_rendicontata_attuale_'+id).data('value'));
      var $speseRiconosciutaPerIlCalcolo = Number($('#spesaRiconosciutaPerCalcolo_'+id).val().replace(',', '.'));
      var v=$('#importoNonRiconosciutoSanzionabile_'+id).val().replace(",",".");
      var $importoNonRiconosciutoSanzionabile = Number($('#importoNonRiconosciutoSanzionabile_'+id).val().replace(',', '.'));
      var importoNonRiconosciutoRispendibile="0,00";
      if (!isNaN($spesaRendicontataAttuale) && !isNaN($speseRiconosciutaPerIlCalcolo) && !isNaN($importoNonRiconosciutoSanzionabile))
      {
        importoNonRiconosciutoRispendibile = $spesaRendicontataAttuale - $speseRiconosciutaPerIlCalcolo - $importoNonRiconosciutoSanzionabile;
        importoNonRiconosciutoRispendibile = Math.round(importoNonRiconosciutoRispendibile*100) / 100;
        var $spesaAmmessa = Number($('#spesaAmmessa_'+id).data('value'));
        importoNonRiconosciutoRispendibile = importoNonRiconosciutoRispendibile.formatCurrency();
      }
      $('#importoNonRiconosciutoRispendibile_'+id).html(importoNonRiconosciutoRispendibile);
      return true;
    }

    function min(v1,v2)
    {
      return (v1<v2)?v1:v2;
    }
    
    function calcolaContributo(id, perc)
    {
      var $spesaRiconosciutaPerCalcolo = Number($('#spesaRiconosciutaPerCalcolo_'+id).val().replace(',', '.'));
      perc=Number(perc);
      var contributo=0;
      if (!isNaN($spesaRiconosciutaPerCalcolo) && !isNaN(perc))
      {
        contributo=Math.round(Number($spesaRiconosciutaPerCalcolo*perc/100)*100)/100;
      }
      if (!isNaN(contributo))
      {
				$('#contributoCalcolato_'+id).html(contributo.formatCurrency());
				return true;
      }
      $('#contributoCalcolato_'+id).html("0,00");
      return true;
    }
    $(document).ready(function()
     {
      $('input[name="id"]').each(function(index, obj)
      {
        var id=$(obj).val();
        var $spesaRendicontataAttuale = Number($('#spesa_rendicontata_attuale_'+id).data('value'));
        if ($spesaRendicontataAttuale>0)
        {
          calcolaImportoNonRiconosciutoRispendibile(id);
          $('#spesaRiconosciutaPerCalcolo_'+id).trigger('change');
        }
      })
     }
    );
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />