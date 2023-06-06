<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-210" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-210" />
	<div class="container-fluid" id="content" style="margin-bottom: 3em">

		<m:panel id="importiLiquidati">

			<c:if test="${msgInfo != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-success" role="alert">
						<p>
							<c:out value="${msgInfo}"></c:out>
						</p>
					</div>
				</div>
			</c:if>

			<div class="container-fluid col-sm-12 form-group input-group">
				<c:if test="${showPrintButton && listSize>0}">

					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
						Stampa liste di liquidazione&nbsp;<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<c:forEach items="${listeDelPO}" var="l">
							<c:if test="${l.flagStatoLista =='A' || l.flagStatoLista =='T' }">
								<li> <c:choose>
									<c:when test="${empty idDocIndex}">
										<a href="../cunembo230/visualizza_${l.idListaLiquidazione}.do"><i class="pull-right ico_spaced ico24 ico_pdf"></i> Lista numero
											${l.numeroLista}</a>
									</c:when>
									<c:otherwise>
										<a href="../cunembo210/getDoc_${l.extIdDocumentoIndex}.do"><i class="pull-right ico_spaced ico24 ico_pdf"></i> Lista numero
											${l.numeroLista}</a>
									</c:otherwise>
								</c:choose> <li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
			</div>


			<!--  <div class="container-fluid col-sm-12">
			<c:if test="${showPrintButton }">
				<c:choose>
					<c:when test="${empty idDocIndex}">
						<a  type="button" class="btn btn-primary" href="../cunembo230/visualizza_${idListaStampa}.do"><i class="pull-right ico_spaced ico24 ico_pdf" ></i> Lista di liquidazione</a>
					</c:when>
					<c:otherwise>
						<a  type="button" class="btn btn-primary" href="../cunembo210/getDoc_${idDocIndex}.do"><i class="pull-right ico_spaced ico24 ico_pdf" ></i> Lista di liquidazione</a>					
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>-->
			<table id="importiLiquidatiTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " data-toggle="table"
				data-undefined-text='' data-url="getImportiLiquidati.json" data-show-columns="true" data-maintain-selected='true'>
				<thead>
					<tr>
						<th data-field="codiceLivello">Tipo operazione</th>
						<th data-field="descrizioneLivello" data-visible="${!colonneNascoste.hide('importiLiquidatiTable','descrizioneLivello',true)}">Descrizione</th>
						<th data-field="importoLiquidato" data-formatter="importoLiquidatoFormatter">Importo</th>
						<th data-field="ammCompetenzaStr" data-visible="${!colonneNascoste.hide('importiLiquidatiTable','ammCompetenzaStr',true)}">Organismo
							delegato</th>
						<th data-field="numeroLista">Numero lista</th>
						<th data-field="dataCreazioneStr">Data creazione</th>
						<th data-field="statoLista">Stato lista</th>
						<th data-field="note" data-visible="${!colonneNascoste.hide('importiLiquidatiTable','note',true)}">Note</th>
						<th data-field="dataApprovazioneStr">Data lista</th>
						<th data-field="tecnico">Tecnico liquidatore</th>
						<th data-field="invioASigop">Inviata a OPR</th>
						<th data-field="dataInvioSigopStr">Data invio</th>
						<th data-field="esitoLiquidazione" data-formatter="esitoLiquidazioneFormatter">Stato pagamento</th>
						<th data-field="numProtocollo" data-visible="${!colonneNascoste.hide('importiLiquidatiTable','numProtocollo',true)}">Numero protocollo</th>
						<th data-field="dataProtocolloStr" data-visible="${!colonneNascoste.hide('importiLiquidatiTable','dataProtocolloStr',true)}">Data
							protocollo</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>

		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
		$('#importiLiquidatiTable').on('load-success.bs.table', function() {
			reloadColspan();
		});

		$('#importiLiquidatiTable').on('column-switch.bs.table', function() {
			reloadColspan();
		});

		function reloadColspan() {
			$('.divideColspanOnload').each(function() {
				var colspan = $(this).attr('colspan');
				$(this).attr('colspan', colspan / 2);
			});
		}

		$(document).ready(function() {
			$('body').on("column-switch.bs.table", function(obj, field, checked) {
				$.ajax({
					type : "POST",
					url : '../session/salvaColonna.do',
					data : "key=importiLiquidatiTable&field=" + field + "&value=" + checked,
					success : function(data) {
					}
				});
			});

		});

		function importoLiquidatoFormatter(index, row) {

			var idListaLiquidazImpLiq = row['idListaLiquidazImpLiq'];
			var html = [];
			html
					.push("<a data-toggle=\"tooltip\" title=\"Visualizza ripartizione importo\" style=\"cursor:pointer;padding-left:0.8em;\" onclick=\"popup("
							+ idListaLiquidazImpLiq
							+ ")\"> <span class=\"glyphicon glyphicon-search\"></span></a><div class =\"pull-right\">"
							+ numberFormatter2(index) + "&euro;&nbsp;</div>");
			return html.join("");
		}

		function popup(id) {

			openPageInPopup("../cunembo210/ripartizioneImportiPopup_" + id + ".do", 'dlgModifica', 'Dettaglio ripartizione importo',
					'modal-lg', false);
			return false;

		}

		function esitoLiquidazioneFormatter(index, row) {

			var invioSigop = row['flagInvioSigop'];
			var id = row['idImportoLiquidato'];
			var esitoLiq = row['flagEsitoLiquidazione'];
			var motivoResp = row['motivoResp'];
			if (motivoResp != null)
				motivoResp = motivoResp.replace("\'", "\\'");
			var html = [];
			if (invioSigop == "S")
				html.push(index);
			if (esitoLiq == "S")
				html
						.push(" <a data-toggle=\"tooltip\" title=\"Visualizza esito liquidazione\" style=\"cursor:pointer;padding-left:0.8em;\" onclick=\"popupEsito("
								+ id + ")\"> <span class=\"glyphicon glyphicon-search\"></span></a>");
			if (esitoLiq == "R")
				html
						.push(" <a  data-toggle=\"tooltip\" title=\"Visualizza motivazione\" style=\"cursor:pointer;padding-left:0.8em;\" onclick=\"popupMotivo('"
								+ motivoResp + "')\"> <span class=\"glyphicon glyphicon-search\"></span></a>");

			return html.join("");
		}

		function popupMotivo(note) {

			showMessageBox('Motivo', note, 'modal-large');
			return false;

		}
		function popupEsito(id) {

			openPageInPopup("../cunembo210/esitoLiquidazionePopup_" + id + ".do", 'dlgModifica', 'Esito liquidazione', 'modal-lg', false);
			return false;

		}
	</script>




	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />