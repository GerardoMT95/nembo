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
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCase}" />
	<p:messaggistica />
	<p:testata cu="${useCase}" />
	<div class="container-fluid" id="content">
		<m:panel id="panelQuadroDinamico">
		  <m:error/>
			<div class="puls-group">
				<p:abilitazione-azione codiceQuadro="${codiceQuadro}" codiceAzione="MODIFICA" visible="${!btnBack}">
					<button type="button" onclick="forwardToPage('../cunembo117_${codiceQuadro}/inseriscimodifica_1.do');" class="btn  btn-primary">modifica</button>
				</p:abilitazione-azione>
				<p:abilitazione-azione codiceQuadro="${codiceQuadro}" codiceAzione="ELIMINA" visible="${!btnBack}">
					<button type="button" onclick="return openPageInPopup('../cunembo118_${codiceQuadroLC}/index_1.do','dlgElimina','Elimina',null,false)"
						class="btn  btn-primary">elimina</button>
					<br class="clear" />
				</p:abilitazione-azione>
			</div>
			<c:forEach items="${quadroDinamico.recordSingolo}" var="g" varStatus="status">
				<c:choose>
					<c:when test="${g.sezioneConTitolo}">
						<m:panel id="gruppo_${status.index}" title="${g.titolo}" collapsible="true">
							<table class="table table-hover table-bordered tableBlueTh" id="gruppo_${status.index}">
								<c:forEach items="${g.elementi}" var="e" varStatus="status">
									<c:choose>
										<c:when test="${e.tipoTXT}">
											<tr>
												<th colspan="2" class="quadroDinamicoTipoTXT">${e.nomeLabel}</th>
											</tr>
										</c:when>
										<c:when test="${e.tipoHTM}">
											<tr>
												<td colspan="2">${e.note}</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<th>${e.nomeLabel}</th>
												<td>${e.valoreCompleto}</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</m:panel>
					</c:when>
					<c:otherwise>
						<table class="table table-hover table-bordered tableBlueTh" id="gruppo_${status.index}">
							<c:forEach items="${g.elementi}" var="e" varStatus="status">
								<c:choose>
									<c:when test="${e.tipoTXT}">
										<tr>
											<th colspan="2" class="quadroDinamicoTipoTXT">${e.nomeLabel}</th>
										</tr>
									</c:when>
									<c:when test="${e.tipoHTM}">
										<tr>
											<td colspan="2">${e.note}</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th>${e.nomeLabel}</th>
											<td>${e.valoreCompleto}</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<tbody>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${btnBack}">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<a href="../procedimento/quadro_${codiceQuadro}.do" class="btn  btn-primary">indietro</a>
					</div>
				</div>
				<br class="clear" />
			</c:if>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />