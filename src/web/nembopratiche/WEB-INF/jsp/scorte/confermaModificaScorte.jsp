<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />

<c:choose>
	<c:when test="${len==0}">
			<form method="post" class="form-horizontal" id="deleteForm" style="margin-top: 1em">
				<h4>Selezionare almeno una scorta da modificare.</h4>
				<div class="form-group puls-group" style="margin-top: 1.5em">
				<div class="col-sm-12">
					<button type="button" data-dismiss="modal" class="btn btn-default">Annulla</button>
				</div>
				<br/>
				</div>
			</form>
	</c:when>
	<c:otherwise>
		<form method="post" action="../cunembo297m/modifica_scorte_dettaglio.do" class="form-horizontal" id="modificaForm" style="margin-top: 1em">
			<c:if test="${len==1}">
			  <h4>Sei sicuro di voler modificare questa scorta?</h4>
			</c:if>
			<c:if test="${len>1}">
			  <h4>Sei sicuro di voler modificare queste ${len} scorte?</h4>
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
					data: $('#modificaForm').submit();
			    }
			  </script>
			</form>
	</c:otherwise>
</c:choose>