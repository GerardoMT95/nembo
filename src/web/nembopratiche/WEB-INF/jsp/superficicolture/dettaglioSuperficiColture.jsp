<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-299-D" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-299-D" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDettaglioSuperficiColture">
		<br/>
		<div style="overflow-x:scroll;">
		
		<form name="formVisualizzaSuperficiColture" id="formVisualizzaSuperficiColture" action="" method="POST">
				
				<h4>Dettaglio Superfici e Colture</h4>
				<table id="tableSuperificiColtureDettaglio"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th>Ubicazione terreno</th>
							<td>${superficiColturaDettaglio.ubicazioneTerreno}</td>
						</tr>
						<tr>
							<th>Utilizzo</th>
							<td>${superficiColturaDettaglio.tipoUtilizzoDescrizione}</td>
						</tr>
						<tr>
							<th>Superficie</th>
							<td>${superficiColturaDettaglio.superficieUtilizzataFormatted}</td>
						</tr>
						<tr>
							<th>Note</th>
							<td>${superficiColturaDettaglio.note}</td>
						</tr>
						<tr>
							<th>Ultima Modifica</th>
							<td>${ultimaModifica}</td>
						</tr>
					</tbody>
				</table>
				
				<br/>
				<h4>Dettaglio PSR</h4>
				<table id="tableSuperificiColtureDettaglioPSR"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
						<tr>
							<th rowspan="2"></th>
							<th colspan="2">Produzione</th>
							<th colspan="2">Giornate<br/>lavorative</th>
							<th rowspan="2">U.F.<br/>Tot.</th>
							<th colspan="2">Reimpieghi</th>
							<th colspan="3">P.L.V.</th>
						</tr>
						<tr>
							<th>q/ha</th>
							<th>Tot.</th>
							<th>Ad ha</th>
							<th>Totale</th>
							
							<th>q</th>
							<th>UF</th>
							<th>Tot. q</th>
							<th>Prezzo/q</th>
							<th>Tot. dichiarato</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th style="background-color:#428BCA; color:#FFF;">Dichiarato</th>
							<td>${superficiColtureDettaglioPsrDTO.produzioneHaFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.prodTotaleFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.giornateLavorateFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.giornateLavorativeTotFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.ufTotFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.qliReimpiegatiFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.ufReimpiegateFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.plvTotQFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.prezzoFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.plvTotDichFormatted}</td>
						</tr>
						
						<tr>
							<th style="background-color:#428BCA; color:#FFF;">Calcolato</th>
							<td>${superficiColtureDettaglioPsrDTO.produzioneHaMediaFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.prodTotaleCalcFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.giornateLavorateMedieFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.giornateLavorativeTotCalcFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.ufTotCalcFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.qliReimpiegatiFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.ufReimpiegateFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.plvTotQCalcFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.prezzoMedioFormatted}</td>
							<td>${superficiColtureDettaglioPsrDTO.plvTotDichCalcFormatted}</td>
						</tr>
						
					</tbody>
				</table>
				
				<br/>
			</form>
		</div>
   		<br/>
   		<a class="btn btn-default" href="../cunembo299l/index.do">Indietro</a>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />