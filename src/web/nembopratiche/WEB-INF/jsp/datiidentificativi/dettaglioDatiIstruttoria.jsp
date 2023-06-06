<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />


	<p:breadcrumbs cdu="CU-NEMBO-172" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-172" />

	<div class="container-fluid" id="content">
		<b:panel>
			<p:abilitazione-azione codiceQuadro="DIDIS" codiceAzione="MODIFICA">
				<div class="puls-group" style="margin-top: 1em; margin-bottom: 2em">
					<div class="pull-left">
						<button type="button" onclick="forwardToPage('../cunembo173/index.do');" class="btn  btn-primary">modifica</button>
					</div>
					<br class="clear" />
				</div>
			</p:abilitazione-azione>

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
							<th>Note</th>
							<td><c:out value="${datiProcedimento.note}"></c:out></td>
						</tr>
						<c:if test="${descrizioneSettore!=null}">
						<tr class="toggle_target_altri">
							<th>Settore di produzione</th>
							<td>${descrizioneSettore}</td>
						</tr>
						</c:if>
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
								<td>${datiProcedimento.dataVisuraCameraleStr}</td>
							</tr>
						</c:if>
						<tr class="toggle_target_altri">
							<th>Ultimo aggiornamento</th>
							<td>${ultimaModifica}</td>
						</tr>
					</tbody>
				</table>
			</b:panel>
			<b:panel title="Dati Anagrafici" id="datiGenerali">
				<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
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
				<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
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
			
				<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
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
			<c:if test="${soggFirmatario.codiceFiscale!=rappLegale.codiceFiscale}">
				<b:panel title="Richiedente" id="firmatario">
					<table summary="firmatario" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
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
			</c:if>

			
		</b:panel>

	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />