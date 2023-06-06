<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-254-L" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-254-L" />

	<div class="container-fluid" id="content">
		<m:panel title="Prospetto economico" id="partecelleAziendali">
			<c:if test="${!empty errore}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<c:out value="${errore}"></c:out>
					</div>
				</div>
			</c:if>
			<c:if test="${!empty pagamentiNonPresenti}">
				<div class="stdMessagePanel">
					<div class="alert alert-warning">
						<c:out value="${pagamentiNonPresenti}"></c:out>
					</div>
				</div>
			</c:if>
			<c:if test="${!empty prospetto}">

				<table id="tblProspettoEconomico" class="table table-bordered table-hover tableBlueTh">
					<thead>
						<tr>
							<th>Contributo concesso</th>
							<th>Tipologia domanda di pagamento</th>
							<th>Data presentazione</th>
							<th>Contributo richiesto</th>
							<th>Contributo rendicontato</th>							
							<th>Contributo in liquidazione/liquidato</th>
							<th>Contributo non riconosciuto non sanzionabile</th>
							<th style="min-width: 15em;">Economia</th>
              <th>Contributo non riconosciuto sanzionabile</th>
							<th>Di cui contributo riduzioni sanzioni</th>

						</tr>
					</thead>

					<c:set var="totContributoRichiestoOperazione" value="0"></c:set>
					<c:set var="totImportoRiduzioniSanzioniOperazione" value="0"></c:set>
					<c:set var="totImportoLiquidatoOperazione" value="0"></c:set>
					<c:set var="totEconomiaOperazione" value="0"></c:set>
					<c:set var="totContributoConcessoOperazione" value="0"></c:set>
					<c:set var="totImportoNonRicNonSanzOperazione" value="0"></c:set>
					<c:set var="totContributoRendicontato" value="0"></c:set>
					<c:set var="totContributoRichiesto" value="0"></c:set>
					<c:set var="totImportoRiduzioniSanzioni" value="0"></c:set>
					<c:set var="totImportoLiquidato" value="0"></c:set>
					<c:set var="totEconomia" value="0"></c:set>
					<c:set var="totContributoConcesso" value="0"></c:set>
					<c:set var="totImportoNonRicNonSanz" value="0"></c:set>

					<c:forEach items="${prospetto}" var="p" varStatus="idx">
						<tr>
							<td colspan="8"><strong>${p.operazione}</strong></td>
						</tr>
						<c:set var="totContributoRichiestoOperazione" value="0"></c:set>
						<c:set var="totContributoRendicontatoOperazione" value="0"></c:set>											
						<c:set var="totImportoRiduzioniSanzioniOperazione" value="0"></c:set>
						<c:set var="totImportoLiquidatoOperazione" value="0"></c:set>
						<c:set var="totEconomiaOperazione" value="0"></c:set>
						<c:set var="totContributoConcessoOperazione" value="0"></c:set>
						<c:set var="totImportoNonRicNonSanzOperazione" value="0"></c:set>
						<c:set var="totContributoSanzionabile" value="0"></c:set>

						<c:forEach items="${p.records}" var="a" varStatus="idx">
							<tr>
								<c:if test="${idx.index ==0 }">
									<td rowspan="${fn:length(p.records)}" style="text-align: center; vertical-align: center;"><span class="pull-right">
									&euro;&nbsp;
									<fmt:formatNumber
												currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2" value="${a.contributoConcesso}"></fmt:formatNumber></span></td>
									<c:set var="totContributoConcessoOperazione" value="${totContributoConcessoOperazione + a.contributoConcesso}"></c:set>
								</c:if>
								<td>${a.tipologiaDomandaDiPagamento}</td>
								<td>${a.dataPresentazioneStr}</td>
								<td><span class="pull-right">
								<c:if test="${not empty a.contributoRichiesto}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.contributoRichiesto}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">
								<c:if test="${not empty a.contributoRendicontato}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.contributoRendicontato}"></fmt:formatNumber></span></td>											
								<td><span class="pull-right">
									<c:if test="${not empty a.importoLiquidato}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.importoLiquidato}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">
									<c:if test="${not empty a.contributoNonRiconosciutoNonSanzionabile}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.contributoNonRiconosciutoNonSanzionabile}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">
									<c:if test="${not empty a.economia}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.economia}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">
									<c:if test="${not empty a.contributoSanzionabile}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.contributoSanzionabile}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">
									<c:if test="${not empty a.importoRiduzioniSanzioni}">&euro;&nbsp;</c:if>
								<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${a.importoRiduzioniSanzioni}"></fmt:formatNumber></span></td>
							</tr>
							<c:set var="totContributoRichiestoOperazione" value="${totContributoRichiestoOperazione + a.contributoRichiesto}"></c:set>
							<c:set var="totContributoRendicontatoOperazione" value="${totContributoRendicontatoOperazione + a.contributoRendicontato}"></c:set>						
							<c:set var="totImportoRiduzioniSanzioniOperazione" value="${totImportoRiduzioniSanzioniOperazione + a.importoRiduzioniSanzioni}"></c:set>
							<c:set var="totImportoLiquidatoOperazione" value="${totImportoLiquidatoOperazione + a.importoLiquidato}"></c:set>
							<c:set var="totEconomiaOperazione" value="${totEconomiaOperazione + a.economia}"></c:set>
							<c:set var="totImportoNonRicNonSanzOperazione" value="${totImportoNonRicNonSanzOperazione + a.contributoNonRiconosciutoNonSanzionabile}"></c:set>
							<c:set var="totContributoSanzionabile" value="${totContributoSanzionabile + a.contributoSanzionabile}"></c:set>

						</c:forEach>
						<tr>
							<td colspan="10"></td>
						</tr>
						<tr>
							<td colspan="7"><strong class="pull-right">Contributo non richiesto:</strong></td>
							<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
										value="${p.contributoNonRichiesto}"></fmt:formatNumber></span></td>
							<td></td>
							<c:set var="totEconomiaOperazione" value="${totEconomiaOperazione + p.contributoNonRichiesto}"></c:set>

						</tr>
						<c:if test="${fn:length(p.records) > 1 && fn:length(prospetto) > 1}">
							<tr>
								<td colspan="10"><strong>Totali:</strong></td>
							</tr>
							<tr>
								<td><span style="display: inline-block; text-align: center;"></span> <span class="pull-right">&euro;&nbsp;<fmt:formatNumber
											currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2" value="${totContributoConcessoOperazione}"></fmt:formatNumber></span></td>
								<td></td>
								<td></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totContributoRichiestoOperazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totContributoRendicontatoOperazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totImportoLiquidatoOperazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totImportoNonRicNonSanzOperazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totEconomiaOperazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totImportoRiduzioniSanzioniOperazione}"></fmt:formatNumber></span></td>
							</tr>
						</c:if>
						<c:set var="totContributoRichiesto" value="${totContributoRichiesto + totContributoRichiestoOperazione}"></c:set>
						<c:set var="totContributoRendicontato" value="${totContributoRendicontato + totContributoRendicontatoOperazione}"></c:set>					
						<c:set var="totImportoRiduzioniSanzioni" value="${totImportoRiduzioniSanzioni + totImportoRiduzioniSanzioniOperazione}"></c:set>
						<c:set var="totImportoLiquidato" value="${totImportoLiquidato + totImportoLiquidatoOperazione}"></c:set>
						<c:set var="totEconomia" value="${totEconomia + totEconomiaOperazione}"></c:set>
						<c:set var="totContributoConcesso" value="${totContributoConcesso + totContributoConcessoOperazione}"></c:set>
						<c:set var="totImportoNonRicNonSanz" value="${totImportoNonRicNonSanz + totImportoNonRicNonSanzOperazione}"></c:set>

					</c:forEach>
					<tr>
						<td colspan="10"><strong>Totali:</strong></td>
					</tr>
					<tr>
						<td><span style="display: inline-block; text-align: center;"></span> <span class="pull-right">&euro;&nbsp;<fmt:formatNumber
									currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2" value="${totContributoConcesso}"></fmt:formatNumber></span></td>
						<td></td>
						<td></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totContributoRichiesto}"></fmt:formatNumber></span></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totContributoRendicontato}"></fmt:formatNumber></span></td>									
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totImportoLiquidato}"></fmt:formatNumber></span></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totImportoNonRicNonSanz}"></fmt:formatNumber></span></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totEconomia}"></fmt:formatNumber></span></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totContributoSanzionabile}"></fmt:formatNumber></span></td>
						<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
									value="${totImportoRiduzioniSanzioni}"></fmt:formatNumber></span></td>
					<tr>
				</table>
			</c:if>
			<div style="padding-top: 2em;">
				<input type="button" class="btn btn-primary" onclick="history.go(-1)" value="Indietro">
			</div>
		</m:panel>

		<br class="clear" /> <br />
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
		
	</script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />