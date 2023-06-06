<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<b:error />
<form action="../cunembo121/inserisci_${idFileAllegati}.do" method="post" class="form-horizontal" id="insertForm" style="margin-top: 1em" target="hiddenIFrame">
	${messaggio}
	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="return confermaRigenera()">conferma</button>
		</div>
	</div>
	<script type="text/javascript">
	 function confermaRigenera()
	 {
		 var w=500;
		 var h=160;
		 window.open("../cunembo126l/rigenera_${idOggettoIcona}.do","attendere_prego_stampe_"+(new Date().getTime()),'width='+w+',height='+h+',menubar=no,resizable=no,titlebar=no,toolbar=no,scrollbar=no');
		 $('#dlgStampeOggetto').modal('hide');
		 return false;
	 }
	</script>
</form>
