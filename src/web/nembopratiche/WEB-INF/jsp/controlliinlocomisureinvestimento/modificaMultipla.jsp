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
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:set-cu-info/>
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form name="mainForm" method="post" action="modifica.do">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<m:error />
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh">
					<colgroup>
						<col width="10%">
						<col width="30%">
						<col width="15%">
						<col width="45%">
					</colgroup>
					<thead>
						<tr>
							<th class="center">Codice</th>
							<th class="center" style="max-width: 100px !important">Descrizione</th>
							<th class="center">Esito</th>
							<th class="center">Note</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${controlli}" var="c">
							<tr>
								<td style="vertical-align: middle">${c.codice}<input type="hidden" name="idQuadroOggControlloAmm" value="${c.idQuadroOggControlloAmm}" /></td>
								<td style="vertical-align: middle" style="max-width:100px !important">${c.descrizione}</td>
								<td style="vertical-align: middle"><m:select list="${esiti}"
										id="idEsito_${c.idQuadroOggControlloAmm}" name="idEsito_${c.idQuadroOggControlloAmm}" preferRequestValues="${preferRequest}"
										selectedValue="${c.idEsito}" /></td>
								<td style="vertical-align: middle"><m:textarea name="note_${c.idQuadroOggControlloAmm}" id="note_${c.idQuadroOggControlloAmm}"
										preferRequestValues="${preferRequest}">${c.note}</m:textarea></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<a role="button" class="btn btn-primary" href="../cunembo${cuNumber}d/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    function calcolaImporto(id)
    {
      $n = Number($("#valore_" + id).val().replace(',', '.'));
      if (!isNaN($n))
      {
        $importo = Number($("#importo_unitario_" + id).val().replace(',', '.'));
        if (!isNaN($importo))
        {
          var txt = Number($importo * $n).formatCurrency();
          $('#lbl_importo_' + id).html(txt);
          return true;
        }
      }
      $('#lbl_importo_' + id).html("0,00");
      return true;
    }
    $('.importo_unitario').each(function(index, tag)
    {
      var tagId = tag.id;
      tagId = tagId.substring(tagId.lastIndexOf('_') + 1);
      calcolaImporto(tagId);
    });

    function ricalcolaIntervento(intervento)
    {
      var txtImportoAmmesso = $('#importoAmmesso_' + intervento).val();
      var txtPercentualeContributo = $('#percentualeContributo_' + intervento).val();
      var importoAmmesso = Number(txtImportoAmmesso.replace(',', '.'));
      var percentualeContributo = Number(txtPercentualeContributo.replace(',', '.'));
      if (!isNaN(importoAmmesso) && !isNaN(percentualeContributo))
      {
        var importoContributo = Math.round(importoAmmesso * percentualeContributo) / 100.0;
        $('#importoContributo_' + intervento).html(importoContributo.formatCurrency());
      }
      else
      {
        $('#importoContributo_' + intervento).html("0,00");
      }
    }
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />