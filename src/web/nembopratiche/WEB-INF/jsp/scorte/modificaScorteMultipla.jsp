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
<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
	
<body>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />

	<p:utente />
	<p:breadcrumbs cdu="CU-NEMBO-297-M" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-297-M" />

	<div class="container-fluid" id="content">
		<m:panel id="panelVisualizzaElencoScorte">
		<form action="../cunembo297m/modifica_scorte_conferma.do" method="POST"> 
			<table id="modificaScorteTable" class="table table-hover table-striped table-bordered tableBlueTh"
			  data-undefined-text = '-'
		      data-pagination="false" 
			  >
			<thead>
	        	<tr>
	        		<th>Tipologia</th>
	        		<th>Descrizione</th>
	        		<th>Quantità</th>
	        		<th>Unità di Misura</th>
	        	</tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${scorte}" var="scorta">
			    	<tr>
				    	<td>
				    		<input type="hidden" name="idScortaMagazzino" id="idScortaMagazzino_${scorta.idScortaMagazzino}" value="${scorta.idScortaMagazzino}"  />
				    		<m:select id="idScorta_${scorta.idScortaMagazzino}" name="idScorta_${scorta.idScortaMagazzino}" list="${elencoTipologieScorte}" preferRequestValues="${preferRequest}" selectedValue="${scorta.idScorta}" onchange="aggiornaUnitaDiMisura(${scorta.idScortaMagazzino});"></m:select>
				    	</td>
				    	<td>
				    		<m:textfield id="descrizione_${scorta.idScortaMagazzino}" name="descrizione_${scorta.idScortaMagazzino}" preferRequestValues="${preferRequest}" value="${scorta.descrizione}" disabled="${mappaDisabledDescrizione[scorta.idScortaMagazzino]}"></m:textfield>
				    	</td>
				    	<td>
				    		<m:textfield id="quantita_${scorta.idScortaMagazzino}" name="quantita_${scorta.idScortaMagazzino}" preferRequestValues="${preferRequest}" value="${scorta.quantita}"></m:textfield>
				    	</td>
				    	<td>
			    			<m:select id="unitaDiMisura_${scorta.idScortaMagazzino}" list="${elencoUnitaMisura}" name="unitaDiMisura_${scorta.idScortaMagazzino}" preferRequestValues="${preferRequest && !(mappaDisabledDescrizione[scorta.idScortaMagazzino])}" selectedValue="${scorta.idUnitaMisura}" onchange="setUnitaDiMisuraHidden(${scorta.idScortaMagazzino})" disabled="${mappaDisabledDescrizione[scorta.idScortaMagazzino]}"></m:select>
							<m:textfield visible="false" name="unitaDiMisuraHidden_${scorta.idScortaMagazzino}" id="unitaDiMisuraHidden_${scorta.idScortaMagazzino}"  style="display:none;" preferRequestValues="${preferRequest && !(mappaDisabledDescrizione[scorta.idScortaMagazzino])}" value="${scorta.idUnitaMisura}"/> 
				    	</td>
			    	</tr>
		    	</c:forEach>
		    </tbody>
		    </table>
		<br/>
		<a class="btn btn-default" href="../cunembo297l/index.do">Indietro</a>
		<input type="submit" class="btn btn-primary pull-right" value="Conferma" />
		</form>	
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
		var mappaTipologiaUnitaMisura={};		
	</script>
	<c:forEach items="${mappaTipologiaScorteUnitaDiMisura}" var="elem">
		<script type="text/javascript">
 			mappaTipologiaUnitaMisura["${elem.key}"] = "${elem.value}";
		</script>
	</c:forEach>
	<script>
//TODO: FIXME:	eventuale futura modifica al DB	 
		 var idScortaAltro="${idScortaAltro}";
		 function aggiornaUnitaDiMisura(idScortaMagazzino)
		 {
		 	var idScorta=$('#idScorta_'+idScortaMagazzino).val();
		 	var idUnitaDiMisuraNuova = mappaTipologiaUnitaMisura[idScorta];
		 	var unitaDiMisura = $('#unitaDiMisura_'+idScortaMagazzino);
		 	var unitaDiMisuraHidden = $('#unitaDiMisuraHidden_' + idScortaMagazzino);
		 	var descrizione = $('#descrizione_' + idScortaMagazzino);
			if(idScorta =='')
			{
				descrizione.prop('disabled',true);
				unitaDiMisura.prop('disabled', false);
				unitaDiMisura.val('');
				unitaDiMisuraHidden.val('');
			}
			else
			{
				if(idUnitaDiMisuraNuova=='')
				{
					unitaDiMisura.prop('disabled', false);
					unitaDiMisura.val('');
					unitaDiMisuraHidden.val('');
					descrizione.prop('disabled',false);
				}
				else
				{
					unitaDiMisura.val(idUnitaDiMisuraNuova);
					unitaDiMisuraHidden.val(idUnitaDiMisuraNuova);
					unitaDiMisura.prop('disabled', true);
					descrizione.prop('disabled',true);
					descrizione.val('');
				}
			}
		 }
		 
		function setUnitaDiMisuraHidden(idScortaMagazzino)
		{
		 	var unitaDiMisura = $('#unitaDiMisura_'+idScortaMagazzino);
		 	var unitaDiMisuraHidden = $('#unitaDiMisuraHidden_' + idScortaMagazzino);
		 	unitaDiMisuraHidden.val(unitaDiMisura.val());
		 	return true;
		}		

		function unitaDiMisuraToHidden()
		{
		 	var um = $('#unitaDiMisura').val();
		 	$('#unitaDiMisuraHidden').val(um);
		 	var um2 = $('#unitaDiMisuraHidden').val();
		}
	</script>
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>	
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />