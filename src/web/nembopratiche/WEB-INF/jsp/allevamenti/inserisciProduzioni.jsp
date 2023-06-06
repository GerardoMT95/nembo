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
	
	<script type="text/javascript">
		var cProd = parseInt("${maxIdDaInserire}");
		var idsDaInserire = "${idsDaInserire}";
		var arrayCProd = [];
		var mapCProd = new Map();
	</script>

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-300-I" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-300-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelDettaglioAllevamento">
		<br/>
				<h4>Dettaglio Allevamento</h4>
		<form id="frmInserisciProduzioni" method="POST" action="../cunembo300i/inserisci_conferma_${idCategoriaAnimale}_${istatComune}.do">
				<table id="tblDettaglioAllevamento"
					class=" table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead></thead>
					<tbody>
						<tr>
							<th>Ubicazione allevamento</th>
							<td>${allevamento.ubicazioneAllevamento}</td>
						</tr>
						<tr>
							<th>Cod. Azienda Zootecnica</th>
							<td>${allevamento.codiceAziendaZootecnica}</td>
						</tr>
						<tr>
							<th>Specie</th>
							<td>${allevamento.descrizioneSpecieAnimale}</td>
						</tr>
						<tr>
							<th>Categoria</th>
							<td>${allevamento.descrizioneCategoriaAnimale}</td>
						</tr>
						<tr>
							<th>Quantit&agrave;</th>
							<td>${allevamento.quantitaUnitaMisura}</td>
						</tr>
						<tr>
							<th>Peso medio per unit&agrave;</th>
							<td><m:textfield id="pesoVivoMedio" name="pesoVivoMedio" value="${allevamento.pesoVivoMedio}" preferRequestValues="${preferRequest}"></m:textfield></td>
						</tr>
						<tr>
							<th>Giornate lavorative medie per unit&agrave;</th>
							<td><m:textfield id="giornLavorMedie" name="giornLavorMedie" value="${allevamento.giornateLavorativeMedie}" preferRequestValues="${preferRequest}"></m:textfield></td>
						</tr>
						<tr>
							<th>Giornate lavorative totali</th>
							<td>${allevamento.giornateLavorativeFormatted}</td>
						</tr>
						<tr>
							<th>Unit&agrave; foraggere</th>
							<td>${allevamento.unitaForaggereFormatted}</td>
						</tr>
						<tr>
							<th>Note</th>
							<td><m:textarea id="note" name="note" preferRequestValues="${preferRequest}">${allevamento.note}</m:textarea></td>
						</tr>
					</tbody>
				</table>
		<br/>
		
		<h4>Produzioni</h4>
		
			<table id="tblProduzioniAllevamento" 
				class="table table-hover table-striped table-bordered tableBlueTh">
			<thead>
				<tr>
					<th><a class="ico24 ico_add" title="Aggiungi Produzione" onclick="aggiungiProduzione();"></a></th>
					<th>Produzione</th>
					<th>Numero<br/>Capi</th>
					<th>Quantit&agrave;<br/>prodotta</th>
					<th>Quantit&agrave;<br/>reimpiegata</th>
					<th>Unit&agrave; di<br/>misura</th>
					<th>Prezzo</th>
				</tr>	
			</thead>
			<tbody>
				<c:forEach var="produzione" items="${produzioni}" varStatus="idx">
					<tr id="row_${idx.index}">
						<td><input type="hidden" name="idDaInserire" value="${idx.index}" /><a class="ico24 ico_trash" onclick="eliminaProduzione(parseInt(${idx.index}));"></a></td>
						<td><m:select id="idProduzione_${idx.index}" list="${listProduzioni}" name="idProduzione_${idx.index}" preferRequestValues="${preferRequest}" selectedValue="${produzione.idProduzione}" onchange="modificaUnitaMisura(${idx.index});" ></m:select></td>
						<td><m:textfield id="numeroCapi_${idx.index}" name="numeroCapi_${idx.index}" preferRequestValues="${preferRequest}" value="${produzione.numeroCapi}"></m:textfield></td>
						<td><m:textfield id="quantitaProdotta_${idx.index}" name="quantitaProdotta_${idx.index}" preferRequestValues="${preferRequest}" value="${produzione.quantitaProdotta}"></m:textfield></td>
						<td><m:textfield id="quantitaReimpiegata_${idx.index}" name="quantitaReimpiegata_${idx.index}" preferRequestValues="${preferRequest}" value="${produzione.quantitaReimpiegata}"></m:textfield></td>
						<td><m:select id="unitaMisura_${idx.index}" list="${listUnitaMisura}" name="unitaMisura_${idx.index}" preferRequestValues="${preferRequest}" selectedValue="${produzione.idProduzione}" disabled="true"></m:select></td>
						<td><m:textfield id="prezzo_${idx.index}" name="prezzo_${idx.index}" preferRequestValues="${preferRequest}" value="${produzione.prezzo}"></m:textfield></td>
					</tr>
				</c:forEach>
					<c:forEach var="i" items="${idsDaInserire}">
						<tr id="row_${i}">
							<td><input type="hidden" name="idDaInserire" value="${i}" /><a class="ico24 ico_trash" onclick="eliminaProduzione(parseInt(${i}));"></a></td>
							<td><m:select id="idProduzione_${i}" list="${listProduzioni}" name="idProduzione_${i}" preferRequestValues="${preferRequest}" onchange="modificaUnitaMisura(${i});"></m:select></td>
							<td><m:textfield id="numeroCapi_${i}" name="numeroCapi_${i}" preferRequestValues="${preferRequest}"></m:textfield></td>
							<td><m:textfield id="quantitaProdotta_${i}" name="quantitaProdotta_${i}" preferRequestValues="${preferRequest}"></m:textfield></td>
							<td><m:textfield id="quantitaReimpiegata_${i}" name="quantitaReimpiegata_${i}" preferRequestValues="${preferRequest}"></m:textfield></td>
							<td><m:select id="unitaMisura_${i}" list="${listUnitaMisura}" name="unitaMisura_${i}" preferRequestValues="${preferRequest}" selectedValue="${produzione.idProduzione}" disabled="true"> </m:select></td>
							<td><m:textfield id="prezzo_${i}" name="prezzo_${i}" preferRequestValues="${preferRequest}"></m:textfield></td>
						</tr>
					</c:forEach>
			</tbody>	
			</table>
			<input class="btn btn-primary pull-right" type="submit" value="Conferma" />
			<a class="btn btn-default" href="../cunembo300l/index.do">Indietro</a>
		</form>
		<div id="hiddenDiv" style="display:none;">
			<m:select id="genericID" list="${listProduzioni}" name="genericID" onchange="modificaUnitaMisura(cProd);"></m:select>
		</div>
		<div id="hiddenDivUM" style="display:none;">
			<m:select id="genericID" list="${listUnitaMisura}" name="genericID" disabled="true" ></m:select>
		</div>
		</m:panel>
	</div>
	<!-- 	Conversione liste java jstl ad array javascript -->
	<script type="text/javascript">
	<c:forEach var="produzione" items="${listProduzioni}" varStatus="i">
		arrayCProd.push("${produzione.codice}");
		mapCProd.set("${produzione.codice}", true);
	</c:forEach>
	</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			loadBootstrapTable('${tableName}', '');
		 });
		</script>
		<script type="text/javascript">
		var idProduzioneVendibile={};
		function idProduzioneVendibileFormatter($value,row,index)
		{
			var valore='';
		 	valore = valore + 
		 		'<a class="ico24 ico_trash" onclick="eliminaProduzione(' + index + ')"></a>'
		 	return valore;
		}
		
		//GUIDA: aggiunta riga tabella con bottone
		function aggiungiProduzione()
		{
			cProd = cProd + 1;
			var risultato = '';
			var selectListProduzioni = $('#hiddenDiv').html()
						.replace('genericID','idProduzione_' + cProd)
						.replace('genericID','idProduzione_' + cProd)
						.replace('cProd', cProd)
						.replace('cProd', cProd);
			var selectListUnitaMisura = $('#hiddenDivUM').html()
						.replace('genericID','unitaMisura_' + cProd)
						.replace('genericID','unitaMisura_' + cProd)
						.replace('cProd', cProd);
			risultato = risultato + 
				'<tr id="row_' + cProd + '">' +
				'<td><input type="hidden" name="idDaInserire" value="' + cProd + '" /><a class="ico24 ico_trash" onclick="eliminaProduzione('+ cProd +');"></a></td>' +
				'<td>' + selectListProduzioni + '</td>' +
				'<td><m:textfield id="numeroCapi_' + cProd + '" name="numeroCapi_' + cProd + '"></m:textfield></td>' +
				'<td><m:textfield id="quantitaProdotta_' + cProd + '" name="quantitaProdotta_' + cProd + '"></m:textfield></td>' +
				'<td><m:textfield id="quantitaReimpiegata_' + cProd + '" name="quantitaReimpiegata_' + cProd + '"></m:textfield></td>' +
				'<td>' + selectListUnitaMisura + '</td>' +
				'<td><m:textfield id="prezzo_' + cProd + '" name="prezzo_' + cProd + '"></m:textfield></td>' +
				'</tr>';
			
			$('#tblProduzioniAllevamento').append(risultato);
			return risultato;
		}
		
		function eliminaProduzione(cProd)
		{
			var idProduzione = $('#idProduzione_'+cProd).val();
			$('#row_'+cProd).remove();
		}
		
		function modificaUnitaMisura(cProd)
		{
			var idProduzione = $('#idProduzione_'+cProd).val();
			$('#unitaMisura_'+cProd).val(idProduzione);
		}

		$('#frmInserisciProduzioni').submit(function(){
			$("select").css("background-color","#eee");
			$("select").removeAttr("disabled");
		});
		</script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />