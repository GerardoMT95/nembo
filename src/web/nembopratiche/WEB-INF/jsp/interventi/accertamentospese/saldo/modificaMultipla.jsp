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
							<th>Intervento</th>
							<th>Spesa<br/>ammessa</th>
							<th>Spesa<br/>rendicontata<br/>attuale</th>
							<th>Spese<br/>accertate<br/>attuali</th>
							<th>Spesa riconosciuta<br/>per il calcolo<br/>del contributo</th>
							<th>Importo non riconosciuto</th>
							<th>Importo non riconosciuto rispendibile</th>
							<th>Contributo calcolato</th>
							<th>Intervento<br />completato
							</th>
							<th>Note</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interventi}" var="intervento">
							<tr>
								<td style="vertical-align: middle; text-align: center"><div style="text-align: center">
										<span class="badge" style="background-color: white; color: black; border: 1px solid black">${intervento.progressivo}</span>
									</div> <input type="hidden" name="id" value="${intervento.idIntervento}" /></td>
								<td style="vertical-align: middle">${intervento.descIntervento}</td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.spesaAmmessa}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center" id="spesa_rendicontata_attuale_${intervento.idIntervento}"><fmt:formatNumber value="${intervento.spesaSostenutaAttuale}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest}"
										value="${intervento.speseAccertateAttuali}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida}" maxlength="13" style="min-width:120px"
										id="speseAccertateAttuali_${intervento.idIntervento}" name="speseAccertateAttuali_${intervento.idIntervento}" 
										onchange="calcolaImportoNonRiconosciuto(${intervento.idIntervento})"
										onkeyup="calcolaImportoNonRiconosciuto(${intervento.idIntervento})"
										/></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest}"
										value="${intervento.spesaRiconosciutaPerCalcolo}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida}" maxlength="13"
										style="min-width:120px" id="spesaRiconosciutaPerCalcolo_${intervento.idIntervento}" name="spesaRiconosciutaPerCalcolo_${intervento.idIntervento}" 
										onchange="calcolaImportoNonRiconosciuto(${intervento.idIntervento});calcolaContributo(${intervento.idIntervento},'${intervento.percentualeContributo}')"
                    onkeyup="calcolaImportoNonRiconosciuto(${intervento.idIntervento});calcolaContributo(${intervento.idIntervento},'${intervento.percentualeContributo}')"
                    /></td>
								<td style="vertical-align: middle; text-align: center" id="importoNonRiconosciuto_${intervento.idIntervento}"><fmt:formatNumber
										value="${intervento.importoNonRiconosciuto}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><m:textfield preferRequestValues="${preferRequest}"
										value="${intervento.importoRispendibile}" type="EURO" disabled="${!intervento.spesaSostenutaAttualeValida}" maxlength="13" style="min-width:120px"
										id="importoRispendibile_${intervento.idIntervento}" name="importoRispendibile_${intervento.idIntervento}" 
										onchange="calcolaImportoNonRiconosciuto(${intervento.idIntervento})"
                    onkeyup="calcolaImportoNonRiconosciuto(${intervento.idIntervento})"
                    /></td>
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
    function calcolaImportoNonRiconosciuto(id)
    {
      var $spesaRendicontataAttuale = Number($('#spesa_rendicontata_attuale_'+id).html().replace('.', '').replace(',', '.'));
      var $spesaRiconosciutaPerCalcolo = Number($('#spesaRiconosciutaPerCalcolo_'+id).val().replace(',', '.'));
      var $importoRispendibile = Number($('#importoRispendibile_'+id).val().replace(',', '.'));
      if (!isNaN($spesaRendicontataAttuale) && !isNaN($spesaRiconosciutaPerCalcolo) && !isNaN($importoRispendibile))
      {
				var importoNonRiconosciuto = $spesaRendicontataAttuale - $spesaRiconosciutaPerCalcolo - $importoRispendibile;
				importoNonRiconosciuto = Math.round(importoNonRiconosciuto*100) / 100;
				$('#importoNonRiconosciuto_'+id).html(importoNonRiconosciuto.formatCurrency());
				return true;
      }
      $('#importoNonRiconosciuto_'+id).html("0,00");
      return true;
    }
    function calcolaContributo(id, perc)
    {
      var $spesaRiconosciutaPerCalcolo = Number($('#spesaRiconosciutaPerCalcolo_'+id).val().replace('.', '').replace(',', '.'));
      perc=Number(perc);
      var contributo=null;
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
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />