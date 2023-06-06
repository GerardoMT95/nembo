<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<m:panel title="Visita per controllo in loco" id="visiteLuogo">
	<form id="formVisiteLuogo" name="formVisiteLuogo" method="post">
		<table summary="Elenco Visite" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
			<thead>
				<tr>
					<c:if test="${!disabilitaControlloLoco}">
						<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
							<th class="center" style="width: 110px"><m:icon icon="add_white" href="#" onclick="return nuovaVisita()" /> <m:icon icon="modify" href="#"
									onclick="return modificaMultiplaVisiteLuogo()" /> <m:icon icon="trash" href="#" onclick="return eliminaMultiplaVisiteLuogo()" /></th>
							<th class="center" style="width: 40px"><input name="chkSelectAllVisite" id="chkSelectAllVisite" onclick="selectAll(this,'idVisitaLuogo')"
								type="checkbox"></th>
						</p:abilitazione-azione>
					</c:if>
					<th class="center">Data visita</th>
					<th class="center">Funzionario controllore</th>
					<th class="center">Esito</th>
					<th class="center">Note</th>
					<th class="center">Data verbale</th>
					<th class="center">Numero verbale</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${visite}" var="v">
					<tr>
						<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
							<td class="center"><m:icon icon="modify" href="../cunembo${cuNumber}m/modifica_visita_luogo_${v.idVisitaLuogo}.do" /> <m:icon icon="trash" href="#"
									onclick="return eliminaVisitaLuogo(${v.idVisitaLuogo})" /></td>
							<td class="center"><input type="checkbox" id="idVisitaLuogo_${v.idVisitaLuogo}" name="idVisitaLuogo" value="${v.idVisitaLuogo}" /></td>
						</p:abilitazione-azione>
						<td><fmt:formatDate value="${v.dataVisita}" pattern="dd/MM/yyyy" /></td>
						<td><c:out value="${v.descTecnico}"/></td>
						<td><c:out value="${v.descEsito}"/></td>
						<td><c:out value="${v.note}"/></td>
						<td><fmt:formatDate value="${v.dataVerbale}" pattern="dd/MM/yyyy"/></td>
						<td><c:out value="${v.numeroVerbale}" /></td>
					</tr>
				</c:forEach>
				<c:if test="${visite==null || visite.size()==0}">
					<tr>
						<th colspan="8">Non sono state eseguite visite sul luogo</th>
					</tr>
				</c:if>
			</tbody>
		</table>
	</form>
</m:panel>
