<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />
<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
<c:if test="${len==1}">
  <h4>Sei sicuro di voler eliminare questo intervento?</h4>
</c:if>
<c:if test="${len>1}">
  <h4>Sei sicuro di voler eliminare questi ${len} interventi?</h4>
</c:if>
  <div class="form-group puls-group" style="margin-top: 1.5em">
    <div class="col-sm-12">
      <button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
      <button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaElimina()">conferma</button>
    </div>
    <c:forEach items="${ids}" var="i"><input type="hidden" name="idIntervento" value="${i}" /></c:forEach>    
  </div>
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
                let COMMENT = '<success';
                if (html != null && html.indexOf(COMMENT) >= 0) 
                {
                  window.location.reload();
                } 
                else 
                {
                  $('#dlgEliminaIntervento .modal-body').html(html);
                  doErrorTooltip();
                }            
            },
            error: function(jqXHR, html, errorThrown) 
             {
                writeModalBodyError("Si � verificato un errore grave nell'accesso alla funzionalit� di eliminazione. Se il problema persistesse si prega di contattare l'assistenza tecnica");
             }  
        });
    }
  </script>
</form>