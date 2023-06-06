<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">

<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
	<p:utente/>
	<p:breadcrumbs cdu="CU-NEMBO-231-L"/>
	<p:messaggistica/><p:testata cu="CU-NEMBO-231-L" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoDate">
		<table id="elencoDate" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
			  data-toggle="table" 
			  data-url="getElencoDate.json"
			  data-undefined-text = ''>
			<colgroup>
				<col width="10%">
				<col width="24%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="36%">
			</colgroup>
			<thead>
	        	<tr>
	        		<c:choose>
	        			<c:when test="${modificaAbilitata}"><th data-field="azione" class="center" data-formatter="formatterIcon" data-switchable="false"></c:when>
	        			<c:otherwise><th data-field="azione" class="center" data-switchable="false"></c:otherwise>
	        		</c:choose>
						<c:if test="${!noInserisci}">
							<p:abilitazione-azione codiceQuadro="DTFIN" codiceAzione="INSERISCI">
								<a href="../cunembo231i/index.do"  style="text-decoration: none;"><i class="ico24 ico_add" title="Aggiungi nuovo record"></i></a>
							</p:abilitazione-azione>
						</c:if>
					</th>
					<th data-field="descrProcedimentoOggetto" data-sortable="true" title="Oggetto">Oggetto</th>
	        		<th data-field="dataFineLavoriStr" data-sortable="true" title="Data fine lavori">Data fine lavori</th>
	        		<th data-field="dataTermineRendicontazioneStr" data-sortable="true" title="Data termine rendicontazione">Data termine rendicontazione</th>
	        		<th data-field="dataProrogaStr" data-sortable="true" title="Data proroga">Data proroga</th>
	        		<th data-field="note" data-sortable="true">Note</th>
	        	</tr>
		    </thead>
		</table>
		<br>
		<table class="myovertable table table-hover table-condensed table-bordered">
				<colgroup>
					<col width="10%">
					<col  width="90%">
				</colgroup>
				<tbody>
				<tr>
					<th>Ultima modifica</th>
					<td><c:out value="${ultimaModifica}"></c:out></td>
				</tr>
				</tbody>
			</table>
		</m:panel>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script type="text/javascript">
	$( document ).ready(function() {
		$('#elencoDate').bootstrapTable();
	});

	function formatterIcon($value, row, index) 
	{
		if(index <= 0)
	    {
 	       var iconModify = "<a href=\"../cunembo231m/index.do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_modify\" title=\"Modifica\" style=\"font-size:1.4em;\"></i></a>";
 	       iconModify = iconModify +"<a onclick=\"return openPageInPopup('../cunembo231e/popupindex_"+row['idFineLavori']+".do','dlgElimina','Elimina Data Fine','modal-lg',false)\" href=\"popupindex_"+row['idFineLavori']+".do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_trash\" title=\"Elimina operazione in campo\"></i></a>";
		   return iconModify;
	    }
	}
</script>

<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
