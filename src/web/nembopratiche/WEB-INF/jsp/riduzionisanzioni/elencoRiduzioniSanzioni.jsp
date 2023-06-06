<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<style type="text/css">
span.tab-space {
	padding-left: 0.4em;
}
.glyphicon-resize-small {
    font-size: 1.5em;
}
.glyphicon-resize-full {
    font-size: 1.5em;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-214-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-214-L" />


	<div class="container-fluid" id="content">
		<m:panel id="panelPosizioni">
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




			<form id="formRiduzioni" method="post" action="modifica.do">
				<table summary="Elenco riduzioni e sanzioni" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<th class="center" style="width: 110px"><p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="MODIFICA">
									<m:icon icon="add_white" href="#" onclick="return nuovaRiduzione()" />
								</p:abilitazione-azione> <p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="MODIFICA">
									<m:icon icon="modify" href="#" onclick="return modificaMultipla()" />
								</p:abilitazione-azione> <p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="ELIMINA">
									<m:icon icon="trash" href="#" onclick="return eliminaMultipla()" />
								</p:abilitazione-azione></th>
							<th class="center" style="width: 40px"><input name="chkSelectAll" id="chkSelectAll" onclick="selectAll(this,'idProcOggSanzione')"
								type="checkbox"></th>
							<th class="center">Tipologia</th>
							<th class="center">Operazione</th>
							<th class="center">Descrizione</th>
							<th class="center">Note</th>
							<th class="center">Importo calcolato</th>
							<th class="center">Importo applicato</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="total" value="${0}" />
						<c:set var="totalCalcolato" value="${0}" />

						<c:forEach items="${riduzioni}" var="r">
							<tr>
								<td class="center" <c:if test="${r.splitted == true}"> rowspan="2"</c:if>><p:abilitazione-azione codiceQuadro="RISAN"
										codiceAzione="MODIFICA">
										<m:icon icon="modify" href="../cunembo214m/index_${r.idProcOggSanzione}.do" />
									</p:abilitazione-azione> <c:if test="${r.idTipologia != 3}">
										<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="ELIMINA">
											<m:icon icon="trash" href="#" onclick="return elimina(${r.idProcOggSanzione})" />
										</p:abilitazione-azione>
									</c:if> <c:if test="${r.idTipologia == 3}">
										<c:choose>
											<c:when test="${r.splitted != true}">
												<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="SPLIT">
													<a href="#" title="split"  onclick="openPageInPopup('../cunembo214m/split_${r.idProcOggSanzione}.do', 'dlgSplitVisita','Split', 'modal-lg', false);"><span class="glyphicon glyphicon-resize-full"></span></a>
												</p:abilitazione-azione>
											</c:when>
											<c:otherwise>
												<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="UNSPLIT">
													<a href="#" title="unsplit" onclick="return openPageInPopup('../cunembo214m/unsplit_${r.idProcOggSanzione}.do', 'dlgUnsplitVisita','Unsplit', 'modal-lg', false);"><span class="glyphicon glyphicon-resize-small"></span></a>
												</p:abilitazione-azione>
											</c:otherwise>
										</c:choose>
									</c:if></td>
								<td class="center" <c:if test="${r.splitted == true}"> rowspan="2" </c:if>><input type="checkbox" name="idProcOggSanzione"
									value="${r.idProcOggSanzione}" /></td>
								<td  <c:if test="${r.splitted == true}"> rowspan="2"</c:if>>${r.tipologia}</td>
								<td <c:if test="${r.splitted == true}"> rowspan="2"</c:if>>${r.operazione}</td>
								<td>${r.descrizione}</td>
								<td>${r.note}</td>
                <td <c:if test="${r.splitted == true}"> rowspan="2" style="vertical-align:middle"</c:if>><div class="pull-right">
                    &euro;
                <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${r.importoCalcolato}">
                    </fmt:formatNumber></div></td>
								<td><div class="pull-right">
										&euro;
										<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${r.importoFirstRecord}">
										</fmt:formatNumber>
									</div></td>
							</tr>
							<c:if test="${r.splitted == true}"><tr>
								<td>${r.descrizioneSecondRecordAfterSplit}</td>
								<td>${r.noteB}</td>
								<td><c:if test="${r.splitted == true}">

									<div class="pull-right">
										&euro;
										<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${r.importoSecondRecordAfterSplit}">
										</fmt:formatNumber>
									</div> </c:if></td>
							</tr></c:if>
							<c:set var="total" value="${total + r.importoFirstRecord + r.importoSecondRecordAfterSplit}" />
							<c:set var="totalCalcolato" value="${totalCalcolato + r.importoCalcolato}" />

						</c:forEach>
						<tr>

						</tr>
						<tr>
							<td colspan="7"></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td style="text-align: right"><strong><i>&euro;&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"
											value="${totalCalcolato}"></fmt:formatNumber></i></strong></td>
							<td style="text-align: right"><strong><i>&euro;&nbsp;<fmt:formatNumber maxFractionDigits="4" minFractionDigits="2"
											value="${total}"></fmt:formatNumber></i></strong></td>
						</tr>
					</tbody>
				</table>
			</form>

			<div style="padding-top: 2em;">
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
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
		function selectAll(self, name) {
			$("input[name='" + name + "']").prop("checked", $(self).prop('checked'));
		}

		function modificaMultipla() {
			if ($("input[name='idProcOggSanzione']:checked").length > 0) {
				submitFormTo($('#formRiduzioni'), '../cunembo214m/index.do');
			} else {
		          showMessageBox("Attenzione", "Selezionare almeno una riduzione o sanzione.", 'modal-large');
			}
			return false;
		}

		function eliminaMultipla() {
			if ($("input[name='idProcOggSanzione']:checked").length > 0) {				
				return openPageInPopup('../cunembo214e/index.do', 'dlgEliminaRiduzioni', 'Elimina riduzioni', '',
						false, $('#formRiduzioni').serialize());
			} else {
		          showMessageBox("Attenzione", "Selezionare almeno una riduzione o sanzione.", 'modal-large');				
			}
			return false;
		}

		function nuovaRiduzione() {
			openPageInPopup('../cunembo214i/index.do', 'dlgInserisci', 'Inserisci nuova riduzione/sanzione', 'modal-lg', false)
			return false;
		}

		function elimina(idProcOggSanzione) {
			return openPageInPopup('../cunembo214e/index_'+idProcOggSanzione+'.do', 'dlgEliminaVisita',
					'Elimina', '', false);
		}


		
	</script>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />