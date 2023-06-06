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

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-305-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-305-M" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		<br/>
		<m:error errorName="errorPercentualeMax"></m:error>
		<form name="formModificaColtureAziendali" id="formModificaColtureAziendali" method="POST" action="../cunembo${cuNumber}m/modifica.do">

				<table id="tableDettaglioColtureAziendali"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
						<tr>
							<th rowspan="2" class="col-md-3">Ubicazione<br/>terreno</th>
							<th rowspan="2" class="col-md-4">Utilizzo</th>
							<th rowspan="2" class="col-md-1">Sup.<br/>(ha)</th>
							<th colspan="2" class="col-md-2">P.L.V. ordinaria annuale<br/>(media del triennio <br/>precedente)</th>
							<th colspan="2" class="col-md-2">P.L.V. effettiva<br/>(conseguita nell'annata<br/>agraria dell'evento)</th>
						</tr>
						<tr>
							
							<th class="col-md-1">Q.li / ha</th>
							<th class="col-md-1">Prezzo<br/>al q.le</th>

							<th class="col-md-1">Tot. Q.li</th>
							<th class="col-md-1">Prezzo<br/>al q.le</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listColtureAziendali}" var="dettaglio">
							<tr>
								<td>
									<input type="hidden" name="idSuperficieColtura" value="${dettaglio.idSuperficieColtura}" />
									${dettaglio.ubicazioneTerreno}
								</td>
								<td>${dettaglio.tipoUtilizzoDescrizione}</td>
								<td>
									${dettaglio.superficieUtilizzata}
								</td>
								<td>
									<m:textfield id="txtProduzioneHaOrd_${dettaglio.idSuperficieColtura}" name="txtProduzioneHaOrd_${dettaglio.idSuperficieColtura}" value="${dettaglio.produzioneHa}" preferRequestValues="${preferRequest}"></m:textfield>
								</td>
								<td>
									<m:textfield id="txtPrezzoOrd_${dettaglio.idSuperficieColtura}" name="txtPrezzoOrd_${dettaglio.idSuperficieColtura}" value="${dettaglio.prezzo}" preferRequestValues="${preferRequest}"></m:textfield>
								</td>
								
								<td>
									<m:textfield id="txtProduzioneTotaleDannoEff_${dettaglio.idSuperficieColtura}" name="txtProduzioneTotaleDannoEff_${dettaglio.idSuperficieColtura}" value="${dettaglio.produzioneTotaleDanno}" preferRequestValues="${preferRequest}"></m:textfield>
								</td>
								<td>
									<m:textfield id="txtPrezzoDanneggiatoEff_${dettaglio.idSuperficieColtura}" name="txtPrezzoDanneggiatoEff_${dettaglio.idSuperficieColtura}" value="${dettaglio.prezzoDanneggiato}" preferRequestValues="${preferRequest}"></m:textfield>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
   		<a class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a>
   		<input type="submit" class="btn btn-primary pull-right" value="Conferma" />
   		</form>
			<br/>
   		<br/>
   		
		<br/>
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

<script type="text/javascript">
var mapIdSuperficieColturaAnomalia={};
</script>	
	
<c:forEach items="${mapIdSuperficieColturaAnomalia}" var="elem">
	<script type="text/javascript">
			mapIdSuperficieColturaAnomalia["${elem.key}"] = "${elem.value}";
	</script>
</c:forEach>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />