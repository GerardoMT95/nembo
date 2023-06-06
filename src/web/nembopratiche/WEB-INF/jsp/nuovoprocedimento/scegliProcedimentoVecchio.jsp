<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<link rel="stylesheet" href="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.css">


<body>
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
  <r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
  
<p:utente/>
	<div class="container-fluid">
        <div class="row">
            <div class="moduletable">
                <ul class="breadcrumb">
				  <li><a href="../index.do">Home</a> <span class="divider">/</span></li>
				  <li><a href="elencobando.do">Elenco bandi</a> <span class="divider">/</span></li>
				  <li><a href="dettaglioBando.do">Dettaglio bando</a> <span class="divider">/</span></li>
				  <li class="active">Elenco procedimenti bando precedente</li>
				</ul>
            </div>
        </div>           
    </div>
    <p:messaggistica/>
	  
	<div class="container-fluid" id="content" style="margin-bottom:3em;position:relative">
		<div class="stdMessagePanel"> 
			<div class="alert alert-warning">
				<p>
					Selezionare quale procedimento si vuole proseguire
				</p>
			</div>
		</div>
		
		<table id="elencoProcediemntiVecchi" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh" data-toggle="table"
				data-show-columns="true" data-show-filter="true" data-undefined-text=''
				 data-pagination="true" data-show-pagination-switch="true" data-pagination-v-align="top">
				<thead>
					<tr>
						<th class="center" data-field="idProcedimento" data-width="130"></th>
						<th data-field="cuaa" data-sortable="true">Cuaa</th>
						<th data-field="bandovecchio" data-sortable="true">Bando</th>
						<th data-field="identificativo" data-sortable="true">Identificativo</th>
						<th data-field="operazioni" data-sortable="true">Operazioni domanda</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${elencoProcedimenti}" var="a">
					<tr>
						<td><a href="../nuovoprocedimento/proseguiprocedimento_${idAzienda}_${a.idProcedimento}_${idBandoRiferimentoVecchio}.do"><i class="icon-circle-arrow-right icon-large"></i></a></td>
						<td><c:out value="${cuaa}"></c:out></td>
						<td><c:out value="${denominazioneBandovecchio}"></c:out></td>
						<td><c:out value="${a.identificativo}"></c:out></td>
						<td><c:out value="${a.elencoOperazioniHtml}" escapeXml="false"></c:out></td>
					</tr>
				</c:forEach>
				</tbody>
			 </table>
		
		
		
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>

<script type="text/javascript">

		
	</script>

<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>
