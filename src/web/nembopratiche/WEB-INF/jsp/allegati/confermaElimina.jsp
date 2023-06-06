<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<b:error />
<form action="../cunembo121/inserisci_${idFileAllegati}.do" method="post" class="form-horizontal" id="insertForm" style="margin-top: 1em" target="hiddenIFrame">
	Sei sicuro di voler cancellare questo allegato? L'operazione non sar&agrave; annullabile.
	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
		</div>
	</div>
	<script type="text/javascript">
		function confermaElimina()
		{
			$.ajax({
            type: "GET",
            url: '../cunembo121/elimina_${idFileAllegati}.do',
            dataType: "html",
            async:false,
            success: function(data) 
            {
                $('#dlgElimina').modal('hide');
                $('body').append('<form id=\"refreshForm_Allegati\" action=\"../cunembo108/index.do\"></form>');
                $('#refreshForm_Allegati').submit();
            }
        });
		}
	</script>
</form>
