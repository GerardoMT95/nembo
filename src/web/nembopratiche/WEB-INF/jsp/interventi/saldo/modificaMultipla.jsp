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
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form name="mainForm" method="post" action="modifica.do">
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<th>Progr.</th>
							<th>Intervento</th>
							<th>Ulteriori informazioni</th>
							<th>Dato/Valore/UM</th>
							<th>Importo</th>
							<c:if test="${importoAmmesso}">
               <th>Spesa ammessa</th>
              </c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interventi}" var="intervento">
							<tr>
								<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}">${intervento.htmlForProgressivo()}</td>
								<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}">${intervento.descIntervento}<input type="hidden" name="id"
									value="${intervento.id}" />
								</td>
								<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}"><c:out value="${intervento.ulterioriInformazioni}" /></td>
								<td style="vertical-align: middle"><m:textfield name="valore_${intervento.id}_0" id="valore_${intervento.id}" maxlength="20"
										preferRequestValues="${preferRequest}" value="${intervento.misurazioneIntervento[0].valore}">
										<m:input-addon left="true">${intervento.misurazioneIntervento[0].descMisurazione}</m:input-addon>
										<m:input-addon left="false">${intervento.misurazioneIntervento[0].codiceUnitaMisura}</m:input-addon>
									</m:textfield></td>
								<td style="vertical-align: middle; width: 176px" class="numero" rowspan="${intervento.misurazioneIntervento.size()}"><fmt:formatNumber
										value="${intervento.importo}" pattern="#,##0.00" /></td>
								                <c:if test="${importoAmmesso}">
                  <td style="vertical-align: middle" class="numero" rowspan="${intervento.misurazioneIntervento.size()}">
                     <m:textfield name="importo_${intervento.id}" id="importo_${intervento.id}" preferRequestValues="${preferRequest}" type="euro" maxlength="13"
                        value="${intervento.importoAmmesso}" disabled="true"/>
                  </td>
                </c:if>
							</tr>
							<c:forEach begin="1" end="${intervento.misurazioneIntervento.size()-1}" var="m" varStatus="status">
								<tr>
									<td style="vertical-align: middle"><m:textfield name="valore_${intervento.id}_${status.index}" id="valore_${intervento.id}_${status.index}"
											maxlength="20" value="${intervento.misurazioneIntervento[status.index].valore}" preferRequestValues="${preferRequest}">
											<m:input-addon left="true">${intervento.misurazioneIntervento[status.index].descMisurazione}</m:input-addon>
											<m:input-addon left="false">${intervento.misurazioneIntervento[status.index].codiceUnitaMisura}</m:input-addon>
										</m:textfield></td>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
				<a role="button" class="btn btn-primary" href="../cunembo${cuNumber}l/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
				<br />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />