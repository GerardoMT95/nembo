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

<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-266-I" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelAttivitaPartecipante">


			<div class="col-sm-12">

				<div class="col-sm-10">

					<div class="stepwizard col-md-offset-1">
						<div class="stepwizard-row setup-panel">
							<div class="stepwizard-step">
								<a href="#step-1" type="button" class="btn btn-primary btn-circle">1</a>
								<p>Attività/Partecipante</p>
							</div>
							<c:choose>
								<c:when test="${empty isModifica}">
									<style>
.stepwizard-step {
	width: 33%;
}
</style>
									<div class="stepwizard-step">
										<a href="#step-2" type="button" class="btn btn-default btn-circle">2</a>
										<p>Interventi</p>
									</div>
									<div class="stepwizard-step">
										<a href="#step-3" type="button" class="btn btn-default btn-circle">3</a>
										<p>Importi</p>
									</div>
								</c:when>
								<c:otherwise>
									<style>
										.stepwizard-step {
											width: 50%;
										}
									</style>
									<div class="stepwizard-step">
										<a href="#step-2" type="button" class="btn btn-default btn-circle">2</a>
										<p>Importi</p>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>

				<form name="inserisciForm" id="elencoForm" method="post" action="" class="form-horizontal" style="margin-top: 2em" enctype="multipart/form-data">
					<br> <br>
					<m:select id="idAttivita" valueProperty="idAttivita" textProperty="descrizione" selectedValue="${idAttivita}" onchange="loadPartecipanti()"
						list="${elencoAttivita}" name="idAttivita" label="Attivita *" preferRequestValues="${prfReqValues}"></m:select>
					<m:select id="idPartecipante" valueProperty="idPartecipante" textProperty="denominazione" selectedValue="${idPartecipante}"
						list="${elencoPartecipanti}" name="idPartecipante" label="Partecipante*" preferRequestValues="${prfReqValues}"></m:select>


					<div class="col-sm-12 ">
						<div class="puls-group" style="margin-top: 2em">
							<div class="pull-left">
								<button type="button" onclick="forwardToPage('../cunembo266l/index.do');" class="btn btn-default">indietro</button>
							</div>
							<div class="pull-right">
								<button type="button" name="conferma" id="conferma" onclick="confermaDatiAttivitaPartecipante()" class="btn btn-primary">avanti</button>
							</div>
						</div>
					</div>
					<br> <br> <br> <br> <input type="hidden" name="idIntervento" value="${idIntervento}">
				</form>
			</div>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
		loadPartecipanti();
		function loadPartecipanti() {
			var idAttivita = $("#idAttivita").val();
			if (idAttivita !== undefined && idAttivita != null && idAttivita != "")
				$.ajax({
					url : "../cunembo266i/loadPartecipanti_" + idAttivita + ".do",
					async : false,
					success : function(data) {
						$("#idPartecipante").html(data);
					}
				});
		}
		function confermaDatiAttivitaPartecipante() {
			$('#elencoForm').attr('action', 'confermaDatiAttivitaPartecipante.do').submit();
		}
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />