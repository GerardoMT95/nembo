<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">

	<h4>${msgElimina}</h4>
	<c:if test="${msgElimina == null || msgElimina == ''}"><h4>Eliminare il documento selezionato?</h4></c:if>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina(${idDocumentoSpesa})">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
        var idDocumentoSpesa = '${idDocumentoSpesa}';
        $.ajax(
          {
            type : "GET",
            url : '../cunembo263e/elimina_'+idDocumentoSpesa+'.do',
            dataType : "html",
            async : false,
            success : function(html)
            {
              var COMMENT = '<success>';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                window.location.reload();
              }
              else
              {
                doErrorTooltip();
                writeModalBodyError(COMMENT);
              }
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("ERRORE:");
            }
          });
    }
  </script>
</form>