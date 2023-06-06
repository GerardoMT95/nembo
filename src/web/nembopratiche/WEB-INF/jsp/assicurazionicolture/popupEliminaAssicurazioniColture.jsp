<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />

<c:choose>
	<c:when test="${len==0}">
				<div class="container-fluid">
					<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
						<div class="row">
							<div class="col-md-12">
								<h4>Selezionare almeno un'assicurazione da eliminare.</h4>
							</div>
						</div>
						<div class="row">
							<div class="form-group puls-group" style="margin-top: 1.5em">
								<div class="col-md-12">
									<button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
								</div>
							</div>
						</div>
					</form>
				</div>
	</c:when>
	<c:otherwise>
		<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
			<div class="container-fluid">		
					<div class="row">
						<div class="col-sm-12">
							<c:if test="${len==1}">
							  <h4>Sei sicuro di voler eliminare questa assicurazione?</h4>
							</c:if>
							<c:if test="${len>1}">
							  <h4>Sei sicuro di voler eliminare queste ${len} assicurazioni?</h4>
							</c:if>
						</div>
					</div>
				  	<div class="form-group puls-group" style="margin-top: 1.5em">
					    <div class="row">
						    <div class="col-sm-12">
						      <button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
						      <button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">Conferma</button>
						    </div>
					    </div>
					    <c:forEach items="${ids}" var="i">
					    	<input type="hidden" name="${idsName}" value="${i}" />
					   	</c:forEach>    
					   	<input type="hidden" name="fieldNameIdName" value="${idsName}" />
					</div>
			</div>
		</form>
	</c:otherwise>
</c:choose>

  <script type="text/javascript">
    function confermaElimina()
    {
      $.ajax({
            type: "POST",
            url: '../cunembo${cuNumber}e/elimina.do',
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
                  var dlgName = "${dlgName}"
                  $('#' + dlgName + ' .modal-body').html(html);
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