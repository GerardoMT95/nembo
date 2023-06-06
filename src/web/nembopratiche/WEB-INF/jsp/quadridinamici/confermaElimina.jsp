<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" action="../cunembo118_${codiceQuadroLC}/elimina_${numProgressivoRecord}.do">
	<div class="col-sm-12">
		<h4 style="padding-bottom:16px">Sei sicuro di voler eliminare questo elemento?</h4>
	</div>
	<div class="form-group puls-group" style="margin-top: 0em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<input type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right" value="conferma" />
		</div>
	</div>
</form>