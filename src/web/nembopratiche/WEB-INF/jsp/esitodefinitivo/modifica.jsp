<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-264-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-264-M" />
	<div class="container-fluid" id="content" style="margin-bottom: 3em">

		<m:panel id="esitoFinale">
			<m:error />
			<form:form id="mainFormEsito" action="" modelAttribute="" method="post" class="form-horizontal" style="margin-top:2em">

				<m:select disabled="true" label="Esito istruttoria confermato " name="idEsito" list="${elencoEsiti}" id="idEsito" valueProperty="id"
					textProperty="descrizione" selectedValue="${esito.idEsito}" />

				<m:select label="Esito provvedimento finale *" onchange="checkIfMotivazioniObbligatorie();" name="idEsitoDefinitivo"
					list="${elencoEsitiDefinitivi}" id="idEsitoDefinitivo" valueProperty="id" textProperty="descrizione" selectedValue="${esito.idEsitoDefinitivo}"
					preferRequestValues="${preferRequestValueEsito}" />

				<m:textarea id="motivazione" placeholder="Inserire le motivazioni" label="Motivazioni" name="motivazione" rows="3"
					preferRequestValues="${preferRequestValueEsito}">${esito.motivazione}</m:textarea>

				<m:selectchoice label="Funzionario istruttore *" name="idTecnico" list="${elencoTecnici}" id="idTecnico" valueProperty="id" textProperty="descrizione"
					selectedValue="${esito.extIdFunzionarioIstruttore}" preferRequestValues="${preferRequestValueEsito}" listChoice="${ufficiZona}" selectedChoice="STESSO_UFFICIO"/>

				<m:selectchoice label="Funzionario di grado superiore *" name="idGradoSup" list="${elencoTecniciSup}" id="idGradoSup" valueProperty="id"
					textProperty="descrizione" selectedValue="${esito.extIdFunzionarioGradoSup}" preferRequestValues="${preferRequestValueEsito}" listChoice="${ufficiZona}" selectedChoice="STESSO_UFFICIO"/>

				<em>I campi contrassegnati con * sono obbligatori</em>
				<br>

				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button" onclick="forwardToPage('../cunembo264l/index.do');" class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>
			</form:form>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<script type="text/javascript">
		checkIfMotivazioniObbligatorie();
		
		function checkIfMotivazioniObbligatorie() {

			var idEsitoDefinitivo = $("#idEsitoDefinitivo").val();
			var motivazioniNeeded = false;

			if (idEsitoDefinitivo !== undefined && idEsitoDefinitivo != null && idEsitoDefinitivo != "")
				$.ajax({
					type : "GET",
					url : "checkIfMotivazioniObbligatorie_" + idEsitoDefinitivo + ".json",
					dataType : "html",
					async : false,
					success : function(data) {
						if (data.indexOf("true") == 0)
							motivazioniNeeded = true;
					}
				});

			if (idEsitoDefinitivo != null && idEsitoDefinitivo !== undefined && idEsitoDefinitivo != "") {
				var addExclamationMark = false;
				if($("label:contains('Motivazioni') .icon-exclamation-sign").length==1)
					addExclamationMark= true;
				
				if (motivazioniNeeded) {
					if($("label:contains('Motivazioni *')").length!=1)
						{
							if(!addExclamationMark)
								$("label:contains('Motivazioni')").html("Motivazioni * ");
							else
								$("label:contains('Motivazioni')").html("Motivazioni * <span class=\"icon icon-exclamation-sign\" aria-hidden=\"true\"></span>");							
						}
				} else {

					if(!addExclamationMark)
						$("label:contains('Motivazioni')").html("Motivazioni ");
					else
						$("label:contains('Motivazioni')").html("Motivazioni <span class=\"icon icon-exclamation-sign\" aria-hidden=\"true\"></span>");

					//rimuovo errori
					$("label:contains('Motivazioni')").removeClass("alert");
					$("label:contains('Motivazioni')").removeClass("alert-danger");
					$("label:contains('Motivazioni')").removeClass("error-label");
					$("label:contains('Motivazioni')").parent().parent().removeClass("has-error");
					$("label:contains('Motivazioni')").css("color", "black");
					
					$("label:contains('Motivazioni')").addClass("control-label");
					$("label:contains('Motivazioni')").removeAttr("data-toggle");
					$("label:contains('Motivazioni')").removeAttr("data-original-title");
					
					$("label:contains('Motivazioni') .icon-exclamation-sign").remove();
					$("#motivazione").parent().removeClass("error-tooltip");
					$("#motivazione").parent().removeClass("has-error");
					$("#motivazione").parent().removeAttr("data-toggle");
					$("#motivazione").parent().removeAttr("data-original-title");
				}
				if(addExclamationMark)
					$("label:contains('Motivazioni')").add
			}
		}
	</script>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />