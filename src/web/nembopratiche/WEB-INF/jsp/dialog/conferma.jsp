<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<b:error />

<form method="post" action="${action}">

<div class="container-fluid">
	<div class="stdMessagePanel">
		<div id="messaggio" class="alert alert-warning">
			<p>${messaggio}</p>
		</div>
	</div>
	<div style="margin-top: 0.5em;margin-bottom: 1.5em">
		<m:textarea id="note"
			placeholder="Inserire eventuali note (al massimo 4000 caratteri)"
			label="Note" 
			name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>
		</div>	

	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12" style="margin-top: 1.5em">
			<button type="button" data-dismiss="modal" class="btn btn-default" onclick="return closeDialog()">${chiudi}</button>
			<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="changeInPleaseWait('${messaggioAttendere}')">${prosegui}</button>
		</div>
	</div>
</div>	

</form>