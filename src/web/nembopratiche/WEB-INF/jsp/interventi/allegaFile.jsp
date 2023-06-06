<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%><p:set-cu-info/>
<m:error />
<form id="reloadForm" name="reloadForm" target="_top" method="post" action="../cunembo108/index.do"
	style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)">
	<input type="hidden" name="anchor" value="allegato_${idDettaglioInfo}" />
</form>
<form action="../cunembo${cuNumber}z/allega_file_${idIntervento}.do" method="post" class="form-horizontal" id="insertForm" style="margin-top: 1em" target="hiddenIFrame"
	enctype="multipart/form-data">
	<m:textfield id="nomeAllegato" label="Nome allegato *" name="nomeAllegato" value="${nomeAllegato}"/>
	<b:file label="File da allegare *" name="fileDaAllegare" />
	<div class="form-group puls-group" style="margin-top: 1.5em">
		<div class="col-sm-12">
			<button type="button" data-dismiss="modal" class="btn btn-default">annulla</button>
			<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
		</div>
	</div>
	<iframe id="iframeUpload" style="position: absolute; left: -1000px; clip: rect(0px, 0px, 0px, 0px)" name="hiddenIFrame"></iframe>
	<script type="text/javascript">
		var first = true;
		function updateModalWindow() {
			if (first) 
			{
				first = false;
				return;
			}
			var html = document.getElementById('iframeUpload').contentWindow.document.body.innerHTML;
			if (html != null && html.indexOf('<refresh>') == 0) 
		  {
			  if ($('#tblMappeFile').length>0)
				{
			    closeModal();
			    $('#tblMappeFile').bootstrapTable('refresh',{});
				}
			} 
			else 
		  {
				setDialogHtml(html);
			}
		}
		document.getElementById('iframeUpload').onload = updateModalWindow;
	</script>
</form>
