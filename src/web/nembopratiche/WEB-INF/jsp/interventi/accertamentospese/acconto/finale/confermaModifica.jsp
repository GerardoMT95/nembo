<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
			<h4>Attenzione!</h4>
			Proseguendo con l'operazione saranno reimpostate le sanzioni automatiche, anche quelle per cui è stato effettuato lo split. Per verificarle occorre consultare il quadro "Riduzioni sanzioni".
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
						
		<button type="button" id = "annulla" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
      $("#confermaModificaInterventi").click();
      $("#annulla").click();

    }
  </script>
</form>