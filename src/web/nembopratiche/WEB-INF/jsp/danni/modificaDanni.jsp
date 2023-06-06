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
	<p:breadcrumbs cdu="CU-NEMBO-298-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-298-M" />
	<script type="text/javascript">
		var idUnitaMisuraDannoAtm={};
	</script>
	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaTipologieDanni">
		<br/>
		<form name="formModificaDanniDettaglio" id="formModificaDanniDettaglio" action="../cunembo298m/modifica_danni_conferma.do" method="POST">
			<table id="modificaDanniTable" class="table table-hover table-striped table-bordered tableBlueTh" data-undefined-text = '-' data-pagination="false" 
			  >
			<thead>
	        	<tr>
					<th>Tipo Danno</th>
					<th>Denominazione</th>
					<th>Descrizione entità<br/>danneggiata</th>
	        		<th>Descrizione<br/>Danno</th>
	        		<th>Quantità<br/>Danno</th>
	        		<th>Unità di<br/>Misura</th>
	        		<th>Importo (&euro;)</th>
	        	</tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${danni}" var="danno">
			    	<tr>
				    	<td>
				    		<m:textfield id="tipoDanno_${danno.idDannoAtm}" name="tipoDanno_${danno.idDannoAtm}" disabled="true" value="${danno.tipoDanno}"></m:textfield>
				    	</td>
				    	<td>
				    		<m:textarea id="denominazione_${danno.idDannoAtm}" name="denominazione_${danno.idDannoAtm}" disabled="true"><c:out value="${danno.denominazioneFormatted}"></c:out></m:textarea>
<%-- 				    		<m:textfield id="denominazione_${danno.idDannoAtm}" name="denominazione_${danno.idDannoAtm}" disabled="true" value="${danno.denominazione}"></m:textfield> --%>
				    	</td>
				    	<td>
				    		<m:textfield id="descEntitaDanneggiata_${danno.idDannoAtm}" name="descEntitaDanneggiata_${danno.idDannoAtm}" disabled="true" value="${danno.descEntitaDanneggiata}" ></m:textfield>
				    	</td>
				    	<td>
					    	<input type="hidden" name="idDannoAtm" value="${danno.idDannoAtm}" />
					    	<m:textfield id="descrizione_${danno.idDannoAtm}" name="descrizione_${danno.idDannoAtm}" preferRequestValues="${preferRequest}" value="${danno.descrizione}"></m:textfield></td>
				    	<td><m:textfield id="quantita_${danno.idDannoAtm}" name="quantita_${danno.idDannoAtm}" preferRequestValues="${preferRequest && !danno.isModificaQuantitaDisabled}" value="${danno.quantita}" disabled="${danno.isModificaQuantitaDisabled}"></m:textfield></td>
				    	<td>
			    			<m:textfield id="descUnitaMisura_${danno.idDannoAtm}" name="descUnitaMisura_${danno.idDannoAtm}" visible="true" value="${danno.descUnitaMisura}" disabled="true"></m:textfield>
				    		<input type="hidden" name="unitaMisura_${danno.idDannoAtm}" id="unitaMisura_${danno.idDannoAtm}" value="${danno.idUnitaMisura}" />
				    	</td>
			    		<td><m:textfield id="importo_${danno.idDannoAtm}"  name="importo_${danno.idDannoAtm}" preferRequestValues="${preferRequest}" value="${danno.importo}"></m:textfield></td>
			    	</tr>
			    	<script type="text/javascript">
						
			    	</script>
		    	</c:forEach>
		    </tbody>
		    </table>
   		
   		<c:choose>
			<c:when test="${idDanno == 5 || idDanno == 6 || idDanno == 7 || idDanno == 11 }">
					<h4>Riepilogo danni</h4>
					<c:set var ="tableName"  value ="tblModificaConduzioniDanni"/>
					<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
					<c:set var ="defaultOrderType"  value =""/><!-- asc o desc -->
					<table id="${tableName}"
							class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh"
							data-toggle="table"
							data-pagination=true 
							data-only-info-pagination=true
							data-show-pagination-switch="true" 
							data-pagination-v-align="top"
							data-show-columns="true" 
							data-page-list="[10, 25, 50, 70, 100]"
							data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '50'}"
							data-show-default-order-button="true"
							data-default-order-column="${defaultOrderColumn}"
							data-default-order-type="${defaultOrderType}"
							data-table-name="${tableName}"
							data-sort-name="${sessionMapNomeColonnaOrdinamento.get(tableName) != null ? 
								sessionMapNomeColonnaOrdinamento.get(tableName) : 
								defaultOrderColumn}"
							data-sort-order="${sessionMapTipoOrdinamento.get(tableName) != null ? sessionMapTipoOrdinamento.get(tableName) : defaultOrderType}"
							data-page-number="${sessionMapNumeroPagina.get(tableName) != null ? sessionMapNumeroPagina.get(tableName) : 1}"
					>
						<thead>
							<tr>
							<th data-field="comune" data-sortable = "true">Comune</th>
							<th data-field="sezione" data-sortable = "true">Sezione</th>
							<th data-field="foglio" data-sortable = "true">Foglio</th>
							<th data-field="particella" data-sortable = "true">Particella</th>
							<th data-field="subalterno" data-sortable = "true">Subalterno</th>
							<th data-field="supCatastale" data-sortable = "true" data-formatter="numberFormatter4" >Superficie catastale</th>
							<th data-field="occupazioneSuolo" data-sortable = "true">Occupazione del suolo</th>
							<th data-field="destinazione" class="alignRight" data-sortable = "true">Destinazione</th>
							<th data-field="uso">Uso</th>
							<th data-field="qualita">Qualità</th>
							<th data-field="varieta">Varietà</th>
							<th data-field="superficieUtilizzata" data-formatter="numberFormatter4">Superficie utilizzata</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${listConduzioni}" var="conduzione">
								<tr>	
									<td>
										${conduzione.comune}
										<input type="hidden" name="idUtilizzoDichiarato" value="${conduzione.idUtilizzoDichiarato}"></input>
									</td>
									<td>${conduzione.sezione}</td>
									<td>${conduzione.foglio}</td>
									<td>${conduzione.particella}</td>
									<td>${conduzione.subalterno}</td>
									<td>${conduzione.supCatastale}</td>
									<td>${conduzione.occupazioneSuolo}</td>
									<td>${conduzione.destinazione}</td>
									<td>${conduzione.uso}</td>
									<td>${conduzione.qualita}</td>
									<td>${conduzione.varieta}</td>
									<td>${conduzione.superficieUtilizzata}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<a id="btnIndietro" class="btn btn-default">Indietro</a>
			</c:when>
			<c:otherwise>
				<br/>
				<a class="btn btn-default" href="../cunembo298l/index.do">Indietro</a>
			</c:otherwise>				
		</c:choose>		
		<a class="pull-right"><input type="submit" class="btn btn-primary" value="Conferma"></input></a>	
		<br/>
   		</form>
   		</m:panel>
   		<br/>
   		<br/>
   		<br/>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
<script src="/nembopratiche/js/nembotableformatter.js"></script>
<c:choose>
	<c:when test="${idDanno == 5 || idDanno == 6 || idDanno == 7 || idDanno == 11 }">
		<script type="text/javascript">
		      $('#btnIndietro').click(function () {
		          $('#formModificaDanniDettaglio').attr("action", "../cunembo298m/modifica_danno_${idDannoAtm}.do");
		          $('#formModificaDanniDettaglio').attr("method", "POST");
		          $('#formModificaDanniDettaglio').submit();
		      });
		</script>
	</c:when>
</c:choose>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />