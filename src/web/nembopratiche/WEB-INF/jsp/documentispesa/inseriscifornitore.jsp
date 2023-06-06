<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-263-I" />
	<p:messaggistica />
	<p:testata onlyCompanyData="true" showIter="true" cu="CU-NEMBO-263-I" />


	<div class="container-fluid" id="content">
		<m:panel id="panelDocumenti">

			<div class="col-sm-12">

				<div class="stepwizard ">
					<div class="stepwizard-row setup-panel">
						<div class="stepwizard-step">
							<a href="#step-1" type="button" class="btn btn-default btn-circle">1</a>
							<p>Dati Documento</p>
						</div>
						<div class="stepwizard-step">
							<a href="#step-2" type="button" class="btn btn-primary btn-circle">2</a>
							<p>Scelta fornitore</p>
						</div>
						<div class="stepwizard-step">
							<a href="#step-4" type="button" class="btn btn-default btn-circle">3</a>
							<p>Ricevute Pagamento</p>
						</div>
					</div>
				</div>
				<c:if test="${msgErrore != null}">
					<div class="">
						<div class="alert alert-danger">
							<p>
								<strong>Attenzione!</strong><br />
								<c:out value="${msgErrore}"></c:out>
							</p>
						</div>
					</div>
				</c:if>

				<div id="firstRow">

					<m:error />
					<div class="col-md-8 form-horizontal">
						<label>Codice fornitore (CUAA / Partita IVA / Codice estero)</label>
						<m:textfield id="filtroPIVA" name="filtroPIVA" />
					</div>
					<!-- /btn-group -->
				</div>
				<div class="col-md-4; padding-top:1em">
					<br />
					<button type="button" name="conferma" id="conferma" onclick="cercafornitori()" class="btn btn-primary">cerca</button>
					<button type="button" name="nuovofornitorediv" id="nuovofornitorediv" style="display: none" onclick="inseriscifornitore()"
						class="btn btn-primary">inserisci</button>
				</div>

				<div class="col-md-12" style="margin-left: 15em; margin-top: 2em;">
					<div id="loadingdiv" style="display: none">
						<div id="floatBarsG_1" class="floatBarsG"></div>
						<div id="floatBarsG_2" class="floatBarsG"></div>
						<div id="floatBarsG_3" class="floatBarsG"></div>
						<div id="floatBarsG_4" class="floatBarsG"></div>
						<div id="floatBarsG_5" class="floatBarsG"></div>
						<div id="floatBarsG_6" class="floatBarsG"></div>
						<div id="floatBarsG_7" class="floatBarsG"></div>
						<div id="floatBarsG_8" class="floatBarsG"></div>
					</div>
				</div>

				<div id="nuovofornitoreform" style="display:${displayDatiFornitori};margin-top:2em">
					<form:form action="confermaDatiNuovoFornitore.do" method="post" class="form-horizontal">
						<m:panel id="risultatiRicerca">

							<div class="container-fluid" style="margin-bottom: 2em">
								<div class="form-group">
									<input type="hidden" name="extIstatComune" id="extIstatComune"> <input type="hidden" name="idDocumentoSpesa" id="idDocumentoSpesa"
										value="${idDocumentoSpesa}"> <input type="hidden" name="istatProvincia" id="istatProvincia"> <input type="hidden"
										id="comuneSedeLegaleHidden" name="comuneSedeLegaleHidden">
									<m:textfield label="CUAA (Codice fiscale / Partita IVA / Codice Estero) *" id="cuaa" name="cuaa" preferRequestValues="${preferRqValue}"></m:textfield>
									<m:textfield label="Denominazione *" id="denominazione" name="denominazione" preferRequestValues="${preferRqValue}"></m:textfield>
									<fieldset>
										<legend>Dati sede legale</legend>
										<m:textfield label="Indirizzo *" id="indirizzoSedeLegale" name="indirizzoSedeLegale" preferRequestValues="${preferRqValue}"></m:textfield>
										<m:textfield disabled="true" label="Comune/Stato estero *" id="comuneSedeLegale" name="comuneSedeLegale">
											<m:input-addon left="false">
												<a href="#" onclick="return ricercaComune()"><i class="icon icon-folder-open"></i> Cambia</a>
											</m:input-addon>
										</m:textfield>										
									</fieldset>
								</div>
								<i>* Campi obbligatori</i>
							</div>
							<div class="puls-group" style="margin-bottom: 2em">
								<div class="puls-group" style="margin-top: 3em; margin-bottom: 3em;">
									<div class="pull-left">
										<c:if test="${!empty idDocumentoSpesa}">
											<button type="button" onclick="forwardToPage('getDatiInserimento_${idDocumentoSpesa}.do');" class="btn btn-default">indietro</button>
										</c:if>
										<c:if test="${empty idDocumentoSpesa}">
											<button type="button" onclick="forwardToPage('getDatiInserimento.do');" class="btn btn-default">indietro</button>
										</c:if>
									</div>
								</div>
								<div class="pull-right">
									<button type="submit" onclick="$('.stdMessagePanel').hide();$('.stdMessageLoad').show();" name="prosegui" id="prosegui"
										class="btn btn-primary">conferma</button>
								</div>
							</div>
						</m:panel>
					</form:form>
				</div>



				<div id="mainForm" style="display: none;">
					<form action="" method="POST" name="elencoForm" id="elencoForm">
						<div class="col-md-12" id="elencofornitorilabel">
							<label>Elenco fornitori</label>
						</div>

						<div class="col-md-12" id="elencofornitori" style="margin-top: 2em; margin bottom: 2em"></div>
						<div class="col-sm-12 ">
							<div class="puls-group" style="margin-top: 3em; margin-bottom: 3em;">
								<div class="pull-left">
									<c:if test="${!empty idDocumentoSpesa}">
										<button type="button" onclick="forwardToPage('getDatiInserimento_${idDocumentoSpesa}.do');" class="btn btn-default">indietro</button>
									</c:if>
									<c:if test="${empty idDocumentoSpesa}">
										<button type="button" onclick="forwardToPage('getDatiInserimento.do');" class="btn btn-default">indietro</button>
									</c:if>
								</div>
							</div>
							<div class="puls-group" style="margin-top: 3em; margin-bottom: 3em;">
								<div class="pull-right">
									<button type="button" name="conferma" id="conferma" onclick="avanti()" class="btn btn-primary">conferma</button>
								</div>
							</div>
						</div>
						<br> <br> <br> <br> <br> <br>

					</form>
				</div>
				<div id="indietroDiv" class="puls-group" style="padding-bottom: 2em; padding-top: 2em; padding-left: 1em;">
					<div class="pull-left">
						<c:if test="${!empty idDocumentoSpesa}">
							<button type="button" onclick="forwardToPage('getDatiInserimento_${idDocumentoSpesa}.do');" class="btn btn-default">indietro</button>
						</c:if>
						<c:if test="${empty idDocumentoSpesa}">
							<button type="button" onclick="forwardToPage('getDatiInserimento.do');" class="btn btn-default">indietro</button>
						</c:if>
					</div>
				</div>
			</div>



		</m:panel>

	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="../js/list.js"></script>
	<script type="text/javascript">
	$( document ).ready(function() {

		$(".breadcrumb").html('<li><a href="../index.do">Home</a><span class="divider">/</span></li><li><a href="../ricercaprocedimento/index.do">Ricerca procedimento</a> <span class="divider">/</span></li><li><a href="../ricercaprocedimento/restoreElencoProcedimenti.do">Elenco procedimenti</a> <span class="divider">/</span></li><li><span class"divider"></span><a href="../cunembo129/index_${idProcedimento}.do">Dettaglio oggetto</a></li><li><span class="divider">/ </span><a href="../cunembo263l/index.do">Documenti spesa</a></li><li class="active"><span class="divider">/</span>Fornitore</li>');

	});	
		cercafornitoriIniziali();
		if ('${forceDatiFornitori}' == 'true') {
			inseriscifornitore();
		}

		function visualizzaDettagli() {
			$('#elencoForm').attr('action', 'getDatiInserimento.do').submit();
		}
		function avanti() {
			//var idSel = $("input[name=idFornitoreSelezionato]").val();
			if ($("input:radio:checked").length > 0)
				$('#elencoForm').attr('action', 'confermaInseriscifornitore.do').submit();
			else
				showMessageBox("Attenzione", "Per proseguire è necessario selezionare un fornitore.", "modal-large");
		}
		function cercafornitori() {
			$('#mainForm').show();
			$('#indietroDiv').hide();

			$('#nuovofornitoreform').hide();
			$('.stdMessagePanel').remove();
			var $val = $('#filtroPIVA').val();
			var $listSportelli = $('#elencofornitori');
			if ($val == '') {
				$listSportelli.empty();
				//return;
			}

			$('#elencofornitori').hide();
			$('#elencofornitorilabel').hide();
			$('#loadingdiv').show();

			var page = "ricerca_fornitori.json";
			$
					.ajax({
						url : page,
						type : "POST",
						data : "piva=" + $val,
						async : true
					})
					.success(

							function(data) {

								$('#elencofornitori').show();
								$('#elencofornitorilabel').show();
								$('#loadingdiv').hide();
								$listSportelli.empty();

								if ($(data).length <= 0) {
									$('#firstRow')
											.before(
													"<div class=\"stdMessagePanel\">"
															+ " <div class=\"alert alert-warning\">"
															+ "  <p>Non è stato trovato nessun fornitore! Crea nuovo fornitore scegliendo \"inserisci\"</p>"
															+ " </div>" + " </div>");
									$('#nuovofornitorediv').show();
								}

								if (data != "") {
									if ($(data).get(0).id < 0) {
										$('#firstRow').before(
												"<div class=\"stdMessagePanel\">" + " <div class=\"alert alert-warning\">" + "  <p>"
														+ $(data).get(0).descrizione + "</p>" + " </div>" + " </div>");
										$('#nuovofornitorediv').show();
									}
									$(data)
											.each(
													function(index, sportello) {

														if (sportello.id > 0) {
															if (sportello.codice == "checked")
																$listSportelli
																		.append("<div class=\"row\">"
																				+ "<div class=\"col-lg-12\">"
																				+ "<div class=\"input-group\">"
																				+ "<span class=\"input-group-addon\"><input type=\"radio\" value=\""+sportello.id+"\" name=\"idFornitoreSelezionato\" checked=\"checked\"></input> </span>"
																				+ "<input type=\"text\" style=\"background-color:#FFF;max-width:200em;\" class=\"form-control\" value=\""+sportello.descrizione+"\" readonly=\"readonly\">"
																				+ " </div><!-- /input-group -->"
																				+ "</div><!-- /.col-lg-9 -->" + " </div>");
															else
																$listSportelli
																		.append("<div class=\"row\">"
																				+ "<div class=\"col-lg-12\">"
																				+ "<div class=\"input-group\">"
																				+ "<span class=\"input-group-addon\"><input type=\"radio\" value=\""+sportello.id+"\" name=\"idFornitoreSelezionato\"></input></span>"
																				+ "<input type=\"text\" style=\"background-color:#FFF;max-width:200em;\" class=\"form-control\" value=\""+sportello.descrizione+"\" readonly=\"readonly\">"
																				+ " </div><!-- /input-group -->"
																				+ "</div><!-- /.col-lg-9 -->" + " </div>");
														}
													});

								}
							}).error(function() {
						return false;

					});

		}

		function inseriscifornitore() {
			$('#mainForm').hide();
			$('#nuovofornitoreform').show();
		}

		function ricercaComune() {
			return openPageInPopup("popup_ricerca_comune.do", "ricerca_comuni", "Elenco comuni/Stati esteri", 'modal-lg');
		}

		function selezionaComune(self, istat, cap, istatProvincia) {
			$('#extIstatComune').val(istat);
			$('#istatProvincia').val(istatProvincia);
			$('#comuneSedeLegale').val($(self).html());
			$('#comuneSedeLegaleHidden').val($(self).html());
			closeModal();
			return false;
		}

		function cercafornitoriIniziali() {

			$('#mainForm').show();
			$('#indietroDiv').hide();

			$('#nuovofornitoreform').hide();
			$('.stdMessagePanel').remove();
			var $val = $('#filtroPIVA').val();
			var $listSportelli = $('#elencofornitori');
			if ($val == '') {
				$listSportelli.empty();
				//return;
			}

			$('#elencofornitori').hide();
			$('#elencofornitorilabel').hide();
			$('#loadingdiv').show();

			var page = "ricerca_fornitori.json";
			$
					.ajax({
						url : page,
						type : "POST",
						data : "piva=" + $val,
						async : true
					})
					.success(

							function(data) {
								$('#loadingdiv').hide();

								if (data != "") {
									$('#elencofornitori').show();
									$('#elencofornitorilabel').show();
									$listSportelli.empty();
								}
								if (data != "")
									$(data)
											.each(
													function(index, sportello) {

														if (sportello.codice == "checked")
															$listSportelli
																	.append("<div class=\"row\">"
																			+ "<div class=\"col-lg-12\">"
																			+ "<div class=\"input-group\">"
																			+ "<span class=\"input-group-addon\"><input type=\"radio\" value=\""+sportello.id+"\" name=\"idFornitoreSelezionato\" checked=\"checked\"></input> </span>"
																			+ "<input type=\"text\" style=\"background-color:#FFF;max-width:200em;\" class=\"form-control\" value=\""+sportello.descrizione+"\" readonly=\"readonly\">"
																			+ " </div><!-- /input-group -->" + "</div><!-- /.col-lg-9 -->"
																			+ " </div>");
														else
															$listSportelli
																	.append("<div class=\"row\">"
																			+ "<div class=\"col-lg-12\">"
																			+ "<div class=\"input-group\">"
																			+ "<span class=\"input-group-addon\"><input type=\"radio\" value=\""+sportello.id+"\" name=\"idFornitoreSelezionato\"></input></span>"
																			+ "<input type=\"text\" style=\"background-color:#FFF;max-width:200em;\" class=\"form-control\" value=\""+sportello.descrizione+"\" readonly=\"readonly\">"
																			+ " </div><!-- /input-group -->" + "</div><!-- /.col-lg-9 -->"
																			+ " </div>");

													});
							}).error(function() {
						return false;

					});

		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />