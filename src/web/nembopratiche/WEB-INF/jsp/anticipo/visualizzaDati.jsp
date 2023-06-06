<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link type="text/css" rel="stylesheet" href="/nembopratiche/css/vertical-tabs.css"></link>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:set-cu-info />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<div class="container-fluid" id="content">
		<m:panel id="mainPanel">
			<br />
			<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
				<a role="button" class="btn btn-primary" href="../cunembo169m/index.do">modifica</a>
				<br />
				<br />
			</p:abilitazione-azione>
			<m:panel id="datiAnticipo" title="Dati anticipo">
				<table class="table table-hover table-bordered tableBlueTh">
					<thead>
						<tr>
							<th>Operazione</th>
							<th>Importo investimento</th>
							<th>Importo ammesso</th>
							<th>Contributo concesso</th>
							<th>Percentuale anticipo</th>
							<th>Importo anticipo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ripartizioneAnticipo}" var="r">
							<tr>
								<td>${r.codiceLivello}</td>
								<td class="numero"><fmt:formatNumber value="${r.importoInvestimento}" pattern="###,##0.00" /> &euro;</td>
								<td class="numero"><fmt:formatNumber value="${r.importoAmmesso}" pattern="###,##0.00" /> &euro;</td>
								<td class="numero"><fmt:formatNumber value="${r.importoContributo}" pattern="###,##0.00" /> &euro;</td>
								<td class="numero"><c:if test="${r.flagAnticipo=='S'}"><c:if test="${datiAnticipo.percentualeAnticipo!=null}">
										<fmt:formatNumber pattern="##0.00" value="${datiAnticipo.percentualeAnticipo}" /> &#37;</c:if></c:if>
										<c:if test="${r.flagAnticipo!='S'}">0,00 &#37</c:if>
										</td>
								<td class="numero"><c:if test="${r.importoAnticipo!=null}">
										<fmt:formatNumber pattern="#,##0.00" value="${r.importoAnticipo}" /> &euro;</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th>Totale</th>
							<th class="numero"><fmt:formatNumber value="${totImportoInvestimento}" pattern="###,##0.00" /> &euro;</th>
							<th class="numero"><fmt:formatNumber value="${totImportoAmmesso}" pattern="###,##0.00" /> &euro;</th>							
							<th class="numero"><fmt:formatNumber value="${totImportoContributo}" pattern="###,##0.00" /> &euro;</th>
							<th class="numero">&nbsp;</th>
							<th class="numero"><fmt:formatNumber pattern="#,##0.00" value="${totImportoAnticipo}" /> &euro;</th>
						</tr>
					</tfoot>
				</table>
			</m:panel>
			<m:panel id="panelFideiussione" title="Dati fideiussione">
				<table summary="Dati della fideiussione" class="table table-hover table-bordered tableBlueTh show-totali">
					<tbody>
						<tr>
							<th>Numero fideiussione</th>
							<td>${datiAnticipo.numeroFideiussione}</td>
						</tr>
						<tr>
							<th>Importo fideiussione</th>
							<td>${datiAnticipo.importoFideiussione}</td>
						</tr>
						<tr>
							<th>Data stipula</th>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${datiAnticipo.dataStipula}" /></td>
						</tr>
						<tr>
							<th>Data scadenza</th>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${datiAnticipo.dataScadenza}" /></td>
						</tr>
						<tr>
							<th>Beneficiario fideiussione</th>
							<td>${datiAnticipo.beneficiarioFideiussione}</td>
						</tr>
					</tbody>
				</table>
			</m:panel>
			<c:choose>
				<c:when test="${datiAnticipo.extIdSportello!=null}">
					<m:panel id="istituto" title="Istituto bancario">
						<table summary="Dati dell'istututo bancario" class="table table-hover table-bordered tableBlueTh show-totali">
							<tbody>
								<tr>
									<th>ABI</th>
									<td>${datiAnticipo.abi}</td>
								</tr>
								<tr>
									<th>Denominazione banca</th>
									<td>${datiAnticipo.denominazioneBanca}</td>
								</tr>
								<tr>
									<th>CAB</th>
									<td>${datiAnticipo.cab}</td>
								</tr>
								<tr>
									<th>Denominazione sportello</th>
									<td>${datiAnticipo.denominazioneSportello}</td>
								</tr>
								<tr>
									<th>Indirizzo sportello</th>
									<td>${datiAnticipo.indirizzoSportello}</td>
								</tr>
								<tr>
									<th>Comune sportello</th>
									<td>${datiAnticipo.descCompletaComuneSportello}</td>
								</tr>
							</tbody>
						</table>
					</m:panel>
				</c:when>
				<c:otherwise>
					<m:panel id="istituto" title="Altro istituto">
						<table summary="Dati di Altro istituto" class="table table-hover table-bordered tableBlueTh show-totali">
							<tbody>
								<tr>
									<th>Denominazione istituto</th>
									<td>${datiAnticipo.altroIstituto}</td>
								</tr>
								<tr>
									<th>Indirizzo istituto</th>
									<td>${datiAnticipo.indirizzoAltroIstituto}</td>
								</tr>
								<tr>
									<th>Comune</th>
									<td>${datiAnticipo.descCompletaComuneAltroIstituto}</td>
								</tr>
							</tbody>
						</table>
					</m:panel>
				</c:otherwise>
			</c:choose>
			<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />