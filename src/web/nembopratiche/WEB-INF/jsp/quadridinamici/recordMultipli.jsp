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
			<table class="table table-hover table-bordered tableBlueTh" id="quadro_dinamico_record_multipli">
				<thead>
					<tr>
						<th class="center vcenter"><p:abilitazione-azione codiceQuadro="${codiceQuadro}" codiceAzione="MODIFICA"><a href="../cunembo117_${codiceQuadroLC}/inserisci.do" class="ico24 ico_add_white"></a></p:abilitazione-azione></th>
						<c:forEach items="${quadroDinamico.intestazioniElenco}" var="i">
							<th>${i}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:if test="${quadroDinamico.numRecords>0}">
						<c:forEach items="${quadroDinamico.recordMultipli}" var="r">
							<tr>
								<td style="min-width:99px"><a href="../cunembo116e_${codiceQuadroLC}/dettaglio_${r.numProgressivoRecord}.do" class="ico24 ico_magnify"></a> <p:abilitazione-azione
										codiceQuadro="${codiceQuadro}" codiceAzione="MODIFICA">
										<a href="../cunembo117_${codiceQuadroLC}/inseriscimodifica_${r.numProgressivoRecord}.do" class="ico24 ico_modify"></a>
										<p:abilitazione-azione codiceQuadro="${codiceQuadro}" codiceAzione="MODIFICA"></p:abilitazione-azione>
										<a href="#" class="ico24 ico_trash"
											onclick="return openPageInPopup('../cunembo118_${codiceQuadroLC}/index_${r.numProgressivoRecord}.do','dlgElimina','Elimina',null,false)"></a>
									</p:abilitazione-azione></td>


								<c:forEach items="${r.elementi}" var="e">
									<td>${e.valoreCompleto}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${quadroDinamico.numRecords==0}">
						<tr>
							<td colspan="${quadroDinamico.countElementiQuadroElenco+1}">Nessun elemento presente</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />