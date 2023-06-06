<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />

<c:choose>
	<c:when test="${len==0}">
			<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
				<h4>Selezionare almeno una scorta da eliminare.</h4>
				<div class="form-group puls-group" style="margin-top: 1.5em">
				<div class="col-sm-12">
					<button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
				</div>
				<br/>
				</div>
			</form>
	</c:when>
	<c:when test="${nInterventi > 0 }">
	<div class="stdMessagePanel stdMessageLoad">
		<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
			<div class="alert alert-danger">
				<p><strong>Attenzione!</strong><br/>Impossibile eliminare le scorte che sono associate a dei danni per i quali esistono degli interventi.</p>
			</div>
			<a data-dismiss="modal" id="conferma" class="btn btn-default">Annulla</a>
			<br/>
			<br/>
		</form>
	</div>
	</c:when>
	<c:otherwise>
		<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
			<c:if test="${len==1}">
			  <h4>Sei sicuro di voler eliminare questa scorta?</h4>
			</c:if>
			<c:if test="${len>1}">
			  <h4>Sei sicuro di voler eliminare queste ${len} scorte?</h4>
			</c:if>
			<c:choose>
				<c:when test="${nDanniScorte == 1}">
				<h4>Il danno associato sarà eliminato. Vuoi continuare?</h4>
				</c:when>
				<c:when test="${nDanniScorte > 1}">
				<h4>I ${nDanniScorte} danni associati saranno eliminati. Vuoi continuare?</h4>	
				</c:when>
			</c:choose>
			
			  <div class="form-group puls-group" style="margin-top: 1.5em">
			    <div class="col-sm-12">
			      <button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
			      <button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">Conferma</button>
			    </div>
			    <c:forEach items="${ids}" var="i">
			    	<input type="hidden" name="idScortaMagazzino" value="${i}" />
			   	</c:forEach>    
			  </div>
			  <script type="text/javascript">
			    function confermaElimina()
			    {
			      $.ajax({
			            type: "POST",
			            url: '../cunembo297e/elimina.do',
			            data: $('#deleteForm').serialize(),
			            dataType: "html",
			            async:false,
			            success: function(html) 
			            {
			                var COMMENT = '<success';
			                if (html != null && html.indexOf(COMMENT) >= 0) 
			                {
			                  window.location.reload();
			                } 
			                else 
			                {
			                  $('#dlgEliminaScorte .modal-body').html(html);
			                  doErrorTooltip();
			                }            
			            },
			            error: function(jqXHR, html, errorThrown) 
			             {
			                writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
			             }  
			        });
			    }
			  </script>
			</form>
	</c:otherwise>
</c:choose>