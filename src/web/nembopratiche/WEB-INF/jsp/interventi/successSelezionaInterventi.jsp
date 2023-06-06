<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<span class="please_wait" style="vertical-align:middle"></span> Attendere prego, caricamento in corso...
<form id="succesSelezionaInterventi" name="succesSelezionaInterventi" action="../cunembo${cuNumber}i/inserisci.do" method="post">
	<c:forEach items="${idDescrizioneIntervento}" var="id">
		<input type="hidden" name="id" value="${id}">
	</c:forEach>
	<c:if test="${idDannoAtm != null}">
		<input type="hidden" name="idDannoAtm" id="idDannoAtm" value="${idDannoAtm}" />
	</c:if>
</form>
<script type="text/javascript">
  $('#succesSelezionaInterventi').submit();
</script>
