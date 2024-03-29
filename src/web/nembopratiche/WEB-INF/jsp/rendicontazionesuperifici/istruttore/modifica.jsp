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
<link rel="stylesheet" href="/nembopratiche/bootstrap-table/src/bootstrap-table.css">
<body>
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />

	<div class="container-fluid" id="content">
		<m:panel id="panelElenco">
			<form:form action="" method="POST">
				<m:panel id="particelle" startOpened="true" title="Localizzazione particelle">
					<m:error />
					<table summary="conduzioni" id="tblConduzioni" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh " data-undefined-text=''>
						<thead>
							<tr>
								<th>Comune</th>
								<th>Sez.</th>
								<th>Foglio</th>
								<th>Part.</th>
								<th>Sub.</th>
								<th>Sup.<br />Catastale<br />(ha)
								</th>
								<th>Occupazione<br />del suolo
								</th>
								<th>Destinazione</th>
								<th>Uso</th>
								<th>Qualit&agrave;</th>
								<th>Variet&agrave;</th>
								<th>Sup.<br />utilizzata<br />(ha)
								</th>
								<th>Sup.<br />ammessa<br />(ha)
								</th>
								<th>Sup.<br />effettiva<br />richiesta (ha)
								</th>
								<th>Sup.<br />accertata<br />GIS (ha)
								</th>
								<th>Sup.<br />impianto di<br/>istruttoria (ha)
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${elenco}" var="p">
								<tr>
									<td>${p.descComune}</td>
									<td>${p.sezione}</td>
									<td>${p.foglio}</td>
									<td>${p.particella}</td>
									<td>${p.subalterno}</td>
									<td>${p.supCatastale}</td>
									<td><c:out value="${p.descTipoUtilizzo}" /></td>
									<td><c:out value="${p.descrizioneDestinazione}" /></td>
									<td><c:out value="${p.descTipoDettaglioUso}" /></td>
									<td><c:out value="${p.descrizioneQualitaUso}" /></td>
									<td><c:out value="${p.descTipoVarieta}" /></td>
									<td>${p.superficieUtilizzata}</td>
									<td>${p.superficieImpegno}</td>
									<td>${p.superficieEffettiva}</td>
									<td>${p.superficieAccertataGis}</td>
									<td><m:textfield id="superficieIstruttoria" name="superficieIstruttoria_${p.idConduzioneDichiarata}_${p.idUtilizzoDichiarato}"
											cssClass="superficieIstruttoria" value="${p.superficieIstruttoria}" maxlength="11" preferRequestValues="${preferRequest}"
											onkeyup="ricalcolaTotale('superficieIstruttoria','totale_superficieIstruttoria')"
                      onchange="ricalcolaTotale('superficieIstruttoria','totale_superficieIstruttoria')" /></td>
								</tr>
							</c:forEach>
						</tbody>
            <tfoot>
              <tr>
                <th colspan="11" style="text-align: right">Totale</th>
                <th class="numero"><fmt:formatNumber pattern="###,##0.0000" value="${totaleSupUtilizzata}" /></th>
                <th class="numero"><fmt:formatNumber pattern="###,##0.0000" value="${totaleSuperficieImpegno}" /></th>
                <th class="numero"><fmt:formatNumber pattern="###,##0.0000" value="${totaleSupEffettiva}" /></th>
                <th class="numero"><fmt:formatNumber pattern="###,##0.0000" value="${totaleSupGIS}" /></th>
                <th class="numero" id="totale_superficieIstruttoria"><fmt:formatNumber pattern="###,##0.0000" value="${totaleSupIstruttoria}" /></th>
              </tr>
            </tfoot>
					</table>
				</m:panel>
				<a class="btn btn-default" href="../cunembo212l/index.do">indietro</a>
				<input type="submit" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
			</form:form>
		</m:panel>
	</div>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	 <script type="text/javascript">
    function ricalcolaTotale(classTextField, idTotale)
    {
      let totale=0;
      $('.'+classTextField).each(function(index, tag)
      {
        let value=$(tag).val();
        let superficie = Number(value.replace(',', '.'));
        if (!isNaN(superficie))
        {
          totale+=superficie;
        }
      });
      $('#'+idTotale).html(totale.formatCurrency(4));
    } 
   </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />