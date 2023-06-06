<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<b:error />
<form id="reloadForm" name="reloadForm" target="_top" method="post" action="../cunembo126l/lista.do"
	style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)">
</form>
<form action="../cunembo126i/inserisci.do" method="post" class="form-horizontal" id="insertForm" style="margin-top: 1em" target="hiddenIFrame"
	enctype="multipart/form-data">
	<b:select label="Tipo allegato *" name="idTipoDocumento" id="idTipoDocumento" selectedValue="${idTipoDocumento}" valueProperty="extIdTipoDocumento" textProperty="descTipoDocumento" list="${tipologie}" />
	<b:file label="File da allegare *" name="fileDaAllegare" id="fileDaAllegare" />
	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
		</div>
	</div>
	<iframe id="iframeUpload" style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)" name="hiddenIFrame"></iframe>
	<script type="text/javascript">
function updateModalWindow() {
		debugger;
		var html = document.getElementById('iframeUpload').contentWindow.document.body.innerHTML;
		if (html != null && html.indexOf('<refresh>') == 0) {
	          window.location.reload();
		} else {
			if(html != "")
				setDialogHtml(html);
				}
			}
		document.getElementById('iframeUpload').onload = updateModalWindow;
	</script>
</form>