<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<m:error />



<form name="aggiornaFlagRendizontazione" id="aggiornaFlagRendizontazione" method="post" action="aggiornaFlagRendizontazione.do">
	<c:if test="${showMsgWarning != null}">
		<div class="stdMessagePanel">
			<div class="alert alert-warning">
				<p>
					<strong>Attenzione!</strong><br /> In seguito alla modifica gli importi associati ai documenti di spesa e agli interventi già presenti in elenco
					dovranno tener conto del nuovo tipo di rendicontazione appena impostato.
				</p>
			</div>
		</div>
	</c:if>

	<c:choose>
		<c:when test="${canModifyRendicontazioneIva==false}">
			<div class="stdMessagePanel">
				<div class="alert alert-danger">
					<p>
						<strong>Attenzione!</strong><br /> Esiste già almeno un documento di spesa associato agli interventi, non è possibile proseguire con
						l'operazione.
					</p>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<m:radio-list labelSize="6" label="Il beneficiario può rendicontare l'IVA" inline="true" id="flagRendicontazione" list="${flagRendicontazioneList}"
				valueProperty="id" textProperty="descrizione" name="flagRendicontazione" selectedValue="${flagRendicontazioneConIva}"></m:radio-list>
			<div style="clear: both;"></div>
		</c:otherwise>
	</c:choose>
	<div class="puls-group" style="margin-top: 2em">
		<div class="pull-left">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		</div>
		<c:if test="${canModifyRendicontazioneIva==true}">
			<div class="pull-right">
				<button type="submit" name="confermaFlag" id="confermaFlag" class="btn btn-primary" onclick="return verificaConferma()">conferma</button>

			</div>
		</c:if>
	</div>


</form>

<br>
<br>
<br>
<br>
<script type="text/javascript">
	function verificaConferma() {
		if ($("input[type='radio']:checked").length != 1) {
			alert("Indicare se il beneficiario può rendicontare l'IVA");
			return false;
		}
		return true;
	}
</script>