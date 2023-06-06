<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<b:error />
<form action="../cunembo121/inserisci_${idFileAllegati}.do" method="post" class="form-horizontal" id="insertForm" style="margin-top: 1em" target="hiddenIFrame">
	<h4>Sei sicuro di voler cancellare questa stampa? L'operazione non sar&agrave; annullabile.</h4>
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
            url: '../cunembo126e/elimina_${idOggettoIcona}.do',
            dataType: "html",
            async:false,
            success: function(html) 
            {
                var COMMENT = '<!--'+'LISTA_STAMPE'+'-->';
                if (html != null && html.indexOf(COMMENT) >= 0) 
                {
                  $('#dlgStampeOggetto').modal('hide');
                  $('#panel_elenco_stampe .panel-body').html(html);
                  return;
                } 
                else 
                {
                  $('#dlgStampeOggetto .modal-body').html(html);
                  doErrorTooltip();
                }            
            }
        });
		}
	</script>
</form>
