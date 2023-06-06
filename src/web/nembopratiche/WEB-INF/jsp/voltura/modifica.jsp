<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<style type="text/css">
h4 {
	margin-left: 1.5em;
}
</style>
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-248-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-248-M" />

	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:panel id="panelVoltura">
			<div>
				<form:form method="post" class="form-horizontal" id="modifyForm" action="../cunembo248m/confermaModifica.do" style="margin-top: 1em">
					<div class="form-group puls-group" style="margin-top: 1.5em; margin-right: 0px">
						<b:error />
						<c:choose>
							<c:when test="${!empty voltura}">
								<h4>MODIFICA VOLTURA</h4>
							</c:when>
							<c:otherwise>
								<h4>INSERISCI VOLTURA</h4>
							</c:otherwise>
						</c:choose>
						<div>
							<div class="col-sm-12">
								<a type="button" class="btn btn-primary pull-right" onclick="cerca();">Cerca azienda</a>
							</div>
						</div>
						<br>
						<div class="col-sm-12" style="padding-top:1em;">
							<m:textarea disabled="true" preferRequestValues="${preferRequest}" id="azienda" name="azienda" label="Denominazione azienda: ">${voltura.denominazioneAzienda} </m:textarea>
						</div>
						<div class="col-sm-12" style="padding-top:1em;">
							<m:textarea disabled="true" preferRequestValues="${preferRequest}" id="cuaa" name="cuaa" label="CUAA: ">${voltura.cuaa} </m:textarea>
						</div>

						<div class="col-sm-12">
							<m:textarea disabled="true" preferRequestValues="${preferRequest}" id="sedeLegale" name="sedeLegale" label="Sede legale: ">${voltura.sedeLegale} </m:textarea>
						</div>

						<div class="col-sm-12">
							<m:textarea disabled="true" preferRequestValues="${preferRequest}" id="rappresentanteLegale" name="rappresentanteLegale"
								label="Legale rappresentante: ">${voltura.rappresentanteLegale} </m:textarea>
						</div>

						<div class="col-sm-12">
							<m:textarea preferRequestValues="${preferRequest}" id="note" name="note" label="Motivazione: " placeholder="Inserire motivazione (max 4000 caratteri)">${voltura.note}</m:textarea>
						</div>
						
						<input type="hidden" id="idAzienda" name="idAzienda" value="${voltura.idAzienda}" />
						<input type="hidden" id="cuaa" name="cuaa" value="${voltura.cuaa}" />
						<input type="hidden" id="denominazioneHidden" name="denominazioneHidden" value="${voltura.denominazioneAzienda}" />
						<input type="hidden" id="sedeLegaleHidden" name="sedeLegaleHidden" value="${voltura.sedeLegale}" />
						<input type="hidden" id="rappresentanteLegaleHidden" name="rappresentanteLegaleHidden" value="${voltura.rappresentanteLegale}" />
						
						<div class="col-sm-12">
							<div class="puls-group" style="margin-top: 2em">
								<div class="pull-left">
									<button type="button" onclick="forwardToPage('../cunembo248l/index.do');" class="btn btn-default">annulla</button>
								</div>
								<div class="pull-right">
									<button type="submit" name="conferma" id="conferma" class="btn btn-primary">conferma</button>
								</div>
							</div>
						</div>

					</div>
				</form:form>
			</div>

		</m:panel>
	</div>


	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">
	function radioFormatter(rows, field) {

		var html=[];
		html.push('<div class="radio"> <label><input type="radio" id="'+field['idAzienda']+'" value="'+field['idAzienda']+'" name="optradio"></label></div>');
		return html.join("");
	}


	function cerca() {
	      openPageInPopup('../cunembo248m/selezioneAziendePopup.do', 'dlgInserisci', 'Seleziona azienda', 'modal-lg',false);
	      return false;
			}
			
	</script>