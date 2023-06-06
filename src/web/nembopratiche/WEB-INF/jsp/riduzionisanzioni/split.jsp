<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>

<m:error></m:error>
<form id="reloadForm" name="reloadForm" target="_top" method="post" action="../cunembo214l/index_${riduzione.idProcOggSanzione}.do"
	style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)"></form>

<div class="form-group puls-group"
		style="margin-top: 1.5em; margin-right: 0px">
<form id="formRiduzioni" method="post" action="../cunembo214m/confermaSplit_${riduzione.idProcOggSanzione}.do" enctype="multipart/form-data"
	target="hiddenIFrame">
	<table summary="Elenco riduzioni e sanzioni" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
		<thead>
			<tr>
				<th class="center">Tipologia - Descrizione</th>
				<th class="center">Operazione</th>
				<th class="center">Note</th>
				<th class="center">Importo</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="hidden" name="idProcOggSanzione" value="${riduzione.idProcOggSanzione}" /> <input type="hidden" name="importoTot"
					value="${riduzione.importo}" /> <c:out value="${riduzione.tipologia} - ${riduzione.descrizione}"></c:out></td>
				<td rowspan="2" style="vertical-align: center; text-align: center; padding-top: 3em;"><c:out value="${riduzione.operazione}"></c:out></td>
				<td><m:textarea id="noteA" name="noteA" preferRequestValues="${preferRequest}">${riduzione.note}</m:textarea></td>
				<td><m:textfield type="EURO" id="importoA" name="importoA" preferRequestValues="${preferRequest}" value="${riduzione.importoFirstRecord}" /></td>
			</tr>
			<tr>
				<td><input type="hidden" name="idProcOggSanzione" value="${riduzione.idProcOggSanzione}" /> <c:out value="${descrizione2}"></c:out></td>
				<td><m:textarea id="noteB" name="noteB" preferRequestValues="${preferRequest}">${riduzione.noteB}</m:textarea></td>

				<td><m:textfield type="EURO" id="importoB" name="importoB" preferRequestValues="${preferRequest}"
						value="${riduzione.importoSecondRecordAfterSplit}" /></td>

			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td style="text-align: right"><strong><i>${riduzione.importo}</i></strong></td>
			</tr>
		</tbody>
	</table>
	<p:abilitazione-azione codiceQuadro="RISAN" codiceAzione="SPLIT">
		<div class="form-group puls-group" style="margin-top: 2em">

			<button class="pull-right btn-primary btn" name="conferma" id="conferma" type="submit">conferma</button>
			<button type="button" data-dismiss="modal" class="btn btn-default" id="annulla">annulla</button>
			<iframe id="iframeUpload" style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)" name="hiddenIFrame"></iframe>
		</div>
	</p:abilitazione-azione>
</form>
</div>

<script type="text/javascript">
	var first = true;


	function updateModalWindow() {
		if (navigator.vendor.search("Google") < 0 && navigator.vendor.search("Apple") < 0) //controllo da fare perché altrimenti non funziona su chrome, opera e safari al primo click, ma solo al secondo e carica due volte il file
		{
			if (first) {
				first = false;
				return;
			}
		}
		var html = document.getElementById('iframeUpload').contentWindow.document.body.innerHTML;
		if (html != null && html.indexOf('<success') == 0) {
			$("#annulla").click();
			location.reload();
			
		} else { //error
			setDialogHtml(html);
		}
	}
	document.getElementById('iframeUpload').onload = updateModalWindow;

	function setDialogHtml(data) {
		$('#dlgSplitVisita .modal-body').html(data);
		doErrorTooltip();
	}

	
</script>