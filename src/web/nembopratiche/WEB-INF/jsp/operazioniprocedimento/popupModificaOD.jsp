<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<b:error />

<form:form action="" modelAttribute="trasmissioneForm" id="formTrasmettiOggetto" method="post">
	<div class="container-fluid">

		<div class="stdMessagePanel" id="msgErroreOD"></div>
		      	
		<div id="msgAttesa" class="stdMessagePanel stdMessageLoad" style="margin-top: 30px; margin-bottom: 50px; display: none">
			<div class="alert alert-info">
				<p>Attenzione: l'operazione potrebbe richiedere alcuni secondi.</p>
			</div>
			<span class="please_wait" style="vertical-align: middle"></span> Attendere prego ...
		</div>
		<c:if test="${!empty isTrasferimentoMassivo}">
			<div class="stdMessagePanel" id="msgMassivo">
				<div class="alert alert-warning">
					<p>Attenzione! Si stanno per modificare i dati relativi a tutte le pratiche selezionate.</p>
				</div>
			</div>
		</c:if>

		<div id="contenutopopup">
			<h4>Situazione attuale</h4>
			<table class="table table-hover table-striped table-bordered tableBlueTh">
				<thead>
					<tr>
						<th>Amministrazione (Organismo Delegato)</th>
						<c:if test="${empty isTrasferimentoMassivo}">
							<th>Responsabile</th>
						</c:if>
						<th>Dettaglio Amministrazione</th>
						<c:if test="${empty isTrasferimentoMassivo}">
							<th>Ufficio di zona</th>
							<th>Funzionario istruttore</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><c:out value="${delegatoDTO.descrizioneAmm}"></c:out></td>
						<c:if test="${empty isTrasferimentoMassivo}">
							<td><c:out value="${delegatoDTO.responsabile}"></c:out></td>
						</c:if>
						<td><c:out value="${delegatoDTO.dettaglioAmm}"></c:out></td>
						<c:if test="${empty isTrasferimentoMassivo}">
							<td><c:out value="${delegatoDTO.descrUffZona}"></c:out></td>
							<td><c:out value="${delegatoDTO.descrTecnico}"></c:out></td>
						</c:if>
					</tr>
				</tbody>
			</table>

			<m:select id="ammcompetenze" list="${ammcompetenzelist}" onchange="refreshUfficiZona()" labelSize="4" addOptionsTitle="true"
				textProperty="descrizione" header="false" valueProperty="idAmmCompetenza" name="ammcompetenze" label="Nuovo Organismo Delegato *"
				selectedValue="${idAmmSelezionato}"></m:select>
			<div style="clear: both; height: 1em"></div>
			<m:select id="ufficiozona" list="${ufficiZonalist}" labelSize="4" onchange="refreshTecnici()" addOptionsTitle="true" textProperty="descrizione"
				header="true" valueProperty="id" name="ufficiozona" label="Ufficio di zona *" selectedValue="${idUfficioSelezionato}"></m:select>
			<div style="clear: both; height: 1em"></div>
			<m:select id="tecnici" list="${tecniciList}" labelSize="4" addOptionsTitle="true" textProperty="descrizione" header="true" valueProperty="id"
				name="tecnici" label="Funzionario istruttore:" selectedValue="${idTecnicoSelezionato}"></m:select>
			<div style="clear: both; height: 1em"></div>

			<m:textarea name="note" id="note" label="Note" labelSize="4"></m:textarea>
			<div style="clear: both; height: 1em"></div>
			<em>I campi contrassegnati con * sono obbligatori</em><br>

			<div class="form-group puls-group elencoAzioni" style="margin-top: 6em">
				<div class="col-sm-12">
					<button type="button" data-dismiss="modal" class="btn btn-default">Chiudi</button>
					<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right elencoStampe" onclick="return submitConferma();">Conferma</button>
				</div>
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">

	function submitConferma() {
		$('#msgErroreOD').html(' ');
		$("#msgAttesa").show();
		$("#contenutopopup").hide();
		$('#msgMassivo').hide();
		
		submitFormViaAjaxAsync('formTrasmettiOggetto', visualizzaMessaggi);
		return false;
	}

	function refreshUfficiZona() {
		$.ajax({
			type : "POST",
			url : "../cunembo251/refreshUfficiZona.do",
			data : "idAmministrazione=" + $('#ammcompetenze').val(),
			dataType : "html",
			async : false,
			success : function(data) {
				$('#ufficiozona').html(data);
			}
		});
		refreshTecnici();
	}
	function refreshTecnici() {

		var x = $('#ufficiozona').val();
		if (x === undefined || x == null || x == "")
			x = "-1";
		$.ajax({
			type : "POST",
			url : "../cunembo251/refreshTecnici.do",
			data : "idUfficioZona=" + x,
			dataType : "html",
			async : false,
			success : function(data) {
				$('#tecnici').html(data);
			}
		});
	}
	showPleaseWait('elencoProcedimenti');

	function visualizzaMessaggi(data, success) {

		if (success) {
			if (data.indexOf("redirect:../cunembo129") != -1) {
				window.location.href = data.split(":")[1];
				return false;
			} else 
				if (data.indexOf("SUCCESS-MASSIVO") != -1) {
					//window.location.reload();						
					$('#elencoProcedimenti').bootstrapTable('refresh');
					$('#dlgTrasferimento').modal('hide');
					return false;
				} else{
				$("#msgAttesa").hide();
				$("#contenutopopup").show();
				$('#msgErroreOD').html('<div class=\"alert alert-danger\" ><p>' + data + '</p></div>');
				$('#msgMassivo').show();
			}
		} else {
			$("#msgAttesa").hide();
			$("#contenutopopup").show();
			$('#msgErroreOD').html('<div class=\"alert alert-danger\" ><p>' + data + '</p></div>');
			$('#msgMassivo').show();
			
		}
	}
</script>