<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%-- NON RIMUOVERE IL COMMENTO, SERVE AL JAVASCRIPT PER CAPIRE CHE PAGINA GLI E' STATA INVIATA --%>
<m:panel title="Esito controllo in loco" id="esitoTecnico">
	
	<c:if test="${!disabilitaControlloLoco}">
		<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
			<m:icon icon="modify" href="" onclick="return modificaEsitoTecnico()" />
		</p:abilitazione-azione>
	</c:if>
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
			  <th>Esito controllo in loco</th>
				<td>${esito.descrEsito}</td>
			</tr>
			<tr>
			  <th>Note</th>
				<td>${esito.note}</td>
			</tr>
		</tbody>
	</table>
</m:panel>
