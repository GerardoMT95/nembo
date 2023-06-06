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
	<p:breadcrumbs cdu="CU-NEMBO-166-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-166-M" />
	<div class="container-fluid" id="content" style="margin-bottom: 3em">

		<m:panel id="esitoFinale">
			<m:error />
			<form:form id="mainFormEsito" action="" modelAttribute="" method="post" class="form-horizontal" style="margin-top:2em">
				<input type="hidden" name="idEsitoIstrut" value="${esito.idEsitoIstrut}" id="idEsitoIstrut" />
				
				<m:select label="Esito finale *" name="idEsito" list="${elencoEsiti}" id="idEsito" valueProperty="id" textProperty="descrizione"
					selectedValue="${esito.idEsito}" preferRequestValues="${preferRequestValueEsito}" />

				<c:forEach var="a" items="${elencoEsiti}">
					<input type="hidden" id="esito_${a.id}" value="${a.codice}" />
				</c:forEach>

				<m:textarea id="motivazione" placeholder="Inserire le motivazioni (al massimo 4000 caratteri)" label="Motivazioni" name="motivazione" rows="3"
					preferRequestValues="${preferRequestValueEsito}">${esito.motivazione}</m:textarea>

				<m:textarea id="prescrizioni" placeholder="Inserire le prescrizioni (al massimo 4000 caratteri)" rows="3" label="Prescrizioni" name="prescrizioni"
					preferRequestValues="${preferRequestValueEsito}">${esito.prescrizioni}</m:textarea>
				
				<m:selectchoice label="Funzionario istruttore *" name="idTecnico" list="${elencoTecnici}" id="idTecnico" valueProperty="id" textProperty="descrizione" listChoice="${ufficiZona}" selectedChoice="STESSO_UFFICIO"
					selectedValue="${esito.idTecnico}" preferRequestValues="${preferRequestValueEsito}" />

				<m:selectchoice label="Funzionario di grado superiore *" name="idGradoSup" list="${elencoTecniciSup}" id="idGradoSup" valueProperty="id" listChoice="${ufficiZona}" selectedChoice="STESSO_UFFICIO"
					textProperty="descrizione" selectedValue="${esito.idGradoSup}" preferRequestValues="${preferRequestValueEsito}" />
					
				
				<c:if test="${!empty elencoAtti && flagAmmissione =='S'}">
				<m:select  label="Tipo atto *" name="idTipoAtto" list="${elencoAtti}" id="idTipoAtto" onchange="checkIfFlagAltreInfo();" valueProperty="id"
					textProperty="descrizione" selectedValue="${esito.idTipoAtto}" preferRequestValues="${preferRequestValueEsito}" />
				<div id="hiddInput" >
				<c:choose>
				<c:when test="${esito.flagAltreInfoAtto=='S'}">
					<m:textfield  id="numeroAtto" placeholder="Inserire il numero dell'atto:" label="Numero: *" name="numeroAtto" preferRequestValues="${preferRequestValueEsito}" value="${esito.numeroAtto}"></m:textfield>
					<m:textfield  id="dataAtto" name="dataAtto" type="date" label="Data: *" preferRequestValues="${preferRequestValueEsito}" value="${esito.dataAtto}"></m:textfield>
				</c:when>
				<c:otherwise>
					<m:textfield disabled="true"  id="numeroAtto" placeholder="Inserire il numero dell'atto:" label="Numero: *" name="numeroAtto" preferRequestValues="${preferRequestValueEsito}" value="${esito.numeroAtto}"></m:textfield>
					<m:textfield disabled="true" id="dataAtto" name="dataAtto" type="date" label="Data: *" preferRequestValues="${preferRequestValueEsito}" value="${esito.dataAtto}"></m:textfield>
				</c:otherwise>
				</c:choose>
				</div>
				</c:if>
				
				<c:if test="${empty elencoAtti }">.
				<div id="hiddInput" style="display:none;">			
				<m:select disabled="true" style="display:none;"  label="Tipo atto *" name="idTipoAtto" list="${elencoAtti}" id="idTipoAtto" onchange="checkIfFlagAltreInfo();" valueProperty="id"
					textProperty="descrizione" selectedValue="${esito.idTipoAtto}" preferRequestValues="${preferRequestValueEsito}" />
					<m:textfield disabled="true"   id="numeroAtto" placeholder="Inserire il numero dell'atto:" label="Numero: *" name="numeroAtto" preferRequestValues="${preferRequestValueEsito}" value="${esito.numeroAtto}"></m:textfield>
					<m:textfield disabled="true"  id="dataAtto" name="dataAtto" type="date" label="Data: *" preferRequestValues="${preferRequestValueEsito}" value="${esito.dataAtto}"></m:textfield>
				</div>
				</c:if>
				
				
				
				
				
				<m:textarea id="note" placeholder="Inserire le note (al massimo 4000 caratteri)" label="Note" name="note" rows="3"
					preferRequestValues="${preferRequestValueEsito}">${esito.note}</m:textarea>

				
				<em>I campi contrassegnati con * sono obbligatori</em>
				<br>

				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button" onclick="forwardToPage('../cunembo166v/index.do');" class="btn btn-default">indietro</button>
						<button type="button" name="conferma" id="conferma" onclick="checkConfirm();" class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>
			</form:form>
		</m:panel>
	</div>

	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<script type="text/javascript">
	checkIfFlagAltreInfo();
	
		function checkConfirm() {
			var selIdEsito = $('#idEsito').val();
			var idEsito = $('#idEsito').val();
			var tipoEsito = $('#esito_' + selIdEsito).val();

			if (tipoEsito == 'N') {
				var idEsitoIstrut = $('#idEsitoIstrut').val();
				var idNumber = Number(idEsitoIstrut);

				var prescrizioni = $.trim($("#prescrizioni").val());

				if ((!isNaN(idNumber) && idNumber > 0) || prescrizioni.length > 0) {
					openPageInPopup('confermaelimina.do', 'dlgElimina', 'Elimina Dati', 'modal-lg', false);
					return false;
				}
			}

			var numeroAtto = $('#numeroAtto').val();
			var idTipoAtto = $('#idTipoAtto').val();
			var dataAtto   = $('#dataAtto').val();
			if(numeroAtto === undefined || numeroAtto==null)
				$('#numeroAtto').val("");
			if(dataAtto === undefined || dataAtto==null)
				$('#dataAtto').val("");
			if(idTipoAtto === undefined || idTipoAtto==null)
				$('#idTipoAtto').val("");
			if(tipoEsito === undefined || tipoEsito==null)
				$('#tipoEsito').val("");		
			if(idEsito === undefined || idEsito==null)
				$('#idEsito').val("");	

			$("#hiddInput :input").attr("disabled", false);	
			
			$('#mainFormEsito').submit();
		}

		function checkConfirmNeg() {
			var selIdEsito = $('#idEsito').val();
			var idEsito = $('#idEsito').val();
			var tipoEsito = $('#esito_' + selIdEsito).val();

			var numeroAtto = $('#numeroAtto').val();
			var idTipoAtto = $('#idTipoAtto').val();
			var dataAtto   = $('#dataAtto').val();
			if(numeroAtto === undefined || numeroAtto==null)
				$('#numeroAtto').val("");
			if(dataAtto === undefined || dataAtto==null)
				$('#dataAtto').val("");
			if(idTipoAtto === undefined || idTipoAtto==null)
				$('#idTipoAtto').val("");
			if(tipoEsito === undefined || tipoEsito==null)
				$('#tipoEsito').val("");		
			if(idEsito === undefined || idEsito==null)
				$('#idEsito').val("");	

			$("#hiddInput :input").attr("disabled", false);	
			
			$('#mainFormEsito').submit();
		}
		

		function checkIfFlagAltreInfo() {
			//se si seleziona il tipo esito "Altro", mostra la textfield nascosta
			var idTipoAtto = $("#idTipoAtto").val();

			var flagAltro = false;

			if(idTipoAtto!==undefined && idTipoAtto!=null && idTipoAtto!="")
			$.ajax({
				type : "GET",
				url : "checkIfFlagAltreInfo_" + idTipoAtto + ".json",
				dataType : "html",
				async : false,
				success : function(data) {
					if (data.indexOf("S") == 0)
						flagAltro = true;
				}
			});

			if (idTipoAtto != null && idTipoAtto !== undefined) {
				if (flagAltro) {
					$("#hiddInput :input").attr("disabled", false);	

				} else {
					$("#hiddInput :input").attr("disabled", true);
					$("#numeroAtto").val("");
					$("#dataAtto").val("");
				}
			}
		}
	</script>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />