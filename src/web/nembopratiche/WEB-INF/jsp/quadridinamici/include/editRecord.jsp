<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${e.tipoTXT}">
		<table class="table table-hover table-bordered tableBlueTh" id="gruppo_${status.index}">
			<tr>
				<th colspan="2" class="qd_txt">${e.nomeLabel}</th>
			</tr>
		</table>
	</c:when>
	<c:when test="${e.tipoHTM}">
		<table class="table table-hover table-bordered tableBlueTh" id="gruppo_${status.index}">
			<tr>
				<td colspan="2">${e.note}</td>
			</tr>
		</table>
	</c:when>
	<c:when test="${e.tipoSTR || e.tipoNumerico}">
		<m:textfield preferRequestValues="${preferRequest}" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" value="${e.valorePerModifica}" label="${e.nomeLabel}" maxlength="${e.maxLength}"
		disabled="${e.protetto}">
		<c:if test="${e.tipoPCT}">
		  <m:input-addon left="false">&#37;</m:input-addon>
		</c:if>
		<c:if test="${e.tipoEUR}">
		  <m:input-addon left="false">&euro;</m:input-addon>
		</c:if>
		</m:textfield>
	</c:when>
	<c:when test="${e.tipoSTM}">
		<m:textfield preferRequestValues="${preferRequest}" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" value="${e.valorePerModifica}" label="${e.nomeLabel}" cssClass="uppercase" maxlength="${e.maxLength}" disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoCMB}">
		<m:select preferRequestValues="${preferRequest}" list="${e.vociElemento}" textProperty="valore" valueProperty="codice" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" selectedValue="${e.valoreGrezzo}" label="${e.nomeLabel}" disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoCBT}">
    <m:checkbox-list preferRequestValues="${preferRequest}" name="elemento_${e.idElementoQuadro}" id="id_${e.idElementoQuadro}" list="${e.valoriCheckbox}" inline="false" label="${e.nomeLabel}" disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoRBT}">
    <m:radio-list preferRequestValues="${preferRequest}" name="elemento_${e.idElementoQuadro}" id="id_${e.idElementoQuadro}" list="${e.valoriCheckbox}" inline="false" label="${e.nomeLabel}" selectedValue="${e.valoreGrezzo}" disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoLST}">
		<m:select preferRequestValues="${preferRequest}" multiple="true" header="false" list="${e.vociElemento}" textProperty="valore" valueProperty="codice" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" selectedValue="${e.valori}" label="${e.nomeLabel}" disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoDTA || e.tipoDTQ || e.tipoDTF}">
		<m:textfield preferRequestValues="${preferRequest}" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" value="${e.valorePerModifica}" label="${e.nomeLabel}" type="DATE"
		 labelSize="3" controlSize="2" maxlength="10" style="min-width:104px !important"  disabled="${e.protetto}"/>
	</c:when>
	<c:when test="${e.tipoMST}">
		<m:textarea preferRequestValues="${preferRequest}" id="id_${e.idElementoQuadro}" name="elemento_${e.idElementoQuadro}" label="${e.nomeLabel}" rows="${e.precisione}" escapeHtml="${preferRequest}" disabled="${e.protetto}">${e.valorePerModifica}</m:textarea>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
