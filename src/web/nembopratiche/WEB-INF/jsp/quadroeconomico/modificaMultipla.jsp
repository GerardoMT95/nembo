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
	<p:breadcrumbs cdu="CU-NEMBO-163-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-163-M" />
	<form name="mainForm" method="post" action="${action}">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh">
					<thead>
						<tr>
							<c:if test="${progressivo}">
								<th rowspan="2" class="vcenter">Progr.</th>
							</c:if>
							<th rowspan="2" class="vcenter">Intervento</th>
							<th rowspan="2" class="vcenter">Ulteriori informazioni</th>
							<th colspan="4" class="center">Importo</th>
						</tr>
						<tr>
							<th>Investimento</th>
							<th>Ammesso</th>
							<th>Percentuale</th>
							<th>Contributo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interventi}" var="intervento">
							<tr>
								<c:if test="${progressivo}">
									<td style="vertical-align: middle">${intervento.htmlForProgressivo()}</td>
								</c:if>
								<td style="vertical-align: middle">${intervento.descIntervento}<input type="hidden" name="idIntervento" value="${intervento.idIntervento}" /></td>
								<td style="vertical-align: middle">${intervento.ulterioriInformazioni}</td>
								<td style="vertical-align: middle; width: 180px !important" class="numero"><fmt:formatNumber value="${intervento.importoInvestimento}"
										pattern="#,##0.00" /> &euro;</td>
								<td style="vertical-align: middle; width: 180px !important"><m:textfield id="importoAmmesso_${intervento.idIntervento}"
										onchange="ricalcolaIntervento(${intervento.idIntervento});ricalcolaTotali();" onkeyup="ricalcolaIntervento(${intervento.idIntervento});ricalcolaTotali()"
										name="importoAmmesso_${intervento.idIntervento}" maxlength="13" value="${intervento.importoAmmessoOInvestimento}"
										preferRequestValues="${preferRequest}" cssClass="importoAmmesso">
										<m:input-addon left="false">&euro;</m:input-addon>
									</m:textfield></td>
								<td style="vertical-align: middle; text-align: right; width: 120px !important"><c:choose>
										<c:when test="${intervento.percentualeFissa}">
											<m:textfield id="percentualeContributo_${intervento.idIntervento}" onchange="ricalcolaIntervento(${intervento.idIntervento});ricalcolaTotali();"
												onkeyup="ricalcolaIntervento(${intervento.idIntervento});ricalcolaTotali();" name="percentualeContributo_${intervento.idIntervento}" maxlength="6"
												value="${intervento.percentualeContributoMassima}" disabled="true">
												<m:input-addon left="false">&#37;</m:input-addon>
											</m:textfield>
										</c:when>
										<c:otherwise>
											<m:textfield id="percentualeContributo_${intervento.idIntervento}" onchange="ricalcolaIntervento(${intervento.idIntervento})"
												onkeyup="ricalcolaIntervento(${intervento.idIntervento})" name="percentualeContributo_${intervento.idIntervento}" maxlength="6"
												value="${intervento.percentualeContributo}" preferRequestValues="${preferRequest}">
												<m:input-addon left="false">&#37;</m:input-addon>
											</m:textfield>
										</c:otherwise>
									</c:choose></td>
								<td style="vertical-align: middle; width: 180px !important" class="numero"><span id="importoContributo_${intervento.idIntervento}" class="importoContributo"><fmt:formatNumber
											value="${intervento.importoContributo}" pattern="0.00"/></span> &euro;</td>
							</tr>
						</c:forEach>
						<tr>
							<th style="text-align: right" colspan="${spanTotali}">Totale</th>
							<th class="numero totale" id="totaleInvestimento"><fmt:formatNumber pattern="#,##0.00" value="${totaleInvestimento}" /> &euro;</th>
							<th class="numero totale" id="totaleAmmesso"><fmt:formatNumber pattern="#,##0.00" value="${totaleAmmesso}" />
							 &euro;</th>
							<th class="numero totale">&nbsp;</th>
							<th class="numero totale" id="totaleContributo"><fmt:formatNumber pattern="#,##0.00" value="${totaleContributo}" /> &euro;</th>
						</tr>
					</tbody>
				</table>

				<a role="button" class="btn btn-primary" href="../cunembo163l/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
				<c:if test="${progressivo}">
					<br />
					<blockquote>
						Legenda<br /> <span class="badge" style="margin-left: 12px; margin-top: 10px; background-color: green; border: 1px solid black">&nbsp;&nbsp;</span> Nuovo
						intervento<br /> <span class="badge" style="margin-left: 12px; background-color: orange; border: 1px solid black">&nbsp;&nbsp;</span> Intervento
						modificato<br /> <span class="badge" style="margin-left: 12px; background-color: white; color: black; border: 1px solid black">&nbsp;&nbsp;</span>
						Intervento consolidato<br />
					</blockquote>
				</c:if>

				<br />
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
      var txtImportoAmmesso=$('#importoAmmesso_'+intervento).val();
      var txtPercentualeContributo=$('#percentualeContributo_'+intervento).val();
      var importoAmmesso=Number(txtImportoAmmesso.replace(',','.'));
      var percentualeContributo=Number(txtPercentualeContributo.replace(',','.'));
      if (!isNaN(importoAmmesso) && !isNaN(percentualeContributo))
      {
        var importoContributo = Math.round(importoAmmesso * percentualeContributo) / 100.0;
        $('#importoContributo_'+intervento).html(importoContributo.formatCurrency());
      }
      else
      {
        $('#importoContributo_'+intervento).html("0,00");
      }
    }

    $(document).ready(function()
        {$('.importoAmmesso').each(function(index, obj)
      {
          $(obj).trigger('change');
      })
      ricalcolaTotali();
      });
    function ricalcolaTotali()
    {
      ricalcolaTotaleAmmesso();
      ricalcolaTotaleContributo();
    }

    function ricalcolaTotaleAmmesso()
    {
      var totale = 0;
      $('.importoAmmesso').each(function(index, obj)
          {
              var value=Number($(obj).val().replace(',','.'));
              if (!isNaN(value))
              {totale+=value;}
          });
      $('#totaleAmmesso').html(totale.formatCurrency()+" &euro;");
    }

    function ricalcolaTotaleContributo()
    {
      var totale = 0;
      $('.importoContributo').each(function(index, obj)
          {
        var importo=$.trim($(obj).html().replace('.','').replace(',','.'));
              var value=Number(importo);
              if (!isNaN(value))
              {totale+=value;}
          });
      $('#totaleContributo').html(totale.formatCurrency()+" &euro;");
    }
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />