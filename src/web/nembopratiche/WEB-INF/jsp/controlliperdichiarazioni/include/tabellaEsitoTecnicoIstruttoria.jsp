<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%-- NON RIMUOVERE IL COMMENTO, SERVE AL JAVASCRIPT PER CAPIRE CHE PAGINA GLI E' STATA INVIATA --%>
<m:panel title="Esito Tecnico" id="esitoTecnico">
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<c:if test="${flagEstratta!=null &&  flagEstratta!='N'}">
			<m:icon icon="modify" href="" onclick="return modificaEsitoTecnico()" />
		</c:if>
	</p:abilitazione-azione>
	<table summary="Esito Tecnico" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
		<tbody>
			<tr>
				<th>Funzionario controllore</th>
				<td>${esito.descrTecnico}</td>
			</tr>
			<tr>
				<th>Funzionario di grado superiore</th>
				<td>${esito.descrGradoSup}</td>
			</tr>
			<tr>
				<th>Esito controlli</th>
				<td>${esito.descrEsito}</td>
			</tr>
			<tr>
				<th>Note</th>
				<td>${esito.note}</td>
			</tr>
		</tbody>
	</table>
</m:panel>
