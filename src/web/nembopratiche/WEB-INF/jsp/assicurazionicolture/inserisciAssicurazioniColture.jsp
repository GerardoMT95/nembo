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
	<p:set-cu-info />
	<p:breadcrumbs cdu="${cdu}" />
	<p:messaggistica />
	<p:testata cu="${cdu}" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelInserisciAssicurazioniColture">
		<br/>
		<form name="formVisualizzaAssicurazioniColture" id="formVisualizzaAssicurazioniColture" action="index.do" method="POST">
				
				<h4>Assicurazioni Colture</h4>
				<c:set var ="tableName"  value ="tableInserisciAssicurazioniColture"/>
				<table id="${tableName}"
					class="table table-hover table-striped table-bordered tableBlueTh">
					<thead>

					</thead>
					<tbody>
						<tr>
							<th rowspan="2" class="col-sm-2">Consorzio di Difesa delle Produzioni<br/>oppure<br/>Ente Privato</th>
								<td class="col-sm-10">
									Provincia: <m:select id="slcProvincia" list="${province}" name="slcProvincia" preferRequestValues="${preferRequest}" onchange="loadSlcConsorzi()" 
														selectedValue="${assicurazioniColture == null ? null : assicurazioniColture.extIdProvincia}" />
									<br/>
									Consorzio: <m:select id="slcConsorzi" list="${consorzi}" name="slcConsorzi" preferRequestValues="${preferRequest}" 
														selectedValue="${assicurazioniColture == null ? null : assicurazioniColture.idConsorzioDifesa}"/>
								</td>

						</tr>
						<tr> 
								<td>
									Ente Privato: <m:textfield id="txtEntePrivato" name="txtEntePrivato" preferRequestValues="${preferRequest}"
													value="${assicurazioniColture == null ? null : assicurazioniColture.nomeEntePrivato}">
													</m:textfield>
								</td>
						</tr>

						<tr>
							<th>Numero Socio <br/>o Polizza</th>
								<td>
									<m:textfield id="txtSocioPolizza" name="txtSocioPolizza" preferRequestValues="${preferRequest}"
												value="${assicurazioniColture == null ? null : assicurazioniColture.numeroSocioPolizza}"/>
								</td>
						</tr>
						<tr>
							<th>Importo Premio<br/>annuale</th>
								<td>
									<m:textfield id="txtPremioAnnuale" name="txtPremioAnnuale" preferRequestValues="${preferRequest}"
												value="${assicurazioniColture == null ? null : assicurazioniColture.importoPremio}"/>
											
									
								</td>
						</tr>
						<tr>
							<th>Valore<br/>Assicurato</th>
								<td>
									<m:textfield id="txtValoreAssicurato" name="txtValoreAssicurato" preferRequestValues="${preferRequest}"
												value="${assicurazioniColture == null ? null : assicurazioniColture.importoAssicurato}"
												/>	
								</td>
						</tr>
						<tr>
							<th>Importo del<br/>Risarcimento</th>
								<td>
									<m:textfield id="txtRisarcimento" name="txtRisarcimento" preferRequestValues="${preferRequest}"
												value="${assicurazioniColture == null ? null : assicurazioniColture.importoRimborso}"
												/>
								</td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="idAssicurazioniColture" id="idAssicurazioniColture" value="${idAssicurazioniColture}" />
				<input type="submit" class="btn btn-primary pull-right" value="Conferma"/>
				<a class="btn btn-default" href="../cunembo${cuNumber}l/index.do">Indietro</a>
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
		
		
		<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
		</p:abilitazione-azione>
		
		<script type="text/javascript">
			function idAssicurazioniColtureFormatter($value, row, index){
				var risultato;
				/*
					<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="MODIFICA">
				*/
					risultato+='<a class="ico24 ico_modify" href="${cuNumber}M/index_' + $value +'.do"></a>';
				/*
					</p:abilitazione-azione>
				*/
				
				/*
					<p:abilitazione-azione codiceQuadro="ASSIC" codiceAzione="ELIMINA">
				*/
					var modifica = 'openPageInPopup("../cunembo${cuNumber}e/index_" + $value +".do",\'dlgEliminaAssicurazioni\',\'Elimina Assicurazioni\',\'modal-large\',false, null);';
					risultato+='<a class="ico24 ico_trash" onclick="' + modifica +'"></a>';
					risultato+='<input type="checkbox" name="chkIdAssicurazioniColture" id="chkIdAssicurazioniColture" value="' + $value + '" />';
				/*
					</p:abilitazione-azione>
				*/						
			}
		
		function loadSlcConsorzi()
		{
			var idProvincia = $('#slcProvincia').val();
					    $.ajax({
		        type: "GET",
		        url: '../cunembo${cuNumber}i/get_list_consorzi_' + idProvincia +'.do',
		        data: '',
		        dataType: "json",
		        success: function(data) {
		        	loadlist('#slcConsorzi',data);
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		}
		
		/*
			Caricamento dimanico combobox select
		*/
		function loadlist(selobj, jsonList) {
		  $(selobj).empty();
		  $(selobj).append('<option value selected="selected">-- selezionare --</option>')
		  $(selobj).append(
		    $.map(jsonList, function(el, i) {
		      console.log(el.slno + ' ' + el.state + ' ' +el.id + ' ' + el.descrizione);
		      return $('<option>').val(el.id).text(el.descrizione)
		}));
}
		</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />
</body>