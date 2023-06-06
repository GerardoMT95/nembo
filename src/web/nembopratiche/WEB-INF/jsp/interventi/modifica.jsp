<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
  <p:set-cu-info/>	
  <r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}"/>
	<p:messaggistica/><p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelModificaInterv">
		<table summary="Elenco Interventi" class="table table-hover table-bordered " data-show-columns="true">
			<tbody>
				<tr>
					<c:if test="noProg">
						<th>Progr.</th>
					</c:if>
					<th>Intervento</th>
					<th>Ulteriori informazioni</th>
					<th>Comuni interessati</th>
					<th>Dato/Valore/UM</th>
					<th>Importo unitario</th>
					<th>Importo</th>
				</tr>
				<c:forEach items="interventi">
					<tr>
						<c:if test="noProg">
							<td></td>
						</c:if>
						<td>${descIntervento}</td>
						<td><textarea name="ulterioriInformazioni">${ulterioriInformazioni}</textarea></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a role="button" class="btn btn-primary" href="../cunemboxxx/index.do">Indietro</a> <br class="clear" /> <br />
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />