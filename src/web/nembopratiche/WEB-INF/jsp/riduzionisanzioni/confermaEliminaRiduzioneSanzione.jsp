<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<b:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
	<c:choose>
		<c:when test="${ids.size()==1}">
			<h4>Sei sicuro di voler eliminare questo elemento?<br> </h4>
			
		</c:when>
		<c:otherwise>
			<h4>Sono stati selezionati ${ids.size()} record, sei sicuro di volerli eliminare?</h4>
		</c:otherwise>
	</c:choose>
				<div class="stdMessagePanel">
				<c:if test="${msgErrore != null}">
				<div class="stdMessagePanel">
					<div class="alert alert-danger">
						<p>
							<strong>Attenzione!</strong><br />
							<c:out value="${msgErrore}"></c:out>
						</p>
					</div>
				</div>
			</c:if>
					<div class="alert alert-danger" hidden="true" id="configErr">
						<p>
							<strong>Attenzione!</strong><br />
							Si è verificato un errore della configurazione del quadro "Riduzioni e sanzioni", l'aggiornamento del 
							contributo erogabile è previsto solo per le istruttorie delle domande di acconto e saldo. 
							Non è possibile proseguire con l'operazione.
						</p>
					</div>
				</div>
	<div class="alert alert-warning" role="alert">
			In caso di
			conferma dell'operazione il sistema aggiornerà il contributo
			erogabile e di conseguenza i dati presenti nel quadro di accertamento
			delle spese.</div>
			
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<c:forEach items="${ids}" var="id">
			<input type="hidden" name="idProcOggSanzione" value="${id}" />
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
            url : '../cunembo214e/elimina_riduzioni_sanzioni.do',
            data : $('#deleteForm').serialize(),
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
                //$('#EliminaVisita .modal-body').html(html);
                $("#configErr").show();
                //doErrorTooltip();
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