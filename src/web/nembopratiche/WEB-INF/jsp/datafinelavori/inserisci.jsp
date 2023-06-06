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
	<p:breadcrumbs cdu="CU-NEMBO-231-I"/>
	<p:messaggistica/><p:testata cu="CU-NEMBO-231-I" />
	
	<div class="container-fluid" id="content">
		<m:panel id="panelElencoDate">
		<form:form action="" modelAttribute="" method="post" class="form-horizontal">
			<table id="elencoDate" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " >
				<colgroup>
					<col width="18%">
					<col width="18%">
					<col width="18%">
					<col width="46%">
				</colgroup>
				<thead>
		        	<tr>
		        		<th data-field="dataFineLavoriStr" data-sortable="true" title="Data fine lavori">Data fine lavori</th>
		        		<th data-field="dataTermineRendicontazioneStr" data-sortable="true" title="Data termine rendicontazione">Data termine rendicontazione</th>
		        		<th data-field="dataProrogaStr" data-sortable="true" title="Data proroga">Data proroga</th>
		        		<th data-field="note" data-sortable="true">Note</th>
		        	</tr>
			    </thead>
			    <tbody>
			    	<c:if test="${isProcedimentoOggettoIstanza}">
						<tr>
							<td></td>
							<td></td>
							<td><m:textfield id="data_proroga" name="data_proroga"  type="DATE" preferRequestValues="${preferRequestValues}"></m:textfield></td>
							<td><m:textarea name="note" id="note" preferRequestValues="${preferRequestValues}"></m:textarea></td>
						</tr>
				    	<c:forEach items="${elenco}" var="a" varStatus="idx">
							<tr>
								<td><c:out value="${a.dataFineLavoriStr}"></c:out></td>
								<td><c:out value="${a.dataTermineRendicontazioneStr}"></c:out></td>
								<td><c:out value="${a.dataProrogaStr}"></c:out></td>
								<td><c:out value="${a.note}"></c:out></td>
							</tr>
				    	</c:forEach>
			    	</c:if>
			    	
			    	<c:if test="${!isProcedimentoOggettoIstanza}">
			    		<tr>
							<td><m:textfield id="data_fine_lavori" name="data_fine_lavori" type="DATE" preferRequestValues="${preferRequestValues}"></m:textfield></td>
							<td>
								<c:if test="${isDomandaOIstruttoriaPagamento}">
									<m:textfield id="data_termine" name="data_termine"  type="DATE" preferRequestValues="${preferRequestValues}" disabled="true"></m:textfield>
								</c:if>
								<c:if test="${empty isDomandaOIstruttoriaPagamento}">
									<m:textfield id="data_termine" name="data_termine"  type="DATE" preferRequestValues="${preferRequestValues}"></m:textfield>
								</c:if>
							</td>
							<td></td>
							<td><m:textarea name="note" id="note" preferRequestValues="${preferRequestValues}"></m:textarea></td>
						</tr>
				    	<c:forEach items="${elenco}" var="a" varStatus="idx">
							<tr>
								<td><c:out value="${a.dataFineLavoriStr}"></c:out></td>
								<td><c:out value="${a.dataTermineRendicontazioneStr}"></c:out></td>
								<td><c:out value="${a.dataProrogaStr}"></c:out></td>
								<td><c:out value="${a.note}"></c:out></td>
							</tr>
				    	</c:forEach>
			    	</c:if>
			    </tbody>
			</table>
			
			<div class="form-group puls-group" style="margin-top: 2em">
				<div class="col-sm-12">
					<button type="button"
						onclick="forwardToPage('../cunembo231l/index.do');"
						class="btn btn-default">indietro</button>
					<button type="submit" name="conferma" id="conferma"
						class="btn btn-primary pull-right">conferma</button>
				</div>
			</div>
		</form:form>
		</m:panel>
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
