<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo227/index.do">Liste di liquidazione</a><span class="divider">/</span></li>
					<li class="active">Nuova lista di liquidazione</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<div class="container-fluid" id="content">
		<m:error />
		<form class="form-horizontal" method="post" action="crea_lista_${idBando}.do">
			<br />
			<c:if test="${errorMsg!=null}">
				<div class="alert alert-danger">
					<strong>${errorMsg}</strong>
				</div>
				<br style="clear: left" />
			</c:if>
			<m:textfield id="bando" name="bando" value="${livelliBando.denominazioneBando}" label="Bando" disabled="true" />
			<m:select id="livelli" list="${livelliBando.livelli}" name="livelli" label="Misura / Sottomisura / Operazione" disabled="true" multiple="true" header="false"
				textProperty="codiceDescrizione" size="10" />
			<m:textfield id="ammCompetenza" name="ammCompetenza" value="${descAmmCompetenza}" label="Amministrazione (Organismo Delegato)" disabled="true" />
			<m:textfield id="ammCompetenza" name="tipoImporto" value="${descTipoImporto}" label="Tipo importo" disabled="true" />
			<m:select id="idTecnico" name="idTecnico" list="${tecnici}" label="Funzionario liquidatore *" preferRequestValues="${preferRequest}" selectedValue="${idTecnicoLiquidatore}"/>
			<table id='tblRisorse' summary="Elenco risorse" class="table table-hover table-bordered tableBlueTh ">
				<thead>
					<tr>
						<th>Operazione</th>
						
						<c:if test="${showColumnUlterioreSpecializzazione == true}"><th>Ulteriore specializzazione</th></c:if>
						
						<th>Risorse attivate</th>
						<th>Importi gi&agrave; inseriti in liste di liquidazione precedenti</th>
						<th>Numero pagamenti da liquidare con questa lista</th>
						<th>Importi da liquidare con questa lista</th>
						<th>Importi rimanenti a disposizione</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${risorse}" var="r">
						<tr>
							<td>${r.codiceOperazione}</td>
							<c:if test="${showColumnUlterioreSpecializzazione == true}"><td><c:out value="${r.raggruppamento}"></c:out></td></c:if>
							<td class="numero"><fmt:formatNumber pattern="#,##0.00" value="${r.risorseAttivate}" />&nbsp;&euro;</td>
							<td class="numero"><fmt:formatNumber pattern="#,##0.00" value="${r.importoInLiquidazione}" />&nbsp;&euro;</td>
							<td class="numero">${r.numeroPagamentiLista}</td>
							<td class="numero"><fmt:formatNumber pattern="#,##0.00" value="${r.importoDaLiquidare}" />&nbsp;&euro;</td>
							<td class="numero" <c:if test="${r.importoRimanente<0}">style="font-weight:bold;color:red"</c:if>><fmt:formatNumber pattern="#,##0.00"
									value="${r.importoRimanente}" />&nbsp;&euro;</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<button class="btn btn-primary" role="button"
				onclick="return openPageInPopup('json/elenco_pratiche_nuova_lista_${idBando}.do', 'popup_elenco_pratiche_nuova_lista', 'Elenco pratiche', 'modal-lg')">Elenco pratiche in lista</button>
			<br style="clear:left"/>
			<br />
			<div class="col-sm-12">
				<a href="elenco_bandi.do" class="btn  btn-default pull-left">annulla</a>
				<c:if test="${errorMsg==null}">
					<button type="submit" class="btn  btn-primary pull-right" onclick="return singleClick()">conferma</button>
				</c:if>
			</div>
		</form>
		<br />
	</div>
	<br />
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

	<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
	<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
	<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />