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
<style>
	checkbox-field {
		max-width: 37px !important;
		width:36px !important;
		min-width: 36px !important;
		text-align:center;
		padding: 8px 0;
	}
</style>	
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:set-cu-info />
	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-307-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-307-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		<br/>
		
		<c:choose>
		<c:when test="${nColtureDanneggiate == 0}">
			<div class="alert alert-danger">
				Non è stato indicato alcun danno sulle colture, operazione non effettuabile.
			</div>
		</c:when>
		<c:otherwise>
		<div>
		<div>
			<h4>Quadro Riepilogativo</h4>
			<table id="tableRiepilogoColtureAziendali"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th class="col-sm-6">Totale P.L.V. ordinaria<br/>(media del triennio precedente)</th>
							<td class="col-sm-6">${riepilogo.totalePlvOrdinariaFormatted}</td>
						</tr>
						<tr>
							<th>Totale P.L.V. effettiva<br/>(conseguita nell'annata agraria dell'evento)</th>
							<td>${riepilogo.totalePlvEffettivaIfNotEqualFormatted}</td>
						</tr>
						<tr>
							<th>Produzione perduta</th>
							<td>${riepilogo.produzionePerdutaFormatted}</td>
						</tr>
						<tr>
							<th>% Danno calcolato</th>
							<td>${riepilogo.percDannoCalcolatoFormatted}</td>
						</tr>
						<tr>
							<th>Importo del Premio pagato all'assicurazione</th>
							<td>${assicurazioniColture.importoPremioFormatted}</td>
						</tr>
						<tr>
							<th>Importo del risarcimento ricevuto dall'assicurazione</th>
							<td>${assicurazioniColture.importoRimborsoFormatted}</td>
						</tr>						
						<tr>
							<th>P.L.V. ricalcolata tenendo conto dei rimborsi assicurativi</th>
							<td>${riepilogo.plvRicalcolataConAssicurazioniFormatted}</td>
						</tr>
						<tr>
							<th>Produzione Perduta ricalcolata tenendo conto dei rimborsi assicurativi</th>
							<td>${riepilogo.plvOrdinariaConAssicurazioniFormatted}</td>
						</tr>
						<tr>
							<th>% Danno (calcolato tenendo conto dei rimborsi assicurativi)</th>
							<td>${riepilogo.percentualeDannoConAssicurazioniFormatted}</td>
						</tr>
						<tr>
							<th>Differenza percentuale fra il totale prod.perduta rispetto triennio e la stessa tenuto conto rimborso assicurativo</th>
							<td>${riepilogo.percProdPerdutaTriennioRimborsiFormatted}</td>
						</tr>						
					</tbody>
				</table>
		</div>
		
		<div style="overflow-x:scroll;">
				<h4>Riepilogo Danni Colture</h4>
				<c:set var ="tableName"  value ="tableRiepilogoDanniColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
				<table id="tableRiepilogoDanniColture"
					class="table-bootstrap table table-hover table-striped table-bordered tableBlueTh"
					data-toggle="table"
					data-url = "get_list_danni_colture.json"
					data-pagination="true" 
					data-only-info-pagination="true"
					data-show-pagination-switch="true" 
					data-pagination-v-align="top"
					data-show-columns="false" 
					data-page-list="[10, 25, 50, 70, 100]"
					data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
					data-show-default-order-button="true"
					data-default-order-column="${defaultOrderColumn}"
					data-default-order-type="${defaultOrderType}"
					data-table-name="${tableName}"
					data-escape-table="true"
				
				>
					<thead>
						<tr>
							<th></th>
							<th></th> 
							<th colspan="2">P.L.V.<br/>Ordinaria</th>
							<th colspan="2">P.L.V.<br/>Effettiva</th>
							<th colspan="2">Produzione Perduta<br/>(comprende rimborsi assicurativi)</th>
							<th>Prestito o Contributo<br/>massimo concedibile</th>
							<th>Prestito o Contributo<br/>erogabile</th>
						</tr>
						<tr>
							<th data-field="tipoUtilizzoDescrizione" data-sortable="true" data-switchable="false">Utilizzo</th>
							<th data-field="superficieUtilizzata" data-sortable="false" data-switchable="false" data-formatter="numberFormatter4">Superficie (ha)</th>
							 
							<th data-field="totQliPlvOrd" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2">Tot. Qli</th>
							<th data-field="totEuroPlvOrd" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2">Tot. Euro</th>
							
							<th data-field="totQliPlvEff" data-sortable="false" data-switchable="false" data-formatter="conditionalFormatterQli">Tot. Qli</th>
							<th data-field="totEuroPlvEff" data-sortable="false" data-switchable="false" data-formatter="conditionalFormatterEuro">Tot. Euro</th>
							
							<th data-field="percDanno" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2">% Danno</th>
							<th data-field="prodPerduta" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2">Tot. Euro</th>

							<th data-field="contrMaxConcedibile" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2AcceptNull">
								<c:if test="${riepilogo.bando.percContributoMaxConcessa != null}">
									(${riepilogo.bando.percContributoMaxConcessaFormatted})
								</c:if>
								<c:if test="${riepilogo.bando.percContributoMaxConcessa == null}">
									(non previsto)
								</c:if>
							
							</th>
							<th data-field="contrErogabile" data-sortable="false" data-switchable="false" data-formatter="numberFormatter2AcceptNull">
								<c:if test="${riepilogo.bando.percContributoErogabile != null}">
									(${riepilogo.bando.percContributoErogabileFormatted})
								</c:if>
								<c:if test="${riepilogo.bando.percContributoErogabile == null}">
									(non previsto)
								</c:if>							
							</th>
							 
						</tr>
						
					</thead>
					<tbody>
					</tbody>
				</table>
				
				
				
			<br/>
		</div>
   		<br/>
   		</div>
   		</c:otherwise>
   		</c:choose>
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
<script type="text/javascript">
	
	function conditionalFormatterQli($value,row,index){
		
		if(parseFloat(row['totQliPlvEff']) == parseFloat(row['totQliPlvOrd']) 
			&& parseFloat(row['totQliPlvOrd'])==0){ //controllo extra-analisi
			return '';
		}else{
			return numberFormatter2($value,row,index);
		}
	}
	
	function conditionalFormatterEuro($value,row,index){
		if(parseFloat(row['totEuroPlvEff']) == parseFloat(row['totEuroPlvOrd'])
			&& parseFloat(row['totEuroPlvOrd'])==0){ //controllo extra-analisi
			return '';
		}else{
			return numberFormatter2($value,row,index);
		}
	}

</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />