<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em" action="../cunembo214m/confermaUnsplit_${idProcOggSanzione}.do">
			
	<h4>Sei sicuro di voler unificare le sanzioni automatiche selezionate? </h4>
						
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
	</div>
</form>