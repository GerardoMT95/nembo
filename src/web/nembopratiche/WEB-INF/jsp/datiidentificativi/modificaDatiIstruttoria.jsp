<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />

<style>

.chkAvvioIstruttoria{
	width: 1.3em;
    height: 1.3em;
   }
</style>

<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-173" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-173" />
	<div class="container-fluid" id="content" style="margin-bottom: 3em">
		<m:error />
		<m:panel id="datiIdentificativi">
			<form:form action="" modelAttribute="" method="post"
				class="form-horizontal" style="margin-top:2em">
	

				<m:textarea id="note"
					placeholder="Inserire le note (al massimo 4000 caratteri)"
					label="Note" name="note" preferRequestValues="${prfvalues}">${note}</m:textarea>


				<c:if test="${visualizzaAvvioIstruttoria}">
					<m:checkBox cssClass="chkAvvioIstruttoria" label="Avvio dell'istruttoria" name="chkAvvioIstruttoria" labelSize="3" controlSize="1" value="S" preferRequestValues="${prfvalues}"></m:checkBox>
				</c:if>
							
				<c:if test="${not empty settoriDiProduzioneInLivelliBandi}">
					<m:select label="Settore di produzione *" name="idSettore"
					list="${settoriDiProduzione}" id="idSettore" 
					valueProperty="idSettore" 
					selectedValue="${idSettoreSelezionato}" preferRequestValues="${prfvalues}" />
					</c:if>		

				<m:textarea id="cup"
					placeholder="Inserire codice CUP"
					label="Codice CUP" name="cup" preferRequestValues="${prfvalues}">${datiProcedimento.codiceCup}</m:textarea>


				<c:if test="${showVercod}">
					<m:textfield id="vercod" placeholder="Inserire codice VERCOD" name="vercod" maxlength="10" label="VERCOD " preferRequestValues="${prfvalues}" value="${datiProcedimento.vercod}"></m:textfield>
					<m:textfield id="dataVisuraCamerale" placeholder="Inserire data" name="dataVisuraCamerale" type="date" label="Data visura camerale " preferRequestValues="${prfvalues}" value="${datiProcedimento.dataVisuraCamerale}"></m:textfield>
				</c:if>



				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button"
							onclick="forwardToPage('../cunembo172/index.do');"
							class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma"
							class="btn btn-primary pull-right">conferma</button>
					</div>
					</div>

			<b:panel title="Dati procedimento" id="datiprocedimento">
				<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="20%" />
						<col width="80%" />
					</colgroup>
					<tbody>
					<tr class="toggle_target_altri">
							<th>Ente competente</th>
							<td>${datiProcedimento.organismoDelegatoEsteso}</td>
						</tr>
						<tr class="toggle_target_altri">
							<th>Ufficio</th>
							<td>${datiProcedimento.ufficio}</td>
						</tr>
						<tr class="toggle_target_altri">
							<th>Responsabile del procedimento</th>
							<td>${datiProcedimento.responsabile}</td>
						</tr>
						<tr class="toggle_target_altri">
							<th>CUP</th>
							<td>${datiProcedimento.codiceCup}</td>
						</tr>
						
						<c:if test="${showVercod}">
							<tr class="toggle_target_altri">
								<th>VERCOD</th>
								<td>${datiProcedimento.vercod}</td>
							</tr>
							<tr class="toggle_target_altri">
								<th>Data visura camerale</th>
								<td>${datiProcedimento.dataVisuraCamerale}</td>
							</tr>
						</c:if>
						<tr class="toggle_target_altri">
							<th>Ultimo aggiornamento</th>
							<td>${ultimaModifica}</td>
						</tr>
					</tbody>
				</table>
			</b:panel>

							<b:panel title="Dati generali" id="datiGenerali">
								<table summary="Bando" style="margin-top: 4px"
									class="myovertable table table-hover table-condensed table-bordered">
									<colgroup>
										<col width="20%" />
										<col width="80%" />
									</colgroup>
									<tbody>
										<tr>
											<th>Cuaa</th>
											<td>${azienda.cuaa}</td>
										</tr>
										<tr>
											<th>Partita IVA</th>
											<td>${azienda.partitaIva}</td>
										</tr>
										<tr>
											<th>Denominazione</th>
											<td>${azienda.denominazione}</td>
										</tr>
										<tr>
											<th>Intestazione azienda</th>
											<td>${azienda.itestazionePartitaIva}</td>
										</tr>
										<tr>
											<th>Forma giuridica</th>
											<td>${azienda.formaGiuridica}</td>
										</tr>
										<tr>
											<th>Sede legale</th>
											<td>${azienda.indirizzoSedeLegale}</td>
										</tr>
										<tr>
											<th>Telefono</th>
											<td>${azienda.telefono}</td>
										</tr>
										<tr>
											<th>Email</th>
											<td>${azienda.email}</td>
										</tr>
										<tr>
											<th>PEC</th>
											<td>${azienda.pec}</td>
										</tr>
									</tbody>
								</table>
							</b:panel>
							<b:panel title="Indicatori" id="indicatori">
								<table summary="Bando" style="margin-top: 4px"
									class="myovertable table table-hover table-condensed table-bordered">
									<colgroup>
										<col width="20%" />
										<col width="80%" />
									</colgroup>
									<tbody>
										<tr>
											<th>Tipo attivit&agrave; ATECO</th>
											<td>${azienda.attivitaAteco}</td>
										</tr>
										<tr>
											<th><c:out value="${azienda.labelAttivitaOte}"></c:out></th>
											<td>${azienda.attivitaOte}</td>
										</tr>
									</tbody>
								</table>
							</b:panel>

							<b:panel title="Titolare Rapp. Legale" id="titolareRappLegale">
								<table summary="Bando" style="margin-top: 4px"
									class="myovertable table table-hover table-condensed table-bordered">
									<colgroup>
										<col width="20%" />
										<col width="80%" />
									</colgroup>
									<tbody>
										<tr class="toggle_target_rl">
											<th>Codice Fiscale</th>
											<td>${rappLegale.codiceFiscale}</td>
										</tr>
										<tr class="toggle_target_rl">
											<th>Cognome</th>
											<td>${rappLegale.cognome}</td>
										</tr>
										<tr class="toggle_target_rl">
											<th>Nome</th>
											<td>${rappLegale.nome}</td>
										</tr>
										<tr class="toggle_target_rl">
											<th>Telefono</th>
											<td>${rappLegale.telefono}</td>
										</tr>
										<tr class="toggle_target_rl">
											<th>Email</th>
											<td>${rappLegale.mail}</td>
										</tr>
									</tbody>
								</table>
							</b:panel>

							<c:if test="${soggFirmatario != null}">
								<b:panel title="Richiedente" id="firmatario">
									<table summary="firmatario" style="margin-top: 4px"
										class="myovertable table table-hover table-condensed table-bordered">
										<colgroup>
											<col width="20%" />
											<col width="80%" />
										</colgroup>
										<tbody>
											<tr class="toggle_target_rl">
												<th>Ruolo</th>
												<td>${soggFirmatario.descrizioneRuolo}</td>
											</tr>
											<tr class="toggle_target_rl">
												<th>Codice Fiscale</th>
												<td>${soggFirmatario.codiceFiscale}</td>
											</tr>
											<tr class="toggle_target_rl">
												<th>Cognome</th>
												<td>${soggFirmatario.cognome}</td>
											</tr>
											<tr class="toggle_target_rl">
												<th>Nome</th>
												<td>${soggFirmatario.nome}</td>
											</tr>
											<tr class="toggle_target_rl">
												<th>Telefono</th>
												<td>${soggFirmatario.telefono}</td>
											</tr>
											<tr class="toggle_target_rl">
												<th>Email</th>
												<td>${soggFirmatario.mail}</td>
											</tr>
										</tbody>
									</table>
								</b:panel>
							</c:if>
			</form:form>
		</m:panel>
	</div>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />