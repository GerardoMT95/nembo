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
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Ricerca documenti</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />

	<div class="container-fluid" id="content">
		<h3>Registro fatture</h3>

		<m:panel title="Ricerca documenti" id="panelDocumenti" collapsible="false">
			<div class="col-sm-12">
				<form name="inserisciForm" id="elencoForm" method="post" action="" class="form-horizontal" style="margin-top: 2em" enctype="multipart/form-data">
					<c:if test="${!empty msgWarning}">
						<div class="stdMessagePanel">
							<div id="messaggio" class="alert alert-warning">
								<p>
									<strong>Attenzione!&nbsp;</strong>${msgWarning }</p>
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label data-toggle="modal" data-target="#tipiDocModal" onclick="selezionaPopupOptions('idTipoDocumento')" for="bando"
							class="col-sm-3 control-label link"> Tipo documento * <span style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-9">
							<m:select multiple="true" size="5" id="idTipoDocumento" header="false" list="${a}" name="idTipoDocumento"
								addOptionsTitle="true" onclick="return false;" readonly="true"></m:select>
							<m:select multiple="true" size="5" id="idTipoDocumento2" header="false" list="${elencoTipiDocumenti}" name="idTipoDocumento2" preferRequestValues="${prfReqValues}"
								addOptionsTitle="true" onclick="return false;" readonly="true" style="display:none;"></m:select>
						</div>
					</div>
					<div class="form-group">
						<label data-toggle="modal" data-target="#modPagModal" onclick="selezionaPopupOptions('idModalitaPagamento')" for="bando"
							class="col-sm-3 control-label link"> Modalit&agrave; pagamento * <span style="text-decoration: none;" class="icon-large icon-folder-open blueFolder"></span>
						</label>
						<div class="col-sm-9">
							<m:select header="false" multiple="true" size="5" id="idModalitaPagamento" list="${b}" name="idModalitaPagamento"
								addOptionsTitle="true" onclick="return false;" readonly="true"></m:select>
							<m:select header="false" multiple="true" size="5" id="idModalitaPagamento2" list="${elencoModalitaPagamento}" name="idModalitaPagamento2"
								preferRequestValues="${prfReqValues}" addOptionsTitle="true" onclick="return false;" style="display:none;"></m:select>
						</div>
					</div>

					<m:textfield disabled="true" label="Fornitore: " labelSize="3" controlSize="9" id="fornitore" name="fornitore" value="${codiceFornitore}">
							<m:input-addon left="true" button="true">
								<button style="margin-bottom:1em;height:2.46em;" class="btn btn-default pull-right" onclick="pulisciCampoFornitore();return false;"><span class="ico24 ico_trash"></span></button>
							</m:input-addon>
							<m:input-addon left="false" button="true">
								<input type="button" style="margin-bottom:1em;height:2.46em;" class="btn btn-primary pull-right" type="button" onclick="ricercaFornitore();" value="cerca">
							</m:input-addon>
					</m:textfield>
					<input type="hidden" id="idFornitore" name="idFornitore" value="${idFornitore}">
					<m:textfield label="Da: " type="DATE" id="dataInizio" name="dataInizio" preferRequestValues="${prfReqValues}" value="${dataDa }"></m:textfield>
					<m:textfield label="A: " type="DATE" id="dataFine" name="dataFine" preferRequestValues="${prfReqValues}" value="${dataA }"></m:textfield>

					<div class="col-sm-12 ">
						<div class="puls-group" style="margin-top: 2em">
							<div class="pull-left">
								<button type="button" onclick="forwardToPage('../index.do');" class="btn btn-default">indietro</button>
							</div>
							<div class="pull-right">
								<button type="button" name="conferma" id="conferma" onclick="confermaDatiRicerca()" class="btn btn-primary">conferma</button>
							</div>
						</div>
					</div>
					<br> <br> <br> <br> <br> <br> <input type="hidden" id="idsTipi" name="idsTipi"> <input type="hidden"
						id="idsModPag" name="idsModPag">

					<!-- POPUP TIPI DOC INI -->
					<div class="modal fade" id="tipiDocModal" tabindex="-1" role="dialog" aria-labelledby="tipiDocModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width: 850px">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Filtro tipologia documento</h4>
								</div>
								<div class="modal-body">

									<div>
										<a href="javascript:selectAll('all_idTipoDocumento', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
											href="javascript:selectAll('all_idTipoDocumento', false);" class="link">Deseleziona tutto</a>
									</div>
									<div class="form-group">
										<div class="col-sm-12">
											<m:select name="all_idTipoDocumento" id="all_idTipoDocumento" addOptionsTitle="true" header="false" list="${elencoTipiDocumenti}"
												valueProperty="id" textProperty="descrizione" multiple="true" size="20"></m:select>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<div class="puls-group" style="margin-top: 1em">
										<div class="pull-left">
											<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
										</div>
										<div class="pull-right">
											<button type="button" onclick="filtra('all_idTipoDocumento');$('#tipiDocModal').modal('hide');" class="btn btn-primary">Conferma</button>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- POPUP TIPI DOC FINE -->

					<!-- POPUP MOD PAG INI -->
					<div class="modal fade" id="modPagModal" tabindex="-1" role="dialog" aria-labelledby="modPagModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width: 850px">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Filtro modalità pagamento</h4>
								</div>
								<div class="modal-body">

									<div>
										<a href="javascript:selectAll('all_idModalitaPagamento', true);" class="link">Seleziona tutto</a> &nbsp;/&nbsp; <a
											href="javascript:selectAll('all_idModalitaPagamento', false);" class="link">Deseleziona tutto</a>
									</div>
									<div class="form-group">
										<div class="col-sm-12">
											<m:select name="all_idModalitaPagamento" id="all_idModalitaPagamento" addOptionsTitle="true" header="false"
												list="${elencoModalitaPagamento}" valueProperty="id" textProperty="descrizione" multiple="true" size="20"></m:select>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<div class="puls-group" style="margin-top: 1em">
										<div class="pull-left">
											<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
										</div>
										<div class="pull-right">
											<button type="button" onclick="filtra('all_idModalitaPagamento');$('#modPagModal').modal('hide');" class="btn btn-primary">Conferma</button>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- POPUP TIPI DOC FINE -->


				</form>
			</div>
		</m:panel>
		<input type="hidden" id="idsTipoDocumentoStr" value="${idsTipoDocumentoStr}"> <input type="hidden" id="idsModalitaPagamentoStr"
			value="${idsModalitaPagamentoStr}">
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
		function ricercaFornitore() {
			openPageInPopup('ricercaFornitorePopup.do', 'dlgInserisci', 'Cerca fornitore', 'modal-lg', false);
		}

		function pulisciCampoFornitore() {
			$("#fornitore").val('');
			$("#idFornitore").val('');
		}
		function selectSelected() {
			//seleziono gli elementi che erano già selezionati
			var idsTipo = $("#idsTipoDocumentoStr").val();
			var idsModPag = $("#idsModalitaPagamentoStr").val();

			if ((idsTipo === undefined || idsTipo == null || idsTipo == '')
					&& (idsModPag === undefined || idsModPag == null || idsModPag == ''))
				return false;

			var idsT = idsTipo.split("&&&");
			var idsM = idsModPag.split("&&&");

			$("#idTipoDocumento2").find("option").removeAttr("selected");
			$("#idModalitaPagamento2").find("option").removeAttr("selected");
			
			var i = 0;
			if (idsT !== undefined && idsT.length != 0) {
				for (i = 0; i < idsT.length - 1; i++) {
					$("#idTipoDocumento2").find("option[value=" + idsT[i] + "]").prop('selected', true);
				}
			}

			if (idsM !== undefined && idsM.length != 0) {
				for (i = 0; i < idsM.length - 1; i++) {
					$("#idModalitaPagamento2").find("option[value=" + idsM[i] + "]").prop('selected', true);
				}
			}
			 $("#idTipoDocumento").append($("#idTipoDocumento2").find("option:selected"));
			 $("#idModalitaPagamento").append($("#idModalitaPagamento2").find("option:selected"));
			 $("#idTipoDocumento").find("option").attr("disabled","disabled").removeAttr("selected");
			 $("#idModalitaPagamento").find("option").attr("disabled","disabled").removeAttr("selected");
			 
		}
		
		selectSelected();
		function confermaDatiRicerca() {

			var i;
			var idsTipi = "";
			var idsMod = "";
			for (i = 0; i < $("#idTipoDocumento").find("option").length; i++) {
				idsTipi += $("#idTipoDocumento").find("option")[i].value + "&&&";
			}
			for (i = 0; i < $("#idModalitaPagamento").find("option").length; i++) {
				idsMod += $("#idModalitaPagamento").find("option")[i].value + "&&&";
			}

			$("#idsTipi").val(idsTipi);
			$("#idsModPag").val(idsMod);

			 $("#idTipoDocumento2").append($("#idTipoDocumento").find("option").clone());
			 $("#idModalitaPagamento2").append($("#idModalitaPagamento").find("option").clone());
			 $("#idTipoDocumento2").find("option").attr("selected","selected");
			 $("#idModalitaPagamento2").find("option").attr("selected","selected");
			 $('#elencoForm').attr('action', 'confermaDatiRicerca.do').submit();
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />