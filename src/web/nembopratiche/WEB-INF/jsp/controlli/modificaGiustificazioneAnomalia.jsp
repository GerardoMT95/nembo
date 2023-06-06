<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<m:error></m:error>
<form id="reloadForm" name="reloadForm" target="_top" method="post" action="../cunembo114/index.do"
	style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)"></form>
<form method="post" class="form-horizontal" id="modifyForm" action="../cunembo234/confermaModifica_${idControllo}_${idSoluzioneAnomalia}.do"
	style="margin-top: 1em" enctype="multipart/form-data" target="hiddenIFrame">
	<h4></h4>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">

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

		<m:textarea id="t" name="t" disabled="true" label="Codice controllo:">${controllo.codice}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" style="min-height:8em;" label="Descrizione controllo:">${controllo.descrizione}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" label="Descrizione anomalia:">${controllo.descrizioneAnomalia}
				</m:textarea>


		<input type="hidden" name="idControllo" value="${idControllo}" /> <input type="hidden" name="idProcedimentoOggetto"
			value="${idProcedimentoOggetto}" />


		<m:select id="idTipoRisoluzione" onchange="getFlagObbligatorieta();" list="${tipiRisoluzioneControlli}" name="idTipoRisoluzione"
			selectedValue="${giustificazione.idTipoRisoluzione}" label="Tipo risoluzione controllo: *" preferRequestValues="${preferRequestValue}"></m:select>


		<c:choose>
			<c:when test="${flagNoteObbligatorie!='S'}">
				<m:textarea id="note" name="note" label="Note: " placeholder="Inserire note (max 4000 caratteri)" preferRequestValues="${preferRequestValue}">${giustificazione.note}</m:textarea>
			</c:when>
			<c:otherwise>
				<m:textarea id="note" name="note" label="Note: *" placeholder="Inserire note (max 4000 caratteri)" preferRequestValues="${preferRequestValue}">${giustificazione.note}</m:textarea>
			</c:otherwise>
		</c:choose>
		<br>
		<c:if test="${!empty modifica}">
			<button class = "btn btn-primary"  onclick="return eliminaFile();" style="min-width: 12em;" value="">Rimuovi file allegato</span></button>
		</c:if>
		<br><br>
		<c:choose>
			<c:when test="${flagFileObbligatorio!='S'}">
				<m:textfield id="nomeAllegato" label="Nome allegato: " name="nomeAllegato" value="${giustificazione.allegato.nomeLogico}"
					preferRequestValues="${preferRequestValue}"></m:textfield>

				<b:file label="File da allegare: " name="fileDaAllegare" value="${giustificazione.allegato.nomeFisico}" />
			</c:when>
			<c:otherwise>

				<m:textfield id="nomeAllegato" label="Nome allegato: *" name="nomeAllegato" value="${giustificazione.allegato.nomeLogico}"
					preferRequestValues="${preferRequestValue}"></m:textfield>

				<b:file label="File da allegare: *" name="fileDaAllegare" id="nomeFisico" value="${giustificazione.allegato.nomeFisico}" />

			</c:otherwise>

		</c:choose>


		<input type="hidden" id="nomeFisicoHidden" name="nomeFisicoHidden" value="${giustificazione.allegato.nomeFisico}">

		<div class="frm_antimafia" style="display: none">
			<fieldset>
				<legend>Dati Richiesta certificati</legend>
				<m:textfield name="amm_competenza" id="amm_competenza" label="Ente competente: Organismo delegato" value="${amm_competenza}" disabled="true"></m:textfield>
				<m:textfield id="prov" name="prov" value="${giustificazione.provPrefettura}" label="Provincia Prefettura di competenza" preferRequestValues="${preferRequestValue}"></m:textfield>
				<m:textfield id="protocollo" name="protocollo" value="${giustificazione.numProtocollo}" label="Numero protocollo" preferRequestValues="${preferRequestValue}"></m:textfield>
				<m:textfield cssClass="date-txt" type="DATE" id="data_protocollo" name="data_protocollo" value="${giustificazione.dataProtocollo}" label="Data protocollo"
					preferRequestValues="${preferRequestValue}"></m:textfield>
				<m:textfield cssClass="date-txt" type="DATE" id="data_documento" name="data_documento" value="${giustificazione.dataDocumento}" label="Data documento"
					preferRequestValues="${preferRequestValue}"></m:textfield>
			</fieldset>
		</div>

		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
		<iframe id="iframeUpload" style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)" name="hiddenIFrame"></iframe>

	</div>
	<script type="text/javascript">

	
		function eliminaFile() {
			$('#nomeAllegato').val('');
			$('#fileName_null').val('');
			$('#nomeFisicoHidden').val('');
			
			return false;
		}

		var first = true;

		$(document).ready(function() {
			$(".date-txt").datepicker({
				dateFormat : 'dd/mm/yy'
			});
			$('#fileName_null').val($('#nomeFisicoHidden').val());
			getFlagObbligatorieta();
			
		});

		function updateModalWindow() {
			if (navigator.vendor.search("Google") < 0 && navigator.vendor.search("Apple") < 0) //controllo da fare perché altrimenti non funziona su chrome, opera e safari al primo click, ma solo al secondo e carica due volte il file
			{
				if (first) {
					first = false;
					return;
				}
			}
			var html = document.getElementById('iframeUpload').contentWindow.document.body.innerHTML;
			if (html != null && html.indexOf('<success') == 0) {
				location.reload();
			} else {
				setDialogHtml(html);
				getFlagObbligatorieta();
			}
		}
		document.getElementById('iframeUpload').onload = updateModalWindow;

		function setDialogHtml(data) {
			$('#dlgModifica .modal-body').html(data);
			doErrorTooltip();
		}

		//chiamata ajax che mi restituisce info relative all'obbligatorieta dei campi
		//in questo modo posso aggiungere o togliere l'asterisco alle lable che indica obbligatorieta
		function getFlagObbligatorieta() {

			var idTipoSol = $("#idTipoRisoluzione").val();
			var element = $("label:contains('Note: *')");
			$(element).empty();
			$(element).append("Note:");
			element = $("label:contains('Nome allegato: *')");
			$(element).empty();
			$(element).append("Nome allegato:");
			element = $("label:contains('File da allegare: *')");
			$(element).empty();
			$(element).append("File da allegare:");
			if (idTipoSol !== undefined && idTipoSol != null && idTipoSol!="") {
				$.ajax({
					type : "GET",
					url : '../cunembo234/getFlagObbligatorieta_' + idTipoSol + '.do',
					success : function(data) {
						if (data.indexOf("entrambi") == 0) {
							var element = $("label:contains('Note:')");
							$(element).empty();
							$(element).append("Note: *");
							element = $("label:contains('Nome allegato:')");
							$(element).empty();
							$(element).append("Nome allegato: *");
							element = $("label:contains('File da allegare:')");
							$(element).empty();
							$(element).append("File da allegare: *");
						}
						if (data.indexOf("soloNote") == 0) {
							var element = $("label:contains('Note:')");
							$(element).empty();
							$(element).append("Note: *");

							element = $("label:contains('Nome allegato: *')");
							$(element).empty();
							$(element).append("Nome allegato:");
							element = $("label:contains('File da allegare: *')");
							$(element).empty();
							$(element).append("File da allegare:");
						}
						if (data.indexOf("soloFile") == 0) {
							var element = $("label:contains('Nome allegato:')");
							$(element).empty();
							$(element).append("Nome allegato: *");
							element = $("label:contains('File da allegare:')");
							$(element).empty();
							$(element).append("File da allegare: *");

							element = $("label:contains('Note: *')");
							$(element).empty();
							$(element).append("Note:");
						}
						if (data.indexOf("nessuno") == 0) {
							var element = $("label:contains('Note: *')");
							$(element).empty();
							$(element).append("Note:");
							element = $("label:contains('Nome allegato: *')");
							$(element).empty();
							$(element).append("Nome allegato:");
							element = $("label:contains('File da allegare: *')");
							$(element).empty();
							$(element).append("File da allegare:");
						}
					}
				});

				if (idTipoSol == '')
					idTipoSol = -1;

				$.ajax({
					type : "GET",
					url : '../cunembo234/isTipoGiustificazioneAntimafia_' + idTipoSol + '.do',
					success : function(data) {
						if (data == "OK") {

							$(".frm_antimafia").show();
						} else {
							$(".frm_antimafia").hide();
						}
					}
				});
			}

		}
	</script>
</form>