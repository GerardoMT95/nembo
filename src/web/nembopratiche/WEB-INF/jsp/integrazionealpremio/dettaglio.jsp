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

	<p:breadcrumbs cdu="CU-NEMBO-259-V" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-259-V" />

	<div class="container-fluid" id="content">
		<m:panel id="panelCC">
			<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}"></c:out>
						</p>
					</div>
				</div>
			</c:if>



			<c:set var="totImportoLiquidato" value="0"></c:set>
			<c:set var="totEconomie" value="0"></c:set>
			<c:set var="totContributoConcesso" value="0"></c:set>
			<c:set var="totContributoIntegrazione" value="0"></c:set>
			<c:set var="totContributoRiduzioniSanzioni" value="0"></c:set>


			<c:if test="${!empty integrazione}">
				<form:form id="elencoForm" name="elencoForm">
					<table id="tblIntegrazioneAlPremio" class="table table-bordered table-hover tableBlueTh">
						<thead>
							<tr>
								<th><p:abilitazione-azione codiceQuadro="DINTP" codiceAzione="DETTAGLIO">
										<input id="slctAll" type="checkbox" onclick="selectAll();" />
										<a href="../cunembo259m/index.do" onclick="return modificaMultipla()" class="ico24 ico_modify"></a>

									</p:abilitazione-azione></th>
								<th>Contributo concesso</th>
								<th>Totale liquidato</th>
								<th>Economie</th>
								<th>Contributo integrazione</th>
								<th>Di cui da sanzioni/riduzioni</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${integrazione}" var="p" varStatus="idx">
								<tr>
									<td><p:abilitazione-azione codiceQuadro="DINTP" codiceAzione="DETTAGLIO">
											<input type="checkbox" name="idLivello" value="${p.idLivello}">
											<a href="#" onclick="return modificaSingola(${p.idLivello})" class="ico24 ico_modify"></a>
										</p:abilitazione-azione></td>
									<td colspan="4"><strong>${p.operazione}</strong></td>
								</tr>
								<tr>
									<td></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.contributoConcesso}"></fmt:formatNumber></span></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.totaleLiquidato}"></fmt:formatNumber></span></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.economie}"></fmt:formatNumber></span></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.contributoIntegrazione}"></fmt:formatNumber></span></td>
									<td><c:if test="${!empty p.contributoRiduzioniSanzioni}"><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.contributoRiduzioniSanzioni}"></fmt:formatNumber></span></c:if></td>
								</tr>

								<c:set var="totImportoLiquidato" value="${totImportoLiquidato + p.totaleLiquidato }"></c:set>
								<c:set var="totEconomie" value="${totEconomie + p.economie }"></c:set>
								<c:set var="totContributoConcesso" value="${totContributoConcesso + p.contributoConcesso }"></c:set>
								<c:set var="totContributoIntegrazione" value="${totContributoIntegrazione + p.contributoIntegrazione }"></c:set>
								<c:set var="totContributoRiduzioniSanzioni" value="${totContributoRiduzioniSanzioni + p.contributoRiduzioniSanzioni }"></c:set>

							</c:forEach>
							<tr>
								<td colspan="5"><strong>Totali:</strong></td>
							</tr>
							<tr>
								<td></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totContributoConcesso}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totImportoLiquidato}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totEconomie}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totContributoIntegrazione}"></fmt:formatNumber></span></td>
								<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
											value="${totContributoRiduzioniSanzioni}"></fmt:formatNumber></span></td>			
							</tr>
						</tbody>
					</table>
				</form:form>

			</c:if>

			<div style="padding-top: 1em;">
				<table class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="10%">
						<col width="90%">
					</colgroup>
					<tbody>
						<tr>
							<th>Ultima modifica</th>
							<td><c:out value="${ultimaModifica}"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>

		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
	<script>
		function modificaMultipla() {
			if ($("input[name='idLivello']:checked").length > 0) {
				submitFormTo($('#elencoForm'), '../cunembo259m/index.do');
			} else {
				showMessageBox("Attenzione!", "Selezionare almeno un elemento.", "modal-large");
			}
			return false;
		}

		function modificaSingola(idLivello) {
				submitFormTo($('#elencoForm'), '../cunembo259m/index_'+idLivello+'.do');
			return false;
		}

		function selectAll() {
			if($("#slctAll")[0].checked)
				$("input[name='idLivello']").prop('checked', true);
			else
				$("input[name='idLivello']").prop('checked', false);
			
		}
	</script>