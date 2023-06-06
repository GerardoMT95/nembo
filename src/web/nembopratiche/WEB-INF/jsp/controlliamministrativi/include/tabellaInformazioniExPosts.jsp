<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<m:panel title="Informazioni Ex-Post" id="infoexposts">
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<m:icon icon="modify" href="" onclick="return modificaInfoExPosts()" />
	</p:abilitazione-azione>
	<table summary="Esito Tecnico" class="table table-hover table-bordered">
		<tbody>
			<tr>
			  <th width="350">Rischio elevato</th>
				<td>${infoexposts.descrRischioElevato}</td>
			</tr>
			<tr>
			  <th  width="350">Anni successivi alla liquidazione in cui aumentare il criterio di rischio</th>
				<td>${infoexposts.anniHtml}</td>
			</tr>
			<tr>
			  <th  width="350">Note</th>
				<td>${infoexposts.note}</td>
			</tr>
		</tbody>
	</table>
</m:panel>
