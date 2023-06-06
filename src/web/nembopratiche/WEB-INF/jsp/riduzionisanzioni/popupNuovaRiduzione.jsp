<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>

<m:error />
<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="INSERISCI">

	<form method="post" class="form-horizontal" id="formRiduzione"
		action="">

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
		<div class="alert alert-warning" role="alert">In caso di
			conferma dell'operazione il sistema aggiornerà il contributo
			erogabile e di conseguenza i dati presenti nel quadro di accertamento
			delle spese.</div>


		<m:select label="Tipologia - Descrizione *" id="tipologia"
			name="idTipologiaSanzioneInvestimento"
			preferRequestValues="${preferRequest}" selectedValue="${idSanzInv}"
			list="${tipologieSanzioniInvestimentoList}" />

		<m:select label="Operazione *" id="operazione" name="operazione"
			preferRequestValues="${preferRequest}" list="${livelli}"
			selectedValue="${idLiv}" />

		<m:textarea name="note" id="note" label="Note"
			placeholder="Inserisci note (max 4000 caratteri)"
			preferRequestValues="${preferRequest}"></m:textarea>

		<m:textfield type="EURO" label="Importo *" id="importo" name="importo"
			preferRequestValues="${preferRequest}" />


		<div class="form-group puls-group"
			style="margin-top: 1.5em; margin-right: 0px">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="button" name="conferma" id="conferma"
				class="btn btn-primary pull-right" onclick="confermaModifica()">conferma</button>
		</div>


	</form>

</p:abilitazione-azione>

<script type="text/javascript">
	initializeDatePicker();
	function confermaModifica() {
		$
				.ajax({
					type : "POST",
					url : '../cunembo214i/popup_inserisci_riduzione.do',
					data : $('#formRiduzione').serialize(),
					dataType : "html",
					async : false,
					success : function(html) {
						var COMMENT = '<success>' + 'true' + '</success>';
						if (html != null && html.indexOf(COMMENT) >= 0) {
							window.location.reload();
						} else {
							$('#dlgInserisci .modal-body').html(html);
							doErrorTooltip();
						}
					},
					error : function(jqXHR, html, errorThrown) {
						writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di aggiornamento. Se il problema persistesse si prega di contattare l'assistenza tecnica");
					}
				});
	}
</script>
