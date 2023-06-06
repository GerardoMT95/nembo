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

		<form name="formModificaSuperficiColture" id="formModificaSuperficiColture" action="../cunembo299m/conferma_modifica.do" method="POST">
				<input type="hidden" name="idSuperficieColtura" name ="idSuperficieColtura" value="${superficiColturaDettaglio.idSuperficieColtura}" />
				
				<h4>Dettaglio Superfici e Colture</h4>
				<table id="tableSuperificiColtureDettaglio"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th class="col-md-4">Ubicazione terreno</th>
							<td class="col-md-8">${superficiColturaDettaglio.ubicazioneTerreno}</td>
						</tr>
						<tr>
							<th class="col-md-4">Utilizzo</th>
							<td class="col-md-8">${superficiColturaDettaglio.tipoUtilizzoDescrizione}</td>
						</tr>
						<tr>
							<th class="col-md-4">Superficie</th>
							<td class="col-md-8">${superficiColturaDettaglio.superficieUtilizzata}</td>
						</tr>
						<tr>
							<th class="col-md-4">Note</th>
							<td class="col-md-8">
							<m:textarea name="txtNote" id="txtNote" preferRequestValues="${preferRequest}">${superficiColturaDettaglio.note}</m:textarea>
							
							</td>
						</tr>
					</tbody>
				</table>
				
				<h4>Dettaglio PSR</h4>
				<table id="tableSuperificiColtureDettaglio"
					class="table table-hover table-striped table-bordered tableBlueTh"
				>
					<thead>
					</thead>
					<tbody>
						<tr>
							<th class="col-md-4">Produzione (q/ha)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtProduzioneHa" name="txtProduzioneHa" value="${superficiColtureDettaglioPsrDTO.produzioneHa}" onkeyup="onUpdateProduzioneHa();" preferRequestValues="${preferRequest}"  ></m:textfield>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">Produzione (totale)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtProduzioneTotale" name="txtProduzioneTotale" disabled="true" value="${superficiColtureDettaglioPsrDTO.prodTotale}"></m:textfield>
								<input type="hidden" id="txtProduzioneTotaleHidden" name="txtProduzioneTotaleHidden" value="${superficiColtureDettaglioPsrDTO.prodTotale}"></input>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">Giornate lavorative (ad ha)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtGiornateLavorate" name="txtGiornateLavorate" value="${superficiColtureDettaglioPsrDTO.giornateLavorate}" onkeyup="onUpdateGiornateLavorate();" preferRequestValues="${preferRequest}"></m:textfield>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">Giornate lavorative (totale)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtGiornateLavorateTot" name="txtGiornateLavorateTot" value="${superficiColtureDettaglioPsrDTO.giornateLavorativeTot}" disabled="true"></m:textfield>
								<input type="hidden" id="txtGiornateLavorateTotHidden" name="txtGiornateLavorateTotHidden" value="${superficiColtureDettaglioPsrDTO.giornateLavorativeTot}"></input>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">Unità foraggere (totali)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtUfProdotte" name="txtUfProdotte" disabled="true" value="${superficiColtureDettaglioPsrDTO.ufTot}"></m:textfield>
								<input type="hidden" id="txtUfProdotteHidden" name="txtUfProdotteHidden" value="${superficiColtureDettaglioPsrDTO.ufTot}"></input>
							</td>
						</tr>
						<tr>
							<th class="col-md-4" rowspan="3">Reimpieghi</th>
							<td class="col-md-2">Unità di Misura:</td>
							<td class="col-md-6">Quantità:</td>								
						</tr>
						<tr>
							<td class="col-md-2"><m:select id="slcUnitaMisura" list="${listUnitaMisura}" name="slcUnitaMisura" onchange="updatePlvTotQ();" preferRequestValues="${preferRequest}"></m:select></td>
							<td class="col-md-6"><m:textfield id="txtReimpieghiQnt" name="txtReimpieghiQnt" value="${superficiColtureDettaglioPsrDTO.reimpieghiQuantita}" onkeyup="updatePlvTotQ()" preferRequestValues="${preferRequest}"></m:textfield></td>
						</tr>
						<tr>
							<td colspan="2">
							Note: per segnalare l'assenza di reimpieghi è sufficiente scrivere 0 nel campo Quantità.
							</td>
						</tr>	
						
						<tr>
							<th class="col-md-4">P.L.V. (totale q)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtPlvTotQ" name="txtPlvTotQ" disabled="true" value="${superficiColtureDettaglioPsrDTO.plvTotQ}"></m:textfield>
								<input type="hidden"id="txtPlvTotQHidden" name="txtPlvTotQHidden" value="${superficiColtureDettaglioPsrDTO.plvTotQ}"></input>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">P.L.V. (prezzo/q)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtPrezzo" name="txtPrezzo" value="${superficiColtureDettaglioPsrDTO.prezzo}" onkeyup="updatePlvTotDichiarato();" preferRequestValues="${preferRequest}"></m:textfield>
							</td>
						</tr>
						<tr>
							<th class="col-md-4">P.L.V. (totale dichiarato)</th>
							<td class="col-md-8" colspan="2">
								<m:textfield id="txtPlvTotDich" name="txtPlvTotDich" disabled="true" value="${superficiColtureDettaglioPsrDTO.plvTotDich}"></m:textfield>
								<input type="hidden" id="txtPlvTotDichHidden" name="txtPlvTotDichHidden" value="${superficiColtureDettaglioPsrDTO.plvTotDich}"></input>
							</td>
						</tr>
					</tbody>
				</table>
			<a class="btn btn-default" href="../cunembo299l/index.do">Indietro</a>
			<input type="submit" class="btn btn-primary pull-right" value="Conferma">
			</form>
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
	
	
	$(document).ready(function(){
		updateUnitaMisura();
		calcolaValoriIniziali();
	});
	
	var map = {};
	map['sumSuperficieUtilizzata'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.sumSuperficieUtilizzata}");
	map['produzioneHaMedia'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.produzioneHaMedia}");
	map['produzioneHaMin'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.produzioneHaMin}");
	map['produzioneHaMax'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.produzioneHaMax}");
	map['giornateLavorateMedie'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.giornateLavorateMedie}");
	map['giornateLavorateMin'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.giornateLavorateMin}");
	map['giornateLavorateMax'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.giornateLavorateMax}");
	map['prezzoMedio'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.prezzoMedio}");
	map['prezzoMin'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.prezzoMin}");
	map['prezzoMax'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.prezzoMax}");
	map['ufProdotte'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.ufProdotte}");
	map['qliReimpiegati'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.qliReimpiegati}");
	map['ufReimpiegate'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.ufReimpiegate}");
	
	map['ufProdotte'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.ufProdotte}");
	map['giornateLavorate'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.giornateLavorate}");
	map['produzioneHa'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.produzioneHa}");
	map['prezzo'] = convertStringToFloat("${superficiColtureDettaglioPsrDTO.prezzo}");
	

    
    function convertStringToFloat(num)
    {
    	if((num.split(",").length - 1)>1)
    	{
    	  return NaN;
    	}
    	var numReplaced = num.replace(',','.').replace(/[.](?=.*[.])/g, "");
    	var returnValue =  parseFloat(numReplaced);
    	return returnValue;
    }
    
	function calcolaValoriIniziali() {

			sostituisciVirgolaConPunto();
			var preferRequestValues = "${preferRequest}";
			var produzioneDichiarata;
			var giornateLavorativeDich;
			var ufTotali;


			var produzioneHa = convertStringToFloat($('#txtProduzioneHa').val());
			var giornateLavorate = convertStringToFloat($('#txtGiornateLavorate').val());
			var reimpieghiUnitaMisura = convertStringToFloat($('#slcUnitaMisura').val());
			var reimpieghiQuantita = convertStringToFloat($('#txtReimpieghiQnt').val());
			var prezzo = convertStringToFloat($('#txtPrezzo').val());

			var giornateLavorativeDich ='';
			var produzioneDichiarat = '';
			var ufTotali = '';
			var plvTotQuintali = '';
			var plvTotDich = '';
			var totaleGiaValutato = false;
			
			if(!isNaN(giornateLavorate))
			{
				giornateLavorativeDich = (giornateLavorate * map['sumSuperficieUtilizzata']).toFixed(2);
			}
			if(!isNaN(produzioneHa))
			{
				produzioneDichiarata = (produzioneHa * map['sumSuperficieUtilizzata']).toFixed(2); 	
				ufTotali = (map['ufProdotte'] * produzioneHa * map['sumSuperficieUtilizzata']).toFixed(2);
				
				if(!isNaN(reimpieghiQuantita))
				{
					
					if (reimpieghiUnitaMisura == 'qli') {
						plvTotQuintali = (produzioneHa * map['sumSuperficieUtilizzata'] - reimpieghiQuantita).toFixed(2);
					} else if (reimpieghiUnitaMisura == 'uf') {
						plvTotQuintali = ((produzioneHa * map['sumSuperficieUtilizzata']) - (reimpieghiQuantita / map['ufProdotte'])).toFixed(2);
					} else{
						if(reimpieghiQuantita == 0)
						{
							plvTotQuintali = (produzioneHa * map['sumSuperficieUtilizzata']).toFixed(2);
						}else
						{
							plvTotQuintali = '';
							plvTotDich = '';
							totaleGiaValutato = true;
						}
					}
					
					if(!isNaN(prezzo) && !totaleGiaValutato)
					{
						plvTotDich = (prezzo * plvTotQuintali).toFixed(2);
					}
				}
			}

			//formattati tutti a 2 cifre decimali
			$('#txtProduzioneTotale').val(produzioneDichiarata);
			$('#txtProduzioneTotaleHidden').val(produzioneDichiarata);

			$('#txtGiornateLavorateTot').val(giornateLavorativeDich);
			$('#txtGiornateLavorateTotHidden').val(giornateLavorativeDich);

			$('#txtUfProdotte').val(ufTotali);
			$('#txtUfProdotteHidden').val(ufTotali);

			$('#txtPlvTotQ').val(plvTotQuintali);
			$('#txtPlvTotQHidden').val(plvTotQuintali);

			$('#txtPlvTotDich').val(plvTotDich);
			$('#txtPlvTotDichHidden').val(plvTotDich);
		}

		function sostituisciVirgolaConPunto() {
			var produzioneHa = convertStringToFloat($('#txtProduzioneHa').val());
			var giornateLavorate = convertStringToFloat($('#txtGiornateLavorate').val())
			var reimpieghiQnt = convertStringToFloat($('#txtReimpieghiQnt').val())
			var prezzo = convertStringToFloat($('#txtPrezzo').val());
			if(!isNaN(produzioneHa))
				$('#txtProduzioneHa').val(produzioneHa);
			
			if(!isNaN(giornateLavorate))
				$('#txtGiornateLavorate').val(giornateLavorate);
			
			if(!isNaN(reimpieghiQnt))
				$('#txtReimpieghiQnt').val(reimpieghiQnt);
			
			if(!isNaN(prezzo))
				$('#txtPrezzo').val(prezzo);
		}

		function onUpdateProduzioneHa() {
			var produzioneHa = convertStringToFloat($('#txtProduzioneHa').val());
			if (!isNaN(produzioneHa)) {
				var produzioneTotale = calcolaProduzioneTotale(produzioneHa);
				var ufTotali = calcolaUnitaForaggereTotali(produzioneHa);
				$('#txtProduzioneTotale').val(produzioneTotale.toFixed(2));
				$('#txtProduzioneTotaleHidden')
						.val(produzioneTotale.toFixed(2));
				$('#txtUfProdotte').val(ufTotali.toFixed(2));
				$('#txtUfProdotteHidden').val(ufTotali);
			} else {
				$('#txtProduzioneTotale').val('');
				$('#txtProduzioneTotaleHidden').val('');
				$('#txtUfProdotte').val('');
				$('#txtUfProdotteHidden').val('');
			}
			updatePlvTotQ();
		}

		function calcolaUnitaForaggereTotali(produzioneHa) {
			var unitaForaggereTotali = map['ufProdotte'] * produzioneHa
					* map['sumSuperficieUtilizzata'];
			return unitaForaggereTotali;
		}

		function calcolaProduzioneTotale(produzioneHa) {
			var produzioneTotale = produzioneHa
					* map['sumSuperficieUtilizzata'];
			return produzioneTotale;
		}

		function onUpdateGiornateLavorate() {
			var giornateLavorate = convertStringToFloat($(
					'#txtGiornateLavorate').val());
			if (!isNaN(giornateLavorate)) {
				var giornateLavorateTot = giornateLavorate
						* map['sumSuperficieUtilizzata'];
				$('#txtGiornateLavorateTot')
						.val(giornateLavorateTot.toFixed(2));
				$('#txtGiornateLavorateTotHidden').val(giornateLavorateTot);
			} else {
				$('#txtGiornateLavorateTot').val('');
				$('#txtGiornateLavorateTotHidden').val('');
			}
		}

		function updateUnitaMisura() {
			var preferRequestValues = "${preferRequest}";
			if (preferRequestValues != null
					&& (preferRequestValues == '' || preferRequestValues == 'false')) {
				if (map['qliReimpiegati'] > 0) {
					$('#slcUnitaMisura').val('qli');
				} else if (map['ufReimpiegate']) {
					$('#slcUnitaMisura').val('uf');
				}
			}
			if (map['ufProdotte'] <= 0) {
				$("#slcUnitaMisura option[value='uf']").remove();
			}
		}

		function updatePlvTotQ() {
			var unitaMisura = $('#slcUnitaMisura').val();
			var reimpieghiQnt = convertStringToFloat($('#txtReimpieghiQnt')
					.val());
			var produzioneHa = convertStringToFloat($('#txtProduzioneHa').val());
			var reimpieghiQnt = convertStringToFloat($('#txtReimpieghiQnt').val());
			if (!isNaN(reimpieghiQnt)
					&& !isNaN(produzioneHa)
					&& !(unitaMisura != 'qli' && unitaMisura != 'uf' && unitaMisura != '')
					&& !isNaN(produzioneHa)) 
			{
				var plvTotQ = 0;
				if (unitaMisura == 'qli') 
				{
					plvTotQ = produzioneHa * map['sumSuperficieUtilizzata']
							- reimpieghiQnt;
				} 
				else if (unitaMisura == 'uf') 
				{
					var unitaForaggereTotali = calcolaUnitaForaggereTotali(produzioneHa);
					var produzioneTotale = calcolaProduzioneTotale(produzioneHa);
					plvTotQ = produzioneHa
							* map['sumSuperficieUtilizzata']
							- ((reimpieghiQnt / unitaForaggereTotali) * produzioneTotale);
				} 
				else if (unitaMisura == '' && reimpieghiQnt == 0) 
				{
					plvTotQ = produzioneHa * map['sumSuperficieUtilizzata'];
				}

				if (unitaMisura == '' && reimpieghiQnt != 0) 
				{
					$('#txtPlvTotQ').val('');
					$('#txtPlvTotQHidden').val('');
				} 
				else 
				{
					$('#txtPlvTotQ').val(plvTotQ.toFixed(2));
					$('#txtPlvTotQHidden').val(plvTotQ.toFixed(2));
				}
			}
			 else 
			{
				$('#txtPlvTotQ').val('');
				$('#txtPlvTotQHidden').val('');
			}
			updatePlvTotDichiarato();
		}

		function updatePlvTotDichiarato() 
		{

			var plvTotQ = convertStringToFloat($('#txtPlvTotQ').val());
			var plvPrezzoQ = convertStringToFloat($('#txtPrezzo').val());

			if (!isNaN(plvTotQ) && !isNaN(plvPrezzoQ)) 
			{
				var totale = plvTotQ * plvPrezzoQ;
				$('#txtPlvTotDich').val(totale.toFixed(2));
				$('#txtPlvTotDichHidden').val(totale);
			} 
			else 
			{
				$('#txtPlvTotDich').val('');
				$('#txtPlvTotDichHidden').val('');
			}
		}
</script>



	
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />