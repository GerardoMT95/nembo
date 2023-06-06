<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li class="active">Certificato Antimafia</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<m:error />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<h3>Certificato Antimafia</h3>
		<form:form method="post" class="form-horizontal" id="formAziende" action="">
			<div class="container-fluid" id="popupContent">
				<m:panel id="ricercaPanel" collapsible="true" title="Ricerca">
					<input type="hidden" name="errorCuaa" value="" />
					<span id="errorBox"></span>
					<div id="popupFilters">
						<div class="col-sm-12">
							<m:textfield id="cuaaFiltro" name="cuaa" label="CUAA:" controlSize="9" labelSize="3" maxlength="16"></m:textfield>
						</div>
						<br style="clear: left"> <br />

						<!--  div class="col-sm-12">
					<m:textfield id="denominazioneAziendaFiltro" name="denominazione" label="Denominazione azienda: " controlSize="9" labelSize="3"></m:textfield>
				</div>
							<br style="clear: left"> <br />-->

						<div class="col-sm-12 form-">
							<input type="button" class="btn btn-primary pull-right" id="cerca" onclick="popup_cercaAzienda();return false;" value="Cerca Azienda"
								title="Cerca Azienda"></input>
						</div>
					</div>
				</m:panel>
				<br style="clear: left"> <br />

				<div id="tableDatiGenerali">
					<h3>Dati generali</h3>
					<table id='tblDatiGenerali' summary="Dati generali" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
						data-undefined-text='' data-url="getDatiAziendaCertificato.json">
						<thead>
							<tr>
								<th data-field="cuaa">CUAA</th>
								<th data-field="denominazione">Denominazione</th>
								<th data-field="indirizzoSedeLegale">Sede Legale</th>
							</tr>
						</thead>
					</table>
				</div>

				<div id="tableTitolare">
					<h3>Titolare Rappresentante Legale</h3>
					<table id='tblTitolare' summary="Titolare Rapp. Legale" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
						data-undefined-text='' data-url="getDatiTitolare.json">
						<thead>
							<tr>
								<th data-field="codiceFiscale">Codice fiscale</th>
								<th data-field="cognome">Cognome</th>
								<th data-field="nome">Nome</th>
								<th data-field="telefono">Telefono</th>
								<th data-field="email">Email</th>
							</tr>
						</thead>
					</table>
				</div>


				<div id="tableCertificato">
					<h3>Certificato antimafia</h3>
					<table id='tblCert' summary="Certificato antimafia" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
						data-undefined-text='' data-url="getDatiCertificato.json">
						<thead>
							<tr>
								<th data-field="statoCertificato">Stato del certificato</th>
								<th data-field="dataRichiestaStr">Data richiesta</th>
								<th data-field="dataScadenzaStr">Data scadenza</th>
								<th data-field="prefetturaRiferimento">Prefettura riferimento</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</form:form>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>

	<script>
		$(document).ready(function() {
			// Get the input field
			var input = document.getElementById("cuaaFiltro");

			// Execute a function when the user releases a key on the keyboard
			input.addEventListener("keydown", function(event) {
				// Number 13 is the "Enter" key on the keyboard
				if (event.keyCode === 13) {
					// Trigger the button element with a click
					{
						$('#cerca').click();
						event.preventDefault();
						return false;
					}
				}
			});
		});

		function popup_cercaAzienda() {

			$('#errorBox').html("");

			var cuaa = $.trim($('#cuaaFiltro').val());
			var denominazioneAzienda = $.trim($('#denominazioneAziendaFiltro').val());
			var ok = true;
			if (cuaa == "" && denominazioneAzienda == "") {
				$('#errorBox').html("<div class='alert alert-danger'>Inserire un filtro di ricerca</div>");
				ok = false;
			} else {
				if (cuaa != '') {
					if (cuaa.length != 11 && cuaa.length != 16) {
						$('#errorBox').html("<div class='alert alert-danger'>Cuaa non valido</div>");
						ok = false;
					}
				} else {
					if (denominazioneAzienda.length < 4) {
						$('#errorBox').html("<div class='alert alert-danger'>Inserire almeno 4 caratteri in denominazione azienda</div>");
						ok = false;
					}
				}
			}
			if (ok) {

				$.ajax({
					type : "POST",
					url : "cerca.do",
					data : $('#formAziende').serialize(),
					async : false,
					success : function(data) {
						$("#tableDatiGenerali").show();
						$("#tableTitolare").show();
						$("#tableCertificato").show();
						$("#tblDatiGenerali").bootstrapTable('refresh', []);
						$("#tblTitolare").bootstrapTable('refresh', []);
						$("#tblCert").bootstrapTable('refresh', []);
					}
				});

			}

		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />