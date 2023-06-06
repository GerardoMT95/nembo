<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table summary="Dati specifici" class="table table-hover table-bordered table-condensed tableBlueTh vcenter" data-show-columns="true">
	<tbody>
		<tr>
			<td colspan="6" style="background-color: #DDD"><strong>Estrazione</strong></td>
		</tr>
		<tr>
			<th>Domanda sottoposta a estrazione</th>
			<td class="center" style="cursor: not-allowed"><input type="checkbox" disabled="disabled" data-toggle="bs-toggle"
				${datiSpecifici.flagSottopostaEstrazione=='S'?'checked="checked"':''} /></td>
			<th>Data estrazione</th>
			<td><fmt:formatDate pattern="dd/MM/YYYY" value="${datiSpecifici.dataEstrazione}" /></td>
			<th>Riferimento estrazione</th>
			<td>${datiSpecifici.descTipoEstrazioneNumLotto}</td>
		</tr>
		<tr>
			<th>Domanda estratta a campione</th>
			<td class="center" style="cursor: not-allowed"><input type="checkbox" disabled="disabled" data-toggle="bs-toggle" ${datiSpecifici.estratta?'checked="checked"':''} /></td>
			<c:choose>
				<c:when test="${modalitaSelezione!=''}">
					<th>Modalit&agrave; selezione</th>
					<td colspan="3">${modalitaSelezione}</td>
				</c:when>
				<c:otherwise>
					<td colspan="4">&nbsp;</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</tbody>
</table>
