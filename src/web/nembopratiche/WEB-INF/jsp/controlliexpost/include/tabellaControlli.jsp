<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%-- NON RIMUOVERE IL COMMENTO, SERVE AL JAVASCRIPT PER CAPIRE CHE PAGINA GLI E' STATA INVIATA --%>
<m:panel title="Controlli tecnico amministrativi (per controllo ex post)" id="controlliAmministrativi">
	<form id="elencoControlli" method="post" action="modifica.do">
		<!-- Domanda estratta per controllo a campione delle dichiarazioni sostitutive ex DPR 445/2000 &nbsp; &nbsp; <input type="checkbox" disabled="disabled"
			data-toggle="bs-toggle" ${datiSpecifici.flagEstratta!='N'?'checked="checked"':''} /> --><br />
		<br />

		<table summary="Controlli amministrativi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
			<thead>
				<tr>
					<c:if test="${!disabilitaControlloLoco}">
						<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
							<th class="center" style="width: 48px"><m:icon icon="modify" href="#" onclick="return modificaMultiplaControlli()" /></th>
						</p:abilitazione-azione>
					</c:if>
					<th class="center" style="width: 40px"><input type="checkbox" name="chkSelectAllControlli" id="chkSelectAllControlli"
						onclick="selectAll(this,'idQuadroOggControlloAmm')" /></th>
					<th class="center">Codice</th>
					<th class="center">Descrizione</th>
					<th class="center">Esito</th>
					<th class="center">Note</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${controlli}" var="c">
					<tr>
						<c:choose>
							<c:when test="${c.idControlloAmministratPadre!=null}">
								<c:if test="${!disabilitaControlloLoco}">
									<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
										<td class="center"><m:icon icon="modify" href="../cunembo${cuNumber}m/modifica_${c.idQuadroOggControlloAmm}.do" /></td>
									</p:abilitazione-azione>
								</c:if>
								<td class="center"><input type="checkbox" name="idQuadroOggControlloAmm" value="${c.idQuadroOggControlloAmm}" /></td>
								<td>${c.codice}</td>
								<td>${c.descrizione}</td>
								<td>${c.descEsito}</td>
								<td>${c.note}</td>
							</c:when>
							<c:otherwise>
								<td colspan="6" style="background-color: #DDD"><strong>${c.descrizione}</strong></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
				<tr>
				</tr>
			</tbody>
		</table>
	</form>
</m:panel>
