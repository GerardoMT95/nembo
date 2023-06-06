<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-108" />
	<p:messaggistica/><p:testata cu="CU-NEMBO-108" />

	<div class="container-fluid" id="content">
	<m:panel id="panelAllegati">
		<p:abilitazione-azione codiceQuadro="ALLEG" codiceAzione="MODIFICA">
			<div class="puls-group" style="margin-bottom: 2em">
				<div class="pull-left">
					<button type="button" onclick="forwardToPage('../cunembo109/index.do');" class="btn  btn-primary">modifica</button>
				</div>
				<br class="clear" />
			</div>
		</p:abilitazione-azione>
		
		<p:allegati isModifica="false" allegati="${allegati}" fileMap="${fileMap}" canUpdate="${canUpdate}" />
		
		
		</m:panel>
		
	</div>
	<!-- Modal -->
	<div class="modal fade" id="dlgInserisci" tabindex="-1" role="dialog" aria-labelledby="dlgTitle" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="dlgTitle">Inserimento nuovo allegato</h4>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="dlgElimina" tabindex="-1" role="dialog" aria-labelledby="dlgTitle" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="dlgTitle">Elimina allegato</h4>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
		function allegaFile(id) {
			$.ajax({
				type : "GET",
				url : '../cunembo121/inserisci_' + id + '.do',
				dataType : "html",
				async : false,
				success : function(data, textStatus) {
					setDialogHtml(data);
				}
			});
		}
		function eliminaAllegato(id) {
			$.ajax({
				type : "GET",
				url : '../cunembo121/conferma_elimina_' + id + '.do',
				dataType : "html",
				async : false,
				success : function(data) {
					$('#dlgElimina .modal-body').html(data);
					doErrorTooltip();
				}
			});
			return false;
		}
		function setDialogHtml(data) 
		{
			$('#dlgInserisci .modal-body').html(data);
			doErrorTooltip();
		}

		if ('${param["anchor"]}' != '') {
			window.location.hash = '${param["anchor"]}';
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />