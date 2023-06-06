<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />
<div class="stdMessagePanel stdMessageLoad">
	<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
		<div class="alert alert-danger">
			<p><strong>Attenzione!</strong><br/>Impossibile ${azione} le scorte che sono associate a danni per i quali esistono degli interventi.</p>
		</div>
		<a data-dismiss="modal" id="conferma" class="btn btn-default">Annulla</a>
		<br/>
		<br/>
	</form>
</div>