<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">

	<h4>Si sta per eliminare il file. Continuare?</h4>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina(${idDocumentoSpesaFile})">conferma</button>
	</div>
	<script type="text/javascript">
    function confermaElimina()
    {
        let idDocumentoSpesaFile = '${idDocumentoSpesaFile}';
        $.ajax(
          {
            type : "GET",
            url : '../cunembo263e/eliminaAllegato_'+idDocumentoSpesaFile+'.do',
            dataType : "html",
            async : false,
            success : function(html)
            {
              let COMMENT = 'success';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                  location.reload();
              }

            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si � verificato un errore grave nell'accesso alla funzionalit� di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
  </script>
</form>