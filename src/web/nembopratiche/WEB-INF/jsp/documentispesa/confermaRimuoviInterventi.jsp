<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">

	<h4>I seguenti interventi: <br> ${interventiRimossi} <br> hanno già almeno un documento di spesa associato. Proseguendo con l'operazione saranno eliminate queste associazioni. Si è sicuri di voler proseguire? </h4>
	
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
		$('#elencoForm').attr('action', 'confermainterventi.do').submit();      
    }
  </script>
</form>