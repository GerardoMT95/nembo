<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<m:error />
<form method="post" class="form-horizontal" id="formModificaPercentualeMultipla">
	<c:forEach items="${rangePercentuali}" var="range" varStatus="status">
		<div class="form-group">
			<div class="col-md-9">
				<label id="label_${status.index}" class="form-label" title="${range.tooltip}">${range.label} <a style="text-decoration: none; cursor: pointer"
					class="icon-info-sign" onclick="alert($('#label_${status.index}').attr('title'));return false">&nbsp;</a>
				</label>
			</div>
			<div class="col-md-3">
				<c:choose>
					<c:when test="${range.fixed}">
            <m:textfield id="percentuale_${status.index}" name="${range.key}" value="${range.percentualeContributoMassima}" controlSize="12" disabled="true">
              <m:input-addon left="false">&#37;</m:input-addon>
            </m:textfield>
					</c:when>
					<c:otherwise>
            <m:textfield id="percentuale_${status.index}" name="${range.key}" value="${percentuale}" controlSize="12" preferRequestValues="${preferRequest}">
              <m:input-addon left="false">&#37;</m:input-addon>
            </m:textfield>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:forEach>
	<blockquote>
	Attenzione: la procedura di assegnazione massiva della percentuale imposta per tutti gli interventi selezionati l'importo ammesso uguale all'importo investimento<c:if test="${percentualeRibasso}"> ribassato della percentuale di riduzione</c:if>
	</blockquote>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
		<button type="button" name="conferma" id="conferma" class="btn btn-primary pull-right" onclick="confermaModifica()">conferma</button>
		<c:forEach items="${paramValues.idIntervento}" var="i">
			<input type="hidden" name="idIntervento" value="${i}" />
		</c:forEach>
	</div>
	<script type="text/javascript">
	$('#formModificaPercentualeMultipla').on("keyup keypress", function(e) {
		  var code = e.keyCode || e.which; 
		  if (code  == 13) {   
			confermaModifica();        
		    e.preventDefault();
		    return false;
		  }
		});
    function confermaModifica()
    {
      $
          .ajax(
          {
            type : "POST",
            url : '../cunembo163m/modifica_percentuale_multipla.do',
            data : $('#formModificaPercentualeMultipla').serialize(),
            dataType : "html",
            async : false,
            success : function(html)
            {
              var COMMENT = '<success>' + 'true' + '</success>';
              if (html != null && html.indexOf(COMMENT) >= 0)
              {
                window.location.reload();
              }
              else
              {
                $('#dlgModificaPercentualeMultipla .modal-body').html(html);
                doErrorTooltip();
              }
            },
            error : function(jqXHR, html, errorThrown)
            {
              writeModalBodyError("Si è verificato un errore grave nell'accesso alla funzionalità di aggiornamento. Se il problema persistesse si prega di contattare l'assistenza tecnica");
            }
          });
    }
  </script>
</form>