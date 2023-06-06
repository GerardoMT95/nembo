<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>

<form method="post" class="form-horizontal" id="modifyForm" style="margin-top: 1em">
	<h4></h4>
	<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">

		<m:textarea id="t" name="t" disabled="true" label="Codice controllo:">${controllo.codice}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" style="min-height:8em;" label="Descrizione controllo:">${controllo.descrizione}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" label="Descrizione anomalia:">${controllo.descrizioneAnomalia}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" label="Tipo risoluzione controllo:">${giustificazione.tipoRisoluzione}
				</m:textarea>

		<m:textarea id="t" name="t" disabled="true" label="Note:">${giustificazione.note}
				</m:textarea>

		<c:choose>
			<c:when test="${giustificazione.allegato.nomeLogico != null}">
				<div class="form-group">
					<label class="control-label col-sm-3">File allegato:</label>
					<div class="col-sm-9">
						<label title="${giustificazione.allegato.nomeFisico}" class="control-label col-sm-12" style="text-align: left;"><a
							style="text-align: left;" class="pull-left" href="../cunembo234/downloadAllegato_${giustificazione.idSoluzioneAnomalia}.do">${giustificazione.allegato.nomeLogico}</a>
						</label>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<m:textarea id="t" name="t" disabled="true" label="File allegato:"></m:textarea>
			</c:otherwise>
		</c:choose>

		<c:if test="${showparametriamf}">
			<fieldset>
				<legend>Dati Richiesta certificati</legend>
				<m:textfield id="prov" name="prov" value="${giustificazione.provPrefettura}" label="Provincia Prefettura di competenza" disabled="true"></m:textfield>
				<m:textfield id="protocollo" name="protocollo" value="${giustificazione.numProtocollo}" label="Numero protocollo" disabled="true"></m:textfield>
				<m:textfield cssClass="date-txt" type="DATE" id="data_protocollo" name="data_protocollo" value="${giustificazione.dataProtocollo}"
					label="Data protocollo" disabled="true"></m:textfield>
				<m:textfield cssClass="date-txt" type="DATE" id="data_documento" name="data_documento" value="${giustificazione.dataDocumento}"
					label="Data documento" disabled="true"></m:textfield>
			</fieldset>
		</c:if>

		<div style="padding-top: 1em;">
			<table class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>Ultima modifica</th>
						<td><c:out value="${ultimaModifica}"></c:out></td>
					</tr>
				</tbody>
			</table>
		</div>
		<button type="button" data-dismiss="modal" class="btn btn-default">chiudi</button>

	</div>
	<script type="text/javascript">
		
	</script>
</form>