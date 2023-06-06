<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<b:error />



<div class="container-fluid">
	<div class="stdMessagePanel">
		<div id="messaggio" class="alert alert-warning">
			<p>${messaggio}</p>
		</div>
	</div>


	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default" onclick="return closeDialog()">${chiudi}</button>
			<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="closeModal(); openPageInPopup('${action}', '${idPopup}', '${titolo}', 'modal-large', true);return false;">${prosegui}</button>
		</div>
	</div>
</div>	
