<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
	<h4>Sei sicuro di voler eliminare questi allegati? L'operazione non sar&agrave; annullabile</h4>
	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
		</div>
	</div>
  <c:forEach items="${ids}" var="i">
    <input type="hidden" name="idFileAllegatiIntervento" value="${i}" />
  </c:forEach>
	<script type="text/javascript">
    function confermaElimina()
    {
      $('#dlgEliminaAllegato button').prop('disabled',true);
      $.ajax(
          {
            type : "POST",
            url : '../cunembo${cuNumber}z/elimina_allegati_${idIntervento}.do',
            dataType : "html",
            data: $('input[name="idFileAllegatiIntervento"]').serialize(),
            success : function(html)
            {
              $('#tblMappeFile').bootstrapTable('refresh',{});
              closeModal();
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
  </script>
</form>