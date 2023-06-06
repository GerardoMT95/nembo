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
	<form name="mainForm" method="post" action="${action}">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<m:error />
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<th>Progr.</th>
							<th>Intervento</th>
							<th>Spesa<br />ammessa
							</th>
							<th>Contributo</th>
							<th>Spese gi&agrave;<br />rendicontate
							</th>
							<th>Spese gi&agrave;<br />riconosciute
							</th>
							<th>Spesa rendicontata<br />attuale
							</th>
							<th>Contributo<br />richiesto
							</th>
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
								<td style="vertical-align: middle">${intervento.descIntervento}<input type="hidden" name="percentualeContributo_${intervento.idIntervento}"
									id="percentualeContributo_${intervento.idIntervento}" value="${intervento.percentualeContributo}" /></td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.spesaAmmessa}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.importoContributo}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.speseSostenute}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><fmt:formatNumber value="${intervento.speseAccertate}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center; width:160px"><m:textfield name="importoSpesa_${intervento.idIntervento}"
										onchange="ricalcolaContributoRichiesto(${intervento.idIntervento})" onkeyup="ricalcolaContributoRichiesto(${intervento.idIntervento})"
										id="importoSpesa_${intervento.idIntervento}" maxlength="13" preferRequestValues="${preferRequest}" value="${intervento.importoSpesa}" type="EURO"
										disabled="${intervento.usaDocumentiSpesa=='S'}">
									</m:textfield></td>
								<td style="vertical-align: middle; text-align: center" id="contributoRichiesto_${intervento.idIntervento}"><fmt:formatNumber
										value="${intervento.contributoRichiesto}" pattern="#,##0.00" /></td>
								<td style="vertical-align: middle; text-align: center"><div class="center">
										<input name="flagInterventoCompletato_${intervento.idIntervento}" data-toggle="bs-toggle" type="checkbox" value="S"
											<c:if test="${intervento.flagInterventoCompletato=='S'}"> checked="checked" </c:if> />
									</div></td>
								<td style="vertical-align: middle; text-align: center"><m:textarea name="note_${intervento.idIntervento}" style="min-width:128px"
										id="note_${intervento.idIntervento}" preferRequestValues="${preferRequest}">${intervento.note}</m:textarea></td>
						</c:forEach>
					</tbody>
				</table>
				<a role="button" class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
				<br />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-toggle/js/bootstrap-toggle.js"></script>
	<script type="text/javascript">
    $('input[data-toggle="bs-toggle"]').bootstrapToggle();
    function ricalcolaContributoRichiesto(id)
    {
      $n=Number($("#importoSpesa_"+id).val().replace(',','.'));
      if (!isNaN($n))
      {
        $importo=Number($("#percentualeContributo_"+id).val().replace(',','.'));
        if (!isNaN($importo))
        {
          var contributoRichiesto = Number($importo*$n);
          contributoRichiesto = Math.round(contributoRichiesto)/100;
          $('#contributoRichiesto_'+id).html(contributoRichiesto.formatCurrency());
          return true;
        }
      }
      $('#contributoRichiesto_'+id).html("0,00");
      return true;
    }
    $('input[name="id"]').each(
        function(index, obj)
        {
          ricalcolaContributoRichiesto($(obj).val());
        }
    )
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />