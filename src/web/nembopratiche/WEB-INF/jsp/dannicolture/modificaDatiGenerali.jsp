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
	<p:breadcrumbs cdu="CU-NEMBO-310-L" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-310-L" />
	
	<form:form action="indexDatiGenerali.do" modelAttribute=""  id="elenco" method="post" class="form-horizontal">
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoColtureAziendali">
		
		<br/>
		<m:panel id="panelElencoColtureAziendali2" title="Dati evento calamitoso">
		<table summary="Bando" style="margin-top: 4px" class="myovertable table table-hover table-condensed table-bordered">
					<colgroup>
						<col width="20%" />
						<col width="80%" />
					</colgroup>
					<tbody>
						<tr class="toggle_target_altri">
							<th>Descrizione evento calamitoso</th>
							<td><m:textarea preferRequestValues="${preferRequestValues}"  id="descrizioneDanno" name="descrizioneDanno">${dannoDTO.descrizioneDanno}</m:textarea> </td>
						</tr>
						<tr class="toggle_target_altri">
							<th>Data evento calamitoso</th>
							<td><m:textfield preferRequestValues="${preferRequestValues}" id="dataDanno" name="dataDanno" value="${dannoDTO.dataDanno}"></m:textfield> </td>
						</tr>
						
					</tbody>
				</table>
				<div class="form-group puls-group" style="margin-top: 2em">
					<div class="col-sm-12">
						<button type="button" onclick="forwardToPage('../cunembo310l/index.do');" class="btn btn-default">indietro</button>
						<button type="submit" name="conferma" id="conferma" class="btn btn-primary pull-right">conferma</button>
					</div>
				</div>
		</m:panel>
		
		<br/>
				<c:set var ="tableName"  value ="tableRiepilogoDanniColture"/>
				<c:set var ="defaultOrderColumn"  value =""/><!-- nome campo per cui ordinare di default -->
				<c:set var ="defaultOrderType"  value ="asc"/><!-- asc o desc -->
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
					data-page-size="${sessionMapPageSize.get(tableName) != null ? sessionMapPageSize.get(tableName) : '25'}"
					data-show-default-order-button="true"
					data-default-order-column="${defaultOrderColumn}"
					data-default-order-type="${defaultOrderType}"
					data-table-name="${tableName}"
					data-escape-table="true"
				
				>
					<thead>
						<tr>
							<th class="center" data-field="idSegnalazioneDanno" data-width="130" data-switchable="false"  data-formatter="iconeFormatter">
								<a style="cursor:pointer" onclick="modificaMultipla();" title="Modifica selezionati"><i class="ico24 ico_modify"></i></a>
							</th>
							<th data-field="descrizioneComune" data-sortable="true" data-switchable="true">Comune</th>
							<th data-field="foglio" data-sortable="true" data-switchable="true">Foglio n.</th>
							<th data-field="descrizioneColtura" data-sortable="true" data-switchable="true">Coltura/struttura</th>
							<th data-field="superficieGraficaStr" data-sortable="true" data-switchable="true">Ha</th>
							<th data-field="percDannoStr" data-sortable="true" data-switchable="true">% danno</th>
							<th data-field="importoDannoStr" data-sortable="true" data-switchable="true">Danno stimato - &euro;</th>
							<th data-field="noteDanno" data-sortable="true" data-switchable="true">Note</th>
						</tr>
					</thead>
			</table>
		</m:panel>
	</div>
</form:form>
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
	
	
	function iconeFormatter($value, row, index)
    {
		debugger;
        var html = '';
        html += '<a style="cursor:pointer" href="#" onclick="elimina('+row.idSegnalazioneDanno+')"><i class="ico24 ico_trash"></i></a>';
        html += '  <a style="cursor:pointer" href=\"../cunembo310m/modifica_'+row.idSegnalazioneDanno+'.do\"><i class="ico24 ico_modify"></i></a>';
        html += '  <input type="checkbox" name="idSegnalazioneDanno" value="' + $value + '"></input>';
		return html;
	}
	
	function modificaMultipla()
	{
		if(isAllCheckboxUnchecked('idSegnalazioneDanno'))
		{
			showMessageBox('Modifica', 'Selezionare almeno una segnalazione danno da modificare.','modal-large');
		}
		else
		{
			submitFormTo($('#elenco'), '../cunembo310m/modifica_multipla.do');
					
		}
	}
	
	function elimina(idSegnalazioneDanno) {
		return openPageInPopup('../cunembo310m/confermaElimina_' + idSegnalazioneDanno + '.do', 'dlgElimina', 'Elimina', 'dlgEliminas',
					false);
			
	}
	

</script>
<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />