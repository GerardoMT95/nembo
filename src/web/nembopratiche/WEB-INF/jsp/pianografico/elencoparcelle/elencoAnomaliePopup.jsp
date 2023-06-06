<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>

<m:error />

<form method="post" class="form-horizontal" id="formAziende" action="">
	<div class="container-fluid" id="popupContent">
		<div id="tableBox" style=" margin-bottom:2em;min-height: 5em">
			<table id='tblAziende' summary="Elenco Anomalie" class="table table-hover table-bordered tableBlueTh" data-toggle="table"
				data-undefined-text='' >
				<thead>
					<tr>
						<th data-field="codice">Controllo</th>
						<th data-field="descrizione">Descrizione controllo</th>
						<th data-field="descrizioneAnomalia">Descrizione anomalia</th>
						<th data-field="ulterioriInformazioni">Ulteriori informazioni</th>
						<th data-field="gravita">Esito</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${elenco!=null}">
						<c:forEach items="${elenco}" var="a">
						<tr>
							<td>${a.codice}</td>
							<td>${a.descrizione}</td>
							<td>${a.descrizioneAnomalia}</td>
							<td>${a.ulterioriInformazioni}</td>
							<td><c:choose>
									<c:when test="${a.gravita == 'B' || a.gravita == 'G'}">
										<img src="../img/ico_cancel.gif">
									</c:when>
									<c:when test="${a.gravita == 'W'}">
										<img src="../img/ico_alert.gif">
									</c:when>
								</c:choose></td>
						</tr>
						</c:forEach>					
					</c:if>
				</tbody>
			</table>
		</div>
	</div>

	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
		<button type="button" data-dismiss="modal" id="annulla" class="btn btn-default">chiudi</button>
	</div>
</form>

<script type="text/javascript">
  $("#tblAziende").bootstrapTable();
</script>
