<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<style>
.stepwizard-step {
	width: 33%;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-266-I" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelInterventi">
			<div class="col-sm-12">
				<div class="col-sm-10">
					<div class="stepwizard col-md-offset-1">
						<div class="stepwizard-row setup-panel">
							<div class="stepwizard-step">
								<a href="#step-1" type="button" class="btn btn-default btn-circle">1</a>
								<p>Attività/Partecipante</p>
							</div>
							<div class="stepwizard-step">
								<a href="#step-2" type="button" class="btn btn-primary btn-circle">2</a>
								<p>Interventi</p>
							</div>
							<div class="stepwizard-step">
								<a href="#step-3" type="button" class="btn btn-default btn-circle">3</a>
								<p>Importi</p>
							</div>
						</div>
					</div>
				</div>

				<form name="inserisciForm" id="elencoForm" method="post" action="" class="form-horizontal" style="margin-top: 2em" enctype="multipart/form-data">

					<m:select disabled="true" id="interventi" valueProperty="idAttivita" textProperty="descrizione" selectedValue="${attivita.idAttivita}"
						onchange="loadPartecipanti()" list="${elencoAttivita}" name="idAttivita" label="Attivita *" preferRequestValues="${prfReqValues}"></m:select>
					<m:select disabled="true" id="idPartecipante" valueProperty="idPartecipante" textProperty="denominazione"
						selectedValue="${partecipante.idPartecipante}" list="${elencoPartecipanti}" name="idPartecipante" label="Partecipante*"
						preferRequestValues="${prfReqValues}"></m:select>

					<div class="stdMessagePanel" id="divErrore" class="" style="display: none;">
						<div class="alert alert-danger">
							<strong>Attenzione!</strong><br /> Selezionare almeno un intervento.
						</div>
					</div>

					<%@include file="/WEB-INF/jsp/interventi/misura16/popupSelezionaInterventi.jsp"%>

					<br> <br> <br> <br>
				</form>
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="../js/dual-list-box.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script type="text/javascript">

		$('#interventiDualList').DualListBox();

		$(document).ready(function(){

		/*	$('body').on('DOMNodeInserted', '#unselectedComboBox', function () {
			      //$(this).combobox();
			    var idTipoIntervento = '${attivita.descrTipoIntervento}';
				$("#unselectedComboBox")[0].value=idTipoIntervento;
				$("#unselectedComboBox")[0].disabled= true
				$("#selectedComboBox")[0].value=idTipoIntervento;
				$("#selectedComboBox")[0].disabled= true	
			});*/

			$(document).bind("DOMSubtreeModified", function(evt) {
				if($("#unselectedComboBox")[0]!==undefined)
					{
					 var idTipoIntervento = '${attivita.descrTipoIntervento}';
						$("#unselectedComboBox")[0].value=idTipoIntervento;
						$("#unselectedComboBox")[0].disabled= true
						$("#selectedComboBox")[0].value=idTipoIntervento;
						$("#selectedComboBox")[0].disabled= true	

					}

				

				});
			/*$(document).on('change load charge init keyup', 'select', function(e) {
				

			});*/
			
		});

		



		
		
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />