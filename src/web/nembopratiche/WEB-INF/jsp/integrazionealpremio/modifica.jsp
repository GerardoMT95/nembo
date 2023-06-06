
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-259-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-259-M" />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelCC">
			<form:form id="myForm" action="modifica.do" modelAttribute="" method="post" class="form-inline" style="margin-top: 2px">
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

				<c:if test="${integrazione == null}">
					<div class="puls-group" style="margin-top: 2em">
						<div class="pull-left">
							<button type="button" onclick="forwardToPage('../cunembo259v/index.do');" class="btn btn-default">annulla</button>
						</div>
					</div>
				</c:if>

				<c:if test="${!empty integrazione}">
					<table id="tblIntegrazioneAlPremio" class="table table-bordered table-hover tableBlueTh">
						<thead>
							<tr>
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
									<td colspan="8"><strong>${p.operazione}</strong><input type="hidden" name="idLivello" value="${p.idLivello}"></td>
								</tr>
								<tr>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.contributoConcesso}"></fmt:formatNumber></span></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.totaleLiquidato}"></fmt:formatNumber></span></td>
									<td><span class="pull-right">&euro;&nbsp;<fmt:formatNumber currencySymbol="euro" maxFractionDigits="2" minFractionDigits="2"
												value="${p.economie}"></fmt:formatNumber></span></td>
									<td><span class="pull-right"><m:textfield type="euro" id="contributoIntegrazione_${p.idLivello}" style="min-width:25em;"
												name="contributoIntegrazione_${p.idLivello}" value="${p.contributoIntegrazione}" preferRequestValues="preferRequest"></m:textfield></span></td>
									<td><span class="pull-right"><m:textfield type="euro" id="contributoRiduzioniSanzioni_${p.idLivello}" style="min-width:25em;"
												name="contributoRiduzioniSanzioni_${p.idLivello}" value="${p.contributoRiduzioniSanzioni}" preferRequestValues="preferRequest"></m:textfield></span></td>

								</tr>
							</c:forEach>

						</tbody>
					</table>

					<div class="puls-group" style="margin-top: 2em">
						<div class="pull-left">
							<button type="button" onclick="forwardToPage('../cunembo259v/index.do');" class="btn btn-default">annulla</button>
						</div>
						<div class="pull-right">
							<button type="button" onclick="checkConfirm();" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
						</div>
					</div>
				</c:if>
			</form:form>
		</m:panel>
	</div>
	<script type="text/javascript">
		function checkConfirm() {
			var elems = $("input[name^='contributoRiduzioniSanzioni_']");

			var i = 0;
			if (elems != null)
				for (i = 0; i < elems.length; i++) {
					if (elems[i].value === undefined || elems[i].value == '')
						{
							openPageInPopup('importiNonValorizzati.do', 'dlgElimina', 'Attenzione!', 'modal-lg', false);
							return false;
						}

				}

			$('#myForm').submit();
		}
	</script>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />