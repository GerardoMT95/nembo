<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal"
	url="/staticresources/assets/application/nembopratiche/include/head.html" />

<link rel="stylesheet"
	href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
		<style>
	span.tab-space {padding-left:0.4em;}

	</style>
<body>
	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />

	<p:breadcrumbs cdu="CU-NEMBO-175" />
	<p:messaggistica />
	<p:testata cu="CU-NEMBO-175" />


		<div class="container-fluid" id="content">
			<m:panel id="panelPosizioni">
				<c:if test="${msgErrore != null}">
					<div class="stdMessagePanel">
						<div class="alert alert-danger">
							<p>
								<strong>Attenzione!</strong><br />
								<c:out value="${msgErrore}"></c:out>
							</p>
						</div>
					</div>
				</c:if>
				
				
		
				<div class="container-fluid" id="content" style="margin-bottom: 3em">
					<h3>Posizione in graduatoria</h3>
					<c:choose>
					<c:when test="${elencoGraduatorie!=null}">
					<table id="posizioni"
						class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
						data-toggle="table" data-url="getElencoGraduatorie.json"
						data-undefined-text=''
						 data-detail-view="true"
						data-detail-formatter="detailFormatter">
						<thead>
							<tr>
								<th data-field="descrizioneRaggruppamento" data-sortable="true">Graduatoria</th>
								<th data-field="dataApprovazioneStr" data-sortable="true"
									data-sorter="dateSorterddmmyyyyHHmmss">Data Approvazione</th>
								<th data-field="posizione" data-sortable="true">Posizione</th>
								<th data-field="flagIstruttoria" data-sortable="true" data-formatter="SNFormatter">Istruibile</th>
								<th data-field="note" data-sortable="true">Note</th>
								
							</tr>
						</thead>
					</table>
					</c:when>
					<c:otherwise>
					<div>Nessuna graduatoria associata al procedimento selezionato</div>
					</c:otherwise>
				</c:choose>
				</div>

			</m:panel>
		</div>




	<r:include resourceProvider="portal"
		url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script src="/nembopratiche/bootstrap-table/src/bootstrap-table.js"></script>
	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	<script type="text/javascript">

	
		function SNFormatter(index, row) {
			var html = [];
	
			if(row['flagIstruttoria']=="S")
				html.push("SI");
			else
			if(row['flagIstruttoria']=="N")
					html.push("NO");
			return html.join('');
			
		}

		function detailFormatter(index, row) {
			
			var html = [];
								html.push('<table id="ordinamenti" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh">');
								html.push('<thead>');
								html.push('<tr>');
								if(row['ordinamento1']!=null && row['ordinamento1']!="")
									html.push('<th><span class="tab-space">'+ row['ordinamento1']+'</span></th>');
								if(row['ordinamento2']!=null && row['ordinamento2']!="")
									html.push('<th><span class="tab-space">'+ row['ordinamento2']+'</span></th>');
								if(row['ordinamento3']!=null && row['ordinamento3']!="")
									html.push('<th><span class="tab-space">'+ row['ordinamento3']+'</span></th>');
								if(row['ordinamento4']!=null && row['ordinamento4']!="")
									html.push('<th><span class="tab-space">'+ row['ordinamento4']+'</span></th>');
								if(row['ordinamento5']!=null && row['ordinamento5']!="")
									html.push('<th><span class="tab-space">'+ row['ordinamento5']+'</span></th>');
								html.push('</tr>');
								html.push('</thead>');
								html.push('<tbody>');
								html.push('<tr>');
								if(row['ordinamento1']!=null && row['ordinamento1']!="")
									html.push('<td>'+row['ord1Val']+'</td>');
								if(row['ordinamento2']!=null && row['ordinamento2']!="")
									html.push('<td>'+row['ord2Val']+'</td>');
								if(row['ordinamento3']!=null && row['ordinamento3']!="")
									html.push('<td>'+row['ord3Val']+'</td>');
								if(row['ordinamento4']!=null && row['ordinamento4']!="")
									html.push('<td>'+row['ord4Val']+'</td>');
								if(row['ordinamento5']!=null && row['ordinamento5']!="")
									html.push('<td>'+row['ord5Val']+'</td>');
								html.push('</tr>');
								html.push('</tbody>');
								html.push('</table>');
						
			return html.join('');
		}
	</script>

	<r:include resourceProvider="portal"
		url="/staticresources/assets/global/include/footerSP07.html" />