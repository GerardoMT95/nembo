<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<m:panel title="Dati specifici" id="panelDatiSpecifici">
	<p:abilitazione-azione codiceQuadro="${cuCodQuadro}" codiceAzione="MODIFICA">
		<m:icon icon="modify" href="#" onclick="return modificaDatiSpecifi()" />
	</p:abilitazione-azione>
	<form id="formDatiSpecifici" method="post" action="modifica.do">
	<%@include file="/WEB-INF/jsp/controlliinlocomisureinvestimento/include/tabellaEstrazione.jsp" %>
		<table summary="Dati specifici" class="table table-hover table-bordered table-condensed tableBlueTh vcenter" data-show-columns="true">
			<tbody>
				<tr>
					<td colspan="6" style="background-color: #DDD"><strong>Preavviso</strong></td>
				</tr>
				<tr>
					<th>Preavviso</th>
					<td><div style="cursor: not-allowed;float:left"><input type="checkbox" disabled="disabled" data-toggle="bs-toggle" ${datiSpecifici.flagPreavviso=='S'?'checked="checked"':''} /></div></td>
					<th>Data preavviso</th>
					<td><fmt:formatDate pattern="dd/MM/YYYY" value="${datiSpecifici.dataPreavviso}" /></td>
					<th>Descrizione preavviso</th>
					<td><c:out value="${datiSpecifici.descTipologiaPreavviso}" /></td>
				</tr>
			</tbody>
		</table>
		<table summary="Dati specifici" class="table table-hover table-bordered table-condensed tableBlueTh vcenter" data-show-columns="true">
			<tbody>
				<tr>
					<td colspan="2" style="background-color: #DDD"><strong>Controllo</strong></td>
				</tr>
				<tr>
					<th>Controllo ex post</th>
					<td><div style="cursor: not-allowed;float:left"><input type="checkbox" disabled="disabled" data-toggle="bs-toggle" ${datiSpecifici.flagControllo=='S'?'checked="checked"':''} /></div></td>
			</tbody>
		</table>
	</form>
</m:panel>
<script type="text/javascript">
  function modificaDatiSpecifi()
  {
    openPageInPopup("../cunembo213m/modifica_dati_specifici.do", "modificaDatiSpecifici", "Modifica dati specifici", "modal-lg");
  }
</script>