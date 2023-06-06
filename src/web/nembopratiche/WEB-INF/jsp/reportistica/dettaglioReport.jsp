<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>

	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html"/>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}"/>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>

	<p:utente/>
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../elencoBandi/visualizzaBandi.do">Elenco bandi</a> <span class="divider">/</span></li>
					<li><a href="elencoreport_${idBandoGrafici}.do">Elenco report</a> <span class="divider">/</span></li>
					<li class="active">Dettaglio report</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="container-fluid form-horizontal"  id="content" style="margin-bottom:2em">
		<div class="alert alert-info" role="alert">
			Bando: <b>${denominazioneBando}</b><br/>
			Descrizione breve: <b>${tabella.descrBreve}</b><br/>
			Descrizione estesa: <b>${tabella.descrEstesa}</b>
		</div>
		<div align="center" id="pleaseWait"><span class="please_wait" style="vertical-align: middle"></span>Attendere prego, preparazione del report in corso...</div>
		
		<div id="dettEstrazioneCnt" style="display:none;overflow-x:auto ">
			<table id="dettEstrazioneTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh" 
				  data-toggle="table" 
				  data-undefined-text = ''
				  data-pagination="true" 
				  data-show-pagination-switch="true" 
				  data-show-toggle="true"
				  data-pagination-v-align="top" 
				  data-show-columns="true"
				  data-show-filter="true">
				<thead>
		        	<tr>
		        		<th data-switchable="false" data-width="40px"><a href="#" title="esporta in excel" onclick="forwardToPage('downloadExcelReport.xls')" class="ico24 ico_excel"></a></th>
						<c:forEach items="${tabella.jsonData['cols']}" var="col">
							<th data-field="${col.id}"><c:out value="${col.label}"></c:out></th>
						</c:forEach>
		        	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${tabella.jsonData['rows']}" var="row" >
				    	<tr>
				    	<td></td>
			    		<c:forEach items="${row['c']}" var="cell" >
			    			<td >
			    				${cell.valueFormatted}
			    			</td>
			    		</c:forEach>
				    	</tr>
			    	</c:forEach>
			    </tbody>
			</table>
		</div>
		<div class="form-group puls-group" style="margin-top:2em;margin-bottom:3em">
			<div class="pull-left">
				<button type="button"  onclick="history.go(-1);" class="btn btn-primary">Indietro</button>
			</div>
	    </div>
	</div>

<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>

<script src="/nembopratiche/bootstrap-table/dist/extensions/filter/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/bootstrap-table-filter.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/ext/bs-table.js"></script>
<script src="/nembopratiche/bootstrap-table/src/locale/bootstrap-table-it-IT.js"></script>
<script src="/nembopratiche/bootstrap-table-filter/src/locale/bootstrap-table-filter.it-IT.js"></script>

<script>

$('#dettEstrazioneTable').on("post-body.bs.table", function (data) {
	$('.fixed-table-loading').remove();
	$('#dettEstrazioneCnt').show();
	$('#pleaseWait').hide();
	
});

</script>

<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html"/>