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
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-214-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-214-M" />

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
			<m:error></m:error>
			<div class="alert alert-warning" role="alert">In caso di conferma dell'operazione il sistema aggiornerà il contributo erogabile e di
				conseguenza i dati presenti nel quadro di accertamento delle spese.</div>

			<form id="formRiduzioni" method="post" action="confermaModifica.do">
				<table summary="Elenco riduzioni e sanzioni" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<th class="center">Tipologia - Descrizione</th>
							<th class="center">Operazione</th>
							<!-- <th class="center">Descrizione</th>  -->
							<th class="center">Note</th>
							<th class="center">Importo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${riduzioni}" var="r">
							<tr>
								<td><input type="hidden" name="idProcOggSanzione" value="${r.idProcOggSanzione}" /> <input type="hidden"
									name="isSplitted_${r.idProcOggSanzione}" value="${r.splitted}" /> <c:choose>
										<c:when test="${r.idTipologia !=3}">
											<c:set var="isDisabled" value="false"></c:set>
											<m:select id="idTipologiaSanzioneInvestimento_${r.idProcOggSanzione}" name="idTipologiaSanzioneInvestimento_${r.idProcOggSanzione}"
												preferRequestValues="${preferRequest}" list="${tipologieSanzioniInvestimentoList}" selectedValue="${r.idTipologiaSanzioneInvestimento}" />
										</c:when>
										<c:otherwise>
											<c:set var="isDisabled" value="true"></c:set>

											<!-- se è splittato, la tipologia e la descrizione NON sono modificabili -->
											<c:choose>
												<c:when test="${r.splitted == true}">
													<select class="form-control" disabled="disabled"><option value="" selected="selected">${r.tipologia}- ${r.descrizione}</option></select>
												</c:when>
												<c:otherwise>


													<m:select id="idTipologiaSanzioneInvestimento_${r.idProcOggSanzione}" name="idTipologiaSanzioneInvestimento_${r.idProcOggSanzione}"
														preferRequestValues="${preferRequest}" list="${tipologieSanzioniInvestimentoAutomaticheList}"
														selectedValue="${r.idTipologiaSanzioneInvestimento}" />
												</c:otherwise>
											</c:choose>

										</c:otherwise>
									</c:choose></td>
								<td <c:if test="${r.splitted == true}"> rowspan="2"</c:if>><c:choose>
										<c:when test="${r.idTipologia !=3}">
											<m:select id="operazione_${r.idProcOggSanzione}" disabled="${isDisabled}" name="operazione_${r.idProcOggSanzione}"
												preferRequestValues="${preferRequest}" list="${livelli}" selectedValue="${r.idOperazione}" />
										</c:when>
										<c:otherwise>
											<m:select id="operazione_${r.idProcOggSanzione}" disabled="true" name="operazione_${r.idProcOggSanzione}" list="${livelli}"
												selectedValue="${r.idOperazione}" />
										</c:otherwise>
									</c:choose></td>
								<td><m:textarea name="note_${r.idProcOggSanzione}" id="note_${r.idProcOggSanzione}" placeholder="Inserisci note (max 4000 caratteri)"
										preferRequestValues="${preferRequest}">${r.note}</m:textarea></td>

								<td><c:choose>
										<c:when test="${r.idTipologia !=3}">
											<m:textfield type="EURO" id="importo_${r.idProcOggSanzione}" name="importo_${r.idProcOggSanzione}" preferRequestValues="${preferRequest}"
												value="${r.importoFirstRecord}" />
										</c:when>
										<c:otherwise>

											<!-- se è splittato (ed è tipologia 3), l'importo è modificabile (se è tipologia 3 e NON è splittato, NON è modificabile)-->

											<c:set var="isDisabledBecauseUnsplitted" value="true"></c:set>
											<c:if test="${r.splitted == true}">
												<c:set var="isDisabledBecauseUnsplitted" value="false"></c:set>
											</c:if>
											<m:textfield type="EURO" disabled="${isDisabledBecauseUnsplitted}" id="importo_${r.idProcOggSanzione}"
												name="importo_${r.idProcOggSanzione}" value="${r.importoFirstRecord}" preferRequestValues="${preferRequest}" />

										</c:otherwise>
									</c:choose></td>
							</tr>
							<c:if test="${r.splitted == true}">
								<tr>
									<td><select class="form-control" disabled="disabled"><option value="" selected="selected">${r.tipologia}-
												${r.descrizioneSecondRecordAfterSplit}</option></select></td>
									<td><m:textarea name="noteB_${r.idProcOggSanzione}" id="noteB_${r.idProcOggSanzione}" placeholder="Inserisci note (max 4000 caratteri)"
											preferRequestValues="${preferRequest}">${r.noteB}</m:textarea></td>

									<td><m:textfield type="EURO" id="importoB_${r.idProcOggSanzione}" name="importoB_${r.idProcOggSanzione}"
											value="${r.importoSecondRecordAfterSplit}" preferRequestValues="${preferRequest}" /></td>
								</tr>
							</c:if>
						</c:forEach>
						<tr>
						</tr>
					</tbody>
				</table>
				<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="MODIFICA">
					<div class="form-group puls-group" style="margin-top: 2em">

						<button class="pull-right btn-primary btn" name="conferma" id="conferma" type="submit">conferma</button>
						<button type="button" onclick="forwardToPage('../cunembo214l/index.do');" class="btn btn-default">indietro</button>
					</div>
				</p:abilitazione-azione>
			</form>


		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>


	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />