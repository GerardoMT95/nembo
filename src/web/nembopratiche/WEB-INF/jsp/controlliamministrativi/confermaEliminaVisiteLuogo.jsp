<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
	<c:choose>
		<c:when test="${ids.size()==1}">
			<h4>Sei sicuro di voler eliminare questa visita sul luogo?</h4>
		</c:when>
		<c:otherwise>
			<h4>Sono state selezionate ${ids.size()} visite sul luogo, sei sicuro di volerle eliminare?</h4>
		</c:otherwise>
	</c:choose>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<c:forEach items="${ids}" var="id">
			<input type="hidden" name="idVisitaLuogo" value="${id}" />
		</c:forEach>
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
      $
          .ajax(
          {
            type : "POST",
            url : '../cunembo165m/elimina_visite_luogo.do',
            data : $('#deleteForm').serialize(),
            dataType : "html",
            async : false,
            success : function(html)
            {
              var COMMENT = '<success';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                window.location.reload();
              }
              else
              {
                $('#EliminaVisita .modal-body').html(html);
                doErrorTooltip();
              }
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
  </script>
</form>